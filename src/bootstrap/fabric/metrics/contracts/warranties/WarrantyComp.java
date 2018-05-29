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
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyValue;
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
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(
      fabric.worker.metrics.ImmutableSet val);
    
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
        
        public static final byte[] $classHash = new byte[] { -10, 94, 75, 36,
        -14, 19, -119, 41, -6, 101, -78, 24, -41, 13, 127, -123, -106, -62, -19,
        -90, -83, -112, -14, -64, -36, 18, 17, 119, 91, -13, 101, 124 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527606629000L;
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
                    this.set$$associated(null);
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
                  newTreaty.get().valid()) {
                this.set$curVal(newVal);
                this.
                  set$curTreaty(
                    new fabric.worker.metrics.treaties.TreatyRef(
                      newTreaty.get().getProxyTreaty(this.$getStore()).
                          addObserver(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$curTreaty().get().getMetric()));
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
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
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
    
    public static final byte[] $classHash = new byte[] { 124, -116, 5, -25, -78,
    -91, 28, 11, 111, 99, 83, -67, -45, 71, 125, -36, 3, 45, 29, -11, -87, 101,
    -55, -81, -51, 118, -76, 27, 82, -18, 42, 79 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527606629000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
