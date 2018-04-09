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
    public boolean get$observing();
    
    public boolean set$observing(boolean val);
    
    public fabric.lang.arrays.ObjectArray get$conjuncts();
    
    public fabric.lang.arrays.ObjectArray set$conjuncts(fabric.lang.arrays.ObjectArray val);
    
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
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.ConjunctionContract {
        public boolean get$observing() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$observing();
        }
        
        public boolean set$observing(boolean val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$observing(val);
        }
        
        public fabric.lang.arrays.ObjectArray get$conjuncts() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$conjuncts();
        }
        
        public fabric.lang.arrays.ObjectArray set$conjuncts(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$conjuncts(val);
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
        
        private fabric.lang.arrays.ObjectArray conjuncts;
        
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
            fabric.metrics.contracts.Contract[] conjuncts1 =
              new fabric.metrics.contracts.Contract[conjunctsBag.size()];
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
                fabric.metrics.contracts.Contract
                  c =
                  (fabric.metrics.contracts.Contract)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(it.next()));
                this.get$conjuncts().set(idx, c);
                conjuncts1[idx++] = c;
            }
            fabric$metrics$contracts$Contract$();
            this.set$observing(false);
            this.
              set$currentPolicy(
                ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                   new fabric.metrics.contracts.enforcement.WitnessPolicy._Impl(
                     this.$getStore()).$getProxy()).
                    fabric$metrics$contracts$enforcement$WitnessPolicy$(
                      conjuncts1));
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
                    fabric.worker.transaction.TransactionManager $tm415 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled418 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff416 = 1;
                    boolean $doBackoff417 = true;
                    boolean $retry412 = true;
                    $label410: for (boolean $commit411 = false; !$commit411; ) {
                        if ($backoffEnabled418) {
                            if ($doBackoff417) {
                                if ($backoff416 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff416);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e413) {
                                            
                                        }
                                    }
                                }
                                if ($backoff416 < 5000) $backoff416 *= 2;
                            }
                            $doBackoff417 = $backoff416 <= 32 || !$doBackoff417;
                        }
                        $commit411 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e413) {
                            $commit411 = false;
                            continue $label410;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e413) {
                            $commit411 = false;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414))
                                continue $label410;
                            if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414)) {
                                $retry412 = true;
                            }
                            else if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects())
                                continue $label410;
                            $retry412 = false;
                            throw new fabric.worker.AbortException($e413);
                        }
                        finally {
                            if ($commit411) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e413) {
                                    $commit411 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e413) {
                                    $commit411 = false;
                                    fabric.common.TransactionID $currentTid414 =
                                      $tm415.getCurrentTid();
                                    if ($currentTid414 != null) {
                                        if ($e413.tid.equals($currentTid414) ||
                                              !$e413.tid.isDescendantOf(
                                                           $currentTid414)) {
                                            throw $e413;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit411 && $retry412) {
                                {  }
                                continue $label410;
                            }
                        }
                    }
                }
            }
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                if (tmp.get$$expiry() >= java.lang.System.currentTimeMillis()) {
                    tmp.set$observing(true);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry421 = true;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff425);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$observing(true);
                            }
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label419;
                            if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry421 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label419;
                            $retry421 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit420 = false;
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit420 && $retry421) {
                                {  }
                                continue $label419;
                            }
                        }
                    }
                }
            }
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
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry < currentTime) {
                    this.get$currentPolicy().
                      unapply((fabric.metrics.contracts.ConjunctionContract)
                                this.$getProxy());
                    this.set$currentPolicy(null);
                    this.set$observing(false);
                }
                return update(curExpiry, asyncExtension);
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
                    tmp.set$observing(false);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm433 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled436 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff434 = 1;
                    boolean $doBackoff435 = true;
                    boolean $retry430 = true;
                    $label428: for (boolean $commit429 = false; !$commit429; ) {
                        if ($backoffEnabled436) {
                            if ($doBackoff435) {
                                if ($backoff434 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff434);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e431) {
                                            
                                        }
                                    }
                                }
                                if ($backoff434 < 5000) $backoff434 *= 2;
                            }
                            $doBackoff435 = $backoff434 <= 32 || !$doBackoff435;
                        }
                        $commit429 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e431) {
                            $commit429 = false;
                            continue $label428;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e431) {
                            $commit429 = false;
                            fabric.common.TransactionID $currentTid432 =
                              $tm433.getCurrentTid();
                            if ($e431.tid.isDescendantOf($currentTid432))
                                continue $label428;
                            if ($currentTid432.parent != null) {
                                $retry430 = false;
                                throw $e431;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e431) {
                            $commit429 = false;
                            if ($tm433.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid432 =
                              $tm433.getCurrentTid();
                            if ($e431.tid.isDescendantOf($currentTid432)) {
                                $retry430 = true;
                            }
                            else if ($currentTid432.parent != null) {
                                $retry430 = false;
                                throw $e431;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e431) {
                            $commit429 = false;
                            if ($tm433.checkForStaleObjects())
                                continue $label428;
                            $retry430 = false;
                            throw new fabric.worker.AbortException($e431);
                        }
                        finally {
                            if ($commit429) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e431) {
                                    $commit429 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e431) {
                                    $commit429 = false;
                                    fabric.common.TransactionID $currentTid432 =
                                      $tm433.getCurrentTid();
                                    if ($currentTid432 != null) {
                                        if ($e431.tid.equals($currentTid432) ||
                                              !$e431.tid.isDescendantOf(
                                                           $currentTid432)) {
                                            throw $e431;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit429 && $retry430) {
                                {  }
                                continue $label428;
                            }
                        }
                    }
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
            out.writeBoolean(this.observing);
            $writeRef($getStore(), this.conjuncts, refTypes, out,
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
            this.observing = in.readBoolean();
            this.conjuncts =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.ConjunctionContract._Impl src =
              (fabric.metrics.contracts.ConjunctionContract._Impl) other;
            this.observing = src.observing;
            this.conjuncts = src.conjuncts;
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
    
    public static final byte[] $classHash = new byte[] { 121, 109, 52, -44,
    -124, 14, 26, 35, 53, 45, -91, -18, -2, 9, 64, -26, -25, 113, 55, 124, -107,
    -123, -85, 110, 19, -14, 42, 35, 69, -90, 126, 82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523309345000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
