package fabric.store.db;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.HashSet;
import java.util.Set;

import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.worker.memoize.CallInstance;

/**
 * Represents the various read and call dependencies of each CallInstance we are
 * caching.  Includes a mapping from CallInstances to their dependency sets and
 * from objects/CallInstances to a set of CallInstances that depend on them.
 */
public class SemanticWarrantyDependencies {
  /**
   * Mapping from a CallInstance's id to the set of onums of objects read during
   * that computation.
   */
  private final ConcurrentMap<CallInstance, LongSet> objectsRead;
  
  /**
   * Mapping from a CallInstance's id to the set of onums of objects created
   * during that computation.
   */
  private final ConcurrentMap<CallInstance, LongSet> objectsCreated;
  
  /**
   * Mapping from a CallInstance's id to the set of ids CallInstance used during
   * that computation.
   */
  private final ConcurrentMap<CallInstance, Set<CallInstance>> callsUsed;

  /**
   * Reverse mapping from an object's onum to the set of ids of CallInstances
   * that read the object.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> objectReaders;

  /**
   * Reverse mapping from an object's onum to the id of the CallInstance that
   * created the object.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> objectCreators;

  /**
   * Reverse mapping from a CallInstance's id to the set of ids of CallInstances
   * that use the call.
   */
  private final ConcurrentMap<CallInstance, Set<CallInstance>> callUsers;

  public SemanticWarrantyDependencies() {
    objectsRead = new ConcurrentHashMap<CallInstance, LongSet>();
    objectsCreated = new ConcurrentHashMap<CallInstance, LongSet>();
    callsUsed = new ConcurrentHashMap<CallInstance, Set<CallInstance>>();
    objectReaders = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    objectCreators = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    callUsers = new ConcurrentHashMap<CallInstance, Set<CallInstance>>();
  }

  /**
   * Insert a new call and set of dependencies into the table.
   *
   * This does <b>not</b> clear out old state if the call was inserted before.
   */
  public void addCall(CallInstance id, LongSet reads, LongSet creates,
      Set<CallInstance> calls) {
    // Clear out any left over state.
    removeCall(id);

    objectsRead.put(id, reads);
    objectsCreated.put(id, creates);
    callsUsed.put(id, calls);

    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext()) {
      long onum = readsIt.next();
      Set<CallInstance> readers = objectReaders.get(onum);
      if (readers == null) {
        readers = new HashSet<CallInstance>();
        objectReaders.put(onum, readers);
      }
      readers.add(id);
    }

    LongIterator createsIt = creates.iterator();
    while (createsIt.hasNext()) {
      long onum = createsIt.next();
      Set<CallInstance> creators = objectCreators.get(onum);
      if (creators == null) {
        creators = new HashSet<CallInstance>();
        objectCreators.put(onum, creators);
      }
      creators.add(id);
    }

    for (CallInstance callId : calls) {
      Set<CallInstance> users = callUsers.get(callId);
      if (users == null) {
        users = new HashSet<CallInstance>();
        callUsers.put(callId, users);
      }
      users.add(id);
    }
  }

  public void addCallDependency(CallInstance id, CallInstance subcall) {
    Set<CallInstance> calls = callsUsed.get(id);
    if (calls == null) return;
    calls.add(subcall);
    Set<CallInstance> callers = callUsers.get(subcall);
    if (callers == null) {
      callers = new HashSet<CallInstance>();
      callUsers.put(subcall, callers);
    }
    callers.add(id);
  }

  public void addReadDependency(CallInstance id, long read) {
    LongSet reads = objectsRead.get(id);
    if (reads == null) return;
    reads.add(read);
    Set<CallInstance> readers = objectReaders.get(read);
    if (readers == null) {
      readers = new HashSet<CallInstance>();
      objectReaders.put(read, readers);
    }
    readers.add(id);
  }

  public void addCreateDependency(CallInstance id, long create) {
    LongSet creates = objectsRead.get(id);
    if (creates == null) return;
    Set<CallInstance> creators = objectCreators.get(create);
    if (creators == null) {
      creators = new HashSet<CallInstance>();
      objectCreators.put(create, creators);
    }
    creators.add(id);
  }

  /**
   * Remove a call and it's dependencies from the table.
   *
   * Does not remove pointers from callers of this call, you have to pass up the
   * dependencies if you want that behavior.
   */
  public void removeCall(CallInstance id) {
    LongSet reads = objectsRead.get(id);
    objectsRead.remove(id);

    LongSet creates = objectsCreated.get(id);
    objectsCreated.remove(id);

    Set<CallInstance> calls = callsUsed.get(id);
    callsUsed.remove(id);

    if (reads != null) {
      LongIterator readsIt = reads.iterator();
      while (readsIt.hasNext()) {
        long read = readsIt.next();
        Set<CallInstance> readers = objectReaders.get(read);
        if (readers == null) continue;
        readers.remove(id);
      }
    }

    if (creates != null) {
      LongIterator createsIt = creates.iterator();
      while (createsIt.hasNext()) {
        long create = createsIt.next();
        objectCreators.remove(create);
      }
    }

    if (calls != null) {
      for (CallInstance call : calls) {
        Set<CallInstance> users = callUsers.get(call);
        if (users == null) continue;
        users.remove(id);
      }
    }
  }

  /**
   * Get the set of call ids that depend on the given call.
   */
  public Set<CallInstance> getCallers(CallInstance id) {
    Set<CallInstance> inTable = callUsers.get(id);
    inTable = inTable == null ? new HashSet<CallInstance>() : inTable;
    return inTable;
  }

  /**
   * Get the set of call ids that the given call used
   */
  public Set<CallInstance> getCalls(CallInstance id) {
    Set<CallInstance> inTable = callsUsed.get(id);
    return inTable == null ? new HashSet<CallInstance>() : inTable;
  }

  /**
   * Get the set of onums that the given call used
   */
  public LongSet getReads(CallInstance id) {
    LongSet inTable = objectsRead.get(id);
    return inTable == null ? new LongHashSet() : inTable;
  }

  /**
   * Get the set of call ids that depend on the given object onum.
   */
  public Set<CallInstance> getReaders(long onum) {
    Set<CallInstance> inTable = objectReaders.get(onum);
    return inTable == null ? new HashSet<CallInstance>() : inTable;
  }

  /**
   * Get the set of onums that the given call used
   */
  public LongSet getCreates(CallInstance id) {
    LongSet inTable = objectsCreated.get(id);
    return inTable == null ? new LongHashSet() : inTable;
  }

  /**
   * Get the set of call ids that depend on the given object onum.
   */
  public Set<CallInstance> getCreators(long onum) {
    Set<CallInstance> inTable = objectCreators.get(onum);
    return inTable == null ? new HashSet<CallInstance>() : inTable;
  }
}
