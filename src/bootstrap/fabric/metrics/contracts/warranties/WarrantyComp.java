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
        
        public static final byte[] $classHash = new byte[] { 117, -3, -122, 17,
        29, -88, -99, -65, -94, 26, -81, 24, 7, 126, -58, -39, -90, 83, -73,
        -54, 79, -17, -119, -40, -122, -112, -53, 122, -89, 65, -25, -8 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1533241119000L;
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
                    boolean rtn$var430 = rtn;
                    fabric.worker.transaction.TransactionManager $tm436 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled439 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff437 = 1;
                    boolean $doBackoff438 = true;
                    boolean $retry433 = true;
                    $label431: for (boolean $commit432 = false; !$commit432; ) {
                        if ($backoffEnabled439) {
                            if ($doBackoff438) {
                                if ($backoff437 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff437);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e434) {
                                            
                                        }
                                    }
                                }
                                if ($backoff437 < 5000) $backoff437 *= 2;
                            }
                            $doBackoff438 = $backoff437 <= 32 || !$doBackoff438;
                        }
                        $commit432 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                rtn =
                                  ((fabric.metrics.contracts.warranties.WarrantyComp.
                                     _Impl) tmp.fetch()).dropOldValue();
                            }
                            catch (final fabric.worker.RetryException $e434) {
                                throw $e434;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e434) {
                                throw $e434;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e434) {
                                throw $e434;
                            }
                            catch (final Throwable $e434) {
                                $tm436.getCurrentLog().checkRetrySignal();
                                throw $e434;
                            }
                        }
                        catch (final fabric.worker.RetryException $e434) {
                            $commit432 = false;
                            continue $label431;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e434) {
                            $commit432 = false;
                            fabric.common.TransactionID $currentTid435 =
                              $tm436.getCurrentTid();
                            if ($e434.tid.isDescendantOf($currentTid435))
                                continue $label431;
                            if ($currentTid435.parent != null) {
                                $retry433 = false;
                                throw $e434;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e434) {
                            $commit432 = false;
                            if ($tm436.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid435 =
                              $tm436.getCurrentTid();
                            if ($e434.tid.isDescendantOf($currentTid435)) {
                                $retry433 = true;
                            }
                            else if ($currentTid435.parent != null) {
                                $retry433 = false;
                                throw $e434;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e434) {
                            $commit432 = false;
                            if ($tm436.checkForStaleObjects())
                                continue $label431;
                            $retry433 = false;
                            throw new fabric.worker.AbortException($e434);
                        }
                        finally {
                            if ($commit432) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e434) {
                                    $commit432 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e434) {
                                    $commit432 = false;
                                    fabric.common.TransactionID $currentTid435 =
                                      $tm436.getCurrentTid();
                                    if ($currentTid435 != null) {
                                        if ($e434.tid.equals($currentTid435) ||
                                              !$e434.tid.isDescendantOf(
                                                           $currentTid435)) {
                                            throw $e434;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit432 && $retry433) {
                                { rtn = rtn$var430; }
                                continue $label431;
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
                    fabric.worker.transaction.TransactionManager $tm445 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled448 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff446 = 1;
                    boolean $doBackoff447 = true;
                    boolean $retry442 = true;
                    $label440: for (boolean $commit441 = false; !$commit441; ) {
                        if ($backoffEnabled448) {
                            if ($doBackoff447) {
                                if ($backoff446 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff446);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e443) {
                                            
                                        }
                                    }
                                }
                                if ($backoff446 < 5000) $backoff446 *= 2;
                            }
                            $doBackoff447 = $backoff446 <= 32 || !$doBackoff447;
                        }
                        $commit441 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                ((fabric.metrics.contracts.warranties.WarrantyComp.
                                   _Impl) tmp.fetch()).setNewValue(newVal,
                                                                   newTreaty);
                            }
                            catch (final fabric.worker.RetryException $e443) {
                                throw $e443;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e443) {
                                throw $e443;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e443) {
                                throw $e443;
                            }
                            catch (final Throwable $e443) {
                                $tm445.getCurrentLog().checkRetrySignal();
                                throw $e443;
                            }
                        }
                        catch (final fabric.worker.RetryException $e443) {
                            $commit441 = false;
                            continue $label440;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e443) {
                            $commit441 = false;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444))
                                continue $label440;
                            if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444)) {
                                $retry442 = true;
                            }
                            else if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects())
                                continue $label440;
                            $retry442 = false;
                            throw new fabric.worker.AbortException($e443);
                        }
                        finally {
                            if ($commit441) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e443) {
                                    $commit441 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e443) {
                                    $commit441 = false;
                                    fabric.common.TransactionID $currentTid444 =
                                      $tm445.getCurrentTid();
                                    if ($currentTid444 != null) {
                                        if ($e443.tid.equals($currentTid444) ||
                                              !$e443.tid.isDescendantOf(
                                                           $currentTid444)) {
                                            throw $e443;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit441 && $retry442) {
                                {  }
                                continue $label440;
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
                      rtn$var449 = rtn;
                    fabric.worker.transaction.TransactionManager $tm455 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled458 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff456 = 1;
                    boolean $doBackoff457 = true;
                    boolean $retry452 = true;
                    $label450: for (boolean $commit451 = false; !$commit451; ) {
                        if ($backoffEnabled458) {
                            if ($doBackoff457) {
                                if ($backoff456 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff456);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e453) {
                                            
                                        }
                                    }
                                }
                                if ($backoff456 < 5000) $backoff456 *= 2;
                            }
                            $doBackoff457 = $backoff456 <= 32 || !$doBackoff457;
                        }
                        $commit451 = true;
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
                            catch (final fabric.worker.RetryException $e453) {
                                throw $e453;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e453) {
                                throw $e453;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e453) {
                                throw $e453;
                            }
                            catch (final Throwable $e453) {
                                $tm455.getCurrentLog().checkRetrySignal();
                                throw $e453;
                            }
                        }
                        catch (final fabric.worker.RetryException $e453) {
                            $commit451 = false;
                            continue $label450;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e453) {
                            $commit451 = false;
                            fabric.common.TransactionID $currentTid454 =
                              $tm455.getCurrentTid();
                            if ($e453.tid.isDescendantOf($currentTid454))
                                continue $label450;
                            if ($currentTid454.parent != null) {
                                $retry452 = false;
                                throw $e453;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e453) {
                            $commit451 = false;
                            if ($tm455.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid454 =
                              $tm455.getCurrentTid();
                            if ($e453.tid.isDescendantOf($currentTid454)) {
                                $retry452 = true;
                            }
                            else if ($currentTid454.parent != null) {
                                $retry452 = false;
                                throw $e453;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e453) {
                            $commit451 = false;
                            if ($tm455.checkForStaleObjects())
                                continue $label450;
                            $retry452 = false;
                            throw new fabric.worker.AbortException($e453);
                        }
                        finally {
                            if ($commit451) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e453) {
                                    $commit451 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e453) {
                                    $commit451 = false;
                                    fabric.common.TransactionID $currentTid454 =
                                      $tm455.getCurrentTid();
                                    if ($currentTid454 != null) {
                                        if ($e453.tid.equals($currentTid454) ||
                                              !$e453.tid.isDescendantOf(
                                                           $currentTid454)) {
                                            throw $e453;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit451 && $retry452) {
                                { rtn = rtn$var449; }
                                continue $label450;
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
    
    public static final byte[] $classHash = new byte[] { 79, 53, 111, -53, 36,
    95, -14, -30, -28, -44, 125, -15, 31, -78, -103, -64, -19, 61, -82, -53,
    -71, -23, 110, -72, -85, -14, -35, -89, 52, -124, -92, 44 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
