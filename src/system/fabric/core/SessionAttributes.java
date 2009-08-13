package fabric.core;

import java.security.PrivateKey;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import fabric.client.Client;
import fabric.client.remote.RemoteClient;
import fabric.common.AbstractWorkerThread;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.NodePrincipal;

final class SessionAttributes extends AbstractWorkerThread.SessionAttributes {
  /**
   * Whether the client is a dissemination node.
   */
  final boolean clientIsDissem;

  /**
   * The core with which the client is interacting.
   */
  final Node.Core core;

  /**
   * The remote client node.
   */
  final RemoteClient remoteNode;

  /**
   * The name the remote principal.
   */
  final String clientPrincipalName;

  /**
   * The client's principal object.
   */
  final NodePrincipal clientPrincipal;

  /**
   * The private signing key for the core with which the client is interacting.
   * XXX This is currently the SSL private key. Should use the core's
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
  private static final Logger logger = Logger.getLogger("fabric.core.worker");

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
  SessionAttributes(Node.Core core, String clientName) {
    this.clientIsDissem = true;
    this.core = core;
    this.remoteNode = Client.getClient().getClient(clientName);
    this.clientPrincipalName = null;
    this.clientPrincipal = null;
    this.privateKey = core.privateKey;

    this.pendingLogs = new LongKeyHashMap<LogRecord>();
    resetStats();
  }

  /**
   * Constructs a SessionAttributes object corresponding to a client node.
   */
  SessionAttributes(Node.Core core, String clientName,
      String clientPrincipalName, NodePrincipal client) {
    this.clientIsDissem = false;
    this.core = core;
    this.remoteNode = Client.getClient().getClient(clientName);
    this.clientPrincipalName = clientPrincipalName;
    this.clientPrincipal = client;
    this.privateKey = core.privateKey;

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
