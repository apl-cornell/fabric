package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link ConjunctionContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface ConjunctionContract extends fabric.metrics.contracts.Contract {
    public fabric.lang.arrays.ObjectArray get$conjuncts();
    
    public fabric.lang.arrays.ObjectArray set$conjuncts(
      fabric.lang.arrays.ObjectArray val);
    
    public boolean get$observing();
    
    public boolean set$observing(boolean val);
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.ConjunctionContract
      fabric$metrics$contracts$ConjunctionContract$(
      fabric.metrics.contracts.Contract[] conjuncts);
    
    public void activate();
    
    public boolean handleUpdates();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension);
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
                           double otherBase);
    
    public java.lang.String toString();
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
    /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.ConjunctionContract {
        public fabric.lang.arrays.ObjectArray get$conjuncts() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$conjuncts();
        }
        
        public fabric.lang.arrays.ObjectArray set$conjuncts(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$conjuncts(val);
        }
        
        public boolean get$observing() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$observing();
        }
        
        public boolean set$observing(boolean val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$observing(val);
        }
        
        public fabric.metrics.contracts.ConjunctionContract
          fabric$metrics$contracts$ConjunctionContract$(
          fabric.metrics.contracts.Contract[] arg1) {
            return ((fabric.metrics.contracts.ConjunctionContract) fetch()).
              fabric$metrics$contracts$ConjunctionContract$(arg1);
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.ConjunctionContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(ConjunctionContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.ConjunctionContract {
        public fabric.lang.arrays.ObjectArray get$conjuncts() {
            return this.conjuncts;
        }
        
        public fabric.lang.arrays.ObjectArray set$conjuncts(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjuncts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.ObjectArray conjuncts;
        
        public boolean get$observing() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observing;
        }
        
        public boolean set$observing(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observing = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean observing;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link ConjunctionContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.ConjunctionContract
          fabric$metrics$contracts$ConjunctionContract$(
          fabric.metrics.contracts.Contract[] conjuncts) {
            java.util.HashSet conjunctsBag = new java.util.HashSet();
            for (int i = 0; i < conjuncts.length; i++) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(conjuncts[i])) instanceof fabric.metrics.contracts.ConjunctionContract) {
                    fabric.metrics.contracts.ConjunctionContract other =
                      (fabric.metrics.contracts.ConjunctionContract)
                        fabric.lang.Object._Proxy.$getProxy(conjuncts[i]);
                    for (int j = 0; j < other.get$conjuncts().get$length();
                         j++) {
                        conjunctsBag.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        other.get$conjuncts().get(j)));
                    }
                }
                else {
                    conjunctsBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              conjuncts[i]));
                }
            }
            final fabric.worker.Store s = this.$getStore();
            final fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            this.set$conjuncts(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       s).fabric$lang$arrays$ObjectArray$(
                            lbl, lbl.confPolicy(),
                            fabric.metrics.contracts.Contract._Proxy.class,
                            conjunctsBag.size()).$getProxy());
            int idx = 0;
            for (java.util.Iterator it = conjunctsBag.iterator(); it.hasNext();
                 ) {
                this.
                  get$conjuncts().
                  set(
                    idx++,
                    (fabric.metrics.contracts.Contract)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        fabric.lang.WrappedJavaInlineable.$wrap(it.next())));
            }
            fabric$metrics$contracts$Contract$();
            this.set$observing(false);
            return (fabric.metrics.contracts.ConjunctionContract)
                     this.$getProxy();
        }
        
        public void activate() {
            fabric.metrics.contracts.ConjunctionContract._Impl.
              static_activate((fabric.metrics.contracts.ConjunctionContract)
                                this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.ConjunctionContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.isActivated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm355 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled358 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff356 = 1;
                    boolean $doBackoff357 = true;
                    boolean $retry352 = true;
                    $label350: for (boolean $commit351 = false; !$commit351; ) {
                        if ($backoffEnabled358) {
                            if ($doBackoff357) {
                                if ($backoff356 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff356);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e353) {
                                            
                                        }
                                    }
                                }
                                if ($backoff356 < 5000) $backoff356 *= 2;
                            }
                            $doBackoff357 = $backoff356 <= 32 || !$doBackoff357;
                        }
                        $commit351 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e353) {
                            $commit351 = false;
                            continue $label350;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e353) {
                            $commit351 = false;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354))
                                continue $label350;
                            if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid354 =
                              $tm355.getCurrentTid();
                            if ($e353.tid.isDescendantOf($currentTid354)) {
                                $retry352 = true;
                            }
                            else if ($currentTid354.parent != null) {
                                $retry352 = false;
                                throw $e353;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e353) {
                            $commit351 = false;
                            if ($tm355.checkForStaleObjects())
                                continue $label350;
                            $retry352 = false;
                            throw new fabric.worker.AbortException($e353);
                        }
                        finally {
                            if ($commit351) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e353) {
                                    $commit351 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e353) {
                                    $commit351 = false;
                                    fabric.common.TransactionID $currentTid354 =
                                      $tm355.getCurrentTid();
                                    if ($currentTid354 != null) {
                                        if ($e353.tid.equals($currentTid354) ||
                                              !$e353.tid.isDescendantOf(
                                                           $currentTid354)) {
                                            throw $e353;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit351 && $retry352) {
                                {  }
                                continue $label350;
                            }
                        }
                    }
                }
            }
            long minExpiry = java.lang.Long.MAX_VALUE;
            for (int i = 0; i < tmp.get$conjuncts().get$length(); i++) {
                ((fabric.metrics.contracts.Contract)
                   tmp.get$conjuncts().get(i)).activate();
                minExpiry =
                  java.lang.Math.min(
                                   minExpiry,
                                   ((fabric.metrics.contracts.Contract)
                                      tmp.get$conjuncts().get(i)).getExpiry());
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$$expiry((long) minExpiry);
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                if (tmp.get$$expiry() >= java.lang.System.currentTimeMillis()) {
                    for (int j = 0; j < tmp.get$conjuncts().get$length(); j++) {
                        ((fabric.metrics.contracts.Contract)
                           tmp.get$conjuncts().get(j)).addObserver(tmp);
                    }
                    tmp.set$observing(true);
                }
            }
            else {
                {
                    long minExpiry$var359 = minExpiry;
                    fabric.worker.transaction.TransactionManager $tm365 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled368 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff366 = 1;
                    boolean $doBackoff367 = true;
                    boolean $retry362 = true;
                    $label360: for (boolean $commit361 = false; !$commit361; ) {
                        if ($backoffEnabled368) {
                            if ($doBackoff367) {
                                if ($backoff366 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff366);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e363) {
                                            
                                        }
                                    }
                                }
                                if ($backoff366 < 5000) $backoff366 *= 2;
                            }
                            $doBackoff367 = $backoff366 <= 32 || !$doBackoff367;
                        }
                        $commit361 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$$expiry((long) minExpiry);
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
                                for (int j = 0;
                                     j < tmp.get$conjuncts().get$length();
                                     j++) {
                                    ((fabric.metrics.contracts.Contract)
                                       tmp.get$conjuncts().get(j)).addObserver(
                                                                     tmp);
                                }
                                tmp.set$observing(true);
                            }
                        }
                        catch (final fabric.worker.RetryException $e363) {
                            $commit361 = false;
                            continue $label360;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e363) {
                            $commit361 = false;
                            fabric.common.TransactionID $currentTid364 =
                              $tm365.getCurrentTid();
                            if ($e363.tid.isDescendantOf($currentTid364))
                                continue $label360;
                            if ($currentTid364.parent != null) {
                                $retry362 = false;
                                throw $e363;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e363) {
                            $commit361 = false;
                            if ($tm365.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid364 =
                              $tm365.getCurrentTid();
                            if ($e363.tid.isDescendantOf($currentTid364)) {
                                $retry362 = true;
                            }
                            else if ($currentTid364.parent != null) {
                                $retry362 = false;
                                throw $e363;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e363) {
                            $commit361 = false;
                            if ($tm365.checkForStaleObjects())
                                continue $label360;
                            $retry362 = false;
                            throw new fabric.worker.AbortException($e363);
                        }
                        finally {
                            if ($commit361) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e363) {
                                    $commit361 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e363) {
                                    $commit361 = false;
                                    fabric.common.TransactionID $currentTid364 =
                                      $tm365.getCurrentTid();
                                    if ($currentTid364 != null) {
                                        if ($e363.tid.equals($currentTid364) ||
                                              !$e363.tid.isDescendantOf(
                                                           $currentTid364)) {
                                            throw $e363;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit361 && $retry362) {
                                { minExpiry = minExpiry$var359; }
                                continue $label360;
                            }
                        }
                    }
                }
            }
        }
        
        public boolean handleUpdates() {
            if (valid()) {
                this.get$lock().checkForRead();
                return refresh(false);
            }
            if (this.get$observing()) {
                for (int j = 0; j < this.get$conjuncts().get$length(); j++) {
                    ((fabric.metrics.contracts.Contract)
                       this.get$conjuncts().get(j)).
                      removeObserver(
                        (fabric.metrics.contracts.ConjunctionContract)
                          this.$getProxy());
                }
                this.set$observing(false);
            }
            return false;
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension) {
            if (!isActivated()) { return false; }
            long currentTime = java.lang.System.currentTimeMillis();
            if (this.get$observing()) {
                long curExpiry = java.lang.Long.MAX_VALUE;
                for (int i = 0; i < this.get$conjuncts().get$length(); i++) {
                    curExpiry =
                      java.lang.Math.min(
                                       curExpiry,
                                       ((fabric.metrics.contracts.Contract)
                                          this.get$conjuncts().get(
                                                                 i)).getExpiry(
                                                                       ));
                }
                if (curExpiry < currentTime) {
                    for (int j = 0; j < this.get$conjuncts().get$length();
                         j++) {
                        ((fabric.metrics.contracts.Contract)
                           this.get$conjuncts().get(j)).
                          removeObserver(
                            (fabric.metrics.contracts.ConjunctionContract)
                              this.$getProxy());
                    }
                    this.set$observing(false);
                }
                boolean result = update(curExpiry);
                return result;
            }
            return false;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            return false;
        }
        
        public java.lang.String toString() {
            java.lang.String result = "(";
            for (int i = 0; i < this.get$conjuncts().get$length(); i++) {
                if (i != 0) result += " ^ ";
                result += ((fabric.metrics.contracts.Contract)
                             this.get$conjuncts().get(i)).toString();
            }
            return result + ")";
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            final fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (!this.get$observing())
                return (fabric.lang.arrays.ObjectArray)
                         new fabric.lang.arrays.ObjectArray._Impl(local).
                         fabric$lang$arrays$ObjectArray$(
                           this.get$$updateLabel(),
                           this.get$$updateLabel().confPolicy(),
                           fabric.metrics.SampledMetric._Proxy.class, 0).
                         $getProxy();
            java.util.HashSet leavesBag = new java.util.HashSet();
            for (int i = 0; i < this.get$conjuncts().get$length(); i++) {
                fabric.lang.arrays.ObjectArray leaves =
                  ((fabric.metrics.contracts.Contract)
                     this.get$conjuncts().get(i)).getLeafSubjects();
                for (int j = 0; j < leaves.get$length(); j++) {
                    leavesBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.SampledMetric)
                                    leaves.get(j)));
                }
            }
            fabric.lang.arrays.ObjectArray result =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  local).fabric$lang$arrays$ObjectArray$(
                           this.get$$updateLabel(),
                           this.get$$updateLabel().confPolicy(),
                           fabric.metrics.SampledMetric._Proxy.class,
                           leavesBag.size()).$getProxy();
            int idx = 0;
            for (java.util.Iterator it = leavesBag.iterator(); it.hasNext(); ) {
                result.
                  set(
                    idx++,
                    (fabric.metrics.SampledMetric)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        fabric.lang.WrappedJavaInlineable.$wrap(it.next())));
            }
            return result;
        }
        
        /**
   * {@inheritDoc}
   *
   * Stops observing any evidence used by the current enforcement policy (by
   * unapplying the policy).
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            fabric.metrics.contracts.ConjunctionContract._Impl.
              static_removeObserver(
                (fabric.metrics.contracts.ConjunctionContract) this.$getProxy(),
                obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.ConjunctionContract tmp,
          fabric.metrics.util.Observer obs) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_removeObserver(
                                                          tmp, obs);
                if (!tmp.isObserved() && tmp.get$observing()) {
                    for (int i = 0; i < tmp.get$conjuncts().get$length(); i++) {
                        ((fabric.metrics.contracts.Contract)
                           tmp.get$conjuncts().get(i)).removeObserver(tmp);
                    }
                    tmp.set$observing(false);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm374 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled377 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff375 = 1;
                    boolean $doBackoff376 = true;
                    boolean $retry371 = true;
                    $label369: for (boolean $commit370 = false; !$commit370; ) {
                        if ($backoffEnabled377) {
                            if ($doBackoff376) {
                                if ($backoff375 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff375);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e372) {
                                            
                                        }
                                    }
                                }
                                if ($backoff375 < 5000) $backoff375 *= 2;
                            }
                            $doBackoff376 = $backoff375 <= 32 || !$doBackoff376;
                        }
                        $commit370 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                for (int i = 0;
                                     i < tmp.get$conjuncts().get$length();
                                     i++) {
                                    ((fabric.metrics.contracts.Contract)
                                       tmp.get$conjuncts().get(i)).
                                      removeObserver(tmp);
                                }
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e372) {
                            $commit370 = false;
                            continue $label369;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e372) {
                            $commit370 = false;
                            fabric.common.TransactionID $currentTid373 =
                              $tm374.getCurrentTid();
                            if ($e372.tid.isDescendantOf($currentTid373))
                                continue $label369;
                            if ($currentTid373.parent != null) {
                                $retry371 = false;
                                throw $e372;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e372) {
                            $commit370 = false;
                            if ($tm374.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid373 =
                              $tm374.getCurrentTid();
                            if ($e372.tid.isDescendantOf($currentTid373)) {
                                $retry371 = true;
                            }
                            else if ($currentTid373.parent != null) {
                                $retry371 = false;
                                throw $e372;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e372) {
                            $commit370 = false;
                            if ($tm374.checkForStaleObjects())
                                continue $label369;
                            $retry371 = false;
                            throw new fabric.worker.AbortException($e372);
                        }
                        finally {
                            if ($commit370) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e372) {
                                    $commit370 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e372) {
                                    $commit370 = false;
                                    fabric.common.TransactionID $currentTid373 =
                                      $tm374.getCurrentTid();
                                    if ($currentTid373 != null) {
                                        if ($e372.tid.equals($currentTid373) ||
                                              !$e372.tid.isDescendantOf(
                                                           $currentTid373)) {
                                            throw $e372;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit370 && $retry371) {
                                {  }
                                continue $label369;
                            }
                        }
                    }
                }
            }
        }
        
        public void acquireReconfigLocks() {
            this.get$lock().acquireOptimistic();
            if (this.get$observing()) {
                for (int i = 0; i < this.get$conjuncts().get$length(); i++) {
                    ((fabric.metrics.contracts.Contract)
                       this.get$conjuncts().get(i)).acquireReconfigLocks();
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.ConjunctionContract._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.conjuncts, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.observing);
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
            this.conjuncts =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.observing = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.ConjunctionContract._Impl src =
              (fabric.metrics.contracts.ConjunctionContract._Impl) other;
            this.conjuncts = src.conjuncts;
            this.observing = src.observing;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.ConjunctionContract._Static {
            public _Proxy(fabric.metrics.contracts.ConjunctionContract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.ConjunctionContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  ConjunctionContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    ConjunctionContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.ConjunctionContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.ConjunctionContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.ConjunctionContract._Static {
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
                return new fabric.metrics.contracts.ConjunctionContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -127, 9, -23, 83, -66,
    -108, -37, 13, -91, -20, -39, 99, -109, -92, 78, -111, -40, -128, -55, 106,
    68, -50, 16, 61, -35, 123, -125, 68, -77, -101, 69, 40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521305458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
