package fabric.worker.transaction;

import fabric.worker.Store;
import fabric.common.TransactionID;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * Holds a map from top-level transaction IDs to their logs.
 */
public final class TransactionRegistry {
  /**
   * Maps top-level transaction IDs to their top-level transaction logs.
   */
  private static final LongKeyMap<Log> registry = new LongKeyHashMap<Log>();

  /**
   * Returns the innermost transaction log for the given top-level tid (or null
   * if no such log exists).
   */
  public static Log getInnermostLog(long tid) {
    Log log;
    synchronized (registry) {
      log = registry.get(tid);
    }

    if (log == null) return null;

    return innermostLog(log);
  }

  /**
   * Returns the innermost transaction log whose top-level tid the same as that
   * of the given tid. If no such log exists, a log is created for the given tid
   * and is returned.
   */
  public static Log getOrCreateInnermostLog(TransactionID tid) {
    if (tid == null) return null;

    Log log;
    synchronized (registry) {
      log = registry.get(tid.topTid);
      if (log == null) log = new Log(tid);
    }

    return innermostLog(log);
  }

  /**
   * Registers a newly created top-level transaction log.
   */
  public static void register(Log log) {
    synchronized (registry) {
      if (registry.get(log.tid.topTid) != null)
        throw new InternalError("Attempted to register a transaction log "
            + "whose top-level tid conflicts with a previously registered log.");

      registry.put(log.tid.topTid, log);
    }
  }

  public static void remove(long topTid) {
    synchronized (registry) {
      registry.remove(topTid);
    }
  }

  private static Log innermostLog(Log log) {
    Log child = log.getChild();
    while (child != null) {
      log = child;
      child = log.getChild();
    }

    return log;
  }

  /**
   * Goes through all transaction logs and performs an onum renumbering. This is
   * used by fabric.lang.Object.$forceRenumber. Do not call this unless if you
   * really know what you are doing.
   * 
   * @deprecated
   */
  public static void renumberObject(Store store, long onum, long newOnum) {
    for (Log log : registry.values()) {
      log.renumberObject(store, onum, newOnum);
    }
  }
}
