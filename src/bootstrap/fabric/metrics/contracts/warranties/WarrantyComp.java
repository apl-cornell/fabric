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
        
        public fabric.metrics.contracts.MetricContract get$oldBaseContract();
        
        public fabric.metrics.contracts.MetricContract set$oldBaseContract(
          fabric.metrics.contracts.MetricContract val);
        
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
            
            public fabric.metrics.contracts.MetricContract get$oldBaseContract(
              ) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).get$oldBaseContract();
            }
            
            public fabric.metrics.contracts.MetricContract set$oldBaseContract(
              fabric.metrics.contracts.MetricContract val) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).set$oldBaseContract(val);
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
            
            public fabric.metrics.contracts.MetricContract get$oldBaseContract(
              ) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.oldBaseContract;
            }
            
            public fabric.metrics.contracts.MetricContract set$oldBaseContract(
              fabric.metrics.contracts.MetricContract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.oldBaseContract = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.MetricContract oldBaseContract;
            
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
                boolean loop = false;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    loop =
                      fabric.lang.Object._Proxy.idEquals(
                                                  tmp.get$curVal().get$contract(
                                                                     ), null) ||
                        !tmp.get$curVal().get$contract().valid();
                }
                else {
                    {
                        boolean loop$var547 = loop;
                        fabric.worker.transaction.TransactionManager $tm553 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled556 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff554 = 1;
                        boolean $doBackoff555 = true;
                        boolean $retry550 = true;
                        $label548: for (boolean $commit549 = false; !$commit549;
                                        ) {
                            if ($backoffEnabled556) {
                                if ($doBackoff555) {
                                    if ($backoff554 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff554);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e551) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff554 < 5000) $backoff554 *= 2;
                                }
                                $doBackoff555 = $backoff554 <= 32 ||
                                                  !$doBackoff555;
                            }
                            $commit549 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                loop =
                                  fabric.lang.Object._Proxy.
                                    idEquals(tmp.get$curVal().get$contract(),
                                             null) ||
                                    !tmp.get$curVal().get$contract().valid();
                            }
                            catch (final fabric.worker.RetryException $e551) {
                                $commit549 = false;
                                continue $label548;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e551) {
                                $commit549 = false;
                                fabric.common.TransactionID $currentTid552 =
                                  $tm553.getCurrentTid();
                                if ($e551.tid.isDescendantOf($currentTid552))
                                    continue $label548;
                                if ($currentTid552.parent != null) {
                                    $retry550 = false;
                                    throw $e551;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e551) {
                                $commit549 = false;
                                if ($tm553.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid552 =
                                  $tm553.getCurrentTid();
                                if ($e551.tid.isDescendantOf($currentTid552)) {
                                    $retry550 = true;
                                }
                                else if ($currentTid552.parent != null) {
                                    $retry550 = false;
                                    throw $e551;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e551) {
                                $commit549 = false;
                                if ($tm553.checkForStaleObjects())
                                    continue $label548;
                                $retry550 = false;
                                throw new fabric.worker.AbortException($e551);
                            }
                            finally {
                                if ($commit549) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e551) {
                                        $commit549 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e551) {
                                        $commit549 = false;
                                        fabric.common.TransactionID
                                          $currentTid552 =
                                          $tm553.getCurrentTid();
                                        if ($currentTid552 != null) {
                                            if ($e551.tid.equals(
                                                            $currentTid552) ||
                                                  !$e551.tid.
                                                  isDescendantOf(
                                                    $currentTid552)) {
                                                throw $e551;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit549 && $retry550) {
                                    { loop = loop$var547; }
                                    continue $label548;
                                }
                            }
                        }
                    }
                }
                while (loop) {
                    fabric.metrics.contracts.warranties.WarrantyValue newVal =
                      tmp.get$baseComputation().
                      apply(java.lang.System.currentTimeMillis(), autoRetry);
                    if (fabric.lang.Object._Proxy.idEquals(
                                                    newVal.get$contract(),
                                                    null)) return newVal;
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn()) {
                        if (fabric.lang.Object._Proxy.idEquals(
                                                        tmp.get$curVal(
                                                              ).get$contract(),
                                                        null) ||
                              !tmp.get$curVal().get$contract().valid()) {
                            tmp.get$curVal().set$value(
                                               tmp.get$baseComputation(
                                                     ).makeProxyResult(
                                                         newVal,
                                                         tmp.$getStore()));
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$oldBaseContract(),
                                           newVal.get$contract())) {
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$curVal().get$contract(),
                                               null)) {
                                    tmp.get$curVal().get$contract().
                                      removeObserver(tmp);
                                }
                                tmp.set$oldBaseContract(newVal.get$contract());
                                tmp.get$curVal().set$contract(
                                                   newVal.get$contract(
                                                            ).getProxyContract(
                                                                tmp.$getStore(
                                                                      )));
                                tmp.get$curVal().get$contract().addObserver(
                                                                  tmp);
                            }
                        }
                    }
                    else {
                        {
                            boolean loop$var557 = loop;
                            fabric.worker.transaction.TransactionManager
                              $tm563 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled566 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff564 = 1;
                            boolean $doBackoff565 = true;
                            boolean $retry560 = true;
                            $label558: for (boolean $commit559 = false;
                                            !$commit559; ) {
                                if ($backoffEnabled566) {
                                    if ($doBackoff565) {
                                        if ($backoff564 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep($backoff564);
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e561) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff564 < 5000)
                                            $backoff564 *= 2;
                                    }
                                    $doBackoff565 = $backoff564 <= 32 ||
                                                      !$doBackoff565;
                                }
                                $commit559 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    if (fabric.lang.Object._Proxy.
                                          idEquals(
                                            tmp.get$curVal().get$contract(),
                                            null) ||
                                          !tmp.get$curVal().get$contract().
                                          valid()) {
                                        tmp.get$curVal().
                                          set$value(
                                            tmp.get$baseComputation(
                                                  ).makeProxyResult(
                                                      newVal, tmp.$getStore()));
                                        if (!fabric.lang.Object._Proxy.
                                              idEquals(
                                                tmp.get$oldBaseContract(),
                                                newVal.get$contract())) {
                                            if (!fabric.lang.Object._Proxy.
                                                  idEquals(
                                                    tmp.get$curVal().
                                                        get$contract(),
                                                    null)) {
                                                tmp.get$curVal().get$contract().
                                                  removeObserver(tmp);
                                            }
                                            tmp.set$oldBaseContract(
                                                  newVal.get$contract());
                                            tmp.get$curVal().
                                              set$contract(
                                                newVal.get$contract(
                                                         ).getProxyContract(
                                                             tmp.$getStore()));
                                            tmp.get$curVal().get$contract().
                                              addObserver(tmp);
                                        }
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e561) {
                                    $commit559 = false;
                                    continue $label558;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e561) {
                                    $commit559 = false;
                                    fabric.common.TransactionID $currentTid562 =
                                      $tm563.getCurrentTid();
                                    if ($e561.tid.isDescendantOf(
                                                    $currentTid562))
                                        continue $label558;
                                    if ($currentTid562.parent != null) {
                                        $retry560 = false;
                                        throw $e561;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e561) {
                                    $commit559 = false;
                                    if ($tm563.checkForStaleObjects()) continue;
                                    fabric.common.TransactionID $currentTid562 =
                                      $tm563.getCurrentTid();
                                    if ($e561.tid.isDescendantOf(
                                                    $currentTid562)) {
                                        $retry560 = true;
                                    }
                                    else if ($currentTid562.parent != null) {
                                        $retry560 = false;
                                        throw $e561;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e561) {
                                    $commit559 = false;
                                    if ($tm563.checkForStaleObjects())
                                        continue $label558;
                                    $retry560 = false;
                                    throw new fabric.worker.AbortException(
                                            $e561);
                                }
                                finally {
                                    if ($commit559) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e561) {
                                            $commit559 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e561) {
                                            $commit559 = false;
                                            fabric.common.TransactionID
                                              $currentTid562 =
                                              $tm563.getCurrentTid();
                                            if ($currentTid562 != null) {
                                                if ($e561.tid.
                                                      equals($currentTid562) ||
                                                      !$e561.tid.
                                                      isDescendantOf(
                                                        $currentTid562)) {
                                                    throw $e561;
                                                }
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit559 && $retry560) {
                                        { loop = loop$var557; }
                                        continue $label558;
                                    }
                                }
                            }
                        }
                    }
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn()) {
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
                            boolean loop$var567 = loop;
                            fabric.worker.transaction.TransactionManager
                              $tm573 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled576 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff574 = 1;
                            boolean $doBackoff575 = true;
                            boolean $retry570 = true;
                            $label568: for (boolean $commit569 = false;
                                            !$commit569; ) {
                                if ($backoffEnabled576) {
                                    if ($doBackoff575) {
                                        if ($backoff574 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep($backoff574);
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e571) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff574 < 5000)
                                            $backoff574 *= 2;
                                    }
                                    $doBackoff575 = $backoff574 <= 32 ||
                                                      !$doBackoff575;
                                }
                                $commit569 = true;
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().startTransaction();
                                try {
                                    loop =
                                      autoRetry &&
                                        !fabric.lang.Object._Proxy.
                                        idEquals(
                                          tmp.get$curVal().get$contract(),
                                          null) &&
                                        !tmp.get$curVal().get$contract().valid(
                                                                           );
                                }
                                catch (final fabric.worker.
                                         RetryException $e571) {
                                    $commit569 = false;
                                    continue $label568;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e571) {
                                    $commit569 = false;
                                    fabric.common.TransactionID $currentTid572 =
                                      $tm573.getCurrentTid();
                                    if ($e571.tid.isDescendantOf(
                                                    $currentTid572))
                                        continue $label568;
                                    if ($currentTid572.parent != null) {
                                        $retry570 = false;
                                        throw $e571;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e571) {
                                    $commit569 = false;
                                    if ($tm573.checkForStaleObjects()) continue;
                                    fabric.common.TransactionID $currentTid572 =
                                      $tm573.getCurrentTid();
                                    if ($e571.tid.isDescendantOf(
                                                    $currentTid572)) {
                                        $retry570 = true;
                                    }
                                    else if ($currentTid572.parent != null) {
                                        $retry570 = false;
                                        throw $e571;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e571) {
                                    $commit569 = false;
                                    if ($tm573.checkForStaleObjects())
                                        continue $label568;
                                    $retry570 = false;
                                    throw new fabric.worker.AbortException(
                                            $e571);
                                }
                                finally {
                                    if ($commit569) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e571) {
                                            $commit569 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e571) {
                                            $commit569 = false;
                                            fabric.common.TransactionID
                                              $currentTid572 =
                                              $tm573.getCurrentTid();
                                            if ($currentTid572 != null) {
                                                if ($e571.tid.
                                                      equals($currentTid572) ||
                                                      !$e571.tid.
                                                      isDescendantOf(
                                                        $currentTid572)) {
                                                    throw $e571;
                                                }
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit569 && $retry570) {
                                        { loop = loop$var567; }
                                        continue $label568;
                                    }
                                }
                            }
                        }
                    }
                }
                return tmp.get$curVal();
            }
            
            public ProxyComp makeProxy(final fabric.worker.Store proxyStore) {
                return fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp._Impl.
                  static_makeProxy((ProxyComp) this.$getProxy(), proxyStore);
            }
            
            private static ProxyComp static_makeProxy(
              ProxyComp tmp, final fabric.worker.Store proxyStore) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return ((ProxyComp)
                              new fabric.metrics.contracts.warranties.
                                WarrantyComp.ProxyComp._Impl(proxyStore).
                              $getProxy()).
                      fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                        tmp.get$baseComputation());
                }
                else {
                    ProxyComp rtn = null;
                    {
                        fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                          rtn$var577 = rtn;
                        fabric.worker.transaction.TransactionManager $tm583 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled586 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff584 = 1;
                        boolean $doBackoff585 = true;
                        boolean $retry580 = true;
                        $label578: for (boolean $commit579 = false; !$commit579;
                                        ) {
                            if ($backoffEnabled586) {
                                if ($doBackoff585) {
                                    if ($backoff584 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff584);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e581) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff584 < 5000) $backoff584 *= 2;
                                }
                                $doBackoff585 = $backoff584 <= 32 ||
                                                  !$doBackoff585;
                            }
                            $commit579 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  ((ProxyComp)
                                     new fabric.metrics.contracts.warranties.
                                       WarrantyComp.ProxyComp._Impl(proxyStore).
                                     $getProxy()).
                                    fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                      tmp.get$baseComputation());
                            }
                            catch (final fabric.worker.RetryException $e581) {
                                $commit579 = false;
                                continue $label578;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e581) {
                                $commit579 = false;
                                fabric.common.TransactionID $currentTid582 =
                                  $tm583.getCurrentTid();
                                if ($e581.tid.isDescendantOf($currentTid582))
                                    continue $label578;
                                if ($currentTid582.parent != null) {
                                    $retry580 = false;
                                    throw $e581;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e581) {
                                $commit579 = false;
                                if ($tm583.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid582 =
                                  $tm583.getCurrentTid();
                                if ($e581.tid.isDescendantOf($currentTid582)) {
                                    $retry580 = true;
                                }
                                else if ($currentTid582.parent != null) {
                                    $retry580 = false;
                                    throw $e581;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e581) {
                                $commit579 = false;
                                if ($tm583.checkForStaleObjects())
                                    continue $label578;
                                $retry580 = false;
                                throw new fabric.worker.AbortException($e581);
                            }
                            finally {
                                if ($commit579) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e581) {
                                        $commit579 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e581) {
                                        $commit579 = false;
                                        fabric.common.TransactionID
                                          $currentTid582 =
                                          $tm583.getCurrentTid();
                                        if ($currentTid582 != null) {
                                            if ($e581.tid.equals(
                                                            $currentTid582) ||
                                                  !$e581.tid.
                                                  isDescendantOf(
                                                    $currentTid582)) {
                                                throw $e581;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit579 && $retry580) {
                                    { rtn = rtn$var577; }
                                    continue $label578;
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
                $writeRef($getStore(), this.oldBaseContract, refTypes, out,
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
                this.oldBaseContract =
                  (fabric.metrics.contracts.MetricContract)
                    $readRef(
                      fabric.metrics.contracts.MetricContract._Proxy.class,
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
                this.oldBaseContract = src.oldBaseContract;
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
        
        public static final byte[] $classHash = new byte[] { 18, 104, 70, -41,
        -2, 113, 105, -109, -44, -23, 67, -125, -44, 16, 103, 96, 38, -40, 71,
        89, -64, -10, 36, 103, -5, -67, 120, 5, -60, 32, -102, 54 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519320323000L;
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
                    boolean loop$var587 = loop;
                    fabric.worker.transaction.TransactionManager $tm593 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled596 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff594 = 1;
                    boolean $doBackoff595 = true;
                    boolean $retry590 = true;
                    $label588: for (boolean $commit589 = false; !$commit589; ) {
                        if ($backoffEnabled596) {
                            if ($doBackoff595) {
                                if ($backoff594 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff594);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e591) {
                                            
                                        }
                                    }
                                }
                                if ($backoff594 < 5000) $backoff594 *= 2;
                            }
                            $doBackoff595 = $backoff594 <= 32 || !$doBackoff595;
                        }
                        $commit589 = true;
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
                        catch (final fabric.worker.RetryException $e591) {
                            $commit589 = false;
                            continue $label588;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e591) {
                            $commit589 = false;
                            fabric.common.TransactionID $currentTid592 =
                              $tm593.getCurrentTid();
                            if ($e591.tid.isDescendantOf($currentTid592))
                                continue $label588;
                            if ($currentTid592.parent != null) {
                                $retry590 = false;
                                throw $e591;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e591) {
                            $commit589 = false;
                            if ($tm593.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid592 =
                              $tm593.getCurrentTid();
                            if ($e591.tid.isDescendantOf($currentTid592)) {
                                $retry590 = true;
                            }
                            else if ($currentTid592.parent != null) {
                                $retry590 = false;
                                throw $e591;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e591) {
                            $commit589 = false;
                            if ($tm593.checkForStaleObjects())
                                continue $label588;
                            $retry590 = false;
                            throw new fabric.worker.AbortException($e591);
                        }
                        finally {
                            if ($commit589) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e591) {
                                    $commit589 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e591) {
                                    $commit589 = false;
                                    fabric.common.TransactionID $currentTid592 =
                                      $tm593.getCurrentTid();
                                    if ($currentTid592 != null) {
                                        if ($e591.tid.equals($currentTid592) ||
                                              !$e591.tid.isDescendantOf(
                                                           $currentTid592)) {
                                            throw $e591;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit589 && $retry590) {
                                { loop = loop$var587; }
                                continue $label588;
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
                    ((fabric.metrics.contracts.MetricContract._Proxy)
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
                        }
                    }
                }
                else {
                    {
                        boolean loop$var597 = loop;
                        fabric.worker.transaction.TransactionManager $tm603 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled606 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff604 = 1;
                        boolean $doBackoff605 = true;
                        boolean $retry600 = true;
                        $label598: for (boolean $commit599 = false; !$commit599;
                                        ) {
                            if ($backoffEnabled606) {
                                if ($doBackoff605) {
                                    if ($backoff604 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff604);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e601) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff604 < 5000) $backoff604 *= 2;
                                }
                                $doBackoff605 = $backoff604 <= 32 ||
                                                  !$doBackoff605;
                            }
                            $commit599 = true;
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
                                    }
                                }
                            }
                            catch (final fabric.worker.RetryException $e601) {
                                $commit599 = false;
                                continue $label598;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e601) {
                                $commit599 = false;
                                fabric.common.TransactionID $currentTid602 =
                                  $tm603.getCurrentTid();
                                if ($e601.tid.isDescendantOf($currentTid602))
                                    continue $label598;
                                if ($currentTid602.parent != null) {
                                    $retry600 = false;
                                    throw $e601;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e601) {
                                $commit599 = false;
                                if ($tm603.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid602 =
                                  $tm603.getCurrentTid();
                                if ($e601.tid.isDescendantOf($currentTid602)) {
                                    $retry600 = true;
                                }
                                else if ($currentTid602.parent != null) {
                                    $retry600 = false;
                                    throw $e601;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e601) {
                                $commit599 = false;
                                if ($tm603.checkForStaleObjects())
                                    continue $label598;
                                $retry600 = false;
                                throw new fabric.worker.AbortException($e601);
                            }
                            finally {
                                if ($commit599) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e601) {
                                        $commit599 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e601) {
                                        $commit599 = false;
                                        fabric.common.TransactionID
                                          $currentTid602 =
                                          $tm603.getCurrentTid();
                                        if ($currentTid602 != null) {
                                            if ($e601.tid.equals(
                                                            $currentTid602) ||
                                                  !$e601.tid.
                                                  isDescendantOf(
                                                    $currentTid602)) {
                                                throw $e601;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit599 && $retry600) {
                                    { loop = loop$var597; }
                                    continue $label598;
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
                        boolean loop$var607 = loop;
                        fabric.worker.transaction.TransactionManager $tm613 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled616 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff614 = 1;
                        boolean $doBackoff615 = true;
                        boolean $retry610 = true;
                        $label608: for (boolean $commit609 = false; !$commit609;
                                        ) {
                            if ($backoffEnabled616) {
                                if ($doBackoff615) {
                                    if ($backoff614 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff614);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e611) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff614 < 5000) $backoff614 *= 2;
                                }
                                $doBackoff615 = $backoff614 <= 32 ||
                                                  !$doBackoff615;
                            }
                            $commit609 = true;
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
                            catch (final fabric.worker.RetryException $e611) {
                                $commit609 = false;
                                continue $label608;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e611) {
                                $commit609 = false;
                                fabric.common.TransactionID $currentTid612 =
                                  $tm613.getCurrentTid();
                                if ($e611.tid.isDescendantOf($currentTid612))
                                    continue $label608;
                                if ($currentTid612.parent != null) {
                                    $retry610 = false;
                                    throw $e611;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e611) {
                                $commit609 = false;
                                if ($tm613.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid612 =
                                  $tm613.getCurrentTid();
                                if ($e611.tid.isDescendantOf($currentTid612)) {
                                    $retry610 = true;
                                }
                                else if ($currentTid612.parent != null) {
                                    $retry610 = false;
                                    throw $e611;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e611) {
                                $commit609 = false;
                                if ($tm613.checkForStaleObjects())
                                    continue $label608;
                                $retry610 = false;
                                throw new fabric.worker.AbortException($e611);
                            }
                            finally {
                                if ($commit609) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e611) {
                                        $commit609 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e611) {
                                        $commit609 = false;
                                        fabric.common.TransactionID
                                          $currentTid612 =
                                          $tm613.getCurrentTid();
                                        if ($currentTid612 != null) {
                                            if ($e611.tid.equals(
                                                            $currentTid612) ||
                                                  !$e611.tid.
                                                  isDescendantOf(
                                                    $currentTid612)) {
                                                throw $e611;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit609 && $retry610) {
                                    { loop = loop$var607; }
                                    continue $label608;
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
            return (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
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
                return ((ProxyComp)
                          new fabric.metrics.contracts.warranties.WarrantyComp.
                            ProxyComp._Impl(proxyStore).
                          $getProxy()).
                  fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                    tmp);
            }
            else {
                ProxyComp rtn = null;
                {
                    fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                      rtn$var617 = rtn;
                    fabric.worker.transaction.TransactionManager $tm623 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled626 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff624 = 1;
                    boolean $doBackoff625 = true;
                    boolean $retry620 = true;
                    $label618: for (boolean $commit619 = false; !$commit619; ) {
                        if ($backoffEnabled626) {
                            if ($doBackoff625) {
                                if ($backoff624 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff624);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e621) {
                                            
                                        }
                                    }
                                }
                                if ($backoff624 < 5000) $backoff624 *= 2;
                            }
                            $doBackoff625 = $backoff624 <= 32 || !$doBackoff625;
                        }
                        $commit619 = true;
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
                        }
                        catch (final fabric.worker.RetryException $e621) {
                            $commit619 = false;
                            continue $label618;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e621) {
                            $commit619 = false;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622))
                                continue $label618;
                            if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622)) {
                                $retry620 = true;
                            }
                            else if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects())
                                continue $label618;
                            $retry620 = false;
                            throw new fabric.worker.AbortException($e621);
                        }
                        finally {
                            if ($commit619) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e621) {
                                    $commit619 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e621) {
                                    $commit619 = false;
                                    fabric.common.TransactionID $currentTid622 =
                                      $tm623.getCurrentTid();
                                    if ($currentTid622 != null) {
                                        if ($e621.tid.equals($currentTid622) ||
                                              !$e621.tid.isDescendantOf(
                                                           $currentTid622)) {
                                            throw $e621;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit619 && $retry620) {
                                { rtn = rtn$var617; }
                                continue $label618;
                            }
                        }
                    }
                }
                return rtn;
            }
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            fabric$metrics$util$AbstractSubject$();
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
    
    public static final byte[] $classHash = new byte[] { -104, -9, -87, -20, 84,
    96, -48, 2, -42, 100, 113, 40, 82, 97, 34, 106, -9, -27, 120, 76, -41, -21,
    -29, -35, -59, 72, -61, 114, -128, -14, -46, 117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519320323000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUZbWwUx3Xu/HnGYGMwH8YYMFdUCLmDFLUKbmjhCsHlqC3bpI3dxsztzZ0X7+0uu3P2OQ0VjRpBU4lUlBBQCT9apympS6RIKKoiqqQtTWga0qIIkh9JUCtEEFCVfoHUtOl7s3O3e2vfxVTtyTtvdua9mfc9b9YTN0iVbZH2FE2oWoSPmcyObKGJzng3tWyWjGnUtvtgdFCZUdl5+INnk21BEoyTeoXqhq4qVBvUbU5mxXfRERrVGY/u6OnsGCAhBQm3UnuIk+DAppxFlpqGNpbWDC43mbT+k3dFDz31UOMLFaShnzSoei+nXFVihs5ZjveT+gzLJJhlb0wmWbKfzNYZS/YyS6Wa+jAgGno/abLVtE551mJ2D7MNbQQRm+ysySyxZ34Q2TeAbSurcMMC9hsd9rNc1aJx1eYdcVKdUpmWtHeTb5DKOKlKaTQNiPPieSmiYsXoFhwH9DoV2LRSVGF5ksphVU9yssRPUZA4vA0QgLQmw/iQUdiqUqcwQJocljSqp6O93FL1NKBWGVnYhZOWkosCUq1JlWGaZoOcLPDjdTtTgBUSakESTpr9aGIlsFmLz2Yea9340mcPfF3fqgdJAHhOMkVD/muBqM1H1MNSzGK6whzC+lXxw3Te6f1BQgC52Yfs4Lz4yM3Pr257+TUHZ9EUOF2JXUzhg8p4YtbvW2Mr761ANmpNw1bRFYokF1btljMdORO8fV5hRZyM5Cdf7vn1g3ufY9eCpK6TVCuGls2AV81WjIypasy6n+nMopwlO0mI6cmYmO8kNdCPqzpzRrtSKZvxTlKpiaFqQ7yDilKwBKqoBvqqnjLyfZPyIdHPmYSQRnhIAP4GCFkHkCwgJPghJ4PRISPDogkty0bBvaPwMGopQ1GIW0tVoralRK2szlVAkkPgRQDsKLg6t6jC7egotSwKOED/Zac7FgPZIsCa+f/fIodSNo4GAmCAJYqRZAlqgzWlZ23q1iB4thpaklmDinbgdCeZc/qo8K4QRoQNXi30FwCPaPXnEi/toeymzTdPDr7ueCbSSvVyssbhOyL5jhT4jrh8R7x8A6v1GIcRyGwRyGwTgVwkdrzzJ8Ldqm0Rl4XV62H19aZGecqwMjkSCAhR5wp64WfgJcOQfSDB1K/s/doXd+5vrwAHN0cr0eaAGvaHm5ukOqFHIYYGlYZ9H/zj+cN7DDfwOAlPygeTKTGe2/16swyFJSFfusuvWkpPDZ7eEw5iLgqhgig4MuScNv8eRXHdkc+RqI2qOJmBOqAaTuUTWx0fsoxRd0T4wyxsmhzXQGX5GBTp9b5e8+m3z139lDh48pm4wZOyexnv8EQ/LtYg4ny2q/s+izHAe/dI9/eevLFvQCgeMJZPtWEYWzQ/hXA3rMde2/3O+++NvxV0jcVJtZlNaKqSE7LM/gh+AXj+jQ+GMA4ghEQek+ljaSF/mLjzCpc3yCQaZDNg3Q7v0DNGUk2pNKEx9JQPGz6x9tT1A42OuTUYcZRnkdUfv4A7vnAT2fv6Q7faxDIBBU8yV38umpMe57grb4RYGEM+ct88v/joq/Rp8HxIbrb6MBP5igh9EGHAe4Qu7hbtWt/cOmzaHW21Fhzef1RswTPX9cX+6MSxltiGa04WKPgirrFsiizwAPWEyT3PZf4ebK8+EyQ1/aRRHPcQ1A9QyG7gBv1wYNsxORgnM4vmiw9f56TpKMRaqz8OPNv6o8DNPtBHbOzXOY7vOA4oogmVFIFnESEVJyU8iLNzTGzn5gJEdNYLkuWiXYHNSqHIICch0zI4cMmg4AipmUyWo/XFPneBqypZCwQTdM2crL2TJCgUgoQtIkRzJVjA7ipOamnCFmvlCtKJX4M8yP4p4XWPdEUuIVls9bEoXLErYTNrxDF/Sw68Z3Gp6kRUVuOPHjqe7HpmrVNDNBWf+Jv1bOanF/7128iRS2enOCdC3DDv1tgI0zzc1cGWyyaVydtF8eb63aVri++NDV9OO9su8bHoxz6xfeLs/SuUg0FSUXCwSRVjMVFHsVvVWQwKXr2vyLmWFtTfjOr/HDxhQipPSfgdr3M5qbe0WTdgs9k1aBBXnCtXelzCnN+gbhYIlIz2bkvNQMIekYUh23/o8Y8iBw459nCq5+WTClgvjVNBi71nCl9Hr1hWbhdBseXK83te+vGefUGZm7ZxSKyGnhYvXymTxHZi08NJXdZMYh6H4MCRjQK5q6ChEBKshmcNIdV9Eq6dps4DIpR86q6Vi6yRcNXHqhtfB8Q+Q2UE2oVNgpMqapraWElZ0H82AhdvSni0hCzYsMmcI8kRCQ+W5jzo+tyAGHxQ2hTBVzmpSRiGxqgudtxdRqoRbLRyUonEtBOezSBhWsIF040KYMW01BHwAI41IN5SffZqlEvOlzBUWuoKsWaF2E+Ijo0l9t9bRsZHsclBzensP1jegKvgGYfU+4SE+p0ZEEkyEqZLi+Ll79tl5kT6+RaUcGm4GDGa6s2KI9bGlO7Lr5D9RQXiZIhzz95eeDp89baTW/2XUw/inyfev3Z+5uKTooitxHuGyI3+W/3kS3vRXVwwW19Qh7iaLYZnHUTgRQl/w8m2//7e9AUGfsSS28WrvIb9L5fLlTpQe2nG1PKY4kCdlK/x/dPYfBfrD98rdp4qXwtUpVSdaoUaRGN6mg9NFdcVYAXsPlE2IQoabL6PzTFBkPPnjry4TgGL5RsUBYbOsBoScwvhbMdbmmYo1NWOc0VTjUjhe1LCuauP56ZUS5ejBw/TInAEi2X8fqLM3ElsToDWFOQ3z1ijK4dThnqY8oX4UngmCFm4Q8INdxbiSHKfhJ+ZXoi/WGbuZ9i8wMnMIaonNbZDnJcC0/IxX4802+F5BQrfKxL+aJqpWPjGBl/ynSEXeUbCY6XF8Rw5Gwu+I0Nl1LCGmRXphQtgwXeKb/iCs1fKaOFVbF6CRJehwwzu2rmxHmZnNZ7fqklu5THw1DtNZXDIPuQsIa3nJPzhnRkcSX4gYRkNecqJX4hVz5eR9y1s3oAQK8ibl3T9nX54CQty7LnZyXd+o6e/ARm5X8J503QaYe5HsNkzxbGNKzVLWDMtz2l0lfNeGeVcwuZtiGl5Yhd0hOMXpoqKODzvELKkwYFtb5ax8M8nxwCSnJPwzPRC+kqZuavY/KFwfQxLU4YLpgy7pgwXmVKwirWKdxS/Myya4iug/HatxH7Fxi9vW91c4gvggkn/TZB0J4831M4/vuOic/Tnv0uH4qQ2ldU0733c0682LZZShZgh53ZuCvAnuIFMw3PhRuC+COVdd+hvcrKgFD13vmiIvpfmb5zMKqbhoizBnhfvFhyqDh6+3XbixNfkw88vhPNpR97VZfklCMTKLVkL/2cz8df5t6tr+y6Jz154why5deJ6387fBS8kd3+yh7bvunU5F7947Y/vntn6S2vvX85n/wOrnIuVSxoAAA==";
}
