package fabric.metrics.treaties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.SortedSet;
import fabric.metrics.Metric;
import fabric.metrics.DerivedMetric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.NoPolicy;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;

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
   * Handles an update to some dependency of the current policy for this treaty.
   *
   * @return the observers of this treaty if there was a retraction.
  /*@Override*/
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
    /**
   * Run a background extension.  This should only result in extending the
   * expiry using the existing policy or waiting further to extend.
   *
   * TODO: Should this be passed a weak read of relevant stats?
   */
    public void backgroundExtension();
    
    /**
   * Force a shift to a new policy.
   */
    public void rebalance();
    
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            return ((fabric.metrics.treaties.Treaty) fetch()).handleUpdates();
        }
        
        public void backgroundExtension() {
            ((fabric.metrics.treaties.Treaty) fetch()).backgroundExtension();
        }
        
        public void rebalance() {
            ((fabric.metrics.treaties.Treaty) fetch()).rebalance();
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
        
        private fabric.worker.metrics.treaties.enforcement.EnforcementPolicy policy;
        
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
            this.set$$expiry(
                   (long)
                     policy.calculateExpiry((fabric.metrics.treaties.Treaty)
                                              this.$getProxy(), weakStats));
            if (valid()) {
                this.set$policy(policy);
                policy.
                  shiftPolicies(
                    (fabric.metrics.treaties.Treaty) this.$getProxy(),
                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                      singleton);
            } else {
                ((fabric.metrics.treaties.Treaty._Impl)
                   this.fetch()).deactivate();
            }
            return (fabric.metrics.treaties.Treaty) this.$getProxy();
        }
        
        /**
   * Perform a not-necessarily-atomic creation of an active treaty.
   *
   * TODO: maybe move this interface into Metric?
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
                    fabric.metrics.treaties.Treaty treaty$var484 = treaty;
                    fabric.worker.transaction.TransactionManager $tm491 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled494 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff492 = 1;
                    boolean $doBackoff493 = true;
                    boolean $retry487 = true;
                    boolean $keepReads488 = false;
                    $label485: for (boolean $commit486 = false; !$commit486; ) {
                        if ($backoffEnabled494) {
                            if ($doBackoff493) {
                                if ($backoff492 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff492));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e489) {
                                            
                                        }
                                    }
                                }
                                if ($backoff492 < 5000) $backoff492 *= 2;
                            }
                            $doBackoff493 = $backoff492 <= 32 || !$doBackoff493;
                        }
                        $commit486 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                treaty =
                                  ((fabric.metrics.treaties.Treaty)
                                     new fabric.metrics.treaties.Treaty._Impl(
                                       s).$getProxy()).
                                    fabric$metrics$treaties$Treaty$(metric,
                                                                    predicate,
                                                                    policy,
                                                                    weakStats);
                            }
                            catch (final fabric.worker.RetryException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e489) {
                                throw $e489;
                            }
                            catch (final Throwable $e489) {
                                $tm491.getCurrentLog().checkRetrySignal();
                                throw $e489;
                            }
                        }
                        catch (final fabric.worker.RetryException $e489) {
                            $commit486 = false;
                            continue $label485;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e489) {
                            $commit486 = false;
                            $retry487 = false;
                            $keepReads488 = $e489.keepReads;
                            if ($tm491.checkForStaleObjects()) {
                                $retry487 = true;
                                $keepReads488 = false;
                                continue $label485;
                            }
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid == null ||
                                  !$e489.tid.isDescendantOf($currentTid490)) {
                                throw $e489;
                            }
                            throw new fabric.worker.UserAbortException($e489);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e489) {
                            $commit486 = false;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490))
                                continue $label485;
                            if ($currentTid490.parent != null) {
                                $retry487 = false;
                                throw $e489;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e489) {
                            $commit486 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label485;
                            $retry487 = false;
                            throw new fabric.worker.AbortException($e489);
                        }
                        finally {
                            if ($commit486) {
                                fabric.common.TransactionID $currentTid490 =
                                  $tm491.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e489) {
                                    $commit486 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e489) {
                                    $commit486 = false;
                                    $retry487 = false;
                                    $keepReads488 = $e489.keepReads;
                                    if ($tm491.checkForStaleObjects()) {
                                        $retry487 = true;
                                        $keepReads488 = false;
                                        continue $label485;
                                    }
                                    if ($e489.tid ==
                                          null ||
                                          !$e489.tid.isDescendantOf(
                                                       $currentTid490))
                                        throw $e489;
                                    throw new fabric.worker.UserAbortException(
                                            $e489);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e489) {
                                    $commit486 = false;
                                    $currentTid490 = $tm491.getCurrentTid();
                                    if ($currentTid490 != null) {
                                        if ($e489.tid.equals($currentTid490) ||
                                              !$e489.tid.isDescendantOf(
                                                           $currentTid490)) {
                                            throw $e489;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads488) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit486) {
                                { treaty = treaty$var484; }
                                if ($retry487) { continue $label485; }
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
   * Create and activate a new enforcement policy.
   */
        private static fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
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
   * Handles an update to some dependency of the current policy for this treaty.
   *
   * @return the observers of this treaty if there was a retraction.
  /*@Override*/
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            long oldExpiry = this.get$$expiry();
            long updatedCurExpiry =
              this.get$policy().updatedExpiry(
                                  (fabric.metrics.treaties.Treaty)
                                    this.$getProxy(),
                                  fabric.worker.metrics.StatsMap.emptyStats());
            if (updatedCurExpiry > java.lang.System.currentTimeMillis()) {
                if (updatedCurExpiry <
                      oldExpiry ||
                      this.get$$expiry() -
                      java.lang.System.currentTimeMillis() <=
                      fabric.metrics.treaties.Treaty._Static._Proxy.$instance.
                      get$UPDATE_THRESHOLD()) {
                    this.set$$expiry((long) updatedCurExpiry);
                }
                else {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerDelayedExtension((fabric.metrics.treaties.Treaty)
                                                 this.$getProxy(), 0);
                }
            }
            else {
                fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
                  oldPolicy = this.get$policy();
                fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
                  newPolicy =
                  fabric.metrics.treaties.Treaty._Impl.
                  newActivatedPolicy(
                    this.get$metric(), this.get$predicate(),
                    fabric.worker.metrics.StatsMap.emptyStats());
                long newPolicyExpiry =
                  newPolicy.calculateExpiry(
                              (fabric.metrics.treaties.Treaty) this.$getProxy(),
                              fabric.worker.metrics.StatsMap.emptyStats());
                this.set$$expiry((long) newPolicyExpiry);
                if (valid()) {
                    this.set$policy(newPolicy);
                    newPolicy.shiftPolicies((fabric.metrics.treaties.Treaty)
                                              this.$getProxy(), oldPolicy);
                } else {
                    ((fabric.metrics.treaties.Treaty._Impl)
                       this.fetch()).deactivate();
                }
            }
            if (this.get$$expiry() < oldExpiry) {
                return getObservers();
            } else {
                return fabric.worker.metrics.ImmutableObserverSet.emptySet();
            }
        }
        
        /**
   * Run a background extension.  This should only result in extending the
   * expiry using the existing policy or waiting further to extend.
   *
   * TODO: Should this be passed a weak read of relevant stats?
   */
        public void backgroundExtension() {
            long oldExpiry = this.get$$expiry();
            long updatedCurExpiry =
              this.get$policy().updatedExpiry(
                                  (fabric.metrics.treaties.Treaty)
                                    this.$getProxy(),
                                  fabric.worker.metrics.StatsMap.emptyStats());
            if (updatedCurExpiry > java.lang.System.currentTimeMillis()) {
                if (updatedCurExpiry < oldExpiry) {
                    throw new java.lang.InternalError(
                            "Somehow had a retraction during a background extension");
                }
                else if (updatedCurExpiry > oldExpiry && this.get$$expiry() -
                           java.lang.System.currentTimeMillis() <=
                           fabric.metrics.treaties.Treaty._Static._Proxy.$instance.get$UPDATE_THRESHOLD()) {
                    this.set$$expiry((long) updatedCurExpiry);
                }
                else {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerDelayedExtension((fabric.metrics.treaties.Treaty)
                                                 this.$getProxy(), 0);
                }
            }
        }
        
        /**
   * Force a shift to a new policy.
   */
        public void rebalance() {
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              oldPolicy = this.get$policy();
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy =
              fabric.metrics.treaties.Treaty._Impl.
              newActivatedPolicy(this.get$metric(), this.get$predicate(),
                                 fabric.worker.metrics.StatsMap.emptyStats());
            this.set$$expiry(
                   (long)
                     newPolicy.calculateExpiry(
                                 (fabric.metrics.treaties.Treaty)
                                   this.$getProxy(),
                                 fabric.worker.metrics.StatsMap.emptyStats()));
            this.set$policy(newPolicy);
            newPolicy.shiftPolicies((fabric.metrics.treaties.Treaty)
                                      this.$getProxy(), oldPolicy);
        }
        
        /**
   * Perform a "deactivation," a forced retraction to 0 and no continued
   * enforcement.
   */
        private void deactivate() {
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              oldPolicy = this.get$policy();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                this.set$$expiry((long) 0);
                this.
                  set$policy(
                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                      singleton);
                oldPolicy.unapply((fabric.metrics.treaties.Treaty)
                                    this.$getProxy());
                this.get$metric().get$treatiesBox().get$treaties().
                  remove((fabric.metrics.treaties.Treaty) this.$getProxy());
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm501 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled504 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff502 = 1;
                    boolean $doBackoff503 = true;
                    boolean $retry497 = true;
                    boolean $keepReads498 = false;
                    $label495: for (boolean $commit496 = false; !$commit496; ) {
                        if ($backoffEnabled504) {
                            if ($doBackoff503) {
                                if ($backoff502 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff502));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e499) {
                                            
                                        }
                                    }
                                }
                                if ($backoff502 < 5000) $backoff502 *= 2;
                            }
                            $doBackoff503 = $backoff502 <= 32 || !$doBackoff503;
                        }
                        $commit496 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.set$$expiry((long) 0);
                                this.
                                  set$policy(
                                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                                      singleton);
                            }
                            catch (final fabric.worker.RetryException $e499) {
                                throw $e499;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e499) {
                                throw $e499;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e499) {
                                throw $e499;
                            }
                            catch (final Throwable $e499) {
                                $tm501.getCurrentLog().checkRetrySignal();
                                throw $e499;
                            }
                        }
                        catch (final fabric.worker.RetryException $e499) {
                            $commit496 = false;
                            continue $label495;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e499) {
                            $commit496 = false;
                            $retry497 = false;
                            $keepReads498 = $e499.keepReads;
                            if ($tm501.checkForStaleObjects()) {
                                $retry497 = true;
                                $keepReads498 = false;
                                continue $label495;
                            }
                            fabric.common.TransactionID $currentTid500 =
                              $tm501.getCurrentTid();
                            if ($e499.tid == null ||
                                  !$e499.tid.isDescendantOf($currentTid500)) {
                                throw $e499;
                            }
                            throw new fabric.worker.UserAbortException($e499);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e499) {
                            $commit496 = false;
                            fabric.common.TransactionID $currentTid500 =
                              $tm501.getCurrentTid();
                            if ($e499.tid.isDescendantOf($currentTid500))
                                continue $label495;
                            if ($currentTid500.parent != null) {
                                $retry497 = false;
                                throw $e499;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e499) {
                            $commit496 = false;
                            if ($tm501.checkForStaleObjects())
                                continue $label495;
                            $retry497 = false;
                            throw new fabric.worker.AbortException($e499);
                        }
                        finally {
                            if ($commit496) {
                                fabric.common.TransactionID $currentTid500 =
                                  $tm501.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e499) {
                                    $commit496 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e499) {
                                    $commit496 = false;
                                    $retry497 = false;
                                    $keepReads498 = $e499.keepReads;
                                    if ($tm501.checkForStaleObjects()) {
                                        $retry497 = true;
                                        $keepReads498 = false;
                                        continue $label495;
                                    }
                                    if ($e499.tid ==
                                          null ||
                                          !$e499.tid.isDescendantOf(
                                                       $currentTid500))
                                        throw $e499;
                                    throw new fabric.worker.UserAbortException(
                                            $e499);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e499) {
                                    $commit496 = false;
                                    $currentTid500 = $tm501.getCurrentTid();
                                    if ($currentTid500 != null) {
                                        if ($e499.tid.equals($currentTid500) ||
                                              !$e499.tid.isDescendantOf(
                                                           $currentTid500)) {
                                            throw $e499;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads498) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit496) {
                                {  }
                                if ($retry497) { continue $label495; }
                            }
                        }
                    }
                }
                oldPolicy.unapply((fabric.metrics.treaties.Treaty)
                                    this.$getProxy());
                {
                    fabric.worker.transaction.TransactionManager $tm511 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled514 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff512 = 1;
                    boolean $doBackoff513 = true;
                    boolean $retry507 = true;
                    boolean $keepReads508 = false;
                    $label505: for (boolean $commit506 = false; !$commit506; ) {
                        if ($backoffEnabled514) {
                            if ($doBackoff513) {
                                if ($backoff512 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff512));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e509) {
                                            
                                        }
                                    }
                                }
                                if ($backoff512 < 5000) $backoff512 *= 2;
                            }
                            $doBackoff513 = $backoff512 <= 32 || !$doBackoff513;
                        }
                        $commit506 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.get$metric().get$treatiesBox().
                                  get$treaties().
                                  remove((fabric.metrics.treaties.Treaty)
                                           this.$getProxy());
                            }
                            catch (final fabric.worker.RetryException $e509) {
                                throw $e509;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e509) {
                                throw $e509;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e509) {
                                throw $e509;
                            }
                            catch (final Throwable $e509) {
                                $tm511.getCurrentLog().checkRetrySignal();
                                throw $e509;
                            }
                        }
                        catch (final fabric.worker.RetryException $e509) {
                            $commit506 = false;
                            continue $label505;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e509) {
                            $commit506 = false;
                            $retry507 = false;
                            $keepReads508 = $e509.keepReads;
                            if ($tm511.checkForStaleObjects()) {
                                $retry507 = true;
                                $keepReads508 = false;
                                continue $label505;
                            }
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid == null ||
                                  !$e509.tid.isDescendantOf($currentTid510)) {
                                throw $e509;
                            }
                            throw new fabric.worker.UserAbortException($e509);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e509) {
                            $commit506 = false;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510))
                                continue $label505;
                            if ($currentTid510.parent != null) {
                                $retry507 = false;
                                throw $e509;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e509) {
                            $commit506 = false;
                            if ($tm511.checkForStaleObjects())
                                continue $label505;
                            $retry507 = false;
                            throw new fabric.worker.AbortException($e509);
                        }
                        finally {
                            if ($commit506) {
                                fabric.common.TransactionID $currentTid510 =
                                  $tm511.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e509) {
                                    $commit506 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e509) {
                                    $commit506 = false;
                                    $retry507 = false;
                                    $keepReads508 = $e509.keepReads;
                                    if ($tm511.checkForStaleObjects()) {
                                        $retry507 = true;
                                        $keepReads508 = false;
                                        continue $label505;
                                    }
                                    if ($e509.tid ==
                                          null ||
                                          !$e509.tid.isDescendantOf(
                                                       $currentTid510))
                                        throw $e509;
                                    throw new fabric.worker.UserAbortException(
                                            $e509);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e509) {
                                    $commit506 = false;
                                    $currentTid510 = $tm511.getCurrentTid();
                                    if ($currentTid510 != null) {
                                        if ($e509.tid.equals($currentTid510) ||
                                              !$e509.tid.isDescendantOf(
                                                           $currentTid510)) {
                                            throw $e509;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads508) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit506) {
                                {  }
                                if ($retry507) { continue $label505; }
                            }
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
            if (!isObserved())
                ((fabric.metrics.treaties.Treaty._Impl) this.fetch()).
                  deactivate();
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
                        fabric.worker.transaction.TransactionManager $tm521 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled524 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff522 = 1;
                        boolean $doBackoff523 = true;
                        boolean $retry517 = true;
                        boolean $keepReads518 = false;
                        $label515: for (boolean $commit516 = false; !$commit516;
                                        ) {
                            if ($backoffEnabled524) {
                                if ($doBackoff523) {
                                    if ($backoff522 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff522));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e519) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff522 < 5000) $backoff522 *= 2;
                                }
                                $doBackoff523 = $backoff522 <= 32 ||
                                                  !$doBackoff523;
                            }
                            $commit516 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.metrics.treaties.Treaty._Static.
                                      _Proxy.
                                      $instance.
                                      set$UPDATE_THRESHOLD((long) 1000);
                                }
                                catch (final fabric.worker.
                                         RetryException $e519) {
                                    throw $e519;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e519) {
                                    throw $e519;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e519) {
                                    throw $e519;
                                }
                                catch (final Throwable $e519) {
                                    $tm521.getCurrentLog().checkRetrySignal();
                                    throw $e519;
                                }
                            }
                            catch (final fabric.worker.RetryException $e519) {
                                $commit516 = false;
                                continue $label515;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e519) {
                                $commit516 = false;
                                $retry517 = false;
                                $keepReads518 = $e519.keepReads;
                                if ($tm521.checkForStaleObjects()) {
                                    $retry517 = true;
                                    $keepReads518 = false;
                                    continue $label515;
                                }
                                fabric.common.TransactionID $currentTid520 =
                                  $tm521.getCurrentTid();
                                if ($e519.tid ==
                                      null ||
                                      !$e519.tid.isDescendantOf(
                                                   $currentTid520)) {
                                    throw $e519;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e519);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e519) {
                                $commit516 = false;
                                fabric.common.TransactionID $currentTid520 =
                                  $tm521.getCurrentTid();
                                if ($e519.tid.isDescendantOf($currentTid520))
                                    continue $label515;
                                if ($currentTid520.parent != null) {
                                    $retry517 = false;
                                    throw $e519;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e519) {
                                $commit516 = false;
                                if ($tm521.checkForStaleObjects())
                                    continue $label515;
                                $retry517 = false;
                                throw new fabric.worker.AbortException($e519);
                            }
                            finally {
                                if ($commit516) {
                                    fabric.common.TransactionID $currentTid520 =
                                      $tm521.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e519) {
                                        $commit516 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e519) {
                                        $commit516 = false;
                                        $retry517 = false;
                                        $keepReads518 = $e519.keepReads;
                                        if ($tm521.checkForStaleObjects()) {
                                            $retry517 = true;
                                            $keepReads518 = false;
                                            continue $label515;
                                        }
                                        if ($e519.tid ==
                                              null ||
                                              !$e519.tid.isDescendantOf(
                                                           $currentTid520))
                                            throw $e519;
                                        throw new fabric.worker.
                                                UserAbortException($e519);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e519) {
                                        $commit516 = false;
                                        $currentTid520 = $tm521.getCurrentTid();
                                        if ($currentTid520 != null) {
                                            if ($e519.tid.equals(
                                                            $currentTid520) ||
                                                  !$e519.tid.
                                                  isDescendantOf(
                                                    $currentTid520)) {
                                                throw $e519;
                                            }
                                        }
                                    }
                                } else if ($keepReads518) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit516) {
                                    {  }
                                    if ($retry517) { continue $label515; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 75, 89, -99, 115, 81,
    -17, -2, 109, -76, -102, -94, -93, 17, -24, 29, -108, 19, -106, 60, 37,
    -104, 37, 117, 42, 83, 14, 18, 87, -99, 97, 18, 10 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549236734000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
