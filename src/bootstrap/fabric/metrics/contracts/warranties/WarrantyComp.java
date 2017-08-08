package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.Observer;

/**
 * A computation that uses {@link Contract}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Contract}.
 * This helps to ensure that the {@link Contract} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.lang.Object {
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(fabric.metrics.contracts.warranties.WarrantyValue val);
    
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
   */
    public fabric.lang.Object apply(long time);
    
    public fabric.util.Set getLeafSubjects();
    
    public boolean handleUpdates();
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
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
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.lang.Object apply(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
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
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
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
        
        /** The currently cached result. */
        private fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
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
   */
        public fabric.lang.Object apply(long time) {
            if (fabric.lang.Object._Proxy.idEquals(this.get$curVal(), null) ||
                  !this.get$curVal().get$contract().isActivated() ||
                  this.get$curVal().get$contract().stale(time)) {
                if (!fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                        null)) {
                    {
                        fabric.worker.transaction.TransactionManager $tm11 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff12 = 1;
                        boolean $doBackoff13 = true;
                        $label7: for (boolean $commit8 = false; !$commit8; ) {
                            if ($doBackoff13) {
                                if ($backoff12 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff12);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e9) {
                                            
                                        }
                                    }
                                }
                                if ($backoff12 < 5000) $backoff12 *= 2;
                            }
                            $doBackoff13 = $backoff12 <= 32 || !$doBackoff13;
                            $commit8 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                this.get$curVal().get$contract().
                                  removeObserver(
                                    (fabric.metrics.contracts.warranties.WarrantyComp)
                                      this.$getProxy());
                            }
                            catch (final fabric.worker.RetryException $e9) {
                                $commit8 = false;
                                continue $label7;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e9) {
                                $commit8 = false;
                                fabric.common.TransactionID $currentTid10 =
                                  $tm11.getCurrentTid();
                                if ($e9.tid.isDescendantOf($currentTid10))
                                    continue $label7;
                                if ($currentTid10.parent != null) throw $e9;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e9) {
                                $commit8 = false;
                                if ($tm11.checkForStaleObjects())
                                    continue $label7;
                                throw new fabric.worker.AbortException($e9);
                            }
                            finally {
                                if ($commit8) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e9) {
                                        $commit8 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e9) {
                                        $commit8 = false;
                                        fabric.common.TransactionID
                                          $currentTid10 = $tm11.getCurrentTid();
                                        if ($currentTid10 != null) {
                                            if ($e9.tid.equals($currentTid10) ||
                                                  !$e9.tid.isDescendantOf(
                                                             $currentTid10)) {
                                                throw $e9;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit8) {  }
                            }
                        }
                    }
                }
                this.set$curVal(updatedVal(time));
                this.get$curVal().get$contract().activate();
                {
                    fabric.worker.transaction.TransactionManager $tm18 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff19 = 1;
                    boolean $doBackoff20 = true;
                    $label14: for (boolean $commit15 = false; !$commit15; ) {
                        if ($doBackoff20) {
                            if ($backoff19 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff19);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e16) {
                                        
                                    }
                                }
                            }
                            if ($backoff19 < 5000) $backoff19 *= 2;
                        }
                        $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                        $commit15 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            this.get$curVal().get$contract().
                              addObserver(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy());
                        }
                        catch (final fabric.worker.RetryException $e16) {
                            $commit15 = false;
                            continue $label14;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e16) {
                            $commit15 = false;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17))
                                continue $label14;
                            if ($currentTid17.parent != null) throw $e16;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e16) {
                            $commit15 = false;
                            if ($tm18.checkForStaleObjects()) continue $label14;
                            throw new fabric.worker.AbortException($e16);
                        }
                        finally {
                            if ($commit15) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e16) {
                                    $commit15 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e16) {
                                    $commit15 = false;
                                    fabric.common.TransactionID $currentTid17 =
                                      $tm18.getCurrentTid();
                                    if ($currentTid17 != null) {
                                        if ($e16.tid.equals($currentTid17) ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17)) {
                                            throw $e16;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit15) {  }
                        }
                    }
                }
            }
            return this.get$curVal().get$value();
        }
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curVal(), null))
                return this.get$curVal().get$contract().getLeafSubjects();
            return ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(this.$getStore()).$getProxy(
                                                                        )).
              fabric$util$HashSet$();
        }
        
        public boolean handleUpdates() {
            long time = java.lang.System.currentTimeMillis();
            return !fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                       null) &&
              this.get$curVal().get$contract().valid(time) &&
              !fabric.lang.Object._Proxy.idEquals(this.get$curVal().get$value(),
                                                  apply(time));
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            fabric$lang$Object$();
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
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
    
    public static final byte[] $classHash = new byte[] { 22, -68, 55, -43, 78,
    115, -97, 121, -96, 72, -103, -10, -125, -53, 85, 48, -52, 59, -101, 47,
    123, -62, 81, -24, -11, 5, 76, -56, -80, -14, 46, -101 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502141466000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
