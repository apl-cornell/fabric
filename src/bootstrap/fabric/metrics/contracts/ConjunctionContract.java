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
                        try { if (tmp.isActivated()) return; }
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
                    fabric.worker.transaction.TransactionManager $tm442 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled445 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff443 = 1;
                    boolean $doBackoff444 = true;
                    boolean $retry439 = true;
                    $label437: for (boolean $commit438 = false; !$commit438; ) {
                        if ($backoffEnabled445) {
                            if ($doBackoff444) {
                                if ($backoff443 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff443);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e440) {
                                            
                                        }
                                    }
                                }
                                if ($backoff443 < 5000) $backoff443 *= 2;
                            }
                            $doBackoff444 = $backoff443 <= 32 || !$doBackoff444;
                        }
                        $commit438 = true;
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
                        catch (final fabric.worker.RetryException $e440) {
                            $commit438 = false;
                            continue $label437;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e440) {
                            $commit438 = false;
                            fabric.common.TransactionID $currentTid441 =
                              $tm442.getCurrentTid();
                            if ($e440.tid.isDescendantOf($currentTid441))
                                continue $label437;
                            if ($currentTid441.parent != null) {
                                $retry439 = false;
                                throw $e440;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e440) {
                            $commit438 = false;
                            if ($tm442.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid441 =
                              $tm442.getCurrentTid();
                            if ($e440.tid.isDescendantOf($currentTid441)) {
                                $retry439 = true;
                            }
                            else if ($currentTid441.parent != null) {
                                $retry439 = false;
                                throw $e440;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e440) {
                            $commit438 = false;
                            if ($tm442.checkForStaleObjects())
                                continue $label437;
                            $retry439 = false;
                            throw new fabric.worker.AbortException($e440);
                        }
                        finally {
                            if ($commit438) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e440) {
                                    $commit438 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e440) {
                                    $commit438 = false;
                                    fabric.common.TransactionID $currentTid441 =
                                      $tm442.getCurrentTid();
                                    if ($currentTid441 != null) {
                                        if ($e440.tid.equals($currentTid441) ||
                                              !$e440.tid.isDescendantOf(
                                                           $currentTid441)) {
                                            throw $e440;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit438 && $retry439) {
                                {  }
                                continue $label437;
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
                    fabric.worker.transaction.TransactionManager $tm451 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled454 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff452 = 1;
                    boolean $doBackoff453 = true;
                    boolean $retry448 = true;
                    $label446: for (boolean $commit447 = false; !$commit447; ) {
                        if ($backoffEnabled454) {
                            if ($doBackoff453) {
                                if ($backoff452 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff452);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e449) {
                                            
                                        }
                                    }
                                }
                                if ($backoff452 < 5000) $backoff452 *= 2;
                            }
                            $doBackoff453 = $backoff452 <= 32 || !$doBackoff453;
                        }
                        $commit447 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e449) {
                            $commit447 = false;
                            continue $label446;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e449) {
                            $commit447 = false;
                            fabric.common.TransactionID $currentTid450 =
                              $tm451.getCurrentTid();
                            if ($e449.tid.isDescendantOf($currentTid450))
                                continue $label446;
                            if ($currentTid450.parent != null) {
                                $retry448 = false;
                                throw $e449;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e449) {
                            $commit447 = false;
                            if ($tm451.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid450 =
                              $tm451.getCurrentTid();
                            if ($e449.tid.isDescendantOf($currentTid450)) {
                                $retry448 = true;
                            }
                            else if ($currentTid450.parent != null) {
                                $retry448 = false;
                                throw $e449;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e449) {
                            $commit447 = false;
                            if ($tm451.checkForStaleObjects())
                                continue $label446;
                            $retry448 = false;
                            throw new fabric.worker.AbortException($e449);
                        }
                        finally {
                            if ($commit447) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e449) {
                                    $commit447 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e449) {
                                    $commit447 = false;
                                    fabric.common.TransactionID $currentTid450 =
                                      $tm451.getCurrentTid();
                                    if ($currentTid450 != null) {
                                        if ($e449.tid.equals($currentTid450) ||
                                              !$e449.tid.isDescendantOf(
                                                           $currentTid450)) {
                                            throw $e449;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit447 && $retry448) {
                                {  }
                                continue $label446;
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
    public static final long jlc$SourceLastModified$fabil = 1523829670000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZe2wUxxmfO9tnnzG2MeFlbDD4gsTrruTRKrhFwAWC4QDXNmlrGpy9vbnzwt7usTtnzmlAaRsK5Q/6CK+0CaoEKQ1xSZoKRVFFlValISJCTYTaRhUEKUrzAJKmaVr6SNLvm5l77e0dOEoszTdz8/6++X2/b2Y9epXU2BaZHVeimh5kIylqB1cq0e5Ij2LZNBbWFdvuh9pBdVx194E3j8VmeIk3QhpUxTANTVX0QcNmpDGyWRlWQgZloQ293V0biV/FgasUe4gR78blGYt0pEx9JKGbTC5SMv/++aF9Bzc1P11FmgZIk2b0MYVpatg0GM2wAdKQpMkotexlsRiNDZAJBqWxPmppiq7dBx1NY4C02FrCUFjaonYvtU19GDu22OkUtfia2UrcvgnbttIqMy3YfrPYfpppeiii2awrQnxxjeoxeyvZQaojpCauKwnoODmS1SLEZwytxHroXq/BNq24otLskOotmhFjZKZzRE7jwBroAENrk5QNmbmlqg0FKkiL2JKuGIlQH7M0IwFda8w0rMJIa9lJoVNdSlG3KAk6yMhUZ78e0QS9/NwsOISRSc5ufCY4s1bHmRWc1tV1X9z7DWOV4SUe2HOMqjruvw4GzXAM6qVxalFDpWJgw7zIAWXyqd1eQqDzJEdn0eeZ+99bumDGc2dEn+kufdZHN1OVDapHo40vtYXn3lGF26hLmbaGUCjSnJ9qj2zpyqQA7ZNzM2JjMNv4XO/vv/bAcXrZS+q7iU819XQSUDVBNZMpTafWXdSglsJorJv4qREL8/ZuUgvliGZQUbs+Hrcp6ybVOq/ymfw3mCgOU6CJaqGsGXEzW04pbIiXMylCSC0k4oF0hpCpCyBvJ6Tql4xsDA2ZSRqK6mm6DeAdgkQVSx0Kgd9amhqyLTVkpQ2mQSdZBSiCzA4B1JmlqAxQYhqb04aKqoZlZRC2lfpsp8+gds3bPB4w/EzVjNGoYsMpSkQt79HBaVaZeoxag6q+91Q3mXjqYY4qP3qCDWjmdvMAEtqcHFI4dl96+Yr3TgyeFYjEsdKsjCwQew7KPQdzew667Bm22YC+FwQ2CwKbjXoywfDh7ic4xHw298XczA0w8+KUrrC4aSUzxOPhat7Ex3NsATK2AOMAqTTM7btn9b27Z1cBqFPbqvGcoWvA6WJ5YuqGkgJ+M6g27Xrzn08e2G7mnY2RQAkHlI5EH57ttJllqjQGHJmffl6HcnLw1PaAF/nHj8ZRALzAMzOcaxT5cleWF9EaNREyDm2g6NiUJbN6NmSZ2/I1HAuNKFoELNBYjg1ySv1SX+rRP59761YebLLs21RA032UdRV4PE7WxH17Qt72/Ral0O/CoZ6H9l/dtZEbHnp0ui0YQBkGT1fAxU1r55mtr7x68eh5b/6wGPGl0lFdUzNclwkfw58H0keY0G2xAnMg77CkjI4cZ6Rw5Tn5vQF76JTjzg5sMJJmTItrSlSniJT/Nd286OSVvc3iuHWoEcazyILrT5Cvn7acPHB2079m8Gk8KkavvP3y3QQlTszPvMyylBHcR+abL7c//LzyKCAfCM3W7qOcowi3B+EHeAu3xUIuFznabkMxW1irjdd77dLwsBLjbB6LA6HRR1rDSy4LBshhEeeY5cIAdysFbnLL8eQH3tm+015SO0CaeYhXDHa3AqwGMBiAIG2HZWWEjC9qLw64Irp05XytzekHBcs6vSDPPFDG3liuF8AXwAFD1KOR2iDNBHr/UOaXsXViCuVNGQ/hhcV8SCeXc1DMzYKxNmVpw4CsTG5SL07ql5O9LfPXCiZlxG9GbWoNA4O5nESPpSXBmYZloKa79+35OLh3n0ChuM10llwoCseIGw1XdTyK+RlYZValVfiIlW88uf1XP9u+S0T7luLYvMJIJ3/+xw9fDB669IILs9dGTVOniiHIBOXnc/ZoRnvcDKmDkOpOmTe6GHmVu5G9WFwCRtOSyTRD3+KqzWcclzxs2KBgu+PqDBGGe5Cw4rlj16adCrx1TSjnvFAVdPzb6KuXXx7ffoKTcDXGSA4c50209KJZdH/kO2zImaAJTTBFJO9/Zf4+I8qnEe8p3GIslSapwUJf0ZhBbbvHBH4ckZeKz34Rgf1JjMyqFOB5CTu25mjKI+MvBwyKLyMWHD+x8NUK0JjHwPM1Q9GzsPDp1EiwId55mXQAzO5kpAqODYs9ZfyZzyfmQfF1FPfwAZncpr2SQ6XOgrGRr+BebhoUAcrbpgFA8Vqim/A8y5lI3Ek0M5h7NEXFhZRmSsxicYMWvwfXcpTlyfbS5fY7wlteTwhgz3QA29n78bWjL9w1R/2hl1TlWLXkaVQ8qKuYS+stCi87o7+IUTvEYd2gZSvEqq0V2nhlEg5bRTNn7dmcN78IF8KWnIiERTtRmDlf5H8+eaV/WuajBXRUECgJMmd7udcXZ82j39p3OLb+sUVeucsVcOrMTC3U6TDVC6by8/Lm3DYwQJAEJHhd1Lwkc7uQFfNc6tCAB5g6OcSSue7UYKELnKYXwmk1GK6A+DbBje0PI+8eyCqyPrceGos0IuQJ2bBB5msZWfPJieVOAP8wja3lPyVPfZrTcQ36y/PMd7LEsqcSsaDY6MYGO0vYAH+qKBKlbswPXqzKB6MwKgD9BxXaHkLxPRQc6OkMr/12hRH7UexgZKGwWUDaLJDj5oDL4yuQB58DslMhrYabU4/Ml40NsjhkqcwXl4dsoQKPVGg7jOIgI3WwZ34NcyP96mFTizl04UywBNI62MhpmR8qo0u5Gwm8QvHbmOPq1yxnOyjz717XMwVZoTxeQVfOUUfg+SVWHcyqjNU/cajXgKNuhQSu2nhW5kdvUD241fpTlsmATGnModw4OdcRmf/ohpQL8+VOVlDuGRQn4CJp0Tg4Pv8UE3bDH+JuD5yfJfP5Y8MfDpkn80D5vVfxrVVlg8wkx71GEA02troBzhcz0zKq/4bv6dcVND+N4lnQXEumdE0A2FXzVkjfh23/Vua/GJvmOOQpmR+/Mc97sULbORTPg+cxU3wXdQnIBQ3TnN9o3DTEg/kxIS1nZX58bBrikMdlfuTGNHylQttfUJwHf0tQFqFKvC/N7xc277yu+HkDdwhyO0TKf8v8Crbu4svvzl392hwo6lPgxLNRKw8m14B1sfhifDEbv177RPHrwlivvxc+WcC7yOe6gKJSwLtSoe0dFG+jkAGvHHBOEjL5czJvGhtwcEijzOuuS2jljpO/k9fzh734UNLKl/5HBeWuoXiXkUaLJs1hWjjYyeg8YPVCehY2+ZTMzTJ6ugcsFMdcghXOZMj83vLqFxx8M4oPcEUPKa+eh/f9D9CnjFguWmbgAeVy/8BPYdNdPlLLf6mo4d/Ro6+vWTCpzAfqqSX/5JLjThxuqptyeMOfxOs+++8Sf4TUxdO6XvjJqKDsS0FQ0rh9/eIDUoqrV8vI1HIPXvFxQpTRNp4aMaYeDrt4DONfDrBU2G88RBHRD3818gNpLRb9fC+taQv/szf6/pRrvrr+S/xDKdi/YyR52/kHG1s7b1/42Dsf+Zf+9Y2tX7h//84njIl/n9e54qc7ev8PdrOCmXEcAAA=";
}
