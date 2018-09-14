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
        
        public static final byte[] $classHash = new byte[] { 80, 15, -110, -28,
        -78, 77, 2, -55, -30, -115, 20, 2, -46, -42, -9, 77, 120, -89, 53, 11,
        -124, 57, -62, 92, 67, -92, -122, -39, 59, -109, 125, 84 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536940699000L;
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
    
    public static final byte[] $classHash = new byte[] { 107, -104, 38, -86,
    -116, 90, -56, 18, 108, -91, -88, 52, -50, 25, 75, 104, 99, -117, -40, 124,
    -70, -30, 82, 2, -27, 69, -93, 17, 105, 24, -61, 6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536940699000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
