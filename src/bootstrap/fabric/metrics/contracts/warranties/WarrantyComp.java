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
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      this.fetch()).
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time);
        }
        
        private fabric.metrics.contracts.warranties.WarrantyValue static_apply(
          fabric.metrics.contracts.warranties.WarrantyComp tmp, long time) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "Starting " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)));
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
                          fabric.lang.Object._Proxy.idEquals(tmp.get$curVal(),
                                                             null) ||
                            !tmp.get$curVal().get$contract().valid();
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
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)));
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$curVal(),
                                                        null)) {
                    tmp.get$curVal().get$contract().removeObserver(tmp);
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Updating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)));
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
                        try { tmp.set$curVal(newVal); }
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
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)));
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     tmp.get$curVal(
                                                           ).get$contract(
                                                               ).$getStore(
                                                                   ).name());
                ((fabric.metrics.contracts.MetricContract._Proxy)
                   tmp.get$curVal().get$contract()).activate$remote(w, null);
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Updated to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              tmp.get$curVal(
                                                                    ))));
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Observing " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)));
                tmp.get$curVal().get$contract().addObserver(tmp);
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
                                                          tmp.get$curVal(),
                                                          null) ||
                                !tmp.get$curVal().get$contract().valid();
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
            return tmp.get$curVal();
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
    
    public static final byte[] $classHash = new byte[] { -2, -28, -43, 47, 84,
    -123, -35, 16, -37, -109, 86, 49, 21, -44, 98, -100, 17, 126, -58, 6, 60,
    117, -117, -70, -54, -36, 119, 6, 58, -102, 36, 101 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234253000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
