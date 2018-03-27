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
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$observing(true);
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
                return update(curExpiry);
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
                    fabric.worker.transaction.TransactionManager $tm373 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled376 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff374 = 1;
                    boolean $doBackoff375 = true;
                    boolean $retry370 = true;
                    $label368: for (boolean $commit369 = false; !$commit369; ) {
                        if ($backoffEnabled376) {
                            if ($doBackoff375) {
                                if ($backoff374 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff374);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e371) {
                                            
                                        }
                                    }
                                }
                                if ($backoff374 < 5000) $backoff374 *= 2;
                            }
                            $doBackoff375 = $backoff374 <= 32 || !$doBackoff375;
                        }
                        $commit369 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e371) {
                            $commit369 = false;
                            continue $label368;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e371) {
                            $commit369 = false;
                            fabric.common.TransactionID $currentTid372 =
                              $tm373.getCurrentTid();
                            if ($e371.tid.isDescendantOf($currentTid372))
                                continue $label368;
                            if ($currentTid372.parent != null) {
                                $retry370 = false;
                                throw $e371;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e371) {
                            $commit369 = false;
                            if ($tm373.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid372 =
                              $tm373.getCurrentTid();
                            if ($e371.tid.isDescendantOf($currentTid372)) {
                                $retry370 = true;
                            }
                            else if ($currentTid372.parent != null) {
                                $retry370 = false;
                                throw $e371;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e371) {
                            $commit369 = false;
                            if ($tm373.checkForStaleObjects())
                                continue $label368;
                            $retry370 = false;
                            throw new fabric.worker.AbortException($e371);
                        }
                        finally {
                            if ($commit369) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e371) {
                                    $commit369 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e371) {
                                    $commit369 = false;
                                    fabric.common.TransactionID $currentTid372 =
                                      $tm373.getCurrentTid();
                                    if ($currentTid372 != null) {
                                        if ($e371.tid.equals($currentTid372) ||
                                              !$e371.tid.isDescendantOf(
                                                           $currentTid372)) {
                                            throw $e371;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit369 && $retry370) {
                                {  }
                                continue $label368;
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
    
    public static final byte[] $classHash = new byte[] { -54, -82, 116, -99,
    -67, -71, 49, -93, -100, 99, 56, 68, -11, -103, -38, 39, 38, -33, 90, 64,
    40, -27, 111, -118, 49, -113, 62, -83, -9, -77, 34, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521832704000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZfWwUxxWfO3+eMbYxmA9jgzEOLV93glRRglsaOCAYDnCxSVvT4O7tzdkLe7vH7pw50hKljVIof5C2fKeAqKCipQ6RiFCEIrepVBpSItREUdu04kOKUJMQmkYpDWqb0Pdm5u721nsHRomlfTM3n++9+b3fm10P3iBltkVa40pU04Nsa5LawWVKtCPSqVg2jYV1xba7obVXHVXase+dE7EpfuKPkGpVMUxDUxW917AZqYlsVAaUkEFZaN3ajvb1JKDixOWK3c+If/3itEVakqa+tU83mdxk2Pp7Z4f27N9Qd7qE1PaQWs3oYgrT1LBpMJpmPaQ6QRNRatmLYjEa6yFjDEpjXdTSFF17HAaaRg+pt7U+Q2Epi9prqW3qAziw3k4lqcX3zDSi+iaobaVUZlqgfp1QP8U0PRTRbNYeIeVxjeoxezN5gpRGSFlcV/pg4PhIxooQXzG0DNtheJUGalpxRaWZKaWbNCPGyFT3jKzFbSthAEytSFDWb2a3KjUUaCD1QiVdMfpCXczSjD4YWmamYBdGGgsuCoMqk4q6SemjvYxMdI/rFF0wKsDdglMYaXAP4yvBmTW6zsxxWjdWf3nXd4zlhp/4QOcYVXXUvxImTXFNWkvj1KKGSsXE6lmRfcr4oR1+QmBwg2uwGPPidz98eM6Ul8+LMZM9xqyJbqQq61WPR2tebwrPfKgE1ahMmraGUMiznJ9qp+xpTycB7eOzK2JnMNP58trff/PJk/S6n1R1kHLV1FMJQNUY1UwkNZ1aj1CDWgqjsQ4SoEYszPs7SAXUI5pBReuaeNymrIOU6ryp3OS/wUVxWAJdVAF1zYibmXpSYf28nk4SQirgIT54zhMysQnKZkJKXmBkfajfTNBQVE/RLQDvEDxUsdT+EMStpakh21JDVspgGgySTYAiKOwQQJ1ZisoAJaaxMWWoaGpYNgZBreTnu3waravb4vOB46eqZoxGFRtOUSJqcacOQbPc1GPU6lX1XUMdZOzQQY6qAEaCDWjmfvMBEprcHOKcuye1eOmHp3ovCETiXOlWRuYInYNS52BW56CHzqBmNcZeENgsCGw26EsHw0c6fsUhVm7zWMyuXA0rL0jqCoubViJNfD5u5jg+n2MLkLEJGAdIpXpm12Mrvr2jtQRAndxSiucMQ9vcIZYjpg6oKRA3vWrt9nf+/fy+bWYu2BhpG8YBw2diDLe6fWaZKo0BR+aWn9WinOkd2tbmR/4JoHMUAC/wzBT3Hnmx3J7hRfRGWYSMQh8oOnZlyKyK9VvmllwLx0INinoBC3SWS0FOqV/pSh7+y8V37+fJJsO+tQ6a7qKs3RHxuFgtj+0xOd93W5TCuEsHOnfvvbF9PXc8jJjutWEbyjBEugIhblpPn9/81pXLx9/05w6LkfJkKqpraprbMuY2/Png+RQfDFtswBLIOywpoyXLGUnceUZON2APnXLc2W3rjIQZ0+KaEtUpIuV/tffNO/P+rjpx3Dq0COdZZM6dF8i1T1pMnryw4eMpfBmfitkr57/cMEGJY3MrL7IsZSvqkf7eG80HX1EOA/KB0Gztcco5inB/EH6A87kv5nI5z9X3JRStwltNvN1vD08PyzDP5rDYExo81BheeF0wQBaLuMY0DwZ4VHGEyfyTiZv+1vJzflLRQ+p4ilcM9qgCrAYw6IEkbYdlY4SMzuvPT7giu7RnY63JHQeObd1RkGMeqONorFcJ4AvggCOq0ElI71OB3j+R5XXsHZtEOS7tI7yygE+ZzuUMFDMzYKxIWtoAICudXdSPiwbkYu/J8m3HoowEzKhNrQFgMI+T6LS0BATTgEzUdMeenbeDu/YIFIrbzPRhFwrnHHGj4aaORjE7DbtMK7YLn7Hs789ve+kX27aLbF+fn5uXGqnEc3/65LXggauvejB7RdQ0daoYgkxQPpD1Rx364z54WggpnS7LGg8nL/d2sh+rC8FpWiKRYhhb3LTZjOOSpw0bDGx2XZ0hw/AIEl68eOLWpKG2d28J49wXKsfAfw5euf7G6OZTnIRLMUdy4LhvosMvmnn3R65hddYFteiCCeLx/1eWHzGifBb5nsItxlJpghos9HWNGdS2O03gx63yUvH5byKw38DItGIJntdwYGOWpnwy/3LAoPgaYsH1EyvfKAKNWQwiXzMUPQOLcp0afayfD14kAwCLJYyUwLFhtbNAPPP1xDoovoXiMT4hnVXaLzlU2iwYG/kK7uWmQRGgvG8SABSvJboJr2dZF4k7iWYGsy9NUXEhpelhbrG4Q/PfB1dxlOXI9ur15ofCm671CWBPdQHbPfqXqwZffWSG+hM/Kcmy6rBXo/xJ7flcWmVReLMzuvMYtUUc1l16tkiu2lykjzcm4LBVdHPGn3U594t0IXzJiUh4dDoKMxuL/K9cXulPy3LQQUeOREmQOZsLvX1x1jz+/T1HYmt+Ps8vtVwKp87M5FydDlDdsVSA1zdm1cAEQfrgmUNI2euytJ2smONSlwU8wVTKKZYsdbcFcz3gNNkJpxXgOAfxbYAb2x+3frAvY8ia7H7oLFIDK/yGkAdHybKEkZX3TixLAPwDNLaK/5Q89Vkuxy3oLswzP8gQy85ixIJivRcbPD2MDfCniqJveBjzgxe78skojCJA/3GRvt0onkHBgZ5K89aniszYi+IJRuYKn7VJn7VlubnN4+WrLQc+F2QnwrMCbk6dslw0MsjilIdluaAwZJ0GHCrSdwTFfkYqQWd+DfMi/dIBU4u5bOFMsBCe1aDIOVkeKGBLoRsJvIXitzHX1a9OrrZflj+8Y2QKskJ5soitnKOOweuX2LU3YzI2H3WZV42z7ofHhsi9IMvjd2ke3GoDSctkQKY05jJulFzrmCyfvSvjwny7M0WMexHFKbhIWjQOgc8/xYS98Ie42wnnF5Xl5JHhD6c0ynJcYd1LuGolmSTT4LrXCKLBzkYvwJXHzJTM6r/lOv26iOXnUJwFy7VEUtcEgD0tR7V/BGqfluXPRmY5TjkqyyKn5lTttSJ9F1G8ApHHTPFd1CMhOzomub/ReFk4C56fElL/kiyfHZmFOOWgLHffnYVvFen7G4o3Id76KItQJd6V4vcLmw9enf96A3cI8gAhFV+V5Xzs3c6335G9+jW5UNSlwIlnslYOTJ4J63L+xfhyJn+9fU/569JIr7+X7i3hXeZrXUJRLOG9X6TvHyjeQyETXiHgnCFkfIsoG26PDDg45VNZ3rojoRU6Tv6evIa/2IsPJY18638VMY5v9gEjNRZNmAPUOdnN6DxhrYXnLNh5VJa9Bez0TlgoTngkK1xpgyw7C5vvOPg6FDdxRx8pbJ6Pj/0P0KfMWB5WpuEFyuP+gZ/CJnt8pJb/UlHDv6PHr62c01DgA/XEYf/kkvNOHamtnHBk3Z/F233m3yWBCKmMp3Td+cnIUS9PQlLSuH8D4gNSkptXwcjEQi+84uOEqKNvfGViThUcdv4cxr8cYM05bjRkETEOf9XwA2nMF91cl8aUhf/ZG/xowq3yyu6r/EMp+L/lD6fY4aGz844dUh9ccvPgX78w40rPw1+8Zu6c98zC5z5+oXX7/wErqpoEcRwAAA==";
}
