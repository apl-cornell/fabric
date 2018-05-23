package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyValue;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;

/**
 * A computation that uses {@link Contract}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Contract}.
 * This helps to ensure that the {@link Contract} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.metrics.contracts.Contract get$curContract();
    
    public fabric.metrics.contracts.Contract set$curContract(
      fabric.metrics.contracts.Contract val);
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(
      fabric.worker.metrics.ImmutableSet val);
    
    public boolean get$recomputeOnInvalidation();
    
    public boolean set$recomputeOnInvalidation(boolean val);
    
    public boolean get$proactive();
    
    public boolean set$proactive(boolean val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.worker.metrics.WarrantyValue updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time,
                                                     boolean autoRetry);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public fabric.worker.metrics.ImmutableObserverSet
      handleUpdates(boolean includesObserver, fabric.common.util.LongSet treaties);
    
    /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
    public fabric.lang.Object makeProxyResult(
      fabric.worker.metrics.WarrantyValue val, final fabric.worker.Store proxyStore);
    
    /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
    public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
      final fabric.worker.Store proxyStore);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$(
      boolean recomputeOnInvalidation, boolean proactive);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$(
      boolean recomputeOnInvalidation);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$();
    
    /**
   * A "Proxy" computation to allow for avoiding contacting a remote store for
   * memoized results.
   */
    public static interface ProxyComp
      extends fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyComp
          get$baseComputation();
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          set$baseComputation(
          fabric.metrics.contracts.warranties.WarrantyComp val);
        
        public ProxyComp
          fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
          fabric.metrics.contracts.warranties.WarrantyComp baseComputation);
        
        public fabric.worker.metrics.WarrantyValue updatedVal(long time);
        
        public static class _Proxy
        extends fabric.metrics.contracts.warranties.WarrantyComp._Proxy
          implements ProxyComp {
            public fabric.metrics.contracts.warranties.WarrantyComp
              get$baseComputation() {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).get$baseComputation();
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp
              set$baseComputation(
              fabric.metrics.contracts.warranties.WarrantyComp val) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).set$baseComputation(val);
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
              fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
              fabric.metrics.contracts.warranties.WarrantyComp arg1) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp) fetch()).
                  fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                    arg1);
            }
            
            public _Proxy(ProxyComp._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.metrics.contracts.warranties.WarrantyComp._Impl
          implements ProxyComp {
            public fabric.metrics.contracts.warranties.WarrantyComp
              get$baseComputation() {
                return this.baseComputation;
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp
              set$baseComputation(
              fabric.metrics.contracts.warranties.WarrantyComp val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.baseComputation = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.warranties.WarrantyComp
              baseComputation;
            
            public ProxyComp
              fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
              fabric.metrics.contracts.warranties.WarrantyComp baseComputation) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(baseComputation)) instanceof ProxyComp) {
                    this.
                      set$baseComputation(
                        ((ProxyComp)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       baseComputation)).
                            get$baseComputation());
                }
                else {
                    this.set$baseComputation(baseComputation);
                }
                fabric$metrics$contracts$warranties$WarrantyComp$(
                  baseComputation.get$recomputeOnInvalidation(),
                  baseComputation.get$proactive());
                return (ProxyComp) this.$getProxy();
            }
            
            public fabric.worker.metrics.WarrantyValue updatedVal(long time) {
                return this.get$baseComputation().updatedVal(time);
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         ProxyComp._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.baseComputation, refTypes, out,
                          intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.baseComputation =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp)
                    $readRef(
                      fabric.metrics.contracts.warranties.WarrantyComp.
                        _Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  ProxyComp.
                  _Impl
                  src =
                  (fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp.
                    _Impl) other;
                this.baseComputation = src.baseComputation;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.metrics.contracts.warranties.WarrantyComp.
                           ProxyComp._Static
            {
                public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                                ProxyComp._Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.warranties.
                  WarrantyComp.ProxyComp._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      warranties.
                      WarrantyComp.
                      ProxyComp.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        warranties.
                        WarrantyComp.
                        ProxyComp.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.warranties.WarrantyComp.
                            ProxyComp._Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.warranties.WarrantyComp.
                        ProxyComp._Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.metrics.contracts.warranties.WarrantyComp.
                           ProxyComp._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.warranties.WarrantyComp.
                             ProxyComp._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 16, -110, 3, 111,
        -9, 19, 89, -49, -36, -18, 92, 60, -1, -76, -101, -84, 24, 3, 68, 124,
        -28, -108, 78, -49, -109, -64, 14, 104, -104, -35, -99, -109 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527105287000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.lang.Object get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.lang.Object set$curVal(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.metrics.contracts.Contract get$curContract() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curContract();
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curContract(val);
        }
        
        public fabric.worker.metrics.ImmutableSet get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.worker.metrics.ImmutableSet set$proxies(
          fabric.worker.metrics.ImmutableSet val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$proxies(val);
        }
        
        public boolean get$recomputeOnInvalidation() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$recomputeOnInvalidation();
        }
        
        public boolean set$recomputeOnInvalidation(boolean val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$recomputeOnInvalidation(val);
        }
        
        public boolean get$proactive() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proactive();
        }
        
        public boolean set$proactive(boolean val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$proactive(val);
        }
        
        public fabric.worker.metrics.WarrantyValue updatedVal(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.worker.metrics.WarrantyValue apply(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.worker.metrics.WarrantyValue apply(long arg1,
                                                         boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1, arg2);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates(arg1, arg2);
        }
        
        public fabric.lang.Object makeProxyResult(
          fabric.worker.metrics.WarrantyValue arg1, fabric.worker.Store arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxyResult(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxy(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(boolean arg1,
                                                            boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(boolean arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$();
        }
        
        public _Proxy(WarrantyComp._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.lang.Object get$curVal() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curVal;
        }
        
        public fabric.lang.Object set$curVal(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.lang.Object curVal;
        
        public fabric.metrics.contracts.Contract get$curContract() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curContract;
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curContract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.metrics.contracts.Contract curContract;
        
        public fabric.worker.metrics.ImmutableSet get$proxies() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.proxies;
        }
        
        public fabric.worker.metrics.ImmutableSet set$proxies(
          fabric.worker.metrics.ImmutableSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The set of proxy computations for this computation. */
        protected fabric.worker.metrics.ImmutableSet proxies;
        
        public boolean get$recomputeOnInvalidation() {
            return this.recomputeOnInvalidation;
        }
        
        public boolean set$recomputeOnInvalidation(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.recomputeOnInvalidation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected boolean recomputeOnInvalidation;
        
        public boolean get$proactive() { return this.proactive; }
        
        public boolean set$proactive(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proactive = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected boolean proactive;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.worker.metrics.WarrantyValue updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.worker.metrics.WarrantyValue apply(long time) {
            return apply(time, true);
        }
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.worker.metrics.WarrantyValue apply(long time,
                                                         boolean autoRetry) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time, autoRetry);
        }
        
        private static fabric.worker.metrics.WarrantyValue static_apply(
          fabric.metrics.contracts.warranties.WarrantyComp tmp, long time,
          boolean autoRetry) {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            int i = 0;
            boolean loop =
              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_dropOldValue(tmp);
            while (loop) {
                i++;
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.INFO,
                    "RUNNING ATTEMPT " +
                      i +
                      " OF " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                      " IN " +
                      java.lang.Thread.currentThread());
                if (!tmp.get$proactive()) {
                    for (java.util.Iterator iter = tmp.get$proxies().iterator();
                         iter.hasNext(); ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                        if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_dropOldValue(proxy)) {
                            fabric.lang.Object newVal = proxy.get$curVal();
                            fabric.metrics.contracts.Contract newContract =
                              proxy.get$curContract();
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_setNewValue(tmp, newVal, newContract);
                            if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                  static_dropOldValue(tmp)) {
                                break;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(tmp.get$curContract(),
                                                       null)) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "GENERATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.worker.metrics.WarrantyValue computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.contract,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return computed;
                    }
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "ACTIVATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    java.lang.String contractStoreName =
                      computed.contract.$getStore().name();
                    if (!fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn() &&
                          !contractStoreName.
                          equals(fabric.worker.Worker.getWorkerName())) {
                        fabric.worker.remote.RemoteWorker w =
                          fabric.worker.Worker.getWorker().getWorker(
                                                             contractStoreName);
                        ((fabric.metrics.contracts.Contract._Proxy)
                           computed.contract).refresh$remote(
                                                w, null, false,
                                                computed.weakStats);
                    }
                    else {
                        computed.contract.refresh(false, computed.weakStats);
                    }
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "SETTING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                      static_setNewValue(tmp, computed.value,
                                         computed.contract);
                    if (tmp.get$proactive()) {
                        for (java.util.Iterator iter =
                               tmp.get$proxies().iterator();
                             iter.hasNext();
                             ) {
                            fabric.metrics.contracts.warranties.WarrantyComp
                              proxy =
                              (fabric.metrics.contracts.warranties.WarrantyComp)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.$wrap(
                                                                      iter.next()));
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_setNewValue(proxy, computed.value,
                                                 computed.contract);
                        }
                    }
                }
                loop =
                  autoRetry &&
                    fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                    static_dropOldValue(tmp);
                if (!loop)
                    fabric.worker.transaction.TransactionManager.getInstance().
                      stats.
                      addMsg("Memoized: true");
            }
            return fabric.worker.metrics.WarrantyValue.newValue(
                                                         tmp.get$curVal(),
                                                         tmp.get$curContract());
        }
        
        private static boolean static_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            boolean rtn = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                rtn = ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                         tmp.fetch()).dropOldValue();
            }
            else {
                {
                    boolean rtn$var589 = rtn;
                    fabric.worker.transaction.TransactionManager $tm595 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled598 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff596 = 1;
                    boolean $doBackoff597 = true;
                    boolean $retry592 = true;
                    $label590: for (boolean $commit591 = false; !$commit591; ) {
                        if ($backoffEnabled598) {
                            if ($doBackoff597) {
                                if ($backoff596 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff596);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e593) {
                                            
                                        }
                                    }
                                }
                                if ($backoff596 < 5000) $backoff596 *= 2;
                            }
                            $doBackoff597 = $backoff596 <= 32 || !$doBackoff597;
                        }
                        $commit591 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e593) {
                            $commit591 = false;
                            continue $label590;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e593) {
                            $commit591 = false;
                            fabric.common.TransactionID $currentTid594 =
                              $tm595.getCurrentTid();
                            if ($e593.tid.isDescendantOf($currentTid594))
                                continue $label590;
                            if ($currentTid594.parent != null) {
                                $retry592 = false;
                                throw $e593;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e593) {
                            $commit591 = false;
                            if ($tm595.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid594 =
                              $tm595.getCurrentTid();
                            if ($e593.tid.isDescendantOf($currentTid594)) {
                                $retry592 = true;
                            }
                            else if ($currentTid594.parent != null) {
                                $retry592 = false;
                                throw $e593;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e593) {
                            $commit591 = false;
                            if ($tm595.checkForStaleObjects())
                                continue $label590;
                            $retry592 = false;
                            throw new fabric.worker.AbortException($e593);
                        }
                        finally {
                            if ($commit591) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e593) {
                                    $commit591 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e593) {
                                    $commit591 = false;
                                    fabric.common.TransactionID $currentTid594 =
                                      $tm595.getCurrentTid();
                                    if ($currentTid594 != null) {
                                        if ($e593.tid.equals($currentTid594) ||
                                              !$e593.tid.isDescendantOf(
                                                           $currentTid594)) {
                                            throw $e593;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit591 && $retry592) {
                                { rtn = rtn$var589; }
                                continue $label590;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private boolean dropOldValue() {
            fabric.metrics.contracts.Contract curContract =
              this.get$curContract();
            if (!fabric.lang.Object._Proxy.idEquals(curContract, null) &&
                  !curContract.valid()) {
                curContract.removeObserver(
                              (fabric.metrics.contracts.warranties.WarrantyComp)
                                this.$getProxy());
                this.set$curContract(null);
                this.set$$associated(null);
            }
            return fabric.lang.Object._Proxy.idEquals(this.get$curContract(),
                                                      null);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   tmp.fetch()).setNewValue(newVal, newContract);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm604 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled607 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff605 = 1;
                    boolean $doBackoff606 = true;
                    boolean $retry601 = true;
                    $label599: for (boolean $commit600 = false; !$commit600; ) {
                        if ($backoffEnabled607) {
                            if ($doBackoff606) {
                                if ($backoff605 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff605);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e602) {
                                            
                                        }
                                    }
                                }
                                if ($backoff605 < 5000) $backoff605 *= 2;
                            }
                            $doBackoff606 = $backoff605 <= 32 || !$doBackoff606;
                        }
                        $commit600 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newContract);
                        }
                        catch (final fabric.worker.RetryException $e602) {
                            $commit600 = false;
                            continue $label599;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e602) {
                            $commit600 = false;
                            fabric.common.TransactionID $currentTid603 =
                              $tm604.getCurrentTid();
                            if ($e602.tid.isDescendantOf($currentTid603))
                                continue $label599;
                            if ($currentTid603.parent != null) {
                                $retry601 = false;
                                throw $e602;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e602) {
                            $commit600 = false;
                            if ($tm604.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid603 =
                              $tm604.getCurrentTid();
                            if ($e602.tid.isDescendantOf($currentTid603)) {
                                $retry601 = true;
                            }
                            else if ($currentTid603.parent != null) {
                                $retry601 = false;
                                throw $e602;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e602) {
                            $commit600 = false;
                            if ($tm604.checkForStaleObjects())
                                continue $label599;
                            $retry601 = false;
                            throw new fabric.worker.AbortException($e602);
                        }
                        finally {
                            if ($commit600) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e602) {
                                    $commit600 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e602) {
                                    $commit600 = false;
                                    fabric.common.TransactionID $currentTid603 =
                                      $tm604.getCurrentTid();
                                    if ($currentTid603 != null) {
                                        if ($e602.tid.equals($currentTid603) ||
                                              !$e602.tid.isDescendantOf(
                                                           $currentTid603)) {
                                            throw $e602;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit600 && $retry601) {
                                {  }
                                continue $label599;
                            }
                        }
                    }
                }
            }
        }
        
        private void setNewValue(
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue() &&
                  !fabric.lang.Object._Proxy.idEquals(newContract, null) &&
                  newContract.valid()) {
                this.set$curVal(newVal);
                this.set$curContract(
                       newContract.getProxyContract(this.$getStore()));
                this.get$curContract().
                  addObserver((fabric.metrics.contracts.warranties.WarrantyComp)
                                this.$getProxy());
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$curContract()));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curContract(),
                                                    null))
                return this.get$curContract().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            if (includesObserver) {
                if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       this.fetch()).dropOldValue()) {
                    if (this.get$recomputeOnInvalidation())
                        apply(java.lang.System.currentTimeMillis(), false);
                    return getObservers();
                }
            }
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
        }
        
        /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
        public fabric.lang.Object makeProxyResult(
          fabric.worker.metrics.WarrantyValue val,
          final fabric.worker.Store proxyStore) {
            return val.value;
        }
        
        /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
        public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_makeProxy(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), proxyStore);
        }
        
        private static fabric.metrics.contracts.warranties.WarrantyComp
          static_makeProxy(fabric.metrics.contracts.warranties.WarrantyComp tmp,
                           final fabric.worker.Store proxyStore) {
            fabric.metrics.contracts.warranties.WarrantyComp rtn = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.$getStore().equals(proxyStore)) {
                    rtn = tmp;
                }
                else {
                    for (java.util.Iterator iter =
                           tmp.get$proxies().iterator();
                         iter.hasNext();
                         ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                        if (proxy.$getStore().equals(proxyStore)) {
                            rtn = proxy;
                            break;
                        }
                    }
                    if (fabric.lang.Object._Proxy.idEquals(rtn, null)) {
                        rtn =
                          ((ProxyComp)
                             new fabric.metrics.contracts.warranties.
                               WarrantyComp.ProxyComp._Impl(proxyStore).
                             $getProxy()).
                            fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                              tmp);
                        for (java.util.Iterator iter =
                               tmp.get$proxies().iterator();
                             iter.hasNext();
                             ) {
                            fabric.metrics.contracts.warranties.WarrantyComp
                              proxy =
                              (fabric.metrics.contracts.warranties.WarrantyComp)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(iter.next()));
                            proxy.set$proxies(proxy.get$proxies().add(rtn));
                        }
                        rtn.set$proxies(tmp.get$proxies().add(tmp));
                        tmp.set$proxies(tmp.get$proxies().add(rtn));
                    }
                }
            }
            else {
                {
                    fabric.metrics.contracts.warranties.WarrantyComp
                      rtn$var608 = rtn;
                    fabric.worker.transaction.TransactionManager $tm614 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled617 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff615 = 1;
                    boolean $doBackoff616 = true;
                    boolean $retry611 = true;
                    $label609: for (boolean $commit610 = false; !$commit610; ) {
                        if ($backoffEnabled617) {
                            if ($doBackoff616) {
                                if ($backoff615 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff615);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e612) {
                                            
                                        }
                                    }
                                }
                                if ($backoff615 < 5000) $backoff615 *= 2;
                            }
                            $doBackoff616 = $backoff615 <= 32 || !$doBackoff616;
                        }
                        $commit610 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.$getStore().equals(proxyStore)) {
                                rtn = tmp;
                            }
                            else {
                                for (java.util.Iterator iter =
                                       tmp.get$proxies().iterator();
                                     iter.hasNext();
                                     ) {
                                    fabric.metrics.contracts.warranties.WarrantyComp
                                      proxy =
                                      (fabric.metrics.contracts.warranties.WarrantyComp)
                                        fabric.lang.Object._Proxy.
                                        $getProxy(
                                          fabric.lang.WrappedJavaInlineable.
                                              $wrap(iter.next()));
                                    if (proxy.$getStore().equals(proxyStore)) {
                                        rtn = proxy;
                                        break;
                                    }
                                }
                                if (fabric.lang.Object._Proxy.idEquals(rtn,
                                                                       null)) {
                                    rtn =
                                      ((ProxyComp)
                                         new fabric.metrics.contracts.
                                           warranties.WarrantyComp.ProxyComp.
                                           _Impl(proxyStore).
                                         $getProxy()).
                                        fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                          tmp);
                                    for (java.util.Iterator iter =
                                           tmp.get$proxies().iterator();
                                         iter.hasNext();
                                         ) {
                                        fabric.metrics.contracts.warranties.WarrantyComp
                                          proxy =
                                          (fabric.metrics.contracts.warranties.WarrantyComp)
                                            fabric.lang.Object._Proxy.
                                            $getProxy(
                                              fabric.lang.WrappedJavaInlineable.
                                                  $wrap(iter.next()));
                                        proxy.set$proxies(
                                                proxy.get$proxies().add(rtn));
                                    }
                                    rtn.set$proxies(tmp.get$proxies().add(tmp));
                                    tmp.set$proxies(tmp.get$proxies().add(rtn));
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e612) {
                            $commit610 = false;
                            continue $label609;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e612) {
                            $commit610 = false;
                            fabric.common.TransactionID $currentTid613 =
                              $tm614.getCurrentTid();
                            if ($e612.tid.isDescendantOf($currentTid613))
                                continue $label609;
                            if ($currentTid613.parent != null) {
                                $retry611 = false;
                                throw $e612;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e612) {
                            $commit610 = false;
                            if ($tm614.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid613 =
                              $tm614.getCurrentTid();
                            if ($e612.tid.isDescendantOf($currentTid613)) {
                                $retry611 = true;
                            }
                            else if ($currentTid613.parent != null) {
                                $retry611 = false;
                                throw $e612;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e612) {
                            $commit610 = false;
                            if ($tm614.checkForStaleObjects())
                                continue $label609;
                            $retry611 = false;
                            throw new fabric.worker.AbortException($e612);
                        }
                        finally {
                            if ($commit610) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e612) {
                                    $commit610 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e612) {
                                    $commit610 = false;
                                    fabric.common.TransactionID $currentTid613 =
                                      $tm614.getCurrentTid();
                                    if ($currentTid613 != null) {
                                        if ($e612.tid.equals($currentTid613) ||
                                              !$e612.tid.isDescendantOf(
                                                           $currentTid613)) {
                                            throw $e612;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit610 && $retry611) {
                                { rtn = rtn$var608; }
                                continue $label609;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(
          boolean recomputeOnInvalidation, boolean proactive) {
            this.set$proxies(fabric.worker.metrics.ImmutableSet.emptySet());
            this.set$recomputeOnInvalidation(recomputeOnInvalidation);
            this.set$proactive(proactive);
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(
          boolean recomputeOnInvalidation) {
            return fabric$metrics$contracts$warranties$WarrantyComp$(
                     recomputeOnInvalidation, true);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return fabric$metrics$contracts$warranties$WarrantyComp$(true,
                                                                     true);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.WarrantyComp._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.curVal, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.curContract, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeInline(out, this.proxies);
            out.writeBoolean(this.recomputeOnInvalidation);
            out.writeBoolean(this.proactive);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.curVal = (fabric.lang.Object)
                            $readRef(fabric.lang.Object._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.curContract =
              (fabric.metrics.contracts.Contract)
                $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
            this.recomputeOnInvalidation = in.readBoolean();
            this.proactive = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.curContract = src.curContract;
            this.proxies = src.proxies;
            this.recomputeOnInvalidation = src.recomputeOnInvalidation;
            this.proactive = src.proactive;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.
              WarrantyComp._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.WarrantyComp._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.WarrantyComp._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -124, -76, 110, -124,
    23, 42, 99, -1, 67, 60, -125, -85, 61, -19, -68, 125, -106, -8, -115, -87,
    -55, -108, -79, 41, -41, 46, -102, -90, 107, -37, -44, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527105287000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
