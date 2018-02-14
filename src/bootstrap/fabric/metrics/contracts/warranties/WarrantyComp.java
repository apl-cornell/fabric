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
                          fabric.lang.Object._Proxy.idEquals(
                                                      tmp.get$curVal(
                                                            ).get$contract(),
                                                      null) ||
                            autoRetry &&
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
                                      fabric.lang.Object._Proxy.
                                        idEquals(
                                          tmp.get$curVal().get$contract(),
                                          null) ||
                                        autoRetry &&
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
        
        public static final byte[] $classHash = new byte[] { -94, -20, 20, -44,
        89, -13, -48, -85, -59, 15, -76, 40, 118, 102, 110, -107, 59, 114, 111,
        96, -60, 54, 50, -124, -43, -128, -53, -4, -80, -121, 63, 54 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518624311000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
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
                      fabric.lang.Object._Proxy.idEquals(
                                                  tmp.get$curVal().get$contract(
                                                                     ), null) ||
                        autoRetry &&
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
                                  fabric.lang.Object._Proxy.
                                    idEquals(tmp.get$curVal().get$contract(),
                                             null) || autoRetry &&
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
    
    public static final byte[] $classHash = new byte[] { -17, 16, -21, 44, -4,
    35, 11, 24, 34, 36, 17, 76, -111, 13, -68, -58, 76, 49, -113, 107, 97, -118,
    15, -23, -25, -121, 19, -86, 54, -63, 66, -82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518624311000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
