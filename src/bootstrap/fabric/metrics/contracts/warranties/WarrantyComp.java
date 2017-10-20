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
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1, boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1, arg2);
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
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time, true);
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
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "Starting " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)));
            boolean loop = false;
            {
                boolean loop$var507 = loop;
                fabric.worker.transaction.TransactionManager $tm512 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled515 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff513 = 1;
                boolean $doBackoff514 = true;
                $label508: for (boolean $commit509 = false; !$commit509; ) {
                    if ($backoffEnabled515) {
                        if ($doBackoff514) {
                            if ($backoff513 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff513);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e510) {
                                        
                                    }
                                }
                            }
                            if ($backoff513 < 5000) $backoff513 *= 2;
                        }
                        $doBackoff514 = $backoff513 <= 32 || !$doBackoff514;
                    }
                    $commit509 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        loop =
                          fabric.lang.Object._Proxy.idEquals(
                                                      tmp.get$curVal(
                                                            ).get$contract(),
                                                      null) ||
                            !tmp.get$curVal().get$contract().valid();
                    }
                    catch (final fabric.worker.RetryException $e510) {
                        $commit509 = false;
                        continue $label508;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e510) {
                        $commit509 = false;
                        fabric.common.TransactionID $currentTid511 =
                          $tm512.getCurrentTid();
                        if ($e510.tid.isDescendantOf($currentTid511))
                            continue $label508;
                        if ($currentTid511.parent != null) throw $e510;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e510) {
                        $commit509 = false;
                        if ($tm512.checkForStaleObjects()) continue $label508;
                        throw new fabric.worker.AbortException($e510);
                    }
                    finally {
                        if ($commit509) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e510) {
                                $commit509 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e510) {
                                $commit509 = false;
                                fabric.common.TransactionID $currentTid511 =
                                  $tm512.getCurrentTid();
                                if ($currentTid511 != null) {
                                    if ($e510.tid.equals($currentTid511) ||
                                          !$e510.tid.isDescendantOf(
                                                       $currentTid511)) {
                                        throw $e510;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit509) {
                            { loop = loop$var507; }
                            continue $label508;
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
                fabric.metrics.contracts.warranties.WarrantyValue newVal =
                  tmp.updatedVal(java.lang.System.currentTimeMillis());
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Updating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                      " to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(newVal)));
                if (fabric.lang.Object._Proxy.idEquals(newVal.get$contract(),
                                                       null)) return newVal;
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Activating " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(newVal)) +
                      " for " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)));
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     newVal.get$contract(
                                                              ).$getStore(
                                                                  ).name());
                ((fabric.metrics.contracts.MetricContract._Proxy)
                   newVal.get$contract()).activate$remote(w, null);
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Setting " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                      " to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(newVal)) +
                      " and Observing");
                {
                    boolean loop$var516 = loop;
                    fabric.worker.transaction.TransactionManager $tm521 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled524 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff522 = 1;
                    boolean $doBackoff523 = true;
                    $label517: for (boolean $commit518 = false; !$commit518; ) {
                        if ($backoffEnabled524) {
                            if ($doBackoff523) {
                                if ($backoff522 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff522);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e519) {
                                            
                                        }
                                    }
                                }
                                if ($backoff522 < 5000) $backoff522 *= 2;
                            }
                            $doBackoff523 = $backoff522 <= 32 || !$doBackoff523;
                        }
                        $commit518 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$curVal().get$contract(),
                                           null) ||
                                  !tmp.get$curVal().get$contract().valid()) {
                                tmp.get$curVal().set$value(newVal.get$value());
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$curVal().get$contract(),
                                               newVal.get$contract())) {
                                    if (!fabric.lang.Object._Proxy.
                                          idEquals(
                                            tmp.get$curVal().get$contract(),
                                            null)) {
                                        tmp.get$curVal().get$contract().
                                          removeObserver(tmp);
                                    }
                                    tmp.get$curVal().set$contract(
                                                       newVal.get$contract());
                                    tmp.get$curVal().get$contract().addObserver(
                                                                      tmp);
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e519) {
                            $commit518 = false;
                            continue $label517;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e519) {
                            $commit518 = false;
                            fabric.common.TransactionID $currentTid520 =
                              $tm521.getCurrentTid();
                            if ($e519.tid.isDescendantOf($currentTid520))
                                continue $label517;
                            if ($currentTid520.parent != null) throw $e519;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e519) {
                            $commit518 = false;
                            if ($tm521.checkForStaleObjects())
                                continue $label517;
                            throw new fabric.worker.AbortException($e519);
                        }
                        finally {
                            if ($commit518) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e519) {
                                    $commit518 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e519) {
                                    $commit518 = false;
                                    fabric.common.TransactionID $currentTid520 =
                                      $tm521.getCurrentTid();
                                    if ($currentTid520 != null) {
                                        if ($e519.tid.equals($currentTid520) ||
                                              !$e519.tid.isDescendantOf(
                                                           $currentTid520)) {
                                            throw $e519;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit518) {
                                { loop = loop$var516; }
                                continue $label517;
                            }
                        }
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "Updated to " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              tmp.get$curVal(
                                                                    ))));
                {
                    boolean loop$var525 = loop;
                    fabric.worker.transaction.TransactionManager $tm530 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled533 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff531 = 1;
                    boolean $doBackoff532 = true;
                    $label526: for (boolean $commit527 = false; !$commit527; ) {
                        if ($backoffEnabled533) {
                            if ($doBackoff532) {
                                if ($backoff531 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff531);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e528) {
                                            
                                        }
                                    }
                                }
                                if ($backoff531 < 5000) $backoff531 *= 2;
                            }
                            $doBackoff532 = $backoff531 <= 32 || !$doBackoff532;
                        }
                        $commit527 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          tmp.get$curVal(
                                                                ).get$contract(
                                                                    ), null) ||
                                autoRetry &&
                                !tmp.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e528) {
                            $commit527 = false;
                            continue $label526;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e528) {
                            $commit527 = false;
                            fabric.common.TransactionID $currentTid529 =
                              $tm530.getCurrentTid();
                            if ($e528.tid.isDescendantOf($currentTid529))
                                continue $label526;
                            if ($currentTid529.parent != null) throw $e528;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e528) {
                            $commit527 = false;
                            if ($tm530.checkForStaleObjects())
                                continue $label526;
                            throw new fabric.worker.AbortException($e528);
                        }
                        finally {
                            if ($commit527) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e528) {
                                    $commit527 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e528) {
                                    $commit527 = false;
                                    fabric.common.TransactionID $currentTid529 =
                                      $tm530.getCurrentTid();
                                    if ($currentTid529 != null) {
                                        if ($e528.tid.equals($currentTid529) ||
                                              !$e528.tid.isDescendantOf(
                                                           $currentTid529)) {
                                            throw $e528;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit527) {
                                { loop = loop$var525; }
                                continue $label526;
                            }
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "Finished " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " with " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          tmp.get$curVal())));
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
            return fabric.lang.Object._Proxy.idEquals(
                                               this.get$curVal().get$contract(),
                                               null) ||
              !this.get$curVal().get$contract().valid(time);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            fabric$lang$Object$();
            this.
              set$curVal(
                ((fabric.metrics.contracts.warranties.WarrantyValue)
                   new fabric.metrics.contracts.warranties.WarrantyValue._Impl(
                     this.$getStore()).$getProxy()).
                    fabric$metrics$contracts$warranties$WarrantyValue$(null,
                                                                       null));
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
    
    public static final byte[] $classHash = new byte[] { 91, 8, -18, 6, -29,
    -39, -14, 26, -21, 21, 52, -63, 98, -1, -71, 39, -36, -63, 75, 68, 93, -113,
    11, -119, 74, 10, -45, -6, -23, 86, -100, 126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508511728000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
