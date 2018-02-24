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
            if (!this.get$observing())
                return (fabric.lang.arrays.ObjectArray)
                         new fabric.lang.arrays.ObjectArray._Impl(
                           this.$getStore()).
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
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
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
            this.get$lock().acquire();
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
    
    public static final byte[] $classHash = new byte[] { 52, 20, -42, -29, 23,
    -103, 93, -52, -8, -120, -111, 47, -116, 27, 0, 7, -127, 1, -81, 127, 124,
    -87, 117, -114, 61, -77, -92, -59, 57, -19, 108, 123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519504057000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
