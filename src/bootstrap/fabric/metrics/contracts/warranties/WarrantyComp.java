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
        public static final long jlc$SourceLastModified$fabil = 1525215695000L;
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
                    boolean rtn$var640 = rtn;
                    fabric.worker.transaction.TransactionManager $tm646 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled649 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff647 = 1;
                    boolean $doBackoff648 = true;
                    boolean $retry643 = true;
                    $label641: for (boolean $commit642 = false; !$commit642; ) {
                        if ($backoffEnabled649) {
                            if ($doBackoff648) {
                                if ($backoff647 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff647);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e644) {
                                            
                                        }
                                    }
                                }
                                if ($backoff647 < 5000) $backoff647 *= 2;
                            }
                            $doBackoff648 = $backoff647 <= 32 || !$doBackoff648;
                        }
                        $commit642 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                inner_dropOldValue(tmp);
                        }
                        catch (final fabric.worker.RetryException $e644) {
                            $commit642 = false;
                            continue $label641;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e644) {
                            $commit642 = false;
                            fabric.common.TransactionID $currentTid645 =
                              $tm646.getCurrentTid();
                            if ($e644.tid.isDescendantOf($currentTid645))
                                continue $label641;
                            if ($currentTid645.parent != null) {
                                $retry643 = false;
                                throw $e644;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e644) {
                            $commit642 = false;
                            if ($tm646.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid645 =
                              $tm646.getCurrentTid();
                            if ($e644.tid.isDescendantOf($currentTid645)) {
                                $retry643 = true;
                            }
                            else if ($currentTid645.parent != null) {
                                $retry643 = false;
                                throw $e644;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e644) {
                            $commit642 = false;
                            if ($tm646.checkForStaleObjects())
                                continue $label641;
                            $retry643 = false;
                            throw new fabric.worker.AbortException($e644);
                        }
                        finally {
                            if ($commit642) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e644) {
                                    $commit642 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e644) {
                                    $commit642 = false;
                                    fabric.common.TransactionID $currentTid645 =
                                      $tm646.getCurrentTid();
                                    if ($currentTid645 != null) {
                                        if ($e644.tid.equals($currentTid645) ||
                                              !$e644.tid.isDescendantOf(
                                                           $currentTid645)) {
                                            throw $e644;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit642 && $retry643) {
                                { rtn = rtn$var640; }
                                continue $label641;
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
                    fabric.worker.transaction.TransactionManager $tm655 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled658 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff656 = 1;
                    boolean $doBackoff657 = true;
                    boolean $retry652 = true;
                    $label650: for (boolean $commit651 = false; !$commit651; ) {
                        if ($backoffEnabled658) {
                            if ($doBackoff657) {
                                if ($backoff656 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff656);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e653) {
                                            
                                        }
                                    }
                                }
                                if ($backoff656 < 5000) $backoff656 *= 2;
                            }
                            $doBackoff657 = $backoff656 <= 32 || !$doBackoff657;
                        }
                        $commit651 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              inner_setNewValue(tmp, newVal, newContract);
                        }
                        catch (final fabric.worker.RetryException $e653) {
                            $commit651 = false;
                            continue $label650;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e653) {
                            $commit651 = false;
                            fabric.common.TransactionID $currentTid654 =
                              $tm655.getCurrentTid();
                            if ($e653.tid.isDescendantOf($currentTid654))
                                continue $label650;
                            if ($currentTid654.parent != null) {
                                $retry652 = false;
                                throw $e653;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e653) {
                            $commit651 = false;
                            if ($tm655.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid654 =
                              $tm655.getCurrentTid();
                            if ($e653.tid.isDescendantOf($currentTid654)) {
                                $retry652 = true;
                            }
                            else if ($currentTid654.parent != null) {
                                $retry652 = false;
                                throw $e653;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e653) {
                            $commit651 = false;
                            if ($tm655.checkForStaleObjects())
                                continue $label650;
                            $retry652 = false;
                            throw new fabric.worker.AbortException($e653);
                        }
                        finally {
                            if ($commit651) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e653) {
                                    $commit651 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e653) {
                                    $commit651 = false;
                                    fabric.common.TransactionID $currentTid654 =
                                      $tm655.getCurrentTid();
                                    if ($currentTid654 != null) {
                                        if ($e653.tid.equals($currentTid654) ||
                                              !$e653.tid.isDescendantOf(
                                                           $currentTid654)) {
                                            throw $e653;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit651 && $retry652) {
                                {  }
                                continue $label650;
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
                    fabric.metrics.contracts.warranties.WarrantyComp
                      rtn$var659 = rtn;
                    fabric.worker.transaction.TransactionManager $tm665 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled668 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff666 = 1;
                    boolean $doBackoff667 = true;
                    boolean $retry662 = true;
                    $label660: for (boolean $commit661 = false; !$commit661; ) {
                        if ($backoffEnabled668) {
                            if ($doBackoff667) {
                                if ($backoff666 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff666);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e663) {
                                            
                                        }
                                    }
                                }
                                if ($backoff666 < 5000) $backoff666 *= 2;
                            }
                            $doBackoff667 = $backoff666 <= 32 || !$doBackoff667;
                        }
                        $commit661 = true;
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
                        catch (final fabric.worker.RetryException $e663) {
                            $commit661 = false;
                            continue $label660;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e663) {
                            $commit661 = false;
                            fabric.common.TransactionID $currentTid664 =
                              $tm665.getCurrentTid();
                            if ($e663.tid.isDescendantOf($currentTid664))
                                continue $label660;
                            if ($currentTid664.parent != null) {
                                $retry662 = false;
                                throw $e663;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e663) {
                            $commit661 = false;
                            if ($tm665.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid664 =
                              $tm665.getCurrentTid();
                            if ($e663.tid.isDescendantOf($currentTid664)) {
                                $retry662 = true;
                            }
                            else if ($currentTid664.parent != null) {
                                $retry662 = false;
                                throw $e663;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e663) {
                            $commit661 = false;
                            if ($tm665.checkForStaleObjects())
                                continue $label660;
                            $retry662 = false;
                            throw new fabric.worker.AbortException($e663);
                        }
                        finally {
                            if ($commit661) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e663) {
                                    $commit661 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e663) {
                                    $commit661 = false;
                                    fabric.common.TransactionID $currentTid664 =
                                      $tm665.getCurrentTid();
                                    if ($currentTid664 != null) {
                                        if ($e663.tid.equals($currentTid664) ||
                                              !$e663.tid.isDescendantOf(
                                                           $currentTid664)) {
                                            throw $e663;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit661 && $retry662) {
                                { rtn = rtn$var659; }
                                continue $label660;
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
                        fabric.worker.transaction.TransactionManager $tm674 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled677 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff675 = 1;
                        boolean $doBackoff676 = true;
                        boolean $retry671 = true;
                        $label669: for (boolean $commit670 = false; !$commit670;
                                        ) {
                            if ($backoffEnabled677) {
                                if ($doBackoff676) {
                                    if ($backoff675 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff675);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e672) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff675 < 5000) $backoff675 *= 2;
                                }
                                $doBackoff676 = $backoff675 <= 32 ||
                                                  !$doBackoff676;
                            }
                            $commit670 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.warranties.WarrantyComp.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$PROACTIVE(true);
                            }
                            catch (final fabric.worker.RetryException $e672) {
                                $commit670 = false;
                                continue $label669;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e672) {
                                $commit670 = false;
                                fabric.common.TransactionID $currentTid673 =
                                  $tm674.getCurrentTid();
                                if ($e672.tid.isDescendantOf($currentTid673))
                                    continue $label669;
                                if ($currentTid673.parent != null) {
                                    $retry671 = false;
                                    throw $e672;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e672) {
                                $commit670 = false;
                                if ($tm674.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid673 =
                                  $tm674.getCurrentTid();
                                if ($e672.tid.isDescendantOf($currentTid673)) {
                                    $retry671 = true;
                                }
                                else if ($currentTid673.parent != null) {
                                    $retry671 = false;
                                    throw $e672;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e672) {
                                $commit670 = false;
                                if ($tm674.checkForStaleObjects())
                                    continue $label669;
                                $retry671 = false;
                                throw new fabric.worker.AbortException($e672);
                            }
                            finally {
                                if ($commit670) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e672) {
                                        $commit670 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e672) {
                                        $commit670 = false;
                                        fabric.common.TransactionID
                                          $currentTid673 =
                                          $tm674.getCurrentTid();
                                        if ($currentTid673 != null) {
                                            if ($e672.tid.equals(
                                                            $currentTid673) ||
                                                  !$e672.tid.
                                                  isDescendantOf(
                                                    $currentTid673)) {
                                                throw $e672;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit670 && $retry671) {
                                    {  }
                                    continue $label669;
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
    public static final long jlc$SourceLastModified$fabil = 1525215695000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUZC5AUxbV377vHwe19+B1w/DYkIOwKptTjAgobkMVFru6DcpSsc7O9dyOzM+tM792eeoFoIgQrpIoggQpSqYREAgQMSplKxFiVGDGkjKHIB0uRlEXQEOInib9KJO/19O7Mze0uR4pcXb83293v9ft398yhS6TMNMiMhNStqEE2kKJmcLnUHYm2SoZJ42FVMs0O6I3Jo0ojO996It7kJd4oqZYlTdcUWVJjmsnImOi9Up8U0igLdbZFWtYRn4yEKySzlxHvuqUZg0xL6epAj6ozscgw/o9dF9rxrfX+oyWkpovUKFo7k5gih3WN0QzrItVJmuymhrkkHqfxLlKrURpvp4Yiqcr9MFHXukidqfRoEksb1Gyjpq724cQ6M52iBl8z24ni6yC2kZaZboD4fkv8NFPUUFQxWUuUlCcUqsbN+8iXSGmUlCVUqQcmjotmtQhxjqHl2A/TqxQQ00hIMs2SlG5QtDgjU90UOY0Dt8MEIK1IUtar55Yq1SToIHWWSKqk9YTamaFoPTC1TE/DKow0FmQKkypTkrxB6qExRia457VaQzDLx82CJIyMdU/jnMBnjS6fObx16Y4vbHtAW6F5iQdkjlNZRfkrgajJRdRGE9Sgmkwtwuo50Z3SuONbvITA5LGuydacZx5879a5Tc+fsOZMyjNndfe9VGYxeV/3mN9NDs9uLkExKlO6qWAoDNGce7VVjLRkUhDt43IccTCYHXy+7VdrNx2gF72kKkLKZV1NJyGqamU9mVJUatxGNWpIjMYjxEe1eJiPR0gFPEcVjVq9qxMJk7IIKVV5V7nOf4OJEsACTVQBz4qW0LPPKYn18udMihDih0Y88L+UkLkfwHMjISWQQ7FQr56koW41TfshvEPQqGTIvSHIW0ORQ6Yhh4y0xhSYJLogigCZIQh1ZkgyM0P9kmFIMAfo77QeB8KgWxBES/3/l8iglv5+jwccMFXW47RbMsGbIrKWtqqQPCt0NU6NmKxuOx4h9cd38+jyYUaYENXcfh6IiMnuWuKk3ZFeuuy9w7GTVmQirTAvI9dbcgeF3MGc3EFb7qBTbhC1GvMwCJUtCJXtkCcTDO+NHOThVm7yvMxxrwbuC1OqxBK6kcwQj4er2sDpeZxBlGyA6gMFpnp2+90r79kyowQCPNVfij6HqQF3utlFKgJPEuRQTK7Z/NYHR3YO6nbiMRIYVg+GU2I+z3DbzdBlGod6abOfM006Fjs+GPBiLfKhgSQIZKg5Te41huR1S7ZGojXKomQU2kBScShb2KpYr6H32z08HsYgqLNCA43lEpCX10Xtqcf/9PLbN/CNJ1uJaxwlu52yFkf2I7Manue1tu07DEph3uu7Wr/52KXN67jhYcbMfAsGEKL7JUh33fjqifvOvHF232mv7SxGylPpblWRM1yX2svw54H2KTZMYexADIU8LMrHtFz9SOHKs2zZoJKoUM1AdDPQqSX1uJJQpG6VYqT8u+Yz84/9bZvfcrcKPZbxDDL3ygzs/olLyaaT6z9s4mw8Mu5ktv3saVZ5rLc5L4FcGEA5Ml8+NWX3i9LjEPlQ3EzlfsrrFeH2INyBC7gt5nE43zX2eQQzLGtN5v2l5vCtYjnuuXYsdoUO7WkML75oVYFcLCKP6XmqwBrJkSYLDiT/5Z1R/oKXVHQRP9/uIanXSFDdIAy6YMM2w6IzSkYPGR+6+Vo7TUsu1ya788CxrDsL7OoDzzgbn6uswLcCBwxRh0aaBm0KGKXawiWf4mh9CmFDxkP4w0JOMpPDWQhmZ4PRlzJ0BlLSeCbH1otsRwl2Hwr8dwdbiGE5bYDGnGQsaC2qI6odtNTmQxPdpc3KVoQ35pZDwcl0S5PSToFvzaPFMksLBIuGC4tUtwh84xBhR4Gw4Wy9FhJPL1jPszNxYmNBeW+AFiCkzGPh0lfzyBstKi9SnRH4lSHyVoBPMrCfZGWdIWTt140N1MiJHEkm0wxzFaJy5NYej+tfD60NxDgo8PY80nfmj5kSLp/SB9WI4T6GJ20IIyUrDF/0OuhpbVu9JNwRWbMsT7a2GkoSCm6fONjRLTu2Xg5u22FVKuv0O3PYAdRJY52A+Vqj+YIZWGV6sVU4xfILRwZ/tn9ws3U6rBt6llumpZM/+sN/fhPcde6lPCeAim5dV6nE9wh/Jr9xvPg4h5FKqdvkQWQnFf+rEceyHoHvctjdUeA8Wc9PdkUpl3N1t0mNPquYNaLeUwqdtbnO+x7asTe++vvzvaKeSuAbpqfmqbSPqo5FcROdPuwut4rfMOzieO7ilObwhvM9lgWnulZ2z/7hqkMv3TZL3u4lJbkqOOxaM5SoZWjtqzIo3Mq0jiEVcFrOqmPRqjdDm0dIucfCZWed0WznQH5vLUaQcBW/BsHpdYFPuv1kb1Ueh8fuFqGI6B4Ge66u9XABMkX2t0EEcEqqSqfiuMU7yurM/ImfPWbyLSeb+T7MfFWHa7W1mJ7TyIdLfRZaMyEV3xZ4cIQ28vCIdpmnUjB5UOC+K5oHfw7wdb5WxBSPIniYkTIplVIH8MfGfLoshLaSkKpaC/vOFdAFwSPDJUeSNwQ+U1hyrx0jXJQYZ729iPg7EHy9mPi8EKyD1gHbSKWFRz13NeG6FkGXyx9+wem4wEcKa1ViFXG+jEu1PUVU24tgJ+wvVsGPFdSwKnsk2QVbTavAC68m2Na6lPMJJs0CLyisnFPiHxQZ24/gO6BN3NBTq9U4TyRuiHz+WgxtDyz8rMDfuCb+Qk7bBN40ovzx88WeLKLXUQQHGKkXXhqReoug7SdkwmmB918T9ZDTEwLvvgr1flpEvWcRPA2nTUWDs/wVteOhiCfCHxMy8ZLAPylSK743PPCQ5BmBnxxRrViCYCVn/YsiuryA4Dk4m5qU3UH77VLu3kP6dCWez20xaMfgFLFZ4OZr4jbkdLPAc0ZeRRxKv1JE6VMIfg0OFPHp0B1HXsyn53poPydk0lMCJ66JnsiJCnzn/6Tnq0X0fA3BaUZqrUC9gpp8T/sitBOETH5X4GNF4jTPnoYkTwt8uLA+TiHfLDJ2HsFZRmp6KItSKdGetu502YPJ3CvcSFZZHWsof0uORMPuJvmMgPvGb+G6+V2BH706IyDJVoG/MjIjvFtk7H0EFxkZ3StpcZV28uOZma/S8BvhKmhnCZnaKXBDEeHzXAeRpF7gqhFVmo1Zb9QP9UY72JzmNzqX4+MiOl9G8E9wfFLaQFvhGjrQRs20yi/DS/K57CZofwHP3SJw7dW5DEn8AhfR2rFNfIJcPeWFdfBUIsBXGzkdbDlcxeUuaH+FpV8WePM1KS7I6RGBMyNypd/WzF9EszoEVQyksupnUQV5UEbBagFCZjdb+HMfX1VQcpKPBH5nRBnlmVhkbBKCBkbmWwEbEHUjkHv5ErBfpgecL9MDXNQMhLOzF184TsrzOUB8xJLDv6T7zt8+d2yBTwEThn1WFHSH99ZUjt/b+Uf+Ijv3gcoXJZWJtKo6X8w5nstTBk0o3KY+6zVdius8zb7GFft2APc/+wcaz9Nk0c9kZEIhema92uTPTppZjIwZSsP4t0J8cs6bzUi5NQ9/zeG+bXSBbJFxK2G94xWvOcT+wAm48o1pAz/eHvrH+I/KKzvO8fffWOAvvDn+7Y2+156ae/D3DaXJUw/8uf+mC+98svVY+9H3H66/cKHkvxw82UJUHgAA";
}
