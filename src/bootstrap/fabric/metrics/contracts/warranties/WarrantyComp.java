package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.metrics.Metric;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.Observer;
import java.util.logging.Level;

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
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.metrics.contracts.warranties.WarrantyValue apply(long time);
    
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
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1) {
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
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "Starting " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
            boolean loop = false;
            {
                boolean loop$var444 = loop;
                fabric.worker.transaction.TransactionManager $tm449 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff450 = 1;
                boolean $doBackoff451 = true;
                $label445: for (boolean $commit446 = false; !$commit446; ) {
                    if ($doBackoff451) {
                        if ($backoff450 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff450);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e447) {
                                    
                                }
                            }
                        }
                        if ($backoff450 < 5000) $backoff450 *= 1;
                    }
                    $doBackoff451 = $backoff450 <= 32 || !$doBackoff451;
                    $commit446 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        loop =
                          fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                             null) ||
                            !this.get$curVal().get$contract().valid();
                    }
                    catch (final fabric.worker.RetryException $e447) {
                        $commit446 = false;
                        continue $label445;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e447) {
                        $commit446 = false;
                        fabric.common.TransactionID $currentTid448 =
                          $tm449.getCurrentTid();
                        if ($e447.tid.isDescendantOf($currentTid448))
                            continue $label445;
                        if ($currentTid448.parent != null) throw $e447;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e447) {
                        $commit446 = false;
                        if ($tm449.checkForStaleObjects()) continue $label445;
                        throw new fabric.worker.AbortException($e447);
                    }
                    finally {
                        if ($commit446) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e447) {
                                $commit446 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e447) {
                                $commit446 = false;
                                fabric.common.TransactionID $currentTid448 =
                                  $tm449.getCurrentTid();
                                if ($currentTid448 != null) {
                                    if ($e447.tid.equals($currentTid448) ||
                                          !$e447.tid.isDescendantOf(
                                                       $currentTid448)) {
                                        throw $e447;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit446) {
                            { loop = loop$var444; }
                            continue $label445;
                        }
                    }
                }
            }
            while (loop) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "Iterating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
                if (!fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                        null)) {
                    this.get$curVal().get$contract().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.WarrantyComp)
                          this.$getProxy());
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "Updating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
                fabric.metrics.contracts.warranties.WarrantyValue newVal =
                  updatedVal(java.lang.System.currentTimeMillis());
                if (fabric.lang.Object._Proxy.idEquals(newVal.get$contract(),
                                                       null)) return newVal;
                {
                    boolean loop$var452 = loop;
                    fabric.worker.transaction.TransactionManager $tm457 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff458 = 1;
                    boolean $doBackoff459 = true;
                    $label453: for (boolean $commit454 = false; !$commit454; ) {
                        if ($doBackoff459) {
                            if ($backoff458 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff458);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e455) {
                                        
                                    }
                                }
                            }
                            if ($backoff458 < 5000) $backoff458 *= 1;
                        }
                        $doBackoff459 = $backoff458 <= 32 || !$doBackoff459;
                        $commit454 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { this.set$curVal(newVal); }
                        catch (final fabric.worker.RetryException $e455) {
                            $commit454 = false;
                            continue $label453;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e455) {
                            $commit454 = false;
                            fabric.common.TransactionID $currentTid456 =
                              $tm457.getCurrentTid();
                            if ($e455.tid.isDescendantOf($currentTid456))
                                continue $label453;
                            if ($currentTid456.parent != null) throw $e455;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e455) {
                            $commit454 = false;
                            if ($tm457.checkForStaleObjects())
                                continue $label453;
                            throw new fabric.worker.AbortException($e455);
                        }
                        finally {
                            if ($commit454) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e455) {
                                    $commit454 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e455) {
                                    $commit454 = false;
                                    fabric.common.TransactionID $currentTid456 =
                                      $tm457.getCurrentTid();
                                    if ($currentTid456 != null) {
                                        if ($e455.tid.equals($currentTid456) ||
                                              !$e455.tid.isDescendantOf(
                                                           $currentTid456)) {
                                            throw $e455;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit454) {
                                { loop = loop$var452; }
                                continue $label453;
                            }
                        }
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "Activating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
                this.get$curVal().get$contract().activate();
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "Updated to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$curVal(
                                                                     ))));
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "Observing " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
                this.get$curVal().get$contract().
                  addObserver((fabric.metrics.contracts.warranties.WarrantyComp)
                                this.$getProxy());
                {
                    boolean loop$var460 = loop;
                    fabric.worker.transaction.TransactionManager $tm465 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff466 = 1;
                    boolean $doBackoff467 = true;
                    $label461: for (boolean $commit462 = false; !$commit462; ) {
                        if ($doBackoff467) {
                            if ($backoff466 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff466);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e463) {
                                        
                                    }
                                }
                            }
                            if ($backoff466 < 5000) $backoff466 *= 1;
                        }
                        $doBackoff467 = $backoff466 <= 32 || !$doBackoff467;
                        $commit462 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          this.get$curVal(),
                                                          null) ||
                                !this.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e463) {
                            $commit462 = false;
                            continue $label461;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e463) {
                            $commit462 = false;
                            fabric.common.TransactionID $currentTid464 =
                              $tm465.getCurrentTid();
                            if ($e463.tid.isDescendantOf($currentTid464))
                                continue $label461;
                            if ($currentTid464.parent != null) throw $e463;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e463) {
                            $commit462 = false;
                            if ($tm465.checkForStaleObjects())
                                continue $label461;
                            throw new fabric.worker.AbortException($e463);
                        }
                        finally {
                            if ($commit462) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e463) {
                                    $commit462 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e463) {
                                    $commit462 = false;
                                    fabric.common.TransactionID $currentTid464 =
                                      $tm465.getCurrentTid();
                                    if ($currentTid464 != null) {
                                        if ($e463.tid.equals($currentTid464) ||
                                              !$e463.tid.isDescendantOf(
                                                           $currentTid464)) {
                                            throw $e463;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit462) {
                                { loop = loop$var460; }
                                continue $label461;
                            }
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "Finished " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
            return this.get$curVal();
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
            return fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                      null) ||
              !this.get$curVal().get$contract().valid(time);
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
    
    public static final byte[] $classHash = new byte[] { 5, -122, -120, -41,
    -70, 30, 20, 78, 11, -128, -92, -56, 86, 99, -39, -13, -21, 50, 73, -44, 83,
    17, -90, -123, -122, 25, -79, -21, -91, -16, 75, -97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
