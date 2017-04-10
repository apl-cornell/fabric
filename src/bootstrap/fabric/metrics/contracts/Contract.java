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
    
    public static final byte[] $classHash = new byte[] { -97, 123, -28, -117,
    -51, 38, 27, -74, 29, -20, 65, 4, 16, -126, 54, -53, -71, -25, 71, -30, 104,
    109, -75, 65, 97, 61, 74, 59, -117, 53, -31, 79 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZe2wUxxmfOz/PGPzgbcDY5iCFwJ1IWyTiuKp9seGSo1jYJK1RceZ25+zFe7ub3Tn7ICVNUjXQoKAqdSBRA0kl2iYpCWor0pYUNVXSlpQ0UqM0bZWmJagoIOCPqE8padPvm5177T1i/1FLM9/ezPd985vvNbPrk9dJjWOTrgSNa3qI77WYExqg8WhskNoOUyM6dZxhGB1V5lRHj1z+rtruJ/4YaVSoYRqaQvVRw+FkXmwPnaRhg/Hwzh3R7l0koKDgVuqMc+Lf1Ze2SYdl6nvHdJPLRYr0P3pjePro7uYfVJGmEdKkGUOcck2JmAZnaT5CGpMsGWe206uqTB0hLQZj6hCzNapr+4DRNEZIq6ONGZSnbObsYI6pTyJjq5OymC3WzAwifBNg2ymFmzbAb3bhp7imh2Oaw7tjpDahMV117ib3kuoYqUnodAwYF8UyuwgLjeEBHAf2Bg1g2gmqsIxI9YRmqJys9Epkdxy8HRhAtC7J+LiZXaraoDBAWl1IOjXGwkPc1owxYK0xU7AKJ21llQJTvUWVCTrGRjlZ4uUbdKeAKyDMgiKcLPSyCU3gszaPz/K8df1ztxy+x9hq+IkPMKtM0RF/PQi1e4R2sASzmaEwV7BxXewIXXT2oJ8QYF7oYXZ5fvSl9z+7vv2lcy7PshI82+N7mMJHlRPxeb9dHlm7uQph1Fumo2EoFOxceHVQznSnLYj2RVmNOBnKTL6045dfuO9ZdtVPGqKkVjH1VBKiqkUxk5amM3sLM5hNOVOjJMAMNSLmo6QOnmOawdzR7YmEw3iUVOtiqNYUv8FECVCBJqqDZ81ImJlni/Jx8Zy2CCHN0IgP2sOEzDXdAf8AJ4PhcTPJwnE9xaYgvMPQGLWV8TDkra0pYcdWwnbK4BowySGIIiBOGEKd21ThECXyKQRYrP+DzjTuo3nK5wMTr1RMlcWpA/6SsdM3qEN6bDV1ldmjin74bJTMP/u4iJ8AxrwDcSss5AOfL/dWi3zZ6VRf//vPj553Yw9lpQE56XSBhiTQUBZoKAMUsDViaoWgWIWgWJ30pUOR49HviQiqdUSqZdU1grqbLZ3yhGkn08TnE3tbIORF6IDjJ6CgQM1oXDv0xdvuOthVBTFrTVWjG4E16M2gXN2JwhOFtBhVmg5c/uepI/vNXC5xEixK8WJJTNEur6FsU2EqlMCc+nUd9PTo2f1BP5aXAFqEQmxCGWn3rlGQqt2ZsofWqImROWgDquNUplY18HHbnMqNiACYh12rGwtoLA9AUTF7hqxjf3j9yifFWZIprk15VXiI8e68hEZlTSJ1W3K2H7YZA753Hhv8xqPXD+wShgeOVaUWDGIfgUSmkMGm/dVzd//xL38+8aY/5yxOaq1UXNeUtNhLy0fw54P2X2yYlTiAFGpzRFaEjmxJsHDlNTlsUBx0KFAA3QnuNJKmqiU0GtcZRsqHTas3nr52uNl1tw4jrvFssv7jFeTGl/aR+87v/le7UONT8HDK2S/H5la8+TnNvbZN9yKO9P1vrHj8V/QYRD7UK0fbx0QJIsIeRDjwJmGLDaLf6Jn7FHZdrrWWi/Eqp7j6D+AxmovFkfDJJ9oin7nqpn02FlFHZ4m0v4PmpclNzyb/4e+q/YWf1I2QZnGCU4PfQaF+QRiMwBnsRORgjMwtmC88T93Dozuba8u9eZC3rDcLcuUGnpEbnxvcwHcDBwzRgEZqg7YYjNLoUv+HODvfwn5B2kfEw81CZJXo12C3NhOMdZatTUJkpbNK/ag0IJV9IOnf85RCBIMHNXtvCTcM2loSMmlSHsLs4PRDH4UOT7sh6N5UVhVdFvJl3NuK2Odc7G5MwyqdlVYREgPvndr/4tP7D7gneWvhudtvpJLPvfWf10KPXXi1RC2v1k23FDcLG2wqNO8N0FYTUm1LurOEebe65sWup9iOKDUs6bYCOzbCjRPOcqYOZDzfK/eM5FbwTtw0dUaNsug6ofUQUtsj6ZoS6AYrokOp1ZK2F3oZTjGwMf4SsJvTpaPIj4/rOKmncUccfblYEn9N8nrRL+mmvFXystonnhdCmnjOVuHD7XGH2ZNuBrdhTKwod2cU8XDigenj6vZvb/TLItLPSYCb1gadTTI9b9G5GF1F7yTbxE05VxEuXF2xOTJxacyNrpWelb3cz2w7+eqWNcojflKVTf2i63mhUHdhwjfYDN4ujOGCtO/IWhUznWyG1grWPC3pwXzP5+KlVM4HLNvkUJmY6sn6OVLXAUnv9XqqdIXeU2FOXNfh0OhynRqUTg1mL0zBzIUpmAN9V+FW10LrgAr3rqSvlNkqdoniHaHIy5KeKb8jn2se/BkRWp0K20phB/f/ejeDh81S2Vs9aWqqZ0Mia1dAWwfV4HVJX6ywoRIpiyJnJP3hzFz05Qpz92O3D6pRktoT/bIi4diUB3sggx001PRJGppp3Ikq4Qm4eqlkg6Q3zGw3X6swdwi7r3A4mKFw2h+7nWVuKtV8IOmVCq54sBg8ilyW9OLMwD9SYW4au4ezYeXi3lYON7ig9hlJj84ON4ockfTrM8P9zQpzx7A7Arg1pzd3aJTCvQRalJC6TklbZ4cbRVoknTMz3CcqzH0Huyfx6ELUcA0qGydBaHcCht2SRmaHG0X6JL1lFjXouQrgT2H3NJzTKUstA12UT7zBjME2firptypAL1E+UeQpSY/OAvoLFaD/GLvvA3SbTZoT5a2OUZICGN2Srpqd1VGkS9Lls4D+swrQf47dGU5qJqmulc/PpdD2w7LXJP3T7JCjyNuS/m5mcX6uwtyvsXsZTv4xxvvF3V1s1gN8IfKvggbn/9xPSNpWBnjJW6C4Rt/pKfILpKalktbNbD9vVJh7E7vX4H5ss4TNnPGyAYSn1UOw5uclvXV2bkCRiKQ9M4P9doW5d7B7C86mcWqoOtsp8lZwTqWhCGVuQvgivazEdy35vVWJvMJOXLp9/cIy37SWFH0Bl3LPH2+qX3x85+/FB5rst9RAjNQnUrqe/8KZ91xrgY01AT/gvn5aglzkZEm5j2DcfeUWz2J7F1yZS5zMK5Th4rM0PuXzXYbK4PLhryvC5m25LvOisKzUi8JQSrx1C0ahrC1l478GTv5t8b9r64cviE8x4JCOJ+/566HfrFn2woprvdXND2w6/5P3tlwcT57upT23dR/69Lvb/wfQ8BKYshgAAA==";
}
