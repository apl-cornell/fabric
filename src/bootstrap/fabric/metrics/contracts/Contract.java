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
    
    public static final byte[] $classHash = new byte[] { -65, -43, -92, -96,
    -92, -73, 11, -45, -121, -78, -92, 98, 4, -74, 46, 44, -79, 109, -122, 121,
    -112, -87, 87, 39, -93, -7, 108, 20, 53, 125, 39, -108 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491925263000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
