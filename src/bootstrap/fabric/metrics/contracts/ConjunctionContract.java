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
                    fabric.worker.transaction.TransactionManager $tm345 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled348 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff346 = 1;
                    boolean $doBackoff347 = true;
                    boolean $retry342 = true;
                    $label340: for (boolean $commit341 = false; !$commit341; ) {
                        if ($backoffEnabled348) {
                            if ($doBackoff347) {
                                if ($backoff346 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff346);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e343) {
                                            
                                        }
                                    }
                                }
                                if ($backoff346 < 5000) $backoff346 *= 2;
                            }
                            $doBackoff347 = $backoff346 <= 32 || !$doBackoff347;
                        }
                        $commit341 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e343) {
                            $commit341 = false;
                            continue $label340;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e343) {
                            $commit341 = false;
                            fabric.common.TransactionID $currentTid344 =
                              $tm345.getCurrentTid();
                            if ($e343.tid.isDescendantOf($currentTid344))
                                continue $label340;
                            if ($currentTid344.parent != null) {
                                $retry342 = false;
                                throw $e343;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e343) {
                            $commit341 = false;
                            if ($tm345.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid344 =
                              $tm345.getCurrentTid();
                            if ($e343.tid.isDescendantOf($currentTid344)) {
                                $retry342 = true;
                            }
                            else if ($currentTid344.parent != null) {
                                $retry342 = false;
                                throw $e343;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e343) {
                            $commit341 = false;
                            if ($tm345.checkForStaleObjects())
                                continue $label340;
                            $retry342 = false;
                            throw new fabric.worker.AbortException($e343);
                        }
                        finally {
                            if ($commit341) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e343) {
                                    $commit341 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e343) {
                                    $commit341 = false;
                                    fabric.common.TransactionID $currentTid344 =
                                      $tm345.getCurrentTid();
                                    if ($currentTid344 != null) {
                                        if ($e343.tid.equals($currentTid344) ||
                                              !$e343.tid.isDescendantOf(
                                                           $currentTid344)) {
                                            throw $e343;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit341 && $retry342) {
                                {  }
                                continue $label340;
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
                    long minExpiry$var349 = minExpiry;
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
                                { minExpiry = minExpiry$var349; }
                                continue $label350;
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
                    fabric.worker.transaction.TransactionManager $tm364 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled367 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff365 = 1;
                    boolean $doBackoff366 = true;
                    boolean $retry361 = true;
                    $label359: for (boolean $commit360 = false; !$commit360; ) {
                        if ($backoffEnabled367) {
                            if ($doBackoff366) {
                                if ($backoff365 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff365);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e362) {
                                            
                                        }
                                    }
                                }
                                if ($backoff365 < 5000) $backoff365 *= 2;
                            }
                            $doBackoff366 = $backoff365 <= 32 || !$doBackoff366;
                        }
                        $commit360 = true;
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
                        catch (final fabric.worker.RetryException $e362) {
                            $commit360 = false;
                            continue $label359;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e362) {
                            $commit360 = false;
                            fabric.common.TransactionID $currentTid363 =
                              $tm364.getCurrentTid();
                            if ($e362.tid.isDescendantOf($currentTid363))
                                continue $label359;
                            if ($currentTid363.parent != null) {
                                $retry361 = false;
                                throw $e362;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e362) {
                            $commit360 = false;
                            if ($tm364.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid363 =
                              $tm364.getCurrentTid();
                            if ($e362.tid.isDescendantOf($currentTid363)) {
                                $retry361 = true;
                            }
                            else if ($currentTid363.parent != null) {
                                $retry361 = false;
                                throw $e362;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e362) {
                            $commit360 = false;
                            if ($tm364.checkForStaleObjects())
                                continue $label359;
                            $retry361 = false;
                            throw new fabric.worker.AbortException($e362);
                        }
                        finally {
                            if ($commit360) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e362) {
                                    $commit360 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e362) {
                                    $commit360 = false;
                                    fabric.common.TransactionID $currentTid363 =
                                      $tm364.getCurrentTid();
                                    if ($currentTid363 != null) {
                                        if ($e362.tid.equals($currentTid363) ||
                                              !$e362.tid.isDescendantOf(
                                                           $currentTid363)) {
                                            throw $e362;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit360 && $retry361) {
                                {  }
                                continue $label359;
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
    
    public static final byte[] $classHash = new byte[] { -61, 81, 76, -70, -53,
    -7, -63, 117, 25, 110, -64, 36, -64, 44, 8, 29, -114, 42, 80, 58, -79, 118,
    84, 25, -66, 71, 69, -66, -74, -123, 89, -117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520199002000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
