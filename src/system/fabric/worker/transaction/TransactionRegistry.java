package fabric.worker.transaction;

import fabric.common.TransactionID;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.worker.Store;

/**
 * Holds a map from top-level transaction IDs to their logs.
 */
public final class TransactionRegistry {
  /**
   * Maps top-level transaction IDs to their top-level transaction logs.
   */
  private static final ConcurrentLongKeyMap<Log> registry =
      new ConcurrentLongKeyHashMap<>();

  /**
   * Returns the top-level transaction log for the given top-level TID (or null
   * if no such log exists).
   */
  public static Log getTopmostLog(long tid) {
    return registry.get(tid);
  }

  /**
   * Returns the innermost transaction log for the given top-level tid (or null
   * if no such log exists).
   */
  public static Log getInnermostLog(long tid) {
    Log log = registry.get(tid);
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

    Log log = registry.get(tid.topTid);
    if (log == null) log = new Log(tid);

    return innermostLog(log);
  }

  /**
   * Registers a newly created top-level transaction log.
   */
  public static void register(Log log) {
    if (registry.putIfAbsent(log.tid.topTid, log) != null) {
      throw new InternalError("Attempted to register a transaction log "
          + "whose top-level tid conflicts with a previously registered log.");
    }
  }

  public static void remove(long topTid) {
    registry.remove(topTid);
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
   * used by fabric.lang.Object.$forceRenumber. Do not call this unless you
   * really know what you are doing.
   *
   * @deprecated
   */
  @Deprecated
  public static void renumberObject(Store store, long onum, long newOnum) {
    for (Log log : registry.values()) {
      log.renumberObject(store, onum, newOnum);
    }
  }
}
