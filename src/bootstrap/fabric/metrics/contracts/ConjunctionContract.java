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
    
    public static final byte[] $classHash = new byte[] { 108, 97, 71, 4, 95,
    -25, -91, -75, 16, 113, -119, -57, 8, -115, 8, -69, -11, 11, -14, 97, 44,
    57, 86, -57, 42, -6, -25, -64, 107, 19, 22, -5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
