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
                    fabric.metrics.treaties.Treaty treaty$var440 = treaty;
                    fabric.worker.transaction.TransactionManager $tm447 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled450 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff448 = 1;
                    boolean $doBackoff449 = true;
                    boolean $retry443 = true;
                    boolean $keepReads444 = false;
                    $label441: for (boolean $commit442 = false; !$commit442; ) {
                        if ($backoffEnabled450) {
                            if ($doBackoff449) {
                                if ($backoff448 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff448));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e445) {
                                            
                                        }
                                    }
                                }
                                if ($backoff448 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff448 =
                                      java.lang.Math.
                                        min(
                                          $backoff448 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff449 = $backoff448 <= 32 || !$doBackoff449;
                        }
                        $commit442 = true;
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
                        catch (final fabric.worker.RetryException $e445) {
                            $commit442 = false;
                            continue $label441;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e445) {
                            $commit442 = false;
                            $retry443 = false;
                            $keepReads444 = $e445.keepReads;
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($e445.tid == null ||
                                  !$e445.tid.isDescendantOf($currentTid446)) {
                                throw $e445;
                            }
                            throw new fabric.worker.UserAbortException($e445);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e445) {
                            $commit442 = false;
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($e445.tid.isDescendantOf($currentTid446))
                                continue $label441;
                            if ($currentTid446.parent != null) {
                                $retry443 = false;
                                throw $e445;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e445) {
                            $commit442 = false;
                            $retry443 = false;
                            if ($tm447.inNestedTxn()) { $keepReads444 = true; }
                            throw $e445;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid446 =
                              $tm447.getCurrentTid();
                            if ($commit442) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e445) {
                                    $commit442 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e445) {
                                    $commit442 = false;
                                    $retry443 = false;
                                    $keepReads444 = $e445.keepReads;
                                    if ($e445.tid ==
                                          null ||
                                          !$e445.tid.isDescendantOf(
                                                       $currentTid446))
                                        throw $e445;
                                    throw new fabric.worker.UserAbortException(
                                            $e445);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e445) {
                                    $commit442 = false;
                                    $currentTid446 = $tm447.getCurrentTid();
                                    if ($currentTid446 != null) {
                                        if ($e445.tid.equals($currentTid446) ||
                                              !$e445.tid.isDescendantOf(
                                                           $currentTid446)) {
                                            throw $e445;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm447.inNestedTxn() &&
                                      $tm447.checkForStaleObjects()) {
                                    $retry443 = true;
                                    $keepReads444 = false;
                                }
                                if ($keepReads444) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e445) {
                                        $currentTid446 = $tm447.getCurrentTid();
                                        if ($currentTid446 != null &&
                                              ($e445.tid.equals($currentTid446) || !$e445.tid.isDescendantOf($currentTid446))) {
                                            throw $e445;
                                        } else {
                                            $retry443 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit442) {
                                { treaty = treaty$var440; }
                                if ($retry443) { continue $label441; }
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
            if (result) tm.registerExpiryUse(this.get$$expiry());
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
            return this.get$$expiry() < java.lang.System.currentTimeMillis();
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
                    fabric.worker.transaction.TransactionManager $tm457 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled460 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff458 = 1;
                    boolean $doBackoff459 = true;
                    boolean $retry453 = true;
                    boolean $keepReads454 = false;
                    $label451: for (boolean $commit452 = false; !$commit452; ) {
                        if ($backoffEnabled460) {
                            if ($doBackoff459) {
                                if ($backoff458 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff458));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e455) {
                                            
                                        }
                                    }
                                }
                                if ($backoff458 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff458 =
                                      java.lang.Math.
                                        min(
                                          $backoff458 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff459 = $backoff458 <= 32 || !$doBackoff459;
                        }
                        $commit452 = true;
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
                        catch (final fabric.worker.RetryException $e455) {
                            $commit452 = false;
                            continue $label451;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e455) {
                            $commit452 = false;
                            $retry453 = false;
                            $keepReads454 = $e455.keepReads;
                            fabric.common.TransactionID $currentTid456 =
                              $tm457.getCurrentTid();
                            if ($e455.tid == null ||
                                  !$e455.tid.isDescendantOf($currentTid456)) {
                                throw $e455;
                            }
                            throw new fabric.worker.UserAbortException($e455);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e455) {
                            $commit452 = false;
                            fabric.common.TransactionID $currentTid456 =
                              $tm457.getCurrentTid();
                            if ($e455.tid.isDescendantOf($currentTid456))
                                continue $label451;
                            if ($currentTid456.parent != null) {
                                $retry453 = false;
                                throw $e455;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e455) {
                            $commit452 = false;
                            $retry453 = false;
                            if ($tm457.inNestedTxn()) { $keepReads454 = true; }
                            throw $e455;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid456 =
                              $tm457.getCurrentTid();
                            if ($commit452) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e455) {
                                    $commit452 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e455) {
                                    $commit452 = false;
                                    $retry453 = false;
                                    $keepReads454 = $e455.keepReads;
                                    if ($e455.tid ==
                                          null ||
                                          !$e455.tid.isDescendantOf(
                                                       $currentTid456))
                                        throw $e455;
                                    throw new fabric.worker.UserAbortException(
                                            $e455);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e455) {
                                    $commit452 = false;
                                    $currentTid456 = $tm457.getCurrentTid();
                                    if ($currentTid456 != null) {
                                        if ($e455.tid.equals($currentTid456) ||
                                              !$e455.tid.isDescendantOf(
                                                           $currentTid456)) {
                                            throw $e455;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm457.inNestedTxn() &&
                                      $tm457.checkForStaleObjects()) {
                                    $retry453 = true;
                                    $keepReads454 = false;
                                }
                                if ($keepReads454) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e455) {
                                        $currentTid456 = $tm457.getCurrentTid();
                                        if ($currentTid456 != null &&
                                              ($e455.tid.equals($currentTid456) || !$e455.tid.isDescendantOf($currentTid456))) {
                                            throw $e455;
                                        } else {
                                            $retry453 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit452) {
                                {  }
                                if ($retry453) { continue $label451; }
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
                fabric.worker.transaction.TransactionManager $tm467 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled470 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff468 = 1;
                boolean $doBackoff469 = true;
                boolean $retry463 = true;
                boolean $keepReads464 = false;
                $label461: for (boolean $commit462 = false; !$commit462; ) {
                    if ($backoffEnabled470) {
                        if ($doBackoff469) {
                            if ($backoff468 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff468));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e465) {
                                        
                                    }
                                }
                            }
                            if ($backoff468 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff468 =
                                  java.lang.Math.
                                    min(
                                      $backoff468 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff469 = $backoff468 <= 32 || !$doBackoff469;
                    }
                    $commit462 = true;
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
                    catch (final fabric.worker.RetryException $e465) {
                        $commit462 = false;
                        continue $label461;
                    }
                    catch (fabric.worker.TransactionAbortingException $e465) {
                        $commit462 = false;
                        $retry463 = false;
                        $keepReads464 = $e465.keepReads;
                        fabric.common.TransactionID $currentTid466 =
                          $tm467.getCurrentTid();
                        if ($e465.tid == null ||
                              !$e465.tid.isDescendantOf($currentTid466)) {
                            throw $e465;
                        }
                        throw new fabric.worker.UserAbortException($e465);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e465) {
                        $commit462 = false;
                        fabric.common.TransactionID $currentTid466 =
                          $tm467.getCurrentTid();
                        if ($e465.tid.isDescendantOf($currentTid466))
                            continue $label461;
                        if ($currentTid466.parent != null) {
                            $retry463 = false;
                            throw $e465;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e465) {
                        $commit462 = false;
                        $retry463 = false;
                        if ($tm467.inNestedTxn()) { $keepReads464 = true; }
                        throw $e465;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid466 =
                          $tm467.getCurrentTid();
                        if ($commit462) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e465) {
                                $commit462 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e465) {
                                $commit462 = false;
                                $retry463 = false;
                                $keepReads464 = $e465.keepReads;
                                if ($e465.tid == null ||
                                      !$e465.tid.isDescendantOf($currentTid466))
                                    throw $e465;
                                throw new fabric.worker.UserAbortException(
                                        $e465);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e465) {
                                $commit462 = false;
                                $currentTid466 = $tm467.getCurrentTid();
                                if ($currentTid466 != null) {
                                    if ($e465.tid.equals($currentTid466) ||
                                          !$e465.tid.isDescendantOf(
                                                       $currentTid466)) {
                                        throw $e465;
                                    }
                                }
                            }
                        } else {
                            if (!$tm467.inNestedTxn() &&
                                  $tm467.checkForStaleObjects()) {
                                $retry463 = true;
                                $keepReads464 = false;
                            }
                            if ($keepReads464) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e465) {
                                    $currentTid466 = $tm467.getCurrentTid();
                                    if ($currentTid466 !=
                                          null &&
                                          ($e465.tid.equals($currentTid466) ||
                                             !$e465.tid.isDescendantOf($currentTid466))) {
                                        throw $e465;
                                    } else {
                                        $retry463 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit462) {
                            {  }
                            if ($retry463) { continue $label461; }
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
                fabric.worker.metrics.StatsMap weakStats$var471 = weakStats;
                fabric.worker.transaction.TransactionManager $tm478 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled481 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff479 = 1;
                boolean $doBackoff480 = true;
                boolean $retry474 = true;
                boolean $keepReads475 = false;
                $label472: for (boolean $commit473 = false; !$commit473; ) {
                    if ($backoffEnabled481) {
                        if ($doBackoff480) {
                            if ($backoff479 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff479));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e476) {
                                        
                                    }
                                }
                            }
                            if ($backoff479 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff479 =
                                  java.lang.Math.
                                    min(
                                      $backoff479 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff480 = $backoff479 <= 32 || !$doBackoff480;
                    }
                    $commit473 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (newPolicy.equals(tmp.get$policy())) return; }
                    catch (final fabric.worker.RetryException $e476) {
                        $commit473 = false;
                        continue $label472;
                    }
                    catch (fabric.worker.TransactionAbortingException $e476) {
                        $commit473 = false;
                        $retry474 = false;
                        $keepReads475 = $e476.keepReads;
                        fabric.common.TransactionID $currentTid477 =
                          $tm478.getCurrentTid();
                        if ($e476.tid == null ||
                              !$e476.tid.isDescendantOf($currentTid477)) {
                            throw $e476;
                        }
                        throw new fabric.worker.UserAbortException($e476);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e476) {
                        $commit473 = false;
                        fabric.common.TransactionID $currentTid477 =
                          $tm478.getCurrentTid();
                        if ($e476.tid.isDescendantOf($currentTid477))
                            continue $label472;
                        if ($currentTid477.parent != null) {
                            $retry474 = false;
                            throw $e476;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e476) {
                        $commit473 = false;
                        $retry474 = false;
                        if ($tm478.inNestedTxn()) { $keepReads475 = true; }
                        throw $e476;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid477 =
                          $tm478.getCurrentTid();
                        if ($commit473) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e476) {
                                $commit473 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e476) {
                                $commit473 = false;
                                $retry474 = false;
                                $keepReads475 = $e476.keepReads;
                                if ($e476.tid == null ||
                                      !$e476.tid.isDescendantOf($currentTid477))
                                    throw $e476;
                                throw new fabric.worker.UserAbortException(
                                        $e476);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e476) {
                                $commit473 = false;
                                $currentTid477 = $tm478.getCurrentTid();
                                if ($currentTid477 != null) {
                                    if ($e476.tid.equals($currentTid477) ||
                                          !$e476.tid.isDescendantOf(
                                                       $currentTid477)) {
                                        throw $e476;
                                    }
                                }
                            }
                        } else {
                            if (!$tm478.inNestedTxn() &&
                                  $tm478.checkForStaleObjects()) {
                                $retry474 = true;
                                $keepReads475 = false;
                            }
                            if ($keepReads475) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e476) {
                                    $currentTid477 = $tm478.getCurrentTid();
                                    if ($currentTid477 !=
                                          null &&
                                          ($e476.tid.equals($currentTid477) ||
                                             !$e476.tid.isDescendantOf($currentTid477))) {
                                        throw $e476;
                                    } else {
                                        $retry474 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit473) {
                            { weakStats = weakStats$var471; }
                            if ($retry474) { continue $label472; }
                        }
                    }
                }
            }
            newPolicy.activate(weakStats);
            {
                fabric.worker.metrics.StatsMap weakStats$var482 = weakStats;
                fabric.worker.transaction.TransactionManager $tm489 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled492 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff490 = 1;
                boolean $doBackoff491 = true;
                boolean $retry485 = true;
                boolean $keepReads486 = false;
                $label483: for (boolean $commit484 = false; !$commit484; ) {
                    if ($backoffEnabled492) {
                        if ($doBackoff491) {
                            if ($backoff490 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff490));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e487) {
                                        
                                    }
                                }
                            }
                            if ($backoff490 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff490 =
                                  java.lang.Math.
                                    min(
                                      $backoff490 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff491 = $backoff490 <= 32 || !$doBackoff491;
                    }
                    $commit484 = true;
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
                    catch (final fabric.worker.RetryException $e487) {
                        $commit484 = false;
                        continue $label483;
                    }
                    catch (fabric.worker.TransactionAbortingException $e487) {
                        $commit484 = false;
                        $retry485 = false;
                        $keepReads486 = $e487.keepReads;
                        fabric.common.TransactionID $currentTid488 =
                          $tm489.getCurrentTid();
                        if ($e487.tid == null ||
                              !$e487.tid.isDescendantOf($currentTid488)) {
                            throw $e487;
                        }
                        throw new fabric.worker.UserAbortException($e487);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e487) {
                        $commit484 = false;
                        fabric.common.TransactionID $currentTid488 =
                          $tm489.getCurrentTid();
                        if ($e487.tid.isDescendantOf($currentTid488))
                            continue $label483;
                        if ($currentTid488.parent != null) {
                            $retry485 = false;
                            throw $e487;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e487) {
                        $commit484 = false;
                        $retry485 = false;
                        if ($tm489.inNestedTxn()) { $keepReads486 = true; }
                        throw $e487;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid488 =
                          $tm489.getCurrentTid();
                        if ($commit484) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e487) {
                                $commit484 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e487) {
                                $commit484 = false;
                                $retry485 = false;
                                $keepReads486 = $e487.keepReads;
                                if ($e487.tid == null ||
                                      !$e487.tid.isDescendantOf($currentTid488))
                                    throw $e487;
                                throw new fabric.worker.UserAbortException(
                                        $e487);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e487) {
                                $commit484 = false;
                                $currentTid488 = $tm489.getCurrentTid();
                                if ($currentTid488 != null) {
                                    if ($e487.tid.equals($currentTid488) ||
                                          !$e487.tid.isDescendantOf(
                                                       $currentTid488)) {
                                        throw $e487;
                                    }
                                }
                            }
                        } else {
                            if (!$tm489.inNestedTxn() &&
                                  $tm489.checkForStaleObjects()) {
                                $retry485 = true;
                                $keepReads486 = false;
                            }
                            if ($keepReads486) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e487) {
                                    $currentTid488 = $tm489.getCurrentTid();
                                    if ($currentTid488 !=
                                          null &&
                                          ($e487.tid.equals($currentTid488) ||
                                             !$e487.tid.isDescendantOf($currentTid488))) {
                                        throw $e487;
                                    } else {
                                        $retry485 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit484) {
                            { weakStats = weakStats$var482; }
                            if ($retry485) { continue $label483; }
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
                        fabric.worker.transaction.TransactionManager $tm499 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled502 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff500 = 1;
                        boolean $doBackoff501 = true;
                        boolean $retry495 = true;
                        boolean $keepReads496 = false;
                        $label493: for (boolean $commit494 = false; !$commit494;
                                        ) {
                            if ($backoffEnabled502) {
                                if ($doBackoff501) {
                                    if ($backoff500 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff500));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e497) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff500 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff500 =
                                          java.lang.Math.
                                            min(
                                              $backoff500 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff501 = $backoff500 <= 32 ||
                                                  !$doBackoff501;
                            }
                            $commit494 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.treaties.Treaty._Static._Proxy.
                                  $instance.
                                  set$UPDATE_THRESHOLD((long) 200);
                            }
                            catch (final fabric.worker.RetryException $e497) {
                                $commit494 = false;
                                continue $label493;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e497) {
                                $commit494 = false;
                                $retry495 = false;
                                $keepReads496 = $e497.keepReads;
                                fabric.common.TransactionID $currentTid498 =
                                  $tm499.getCurrentTid();
                                if ($e497.tid ==
                                      null ||
                                      !$e497.tid.isDescendantOf(
                                                   $currentTid498)) {
                                    throw $e497;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e497);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e497) {
                                $commit494 = false;
                                fabric.common.TransactionID $currentTid498 =
                                  $tm499.getCurrentTid();
                                if ($e497.tid.isDescendantOf($currentTid498))
                                    continue $label493;
                                if ($currentTid498.parent != null) {
                                    $retry495 = false;
                                    throw $e497;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e497) {
                                $commit494 = false;
                                $retry495 = false;
                                if ($tm499.inNestedTxn()) {
                                    $keepReads496 = true;
                                }
                                throw $e497;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid498 =
                                  $tm499.getCurrentTid();
                                if ($commit494) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e497) {
                                        $commit494 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e497) {
                                        $commit494 = false;
                                        $retry495 = false;
                                        $keepReads496 = $e497.keepReads;
                                        if ($e497.tid ==
                                              null ||
                                              !$e497.tid.isDescendantOf(
                                                           $currentTid498))
                                            throw $e497;
                                        throw new fabric.worker.
                                                UserAbortException($e497);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e497) {
                                        $commit494 = false;
                                        $currentTid498 = $tm499.getCurrentTid();
                                        if ($currentTid498 != null) {
                                            if ($e497.tid.equals(
                                                            $currentTid498) ||
                                                  !$e497.tid.
                                                  isDescendantOf(
                                                    $currentTid498)) {
                                                throw $e497;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm499.inNestedTxn() &&
                                          $tm499.checkForStaleObjects()) {
                                        $retry495 = true;
                                        $keepReads496 = false;
                                    }
                                    if ($keepReads496) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e497) {
                                            $currentTid498 = $tm499.getCurrentTid();
                                            if ($currentTid498 != null &&
                                                  ($e497.tid.equals($currentTid498) || !$e497.tid.isDescendantOf($currentTid498))) {
                                                throw $e497;
                                            } else {
                                                $retry495 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit494) {
                                    {  }
                                    if ($retry495) { continue $label493; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 35, 84, -96, -49, 95,
    -85, 126, -8, 86, 103, 78, -2, 0, -24, -57, 46, 98, -50, -42, -86, -50,
    -107, -6, 71, 21, 65, 62, -48, -85, 56, 6, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556552475000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZC2wUxxmeO9vnBwYbG/MwxjbYoWDgLnYTVGKKMMaAy4Fdv6qYlmNvb87eeG/3sjtnn5OQpqkoqIkQbYCSClCjQEPAhSpV1KgtUqo2DWkQ6QPSR9QWVU1LRJFKI0pUpaH/Pzt3e3feO+yotbzzz+3M/8///GZ2d+IGKTANsiQsBRXVy8aj1PRukoKd/m7JMGmoXZVMsw/uBuQZ+Z2Hr70QqnUTt5+UypKma4osqQHNZGSW/yFpVPJplPn6ezpbd5BiGRm3SOYwI+4dG+IGqY/q6viQqjOxyCT5h1b4Dn5jZ/lLeaRskJQpWi+TmCK36xqjcTZISiM0EqSG2RYK0dAgma1RGuqlhiKpyiMwUdcGSYWpDGkSixnU7KGmro7ixAozFqUGXzNxE9XXQW0jJjPdAPXLLfVjTFF9fsVkrX7iCStUDZkPk8dJvp8UhFVpCCbO9Ses8HGJvk14H6aXKKCmEZZkmmDJH1G0ECN1mRxJixu3wgRgLYxQNqwnl8rXJLhBKiyVVEkb8vUyQ9GGYGqBHoNVGKnOKhQmFUUleUQaogFG5mfO67aGYFYxdwuyMFKVOY1LgphVZ8QsJVo3tq/d/6i2RXMTF+gcorKK+hcBU20GUw8NU4NqMrUYS5v8h6W55/e5CYHJVRmTrTnff+zm+pW1r16w5ix0mNMVfIjKLCCfCM76ZU378jV5qEZRVDcVTIU0y3lUu8VIazwK2T43KREHvYnBV3t+9uATp+l1NynpJB5ZV2MRyKrZsh6JKio1NlONGhKjoU5STLVQOx/vJIXQ9ysate52hcMmZZ0kX+W3PDr/DS4Kgwh0USH0FS2sJ/pRiQ3zfjxKCCmEi7jgXyek5QXoLyQkb4KRHt+wHqG+oBqjY5DePrioZMjDPqhbQ5FXyXp03Gcass+IaUyBmdZ9H6QSENPHDApFgqHFzrgXtIn+X6TG0ZbyMZcL3Fwn6yEalEyImcifDd0qlMgWXQ1RIyCr+893ksrzz/IcKsa8NyF3uZdcEPeaTMRI5T0Y29Bx82zgTSv/kFc4kZFaS0uv0NKb0NJraQmKlWJteQGtvIBWE664t/145xmeQh6T11pSVinIeiCqSiysG5E4cbm4YXM4P88diPwIIAqARuny3i98Zte+JXmQtNGxfIwjTG3MLCEbeDqhJ0FdBOSyvdf+de7wbt0uJkYaJ9X4ZE6s0SWZXjJ0mYYAA23xTfXSy4HzuxvdiC/FAH1MguQEHKnNXCOtVlsTuIfeKPCTGegDScWhBFiVsGFDH7Pv8OjPwqbCSgR0VoaCHDI/3Rs99ttL732SbyYJdC1LgeFeylpTKhqFlfHanW37HuJJYd4fjnQ/c+jG3h3c8TCjwWnBRmzboZIlKGHd2HPh4d/96Y8nLrvtYDHiicaCqiLHuS2z78CfC66P8MKyxBtIAZzbBSTUJzEhiisvtXUDdFABoUB1s7Ffi+ghJaxIQZVipnxYdk/zy3/fX26FW4U7lvMMsvLuAuz7CzaQJ97cebuWi3HJuDvZ/rOnWZBXaUtuMwxpHPWIf+lXi559XToGmQ+AZSqPUI5BhPuD8AC2cF+s4m1zxth92CyxvFXD7+ebk+F/E+6jdi4O+iaOVrevu27VfDIXUcZih5ofkFLKpOV05JZ7iec1NykcJOV8C5c0NiABfEEaDMImbLaLm34yM208fUO1do/WZK3VZNZByrKZVWBjDfRxNvZLrMS3EgccUYZOWmRd+QUWzbuNo5VRbOfEXYR3HuAsDbxdis1y7kg3dpsYKVYikRjDsPMFVkCOClTDn1WwdWdg3TZOcbDaqj9sV6frtRyuWtDLJ2iVg14bcumFzbqEQsVRg4bgIMhoQqc1Qqcx3RihxmQYBr8yGqEaSyByb/IGSliQibpOhpSiIffAVQcG9Au61sGQrc6GuLjiOoM0oKF4Uqwbxc4Q4loFbUkRiyChA0aMJ4xdexdjKWz1hsyt83bY/W5byJTsnYuKtcB1lJA5bwn6PQd7B5ztzRMJ5TH5qTotguX93Rvb+joCfVt6Onq3dPk3OlRxt6FEAIhHxSGO7jv41Tve/QctBLNOug2TDpupPNZply85k68bh1UW51qFc2z627ndPzy1e691EqxIP7d1aLHId97+z0XvkatvOJwD8lXd2snL41mSgDvFjj7/84hD1xlBT6Y4OQXqXIkEqMmoQK5ZV9CkxqgFa9Vo6aJsJ2lu5YknDx4PdZ1sdgtk3QXZyfToKpWOUjVl0dnos0lPatv484MNk1evL1rTPvLukOWzuoyVM2e/uG3ijc1L5a+7SV4SDyc9tKQztaajYIlB4ZlL60vDwvqkV4vRq9vh+gQhBQGgS6Gm3klNXTvhG7AJpZdjkWD5vaCXMwNi70751i6EP9djsxmbnkSkap1LFdHH3CZFnYuRKxfLsQs+ig1kW50lvVGIbUwgQKOFcY22kZGkfeUoZSdc68HMxwT1ZnFNdiwezACwMiFplaD12T2WZ6OD7bFRvuyXcxi9B5vdkKUaHbPsczKPR34+XAMAqjWCzpxe5JGlVFBPdjtSlXs6x9h+bPYyUjAqqYq13OcFHCGBZ+bCoK6rVNKcbKmGSyFkVpOgC6ZnC7LMF7RiarYcyTH2TWyeAY0VjVuDPw9kqF2aOIiYkBbHBH06h9rbJ++FyPKUoE9OTe3ncow9j81RRkpCVIID6mji5JARh/xRXQTItqYShdwH115CKvDGVwATX5xOvXQ51UuFkHRK0CMfq17O5rD5u9icgkMo1EubMDpknQFsiHKw82vQ3wH0ACh5839iJ0r6h6BXP5adr+Sw8wfYvMTIHLCzX5PubinPz9VwPU9I1RVBz0wvP5HltKDPZTfJZe/5B7jUH+cw5CfY/IiRylg0BBZ8TmHD7THDtsOxztbC9QohC8KCrp6eHchyv6Arp2THTi715znsuIjNa4wsNOgQPPFRMIHB8VOR1I44dEx8vMQpJ53gDvePC3AKOiyoMT24Q5aHBR2ZGm78OsfYFWwuMTJzWNJCKu3ngTETm3uT8+bemXh+SpzI4EHReaN3ckADXLcIWfxtQXMBp4MDkOUpQfdMzQFXc4z9GZt3ICeDkjwyZOgxLZQMYtYYwonJBeeBxouCnpuWCZzlrKCnplFb7+Ww4zo2f4HDg0GDkooHSift+dnoXhAKm+3yekHJdDAw29kIJS27I+g/p2RUOV/s/RxG3cLmBiNlSaMCkjmuyVkDsxhEf5YQb5+ga7OYliUwyNIq6P13tSFRJZXpVdLLdIPmOPd+mN1gF3fxbUaKhijrNvR49hPgMlAANjHfFwXdNT1LkSUg6INTitYQV7A4h/IzsMlnZJZBI/ooTX1ccwzWRpAchHSMCNoyPROQpVnQFVMCAldFjrE52MyEXAPX+6kU7o3x91lJLFx5Fyy0XhKZA5R//poyGsLp16US0vxXQa9MzwnIclnQS1NzQl2OscXYVEP+Md36Ppawvpy/6cT3fN6UgckJzojHenTB16MLHT5ViM9ocvtP6Yl3t66syvKZYv6kD5uC7+zxsqJ5x/t/w1+7Jz+RFftJUTimqqmvEVP6nqhBwwp3Z7H1UjHKzW1iZF6W7xroA9FFU13LLI5VkN7pHIx/a8Re6rx7wRHWPPzVzINSbTcJtzY4vedoC5rMgGOeyEDOwLWujhn43Xfi/XkfeIr6rvLX7BC5+oa+b70VOPP4BwND2z8i1173Bi+9ffrSoX9vrmpb94szn/Js/i8mUXRajx4AAA==";
}
