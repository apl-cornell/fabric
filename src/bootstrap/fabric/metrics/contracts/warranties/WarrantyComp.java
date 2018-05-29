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
        
        public static final byte[] $classHash = new byte[] { -4, -36, -82, -109,
        118, -8, -42, -24, 34, -49, 45, -80, -52, 45, -8, 12, -96, 38, 9, 84,
        -70, -76, 67, 75, 59, 95, 18, 40, 123, -45, -3, -45 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527629406000L;
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
                                                               newTreaty);
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
    
    public static final byte[] $classHash = new byte[] { 99, -92, -69, 62, -122,
    -58, 101, -20, 94, 51, 104, -60, -75, 77, -76, -53, 4, -17, -109, -28, 30,
    102, -24, 85, 28, 41, 22, -66, -4, -27, 13, 69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629406000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZDWwcxRV+d3Zsn2PHjvOL4ziJY5ImhDuS0B/iEjVxE2K4ECtO+HFELnt7c/bivd1ld86+UBIBooTSKkIlBFCbSFWDaCGAhBpQS9PSllJQaKsiBK3aUioRQRUQRfyUtqH0vZm5u/X6bmNX1PLO25ud9+Z9729mdo+/DdM8F7qyWtow43yvw7z4Zi3dl+zXXI9lek3N83Zgb0qfXtt3+M0HM51RiCahSdcs2zJ0zUxZHocZyeu1US1hMZ7Yub2vZxfEdGLconnDHKK7NhZcWOzY5t4h0+Zqkgny77kgceje3a2P10DLILQY1gDXuKH32hZnBT4ITTmWSzPX25DJsMwgzLQYywww19BM40YcaFuD0OYZQ5bG8y7ztjPPNkdpYJuXd5gr5ix2kvo2qu3mdW67qH6rVD/PDTORNDzek4S6rMHMjHcD7IfaJEzLmtoQDpybLKJICImJzdSPwxsNVNPNajorstSOGFaGw6IgRwlx9xU4AFnrc4wP26Wpai0NO6BNqmRq1lBigLuGNYRDp9l5nIVDe1WhOKjB0fQRbYilOMwPjuuXj3BUTJiFWDjMCQ4TktBn7QGf+bz19pVfPPgVa4sVhQjqnGG6Sfo3IFNngGk7yzKXWTqTjE0rk4e1uSfviALg4DmBwXLMkze9+6VVnU8/J8csqDBmW/p6pvOUfiw943cdvSsuqSE1GhzbMygUxiEXXu1XT3oKDkb73JJEehgvPnx6+7PX3vwQOxOFxj6o020zn8OomqnbOccwmXsZs5ircZbpgxizMr3ieR/U433SsJjs3ZbNeoz3Qa0puups8RtNlEURZKJ6vDesrF28dzQ+LO4LDgC04gUR/GcAn23G+8UAtZxDKjFs51gibebZGIZ3Ai+mufpwAvPWNfSE5+oJN29xAwepLowiJF4CQ527ms69xJjmuhqOQf6r5e3eXsQWR9Wc//8UBULZOhaJoAMW6XaGpTUPvakia2O/icmzxTYzzE3p5sGTfTDr5P0iumKUER5GtbBfBCOiI1hL/LyH8hs3vfto6pSMTOJV5uVwkdQ7rvSOl/SOl/WO+/VGVZsoD+NY2eJY2Y5HCvHeo30Pi3Cr80RelqQ3ofR1jqnxrO3mChCJCKizBb+IM4ySEaw+WGCaVgxcd/meO7pqMMCdsVryOQ7tDqZbuUj14Z2GOZTSWw68+eFjh/fZ5cTj0D2hHkzkpHzuCtrNtXWWwXpZFr9ysXYidXJfd5RqUYwMpGEgY83pDM4xLq97ijWSrDEtCdPJBppJj4qFrZEPu/ZYuUfEwwxq2mRokLECCoryeumAc+T3v/nbWrHwFCtxi69kDzDe48t+EtYi8nxm2fY7XMZw3J/v67/7nrcP7BKGxxFLK03YTS25X8N0t92vPnfDH/7y6rGXomVncahz8mnT0AsCy8xP8C+C13/oohSmDqJYyHtV+Vhcqh8OzbysrBtWEhOrGarude+0cnbGyBpa2mQUKWdbzl994q2DrdLdJvZI47mw6twCyv3nbYSbT+3+R6cQE9FpJSvbrzxMlsdZZckbMBf2kh6FW15ceP+vtCMY+VjcPONGJuoVCHuAcOAaYYsLRbs68OxiarqktTpEP+06gkvFZlpzy7E4mDj+7fbe9WdkFSjFIslYUqEKXKX50mTNQ7kPol11v4xC/SC0iuUek/oqDasbhsEgLther+pMQvO45+MXX7nS9JRyrSOYB75pg1lQrj54T6PpvlEGvgwcNEQbyDIPS9Eo8xVtpKezHGpnFyIgbtYJlqWiXUbNimIwxhzX5qglyxRKYqMkdroSVyNp7b98YjGG9byLiAXLHEStqiPBjkvY4tF5wdIms5Xaz5WmayqiWIbT7VE0WQHFJomCmksnKktcVyi6cZyyMVQWk1jje4v6fkbpO2a7I8wtFXVOg6iQy9FYpoooYoTCtHHTWh3CWrxWAtQ1STrtdAUIV4ZCIK7XFf3jOAj16KYCalYE0FUZQF8ul+eUvhiok3eACKM1eF2E2j+l6LcqaH915TCK0u16NJJRnF5McwGHeS6jLRBuPbdZfdYobrgzYsNdIYH7XSOHNXhU7fXYHYfu/CR+8JAsXnJDvHTCntTPIzfFYuZmMX0BZ1kSNovg2PzGY/ue+t6+A3LD2DZ+e7fJyuceefnjF+L3vfZ8hU1Bfdq2TaZZVY3ajdcXABoWKdpcwaiZMKNSs6toTcpV3HKg9tSRErMWQrhXcmjQ0p7Yp5SzW/y1qP2hp2jGp5iv0kaK8dYR2P4I62xLe8wdlVW1nay9sNqmX1j62K2Hjma2PbA6qgq7hoi47VxoslFm+ial1XzJhEPlVnHUKVfp184svKR35PSQ9NuiwMzB0d/fevz5y5bp34xCTakcTzhfjWfqGV+EG12Gx0Nrx7hSvLhk1TkgXQwbAGILJW3w10xf5oX4eiRQhWcrSf9U9I2gn8prZsTnsetUAhDZw3Hxt60hocC+kIX2VmrGODTmnQztNXz1fWnlclPc74q1j4YmBQMvYYiR8OV4bQVoPKHovZO0SkTEcMAgDUrIYUXvOqdB6Od+Mc83QsAfpOZ2DtM0xzH30o/bKmFZh9dugOblkjZ9WAULNV+bqDmxfKDoO9U1j5ajYr9IdSH6nhD1hU3vClNfpP4uvNKo/o8UdSYboGIFMkYxLDgdXuj1SsAxrUqkrahWHV6NkFkj5gtg/E4Ixu9Scz8uaHL+VDjU9Xg9BLBgiaTt708lF49Qc7QCQpL0nqKnqyP0hV6rmOzhEFyPUPMAh1kKV8a1nW1mppRUqQC8RpBFG54A6GiTdMHZqSTVkQCymBLyb0Xfr47Mr/iJkGdPUvMYOutcaISzMB/g5wALf6yo/ak4iyRZiu6ZfDhuoOZyMe1PQxD+jJof4t5Xuc1j/Eo2JnBWrMGjtpGp5EkK1BcAOp9Q9NaQkvL4RL8Ryy2K3jipkuLDdyoE36+peZbDdB8w6vpFpbr4ZbxewagcVbQnBESFukgs6xS9eHLB91LIs5ep+S2e9IcYTzItO5CXR5LicrbqHLvnrbLjKiZe8hLThH10JSNcjderAEtuUjQ+NSMQy4WKLp+UJ1NFQO0KEG62c7alXhnhmo/HABqRFLP/NcRiopb9iUPzsGZlTLZT7ABK9lp5DnsVN4JVTx0Ba4njEq7e8BHA+dcqOj/EWhXOSsQyT9EZk7LWbUU4s8bDGUAns8p6Cz3+HmI3sf6fwUjLaSOsH89oeGj08qaw+oZKMfJ58Y4WlvUqOmdKMSJYZisagtq3/rwrpH4cguETaj7CzXgJQ1mPQJ2+BoUi47IXFf36p1GnhaQ7Fb1pUq5sLSGL1FdHFolRE+GolSzRoQBFUO5HNbAgJ2xJ4x9PKSgFy1lF35tcCotGaNsagqSNmukcVsvQ7VYp2F16Dd1dfg3d7X8N3V0V6gjquRnP+g8qumVqUInlMkXXTyoSJcrzQlAuoGb2p4oyiZNfDrD6GkWnVmUEyzxFQ/LND6Ir5BnpGVn4PwMsYH3y99Ib1wUVvoeor3h67zPs2OkrVs2p8i1k/oTvqorv0aMtDfOO7nxFvMkvfaGLJaEhmzdN/5tJ332d47KsIWwak+8pHYF5Rfn4GPbxBM+d5R9kvMhyyb+Kw/xq/Fy+2xX3fp4Ehxnjebj4WEp3/nFr8DQjx9GvtcK37YGmuGoEQciX3Or1itphCAYBvj3v0tfr4+/N+6iuYcdr4gMARsFi/dhT629/lr21e+3wMye2/uBU7Tt3v96ZfXNnx4q5Pzl7unnTfwFHmBMLVR8AAA==";
}
