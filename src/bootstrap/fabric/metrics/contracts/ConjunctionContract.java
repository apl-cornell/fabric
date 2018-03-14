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
                    fabric.worker.transaction.TransactionManager $tm327 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled330 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff328 = 1;
                    boolean $doBackoff329 = true;
                    boolean $retry324 = true;
                    $label322: for (boolean $commit323 = false; !$commit323; ) {
                        if ($backoffEnabled330) {
                            if ($doBackoff329) {
                                if ($backoff328 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff328);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e325) {
                                            
                                        }
                                    }
                                }
                                if ($backoff328 < 5000) $backoff328 *= 2;
                            }
                            $doBackoff329 = $backoff328 <= 32 || !$doBackoff329;
                        }
                        $commit323 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e325) {
                            $commit323 = false;
                            continue $label322;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e325) {
                            $commit323 = false;
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid.isDescendantOf($currentTid326))
                                continue $label322;
                            if ($currentTid326.parent != null) {
                                $retry324 = false;
                                throw $e325;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e325) {
                            $commit323 = false;
                            if ($tm327.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid326 =
                              $tm327.getCurrentTid();
                            if ($e325.tid.isDescendantOf($currentTid326)) {
                                $retry324 = true;
                            }
                            else if ($currentTid326.parent != null) {
                                $retry324 = false;
                                throw $e325;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e325) {
                            $commit323 = false;
                            if ($tm327.checkForStaleObjects())
                                continue $label322;
                            $retry324 = false;
                            throw new fabric.worker.AbortException($e325);
                        }
                        finally {
                            if ($commit323) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e325) {
                                    $commit323 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e325) {
                                    $commit323 = false;
                                    fabric.common.TransactionID $currentTid326 =
                                      $tm327.getCurrentTid();
                                    if ($currentTid326 != null) {
                                        if ($e325.tid.equals($currentTid326) ||
                                              !$e325.tid.isDescendantOf(
                                                           $currentTid326)) {
                                            throw $e325;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit323 && $retry324) {
                                {  }
                                continue $label322;
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
                    long minExpiry$var331 = minExpiry;
                    fabric.worker.transaction.TransactionManager $tm337 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled340 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff338 = 1;
                    boolean $doBackoff339 = true;
                    boolean $retry334 = true;
                    $label332: for (boolean $commit333 = false; !$commit333; ) {
                        if ($backoffEnabled340) {
                            if ($doBackoff339) {
                                if ($backoff338 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff338);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e335) {
                                            
                                        }
                                    }
                                }
                                if ($backoff338 < 5000) $backoff338 *= 2;
                            }
                            $doBackoff339 = $backoff338 <= 32 || !$doBackoff339;
                        }
                        $commit333 = true;
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
                        catch (final fabric.worker.RetryException $e335) {
                            $commit333 = false;
                            continue $label332;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e335) {
                            $commit333 = false;
                            fabric.common.TransactionID $currentTid336 =
                              $tm337.getCurrentTid();
                            if ($e335.tid.isDescendantOf($currentTid336))
                                continue $label332;
                            if ($currentTid336.parent != null) {
                                $retry334 = false;
                                throw $e335;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e335) {
                            $commit333 = false;
                            if ($tm337.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid336 =
                              $tm337.getCurrentTid();
                            if ($e335.tid.isDescendantOf($currentTid336)) {
                                $retry334 = true;
                            }
                            else if ($currentTid336.parent != null) {
                                $retry334 = false;
                                throw $e335;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e335) {
                            $commit333 = false;
                            if ($tm337.checkForStaleObjects())
                                continue $label332;
                            $retry334 = false;
                            throw new fabric.worker.AbortException($e335);
                        }
                        finally {
                            if ($commit333) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e335) {
                                    $commit333 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e335) {
                                    $commit333 = false;
                                    fabric.common.TransactionID $currentTid336 =
                                      $tm337.getCurrentTid();
                                    if ($currentTid336 != null) {
                                        if ($e335.tid.equals($currentTid336) ||
                                              !$e335.tid.isDescendantOf(
                                                           $currentTid336)) {
                                            throw $e335;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit333 && $retry334) {
                                { minExpiry = minExpiry$var331; }
                                continue $label332;
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
                    fabric.worker.transaction.TransactionManager $tm346 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled349 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff347 = 1;
                    boolean $doBackoff348 = true;
                    boolean $retry343 = true;
                    $label341: for (boolean $commit342 = false; !$commit342; ) {
                        if ($backoffEnabled349) {
                            if ($doBackoff348) {
                                if ($backoff347 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff347);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e344) {
                                            
                                        }
                                    }
                                }
                                if ($backoff347 < 5000) $backoff347 *= 2;
                            }
                            $doBackoff348 = $backoff347 <= 32 || !$doBackoff348;
                        }
                        $commit342 = true;
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
                        catch (final fabric.worker.RetryException $e344) {
                            $commit342 = false;
                            continue $label341;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e344) {
                            $commit342 = false;
                            fabric.common.TransactionID $currentTid345 =
                              $tm346.getCurrentTid();
                            if ($e344.tid.isDescendantOf($currentTid345))
                                continue $label341;
                            if ($currentTid345.parent != null) {
                                $retry343 = false;
                                throw $e344;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e344) {
                            $commit342 = false;
                            if ($tm346.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid345 =
                              $tm346.getCurrentTid();
                            if ($e344.tid.isDescendantOf($currentTid345)) {
                                $retry343 = true;
                            }
                            else if ($currentTid345.parent != null) {
                                $retry343 = false;
                                throw $e344;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e344) {
                            $commit342 = false;
                            if ($tm346.checkForStaleObjects())
                                continue $label341;
                            $retry343 = false;
                            throw new fabric.worker.AbortException($e344);
                        }
                        finally {
                            if ($commit342) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e344) {
                                    $commit342 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e344) {
                                    $commit342 = false;
                                    fabric.common.TransactionID $currentTid345 =
                                      $tm346.getCurrentTid();
                                    if ($currentTid345 != null) {
                                        if ($e344.tid.equals($currentTid345) ||
                                              !$e344.tid.isDescendantOf(
                                                           $currentTid345)) {
                                            throw $e344;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit342 && $retry343) {
                                {  }
                                continue $label341;
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
