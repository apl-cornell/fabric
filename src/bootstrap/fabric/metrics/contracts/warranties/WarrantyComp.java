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
        
        public static final byte[] $classHash = new byte[] { 28, -62, 27, -44,
        -8, -16, -113, 44, 42, -26, 89, 74, -47, 7, -94, -11, -11, -76, -20,
        -117, 116, -18, 73, -122, -87, 98, 28, 48, 99, -121, 107, 95 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1523829676000L;
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
                            tmp.get$proxies().add(rtn);
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
    public static final long jlc$SourceLastModified$fabil = 1523829676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
