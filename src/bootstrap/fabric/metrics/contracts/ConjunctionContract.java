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
import fabric.worker.metrics.ImmutableSet;
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
            this.
              set$$associated(
                fabric.worker.metrics.ImmutableSet.emptySet().
                    add(this.get$currentPolicy()));
            return (fabric.metrics.contracts.ConjunctionContract)
                     this.$getProxy();
        }
        
        public void activate() { refresh(false); }
        
        private static void static_activate(
          fabric.metrics.contracts.ConjunctionContract tmp) {
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
            long currentTime = java.lang.System.currentTimeMillis();
            if (this.get$observing()) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry < currentTime) {
                    this.get$currentPolicy().
                      unapply((fabric.metrics.contracts.ConjunctionContract)
                                this.$getProxy());
                    this.set$currentPolicy(null);
                    this.set$$associated(null);
                    this.set$observing(false);
                }
                else {
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.ConjunctionContract)
                              this.$getProxy());
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.ConjunctionContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -127, -67, 41, -117,
    26, -110, -41, 71, 124, 115, -60, -117, 47, 90, -67, -116, -119, -97, -79,
    20, -98, -117, -55, -120, -89, -116, -48, 75, 65, 124, -86, -121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
