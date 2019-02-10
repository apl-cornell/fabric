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
                fabric.worker.metrics.treaties.enforcement.NoPolicy.singleton.
                  shiftPolicies((fabric.metrics.treaties.Treaty)
                                  this.$getProxy(), policy);
            } else {
                ((fabric.metrics.treaties.Treaty._Impl)
                   this.fetch()).deactivate();
            }
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet(
                                                              ).add(metric));
            fabric.common.Logging.METRICS_LOGGER.fine("CREATED TREATY " +
                                                        metric.$getOnum() +
                                                        " " + predicate +
                                                        " until " +
                                                        this.get$$expiry());
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
                    fabric.metrics.treaties.Treaty treaty$var88 = treaty;
                    fabric.worker.transaction.TransactionManager $tm95 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled98 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff96 = 1;
                    boolean $doBackoff97 = true;
                    boolean $retry91 = true;
                    boolean $keepReads92 = false;
                    $label89: for (boolean $commit90 = false; !$commit90; ) {
                        if ($backoffEnabled98) {
                            if ($doBackoff97) {
                                if ($backoff96 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff96));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e93) {
                                            
                                        }
                                    }
                                }
                                if ($backoff96 < 5000) $backoff96 *= 2;
                            }
                            $doBackoff97 = $backoff96 <= 32 || !$doBackoff97;
                        }
                        $commit90 = true;
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
                            catch (final fabric.worker.RetryException $e93) {
                                throw $e93;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e93) {
                                throw $e93;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e93) {
                                throw $e93;
                            }
                            catch (final Throwable $e93) {
                                $tm95.getCurrentLog().checkRetrySignal();
                                throw $e93;
                            }
                        }
                        catch (final fabric.worker.RetryException $e93) {
                            $commit90 = false;
                            continue $label89;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e93) {
                            $commit90 = false;
                            $retry91 = false;
                            $keepReads92 = $e93.keepReads;
                            if ($tm95.checkForStaleObjects()) {
                                $retry91 = true;
                                $keepReads92 = false;
                                continue $label89;
                            }
                            fabric.common.TransactionID $currentTid94 =
                              $tm95.getCurrentTid();
                            if ($e93.tid == null ||
                                  !$e93.tid.isDescendantOf($currentTid94)) {
                                throw $e93;
                            }
                            throw new fabric.worker.UserAbortException($e93);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e93) {
                            $commit90 = false;
                            fabric.common.TransactionID $currentTid94 =
                              $tm95.getCurrentTid();
                            if ($e93.tid.isDescendantOf($currentTid94))
                                continue $label89;
                            if ($currentTid94.parent != null) {
                                $retry91 = false;
                                throw $e93;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e93) {
                            $commit90 = false;
                            if ($tm95.checkForStaleObjects()) continue $label89;
                            $retry91 = false;
                            throw new fabric.worker.AbortException($e93);
                        }
                        finally {
                            if ($commit90) {
                                fabric.common.TransactionID $currentTid94 =
                                  $tm95.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e93) {
                                    $commit90 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e93) {
                                    $commit90 = false;
                                    $retry91 = false;
                                    $keepReads92 = $e93.keepReads;
                                    if ($tm95.checkForStaleObjects()) {
                                        $retry91 = true;
                                        $keepReads92 = false;
                                        continue $label89;
                                    }
                                    if ($e93.tid ==
                                          null ||
                                          !$e93.tid.isDescendantOf(
                                                      $currentTid94))
                                        throw $e93;
                                    throw new fabric.worker.UserAbortException(
                                            $e93);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e93) {
                                    $commit90 = false;
                                    $currentTid94 = $tm95.getCurrentTid();
                                    if ($currentTid94 != null) {
                                        if ($e93.tid.equals($currentTid94) ||
                                              !$e93.tid.isDescendantOf(
                                                          $currentTid94)) {
                                            throw $e93;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads92) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit90) {
                                { treaty = treaty$var88; }
                                if ($retry91) { continue $label89; }
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
                    fabric.common.Logging.METRICS_LOGGER.fine(
                                                           "UPDATED TREATY " +
                                                             this.get$metric(
                                                                    ).$getOnum(
                                                                        ) +
                                                             " " +
                                                             this.get$predicate(
                                                                    ) +
                                                             " until " +
                                                             this.get$$expiry(
                                                                    ));
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
                fabric.common.Logging.METRICS_LOGGER.fine(
                                                       "UPDATED TREATY " +
                                                         this.get$metric(
                                                                ).$getOnum() +
                                                         " " +
                                                         this.get$predicate() +
                                                         " until " +
                                                         this.get$$expiry());
                if (valid()) {
                    this.set$policy(newPolicy);
                    oldPolicy.shiftPolicies((fabric.metrics.treaties.Treaty)
                                              this.$getProxy(), newPolicy);
                } else {
                    ((fabric.metrics.treaties.Treaty._Impl)
                       this.fetch()).deactivate();
                }
            }
            if (this.get$$expiry() < oldExpiry) {
                return getObservers();
            }
            else {
                if (this.get$$expiry() > oldExpiry) {
                    for (java.util.Iterator iter =
                           getObservers().iterator();
                         iter.hasNext();
                         ) {
                        java.lang.Object o =
                          iter.next();
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
                                (fabric.metrics.treaties.Treaty)
                                  this.$getProxy());
                    }
                }
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
                    fabric.common.Logging.METRICS_LOGGER.fine(
                                                           "UPDATED TREATY " +
                                                             this.get$metric(
                                                                    ).$getOnum(
                                                                        ) +
                                                             " " +
                                                             this.get$predicate(
                                                                    ) +
                                                             " until " +
                                                             this.get$$expiry(
                                                                    ));
                }
                else {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerDelayedExtension((fabric.metrics.treaties.Treaty)
                                                 this.$getProxy());
                }
            }
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
            fabric.common.Logging.METRICS_LOGGER.fine(
                                                   "UPDATED TREATY " +
                                                     this.get$metric().$getOnum(
                                                                         ) +
                                                     " " +
                                                     this.get$predicate() +
                                                     " until " +
                                                     this.get$$expiry());
            this.set$policy(newPolicy);
            oldPolicy.shiftPolicies((fabric.metrics.treaties.Treaty)
                                      this.$getProxy(), newPolicy);
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
                fabric.common.Logging.METRICS_LOGGER.fine(
                                                       "UPDATED TREATY " +
                                                         this.get$metric(
                                                                ).$getOnum() +
                                                         " " +
                                                         this.get$predicate() +
                                                         " until " +
                                                         this.get$$expiry());
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
                    fabric.worker.transaction.TransactionManager $tm105 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled108 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff106 = 1;
                    boolean $doBackoff107 = true;
                    boolean $retry101 = true;
                    boolean $keepReads102 = false;
                    $label99: for (boolean $commit100 = false; !$commit100; ) {
                        if ($backoffEnabled108) {
                            if ($doBackoff107) {
                                if ($backoff106 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff106));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e103) {
                                            
                                        }
                                    }
                                }
                                if ($backoff106 < 5000) $backoff106 *= 2;
                            }
                            $doBackoff107 = $backoff106 <= 32 || !$doBackoff107;
                        }
                        $commit100 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.set$$expiry((long) 0);
                                fabric.common.Logging.METRICS_LOGGER.
                                  fine("UPDATED TREATY " +
                                         this.get$metric().$getOnum() + " " +
                                         this.get$predicate() + " until " +
                                         this.get$$expiry());
                                this.
                                  set$policy(
                                    fabric.worker.metrics.treaties.enforcement.NoPolicy.
                                      singleton);
                            }
                            catch (final fabric.worker.RetryException $e103) {
                                throw $e103;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e103) {
                                throw $e103;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                throw $e103;
                            }
                            catch (final Throwable $e103) {
                                $tm105.getCurrentLog().checkRetrySignal();
                                throw $e103;
                            }
                        }
                        catch (final fabric.worker.RetryException $e103) {
                            $commit100 = false;
                            continue $label99;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e103) {
                            $commit100 = false;
                            $retry101 = false;
                            $keepReads102 = $e103.keepReads;
                            if ($tm105.checkForStaleObjects()) {
                                $retry101 = true;
                                $keepReads102 = false;
                                continue $label99;
                            }
                            fabric.common.TransactionID $currentTid104 =
                              $tm105.getCurrentTid();
                            if ($e103.tid == null ||
                                  !$e103.tid.isDescendantOf($currentTid104)) {
                                throw $e103;
                            }
                            throw new fabric.worker.UserAbortException($e103);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e103) {
                            $commit100 = false;
                            fabric.common.TransactionID $currentTid104 =
                              $tm105.getCurrentTid();
                            if ($e103.tid.isDescendantOf($currentTid104))
                                continue $label99;
                            if ($currentTid104.parent != null) {
                                $retry101 = false;
                                throw $e103;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e103) {
                            $commit100 = false;
                            if ($tm105.checkForStaleObjects())
                                continue $label99;
                            $retry101 = false;
                            throw new fabric.worker.AbortException($e103);
                        }
                        finally {
                            if ($commit100) {
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e103) {
                                    $commit100 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e103) {
                                    $commit100 = false;
                                    $retry101 = false;
                                    $keepReads102 = $e103.keepReads;
                                    if ($tm105.checkForStaleObjects()) {
                                        $retry101 = true;
                                        $keepReads102 = false;
                                        continue $label99;
                                    }
                                    if ($e103.tid ==
                                          null ||
                                          !$e103.tid.isDescendantOf(
                                                       $currentTid104))
                                        throw $e103;
                                    throw new fabric.worker.UserAbortException(
                                            $e103);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e103) {
                                    $commit100 = false;
                                    $currentTid104 = $tm105.getCurrentTid();
                                    if ($currentTid104 != null) {
                                        if ($e103.tid.equals($currentTid104) ||
                                              !$e103.tid.isDescendantOf(
                                                           $currentTid104)) {
                                            throw $e103;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads102) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit100) {
                                {  }
                                if ($retry101) { continue $label99; }
                            }
                        }
                    }
                }
                oldPolicy.unapply((fabric.metrics.treaties.Treaty)
                                    this.$getProxy());
                {
                    fabric.worker.transaction.TransactionManager $tm115 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled118 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff116 = 1;
                    boolean $doBackoff117 = true;
                    boolean $retry111 = true;
                    boolean $keepReads112 = false;
                    $label109: for (boolean $commit110 = false; !$commit110; ) {
                        if ($backoffEnabled118) {
                            if ($doBackoff117) {
                                if ($backoff116 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff116));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e113) {
                                            
                                        }
                                    }
                                }
                                if ($backoff116 < 5000) $backoff116 *= 2;
                            }
                            $doBackoff117 = $backoff116 <= 32 || !$doBackoff117;
                        }
                        $commit110 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                this.get$metric().get$treatiesBox().
                                  remove((fabric.metrics.treaties.Treaty)
                                           this.$getProxy());
                            }
                            catch (final fabric.worker.RetryException $e113) {
                                throw $e113;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e113) {
                                throw $e113;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e113) {
                                throw $e113;
                            }
                            catch (final Throwable $e113) {
                                $tm115.getCurrentLog().checkRetrySignal();
                                throw $e113;
                            }
                        }
                        catch (final fabric.worker.RetryException $e113) {
                            $commit110 = false;
                            continue $label109;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e113) {
                            $commit110 = false;
                            $retry111 = false;
                            $keepReads112 = $e113.keepReads;
                            if ($tm115.checkForStaleObjects()) {
                                $retry111 = true;
                                $keepReads112 = false;
                                continue $label109;
                            }
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid == null ||
                                  !$e113.tid.isDescendantOf($currentTid114)) {
                                throw $e113;
                            }
                            throw new fabric.worker.UserAbortException($e113);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e113) {
                            $commit110 = false;
                            fabric.common.TransactionID $currentTid114 =
                              $tm115.getCurrentTid();
                            if ($e113.tid.isDescendantOf($currentTid114))
                                continue $label109;
                            if ($currentTid114.parent != null) {
                                $retry111 = false;
                                throw $e113;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e113) {
                            $commit110 = false;
                            if ($tm115.checkForStaleObjects())
                                continue $label109;
                            $retry111 = false;
                            throw new fabric.worker.AbortException($e113);
                        }
                        finally {
                            if ($commit110) {
                                fabric.common.TransactionID $currentTid114 =
                                  $tm115.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e113) {
                                    $commit110 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e113) {
                                    $commit110 = false;
                                    $retry111 = false;
                                    $keepReads112 = $e113.keepReads;
                                    if ($tm115.checkForStaleObjects()) {
                                        $retry111 = true;
                                        $keepReads112 = false;
                                        continue $label109;
                                    }
                                    if ($e113.tid ==
                                          null ||
                                          !$e113.tid.isDescendantOf(
                                                       $currentTid114))
                                        throw $e113;
                                    throw new fabric.worker.UserAbortException(
                                            $e113);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e113) {
                                    $commit110 = false;
                                    $currentTid114 = $tm115.getCurrentTid();
                                    if ($currentTid114 != null) {
                                        if ($e113.tid.equals($currentTid114) ||
                                              !$e113.tid.isDescendantOf(
                                                           $currentTid114)) {
                                            throw $e113;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads112) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit110) {
                                {  }
                                if ($retry111) { continue $label109; }
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
                        fabric.worker.transaction.TransactionManager $tm125 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled128 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff126 = 1;
                        boolean $doBackoff127 = true;
                        boolean $retry121 = true;
                        boolean $keepReads122 = false;
                        $label119: for (boolean $commit120 = false; !$commit120;
                                        ) {
                            if ($backoffEnabled128) {
                                if ($doBackoff127) {
                                    if ($backoff126 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff126));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e123) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff126 < 5000) $backoff126 *= 2;
                                }
                                $doBackoff127 = $backoff126 <= 32 ||
                                                  !$doBackoff127;
                            }
                            $commit120 = true;
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
                                         RetryException $e123) {
                                    throw $e123;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e123) {
                                    throw $e123;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e123) {
                                    throw $e123;
                                }
                                catch (final Throwable $e123) {
                                    $tm125.getCurrentLog().checkRetrySignal();
                                    throw $e123;
                                }
                            }
                            catch (final fabric.worker.RetryException $e123) {
                                $commit120 = false;
                                continue $label119;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e123) {
                                $commit120 = false;
                                $retry121 = false;
                                $keepReads122 = $e123.keepReads;
                                if ($tm125.checkForStaleObjects()) {
                                    $retry121 = true;
                                    $keepReads122 = false;
                                    continue $label119;
                                }
                                fabric.common.TransactionID $currentTid124 =
                                  $tm125.getCurrentTid();
                                if ($e123.tid ==
                                      null ||
                                      !$e123.tid.isDescendantOf(
                                                   $currentTid124)) {
                                    throw $e123;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e123);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e123) {
                                $commit120 = false;
                                fabric.common.TransactionID $currentTid124 =
                                  $tm125.getCurrentTid();
                                if ($e123.tid.isDescendantOf($currentTid124))
                                    continue $label119;
                                if ($currentTid124.parent != null) {
                                    $retry121 = false;
                                    throw $e123;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e123) {
                                $commit120 = false;
                                if ($tm125.checkForStaleObjects())
                                    continue $label119;
                                $retry121 = false;
                                throw new fabric.worker.AbortException($e123);
                            }
                            finally {
                                if ($commit120) {
                                    fabric.common.TransactionID $currentTid124 =
                                      $tm125.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e123) {
                                        $commit120 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e123) {
                                        $commit120 = false;
                                        $retry121 = false;
                                        $keepReads122 = $e123.keepReads;
                                        if ($tm125.checkForStaleObjects()) {
                                            $retry121 = true;
                                            $keepReads122 = false;
                                            continue $label119;
                                        }
                                        if ($e123.tid ==
                                              null ||
                                              !$e123.tid.isDescendantOf(
                                                           $currentTid124))
                                            throw $e123;
                                        throw new fabric.worker.
                                                UserAbortException($e123);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e123) {
                                        $commit120 = false;
                                        $currentTid124 = $tm125.getCurrentTid();
                                        if ($currentTid124 != null) {
                                            if ($e123.tid.equals(
                                                            $currentTid124) ||
                                                  !$e123.tid.
                                                  isDescendantOf(
                                                    $currentTid124)) {
                                                throw $e123;
                                            }
                                        }
                                    }
                                } else if ($keepReads122) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit120) {
                                    {  }
                                    if ($retry121) { continue $label119; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 63, -108, 76, 112, -14,
    -23, 6, -90, -106, 124, -74, 106, -63, 120, 106, -106, 82, 44, -112, -25,
    30, 82, 56, -25, -118, -86, 44, 100, 110, -11, 94, 83 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549835112000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZe3ATxxlfyfIzfgHhZYx5WCGBGKkkaSfElASMARWBXRuYqWlxTncr+fDp7rhb2XIaWpqZDCR/MCQ1lCSEv2iaUgjTztD0RSaZpoGUTjvpK+SPpvzDEIbSKU3TNFNS+n17K510Pgm7M2XY/Va7++1+z9/unk9eJ5W2RRYnpYSqRdiYSe3IeikRi/dKlk2VLk2y7a3QOyjfEYod/uA7SluQBOOkXpZ0Q1dlSRvUbUYa47ukESmqUxbd1hfr3EFqZWTcKNlDjAR3rM1aZKFpaGMpzWBikwnrH7o3Ov6tnc0/qCBNA6RJ1fuZxFS5y9AZzbIBUp+m6QS17DWKQpUBMk2nVOmnlipp6mMw0dAHyHRbTekSy1jU7qO2oY3gxOl2xqQW3zPXieIbILaVkZlhgfjNjvgZpmrRuGqzzjipSqpUU+zd5GskFCeVSU1KwcRZ8ZwWUb5idD32w/Q6FcS0kpJMcyyhYVVXGFng5chrHN4EE4C1Ok3ZkJHfKqRL0EGmOyJpkp6K9jNL1VMwtdLIwC6MtJRcFCbVmJI8LKXoICNzvPN6nSGYVcvNgiyMzPRO4yuBz1o8Pivw1vUtqw58Vd+oB0kAZFaorKH8NcDU5mHqo0lqUV2mDmP9svhhadbZ/UFCYPJMz2RnzquP33iko+31886ceT5zehK7qMwG5eOJxndau5aurEAxakzDVjEUijTnXu0VI51ZE6J9Vn5FHIzkBl/ve+tLe0/Qa0FSFyNVsqFl0hBV02QjbaoatTZQnVoSo0qM1FJd6eLjMVIN7biqU6e3J5m0KYuRkMa7qgz+G0yUhCXQRNXQVvWkkWubEhvi7axJCKmGQgLwfwkhy45Aey4hFaBPX3TISNNoQsvQUQjvKBQqWfJQFPLWUuXlsmGORW1LjloZnakw0+mPQigBsaPMopAk6FpsjEVAGvP/smoWdWkeDQTAzAtkQ6EJyQafifhZ26tBimw0NIVag7J24GyMzDj7HI+hWox7G2KXWykAfm/1IkYh73hmbfeNVwYvOPGHvMKIjLQ5UkaElJGclBFHShCsHnMrAmgVAbQ6GchGuo7FvsdDqMrmuZZfqx7WesjUJJY0rHSWBAJcsTs5P48d8PwwIAqARv3S/q984dH9iysgaM3REPoRpoa9KeQCTwxaEuTFoNy074N/nj68x3CTiZHwhByfyIk5uthrJcuQqQIY6C6/bKF0ZvDsnnAQ8aUWoI9JEJyAI23ePYpytTOHe2iNyji5A20gaTiUA6s6NmQZo24P934jVtOdQEBjeQTkkPn5fvPFi7++ej8/THLo2lQAw/2UdRZkNC7WxHN3mmt78CeFeX860vvNQ9f37eCGhxntfhuGse6CTJYghQ3ryfO73/vz+8d/H3SdxUiVmUloqpzluky7Bf8CUP6DBdMSO5ACOHcJSFiYxwQTd17iygbooAFCgeh2eJueNhQ1qUoJjWKk3Gy6a8WZvxxodtytQY9jPIt03H4Bt3/uWrL3ws6P2/gyARlPJ9d+7jQH8ma4K6+xLGkM5ch+47fznzsnvQiRD4Blq49RjkGE24NwB97HbbGc1ys8Yw9gtdixVivvD9kT4X89nqNuLA5ETx5t6Vp9zcn5fCziGot8cn67VJAm951IfxRcXPWLIKkeIM38CJd0tl0C+IIwGIBD2O4SnXHSUDRefKA6p0dnPtdavXlQsK03C1ysgTbOxnadE/hO4IAhmtBI86HMA/g+J+iPcXSGifWd2QDhjYc4Szuvl2C1lBsyiM1ljNSq6XSGodv5BvdCjApUw58z4ej2YN1mTnGwxck/rD9XLNdSKK0gzw1B3/WRa205ubBanROo1rSoAhdBRnMyrRQyjRrWMLUmwjDYldE01VkOkfvzHbjCXC/q+ilSh4q0O0YOzXRoxU0fRTb5KwLZXm1a6ghsnM0vGsRFa8Vi/xb0HwWLIkQYgBBjOVVX3UZVCge9JXPdIt1uu9ddZFLazkLB7ocyRkjzM4IyH223+2tbIcKpyuZ36iL/NW/rXbdma/fg1o193f0be+LrfHK411LTAMMj4gpH948/fStyYNzBL+ee2z7hqlnI49x1+ZYNfN8s7LKo3C6cY/2V03t++vKefc49cHrxra1bz6RP/fHTX0WOXHrb5xYQ0gznHG/OlggBbhTX+/xflbhypQVNFRi5AOgCuQBo9eQfl6wnYVNrBN4qvg5G1eeXulhztY8/MX5M6fn2iqAA2kchy5hhLtfoCNUKpKhHI054uG3mzwkXNS9dm7+ya/hyyjHiAs/O3tnf3Xzy7Q1L5GeDpCIPjxPeMMVMncWgWGdReILpW4ugcWHezJhfZAuUuwipXAA0DBn8QmEsuxnQjpVSnJ81guV5Qce9HnIPq5BzKOHPR7DagFVfznVt/rmLYGRvlswSzsPe0TKH4h6s4Gq1wFk9LJYN5yAh7EBe2FXSyOvXjKvshNIJat7t0Or3SpimNDQPeBCtSax0UdALpS1W4cKFazFH6SfLKL0Pq69DlOp01NHPTz3u+TlQvgj4fU7QH03N88jyqqDfL61HoXAHyowdxOopRipHJE11tvuywCck8ISuThiGRiXdT5cWKAohDX8Q9M2p6YIsPxf07OR0eb7M2FGsDoHEqs61wZ/PesTmOPcAFMC3xk+AakCfmUqE9fhFWLNY6aCgT/xPEXa8jHIvYXUMbnEQYWvgWosHt+Ico25Se9yzHMpe0Hm3oJun5h5kiQu6fnLuOVVm7DRWLzPSMCTpika3mQpemXJgtMwfjGK561/uSIF7rj8w+RkA70evwd2zTdDQ1AyALBUObfl0cgb4SZmxn2F1hpEZCUkeTllGRle6s4zqNj5xfDIvNGKIlPSoBecyuUzIoh5BH56aWsiyWtAHJ6fWW2XGzmP1BmCfRROShuchdrzmEZzfV+ERQD4Et5wS9GgZwbdMvJUiywuCljn1CoX7TZmxd7D6JSN1CpVERvlJzk2+CK48DYTc/RlB50zJ5JxltqDTSkseKL5bzSjOiX54ttMyp/LFMrq+j9XvGKlJUdZrGdnS59M9IACIes8OQVdNTVNk6RT0s7fVFH+m+KqXywh/BatLjDRaNG2M5KGgpLPWwcqQ8ktjgjZOTQVkaRC0enJh9tcyY3/D6iojTWD6OJWS/Rn++M4jX8dtkM950drbKf9W7+9+eNk41w780jHP56uj+CIud71Jj1/e1DGzxBfHORP+RiH4XjnWVDP72LZ3+Re0/Nfu2jipSWY0rfCLQEG7Cl7GSZUbodb5PmBy8gkjs0t8ooQIzTW5qh87HDfB+cUcjP/ZAFuF826BIZx58Es8CVvcKmfydr9Hy5qEzSwAgpx/SidaS8bCv+mc/HD2v6pqtl7in9DA2wsfHo+bf79a9dLhx3+4643srsN9HQevtPU9eOXpEx2K/tHO/v8CPYfDY2saAAA=";
}
