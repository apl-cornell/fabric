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
        
        public static final byte[] $classHash = new byte[] { -98, 48, -61, 80,
        18, 18, -97, -109, 24, 121, -117, 79, -82, 80, -40, 65, 20, -60, -87,
        -104, 20, -47, -60, -115, 27, 85, 81, -4, 20, 127, 18, 57 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556306458000L;
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
                    boolean rtn$var408 = rtn;
                    fabric.worker.transaction.TransactionManager $tm415 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled418 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff416 = 1;
                    boolean $doBackoff417 = true;
                    boolean $retry411 = true;
                    boolean $keepReads412 = false;
                    $label409: for (boolean $commit410 = false; !$commit410; ) {
                        if ($backoffEnabled418) {
                            if ($doBackoff417) {
                                if ($backoff416 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff416));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e413) {
                                            
                                        }
                                    }
                                }
                                if ($backoff416 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff416 =
                                      java.lang.Math.
                                        min(
                                          $backoff416 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff417 = $backoff416 <= 32 || !$doBackoff417;
                        }
                        $commit410 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e413) {
                            $commit410 = false;
                            continue $label409;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e413) {
                            $commit410 = false;
                            $retry411 = false;
                            $keepReads412 = $e413.keepReads;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid == null ||
                                  !$e413.tid.isDescendantOf($currentTid414)) {
                                throw $e413;
                            }
                            throw new fabric.worker.UserAbortException($e413);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e413) {
                            $commit410 = false;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414))
                                continue $label409;
                            if ($currentTid414.parent != null) {
                                $retry411 = false;
                                throw $e413;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e413) {
                            $commit410 = false;
                            $retry411 = false;
                            if ($tm415.inNestedTxn()) { $keepReads412 = true; }
                            throw $e413;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($commit410) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e413) {
                                    $commit410 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e413) {
                                    $commit410 = false;
                                    $retry411 = false;
                                    $keepReads412 = $e413.keepReads;
                                    if ($e413.tid ==
                                          null ||
                                          !$e413.tid.isDescendantOf(
                                                       $currentTid414))
                                        throw $e413;
                                    throw new fabric.worker.UserAbortException(
                                            $e413);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e413) {
                                    $commit410 = false;
                                    $currentTid414 = $tm415.getCurrentTid();
                                    if ($currentTid414 != null) {
                                        if ($e413.tid.equals($currentTid414) ||
                                              !$e413.tid.isDescendantOf(
                                                           $currentTid414)) {
                                            throw $e413;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm415.inNestedTxn() &&
                                      $tm415.checkForStaleObjects()) {
                                    $retry411 = true;
                                    $keepReads412 = false;
                                }
                                if ($keepReads412) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e413) {
                                        $currentTid414 = $tm415.getCurrentTid();
                                        if ($currentTid414 != null &&
                                              ($e413.tid.equals($currentTid414) || !$e413.tid.isDescendantOf($currentTid414))) {
                                            throw $e413;
                                        } else {
                                            $retry411 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit410) {
                                { rtn = rtn$var408; }
                                if ($retry411) { continue $label409; }
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
                    fabric.worker.transaction.TransactionManager $tm425 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled428 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff426 = 1;
                    boolean $doBackoff427 = true;
                    boolean $retry421 = true;
                    boolean $keepReads422 = false;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled428) {
                            if ($doBackoff427) {
                                if ($backoff426 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff426));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e423) {
                                            
                                        }
                                    }
                                }
                                if ($backoff426 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff426 =
                                      java.lang.Math.
                                        min(
                                          $backoff426 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff427 = $backoff426 <= 32 || !$doBackoff427;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e423) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e423) {
                            $commit420 = false;
                            $retry421 = false;
                            $keepReads422 = $e423.keepReads;
                            fabric.common.TransactionID $currentTid424 =
                              $tm425.getCurrentTid();
                            if ($e423.tid == null ||
                                  !$e423.tid.isDescendantOf($currentTid424)) {
                                throw $e423;
                            }
                            throw new fabric.worker.UserAbortException($e423);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e423) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid424 =
                              $tm425.getCurrentTid();
                            if ($e423.tid.isDescendantOf($currentTid424))
                                continue $label419;
                            if ($currentTid424.parent != null) {
                                $retry421 = false;
                                throw $e423;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e423) {
                            $commit420 = false;
                            $retry421 = false;
                            if ($tm425.inNestedTxn()) { $keepReads422 = true; }
                            throw $e423;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid424 =
                              $tm425.getCurrentTid();
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e423) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e423) {
                                    $commit420 = false;
                                    $retry421 = false;
                                    $keepReads422 = $e423.keepReads;
                                    if ($e423.tid ==
                                          null ||
                                          !$e423.tid.isDescendantOf(
                                                       $currentTid424))
                                        throw $e423;
                                    throw new fabric.worker.UserAbortException(
                                            $e423);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e423) {
                                    $commit420 = false;
                                    $currentTid424 = $tm425.getCurrentTid();
                                    if ($currentTid424 != null) {
                                        if ($e423.tid.equals($currentTid424) ||
                                              !$e423.tid.isDescendantOf(
                                                           $currentTid424)) {
                                            throw $e423;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm425.inNestedTxn() &&
                                      $tm425.checkForStaleObjects()) {
                                    $retry421 = true;
                                    $keepReads422 = false;
                                }
                                if ($keepReads422) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e423) {
                                        $currentTid424 = $tm425.getCurrentTid();
                                        if ($currentTid424 != null &&
                                              ($e423.tid.equals($currentTid424) || !$e423.tid.isDescendantOf($currentTid424))) {
                                            throw $e423;
                                        } else {
                                            $retry421 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit420) {
                                {  }
                                if ($retry421) { continue $label419; }
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
                      rtn$var429 = rtn;
                    fabric.worker.transaction.TransactionManager $tm436 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled439 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff437 = 1;
                    boolean $doBackoff438 = true;
                    boolean $retry432 = true;
                    boolean $keepReads433 = false;
                    $label430: for (boolean $commit431 = false; !$commit431; ) {
                        if ($backoffEnabled439) {
                            if ($doBackoff438) {
                                if ($backoff437 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff437));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e434) {
                                            
                                        }
                                    }
                                }
                                if ($backoff437 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff437 =
                                      java.lang.Math.
                                        min(
                                          $backoff437 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff438 = $backoff437 <= 32 || !$doBackoff438;
                        }
                        $commit431 = true;
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
                        catch (final fabric.worker.RetryException $e434) {
                            $commit431 = false;
                            continue $label430;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e434) {
                            $commit431 = false;
                            $retry432 = false;
                            $keepReads433 = $e434.keepReads;
                            fabric.common.TransactionID $currentTid435 =
                              $tm436.getCurrentTid();
                            if ($e434.tid == null ||
                                  !$e434.tid.isDescendantOf($currentTid435)) {
                                throw $e434;
                            }
                            throw new fabric.worker.UserAbortException($e434);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e434) {
                            $commit431 = false;
                            fabric.common.TransactionID $currentTid435 =
                              $tm436.getCurrentTid();
                            if ($e434.tid.isDescendantOf($currentTid435))
                                continue $label430;
                            if ($currentTid435.parent != null) {
                                $retry432 = false;
                                throw $e434;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e434) {
                            $commit431 = false;
                            $retry432 = false;
                            if ($tm436.inNestedTxn()) { $keepReads433 = true; }
                            throw $e434;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid435 =
                              $tm436.getCurrentTid();
                            if ($commit431) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e434) {
                                    $commit431 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e434) {
                                    $commit431 = false;
                                    $retry432 = false;
                                    $keepReads433 = $e434.keepReads;
                                    if ($e434.tid ==
                                          null ||
                                          !$e434.tid.isDescendantOf(
                                                       $currentTid435))
                                        throw $e434;
                                    throw new fabric.worker.UserAbortException(
                                            $e434);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e434) {
                                    $commit431 = false;
                                    $currentTid435 = $tm436.getCurrentTid();
                                    if ($currentTid435 != null) {
                                        if ($e434.tid.equals($currentTid435) ||
                                              !$e434.tid.isDescendantOf(
                                                           $currentTid435)) {
                                            throw $e434;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm436.inNestedTxn() &&
                                      $tm436.checkForStaleObjects()) {
                                    $retry432 = true;
                                    $keepReads433 = false;
                                }
                                if ($keepReads433) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e434) {
                                        $currentTid435 = $tm436.getCurrentTid();
                                        if ($currentTid435 != null &&
                                              ($e434.tid.equals($currentTid435) || !$e434.tid.isDescendantOf($currentTid435))) {
                                            throw $e434;
                                        } else {
                                            $retry432 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit431) {
                                { rtn = rtn$var429; }
                                if ($retry432) { continue $label430; }
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
    
    public static final byte[] $classHash = new byte[] { 115, 117, -119, -9,
    -54, -102, -30, -38, 53, 99, 71, 51, 107, -92, -47, -85, 21, -31, 3, 93, 31,
    -127, 105, -105, -127, 47, 111, -29, -122, -127, -128, -120 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556306458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
