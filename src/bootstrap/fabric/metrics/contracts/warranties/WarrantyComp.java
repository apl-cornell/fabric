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
                java.util.logging.Level.FINER,
                "Starting " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
            boolean loop = false;
            {
                boolean loop$var433 = loop;
                fabric.worker.transaction.TransactionManager $tm438 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled441 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff439 = 1;
                boolean $doBackoff440 = true;
                $label434: for (boolean $commit435 = false; !$commit435; ) {
                    if ($backoffEnabled441) {
                        if ($doBackoff440) {
                            if ($backoff439 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff439);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e436) {
                                        
                                    }
                                }
                            }
                            if ($backoff439 < 5000) $backoff439 *= 2;
                        }
                        $doBackoff440 = $backoff439 <= 32 || !$doBackoff440;
                    }
                    $commit435 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        loop =
                          fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                             null) ||
                            !this.get$curVal().get$contract().valid();
                    }
                    catch (final fabric.worker.RetryException $e436) {
                        $commit435 = false;
                        continue $label434;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e436) {
                        $commit435 = false;
                        fabric.common.TransactionID $currentTid437 =
                          $tm438.getCurrentTid();
                        if ($e436.tid.isDescendantOf($currentTid437))
                            continue $label434;
                        if ($currentTid437.parent != null) throw $e436;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e436) {
                        $commit435 = false;
                        if ($tm438.checkForStaleObjects()) continue $label434;
                        throw new fabric.worker.AbortException($e436);
                    }
                    finally {
                        if ($commit435) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e436) {
                                $commit435 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e436) {
                                $commit435 = false;
                                fabric.common.TransactionID $currentTid437 =
                                  $tm438.getCurrentTid();
                                if ($currentTid437 != null) {
                                    if ($e436.tid.equals($currentTid437) ||
                                          !$e436.tid.isDescendantOf(
                                                       $currentTid437)) {
                                        throw $e436;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit435) {
                            { loop = loop$var433; }
                            continue $label434;
                        }
                    }
                }
            }
            while (loop) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
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
                    java.util.logging.Level.FINER,
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
                    boolean loop$var442 = loop;
                    fabric.worker.transaction.TransactionManager $tm447 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled450 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff448 = 1;
                    boolean $doBackoff449 = true;
                    $label443: for (boolean $commit444 = false; !$commit444; ) {
                        if ($backoffEnabled450) {
                            if ($doBackoff449) {
                                if ($backoff448 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff448);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e445) {
                                            
                                        }
                                    }
                                }
                                if ($backoff448 < 5000) $backoff448 *= 2;
                            }
                            $doBackoff449 = $backoff448 <= 32 || !$doBackoff449;
                        }
                        $commit444 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { this.set$curVal(newVal); }
                        catch (final fabric.worker.RetryException $e445) {
                            $commit444 = false;
                            continue $label443;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e445) {
                            $commit444 = false;
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($e445.tid.isDescendantOf($currentTid446))
                                continue $label443;
                            if ($currentTid446.parent != null) throw $e445;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e445) {
                            $commit444 = false;
                            if ($tm447.checkForStaleObjects())
                                continue $label443;
                            throw new fabric.worker.AbortException($e445);
                        }
                        finally {
                            if ($commit444) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e445) {
                                    $commit444 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e445) {
                                    $commit444 = false;
                                    fabric.common.TransactionID $currentTid446 =
                                      $tm447.getCurrentTid();
                                    if ($currentTid446 != null) {
                                        if ($e445.tid.equals($currentTid446) ||
                                              !$e445.tid.isDescendantOf(
                                                           $currentTid446)) {
                                            throw $e445;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit444) {
                                { loop = loop$var442; }
                                continue $label443;
                            }
                        }
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Activating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.WarrantyComp)
                                  this.$getProxy())));
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     this.get$curVal(
                                                            ).get$contract(
                                                                ).$getStore(
                                                                    ).name());
                ((fabric.metrics.contracts.MetricContract._Proxy)
                   this.get$curVal().get$contract()).activate$remote(w, null);
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Updated to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$curVal(
                                                                     ))));
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
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
                    boolean loop$var451 = loop;
                    fabric.worker.transaction.TransactionManager $tm456 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled459 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff457 = 1;
                    boolean $doBackoff458 = true;
                    $label452: for (boolean $commit453 = false; !$commit453; ) {
                        if ($backoffEnabled459) {
                            if ($doBackoff458) {
                                if ($backoff457 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff457);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e454) {
                                            
                                        }
                                    }
                                }
                                if ($backoff457 < 5000) $backoff457 *= 2;
                            }
                            $doBackoff458 = $backoff457 <= 32 || !$doBackoff458;
                        }
                        $commit453 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          this.get$curVal(),
                                                          null) ||
                                !this.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e454) {
                            $commit453 = false;
                            continue $label452;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e454) {
                            $commit453 = false;
                            fabric.common.TransactionID $currentTid455 =
                              $tm456.getCurrentTid();
                            if ($e454.tid.isDescendantOf($currentTid455))
                                continue $label452;
                            if ($currentTid455.parent != null) throw $e454;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e454) {
                            $commit453 = false;
                            if ($tm456.checkForStaleObjects())
                                continue $label452;
                            throw new fabric.worker.AbortException($e454);
                        }
                        finally {
                            if ($commit453) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e454) {
                                    $commit453 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e454) {
                                    $commit453 = false;
                                    fabric.common.TransactionID $currentTid455 =
                                      $tm456.getCurrentTid();
                                    if ($currentTid455 != null) {
                                        if ($e454.tid.equals($currentTid455) ||
                                              !$e454.tid.isDescendantOf(
                                                           $currentTid455)) {
                                            throw $e454;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit453) {
                                { loop = loop$var451; }
                                continue $label452;
                            }
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
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
    
    public static final byte[] $classHash = new byte[] { 125, -125, 17, 100, 15,
    -118, 16, -112, 49, -22, 44, -19, -96, 7, -14, 13, -3, -116, -56, -28, 88,
    -126, -3, 50, -22, -81, 2, 83, -97, -90, -120, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
