package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Contract;
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
        
        public fabric.metrics.contracts.Contract get$oldBaseContract();
        
        public fabric.metrics.contracts.Contract set$oldBaseContract(
          fabric.metrics.contracts.Contract val);
        
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
            
            public fabric.metrics.contracts.Contract get$oldBaseContract() {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).get$oldBaseContract();
            }
            
            public fabric.metrics.contracts.Contract set$oldBaseContract(
              fabric.metrics.contracts.Contract val) {
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
            
            public fabric.metrics.contracts.Contract get$oldBaseContract() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.oldBaseContract;
            }
            
            public fabric.metrics.contracts.Contract set$oldBaseContract(
              fabric.metrics.contracts.Contract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.oldBaseContract = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.Contract oldBaseContract;
            
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
                        boolean loop$var573 = loop;
                        fabric.worker.transaction.TransactionManager $tm579 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled582 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff580 = 1;
                        boolean $doBackoff581 = true;
                        boolean $retry576 = true;
                        $label574: for (boolean $commit575 = false; !$commit575;
                                        ) {
                            if ($backoffEnabled582) {
                                if ($doBackoff581) {
                                    if ($backoff580 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff580);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e577) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff580 < 5000) $backoff580 *= 2;
                                }
                                $doBackoff581 = $backoff580 <= 32 ||
                                                  !$doBackoff581;
                            }
                            $commit575 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                loop =
                                  fabric.lang.Object._Proxy.
                                    idEquals(tmp.get$curVal().get$contract(),
                                             null) ||
                                    !tmp.get$curVal().get$contract().valid();
                            }
                            catch (final fabric.worker.RetryException $e577) {
                                $commit575 = false;
                                continue $label574;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e577) {
                                $commit575 = false;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid.isDescendantOf($currentTid578))
                                    continue $label574;
                                if ($currentTid578.parent != null) {
                                    $retry576 = false;
                                    throw $e577;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e577) {
                                $commit575 = false;
                                if ($tm579.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid578 =
                                  $tm579.getCurrentTid();
                                if ($e577.tid.isDescendantOf($currentTid578)) {
                                    $retry576 = true;
                                }
                                else if ($currentTid578.parent != null) {
                                    $retry576 = false;
                                    throw $e577;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e577) {
                                $commit575 = false;
                                if ($tm579.checkForStaleObjects())
                                    continue $label574;
                                $retry576 = false;
                                throw new fabric.worker.AbortException($e577);
                            }
                            finally {
                                if ($commit575) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e577) {
                                        $commit575 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e577) {
                                        $commit575 = false;
                                        fabric.common.TransactionID
                                          $currentTid578 =
                                          $tm579.getCurrentTid();
                                        if ($currentTid578 != null) {
                                            if ($e577.tid.equals(
                                                            $currentTid578) ||
                                                  !$e577.tid.
                                                  isDescendantOf(
                                                    $currentTid578)) {
                                                throw $e577;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit575 && $retry576) {
                                    { loop = loop$var573; }
                                    continue $label574;
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
                            boolean loop$var583 = loop;
                            fabric.worker.transaction.TransactionManager
                              $tm589 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled592 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff590 = 1;
                            boolean $doBackoff591 = true;
                            boolean $retry586 = true;
                            $label584: for (boolean $commit585 = false;
                                            !$commit585; ) {
                                if ($backoffEnabled592) {
                                    if ($doBackoff591) {
                                        if ($backoff590 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep($backoff590);
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e587) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff590 < 5000)
                                            $backoff590 *= 2;
                                    }
                                    $doBackoff591 = $backoff590 <= 32 ||
                                                      !$doBackoff591;
                                }
                                $commit585 = true;
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
                                         RetryException $e587) {
                                    $commit585 = false;
                                    continue $label584;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e587) {
                                    $commit585 = false;
                                    fabric.common.TransactionID $currentTid588 =
                                      $tm589.getCurrentTid();
                                    if ($e587.tid.isDescendantOf(
                                                    $currentTid588))
                                        continue $label584;
                                    if ($currentTid588.parent != null) {
                                        $retry586 = false;
                                        throw $e587;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e587) {
                                    $commit585 = false;
                                    if ($tm589.checkForStaleObjects()) continue;
                                    fabric.common.TransactionID $currentTid588 =
                                      $tm589.getCurrentTid();
                                    if ($e587.tid.isDescendantOf(
                                                    $currentTid588)) {
                                        $retry586 = true;
                                    }
                                    else if ($currentTid588.parent != null) {
                                        $retry586 = false;
                                        throw $e587;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e587) {
                                    $commit585 = false;
                                    if ($tm589.checkForStaleObjects())
                                        continue $label584;
                                    $retry586 = false;
                                    throw new fabric.worker.AbortException(
                                            $e587);
                                }
                                finally {
                                    if ($commit585) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e587) {
                                            $commit585 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e587) {
                                            $commit585 = false;
                                            fabric.common.TransactionID
                                              $currentTid588 =
                                              $tm589.getCurrentTid();
                                            if ($currentTid588 != null) {
                                                if ($e587.tid.
                                                      equals($currentTid588) ||
                                                      !$e587.tid.
                                                      isDescendantOf(
                                                        $currentTid588)) {
                                                    throw $e587;
                                                }
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit585 && $retry586) {
                                        { loop = loop$var583; }
                                        continue $label584;
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
                            boolean loop$var593 = loop;
                            fabric.worker.transaction.TransactionManager
                              $tm599 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled602 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff600 = 1;
                            boolean $doBackoff601 = true;
                            boolean $retry596 = true;
                            $label594: for (boolean $commit595 = false;
                                            !$commit595; ) {
                                if ($backoffEnabled602) {
                                    if ($doBackoff601) {
                                        if ($backoff600 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep($backoff600);
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e597) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff600 < 5000)
                                            $backoff600 *= 2;
                                    }
                                    $doBackoff601 = $backoff600 <= 32 ||
                                                      !$doBackoff601;
                                }
                                $commit595 = true;
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
                                         RetryException $e597) {
                                    $commit595 = false;
                                    continue $label594;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e597) {
                                    $commit595 = false;
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($e597.tid.isDescendantOf(
                                                    $currentTid598))
                                        continue $label594;
                                    if ($currentTid598.parent != null) {
                                        $retry596 = false;
                                        throw $e597;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e597) {
                                    $commit595 = false;
                                    if ($tm599.checkForStaleObjects()) continue;
                                    fabric.common.TransactionID $currentTid598 =
                                      $tm599.getCurrentTid();
                                    if ($e597.tid.isDescendantOf(
                                                    $currentTid598)) {
                                        $retry596 = true;
                                    }
                                    else if ($currentTid598.parent != null) {
                                        $retry596 = false;
                                        throw $e597;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e597) {
                                    $commit595 = false;
                                    if ($tm599.checkForStaleObjects())
                                        continue $label594;
                                    $retry596 = false;
                                    throw new fabric.worker.AbortException(
                                            $e597);
                                }
                                finally {
                                    if ($commit595) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e597) {
                                            $commit595 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e597) {
                                            $commit595 = false;
                                            fabric.common.TransactionID
                                              $currentTid598 =
                                              $tm599.getCurrentTid();
                                            if ($currentTid598 != null) {
                                                if ($e597.tid.
                                                      equals($currentTid598) ||
                                                      !$e597.tid.
                                                      isDescendantOf(
                                                        $currentTid598)) {
                                                    throw $e597;
                                                }
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit595 && $retry596) {
                                        { loop = loop$var593; }
                                        continue $label594;
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
                          rtn$var603 = rtn;
                        fabric.worker.transaction.TransactionManager $tm609 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled612 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff610 = 1;
                        boolean $doBackoff611 = true;
                        boolean $retry606 = true;
                        $label604: for (boolean $commit605 = false; !$commit605;
                                        ) {
                            if ($backoffEnabled612) {
                                if ($doBackoff611) {
                                    if ($backoff610 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff610);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e607) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff610 < 5000) $backoff610 *= 2;
                                }
                                $doBackoff611 = $backoff610 <= 32 ||
                                                  !$doBackoff611;
                            }
                            $commit605 = true;
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
                            catch (final fabric.worker.RetryException $e607) {
                                $commit605 = false;
                                continue $label604;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e607) {
                                $commit605 = false;
                                fabric.common.TransactionID $currentTid608 =
                                  $tm609.getCurrentTid();
                                if ($e607.tid.isDescendantOf($currentTid608))
                                    continue $label604;
                                if ($currentTid608.parent != null) {
                                    $retry606 = false;
                                    throw $e607;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e607) {
                                $commit605 = false;
                                if ($tm609.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid608 =
                                  $tm609.getCurrentTid();
                                if ($e607.tid.isDescendantOf($currentTid608)) {
                                    $retry606 = true;
                                }
                                else if ($currentTid608.parent != null) {
                                    $retry606 = false;
                                    throw $e607;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e607) {
                                $commit605 = false;
                                if ($tm609.checkForStaleObjects())
                                    continue $label604;
                                $retry606 = false;
                                throw new fabric.worker.AbortException($e607);
                            }
                            finally {
                                if ($commit605) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e607) {
                                        $commit605 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e607) {
                                        $commit605 = false;
                                        fabric.common.TransactionID
                                          $currentTid608 =
                                          $tm609.getCurrentTid();
                                        if ($currentTid608 != null) {
                                            if ($e607.tid.equals(
                                                            $currentTid608) ||
                                                  !$e607.tid.
                                                  isDescendantOf(
                                                    $currentTid608)) {
                                                throw $e607;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit605 && $retry606) {
                                    { rtn = rtn$var603; }
                                    continue $label604;
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
                  (fabric.metrics.contracts.Contract)
                    $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
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
        
        public static final byte[] $classHash = new byte[] { -52, -74, 94, 103,
        -104, 20, -39, -121, -88, 98, -97, -128, -126, 106, -63, 52, 118, -68,
        -47, 4, 31, -9, -15, 45, -42, -75, -29, 84, -69, 91, -33, -67 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519623915000L;
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
                    boolean loop$var613 = loop;
                    fabric.worker.transaction.TransactionManager $tm619 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled622 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff620 = 1;
                    boolean $doBackoff621 = true;
                    boolean $retry616 = true;
                    $label614: for (boolean $commit615 = false; !$commit615; ) {
                        if ($backoffEnabled622) {
                            if ($doBackoff621) {
                                if ($backoff620 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff620);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e617) {
                                            
                                        }
                                    }
                                }
                                if ($backoff620 < 5000) $backoff620 *= 2;
                            }
                            $doBackoff621 = $backoff620 <= 32 || !$doBackoff621;
                        }
                        $commit615 = true;
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
                        catch (final fabric.worker.RetryException $e617) {
                            $commit615 = false;
                            continue $label614;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e617) {
                            $commit615 = false;
                            fabric.common.TransactionID $currentTid618 =
                              $tm619.getCurrentTid();
                            if ($e617.tid.isDescendantOf($currentTid618))
                                continue $label614;
                            if ($currentTid618.parent != null) {
                                $retry616 = false;
                                throw $e617;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e617) {
                            $commit615 = false;
                            if ($tm619.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid618 =
                              $tm619.getCurrentTid();
                            if ($e617.tid.isDescendantOf($currentTid618)) {
                                $retry616 = true;
                            }
                            else if ($currentTid618.parent != null) {
                                $retry616 = false;
                                throw $e617;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e617) {
                            $commit615 = false;
                            if ($tm619.checkForStaleObjects())
                                continue $label614;
                            $retry616 = false;
                            throw new fabric.worker.AbortException($e617);
                        }
                        finally {
                            if ($commit615) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e617) {
                                    $commit615 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e617) {
                                    $commit615 = false;
                                    fabric.common.TransactionID $currentTid618 =
                                      $tm619.getCurrentTid();
                                    if ($currentTid618 != null) {
                                        if ($e617.tid.equals($currentTid618) ||
                                              !$e617.tid.isDescendantOf(
                                                           $currentTid618)) {
                                            throw $e617;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit615 && $retry616) {
                                { loop = loop$var613; }
                                continue $label614;
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
                    ((fabric.metrics.contracts.Contract._Proxy)
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
                        boolean loop$var623 = loop;
                        fabric.worker.transaction.TransactionManager $tm629 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled632 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff630 = 1;
                        boolean $doBackoff631 = true;
                        boolean $retry626 = true;
                        $label624: for (boolean $commit625 = false; !$commit625;
                                        ) {
                            if ($backoffEnabled632) {
                                if ($doBackoff631) {
                                    if ($backoff630 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff630);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e627) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff630 < 5000) $backoff630 *= 2;
                                }
                                $doBackoff631 = $backoff630 <= 32 ||
                                                  !$doBackoff631;
                            }
                            $commit625 = true;
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
                            catch (final fabric.worker.RetryException $e627) {
                                $commit625 = false;
                                continue $label624;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e627) {
                                $commit625 = false;
                                fabric.common.TransactionID $currentTid628 =
                                  $tm629.getCurrentTid();
                                if ($e627.tid.isDescendantOf($currentTid628))
                                    continue $label624;
                                if ($currentTid628.parent != null) {
                                    $retry626 = false;
                                    throw $e627;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e627) {
                                $commit625 = false;
                                if ($tm629.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid628 =
                                  $tm629.getCurrentTid();
                                if ($e627.tid.isDescendantOf($currentTid628)) {
                                    $retry626 = true;
                                }
                                else if ($currentTid628.parent != null) {
                                    $retry626 = false;
                                    throw $e627;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e627) {
                                $commit625 = false;
                                if ($tm629.checkForStaleObjects())
                                    continue $label624;
                                $retry626 = false;
                                throw new fabric.worker.AbortException($e627);
                            }
                            finally {
                                if ($commit625) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e627) {
                                        $commit625 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e627) {
                                        $commit625 = false;
                                        fabric.common.TransactionID
                                          $currentTid628 =
                                          $tm629.getCurrentTid();
                                        if ($currentTid628 != null) {
                                            if ($e627.tid.equals(
                                                            $currentTid628) ||
                                                  !$e627.tid.
                                                  isDescendantOf(
                                                    $currentTid628)) {
                                                throw $e627;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit625 && $retry626) {
                                    { loop = loop$var623; }
                                    continue $label624;
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
                        boolean loop$var633 = loop;
                        fabric.worker.transaction.TransactionManager $tm639 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled642 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff640 = 1;
                        boolean $doBackoff641 = true;
                        boolean $retry636 = true;
                        $label634: for (boolean $commit635 = false; !$commit635;
                                        ) {
                            if ($backoffEnabled642) {
                                if ($doBackoff641) {
                                    if ($backoff640 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff640);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e637) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff640 < 5000) $backoff640 *= 2;
                                }
                                $doBackoff641 = $backoff640 <= 32 ||
                                                  !$doBackoff641;
                            }
                            $commit635 = true;
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
                            catch (final fabric.worker.RetryException $e637) {
                                $commit635 = false;
                                continue $label634;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e637) {
                                $commit635 = false;
                                fabric.common.TransactionID $currentTid638 =
                                  $tm639.getCurrentTid();
                                if ($e637.tid.isDescendantOf($currentTid638))
                                    continue $label634;
                                if ($currentTid638.parent != null) {
                                    $retry636 = false;
                                    throw $e637;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e637) {
                                $commit635 = false;
                                if ($tm639.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid638 =
                                  $tm639.getCurrentTid();
                                if ($e637.tid.isDescendantOf($currentTid638)) {
                                    $retry636 = true;
                                }
                                else if ($currentTid638.parent != null) {
                                    $retry636 = false;
                                    throw $e637;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e637) {
                                $commit635 = false;
                                if ($tm639.checkForStaleObjects())
                                    continue $label634;
                                $retry636 = false;
                                throw new fabric.worker.AbortException($e637);
                            }
                            finally {
                                if ($commit635) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e637) {
                                        $commit635 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e637) {
                                        $commit635 = false;
                                        fabric.common.TransactionID
                                          $currentTid638 =
                                          $tm639.getCurrentTid();
                                        if ($currentTid638 != null) {
                                            if ($e637.tid.equals(
                                                            $currentTid638) ||
                                                  !$e637.tid.
                                                  isDescendantOf(
                                                    $currentTid638)) {
                                                throw $e637;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit635 && $retry636) {
                                    { loop = loop$var633; }
                                    continue $label634;
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
                      rtn$var643 = rtn;
                    fabric.worker.transaction.TransactionManager $tm649 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled652 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff650 = 1;
                    boolean $doBackoff651 = true;
                    boolean $retry646 = true;
                    $label644: for (boolean $commit645 = false; !$commit645; ) {
                        if ($backoffEnabled652) {
                            if ($doBackoff651) {
                                if ($backoff650 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff650);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e647) {
                                            
                                        }
                                    }
                                }
                                if ($backoff650 < 5000) $backoff650 *= 2;
                            }
                            $doBackoff651 = $backoff650 <= 32 || !$doBackoff651;
                        }
                        $commit645 = true;
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
                        catch (final fabric.worker.RetryException $e647) {
                            $commit645 = false;
                            continue $label644;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e647) {
                            $commit645 = false;
                            fabric.common.TransactionID $currentTid648 =
                              $tm649.getCurrentTid();
                            if ($e647.tid.isDescendantOf($currentTid648))
                                continue $label644;
                            if ($currentTid648.parent != null) {
                                $retry646 = false;
                                throw $e647;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e647) {
                            $commit645 = false;
                            if ($tm649.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid648 =
                              $tm649.getCurrentTid();
                            if ($e647.tid.isDescendantOf($currentTid648)) {
                                $retry646 = true;
                            }
                            else if ($currentTid648.parent != null) {
                                $retry646 = false;
                                throw $e647;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e647) {
                            $commit645 = false;
                            if ($tm649.checkForStaleObjects())
                                continue $label644;
                            $retry646 = false;
                            throw new fabric.worker.AbortException($e647);
                        }
                        finally {
                            if ($commit645) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e647) {
                                    $commit645 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e647) {
                                    $commit645 = false;
                                    fabric.common.TransactionID $currentTid648 =
                                      $tm649.getCurrentTid();
                                    if ($currentTid648 != null) {
                                        if ($e647.tid.equals($currentTid648) ||
                                              !$e647.tid.isDescendantOf(
                                                           $currentTid648)) {
                                            throw $e647;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit645 && $retry646) {
                                { rtn = rtn$var643; }
                                continue $label644;
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
    
    public static final byte[] $classHash = new byte[] { -44, 124, 6, -8, -23,
    75, 45, -103, -56, -50, -44, 31, -1, 126, -20, 114, -119, -50, -112, -28,
    -37, -3, 81, 45, 68, -67, 85, -49, 57, 83, 125, -16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519623915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
