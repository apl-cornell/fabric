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
                ((fabric.metrics.treaties.Treaty._Impl)
                   this.fetch()).deactivate();
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
                    int $backoff8 = 1;
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
                                if ($backoff8 < 5000) $backoff8 *= 2;
                            }
                            $doBackoff9 = $backoff8 <= 32 || !$doBackoff9;
                        }
                        $commit2 = true;
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
                            catch (final fabric.worker.RetryException $e5) {
                                throw $e5;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e5) {
                                throw $e5;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e5) {
                                throw $e5;
                            }
                            catch (final Throwable $e5) {
                                $tm7.getCurrentLog().checkRetrySignal();
                                throw $e5;
                            }
                        }
                        catch (final fabric.worker.RetryException $e5) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (fabric.worker.TransactionAbortingException $e5) {
                            $commit2 = false;
                            $retry3 = false;
                            $keepReads4 = $e5.keepReads;
                            if ($tm7.checkForStaleObjects()) {
                                $retry3 = true;
                                $keepReads4 = false;
                                continue $label1;
                            }
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
                            if ($tm7.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e5);
                        }
                        finally {
                            if ($commit2) {
                                fabric.common.TransactionID $currentTid6 =
                                  $tm7.getCurrentTid();
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
                                    if ($tm7.checkForStaleObjects()) {
                                        $retry3 = true;
                                        $keepReads4 = false;
                                        continue $label1;
                                    }
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
                            }
                            else if ($keepReads4) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
        private void deactivate() {
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
                    int $backoff18 = 1;
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
                                if ($backoff18 < 5000) $backoff18 *= 2;
                            }
                            $doBackoff19 = $backoff18 <= 32 || !$doBackoff19;
                        }
                        $commit12 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
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
                                throw $e15;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e15) {
                                throw $e15;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e15) {
                                throw $e15;
                            }
                            catch (final Throwable $e15) {
                                $tm17.getCurrentLog().checkRetrySignal();
                                throw $e15;
                            }
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
                            if ($tm17.checkForStaleObjects()) {
                                $retry13 = true;
                                $keepReads14 = false;
                                continue $label11;
                            }
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
                            if ($tm17.checkForStaleObjects()) continue $label11;
                            $retry13 = false;
                            throw new fabric.worker.AbortException($e15);
                        }
                        finally {
                            if ($commit12) {
                                fabric.common.TransactionID $currentTid16 =
                                  $tm17.getCurrentTid();
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
                                    if ($tm17.checkForStaleObjects()) {
                                        $retry13 = true;
                                        $keepReads14 = false;
                                        continue $label11;
                                    }
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
                            }
                            else if ($keepReads14) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
   * Run an update using the current expiry.  Factored out here since it's used
   * by both handleUpdates() and backgroundExtension().
   * @return true iff the current policy is still viable.
   */
        private boolean updateWithCurPolicy() {
            long updatedCurExpiry =
              this.get$policy().updatedExpiry(
                                  (fabric.metrics.treaties.Treaty)
                                    this.$getProxy(),
                                  fabric.worker.metrics.StatsMap.emptyStats());
            if (updatedCurExpiry > java.lang.System.currentTimeMillis()) {
                if (updatedCurExpiry <
                      this.get$$expiry() ||
                      this.get$$expiry() -
                      java.lang.System.currentTimeMillis() <=
                      fabric.metrics.treaties.Treaty._Static._Proxy.$instance.
                      get$UPDATE_THRESHOLD()) {
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
        private void registerPotentialExtensions(long oldExpiry) {
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
            if (!((fabric.metrics.treaties.Treaty._Impl) this.fetch()).
                  updateWithCurPolicy()) {
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
                if (newPolicyExpiry > java.lang.System.currentTimeMillis()) {
                    this.get$policy().shiftPolicies(
                                        (fabric.metrics.treaties.Treaty)
                                          this.$getProxy(), newPolicy);
                    this.set$policy(newPolicy);
                    this.set$$expiry((long) newPolicyExpiry);
                } else {
                    newPolicy.unapply((fabric.metrics.treaties.Treaty)
                                        this.$getProxy());
                    ((fabric.metrics.treaties.Treaty._Impl)
                       this.fetch()).deactivate();
                }
            }
            if (this.get$$expiry() < oldExpiry) { return getObservers(); }
            ((fabric.metrics.treaties.Treaty._Impl) this.fetch()).
              registerPotentialExtensions(oldExpiry);
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
        }
        
        /**
   * Run a background extension.  This should only result in extending the
   * expiry using the existing policy or waiting further to extend.
   */
        public void backgroundExtension() {
            long oldExpiry = this.get$$expiry();
            boolean policyLive = ((fabric.metrics.treaties.Treaty._Impl)
                                    this.fetch()).updateWithCurPolicy();
            if (this.get$$expiry() < oldExpiry) {
                throw new java.lang.InternalError(
                        "Somehow had a retraction during a background extension: " +
                          oldExpiry + " to " + this.get$$expiry());
            }
            ((fabric.metrics.treaties.Treaty._Impl) this.fetch()).
              registerPotentialExtensions(oldExpiry);
        }
        
        /**
   * Force a shift to a new policy.
   */
        public void rebalance() {
            fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
              newPolicy =
              fabric.metrics.treaties.Treaty._Impl.
              newActivatedPolicy(this.get$metric(), this.get$predicate(),
                                 fabric.worker.metrics.StatsMap.emptyStats());
            this.get$policy().shiftPolicies((fabric.metrics.treaties.Treaty)
                                              this.$getProxy(), newPolicy);
            this.set$policy(newPolicy);
            this.set$$expiry(
                   (long)
                     newPolicy.calculateExpiry(
                                 (fabric.metrics.treaties.Treaty)
                                   this.$getProxy(),
                                 fabric.worker.metrics.StatsMap.emptyStats()));
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
                        fabric.worker.transaction.TransactionManager $tm27 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled30 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff28 = 1;
                        boolean $doBackoff29 = true;
                        boolean $retry23 = true;
                        boolean $keepReads24 = false;
                        $label21: for (boolean $commit22 = false; !$commit22;
                                       ) {
                            if ($backoffEnabled30) {
                                if ($doBackoff29) {
                                    if ($backoff28 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff28));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e25) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff28 < 5000) $backoff28 *= 2;
                                }
                                $doBackoff29 = $backoff28 <= 32 ||
                                                 !$doBackoff29;
                            }
                            $commit22 = true;
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
                                         RetryException $e25) {
                                    throw $e25;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e25) {
                                    throw $e25;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e25) {
                                    throw $e25;
                                }
                                catch (final Throwable $e25) {
                                    $tm27.getCurrentLog().checkRetrySignal();
                                    throw $e25;
                                }
                            }
                            catch (final fabric.worker.RetryException $e25) {
                                $commit22 = false;
                                continue $label21;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e25) {
                                $commit22 = false;
                                $retry23 = false;
                                $keepReads24 = $e25.keepReads;
                                if ($tm27.checkForStaleObjects()) {
                                    $retry23 = true;
                                    $keepReads24 = false;
                                    continue $label21;
                                }
                                fabric.common.TransactionID $currentTid26 =
                                  $tm27.getCurrentTid();
                                if ($e25.tid == null ||
                                      !$e25.tid.isDescendantOf($currentTid26)) {
                                    throw $e25;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e25);
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
                                if ($tm27.checkForStaleObjects())
                                    continue $label21;
                                $retry23 = false;
                                throw new fabric.worker.AbortException($e25);
                            }
                            finally {
                                if ($commit22) {
                                    fabric.common.TransactionID $currentTid26 =
                                      $tm27.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e25) {
                                        $commit22 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e25) {
                                        $commit22 = false;
                                        $retry23 = false;
                                        $keepReads24 = $e25.keepReads;
                                        if ($tm27.checkForStaleObjects()) {
                                            $retry23 = true;
                                            $keepReads24 = false;
                                            continue $label21;
                                        }
                                        if ($e25.tid ==
                                              null ||
                                              !$e25.tid.isDescendantOf(
                                                          $currentTid26))
                                            throw $e25;
                                        throw new fabric.worker.
                                                UserAbortException($e25);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e25) {
                                        $commit22 = false;
                                        $currentTid26 = $tm27.getCurrentTid();
                                        if ($currentTid26 != null) {
                                            if ($e25.tid.equals(
                                                           $currentTid26) ||
                                                  !$e25.tid.isDescendantOf(
                                                              $currentTid26)) {
                                                throw $e25;
                                            }
                                        }
                                    }
                                } else if ($keepReads24) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit22) {
                                    {  }
                                    if ($retry23) { continue $label21; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -33, 3, -2, 62, 44,
    -90, 30, -115, -115, -85, 55, -57, 33, -112, 73, -80, 91, -69, -66, -4, 18,
    53, 14, -106, -34, -1, -55, -88, -96, -39, 13, 115 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1551215353000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
