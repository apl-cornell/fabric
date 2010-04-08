package fabric.store;

import java.security.PrivateKey;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.common.AbstractMessageHandlerThread;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.security.NodePrincipal;

final class SessionAttributes extends AbstractMessageHandlerThread.SessionAttributes {
  /**
   * Whether the worker is a dissemination node.
   */
  final boolean workerIsDissem;

  /**
   * The store with which the worker is interacting.
   */
  final Node.Store store;

  /**
   * The remote worker node.
   */
  final RemoteWorker remoteNode;

  /**
   * The name the remote principal.
   */
  final String workerPrincipalName;

  /**
   * The worker's principal object.
   */
  final NodePrincipal workerPrincipal;

  /**
   * The private signing key for the store with which the worker is interacting.
   * XXX This is currently the SSL private key. Should use the store's
   * NodePrincipal's key instead.
   */
  final PrivateKey privateKey;

  // Bookkeeping information for debugging/monitoring purposes:
  private int numReads;
  int numObjectsSent;
  private int numGlobsSent;
  int numGlobbedObjects;
  int numGlobsCreated;
  private int numPrepares;
  private int numCommits;
  private int numCreates;
  private int numWrites;
  Map<String, Integer> numSendsByType;
  private static final Logger logger = Logger.getLogger("fabric.store.MessageHandler");

  /** Associates debugging log messages with pending transactions */
  private final LongKeyMap<LogRecord> pendingLogs;

  private class LogRecord {
    public LogRecord(int creates, int writes) {
      this.creates = creates;
      this.writes = writes;
    }

    public int creates;
    public int writes;
  }

  /**
   * Constructs a SessionAttributes object corresponding to a dissemination
   * node.
   */
  SessionAttributes(Node.Store store, String workerName) {
    this.workerIsDissem = true;
    this.store = store;
    this.remoteNode = Worker.getWorker().getWorker(workerName);
    this.workerPrincipalName = null;
    this.workerPrincipal = null;
    this.privateKey = store.privateKey;

    this.pendingLogs = new LongKeyHashMap<LogRecord>();
    resetStats();
  }

  /**
   * Constructs a SessionAttributes object corresponding to a worker node.
   */
  SessionAttributes(Node.Store store, String workerName,
      String workerPrincipalName, NodePrincipal worker) {
    this.workerIsDissem = false;
    this.store = store;
    this.remoteNode = Worker.getWorker().getWorker(workerName);
    this.workerPrincipalName = workerPrincipalName;
    this.workerPrincipal = worker;
    this.privateKey = store.privateKey;

    this.pendingLogs = new LongKeyHashMap<LogRecord>();
    resetStats();
  }

  /**
   * Resets any bookkeeping information for debugging/monitoring purposes.
   */
  private void resetStats() {
    // Reset the statistics counters.
    numReads =
        numObjectsSent =
            numGlobsSent =
                numGlobsCreated =
                    numGlobbedObjects =
                        numPrepares = numCommits = numCreates = numWrites = 0;
    numSendsByType = new TreeMap<String, Integer>();
    pendingLogs.clear();
  }

  @Override
  public void endSession() {
    // Report any statistics that may have been recorded.
    logger.info(numReads + " read requests");
    logger.info(numObjectsSent + " objects sent");
    logger.info(numGlobsSent + " encrypted globs sent");
    logger.info(numGlobsCreated + " encrypted globs created");
    if (numGlobsCreated != 0)
      logger.info("  " + (numGlobbedObjects / numGlobsCreated)
          + " objects per glob");
    logger.info(numPrepares + " prepare requests");
    logger.info(numCommits + " commit requests");
    logger.info(numCreates + " objects created");
    logger.info(numWrites + " objects updated");

    for (Map.Entry<String, Integer> entry : numSendsByType.entrySet()) {
      logger.info("\t" + entry.getValue() + " " + entry.getKey() + " sent");
    }
  }

  protected synchronized void recordCommitAttempt() {
    numCommits++;
  }

  protected synchronized void recordCommitSuccess(long transactionID) {
    LogRecord lr = pendingLogs.remove(transactionID);
    this.numCreates += lr.creates;
    this.numWrites += lr.writes;
  }

  protected synchronized void recordPrepare() {
    numPrepares++;
  }

  protected synchronized void recordRead() {
    numReads++;
  }

  protected synchronized void recordGlobSent() {
    numGlobsSent++;
  }

  protected synchronized void recordGlobCreated(int globSize) {
    numGlobsCreated++;
    numGlobbedObjects += globSize;
  }

  protected synchronized void recordObjectSent(String className) {
    int count = 0;
    numObjectsSent++;
    if (numSendsByType.containsKey(className))
      count = numSendsByType.get(className);
    count++;
    numSendsByType.put(className, count);
  }

  protected synchronized void addPendingLog(long tid, int numCreates,
      int numWrites) {
    pendingLogs.put(tid, new LogRecord(numCreates, numWrites));
  }
}
