package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyValue;
import fabric.worker.metrics.proxies.ProxyMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;
import fabric.common.util.LongIterator;
import fabric.common.util.Pair;

/**
 * A computation that uses {@link MetricTreaty}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link MetricTreaty}.
 * This helps to ensure that the {@link MetricTreaty} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.lang.Object {
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.worker.metrics.treaties.TreatyRef get$curTreaty();
    
    public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
      fabric.worker.metrics.treaties.TreatyRef val);
    
    public fabric.worker.metrics.proxies.ProxyMap get$proxies();
    
    public fabric.worker.metrics.proxies.ProxyMap set$proxies(
      fabric.worker.metrics.proxies.ProxyMap val);
    
    public boolean get$recomputeOnInvalidation();
    
    public boolean set$recomputeOnInvalidation(boolean val);
    
    public boolean get$proactive();
    
    public boolean set$proactive(boolean val);
    
    /**
   * Create a new updated result paired with a treaty that would enforce it
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
   * treaty associated with it (guaranteed active when returned) that asserts
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
   *            retry if the treaty goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.metrics.treaties.TreatySet treaties,
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
                    super(store, onum, version, observers, treaties, labelStore,
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
        
        public static final byte[] $classHash = new byte[] { 65, 110, -128, 117,
        30, 30, -58, -100, 19, 76, -127, 44, -37, 76, -28, -73, 47, 19, -48, 37,
        76, 55, 127, -79, 34, -74, 65, -36, 121, -97, 44, 19 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1529351168000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.lang.Object get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.lang.Object set$curVal(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.worker.metrics.treaties.TreatyRef get$curTreaty() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curTreaty();
        }
        
        public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
          fabric.worker.metrics.treaties.TreatyRef val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curTreaty(val);
        }
        
        public fabric.worker.metrics.proxies.ProxyMap get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.worker.metrics.proxies.ProxyMap set$proxies(
          fabric.worker.metrics.proxies.ProxyMap val) {
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
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
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
        
        public fabric.worker.metrics.treaties.TreatyRef get$curTreaty() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curTreaty;
        }
        
        public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
          fabric.worker.metrics.treaties.TreatyRef val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curTreaty = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.worker.metrics.treaties.TreatyRef curTreaty;
        
        public fabric.worker.metrics.proxies.ProxyMap get$proxies() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.proxies;
        }
        
        public fabric.worker.metrics.proxies.ProxyMap set$proxies(
          fabric.worker.metrics.proxies.ProxyMap val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The set of proxy computations for this computation. */
        protected fabric.worker.metrics.proxies.ProxyMap proxies;
        
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
   * Create a new updated result paired with a treaty that would enforce it
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
   * treaty associated with it (guaranteed active when returned) that asserts
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
   *            retry if the treaty goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
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
                    java.util.logging.Level.FINE,
                    "RUNNING ATTEMPT " +
                      i +
                      " OF " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                      " IN " +
                      java.lang.Thread.currentThread());
                if (!tmp.get$proactive()) {
                    for (java.util.Iterator iter =
                           tmp.get$proxies().values().iterator();
                         iter.hasNext();
                         ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                        if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_dropOldValue(proxy)) {
                            fabric.lang.Object newVal = proxy.get$curVal();
                            fabric.worker.metrics.treaties.TreatyRef newTreaty =
                              proxy.get$curTreaty();
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_setNewValue(tmp, newVal, newTreaty);
                            if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                  static_dropOldValue(tmp)) {
                                break;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(tmp.get$curTreaty(),
                                                       null)) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
                        "GENERATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.worker.metrics.WarrantyValue computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.treaty,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return computed;
                    }
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
                        "ACTIVATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    java.lang.String treatyStoreName =
                      computed.treaty.get().getMetric().$getStore().name();
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         treatyStoreName);
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn()) {
                        computed.treaty.get().getMetric().
                          refreshTreaty(false, computed.treaty.get().getId(),
                                        computed.weakStats);
                    }
                    else {
                        ((fabric.metrics.Metric._Proxy)
                           computed.treaty.get().getMetric()).
                          refreshTreaty$remote(w, null, false,
                                               computed.treaty.get().getId(),
                                               computed.weakStats);
                    }
                    fabric.worker.metrics.treaties.TreatyRef activatedTreaty =
                      computed.treaty;
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.FINE,
                        "SETTING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                      static_setNewValue(tmp, computed.value, activatedTreaty);
                    if (tmp.get$proactive()) {
                        for (java.util.Iterator iter =
                               tmp.get$proxies().values().iterator();
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
                                                 activatedTreaty);
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
                                                         tmp.get$curTreaty());
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
                    boolean rtn$var410 = rtn;
                    fabric.worker.transaction.TransactionManager $tm416 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled419 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff417 = 1;
                    boolean $doBackoff418 = true;
                    boolean $retry413 = true;
                    $label411: for (boolean $commit412 = false; !$commit412; ) {
                        if ($backoffEnabled419) {
                            if ($doBackoff418) {
                                if ($backoff417 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff417);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e414) {
                                            
                                        }
                                    }
                                }
                                if ($backoff417 < 5000) $backoff417 *= 2;
                            }
                            $doBackoff418 = $backoff417 <= 32 || !$doBackoff418;
                        }
                        $commit412 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e414) {
                            $commit412 = false;
                            continue $label411;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e414) {
                            $commit412 = false;
                            fabric.common.TransactionID $currentTid415 =
                              $tm416.getCurrentTid();
                            if ($e414.tid.isDescendantOf($currentTid415))
                                continue $label411;
                            if ($currentTid415.parent != null) {
                                $retry413 = false;
                                throw $e414;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e414) {
                            $commit412 = false;
                            if ($tm416.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid415 =
                              $tm416.getCurrentTid();
                            if ($e414.tid.isDescendantOf($currentTid415)) {
                                $retry413 = true;
                            }
                            else if ($currentTid415.parent != null) {
                                $retry413 = false;
                                throw $e414;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e414) {
                            $commit412 = false;
                            if ($tm416.checkForStaleObjects())
                                continue $label411;
                            $retry413 = false;
                            throw new fabric.worker.AbortException($e414);
                        }
                        finally {
                            if ($commit412) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e414) {
                                    $commit412 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e414) {
                                    $commit412 = false;
                                    fabric.common.TransactionID $currentTid415 =
                                      $tm416.getCurrentTid();
                                    if ($currentTid415 != null) {
                                        if ($e414.tid.equals($currentTid415) ||
                                              !$e414.tid.isDescendantOf(
                                                           $currentTid415)) {
                                            throw $e414;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit412 && $retry413) {
                                { rtn = rtn$var410; }
                                continue $label411;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private boolean dropOldValue() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(),
                                                    null)) {
                if (fabric.lang.Object._Proxy.idEquals(
                                                this.get$curTreaty().get(),
                                                null) ||
                      !this.get$curTreaty().get().valid()) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     this.get$curTreaty().get(),
                                                     null))
                        this.get$curTreaty().get().
                          removeObserver(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy());
                    this.set$curTreaty(null);
                }
            }
            return fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(),
                                                      null);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.worker.metrics.treaties.TreatyRef newTreaty) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   tmp.fetch()).setNewValue(newVal, newTreaty);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm425 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled428 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff426 = 1;
                    boolean $doBackoff427 = true;
                    boolean $retry422 = true;
                    $label420: for (boolean $commit421 = false; !$commit421; ) {
                        if ($backoffEnabled428) {
                            if ($doBackoff427) {
                                if ($backoff426 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff426);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e423) {
                                            
                                        }
                                    }
                                }
                                if ($backoff426 < 5000) $backoff426 *= 2;
                            }
                            $doBackoff427 = $backoff426 <= 32 || !$doBackoff427;
                        }
                        $commit421 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e423) {
                            $commit421 = false;
                            continue $label420;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e423) {
                            $commit421 = false;
                            fabric.common.TransactionID $currentTid424 =
                              $tm425.getCurrentTid();
                            if ($e423.tid.isDescendantOf($currentTid424))
                                continue $label420;
                            if ($currentTid424.parent != null) {
                                $retry422 = false;
                                throw $e423;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e423) {
                            $commit421 = false;
                            if ($tm425.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid424 =
                              $tm425.getCurrentTid();
                            if ($e423.tid.isDescendantOf($currentTid424)) {
                                $retry422 = true;
                            }
                            else if ($currentTid424.parent != null) {
                                $retry422 = false;
                                throw $e423;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e423) {
                            $commit421 = false;
                            if ($tm425.checkForStaleObjects())
                                continue $label420;
                            $retry422 = false;
                            throw new fabric.worker.AbortException($e423);
                        }
                        finally {
                            if ($commit421) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e423) {
                                    $commit421 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e423) {
                                    $commit421 = false;
                                    fabric.common.TransactionID $currentTid424 =
                                      $tm425.getCurrentTid();
                                    if ($currentTid424 != null) {
                                        if ($e423.tid.equals($currentTid424) ||
                                              !$e423.tid.isDescendantOf(
                                                           $currentTid424)) {
                                            throw $e423;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit421 && $retry422) {
                                {  }
                                continue $label420;
                            }
                        }
                    }
                }
            }
        }
        
        private void setNewValue(
          fabric.lang.Object newVal,
          fabric.worker.metrics.treaties.TreatyRef newTreaty) {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue() &&
                  !fabric.lang.Object._Proxy.idEquals(newTreaty, null) &&
                  !fabric.lang.Object._Proxy.idEquals(newTreaty.get(), null) &&
                  newTreaty.get().valid()) {
                this.set$curVal(newVal);
                this.
                  set$curTreaty(
                    fabric.worker.metrics.treaties.TreatyRef.
                        createRef(
                          newTreaty.get().getProxyTreaty(this.$getStore()).
                              addObserver(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(),
                                                    null) &&
                  !fabric.lang.Object._Proxy.idEquals(
                                               this.get$curTreaty().get(),
                                               null))
                return this.get$curTreaty().get().getLeafSubjects();
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
                }
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
                    rtn =
                      (fabric.metrics.contracts.warranties.WarrantyComp)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    tmp.get$proxies(
                                                          ).get(proxyStore));
                    if (fabric.lang.Object._Proxy.idEquals(rtn, null)) {
                        rtn =
                          ((ProxyComp)
                             new fabric.metrics.contracts.warranties.
                               WarrantyComp.ProxyComp._Impl(proxyStore).
                             $getProxy()).
                            fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                              tmp);
                        for (java.util.Iterator iter =
                               tmp.get$proxies().values().iterator();
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
                                    proxy.get$proxies().put(rtn.$getStore(),
                                                            rtn));
                        }
                        rtn.set$proxies(tmp.get$proxies().put(tmp.$getStore(),
                                                              tmp));
                        tmp.set$proxies(tmp.get$proxies().put(rtn.$getStore(),
                                                              rtn));
                    }
                }
            }
            else {
                {
                    fabric.metrics.contracts.warranties.WarrantyComp
                      rtn$var429 = rtn;
                    fabric.worker.transaction.TransactionManager $tm435 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled438 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff436 = 1;
                    boolean $doBackoff437 = true;
                    boolean $retry432 = true;
                    $label430: for (boolean $commit431 = false; !$commit431; ) {
                        if ($backoffEnabled438) {
                            if ($doBackoff437) {
                                if ($backoff436 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff436);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e433) {
                                            
                                        }
                                    }
                                }
                                if ($backoff436 < 5000) $backoff436 *= 2;
                            }
                            $doBackoff437 = $backoff436 <= 32 || !$doBackoff437;
                        }
                        $commit431 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.$getStore().equals(proxyStore)) {
                                rtn = tmp;
                            }
                            else {
                                rtn =
                                  (fabric.metrics.contracts.warranties.WarrantyComp)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      tmp.get$proxies().get(proxyStore));
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
                                           tmp.get$proxies().values().iterator(
                                                                        );
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
                                                proxy.get$proxies(
                                                        ).put(rtn.$getStore(),
                                                              rtn));
                                    }
                                    rtn.set$proxies(
                                          tmp.get$proxies().put(tmp.$getStore(),
                                                                tmp));
                                    tmp.set$proxies(
                                          tmp.get$proxies().put(rtn.$getStore(),
                                                                rtn));
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e433) {
                            $commit431 = false;
                            continue $label430;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e433) {
                            $commit431 = false;
                            fabric.common.TransactionID $currentTid434 =
                              $tm435.getCurrentTid();
                            if ($e433.tid.isDescendantOf($currentTid434))
                                continue $label430;
                            if ($currentTid434.parent != null) {
                                $retry432 = false;
                                throw $e433;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e433) {
                            $commit431 = false;
                            if ($tm435.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid434 =
                              $tm435.getCurrentTid();
                            if ($e433.tid.isDescendantOf($currentTid434)) {
                                $retry432 = true;
                            }
                            else if ($currentTid434.parent != null) {
                                $retry432 = false;
                                throw $e433;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e433) {
                            $commit431 = false;
                            if ($tm435.checkForStaleObjects())
                                continue $label430;
                            $retry432 = false;
                            throw new fabric.worker.AbortException($e433);
                        }
                        finally {
                            if ($commit431) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e433) {
                                    $commit431 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e433) {
                                    $commit431 = false;
                                    fabric.common.TransactionID $currentTid434 =
                                      $tm435.getCurrentTid();
                                    if ($currentTid434 != null) {
                                        if ($e433.tid.equals($currentTid434) ||
                                              !$e433.tid.isDescendantOf(
                                                           $currentTid434)) {
                                            throw $e433;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit431 && $retry432) {
                                { rtn = rtn$var429; }
                                continue $label430;
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
            this.set$recomputeOnInvalidation(recomputeOnInvalidation);
            this.set$proactive(proactive);
            fabric$lang$Object$();
            this.set$proxies(fabric.worker.metrics.proxies.ProxyMap.emptyMap());
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
            $writeInline(out, this.curTreaty);
            $writeInline(out, this.proxies);
            out.writeBoolean(this.recomputeOnInvalidation);
            out.writeBoolean(this.proactive);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.curVal = (fabric.lang.Object)
                            $readRef(fabric.lang.Object._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.curTreaty = (fabric.worker.metrics.treaties.TreatyRef)
                               in.readObject();
            this.proxies = (fabric.worker.metrics.proxies.ProxyMap)
                             in.readObject();
            this.recomputeOnInvalidation = in.readBoolean();
            this.proactive = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.curTreaty = src.curTreaty;
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -56, 83, -88, -48, 53,
    -27, -102, -73, 58, -71, 54, 68, -29, 12, -97, 66, -33, 86, -56, -60, 20,
    99, -17, -1, -125, -22, -75, 94, 93, 36, 61, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZC2wcxRn+7+zYPseJHecBOC8nuaZN4twRXoW4QSSGwMEljuI8qCNi9vbm7MV7u8funH2mTUqBlqiVogrCI20JVQmC0gAtaqApikhVHqH0ARUCqopHi3i0NKIUQmlLSf9/Zu5uvb7b2BW1vPPvzc4/83///P83M7sHjsEk14GFGS1lmDE+kmNubK2WSiQ3aI7L0l2m5rqbsLZPn1ybuPXte9LzwhBOQpOuWbZl6JrZZ7kcpiav0oa0uMV4fPPGROc2iOikeInmDnAIb1tTcKA9Z5sj/abN1SBj+r9lWXzPbdtbHqqB5l5oNqwernFD77Itzgq8F5qyLJtijrs6nWbpXphmMZbuYY6hmcY12NC2eqHVNfotjecd5m5krm0OUcNWN59jjhizWEnm22i2k9e57aD5LdL8PDfMeNJweWcS6jIGM9Pu1bATapMwKWNq/dhwVrKIIi56jK+lemzeaKCZTkbTWVGldtCw0hzm+zVKiKOXYQNUrc8yPmCXhqq1NKyAVmmSqVn98R7uGFY/Np1k53EUDm1VO8VGDTlNH9T6WR+HU/3tNshH2Coi3EIqHGb6m4mecM7afHPmma1j67+w+0vWJVYYQmhzmukm2d+ASvN8ShtZhjnM0plUbFqavFWbdXhXGAAbz/Q1lm0e+fJ7F3TMO3JUtpldoU136iqm8z59f2rqc3O6lpxXQ2Y05GzXoFAYhVzM6gb1pLOQw2ifVeqRHsaKD49sfPKL197H3glDYwLqdNvMZzGqpul2NmeYzLmYWczROEsnIMKsdJd4noB6vE8aFpO13ZmMy3gCak1RVWeL3+iiDHZBLqrHe8PK2MX7nMYHxH0hBwAteEEI/9cDrDiI9+0AtQUOffEBO8viKTPPhjG843gxzdEH4pi3jqHHXUePO3mLG9hIVWEUoXDjGOrc0XTuxoc1x9GwDepvlbcjXYgthqbl/v9DFAhly3AohBMwX7fTLKW5OJsqstZsMDF5LrHNNHP6dHP34QRMP7xXRFeEMsLFqBb+C2FEzPFziVd3T37NRe890PeMjEzSVe7lcLq0O6bsjpXsjpXtjnntRlObKA9jyGwxZLYDoUKsa1/ihyLc6lyRl6Xem7D3lTlT4xnbyRYgFBJQZwh9EWcYJYPIPkgwTUt6rrj0yl0LazDAc8O1NOfYNOpPtzJJJfBOwxzq05tvfPvDB2/dYZcTj0N0DB+M1aR8Xuj3m2PrLI18We5+abt2sO/wjmiYuChCDtIwkJFz5vnHGJXXnUWOJG9MSsJk8oFm0qMisTXyAcceLteIeJhKRasMDXKWz0BBr6t6cne89Js/nykWniITN3sou4fxTk/2U2fNIs+nlX2/yWEM2718+4abbzl24zbheGyxqNKAUSpp+jVMd9v52tGrf//qK/ufD5cni0NdLp8yDb0gsEw7gX8hvD6hi1KYKkgikXcp+mgv8UeORl5ctg2ZxEQ2Q9Pd6GYra6eNjKGlTEaR8nHzZ1Yc/OvuFjndJtZI5znQcfIOyvWnrYFrn9n+j3mim5BOK1nZf+Vmkh6nl3tejbkwQnYUvvq7uXuf0u7AyEdyc41rmOArEP4AMYFnCF8sF+UK37OzqFgovTVH1NOuw79UrKU1txyLvfED323rOv8dyQKlWKQ+FlRggS2aJ03OuC97PLyw7okw1PdCi1juMam3aMhuGAa9uGC7XaoyCVNGPR+9+MqVprOUa3P8eeAZ1p8FZfbBe2pN940y8GXgoCNaQdI8LEKn1EpZ+yE9nZ6jckYhBOJmpVBZJMrFVCwpBmMk59gcrWTpQqnbMHU7WXX3rpJverrFGNbzDiIWKjMRtWJHgh2TsMWj0/zUJrOVynNKwzUVUSwGQi7luRVQXCRRULFqrLGk9XklY6OMjaCxmMQaHyna+zll77DtDDKnROqcGhGRy9ZIUxNEEcdrKY7/gZLPVUCxPhAFaT2r5FOjUNTjTBXQuCKGxZUxqFZE0IWRdVpu/AhENJ2B1+kAdXcpeX0FBFsrR1OYbs9HdxvZbJ4TiYhhlnE4xWG0E8IdaLeVsIZw350W++4KebzBMbJIxUNqy8d27fnGidjuPZLD5L540ZitqVdH7o3FyFPE8AUcZUHQKEJj7VsP7nj03h03yn1j6+hd3kVWPnv/C//5Vez2156usDeoT9m2yTSrqlOjeJ0L0FAvZf3xCk5NBzmVim1Fb1LK4s4DraeKPjFqIUB7KYcGLeWK7Uo5ycVfs9omDis54DHMQ7ihYszN8e2ChHe6Uy5zhiS5tpG351bb+wtP779uz750990rworfNUTE7dxykw0x0zMoLeoLxpwt14kTT5msX3tn7nldg2/0y3mb7xvZ3/oH6w48ffFi/aYw1JRYecwxa7RS52gubnQYnhKtTaMYub3k1ZkgpxhWA0QmSdnwJ+90l4MkYK4HfWQ8Q/X0RyV/65+n8tIZ8szYFSoBSFzJcQ9gW/3CgB0B6+11VAxzaMzn0rTl8ND8osqUU9z2iiWwyDcR4hvTxmO+HJ+XEEVoqM/itQ6g8Q4lvzJOH4VERPvc06A62alk4aTuoZ87RbPdAa74FhW7OEzScjlzhH7cUAnLSry2A0xplrLplSpYqPjmWMtJ5WUlX6xuebgcIztF4ouubwswfy8VNwWZL4hgG14pNP/7Sl4+3nAVa5IxhEHC6URD71x8E9OiutyqZKI6vBrRZ40Yz4fxrgCMd1PxHVze5Ph9wVDPx+s+gNmnK1kzkcy8k4rvVUBIPYWlbPtgXKHXIga7PwDXg1Tcw2G6wpV27Fy3mRYpJjzjg9cIksLhYYA5C5ScPJGkutOHLKI6aVSypjoyr+GPBDw7RMWPcbJOhkZMFuYDHAGYu1fJ5KcyWdTTZUquGn84rqbiUjHszwMQ/oKKR3FDrKbNZXw9Gy4To5+Rh2wjXWkmKVB/CTDvZiX7AijlJ2PnjVS2K7l1XJTiwffrAHxi4TnKYbIHGFU9UYkXL8TreYzKkJTznwwAUYEXSeUJJR8bX/C9EPDsJSqew+N/P+NJpmV68vKcUlzcOiovboniZnadrNjCxJtfUhqzq67kBKK+PwAsqJey/dDEnEAqP1XyoXHNZF8RUJsChFvvrG2p90i4A8BDLLX4uhj99QCPvUXFKxymDGhW2mSbxX6g5K+lJ/FXcVuIw43LW+IAhas3HMMD5wVSLjwR4K0KpydS+UTJ4+Py1g1FONNHw+nBSWaV7RZ2/D3Abx9RcQwjLasNMnEO28jcvCm8vrpSjNCx9TgeE5ZKuejjicUIqfxbyQDUnvXnfdHrieoYQqLZv3AbV8JQtsPH05fj9U+0/pCSzqfC09TT1Urq45rKlhKyUCQAGS2KoRqOVkmKDgQoghK3lSHcr3Z0SLnsZxMKSqFySMkfjS+FRSGsbQ1AMoOKKRxWyNCNqhSMlt5NR8vvpqPed9PRqlAH0c44wPJuKTv+NjGopPKukm9Wh+qJRIlydgDKuVTM+lRRJnHwMwFiIOXyIxNDSSqPKflwdZReENGAZ5QSofn/M8AC8pO3ll7Dzq7wkUR92tO7Hmf737isY2aVDySnjvnYqvQe2NfccMq+zS+K1/ulz3aRJDRk8qbpfV3pua/LOSxjCJ9G5MvLnMC8rHyYDPqigqfQ8g9yXmiJ1I9xOLWaPpcvfMW9V2cFh6mjdbj4gkp33nZn4WlGtqNfZ4u5batQrBaA2vIOfaY+8P4pH9U1bHpNvOnHmW0/2nPvs2e/8e1HVh4658LXm+5c8+qWo4/P0N89cf1fDm6/Iroq/F+Xz+69Ph8AAA==";
}
