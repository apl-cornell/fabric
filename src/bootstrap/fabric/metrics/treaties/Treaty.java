package fabric.metrics.treaties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Iterator;
import java.util.SortedSet;
import fabric.metrics.Metric;
import fabric.metrics.DerivedMetric;
import fabric.metrics.SampledMetric;
import fabric.metrics.SumMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.NoPolicy;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Worker;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * Treaties allow for time-limited assertions of predicates over metrics and
 * time.
 */
public interface Treaty
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.worker.metrics.treaties.statements.TreatyStatement
      get$predicate();
    
    public fabric.worker.metrics.treaties.statements.TreatyStatement
      set$predicate(
      fabric.worker.metrics.treaties.statements.TreatyStatement val);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      get$policy();
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      set$policy(fabric.worker.metrics.treaties.enforcement.EnforcementPolicy val);
    
    /**
   * Create a new treaty with a given metric, predicate, and policy.
   *
   * Policy is assumed preactivated.
   */
    public fabric.metrics.treaties.Treaty fabric$metrics$treaties$Treaty$(
      fabric.metrics.Metric metric,
      fabric.worker.metrics.treaties.statements.TreatyStatement predicate,
      fabric.worker.metrics.treaties.enforcement.EnforcementPolicy policy, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Checks if the treaty is currently valid.
   *
   * Ensures that the transaction manager considers the expiry seen when
   * checking the commit time.
   */
    public boolean valid();
    
    /**
   * Checks if the treaty is currently invalid.
   *
   * Unlike valid, this doesn't register the check with the transaction manager,
   * so if the treaty is valid, it's not required to still be valid by commit
   * time.  This is primarily to allow for interactions with a treaty to kick
   * out early if they're already invalid.
   */
    public boolean invalid();
    
    /**
   * Perform a "deactivation," a forced retraction to 0 and no continued
   * enforcement.  Garbage collects observer relationships with old policy and
   * removes this treaty from the metric's set of available treaties.
   */
    public void deactivate();
    
    /**
   * Run an update using the current expiry.  Factored out here since it's used
   * by both handleUpdates() and backgroundExtension().
   * @return true iff the current policy is still viable.
   */
    public boolean updateWithCurPolicy(boolean force);
    
    /**
   * Register potentially enabled extensions for observers given the expiry
   * relative to the original (given) expiry.  Factored out here since it's used
   * by both handleUpdates() and backgroundExtension().
   */
    public void registerPotentialExtensions(long oldExpiry);
    
    /**
   * Handles an update to some dependency of the current policy for this treaty.
   *
   * @return the observers of this treaty if there was a retraction.
  /*@Override*/
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
    /**
   * Run a background extension.  This should only result in extending the
   * expiry using the existing policy or waiting further to extend.
   */
    public void backgroundExtension();
    
    /**
   * Force a shift to a new policy.
   */
    public void rebalance(boolean force);
    
    /**
   * Get a proxy for this treaty stored on the given store.
   */
    public fabric.metrics.treaties.Treaty getProxy(fabric.worker.Store s);
    
    /**
   * Remove an observer.  If this is the last remaining observer, go ahead and
   * retract it to avoid unnecessary monitoring overhead.
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public java.lang.String toString();
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.treaties.Treaty {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).get$metric(
                                                                      );
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).set$metric(
                                                                      val);
        }
        
        public fabric.worker.metrics.treaties.statements.TreatyStatement
          get$predicate() {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).
              get$predicate();
        }
        
        public fabric.worker.metrics.treaties.statements.TreatyStatement
          set$predicate(
          fabric.worker.metrics.treaties.statements.TreatyStatement val) {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).
              set$predicate(val);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          get$policy() {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).get$policy(
                                                                      );
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          set$policy(
          fabric.worker.metrics.treaties.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.treaties.Treaty._Impl) fetch()).set$policy(
                                                                      val);
        }
        
        public fabric.metrics.treaties.Treaty fabric$metrics$treaties$Treaty$(
          fabric.metrics.Metric arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.treaties.enforcement.EnforcementPolicy arg3,
          fabric.worker.metrics.StatsMap arg4) {
            return ((fabric.metrics.treaties.Treaty) fetch()).
              fabric$metrics$treaties$Treaty$(arg1, arg2, arg3, arg4);
        }
        
        public static fabric.metrics.treaties.Treaty newTreaty(
          fabric.metrics.Metric arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.treaties.Treaty._Impl.newTreaty(arg1, arg2,
                                                                  arg3);
        }
        
        public boolean valid() {
            return ((fabric.metrics.treaties.Treaty) fetch()).valid();
        }
        
        public boolean invalid() {
            return ((fabric.metrics.treaties.Treaty) fetch()).invalid();
        }
        
        public void deactivate() {
            ((fabric.metrics.treaties.Treaty) fetch()).deactivate();
        }
        
        public static fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          newActivatedPolicy(
          fabric.metrics.Metric arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.treaties.Treaty._Impl.newActivatedPolicy(
                                                          arg1, arg2, arg3);
        }
        
        public static fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          newUnactivatedPolicy(
          fabric.metrics.Metric arg1,
          fabric.worker.metrics.treaties.statements.TreatyStatement arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.treaties.Treaty._Impl.newUnactivatedPolicy(
                                                          arg1, arg2, arg3);
        }
        
        public boolean updateWithCurPolicy(boolean arg1) {
            return ((fabric.metrics.treaties.Treaty) fetch()).
              updateWithCurPolicy(arg1);
        }
        
        public void registerPotentialExtensions(long arg1) {
            ((fabric.metrics.treaties.Treaty) fetch()).
              registerPotentialExtensions(arg1);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            return ((fabric.metrics.treaties.Treaty) fetch()).handleUpdates();
        }
        
        public void backgroundExtension() {
            ((fabric.metrics.treaties.Treaty) fetch()).backgroundExtension();
        }
        
        public void rebalance(boolean arg1) {
            ((fabric.metrics.treaties.Treaty) fetch()).rebalance(arg1);
        }
        
        public static void rebalance_async(
          fabric.metrics.treaties.Treaty arg1) {
            fabric.metrics.treaties.Treaty._Impl.rebalance_async(arg1);
        }
        
        public fabric.metrics.treaties.Treaty getProxy(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.treaties.Treaty) fetch()).getProxy(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.treaties.Treaty) fetch()).getLeafSubjects();
        }
        
        public _Proxy(Treaty._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.treaties.Treaty {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric metric;
        
        public fabric.worker.metrics.treaties.statements.TreatyStatement
          get$predicate() {
            return this.predicate;
        }
        
        public fabric.worker.metrics.treaties.statements.TreatyStatement
          set$predicate(
          fabric.worker.metrics.treaties.statements.TreatyStatement val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.predicate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.treaties.statements.TreatyStatement
          predicate;
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          get$policy() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.policy;
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          set$policy(
          fabric.worker.metrics.treaties.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.policy = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.worker.metrics.treaties.enforcement.EnforcementPolicy policy;
        
        /**
   * Create a new treaty with a given metric, predicate, and policy.
   *
   * Policy is assumed preactivated.
   */
        public fabric.metrics.treaties.Treaty fabric$metrics$treaties$Treaty$(
          fabric.metrics.Metric metric,
          fabric.worker.metrics.treaties.statements.TreatyStatement predicate,
          fabric.worker.metrics.treaties.enforcement.EnforcementPolicy policy,
          fabric.worker.metrics.StatsMap weakStats) {
            this.set$metric(metric);
            this.set$predicate(predicate);
            fabric$metrics$util$AbstractSubject$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet(
                                                              ).add(metric));
            this.
              set$policy(
                fabric.worker.metrics.treaties.enforcement.NoPolicy.singleton);
            this.set$$expiry((long) 0);
            long policyExpiry =
              policy.calculateExpiry((fabric.metrics.treaties.Treaty)
                                       this.$getProxy(), weakStats);
            if (policyExpiry > java.lang.System.currentTimeMillis()) {
                this.get$policy().shiftPolicies((fabric.metrics.treaties.Treaty)
                                                  this.$getProxy(), policy);
                this.set$policy(policy);
                this.set$$expiry((long) policyExpiry);
            } else {
                policy.unapply((fabric.metrics.treaties.Treaty)
                                 this.$getProxy());
                deactivate();
            }
            return (fabric.metrics.treaties.Treaty) this.$getProxy();
        }
        
        /**
   * Perform a not-necessarily-atomic creation of an active treaty.
   */
        public static fabric.metrics.treaties.Treaty newTreaty(
          fabric.metrics.Metric metric,
          fabric.worker.metrics.treaties.statements.TreatyStatement predicate,
          fabric.worker.metrics.StatsMap weakStats) {
            final fabric.worker.Store s = metric.$getStore();
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              policy =
              fabric.metrics.treaties.Treaty._Impl.newActivatedPolicy(
                                                     metric, predicate,
                                                     weakStats);
            fabric.metrics.treaties.Treaty treaty = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                treaty =
                  ((fabric.metrics.treaties.Treaty)
                     new fabric.metrics.treaties.Treaty._Impl(s).$getProxy()).
                    fabric$metrics$treaties$Treaty$(metric, predicate, policy,
                                                    weakStats);
            }
            else {
                {
                    fabric.metrics.treaties.Treaty treaty$var0 = treaty;
                    fabric.worker.transaction.TransactionManager $tm7 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled10 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff8 = 1;
                    boolean $doBackoff9 = true;
                    boolean $retry3 = true;
                    boolean $keepReads4 = false;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled10) {
                            if ($doBackoff9) {
                                if ($backoff8 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff8));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e5) {
                                            
                                        }
                                    }
                                }
                                if ($backoff8 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff8 =
                                      java.lang.Math.
                                        min(
                                          $backoff8 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff9 = $backoff8 <= 32 || !$doBackoff9;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            treaty =
                              ((fabric.metrics.treaties.Treaty)
                                 new fabric.metrics.treaties.Treaty._Impl(s).
                                 $getProxy()).fabric$metrics$treaties$Treaty$(
                                                metric, predicate, policy,
                                                weakStats);
                        }
                        catch (final fabric.worker.RetryException $e5) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (fabric.worker.TransactionAbortingException $e5) {
                            $commit2 = false;
                            $retry3 = false;
                            $keepReads4 = $e5.keepReads;
                            fabric.common.TransactionID $currentTid6 =
                              $tm7.getCurrentTid();
                            if ($e5.tid == null ||
                                  !$e5.tid.isDescendantOf($currentTid6)) {
                                throw $e5;
                            }
                            throw new fabric.worker.UserAbortException($e5);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e5) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid6 =
                              $tm7.getCurrentTid();
                            if ($e5.tid.isDescendantOf($currentTid6))
                                continue $label1;
                            if ($currentTid6.parent != null) {
                                $retry3 = false;
                                throw $e5;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e5) {
                            $commit2 = false;
                            $retry3 = false;
                            if ($tm7.inNestedTxn()) { $keepReads4 = true; }
                            throw $e5;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid6 =
                              $tm7.getCurrentTid();
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e5) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e5) {
                                    $commit2 = false;
                                    $retry3 = false;
                                    $keepReads4 = $e5.keepReads;
                                    if ($e5.tid == null ||
                                          !$e5.tid.isDescendantOf($currentTid6))
                                        throw $e5;
                                    throw new fabric.worker.UserAbortException(
                                            $e5);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e5) {
                                    $commit2 = false;
                                    $currentTid6 = $tm7.getCurrentTid();
                                    if ($currentTid6 != null) {
                                        if ($e5.tid.equals($currentTid6) ||
                                              !$e5.tid.isDescendantOf(
                                                         $currentTid6)) {
                                            throw $e5;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm7.inNestedTxn() &&
                                      $tm7.checkForStaleObjects()) {
                                    $retry3 = true;
                                    $keepReads4 = false;
                                }
                                if ($keepReads4) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e5) {
                                        $currentTid6 = $tm7.getCurrentTid();
                                        if ($currentTid6 !=
                                              null &&
                                              ($e5.tid.equals($currentTid6) ||
                                                 !$e5.tid.isDescendantOf($currentTid6))) {
                                            throw $e5;
                                        } else {
                                            $retry3 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit2) {
                                { treaty = treaty$var0; }
                                if ($retry3) { continue $label1; }
                            }
                        }
                    }
                }
            }
            return treaty;
        }
        
        /**
   * Checks if the treaty is currently valid.
   *
   * Ensures that the transaction manager considers the expiry seen when
   * checking the commit time.
   */
        public boolean valid() {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.resolveObservations();
            boolean result = this.get$$expiry() >=
              java.lang.System.currentTimeMillis();
            if (result) tm.registerExpiryUse(this.get$$expiry()); else
                this.$getExpiryStrict();
            return result;
        }
        
        /**
   * Checks if the treaty is currently invalid.
   *
   * Unlike valid, this doesn't register the check with the transaction manager,
   * so if the treaty is valid, it's not required to still be valid by commit
   * time.  This is primarily to allow for interactions with a treaty to kick
   * out early if they're already invalid.
   */
        public boolean invalid() {
            boolean result = this.get$$expiry() <
              java.lang.System.currentTimeMillis();
            if (result) this.$getExpiryStrict();
            return result;
        }
        
        /**
   * Perform a "deactivation," a forced retraction to 0 and no continued
   * enforcement.  Garbage collects observer relationships with old policy and
   * removes this treaty from the metric's set of available treaties.
   */
        public void deactivate() {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                this.
                  get$policy().
                  shiftPolicies(
                    (fabric.metrics.treaties.Treaty)
                      this.
                      $getProxy(),
                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                      singleton);
                this.
                  set$policy(
                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                      singleton);
                this.set$$expiry((long) 0);
                this.get$metric().get$treatiesBox().
                  remove((fabric.metrics.treaties.Treaty) this.$getProxy());
            }
            else {
                this.
                  get$policy().
                  shiftPolicies(
                    (fabric.metrics.treaties.Treaty)
                      this.
                      $getProxy(),
                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                      singleton);
                {
                    fabric.worker.transaction.TransactionManager $tm17 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled20 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff18 = 1;
                    boolean $doBackoff19 = true;
                    boolean $retry13 = true;
                    boolean $keepReads14 = false;
                    $label11: for (boolean $commit12 = false; !$commit12; ) {
                        if ($backoffEnabled20) {
                            if ($doBackoff19) {
                                if ($backoff18 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff18));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e15) {
                                            
                                        }
                                    }
                                }
                                if ($backoff18 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff18 =
                                      java.lang.Math.
                                        min(
                                          $backoff18 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff19 = $backoff18 <= 32 || !$doBackoff19;
                        }
                        $commit12 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            this.
                              set$policy(
                                fabric.worker.metrics.treaties.enforcement.NoPolicy.
                                  singleton);
                            this.set$$expiry((long) 0);
                            this.get$metric().get$treatiesBox().
                              remove((fabric.metrics.treaties.Treaty)
                                       this.$getProxy());
                        }
                        catch (final fabric.worker.RetryException $e15) {
                            $commit12 = false;
                            continue $label11;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e15) {
                            $commit12 = false;
                            $retry13 = false;
                            $keepReads14 = $e15.keepReads;
                            fabric.common.TransactionID $currentTid16 =
                              $tm17.getCurrentTid();
                            if ($e15.tid == null ||
                                  !$e15.tid.isDescendantOf($currentTid16)) {
                                throw $e15;
                            }
                            throw new fabric.worker.UserAbortException($e15);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e15) {
                            $commit12 = false;
                            fabric.common.TransactionID $currentTid16 =
                              $tm17.getCurrentTid();
                            if ($e15.tid.isDescendantOf($currentTid16))
                                continue $label11;
                            if ($currentTid16.parent != null) {
                                $retry13 = false;
                                throw $e15;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e15) {
                            $commit12 = false;
                            $retry13 = false;
                            if ($tm17.inNestedTxn()) { $keepReads14 = true; }
                            throw $e15;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid16 =
                              $tm17.getCurrentTid();
                            if ($commit12) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e15) {
                                    $commit12 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e15) {
                                    $commit12 = false;
                                    $retry13 = false;
                                    $keepReads14 = $e15.keepReads;
                                    if ($e15.tid ==
                                          null ||
                                          !$e15.tid.isDescendantOf(
                                                      $currentTid16))
                                        throw $e15;
                                    throw new fabric.worker.UserAbortException(
                                            $e15);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e15) {
                                    $commit12 = false;
                                    $currentTid16 = $tm17.getCurrentTid();
                                    if ($currentTid16 != null) {
                                        if ($e15.tid.equals($currentTid16) ||
                                              !$e15.tid.isDescendantOf(
                                                          $currentTid16)) {
                                            throw $e15;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm17.inNestedTxn() &&
                                      $tm17.checkForStaleObjects()) {
                                    $retry13 = true;
                                    $keepReads14 = false;
                                }
                                if ($keepReads14) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e15) {
                                        $currentTid16 = $tm17.getCurrentTid();
                                        if ($currentTid16 !=
                                              null &&
                                              ($e15.tid.equals($currentTid16) ||
                                                 !$e15.tid.isDescendantOf($currentTid16))) {
                                            throw $e15;
                                        } else {
                                            $retry13 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit12) {
                                {  }
                                if ($retry13) { continue $label11; }
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Create and activate a new enforcement policy.
   */
        public static fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          newActivatedPolicy(
          fabric.metrics.Metric metric,
          fabric.worker.metrics.treaties.statements.TreatyStatement predicate,
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy = predicate.getNewPolicy(metric, weakStats);
            newPolicy.activate(weakStats);
            return newPolicy;
        }
        
        /**
   * Create and activate a new enforcement policy.
   */
        public static fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          newUnactivatedPolicy(
          fabric.metrics.Metric metric,
          fabric.worker.metrics.treaties.statements.TreatyStatement predicate,
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy = predicate.getNewPolicy(metric, weakStats);
            return newPolicy;
        }
        
        /**
   * Run an update using the current expiry.  Factored out here since it's used
   * by both handleUpdates() and backgroundExtension().
   * @return true iff the current policy is still viable.
   */
        public boolean updateWithCurPolicy(boolean force) {
            long updatedCurExpiry =
              this.get$policy().updatedExpiry(
                                  (fabric.metrics.treaties.Treaty)
                                    this.$getProxy(),
                                  fabric.worker.metrics.StatsMap.emptyStats());
            if (updatedCurExpiry > java.lang.System.currentTimeMillis()) {
                if (updatedCurExpiry <
                      this.get$$expiry() ||
                      (force ||
                         this.get$$expiry() -
                         java.lang.System.currentTimeMillis() <=
                         fabric.metrics.treaties.Treaty._Static._Proxy.$instance.get$UPDATE_THRESHOLD(
                                                                                   ))) {
                    if (this.get$$expiry() != updatedCurExpiry)
                        this.set$$expiry((long) updatedCurExpiry);
                }
                else if (updatedCurExpiry > this.get$$expiry()) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerDelayedExtension((fabric.metrics.treaties.Treaty)
                                                 this.$getProxy());
                }
                return true;
            }
            return false;
        }
        
        /**
   * Register potentially enabled extensions for observers given the expiry
   * relative to the original (given) expiry.  Factored out here since it's used
   * by both handleUpdates() and backgroundExtension().
   */
        public void registerPotentialExtensions(long oldExpiry) {
            if (this.get$$expiry() > oldExpiry) {
                for (java.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    java.lang.Object o = iter.next();
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            o) instanceof fabric.metrics.treaties.Treaty)
                        fabric.worker.transaction.TransactionManager.
                          getInstance().
                          registerDelayedExtension(
                            (fabric.metrics.treaties.Treaty)
                              fabric.lang.Object._Proxy.
                              $getProxy(
                                fabric.lang.WrappedJavaInlineable.$wrap(o)),
                            (fabric.metrics.treaties.Treaty) this.$getProxy());
                }
            }
        }
        
        /**
   * Handles an update to some dependency of the current policy for this treaty.
   *
   * @return the observers of this treaty if there was a retraction.
  /*@Override*/
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            long oldExpiry = this.get$$expiry();
            if (!updateWithCurPolicy(false)) {
                fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
                  newPolicy =
                  fabric.metrics.treaties.Treaty._Impl.
                  newUnactivatedPolicy(
                    this.get$metric(), this.get$predicate(),
                    fabric.worker.metrics.StatsMap.emptyStats());
                if (newPolicy.equals(this.get$policy())) {
                    deactivate();
                }
                else {
                    newPolicy.activate(
                                fabric.worker.metrics.StatsMap.emptyStats());
                    long newPolicyExpiry =
                      newPolicy.calculateExpiry(
                                  (fabric.metrics.treaties.Treaty)
                                    this.$getProxy(),
                                  fabric.worker.metrics.StatsMap.emptyStats());
                    if (newPolicyExpiry >
                          java.lang.System.currentTimeMillis()) {
                        this.get$policy().shiftPolicies(
                                            (fabric.metrics.treaties.Treaty)
                                              this.$getProxy(), newPolicy);
                        this.set$policy(newPolicy);
                        this.set$$expiry((long) newPolicyExpiry);
                    } else {
                        newPolicy.unapply((fabric.metrics.treaties.Treaty)
                                            this.$getProxy());
                        deactivate();
                    }
                }
            }
            else if (fabric.worker.transaction.TransactionManager.getInstance().
                       shouldRebalance((fabric.metrics.treaties.Treaty)
                                         this.$getProxy())) {
                rebalance(false);
            }
            if (this.get$$expiry() < oldExpiry) { return getObservers(); }
            registerPotentialExtensions(oldExpiry);
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
        }
        
        /**
   * Run a background extension.  This should only result in extending the
   * expiry using the existing policy or waiting further to extend.
   */
        public void backgroundExtension() {
            long oldExpiry = this.get$$expiry();
            if (oldExpiry != 0) {
                boolean policyLive = updateWithCurPolicy(true);
                if (this.get$$expiry() < oldExpiry) {
                    throw new fabric.worker.RetryException();
                }
                else {
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().shouldRebalance(
                                          (fabric.metrics.treaties.Treaty)
                                            this.$getProxy())) {
                        rebalance(true);
                    }
                    registerPotentialExtensions(oldExpiry);
                }
            }
        }
        
        /**
   * Force a shift to a new policy.
   */
        public void rebalance(boolean force) {
            long oldExpiry = this.get$$expiry();
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy =
              fabric.metrics.treaties.Treaty._Impl.
              newUnactivatedPolicy(this.get$metric(), this.get$predicate(),
                                   fabric.worker.metrics.StatsMap.emptyStats());
            if (newPolicy.equals(this.get$policy())) return;
            newPolicy.activate(fabric.worker.metrics.StatsMap.emptyStats());
            long newPolicyExpiry =
              newPolicy.calculateExpiry(
                          (fabric.metrics.treaties.Treaty) this.$getProxy(),
                          fabric.worker.metrics.StatsMap.emptyStats());
            if (newPolicyExpiry > oldExpiry) {
                if (force ||
                      oldExpiry -
                      java.lang.System.currentTimeMillis() <=
                      fabric.metrics.treaties.Treaty._Static._Proxy.$instance.
                      get$UPDATE_THRESHOLD()) {
                    this.get$policy().shiftPolicies(
                                        (fabric.metrics.treaties.Treaty)
                                          this.$getProxy(), newPolicy);
                    this.set$policy(newPolicy);
                    this.set$$expiry((long) newPolicyExpiry);
                    registerPotentialExtensions(oldExpiry);
                }
                else {
                    newPolicy.abandonPolicy((fabric.metrics.treaties.Treaty)
                                              this.$getProxy(),
                                            this.get$policy());
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerDelayedExtension((fabric.metrics.treaties.Treaty)
                                                 this.$getProxy());
                }
            } else if (!this.get$policy().equals(newPolicy)) {
                newPolicy.abandonPolicy((fabric.metrics.treaties.Treaty)
                                          this.$getProxy(), this.get$policy());
            }
        }
        
        /**
   * Force a shift to a new policy.
   */
        public static void rebalance_async(fabric.metrics.treaties.Treaty tmp) {
            {
                fabric.worker.transaction.TransactionManager $tm27 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled30 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff28 = 1;
                boolean $doBackoff29 = true;
                boolean $retry23 = true;
                boolean $keepReads24 = false;
                $label21: for (boolean $commit22 = false; !$commit22; ) {
                    if ($backoffEnabled30) {
                        if ($doBackoff29) {
                            if ($backoff28 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff28));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e25) {
                                        
                                    }
                                }
                            }
                            if ($backoff28 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff28 =
                                  java.lang.Math.
                                    min(
                                      $backoff28 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff29 = $backoff28 <= 32 || !$doBackoff29;
                    }
                    $commit22 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!(fabric.lang.Object._Proxy.
                                $getProxy(
                                  (java.lang.Object)
                                    fabric.lang.WrappedJavaInlineable.
                                    $unwrap(
                                      tmp.get$metric(
                                            ))) instanceof fabric.metrics.SumMetric))
                            return;
                    }
                    catch (final fabric.worker.RetryException $e25) {
                        $commit22 = false;
                        continue $label21;
                    }
                    catch (fabric.worker.TransactionAbortingException $e25) {
                        $commit22 = false;
                        $retry23 = false;
                        $keepReads24 = $e25.keepReads;
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($e25.tid == null ||
                              !$e25.tid.isDescendantOf($currentTid26)) {
                            throw $e25;
                        }
                        throw new fabric.worker.UserAbortException($e25);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e25) {
                        $commit22 = false;
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($e25.tid.isDescendantOf($currentTid26))
                            continue $label21;
                        if ($currentTid26.parent != null) {
                            $retry23 = false;
                            throw $e25;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e25) {
                        $commit22 = false;
                        $retry23 = false;
                        if ($tm27.inNestedTxn()) { $keepReads24 = true; }
                        throw $e25;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($commit22) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e25) {
                                $commit22 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e25) {
                                $commit22 = false;
                                $retry23 = false;
                                $keepReads24 = $e25.keepReads;
                                if ($e25.tid == null ||
                                      !$e25.tid.isDescendantOf($currentTid26))
                                    throw $e25;
                                throw new fabric.worker.UserAbortException(
                                        $e25);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e25) {
                                $commit22 = false;
                                $currentTid26 = $tm27.getCurrentTid();
                                if ($currentTid26 != null) {
                                    if ($e25.tid.equals($currentTid26) ||
                                          !$e25.tid.isDescendantOf(
                                                      $currentTid26)) {
                                        throw $e25;
                                    }
                                }
                            }
                        } else {
                            if (!$tm27.inNestedTxn() &&
                                  $tm27.checkForStaleObjects()) {
                                $retry23 = true;
                                $keepReads24 = false;
                            }
                            if ($keepReads24) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e25) {
                                    $currentTid26 = $tm27.getCurrentTid();
                                    if ($currentTid26 !=
                                          null &&
                                          ($e25.tid.equals($currentTid26) ||
                                             !$e25.tid.isDescendantOf($currentTid26))) {
                                        throw $e25;
                                    } else {
                                        $retry23 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit22) {
                            {  }
                            if ($retry23) { continue $label21; }
                        }
                    }
                }
            }
            fabric.worker.metrics.StatsMap weakStats =
              fabric.worker.metrics.StatsMap.emptyStats();
            if (tmp.get$metric().$getStore().name().
                  equals(fabric.worker.Worker.getWorkerName())) {
                weakStats = tmp.get$metric().refreshWeakEstimates(weakStats);
            }
            else {
                fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker(
                                                     tmp.get$metric().$getStore(
                                                                        ).name(
                                                                            ));
                weakStats =
                  ((fabric.metrics.Metric._Proxy) tmp.get$metric()).
                    refreshWeakEstimates$remote(w, null, weakStats);
            }
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy =
              fabric.metrics.treaties.Treaty._Impl.newUnactivatedPolicy(
                                                     tmp.get$metric(),
                                                     tmp.get$predicate(),
                                                     weakStats);
            {
                fabric.worker.metrics.StatsMap weakStats$var31 = weakStats;
                fabric.worker.transaction.TransactionManager $tm38 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled41 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff39 = 1;
                boolean $doBackoff40 = true;
                boolean $retry34 = true;
                boolean $keepReads35 = false;
                $label32: for (boolean $commit33 = false; !$commit33; ) {
                    if ($backoffEnabled41) {
                        if ($doBackoff40) {
                            if ($backoff39 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff39));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e36) {
                                        
                                    }
                                }
                            }
                            if ($backoff39 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff39 =
                                  java.lang.Math.
                                    min(
                                      $backoff39 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff40 = $backoff39 <= 32 || !$doBackoff40;
                    }
                    $commit33 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (newPolicy.equals(tmp.get$policy())) return; }
                    catch (final fabric.worker.RetryException $e36) {
                        $commit33 = false;
                        continue $label32;
                    }
                    catch (fabric.worker.TransactionAbortingException $e36) {
                        $commit33 = false;
                        $retry34 = false;
                        $keepReads35 = $e36.keepReads;
                        fabric.common.TransactionID $currentTid37 =
                          $tm38.getCurrentTid();
                        if ($e36.tid == null ||
                              !$e36.tid.isDescendantOf($currentTid37)) {
                            throw $e36;
                        }
                        throw new fabric.worker.UserAbortException($e36);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e36) {
                        $commit33 = false;
                        fabric.common.TransactionID $currentTid37 =
                          $tm38.getCurrentTid();
                        if ($e36.tid.isDescendantOf($currentTid37))
                            continue $label32;
                        if ($currentTid37.parent != null) {
                            $retry34 = false;
                            throw $e36;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e36) {
                        $commit33 = false;
                        $retry34 = false;
                        if ($tm38.inNestedTxn()) { $keepReads35 = true; }
                        throw $e36;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid37 =
                          $tm38.getCurrentTid();
                        if ($commit33) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e36) {
                                $commit33 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e36) {
                                $commit33 = false;
                                $retry34 = false;
                                $keepReads35 = $e36.keepReads;
                                if ($e36.tid == null ||
                                      !$e36.tid.isDescendantOf($currentTid37))
                                    throw $e36;
                                throw new fabric.worker.UserAbortException(
                                        $e36);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e36) {
                                $commit33 = false;
                                $currentTid37 = $tm38.getCurrentTid();
                                if ($currentTid37 != null) {
                                    if ($e36.tid.equals($currentTid37) ||
                                          !$e36.tid.isDescendantOf(
                                                      $currentTid37)) {
                                        throw $e36;
                                    }
                                }
                            }
                        } else {
                            if (!$tm38.inNestedTxn() &&
                                  $tm38.checkForStaleObjects()) {
                                $retry34 = true;
                                $keepReads35 = false;
                            }
                            if ($keepReads35) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e36) {
                                    $currentTid37 = $tm38.getCurrentTid();
                                    if ($currentTid37 !=
                                          null &&
                                          ($e36.tid.equals($currentTid37) ||
                                             !$e36.tid.isDescendantOf($currentTid37))) {
                                        throw $e36;
                                    } else {
                                        $retry34 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit33) {
                            { weakStats = weakStats$var31; }
                            if ($retry34) { continue $label32; }
                        }
                    }
                }
            }
            newPolicy.activate(weakStats);
            {
                fabric.worker.metrics.StatsMap weakStats$var42 = weakStats;
                fabric.worker.transaction.TransactionManager $tm49 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled52 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff50 = 1;
                boolean $doBackoff51 = true;
                boolean $retry45 = true;
                boolean $keepReads46 = false;
                $label43: for (boolean $commit44 = false; !$commit44; ) {
                    if ($backoffEnabled52) {
                        if ($doBackoff51) {
                            if ($backoff50 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff50));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e47) {
                                        
                                    }
                                }
                            }
                            if ($backoff50 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff50 =
                                  java.lang.Math.
                                    min(
                                      $backoff50 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff51 = $backoff50 <= 32 || !$doBackoff51;
                    }
                    $commit44 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        long newPolicyExpiry =
                          newPolicy.calculateExpiry(tmp, weakStats);
                        if (newPolicyExpiry > tmp.get$$expiry()) {
                            tmp.get$policy().shiftPolicies(tmp, newPolicy);
                            tmp.set$policy(newPolicy);
                            tmp.set$$expiry((long) newPolicyExpiry);
                            tmp.registerPotentialExtensions(tmp.get$$expiry());
                        } else if (!tmp.get$policy().equals(newPolicy)) {
                            newPolicy.abandonPolicy(tmp, tmp.get$policy());
                        }
                    }
                    catch (final fabric.worker.RetryException $e47) {
                        $commit44 = false;
                        continue $label43;
                    }
                    catch (fabric.worker.TransactionAbortingException $e47) {
                        $commit44 = false;
                        $retry45 = false;
                        $keepReads46 = $e47.keepReads;
                        fabric.common.TransactionID $currentTid48 =
                          $tm49.getCurrentTid();
                        if ($e47.tid == null ||
                              !$e47.tid.isDescendantOf($currentTid48)) {
                            throw $e47;
                        }
                        throw new fabric.worker.UserAbortException($e47);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e47) {
                        $commit44 = false;
                        fabric.common.TransactionID $currentTid48 =
                          $tm49.getCurrentTid();
                        if ($e47.tid.isDescendantOf($currentTid48))
                            continue $label43;
                        if ($currentTid48.parent != null) {
                            $retry45 = false;
                            throw $e47;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e47) {
                        $commit44 = false;
                        $retry45 = false;
                        if ($tm49.inNestedTxn()) { $keepReads46 = true; }
                        throw $e47;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid48 =
                          $tm49.getCurrentTid();
                        if ($commit44) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e47) {
                                $commit44 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e47) {
                                $commit44 = false;
                                $retry45 = false;
                                $keepReads46 = $e47.keepReads;
                                if ($e47.tid == null ||
                                      !$e47.tid.isDescendantOf($currentTid48))
                                    throw $e47;
                                throw new fabric.worker.UserAbortException(
                                        $e47);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e47) {
                                $commit44 = false;
                                $currentTid48 = $tm49.getCurrentTid();
                                if ($currentTid48 != null) {
                                    if ($e47.tid.equals($currentTid48) ||
                                          !$e47.tid.isDescendantOf(
                                                      $currentTid48)) {
                                        throw $e47;
                                    }
                                }
                            }
                        } else {
                            if (!$tm49.inNestedTxn() &&
                                  $tm49.checkForStaleObjects()) {
                                $retry45 = true;
                                $keepReads46 = false;
                            }
                            if ($keepReads46) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e47) {
                                    $currentTid48 = $tm49.getCurrentTid();
                                    if ($currentTid48 !=
                                          null &&
                                          ($e47.tid.equals($currentTid48) ||
                                             !$e47.tid.isDescendantOf($currentTid48))) {
                                        throw $e47;
                                    } else {
                                        $retry45 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit44) {
                            { weakStats = weakStats$var42; }
                            if ($retry45) { continue $label43; }
                        }
                    }
                }
            }
        }
        
        /**
   * Get a proxy for this treaty stored on the given store.
   */
        public fabric.metrics.treaties.Treaty getProxy(fabric.worker.Store s) {
            if ($getStore().equals(s))
                return (fabric.metrics.treaties.Treaty) this.$getProxy();
            return this.get$predicate().getProxy(this.get$metric(), s);
        }
        
        /**
   * Remove an observer.  If this is the last remaining observer, go ahead and
   * retract it to avoid unnecessary monitoring overhead.
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            super.removeObserver(o);
            if (!isObserved()) deactivate();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        this.get$metric(
                               ))) instanceof fabric.metrics.DerivedMetric) {
                return ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      this.get$metric())).
                  getLeafSubjects();
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.$unwrap(
                                                               this.get$metric())) instanceof fabric.metrics.SampledMetric) {
                return fabric.worker.metrics.ImmutableMetricsVector.
                  createVector(
                    new fabric.metrics.Metric[] { this.get$metric() });
            }
            throw new java.lang.InternalError(
                    "Unknown metric type: " +
                      fabric.lang.Object._Proxy.
                        $getProxy(
                          (java.lang.Comparable)
                            fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                this.get$metric(
                                                                       ))).
                        getClass() +
                      " " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$metric(
                                                                     ))));
        }
        
        public java.lang.String toString() {
            return "(" +
            $getStore().name() +
            "/" +
            $getOnum() +
            "): " +
            ((java.lang.Comparable)
               fabric.lang.WrappedJavaInlineable.$unwrap(this.get$metric())).
              toString() + " " + this.get$predicate() + " until " +
            this.get$$expiry() + " [" + this.get$policy() +
            " and observed by " + getObservers() + "]";
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.treaties.Treaty._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.predicate);
            $writeInline(out, this.policy);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.predicate =
              (fabric.worker.metrics.treaties.statements.TreatyStatement)
                in.readObject();
            this.policy =
              (fabric.worker.metrics.treaties.enforcement.EnforcementPolicy)
                in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.treaties.Treaty._Impl src =
              (fabric.metrics.treaties.Treaty._Impl) other;
            this.metric = src.metric;
            this.predicate = src.predicate;
            this.policy = src.policy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$UPDATE_THRESHOLD();
        
        public long set$UPDATE_THRESHOLD(long val);
        
        public long postInc$UPDATE_THRESHOLD();
        
        public long postDec$UPDATE_THRESHOLD();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.treaties.Treaty._Static {
            public long get$UPDATE_THRESHOLD() {
                return ((fabric.metrics.treaties.Treaty._Static._Impl) fetch()).
                  get$UPDATE_THRESHOLD();
            }
            
            public long set$UPDATE_THRESHOLD(long val) {
                return ((fabric.metrics.treaties.Treaty._Static._Impl) fetch()).
                  set$UPDATE_THRESHOLD(val);
            }
            
            public long postInc$UPDATE_THRESHOLD() {
                return ((fabric.metrics.treaties.Treaty._Static._Impl) fetch()).
                  postInc$UPDATE_THRESHOLD();
            }
            
            public long postDec$UPDATE_THRESHOLD() {
                return ((fabric.metrics.treaties.Treaty._Static._Impl) fetch()).
                  postDec$UPDATE_THRESHOLD();
            }
            
            public _Proxy(fabric.metrics.treaties.Treaty._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.treaties.Treaty._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  treaties.
                  Treaty.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.treaties.Treaty._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.treaties.Treaty._Static._Impl.class);
                $instance = (fabric.metrics.treaties.Treaty._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.treaties.Treaty._Static {
            public long get$UPDATE_THRESHOLD() { return this.UPDATE_THRESHOLD; }
            
            public long set$UPDATE_THRESHOLD(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.UPDATE_THRESHOLD = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$UPDATE_THRESHOLD() {
                long tmp = this.get$UPDATE_THRESHOLD();
                this.set$UPDATE_THRESHOLD((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$UPDATE_THRESHOLD() {
                long tmp = this.get$UPDATE_THRESHOLD();
                this.set$UPDATE_THRESHOLD((long) (tmp - 1));
                return tmp;
            }
            
            public long UPDATE_THRESHOLD;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.UPDATE_THRESHOLD);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.UPDATE_THRESHOLD = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.treaties.Treaty._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm59 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled62 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff60 = 1;
                        boolean $doBackoff61 = true;
                        boolean $retry55 = true;
                        boolean $keepReads56 = false;
                        $label53: for (boolean $commit54 = false; !$commit54;
                                       ) {
                            if ($backoffEnabled62) {
                                if ($doBackoff61) {
                                    if ($backoff60 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff60));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e57) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff60 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff60 =
                                          java.lang.Math.
                                            min(
                                              $backoff60 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff61 = $backoff60 <= 32 ||
                                                 !$doBackoff61;
                            }
                            $commit54 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.treaties.Treaty._Static._Proxy.
                                  $instance.
                                  set$UPDATE_THRESHOLD((long) 200);
                            }
                            catch (final fabric.worker.RetryException $e57) {
                                $commit54 = false;
                                continue $label53;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e57) {
                                $commit54 = false;
                                $retry55 = false;
                                $keepReads56 = $e57.keepReads;
                                fabric.common.TransactionID $currentTid58 =
                                  $tm59.getCurrentTid();
                                if ($e57.tid == null ||
                                      !$e57.tid.isDescendantOf($currentTid58)) {
                                    throw $e57;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e57);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e57) {
                                $commit54 = false;
                                fabric.common.TransactionID $currentTid58 =
                                  $tm59.getCurrentTid();
                                if ($e57.tid.isDescendantOf($currentTid58))
                                    continue $label53;
                                if ($currentTid58.parent != null) {
                                    $retry55 = false;
                                    throw $e57;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e57) {
                                $commit54 = false;
                                $retry55 = false;
                                if ($tm59.inNestedTxn()) {
                                    $keepReads56 = true;
                                }
                                throw $e57;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid58 =
                                  $tm59.getCurrentTid();
                                if ($commit54) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e57) {
                                        $commit54 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e57) {
                                        $commit54 = false;
                                        $retry55 = false;
                                        $keepReads56 = $e57.keepReads;
                                        if ($e57.tid ==
                                              null ||
                                              !$e57.tid.isDescendantOf(
                                                          $currentTid58))
                                            throw $e57;
                                        throw new fabric.worker.
                                                UserAbortException($e57);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e57) {
                                        $commit54 = false;
                                        $currentTid58 = $tm59.getCurrentTid();
                                        if ($currentTid58 != null) {
                                            if ($e57.tid.equals(
                                                           $currentTid58) ||
                                                  !$e57.tid.isDescendantOf(
                                                              $currentTid58)) {
                                                throw $e57;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm59.inNestedTxn() &&
                                          $tm59.checkForStaleObjects()) {
                                        $retry55 = true;
                                        $keepReads56 = false;
                                    }
                                    if ($keepReads56) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e57) {
                                            $currentTid58 = $tm59.getCurrentTid();
                                            if ($currentTid58 != null &&
                                                  ($e57.tid.equals($currentTid58) || !$e57.tid.isDescendantOf($currentTid58))) {
                                                throw $e57;
                                            } else {
                                                $retry55 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit54) {
                                    {  }
                                    if ($retry55) { continue $label53; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -104, -58, -49, 92,
    -44, -109, -27, 42, 19, 36, 79, -65, -12, 34, 11, 113, -1, 2, 31, 107, 91,
    97, 51, -101, 121, 100, 119, 120, -13, 110, 118, -46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556557083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUZbXBU1fXu5oMkBBISPkMIkARaEHblo3YwyAgBZMtiUkJoG5Rw9+3d5JG37y3v3U02KFU7VVBbptWAOFXGHzBUjDqDtf1RafnhF6ODlmFqbUflR2npUMZhbEudttJz7rtv3+7m7ZJ12kzuPXfvvefc833Pe2/0KimzTNIcoxFVC/DhBLMCG2kkFO6kpsWi7Rq1rG0w26tMLA0dvnwi2uQn/jCpVqhu6KpCtV7d4mRyeDcdpEGd8WD31lDbDlKpIOImavVz4t+xLmWSeQlDG+7TDC4PGUP/0C3Bkad21p4qITU9pEbVuzjlqtJu6JyleA+pjrN4hJnW2miURXvIFJ2xaBczVaqpe2GjofeQOkvt0ylPmszayixDG8SNdVYywUxxpjOJ7BvAtplUuGEC+7U2+0muasGwavG2MCmPqUyLWnvId0lpmJTFNNoHG6eHHSmCgmJwI87D9ioV2DRjVGEOSumAqkc5mZuLkZa4dTNsANQJccb7jfRRpTqFCVJns6RRvS/YxU1V74OtZUYSTuGkIS9R2FSRoMoA7WO9nMzM3ddpL8GuSqEWROFkWu42QQls1pBjswxrXb179cH79E26n/iA5yhTNOS/ApCacpC2shgzma4wG7F6cfgwnX76gJ8Q2DwtZ7O95xf3X7tzSdOZt+09sz32dER2M4X3Kscik3/T2L5oVQmyUZEwLBVdIUtyYdVOudKWSoC3T09TxMWAs3hm65vfefAku+InVSFSrhhaMg5eNUUx4glVY+ZdTGcm5SwaIpVMj7aL9RCZAOOwqjN7tiMWsxgPkVJNTJUb4jeoKAYkUEUTYKzqMcMZJyjvF+NUghAyARrxwb9FyIqHYTybkJJRTrYG+404C0a0JBsC9w5CY9RU+oMQt6aqLFWMxHDQMpWgmdS5Cjvt+SC4EgAryE0GQYKmxcFwALhJ/F+oplCW2iGfD9Q8VzGiLEItsJn0n3WdGoTIJkOLMrNX0Q6eDpH6008LH6pEv7fAd4WWfGD3xtyMkYk7kly34dpLve/Y/oe4UomcNNlcBiSXAYfLgM0lMFaNsRWAbBWAbDXqSwXaj4ZeEC5UbolYS9OqBlq3JzTKY4YZTxGfTwg2VeAL3wHLD0BGgaRRvajr3m/sOtBcAk6bGCpFO8LW1twQchNPCEYU4qJXqdl/+R8vH95nuMHESeuYGB+LiTHanKsl01BYFHKgS37xPPpq7+l9rX7ML5WQ+jgF54Q80pR7Rlastjl5D7VRFiYTUQdUwyUnWVXxftMYcmeE9SdjV2c7Aiorh0GRMu/oSjz7u3N/WSEuEye71mSk4S7G2zIiGonViNid4uoe7Mlg30dHOp88dHX/DqF42NHidWAr9u0QyRRC2DAffnvPh598fOyC3zUWJ+WJZERTlZSQZcoN+PNB+wIbhiVOIITk3C5Twrx0TkjgyQtd3iA7aJChgHWrtVuPG1E1ptKIxtBT/l2zYNmrfz1Ya5tbgxlbeSZZcnMC7vysdeTBd3ZebxJkfAreTq7+3G12yqt3Ka81TTqMfKQeOj/n6bfos+D5kLAsdS8TOYgIfRBhwOVCF0tFvyxnbSV2zba2GsV8qTU2/W/Ee9T1xZ7g6DMN7Wuu2DGf9kWkMd8j5rfTjDBZfjL+d39z+Rt+MqGH1IornOp8O4X0BW7QA5ew1S4nw2RS1nr2hWrfHm3pWGvMjYOMY3OjwM01MMbdOK6yHd92HFBEDSppjt1Ky2xYch1X6xPYT035iBjcLlBaRL8Qu0VCkX4cLuakUo3HkxzNLg64BXxUZjX8OQ2u7pxct8VdnZWbveyAxP62bEYXQWsCRoMSTvNgdH0hRrFb43BYmTBZFCpDzhwmV0kmhwxzgJlj8zIomrM407mTorvSE+MWpBoFWQBtLgjQLeFqD0G2eAviE4wbHPyCRVNpsn4kO1GSa5NweQZZzBoGJI1hR9jVNxGWwd1vKkK6wAZ33OkSGZe805Gx5dCeI2TaixL+2EPeb3vLWyI9rNwSZXaWBWu7O9ev3bahd9umrRu6NnWE13uEdaepxiEzD8qqjh0YeexG4OCIndLs0rdlTPWZiWOXv+LISeLcFJwyv9ApAmPjn1/e98uf7ttvl4Z12YXcBj0Zf/G3/3k3cOTiWY/CoFQz7Ku9NpXHCYRSXOuLv3JZhb0g4fEMJWfkPp/jAI05ISk464hYzByExxdPA6Poc/LV2kLsY98bORrtOL7ML3NvBNyVG4mlGhtkWgYXU1CJY57ltognDDeRXrwyZ1X7wKU+W4lzc07O3f38ltGzdy1UnvCTknTGHPNYk43Ulp0nq0wGT2X6tqxsOS+t5kpU893QvkJIWS/AhRBkf8j0ZTcCWrCLZcdnhUT5vYQXci3k3l+l9j2FP+/ELoTdNsd0Td6xi+nI2kITeYyHs8MF7skHsOOgM5t6qyTb6qSEVjvptbpC7knLV4tUdkK7E8S8X8JAHtXkT8735GS0GklpqYTz8musxE0Xrsb2imP3FxD6UeweAi/V2ZAtn5d4wvIzoW2HLNso4aTiLI8o1RKW55cjk7kfFVh7ArvHOSkbpJpqP5XvlPkJAeVkQsQwNEZ1L1kaoO0mUAlIGClOFkShEu4YnyzPFFg7it1TwLGqC2nw50gO29VOqTIE3nafhLsLsN059nJEFFXCXeNj+3iBtRPYPcdJVZRRKGEHnVIixw6lg4Y0kCtNPRJZCe1xQuo+BfgYwP3FxMs3veKlTlJ6RMLBLxUvpwrI/DPs4EG/DuJlrRQ6ahcFborykPMQIVNXARyBuff+J3IipXMS/upLyflaATkFxZ9zMhXk7NbpzSUV/nkbtBNQ9Lwi4aPF+SeiHJDwgfwi+dwiYERQfaOAIG9hd4aT+mQiChJ8S+X97UnTlcMzzlZDew2yxHoJpxYnB6LUS1g1Ljl2CarnCsjxPnZnOZltsj54JmQgAod6VKXahhQMLHwAxS3Pe6U7vD/eJaSRS9hZXLpDlA4JQ+PLGx8UWPsQu/OcTOqnelRj3cIwlnO5L/a+3EPOE5ZTosGjpPdF76WAFmifE9L8fQkLJU4PBSCKKqEyPgX8scDan7D7BHwyQpWBPtNI6tG0EfPaEComHzjVgpMS/rAoEQTKDyR8pIjYulpAjk+xuwzFg8kiVMOC0ot7URvdCkQD8Aj7hYQfF5MD89VGgtJHEr4/LqFqxWHXCwj1OXbXOKlJC9VLrWFdyWuY+UAanmSDyyVsKM4wiDJLwvqbyuBESX12lHRxw2T5614fyS+wTxRj/+Kkoo/xTtNI5a8AvwoMQN1/670SrilOUkS5Q8Kvj8taqmBwUgHma7Cr4GSyyeLGYDo55DUWZHRfjJBlmyWsLU4ERKmRsHJcicA3vcDaTOymgK+B6sOMxrqS4o1XOhcuuUkulK+RtjPxgWzc2RCqX1+CkOWvS/hKcUpAlFMSjo5PCS0F1hZg1wT+xw37C5ojfa14F4pvAgMZC2MdnJNy+9EFX6DO9viYIT+0Ke2vs2OXNi+ZludDxswxnz4l3ktHaypmHO3+QLyYT39EqwyTilhS0zJfNGaMyxMmiwkHJpX2a8eEEDfAyYw8Xz5QB3KIovqW2BjLwL2zMbj4GomjzH0rQRH2Pvz1NWGUBrdz1Nri9eJjbcTiJpR5jgfmTSWkIWnip+LRz2b8s7xi20XxZh5MOe/Im+/dc+HJS4vrWzt+/bfmiXtu+OcO7KArfjIcHUp9pg+e/y90jqgmwh4AAA==";
}
