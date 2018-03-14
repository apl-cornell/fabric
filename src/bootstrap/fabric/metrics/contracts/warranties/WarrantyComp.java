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
                          rtn$var619 = rtn;
                        fabric.worker.transaction.TransactionManager $tm625 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled628 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff626 = 1;
                        boolean $doBackoff627 = true;
                        boolean $retry622 = true;
                        $label620: for (boolean $commit621 = false; !$commit621;
                                        ) {
                            if ($backoffEnabled628) {
                                if ($doBackoff627) {
                                    if ($backoff626 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff626);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e623) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff626 < 5000) $backoff626 *= 2;
                                }
                                $doBackoff627 = $backoff626 <= 32 ||
                                                  !$doBackoff627;
                            }
                            $commit621 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e623) {
                                $commit621 = false;
                                continue $label620;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e623) {
                                $commit621 = false;
                                fabric.common.TransactionID $currentTid624 =
                                  $tm625.getCurrentTid();
                                if ($e623.tid.isDescendantOf($currentTid624))
                                    continue $label620;
                                if ($currentTid624.parent != null) {
                                    $retry622 = false;
                                    throw $e623;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e623) {
                                $commit621 = false;
                                if ($tm625.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid624 =
                                  $tm625.getCurrentTid();
                                if ($e623.tid.isDescendantOf($currentTid624)) {
                                    $retry622 = true;
                                }
                                else if ($currentTid624.parent != null) {
                                    $retry622 = false;
                                    throw $e623;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e623) {
                                $commit621 = false;
                                if ($tm625.checkForStaleObjects())
                                    continue $label620;
                                $retry622 = false;
                                throw new fabric.worker.AbortException($e623);
                            }
                            finally {
                                if ($commit621) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e623) {
                                        $commit621 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e623) {
                                        $commit621 = false;
                                        fabric.common.TransactionID
                                          $currentTid624 =
                                          $tm625.getCurrentTid();
                                        if ($currentTid624 != null) {
                                            if ($e623.tid.equals(
                                                            $currentTid624) ||
                                                  !$e623.tid.
                                                  isDescendantOf(
                                                    $currentTid624)) {
                                                throw $e623;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit621 && $retry622) {
                                    { rtn = rtn$var619; }
                                    continue $label620;
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
        
        public static final byte[] $classHash = new byte[] { 20, -81, 47, -57,
        98, 45, -66, 17, -85, 120, -59, -62, -30, 65, -8, -72, 80, -120, 117,
        -11, -119, 79, 79, 66, -97, 18, 14, 91, -89, -59, -20, -47 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1520977993000L;
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
                    boolean loop$var629 = loop;
                    fabric.worker.transaction.TransactionManager $tm635 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled638 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff636 = 1;
                    boolean $doBackoff637 = true;
                    boolean $retry632 = true;
                    $label630: for (boolean $commit631 = false; !$commit631; ) {
                        if ($backoffEnabled638) {
                            if ($doBackoff637) {
                                if ($backoff636 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff636);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e633) {
                                            
                                        }
                                    }
                                }
                                if ($backoff636 < 5000) $backoff636 *= 2;
                            }
                            $doBackoff637 = $backoff636 <= 32 || !$doBackoff637;
                        }
                        $commit631 = true;
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
                        catch (final fabric.worker.RetryException $e633) {
                            $commit631 = false;
                            continue $label630;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e633) {
                            $commit631 = false;
                            fabric.common.TransactionID $currentTid634 =
                              $tm635.getCurrentTid();
                            if ($e633.tid.isDescendantOf($currentTid634))
                                continue $label630;
                            if ($currentTid634.parent != null) {
                                $retry632 = false;
                                throw $e633;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e633) {
                            $commit631 = false;
                            if ($tm635.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid634 =
                              $tm635.getCurrentTid();
                            if ($e633.tid.isDescendantOf($currentTid634)) {
                                $retry632 = true;
                            }
                            else if ($currentTid634.parent != null) {
                                $retry632 = false;
                                throw $e633;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e633) {
                            $commit631 = false;
                            if ($tm635.checkForStaleObjects())
                                continue $label630;
                            $retry632 = false;
                            throw new fabric.worker.AbortException($e633);
                        }
                        finally {
                            if ($commit631) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e633) {
                                    $commit631 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e633) {
                                    $commit631 = false;
                                    fabric.common.TransactionID $currentTid634 =
                                      $tm635.getCurrentTid();
                                    if ($currentTid634 != null) {
                                        if ($e633.tid.equals($currentTid634) ||
                                              !$e633.tid.isDescendantOf(
                                                           $currentTid634)) {
                                            throw $e633;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit631 && $retry632) {
                                { loop = loop$var629; }
                                continue $label630;
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
                        boolean loop$var639 = loop;
                        fabric.worker.transaction.TransactionManager $tm645 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled648 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff646 = 1;
                        boolean $doBackoff647 = true;
                        boolean $retry642 = true;
                        $label640: for (boolean $commit641 = false; !$commit641;
                                        ) {
                            if ($backoffEnabled648) {
                                if ($doBackoff647) {
                                    if ($backoff646 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff646);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e643) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff646 < 5000) $backoff646 *= 2;
                                }
                                $doBackoff647 = $backoff646 <= 32 ||
                                                  !$doBackoff647;
                            }
                            $commit641 = true;
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
                            catch (final fabric.worker.RetryException $e643) {
                                $commit641 = false;
                                continue $label640;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e643) {
                                $commit641 = false;
                                fabric.common.TransactionID $currentTid644 =
                                  $tm645.getCurrentTid();
                                if ($e643.tid.isDescendantOf($currentTid644))
                                    continue $label640;
                                if ($currentTid644.parent != null) {
                                    $retry642 = false;
                                    throw $e643;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e643) {
                                $commit641 = false;
                                if ($tm645.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid644 =
                                  $tm645.getCurrentTid();
                                if ($e643.tid.isDescendantOf($currentTid644)) {
                                    $retry642 = true;
                                }
                                else if ($currentTid644.parent != null) {
                                    $retry642 = false;
                                    throw $e643;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e643) {
                                $commit641 = false;
                                if ($tm645.checkForStaleObjects())
                                    continue $label640;
                                $retry642 = false;
                                throw new fabric.worker.AbortException($e643);
                            }
                            finally {
                                if ($commit641) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e643) {
                                        $commit641 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e643) {
                                        $commit641 = false;
                                        fabric.common.TransactionID
                                          $currentTid644 =
                                          $tm645.getCurrentTid();
                                        if ($currentTid644 != null) {
                                            if ($e643.tid.equals(
                                                            $currentTid644) ||
                                                  !$e643.tid.
                                                  isDescendantOf(
                                                    $currentTid644)) {
                                                throw $e643;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit641 && $retry642) {
                                    { loop = loop$var639; }
                                    continue $label640;
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
                        boolean loop$var649 = loop;
                        fabric.worker.transaction.TransactionManager $tm655 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled658 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff656 = 1;
                        boolean $doBackoff657 = true;
                        boolean $retry652 = true;
                        $label650: for (boolean $commit651 = false; !$commit651;
                                        ) {
                            if ($backoffEnabled658) {
                                if ($doBackoff657) {
                                    if ($backoff656 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff656);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e653) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff656 < 5000) $backoff656 *= 2;
                                }
                                $doBackoff657 = $backoff656 <= 32 ||
                                                  !$doBackoff657;
                            }
                            $commit651 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid654 =
                                          $tm655.getCurrentTid();
                                        if ($currentTid654 != null) {
                                            if ($e653.tid.equals(
                                                            $currentTid654) ||
                                                  !$e653.tid.
                                                  isDescendantOf(
                                                    $currentTid654)) {
                                                throw $e653;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit651 && $retry652) {
                                    { loop = loop$var649; }
                                    continue $label650;
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
                            rtn =
                              ((ProxyComp)
                                 new fabric.metrics.contracts.warranties.
                                   WarrantyComp.ProxyComp._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                  tmp);
                            tmp.get$proxies().add(rtn);
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
    
    public static final byte[] $classHash = new byte[] { -27, 4, 114, 26, -126,
    -52, 65, -60, -112, 62, -87, 69, 22, 44, 123, 80, 87, -70, -107, 125, -53,
    -38, -88, -17, -27, -40, -54, 43, -69, -23, 67, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
