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
import fabric.worker.transaction.TransactionManager;

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
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
      fabric.metrics.contracts.warranties.WarrantyValue val);
    
    public fabric.util.ArrayList get$proxies();
    
    public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.metrics.contracts.warranties.WarrantyValue
      updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.metrics.contracts.warranties.WarrantyValue apply(long time);
    
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
    public fabric.metrics.contracts.warranties.WarrantyValue apply(
      long time, boolean autoRetry);
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
    public boolean handleUpdates();
    
    /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
    public fabric.lang.Object makeProxyResult(
      fabric.metrics.contracts.warranties.WarrantyValue val, final fabric.worker.Store proxyStore);
    
    /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
    public ProxyComp makeProxy(final fabric.worker.Store proxyStore);
    
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
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long time);
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time, boolean autoRetry);
        
        public ProxyComp makeProxy(final fabric.worker.Store proxyStore);
        
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
                this.set$baseComputation(baseComputation);
                fabric$metrics$contracts$warranties$WarrantyComp$();
                this.get$baseComputation().addObserver((ProxyComp)
                                                         this.$getProxy());
                return (ProxyComp) this.$getProxy();
            }
            
            public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
              long time) {
                return this.get$baseComputation().updatedVal(time);
            }
            
            public fabric.metrics.contracts.warranties.WarrantyValue apply(
              long time, boolean autoRetry) {
                return fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp._Impl.
                  static_apply((ProxyComp) this.$getProxy(), time, autoRetry);
            }
            
            private static fabric.metrics.contracts.warranties.WarrantyValue
              static_apply(ProxyComp tmp, long time, boolean autoRetry) {
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 tmp.get$curVal().get$contract(
                                                                    ), null) &&
                      tmp.get$curVal().get$contract().valid()) {
                    return tmp.get$curVal();
                }
                return tmp.get$baseComputation().apply(time, autoRetry);
            }
            
            public ProxyComp makeProxy(final fabric.worker.Store proxyStore) {
                return fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp._Impl.
                  static_makeProxy((ProxyComp) this.$getProxy(), proxyStore);
            }
            
            private static ProxyComp static_makeProxy(
              ProxyComp tmp, final fabric.worker.Store proxyStore) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return tmp.get$baseComputation().makeProxy(proxyStore);
                }
                else {
                    ProxyComp rtn = null;
                    {
                        fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                          rtn$var670 = rtn;
                        fabric.worker.transaction.TransactionManager $tm676 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled679 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff677 = 1;
                        boolean $doBackoff678 = true;
                        boolean $retry673 = true;
                        $label671: for (boolean $commit672 = false; !$commit672;
                                        ) {
                            if ($backoffEnabled679) {
                                if ($doBackoff678) {
                                    if ($backoff677 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff677);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e674) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff677 < 5000) $backoff677 *= 2;
                                }
                                $doBackoff678 = $backoff677 <= 32 ||
                                                  !$doBackoff678;
                            }
                            $commit672 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e674) {
                                $commit672 = false;
                                continue $label671;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e674) {
                                $commit672 = false;
                                fabric.common.TransactionID $currentTid675 =
                                  $tm676.getCurrentTid();
                                if ($e674.tid.isDescendantOf($currentTid675))
                                    continue $label671;
                                if ($currentTid675.parent != null) {
                                    $retry673 = false;
                                    throw $e674;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e674) {
                                $commit672 = false;
                                if ($tm676.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid675 =
                                  $tm676.getCurrentTid();
                                if ($e674.tid.isDescendantOf($currentTid675)) {
                                    $retry673 = true;
                                }
                                else if ($currentTid675.parent != null) {
                                    $retry673 = false;
                                    throw $e674;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e674) {
                                $commit672 = false;
                                if ($tm676.checkForStaleObjects())
                                    continue $label671;
                                $retry673 = false;
                                throw new fabric.worker.AbortException($e674);
                            }
                            finally {
                                if ($commit672) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e674) {
                                        $commit672 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e674) {
                                        $commit672 = false;
                                        fabric.common.TransactionID
                                          $currentTid675 =
                                          $tm676.getCurrentTid();
                                        if ($currentTid675 != null) {
                                            if ($e674.tid.equals(
                                                            $currentTid675) ||
                                                  !$e674.tid.
                                                  isDescendantOf(
                                                    $currentTid675)) {
                                                throw $e674;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit672 && $retry673) {
                                    { rtn = rtn$var670; }
                                    continue $label671;
                                }
                            }
                        }
                    }
                    return rtn;
                }
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
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
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.warranties.WarrantyComp.
                             ProxyComp._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 28, -62, 27, -44,
        -8, -16, -113, 44, 42, -26, 89, 74, -47, 7, -94, -11, -11, -76, -20,
        -117, 116, -18, 73, -122, -87, 98, 28, 48, 99, -121, 107, 95 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1523309244000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.util.ArrayList get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$proxies(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1, boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1, arg2);
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
        }
        
        public fabric.lang.Object makeProxyResult(
          fabric.metrics.contracts.warranties.WarrantyValue arg1,
          fabric.worker.Store arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxyResult(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
          makeProxy(fabric.worker.Store arg1) {
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
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return this.curVal;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
        public fabric.util.ArrayList get$proxies() { return this.proxies; }
        
        public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.util.ArrayList proxies;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.metrics.contracts.warranties.WarrantyValue
          updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time) {
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
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time, boolean autoRetry) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time, autoRetry);
        }
        
        private static fabric.metrics.contracts.warranties.WarrantyValue
          static_apply(fabric.metrics.contracts.warranties.WarrantyComp tmp,
                       long time, boolean autoRetry) {
            boolean loop = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                loop =
                  fabric.lang.Object._Proxy.idEquals(
                                              tmp.get$curVal().get$contract(),
                                              null) ||
                    !tmp.get$curVal().get$contract().valid();
            }
            else {
                {
                    boolean loop$var680 = loop;
                    fabric.worker.transaction.TransactionManager $tm686 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled689 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff687 = 1;
                    boolean $doBackoff688 = true;
                    boolean $retry683 = true;
                    $label681: for (boolean $commit682 = false; !$commit682; ) {
                        if ($backoffEnabled689) {
                            if ($doBackoff688) {
                                if ($backoff687 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff687);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e684) {
                                            
                                        }
                                    }
                                }
                                if ($backoff687 < 5000) $backoff687 *= 2;
                            }
                            $doBackoff688 = $backoff687 <= 32 || !$doBackoff688;
                        }
                        $commit682 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          tmp.get$curVal(
                                                                ).get$contract(
                                                                    ), null) ||
                                !tmp.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e684) {
                            $commit682 = false;
                            continue $label681;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e684) {
                            $commit682 = false;
                            fabric.common.TransactionID $currentTid685 =
                              $tm686.getCurrentTid();
                            if ($e684.tid.isDescendantOf($currentTid685))
                                continue $label681;
                            if ($currentTid685.parent != null) {
                                $retry683 = false;
                                throw $e684;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e684) {
                            $commit682 = false;
                            if ($tm686.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid685 =
                              $tm686.getCurrentTid();
                            if ($e684.tid.isDescendantOf($currentTid685)) {
                                $retry683 = true;
                            }
                            else if ($currentTid685.parent != null) {
                                $retry683 = false;
                                throw $e684;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e684) {
                            $commit682 = false;
                            if ($tm686.checkForStaleObjects())
                                continue $label681;
                            $retry683 = false;
                            throw new fabric.worker.AbortException($e684);
                        }
                        finally {
                            if ($commit682) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e684) {
                                    $commit682 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e684) {
                                    $commit682 = false;
                                    fabric.common.TransactionID $currentTid685 =
                                      $tm686.getCurrentTid();
                                    if ($currentTid685 != null) {
                                        if ($e684.tid.equals($currentTid685) ||
                                              !$e684.tid.isDescendantOf(
                                                           $currentTid685)) {
                                            throw $e684;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit682 && $retry683) {
                                { loop = loop$var680; }
                                continue $label681;
                            }
                        }
                    }
                }
            }
            while (loop) {
                fabric.metrics.contracts.warranties.WarrantyValue newVal =
                  tmp.updatedVal(java.lang.System.currentTimeMillis());
                if (fabric.lang.Object._Proxy.idEquals(newVal.get$contract(),
                                                       null)) return newVal;
                if (fabric.lang.Object._Proxy.
                      idEquals(
                        fabric.worker.transaction.TransactionManager.
                            getInstance().getCurrentLog(),
                        null) &&
                      !newVal.get$contract().$getStore().name().
                      equals(fabric.worker.Worker.getWorkerName())) {
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         newVal.get$contract(
                                                                  ).$getStore(
                                                                      ).name());
                    ((fabric.metrics.contracts.Contract._Proxy)
                       newVal.get$contract()).activate$remote(w, null);
                }
                else {
                    newVal.get$contract().activate();
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    if (fabric.lang.Object._Proxy.idEquals(
                                                    tmp.get$curVal(
                                                          ).get$contract(),
                                                    null) ||
                          !tmp.get$curVal().get$contract().valid()) {
                        tmp.get$curVal().set$value(newVal.get$value());
                        if (!fabric.lang.Object._Proxy.idEquals(
                                                         tmp.get$curVal(
                                                               ).get$contract(),
                                                         newVal.get$contract(
                                                                  ))) {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$curVal().get$contract(),
                                           null)) {
                                tmp.get$curVal().get$contract().removeObserver(
                                                                  tmp);
                            }
                            tmp.get$curVal().set$contract(
                                               newVal.get$contract());
                            tmp.get$curVal().get$contract().addObserver(tmp);
                            int size = tmp.get$proxies().size();
                            for (int i = 0; i < size; i++) {
                                ProxyComp p =
                                  (ProxyComp)
                                    fabric.lang.Object._Proxy.$getProxy(
                                                                tmp.get$proxies(
                                                                      ).get(i));
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(p.get$curVal().get$contract(),
                                               null)) {
                                    p.get$curVal().get$contract().
                                      removeObserver(p);
                                }
                                p.get$curVal().set$value(newVal.get$value());
                                p.get$curVal().set$contract(
                                                 newVal.get$contract(
                                                          ).getProxyContract(
                                                              p.$getStore()));
                                p.get$curVal().get$contract().addObserver(p);
                            }
                        }
                    }
                }
                else {
                    {
                        boolean loop$var690 = loop;
                        fabric.worker.transaction.TransactionManager $tm696 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled699 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff697 = 1;
                        boolean $doBackoff698 = true;
                        boolean $retry693 = true;
                        $label691: for (boolean $commit692 = false; !$commit692;
                                        ) {
                            if ($backoffEnabled699) {
                                if ($doBackoff698) {
                                    if ($backoff697 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff697);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e694) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff697 < 5000) $backoff697 *= 2;
                                }
                                $doBackoff698 = $backoff697 <= 32 ||
                                                  !$doBackoff698;
                            }
                            $commit692 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                if (fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$curVal().get$contract(),
                                               null) ||
                                      !tmp.get$curVal().get$contract().valid(
                                                                         )) {
                                    tmp.get$curVal().set$value(
                                                       newVal.get$value());
                                    if (!fabric.lang.Object._Proxy.
                                          idEquals(
                                            tmp.get$curVal().get$contract(),
                                            newVal.get$contract())) {
                                        if (!fabric.lang.Object._Proxy.
                                              idEquals(
                                                tmp.get$curVal().get$contract(),
                                                null)) {
                                            tmp.get$curVal().get$contract().
                                              removeObserver(tmp);
                                        }
                                        tmp.get$curVal().set$contract(
                                                           newVal.get$contract(
                                                                    ));
                                        tmp.get$curVal().get$contract().
                                          addObserver(tmp);
                                        int size = tmp.get$proxies().size();
                                        for (int i = 0; i < size; i++) {
                                            ProxyComp
                                              p =
                                              (ProxyComp)
                                                fabric.lang.Object._Proxy.
                                                $getProxy(
                                                  tmp.get$proxies().get(i));
                                            if (!fabric.lang.Object._Proxy.
                                                  idEquals(
                                                    p.get$curVal().get$contract(
                                                                     ), null)) {
                                                p.get$curVal().get$contract().
                                                  removeObserver(p);
                                            }
                                            p.get$curVal().
                                              set$value(
                                                tmp.makeProxyResult(
                                                      newVal, p.$getStore()));
                                            p.get$curVal().
                                              set$contract(
                                                newVal.get$contract(
                                                         ).getProxyContract(
                                                             p.$getStore()));
                                            p.get$curVal().get$contract().
                                              addObserver(p);
                                        }
                                    }
                                }
                            }
                            catch (final fabric.worker.RetryException $e694) {
                                $commit692 = false;
                                continue $label691;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e694) {
                                $commit692 = false;
                                fabric.common.TransactionID $currentTid695 =
                                  $tm696.getCurrentTid();
                                if ($e694.tid.isDescendantOf($currentTid695))
                                    continue $label691;
                                if ($currentTid695.parent != null) {
                                    $retry693 = false;
                                    throw $e694;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e694) {
                                $commit692 = false;
                                if ($tm696.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid695 =
                                  $tm696.getCurrentTid();
                                if ($e694.tid.isDescendantOf($currentTid695)) {
                                    $retry693 = true;
                                }
                                else if ($currentTid695.parent != null) {
                                    $retry693 = false;
                                    throw $e694;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e694) {
                                $commit692 = false;
                                if ($tm696.checkForStaleObjects())
                                    continue $label691;
                                $retry693 = false;
                                throw new fabric.worker.AbortException($e694);
                            }
                            finally {
                                if ($commit692) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e694) {
                                        $commit692 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e694) {
                                        $commit692 = false;
                                        fabric.common.TransactionID
                                          $currentTid695 =
                                          $tm696.getCurrentTid();
                                        if ($currentTid695 != null) {
                                            if ($e694.tid.equals(
                                                            $currentTid695) ||
                                                  !$e694.tid.
                                                  isDescendantOf(
                                                    $currentTid695)) {
                                                throw $e694;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit692 && $retry693) {
                                    { loop = loop$var690; }
                                    continue $label691;
                                }
                            }
                        }
                    }
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    loop =
                      autoRetry &&
                        !fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$curVal(
                                                           ).get$contract(),
                                                     null) &&
                        !tmp.get$curVal().get$contract().valid();
                }
                else {
                    {
                        boolean loop$var700 = loop;
                        fabric.worker.transaction.TransactionManager $tm706 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled709 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff707 = 1;
                        boolean $doBackoff708 = true;
                        boolean $retry703 = true;
                        $label701: for (boolean $commit702 = false; !$commit702;
                                        ) {
                            if ($backoffEnabled709) {
                                if ($doBackoff708) {
                                    if ($backoff707 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff707);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e704) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff707 < 5000) $backoff707 *= 2;
                                }
                                $doBackoff708 = $backoff707 <= 32 ||
                                                  !$doBackoff708;
                            }
                            $commit702 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                loop =
                                  autoRetry &&
                                    !fabric.lang.Object._Proxy.
                                    idEquals(tmp.get$curVal().get$contract(),
                                             null) &&
                                    !tmp.get$curVal().get$contract().valid();
                            }
                            catch (final fabric.worker.RetryException $e704) {
                                $commit702 = false;
                                continue $label701;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e704) {
                                $commit702 = false;
                                fabric.common.TransactionID $currentTid705 =
                                  $tm706.getCurrentTid();
                                if ($e704.tid.isDescendantOf($currentTid705))
                                    continue $label701;
                                if ($currentTid705.parent != null) {
                                    $retry703 = false;
                                    throw $e704;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e704) {
                                $commit702 = false;
                                if ($tm706.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid705 =
                                  $tm706.getCurrentTid();
                                if ($e704.tid.isDescendantOf($currentTid705)) {
                                    $retry703 = true;
                                }
                                else if ($currentTid705.parent != null) {
                                    $retry703 = false;
                                    throw $e704;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e704) {
                                $commit702 = false;
                                if ($tm706.checkForStaleObjects())
                                    continue $label701;
                                $retry703 = false;
                                throw new fabric.worker.AbortException($e704);
                            }
                            finally {
                                if ($commit702) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e704) {
                                        $commit702 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e704) {
                                        $commit702 = false;
                                        fabric.common.TransactionID
                                          $currentTid705 =
                                          $tm706.getCurrentTid();
                                        if ($currentTid705 != null) {
                                            if ($e704.tid.equals(
                                                            $currentTid705) ||
                                                  !$e704.tid.
                                                  isDescendantOf(
                                                    $currentTid705)) {
                                                throw $e704;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit702 && $retry703) {
                                    { loop = loop$var700; }
                                    continue $label701;
                                }
                            }
                        }
                    }
                }
            }
            return tmp.get$curVal();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$curVal().get$contract(),
                                             null))
                return this.get$curVal().get$contract().getLeafSubjects();
            final fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            return (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(local).
                     fabric$lang$arrays$ObjectArray$(
                       this.get$$updateLabel(),
                       this.get$$updateLabel().confPolicy(),
                       fabric.metrics.SampledMetric._Proxy.class, 0).$getProxy(
                                                                       );
        }
        
        public boolean handleUpdates() {
            long time = java.lang.System.currentTimeMillis();
            if (fabric.lang.Object._Proxy.idEquals(
                                            this.get$curVal().get$contract(),
                                            null)) {
                return false;
            }
            else if (!this.get$curVal().get$contract().valid(time)) {
                this.get$curVal().get$contract().
                  removeObserver(
                    (fabric.metrics.contracts.warranties.WarrantyComp)
                      this.$getProxy());
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
          fabric.metrics.contracts.warranties.WarrantyValue val,
          final fabric.worker.Store proxyStore) {
            return val.get$value();
        }
        
        /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
        public ProxyComp makeProxy(final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_makeProxy(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), proxyStore);
        }
        
        private static ProxyComp static_makeProxy(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          final fabric.worker.Store proxyStore) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ProxyComp
                  p =
                  ((ProxyComp)
                     new fabric.metrics.contracts.warranties.WarrantyComp.
                       ProxyComp._Impl(proxyStore).
                     $getProxy()).
                  fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                    tmp);
                tmp.get$proxies().add(p);
                return p;
            }
            else {
                ProxyComp rtn = null;
                {
                    fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                      rtn$var710 = rtn;
                    fabric.worker.transaction.TransactionManager $tm716 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled719 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff717 = 1;
                    boolean $doBackoff718 = true;
                    boolean $retry713 = true;
                    $label711: for (boolean $commit712 = false; !$commit712; ) {
                        if ($backoffEnabled719) {
                            if ($doBackoff718) {
                                if ($backoff717 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff717);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e714) {
                                            
                                        }
                                    }
                                }
                                if ($backoff717 < 5000) $backoff717 *= 2;
                            }
                            $doBackoff718 = $backoff717 <= 32 || !$doBackoff718;
                        }
                        $commit712 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((ProxyComp)
                                 new fabric.metrics.contracts.warranties.
                                   WarrantyComp.ProxyComp._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                  tmp);
                            tmp.get$proxies().add(rtn);
                        }
                        catch (final fabric.worker.RetryException $e714) {
                            $commit712 = false;
                            continue $label711;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e714) {
                            $commit712 = false;
                            fabric.common.TransactionID $currentTid715 =
                              $tm716.getCurrentTid();
                            if ($e714.tid.isDescendantOf($currentTid715))
                                continue $label711;
                            if ($currentTid715.parent != null) {
                                $retry713 = false;
                                throw $e714;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e714) {
                            $commit712 = false;
                            if ($tm716.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid715 =
                              $tm716.getCurrentTid();
                            if ($e714.tid.isDescendantOf($currentTid715)) {
                                $retry713 = true;
                            }
                            else if ($currentTid715.parent != null) {
                                $retry713 = false;
                                throw $e714;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e714) {
                            $commit712 = false;
                            if ($tm716.checkForStaleObjects())
                                continue $label711;
                            $retry713 = false;
                            throw new fabric.worker.AbortException($e714);
                        }
                        finally {
                            if ($commit712) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e714) {
                                    $commit712 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e714) {
                                    $commit712 = false;
                                    fabric.common.TransactionID $currentTid715 =
                                      $tm716.getCurrentTid();
                                    if ($currentTid715 != null) {
                                        if ($e714.tid.equals($currentTid715) ||
                                              !$e714.tid.isDescendantOf(
                                                           $currentTid715)) {
                                            throw $e714;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit712 && $retry713) {
                                { rtn = rtn$var710; }
                                continue $label711;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            this.
              set$curVal(
                ((fabric.metrics.contracts.warranties.WarrantyValue)
                   new fabric.metrics.contracts.warranties.WarrantyValue._Impl(
                     this.$getStore()).$getProxy()).
                    fabric$metrics$contracts$warranties$WarrantyValue$(null,
                                                                       null));
            this.set$proxies(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         4));
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
            $writeRef($getStore(), this.proxies, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.curVal =
              (fabric.
                metrics.
                contracts.
                warranties.
                WarrantyValue)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyValue.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.util.ArrayList)
                             $readRef(fabric.util.ArrayList._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.proxies = src.proxies;
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { -50, -21, 111, 30,
    -100, -113, -119, -115, 11, 57, -83, 124, -20, 85, -15, 32, -97, 88, -30,
    62, 65, -67, 20, 68, -125, -104, -32, 2, -93, 42, 66, 64 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523309244000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
