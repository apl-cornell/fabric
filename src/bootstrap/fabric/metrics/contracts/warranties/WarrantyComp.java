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
                          rtn$var197 = rtn;
                        fabric.worker.transaction.TransactionManager $tm203 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled206 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff204 = 1;
                        boolean $doBackoff205 = true;
                        boolean $retry200 = true;
                        $label198: for (boolean $commit199 = false; !$commit199;
                                        ) {
                            if ($backoffEnabled206) {
                                if ($doBackoff205) {
                                    if ($backoff204 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff204);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e201) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff204 < 5000) $backoff204 *= 2;
                                }
                                $doBackoff205 = $backoff204 <= 32 ||
                                                  !$doBackoff205;
                            }
                            $commit199 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e201) {
                                $commit199 = false;
                                continue $label198;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e201) {
                                $commit199 = false;
                                fabric.common.TransactionID $currentTid202 =
                                  $tm203.getCurrentTid();
                                if ($e201.tid.isDescendantOf($currentTid202))
                                    continue $label198;
                                if ($currentTid202.parent != null) {
                                    $retry200 = false;
                                    throw $e201;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e201) {
                                $commit199 = false;
                                if ($tm203.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid202 =
                                  $tm203.getCurrentTid();
                                if ($e201.tid.isDescendantOf($currentTid202)) {
                                    $retry200 = true;
                                }
                                else if ($currentTid202.parent != null) {
                                    $retry200 = false;
                                    throw $e201;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e201) {
                                $commit199 = false;
                                if ($tm203.checkForStaleObjects())
                                    continue $label198;
                                $retry200 = false;
                                throw new fabric.worker.AbortException($e201);
                            }
                            finally {
                                if ($commit199) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e201) {
                                        $commit199 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e201) {
                                        $commit199 = false;
                                        fabric.common.TransactionID
                                          $currentTid202 =
                                          $tm203.getCurrentTid();
                                        if ($currentTid202 != null) {
                                            if ($e201.tid.equals(
                                                            $currentTid202) ||
                                                  !$e201.tid.
                                                  isDescendantOf(
                                                    $currentTid202)) {
                                                throw $e201;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit199 && $retry200) {
                                    { rtn = rtn$var197; }
                                    continue $label198;
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
        public static final long jlc$SourceLastModified$fabil = 1521832678000L;
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
                    boolean loop$var207 = loop;
                    fabric.worker.transaction.TransactionManager $tm213 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled216 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff214 = 1;
                    boolean $doBackoff215 = true;
                    boolean $retry210 = true;
                    $label208: for (boolean $commit209 = false; !$commit209; ) {
                        if ($backoffEnabled216) {
                            if ($doBackoff215) {
                                if ($backoff214 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff214);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e211) {
                                            
                                        }
                                    }
                                }
                                if ($backoff214 < 5000) $backoff214 *= 2;
                            }
                            $doBackoff215 = $backoff214 <= 32 || !$doBackoff215;
                        }
                        $commit209 = true;
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
                        catch (final fabric.worker.RetryException $e211) {
                            $commit209 = false;
                            continue $label208;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e211) {
                            $commit209 = false;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212))
                                continue $label208;
                            if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid212 =
                              $tm213.getCurrentTid();
                            if ($e211.tid.isDescendantOf($currentTid212)) {
                                $retry210 = true;
                            }
                            else if ($currentTid212.parent != null) {
                                $retry210 = false;
                                throw $e211;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e211) {
                            $commit209 = false;
                            if ($tm213.checkForStaleObjects())
                                continue $label208;
                            $retry210 = false;
                            throw new fabric.worker.AbortException($e211);
                        }
                        finally {
                            if ($commit209) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e211) {
                                    $commit209 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e211) {
                                    $commit209 = false;
                                    fabric.common.TransactionID $currentTid212 =
                                      $tm213.getCurrentTid();
                                    if ($currentTid212 != null) {
                                        if ($e211.tid.equals($currentTid212) ||
                                              !$e211.tid.isDescendantOf(
                                                           $currentTid212)) {
                                            throw $e211;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit209 && $retry210) {
                                { loop = loop$var207; }
                                continue $label208;
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
                        boolean loop$var217 = loop;
                        fabric.worker.transaction.TransactionManager $tm223 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled226 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff224 = 1;
                        boolean $doBackoff225 = true;
                        boolean $retry220 = true;
                        $label218: for (boolean $commit219 = false; !$commit219;
                                        ) {
                            if ($backoffEnabled226) {
                                if ($doBackoff225) {
                                    if ($backoff224 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff224);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e221) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff224 < 5000) $backoff224 *= 2;
                                }
                                $doBackoff225 = $backoff224 <= 32 ||
                                                  !$doBackoff225;
                            }
                            $commit219 = true;
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
                            catch (final fabric.worker.RetryException $e221) {
                                $commit219 = false;
                                continue $label218;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e221) {
                                $commit219 = false;
                                fabric.common.TransactionID $currentTid222 =
                                  $tm223.getCurrentTid();
                                if ($e221.tid.isDescendantOf($currentTid222))
                                    continue $label218;
                                if ($currentTid222.parent != null) {
                                    $retry220 = false;
                                    throw $e221;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e221) {
                                $commit219 = false;
                                if ($tm223.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid222 =
                                  $tm223.getCurrentTid();
                                if ($e221.tid.isDescendantOf($currentTid222)) {
                                    $retry220 = true;
                                }
                                else if ($currentTid222.parent != null) {
                                    $retry220 = false;
                                    throw $e221;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e221) {
                                $commit219 = false;
                                if ($tm223.checkForStaleObjects())
                                    continue $label218;
                                $retry220 = false;
                                throw new fabric.worker.AbortException($e221);
                            }
                            finally {
                                if ($commit219) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e221) {
                                        $commit219 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e221) {
                                        $commit219 = false;
                                        fabric.common.TransactionID
                                          $currentTid222 =
                                          $tm223.getCurrentTid();
                                        if ($currentTid222 != null) {
                                            if ($e221.tid.equals(
                                                            $currentTid222) ||
                                                  !$e221.tid.
                                                  isDescendantOf(
                                                    $currentTid222)) {
                                                throw $e221;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit219 && $retry220) {
                                    { loop = loop$var217; }
                                    continue $label218;
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
                        boolean loop$var227 = loop;
                        fabric.worker.transaction.TransactionManager $tm233 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled236 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff234 = 1;
                        boolean $doBackoff235 = true;
                        boolean $retry230 = true;
                        $label228: for (boolean $commit229 = false; !$commit229;
                                        ) {
                            if ($backoffEnabled236) {
                                if ($doBackoff235) {
                                    if ($backoff234 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff234);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e231) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff234 < 5000) $backoff234 *= 2;
                                }
                                $doBackoff235 = $backoff234 <= 32 ||
                                                  !$doBackoff235;
                            }
                            $commit229 = true;
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
                            catch (final fabric.worker.RetryException $e231) {
                                $commit229 = false;
                                continue $label228;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e231) {
                                $commit229 = false;
                                fabric.common.TransactionID $currentTid232 =
                                  $tm233.getCurrentTid();
                                if ($e231.tid.isDescendantOf($currentTid232))
                                    continue $label228;
                                if ($currentTid232.parent != null) {
                                    $retry230 = false;
                                    throw $e231;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e231) {
                                $commit229 = false;
                                if ($tm233.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid232 =
                                  $tm233.getCurrentTid();
                                if ($e231.tid.isDescendantOf($currentTid232)) {
                                    $retry230 = true;
                                }
                                else if ($currentTid232.parent != null) {
                                    $retry230 = false;
                                    throw $e231;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e231) {
                                $commit229 = false;
                                if ($tm233.checkForStaleObjects())
                                    continue $label228;
                                $retry230 = false;
                                throw new fabric.worker.AbortException($e231);
                            }
                            finally {
                                if ($commit229) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e231) {
                                        $commit229 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e231) {
                                        $commit229 = false;
                                        fabric.common.TransactionID
                                          $currentTid232 =
                                          $tm233.getCurrentTid();
                                        if ($currentTid232 != null) {
                                            if ($e231.tid.equals(
                                                            $currentTid232) ||
                                                  !$e231.tid.
                                                  isDescendantOf(
                                                    $currentTid232)) {
                                                throw $e231;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit229 && $retry230) {
                                    { loop = loop$var227; }
                                    continue $label228;
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
                      rtn$var237 = rtn;
                    fabric.worker.transaction.TransactionManager $tm243 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled246 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff244 = 1;
                    boolean $doBackoff245 = true;
                    boolean $retry240 = true;
                    $label238: for (boolean $commit239 = false; !$commit239; ) {
                        if ($backoffEnabled246) {
                            if ($doBackoff245) {
                                if ($backoff244 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff244);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e241) {
                                            
                                        }
                                    }
                                }
                                if ($backoff244 < 5000) $backoff244 *= 2;
                            }
                            $doBackoff245 = $backoff244 <= 32 || !$doBackoff245;
                        }
                        $commit239 = true;
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
                        catch (final fabric.worker.RetryException $e241) {
                            $commit239 = false;
                            continue $label238;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e241) {
                            $commit239 = false;
                            fabric.common.TransactionID $currentTid242 =
                              $tm243.getCurrentTid();
                            if ($e241.tid.isDescendantOf($currentTid242))
                                continue $label238;
                            if ($currentTid242.parent != null) {
                                $retry240 = false;
                                throw $e241;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e241) {
                            $commit239 = false;
                            if ($tm243.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid242 =
                              $tm243.getCurrentTid();
                            if ($e241.tid.isDescendantOf($currentTid242)) {
                                $retry240 = true;
                            }
                            else if ($currentTid242.parent != null) {
                                $retry240 = false;
                                throw $e241;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e241) {
                            $commit239 = false;
                            if ($tm243.checkForStaleObjects())
                                continue $label238;
                            $retry240 = false;
                            throw new fabric.worker.AbortException($e241);
                        }
                        finally {
                            if ($commit239) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e241) {
                                    $commit239 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e241) {
                                    $commit239 = false;
                                    fabric.common.TransactionID $currentTid242 =
                                      $tm243.getCurrentTid();
                                    if ($currentTid242 != null) {
                                        if ($e241.tid.equals($currentTid242) ||
                                              !$e241.tid.isDescendantOf(
                                                           $currentTid242)) {
                                            throw $e241;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit239 && $retry240) {
                                { rtn = rtn$var237; }
                                continue $label238;
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
    public static final long jlc$SourceLastModified$fabil = 1521832678000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
