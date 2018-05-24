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
        
        public static final byte[] $classHash = new byte[] { -46, -73, -8, 10,
        123, 102, -126, -31, -107, -111, 18, 82, 108, 1, -60, -93, -75, 32, 123,
        -34, 104, 6, -10, 69, -34, -6, -61, 77, 92, 27, 6, -65 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527194716000L;
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
            if (!fabric.lang.Object._Proxy.idEquals(curTreaty, null) &&
                  !curTreaty.get().valid()) {
                curTreaty.get().
                  removeObserver(
                    (fabric.metrics.contracts.warranties.WarrantyComp)
                      this.$getProxy());
                this.set$curTreaty(null);
                this.set$$associated(null);
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
                  newTreaty.get().valid()) {
                this.set$curVal(newVal);
                this.
                  set$curTreaty(
                    new fabric.worker.metrics.treaties.TreatyRef(
                      this.get$curTreaty().get().
                          addObserver(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(), null))
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
    
    public static final byte[] $classHash = new byte[] { -80, -24, 42, -22, 1,
    124, 5, -9, -121, -41, -103, 72, -104, 8, 68, 25, -32, 103, 43, 4, -42, 62,
    78, 38, 2, -10, -90, -43, 93, 22, 41, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527194716000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
