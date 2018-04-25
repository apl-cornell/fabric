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
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(fabric.worker.metrics.ImmutableSet val);
    
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
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
                          rtn$var650 = rtn;
                        fabric.worker.transaction.TransactionManager $tm656 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled659 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff657 = 1;
                        boolean $doBackoff658 = true;
                        boolean $retry653 = true;
                        $label651: for (boolean $commit652 = false; !$commit652;
                                        ) {
                            if ($backoffEnabled659) {
                                if ($doBackoff658) {
                                    if ($backoff657 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff657);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e654) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff657 < 5000) $backoff657 *= 2;
                                }
                                $doBackoff658 = $backoff657 <= 32 ||
                                                  !$doBackoff658;
                            }
                            $commit652 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e654) {
                                $commit652 = false;
                                continue $label651;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e654) {
                                $commit652 = false;
                                fabric.common.TransactionID $currentTid655 =
                                  $tm656.getCurrentTid();
                                if ($e654.tid.isDescendantOf($currentTid655))
                                    continue $label651;
                                if ($currentTid655.parent != null) {
                                    $retry653 = false;
                                    throw $e654;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e654) {
                                $commit652 = false;
                                if ($tm656.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid655 =
                                  $tm656.getCurrentTid();
                                if ($e654.tid.isDescendantOf($currentTid655)) {
                                    $retry653 = true;
                                }
                                else if ($currentTid655.parent != null) {
                                    $retry653 = false;
                                    throw $e654;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e654) {
                                $commit652 = false;
                                if ($tm656.checkForStaleObjects())
                                    continue $label651;
                                $retry653 = false;
                                throw new fabric.worker.AbortException($e654);
                            }
                            finally {
                                if ($commit652) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e654) {
                                        $commit652 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e654) {
                                        $commit652 = false;
                                        fabric.common.TransactionID
                                          $currentTid655 =
                                          $tm656.getCurrentTid();
                                        if ($currentTid655 != null) {
                                            if ($e654.tid.equals(
                                                            $currentTid655) ||
                                                  !$e654.tid.
                                                  isDescendantOf(
                                                    $currentTid655)) {
                                                throw $e654;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit652 && $retry653) {
                                    { rtn = rtn$var650; }
                                    continue $label651;
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
        
        public static final byte[] $classHash = new byte[] { 47, -119, 79, 47,
        112, 86, -81, 18, 65, 117, 76, 39, -75, 48, 25, 91, 3, 80, 70, -127,
        109, -69, -1, 78, 76, -42, 31, 117, -99, 31, 107, 4 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524505527000L;
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
        
        public fabric.worker.metrics.ImmutableSet get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.worker.metrics.ImmutableSet set$proxies(
          fabric.worker.metrics.ImmutableSet val) {
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
                    boolean loop$var660 = loop;
                    fabric.worker.transaction.TransactionManager $tm666 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled669 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff667 = 1;
                    boolean $doBackoff668 = true;
                    boolean $retry663 = true;
                    $label661: for (boolean $commit662 = false; !$commit662; ) {
                        if ($backoffEnabled669) {
                            if ($doBackoff668) {
                                if ($backoff667 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff667);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e664) {
                                            
                                        }
                                    }
                                }
                                if ($backoff667 < 5000) $backoff667 *= 2;
                            }
                            $doBackoff668 = $backoff667 <= 32 || !$doBackoff668;
                        }
                        $commit662 = true;
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
                        catch (final fabric.worker.RetryException $e664) {
                            $commit662 = false;
                            continue $label661;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e664) {
                            $commit662 = false;
                            fabric.common.TransactionID $currentTid665 =
                              $tm666.getCurrentTid();
                            if ($e664.tid.isDescendantOf($currentTid665))
                                continue $label661;
                            if ($currentTid665.parent != null) {
                                $retry663 = false;
                                throw $e664;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e664) {
                            $commit662 = false;
                            if ($tm666.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid665 =
                              $tm666.getCurrentTid();
                            if ($e664.tid.isDescendantOf($currentTid665)) {
                                $retry663 = true;
                            }
                            else if ($currentTid665.parent != null) {
                                $retry663 = false;
                                throw $e664;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e664) {
                            $commit662 = false;
                            if ($tm666.checkForStaleObjects())
                                continue $label661;
                            $retry663 = false;
                            throw new fabric.worker.AbortException($e664);
                        }
                        finally {
                            if ($commit662) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e664) {
                                    $commit662 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e664) {
                                    $commit662 = false;
                                    fabric.common.TransactionID $currentTid665 =
                                      $tm666.getCurrentTid();
                                    if ($currentTid665 != null) {
                                        if ($e664.tid.equals($currentTid665) ||
                                              !$e664.tid.isDescendantOf(
                                                           $currentTid665)) {
                                            throw $e664;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit662 && $retry663) {
                                { loop = loop$var660; }
                                continue $label661;
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
                            for (java.util.Iterator iter =
                                   tmp.get$proxies().iterator();
                                 iter.hasNext();
                                 ) {
                                ProxyComp
                                  p =
                                  (ProxyComp)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      fabric.lang.WrappedJavaInlineable.
                                          $wrap(iter.next()));
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
                        boolean loop$var670 = loop;
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
                                        for (java.util.Iterator iter =
                                               tmp.get$proxies().iterator();
                                             iter.hasNext();
                                             ) {
                                            ProxyComp
                                              p =
                                              (ProxyComp)
                                                fabric.lang.Object._Proxy.
                                                $getProxy(
                                                  fabric.lang.WrappedJavaInlineable.
                                                      $wrap(iter.next()));
                                            if (!fabric.lang.Object._Proxy.
                                                  idEquals(
                                                    p.get$curVal().get$contract(
                                                                     ), null)) {
                                                p.get$curVal().get$contract().
                                                  removeObserver(p);
                                            }
                                            p.get$curVal().set$value(
                                                             newVal.get$value(
                                                                      ));
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
                                    { loop = loop$var670; }
                                    continue $label671;
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
                        boolean loop$var680 = loop;
                        fabric.worker.transaction.TransactionManager $tm686 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled689 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff687 = 1;
                        boolean $doBackoff688 = true;
                        boolean $retry683 = true;
                        $label681: for (boolean $commit682 = false; !$commit682;
                                        ) {
                            if ($backoffEnabled689) {
                                if ($doBackoff688) {
                                    if ($backoff687 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff687);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e684) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff687 < 5000) $backoff687 *= 2;
                                }
                                $doBackoff688 = $backoff687 <= 32 ||
                                                  !$doBackoff688;
                            }
                            $commit682 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid685 =
                                          $tm686.getCurrentTid();
                                        if ($currentTid685 != null) {
                                            if ($e684.tid.equals(
                                                            $currentTid685) ||
                                                  !$e684.tid.
                                                  isDescendantOf(
                                                    $currentTid685)) {
                                                throw $e684;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit682 && $retry683) {
                                    { loop = loop$var680; }
                                    continue $label681;
                                }
                            }
                        }
                    }
                }
            }
            return tmp.get$curVal();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$curVal().get$contract(),
                                             null))
                return this.get$curVal().get$contract().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
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
                tmp.set$proxies(tmp.get$proxies().add(p));
                return p;
            }
            else {
                ProxyComp rtn = null;
                {
                    fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                      rtn$var690 = rtn;
                    fabric.worker.transaction.TransactionManager $tm696 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled699 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff697 = 1;
                    boolean $doBackoff698 = true;
                    boolean $retry693 = true;
                    $label691: for (boolean $commit692 = false; !$commit692; ) {
                        if ($backoffEnabled699) {
                            if ($doBackoff698) {
                                if ($backoff697 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff697);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e694) {
                                            
                                        }
                                    }
                                }
                                if ($backoff697 < 5000) $backoff697 *= 2;
                            }
                            $doBackoff698 = $backoff697 <= 32 || !$doBackoff698;
                        }
                        $commit692 = true;
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
                            tmp.set$proxies(tmp.get$proxies().add(rtn));
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
                                    fabric.common.TransactionID $currentTid695 =
                                      $tm696.getCurrentTid();
                                    if ($currentTid695 != null) {
                                        if ($e694.tid.equals($currentTid695) ||
                                              !$e694.tid.isDescendantOf(
                                                           $currentTid695)) {
                                            throw $e694;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit692 && $retry693) {
                                { rtn = rtn$var690; }
                                continue $label691;
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
            $writeInline(out, this.proxies);
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
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
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
    
    public static final byte[] $classHash = new byte[] { 29, -105, -52, -42, 11,
    -78, 63, 59, 47, -89, -27, 116, 3, 92, -18, -24, 79, -52, 71, -113, -49,
    -32, -51, -52, 28, 21, 5, -113, -23, 0, 1, -65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524505527000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
