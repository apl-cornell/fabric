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

import fabric.common.ONumConstants;
import fabric.common.RefTypeEnum;
import fabric.common.SerializedObject;
import fabric.common.Timing;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.RuntimeFetchException;
import fabric.common.util.Pair;
import fabric.lang.arrays.internal._InternalArrayImpl;
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
import fabric.worker.transaction.ReadMapEntry;
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

  /** Label for this object */
  Label get$label();
  
  /** Access Label for this object */
  Label get$accesslabel();

  /** Whether this object is "equal" to another object. */
  boolean equals(Object o);
  
  /** Whether this object has the same identity as another object. */
  boolean idEquals(Object o);

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
      if (store.isLocalStore() && onum != ONumConstants.EMPTY_LABEL
          && onum != ONumConstants.PUBLIC_READONLY_LABEL)
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
        // Check the current transaction's update map.
        Timing.FETCH.begin();
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
      return this.$getStore() == other.$getStore() && this.$getOnum() == other.$getOnum();
    }

    @Override
    public final Label get$label() {
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
    public final Label get$accesslabel() {
      // If the object hasn't been deserialized yet, avoid deserialization by
      // obtaining a reference to the object's access label directly from the
      // serialized object. We can do this without interacting with the
      // transaction manager, since access labels are immutable, and stores are
      // trusted to enforce this.
      
      // Paranoia: continually fetch in case the entry becomes evicted between
      // the fetchEntry() and the getAccessLabel().
      while (true) {
        Label result = fetchEntry().getAccessLabel();
        if (result != null) return result;
      }
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
    public static final boolean idEquals(java.lang.Object o1, java.lang.Object o2) {
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

    protected Label $label;
    
    protected Label $accessLabel;

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
    public ReadMapEntry $readMapEntry;

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
    public int $updateMapVersion;

    /**
     * A private constructor for initializing transaction-management state.
     */
    private _Impl(Store store, long onum, int version, long expiry,
        Label label, Label accessLabel) {
      this.$version = version;
      this.$writer = null;
      this.$writeLockHolder = null;
      this.$reader = Log.NO_READER;
      this.$history = null;
      this.$numWaiting = 0;
      this.$ref = new FabricSoftRef(store, onum, this);
      this.$cacheEntry = new ObjectCache.Entry(this);
      this.$readMapEntry = TransactionManager.getReadMapEntry(this, expiry);
      this.$ref.readMapEntry(this.$readMapEntry);
      this.$isOwned = false;
      this.$updateMapVersion = -1;

      // By default, update labels are public read-only.
      if (label == null && this instanceof Label)
        label = Worker.getWorker().getLocalStore().getPublicReadonlyLabel();

      if (label == null) throw new InternalError("Null update label!");

      // By default, access labels are public read-only.
      if (accessLabel == null && this instanceof Label)
        accessLabel = Worker.getWorker().getLocalStore().getPublicReadonlyLabel();

      if (accessLabel == null) throw new InternalError("Null access label!");

      if (!(store instanceof LocalStore)) {
        if (label.$getStore() instanceof LocalStore
          && !ONumConstants.isGlobalConstant(label.$getOnum()))
          throw new InternalError("Remote object has local update label");

        if (accessLabel.$getStore() instanceof LocalStore
            && !ONumConstants.isGlobalConstant(accessLabel.$getOnum()))
          throw new InternalError("Remote object has local access label");
      }
      this.$label = label;
      this.$accessLabel = accessLabel;
    }

    /**
     * Creates a new Fabric object that will reside on the given Store.
     * 
     * @param store
     *          the location for the object
     * @param label
     *          the security label for the object
     * @param accessLabel
     *          the access label for the object
     */
    public _Impl(Store store, Label label, Label accessLabel)
        throws UnreachableNodeException {
      this(store, store.createOnum(), 0, 0, label, accessLabel);
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
      return o.$getStore().equals($getStore()) && o.$getOnum() == $getOnum();
    }

    /**
     * Default hashCode implementation uses the OID to compute the hash value.
     */
    @Override
    public int hashCode() {
      return (int) $getOnum();
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
      $reader = other.$reader;
      $history = other.$history;
      $isOwned = other.$isOwned;
      $updateMapVersion = other.$updateMapVersion;
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
    public final Label get$label() {
      return $label;
    }
    
    @Override
    public final Label get$accesslabel() {
      return $accessLabel;
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
      return $getStore() == other.$getStore()
          && $getOnum() == other.$getOnum();
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
     */
    @SuppressWarnings("unused")
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
     * @param label
     *          Onum of the object's label.
     * @param serializedInput
     *          A stream of serialized primitive values and inlined objects.
     * @param refTypes
     *          An iterator of <code>RefTypeEnum</code>s indicating the type of
     *          each reference being deserialized (e.g., null, inlined,
     *          intraStore).
     * @param intraStoreRefs
     *          An iterator of intra-store references, each represented by an
     *          onum.
     */
    @SuppressWarnings("unused")
    public _Impl(Store store, long onum, int version, long expiry, long label,
        long accessLabel, ObjectInput serializedInput,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs)
        throws IOException, ClassNotFoundException {
      this(store, onum, version, expiry, new Label._Proxy(store, label),
          new Label._Proxy(store, accessLabel));
    }
    
    /**
     * Maps proxy classes to their constructors.
     */
    private static final Map<Class<? extends Object._Proxy>, Constructor<? extends Object._Proxy>> constructorTable =
        Collections
            .synchronizedMap(new HashMap<Class<? extends Object._Proxy>, Constructor<? extends Object._Proxy>>());

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
     * @throws ClassNotFoundException
     *           Thrown when the class for a wrapped object is unavailable.
     * @throws IOException
     *           Thrown when an I/O error has occurred in the given
     *           <code>ObjectInput</code> stream.
     */
    protected static final Object $readRef(
        Class<? extends Object._Proxy> proxyClass, RefTypeEnum refType,
        ObjectInput in, Store store, Iterator<Long> intraStoreRefs)
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
          
          return constructor.newInstance(
              store, intraStoreRefs.next());
        } catch (Exception e) {
          throw new InternalError(e);
        }

      case REMOTE:
        // These should have been swizzled by the store.
        throw new InternalError(
            "Unexpected remote object reference encountered during deserialization.");
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
      if (ONumConstants.isGlobalConstant(p.ref.onum) || p.ref.store.equals(store)) {
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
        throw new InternalError(
            "Creating remote ref to local store.  Object on local store has "
                + "class " + objClass + " and onum " + p.ref.onum + ".");
      }
      refType.add(RefTypeEnum.REMOTE);
      interStoreRefs.add(new Pair<String, Long>(p.ref.store.name(), p.ref.onum));
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
        // XXX Need a real store and a real label. (Should be given as args.)
        final LocalStore store = Worker.getWorker().getLocalStore();

        return Worker.runInSubTransaction(new Worker.Code<Object>() {
          @Override
          public Object run() throws Throwable {
            Constructor<? extends Object._Impl> constr =
              c.getConstructor(Store.class, Label.class, Label.class);
            Label emptyLabel = store.getEmptyLabel();
            return constr.newInstance(store, emptyLabel, emptyLabel);
          }
        });
      }
    }

    public static class _Impl extends Object._Impl implements _Static {
      public _Impl(Store store, Label label, Label accessLabel)
          throws UnreachableNodeException {
        super(store, label, accessLabel);
      }

      @Override
      protected Object._Proxy $makeProxy() {
        return new _Static._Proxy(this);
      }
    }
  }

}
