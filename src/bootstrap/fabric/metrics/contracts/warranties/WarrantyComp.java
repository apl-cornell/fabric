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
                boolean loop$var288 = loop;
                fabric.worker.transaction.TransactionManager $tm293 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff294 = 1;
                boolean $doBackoff295 = true;
                $label289: for (boolean $commit290 = false; !$commit290; ) {
                    if ($doBackoff295) {
                        if ($backoff294 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff294);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e291) {
                                    
                                }
                            }
                        }
                        if ($backoff294 < 5000) $backoff294 *= 1;
                    }
                    $doBackoff295 = $backoff294 <= 32 || !$doBackoff295;
                    $commit290 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        loop =
                          fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                             null) ||
                            !this.get$curVal().get$contract().valid();
                    }
                    catch (final fabric.worker.RetryException $e291) {
                        $commit290 = false;
                        continue $label289;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e291) {
                        $commit290 = false;
                        fabric.common.TransactionID $currentTid292 =
                          $tm293.getCurrentTid();
                        if ($e291.tid.isDescendantOf($currentTid292))
                            continue $label289;
                        if ($currentTid292.parent != null) throw $e291;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e291) {
                        $commit290 = false;
                        if ($tm293.checkForStaleObjects()) continue $label289;
                        throw new fabric.worker.AbortException($e291);
                    }
                    finally {
                        if ($commit290) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e291) {
                                $commit290 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e291) {
                                $commit290 = false;
                                fabric.common.TransactionID $currentTid292 =
                                  $tm293.getCurrentTid();
                                if ($currentTid292 != null) {
                                    if ($e291.tid.equals($currentTid292) ||
                                          !$e291.tid.isDescendantOf(
                                                       $currentTid292)) {
                                        throw $e291;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit290) {
                            { loop = loop$var288; }
                            continue $label289;
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
                    boolean loop$var296 = loop;
                    fabric.worker.transaction.TransactionManager $tm301 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff302 = 1;
                    boolean $doBackoff303 = true;
                    $label297: for (boolean $commit298 = false; !$commit298; ) {
                        if ($doBackoff303) {
                            if ($backoff302 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff302);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e299) {
                                        
                                    }
                                }
                            }
                            if ($backoff302 < 5000) $backoff302 *= 1;
                        }
                        $doBackoff303 = $backoff302 <= 32 || !$doBackoff303;
                        $commit298 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { this.set$curVal(newVal); }
                        catch (final fabric.worker.RetryException $e299) {
                            $commit298 = false;
                            continue $label297;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e299) {
                            $commit298 = false;
                            fabric.common.TransactionID $currentTid300 =
                              $tm301.getCurrentTid();
                            if ($e299.tid.isDescendantOf($currentTid300))
                                continue $label297;
                            if ($currentTid300.parent != null) throw $e299;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e299) {
                            $commit298 = false;
                            if ($tm301.checkForStaleObjects())
                                continue $label297;
                            throw new fabric.worker.AbortException($e299);
                        }
                        finally {
                            if ($commit298) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e299) {
                                    $commit298 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e299) {
                                    $commit298 = false;
                                    fabric.common.TransactionID $currentTid300 =
                                      $tm301.getCurrentTid();
                                    if ($currentTid300 != null) {
                                        if ($e299.tid.equals($currentTid300) ||
                                              !$e299.tid.isDescendantOf(
                                                           $currentTid300)) {
                                            throw $e299;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit298) {
                                { loop = loop$var296; }
                                continue $label297;
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
                    boolean loop$var304 = loop;
                    fabric.worker.transaction.TransactionManager $tm309 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff310 = 1;
                    boolean $doBackoff311 = true;
                    $label305: for (boolean $commit306 = false; !$commit306; ) {
                        if ($doBackoff311) {
                            if ($backoff310 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff310);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e307) {
                                        
                                    }
                                }
                            }
                            if ($backoff310 < 5000) $backoff310 *= 1;
                        }
                        $doBackoff311 = $backoff310 <= 32 || !$doBackoff311;
                        $commit306 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          this.get$curVal(),
                                                          null) ||
                                !this.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e307) {
                            $commit306 = false;
                            continue $label305;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e307) {
                            $commit306 = false;
                            fabric.common.TransactionID $currentTid308 =
                              $tm309.getCurrentTid();
                            if ($e307.tid.isDescendantOf($currentTid308))
                                continue $label305;
                            if ($currentTid308.parent != null) throw $e307;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e307) {
                            $commit306 = false;
                            if ($tm309.checkForStaleObjects())
                                continue $label305;
                            throw new fabric.worker.AbortException($e307);
                        }
                        finally {
                            if ($commit306) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e307) {
                                    $commit306 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e307) {
                                    $commit306 = false;
                                    fabric.common.TransactionID $currentTid308 =
                                      $tm309.getCurrentTid();
                                    if ($currentTid308 != null) {
                                        if ($e307.tid.equals($currentTid308) ||
                                              !$e307.tid.isDescendantOf(
                                                           $currentTid308)) {
                                            throw $e307;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit306) {
                                { loop = loop$var304; }
                                continue $label305;
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
    public static final long jlc$SourceLastModified$fabil = 1506965800000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
