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
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
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
        
        public static final byte[] $classHash = new byte[] { 2, 68, -56, -63,
        -30, -15, 6, -97, -3, -88, 112, -72, 69, 47, 76, -31, 61, -117, -75,
        -53, -56, 118, 29, 99, 120, 118, -29, -73, 86, -79, 17, 108 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527097637000L;
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue()) {
                if (this.get$recomputeOnInvalidation())
                    apply(java.lang.System.currentTimeMillis(), false);
                return getObservers();
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
    
    public static final byte[] $classHash = new byte[] { 68, 78, -48, -114, -95,
    -2, -47, -36, -123, -7, -115, -82, -96, -9, 58, 118, 85, -18, -76, 53, 72,
    92, 66, 48, -72, -90, -78, 119, 22, -77, 111, -11 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527097637000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
