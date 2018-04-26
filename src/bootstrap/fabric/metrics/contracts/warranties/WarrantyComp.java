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
        
        public static final byte[] $classHash = new byte[] { 49, 46, -16, 12,
        80, 5, 95, 83, 11, 100, -18, -45, 58, 95, 46, -15, 124, -113, 88, 26,
        -17, 67, -11, -74, 76, 47, -97, -104, -24, -20, 27, -11 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524675608000L;
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 77, -105, 97, 63, 123,
    -9, 52, 113, -112, 11, 73, -105, 53, -128, -59, 28, -118, 14, 93, -85, 85,
    -125, -85, -125, 58, -36, 95, -47, 86, 26, -114, 38 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
