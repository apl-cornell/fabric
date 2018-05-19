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
        
        public static final byte[] $classHash = new byte[] { 81, 45, 1, 34,
        -119, 37, -40, 87, 100, 14, 46, 107, 120, -6, -96, 80, -79, -56, -52,
        31, 72, -68, -51, -20, -27, 10, -66, -10, -85, 107, 98, 61 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526753800000L;
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
                           computed.contract).refresh$remote(w, null, false);
                    }
                    else {
                        computed.contract.refresh(false);
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
    
    public static final byte[] $classHash = new byte[] { 2, -51, -21, -106, 30,
    116, -78, -128, -106, 15, -59, -93, 63, 96, -127, -49, 66, 109, -103, -85,
    43, -87, -59, 93, 116, -118, 1, 57, -15, 118, 30, -68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753800000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
