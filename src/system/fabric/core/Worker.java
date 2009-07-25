package fabric.core;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.ObjectGroup;
import fabric.common.AbstractWorkerThread;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.messages.*;

public class Worker extends AbstractWorkerThread<SessionAttributes, Worker> {

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
   * A factory for creating Worker instances. This is used by WorkerThread.Pool.
   */
  static class Factory implements AbstractWorkerThread.Factory<Worker> {
    public Worker createWorker(Pool<Worker> pool) {
      return new Worker(pool);
    }
  }

  /**
   * Instantiates a new worker thread and starts it running.
   */
  private Worker(Pool<Worker> pool) {
    super("Core worker", pool);
    this.pendingLogs = new LongKeyHashMap<LogRecord>();

    fabric.client.transaction.TransactionManager.startThread(this);
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }

  @Override
  protected void logStats() {
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

  @Override
  protected void resetStats() {
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

  public void handle(AbortTransactionMessage message) throws AccessException,
      ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Abort Message");
    session.core.tm.abortTransaction(session.clientPrincipal,
        message.tid.topTid);
    logger.fine("Transaction " + message.tid.topTid + " aborted");
  }

  /**
   * Processes the given request for new OIDs.
   */
  public AllocateMessage.Response handle(AllocateMessage msg)
      throws AccessException, ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Allocate Message");
    long[] onums = session.core.tm.newOnums(session.clientPrincipal, msg.num);
    return new AllocateMessage.Response(onums);
  }

  /**
   * Processes the given commit request
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage message) throws ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Commit Message");
    this.numCommits++;

    try {
      session.core.tm.commitTransaction(session.remoteNode,
          session.clientPrincipal, message.transactionID);
      logger.fine("Transaction " + message.transactionID + " committed");

      // updated object tallies
      LogRecord lr = pendingLogs.remove(message.transactionID);
      this.numCreates += lr.creates;
      this.numWrites += lr.writes;

      return new CommitTransactionMessage.Response(true);
    } catch (TransactionCommitFailedException e) {
      return new CommitTransactionMessage.Response(false, e.getMessage());
    }
  }

  /**
   * Processes the given PREPARE request.
   */
  public PrepareTransactionMessage.Response handle(PrepareTransactionMessage msg)
      throws ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Prepare Message");
    this.numPrepares++;

    PrepareRequest req =
        new PrepareRequest(msg.tid, msg.commitTime, msg.serializedCreates,
            msg.serializedWrites, msg.reads);

    session.core.sm.createSurrogates(req);

    try {
      boolean subTransactionCreated =
          session.core.tm.prepare(session.clientPrincipal, req);

      logger.fine("Transaction " + req.tid + " prepared");
      // Store the size of the transaction for debugging at the end of the
      // session
      // Note: this number does not include surrogates
      pendingLogs.put(req.tid, new LogRecord(msg.serializedCreates.size(),
          msg.serializedWrites.size()));

      return new PrepareTransactionMessage.Response(subTransactionCreated);
    } catch (TransactionPrepareFailedException e) {
      return new PrepareTransactionMessage.Response(e.getMessage(),
          e.versionConflicts);
    }
  }

  /**
   * Processes the given read request.
   */
  public ReadMessage.Response handle(ReadMessage msg) throws AccessException,
      ProtocolError {
    if (session.clientIsDissem)
      throw new ProtocolError("Message not supported.");

    logger.finer("Handling Read Message");
    this.numReads++;

    ObjectGroup group =
        session.core.tm.getGroup(session.clientPrincipal, session.remoteNode,
            msg.onum, this);
    return new ReadMessage.Response(group);
  }

  /**
   * Processes the given dissemination-read request.
   */
  public DissemReadMessage.Response handle(DissemReadMessage msg)
      throws AccessException {
    logger.finer("Handling DissemRead message");
    this.numReads++;

    Glob glob = session.core.tm.getGlob(msg.onum, session.remoteNode, this);
    if (glob != null) numGlobsSent++;

    return new DissemReadMessage.Response(glob);
  }

}
