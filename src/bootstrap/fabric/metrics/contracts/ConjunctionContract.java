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
                    fabric.worker.transaction.TransactionManager $tm5 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled8 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff6 = 1;
                    boolean $doBackoff7 = true;
                    boolean $retry2 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($backoffEnabled8) {
                            if ($doBackoff7) {
                                if ($backoff6 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff6);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e3) {
                                            
                                        }
                                    }
                                }
                                if ($backoff6 < 5000) $backoff6 *= 2;
                            }
                            $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                        }
                        $commit1 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e3) {
                            $commit1 = false;
                            continue $label0;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e3) {
                            $commit1 = false;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4))
                                continue $label0;
                            if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4)) {
                                $retry2 = true;
                            }
                            else if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue $label0;
                            $retry2 = false;
                            throw new fabric.worker.AbortException($e3);
                        }
                        finally {
                            if ($commit1) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e3) {
                                    $commit1 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e3) {
                                    $commit1 = false;
                                    fabric.common.TransactionID $currentTid4 =
                                      $tm5.getCurrentTid();
                                    if ($currentTid4 != null) {
                                        if ($e3.tid.equals($currentTid4) ||
                                              !$e3.tid.isDescendantOf(
                                                         $currentTid4)) {
                                            throw $e3;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit1 && $retry2) {
                                {  }
                                continue $label0;
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
                    fabric.worker.transaction.TransactionManager $tm14 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled17 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff15 = 1;
                    boolean $doBackoff16 = true;
                    boolean $retry11 = true;
                    $label9: for (boolean $commit10 = false; !$commit10; ) {
                        if ($backoffEnabled17) {
                            if ($doBackoff16) {
                                if ($backoff15 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff15);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e12) {
                                            
                                        }
                                    }
                                }
                                if ($backoff15 < 5000) $backoff15 *= 2;
                            }
                            $doBackoff16 = $backoff15 <= 32 || !$doBackoff16;
                        }
                        $commit10 = true;
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
                        catch (final fabric.worker.RetryException $e12) {
                            $commit10 = false;
                            continue $label9;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e12) {
                            $commit10 = false;
                            fabric.common.TransactionID $currentTid13 =
                              $tm14.getCurrentTid();
                            if ($e12.tid.isDescendantOf($currentTid13))
                                continue $label9;
                            if ($currentTid13.parent != null) {
                                $retry11 = false;
                                throw $e12;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e12) {
                            $commit10 = false;
                            if ($tm14.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid13 =
                              $tm14.getCurrentTid();
                            if ($e12.tid.isDescendantOf($currentTid13)) {
                                $retry11 = true;
                            }
                            else if ($currentTid13.parent != null) {
                                $retry11 = false;
                                throw $e12;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e12) {
                            $commit10 = false;
                            if ($tm14.checkForStaleObjects()) continue $label9;
                            $retry11 = false;
                            throw new fabric.worker.AbortException($e12);
                        }
                        finally {
                            if ($commit10) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e12) {
                                    $commit10 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e12) {
                                    $commit10 = false;
                                    fabric.common.TransactionID $currentTid13 =
                                      $tm14.getCurrentTid();
                                    if ($currentTid13 != null) {
                                        if ($e12.tid.equals($currentTid13) ||
                                              !$e12.tid.isDescendantOf(
                                                          $currentTid13)) {
                                            throw $e12;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit10 && $retry11) {
                                {  }
                                continue $label9;
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
                    fabric.worker.transaction.TransactionManager $tm23 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled26 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff24 = 1;
                    boolean $doBackoff25 = true;
                    boolean $retry20 = true;
                    $label18: for (boolean $commit19 = false; !$commit19; ) {
                        if ($backoffEnabled26) {
                            if ($doBackoff25) {
                                if ($backoff24 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff24);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e21) {
                                            
                                        }
                                    }
                                }
                                if ($backoff24 < 5000) $backoff24 *= 2;
                            }
                            $doBackoff25 = $backoff24 <= 32 || !$doBackoff25;
                        }
                        $commit19 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e21) {
                            $commit19 = false;
                            continue $label18;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e21) {
                            $commit19 = false;
                            fabric.common.TransactionID $currentTid22 =
                              $tm23.getCurrentTid();
                            if ($e21.tid.isDescendantOf($currentTid22))
                                continue $label18;
                            if ($currentTid22.parent != null) {
                                $retry20 = false;
                                throw $e21;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e21) {
                            $commit19 = false;
                            if ($tm23.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid22 =
                              $tm23.getCurrentTid();
                            if ($e21.tid.isDescendantOf($currentTid22)) {
                                $retry20 = true;
                            }
                            else if ($currentTid22.parent != null) {
                                $retry20 = false;
                                throw $e21;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e21) {
                            $commit19 = false;
                            if ($tm23.checkForStaleObjects()) continue $label18;
                            $retry20 = false;
                            throw new fabric.worker.AbortException($e21);
                        }
                        finally {
                            if ($commit19) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e21) {
                                    $commit19 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e21) {
                                    $commit19 = false;
                                    fabric.common.TransactionID $currentTid22 =
                                      $tm23.getCurrentTid();
                                    if ($currentTid22 != null) {
                                        if ($e21.tid.equals($currentTid22) ||
                                              !$e21.tid.isDescendantOf(
                                                          $currentTid22)) {
                                            throw $e21;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit19 && $retry20) {
                                {  }
                                continue $label18;
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
      "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";
}
