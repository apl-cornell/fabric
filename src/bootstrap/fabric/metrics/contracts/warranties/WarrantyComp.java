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
        
        public static final byte[] $classHash = new byte[] { 63, -60, 69, 23,
        34, 30, -104, 100, -17, -17, 40, 9, -110, -49, 95, 106, -128, 6, 11, 84,
        33, -21, -115, -71, 16, -69, -42, -36, 88, 124, -8, 73 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1518571543000L;
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
            return fabric.lang.Object._Proxy.idEquals(
                                               this.get$curVal().get$contract(),
                                               null) ||
              !this.get$curVal().get$contract().valid(time);
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
    
    public static final byte[] $classHash = new byte[] { -65, 75, -79, 86, 91,
    60, -63, 17, -93, 107, -53, -57, -34, -59, -12, -128, -76, 20, -41, -16, 53,
    54, -82, 26, -50, -80, -6, 2, -115, -94, 34, 114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518571543000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
