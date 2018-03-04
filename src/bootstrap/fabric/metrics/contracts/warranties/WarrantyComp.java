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
                          rtn$var637 = rtn;
                        fabric.worker.transaction.TransactionManager $tm643 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled646 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff644 = 1;
                        boolean $doBackoff645 = true;
                        boolean $retry640 = true;
                        $label638: for (boolean $commit639 = false; !$commit639;
                                        ) {
                            if ($backoffEnabled646) {
                                if ($doBackoff645) {
                                    if ($backoff644 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff644);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e641) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff644 < 5000) $backoff644 *= 2;
                                }
                                $doBackoff645 = $backoff644 <= 32 ||
                                                  !$doBackoff645;
                            }
                            $commit639 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e641) {
                                $commit639 = false;
                                continue $label638;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e641) {
                                $commit639 = false;
                                fabric.common.TransactionID $currentTid642 =
                                  $tm643.getCurrentTid();
                                if ($e641.tid.isDescendantOf($currentTid642))
                                    continue $label638;
                                if ($currentTid642.parent != null) {
                                    $retry640 = false;
                                    throw $e641;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e641) {
                                $commit639 = false;
                                if ($tm643.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid642 =
                                  $tm643.getCurrentTid();
                                if ($e641.tid.isDescendantOf($currentTid642)) {
                                    $retry640 = true;
                                }
                                else if ($currentTid642.parent != null) {
                                    $retry640 = false;
                                    throw $e641;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e641) {
                                $commit639 = false;
                                if ($tm643.checkForStaleObjects())
                                    continue $label638;
                                $retry640 = false;
                                throw new fabric.worker.AbortException($e641);
                            }
                            finally {
                                if ($commit639) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e641) {
                                        $commit639 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e641) {
                                        $commit639 = false;
                                        fabric.common.TransactionID
                                          $currentTid642 =
                                          $tm643.getCurrentTid();
                                        if ($currentTid642 != null) {
                                            if ($e641.tid.equals(
                                                            $currentTid642) ||
                                                  !$e641.tid.
                                                  isDescendantOf(
                                                    $currentTid642)) {
                                                throw $e641;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit639 && $retry640) {
                                    { rtn = rtn$var637; }
                                    continue $label638;
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
        public static final long jlc$SourceLastModified$fabil = 1520199002000L;
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
                    boolean loop$var647 = loop;
                    fabric.worker.transaction.TransactionManager $tm653 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled656 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff654 = 1;
                    boolean $doBackoff655 = true;
                    boolean $retry650 = true;
                    $label648: for (boolean $commit649 = false; !$commit649; ) {
                        if ($backoffEnabled656) {
                            if ($doBackoff655) {
                                if ($backoff654 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff654);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e651) {
                                            
                                        }
                                    }
                                }
                                if ($backoff654 < 5000) $backoff654 *= 2;
                            }
                            $doBackoff655 = $backoff654 <= 32 || !$doBackoff655;
                        }
                        $commit649 = true;
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
                        catch (final fabric.worker.RetryException $e651) {
                            $commit649 = false;
                            continue $label648;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e651) {
                            $commit649 = false;
                            fabric.common.TransactionID $currentTid652 =
                              $tm653.getCurrentTid();
                            if ($e651.tid.isDescendantOf($currentTid652))
                                continue $label648;
                            if ($currentTid652.parent != null) {
                                $retry650 = false;
                                throw $e651;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e651) {
                            $commit649 = false;
                            if ($tm653.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid652 =
                              $tm653.getCurrentTid();
                            if ($e651.tid.isDescendantOf($currentTid652)) {
                                $retry650 = true;
                            }
                            else if ($currentTid652.parent != null) {
                                $retry650 = false;
                                throw $e651;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e651) {
                            $commit649 = false;
                            if ($tm653.checkForStaleObjects())
                                continue $label648;
                            $retry650 = false;
                            throw new fabric.worker.AbortException($e651);
                        }
                        finally {
                            if ($commit649) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e651) {
                                    $commit649 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e651) {
                                    $commit649 = false;
                                    fabric.common.TransactionID $currentTid652 =
                                      $tm653.getCurrentTid();
                                    if ($currentTid652 != null) {
                                        if ($e651.tid.equals($currentTid652) ||
                                              !$e651.tid.isDescendantOf(
                                                           $currentTid652)) {
                                            throw $e651;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit649 && $retry650) {
                                { loop = loop$var647; }
                                continue $label648;
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
                        boolean loop$var657 = loop;
                        fabric.worker.transaction.TransactionManager $tm663 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled666 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff664 = 1;
                        boolean $doBackoff665 = true;
                        boolean $retry660 = true;
                        $label658: for (boolean $commit659 = false; !$commit659;
                                        ) {
                            if ($backoffEnabled666) {
                                if ($doBackoff665) {
                                    if ($backoff664 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff664);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e661) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff664 < 5000) $backoff664 *= 2;
                                }
                                $doBackoff665 = $backoff664 <= 32 ||
                                                  !$doBackoff665;
                            }
                            $commit659 = true;
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
                            catch (final fabric.worker.RetryException $e661) {
                                $commit659 = false;
                                continue $label658;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e661) {
                                $commit659 = false;
                                fabric.common.TransactionID $currentTid662 =
                                  $tm663.getCurrentTid();
                                if ($e661.tid.isDescendantOf($currentTid662))
                                    continue $label658;
                                if ($currentTid662.parent != null) {
                                    $retry660 = false;
                                    throw $e661;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e661) {
                                $commit659 = false;
                                if ($tm663.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid662 =
                                  $tm663.getCurrentTid();
                                if ($e661.tid.isDescendantOf($currentTid662)) {
                                    $retry660 = true;
                                }
                                else if ($currentTid662.parent != null) {
                                    $retry660 = false;
                                    throw $e661;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e661) {
                                $commit659 = false;
                                if ($tm663.checkForStaleObjects())
                                    continue $label658;
                                $retry660 = false;
                                throw new fabric.worker.AbortException($e661);
                            }
                            finally {
                                if ($commit659) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e661) {
                                        $commit659 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e661) {
                                        $commit659 = false;
                                        fabric.common.TransactionID
                                          $currentTid662 =
                                          $tm663.getCurrentTid();
                                        if ($currentTid662 != null) {
                                            if ($e661.tid.equals(
                                                            $currentTid662) ||
                                                  !$e661.tid.
                                                  isDescendantOf(
                                                    $currentTid662)) {
                                                throw $e661;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit659 && $retry660) {
                                    { loop = loop$var657; }
                                    continue $label658;
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
                        boolean loop$var667 = loop;
                        fabric.worker.transaction.TransactionManager $tm673 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled676 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff674 = 1;
                        boolean $doBackoff675 = true;
                        boolean $retry670 = true;
                        $label668: for (boolean $commit669 = false; !$commit669;
                                        ) {
                            if ($backoffEnabled676) {
                                if ($doBackoff675) {
                                    if ($backoff674 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff674);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e671) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff674 < 5000) $backoff674 *= 2;
                                }
                                $doBackoff675 = $backoff674 <= 32 ||
                                                  !$doBackoff675;
                            }
                            $commit669 = true;
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
                            catch (final fabric.worker.RetryException $e671) {
                                $commit669 = false;
                                continue $label668;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e671) {
                                $commit669 = false;
                                fabric.common.TransactionID $currentTid672 =
                                  $tm673.getCurrentTid();
                                if ($e671.tid.isDescendantOf($currentTid672))
                                    continue $label668;
                                if ($currentTid672.parent != null) {
                                    $retry670 = false;
                                    throw $e671;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e671) {
                                $commit669 = false;
                                if ($tm673.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid672 =
                                  $tm673.getCurrentTid();
                                if ($e671.tid.isDescendantOf($currentTid672)) {
                                    $retry670 = true;
                                }
                                else if ($currentTid672.parent != null) {
                                    $retry670 = false;
                                    throw $e671;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e671) {
                                $commit669 = false;
                                if ($tm673.checkForStaleObjects())
                                    continue $label668;
                                $retry670 = false;
                                throw new fabric.worker.AbortException($e671);
                            }
                            finally {
                                if ($commit669) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e671) {
                                        $commit669 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e671) {
                                        $commit669 = false;
                                        fabric.common.TransactionID
                                          $currentTid672 =
                                          $tm673.getCurrentTid();
                                        if ($currentTid672 != null) {
                                            if ($e671.tid.equals(
                                                            $currentTid672) ||
                                                  !$e671.tid.
                                                  isDescendantOf(
                                                    $currentTid672)) {
                                                throw $e671;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit669 && $retry670) {
                                    { loop = loop$var667; }
                                    continue $label668;
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
                      rtn$var677 = rtn;
                    fabric.worker.transaction.TransactionManager $tm683 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled686 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff684 = 1;
                    boolean $doBackoff685 = true;
                    boolean $retry680 = true;
                    $label678: for (boolean $commit679 = false; !$commit679; ) {
                        if ($backoffEnabled686) {
                            if ($doBackoff685) {
                                if ($backoff684 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff684);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e681) {
                                            
                                        }
                                    }
                                }
                                if ($backoff684 < 5000) $backoff684 *= 2;
                            }
                            $doBackoff685 = $backoff684 <= 32 || !$doBackoff685;
                        }
                        $commit679 = true;
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
                        catch (final fabric.worker.RetryException $e681) {
                            $commit679 = false;
                            continue $label678;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e681) {
                            $commit679 = false;
                            fabric.common.TransactionID $currentTid682 =
                              $tm683.getCurrentTid();
                            if ($e681.tid.isDescendantOf($currentTid682))
                                continue $label678;
                            if ($currentTid682.parent != null) {
                                $retry680 = false;
                                throw $e681;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e681) {
                            $commit679 = false;
                            if ($tm683.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid682 =
                              $tm683.getCurrentTid();
                            if ($e681.tid.isDescendantOf($currentTid682)) {
                                $retry680 = true;
                            }
                            else if ($currentTid682.parent != null) {
                                $retry680 = false;
                                throw $e681;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e681) {
                            $commit679 = false;
                            if ($tm683.checkForStaleObjects())
                                continue $label678;
                            $retry680 = false;
                            throw new fabric.worker.AbortException($e681);
                        }
                        finally {
                            if ($commit679) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e681) {
                                    $commit679 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e681) {
                                    $commit679 = false;
                                    fabric.common.TransactionID $currentTid682 =
                                      $tm683.getCurrentTid();
                                    if ($currentTid682 != null) {
                                        if ($e681.tid.equals($currentTid682) ||
                                              !$e681.tid.isDescendantOf(
                                                           $currentTid682)) {
                                            throw $e681;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit679 && $retry680) {
                                { rtn = rtn$var677; }
                                continue $label678;
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
    public static final long jlc$SourceLastModified$fabil = 1520199002000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
