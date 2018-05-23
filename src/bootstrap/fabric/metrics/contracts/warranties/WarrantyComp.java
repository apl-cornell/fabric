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
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;
import fabric.common.util.LongIterator;
import fabric.common.util.Pair;

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
        
        public static final byte[] $classHash = new byte[] { 85, 3, -45, -76,
        -72, 94, -117, 76, 111, -58, -42, -104, 50, 67, -41, 35, 93, -49, -14,
        115, -84, -7, -7, 97, 111, -29, 36, -68, -33, 98, 107, -69 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527110221000L;
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
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            if (includesObserver) {
                if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       this.fetch()).dropOldValue()) {
                    if (this.get$recomputeOnInvalidation())
                        apply(java.lang.System.currentTimeMillis(), false);
                    affected = affected.addAll(getObservers());
                }
            }
            for (fabric.common.util.LongIterator iter = treaties.iterator();
                 iter.hasNext(); ) {
                long treatyId = iter.next();
                fabric.worker.metrics.treaties.MetricTreaty origTreaty =
                  this.get$$treaties().get(treatyId);
                fabric.common.util.Pair treatyUpdate =
                  origTreaty.update(
                               false,
                               fabric.worker.metrics.StatsMap.emptyStats());
                affected =
                  affected.addAll((fabric.worker.metrics.ImmutableObserverSet)
                                    treatyUpdate.second);
                fabric.worker.metrics.treaties.MetricTreaty newTreaty =
                  (fabric.worker.metrics.treaties.MetricTreaty)
                    treatyUpdate.first;
                if (!newTreaty.equals(origTreaty))
                    this.set$$treaties(this.get$$treaties().add(newTreaty));
            }
            return affected;
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
    
    public static final byte[] $classHash = new byte[] { -105, -78, -22, -21,
    -4, -44, 104, 111, 79, 67, -80, -25, 19, 96, 68, -45, 86, -107, 48, 86, 82,
    92, 113, 49, -112, -116, -79, 15, -11, -17, 51, -79 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527110221000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
