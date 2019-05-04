package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyProposal;
import fabric.worker.metrics.WarrantyValue;
import fabric.worker.metrics.proxies.ProxyMap;
import fabric.worker.transaction.TransactionManager;
import java.util.Iterator;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A computation that uses {@link Treaty}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Treaty}.
 * This helps to ensure that the {@link Treaty} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.lang.Object {
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.metrics.treaties.Treaty get$curTreaty();
    
    public fabric.metrics.treaties.Treaty set$curTreaty(
      fabric.metrics.treaties.Treaty val);
    
    public fabric.worker.metrics.proxies.ProxyMap get$proxies();
    
    public fabric.worker.metrics.proxies.ProxyMap set$proxies(
      fabric.worker.metrics.proxies.ProxyMap val);
    
    public boolean get$recomputeOnInvalidation();
    
    public boolean set$recomputeOnInvalidation(boolean val);
    
    public boolean get$proactive();
    
    public boolean set$proactive(boolean val);
    
    /**
   * Create a new updated proposed result (including a proposed {@link Metric}
   * and {@link TreatyStatement}).
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.worker.metrics.WarrantyProposal updatedVal(long time);
    
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
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
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
        
        public fabric.worker.metrics.WarrantyProposal updatedVal(long time);
        
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
            
            public fabric.worker.metrics.WarrantyProposal updatedVal(
              long time) {
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
        
        public static final byte[] $classHash = new byte[] { 103, -28, -96, 68,
        -60, 64, -84, 23, 93, 85, 108, 10, 0, 124, -119, -62, -64, 28, 53, -25,
        68, -76, 16, -72, -5, -10, 8, -25, -2, 94, 90, -24 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556640403000L;
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
        
        public fabric.metrics.treaties.Treaty get$curTreaty() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curTreaty();
        }
        
        public fabric.metrics.treaties.Treaty set$curTreaty(
          fabric.metrics.treaties.Treaty val) {
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
        
        public fabric.worker.metrics.WarrantyProposal updatedVal(long arg1) {
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
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
        
        public fabric.metrics.treaties.Treaty get$curTreaty() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curTreaty;
        }
        
        public fabric.metrics.treaties.Treaty set$curTreaty(
          fabric.metrics.treaties.Treaty val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curTreaty = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.metrics.treaties.Treaty curTreaty;
        
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
   * Create a new updated proposed result (including a proposed {@link Metric}
   * and {@link TreatyStatement}).
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.worker.metrics.WarrantyProposal updatedVal(long time);
        
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
                            fabric.metrics.treaties.Treaty newTreaty =
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
                    fabric.worker.metrics.WarrantyProposal computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.metric,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return fabric.worker.metrics.WarrantyValue.
                          newValue(computed.value, null);
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
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().
                      getWorker(computed.metric.$getStore().name());
                    fabric.metrics.treaties.Treaty activatedTreaty = null;
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn()) {
                        activatedTreaty =
                          computed.metric.createTreaty(computed.predicate,
                                                       computed.weakStats);
                    }
                    else {
                        activatedTreaty =
                          ((fabric.metrics.Metric._Proxy) computed.metric).
                            createTreaty$remote(w, null, computed.predicate,
                                                computed.weakStats);
                    }
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
                    boolean rtn$var377 = rtn;
                    fabric.worker.transaction.TransactionManager $tm384 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled387 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff385 = 1;
                    boolean $doBackoff386 = true;
                    boolean $retry380 = true;
                    boolean $keepReads381 = false;
                    $label378: for (boolean $commit379 = false; !$commit379; ) {
                        if ($backoffEnabled387) {
                            if ($doBackoff386) {
                                if ($backoff385 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff385));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e382) {
                                            
                                        }
                                    }
                                }
                                if ($backoff385 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff385 =
                                      java.lang.Math.
                                        min(
                                          $backoff385 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff386 = $backoff385 <= 32 || !$doBackoff386;
                        }
                        $commit379 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e382) {
                            $commit379 = false;
                            continue $label378;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e382) {
                            $commit379 = false;
                            $retry380 = false;
                            $keepReads381 = $e382.keepReads;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid == null ||
                                  !$e382.tid.isDescendantOf($currentTid383)) {
                                throw $e382;
                            }
                            throw new fabric.worker.UserAbortException($e382);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e382) {
                            $commit379 = false;
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($e382.tid.isDescendantOf($currentTid383))
                                continue $label378;
                            if ($currentTid383.parent != null) {
                                $retry380 = false;
                                throw $e382;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e382) {
                            $commit379 = false;
                            $retry380 = false;
                            if ($tm384.inNestedTxn()) { $keepReads381 = true; }
                            throw $e382;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid383 =
                              $tm384.getCurrentTid();
                            if ($commit379) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e382) {
                                    $commit379 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e382) {
                                    $commit379 = false;
                                    $retry380 = false;
                                    $keepReads381 = $e382.keepReads;
                                    if ($e382.tid ==
                                          null ||
                                          !$e382.tid.isDescendantOf(
                                                       $currentTid383))
                                        throw $e382;
                                    throw new fabric.worker.UserAbortException(
                                            $e382);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e382) {
                                    $commit379 = false;
                                    $currentTid383 = $tm384.getCurrentTid();
                                    if ($currentTid383 != null) {
                                        if ($e382.tid.equals($currentTid383) ||
                                              !$e382.tid.isDescendantOf(
                                                           $currentTid383)) {
                                            throw $e382;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm384.inNestedTxn() &&
                                      $tm384.checkForStaleObjects()) {
                                    $retry380 = true;
                                    $keepReads381 = false;
                                }
                                if ($keepReads381) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e382) {
                                        $currentTid383 = $tm384.getCurrentTid();
                                        if ($currentTid383 != null &&
                                              ($e382.tid.equals($currentTid383) || !$e382.tid.isDescendantOf($currentTid383))) {
                                            throw $e382;
                                        } else {
                                            $retry380 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit379) {
                                { rtn = rtn$var377; }
                                if ($retry380) { continue $label378; }
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
                if (fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(),
                                                       null) ||
                      !this.get$curTreaty().valid()) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     this.get$curTreaty(),
                                                     null))
                        this.get$curTreaty().
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
          fabric.lang.Object newVal, fabric.metrics.treaties.Treaty newTreaty) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   tmp.fetch()).setNewValue(newVal, newTreaty);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm394 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled397 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff395 = 1;
                    boolean $doBackoff396 = true;
                    boolean $retry390 = true;
                    boolean $keepReads391 = false;
                    $label388: for (boolean $commit389 = false; !$commit389; ) {
                        if ($backoffEnabled397) {
                            if ($doBackoff396) {
                                if ($backoff395 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff395));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e392) {
                                            
                                        }
                                    }
                                }
                                if ($backoff395 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff395 =
                                      java.lang.Math.
                                        min(
                                          $backoff395 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff396 = $backoff395 <= 32 || !$doBackoff396;
                        }
                        $commit389 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e392) {
                            $commit389 = false;
                            continue $label388;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e392) {
                            $commit389 = false;
                            $retry390 = false;
                            $keepReads391 = $e392.keepReads;
                            fabric.common.TransactionID $currentTid393 =
                              $tm394.getCurrentTid();
                            if ($e392.tid == null ||
                                  !$e392.tid.isDescendantOf($currentTid393)) {
                                throw $e392;
                            }
                            throw new fabric.worker.UserAbortException($e392);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e392) {
                            $commit389 = false;
                            fabric.common.TransactionID $currentTid393 =
                              $tm394.getCurrentTid();
                            if ($e392.tid.isDescendantOf($currentTid393))
                                continue $label388;
                            if ($currentTid393.parent != null) {
                                $retry390 = false;
                                throw $e392;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e392) {
                            $commit389 = false;
                            $retry390 = false;
                            if ($tm394.inNestedTxn()) { $keepReads391 = true; }
                            throw $e392;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid393 =
                              $tm394.getCurrentTid();
                            if ($commit389) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e392) {
                                    $commit389 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e392) {
                                    $commit389 = false;
                                    $retry390 = false;
                                    $keepReads391 = $e392.keepReads;
                                    if ($e392.tid ==
                                          null ||
                                          !$e392.tid.isDescendantOf(
                                                       $currentTid393))
                                        throw $e392;
                                    throw new fabric.worker.UserAbortException(
                                            $e392);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e392) {
                                    $commit389 = false;
                                    $currentTid393 = $tm394.getCurrentTid();
                                    if ($currentTid393 != null) {
                                        if ($e392.tid.equals($currentTid393) ||
                                              !$e392.tid.isDescendantOf(
                                                           $currentTid393)) {
                                            throw $e392;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm394.inNestedTxn() &&
                                      $tm394.checkForStaleObjects()) {
                                    $retry390 = true;
                                    $keepReads391 = false;
                                }
                                if ($keepReads391) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e392) {
                                        $currentTid393 = $tm394.getCurrentTid();
                                        if ($currentTid393 != null &&
                                              ($e392.tid.equals($currentTid393) || !$e392.tid.isDescendantOf($currentTid393))) {
                                            throw $e392;
                                        } else {
                                            $retry390 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit389) {
                                {  }
                                if ($retry390) { continue $label388; }
                            }
                        }
                    }
                }
            }
        }
        
        private void setNewValue(fabric.lang.Object newVal,
                                 fabric.metrics.treaties.Treaty newTreaty) {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue() &&
                  !fabric.lang.Object._Proxy.idEquals(newTreaty, null) &&
                  newTreaty.valid()) {
                this.set$curVal(newVal);
                this.set$curTreaty(newTreaty.getProxy(this.$getStore()));
                this.get$curTreaty().
                  addObserver((fabric.metrics.contracts.warranties.WarrantyComp)
                                this.$getProxy());
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(), null))
                return this.get$curTreaty().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue()) {
                if (this.get$recomputeOnInvalidation())
                    apply(java.lang.System.currentTimeMillis(), false);
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
                      rtn$var398 = rtn;
                    fabric.worker.transaction.TransactionManager $tm405 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled408 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff406 = 1;
                    boolean $doBackoff407 = true;
                    boolean $retry401 = true;
                    boolean $keepReads402 = false;
                    $label399: for (boolean $commit400 = false; !$commit400; ) {
                        if ($backoffEnabled408) {
                            if ($doBackoff407) {
                                if ($backoff406 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff406));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e403) {
                                            
                                        }
                                    }
                                }
                                if ($backoff406 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff406 =
                                      java.lang.Math.
                                        min(
                                          $backoff406 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff407 = $backoff406 <= 32 || !$doBackoff407;
                        }
                        $commit400 = true;
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
                        catch (final fabric.worker.RetryException $e403) {
                            $commit400 = false;
                            continue $label399;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e403) {
                            $commit400 = false;
                            $retry401 = false;
                            $keepReads402 = $e403.keepReads;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid == null ||
                                  !$e403.tid.isDescendantOf($currentTid404)) {
                                throw $e403;
                            }
                            throw new fabric.worker.UserAbortException($e403);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e403) {
                            $commit400 = false;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid.isDescendantOf($currentTid404))
                                continue $label399;
                            if ($currentTid404.parent != null) {
                                $retry401 = false;
                                throw $e403;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e403) {
                            $commit400 = false;
                            $retry401 = false;
                            if ($tm405.inNestedTxn()) { $keepReads402 = true; }
                            throw $e403;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($commit400) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e403) {
                                    $commit400 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e403) {
                                    $commit400 = false;
                                    $retry401 = false;
                                    $keepReads402 = $e403.keepReads;
                                    if ($e403.tid ==
                                          null ||
                                          !$e403.tid.isDescendantOf(
                                                       $currentTid404))
                                        throw $e403;
                                    throw new fabric.worker.UserAbortException(
                                            $e403);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e403) {
                                    $commit400 = false;
                                    $currentTid404 = $tm405.getCurrentTid();
                                    if ($currentTid404 != null) {
                                        if ($e403.tid.equals($currentTid404) ||
                                              !$e403.tid.isDescendantOf(
                                                           $currentTid404)) {
                                            throw $e403;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm405.inNestedTxn() &&
                                      $tm405.checkForStaleObjects()) {
                                    $retry401 = true;
                                    $keepReads402 = false;
                                }
                                if ($keepReads402) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e403) {
                                        $currentTid404 = $tm405.getCurrentTid();
                                        if ($currentTid404 != null &&
                                              ($e403.tid.equals($currentTid404) || !$e403.tid.isDescendantOf($currentTid404))) {
                                            throw $e403;
                                        } else {
                                            $retry401 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit400) {
                                { rtn = rtn$var398; }
                                if ($retry401) { continue $label399; }
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
            $writeRef($getStore(), this.curTreaty, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.curTreaty =
              (fabric.metrics.treaties.Treaty)
                $readRef(fabric.metrics.treaties.Treaty._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
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
    
    public static final byte[] $classHash = new byte[] { 110, -26, 61, 82, 30,
    61, -68, 40, -21, 74, -81, 100, 44, 30, -63, 33, -111, -126, -7, 88, 6, 55,
    25, -45, -66, 39, 94, 123, 55, 45, 43, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
