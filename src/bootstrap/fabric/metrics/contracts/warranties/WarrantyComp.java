package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyValue;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A computation that uses {@link Contract}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Contract}.
 * This helps to ensure that the {@link Contract} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.metrics.contracts.Contract get$curContract();
    
    public fabric.metrics.contracts.Contract set$curContract(
      fabric.metrics.contracts.Contract val);
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(fabric.worker.metrics.ImmutableSet val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
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
   * contract associated with it (guaranteed active when returned) that asserts
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
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time,
                                                     boolean autoRetry);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public boolean handleUpdates();
    
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
                fabric$metrics$contracts$warranties$WarrantyComp$();
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
        
        public static final byte[] $classHash = new byte[] { 34, 123, 2, 115,
        -95, 12, -4, 105, -31, -18, 14, 12, -38, -23, -45, -121, 32, 51, -22, 4,
        59, 64, 94, 115, 52, 60, -60, 124, -26, -3, 71, 30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526756059000L;
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
        
        public fabric.metrics.contracts.Contract get$curContract() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curContract();
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curContract(val);
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
        
        public boolean handleUpdates() {
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
        
        public fabric.metrics.contracts.Contract get$curContract() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curContract;
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curContract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.metrics.contracts.Contract curContract;
        
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
        
        /**
   * Create a new updated result paired with a contract that would enforce it
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
   * contract associated with it (guaranteed active when returned) that asserts
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
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
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
              ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                 tmp.fetch()).dropOldValue();
            while (loop) {
                i++;
                if (!fabric.metrics.contracts.warranties.WarrantyComp._Static._Proxy.$instance.
                      get$PROACTIVE()) {
                    for (java.util.Iterator iter = tmp.get$proxies().iterator();
                         iter.hasNext(); ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                        if (!((fabric.metrics.contracts.warranties.WarrantyComp.
                                _Impl) proxy.fetch()).dropOldValue()) {
                            fabric.lang.Object newVal = proxy.get$curVal();
                            fabric.metrics.contracts.Contract newContract =
                              proxy.get$curContract();
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal, newContract);
                            if (!((fabric.metrics.contracts.warranties.WarrantyComp.
                                    _Impl) tmp.fetch()).dropOldValue()) {
                                break;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(tmp.get$curContract(),
                                                       null)) {
                    fabric.worker.metrics.WarrantyValue computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.contract,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return computed;
                    }
                    java.lang.String contractStoreName =
                      computed.contract.$getStore().name();
                    if (!fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn() &&
                          !contractStoreName.
                          equals(fabric.worker.Worker.getWorkerName())) {
                        fabric.worker.remote.RemoteWorker w =
                          fabric.worker.Worker.getWorker().getWorker(
                                                             contractStoreName);
                        ((fabric.metrics.contracts.Contract._Proxy)
                           computed.contract).refresh$remote(
                                                w, null, false,
                                                computed.weakStats);
                    }
                    else {
                        computed.contract.refresh(false, computed.weakStats);
                    }
                    ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       tmp.fetch()).setNewValue(computed.value, computed.contract);
                    if (fabric.metrics.contracts.warranties.WarrantyComp._Static._Proxy.$instance.
                          get$PROACTIVE()) {
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
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) proxy.fetch()).setNewValue(
                                                       computed.value,
                                                       computed.contract);
                        }
                    }
                }
                loop =
                  autoRetry &&
                    ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       tmp.fetch()).dropOldValue();
                if (!loop)
                    fabric.worker.transaction.TransactionManager.getInstance().
                      stats.
                      addMsg("Memoized: true");
            }
            return fabric.worker.metrics.WarrantyValue.newValue(
                                                         tmp.get$curVal(),
                                                         tmp.get$curContract());
        }
        
        private boolean dropOldValue() {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_dropOldValue(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy());
        }
        
        private static boolean static_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            boolean rtn = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                rtn =
                  fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                    inner_dropOldValue(tmp);
            }
            else {
                {
                    boolean rtn$var558 = rtn;
                    fabric.worker.transaction.TransactionManager $tm564 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled567 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff565 = 1;
                    boolean $doBackoff566 = true;
                    boolean $retry561 = true;
                    $label559: for (boolean $commit560 = false; !$commit560; ) {
                        if ($backoffEnabled567) {
                            if ($doBackoff566) {
                                if ($backoff565 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff565);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e562) {
                                            
                                        }
                                    }
                                }
                                if ($backoff565 < 5000) $backoff565 *= 2;
                            }
                            $doBackoff566 = $backoff565 <= 32 || !$doBackoff566;
                        }
                        $commit560 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                inner_dropOldValue(tmp);
                        }
                        catch (final fabric.worker.RetryException $e562) {
                            $commit560 = false;
                            continue $label559;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e562) {
                            $commit560 = false;
                            fabric.common.TransactionID $currentTid563 =
                              $tm564.getCurrentTid();
                            if ($e562.tid.isDescendantOf($currentTid563))
                                continue $label559;
                            if ($currentTid563.parent != null) {
                                $retry561 = false;
                                throw $e562;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e562) {
                            $commit560 = false;
                            if ($tm564.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid563 =
                              $tm564.getCurrentTid();
                            if ($e562.tid.isDescendantOf($currentTid563)) {
                                $retry561 = true;
                            }
                            else if ($currentTid563.parent != null) {
                                $retry561 = false;
                                throw $e562;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e562) {
                            $commit560 = false;
                            if ($tm564.checkForStaleObjects())
                                continue $label559;
                            $retry561 = false;
                            throw new fabric.worker.AbortException($e562);
                        }
                        finally {
                            if ($commit560) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e562) {
                                    $commit560 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e562) {
                                    $commit560 = false;
                                    fabric.common.TransactionID $currentTid563 =
                                      $tm564.getCurrentTid();
                                    if ($currentTid563 != null) {
                                        if ($e562.tid.equals($currentTid563) ||
                                              !$e562.tid.isDescendantOf(
                                                           $currentTid563)) {
                                            throw $e562;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit560 && $retry561) {
                                { rtn = rtn$var558; }
                                continue $label559;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private static boolean inner_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            fabric.metrics.contracts.Contract curContract =
              tmp.get$curContract();
            if (!fabric.lang.Object._Proxy.idEquals(curContract, null) &&
                  !curContract.valid()) {
                curContract.removeObserver(tmp);
                tmp.set$curContract(null);
                tmp.set$$associated(null);
            }
            return fabric.lang.Object._Proxy.idEquals(tmp.get$curContract(),
                                                      null);
        }
        
        private void setNewValue(
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_setNewValue(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), newVal, newContract);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_setNewValue(tmp, newVal, newContract);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm573 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled576 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff574 = 1;
                    boolean $doBackoff575 = true;
                    boolean $retry570 = true;
                    $label568: for (boolean $commit569 = false; !$commit569; ) {
                        if ($backoffEnabled576) {
                            if ($doBackoff575) {
                                if ($backoff574 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff574);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e571) {
                                            
                                        }
                                    }
                                }
                                if ($backoff574 < 5000) $backoff574 *= 2;
                            }
                            $doBackoff575 = $backoff574 <= 32 || !$doBackoff575;
                        }
                        $commit569 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              inner_setNewValue(tmp, newVal, newContract);
                        }
                        catch (final fabric.worker.RetryException $e571) {
                            $commit569 = false;
                            continue $label568;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e571) {
                            $commit569 = false;
                            fabric.common.TransactionID $currentTid572 =
                              $tm573.getCurrentTid();
                            if ($e571.tid.isDescendantOf($currentTid572))
                                continue $label568;
                            if ($currentTid572.parent != null) {
                                $retry570 = false;
                                throw $e571;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e571) {
                            $commit569 = false;
                            if ($tm573.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid572 =
                              $tm573.getCurrentTid();
                            if ($e571.tid.isDescendantOf($currentTid572)) {
                                $retry570 = true;
                            }
                            else if ($currentTid572.parent != null) {
                                $retry570 = false;
                                throw $e571;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e571) {
                            $commit569 = false;
                            if ($tm573.checkForStaleObjects())
                                continue $label568;
                            $retry570 = false;
                            throw new fabric.worker.AbortException($e571);
                        }
                        finally {
                            if ($commit569) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e571) {
                                    $commit569 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e571) {
                                    $commit569 = false;
                                    fabric.common.TransactionID $currentTid572 =
                                      $tm573.getCurrentTid();
                                    if ($currentTid572 != null) {
                                        if ($e571.tid.equals($currentTid572) ||
                                              !$e571.tid.isDescendantOf(
                                                           $currentTid572)) {
                                            throw $e571;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit569 && $retry570) {
                                {  }
                                continue $label568;
                            }
                        }
                    }
                }
            }
        }
        
        private static void inner_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_dropOldValue(tmp) &&
                  !fabric.lang.Object._Proxy.idEquals(newContract, null) &&
                  newContract.valid()) {
                tmp.set$curVal(newVal);
                tmp.set$curContract(
                      newContract.getProxyContract(tmp.$getStore()));
                tmp.get$curContract().addObserver(tmp);
                tmp.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(tmp.get$curContract()));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curContract(),
                                                    null))
                return this.get$curContract().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
        }
        
        public boolean handleUpdates() {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue()) {
                apply(java.lang.System.currentTimeMillis(), false);
                return true;
            }
            return false;
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
                      rtn$var577 = rtn;
                    fabric.worker.transaction.TransactionManager $tm583 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled586 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff584 = 1;
                    boolean $doBackoff585 = true;
                    boolean $retry580 = true;
                    $label578: for (boolean $commit579 = false; !$commit579; ) {
                        if ($backoffEnabled586) {
                            if ($doBackoff585) {
                                if ($backoff584 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff584);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e581) {
                                            
                                        }
                                    }
                                }
                                if ($backoff584 < 5000) $backoff584 *= 2;
                            }
                            $doBackoff585 = $backoff584 <= 32 || !$doBackoff585;
                        }
                        $commit579 = true;
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
                        catch (final fabric.worker.RetryException $e581) {
                            $commit579 = false;
                            continue $label578;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e581) {
                            $commit579 = false;
                            fabric.common.TransactionID $currentTid582 =
                              $tm583.getCurrentTid();
                            if ($e581.tid.isDescendantOf($currentTid582))
                                continue $label578;
                            if ($currentTid582.parent != null) {
                                $retry580 = false;
                                throw $e581;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e581) {
                            $commit579 = false;
                            if ($tm583.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid582 =
                              $tm583.getCurrentTid();
                            if ($e581.tid.isDescendantOf($currentTid582)) {
                                $retry580 = true;
                            }
                            else if ($currentTid582.parent != null) {
                                $retry580 = false;
                                throw $e581;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e581) {
                            $commit579 = false;
                            if ($tm583.checkForStaleObjects())
                                continue $label578;
                            $retry580 = false;
                            throw new fabric.worker.AbortException($e581);
                        }
                        finally {
                            if ($commit579) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e581) {
                                    $commit579 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e581) {
                                    $commit579 = false;
                                    fabric.common.TransactionID $currentTid582 =
                                      $tm583.getCurrentTid();
                                    if ($currentTid582 != null) {
                                        if ($e581.tid.equals($currentTid582) ||
                                              !$e581.tid.isDescendantOf(
                                                           $currentTid582)) {
                                            throw $e581;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit579 && $retry580) {
                                { rtn = rtn$var577; }
                                continue $label578;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            this.set$proxies(fabric.worker.metrics.ImmutableSet.emptySet());
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
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
            $writeRef($getStore(), this.curContract, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeInline(out, this.proxies);
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
            this.curContract =
              (fabric.metrics.contracts.Contract)
                $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.curContract = src.curContract;
            this.proxies = src.proxies;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public boolean get$PROACTIVE();
        
        public boolean set$PROACTIVE(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public boolean get$PROACTIVE() {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          _Static._Impl) fetch()).get$PROACTIVE();
            }
            
            public boolean set$PROACTIVE(boolean val) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          _Static._Impl) fetch()).set$PROACTIVE(val);
            }
            
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
            public boolean get$PROACTIVE() { return this.PROACTIVE; }
            
            public boolean set$PROACTIVE(boolean val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.PROACTIVE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private boolean PROACTIVE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeBoolean(this.PROACTIVE);
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
                this.PROACTIVE = in.readBoolean();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm592 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled595 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff593 = 1;
                        boolean $doBackoff594 = true;
                        boolean $retry589 = true;
                        $label587: for (boolean $commit588 = false; !$commit588;
                                        ) {
                            if ($backoffEnabled595) {
                                if ($doBackoff594) {
                                    if ($backoff593 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff593);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e590) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff593 < 5000) $backoff593 *= 2;
                                }
                                $doBackoff594 = $backoff593 <= 32 ||
                                                  !$doBackoff594;
                            }
                            $commit588 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.warranties.WarrantyComp.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$PROACTIVE(true);
                            }
                            catch (final fabric.worker.RetryException $e590) {
                                $commit588 = false;
                                continue $label587;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e590) {
                                $commit588 = false;
                                fabric.common.TransactionID $currentTid591 =
                                  $tm592.getCurrentTid();
                                if ($e590.tid.isDescendantOf($currentTid591))
                                    continue $label587;
                                if ($currentTid591.parent != null) {
                                    $retry589 = false;
                                    throw $e590;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e590) {
                                $commit588 = false;
                                if ($tm592.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid591 =
                                  $tm592.getCurrentTid();
                                if ($e590.tid.isDescendantOf($currentTid591)) {
                                    $retry589 = true;
                                }
                                else if ($currentTid591.parent != null) {
                                    $retry589 = false;
                                    throw $e590;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e590) {
                                $commit588 = false;
                                if ($tm592.checkForStaleObjects())
                                    continue $label587;
                                $retry589 = false;
                                throw new fabric.worker.AbortException($e590);
                            }
                            finally {
                                if ($commit588) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e590) {
                                        $commit588 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e590) {
                                        $commit588 = false;
                                        fabric.common.TransactionID
                                          $currentTid591 =
                                          $tm592.getCurrentTid();
                                        if ($currentTid591 != null) {
                                            if ($e590.tid.equals(
                                                            $currentTid591) ||
                                                  !$e590.tid.
                                                  isDescendantOf(
                                                    $currentTid591)) {
                                                throw $e590;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit588 && $retry589) {
                                    {  }
                                    continue $label587;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 18, -6, 18, -69, -11,
    50, 101, 34, -77, -120, -29, -7, 85, -78, -22, -98, 11, -94, 23, 3, -89, 48,
    -60, -93, -19, -111, 58, 116, -79, -37, 85, -93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526756059000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
