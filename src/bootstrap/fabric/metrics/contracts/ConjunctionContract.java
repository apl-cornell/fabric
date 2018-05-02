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
    
    public static final byte[] $classHash = new byte[] { 55, -115, 23, -66, 24,
    -92, -61, 14, -120, 92, 83, -23, -106, -67, -104, -86, -105, 4, -72, -128,
    119, 106, 15, -125, -88, 98, -111, -13, -24, 13, 33, -59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525209021000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
