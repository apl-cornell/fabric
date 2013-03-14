package fabric.store.db;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.HashSet;
import java.util.Set;

import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.worker.memoize.CallID;

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
  private final ConcurrentMap<CallID, LongSet> objectsRead;
  
  /**
   * Mapping from a CallInstance's id to the set of ids CallInstance used during
   * that computation.
   */
  private final ConcurrentMap<CallID, Set<CallID>> callsUsed;

  /**
   * Reverse mapping from an object's onum to the set of ids of CallInstances
   * that read the object.
   */
  private final ConcurrentLongKeyMap<Set<CallID>> objectReaders;

  /**
   * Reverse mapping from a CallInstance's id to the set of ids of CallInstances
   * that use the call.
   */
  private final ConcurrentMap<CallID, Set<CallID>> callUsers;

  public SemanticWarrantyDependencies() {
    objectsRead = new ConcurrentHashMap<CallID, LongSet>();
    callsUsed = new ConcurrentHashMap<CallID, Set<CallID>>();
    objectReaders = new ConcurrentLongKeyHashMap<Set<CallID>>();
    callUsers = new ConcurrentHashMap<CallID, Set<CallID>>();
  }

  /**
   * Insert a new call and set of dependencies into the table.
   *
   * This does <b>not</b> clear out old state if the call was inserted before.
   */
  public void addCall(CallID id, LongSet reads, Set<CallID> calls) {
    objectsRead.put(id, reads);
    callsUsed.put(id, calls);

    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext()) {
      long onum = readsIt.next();
      Set<CallID> readers = objectReaders.get(onum);
      if (readers == null) {
        readers = new HashSet<CallID>();
        objectReaders.put(onum, readers);
      }
      readers.add(id);
    }

    for (CallID callId : calls) {
      Set<CallID> users = callUsers.get(callId);
      if (users == null) {
        users = new HashSet<CallID>();
        callUsers.put(callId, users);
      }
      users.add(id);
    }
  }

  /**
   * Remove a call and it's dependencies from the table.
   */
  public void removeCall(CallID id) {
    LongSet objects = objectsRead.get(id);
    objectsRead.remove(id);

    Set<CallID> calls = callsUsed.get(id);
    callsUsed.remove(id);

    if (objects != null) {
      LongIterator objectsIt = objects.iterator();
      while (objectsIt.hasNext()) {
        long object = objectsIt.next();
        Set<CallID> readers = objectReaders.get(object);
        if (readers == null) continue;
        readers.remove(id);
      }
    }

    if (calls != null) {
      for (CallID call : calls) {
        Set<CallID> users = callUsers.get(call);
        if (users == null) continue;
        users.remove(id);
      }
    }
  }

  /**
   * Update a call with a new set of read and call dependencies in the table.
   */
  public void updateCall(CallID id, LongSet reads, Set<CallID> calls) {
    /* TODO: This is probably not threadsafe? */
    removeCall(id);
    addCall(id, reads, calls);
  }

  /**
   * Get the set of call ids that depend on the given call.
   */
  public Set<CallID> getCallers(CallID id) {
    return callUsers.get(id);
  }

  /**
   * Get the set of call ids that depend on the given object onum.
   */
  public Set<CallID> getReaders(long onum) {
    return objectReaders.get(onum);
  }
}
