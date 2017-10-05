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
                boolean loop$var488 = loop;
                fabric.worker.transaction.TransactionManager $tm493 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled496 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff494 = 1;
                boolean $doBackoff495 = true;
                $label489: for (boolean $commit490 = false; !$commit490; ) {
                    if ($backoffEnabled496) {
                        if ($doBackoff495) {
                            if ($backoff494 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff494);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e491) {
                                        
                                    }
                                }
                            }
                            if ($backoff494 < 5000) $backoff494 *= 2;
                        }
                        $doBackoff495 = $backoff494 <= 32 || !$doBackoff495;
                    }
                    $commit490 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        loop =
                          fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                             null) ||
                            !this.get$curVal().get$contract().valid();
                    }
                    catch (final fabric.worker.RetryException $e491) {
                        $commit490 = false;
                        continue $label489;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e491) {
                        $commit490 = false;
                        fabric.common.TransactionID $currentTid492 =
                          $tm493.getCurrentTid();
                        if ($e491.tid.isDescendantOf($currentTid492))
                            continue $label489;
                        if ($currentTid492.parent != null) throw $e491;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e491) {
                        $commit490 = false;
                        if ($tm493.checkForStaleObjects()) continue $label489;
                        throw new fabric.worker.AbortException($e491);
                    }
                    finally {
                        if ($commit490) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e491) {
                                $commit490 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e491) {
                                $commit490 = false;
                                fabric.common.TransactionID $currentTid492 =
                                  $tm493.getCurrentTid();
                                if ($currentTid492 != null) {
                                    if ($e491.tid.equals($currentTid492) ||
                                          !$e491.tid.isDescendantOf(
                                                       $currentTid492)) {
                                        throw $e491;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit490) {
                            { loop = loop$var488; }
                            continue $label489;
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
                    boolean loop$var497 = loop;
                    fabric.worker.transaction.TransactionManager $tm502 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled505 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff503 = 1;
                    boolean $doBackoff504 = true;
                    $label498: for (boolean $commit499 = false; !$commit499; ) {
                        if ($backoffEnabled505) {
                            if ($doBackoff504) {
                                if ($backoff503 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff503);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e500) {
                                            
                                        }
                                    }
                                }
                                if ($backoff503 < 5000) $backoff503 *= 2;
                            }
                            $doBackoff504 = $backoff503 <= 32 || !$doBackoff504;
                        }
                        $commit499 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { this.set$curVal(newVal); }
                        catch (final fabric.worker.RetryException $e500) {
                            $commit499 = false;
                            continue $label498;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e500) {
                            $commit499 = false;
                            fabric.common.TransactionID $currentTid501 =
                              $tm502.getCurrentTid();
                            if ($e500.tid.isDescendantOf($currentTid501))
                                continue $label498;
                            if ($currentTid501.parent != null) throw $e500;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e500) {
                            $commit499 = false;
                            if ($tm502.checkForStaleObjects())
                                continue $label498;
                            throw new fabric.worker.AbortException($e500);
                        }
                        finally {
                            if ($commit499) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e500) {
                                    $commit499 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e500) {
                                    $commit499 = false;
                                    fabric.common.TransactionID $currentTid501 =
                                      $tm502.getCurrentTid();
                                    if ($currentTid501 != null) {
                                        if ($e500.tid.equals($currentTid501) ||
                                              !$e500.tid.isDescendantOf(
                                                           $currentTid501)) {
                                            throw $e500;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit499) {
                                { loop = loop$var497; }
                                continue $label498;
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
                    boolean loop$var506 = loop;
                    fabric.worker.transaction.TransactionManager $tm511 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled514 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff512 = 1;
                    boolean $doBackoff513 = true;
                    $label507: for (boolean $commit508 = false; !$commit508; ) {
                        if ($backoffEnabled514) {
                            if ($doBackoff513) {
                                if ($backoff512 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff512);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e509) {
                                            
                                        }
                                    }
                                }
                                if ($backoff512 < 5000) $backoff512 *= 2;
                            }
                            $doBackoff513 = $backoff512 <= 32 || !$doBackoff513;
                        }
                        $commit508 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          this.get$curVal(),
                                                          null) ||
                                !this.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e509) {
                            $commit508 = false;
                            continue $label507;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e509) {
                            $commit508 = false;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510))
                                continue $label507;
                            if ($currentTid510.parent != null) throw $e509;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e509) {
                            $commit508 = false;
                            if ($tm511.checkForStaleObjects())
                                continue $label507;
                            throw new fabric.worker.AbortException($e509);
                        }
                        finally {
                            if ($commit508) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e509) {
                                    $commit508 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e509) {
                                    $commit508 = false;
                                    fabric.common.TransactionID $currentTid510 =
                                      $tm511.getCurrentTid();
                                    if ($currentTid510 != null) {
                                        if ($e509.tid.equals($currentTid510) ||
                                              !$e509.tid.isDescendantOf(
                                                           $currentTid510)) {
                                            throw $e509;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit508) {
                                { loop = loop$var506; }
                                continue $label507;
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
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
