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
                    fabric.metrics.treaties.Treaty treaty$var409 = treaty;
                    fabric.worker.transaction.TransactionManager $tm416 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled419 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff417 = 1;
                    boolean $doBackoff418 = true;
                    boolean $retry412 = true;
                    boolean $keepReads413 = false;
                    $label410: for (boolean $commit411 = false; !$commit411; ) {
                        if ($backoffEnabled419) {
                            if ($doBackoff418) {
                                if ($backoff417 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff417));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e414) {
                                            
                                        }
                                    }
                                }
                                if ($backoff417 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff417 =
                                      java.lang.Math.
                                        min(
                                          $backoff417 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff418 = $backoff417 <= 32 || !$doBackoff418;
                        }
                        $commit411 = true;
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
                        catch (final fabric.worker.RetryException $e414) {
                            $commit411 = false;
                            continue $label410;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e414) {
                            $commit411 = false;
                            $retry412 = false;
                            $keepReads413 = $e414.keepReads;
                            fabric.common.TransactionID $currentTid415 =
                              $tm416.getCurrentTid();
                            if ($e414.tid == null ||
                                  !$e414.tid.isDescendantOf($currentTid415)) {
                                throw $e414;
                            }
                            throw new fabric.worker.UserAbortException($e414);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e414) {
                            $commit411 = false;
                            fabric.common.TransactionID $currentTid415 =
                              $tm416.getCurrentTid();
                            if ($e414.tid.isDescendantOf($currentTid415))
                                continue $label410;
                            if ($currentTid415.parent != null) {
                                $retry412 = false;
                                throw $e414;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e414) {
                            $commit411 = false;
                            $retry412 = false;
                            if ($tm416.inNestedTxn()) { $keepReads413 = true; }
                            throw $e414;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid415 =
                              $tm416.getCurrentTid();
                            if ($commit411) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e414) {
                                    $commit411 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e414) {
                                    $commit411 = false;
                                    $retry412 = false;
                                    $keepReads413 = $e414.keepReads;
                                    if ($e414.tid ==
                                          null ||
                                          !$e414.tid.isDescendantOf(
                                                       $currentTid415))
                                        throw $e414;
                                    throw new fabric.worker.UserAbortException(
                                            $e414);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e414) {
                                    $commit411 = false;
                                    $currentTid415 = $tm416.getCurrentTid();
                                    if ($currentTid415 != null) {
                                        if ($e414.tid.equals($currentTid415) ||
                                              !$e414.tid.isDescendantOf(
                                                           $currentTid415)) {
                                            throw $e414;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm416.inNestedTxn() &&
                                      $tm416.checkForStaleObjects()) {
                                    $retry412 = true;
                                    $keepReads413 = false;
                                }
                                if ($keepReads413) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e414) {
                                        $currentTid415 = $tm416.getCurrentTid();
                                        if ($currentTid415 != null &&
                                              ($e414.tid.equals($currentTid415) || !$e414.tid.isDescendantOf($currentTid415))) {
                                            throw $e414;
                                        } else {
                                            $retry412 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit411) {
                                { treaty = treaty$var409; }
                                if ($retry412) { continue $label410; }
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
                    fabric.worker.transaction.TransactionManager $tm426 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled429 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff427 = 1;
                    boolean $doBackoff428 = true;
                    boolean $retry422 = true;
                    boolean $keepReads423 = false;
                    $label420: for (boolean $commit421 = false; !$commit421; ) {
                        if ($backoffEnabled429) {
                            if ($doBackoff428) {
                                if ($backoff427 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff427));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e424) {
                                            
                                        }
                                    }
                                }
                                if ($backoff427 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff427 =
                                      java.lang.Math.
                                        min(
                                          $backoff427 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff428 = $backoff427 <= 32 || !$doBackoff428;
                        }
                        $commit421 = true;
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
                        catch (final fabric.worker.RetryException $e424) {
                            $commit421 = false;
                            continue $label420;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e424) {
                            $commit421 = false;
                            $retry422 = false;
                            $keepReads423 = $e424.keepReads;
                            fabric.common.TransactionID $currentTid425 =
                              $tm426.getCurrentTid();
                            if ($e424.tid == null ||
                                  !$e424.tid.isDescendantOf($currentTid425)) {
                                throw $e424;
                            }
                            throw new fabric.worker.UserAbortException($e424);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e424) {
                            $commit421 = false;
                            fabric.common.TransactionID $currentTid425 =
                              $tm426.getCurrentTid();
                            if ($e424.tid.isDescendantOf($currentTid425))
                                continue $label420;
                            if ($currentTid425.parent != null) {
                                $retry422 = false;
                                throw $e424;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e424) {
                            $commit421 = false;
                            $retry422 = false;
                            if ($tm426.inNestedTxn()) { $keepReads423 = true; }
                            throw $e424;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid425 =
                              $tm426.getCurrentTid();
                            if ($commit421) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e424) {
                                    $commit421 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e424) {
                                    $commit421 = false;
                                    $retry422 = false;
                                    $keepReads423 = $e424.keepReads;
                                    if ($e424.tid ==
                                          null ||
                                          !$e424.tid.isDescendantOf(
                                                       $currentTid425))
                                        throw $e424;
                                    throw new fabric.worker.UserAbortException(
                                            $e424);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e424) {
                                    $commit421 = false;
                                    $currentTid425 = $tm426.getCurrentTid();
                                    if ($currentTid425 != null) {
                                        if ($e424.tid.equals($currentTid425) ||
                                              !$e424.tid.isDescendantOf(
                                                           $currentTid425)) {
                                            throw $e424;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm426.inNestedTxn() &&
                                      $tm426.checkForStaleObjects()) {
                                    $retry422 = true;
                                    $keepReads423 = false;
                                }
                                if ($keepReads423) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e424) {
                                        $currentTid425 = $tm426.getCurrentTid();
                                        if ($currentTid425 != null &&
                                              ($e424.tid.equals($currentTid425) || !$e424.tid.isDescendantOf($currentTid425))) {
                                            throw $e424;
                                        } else {
                                            $retry422 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit421) {
                                {  }
                                if ($retry422) { continue $label420; }
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
                fabric.worker.transaction.TransactionManager $tm436 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled439 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff437 = 1;
                boolean $doBackoff438 = true;
                boolean $retry432 = true;
                boolean $keepReads433 = false;
                $label430: for (boolean $commit431 = false; !$commit431; ) {
                    if ($backoffEnabled439) {
                        if ($doBackoff438) {
                            if ($backoff437 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff437));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e434) {
                                        
                                    }
                                }
                            }
                            if ($backoff437 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff437 =
                                  java.lang.Math.
                                    min(
                                      $backoff437 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff438 = $backoff437 <= 32 || !$doBackoff438;
                    }
                    $commit431 = true;
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
                    catch (final fabric.worker.RetryException $e434) {
                        $commit431 = false;
                        continue $label430;
                    }
                    catch (fabric.worker.TransactionAbortingException $e434) {
                        $commit431 = false;
                        $retry432 = false;
                        $keepReads433 = $e434.keepReads;
                        fabric.common.TransactionID $currentTid435 =
                          $tm436.getCurrentTid();
                        if ($e434.tid == null ||
                              !$e434.tid.isDescendantOf($currentTid435)) {
                            throw $e434;
                        }
                        throw new fabric.worker.UserAbortException($e434);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e434) {
                        $commit431 = false;
                        fabric.common.TransactionID $currentTid435 =
                          $tm436.getCurrentTid();
                        if ($e434.tid.isDescendantOf($currentTid435))
                            continue $label430;
                        if ($currentTid435.parent != null) {
                            $retry432 = false;
                            throw $e434;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e434) {
                        $commit431 = false;
                        $retry432 = false;
                        if ($tm436.inNestedTxn()) { $keepReads433 = true; }
                        throw $e434;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid435 =
                          $tm436.getCurrentTid();
                        if ($commit431) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e434) {
                                $commit431 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e434) {
                                $commit431 = false;
                                $retry432 = false;
                                $keepReads433 = $e434.keepReads;
                                if ($e434.tid == null ||
                                      !$e434.tid.isDescendantOf($currentTid435))
                                    throw $e434;
                                throw new fabric.worker.UserAbortException(
                                        $e434);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e434) {
                                $commit431 = false;
                                $currentTid435 = $tm436.getCurrentTid();
                                if ($currentTid435 != null) {
                                    if ($e434.tid.equals($currentTid435) ||
                                          !$e434.tid.isDescendantOf(
                                                       $currentTid435)) {
                                        throw $e434;
                                    }
                                }
                            }
                        } else {
                            if (!$tm436.inNestedTxn() &&
                                  $tm436.checkForStaleObjects()) {
                                $retry432 = true;
                                $keepReads433 = false;
                            }
                            if ($keepReads433) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e434) {
                                    $currentTid435 = $tm436.getCurrentTid();
                                    if ($currentTid435 !=
                                          null &&
                                          ($e434.tid.equals($currentTid435) ||
                                             !$e434.tid.isDescendantOf($currentTid435))) {
                                        throw $e434;
                                    } else {
                                        $retry432 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit431) {
                            {  }
                            if ($retry432) { continue $label430; }
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
                fabric.worker.metrics.StatsMap weakStats$var440 = weakStats;
                fabric.worker.transaction.TransactionManager $tm447 =
                  fabric.worker.transaction.TransactionManager.getInstance();
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
                                                round(java.lang.Math.random() *
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
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff449 = $backoff448 <= 32 || !$doBackoff449;
                    }
                    $commit442 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (newPolicy.equals(tmp.get$policy())) return; }
                    catch (final fabric.worker.RetryException $e445) {
                        $commit442 = false;
                        continue $label441;
                    }
                    catch (fabric.worker.TransactionAbortingException $e445) {
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
                            catch (final fabric.worker.AbortException $e445) {
                                $commit442 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e445) {
                                $commit442 = false;
                                $retry443 = false;
                                $keepReads444 = $e445.keepReads;
                                if ($e445.tid == null ||
                                      !$e445.tid.isDescendantOf($currentTid446))
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
                                    if ($currentTid446 !=
                                          null &&
                                          ($e445.tid.equals($currentTid446) ||
                                             !$e445.tid.isDescendantOf($currentTid446))) {
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
                            { weakStats = weakStats$var440; }
                            if ($retry443) { continue $label441; }
                        }
                    }
                }
            }
            newPolicy.activate(weakStats);
            {
                fabric.worker.metrics.StatsMap weakStats$var451 = weakStats;
                fabric.worker.transaction.TransactionManager $tm458 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled461 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff459 = 1;
                boolean $doBackoff460 = true;
                boolean $retry454 = true;
                boolean $keepReads455 = false;
                $label452: for (boolean $commit453 = false; !$commit453; ) {
                    if ($backoffEnabled461) {
                        if ($doBackoff460) {
                            if ($backoff459 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff459));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e456) {
                                        
                                    }
                                }
                            }
                            if ($backoff459 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff459 =
                                  java.lang.Math.
                                    min(
                                      $backoff459 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff460 = $backoff459 <= 32 || !$doBackoff460;
                    }
                    $commit453 = true;
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
                    catch (final fabric.worker.RetryException $e456) {
                        $commit453 = false;
                        continue $label452;
                    }
                    catch (fabric.worker.TransactionAbortingException $e456) {
                        $commit453 = false;
                        $retry454 = false;
                        $keepReads455 = $e456.keepReads;
                        fabric.common.TransactionID $currentTid457 =
                          $tm458.getCurrentTid();
                        if ($e456.tid == null ||
                              !$e456.tid.isDescendantOf($currentTid457)) {
                            throw $e456;
                        }
                        throw new fabric.worker.UserAbortException($e456);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e456) {
                        $commit453 = false;
                        fabric.common.TransactionID $currentTid457 =
                          $tm458.getCurrentTid();
                        if ($e456.tid.isDescendantOf($currentTid457))
                            continue $label452;
                        if ($currentTid457.parent != null) {
                            $retry454 = false;
                            throw $e456;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e456) {
                        $commit453 = false;
                        $retry454 = false;
                        if ($tm458.inNestedTxn()) { $keepReads455 = true; }
                        throw $e456;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid457 =
                          $tm458.getCurrentTid();
                        if ($commit453) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e456) {
                                $commit453 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e456) {
                                $commit453 = false;
                                $retry454 = false;
                                $keepReads455 = $e456.keepReads;
                                if ($e456.tid == null ||
                                      !$e456.tid.isDescendantOf($currentTid457))
                                    throw $e456;
                                throw new fabric.worker.UserAbortException(
                                        $e456);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e456) {
                                $commit453 = false;
                                $currentTid457 = $tm458.getCurrentTid();
                                if ($currentTid457 != null) {
                                    if ($e456.tid.equals($currentTid457) ||
                                          !$e456.tid.isDescendantOf(
                                                       $currentTid457)) {
                                        throw $e456;
                                    }
                                }
                            }
                        } else {
                            if (!$tm458.inNestedTxn() &&
                                  $tm458.checkForStaleObjects()) {
                                $retry454 = true;
                                $keepReads455 = false;
                            }
                            if ($keepReads455) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e456) {
                                    $currentTid457 = $tm458.getCurrentTid();
                                    if ($currentTid457 !=
                                          null &&
                                          ($e456.tid.equals($currentTid457) ||
                                             !$e456.tid.isDescendantOf($currentTid457))) {
                                        throw $e456;
                                    } else {
                                        $retry454 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit453) {
                            { weakStats = weakStats$var451; }
                            if ($retry454) { continue $label452; }
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
                        fabric.worker.transaction.TransactionManager $tm468 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled471 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff469 = 1;
                        boolean $doBackoff470 = true;
                        boolean $retry464 = true;
                        boolean $keepReads465 = false;
                        $label462: for (boolean $commit463 = false; !$commit463;
                                        ) {
                            if ($backoffEnabled471) {
                                if ($doBackoff470) {
                                    if ($backoff469 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff469));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e466) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff469 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff469 =
                                          java.lang.Math.
                                            min(
                                              $backoff469 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff470 = $backoff469 <= 32 ||
                                                  !$doBackoff470;
                            }
                            $commit463 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.treaties.Treaty._Static._Proxy.
                                  $instance.
                                  set$UPDATE_THRESHOLD((long) 200);
                            }
                            catch (final fabric.worker.RetryException $e466) {
                                $commit463 = false;
                                continue $label462;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e466) {
                                $commit463 = false;
                                $retry464 = false;
                                $keepReads465 = $e466.keepReads;
                                fabric.common.TransactionID $currentTid467 =
                                  $tm468.getCurrentTid();
                                if ($e466.tid ==
                                      null ||
                                      !$e466.tid.isDescendantOf(
                                                   $currentTid467)) {
                                    throw $e466;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e466);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e466) {
                                $commit463 = false;
                                fabric.common.TransactionID $currentTid467 =
                                  $tm468.getCurrentTid();
                                if ($e466.tid.isDescendantOf($currentTid467))
                                    continue $label462;
                                if ($currentTid467.parent != null) {
                                    $retry464 = false;
                                    throw $e466;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e466) {
                                $commit463 = false;
                                $retry464 = false;
                                if ($tm468.inNestedTxn()) {
                                    $keepReads465 = true;
                                }
                                throw $e466;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid467 =
                                  $tm468.getCurrentTid();
                                if ($commit463) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e466) {
                                        $commit463 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e466) {
                                        $commit463 = false;
                                        $retry464 = false;
                                        $keepReads465 = $e466.keepReads;
                                        if ($e466.tid ==
                                              null ||
                                              !$e466.tid.isDescendantOf(
                                                           $currentTid467))
                                            throw $e466;
                                        throw new fabric.worker.
                                                UserAbortException($e466);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e466) {
                                        $commit463 = false;
                                        $currentTid467 = $tm468.getCurrentTid();
                                        if ($currentTid467 != null) {
                                            if ($e466.tid.equals(
                                                            $currentTid467) ||
                                                  !$e466.tid.
                                                  isDescendantOf(
                                                    $currentTid467)) {
                                                throw $e466;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm468.inNestedTxn() &&
                                          $tm468.checkForStaleObjects()) {
                                        $retry464 = true;
                                        $keepReads465 = false;
                                    }
                                    if ($keepReads465) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e466) {
                                            $currentTid467 = $tm468.getCurrentTid();
                                            if ($currentTid467 != null &&
                                                  ($e466.tid.equals($currentTid467) || !$e466.tid.isDescendantOf($currentTid467))) {
                                                throw $e466;
                                            } else {
                                                $retry464 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit463) {
                                    {  }
                                    if ($retry464) { continue $label462; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 21, -60, 48, 103, -45,
    104, 115, -91, 30, -75, -10, -48, 101, -73, -11, 47, -18, -15, -51, -124,
    -26, -112, 24, -123, -26, 72, 18, 39, -64, 7, 36, 16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZD2wT1xl/dhInDoGEhPAnhCSEQAsFG9KqEw1FQAgkw5AoIUwLK+F8fnaOnO/cu+fEKWXrtlJY1yG2BkanFm0aqB1N6USFOm1lYhrd2pWxbu3+Vu3QtHatGJtQRdmkrt33vXv22c7ZxNUW5d73fO993/v+/t67u4mrpMQ0SHNYCiqqj43FqOnbJAW7Aj2SYdJQuyqZ5na4OyhPK+46+u6ToQY3cQdIhSxpuqbIkjqomYzMCOyRRiS/Rpm/v7erbSfxysjYKZlDjLh3bkgYpCmmq2MRVWdikUnyj9zmH//mrqozRaRygFQqWh+TmCK36xqjCTZAKqI0GqSGuT4UoqEBMlOjNNRHDUVSlftgoq4NkGpTiWgSixvU7KWmro7gxGozHqMGXzN5E9XXQW0jLjPdAPWrLPXjTFH9AcVkbQHiCStUDZn3ks+T4gApCatSBCbODiSt8HOJ/k14H6aXK6CmEZZkmmQpHla0ECON2Rwpi1u2wARgLY1SNqSnlirWJLhBqi2VVEmL+PuYoWgRmFqix2EVRupyCoVJZTFJHpYidJCRudnzeqwhmOXlbkEWRmqzp3FJELO6rJilRevqtjWH9mqdmpu4QOcQlVXUvwyYGrKYemmYGlSTqcVYsSxwVJp97qCbEJhcmzXZmvP8/dfWLW84/5I1Z77DnO7gHiqzQflEcMav69uXri5CNcpiuqlgKmRYzqPaI0baEjHI9tkpiTjoSw6e7/3ZZx84Ra+4SXkX8ci6Go9CVs2U9WhMUamxmWrUkBgNdREv1ULtfLyLlEI/oGjUutsdDpuUdZFild/y6Pw3uCgMItBFpdBXtLCe7MckNsT7iRghpBQu4oJ/k5Db90N/PiFFE4z0+of0KPUH1TgdhfT2w0UlQx7yQ90airxC1mNjftOQ/UZcYwrMtO77IZWAmH5mUCgSDC12xnygTez/IjWBtlSNulzg5kZZD9GgZELMRP5s6FGhRDp1NUSNQVk9dK6L1Jx7jOeQF/PehNzlXnJB3OuzESOddzy+oePa6cFXrPxDXuFERhosLX1CS19SS5+lJShWgbXlA7TyAVpNuBK+9uNdT/MU8pi81lKyKkDWXTFVYmHdiCaIy8UNm8X5ee5A5IcBUQA0Kpb23fPp3QebiyBpY6PFGEeY2pJdQjbwdEFPgroYlCsPvPvBs0f36XYxMdIyqcYnc2KNNmd7ydBlGgIMtMUva5LODp7b1+JGfPEC9DEJkhNwpCF7jYxabUviHnqjJECmoQ8kFYeSYFXOhgx91L7Doz8Dm2orEdBZWQpyyLy7L/bEHy69dzvfTJLoWpkGw32UtaVVNAqr5LU70/Y9xJPCvDeP9Tx65OqBndzxMGOR04It2LZDJUtQwrqx/6V7//jnt0687raDxYgnFg+qipzgtsz8GP5ccH2EF5Yl3kAK4NwuIKEphQkxXHmJrRuggwoIBaqbLf1aVA8pYUUKqhQz5cPKxavO/v1QlRVuFe5YzjPI8psLsO/P20AeeGXXjQYuxiXj7mT7z55mQV6NLXm9YUhjqEfii79Z8NjPpScg8wGwTOU+yjGIcH8QHsBW7osVvF2VNXYHNs2Wt+r5/WJzMvxvwn3UzsUB/8Tjde1rr1g1n8pFlLHQoeZ3SGll0noqet3d7HnRTUoHSBXfwiWN7ZAAviANBmATNtvFzQCZnjGeuaFau0dbqtbqs+sgbdnsKrCxBvo4G/vlVuJbiQOOqEQnLbCu4hKLFt3A0ZoYtrMSLsI7d3GWRbxdgs1S7kg3dpcx4lWi0TjDsPMFboMcFaiGP2th687Cuq2c4mCdVX/Y3pmp11K4GkAvv6C1DnptyKcXNmuTCnljBg3BQZDRpE6rhU6jujFMjckwDH5lNEo1lkTkvtQNlDAvG3WdDKlAQxbD1QgG9Au6xsGQLc6GuLjiOoM0oKFESqwbxU4T4toEbU0TiyChA0aMJY1dcxNjKWz1hsyt83XY/R5byJTsnY2KtcL1bUJqnxH06w727nC2t0gklMfkp+qMCFb192xcv71jcHtnb0dfZ3dgo0MV9xhKFIB4RBzi6MHxhz/2HRq3EMw66S6adNhM57FOu3zJ6XzdBKyyMN8qnGPT357d96On9h2wToLVmee2Di0efeZ3/7noO3b5ZYdzQLGqWzt5VSJHEnCn2NHnfx5x6Hpa0JNpTk6DOlcyAeqzKpBr1h00qTFiwVodWrog10maW3niS+PHQ90nV7kFsu6G7GR6bIVKR6iatuhM9NmkJ7Wt/PnBhsnLVxasbh9+O2L5rDFr5ezZ39s68fLmJfI33KQohYeTHloymdoyUbDcoPDMpW3PwMKmlFe96NVtcN1CSMkg0CVQU2+kp66d8IuwCWWWY5lg+ZOgr2cHxN6diq1dCH+uw2YzNr3JSDU4lyqij7lVijkXI1cunmcX3IsNZFujJb1FiG1JIkCLhXEttpHRlH1VKGUXXOvAzPsF9eVwTW4sHsgCsEohaYWgTbk9VmSjg+2xEb7sl/MYvR+bfZClGh217HMyj0d+Llw7AFTrBZ1eWOSRpUJQT2470pV7JM/YIWwOMFIyIqmKtdznBBwhgWfm0qCuq1TSnGypg2sPgX1e0GBhtiCLJOjOqdlyLM/Yt7B5FDRWNG4N/jycpXZF8iAyCtm2V9A9edTeNnkvRBZF0N1TU/s7eca+i83jjJSHqAQH1JHkySErDsUjugiQbU0NCrkDrq8SUv1PoA8DPVBIvXQ71Uu1kPSQoCOfqF5O57H5+9g8BYdQqJf1wuiQdQawIcrBziOEzFoNdBzu/ep/YidKuiTojz+RnT/IY+cPsTnDyCyws1+Tbm4pz8874XoSzjjPCfqVwvITWQ4K+oXcJrnsPf8wl/qTPIb8FJsXGKmJx0JgwWcUNtQeN2w7HOtsDVwvAEpsFHRWYXYgS42g5VOyYxeX+os8dlzE5kVG5hs0Ak98FExgcPxUJLUjAR0THy9xykknuMP94yIh9UzQnsLgDlm6Be2aGm68lmfst9hcYmT6kKSFVNrPA2MmN/dlzpt7V/L5KXkigwdF543eyQGL4Po3Ic0PCpoPOB0cgCyKoPLUHHA5z9hfsHkDcjIoycMRQ49roVQQc8YQTkwuSKrFpwT9WkEmcJZHBH2ogNp6L48dV7D5KxweDBqUVDxQOmnPz0YrQagPnlg/EvStQjAw19mIS3pT0FenZFQVX+z9PEZdx+YqI5UpowYlc0yTcwZmIYiGB1d/q6B1hQUGWeYJWnNTG5JVUpNZJX1MN2iec++HuQ12cRffYKQsQlmPoSdynwBvBQXg3L/yHkHXFmYpstwt6KemFK0IV9CbR/lp2BQzMsOgUX2Epj+uOQYLEN0VJmTVFkGrCjMBWSoF9U4JCFzVecZwX3FNh1wD1weoFO6L8/dZKSxcfhMstF4SmTso//w1ZTSE068rRkjrBUGfK8wJyHJG0ImpOaExz9hCbOog/5hufR9LWl/F33Tiez5f2sDkBGfEYz264OvR+Q6fKsRnNLn9Aj3x9pbltTk+U8yd9GFT8J0+Xlk253j/7/lr99QnMm+AlIXjqpr+GjGt74kZNKxwd3qtl4oxbu4yRubk+K6BPhBdNNV1q8WxAtI7k4Pxb43YS5+3EhxhzcNfq3hQ6uwm6dZFTu851gdNZsAxT2QgZ+Ba18UN/O478f6cf3nKtl/mr9khck21F1ZGXhsyTzac/eBV+vx1/z+u/fLBdw7P3f9OZ/Ut50tbqv4LSR/H5o8eAAA=";
}
