package fabric.lang;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fabric.common.Logging;
import fabric.common.NSUtil;
import fabric.common.ONumConstants;
import fabric.common.RefTypeEnum;
import fabric.common.SerializedObject;
import fabric.common.Timing;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.RuntimeFetchException;
import fabric.common.util.Pair;
import fabric.lang.arrays.internal._InternalArrayImpl;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.lang.security.SecretKeyObject;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.FabricSoftRef;
import fabric.worker.LocalStore;
import fabric.worker.ObjectCache;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.ReadMap;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

/**
 * All Fabric objects implement this interface.
 */
public interface Object {

  /** The store where the object resides. */
  Store $getStore();

  /** The object's onum. */
  long $getOnum();

  /** @return an exact proxy for this object. */
  _Proxy $getProxy();

  /** The label that protects this object at run time. */
  Label get$$updateLabel();

  Label set$$updateLabel(Label label);

  /**
   * The object's access policy, specifying the program contexts in which it is
   * safe to use this object.
   */
  ConfPolicy get$$accessPolicy();

  ConfPolicy set$$accessPolicy(ConfPolicy policy);

  /**
   * Calls $initLabels
   */
  Object fabric$lang$Object$();

  /**
   * Initializes the object's update label and access policy.
   */
  Object $initLabels();

  /** Whether this object is "equal" to another object. */
  boolean equals(Object o);

  /** Whether this object has the same identity as another object. */
  boolean idEquals(Object o);

  /** A hash of the object's oid. */
  int oidHashCode();

  /** Unwraps a wrapped Java inlineable. */
  java.lang.Object $unwrap();

  /** Fetches the object if this is a proxy; returns itself if it's an impl. */
  Object fetch();

  /**
   * <p>
   * This method changes the onum of the object. Unless if you <i>really</i>
   * know what you're doing, you should <b>not</b> call this, as it leaves the
   * system in an inconsistent state.
   * </p>
   * <p>
   * This method is used to initialize object databases with objects at
   * well-known onums (e.g., naming map and store principal).
   * </p>
   *
   * @deprecated
   */
  @Deprecated
  void $forceRenumber(long onum);

  /**
   * Creates a Statistics object to determine promises for this Object.
   */
  Statistics createStatistics();

  /**
   * _Proxy objects behave like regular objects by delegating to _Impl objects,
   * pointed to by a soft reference. This class abstracts away the code for
   * maintaining that soft reference.
   */
  public static class _Proxy implements Object {
    private transient FabricSoftRef ref;

    /**
     * This is used only to pin the _Impl in the case where it's a object on the
     * local store.
     */
    private transient final _Impl anchor;

    public _Proxy(Store store, long onum) {
      if (store.isLocalStore() && !ONumConstants.isGlobalConstant(onum))
        throw new InternalError(
            "Attempted to create unresolved reference to a local object (onum="
                + onum + ").");

      this.ref = new FabricSoftRef(store, onum, null);
      this.anchor = null;
    }

    public _Proxy(_Impl impl) {
      this.ref = impl.$ref;
      Store store = impl.$getStore();
      if (store instanceof LocalStore)
        this.anchor = impl;
      else this.anchor = null;
    }

    @Override
    public final _Impl fetch() {
      // Paranoia: continually fetch in case the entry becomes evicted between
      // the fetchEntry() and the getImpl().
      while (true) {
        _Impl result = fetchEntry().getImpl(true);
        if (result != null) {
          ref = result.$ref;
          return result;
        }
      }
    }

    /**
     * Ensures the object is resident in memory and returns its cache entry. If
     * the object is fetched from the network, it will not be deserialized.
     */
    public final ObjectCache.Entry fetchEntry() {
      // Check soft ref.
      _Impl impl = ref.get();
      if (impl != null) return impl.$cacheEntry;

      // Check anchor.
      if (anchor != null) return anchor.$cacheEntry;

      // Intercept reads of global constants and redirect them to the local store.
      if (ONumConstants.isGlobalConstant(ref.onum)) {
        return Worker.getWorker().getLocalStore().readObject(ref.onum);
      }

      // Check worker's cache.
      ObjectCache.Entry result = ref.store.readFromCache(ref.onum);
      if (result != null) return result;

      // Object has been evicted.  Fetch from the network.
      try {
        // Check the current transaction's writer map.
        Timing.FETCH.begin();
        // XXX BEGIN HACK FOR OAKLAND 2012 TIMING STUFF
        if (this instanceof FClass) {
          Logging.TIMING_LOGGER.fine("begin fetching FClass");
        }
        // XXX END HACK FOR OAKLAND 2012 TIMING STUFF
        TransactionManager tm = TransactionManager.getInstance();
        RemoteWorker worker = tm.getFetchWorker(this);
        if (worker != null) {
          // Sanity check.
          RemoteWorker localWorker = Worker.getWorker().getLocalWorker();
          if (worker == localWorker) {
            throw new InternalError();
          }

          // Fetch from the worker.
          Pair<Store, SerializedObject> serialized =
              worker.readObject(tm.getCurrentTid(), ref.store, ref.onum);
          result = serialized.first.cache(serialized.second);
        } else if (this instanceof SecretKeyObject
            || ref.store instanceof InProcessStore) {
          // Fetch from the store. Bypass dissemination when reading key
          // objects and when reading from an in-process store.
          result = ref.store.readObjectNoDissem(ref.onum);
        } else {
          // Fetch from the store.
          result = ref.store.readObject(ref.onum);
        }
      } catch (AccessException e) {
        throw new RuntimeFetchException(e);
      } finally {
        Timing.FETCH.end();
        // XXX BEGIN HACK FOR OAKLAND 2012 TIMING STUFF
        if (this instanceof FClass) {
          Logging.TIMING_LOGGER.fine("done fetching FClass");
        }
        // XXX END HACK FOR OAKLAND 2012 TIMING STUFF
      }

      return result;
    }

    @Override
    public final Store $getStore() {
      return ref.store;
    }

    @Override
    public final long $getOnum() {
      return ref.onum;
    }

    @Override
    public final boolean idEquals(Object other) {
      return this.$getStore() == other.$getStore()
          && this.$getOnum() == other.$getOnum();
    }

    @Override
    public final int oidHashCode() {
      long onum = this.$getOnum();
      if (ONumConstants.isGlobalConstant(onum)) return (int) onum;

      Store store = this.$getStore();
      return store.hashCode() ^ (int) onum;
    }

    /**
     * This static version of oidHashCode is to be used by Fabric programs. It
     * launders the dereference of <code>o</code> to prevent the Fabric type
     * system from thinking the object will be fetched, when it really won't be.
     */
    public static int oidHashCode(Object o) {
      return Object._Impl.oidHashCode(o);
    }

    @Override
    public final Label get$$updateLabel() {
      // If the object hasn't been deserialized yet, avoid deserialization by
      // obtaining a reference to the object's access label directly from the
      // serialized object. We can do this without interacting with the
      // transaction manager, since labels are immutable, and stores are trusted
      // to enforce this.

      // Paranoia: continually fetch in case the entry becomes evicted between
      // the fetchEntry() and the getLabel().
      while (true) {
        Label result = fetchEntry().getLabel();
        if (result != null) return result;
      }
    }

    @Override
    public final Label set$$updateLabel(Label label) {
      return fetch().set$$updateLabel(label);
    }

    @Override
    public final ConfPolicy get$$accessPolicy() {
      // If the object hasn't been deserialized yet, avoid deserialization by
      // obtaining a reference to the object's access label directly from the
      // serialized object. We can do this without interacting with the
      // transaction manager, since access labels are immutable, and stores are
      // trusted to enforce this.

      // Paranoia: continually fetch in case the entry becomes evicted between
      // the fetchEntry() and the getAccessLabel().
      while (true) {
        ConfPolicy result = fetchEntry().getAccessPolicy();
        if (result != null) return result;
      }
    }

    @Override
    public final ConfPolicy set$$accessPolicy(ConfPolicy policy) {
      return fetch().set$$accessPolicy(policy);
    }

    @Override
    public Object $initLabels() {
      return fetch().$initLabels();
    }

    @Override
    public Object fabric$lang$Object$() {
      return fetch().fabric$lang$Object$();
    }

    @Override
    public final _Proxy $getProxy() {
      // If the object hasn't been deserialized yet, avoid deserialization by
      // obtaining a reference to the object's access label directly from the
      // serialized object. We can do this without interacting with the
      // transaction manager, since the object's class is immutable, and stores
      // are trusted to enforce this.

      // Paranoia: continually fetch in case the entry becomes evicted between
      // the fetchEntry() and the getProxy().
      while (true) {
        _Proxy result = fetchEntry().getProxy();
        if (result != null) return result;
      }
    }

    @Override
    public final java.lang.Object $unwrap() {
      return this;
    }

    public static final java.lang.Object $getProxy(java.lang.Object o) {
      if (o instanceof Object) return ((Object) o).$getProxy();
      return o;
    }

    /**
     * return true if o1 and o2 are objects with the same identity.
     */
    public static final boolean idEquals(java.lang.Object o1,
        java.lang.Object o2) {
      if (o1 instanceof Object && o2 instanceof Object)
        return ((Object) o1).idEquals((Object) o2);
      return o1 == o2;
    }

    /**
     * A delegate for the default equals implementation.
     */
    @Override
    public boolean equals(java.lang.Object arg1) {
      return fetch().equals(arg1);
    }

    /**
     * A delegate for the default equals implementation.
     */
    @Override
    public boolean equals(Object arg1) {
      return fetch().equals(arg1);
    }

    /**
     * A delegate for the default toString implementation.
     */
    @Override
    public String toString() {
      return fetch().toString();
    }

    /**
     * A delegate for the default hashcode implementation.
     */
    @Override
    public int hashCode() {
      return fetch().hashCode();
    }

    /**
     * <p>
     * This method changes the onum of the object. Unless if you <i>really</i>
     * know what you're doing, you should <b>not</b> call this, as it leaves the
     * system in an inconsistent state.
     * </p>
     * <p>
     * This method is used to initialize object databases with objects at
     * well-known onums (e.g., naming map and store principal).
     * </p>
     *
     * @deprecated
     */
    @Deprecated
    @Override
    public final void $forceRenumber(long onum) {
      fetch().$forceRenumber(onum);
    }

    @Override
    public Statistics createStatistics() {
      return fetch().createStatistics();
    }

    /**
     * A dummy method. This is a hack for working around reachability problems in
     * generated code.
     */
    public static void _npe(final Label lbl) throws NullPointerException {
    }
  }

  /**
   * _Impl objects hold the actual code and data of Fabric objects and may be
   * evicted from memory.
   */
  public static class _Impl implements Object, Cloneable {
    /**
     * The cached exact proxy for this object.
     */
    private _Proxy $proxy;

    public final FabricSoftRef $ref;

    /**
     * The worker's cache entry object for this _Impl.
     */
    public final ObjectCache.Entry $cacheEntry;

    /**
     * A reference to the class object. TODO Figure out class loading.
     */
    protected _Proxy $class;

    protected Label $updateLabel;

    protected ConfPolicy $accessPolicy;

    public int $version;

    // *********************************************************
    // The following fields are used for transaction management.
    // They should stay on the worker and should not be sent to
    // the store.
    // *********************************************************

    /**
     * The unique running transaction that can write to the object, or null if
     * none. (This is either null or holds the same value as $writeLockHolder.
     */
    public Log $writer;

    /**
     * The innermost transaction that is holding a write lock on the object.
     */
    public Log $writeLockHolder;

    /**
     * Stack trace for where the write lock for this object was obtained. Used
     * for debugging.
     */
    public StackTraceElement[] $writeLockStackTrace;

    /**
     * Any transaction that has logged a read of the object, or null if none.
     */
    public Log $reader;

    /**
     * Modification log. Holds the state of the object at the beginning of the
     * transaction that currently holds the write lock. A transaction has
     * acquired a write lock on an object if any entry in this history has
     * $writer set to that transaction's log.
     */
    public _Impl $history;

    /**
     * A reference to the global read list for this object.
     *
     * @see fabric.worker.transaction.TransactionManager#readMap
     */
    public final ReadMap.Entry $readMapEntry;

    /**
     * The number of threads waiting on this object.
     */
    public int $numWaiting;

    /**
     * Whether this worker owns the most up-to-date copy of the object.
     */
    public boolean $isOwned;

    /**
     * The version number on the last update-map that was checked.
     */
    public int writerMapVersion;

    /**
     * A stack trace of where this object was created. Used for debugging.
     */
    public final StackTraceElement[] $stackTrace;

    /**
     * A private constructor for initializing transaction-management state.
     */
    private _Impl(Store store, long onum, int version, long expiry) {
      this.$version = version;
      this.$writer = null;
      this.$writeLockHolder = null;
      this.$writeLockStackTrace = null;
      this.$reader = Log.NO_READER;
      this.$history = null;
      this.$numWaiting = 0;
      this.$ref = new FabricSoftRef(store, onum, this);
      this.$cacheEntry = new ObjectCache.Entry(this);
      this.$readMapEntry = TransactionManager.getReadMapEntry(this, expiry);
      this.$ref.readMapEntry(this.$readMapEntry);
      this.$isOwned = false;
      this.writerMapVersion = -1;

      if (TRACE_OBJECTS)
        this.$stackTrace = Thread.currentThread().getStackTrace();
      else this.$stackTrace = null;
    }

    /**
     * A debugging switch for storing a stack trace each time an _Impl is
     * created. This is enabled by passing "--trace-objects" as a command-line
     * argument to the worker.
     */
    public static boolean TRACE_OBJECTS = false;

    /**
     * Creates a new Fabric object that will reside on the given Store.
     *
     * @param store
     *          the location for the object
     */
    public _Impl(Store store) throws UnreachableNodeException {
      this(store, store.createOnum(), 0, 0);
      store.cache(this);

      // Register the new object with the transaction manager.
      TransactionManager.getInstance().registerCreate(this);
    }

    @Override
    public final _Impl clone() {
      try {
        _Impl result = (_Impl) super.clone();
        if (result instanceof _InternalArrayImpl)
          ((_InternalArrayImpl) result).cloneValues();
        return result;
      } catch (Exception e) {
        throw new InternalError(e);
      }
    }

    @Override
    public Object $initLabels() {
      // Update label is public, untrusted.
      set$$updateLabel(Worker.getWorker().getLocalStore().getEmptyLabel());

      // Access policy is public.
      set$$accessPolicy(
          Worker.getWorker().getLocalStore().getBottomConfidPolicy());

      return $getProxy();
    }

    @Override
    public Object fabric$lang$Object$() {
      Object result = $initLabels();

      // Register the new object with the transaction manager.
      TransactionManager.getInstance().registerLabelsInitialized(this);

      return result;
    }

    /**
     * Default equals implementation uses pointer equality.
     */
    @Override
    public boolean equals(java.lang.Object o) {
      if (!(o instanceof Object)) return false;
      return equals((Object) o);
    }

    /**
     * Default equals implementation uses pointer equality.
     */
    @Override
    public boolean equals(Object o) {
      //ensure not a surrogate:
      Object impl = o.fetch();
      return impl.$getStore().equals($getStore())
          && impl.$getOnum() == $getOnum();
    }

    /**
     * Default hashCode implementation uses oidHashCode().
     */
    @Override
    public int hashCode() {
      return oidHashCode();
    }

    @Override
    public int oidHashCode() {
      return this.$getProxy().oidHashCode();
    }

    /**
     * This static version of oidHashCode is to be used by Fabric programs. It
     * launders the dereference of <code>o</code> to prevent the Fabric type
     * system from thinking the object will be fetched, when it really won't be.
     */
    public static int oidHashCode(Object o) {
      return o == null ? 0 : o.oidHashCode();
    }

    /**
     * Default toString implementation prints out the class name and global
     * object name.
     */
    @Override
    public String toString() {
      return getClass().getName() + "@fab://" + $getStore().name() + "/"
          + $getOnum();
    }

    /**
     * This is used to restore the state of the object during transaction
     * roll-back.
     */
    public final void $copyStateFrom(_Impl other) {
      $writer = null;
      $writeLockHolder = other.$writeLockHolder;
      $writeLockStackTrace = other.$writeLockStackTrace;
      $reader = other.$reader;
      $history = other.$history;
      $isOwned = other.$isOwned;
      writerMapVersion = other.writerMapVersion;
      $copyAppStateFrom(other);
    }

    /**
     * This copies the application state of the object. Subclasses should
     * override this method and call
     * <code>super.copyAppStateFrom(other)</code>.
     */
    public void $copyAppStateFrom(_Impl other) {
    }

    @Override
    public final Store $getStore() {
      return $ref.store;
    }

    @Override
    public final long $getOnum() {
      return $ref.onum;
    }

    public final _Proxy $getClass() {
      return $class;
    }

    @Override
    public final Label get$$updateLabel() {
      return $updateLabel;
    }

    @Override
    public final Label set$$updateLabel(Label label) {
      TransactionManager tm = TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.$updateLabel = label;
      if (transactionCreated) tm.commitTransaction();
      return label;
    }

    @Override
    public final ConfPolicy get$$accessPolicy() {
      return $accessPolicy;
    }

    @Override
    public final ConfPolicy set$$accessPolicy(ConfPolicy policy) {
      TransactionManager tm = TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.$accessPolicy = policy;
      if (transactionCreated) tm.commitTransaction();
      return policy;
    }

    public final int $getVersion() {
      return $version;
    }

    @Override
    public final _Proxy $getProxy() {
      if ($proxy == null) $proxy = $makeProxy();
      return $proxy;
    }

    @Override
    public final boolean idEquals(Object other) {
      return $getStore() == other.$getStore() && $getOnum() == other.$getOnum();
    }

    @Override
    public final _Impl fetch() {
      return this;
    }

    @Override
    public Statistics createStatistics() {
      return DefaultStatistics.instance;
    }

    /**
     * Serializes the non-transient fields of this object. Subclasses should
     * call the super method first so that inherited fields are written before
     * fields declared in this subclass. The order in which fields are written
     * must be fixed and the same as the order used by the deserialization
     * constructor.
     *
     * @param serializedOutput
     *          An output stream for writing serialized primitive values and
     *          inlined objects.
     * @param refTypes
     *          A list to which <code>RefTypeEnum</code>s will be written to
     *          indicate the type of reference being serialized (e.g., null,
     *          inlined, intraStore, interStore).
     * @param intraStoreRefs
     *          A list to which onums denoting intra-store references will be
     *          written.
     * @param interStoreRefs
     *          A list to which global object names (hostname/onum pairs),
     *          denoting inter-store references, will be written.
     * @throws IOException
     */
    public void $serialize(ObjectOutput serializedOutput,
        List<RefTypeEnum> refTypes, List<Long> intraStoreRefs,
        List<Pair<String, Long>> interStoreRefs) throws IOException {
      // Nothing to output here. SerializedObject.write(_Impl, DataOutput) takes
      // care of writing the onum, version, label onum, and type information.
      return;
    }

    /**
     * This is the deserialization constructor and reconstructs the object from
     * its serialized state. Subclasses should call the super constructor to
     * first read inherited fields. It should then read the value of each
     * non-transient field declared in this subclass. The order in which fields
     * are presented is the same as the order used by $serialize.
     *
     * @param store
     *          The store on which the object lives.
     * @param onum
     *          The object's onum.
     * @param version
     *          The object's version number.
     * @param updateLabel
     *          Onum of the object's update label.
     * @param accessPolicy
     *          onum of the object's access policy.
     * @param serializedInput
     *          A stream of serialized primitive values and inlined objects.
     * @param refTypes
     *          An iterator of <code>RefTypeEnum</code>s indicating the type of
     *          each reference being deserialized (e.g., null, inlined,
     *          intraStore).
     * @param intraStoreRefs
     *          An iterator of intra-store references, each represented by an
     *          onum.
     * @param interStoreRefs
     *          An iterator of inter-store references.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public _Impl(Store store, long onum, int version, long expiry,
        long updateLabel, long accessPolicy, ObjectInput serializedInput,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
            throws IOException, ClassNotFoundException {
      this(store, onum, version, expiry);
      this.$updateLabel = new Label._Proxy(store, updateLabel);
      this.$accessPolicy = new ConfPolicy._Proxy(store, accessPolicy);
    }

    /**
     * Maps proxy classes to their constructors.
     */
    private static final Map<Class<? extends Object._Proxy>, Constructor<? extends Object._Proxy>> constructorTable =
        Collections.synchronizedMap(
            new HashMap<Class<? extends Object._Proxy>, Constructor<? extends Object._Proxy>>());

    /**
     * A helper method for reading a pointer during object deserialization.
     *
     * @param proxyClass
     *          The expected proxy class for the reference being read.
     * @param refType
     *          The type of reference being read.
     * @param in
     *          The stream from which to read any inlined objects.
     * @param store
     *          The store to use when constructing any intra-store references.
     * @param intraStoreRefs
     *          An iterator of intra-store references, each represented by an
     *          onum.
     * @param interStoreRefs
     *          An iterator of inter-store references.
     * @throws ClassNotFoundException
     *           Thrown when the class for a wrapped object is unavailable.
     * @throws IOException
     *           Thrown when an I/O error has occurred in the given
     *           <code>ObjectInput</code> stream.
     */
    protected static final Object $readRef(
        Class<? extends Object._Proxy> proxyClass, RefTypeEnum refType,
        ObjectInput in, Store store, Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
            throws IOException, ClassNotFoundException {
      switch (refType) {
      case NULL:
        return null;

      case INLINE:
        return WrappedJavaInlineable.$wrap(in.readObject());

      case ONUM:
        try {
          Constructor<? extends Object._Proxy> constructor =
              constructorTable.get(proxyClass);
          if (constructor == null) {
            constructor = proxyClass.getConstructor(Store.class, long.class);
            constructorTable.put(proxyClass, constructor);
          }

          return constructor.newInstance(store, intraStoreRefs.next());
        } catch (Exception e) {
          throw new InternalError(e);
        }

      case REMOTE:
        try {
          Constructor<? extends Object._Proxy> constructor =
              constructorTable.get(proxyClass);
          if (constructor == null) {
            constructor = proxyClass.getConstructor(Store.class, long.class);
            constructorTable.put(proxyClass, constructor);
          }

          Pair<String, Long> ref = interStoreRefs.next();
          String storeName = ref.first;
          long onum = ref.second;
          return constructor.newInstance(Worker.getWorker().getStore(storeName),
              onum);
        } catch (Exception e) {
          throw new InternalError(e);
        }
      }

      throw new InternalError(
          "Unknown pointer type while deserializing reference.");
    }

    /**
     * Serializes an object to the given ObjectOutput.
     */
    protected static final void $writeInline(ObjectOutput out,
        java.lang.Object obj) throws IOException {
      if (obj instanceof WrappedJavaInlineable<?>) {
        obj = ((WrappedJavaInlineable<?>) obj).obj;
      }

      out.writeObject(obj);
    }

    /**
     * A helper method for serializing a reference during object serialization.
     *
     * @param store
     *          The referring object's store.
     * @param obj
     *          The reference to be serialized.
     * @param refType
     *          A list to which a <code>RefTypeEnum</code> will be written to
     *          indicate the type of reference being serialized (e.g., null,
     *          inlined, intraStore, interStore).
     * @param out
     *          An output stream for writing inlined objects.
     * @param intraStoreRefs
     *          A list for writing intra-store references, represented by onums.
     * @param interStoreRefs
     *          A list for writing denoting inter-store references, represented
     *          by global object names (hostname/onum pairs).
     */
    protected static final void $writeRef(Store store, Object obj,
        List<RefTypeEnum> refType, ObjectOutput out, List<Long> intraStoreRefs,
        List<Pair<String, Long>> interStoreRefs) throws IOException {
      if (obj == null) {
        refType.add(RefTypeEnum.NULL);
        return;
      }

      if (obj instanceof WrappedJavaInlineable<?>) {
        refType.add(RefTypeEnum.INLINE);
        out.writeObject(obj.$unwrap());
        return;
      }

      _Proxy p = (_Proxy) obj;
      if (ONumConstants.isGlobalConstant(p.ref.onum)
          || p.ref.store.equals(store)) {
        // Intra-store reference.
        refType.add(RefTypeEnum.ONUM);
        intraStoreRefs.add(p.ref.onum);
        return;
      }

      // Remote reference.
      if (p.ref.store instanceof LocalStore) {
        Class<?> objClass = obj.getClass();
        //XXX: calling toString is unsafe since it may create new objects.
        //  you should avoid this call if you are getting "Cannot create
        //  object outside transaction" errors.  You still have a remote ref
        //  error, you just can't print out the offending object outside a txn.
        //String objStr = obj.toString();
        String message =
            "Creating remote ref to local store.  Object on local store has "
                + "class " + objClass + " and onum " + p.ref.onum + ".";
        if (p.fetch().$stackTrace != null) {
          message +=
              "  A stack trace for the creation of the local object follows.";
          for (StackTraceElement e : p.anchor.$stackTrace)
            message += System.getProperty("line.separator") + "  " + e;
        }
        throw new InternalError(message);
      }
      refType.add(RefTypeEnum.REMOTE);
      interStoreRefs.add(new Pair<>(p.ref.store.name(), p.ref.onum));
    }

    /**
     * Subclasses should override this method.
     */
    protected _Proxy $makeProxy() {
      return new _Proxy(this);
    }

    @Override
    public final java.lang.Object $unwrap() {
      return this;
    }

    /**
     * <p>
     * This method changes the onum of the object. Unless if you <i>really</i>
     * know what you're doing, you should <b>not</b> call this, as it leaves the
     * system in an inconsistent state.
     * </p>
     * <p>
     * This method is used to initialize object databases with objects at
     * well-known onums (e.g., naming map and store principal).
     * </p>
     *
     * @deprecated
     */
    @Deprecated
    @Override
    public final void $forceRenumber(long onum) {
      long oldOnum = $ref.onum;
      this.$ref.onum = onum;
      TransactionRegistry.renumberObject($ref.store, oldOnum, onum);
    }

    /**
     * A dummy method. This is a hack for working around reachability problems in
     * generated code.
     */
    public static void _npe(final Label lbl) throws NullPointerException {
    }
  }

  /**
   * _Static objects hold all static state for the class.
   */
  public static interface _Static extends Object, Cloneable {
    public static class _Proxy extends Object._Proxy implements _Static {
      public _Proxy(_Static._Impl impl) {
        super(impl);
      }

      public _Proxy(Store store, long onum) {
        super(store, onum);
      }

      /**
       * Used to initialize the _Static._Proxy.$instance variables.
       *
       * @param c
       *          The class to instantiate.
       */
      public static final Object $makeStaticInstance(
          final Class<? extends Object._Impl> c) {

        return Worker.runInSubTransaction(new Worker.Code<Object>() {
          @Override
          public Object run() throws Throwable {
            String name = c.getCanonicalName();
            Store store;

            if (!NSUtil.isPlatformName(name)) {
              Object o = NSUtil.toProxy(name);
              store = o.$getStore();
              if (o instanceof FClass) {
                FClass fclass = (FClass) o;
                Object inst = fclass.get$staticInstance();
                if (inst == null) {
                  Constructor<? extends Object._Impl> constr =
                      c.getConstructor(Store.class);
                  inst = constr.newInstance(store).$initLabels();
                  fclass.set$staticInstance(inst);
                }
                return inst.fetch();
              } else throw new InternalError("Expected FClass for "
                  + o.toString() + " but got " + o.getClass());
            } else {
              store = Worker.getWorker().getLocalStore();
              Constructor<? extends Object._Impl> constr =
                  c.getConstructor(Store.class);
              return constr.newInstance(store).$initLabels().fetch();
            }
          }
        });
      }
    }

    public static class _Impl extends Object._Impl implements _Static {
      public _Impl(Store store) throws UnreachableNodeException {
        super(store);
      }

      public _Impl(Store store, long onum, int version, long expiry,
          long updateLabel, long accessPolicy, ObjectInput serializedInput,
          Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs,
          Iterator<Pair<String, Long>> interStoreRefs)
              throws IOException, ClassNotFoundException {
        super(store, onum, version, expiry, updateLabel, accessPolicy,
            serializedInput, refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      protected Object._Proxy $makeProxy() {
        return new _Static._Proxy(this);
      }
    }
  }

}
