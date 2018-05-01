package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.ReconfigLock;
import fabric.metrics.util.Subject;
import fabric.util.ArrayList;
import java.util.Iterator;
import fabric.util.List;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link Contract} represents an assertion that is enforced until an
 * expiration time, once {@link #activate()}d. If the current time is earlier
 * than the expiration time and the {@link Contract} {@link #isActivated()},
 * then the {@link Contract} is <i>valid</i>: the enforcement protocol
 * implemented by the API will ensure the assertion holds.
 * <p>
 * This class follows the {@link Subject}-{@link Observer} pattern. An instance
 * can be an observer of a {@link Metric} or other {@link Contract}s, and can be
 * observed by {@link WarrantyComp}s or other {@link Contract}s.
 * <p>
 * TODO: right now all the uses of expiry assume that the check is for a time
 * after the time the {@link Contract} was created. Some of these operations
 * will not be correct if the times being compared with are prior to creation.
 */
public interface Contract
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param store
   *            the {@link Store} this {@link Contract} is stored at
   */
    public fabric.metrics.contracts.Contract fabric$metrics$contracts$Contract$();
    
    /**
   * Extends the expiration time (queued to be extended later if the current
   * time is much earlier than the current expiration).
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
    public void extendTo(long newExpiry, boolean isAsyncExtension);
    
    /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
    public void activate();
    
    public void activate_remote(fabric.lang.security.Principal p);
    
    public void refresh_remote(fabric.lang.security.Principal p, boolean asyncExtension);
    
    /**
   * {@inheritDoc}
   *
   * If there are no {@link Observer}s of this {@link Contract} left, mark as
   * stale (to avoid unnecessary enforcement overhead) and stop observing any
   * enforcement evidence.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    /**
   * Updates the expiration time of this contract, either extending or
   * retracting as needed.
   *
   * @param newExpiry
   *            the new expiration time that could be associated with this
   *            {@link Contract} (if it's past the current expiry, it may not
   *            be immediately used to avoid unnecessary extension overhead).
   * @return true iff the contract was retracted by this update.
   */
    public boolean update(long newExpiry, boolean isAsyncExtension);
    
    /**
   * Called to retract this contract's expiry to an earlier time (applied in
   * the current transaction context).
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
    public void retract(long newExpiry);
    
    /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
    public boolean valid(long time);
    
    /**
   * @return true iff the contract is valid at the given time in the current
   *         transaction context.
   */
    public boolean valid();
    
    /**
   * @param time
   *            the time we're checking against.
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
    public boolean stale(long time);
    
    /**
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
    public boolean stale();
    
    /**
   * @return The expiration time for this {@link Contract} (shifted to account
   *         for a constant "max" clock drift between the node that last set
   *         the expiration time and other nodes checking it.)
   */
    public long getExpiry();
    
    /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
    public abstract boolean refresh(boolean asyncExtension);
    
    public boolean handleUpdates();
    
    /**
   * Check if this implies another {@link Contract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link Contract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link Contract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link Contract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link Contract} with the given metric and bound.
   */
    public abstract boolean
      implies(fabric.metrics.Metric otherMetric, double otherRate, double otherBase);
    
    /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
    public void attemptExtension();
    
    /**
   * Acquire reconfig locks starting from this contract.
   */
    public void acquireReconfigLocks();
    
    /**
   * Create a {@link ConjunctionContract} with this and another contract.
   */
    public fabric.metrics.contracts.Contract and(
      fabric.metrics.contracts.Contract other);
    
    /**
   * Create a proxy for this contract on the given store.
   */
    public fabric.metrics.contracts.Contract getProxyContract(final fabric.worker.Store proxyStore);
    
    /**
   * A Contract which basically acts as a proxy for another Contract
   * to allow local access on another store while the contract is valid.
   *
   * Basically operates by using the original Contract as the only witness
   * of this metric contract.
   */
    public static interface ProxyContract
      extends fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.Contract get$target();
        
        public fabric.metrics.contracts.Contract set$target(
          fabric.metrics.contracts.Contract val);
        
        /**
     * @param target
     *        the {@link Contract} this proxies
     */
        public ProxyContract fabric$metrics$contracts$Contract$ProxyContract$(
          fabric.metrics.contracts.Contract target);
        
        public boolean refresh(boolean asyncExtension);
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase);
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
        
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract other);
        
        public java.lang.String toString();
        
        public static class _Proxy
        extends fabric.metrics.contracts.Contract._Proxy
          implements ProxyContract {
            public fabric.metrics.contracts.Contract get$target() {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).get$target();
            }
            
            public fabric.metrics.contracts.Contract set$target(
              fabric.metrics.contracts.Contract val) {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).set$target(val);
            }
            
            public fabric.metrics.contracts.Contract.ProxyContract
              fabric$metrics$contracts$Contract$ProxyContract$(
              fabric.metrics.contracts.Contract arg1) {
                return ((fabric.metrics.contracts.Contract.ProxyContract)
                          fetch()).
                  fabric$metrics$contracts$Contract$ProxyContract$(arg1);
            }
            
            public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects(
              ) {
                return ((fabric.metrics.contracts.Contract.ProxyContract)
                          fetch()).getLeafSubjects();
            }
            
            public _Proxy(ProxyContract._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.metrics.contracts.Contract._Impl implements ProxyContract
        {
            public fabric.metrics.contracts.Contract get$target() {
                return this.target;
            }
            
            public fabric.metrics.contracts.Contract set$target(
              fabric.metrics.contracts.Contract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.target = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.Contract target;
            
            /**
     * @param target
     *        the {@link Contract} this proxies
     */
            public ProxyContract
              fabric$metrics$contracts$Contract$ProxyContract$(
              fabric.metrics.contracts.Contract target) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(target)) instanceof ProxyContract) {
                    this.set$target(
                           ((ProxyContract)
                              fabric.lang.Object._Proxy.$getProxy(
                                                          target)).get$target(
                                                                     ));
                }
                else {
                    this.set$target(target);
                }
                fabric$metrics$contracts$Contract$();
                this.
                  set$currentPolicy(
                    ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                       new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         _Impl(this.$getStore()).
                       $getProxy()).
                        fabric$metrics$contracts$enforcement$WitnessPolicy$(
                          new fabric.metrics.contracts.Contract[] { this.
                              get$target() }));
                return (ProxyContract) this.$getProxy();
            }
            
            public boolean refresh(boolean asyncExtension) {
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    this.get$currentPolicy().activate();
                    long curExpiry = this.get$currentPolicy().expiry();
                    boolean result = update(curExpiry, asyncExtension);
                    if (curExpiry < currentTime) {
                        this.get$currentPolicy().unapply((ProxyContract)
                                                           this.$getProxy());
                        this.set$currentPolicy(null);
                    } else {
                        this.get$currentPolicy().apply((ProxyContract)
                                                         this.$getProxy());
                    }
                    return result;
                }
                return false;
            }
            
            public boolean implies(fabric.metrics.Metric otherMetric,
                                   double otherRate, double otherBase) {
                return this.get$target().implies(otherMetric, otherRate,
                                                 otherBase);
            }
            
            public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects(
              ) {
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                if (fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                       null))
                    return fabric.worker.metrics.ImmutableMetricsVector.
                      emptyVector();
                return this.get$target().getLeafSubjects();
            }
            
            public fabric.metrics.contracts.Contract and(
              fabric.metrics.contracts.Contract other) {
                return this.get$target().and(other);
            }
            
            public java.lang.String toString() {
                return "Proxy @ " +
                $getStore() +
                " for " +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$target())) +
                " until " +
                this.get$$expiry();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract.ProxyContract.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.target, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.target =
                  (fabric.metrics.contracts.Contract)
                    $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.contracts.Contract.ProxyContract._Impl src =
                  (fabric.metrics.contracts.Contract.ProxyContract._Impl) other;
                this.target = src.target;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.metrics.contracts.Contract.ProxyContract._Static
            {
                public _Proxy(fabric.metrics.contracts.Contract.ProxyContract.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.Contract.
                  ProxyContract._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      Contract.
                      ProxyContract.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        Contract.
                        ProxyContract.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.Contract.ProxyContract.
                            _Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.Contract.ProxyContract._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.metrics.contracts.Contract.ProxyContract._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.Contract.ProxyContract.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -102, -21, 91, -59,
        20, 72, -71, 90, 65, 10, -55, -72, -95, -92, 20, -60, 3, 13, -50, 89,
        24, 78, 23, 78, 39, -18, 72, 47, -40, -96, -70, 62 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525113242000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYa2xcRxU+u3bWjzix49h5GMdxnG2qPNglgT+toWq81PU2m8TYSWg3IpvZe2ftW9+992bubHxTmrQgUAKI/Ahu6INYBVwBxbQSUnkqqD9aaFQUCYQgCAHhR0VRCFKFePwAypmZu3t3764d/ANLOzN35pyZ8/jOmTNevA2rXAZDBZI3zAQ/41A3MUry6cw4YS7VUyZx3SM4m9NWN6cvv/01fSAK0Qx0aMSyLUMjZs5yOazNPEpOk6RFefLoRHr4OLRpgnGMuNMcosdHPAaDjm2emTJt7h9St/9Tu5NzXzzR9e0m6MxCp2FNcsINLWVbnHo8Cx1FWsxT5u7XdapnYZ1FqT5JmUFM4zEktK0sdLvGlEV4iVF3grq2eVoQdrslhzJ5ZnlSiG+j2KykcZuh+F1K/BI3zGTGcPlwBmIFg5q6ewrOQXMGVhVMMoWEGzJlLZJyx+SomEfydgPFZAWi0TJL84xh6Ry2hjkqGscPIAGythQpn7YrRzVbBCegW4lkEmsqOcmZYU0h6Sq7hKdw6FtyUyRqdYg2Q6ZojsOmMN24WkKqNmkWwcKhN0wmd0Kf9YV8VuWt24c+ePHj1pgVhQjKrFPNFPK3ItNAiGmCFiijlkYVY8euzGWy4eqFKAAS94aIFc13H3/n/j0Dr76haN7TgOZw/lGq8Zy2kF/7s/7UznuahBitju0aAgo1mkuvjvsrw56DaN9Q2VEsJsqLr078+JEnX6S3otCehphmm6UiomqdZhcdw6TsQWpRRjjV09BGLT0l19PQguOMYVE1e7hQcClPQ7Mpp2K2/EYTFXALYaIWHBtWwS6PHcKn5dhzAGA9/qAJINoPkOkGiNwASH2Vw3hy2i7SZN4s0VmEdxJ/lDBtOolxywwt6TItyUoWN5DIn0IUYecmEeqcEY0jSvxRAmVx/g97ekKPrtlIBE28VbN1micu+svHzsi4ieExZps6ZTnNvHg1DeuvPiPx0yYw7yJupYUi6PP+cLao5p0rjTzwzku5NxX2BK9vQA6+oAlf0ERF0ERZ0Pg4s70z5S+UtEMEWgJTVwJT12LES6Tm09+UeIq5MvAqm3fg5vc6JuEFmxU9iESkpj2SXwIJYTCD6QUzSMfOyY89dPLCELrSc2ab0amCNB6OpyALpXFEMEhyWuf5t//+8uWzdhBZHOJ1AV/PKQJ2KGw2ZmtUx4QYbL9rkLySu3o2HhXJpk3YhyBSMakMhM+oCdzhchIU1liVgdXCBsQUS+XM1c6nmT0bzEg4rBVNt0KGMFZIQJk/PzTpXLlx/U/vlzdLOdV2VuXkScqHq8JbbNYpA3ldYPsjjFKk++3T41946vb549LwSLG90YFx0aYwrAnGs80+/capX//+dwu/iAbO4tDmMJtjjqG6J9VZ9y7+RfD3H/ETYSomRI/JOuWniMFKjnDE4TsC8TBbmLgbSu/Gj1pFWzcKBsmbVIDlX5137X3lzxe7lMdNnFH2Y7DnzhsE85tH4Mk3T/xjQG4T0cRtFZgwIFMpcH2w837GyBkhh/eJn2955ifkCoIfE5hrPEZlTgJpEpA+3Cdt8V7Z7g2tfUA0Q8pa/RXMh6+DUXGvBnDMJhe/1Je675bKAxU4ij22NcgDx0hVpOx7sfi36FDs9Si0ZKFLXunE4scIJjREQhYvZTflT2ZgTc167QWrbpPhSrj1h0Oh6thwIAT5B8eCWozbFfYVcNAQPcJIcTTIbwA+vMXv14jV9Y5oe7wIyMG9kmW7bHeIZqc0ZFQMdyEojWKxxIXb5QG7OcQ4YVOUS4ZeDtvumP8EYZ8MR2/5szD7iQLMqygRFUqU76Sv+P2lKiWqPA8eun7LUuWDLH0WPjk3rx9+Ya+65Ltrr+QHrFLxW7/8908TT9+81iDNx/xiMDgwhudtqytiD8rSKkDMzVtb7knNvDWlztwaki9M/Y2Di9ce3KFdikJTBRp19Vwt03AtINoZxXLUOlIDi8GKRVcLS2XRkn9AOFz3+yeqYaHyZkM/YY6KOaW8We0iafp2f6Nzfu+FXRSEb0TtJD7vl2THlonvh0VzmMP7FMbiPsbiFYzFG9+x8UCJTK3q+/Ds2wCjT/j9w0uoLpqJeiUFy0f9/iN3VLJBLhpnRhFvlNN+aUovzH323cTFOQU4Vb9vryuhq3lUDS9FldG8W8B+23KnSI7RP7589odfP3s+6lt2jENL3rZNSiz5nVvGC7IcySIDowWs2GTtmG9k3AmM2BaA9I/8fnZFxpUsp/3eWdq4TVK0pnL+6Q3ln4OylzlHkpz0rSQ6vKFiuo0IlrFxSspkLaN5STQGam4UHdNQRUZDzUdR7A6Ah076/V0r01ywxP1+YGnNq0V7fJm1c6KZxYIG03SGksJkSd42btlke3yTzdpshrKK5dLlTK9M6B6j8rEqmDaHS9FGRrgbNcBr58AJvx9ZmREEy36/H15BAvnMMpb4nGg+xaGJWLrkaCQ3Pnyim/HQ7/j9CyuTW7As+P38/+a8S8uszYnm8xxaua1e4WWvdcn6SVQPiaqFOs94HNbUJEOlNz5qG7yP/He7lnqNLrx1YE/vEm+jTXX/SfH5XprvbN04f/RXsrSvvMnbsHIulEyzuk6pGsccTCSGVLZNVS2O7K7gI3qpYoKrSk2Opd7PKZ7nOayt5eHy3xtiVE2HD9qYohNfC9JDfbWNQlNfiYl/Hy3+deM/Y61HbsoCHV0z+Oyt46/3jH0/u7/92ve+vNDzWtOa649sOrTx0N1/GUveeP4H9/0Xvong09YSAAA=";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$currentPolicy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$currentPolicy(val);
        }
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              fabric$metrics$contracts$Contract$();
        }
        
        public void extendTo(long arg1, boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1, arg2);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.Contract) fetch()).activate();
        }
        
        public void activate_remote(fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).activate_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes2 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes2, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void refresh_remote(fabric.lang.security.Principal arg1,
                                   boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).refresh_remote(arg1,
                                                                         arg2);
        }
        
        public static final java.lang.Class[] $paramTypes3 = { boolean.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "refresh", $paramTypes3,
                                    new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static void static_activate(
          fabric.metrics.contracts.Contract arg1) {
            fabric.metrics.contracts.Contract._Impl.static_activate(arg1);
        }
        
        public boolean update(long arg1, boolean arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).update(arg1,
                                                                        arg2);
        }
        
        public void retract(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).retract(arg1);
        }
        
        public boolean valid(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
        }
        
        public boolean valid() {
            return ((fabric.metrics.contracts.Contract) fetch()).valid();
        }
        
        public boolean stale(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).stale(arg1);
        }
        
        public boolean stale() {
            return ((fabric.metrics.contracts.Contract) fetch()).stale();
        }
        
        public long getExpiry() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiry();
        }
        
        public boolean refresh(boolean arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).refresh(arg1);
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.Contract) fetch()).handleUpdates(
                                                                   );
        }
        
        public boolean implies(fabric.metrics.Metric arg1, double arg2,
                               double arg3) {
            return ((fabric.metrics.contracts.Contract) fetch()).implies(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).
              attemptExtension_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes4 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes4, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void attemptExtension() {
            ((fabric.metrics.contracts.Contract) fetch()).attemptExtension();
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.Contract) fetch()).acquireReconfigLocks(
                                                            );
        }
        
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).and(arg1);
        }
        
        public fabric.metrics.contracts.Contract getProxyContract(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              getProxyContract(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Contract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.currentPolicy;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.currentPolicy = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.enforcement.EnforcementPolicy currentPolicy;
        
        /**
   * @param store
   *            the {@link Store} this {@link Contract} is stored at
   */
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$AbstractSubject$();
            this.set$$expiry((long) -1);
            return (fabric.metrics.contracts.Contract) this.$getProxy();
        }
        
        /**
   * Extends the expiration time (queued to be extended later if the current
   * time is much earlier than the current expiration).
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
        public void extendTo(long newExpiry, boolean isAsyncExtension) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            long currentTime = java.lang.System.currentTimeMillis();
            if (getExpiry() -
                  currentTime <=
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$EXTENSION_WINDOW() || isAsyncExtension) {
                for (java.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.util.Observer
                      parent =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                parent)) instanceof fabric.metrics.contracts.Contract) {
                        tm.registerDelayedExtension(
                             parent,
                             (fabric.metrics.contracts.Contract)
                               this.$getProxy());
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "SYNCH EXTENSION OF {0} TO {2} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(
                                                 (fabric.metrics.contracts.Contract)
                                                   this.$getProxy()),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog(),
                      java.lang.Long.
                        valueOf(newExpiry) });
                this.set$$expiry((long) newExpiry);
            }
            else {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "DELAYED EXTENSION OF {0} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(
                                                 (fabric.metrics.contracts.Contract)
                                                   this.$getProxy()),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog() });
                tm.registerDelayedExtension((fabric.metrics.contracts.Contract)
                                              this.$getProxy());
            }
        }
        
        /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
        public void activate() { refresh(false); }
        
        public void activate_remote(fabric.lang.security.Principal p) {
            this.refresh(false);
        }
        
        public void refresh_remote(fabric.lang.security.Principal p,
                                   boolean asyncExtension) {
            this.refresh(asyncExtension);
        }
        
        public static void static_activate(
          fabric.metrics.contracts.Contract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().activate();
                }
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null) &&
                      tmp.get$currentPolicy().expiry() >
                      java.lang.System.currentTimeMillis()) {
                    tmp.set$$expiry((long) tmp.get$currentPolicy().expiry());
                    tmp.get$currentPolicy().apply(tmp);
                } else {
                    tmp.set$currentPolicy(null);
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "ACTIVATED {0} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(tmp),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog() });
            }
            else {
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().activate();
                }
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
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$currentPolicy(), null) &&
                                  tmp.get$currentPolicy().expiry() >
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$$expiry(
                                      (long) tmp.get$currentPolicy().expiry());
                                tmp.get$currentPolicy().apply(tmp);
                            }
                            else {
                                tmp.set$currentPolicy(null);
                            }
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINE,
                                "ACTIVATED {0} IN {1}",
                                new java.lang.Object[] { (java.lang.Object)
                                                           fabric.lang.WrappedJavaInlineable.
                                                           $unwrap(tmp),
                                  fabric.worker.transaction.TransactionManager.
                                    getInstance().
                                    getCurrentLog() });
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
        
        /**
   * {@inheritDoc}
   *
   * If there are no {@link Observer}s of this {@link Contract} left, mark as
   * stale (to avoid unnecessary enforcement overhead) and stop observing any
   * enforcement evidence.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            fabric.metrics.contracts.Contract._Impl.
              static_removeObserver((fabric.metrics.contracts.Contract)
                                      this.$getProxy(), obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.Contract tmp,
          fabric.metrics.util.Observer obs) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                            tmp, obs);
                if (!tmp.isObserved()) {
                    tmp.set$$expiry((long) -1);
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null)) {
                        tmp.get$currentPolicy().unapply(tmp);
                        tmp.set$currentPolicy(null);
                    }
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm460 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled463 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff461 = 1;
                    boolean $doBackoff462 = true;
                    boolean $retry457 = true;
                    $label455: for (boolean $commit456 = false; !$commit456; ) {
                        if ($backoffEnabled463) {
                            if ($doBackoff462) {
                                if ($backoff461 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff461);
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
                        $commit456 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$currentPolicy(), null)) {
                                    tmp.get$currentPolicy().unapply(tmp);
                                    tmp.set$currentPolicy(null);
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e458) {
                            $commit456 = false;
                            continue $label455;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e458) {
                            $commit456 = false;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459))
                                continue $label455;
                            if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459)) {
                                $retry457 = true;
                            }
                            else if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects())
                                continue $label455;
                            $retry457 = false;
                            throw new fabric.worker.AbortException($e458);
                        }
                        finally {
                            if ($commit456) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e458) {
                                    $commit456 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e458) {
                                    $commit456 = false;
                                    fabric.common.TransactionID $currentTid459 =
                                      $tm460.getCurrentTid();
                                    if ($currentTid459 != null) {
                                        if ($e458.tid.equals($currentTid459) ||
                                              !$e458.tid.isDescendantOf(
                                                           $currentTid459)) {
                                            throw $e458;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit456 && $retry457) {
                                {  }
                                continue $label455;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Updates the expiration time of this contract, either extending or
   * retracting as needed.
   *
   * @param newExpiry
   *            the new expiration time that could be associated with this
   *            {@link Contract} (if it's past the current expiry, it may not
   *            be immediately used to avoid unnecessary extension overhead).
   * @return true iff the contract was retracted by this update.
   */
        public boolean update(long newExpiry, boolean isAsyncExtension) {
            fabric.worker.Worker localW = fabric.worker.Worker.getWorker();
            if (!localW.getStore(localW.getName()).equals(getStore()))
                newExpiry =
                  newExpiry -
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$DRIFT_FACTOR();
            if (getExpiry() <
                  newExpiry -
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$DRIFT_FACTOR()) {
                extendTo(newExpiry, isAsyncExtension);
            }
            else if (getExpiry() > newExpiry) {
                retract(newExpiry);
                return true;
            }
            return false;
        }
        
        /**
   * Called to retract this contract's expiry to an earlier time (applied in
   * the current transaction context).
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void retract(long newExpiry) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "RETRACTION OF {0} TO {2} IN {1}",
                new java.lang.Object[] { (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             (fabric.metrics.contracts.Contract)
                                               this.$getProxy()),
                  fabric.worker.transaction.TransactionManager.
                    getInstance().
                    getCurrentLog(),
                  java.lang.Long.
                    valueOf(newExpiry) });
            this.set$$expiry((long) newExpiry);
        }
        
        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(long time) {
            boolean rtn = getExpiry() >= time;
            if (rtn)
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExpiryUse(getExpiry());
            return rtn;
        }
        
        /**
   * @return true iff the contract is valid at the given time in the current
   *         transaction context.
   */
        public boolean valid() {
            boolean rtn = getExpiry() >= java.lang.System.currentTimeMillis();
            if (rtn)
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExpiryUse(getExpiry());
            return rtn;
        }
        
        /**
   * @param time
   *            the time we're checking against.
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
        public boolean stale(long time) { return getExpiry() < time; }
        
        /**
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
        public boolean stale() {
            return getExpiry() < java.lang.System.currentTimeMillis();
        }
        
        /**
   * @return The expiration time for this {@link Contract} (shifted to account
   *         for a constant "max" clock drift between the node that last set
   *         the expiration time and other nodes checking it.)
   */
        public long getExpiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$$expiry() -
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$DRIFT_FACTOR();
        }
        
        /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
        public abstract boolean refresh(boolean asyncExtension);
        
        public boolean handleUpdates() {
            if (valid()) { return refresh(false); }
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                this.get$currentPolicy().unapply(
                                           (fabric.metrics.contracts.Contract)
                                             this.$getProxy());
                this.set$currentPolicy(null);
                return true;
            }
            return false;
        }
        
        /**
   * Check if this implies another {@link Contract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link Contract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link Contract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link Contract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link Contract} with the given metric and bound.
   */
        public abstract boolean implies(
          fabric.metrics.Metric otherMetric, double otherRate, double otherBase);
        
        /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            this.attemptExtension();
        }
        
        /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension() {
            fabric.metrics.contracts.Contract._Impl.
              static_attemptExtension((fabric.metrics.contracts.Contract)
                                        this.$getProxy());
        }
        
        private static void static_attemptExtension(
          fabric.metrics.contracts.Contract tmp) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "ASYNC EXTENSION OF " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.valid()) {
                    tmp.refresh(true);
                }
                else if (!fabric.lang.Object._Proxy.idEquals(
                                                      tmp.get$currentPolicy(),
                                                      null)) {
                    tmp.get$currentPolicy().unapply(tmp);
                    tmp.set$currentPolicy(null);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm469 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled472 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff470 = 1;
                    boolean $doBackoff471 = true;
                    boolean $retry466 = true;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled472) {
                            if ($doBackoff471) {
                                if ($backoff470 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff470);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e467) {
                                            
                                        }
                                    }
                                }
                                if ($backoff470 < 5000) $backoff470 *= 2;
                            }
                            $doBackoff471 = $backoff470 <= 32 || !$doBackoff471;
                        }
                        $commit465 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.valid()) {
                                tmp.refresh(true);
                            }
                            else if (!fabric.lang.Object._Proxy.
                                       idEquals(tmp.get$currentPolicy(),
                                                null)) {
                                tmp.get$currentPolicy().unapply(tmp);
                                tmp.set$currentPolicy(null);
                            }
                        }
                        catch (final fabric.worker.RetryException $e467) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e467) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468))
                                continue $label464;
                            if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468)) {
                                $retry466 = true;
                            }
                            else if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e467);
                        }
                        finally {
                            if ($commit465) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e467) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e467) {
                                    $commit465 = false;
                                    fabric.common.TransactionID $currentTid468 =
                                      $tm469.getCurrentTid();
                                    if ($currentTid468 != null) {
                                        if ($e467.tid.equals($currentTid468) ||
                                              !$e467.tid.isDescendantOf(
                                                           $currentTid468)) {
                                            throw $e467;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465 && $retry466) {
                                {  }
                                continue $label464;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Acquire reconfig locks starting from this contract.
   */
        public void acquireReconfigLocks() {  }
        
        /**
   * Create a {@link ConjunctionContract} with this and another contract.
   */
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof ProxyContract)
                other =
                  ((ProxyContract) fabric.lang.Object._Proxy.$getProxy(other)).
                    get$target();
            return ((fabric.metrics.contracts.ConjunctionContract)
                      new fabric.metrics.contracts.ConjunctionContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$ConjunctionContract$(
                new fabric.metrics.contracts.Contract[] { (fabric.metrics.contracts.Contract)
                                                            this.$getProxy(),
                  other });
        }
        
        /**
   * Create a proxy for this contract on the given store.
   */
        public fabric.metrics.contracts.Contract getProxyContract(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.Contract._Impl.
              static_getProxyContract((fabric.metrics.contracts.Contract)
                                        this.$getProxy(), proxyStore);
        }
        
        private static fabric.metrics.contracts.Contract
          static_getProxyContract(fabric.metrics.contracts.Contract tmp,
                                  final fabric.worker.Store proxyStore) {
            fabric.metrics.contracts.Contract proxy = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.$getStore().equals(proxyStore)) {
                    proxy = tmp;
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(tmp)) instanceof ProxyContract &&
                           ((ProxyContract)
                              fabric.lang.Object._Proxy.$getProxy(tmp)).
                           get$target().$getStore().equals(proxyStore)) {
                    proxy =
                      ((ProxyContract)
                         fabric.lang.Object._Proxy.$getProxy(tmp)).get$target();
                }
                else {
                    proxy =
                      ((ProxyContract)
                         new fabric.metrics.contracts.Contract.ProxyContract.
                           _Impl(proxyStore).
                         $getProxy()).
                        fabric$metrics$contracts$Contract$ProxyContract$(tmp);
                    proxy.refresh(false);
                }
            }
            else {
                {
                    fabric.metrics.contracts.Contract proxy$var473 = proxy;
                    fabric.worker.transaction.TransactionManager $tm479 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled482 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff480 = 1;
                    boolean $doBackoff481 = true;
                    boolean $retry476 = true;
                    $label474: for (boolean $commit475 = false; !$commit475; ) {
                        if ($backoffEnabled482) {
                            if ($doBackoff481) {
                                if ($backoff480 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff480);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e477) {
                                            
                                        }
                                    }
                                }
                                if ($backoff480 < 5000) $backoff480 *= 2;
                            }
                            $doBackoff481 = $backoff480 <= 32 || !$doBackoff481;
                        }
                        $commit475 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.$getStore().equals(proxyStore)) {
                                proxy = tmp;
                            }
                            else if (fabric.lang.Object._Proxy.
                                       $getProxy(
                                         (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             tmp)) instanceof ProxyContract &&
                                       ((ProxyContract)
                                          fabric.lang.Object._Proxy.$getProxy(
                                                                      tmp)).
                                       get$target().$getStore().equals(
                                                                  proxyStore)) {
                                proxy =
                                  ((ProxyContract)
                                     fabric.lang.Object._Proxy.$getProxy(tmp)).
                                    get$target();
                            }
                            else {
                                proxy =
                                  ((ProxyContract)
                                     new fabric.metrics.contracts.Contract.
                                       ProxyContract._Impl(proxyStore).
                                     $getProxy()).
                                    fabric$metrics$contracts$Contract$ProxyContract$(
                                      tmp);
                                proxy.refresh(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e477) {
                            $commit475 = false;
                            continue $label474;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e477) {
                            $commit475 = false;
                            fabric.common.TransactionID $currentTid478 =
                              $tm479.getCurrentTid();
                            if ($e477.tid.isDescendantOf($currentTid478))
                                continue $label474;
                            if ($currentTid478.parent != null) {
                                $retry476 = false;
                                throw $e477;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e477) {
                            $commit475 = false;
                            if ($tm479.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid478 =
                              $tm479.getCurrentTid();
                            if ($e477.tid.isDescendantOf($currentTid478)) {
                                $retry476 = true;
                            }
                            else if ($currentTid478.parent != null) {
                                $retry476 = false;
                                throw $e477;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e477) {
                            $commit475 = false;
                            if ($tm479.checkForStaleObjects())
                                continue $label474;
                            $retry476 = false;
                            throw new fabric.worker.AbortException($e477);
                        }
                        finally {
                            if ($commit475) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e477) {
                                    $commit475 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e477) {
                                    $commit475 = false;
                                    fabric.common.TransactionID $currentTid478 =
                                      $tm479.getCurrentTid();
                                    if ($currentTid478 != null) {
                                        if ($e477.tid.equals($currentTid478) ||
                                              !$e477.tid.isDescendantOf(
                                                           $currentTid478)) {
                                            throw $e477;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit475 && $retry476) {
                                { proxy = proxy$var473; }
                                continue $label474;
                            }
                        }
                    }
                }
            }
            return proxy;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.Contract._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.currentPolicy, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.currentPolicy =
              (fabric.
                metrics.
                contracts.
                enforcement.
                EnforcementPolicy)
                $readRef(
                  fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.currentPolicy = src.currentPolicy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$MIN_EXTENSION_FACTOR();
        
        public double set$MIN_EXTENSION_FACTOR(double val);
        
        public double postInc$MIN_EXTENSION_FACTOR();
        
        public double postDec$MIN_EXTENSION_FACTOR();
        
        public long get$DRIFT_FACTOR();
        
        public long set$DRIFT_FACTOR(long val);
        
        public long postInc$DRIFT_FACTOR();
        
        public long postDec$DRIFT_FACTOR();
        
        public long get$EXTENSION_WINDOW();
        
        public long set$EXTENSION_WINDOW(long val);
        
        public long postInc$EXTENSION_WINDOW();
        
        public long postDec$EXTENSION_WINDOW();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Contract._Static {
            public double get$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$MIN_EXTENSION_FACTOR();
            }
            
            public double set$MIN_EXTENSION_FACTOR(double val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$MIN_EXTENSION_FACTOR(val);
            }
            
            public double postInc$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$MIN_EXTENSION_FACTOR();
            }
            
            public double postDec$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$MIN_EXTENSION_FACTOR();
            }
            
            public long get$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$DRIFT_FACTOR();
            }
            
            public long set$DRIFT_FACTOR(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$DRIFT_FACTOR(val);
            }
            
            public long postInc$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$DRIFT_FACTOR();
            }
            
            public long postDec$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$DRIFT_FACTOR();
            }
            
            public long get$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$EXTENSION_WINDOW();
            }
            
            public long set$EXTENSION_WINDOW(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$EXTENSION_WINDOW(val);
            }
            
            public long postInc$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$EXTENSION_WINDOW();
            }
            
            public long postDec$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$EXTENSION_WINDOW();
            }
            
            public _Proxy(fabric.metrics.contracts.Contract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.Contract._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  Contract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.contracts.Contract._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.Contract._Static._Impl.class);
                $instance = (fabric.metrics.contracts.Contract._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.Contract._Static {
            public double get$MIN_EXTENSION_FACTOR() {
                return this.MIN_EXTENSION_FACTOR;
            }
            
            public double set$MIN_EXTENSION_FACTOR(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MIN_EXTENSION_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$MIN_EXTENSION_FACTOR() {
                double tmp = this.get$MIN_EXTENSION_FACTOR();
                this.set$MIN_EXTENSION_FACTOR((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$MIN_EXTENSION_FACTOR() {
                double tmp = this.get$MIN_EXTENSION_FACTOR();
                this.set$MIN_EXTENSION_FACTOR((double) (tmp - 1));
                return tmp;
            }
            
            public double MIN_EXTENSION_FACTOR;
            
            public long get$DRIFT_FACTOR() { return this.DRIFT_FACTOR; }
            
            public long set$DRIFT_FACTOR(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DRIFT_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$DRIFT_FACTOR() {
                long tmp = this.get$DRIFT_FACTOR();
                this.set$DRIFT_FACTOR((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$DRIFT_FACTOR() {
                long tmp = this.get$DRIFT_FACTOR();
                this.set$DRIFT_FACTOR((long) (tmp - 1));
                return tmp;
            }
            
            public long DRIFT_FACTOR;
            
            public long get$EXTENSION_WINDOW() { return this.EXTENSION_WINDOW; }
            
            public long set$EXTENSION_WINDOW(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EXTENSION_WINDOW = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$EXTENSION_WINDOW() {
                long tmp = this.get$EXTENSION_WINDOW();
                this.set$EXTENSION_WINDOW((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$EXTENSION_WINDOW() {
                long tmp = this.get$EXTENSION_WINDOW();
                this.set$EXTENSION_WINDOW((long) (tmp - 1));
                return tmp;
            }
            
            public long EXTENSION_WINDOW;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.MIN_EXTENSION_FACTOR);
                out.writeLong(this.DRIFT_FACTOR);
                out.writeLong(this.EXTENSION_WINDOW);
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
                this.MIN_EXTENSION_FACTOR = in.readDouble();
                this.DRIFT_FACTOR = in.readLong();
                this.EXTENSION_WINDOW = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm488 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled491 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff489 = 1;
                        boolean $doBackoff490 = true;
                        boolean $retry485 = true;
                        $label483: for (boolean $commit484 = false; !$commit484;
                                        ) {
                            if ($backoffEnabled491) {
                                if ($doBackoff490) {
                                    if ($backoff489 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff489);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e486) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff489 < 5000) $backoff489 *= 2;
                                }
                                $doBackoff490 = $backoff489 <= 32 ||
                                                  !$doBackoff490;
                            }
                            $commit484 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1.05);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e486) {
                                $commit484 = false;
                                continue $label483;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e486) {
                                $commit484 = false;
                                fabric.common.TransactionID $currentTid487 =
                                  $tm488.getCurrentTid();
                                if ($e486.tid.isDescendantOf($currentTid487))
                                    continue $label483;
                                if ($currentTid487.parent != null) {
                                    $retry485 = false;
                                    throw $e486;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e486) {
                                $commit484 = false;
                                if ($tm488.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid487 =
                                  $tm488.getCurrentTid();
                                if ($e486.tid.isDescendantOf($currentTid487)) {
                                    $retry485 = true;
                                }
                                else if ($currentTid487.parent != null) {
                                    $retry485 = false;
                                    throw $e486;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e486) {
                                $commit484 = false;
                                if ($tm488.checkForStaleObjects())
                                    continue $label483;
                                $retry485 = false;
                                throw new fabric.worker.AbortException($e486);
                            }
                            finally {
                                if ($commit484) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e486) {
                                        $commit484 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e486) {
                                        $commit484 = false;
                                        fabric.common.TransactionID
                                          $currentTid487 =
                                          $tm488.getCurrentTid();
                                        if ($currentTid487 != null) {
                                            if ($e486.tid.equals(
                                                            $currentTid487) ||
                                                  !$e486.tid.
                                                  isDescendantOf(
                                                    $currentTid487)) {
                                                throw $e486;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit484 && $retry485) {
                                    {  }
                                    continue $label483;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -72, 70, 32, -101, 103,
    13, 54, 122, -14, -73, 114, -28, -114, -50, 10, 79, -4, -61, -67, -91, -109,
    -5, 55, -91, -2, -2, -9, -71, 4, -71, -68, -64 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525113242000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
