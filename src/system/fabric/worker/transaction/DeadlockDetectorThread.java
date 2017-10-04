package fabric.worker.transaction;

import static fabric.common.Logging.WORKER_DEADLOCK_LOGGER;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import fabric.common.Logging;
import fabric.common.TransactionID;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;

/**
 * On request, examines the waits-for graph for deadlocks. If a deadlock is
 * detected (in the form of a waits-for cycle), the deadlock is resolved by
 * restarting a transaction involved in the deadlock.
 */
public class DeadlockDetectorThread extends Thread {

  /**
   * Queue of logs of transactions for which deadlock-detection requests have
   * been made.
   */
  private BlockingQueue<Log> detectRequests;

  /**
   * Constructs a deadlock detector thread and starts it running.
   */
  public DeadlockDetectorThread() {
    super("Deadlock detector");
    setDaemon(true);
    this.detectRequests = new LinkedBlockingQueue<>();
    start();
  }

  /**
   * Requests a deadlock detection for the waits-for graph involving the given
   * transaction log.
   */
  void requestDetect(Log log) {
    detectRequests.add(log);
    WORKER_DEADLOCK_LOGGER.log(Level.FINEST,
        "Deadlock detection requested for {0}", log);
  }

  @Override
  public void run() {
    while (true) {
      try {
        // Wait for a request.
        Set<Log> requests = new LinkedHashSet<>();
        requests.add(detectRequests.take());

        // Obtain all requests made thus far and iterate over them.
        detectRequests.drainTo(requests);

        WORKER_DEADLOCK_LOGGER.log(Level.FINER,
            "Performing deadlock detection for {0}", requests);

        // Don't loop with an iterator. The set of requests will be modified as
        // requested nodes are encountered during the call to findCycles().
        while (!requests.isEmpty()) {
          // Grab the next request and find cycles in the waits-for graph
          // reachable from that transaction.
          Set<Set<Log>> cycles =
              findCycles(requests.iterator().next(), new LongKeyHashMap<Log>(),
                  new LongHashSet(), new HashSet<Set<Log>>(), requests);

          Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINE,
              "Found {0} deadlocks: {1}", cycles.size(), cycles);

          resolveDeadlocks(cycles);
        }
      } catch (InterruptedException e) {
        Logging.logIgnoredInterruptedException(e);
      } catch (RuntimeException | Error e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Finds cycles in the waits-for graph.
   *
   * @param curLog
   *          the current node being visited.
   * @param pathToTid
   *          a map representing the set of nodes in the waits-for path up to
   *          the current node, but excluding the current node itself. (In the
   *          case of a cycle, pathToTid will already contain a log with the
   *          same top-level TID as the current node.) Nodes in the path are
   *          represented by mapping the node's top-level TID to the node's
   *          nested-transaction log.
   * @param topLevelTidsVisited
   *          the set of top-level TIDs that have been visited already
   *          (excluding that of curLog if this is the first time visiting
   *          curLog's top-level TID).
   * @param cyclesFound
   *          the cycles found so far. Any cycles found during the call will be
   *          added to this set, and this set is returned.
   * @param requests
   *          the set of transaction logs for which deadlock-detection requests
   *          have been made.
   *
   * @return cyclesFound with any found cycles added.
   */
  private Set<Set<Log>> findCycles(Log curLog, LongKeyMap<Log> pathToTid,
      LongSet topLevelTidsVisited, Set<Set<Log>> cyclesFound,
      Set<Log> requests) {
    // Remove the current TID from the set of deadlock-detection requests, since
    // we are handling it now.
    requests.remove(curLog);

    TransactionID curTid = curLog.getTid();
    if (curTid == null) {
      // Transaction was committed/aborted.
      return cyclesFound;
    }

    long curTopTid = curTid.topTid;

    // Check for cycle.
    if (pathToTid.containsKey(curTopTid)) {
      // Cycle detected. Add to the list of cycles found and return.
      // This computes a conservative approximation, since not all path elements
      // are necessarily involved in the cycle.
      Set<Log> cycle = new HashSet<>();
      cycle.add(curLog.getOutermost());
      for (Log pathElement : pathToTid.values()) {
        if (pathElement.getTid().topTid != curTopTid) {
          cycle.add(pathElement.getOutermost());
        }
      }

      cyclesFound.add(cycle);
      return cyclesFound;
    }

    // Skip if we've previously visited this node.
    if (topLevelTidsVisited.contains(curTopTid)) return cyclesFound;

    // Gather up the logs for all the transactions that curLog is waiting for.
    Set<Log> waitsFor = curLog.getWaitsFor();

    // Add the given tid to the path and set of top-level TIDs visited, and
    // recurse.
    pathToTid.put(curTopTid, curLog);
    topLevelTidsVisited.add(curTopTid);
    try {
      for (Log waitsForLog : waitsFor) {
        findCycles(waitsForLog, pathToTid, topLevelTidsVisited, cyclesFound,
            requests);
      }
    } finally {
      pathToTid.remove(curTopTid);
    }

    return cyclesFound;
  }

  /**
   * A comparator on Logs that orders by transaction depth (outer transactions
   * first.)
   */
  private static final Comparator<Log> LOG_COMPARATOR = new Comparator<Log>() {
    @Override
    public int compare(Log log1, Log log2) {
      TransactionID tid1 = log1.getTid();
      TransactionID tid2 = log2.getTid();
      if (tid1.topTid == tid2.topTid) {
        return tid1.depth - tid2.depth;
      }

      // Incomparable.
      return 0;
    }
  };

  /**
   * Resolves deadlocks by aborting transactions.
   *
   * @param cycles
   *          the set of deadlocks, represented by the logs of transactions
   *          involved in waits-for cycles.
   */
  private void resolveDeadlocks(Set<Set<Log>> cycles) {
    // Turn the set of cycles into a map from top-level TIDs to sorted multisets
    // of transaction logs. The multisets are sorted by transaction depth, outer
    // transactions first.
    LongKeyMap<Multiset<Log>> logsByTopLevelTid = new LongKeyHashMap<>();
    for (Set<Log> cycle : cycles) {
      for (Log log : cycle) {
        long topLevelTid = log.getTid().topTid;
        Multiset<Log> logs = logsByTopLevelTid.get(topLevelTid);
        if (logs == null) {
          logs = TreeMultiset.create(LOG_COMPARATOR);
          logsByTopLevelTid.put(topLevelTid, logs);
        }

        logs.add(log);
      }
    }

    // Abort transactions to break up cycles. Transactions involved in more
    // cycles are aborted first.
    while (!cycles.isEmpty()) {
      // Figure out which top-level transaction(s) is involved in the most number
      // of deadlocks.
      int curMax = 0;
      LongSet abortCandidates = new LongHashSet();
      for (LongKeyMap.Entry<Multiset<Log>> entry : logsByTopLevelTid
          .entrySet()) {
        int curSize = entry.getValue().size();
        if (curMax > curSize) continue;

        if (curMax < curSize) {
          curMax = curSize;
          abortCandidates.clear();
        }

        abortCandidates.add(entry.getKey());
      }

      // Figure out which transaction to abort. (Pick the newest one.)
      Log toAbort = null;
      Multiset<Log> abortSet = null;
      for (LongIterator it = abortCandidates.iterator(); it.hasNext();) {
        long curTopLevelTid = it.next();
        Multiset<Log> curCandidateSet = logsByTopLevelTid.get(curTopLevelTid);
        Log curCandidate = curCandidateSet.iterator().next();

        if (toAbort == null || toAbort.startTime < curCandidate.startTime) {
          toAbort = curCandidate;
          abortSet = curCandidateSet;
        }
      }

      // Abort the transaction.
      WORKER_DEADLOCK_LOGGER.log(Level.FINE, "Aborting {0}", toAbort);
      toAbort.flagRetry();

      // Fix up our data structures to reflect the aborted transaction.
      for (Iterator<Set<Log>> cycleIt = cycles.iterator(); cycleIt.hasNext();) {
        Set<Log> cycle = cycleIt.next();

        // Check if the cycle has a transaction that was aborted.
        if (!haveCommonElements(cycle, abortSet.elementSet())) continue;

        // Cycle was broken, so remove from the set of cycles.
        cycleIt.remove();

        // Fix up logsByTopLevelTid.
        for (Log log : cycle) {
          long topLevelTid = log.getTid().topTid;
          Multiset<Log> logs = logsByTopLevelTid.get(topLevelTid);
          logs.remove(log);
          if (logs.isEmpty()) {
            logsByTopLevelTid.remove(topLevelTid);
          }
        }
      }
    }
  }

  private static <T> boolean haveCommonElements(Set<T> set1, Set<T> set2) {
    // Make set1 the smaller set.
    if (set1.size() > set2.size()) {
      Set<T> tmp = set1;
      set1 = set2;
      set2 = tmp;
    }

    for (T t : set1) {
      if (set2.contains(t)) return true;
    }

    return false;
  }
}
