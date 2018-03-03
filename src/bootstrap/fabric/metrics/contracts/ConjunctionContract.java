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
                    fabric.worker.transaction.TransactionManager $tm317 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled320 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff318 = 1;
                    boolean $doBackoff319 = true;
                    boolean $retry314 = true;
                    $label312: for (boolean $commit313 = false; !$commit313; ) {
                        if ($backoffEnabled320) {
                            if ($doBackoff319) {
                                if ($backoff318 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff318);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e315) {
                                            
                                        }
                                    }
                                }
                                if ($backoff318 < 5000) $backoff318 *= 2;
                            }
                            $doBackoff319 = $backoff318 <= 32 || !$doBackoff319;
                        }
                        $commit313 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e315) {
                            $commit313 = false;
                            continue $label312;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e315) {
                            $commit313 = false;
                            fabric.common.TransactionID $currentTid316 =
                              $tm317.getCurrentTid();
                            if ($e315.tid.isDescendantOf($currentTid316))
                                continue $label312;
                            if ($currentTid316.parent != null) {
                                $retry314 = false;
                                throw $e315;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e315) {
                            $commit313 = false;
                            if ($tm317.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid316 =
                              $tm317.getCurrentTid();
                            if ($e315.tid.isDescendantOf($currentTid316)) {
                                $retry314 = true;
                            }
                            else if ($currentTid316.parent != null) {
                                $retry314 = false;
                                throw $e315;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e315) {
                            $commit313 = false;
                            if ($tm317.checkForStaleObjects())
                                continue $label312;
                            $retry314 = false;
                            throw new fabric.worker.AbortException($e315);
                        }
                        finally {
                            if ($commit313) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e315) {
                                    $commit313 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e315) {
                                    $commit313 = false;
                                    fabric.common.TransactionID $currentTid316 =
                                      $tm317.getCurrentTid();
                                    if ($currentTid316 != null) {
                                        if ($e315.tid.equals($currentTid316) ||
                                              !$e315.tid.isDescendantOf(
                                                           $currentTid316)) {
                                            throw $e315;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit313 && $retry314) {
                                {  }
                                continue $label312;
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
                    long minExpiry$var321 = minExpiry;
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
                                { minExpiry = minExpiry$var321; }
                                continue $label322;
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
                    fabric.worker.transaction.TransactionManager $tm336 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled339 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff337 = 1;
                    boolean $doBackoff338 = true;
                    boolean $retry333 = true;
                    $label331: for (boolean $commit332 = false; !$commit332; ) {
                        if ($backoffEnabled339) {
                            if ($doBackoff338) {
                                if ($backoff337 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff337);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e334) {
                                            
                                        }
                                    }
                                }
                                if ($backoff337 < 5000) $backoff337 *= 2;
                            }
                            $doBackoff338 = $backoff337 <= 32 || !$doBackoff338;
                        }
                        $commit332 = true;
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
                        catch (final fabric.worker.RetryException $e334) {
                            $commit332 = false;
                            continue $label331;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e334) {
                            $commit332 = false;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid.isDescendantOf($currentTid335))
                                continue $label331;
                            if ($currentTid335.parent != null) {
                                $retry333 = false;
                                throw $e334;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e334) {
                            $commit332 = false;
                            if ($tm336.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid335 =
                              $tm336.getCurrentTid();
                            if ($e334.tid.isDescendantOf($currentTid335)) {
                                $retry333 = true;
                            }
                            else if ($currentTid335.parent != null) {
                                $retry333 = false;
                                throw $e334;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e334) {
                            $commit332 = false;
                            if ($tm336.checkForStaleObjects())
                                continue $label331;
                            $retry333 = false;
                            throw new fabric.worker.AbortException($e334);
                        }
                        finally {
                            if ($commit332) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e334) {
                                    $commit332 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e334) {
                                    $commit332 = false;
                                    fabric.common.TransactionID $currentTid335 =
                                      $tm336.getCurrentTid();
                                    if ($currentTid335 != null) {
                                        if ($e334.tid.equals($currentTid335) ||
                                              !$e334.tid.isDescendantOf(
                                                           $currentTid335)) {
                                            throw $e334;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit332 && $retry333) {
                                {  }
                                continue $label331;
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
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
