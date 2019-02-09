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
import fabric.worker.metrics.ImmutableObjectSet;
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
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet(
                                                              ).add(metric));
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
                    fabric.metrics.treaties.Treaty treaty$var453 = treaty;
                    fabric.worker.transaction.TransactionManager $tm460 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled463 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff461 = 1;
                    boolean $doBackoff462 = true;
                    boolean $retry456 = true;
                    boolean $keepReads457 = false;
                    $label454: for (boolean $commit455 = false; !$commit455; ) {
                        if ($backoffEnabled463) {
                            if ($doBackoff462) {
                                if ($backoff461 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff461));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e458) {
                                            
                                        }
                                    }
                                }
                                if ($backoff461 < 5000) $backoff461 *= 2;
                            }
                            $doBackoff462 = $backoff461 <= 32 || !$doBackoff462;
                        }
                        $commit455 = true;
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
                            catch (final fabric.worker.RetryException $e458) {
                                throw $e458;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e458) {
                                throw $e458;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e458) {
                                throw $e458;
                            }
                            catch (final Throwable $e458) {
                                $tm460.getCurrentLog().checkRetrySignal();
                                throw $e458;
                            }
                        }
                        catch (final fabric.worker.RetryException $e458) {
                            $commit455 = false;
                            continue $label454;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e458) {
                            $commit455 = false;
                            $retry456 = false;
                            $keepReads457 = $e458.keepReads;
                            if ($tm460.checkForStaleObjects()) {
                                $retry456 = true;
                                $keepReads457 = false;
                                continue $label454;
                            }
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid == null ||
                                  !$e458.tid.isDescendantOf($currentTid459)) {
                                throw $e458;
                            }
                            throw new fabric.worker.UserAbortException($e458);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e458) {
                            $commit455 = false;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459))
                                continue $label454;
                            if ($currentTid459.parent != null) {
                                $retry456 = false;
                                throw $e458;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e458) {
                            $commit455 = false;
                            if ($tm460.checkForStaleObjects())
                                continue $label454;
                            $retry456 = false;
                            throw new fabric.worker.AbortException($e458);
                        }
                        finally {
                            if ($commit455) {
                                fabric.common.TransactionID $currentTid459 =
                                  $tm460.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e458) {
                                    $commit455 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e458) {
                                    $commit455 = false;
                                    $retry456 = false;
                                    $keepReads457 = $e458.keepReads;
                                    if ($tm460.checkForStaleObjects()) {
                                        $retry456 = true;
                                        $keepReads457 = false;
                                        continue $label454;
                                    }
                                    if ($e458.tid ==
                                          null ||
                                          !$e458.tid.isDescendantOf(
                                                       $currentTid459))
                                        throw $e458;
                                    throw new fabric.worker.UserAbortException(
                                            $e458);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e458) {
                                    $commit455 = false;
                                    $currentTid459 = $tm460.getCurrentTid();
                                    if ($currentTid459 != null) {
                                        if ($e458.tid.equals($currentTid459) ||
                                              !$e458.tid.isDescendantOf(
                                                           $currentTid459)) {
                                            throw $e458;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads457) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit455) {
                                { treaty = treaty$var453; }
                                if ($retry456) { continue $label454; }
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
                                                 this.$getProxy());
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
                                                 this.$getProxy());
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
                this.get$metric().get$treatiesBox().
                  remove((fabric.metrics.treaties.Treaty) this.$getProxy());
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm470 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled473 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff471 = 1;
                    boolean $doBackoff472 = true;
                    boolean $retry466 = true;
                    boolean $keepReads467 = false;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled473) {
                            if ($doBackoff472) {
                                if ($backoff471 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff471));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e468) {
                                            
                                        }
                                    }
                                }
                                if ($backoff471 < 5000) $backoff471 *= 2;
                            }
                            $doBackoff472 = $backoff471 <= 32 || !$doBackoff472;
                        }
                        $commit465 = true;
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
                            catch (final fabric.worker.RetryException $e468) {
                                throw $e468;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e468) {
                                throw $e468;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e468) {
                                throw $e468;
                            }
                            catch (final Throwable $e468) {
                                $tm470.getCurrentLog().checkRetrySignal();
                                throw $e468;
                            }
                        }
                        catch (final fabric.worker.RetryException $e468) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e468) {
                            $commit465 = false;
                            $retry466 = false;
                            $keepReads467 = $e468.keepReads;
                            if ($tm470.checkForStaleObjects()) {
                                $retry466 = true;
                                $keepReads467 = false;
                                continue $label464;
                            }
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid == null ||
                                  !$e468.tid.isDescendantOf($currentTid469)) {
                                throw $e468;
                            }
                            throw new fabric.worker.UserAbortException($e468);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e468) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469))
                                continue $label464;
                            if ($currentTid469.parent != null) {
                                $retry466 = false;
                                throw $e468;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e468) {
                            $commit465 = false;
                            if ($tm470.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e468);
                        }
                        finally {
                            if ($commit465) {
                                fabric.common.TransactionID $currentTid469 =
                                  $tm470.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e468) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e468) {
                                    $commit465 = false;
                                    $retry466 = false;
                                    $keepReads467 = $e468.keepReads;
                                    if ($tm470.checkForStaleObjects()) {
                                        $retry466 = true;
                                        $keepReads467 = false;
                                        continue $label464;
                                    }
                                    if ($e468.tid ==
                                          null ||
                                          !$e468.tid.isDescendantOf(
                                                       $currentTid469))
                                        throw $e468;
                                    throw new fabric.worker.UserAbortException(
                                            $e468);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e468) {
                                    $commit465 = false;
                                    $currentTid469 = $tm470.getCurrentTid();
                                    if ($currentTid469 != null) {
                                        if ($e468.tid.equals($currentTid469) ||
                                              !$e468.tid.isDescendantOf(
                                                           $currentTid469)) {
                                            throw $e468;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads467) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465) {
                                {  }
                                if ($retry466) { continue $label464; }
                            }
                        }
                    }
                }
                oldPolicy.unapply((fabric.metrics.treaties.Treaty)
                                    this.$getProxy());
                {
                    fabric.worker.transaction.TransactionManager $tm480 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled483 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff481 = 1;
                    boolean $doBackoff482 = true;
                    boolean $retry476 = true;
                    boolean $keepReads477 = false;
                    $label474: for (boolean $commit475 = false; !$commit475; ) {
                        if ($backoffEnabled483) {
                            if ($doBackoff482) {
                                if ($backoff481 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff481));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e478) {
                                            
                                        }
                                    }
                                }
                                if ($backoff481 < 5000) $backoff481 *= 2;
                            }
                            $doBackoff482 = $backoff481 <= 32 || !$doBackoff482;
                        }
                        $commit475 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.get$metric().get$treatiesBox().
                                  remove((fabric.metrics.treaties.Treaty)
                                           this.$getProxy());
                            }
                            catch (final fabric.worker.RetryException $e478) {
                                throw $e478;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e478) {
                                throw $e478;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e478) {
                                throw $e478;
                            }
                            catch (final Throwable $e478) {
                                $tm480.getCurrentLog().checkRetrySignal();
                                throw $e478;
                            }
                        }
                        catch (final fabric.worker.RetryException $e478) {
                            $commit475 = false;
                            continue $label474;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e478) {
                            $commit475 = false;
                            $retry476 = false;
                            $keepReads477 = $e478.keepReads;
                            if ($tm480.checkForStaleObjects()) {
                                $retry476 = true;
                                $keepReads477 = false;
                                continue $label474;
                            }
                            fabric.common.TransactionID $currentTid479 =
                              $tm480.getCurrentTid();
                            if ($e478.tid == null ||
                                  !$e478.tid.isDescendantOf($currentTid479)) {
                                throw $e478;
                            }
                            throw new fabric.worker.UserAbortException($e478);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e478) {
                            $commit475 = false;
                            fabric.common.TransactionID $currentTid479 =
                              $tm480.getCurrentTid();
                            if ($e478.tid.isDescendantOf($currentTid479))
                                continue $label474;
                            if ($currentTid479.parent != null) {
                                $retry476 = false;
                                throw $e478;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e478) {
                            $commit475 = false;
                            if ($tm480.checkForStaleObjects())
                                continue $label474;
                            $retry476 = false;
                            throw new fabric.worker.AbortException($e478);
                        }
                        finally {
                            if ($commit475) {
                                fabric.common.TransactionID $currentTid479 =
                                  $tm480.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e478) {
                                    $commit475 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e478) {
                                    $commit475 = false;
                                    $retry476 = false;
                                    $keepReads477 = $e478.keepReads;
                                    if ($tm480.checkForStaleObjects()) {
                                        $retry476 = true;
                                        $keepReads477 = false;
                                        continue $label474;
                                    }
                                    if ($e478.tid ==
                                          null ||
                                          !$e478.tid.isDescendantOf(
                                                       $currentTid479))
                                        throw $e478;
                                    throw new fabric.worker.UserAbortException(
                                            $e478);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e478) {
                                    $commit475 = false;
                                    $currentTid479 = $tm480.getCurrentTid();
                                    if ($currentTid479 != null) {
                                        if ($e478.tid.equals($currentTid479) ||
                                              !$e478.tid.isDescendantOf(
                                                           $currentTid479)) {
                                            throw $e478;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads477) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit475) {
                                {  }
                                if ($retry476) { continue $label474; }
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
                        fabric.worker.transaction.TransactionManager $tm490 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled493 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff491 = 1;
                        boolean $doBackoff492 = true;
                        boolean $retry486 = true;
                        boolean $keepReads487 = false;
                        $label484: for (boolean $commit485 = false; !$commit485;
                                        ) {
                            if ($backoffEnabled493) {
                                if ($doBackoff492) {
                                    if ($backoff491 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff491));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e488) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff491 < 5000) $backoff491 *= 2;
                                }
                                $doBackoff492 = $backoff491 <= 32 ||
                                                  !$doBackoff492;
                            }
                            $commit485 = true;
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
                                         RetryException $e488) {
                                    throw $e488;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e488) {
                                    throw $e488;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e488) {
                                    throw $e488;
                                }
                                catch (final Throwable $e488) {
                                    $tm490.getCurrentLog().checkRetrySignal();
                                    throw $e488;
                                }
                            }
                            catch (final fabric.worker.RetryException $e488) {
                                $commit485 = false;
                                continue $label484;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e488) {
                                $commit485 = false;
                                $retry486 = false;
                                $keepReads487 = $e488.keepReads;
                                if ($tm490.checkForStaleObjects()) {
                                    $retry486 = true;
                                    $keepReads487 = false;
                                    continue $label484;
                                }
                                fabric.common.TransactionID $currentTid489 =
                                  $tm490.getCurrentTid();
                                if ($e488.tid ==
                                      null ||
                                      !$e488.tid.isDescendantOf(
                                                   $currentTid489)) {
                                    throw $e488;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e488);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e488) {
                                $commit485 = false;
                                fabric.common.TransactionID $currentTid489 =
                                  $tm490.getCurrentTid();
                                if ($e488.tid.isDescendantOf($currentTid489))
                                    continue $label484;
                                if ($currentTid489.parent != null) {
                                    $retry486 = false;
                                    throw $e488;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e488) {
                                $commit485 = false;
                                if ($tm490.checkForStaleObjects())
                                    continue $label484;
                                $retry486 = false;
                                throw new fabric.worker.AbortException($e488);
                            }
                            finally {
                                if ($commit485) {
                                    fabric.common.TransactionID $currentTid489 =
                                      $tm490.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e488) {
                                        $commit485 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e488) {
                                        $commit485 = false;
                                        $retry486 = false;
                                        $keepReads487 = $e488.keepReads;
                                        if ($tm490.checkForStaleObjects()) {
                                            $retry486 = true;
                                            $keepReads487 = false;
                                            continue $label484;
                                        }
                                        if ($e488.tid ==
                                              null ||
                                              !$e488.tid.isDescendantOf(
                                                           $currentTid489))
                                            throw $e488;
                                        throw new fabric.worker.
                                                UserAbortException($e488);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e488) {
                                        $commit485 = false;
                                        $currentTid489 = $tm490.getCurrentTid();
                                        if ($currentTid489 != null) {
                                            if ($e488.tid.equals(
                                                            $currentTid489) ||
                                                  !$e488.tid.
                                                  isDescendantOf(
                                                    $currentTid489)) {
                                                throw $e488;
                                            }
                                        }
                                    }
                                } else if ($keepReads487) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit485) {
                                    {  }
                                    if ($retry486) { continue $label484; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -18, 17, -57, 39, 57,
    71, -107, 31, -19, -108, 82, 29, -116, 13, -95, -107, 51, 2, -33, -25, 100,
    -31, -85, 106, -75, 52, 86, -15, -10, 76, -84, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549752164000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
