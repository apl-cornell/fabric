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
        
        public static final byte[] $classHash = new byte[] { -1, -30, -11, 113,
        3, -42, 66, 15, 9, 104, 31, -61, -96, -64, 40, 102, -120, 48, 122, -59,
        -24, -55, -9, -28, 75, -9, 1, -98, 38, -48, -90, 81 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1528498818000L;
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
                    fabric.worker.metrics.treaties.TreatyRef activatedTreaty =
                      fabric.worker.metrics.treaties.TreatyRef.
                      createRef(
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
                    boolean rtn$var0 = rtn;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { rtn = rtn$var0; }
                                continue $label1;
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
                    fabric.worker.transaction.TransactionManager $tm15 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled18 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff16 = 1;
                    boolean $doBackoff17 = true;
                    boolean $retry12 = true;
                    $label10: for (boolean $commit11 = false; !$commit11; ) {
                        if ($backoffEnabled18) {
                            if ($doBackoff17) {
                                if ($backoff16 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff16);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e13) {
                                            
                                        }
                                    }
                                }
                                if ($backoff16 < 5000) $backoff16 *= 2;
                            }
                            $doBackoff17 = $backoff16 <= 32 || !$doBackoff17;
                        }
                        $commit11 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e13) {
                            $commit11 = false;
                            continue $label10;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e13) {
                            $commit11 = false;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14))
                                continue $label10;
                            if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14)) {
                                $retry12 = true;
                            }
                            else if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue $label10;
                            $retry12 = false;
                            throw new fabric.worker.AbortException($e13);
                        }
                        finally {
                            if ($commit11) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e13) {
                                    $commit11 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e13) {
                                    $commit11 = false;
                                    fabric.common.TransactionID $currentTid14 =
                                      $tm15.getCurrentTid();
                                    if ($currentTid14 != null) {
                                        if ($e13.tid.equals($currentTid14) ||
                                              !$e13.tid.isDescendantOf(
                                                          $currentTid14)) {
                                            throw $e13;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit11 && $retry12) {
                                {  }
                                continue $label10;
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
                    fabric.metrics.contracts.warranties.WarrantyComp rtn$var19 =
                      rtn;
                    fabric.worker.transaction.TransactionManager $tm25 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled28 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff26 = 1;
                    boolean $doBackoff27 = true;
                    boolean $retry22 = true;
                    $label20: for (boolean $commit21 = false; !$commit21; ) {
                        if ($backoffEnabled28) {
                            if ($doBackoff27) {
                                if ($backoff26 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff26);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e23) {
                                            
                                        }
                                    }
                                }
                                if ($backoff26 < 5000) $backoff26 *= 2;
                            }
                            $doBackoff27 = $backoff26 <= 32 || !$doBackoff27;
                        }
                        $commit21 = true;
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
                        catch (final fabric.worker.RetryException $e23) {
                            $commit21 = false;
                            continue $label20;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e23) {
                            $commit21 = false;
                            fabric.common.TransactionID $currentTid24 =
                              $tm25.getCurrentTid();
                            if ($e23.tid.isDescendantOf($currentTid24))
                                continue $label20;
                            if ($currentTid24.parent != null) {
                                $retry22 = false;
                                throw $e23;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e23) {
                            $commit21 = false;
                            if ($tm25.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid24 =
                              $tm25.getCurrentTid();
                            if ($e23.tid.isDescendantOf($currentTid24)) {
                                $retry22 = true;
                            }
                            else if ($currentTid24.parent != null) {
                                $retry22 = false;
                                throw $e23;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e23) {
                            $commit21 = false;
                            if ($tm25.checkForStaleObjects()) continue $label20;
                            $retry22 = false;
                            throw new fabric.worker.AbortException($e23);
                        }
                        finally {
                            if ($commit21) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e23) {
                                    $commit21 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e23) {
                                    $commit21 = false;
                                    fabric.common.TransactionID $currentTid24 =
                                      $tm25.getCurrentTid();
                                    if ($currentTid24 != null) {
                                        if ($e23.tid.equals($currentTid24) ||
                                              !$e23.tid.isDescendantOf(
                                                          $currentTid24)) {
                                            throw $e23;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit21 && $retry22) {
                                { rtn = rtn$var19; }
                                continue $label20;
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
    
    public static final byte[] $classHash = new byte[] { 46, -1, 40, -33, -39,
    -4, 36, -24, -93, -5, -47, 31, -95, 101, -12, 86, -113, -69, 17, -82, -79,
    -40, -45, -96, 112, 18, -108, 53, 55, 94, 62, -53 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528498818000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
