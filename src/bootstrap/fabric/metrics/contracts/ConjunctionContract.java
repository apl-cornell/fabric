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
      fabric$metrics$contracts$ConjunctionContract$(fabric.metrics.contracts.Contract[] conjuncts);
    
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
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
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
    
    public static final byte[] $classHash = new byte[] { 31, 70, 37, 73, -83,
    102, -114, -61, -94, -42, 12, -89, 102, -78, -15, -69, 89, 78, -111, -128,
    -85, -5, -91, -126, -125, 92, 109, -120, 105, 33, 118, -29 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526752469000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
