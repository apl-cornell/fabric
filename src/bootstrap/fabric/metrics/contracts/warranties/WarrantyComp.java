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
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import fabric.common.Logging;
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
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, java.util.SortedSet treaties);
    
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
                               ImmutableObjectSet associates, long expiry,
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
                    super(store, onum, version, associates, expiry, labelStore,
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
        
        public static final byte[] $classHash = new byte[] { 46, 19, 92, 22, 94,
        -52, 109, -118, -29, -128, -75, -2, 11, 29, -124, 57, 16, -128, 125,
        -49, -23, 0, 51, 42, 59, 91, 93, 94, -66, -81, 8, -70 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1548260582000L;
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
          boolean arg1, java.util.SortedSet arg2) {
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
                    boolean rtn$var463 = rtn;
                    fabric.worker.transaction.TransactionManager $tm470 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled473 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff471 = 1;
                    boolean $doBackoff472 = true;
                    boolean $retry466 = true;
                    boolean $keepReads467 = false;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled473) {
                            if ($doBackoff472) {
                                if ($backoff471 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff471));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e468) {
                                            
                                        }
                                    }
                                }
                                if ($backoff471 < 5000) $backoff471 *= 2;
                            }
                            $doBackoff472 = $backoff471 <= 32 || !$doBackoff472;
                        }
                        $commit465 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                rtn =
                                  ((fabric.metrics.contracts.warranties.WarrantyComp.
                                     _Impl) tmp.fetch()).dropOldValue();
                            }
                            catch (final fabric.worker.RetryException $e468) {
                                throw $e468;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e468) {
                                throw $e468;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e468) {
                                throw $e468;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e468) {
                                throw $e468;
                            }
                            catch (final Throwable $e468) {
                                $tm470.getCurrentLog().checkRetrySignal();
                                throw $e468;
                            }
                        }
                        catch (final fabric.worker.RetryException $e468) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e468) {
                            $commit465 = false;
                            $retry466 = false;
                            $keepReads467 = $e468.keepReads;
                            if ($tm470.checkForStaleObjects()) {
                                $retry466 = true;
                                $keepReads467 = false;
                                continue $label464;
                            }
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid == null ||
                                  !$e468.tid.isDescendantOf($currentTid469)) {
                                throw $e468;
                            }
                            throw new fabric.worker.UserAbortException($e468);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e468) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469))
                                continue $label464;
                            if ($currentTid469.parent != null) {
                                $retry466 = false;
                                throw $e468;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e468) {
                            $commit465 = false;
                            if ($tm470.checkForStaleObjects())
                                continue $label464;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469)) {
                                $retry466 = true;
                            }
                            else if ($currentTid469.parent != null) {
                                $retry466 = false;
                                throw $e468;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e468) {
                            $commit465 = false;
                            if ($tm470.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e468);
                        }
                        finally {
                            if ($commit465) {
                                fabric.common.TransactionID $currentTid469 =
                                  $tm470.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e468) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e468) {
                                    $commit465 = false;
                                    $retry466 = false;
                                    $keepReads467 = $e468.keepReads;
                                    if ($tm470.checkForStaleObjects()) {
                                        $retry466 = true;
                                        $keepReads467 = false;
                                        continue $label464;
                                    }
                                    if ($e468.tid ==
                                          null ||
                                          !$e468.tid.isDescendantOf(
                                                       $currentTid469))
                                        throw $e468;
                                    throw new fabric.worker.UserAbortException(
                                            $e468);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e468) {
                                    $commit465 = false;
                                    $currentTid469 = $tm470.getCurrentTid();
                                    if ($currentTid469 != null) {
                                        if ($e468.tid.equals($currentTid469) ||
                                              !$e468.tid.isDescendantOf(
                                                           $currentTid469)) {
                                            throw $e468;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads467) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465) {
                                { rtn = rtn$var463; }
                                if ($retry466) { continue $label464; }
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
                    fabric.worker.transaction.TransactionManager $tm480 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled483 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff481 = 1;
                    boolean $doBackoff482 = true;
                    boolean $retry476 = true;
                    boolean $keepReads477 = false;
                    $label474: for (boolean $commit475 = false; !$commit475; ) {
                        if ($backoffEnabled483) {
                            if ($doBackoff482) {
                                if ($backoff481 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff481));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e478) {
                                            
                                        }
                                    }
                                }
                                if ($backoff481 < 5000) $backoff481 *= 2;
                            }
                            $doBackoff482 = $backoff481 <= 32 || !$doBackoff482;
                        }
                        $commit475 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                ((fabric.metrics.contracts.warranties.WarrantyComp.
                                   _Impl) tmp.fetch()).setNewValue(newVal,
                                                                   newTreaty);
                            }
                            catch (final fabric.worker.RetryException $e478) {
                                throw $e478;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e478) {
                                throw $e478;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e478) {
                                throw $e478;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e478) {
                                throw $e478;
                            }
                            catch (final Throwable $e478) {
                                $tm480.getCurrentLog().checkRetrySignal();
                                throw $e478;
                            }
                        }
                        catch (final fabric.worker.RetryException $e478) {
                            $commit475 = false;
                            continue $label474;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e478) {
                            $commit475 = false;
                            $retry476 = false;
                            $keepReads477 = $e478.keepReads;
                            if ($tm480.checkForStaleObjects()) {
                                $retry476 = true;
                                $keepReads477 = false;
                                continue $label474;
                            }
                            fabric.common.TransactionID $currentTid479 =
                              $tm480.getCurrentTid();
                            if ($e478.tid == null ||
                                  !$e478.tid.isDescendantOf($currentTid479)) {
                                throw $e478;
                            }
                            throw new fabric.worker.UserAbortException($e478);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e478) {
                            $commit475 = false;
                            fabric.common.TransactionID $currentTid479 =
                              $tm480.getCurrentTid();
                            if ($e478.tid.isDescendantOf($currentTid479))
                                continue $label474;
                            if ($currentTid479.parent != null) {
                                $retry476 = false;
                                throw $e478;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e478) {
                            $commit475 = false;
                            if ($tm480.checkForStaleObjects())
                                continue $label474;
                            fabric.common.TransactionID $currentTid479 =
                              $tm480.getCurrentTid();
                            if ($e478.tid.isDescendantOf($currentTid479)) {
                                $retry476 = true;
                            }
                            else if ($currentTid479.parent != null) {
                                $retry476 = false;
                                throw $e478;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e478) {
                            $commit475 = false;
                            if ($tm480.checkForStaleObjects())
                                continue $label474;
                            $retry476 = false;
                            throw new fabric.worker.AbortException($e478);
                        }
                        finally {
                            if ($commit475) {
                                fabric.common.TransactionID $currentTid479 =
                                  $tm480.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e478) {
                                    $commit475 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e478) {
                                    $commit475 = false;
                                    $retry476 = false;
                                    $keepReads477 = $e478.keepReads;
                                    if ($tm480.checkForStaleObjects()) {
                                        $retry476 = true;
                                        $keepReads477 = false;
                                        continue $label474;
                                    }
                                    if ($e478.tid ==
                                          null ||
                                          !$e478.tid.isDescendantOf(
                                                       $currentTid479))
                                        throw $e478;
                                    throw new fabric.worker.UserAbortException(
                                            $e478);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e478) {
                                    $commit475 = false;
                                    $currentTid479 = $tm480.getCurrentTid();
                                    if ($currentTid479 != null) {
                                        if ($e478.tid.equals($currentTid479) ||
                                              !$e478.tid.isDescendantOf(
                                                           $currentTid479)) {
                                            throw $e478;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads477) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit475) {
                                {  }
                                if ($retry476) { continue $label474; }
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
          boolean includesObserver, java.util.SortedSet treaties) {
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
                      rtn$var484 = rtn;
                    fabric.worker.transaction.TransactionManager $tm491 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled494 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff492 = 1;
                    boolean $doBackoff493 = true;
                    boolean $retry487 = true;
                    boolean $keepReads488 = false;
                    $label485: for (boolean $commit486 = false; !$commit486; ) {
                        if ($backoffEnabled494) {
                            if ($doBackoff493) {
                                if ($backoff492 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff492));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e489) {
                                            
                                        }
                                    }
                                }
                                if ($backoff492 < 5000) $backoff492 *= 2;
                            }
                            $doBackoff493 = $backoff492 <= 32 || !$doBackoff493;
                        }
                        $commit486 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
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
                                    if (fabric.lang.Object._Proxy.idEquals(
                                                                    rtn,
                                                                    null)) {
                                        rtn =
                                          ((ProxyComp)
                                             new fabric.metrics.contracts.
                                               warranties.WarrantyComp.
                                               ProxyComp._Impl(proxyStore).
                                             $getProxy()).
                                            fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                              tmp);
                                        for (java.util.Iterator iter =
                                               tmp.get$proxies().values().
                                               iterator();
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
                                                            ).put(
                                                                rtn.$getStore(),
                                                                rtn));
                                        }
                                        rtn.set$proxies(
                                              tmp.get$proxies().put(
                                                                  tmp.$getStore(
                                                                        ),
                                                                  tmp));
                                        tmp.set$proxies(
                                              tmp.get$proxies().put(
                                                                  rtn.$getStore(
                                                                        ),
                                                                  rtn));
                                    }
                                }
                            }
                            catch (final fabric.worker.RetryException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e489) {
                                throw $e489;
                            }
                            catch (final Throwable $e489) {
                                $tm491.getCurrentLog().checkRetrySignal();
                                throw $e489;
                            }
                        }
                        catch (final fabric.worker.RetryException $e489) {
                            $commit486 = false;
                            continue $label485;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e489) {
                            $commit486 = false;
                            $retry487 = false;
                            $keepReads488 = $e489.keepReads;
                            if ($tm491.checkForStaleObjects()) {
                                $retry487 = true;
                                $keepReads488 = false;
                                continue $label485;
                            }
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid == null ||
                                  !$e489.tid.isDescendantOf($currentTid490)) {
                                throw $e489;
                            }
                            throw new fabric.worker.UserAbortException($e489);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e489) {
                            $commit486 = false;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490))
                                continue $label485;
                            if ($currentTid490.parent != null) {
                                $retry487 = false;
                                throw $e489;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e489) {
                            $commit486 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label485;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490)) {
                                $retry487 = true;
                            }
                            else if ($currentTid490.parent != null) {
                                $retry487 = false;
                                throw $e489;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e489) {
                            $commit486 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label485;
                            $retry487 = false;
                            throw new fabric.worker.AbortException($e489);
                        }
                        finally {
                            if ($commit486) {
                                fabric.common.TransactionID $currentTid490 =
                                  $tm491.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e489) {
                                    $commit486 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e489) {
                                    $commit486 = false;
                                    $retry487 = false;
                                    $keepReads488 = $e489.keepReads;
                                    if ($tm491.checkForStaleObjects()) {
                                        $retry487 = true;
                                        $keepReads488 = false;
                                        continue $label485;
                                    }
                                    if ($e489.tid ==
                                          null ||
                                          !$e489.tid.isDescendantOf(
                                                       $currentTid490))
                                        throw $e489;
                                    throw new fabric.worker.UserAbortException(
                                            $e489);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e489) {
                                    $commit486 = false;
                                    $currentTid490 = $tm491.getCurrentTid();
                                    if ($currentTid490 != null) {
                                        if ($e489.tid.equals($currentTid490) ||
                                              !$e489.tid.isDescendantOf(
                                                           $currentTid490)) {
                                            throw $e489;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads488) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit486) {
                                { rtn = rtn$var484; }
                                if ($retry487) { continue $label485; }
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 117, -62, -77, 69,
    -127, 57, -31, -40, 44, 109, 6, -67, 117, 49, -6, 34, -45, -44, -91, -67,
    15, -61, 100, 68, 7, -36, -20, 38, 69, -69, -107, 106 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
