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
                          rtn$var545 = rtn;
                        fabric.worker.transaction.TransactionManager $tm551 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled554 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff552 = 1;
                        boolean $doBackoff553 = true;
                        boolean $retry548 = true;
                        $label546: for (boolean $commit547 = false; !$commit547;
                                        ) {
                            if ($backoffEnabled554) {
                                if ($doBackoff553) {
                                    if ($backoff552 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff552);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e549) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff552 < 5000) $backoff552 *= 2;
                                }
                                $doBackoff553 = $backoff552 <= 32 ||
                                                  !$doBackoff553;
                            }
                            $commit547 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e549) {
                                $commit547 = false;
                                continue $label546;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e549) {
                                $commit547 = false;
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid.isDescendantOf($currentTid550))
                                    continue $label546;
                                if ($currentTid550.parent != null) {
                                    $retry548 = false;
                                    throw $e549;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e549) {
                                $commit547 = false;
                                if ($tm551.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid.isDescendantOf($currentTid550)) {
                                    $retry548 = true;
                                }
                                else if ($currentTid550.parent != null) {
                                    $retry548 = false;
                                    throw $e549;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e549) {
                                $commit547 = false;
                                if ($tm551.checkForStaleObjects())
                                    continue $label546;
                                $retry548 = false;
                                throw new fabric.worker.AbortException($e549);
                            }
                            finally {
                                if ($commit547) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e549) {
                                        $commit547 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e549) {
                                        $commit547 = false;
                                        fabric.common.TransactionID
                                          $currentTid550 =
                                          $tm551.getCurrentTid();
                                        if ($currentTid550 != null) {
                                            if ($e549.tid.equals(
                                                            $currentTid550) ||
                                                  !$e549.tid.
                                                  isDescendantOf(
                                                    $currentTid550)) {
                                                throw $e549;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit547 && $retry548) {
                                    { rtn = rtn$var545; }
                                    continue $label546;
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
        public static final long jlc$SourceLastModified$fabil = 1520116324000L;
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
                    boolean loop$var555 = loop;
                    fabric.worker.transaction.TransactionManager $tm561 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled564 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff562 = 1;
                    boolean $doBackoff563 = true;
                    boolean $retry558 = true;
                    $label556: for (boolean $commit557 = false; !$commit557; ) {
                        if ($backoffEnabled564) {
                            if ($doBackoff563) {
                                if ($backoff562 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff562);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e559) {
                                            
                                        }
                                    }
                                }
                                if ($backoff562 < 5000) $backoff562 *= 2;
                            }
                            $doBackoff563 = $backoff562 <= 32 || !$doBackoff563;
                        }
                        $commit557 = true;
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
                        catch (final fabric.worker.RetryException $e559) {
                            $commit557 = false;
                            continue $label556;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e559) {
                            $commit557 = false;
                            fabric.common.TransactionID $currentTid560 =
                              $tm561.getCurrentTid();
                            if ($e559.tid.isDescendantOf($currentTid560))
                                continue $label556;
                            if ($currentTid560.parent != null) {
                                $retry558 = false;
                                throw $e559;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e559) {
                            $commit557 = false;
                            if ($tm561.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid560 =
                              $tm561.getCurrentTid();
                            if ($e559.tid.isDescendantOf($currentTid560)) {
                                $retry558 = true;
                            }
                            else if ($currentTid560.parent != null) {
                                $retry558 = false;
                                throw $e559;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e559) {
                            $commit557 = false;
                            if ($tm561.checkForStaleObjects())
                                continue $label556;
                            $retry558 = false;
                            throw new fabric.worker.AbortException($e559);
                        }
                        finally {
                            if ($commit557) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e559) {
                                    $commit557 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e559) {
                                    $commit557 = false;
                                    fabric.common.TransactionID $currentTid560 =
                                      $tm561.getCurrentTid();
                                    if ($currentTid560 != null) {
                                        if ($e559.tid.equals($currentTid560) ||
                                              !$e559.tid.isDescendantOf(
                                                           $currentTid560)) {
                                            throw $e559;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit557 && $retry558) {
                                { loop = loop$var555; }
                                continue $label556;
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
                        boolean loop$var565 = loop;
                        fabric.worker.transaction.TransactionManager $tm571 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled574 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff572 = 1;
                        boolean $doBackoff573 = true;
                        boolean $retry568 = true;
                        $label566: for (boolean $commit567 = false; !$commit567;
                                        ) {
                            if ($backoffEnabled574) {
                                if ($doBackoff573) {
                                    if ($backoff572 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff572);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e569) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff572 < 5000) $backoff572 *= 2;
                                }
                                $doBackoff573 = $backoff572 <= 32 ||
                                                  !$doBackoff573;
                            }
                            $commit567 = true;
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
                            catch (final fabric.worker.RetryException $e569) {
                                $commit567 = false;
                                continue $label566;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e569) {
                                $commit567 = false;
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid.isDescendantOf($currentTid570))
                                    continue $label566;
                                if ($currentTid570.parent != null) {
                                    $retry568 = false;
                                    throw $e569;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e569) {
                                $commit567 = false;
                                if ($tm571.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid570 =
                                  $tm571.getCurrentTid();
                                if ($e569.tid.isDescendantOf($currentTid570)) {
                                    $retry568 = true;
                                }
                                else if ($currentTid570.parent != null) {
                                    $retry568 = false;
                                    throw $e569;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e569) {
                                $commit567 = false;
                                if ($tm571.checkForStaleObjects())
                                    continue $label566;
                                $retry568 = false;
                                throw new fabric.worker.AbortException($e569);
                            }
                            finally {
                                if ($commit567) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e569) {
                                        $commit567 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e569) {
                                        $commit567 = false;
                                        fabric.common.TransactionID
                                          $currentTid570 =
                                          $tm571.getCurrentTid();
                                        if ($currentTid570 != null) {
                                            if ($e569.tid.equals(
                                                            $currentTid570) ||
                                                  !$e569.tid.
                                                  isDescendantOf(
                                                    $currentTid570)) {
                                                throw $e569;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit567 && $retry568) {
                                    { loop = loop$var565; }
                                    continue $label566;
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
                        boolean loop$var575 = loop;
                        fabric.worker.transaction.TransactionManager $tm581 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled584 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff582 = 1;
                        boolean $doBackoff583 = true;
                        boolean $retry578 = true;
                        $label576: for (boolean $commit577 = false; !$commit577;
                                        ) {
                            if ($backoffEnabled584) {
                                if ($doBackoff583) {
                                    if ($backoff582 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff582);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e579) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff582 < 5000) $backoff582 *= 2;
                                }
                                $doBackoff583 = $backoff582 <= 32 ||
                                                  !$doBackoff583;
                            }
                            $commit577 = true;
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
                            catch (final fabric.worker.RetryException $e579) {
                                $commit577 = false;
                                continue $label576;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e579) {
                                $commit577 = false;
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid.isDescendantOf($currentTid580))
                                    continue $label576;
                                if ($currentTid580.parent != null) {
                                    $retry578 = false;
                                    throw $e579;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e579) {
                                $commit577 = false;
                                if ($tm581.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid580 =
                                  $tm581.getCurrentTid();
                                if ($e579.tid.isDescendantOf($currentTid580)) {
                                    $retry578 = true;
                                }
                                else if ($currentTid580.parent != null) {
                                    $retry578 = false;
                                    throw $e579;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e579) {
                                $commit577 = false;
                                if ($tm581.checkForStaleObjects())
                                    continue $label576;
                                $retry578 = false;
                                throw new fabric.worker.AbortException($e579);
                            }
                            finally {
                                if ($commit577) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e579) {
                                        $commit577 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e579) {
                                        $commit577 = false;
                                        fabric.common.TransactionID
                                          $currentTid580 =
                                          $tm581.getCurrentTid();
                                        if ($currentTid580 != null) {
                                            if ($e579.tid.equals(
                                                            $currentTid580) ||
                                                  !$e579.tid.
                                                  isDescendantOf(
                                                    $currentTid580)) {
                                                throw $e579;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit577 && $retry578) {
                                    { loop = loop$var575; }
                                    continue $label576;
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
                      rtn$var585 = rtn;
                    fabric.worker.transaction.TransactionManager $tm591 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled594 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff592 = 1;
                    boolean $doBackoff593 = true;
                    boolean $retry588 = true;
                    $label586: for (boolean $commit587 = false; !$commit587; ) {
                        if ($backoffEnabled594) {
                            if ($doBackoff593) {
                                if ($backoff592 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff592);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e589) {
                                            
                                        }
                                    }
                                }
                                if ($backoff592 < 5000) $backoff592 *= 2;
                            }
                            $doBackoff593 = $backoff592 <= 32 || !$doBackoff593;
                        }
                        $commit587 = true;
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
                        catch (final fabric.worker.RetryException $e589) {
                            $commit587 = false;
                            continue $label586;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e589) {
                            $commit587 = false;
                            fabric.common.TransactionID $currentTid590 =
                              $tm591.getCurrentTid();
                            if ($e589.tid.isDescendantOf($currentTid590))
                                continue $label586;
                            if ($currentTid590.parent != null) {
                                $retry588 = false;
                                throw $e589;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e589) {
                            $commit587 = false;
                            if ($tm591.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid590 =
                              $tm591.getCurrentTid();
                            if ($e589.tid.isDescendantOf($currentTid590)) {
                                $retry588 = true;
                            }
                            else if ($currentTid590.parent != null) {
                                $retry588 = false;
                                throw $e589;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e589) {
                            $commit587 = false;
                            if ($tm591.checkForStaleObjects())
                                continue $label586;
                            $retry588 = false;
                            throw new fabric.worker.AbortException($e589);
                        }
                        finally {
                            if ($commit587) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e589) {
                                    $commit587 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e589) {
                                    $commit587 = false;
                                    fabric.common.TransactionID $currentTid590 =
                                      $tm591.getCurrentTid();
                                    if ($currentTid590 != null) {
                                        if ($e589.tid.equals($currentTid590) ||
                                              !$e589.tid.isDescendantOf(
                                                           $currentTid590)) {
                                            throw $e589;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit587 && $retry588) {
                                { rtn = rtn$var585; }
                                continue $label586;
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
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
