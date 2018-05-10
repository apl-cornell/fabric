package fabric.store;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;
import static fabric.store.db.ObjectDB.UpdateMode.CREATE;
import static fabric.store.db.ObjectDB.UpdateMode.WRITE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.Pair;
import fabric.lang.security.Principal;
import fabric.store.db.ObjectDB;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;

/**
 * A convenience class for grouping together the created, modified, and read
 * object sets of a prepare request.
 *
 * @author mdgeorge
 */
public final class PrepareRequest {
  /**
   * Generic class for various kinds of prepares for an object.
   *
   * TODO: Need to override equals for these objects.
   */
  public static abstract class ItemPrepare implements Comparable<ItemPrepare> {
    public static enum PrepareType {
      READ("READ"), EXTENSION("EXTENSION"), WRITE("WRITE"), CREATE("CREATE");

      private final String name;

      PrepareType(String name) {
        this.name = name;
      }

      @Override
      public String toString() {
        return name;
      }
    }

    /**
     * Runs a preparation or version check, depending on whether the request is
     * already doomed.
     *
     * @param database
     *          the {@link ObjectDB} the item is being prepared on.
     * @param worker
     *          the {@link Principal} performing the prepare.
     * @param versionConflicts
     *          a map from onums to updated copies of objects for which there
     *          have been version conflicts so far, to be added to if this item
     *          has a version conflict.
     * @param longerContracts
     *          a map from onums to updated expiries of objects for which the
     *          expiration on the database is longer for the same version number
     *          than the value seen by the worker.
     * @param failures
     *          list of prepare failed exceptions already encountered in the
     *          request.
     */
    public void prepareOrCheck(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts,
        List<TransactionPrepareFailedException> failures, long tid) {
      if (versionConflicts.isEmpty() && failures.isEmpty()) {
        try {
          prepare(database, worker, versionConflicts, longerContracts);
        } catch (TransactionPrepareFailedException e) {
          failures.add(e);
        }
      } else {
        // We're already doomed, so don't lock things, just check for more
        // conflicts and contracts
        SerializedObject value = database.read(getOnum());
        if (value != null) {
          if (value.getVersion() != getVersion()) {
            versionConflicts.put(getOnum(), value);
          } else if (value.getExpiry() > getExpiry()) {
            longerContracts.put(getOnum(), value.getExpiry());
          }
        }
      }
    }

    /**
     * Performs the prepare for a single object.
     *
     * @param database
     *          the {@link ObjectDB} the item is being prepared on.
     * @param worker
     *          the {@link Principal} performing the prepare.
     * @param versionConflicts
     *          a map from onums to updated copies of objects for which there
     *          have been version conflicts so far, to be added to if this item
     *          has a version conflict.
     * @param longerContracts
     *          a map from onums to updated expiries of objects for which the
     *          expiration on the database is longer for the same version number
     *          than the value seen by the worker.
     */
    public abstract void prepare(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts)
        throws TransactionPrepareFailedException;

    /**
     * @return the onum of the item being prepared.
     */
    public abstract long getOnum();

    /**
     * @return the version of the item being prepared.
     */
    public abstract int getVersion();

    /**
     * @return the expiry of the item being prepared.
     */
    public abstract long getExpiry();

    /**
     * @return the {@link PrepareType} this prepare object is.
     */
    public abstract PrepareType type();

    @Override
    public int compareTo(ItemPrepare other) {
      int onumCompare = Long.compare(getOnum(), other.getOnum());
      if (onumCompare == 0) {
        onumCompare = Integer.compare(type().ordinal(), other.type().ordinal());
      }
      return onumCompare;
    }

    @Override
    public String toString() {
      return type().toString() + " OF " + getOnum() + "v" + getVersion()
          + "(until " + getExpiry() + ")";
    }
  }

  /**
   * Object for a read prepare on an onum.
   */
  public class ReadPrepare extends ItemPrepare {
    private final long onum;
    private final int version;
    private final long expiry;

    public ReadPrepare(long onum, int version, long expiry) {
      this.onum = onum;
      this.version = version;
      this.expiry = expiry;
    }

    @Override
    public void prepare(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts)
        throws TransactionPrepareFailedException {
      database.prepareRead(tid, worker, onum, version, expiry, versionConflicts,
          longerContracts);
    }

    @Override
    public long getOnum() {
      return onum;
    }

    @Override
    public long getExpiry() {
      return expiry;
    }

    @Override
    public int getVersion() {
      return version;
    }

    @Override
    public PrepareType type() {
      return PrepareType.READ;
    }
  }

  /**
   * Object for a expiry extension prepare on an onum.
   */
  public class ExtensionPrepare extends ItemPrepare {
    private final ExpiryExtension extension;

    public ExtensionPrepare(ExpiryExtension extension) {
      this.extension = extension;
    }

    @Override
    public void prepare(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts)
        throws TransactionPrepareFailedException {
      database.prepareExtension(tid, worker, extension, versionConflicts,
          longerContracts);
    }

    @Override
    public long getOnum() {
      return extension.onum;
    }

    @Override
    public long getExpiry() {
      return extension.expiry;
    }

    @Override
    public int getVersion() {
      return extension.version;
    }

    @Override
    public PrepareType type() {
      return PrepareType.EXTENSION;
    }
  }

  /**
   * Object for a write prepare on an onum.
   */
  public class WritePrepare extends ItemPrepare {
    private final SerializedObject val;

    public WritePrepare(SerializedObject val) {
      this.val = val;
    }

    @Override
    public void prepare(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts)
        throws TransactionPrepareFailedException {
      database.prepareUpdate(tid, worker, val, versionConflicts,
          longerContracts, WRITE);
    }

    @Override
    public long getOnum() {
      return val.getOnum();
    }

    @Override
    public long getExpiry() {
      return val.getExpiry();
    }

    @Override
    public int getVersion() {
      return val.getVersion();
    }

    @Override
    public PrepareType type() {
      return PrepareType.WRITE;
    }
  }

  /**
   * Object for a create prepare on an onum.
   */
  public class CreatePrepare extends ItemPrepare {
    private final SerializedObject val;

    public CreatePrepare(SerializedObject val) {
      this.val = val;
    }

    @Override
    public void prepare(ObjectDB database, Principal worker,
        LongKeyMap<SerializedObject> versionConflicts,
        LongKeyMap<Long> longerContracts)
        throws TransactionPrepareFailedException {
      database.prepareUpdate(tid, worker, val, versionConflicts,
          longerContracts, CREATE);
    }

    @Override
    public long getOnum() {
      return val.getOnum();
    }

    @Override
    public long getExpiry() {
      return val.getExpiry();
    }

    @Override
    public int getVersion() {
      return val.getVersion();
    }

    @Override
    public PrepareType type() {
      return PrepareType.CREATE;
    }
  }

  public final long tid;

  /** The set of created objects */
  public final Collection<SerializedObject> creates;

  /**
   * The collection of modified objects
   */
  public final Collection<SerializedObject> writes;

  /** The object numbers, version numbers, and expiries of the read objects */
  public final LongKeyMap<Pair<Integer, Long>> reads;

  /**
   * The collection of extensions
   */
  public final Collection<ExpiryExtension> extensions;

  /**
   * The delayed extensions triggered by this transaction.
   * Maps onums being committed to oids that will be extended.
   */
  public final LongKeyMap<Set<Oid>> extensionsTriggered;

  /**
   * The delayed extensions with no trigger that should run at the store after
   * this commits.
   */
  public final LongSet delayedExtensions;

  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest(long tid, Collection<SerializedObject> creates,
      Collection<SerializedObject> writes,
      LongKeyMap<Pair<Integer, Long>> reads,
      Collection<ExpiryExtension> extensions,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions) {
    this.tid = tid;
    this.reads = reads;
    this.extensions = extensions;
    this.writes = writes;
    this.creates = creates;
    this.extensionsTriggered = extensionsTriggered;
    this.delayedExtensions = delayedExtensions;
  }

  /**
   * Run the prepare.
   * @return a set of longer contracts to notify the worker about.
   */
  public LongKeyMap<Long> runPrepare(TransactionManager tm, ObjectDB database,
      Principal worker) throws TransactionPrepareFailedException {
    LongKeyMap<Long> longerContracts = new LongKeyHashMap<>();

    // First, check read and write permissions. We do this before we attempt to
    // do the actual prepare because we want to run the permissions check in a
    // transaction outside of the worker's transaction.
    fabric.worker.Store store = Worker.getWorker().getStore(database.getName());
    if (worker == null || worker.$getStore() != store
        || worker.$getOnum() != ONumConstants.STORE_PRINCIPAL) {
      try {
        tm.checkPerms(worker, reads.keySet(), writes, extensions);
      } catch (AccessException e) {
        throw new TransactionPrepareFailedException(e.getMessage());
      }
    }

    try {
      database.beginTransaction(tid, worker);
    } catch (final AccessException e) {
      throw new TransactionPrepareFailedException("Insufficient privileges");
    }

    try {
      // This will store the set of onums of objects that were out of date.
      LongKeyMap<SerializedObject> versionConflicts = new LongKeyHashMap<>();
      List<TransactionPrepareFailedException> failures = new ArrayList<>();

      // Sort the objects being prepared.
      SortedSet<ItemPrepare> prepares = new TreeSet<>();
      for (LongKeyMap.Entry<Pair<Integer, Long>> read : reads.entrySet()) {
        prepares.add(new ReadPrepare(read.getKey(), read.getValue().first,
            read.getValue().second));
      }
      for (ExpiryExtension extension : extensions) {
        prepares.add(new ExtensionPrepare(extension));
      }
      for (SerializedObject write : writes) {
        prepares.add(new WritePrepare(write));
      }
      for (SerializedObject create : creates) {
        prepares.add(new CreatePrepare(create));
      }

      // Run it.
      for (ItemPrepare p : prepares) {
        p.prepareOrCheck(database, worker, versionConflicts, longerContracts,
            failures, tid);
      }

      if (!versionConflicts.isEmpty() || !failures.isEmpty()) {
        TransactionPrepareFailedException fail =
            new TransactionPrepareFailedException(failures);
        fail.versionConflicts.putAll(versionConflicts);
        fail.longerContracts.putAll(longerContracts);
        database.abortPrepare(tid, worker);
        throw fail;
      }

      // Dont' bother with these if there's already a failure.
      database.prepareDelayedExtensions(tid, worker, extensionsTriggered,
          delayedExtensions);

      database.finishPrepare(tid, worker);

      STORE_TRANSACTION_LOGGER.log(Level.FINE, "Prepared transaction {0}", tid);
    } catch (RuntimeException e) {
      e.printStackTrace();
      database.abortPrepare(tid, worker);
      throw e;
    }

    return longerContracts;
  }
}
