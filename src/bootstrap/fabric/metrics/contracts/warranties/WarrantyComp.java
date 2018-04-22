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
                          rtn$var688 = rtn;
                        fabric.worker.transaction.TransactionManager $tm694 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled697 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff695 = 1;
                        boolean $doBackoff696 = true;
                        boolean $retry691 = true;
                        $label689: for (boolean $commit690 = false; !$commit690;
                                        ) {
                            if ($backoffEnabled697) {
                                if ($doBackoff696) {
                                    if ($backoff695 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff695);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e692) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff695 < 5000) $backoff695 *= 2;
                                }
                                $doBackoff696 = $backoff695 <= 32 ||
                                                  !$doBackoff696;
                            }
                            $commit690 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e692) {
                                $commit690 = false;
                                continue $label689;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e692) {
                                $commit690 = false;
                                fabric.common.TransactionID $currentTid693 =
                                  $tm694.getCurrentTid();
                                if ($e692.tid.isDescendantOf($currentTid693))
                                    continue $label689;
                                if ($currentTid693.parent != null) {
                                    $retry691 = false;
                                    throw $e692;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e692) {
                                $commit690 = false;
                                if ($tm694.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid693 =
                                  $tm694.getCurrentTid();
                                if ($e692.tid.isDescendantOf($currentTid693)) {
                                    $retry691 = true;
                                }
                                else if ($currentTid693.parent != null) {
                                    $retry691 = false;
                                    throw $e692;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e692) {
                                $commit690 = false;
                                if ($tm694.checkForStaleObjects())
                                    continue $label689;
                                $retry691 = false;
                                throw new fabric.worker.AbortException($e692);
                            }
                            finally {
                                if ($commit690) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e692) {
                                        $commit690 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e692) {
                                        $commit690 = false;
                                        fabric.common.TransactionID
                                          $currentTid693 =
                                          $tm694.getCurrentTid();
                                        if ($currentTid693 != null) {
                                            if ($e692.tid.equals(
                                                            $currentTid693) ||
                                                  !$e692.tid.
                                                  isDescendantOf(
                                                    $currentTid693)) {
                                                throw $e692;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit690 && $retry691) {
                                    { rtn = rtn$var688; }
                                    continue $label689;
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
        
        public static final byte[] $classHash = new byte[] { 60, -32, 50, 71,
        -72, -12, -108, 54, 14, -30, -25, -35, 46, -28, -126, -43, -76, 100, 1,
        48, -27, 75, 22, -56, -83, 16, 120, 27, 79, 85, 49, 40 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524349469000L;
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
                    boolean loop$var698 = loop;
                    fabric.worker.transaction.TransactionManager $tm704 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled707 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff705 = 1;
                    boolean $doBackoff706 = true;
                    boolean $retry701 = true;
                    $label699: for (boolean $commit700 = false; !$commit700; ) {
                        if ($backoffEnabled707) {
                            if ($doBackoff706) {
                                if ($backoff705 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff705);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e702) {
                                            
                                        }
                                    }
                                }
                                if ($backoff705 < 5000) $backoff705 *= 2;
                            }
                            $doBackoff706 = $backoff705 <= 32 || !$doBackoff706;
                        }
                        $commit700 = true;
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
                        catch (final fabric.worker.RetryException $e702) {
                            $commit700 = false;
                            continue $label699;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e702) {
                            $commit700 = false;
                            fabric.common.TransactionID $currentTid703 =
                              $tm704.getCurrentTid();
                            if ($e702.tid.isDescendantOf($currentTid703))
                                continue $label699;
                            if ($currentTid703.parent != null) {
                                $retry701 = false;
                                throw $e702;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e702) {
                            $commit700 = false;
                            if ($tm704.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid703 =
                              $tm704.getCurrentTid();
                            if ($e702.tid.isDescendantOf($currentTid703)) {
                                $retry701 = true;
                            }
                            else if ($currentTid703.parent != null) {
                                $retry701 = false;
                                throw $e702;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e702) {
                            $commit700 = false;
                            if ($tm704.checkForStaleObjects())
                                continue $label699;
                            $retry701 = false;
                            throw new fabric.worker.AbortException($e702);
                        }
                        finally {
                            if ($commit700) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e702) {
                                    $commit700 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e702) {
                                    $commit700 = false;
                                    fabric.common.TransactionID $currentTid703 =
                                      $tm704.getCurrentTid();
                                    if ($currentTid703 != null) {
                                        if ($e702.tid.equals($currentTid703) ||
                                              !$e702.tid.isDescendantOf(
                                                           $currentTid703)) {
                                            throw $e702;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit700 && $retry701) {
                                { loop = loop$var698; }
                                continue $label699;
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
                        boolean loop$var708 = loop;
                        fabric.worker.transaction.TransactionManager $tm714 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled717 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff715 = 1;
                        boolean $doBackoff716 = true;
                        boolean $retry711 = true;
                        $label709: for (boolean $commit710 = false; !$commit710;
                                        ) {
                            if ($backoffEnabled717) {
                                if ($doBackoff716) {
                                    if ($backoff715 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff715);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e712) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff715 < 5000) $backoff715 *= 2;
                                }
                                $doBackoff716 = $backoff715 <= 32 ||
                                                  !$doBackoff716;
                            }
                            $commit710 = true;
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
                            catch (final fabric.worker.RetryException $e712) {
                                $commit710 = false;
                                continue $label709;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e712) {
                                $commit710 = false;
                                fabric.common.TransactionID $currentTid713 =
                                  $tm714.getCurrentTid();
                                if ($e712.tid.isDescendantOf($currentTid713))
                                    continue $label709;
                                if ($currentTid713.parent != null) {
                                    $retry711 = false;
                                    throw $e712;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e712) {
                                $commit710 = false;
                                if ($tm714.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid713 =
                                  $tm714.getCurrentTid();
                                if ($e712.tid.isDescendantOf($currentTid713)) {
                                    $retry711 = true;
                                }
                                else if ($currentTid713.parent != null) {
                                    $retry711 = false;
                                    throw $e712;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e712) {
                                $commit710 = false;
                                if ($tm714.checkForStaleObjects())
                                    continue $label709;
                                $retry711 = false;
                                throw new fabric.worker.AbortException($e712);
                            }
                            finally {
                                if ($commit710) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e712) {
                                        $commit710 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e712) {
                                        $commit710 = false;
                                        fabric.common.TransactionID
                                          $currentTid713 =
                                          $tm714.getCurrentTid();
                                        if ($currentTid713 != null) {
                                            if ($e712.tid.equals(
                                                            $currentTid713) ||
                                                  !$e712.tid.
                                                  isDescendantOf(
                                                    $currentTid713)) {
                                                throw $e712;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit710 && $retry711) {
                                    { loop = loop$var708; }
                                    continue $label709;
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
                        boolean loop$var718 = loop;
                        fabric.worker.transaction.TransactionManager $tm724 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled727 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff725 = 1;
                        boolean $doBackoff726 = true;
                        boolean $retry721 = true;
                        $label719: for (boolean $commit720 = false; !$commit720;
                                        ) {
                            if ($backoffEnabled727) {
                                if ($doBackoff726) {
                                    if ($backoff725 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff725);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e722) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff725 < 5000) $backoff725 *= 2;
                                }
                                $doBackoff726 = $backoff725 <= 32 ||
                                                  !$doBackoff726;
                            }
                            $commit720 = true;
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
                            catch (final fabric.worker.RetryException $e722) {
                                $commit720 = false;
                                continue $label719;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e722) {
                                $commit720 = false;
                                fabric.common.TransactionID $currentTid723 =
                                  $tm724.getCurrentTid();
                                if ($e722.tid.isDescendantOf($currentTid723))
                                    continue $label719;
                                if ($currentTid723.parent != null) {
                                    $retry721 = false;
                                    throw $e722;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e722) {
                                $commit720 = false;
                                if ($tm724.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid723 =
                                  $tm724.getCurrentTid();
                                if ($e722.tid.isDescendantOf($currentTid723)) {
                                    $retry721 = true;
                                }
                                else if ($currentTid723.parent != null) {
                                    $retry721 = false;
                                    throw $e722;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e722) {
                                $commit720 = false;
                                if ($tm724.checkForStaleObjects())
                                    continue $label719;
                                $retry721 = false;
                                throw new fabric.worker.AbortException($e722);
                            }
                            finally {
                                if ($commit720) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e722) {
                                        $commit720 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e722) {
                                        $commit720 = false;
                                        fabric.common.TransactionID
                                          $currentTid723 =
                                          $tm724.getCurrentTid();
                                        if ($currentTid723 != null) {
                                            if ($e722.tid.equals(
                                                            $currentTid723) ||
                                                  !$e722.tid.
                                                  isDescendantOf(
                                                    $currentTid723)) {
                                                throw $e722;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit720 && $retry721) {
                                    { loop = loop$var718; }
                                    continue $label719;
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
                      rtn$var728 = rtn;
                    fabric.worker.transaction.TransactionManager $tm734 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled737 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff735 = 1;
                    boolean $doBackoff736 = true;
                    boolean $retry731 = true;
                    $label729: for (boolean $commit730 = false; !$commit730; ) {
                        if ($backoffEnabled737) {
                            if ($doBackoff736) {
                                if ($backoff735 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff735);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e732) {
                                            
                                        }
                                    }
                                }
                                if ($backoff735 < 5000) $backoff735 *= 2;
                            }
                            $doBackoff736 = $backoff735 <= 32 || !$doBackoff736;
                        }
                        $commit730 = true;
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
                        catch (final fabric.worker.RetryException $e732) {
                            $commit730 = false;
                            continue $label729;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e732) {
                            $commit730 = false;
                            fabric.common.TransactionID $currentTid733 =
                              $tm734.getCurrentTid();
                            if ($e732.tid.isDescendantOf($currentTid733))
                                continue $label729;
                            if ($currentTid733.parent != null) {
                                $retry731 = false;
                                throw $e732;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e732) {
                            $commit730 = false;
                            if ($tm734.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid733 =
                              $tm734.getCurrentTid();
                            if ($e732.tid.isDescendantOf($currentTid733)) {
                                $retry731 = true;
                            }
                            else if ($currentTid733.parent != null) {
                                $retry731 = false;
                                throw $e732;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e732) {
                            $commit730 = false;
                            if ($tm734.checkForStaleObjects())
                                continue $label729;
                            $retry731 = false;
                            throw new fabric.worker.AbortException($e732);
                        }
                        finally {
                            if ($commit730) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e732) {
                                    $commit730 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e732) {
                                    $commit730 = false;
                                    fabric.common.TransactionID $currentTid733 =
                                      $tm734.getCurrentTid();
                                    if ($currentTid733 != null) {
                                        if ($e732.tid.equals($currentTid733) ||
                                              !$e732.tid.isDescendantOf(
                                                           $currentTid733)) {
                                            throw $e732;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit730 && $retry731) {
                                { rtn = rtn$var728; }
                                continue $label729;
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
    
    public static final byte[] $classHash = new byte[] { -1, 37, -17, 82, 104,
    -2, 81, -4, -115, 53, 46, -95, -103, 96, 54, -44, -128, -2, -89, 56, -98,
    120, 44, -30, 83, -83, 13, 31, 29, 73, 83, 91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349469000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
