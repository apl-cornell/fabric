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
        
        public static final byte[] $classHash = new byte[] { 101, -13, -47, -75,
        99, 5, -98, -4, 55, -90, 18, -121, -99, 27, -18, -65, -59, -121, 17,
        112, -79, 94, -36, -16, 96, -82, -59, -79, 45, -104, -27, 13 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527874708000L;
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
                    boolean rtn$var372 = rtn;
                    fabric.worker.transaction.TransactionManager $tm378 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled381 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff379 = 1;
                    boolean $doBackoff380 = true;
                    boolean $retry375 = true;
                    $label373: for (boolean $commit374 = false; !$commit374; ) {
                        if ($backoffEnabled381) {
                            if ($doBackoff380) {
                                if ($backoff379 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff379);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e376) {
                                            
                                        }
                                    }
                                }
                                if ($backoff379 < 5000) $backoff379 *= 2;
                            }
                            $doBackoff380 = $backoff379 <= 32 || !$doBackoff380;
                        }
                        $commit374 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e376) {
                            $commit374 = false;
                            continue $label373;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e376) {
                            $commit374 = false;
                            fabric.common.TransactionID $currentTid377 =
                              $tm378.getCurrentTid();
                            if ($e376.tid.isDescendantOf($currentTid377))
                                continue $label373;
                            if ($currentTid377.parent != null) {
                                $retry375 = false;
                                throw $e376;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e376) {
                            $commit374 = false;
                            if ($tm378.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid377 =
                              $tm378.getCurrentTid();
                            if ($e376.tid.isDescendantOf($currentTid377)) {
                                $retry375 = true;
                            }
                            else if ($currentTid377.parent != null) {
                                $retry375 = false;
                                throw $e376;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e376) {
                            $commit374 = false;
                            if ($tm378.checkForStaleObjects())
                                continue $label373;
                            $retry375 = false;
                            throw new fabric.worker.AbortException($e376);
                        }
                        finally {
                            if ($commit374) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e376) {
                                    $commit374 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e376) {
                                    $commit374 = false;
                                    fabric.common.TransactionID $currentTid377 =
                                      $tm378.getCurrentTid();
                                    if ($currentTid377 != null) {
                                        if ($e376.tid.equals($currentTid377) ||
                                              !$e376.tid.isDescendantOf(
                                                           $currentTid377)) {
                                            throw $e376;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit374 && $retry375) {
                                { rtn = rtn$var372; }
                                continue $label373;
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
                    fabric.worker.transaction.TransactionManager $tm387 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled390 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff388 = 1;
                    boolean $doBackoff389 = true;
                    boolean $retry384 = true;
                    $label382: for (boolean $commit383 = false; !$commit383; ) {
                        if ($backoffEnabled390) {
                            if ($doBackoff389) {
                                if ($backoff388 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff388);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e385) {
                                            
                                        }
                                    }
                                }
                                if ($backoff388 < 5000) $backoff388 *= 2;
                            }
                            $doBackoff389 = $backoff388 <= 32 || !$doBackoff389;
                        }
                        $commit383 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e385) {
                            $commit383 = false;
                            continue $label382;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e385) {
                            $commit383 = false;
                            fabric.common.TransactionID $currentTid386 =
                              $tm387.getCurrentTid();
                            if ($e385.tid.isDescendantOf($currentTid386))
                                continue $label382;
                            if ($currentTid386.parent != null) {
                                $retry384 = false;
                                throw $e385;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e385) {
                            $commit383 = false;
                            if ($tm387.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid386 =
                              $tm387.getCurrentTid();
                            if ($e385.tid.isDescendantOf($currentTid386)) {
                                $retry384 = true;
                            }
                            else if ($currentTid386.parent != null) {
                                $retry384 = false;
                                throw $e385;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e385) {
                            $commit383 = false;
                            if ($tm387.checkForStaleObjects())
                                continue $label382;
                            $retry384 = false;
                            throw new fabric.worker.AbortException($e385);
                        }
                        finally {
                            if ($commit383) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e385) {
                                    $commit383 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e385) {
                                    $commit383 = false;
                                    fabric.common.TransactionID $currentTid386 =
                                      $tm387.getCurrentTid();
                                    if ($currentTid386 != null) {
                                        if ($e385.tid.equals($currentTid386) ||
                                              !$e385.tid.isDescendantOf(
                                                           $currentTid386)) {
                                            throw $e385;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit383 && $retry384) {
                                {  }
                                continue $label382;
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
                      rtn$var391 = rtn;
                    fabric.worker.transaction.TransactionManager $tm397 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled400 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff398 = 1;
                    boolean $doBackoff399 = true;
                    boolean $retry394 = true;
                    $label392: for (boolean $commit393 = false; !$commit393; ) {
                        if ($backoffEnabled400) {
                            if ($doBackoff399) {
                                if ($backoff398 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff398);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e395) {
                                            
                                        }
                                    }
                                }
                                if ($backoff398 < 5000) $backoff398 *= 2;
                            }
                            $doBackoff399 = $backoff398 <= 32 || !$doBackoff399;
                        }
                        $commit393 = true;
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
                        catch (final fabric.worker.RetryException $e395) {
                            $commit393 = false;
                            continue $label392;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e395) {
                            $commit393 = false;
                            fabric.common.TransactionID $currentTid396 =
                              $tm397.getCurrentTid();
                            if ($e395.tid.isDescendantOf($currentTid396))
                                continue $label392;
                            if ($currentTid396.parent != null) {
                                $retry394 = false;
                                throw $e395;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e395) {
                            $commit393 = false;
                            if ($tm397.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid396 =
                              $tm397.getCurrentTid();
                            if ($e395.tid.isDescendantOf($currentTid396)) {
                                $retry394 = true;
                            }
                            else if ($currentTid396.parent != null) {
                                $retry394 = false;
                                throw $e395;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e395) {
                            $commit393 = false;
                            if ($tm397.checkForStaleObjects())
                                continue $label392;
                            $retry394 = false;
                            throw new fabric.worker.AbortException($e395);
                        }
                        finally {
                            if ($commit393) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e395) {
                                    $commit393 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e395) {
                                    $commit393 = false;
                                    fabric.common.TransactionID $currentTid396 =
                                      $tm397.getCurrentTid();
                                    if ($currentTid396 != null) {
                                        if ($e395.tid.equals($currentTid396) ||
                                              !$e395.tid.isDescendantOf(
                                                           $currentTid396)) {
                                            throw $e395;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit393 && $retry394) {
                                { rtn = rtn$var391; }
                                continue $label392;
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
    
    public static final byte[] $classHash = new byte[] { -32, 28, -118, -73,
    -49, -37, -15, -52, 4, -80, -103, 126, 113, -82, -62, 41, 121, -67, 66, 74,
    11, 73, -23, 19, 4, 46, 40, -85, -84, -43, 66, -2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
