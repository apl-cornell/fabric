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
                    fabric.metrics.treaties.Treaty treaty$var463 = treaty;
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
                                treaty =
                                  ((fabric.metrics.treaties.Treaty)
                                     new fabric.metrics.treaties.Treaty._Impl(
                                       s).$getProxy()).
                                    fabric$metrics$treaties$Treaty$(metric,
                                                                    predicate,
                                                                    policy,
                                                                    weakStats);
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
                                { treaty = treaty$var463; }
                                if ($retry466) { continue $label464; }
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
                                this.set$$expiry((long) 0);
                                this.
                                  set$policy(
                                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                                      singleton);
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
                oldPolicy.unapply((fabric.metrics.treaties.Treaty)
                                    this.$getProxy());
                {
                    fabric.worker.transaction.TransactionManager $tm490 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled493 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff491 = 1;
                    boolean $doBackoff492 = true;
                    boolean $retry486 = true;
                    boolean $keepReads487 = false;
                    $label484: for (boolean $commit485 = false; !$commit485; ) {
                        if ($backoffEnabled493) {
                            if ($doBackoff492) {
                                if ($backoff491 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
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
                            $doBackoff492 = $backoff491 <= 32 || !$doBackoff492;
                        }
                        $commit485 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.get$metric().get$treatiesBox().
                                  remove((fabric.metrics.treaties.Treaty)
                                           this.$getProxy());
                            }
                            catch (final fabric.worker.RetryException $e488) {
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
                            if ($e488.tid == null ||
                                  !$e488.tid.isDescendantOf($currentTid489)) {
                                throw $e488;
                            }
                            throw new fabric.worker.UserAbortException($e488);
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
                                    throw new fabric.worker.UserAbortException(
                                            $e488);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e488) {
                                    $commit485 = false;
                                    $currentTid489 = $tm490.getCurrentTid();
                                    if ($currentTid489 != null) {
                                        if ($e488.tid.equals($currentTid489) ||
                                              !$e488.tid.isDescendantOf(
                                                           $currentTid489)) {
                                            throw $e488;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads487) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        fabric.worker.transaction.TransactionManager $tm500 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled503 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff501 = 1;
                        boolean $doBackoff502 = true;
                        boolean $retry496 = true;
                        boolean $keepReads497 = false;
                        $label494: for (boolean $commit495 = false; !$commit495;
                                        ) {
                            if ($backoffEnabled503) {
                                if ($doBackoff502) {
                                    if ($backoff501 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff501));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e498) {

                                            }
                                        }
                                    }
                                    if ($backoff501 < 5000) $backoff501 *= 2;
                                }
                                $doBackoff502 = $backoff501 <= 32 ||
                                                  !$doBackoff502;
                            }
                            $commit495 = true;
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
                                         RetryException $e498) {
                                    throw $e498;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e498) {
                                    throw $e498;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e498) {
                                    throw $e498;
                                }
                                catch (final Throwable $e498) {
                                    $tm500.getCurrentLog().checkRetrySignal();
                                    throw $e498;
                                }
                            }
                            catch (final fabric.worker.RetryException $e498) {
                                $commit495 = false;
                                continue $label494;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e498) {
                                $commit495 = false;
                                $retry496 = false;
                                $keepReads497 = $e498.keepReads;
                                if ($tm500.checkForStaleObjects()) {
                                    $retry496 = true;
                                    $keepReads497 = false;
                                    continue $label494;
                                }
                                fabric.common.TransactionID $currentTid499 =
                                  $tm500.getCurrentTid();
                                if ($e498.tid ==
                                      null ||
                                      !$e498.tid.isDescendantOf(
                                                   $currentTid499)) {
                                    throw $e498;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e498);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e498) {
                                $commit495 = false;
                                fabric.common.TransactionID $currentTid499 =
                                  $tm500.getCurrentTid();
                                if ($e498.tid.isDescendantOf($currentTid499))
                                    continue $label494;
                                if ($currentTid499.parent != null) {
                                    $retry496 = false;
                                    throw $e498;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e498) {
                                $commit495 = false;
                                if ($tm500.checkForStaleObjects())
                                    continue $label494;
                                $retry496 = false;
                                throw new fabric.worker.AbortException($e498);
                            }
                            finally {
                                if ($commit495) {
                                    fabric.common.TransactionID $currentTid499 =
                                      $tm500.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e498) {
                                        $commit495 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e498) {
                                        $commit495 = false;
                                        $retry496 = false;
                                        $keepReads497 = $e498.keepReads;
                                        if ($tm500.checkForStaleObjects()) {
                                            $retry496 = true;
                                            $keepReads497 = false;
                                            continue $label494;
                                        }
                                        if ($e498.tid ==
                                              null ||
                                              !$e498.tid.isDescendantOf(
                                                           $currentTid499))
                                            throw $e498;
                                        throw new fabric.worker.
                                                UserAbortException($e498);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e498) {
                                        $commit495 = false;
                                        $currentTid499 = $tm500.getCurrentTid();
                                        if ($currentTid499 != null) {
                                            if ($e498.tid.equals(
                                                            $currentTid499) ||
                                                  !$e498.tid.
                                                  isDescendantOf(
                                                    $currentTid499)) {
                                                throw $e498;
                                            }
                                        }
                                    }
                                } else if ($keepReads497) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit495) {
                                    {  }
                                    if ($retry496) { continue $label494; }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static final byte[] $classHash = new byte[] { -53, -32, 120, -98, 15,
    124, 121, 21, 111, -83, 27, -95, -107, 11, -26, -18, 32, 95, 20, 80, 32, 62,
    9, 115, 18, -25, 26, -21, -35, 52, -112, -106 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549295522000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
