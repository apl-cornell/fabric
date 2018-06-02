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
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
                    super(store, onum, version, observers, labelStore,
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
        
        public static final byte[] $classHash = new byte[] { 93, -61, 38, 86,
        25, 43, -25, -80, 124, 16, -86, -50, 22, -93, 118, -86, 43, 98, 55, 34,
        -60, -24, 57, -17, -34, 46, -105, -84, 124, -20, -54, 124 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527964132000L;
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
                    fabric.worker.metrics.treaties.TreatyRef activatedTreaty =
                      new fabric.worker.metrics.treaties.TreatyRef(
                        (fabric.worker.metrics.treaties.MetricTreaty)
                          computed.treaty.get().update(
                                                  false,
                                                  computed.weakStats).first);
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
                    boolean rtn$var400 = rtn;
                    fabric.worker.transaction.TransactionManager $tm406 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled409 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff407 = 1;
                    boolean $doBackoff408 = true;
                    boolean $retry403 = true;
                    $label401: for (boolean $commit402 = false; !$commit402; ) {
                        if ($backoffEnabled409) {
                            if ($doBackoff408) {
                                if ($backoff407 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff407);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e404) {
                                            
                                        }
                                    }
                                }
                                if ($backoff407 < 5000) $backoff407 *= 2;
                            }
                            $doBackoff408 = $backoff407 <= 32 || !$doBackoff408;
                        }
                        $commit402 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e404) {
                            $commit402 = false;
                            continue $label401;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e404) {
                            $commit402 = false;
                            fabric.common.TransactionID $currentTid405 =
                              $tm406.getCurrentTid();
                            if ($e404.tid.isDescendantOf($currentTid405))
                                continue $label401;
                            if ($currentTid405.parent != null) {
                                $retry403 = false;
                                throw $e404;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e404) {
                            $commit402 = false;
                            if ($tm406.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid405 =
                              $tm406.getCurrentTid();
                            if ($e404.tid.isDescendantOf($currentTid405)) {
                                $retry403 = true;
                            }
                            else if ($currentTid405.parent != null) {
                                $retry403 = false;
                                throw $e404;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e404) {
                            $commit402 = false;
                            if ($tm406.checkForStaleObjects())
                                continue $label401;
                            $retry403 = false;
                            throw new fabric.worker.AbortException($e404);
                        }
                        finally {
                            if ($commit402) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e404) {
                                    $commit402 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e404) {
                                    $commit402 = false;
                                    fabric.common.TransactionID $currentTid405 =
                                      $tm406.getCurrentTid();
                                    if ($currentTid405 != null) {
                                        if ($e404.tid.equals($currentTid405) ||
                                              !$e404.tid.isDescendantOf(
                                                           $currentTid405)) {
                                            throw $e404;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit402 && $retry403) {
                                { rtn = rtn$var400; }
                                continue $label401;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private boolean dropOldValue() {
            fabric.worker.metrics.treaties.TreatyRef curTreaty =
              this.get$curTreaty();
            if (!fabric.lang.Object._Proxy.idEquals(curTreaty, null)) {
                if (fabric.lang.Object._Proxy.idEquals(curTreaty.get(), null) ||
                      !curTreaty.get().valid()) {
                    if (!fabric.lang.Object._Proxy.idEquals(curTreaty.get(),
                                                            null))
                        curTreaty.get().
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
                    fabric.worker.transaction.TransactionManager $tm415 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled418 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff416 = 1;
                    boolean $doBackoff417 = true;
                    boolean $retry412 = true;
                    $label410: for (boolean $commit411 = false; !$commit411; ) {
                        if ($backoffEnabled418) {
                            if ($doBackoff417) {
                                if ($backoff416 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff416);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e413) {
                                            
                                        }
                                    }
                                }
                                if ($backoff416 < 5000) $backoff416 *= 2;
                            }
                            $doBackoff417 = $backoff416 <= 32 || !$doBackoff417;
                        }
                        $commit411 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e413) {
                            $commit411 = false;
                            continue $label410;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e413) {
                            $commit411 = false;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414))
                                continue $label410;
                            if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414)) {
                                $retry412 = true;
                            }
                            else if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects())
                                continue $label410;
                            $retry412 = false;
                            throw new fabric.worker.AbortException($e413);
                        }
                        finally {
                            if ($commit411) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e413) {
                                    $commit411 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e413) {
                                    $commit411 = false;
                                    fabric.common.TransactionID $currentTid414 =
                                      $tm415.getCurrentTid();
                                    if ($currentTid414 != null) {
                                        if ($e413.tid.equals($currentTid414) ||
                                              !$e413.tid.isDescendantOf(
                                                           $currentTid414)) {
                                            throw $e413;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit411 && $retry412) {
                                {  }
                                continue $label410;
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
                    new fabric.worker.metrics.treaties.TreatyRef(
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
                    affected = affected.addAll(getObservers());
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
                      rtn$var419 = rtn;
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
                            if (tmp.$getStore().equals(proxyStore)) {
                                rtn = tmp;
                            }
                            else {
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
                                { rtn = rtn$var419; }
                                continue $label420;
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
            this.set$proxies(fabric.worker.metrics.proxies.ProxyMap.emptyMap());
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
            $writeInline(out, this.proxies);
            out.writeBoolean(this.recomputeOnInvalidation);
            out.writeBoolean(this.proactive);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.curVal = (fabric.lang.Object)
                            $readRef(fabric.lang.Object._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -35, -106, -27, -115,
    66, -87, -27, -5, -57, 2, -20, 117, 62, 113, 30, -108, 4, 112, -50, 20,
    -103, 76, 114, -127, -63, -109, 45, 125, -114, -110, -121, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527964132000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
