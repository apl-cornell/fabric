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
        
        public static final byte[] $classHash = new byte[] { -37, -34, -105,
        -24, -52, -70, 52, 67, 3, -42, 119, 34, 74, -85, 13, 31, -49, 121, 88,
        -89, 124, 57, 68, 123, -99, 49, 79, 4, -100, -25, -108, 118 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527633043000L;
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
    
    public static final byte[] $classHash = new byte[] { -119, -57, -99, -101,
    -109, -104, -101, -68, 25, -35, 11, 81, 13, 99, -36, -22, 88, -113, -126,
    -61, 54, 29, -59, 61, -34, -92, 45, 24, 69, -22, 77, 7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527633043000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
