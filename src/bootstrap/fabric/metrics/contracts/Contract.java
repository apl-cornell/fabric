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
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;

/**
 * A {@link Contract} represents an assertion that is enforced until an
 * expiration time, once {@link #refresh()}ed. If the current time is earlier
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
    
    public void
      refresh_remote(fabric.lang.security.Principal p,
                     boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
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
   * Use this instead of valid() if you're checking something that doesn't need
   * to stay true by the end of the transaction.
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
    public boolean refresh(boolean asyncExtension);
    
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
    public boolean refresh(boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Inner implementation of static_updateWithCurPolicy, to be run within a
   * transaction.
   *
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
    public byte updateWithCurPolicy(boolean asyncExtension,
                                    fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @return a new policy to enforce this treaty.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Attempt switching to a new policy (only switching if the policy enables the
   * treaty to be valid).
   *
   * @return true iff the updated expiry is a retraction of the preexisting
   * expiry.
   */
    public boolean switchToNewPolicy(
      fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy,
      boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.ImmutableObserverSet
      handleUpdates(boolean includesObserver, fabric.common.util.LongSet treaties);
    
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
        
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
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                return (ProxyContract) this.$getProxy();
            }
            
            public fabric.metrics.contracts.enforcement.EnforcementPolicy
              getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
                    new fabric.metrics.contracts.Contract[] { this.get$target(
                                                                     ) });
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
        
        public static final byte[] $classHash = new byte[] { 39, 103, -47, 115,
        59, -124, 33, -78, 47, 52, -125, -36, -70, -25, 96, 89, 104, -3, -103,
        46, 15, 114, -104, -41, 102, -52, -112, 79, 108, 29, -98, -119 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527105289000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYfWwcxRV/d3bOH3Fsx4kTcB3Hdo6gBPeuobQScVslPmF85Rwf/gjlosSZ2507b7y3u8zOxeeUIFqKkhYUoeCkiUpcCRkBwYCEhJBAkVKpH0GgSkFVaat+5B8EVZo/UNWPP0rpm9m927u9s0P+qKWbN555b+Z9/N6bmV2+AWtsBv0Zktb0CJ+3qB0ZJul4IkmYTdWYTmx7EkenlbX18bOfvKj2BCGYgBaFGKahKUSfNmwOrYkj5CiJGpRHp8bjgwegSRGCI8Se4RA8MFRg0GuZ+nxWN7m7SdX6Z+6KLvz4UPsbddCWgjbNmOCEa0rMNDgt8BS05GguTZm9V1WpmoL1BqXqBGUa0bVjyGgaKeiwtaxBeJ5Re5zapn5UMHbYeYsyuWdxUKhvotosr3CTofrtjvp5runRhGbzwQSEMhrVVfsReAzqE7Amo5MsMm5KFK2IyhWjw2Ic2Zs1VJNliEKLIvWzmqFy2OqXKFkcfgAZULQhR/mMWdqq3iA4AB2OSjoxstEJzjQji6xrzDzuwqFrxUWRqdEiyizJ0mkOt/n5ks4UcjVJtwgRDp1+NrkSxqzLF7OyaN3Y941T3zVGjCAEUGeVKrrQvxGFenxC4zRDGTUU6gi27EycJZsunQwCIHOnj9nheevRT/cM9Fy+4vB8qQbPWPoIVfi0spRuvdod23FvnVCj0TJtTUChwnIZ1aQ7M1iwEO2bSiuKyUhx8vL4Lx9+/CK9HoTmOIQUU8/nEFXrFTNnaTpl91ODMsKpGocmaqgxOR+HBuwnNIM6o2OZjE15HOp1ORQy5f/oogwuIVzUgH3NyJjFvkX4jOwXLADYgD+oAwgeBJiKIB0A2HeBQzI6Y+ZoNK3n6RzCO4o/SpgyE8W8ZZoStZkSZXmDa8jkDiGKkNhRhDpnROGIErcXQV2s/8OaBWFH+1wggC7eqpgqTRMb4+ViZyipY3qMmLpK2bSin7oUhw2Xzkv8NAnM24hb6aEAxrzbXy3KZRfyQ/d9+tr0ew72hKzrQA6uohFX0UhJ0UhR0XCSmYX54n+oaYtItAiWrgiWruVAIRJbjL8i8RSyZeKVFm/BxXdbOuEZk+UKEAhISzdKeQkkhMEslhesIC07Jg5++/DJfgxlwZqrx6AK1rA/n7wqFMcewSSZVtpOfPLP188eN73M4hCuSvhqSZGw/X63MVOhKhZEb/mdveTN6UvHw0FRbJqEfwgiFYtKj3+PisQdLBZB4Y01CVgrfEB0MVWsXM18hplz3oiEQ6toOhxkCGf5FJT185sT1oXf/fqvX5UnS7HUtpXV5AnKB8vSWyzWJhN5vef7SUYp8v3pXPLZMzdOHJCOR45ttTYMizaGaU0wn0325JVHfv+XPy/9JugFi0OTxUyONYaqBWnO+s/xL4C//4qfSFMxICgW65hbInpLNcISm2/31MNqoeNqqL0dnjJypqplNJLWqQDLf9ru2PXm3061OxHXccTxH4OBmy/gjd8+BI+/d+hfPXKZgCJOK8+FHptTAjd4K+9ljMwLPQrf+2DL+V+RCwh+LGC2dozKmgTSJSBjeLf0xZdlu8s3d49o+h1vdZcw7z8OhsW56sExFV1+riv2retOHSjBUazRV6MO7CdlmXL3xdw/gv2hXwShIQXt8kgnBt9PsKAhElJ4KNsxdzAB6yrmKw9Y5zQZLKVbtz8Vyrb1J4JXf7AvuEW/2cG+Axx0xEbhpDCW8yjA2GaXNojZDZZoNxYCIDu7pcg22W4XzQ7pyKDo7kRQarlcnouwyw3u4hDihGUplwKdHPpuWv8EY5dMx8Lqe2H1ExewQsmIoDCiwz2TnnPp02VGlEUeChj6LStdH+TVZ+n7C4vq2Au7nEO+o/JIvs/I51797WfvR85de7dGmQ+5l0FvwxDu11d1iR2VVysPMdeub7k3NvtR1tlzq08/P/fLo8vv3r9dOR2EuhI0qu5zlUKDlYBoZhSvo8ZkBSx6Sx5dKzyVQk9+DeFwxaXz5bBw6mbNOGGNCln5tF4eIun6ZnehgkuZP0Re+gaclcS/e+Re+1fJ7++IZozDVxyMhV2MhUsYC9c+Y8OeEYmSpq1i2T2o4RDAg1dd+tMvarqEqM/sde4iiy49c1Ozi0nT4ybNnMlmKSvljniA2KPEkmy3+49/qd/hVdyVEU0KxTA999G5pImRmi9u+fUV85Ti3ZApNEcNjllQ6jviMnl9npQgGkejR5D8waXPruBJ0YxXw0WInHZpVUZ75tVJ8+qKRnT6jBiV1NGxuvAnmZbD4/uo+w6gJxd+9Hnk1IKT3c5jaVvVe6VcxnkwSWvWyeonakzfartIieGPXz/+zkvHTwTduIxg3qgmJo5MSS7HzFXiKJ1+hEODlrN0jdqSibkKCJLHubRp6pQYtUIzjH4dBZh41KV7by00QmSPS3evHJpyjZ9YZe5J0TyG1ytEZYKSzERenn12MaYDtXMhXjx3nBjb+6l8OtfMjFpOuBMtmAKYPObSQ7fmBCFy0KUP3UI5e2YVT5wWzVMc6oihSolaenfjpligJz9w6c9uTW8hctmlb3+x4J1fZe4nolng0MhN55tAMWrt8jYn7jKRsonqmsVhXUVpduzGJ3aN15r7FUGJ/ZwuffTAQOcKL7Xbqr7ruHKvLbY1bl6c+lA+NEpfCJrwHp/J63r5ramsH7IYzWjS2CbnDmVJsoRP+pVKJnfujbIv7X7ekXmRQ2ulDJcfW0SvnO8iFgSHT/z3ioxQV2XjoKkrz8THrOW/b/53qHHymnwuYGh678xetQd/0PdG9J4n/vj2x4cfnvnsfKSNnfsw8/4zY/qWxR/+D6EO1f9kEwAA";
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
        
        public void refresh_remote(fabric.lang.security.Principal arg1,
                                   boolean arg2,
                                   fabric.worker.metrics.StatsMap arg3) {
            ((fabric.metrics.contracts.Contract) fetch()).refresh_remote(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public static final java.lang.Class[] $paramTypes2 = { boolean.class,
        fabric.worker.metrics.StatsMap.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2, arg3);
            else
                try {
                    $remoteWorker.issueRemoteCall(this,
                                                  "refresh",
                                                  $paramTypes2,
                                                  new java.lang.Object[] { arg2,
                                                    arg3 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
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
        
        public boolean refresh(boolean arg1,
                               fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).refresh(arg1,
                                                                         arg2);
        }
        
        public static byte static_updateWithCurPolicy(
          fabric.metrics.contracts.Contract arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.contracts.Contract._Impl.
              static_updateWithCurPolicy(arg1, arg2, arg3);
        }
        
        public byte updateWithCurPolicy(boolean arg1,
                                        fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              updateWithCurPolicy(arg1, arg2);
        }
        
        public static boolean updateToNewPolicy(
          fabric.metrics.contracts.Contract arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.contracts.Contract._Impl.updateToNewPolicy(
                                                             arg1, arg2, arg3);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).getNewPolicy(
                                                                   arg1);
        }
        
        public boolean switchToNewPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg1,
          boolean arg2, fabric.worker.metrics.StatsMap arg3) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              switchToNewPolicy(arg1, arg2, arg3);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).handleUpdates(
                                                                   arg1, arg2);
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
        
        public static final java.lang.Class[] $paramTypes3 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes3, null);
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
        
        public void refresh_remote(fabric.lang.security.Principal p,
                                   boolean asyncExtension,
                                   fabric.worker.metrics.StatsMap weakStats) {
            this.refresh(asyncExtension, weakStats);
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
                        tmp.set$$associated(null);
                    }
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm414 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled417 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff415 = 1;
                    boolean $doBackoff416 = true;
                    boolean $retry411 = true;
                    $label409: for (boolean $commit410 = false; !$commit410; ) {
                        if ($backoffEnabled417) {
                            if ($doBackoff416) {
                                if ($backoff415 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff415);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e412) {
                                            
                                        }
                                    }
                                }
                                if ($backoff415 < 5000) $backoff415 *= 2;
                            }
                            $doBackoff416 = $backoff415 <= 32 || !$doBackoff416;
                        }
                        $commit410 = true;
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
                                    tmp.set$$associated(null);
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e412) {
                            $commit410 = false;
                            continue $label409;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e412) {
                            $commit410 = false;
                            fabric.common.TransactionID $currentTid413 =
                              $tm414.getCurrentTid();
                            if ($e412.tid.isDescendantOf($currentTid413))
                                continue $label409;
                            if ($currentTid413.parent != null) {
                                $retry411 = false;
                                throw $e412;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e412) {
                            $commit410 = false;
                            if ($tm414.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid413 =
                              $tm414.getCurrentTid();
                            if ($e412.tid.isDescendantOf($currentTid413)) {
                                $retry411 = true;
                            }
                            else if ($currentTid413.parent != null) {
                                $retry411 = false;
                                throw $e412;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e412) {
                            $commit410 = false;
                            if ($tm414.checkForStaleObjects())
                                continue $label409;
                            $retry411 = false;
                            throw new fabric.worker.AbortException($e412);
                        }
                        finally {
                            if ($commit410) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e412) {
                                    $commit410 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e412) {
                                    $commit410 = false;
                                    fabric.common.TransactionID $currentTid413 =
                                      $tm414.getCurrentTid();
                                    if ($currentTid413 != null) {
                                        if ($e412.tid.equals($currentTid413) ||
                                              !$e412.tid.isDescendantOf(
                                                           $currentTid413)) {
                                            throw $e412;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit410 && $retry411) {
                                {  }
                                continue $label409;
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
   * Use this instead of valid() if you're checking something that doesn't need
   * to stay true by the end of the transaction.
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
        public boolean refresh(boolean asyncExtension) {
            return refresh(asyncExtension,
                           fabric.worker.metrics.StatsMap.emptyStats());
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
        public boolean refresh(boolean asyncExtension,
                               fabric.worker.metrics.StatsMap weakStats) {
            switch (fabric.metrics.contracts.Contract._Impl.
                      static_updateWithCurPolicy(
                        (fabric.metrics.contracts.Contract) this.$getProxy(),
                        asyncExtension, weakStats)) {
                case 0:
                case 1:
                    return !asyncExtension &&
                      fabric.metrics.contracts.Contract._Impl.
                      updateToNewPolicy((fabric.metrics.contracts.Contract)
                                          this.$getProxy(), asyncExtension,
                                        weakStats);
                case 2:
                    return false;
                case 3:
                default:
                    return true;
            }
        }
        
        /**
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
        public static byte static_updateWithCurPolicy(
          fabric.metrics.contracts.Contract tmp, boolean asyncExtension,
          fabric.worker.metrics.StatsMap weakStats) {
            byte result =
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$NO_POLICY();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.updateWithCurPolicy(asyncExtension, weakStats);
            }
            else {
                {
                    byte result$var418 = result;
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry421 = true;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff425);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            result = tmp.updateWithCurPolicy(asyncExtension,
                                                             weakStats);
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label419;
                            if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry421 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label419;
                            $retry421 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit420 = false;
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit420 && $retry421) {
                                { result = result$var418; }
                                continue $label419;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        /**
   * Inner implementation of static_updateWithCurPolicy, to be run within a
   * transaction.
   *
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
        public byte updateWithCurPolicy(
          boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats) {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long updatedExpiry = this.get$currentPolicy().expiry(weakStats);
                if (updatedExpiry >= java.lang.System.currentTimeMillis()) {
                    if (this.update(updatedExpiry, asyncExtension)) {
                        this.get$currentPolicy().
                          apply(
                            (fabric.metrics.contracts.Contract) this.$getProxy());
                        return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                          get$POLICY_GOOD_RETRACTED();
                    }
                    else {
                        this.get$currentPolicy().
                          apply(
                            (fabric.metrics.contracts.Contract) this.$getProxy());
                        return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                          get$POLICY_GOOD_EXTENDED();
                    }
                } else {
                    return fabric.metrics.contracts.Contract._Static._Proxy.$instance.get$POLICY_BAD();
                }
            }
            return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$NO_POLICY();
        }
        
        /**
   * Update the treaty to use a new policy (or none if the treaty is no longer
   * valid).
   *
   * @return true iff the updated expiry is a retraction from the preexisting
   * expiry.
   */
        public static boolean updateToNewPolicy(
          fabric.metrics.contracts.Contract tmp, boolean asyncExtension,
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.markCoordination();
            if (tm.inTxn()) {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy = tmp.getNewPolicy(weakStats);
                newPolicy.activate(weakStats);
                return tmp.switchToNewPolicy(newPolicy, asyncExtension,
                                             weakStats);
            }
            else {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy = null;
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      newPolicy$var428 = newPolicy;
                    fabric.worker.transaction.TransactionManager $tm434 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled437 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff435 = 1;
                    boolean $doBackoff436 = true;
                    boolean $retry431 = true;
                    $label429: for (boolean $commit430 = false; !$commit430; ) {
                        if ($backoffEnabled437) {
                            if ($doBackoff436) {
                                if ($backoff435 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff435);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e432) {
                                            
                                        }
                                    }
                                }
                                if ($backoff435 < 5000) $backoff435 *= 2;
                            }
                            $doBackoff436 = $backoff435 <= 32 || !$doBackoff436;
                        }
                        $commit430 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { newPolicy = tmp.getNewPolicy(weakStats); }
                        catch (final fabric.worker.RetryException $e432) {
                            $commit430 = false;
                            continue $label429;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e432) {
                            $commit430 = false;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433))
                                continue $label429;
                            if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433)) {
                                $retry431 = true;
                            }
                            else if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects())
                                continue $label429;
                            $retry431 = false;
                            throw new fabric.worker.AbortException($e432);
                        }
                        finally {
                            if ($commit430) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e432) {
                                    $commit430 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e432) {
                                    $commit430 = false;
                                    fabric.common.TransactionID $currentTid433 =
                                      $tm434.getCurrentTid();
                                    if ($currentTid433 != null) {
                                        if ($e432.tid.equals($currentTid433) ||
                                              !$e432.tid.isDescendantOf(
                                                           $currentTid433)) {
                                            throw $e432;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit430 && $retry431) {
                                { newPolicy = newPolicy$var428; }
                                continue $label429;
                            }
                        }
                    }
                }
                newPolicy.activate(weakStats);
                boolean result = false;
                {
                    boolean result$var438 = result;
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      newPolicy$var439 = newPolicy;
                    fabric.worker.transaction.TransactionManager $tm445 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled448 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff446 = 1;
                    boolean $doBackoff447 = true;
                    boolean $retry442 = true;
                    $label440: for (boolean $commit441 = false; !$commit441; ) {
                        if ($backoffEnabled448) {
                            if ($doBackoff447) {
                                if ($backoff446 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff446);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e443) {
                                            
                                        }
                                    }
                                }
                                if ($backoff446 < 5000) $backoff446 *= 2;
                            }
                            $doBackoff447 = $backoff446 <= 32 || !$doBackoff447;
                        }
                        $commit441 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            result = tmp.switchToNewPolicy(newPolicy,
                                                           asyncExtension,
                                                           weakStats);
                        }
                        catch (final fabric.worker.RetryException $e443) {
                            $commit441 = false;
                            continue $label440;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e443) {
                            $commit441 = false;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444))
                                continue $label440;
                            if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444)) {
                                $retry442 = true;
                            }
                            else if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects())
                                continue $label440;
                            $retry442 = false;
                            throw new fabric.worker.AbortException($e443);
                        }
                        finally {
                            if ($commit441) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e443) {
                                    $commit441 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e443) {
                                    $commit441 = false;
                                    fabric.common.TransactionID $currentTid444 =
                                      $tm445.getCurrentTid();
                                    if ($currentTid444 != null) {
                                        if ($e443.tid.equals($currentTid444) ||
                                              !$e443.tid.isDescendantOf(
                                                           $currentTid444)) {
                                            throw $e443;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit441 && $retry442) {
                                {
                                    result = result$var438;
                                    newPolicy = newPolicy$var439;
                                }
                                continue $label440;
                            }
                        }
                    }
                }
                return result;
            }
        }
        
        /**
   * @return a new policy to enforce this treaty.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * Attempt switching to a new policy (only switching if the policy enables the
   * treaty to be valid).
   *
   * @return true iff the updated expiry is a retraction of the preexisting
   * expiry.
   */
        public boolean switchToNewPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy,
          boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            long newExpiry = newPolicy.expiry(weakStats);
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply((fabric.metrics.contracts.Contract)
                                    this.$getProxy());
            boolean result = this.update(newExpiry, asyncExtension);
            if (newExpiry >= java.lang.System.currentTimeMillis()) {
                this.set$currentPolicy(newPolicy);
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().apply(
                                           (fabric.metrics.contracts.Contract)
                                             this.$getProxy());
            } else {
                newPolicy.unapply((fabric.metrics.contracts.Contract)
                                    this.$getProxy());
                this.set$currentPolicy(null);
                this.set$$associated(null);
            }
            return result;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            if (includesObserver) {
                if (!stale()) {
                    if (refresh(false)) return getObservers();
                    return fabric.worker.metrics.ImmutableObserverSet.emptySet(
                                                                        );
                }
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    this.get$currentPolicy().
                      unapply((fabric.metrics.contracts.Contract)
                                this.$getProxy());
                    this.set$currentPolicy(null);
                    this.set$$associated(null);
                    return getObservers();
                }
            }
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
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
                    tmp.set$$associated(null);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm454 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled457 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff455 = 1;
                    boolean $doBackoff456 = true;
                    boolean $retry451 = true;
                    $label449: for (boolean $commit450 = false; !$commit450; ) {
                        if ($backoffEnabled457) {
                            if ($doBackoff456) {
                                if ($backoff455 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff455);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e452) {
                                            
                                        }
                                    }
                                }
                                if ($backoff455 < 5000) $backoff455 *= 2;
                            }
                            $doBackoff456 = $backoff455 <= 32 || !$doBackoff456;
                        }
                        $commit450 = true;
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
                                tmp.set$$associated(null);
                            }
                        }
                        catch (final fabric.worker.RetryException $e452) {
                            $commit450 = false;
                            continue $label449;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e452) {
                            $commit450 = false;
                            fabric.common.TransactionID $currentTid453 =
                              $tm454.getCurrentTid();
                            if ($e452.tid.isDescendantOf($currentTid453))
                                continue $label449;
                            if ($currentTid453.parent != null) {
                                $retry451 = false;
                                throw $e452;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e452) {
                            $commit450 = false;
                            if ($tm454.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid453 =
                              $tm454.getCurrentTid();
                            if ($e452.tid.isDescendantOf($currentTid453)) {
                                $retry451 = true;
                            }
                            else if ($currentTid453.parent != null) {
                                $retry451 = false;
                                throw $e452;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e452) {
                            $commit450 = false;
                            if ($tm454.checkForStaleObjects())
                                continue $label449;
                            $retry451 = false;
                            throw new fabric.worker.AbortException($e452);
                        }
                        finally {
                            if ($commit450) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e452) {
                                    $commit450 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e452) {
                                    $commit450 = false;
                                    fabric.common.TransactionID $currentTid453 =
                                      $tm454.getCurrentTid();
                                    if ($currentTid453 != null) {
                                        if ($e452.tid.equals($currentTid453) ||
                                              !$e452.tid.isDescendantOf(
                                                           $currentTid453)) {
                                            throw $e452;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit450 && $retry451) {
                                {  }
                                continue $label449;
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
                    fabric.metrics.contracts.Contract proxy$var458 = proxy;
                    fabric.worker.transaction.TransactionManager $tm464 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled467 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff465 = 1;
                    boolean $doBackoff466 = true;
                    boolean $retry461 = true;
                    $label459: for (boolean $commit460 = false; !$commit460; ) {
                        if ($backoffEnabled467) {
                            if ($doBackoff466) {
                                if ($backoff465 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff465);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e462) {
                                            
                                        }
                                    }
                                }
                                if ($backoff465 < 5000) $backoff465 *= 2;
                            }
                            $doBackoff466 = $backoff465 <= 32 || !$doBackoff466;
                        }
                        $commit460 = true;
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
                        catch (final fabric.worker.RetryException $e462) {
                            $commit460 = false;
                            continue $label459;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e462) {
                            $commit460 = false;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463))
                                continue $label459;
                            if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463)) {
                                $retry461 = true;
                            }
                            else if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects())
                                continue $label459;
                            $retry461 = false;
                            throw new fabric.worker.AbortException($e462);
                        }
                        finally {
                            if ($commit460) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e462) {
                                    $commit460 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e462) {
                                    $commit460 = false;
                                    fabric.common.TransactionID $currentTid463 =
                                      $tm464.getCurrentTid();
                                    if ($currentTid463 != null) {
                                        if ($e462.tid.equals($currentTid463) ||
                                              !$e462.tid.isDescendantOf(
                                                           $currentTid463)) {
                                            throw $e462;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit460 && $retry461) {
                                { proxy = proxy$var458; }
                                continue $label459;
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
        public long get$DRIFT_FACTOR();
        
        public long set$DRIFT_FACTOR(long val);
        
        public long postInc$DRIFT_FACTOR();
        
        public long postDec$DRIFT_FACTOR();
        
        public long get$EXTENSION_WINDOW();
        
        public long set$EXTENSION_WINDOW(long val);
        
        public long postInc$EXTENSION_WINDOW();
        
        public long postDec$EXTENSION_WINDOW();
        
        public byte get$NO_POLICY();
        
        public byte set$NO_POLICY(byte val);
        
        public byte postInc$NO_POLICY();
        
        public byte postDec$NO_POLICY();
        
        public byte get$POLICY_BAD();
        
        public byte set$POLICY_BAD(byte val);
        
        public byte postInc$POLICY_BAD();
        
        public byte postDec$POLICY_BAD();
        
        public byte get$POLICY_GOOD_EXTENDED();
        
        public byte set$POLICY_GOOD_EXTENDED(byte val);
        
        public byte postInc$POLICY_GOOD_EXTENDED();
        
        public byte postDec$POLICY_GOOD_EXTENDED();
        
        public byte get$POLICY_GOOD_RETRACTED();
        
        public byte set$POLICY_GOOD_RETRACTED(byte val);
        
        public byte postInc$POLICY_GOOD_RETRACTED();
        
        public byte postDec$POLICY_GOOD_RETRACTED();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Contract._Static {
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
            
            public byte get$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$NO_POLICY();
            }
            
            public byte set$NO_POLICY(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$NO_POLICY(val);
            }
            
            public byte postInc$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$NO_POLICY();
            }
            
            public byte postDec$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$NO_POLICY();
            }
            
            public byte get$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_BAD();
            }
            
            public byte set$POLICY_BAD(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_BAD(val);
            }
            
            public byte postInc$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_BAD();
            }
            
            public byte postDec$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_BAD();
            }
            
            public byte get$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_GOOD_EXTENDED();
            }
            
            public byte set$POLICY_GOOD_EXTENDED(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_GOOD_EXTENDED(val);
            }
            
            public byte postInc$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_GOOD_EXTENDED();
            }
            
            public byte postDec$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_GOOD_EXTENDED();
            }
            
            public byte get$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_GOOD_RETRACTED();
            }
            
            public byte set$POLICY_GOOD_RETRACTED(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_GOOD_RETRACTED(val);
            }
            
            public byte postInc$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_GOOD_RETRACTED();
            }
            
            public byte postDec$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_GOOD_RETRACTED();
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
            
            public byte get$NO_POLICY() { return this.NO_POLICY; }
            
            public byte set$NO_POLICY(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.NO_POLICY = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$NO_POLICY() {
                byte tmp = this.get$NO_POLICY();
                this.set$NO_POLICY((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$NO_POLICY() {
                byte tmp = this.get$NO_POLICY();
                this.set$NO_POLICY((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte NO_POLICY;
            
            public byte get$POLICY_BAD() { return this.POLICY_BAD; }
            
            public byte set$POLICY_BAD(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_BAD = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_BAD() {
                byte tmp = this.get$POLICY_BAD();
                this.set$POLICY_BAD((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_BAD() {
                byte tmp = this.get$POLICY_BAD();
                this.set$POLICY_BAD((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_BAD;
            
            public byte get$POLICY_GOOD_EXTENDED() {
                return this.POLICY_GOOD_EXTENDED;
            }
            
            public byte set$POLICY_GOOD_EXTENDED(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_GOOD_EXTENDED = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_GOOD_EXTENDED() {
                byte tmp = this.get$POLICY_GOOD_EXTENDED();
                this.set$POLICY_GOOD_EXTENDED((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_GOOD_EXTENDED() {
                byte tmp = this.get$POLICY_GOOD_EXTENDED();
                this.set$POLICY_GOOD_EXTENDED((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_GOOD_EXTENDED;
            
            public byte get$POLICY_GOOD_RETRACTED() {
                return this.POLICY_GOOD_RETRACTED;
            }
            
            public byte set$POLICY_GOOD_RETRACTED(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_GOOD_RETRACTED = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_GOOD_RETRACTED() {
                byte tmp = this.get$POLICY_GOOD_RETRACTED();
                this.set$POLICY_GOOD_RETRACTED((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_GOOD_RETRACTED() {
                byte tmp = this.get$POLICY_GOOD_RETRACTED();
                this.set$POLICY_GOOD_RETRACTED((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_GOOD_RETRACTED;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.DRIFT_FACTOR);
                out.writeLong(this.EXTENSION_WINDOW);
                out.writeByte(this.NO_POLICY);
                out.writeByte(this.POLICY_BAD);
                out.writeByte(this.POLICY_GOOD_EXTENDED);
                out.writeByte(this.POLICY_GOOD_RETRACTED);
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
                this.DRIFT_FACTOR = in.readLong();
                this.EXTENSION_WINDOW = in.readLong();
                this.NO_POLICY = in.readByte();
                this.POLICY_BAD = in.readByte();
                this.POLICY_GOOD_EXTENDED = in.readByte();
                this.POLICY_GOOD_RETRACTED = in.readByte();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm473 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled476 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff474 = 1;
                        boolean $doBackoff475 = true;
                        boolean $retry470 = true;
                        $label468: for (boolean $commit469 = false; !$commit469;
                                        ) {
                            if ($backoffEnabled476) {
                                if ($doBackoff475) {
                                    if ($backoff474 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff474);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e471) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff474 < 5000) $backoff474 *= 2;
                                }
                                $doBackoff475 = $backoff474 <= 32 ||
                                                  !$doBackoff475;
                            }
                            $commit469 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$NO_POLICY((byte) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_BAD((byte) 1);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_GOOD_EXTENDED((byte) 2);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_GOOD_RETRACTED((byte) 3);
                            }
                            catch (final fabric.worker.RetryException $e471) {
                                $commit469 = false;
                                continue $label468;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e471) {
                                $commit469 = false;
                                fabric.common.TransactionID $currentTid472 =
                                  $tm473.getCurrentTid();
                                if ($e471.tid.isDescendantOf($currentTid472))
                                    continue $label468;
                                if ($currentTid472.parent != null) {
                                    $retry470 = false;
                                    throw $e471;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e471) {
                                $commit469 = false;
                                if ($tm473.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid472 =
                                  $tm473.getCurrentTid();
                                if ($e471.tid.isDescendantOf($currentTid472)) {
                                    $retry470 = true;
                                }
                                else if ($currentTid472.parent != null) {
                                    $retry470 = false;
                                    throw $e471;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e471) {
                                $commit469 = false;
                                if ($tm473.checkForStaleObjects())
                                    continue $label468;
                                $retry470 = false;
                                throw new fabric.worker.AbortException($e471);
                            }
                            finally {
                                if ($commit469) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e471) {
                                        $commit469 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e471) {
                                        $commit469 = false;
                                        fabric.common.TransactionID
                                          $currentTid472 =
                                          $tm473.getCurrentTid();
                                        if ($currentTid472 != null) {
                                            if ($e471.tid.equals(
                                                            $currentTid472) ||
                                                  !$e471.tid.
                                                  isDescendantOf(
                                                    $currentTid472)) {
                                                throw $e471;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit469 && $retry470) {
                                    {  }
                                    continue $label468;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 70, -96, 102, 55, -107,
    70, -22, -21, -10, 27, -6, 83, 39, -20, -41, -38, 76, 26, -115, -56, -19,
    -20, -49, -102, -111, 18, 59, 123, 56, -22, -7, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527105289000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
