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
    
    public static final byte[] $classHash = new byte[] { -1, 44, -41, 90, -107,
    -68, 65, 87, 57, 111, -99, 68, -37, 22, 7, -80, 8, -15, 24, 84, -61, 38, 65,
    11, -55, -57, -56, 81, -20, 105, 126, -53 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519623915000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
