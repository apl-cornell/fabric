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
        
        public static final byte[] $classHash = new byte[] { 92, 77, 81, -58,
        -67, -123, 72, -59, 63, -84, -23, -109, 47, -44, 89, 114, -112, 88, 123,
        87, -90, 35, -90, 26, 87, -126, -88, 88, 59, 103, 125, -118 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1549748453000L;
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
                    boolean rtn$var421 = rtn;
                    fabric.worker.transaction.TransactionManager $tm428 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled431 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff429 = 1;
                    boolean $doBackoff430 = true;
                    boolean $retry424 = true;
                    boolean $keepReads425 = false;
                    $label422: for (boolean $commit423 = false; !$commit423; ) {
                        if ($backoffEnabled431) {
                            if ($doBackoff430) {
                                if ($backoff429 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff429));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e426) {
                                            
                                        }
                                    }
                                }
                                if ($backoff429 < 5000) $backoff429 *= 2;
                            }
                            $doBackoff430 = $backoff429 <= 32 || !$doBackoff430;
                        }
                        $commit423 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                rtn =
                                  ((fabric.metrics.contracts.warranties.WarrantyComp.
                                     _Impl) tmp.fetch()).dropOldValue();
                            }
                            catch (final fabric.worker.RetryException $e426) {
                                throw $e426;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e426) {
                                throw $e426;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e426) {
                                throw $e426;
                            }
                            catch (final Throwable $e426) {
                                $tm428.getCurrentLog().checkRetrySignal();
                                throw $e426;
                            }
                        }
                        catch (final fabric.worker.RetryException $e426) {
                            $commit423 = false;
                            continue $label422;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e426) {
                            $commit423 = false;
                            $retry424 = false;
                            $keepReads425 = $e426.keepReads;
                            if ($tm428.checkForStaleObjects()) {
                                $retry424 = true;
                                $keepReads425 = false;
                                continue $label422;
                            }
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid == null ||
                                  !$e426.tid.isDescendantOf($currentTid427)) {
                                throw $e426;
                            }
                            throw new fabric.worker.UserAbortException($e426);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e426) {
                            $commit423 = false;
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid.isDescendantOf($currentTid427))
                                continue $label422;
                            if ($currentTid427.parent != null) {
                                $retry424 = false;
                                throw $e426;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e426) {
                            $commit423 = false;
                            if ($tm428.checkForStaleObjects())
                                continue $label422;
                            $retry424 = false;
                            throw new fabric.worker.AbortException($e426);
                        }
                        finally {
                            if ($commit423) {
                                fabric.common.TransactionID $currentTid427 =
                                  $tm428.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e426) {
                                    $commit423 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e426) {
                                    $commit423 = false;
                                    $retry424 = false;
                                    $keepReads425 = $e426.keepReads;
                                    if ($tm428.checkForStaleObjects()) {
                                        $retry424 = true;
                                        $keepReads425 = false;
                                        continue $label422;
                                    }
                                    if ($e426.tid ==
                                          null ||
                                          !$e426.tid.isDescendantOf(
                                                       $currentTid427))
                                        throw $e426;
                                    throw new fabric.worker.UserAbortException(
                                            $e426);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e426) {
                                    $commit423 = false;
                                    $currentTid427 = $tm428.getCurrentTid();
                                    if ($currentTid427 != null) {
                                        if ($e426.tid.equals($currentTid427) ||
                                              !$e426.tid.isDescendantOf(
                                                           $currentTid427)) {
                                            throw $e426;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads425) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit423) {
                                { rtn = rtn$var421; }
                                if ($retry424) { continue $label422; }
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
                    fabric.worker.transaction.TransactionManager $tm438 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled441 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff439 = 1;
                    boolean $doBackoff440 = true;
                    boolean $retry434 = true;
                    boolean $keepReads435 = false;
                    $label432: for (boolean $commit433 = false; !$commit433; ) {
                        if ($backoffEnabled441) {
                            if ($doBackoff440) {
                                if ($backoff439 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff439));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e436) {
                                            
                                        }
                                    }
                                }
                                if ($backoff439 < 5000) $backoff439 *= 2;
                            }
                            $doBackoff440 = $backoff439 <= 32 || !$doBackoff440;
                        }
                        $commit433 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                ((fabric.metrics.contracts.warranties.WarrantyComp.
                                   _Impl) tmp.fetch()).setNewValue(newVal,
                                                                   newTreaty);
                            }
                            catch (final fabric.worker.RetryException $e436) {
                                throw $e436;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e436) {
                                throw $e436;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e436) {
                                throw $e436;
                            }
                            catch (final Throwable $e436) {
                                $tm438.getCurrentLog().checkRetrySignal();
                                throw $e436;
                            }
                        }
                        catch (final fabric.worker.RetryException $e436) {
                            $commit433 = false;
                            continue $label432;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e436) {
                            $commit433 = false;
                            $retry434 = false;
                            $keepReads435 = $e436.keepReads;
                            if ($tm438.checkForStaleObjects()) {
                                $retry434 = true;
                                $keepReads435 = false;
                                continue $label432;
                            }
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid == null ||
                                  !$e436.tid.isDescendantOf($currentTid437)) {
                                throw $e436;
                            }
                            throw new fabric.worker.UserAbortException($e436);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e436) {
                            $commit433 = false;
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid.isDescendantOf($currentTid437))
                                continue $label432;
                            if ($currentTid437.parent != null) {
                                $retry434 = false;
                                throw $e436;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e436) {
                            $commit433 = false;
                            if ($tm438.checkForStaleObjects())
                                continue $label432;
                            $retry434 = false;
                            throw new fabric.worker.AbortException($e436);
                        }
                        finally {
                            if ($commit433) {
                                fabric.common.TransactionID $currentTid437 =
                                  $tm438.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e436) {
                                    $commit433 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e436) {
                                    $commit433 = false;
                                    $retry434 = false;
                                    $keepReads435 = $e436.keepReads;
                                    if ($tm438.checkForStaleObjects()) {
                                        $retry434 = true;
                                        $keepReads435 = false;
                                        continue $label432;
                                    }
                                    if ($e436.tid ==
                                          null ||
                                          !$e436.tid.isDescendantOf(
                                                       $currentTid437))
                                        throw $e436;
                                    throw new fabric.worker.UserAbortException(
                                            $e436);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e436) {
                                    $commit433 = false;
                                    $currentTid437 = $tm438.getCurrentTid();
                                    if ($currentTid437 != null) {
                                        if ($e436.tid.equals($currentTid437) ||
                                              !$e436.tid.isDescendantOf(
                                                           $currentTid437)) {
                                            throw $e436;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads435) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit433) {
                                {  }
                                if ($retry434) { continue $label432; }
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
                      rtn$var442 = rtn;
                    fabric.worker.transaction.TransactionManager $tm449 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled452 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff450 = 1;
                    boolean $doBackoff451 = true;
                    boolean $retry445 = true;
                    boolean $keepReads446 = false;
                    $label443: for (boolean $commit444 = false; !$commit444; ) {
                        if ($backoffEnabled452) {
                            if ($doBackoff451) {
                                if ($backoff450 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff450));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e447) {
                                            
                                        }
                                    }
                                }
                                if ($backoff450 < 5000) $backoff450 *= 2;
                            }
                            $doBackoff451 = $backoff450 <= 32 || !$doBackoff451;
                        }
                        $commit444 = true;
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
                            catch (final fabric.worker.RetryException $e447) {
                                throw $e447;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e447) {
                                throw $e447;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e447) {
                                throw $e447;
                            }
                            catch (final Throwable $e447) {
                                $tm449.getCurrentLog().checkRetrySignal();
                                throw $e447;
                            }
                        }
                        catch (final fabric.worker.RetryException $e447) {
                            $commit444 = false;
                            continue $label443;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e447) {
                            $commit444 = false;
                            $retry445 = false;
                            $keepReads446 = $e447.keepReads;
                            if ($tm449.checkForStaleObjects()) {
                                $retry445 = true;
                                $keepReads446 = false;
                                continue $label443;
                            }
                            fabric.common.TransactionID $currentTid448 =
                              $tm449.getCurrentTid();
                            if ($e447.tid == null ||
                                  !$e447.tid.isDescendantOf($currentTid448)) {
                                throw $e447;
                            }
                            throw new fabric.worker.UserAbortException($e447);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e447) {
                            $commit444 = false;
                            fabric.common.TransactionID $currentTid448 =
                              $tm449.getCurrentTid();
                            if ($e447.tid.isDescendantOf($currentTid448))
                                continue $label443;
                            if ($currentTid448.parent != null) {
                                $retry445 = false;
                                throw $e447;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e447) {
                            $commit444 = false;
                            if ($tm449.checkForStaleObjects())
                                continue $label443;
                            $retry445 = false;
                            throw new fabric.worker.AbortException($e447);
                        }
                        finally {
                            if ($commit444) {
                                fabric.common.TransactionID $currentTid448 =
                                  $tm449.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e447) {
                                    $commit444 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e447) {
                                    $commit444 = false;
                                    $retry445 = false;
                                    $keepReads446 = $e447.keepReads;
                                    if ($tm449.checkForStaleObjects()) {
                                        $retry445 = true;
                                        $keepReads446 = false;
                                        continue $label443;
                                    }
                                    if ($e447.tid ==
                                          null ||
                                          !$e447.tid.isDescendantOf(
                                                       $currentTid448))
                                        throw $e447;
                                    throw new fabric.worker.UserAbortException(
                                            $e447);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e447) {
                                    $commit444 = false;
                                    $currentTid448 = $tm449.getCurrentTid();
                                    if ($currentTid448 != null) {
                                        if ($e447.tid.equals($currentTid448) ||
                                              !$e447.tid.isDescendantOf(
                                                           $currentTid448)) {
                                            throw $e447;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads446) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit444) {
                                { rtn = rtn$var442; }
                                if ($retry445) { continue $label443; }
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
    
    public static final byte[] $classHash = new byte[] { -34, -49, -117, -111,
    37, -97, 66, 8, -20, 44, 22, 57, -77, -3, -14, 57, 73, -102, 34, -82, 97,
    -22, -88, 94, 28, -121, 58, -30, -117, -10, -94, -62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
