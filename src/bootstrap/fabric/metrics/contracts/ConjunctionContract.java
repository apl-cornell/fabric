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
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            if (tmp.get$$expiry() >=
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$observing(true);
                            }
                        }
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
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved() && tmp.get$observing()) {
                                tmp.set$observing(false);
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
    
    public static final byte[] $classHash = new byte[] { 83, -85, -123, 4, -36,
    -119, 119, 71, 88, -97, -10, 101, 76, 53, -40, -32, -86, 3, -114, -58, -26,
    -89, 22, -93, -117, 124, 123, -57, 104, 111, 8, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613290000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
