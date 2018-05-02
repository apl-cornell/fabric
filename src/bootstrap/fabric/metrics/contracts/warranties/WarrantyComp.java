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
        
        public static final byte[] $classHash = new byte[] { -112, -60, 60, 33,
        -52, 72, 75, -44, 37, -41, 127, -87, 46, -74, 77, 15, -85, -77, 23, -17,
        -101, -40, -48, 112, 95, -40, 103, 110, -60, -12, 111, 70 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525272765000L;
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
                           computed.contract).activate$remote(w, null);
                    }
                    else {
                        computed.contract.activate();
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
                    boolean rtn$var31 = rtn;
                    fabric.worker.transaction.TransactionManager $tm37 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled40 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff38 = 1;
                    boolean $doBackoff39 = true;
                    boolean $retry34 = true;
                    $label32: for (boolean $commit33 = false; !$commit33; ) {
                        if ($backoffEnabled40) {
                            if ($doBackoff39) {
                                if ($backoff38 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff38);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e35) {
                                            
                                        }
                                    }
                                }
                                if ($backoff38 < 5000) $backoff38 *= 2;
                            }
                            $doBackoff39 = $backoff38 <= 32 || !$doBackoff39;
                        }
                        $commit33 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                inner_dropOldValue(tmp);
                        }
                        catch (final fabric.worker.RetryException $e35) {
                            $commit33 = false;
                            continue $label32;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e35) {
                            $commit33 = false;
                            fabric.common.TransactionID $currentTid36 =
                              $tm37.getCurrentTid();
                            if ($e35.tid.isDescendantOf($currentTid36))
                                continue $label32;
                            if ($currentTid36.parent != null) {
                                $retry34 = false;
                                throw $e35;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e35) {
                            $commit33 = false;
                            if ($tm37.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid36 =
                              $tm37.getCurrentTid();
                            if ($e35.tid.isDescendantOf($currentTid36)) {
                                $retry34 = true;
                            }
                            else if ($currentTid36.parent != null) {
                                $retry34 = false;
                                throw $e35;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e35) {
                            $commit33 = false;
                            if ($tm37.checkForStaleObjects()) continue $label32;
                            $retry34 = false;
                            throw new fabric.worker.AbortException($e35);
                        }
                        finally {
                            if ($commit33) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e35) {
                                    $commit33 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e35) {
                                    $commit33 = false;
                                    fabric.common.TransactionID $currentTid36 =
                                      $tm37.getCurrentTid();
                                    if ($currentTid36 != null) {
                                        if ($e35.tid.equals($currentTid36) ||
                                              !$e35.tid.isDescendantOf(
                                                          $currentTid36)) {
                                            throw $e35;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit33 && $retry34) {
                                { rtn = rtn$var31; }
                                continue $label32;
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
                    fabric.worker.transaction.TransactionManager $tm46 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled49 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff47 = 1;
                    boolean $doBackoff48 = true;
                    boolean $retry43 = true;
                    $label41: for (boolean $commit42 = false; !$commit42; ) {
                        if ($backoffEnabled49) {
                            if ($doBackoff48) {
                                if ($backoff47 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff47);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e44) {
                                            
                                        }
                                    }
                                }
                                if ($backoff47 < 5000) $backoff47 *= 2;
                            }
                            $doBackoff48 = $backoff47 <= 32 || !$doBackoff48;
                        }
                        $commit42 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              inner_setNewValue(tmp, newVal, newContract);
                        }
                        catch (final fabric.worker.RetryException $e44) {
                            $commit42 = false;
                            continue $label41;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e44) {
                            $commit42 = false;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45))
                                continue $label41;
                            if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid45 =
                              $tm46.getCurrentTid();
                            if ($e44.tid.isDescendantOf($currentTid45)) {
                                $retry43 = true;
                            }
                            else if ($currentTid45.parent != null) {
                                $retry43 = false;
                                throw $e44;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e44) {
                            $commit42 = false;
                            if ($tm46.checkForStaleObjects()) continue $label41;
                            $retry43 = false;
                            throw new fabric.worker.AbortException($e44);
                        }
                        finally {
                            if ($commit42) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e44) {
                                    $commit42 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e44) {
                                    $commit42 = false;
                                    fabric.common.TransactionID $currentTid45 =
                                      $tm46.getCurrentTid();
                                    if ($currentTid45 != null) {
                                        if ($e44.tid.equals($currentTid45) ||
                                              !$e44.tid.isDescendantOf(
                                                          $currentTid45)) {
                                            throw $e44;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit42 && $retry43) {
                                {  }
                                continue $label41;
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
                    fabric.metrics.contracts.warranties.WarrantyComp rtn$var50 =
                      rtn;
                    fabric.worker.transaction.TransactionManager $tm56 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled59 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff57 = 1;
                    boolean $doBackoff58 = true;
                    boolean $retry53 = true;
                    $label51: for (boolean $commit52 = false; !$commit52; ) {
                        if ($backoffEnabled59) {
                            if ($doBackoff58) {
                                if ($backoff57 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff57);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e54) {
                                            
                                        }
                                    }
                                }
                                if ($backoff57 < 5000) $backoff57 *= 2;
                            }
                            $doBackoff58 = $backoff57 <= 32 || !$doBackoff58;
                        }
                        $commit52 = true;
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
                        catch (final fabric.worker.RetryException $e54) {
                            $commit52 = false;
                            continue $label51;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e54) {
                            $commit52 = false;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55))
                                continue $label51;
                            if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid55 =
                              $tm56.getCurrentTid();
                            if ($e54.tid.isDescendantOf($currentTid55)) {
                                $retry53 = true;
                            }
                            else if ($currentTid55.parent != null) {
                                $retry53 = false;
                                throw $e54;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e54) {
                            $commit52 = false;
                            if ($tm56.checkForStaleObjects()) continue $label51;
                            $retry53 = false;
                            throw new fabric.worker.AbortException($e54);
                        }
                        finally {
                            if ($commit52) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e54) {
                                    $commit52 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e54) {
                                    $commit52 = false;
                                    fabric.common.TransactionID $currentTid55 =
                                      $tm56.getCurrentTid();
                                    if ($currentTid55 != null) {
                                        if ($e54.tid.equals($currentTid55) ||
                                              !$e54.tid.isDescendantOf(
                                                          $currentTid55)) {
                                            throw $e54;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit52 && $retry53) {
                                { rtn = rtn$var50; }
                                continue $label51;
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
                        fabric.worker.transaction.TransactionManager $tm65 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled68 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff66 = 1;
                        boolean $doBackoff67 = true;
                        boolean $retry62 = true;
                        $label60: for (boolean $commit61 = false; !$commit61;
                                       ) {
                            if ($backoffEnabled68) {
                                if ($doBackoff67) {
                                    if ($backoff66 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff66);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e63) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff66 < 5000) $backoff66 *= 2;
                                }
                                $doBackoff67 = $backoff66 <= 32 ||
                                                 !$doBackoff67;
                            }
                            $commit61 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.warranties.WarrantyComp.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$PROACTIVE(true);
                            }
                            catch (final fabric.worker.RetryException $e63) {
                                $commit61 = false;
                                continue $label60;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e63) {
                                $commit61 = false;
                                fabric.common.TransactionID $currentTid64 =
                                  $tm65.getCurrentTid();
                                if ($e63.tid.isDescendantOf($currentTid64))
                                    continue $label60;
                                if ($currentTid64.parent != null) {
                                    $retry62 = false;
                                    throw $e63;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e63) {
                                $commit61 = false;
                                if ($tm65.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid64 =
                                  $tm65.getCurrentTid();
                                if ($e63.tid.isDescendantOf($currentTid64)) {
                                    $retry62 = true;
                                }
                                else if ($currentTid64.parent != null) {
                                    $retry62 = false;
                                    throw $e63;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e63) {
                                $commit61 = false;
                                if ($tm65.checkForStaleObjects())
                                    continue $label60;
                                $retry62 = false;
                                throw new fabric.worker.AbortException($e63);
                            }
                            finally {
                                if ($commit61) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e63) {
                                        $commit61 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e63) {
                                        $commit61 = false;
                                        fabric.common.TransactionID
                                          $currentTid64 = $tm65.getCurrentTid();
                                        if ($currentTid64 != null) {
                                            if ($e63.tid.equals(
                                                           $currentTid64) ||
                                                  !$e63.tid.isDescendantOf(
                                                              $currentTid64)) {
                                                throw $e63;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit61 && $retry62) {
                                    {  }
                                    continue $label60;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -25, -29, 23, -23, 127,
    9, -36, -77, 44, -85, -43, 20, 4, 109, -46, 123, -31, 119, 55, -25, -17, -6,
    -118, -75, 83, -78, -14, -125, 19, -25, -25, 3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525272765000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
