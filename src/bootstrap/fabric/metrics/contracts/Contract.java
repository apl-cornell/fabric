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
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

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
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
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
                        this.set$$associated(null);
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
        
        public static final byte[] $classHash = new byte[] { -126, 92, -45, 115,
        -50, -24, -54, 13, 6, 58, -69, 14, 44, 12, 40, 31, -56, -56, 92, 38, 16,
        -50, -89, -121, 21, 123, 75, -89, -99, -33, 52, 60 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526752484000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYXWwcVxU+u3bWP3Fsx4nT1nUcx1kCScwuCX1pnJbG27hZsiGWnQTYlLp3Z+7aU8/OTO7cjTdtE4UKlBREhIrrtgJHlTAEimklUMVDFdSHFlwKkfgrP1LbvFQEhTxUiJ8HoJxzZ/Zvdu3gB1aae+/ce8695/e7Z3bxJqxxBQxkWcYwY/K0w93YCMskU6NMuFxPmMx1j+LshLa2MTl3/bLeF4ZwCto0ZtmWoTFzwnIltKceYadY3OIyfmwsOXQCWjRiPMjcKQnhE8MFAf2ObZ6eNG3pH1Kz/9O74rPPPNT5gwboSEOHYY1LJg0tYVuSF2Qa2nI8l+HC3a/rXE/DeotzfZwLg5nGo0hoW2noco1Ji8m84O4Yd23zFBF2uXmHC3VmcZLEt1FskdekLVD8Tk/8vDTMeMpw5VAKIlmDm7p7Es5CYwrWZE02iYSbUkUt4mrH+AjNI3mrgWKKLNN4kaVx2rB0CVuCHCWNo4eQAFmbclxO2aWjGi2GE9DliWQyazI+LoVhTSLpGjuPp0joWXZTJGp2mDbNJvmEhNuDdKPeElK1KLMQi4TuIJnaCX3WE/BZhbdufmrfxcesg1YYQiizzjWT5G9Gpr4A0xjPcsEtjXuMbTtTc2zTlQthACTuDhB7ND96/P37BvteXfJo7qxDcyTzCNfkhLaQaf9lb2LH3Q0kRrNjuwaFQpXmyquj/spQwcFo31TakRZjxcVXx37y2XMv8BthaE1CRLPNfA6jar1m5xzD5OIBbnHBJNeT0MItPaHWk9CE45RhcW/2SDbrcpmERlNNRWz1jibK4hZkoiYcG1bWLo4dJqfUuOAAwAZ8oAEg9DbA/XuwnwfYNyhhND5l53g8Y+b5DIZ3HB/OhDYVx7wVhhZ3hRYXeUsaSORPYRRh58Yx1KVgmsQo8UcxlMX5P+xZID06Z0IhNPEWzdZ5hrnoLz92hkdNTI+DtqlzMaGZF68kYcOV51T8tFDMuxi3ykIh9HlvEC0qeWfzwwfef3HiTS/2iNc3oARf0JgvaKwkaKwoaHRU2IXTxTeUtI0SLYbQFUPoWgwVYolLye+peIq4KvFKm7fh5nsdk8msLXIFCIWUphsVvwokDINphBdEkLYd45/75MMXBtCVBWemEZ1KpNFgPpVRKIkjhkkyoXWcv/73l+bO2OXMkhCtSfhaTkrYgaDZhK1xHQGxvP3OfvbyxJUz0TCBTQvZh2GkIqj0Bc+oStyhIgiSNdakYC3ZgJm0VESuVjkl7JnyjAqHdmq6vMggYwUEVPh5z7gz//urf/64ulmKUNtRgcnjXA5VpDdt1qESeX3Z9kcF50j39rOjX3v65vkTyvBIsa3egVFqE5jWDPPZFl9cOvmHd99Z+E247CwJLY6wJWIM1wtKnfUf4C+Ez3/ooTSlCeoRrBM+RPSXMMKhw7eXxUO0MHE3lN6NHrNytm5kDZYxOQXLvzo+tPvlv1zs9Dxu4oxnPwGDt96gPH/HMJx786F/9KltQhrdVmUTlsk8CNxQ3nm/EOw0yVH4/K82P/dTNo/BjwDmGo9yhUmgTALKh3uULT6q2t2BtbuoGfCs1VuK+eB1MEL3ajkc0/HFb/Qk7r3h4UApHGmPrXVw4DiryJQ9L+T+Fh6IvB6GpjR0qiudWfI4Q0DDSEjjpewm/MkUrKtar75gvdtkqJRuvcFUqDg2mAhl/MExUdO41Yt9L3DQEBvJSFE0yPMI59/2+zla3eBQu7EQAjXYq1i2qXY7NTuUIcM03IlBaeRyeUluVwfskhCRTExyqRi6JWy9Jf4RYY9Kx8LKZyH6UQFWKCkRJiW6/Dtpl9/fWaFEheehgK7fvFz5oEqfhSdmL+lHvrXbu+S7qq/kA1Y+9/23/v3z2LPX3qgD8xG/GCwfGMHzttYUsYdVaVWOmGs3Nt+dmH5v0jtzS0C+IPV3Dy++8cB27akwNJRCo6aeq2Yaqg6IVsGxHLWOVoVFf8mia8lSabQkhsQ9n/b75sqw8HCzrp8QoyJOPmNWukiZvtXfqMnvQ0EXldM35O1Er/eps46vkN+foeaIhI95MRb1YyxairFo/Ts2WlYiVa06FTg/BPjEmN/vWkZ1asZqlSSWnX4fvaWSdbBoVBg5vFFO+aUpvzD7pQ9iF2e9gPPq9201JXQlj1fDK1HXqYSksN+60imKY+RPL5155Ttnzod9yx6U0JSxbZMzS71PrOAFVY6kkUHwLFZsqnbM1DMuGXUJYP87fv/M6oxLLHN+/9XljdugRGso4k93AH8Oq15hjiJ52LcSdXhDRXQbI1jlxkklk7WC5nlqDNTcyDmm4RUZdTUfQbF/ATB8zu8PrE5zYrnf7+9dXvNK0R5fYe0sNTNY0CBMpzjLjufVbeMWTTbom2zGFtNclCyXLCK9Z0L3OFcfq8R0R7AUrWeED6MGvwVInPX7zOqMQCzM70+sAkCeXMESX6bmCxIamKUrjnpy9+J2f8RD3/L711cnN7G85vc//t+c99QKa7PUfEVCs7S9r/Ci1zpV/UTVQ6xiocYzBQnrqsDQ0xs/aut8H/nf7VriNb7w3qHB7mW+jW6v+SfF53vxUkfzbZeO/U6V9qVv8hasnLN506ysUyrGEQeBxFDKtnhVi6O6efyIXq6YkF6lpsZK7697PM9LaK/mkervDRpV0n0TM9+jo7cF5aGe6saLpp68oL+PFv962z8jzUevqQIdXdP/xIO/dq9e/9m6yN5X2gfbPrJlaenB7Z1XL5/vfuzQ5fl379r3X5y6zKHWEgAA";
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
                                   boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).refresh_remote(arg1,
                                                                         arg2);
        }
        
        public static final java.lang.Class[] $paramTypes2 = { boolean.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "refresh", $paramTypes2,
                                    new java.lang.Object[] { arg2 });
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
                                   boolean asyncExtension) {
            this.refresh(asyncExtension);
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
                this.set$$associated(null);
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
                    tmp.set$$associated(null);
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
                    fabric.metrics.contracts.Contract proxy$var464 = proxy;
                    fabric.worker.transaction.TransactionManager $tm470 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled473 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff471 = 1;
                    boolean $doBackoff472 = true;
                    boolean $retry467 = true;
                    $label465: for (boolean $commit466 = false; !$commit466; ) {
                        if ($backoffEnabled473) {
                            if ($doBackoff472) {
                                if ($backoff471 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff471);
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
                        $commit466 = true;
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
                        catch (final fabric.worker.RetryException $e468) {
                            $commit466 = false;
                            continue $label465;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e468) {
                            $commit466 = false;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469))
                                continue $label465;
                            if ($currentTid469.parent != null) {
                                $retry467 = false;
                                throw $e468;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e468) {
                            $commit466 = false;
                            if ($tm470.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469)) {
                                $retry467 = true;
                            }
                            else if ($currentTid469.parent != null) {
                                $retry467 = false;
                                throw $e468;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e468) {
                            $commit466 = false;
                            if ($tm470.checkForStaleObjects())
                                continue $label465;
                            $retry467 = false;
                            throw new fabric.worker.AbortException($e468);
                        }
                        finally {
                            if ($commit466) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e468) {
                                    $commit466 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e468) {
                                    $commit466 = false;
                                    fabric.common.TransactionID $currentTid469 =
                                      $tm470.getCurrentTid();
                                    if ($currentTid469 != null) {
                                        if ($e468.tid.equals($currentTid469) ||
                                              !$e468.tid.isDescendantOf(
                                                           $currentTid469)) {
                                            throw $e468;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit466 && $retry467) {
                                { proxy = proxy$var464; }
                                continue $label465;
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
                        fabric.worker.transaction.TransactionManager $tm479 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled482 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff480 = 1;
                        boolean $doBackoff481 = true;
                        boolean $retry476 = true;
                        $label474: for (boolean $commit475 = false; !$commit475;
                                        ) {
                            if ($backoffEnabled482) {
                                if ($doBackoff481) {
                                    if ($backoff480 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff480);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e477) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff480 < 5000) $backoff480 *= 2;
                                }
                                $doBackoff481 = $backoff480 <= 32 ||
                                                  !$doBackoff481;
                            }
                            $commit475 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid478 =
                                          $tm479.getCurrentTid();
                                        if ($currentTid478 != null) {
                                            if ($e477.tid.equals(
                                                            $currentTid478) ||
                                                  !$e477.tid.
                                                  isDescendantOf(
                                                    $currentTid478)) {
                                                throw $e477;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit475 && $retry476) {
                                    {  }
                                    continue $label474;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -57, -81, -15, 27, 119,
    94, -95, 46, 48, 35, 17, 68, 53, 102, -97, 81, -80, -104, 8, 17, 48, 2, 89,
    -45, 30, 69, 100, -18, -15, -9, 38, 28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526752484000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
