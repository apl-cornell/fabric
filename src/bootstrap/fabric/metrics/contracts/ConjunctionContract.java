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
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableContractsVector;
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
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public fabric.worker.metrics.ImmutableContractsVector get$conjuncts();
    
    public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(fabric.worker.metrics.ImmutableContractsVector val);
    
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
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$leafMetrics(val);
        }
        
        public fabric.worker.metrics.ImmutableContractsVector get$conjuncts() {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).get$conjuncts();
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(
          fabric.worker.metrics.ImmutableContractsVector val) {
            return ((fabric.metrics.contracts.ConjunctionContract._Impl)
                      fetch()).set$conjuncts(val);
        }
        
        public fabric.metrics.contracts.ConjunctionContract
          fabric$metrics$contracts$ConjunctionContract$(
          fabric.metrics.contracts.Contract[] arg1) {
            return ((fabric.metrics.contracts.ConjunctionContract) fetch()).
              fabric$metrics$contracts$ConjunctionContract$(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableMetricsVector leafMetrics;
        
        public fabric.worker.metrics.ImmutableContractsVector get$conjuncts() {
            return this.conjuncts;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$conjuncts(
          fabric.worker.metrics.ImmutableContractsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjuncts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableContractsVector conjuncts;
        
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
                    for (int j = 0; j < other.get$conjuncts().length(); j++) {
                        conjunctsBag.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(other.get$conjuncts().get(j)));
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
            int idx = 0;
            for (java.util.Iterator it = conjunctsBag.iterator(); it.hasNext();
                 ) {
                fabric.metrics.contracts.Contract
                  c =
                  (fabric.metrics.contracts.Contract)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(it.next()));
                conjuncts1[idx++] = c;
            }
            this.
              set$conjuncts(
                fabric.worker.metrics.ImmutableContractsVector.createVector(
                                                                 conjuncts1));
            java.util.HashSet leavesBag = new java.util.HashSet();
            for (int i = 0; i < this.get$conjuncts().length(); i++) {
                fabric.worker.metrics.ImmutableMetricsVector leaves =
                  this.get$conjuncts().get(i).getLeafSubjects();
                for (int j = 0; j < leaves.length(); j++) {
                    leavesBag.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              leaves.get(j)));
                }
            }
            fabric.metrics.Metric[] leaves =
              new fabric.metrics.Metric[leavesBag.size()];
            idx = 0;
            for (java.util.Iterator iter = leavesBag.iterator(); iter.hasNext();
                 ) {
                leaves[idx++] =
                  (fabric.metrics.Metric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
            }
            this.set$leafMetrics(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  leaves));
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
            for (int i = 0; i < this.get$conjuncts().length(); i++) {
                if (i != 0) result += " ^ ";
                result += this.get$conjuncts().get(i).toString();
            }
            return result + ")";
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
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
            $writeInline(out, this.leafMetrics);
            $writeInline(out, this.conjuncts);
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
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.conjuncts = (fabric.worker.metrics.ImmutableContractsVector)
                               in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.ConjunctionContract._Impl src =
              (fabric.metrics.contracts.ConjunctionContract._Impl) other;
            this.observing = src.observing;
            this.leafMetrics = src.leafMetrics;
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
    
    public static final byte[] $classHash = new byte[] { -63, 22, -86, 88, 76,
    30, 116, -93, 103, -86, -18, -111, -105, -86, -26, 44, -126, -70, -4, 80,
    -122, -111, -52, 6, 103, -7, 3, 89, 88, 71, -31, -13 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349472000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
