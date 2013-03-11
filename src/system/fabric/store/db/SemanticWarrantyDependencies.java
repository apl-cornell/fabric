package fabric.store.db;

import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;

/**
 * Represents the various read and call dependencies of each CallInstance we are
 * caching.  Includes a mapping from CallInstances to their dependency sets and
 * from objects/CallInstances to a set of CallInstances that depend on them.
 */
public class SemanticWarrantyDependencies {
  /**
   * Mapping from a CallInstance's id to the set of onums of object read during
   * that computation.
   */
  private final ConcurrentLongKeyMap<LongSet> objectsRead;
  
  /**
   * Mapping from a CallInstance's id to the set of ids CallInstance used during
   * that computation.
   */
  private final ConcurrentLongKeyMap<LongSet> callsUsed;

  /**
   * Reverse mapping from an object's onum to the set of ids of CallInstances
   * that read the object.
   */
  private final ConcurrentLongKeyMap<LongSet> objectReaders;

  /**
   * Reverse mapping from a CallInstance's id to the set of ids of CallInstances
   * that use the call.
   */
  private final ConcurrentLongKeyMap<LongSet> callUsers;

  public SemanticWarrantyDependencies() {
    objectsRead = new ConcurrentLongKeyHashMap<LongSet>();
    callsUsed = new ConcurrentLongKeyHashMap<LongSet>();
    objectReaders = new ConcurrentLongKeyHashMap<LongSet>();
    callUsers = new ConcurrentLongKeyHashMap<LongSet>();
  }

  /**
   * Insert a new call and set of dependencies into the table.
   *
   * This does <b>not</b> clear out old state if the call was inserted before.
   */
  public void addCall(long id, LongSet reads, LongSet calls) {
    objectsRead.put(id, reads);
    callsUsed.put(id, calls);

    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext()) {
      long onum = readsIt.next();
      LongSet readers = objectReaders.get(onum);
      if (readers == null) {
        readers = new LongHashSet();
        objectReaders.put(onum, readers);
      }
      readers.add(id);
    }

    LongIterator callsIt = calls.iterator();
    while (callsIt.hasNext()) {
      long callId = callsIt.next();
      LongSet users = callUsers.get(callId);
      if (users == null) {
        users = new LongHashSet();
        callUsers.put(callId, users);
      }
      users.add(id);
    }
  }

  /**
   * Remove a call and it's dependencies from the table.
   */
  public void removeCall(long id) {
    LongSet objects = objectsRead.get(id);
    objectsRead.remove(id);

    LongSet calls = callsUsed.get(id);
    callsUsed.remove(id);

    if (objects != null) {
      LongIterator objectsIt = objects.iterator();
      while (objectsIt.hasNext()) {
        long object = objectsIt.next();
        LongSet readers = objectReaders.get(object);
        if (readers == null) continue;
        readers.remove(id);
      }
    }

    if (calls != null) {
      LongIterator callsIt = calls.iterator();
      while (callsIt.hasNext()) {
        long call = callsIt.next();
        LongSet users = callUsers.get(call);
        if (users == null) continue;
        users.remove(id);
      }
    }
  }

  /**
   * Update a call with a new set of read and call dependencies in the table.
   */
  public void updateCall(long id, LongSet reads, LongSet calls) {
    /* TODO: This is probably not threadsafe? */
    removeCall(id);
    addCall(id, reads, calls);
  }

  /**
   * Get the set of call ids that depend on the given call.
   */
  public LongSet getCallers(long id) {
    return callUsers.get(id);
  }

  /**
   * Get the set of call ids that depend on the given object onum.
   */
  public LongSet getReaders(long onum) {
    return objectReaders.get(onum);
  }
}
