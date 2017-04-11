package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;

/**
 * A {@link Contract} consists of an assertion and an expiration time. If the
 * current time is earlier than the expiration time, then the contract is
 * <i>valid</i>: the enforcement protocol will ensure the assertion holds.
 * <p>
 * This class follows the subject-observer pattern. An instance can be an
 * observer of a {@link Metric} or other {@link Contract}s, and can be observed
 * by other {@link Contract}s.
 */
public interface Contract
  extends fabric.metrics.util.Observer, fabric.metrics.util.Subject {
    public fabric.metrics.contracts.Contract fabric$metrics$contracts$Contract$(
      );
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    /**
   * Extends the expiration time.
   *
   * @param newExpiry
   *        the new expiration time for this {@link Contract} given in
   *        milliseconds.
   */
    public void extendTo(long newExpiry);
    
    public boolean get$extendedFlag();
    
    public boolean set$extendedFlag(boolean val);
    
    /**
   * Clear this contract's extended flag.
   */
    public void clearExtended();
    
    /**
   * @return true iff this contract is currently marked as being extended by
   *       the current transaction
   */
    public boolean extended();
    
    public boolean get$active();
    
    public boolean set$active(boolean val);
    
    /**
   * @return true if this contract is currently active (enforced).
   */
    public boolean isActive();
    
    /**
   * Activate and start enforcing this {@link Contract}.
   */
    public void activate();
    
    /**
   * Deactivate and stop observing any evidence.
   */
    public void deactivate();
    
    /**
   * Updates the expiration time of this contract, either extending or
   * revoking as needed.
   *
   * @param newExpiry
   *        the new expiration time associated with this {@link Contract}.
   */
    public void update(long newExpiry);
    
    /**
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
    public void revoke(long newExpiry);
    
    /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
    public boolean valid(long time);
    
    /**
   * @return the expiration time of the current state, in milliseconds
   */
    public long getExpiry();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public abstract void refresh();
    
    public void handleUpdates();
    
    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
      implements fabric.metrics.contracts.Contract {
        public long get$expiry() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              postDec$expiry();
        }
        
        public boolean get$extendedFlag() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$extendedFlag();
        }
        
        public boolean set$extendedFlag(boolean val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$extendedFlag(val);
        }
        
        public boolean get$active() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$active();
        }
        
        public boolean set$active(boolean val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$active(val);
        }
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              fabric$metrics$contracts$Contract$();
        }
        
        public void extendTo(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1);
        }
        
        public void clearExtended() {
            ((fabric.metrics.contracts.Contract) fetch()).clearExtended();
        }
        
        public boolean extended() {
            return ((fabric.metrics.contracts.Contract) fetch()).extended();
        }
        
        public boolean isActive() {
            return ((fabric.metrics.contracts.Contract) fetch()).isActive();
        }
        
        public void activate() {
            ((fabric.metrics.contracts.Contract) fetch()).activate();
        }
        
        public void deactivate() {
            ((fabric.metrics.contracts.Contract) fetch()).deactivate();
        }
        
        public void update(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).update(arg1);
        }
        
        public void revoke(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).revoke(arg1);
        }
        
        public boolean valid(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
        }
        
        public long getExpiry() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiry();
        }
        
        public void refresh() {
            ((fabric.metrics.contracts.Contract) fetch()).refresh();
        }
        
        public void handleUpdates() {
            ((fabric.metrics.contracts.Contract) fetch()).handleUpdates();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Contract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.util.Subject._Impl
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$Subject$();
            return (fabric.metrics.contracts.Contract) this.$getProxy();
        }
        
        public long get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public long set$expiry(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp - 1));
            return tmp;
        }
        
        private long expiry = 0;
        
        /**
   * Extends the expiration time.
   *
   * @param newExpiry
   *        the new expiration time for this {@link Contract} given in
   *        milliseconds.
   */
        public void extendTo(long newExpiry) {
            if (newExpiry > this.get$expiry())
                ((fabric.metrics.contracts.Contract._Impl) this.fetch()).
                  markExtended();
            this.set$expiry((long)
                              (this.get$expiry() > newExpiry
                                 ? this.get$expiry()
                                 : newExpiry));
        }
        
        public boolean get$extendedFlag() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.extendedFlag;
        }
        
        public boolean set$extendedFlag(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.extendedFlag = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean extendedFlag = false;
        
        /**
   * Mark this contract as having been extended.
   */
        private void markExtended() { this.set$extendedFlag(true); }
        
        /**
   * Clear this contract's extended flag.
   */
        public void clearExtended() { this.set$extendedFlag(false); }
        
        /**
   * @return true iff this contract is currently marked as being extended by
   *       the current transaction
   */
        public boolean extended() { return this.get$extendedFlag(); }
        
        public boolean get$active() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.active;
        }
        
        public boolean set$active(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.active = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean active = false;
        
        /**
   * @return true if this contract is currently active (enforced).
   */
        public boolean isActive() { return this.get$active(); }
        
        /**
   * Activate and start enforcing this {@link Contract}.
   */
        public void activate() {
            if (!this.get$active()) {
                this.set$active(true);
                refresh();
            }
        }
        
        /**
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
            if (!isObserved()) {
                this.set$active(false);
                this.set$expiry((long) 0);
            }
        }
        
        /**
   * Updates the expiration time of this contract, either extending or
   * revoking as needed.
   *
   * @param newExpiry
   *        the new expiration time associated with this {@link Contract}.
   */
        public void update(long newExpiry) {
            if (this.get$expiry() != newExpiry) {
                if (newExpiry > this.get$expiry()) {
                    extendTo(newExpiry);
                } else {
                    revoke(newExpiry);
                }
            }
        }
        
        /**
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void revoke(long newExpiry) {
            clearExtended();
            markModified();
            this.set$expiry((long) newExpiry);
        }
        
        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(long time) {
            if (this.get$expiry() < time) refresh();
            return this.get$expiry() >= time;
        }
        
        /**
   * @return the expiration time of the current state, in milliseconds
   */
        public long getExpiry() { return this.get$expiry(); }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public abstract void refresh();
        
        public void handleUpdates() { refresh(); }
        
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
            out.writeLong(this.expiry);
            out.writeBoolean(this.extendedFlag);
            out.writeBoolean(this.active);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.expiry = in.readLong();
            this.extendedFlag = in.readBoolean();
            this.active = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.expiry = src.expiry;
            this.extendedFlag = src.extendedFlag;
            this.active = src.active;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Contract._Static {
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
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 94, -84, 119, -89, 79,
    118, -4, 35, 43, 102, 29, 116, 38, 43, 93, -42, 23, 22, -83, 127, 100, -88,
    123, 70, -2, -46, 71, 52, 123, -18, -51, -93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
