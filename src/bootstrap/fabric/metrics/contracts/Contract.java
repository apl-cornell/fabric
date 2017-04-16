package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.transaction.TransactionManager;

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
    public fabric.metrics.contracts.Contract fabric$metrics$contracts$Contract$();

    /**
   * Extends the expiration time.
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
    public void extendTo(long newExpiry);

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

    public long getExpiry();

    /**
   * Update this contract's expiration time to stay valid in
   * response to a change in the value of the {@link Subject}s used
   * for enforcing this {@link Contract}. Revokes, extends, and
   * updates the enforcement strategy as needed.
   */
    public abstract void refresh();

    public void handleUpdates();

    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
      implements fabric.metrics.contracts.Contract {
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
            this.set$$expiry((long) -1);
            return (fabric.metrics.contracts.Contract) this.$getProxy();
        }

        /**
   * Extends the expiration time.
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
        public void extendTo(long newExpiry) {
            long currentTime = java.lang.System.currentTimeMillis();
            long
              adjustedNewExpiry =
              java.lang.Math.
              min(
                newExpiry,
                currentTime +
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$MAX_EXTENSION());
            if (adjustedNewExpiry >
                  currentTime +
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$MIN_EXTENSION_FACTOR() * (getExpiry() - currentTime)) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExtension((fabric.metrics.contracts.Contract)
                                      this.$getProxy());
            }
            final fabric.worker.Store s = $getStore();
            this.set$$expiry((long) adjustedNewExpiry);
        }

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
                fabric.common.Logging.METRICS_LOGGER.
                  info(
                    "ACTIVATING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IN " +
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     ).
                        getCurrentTid());
                this.set$active(true);
                refresh();
            }
        }

        /**
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
            if (!isObserved()) {
                fabric.common.Logging.METRICS_LOGGER.
                  info(
                    "DEACTIVATING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IN " +
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     ).
                        getCurrentTid());
                this.set$active(false);
                this.set$$expiry((long) -1);
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
            fabric.worker.Worker localW = fabric.worker.Worker.getWorker();
            if (!localW.getStore(localW.getName()).equals($getStore()))
                newExpiry =
                  newExpiry -
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$DRIFT_FACTOR();
            if (getExpiry() < newExpiry) {
                extendTo(newExpiry);
            } else if (getExpiry() > newExpiry) {
                revoke(newExpiry);
            }
        }

        /**
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void revoke(long newExpiry) {
            markModified();
            fabric.common.Logging.METRICS_LOGGER.
              info(
                "REVOKING " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.Contract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            this.set$$expiry((long) newExpiry);
        }

        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(long time) {
            if (!isActive()) return false;
            return getExpiry() > time;
        }

        public long getExpiry() { return this.get$$expiry(); }

        /**
   * Update this contract's expiration time to stay valid in
   * response to a change in the value of the {@link Subject}s used
   * for enforcing this {@link Contract}. Revokes, extends, and
   * updates the enforcement strategy as needed.
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
            this.active = in.readBoolean();
        }

        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.active = src.active;
        }
    }

    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$MIN_EXTENSION_FACTOR();

        public double set$MIN_EXTENSION_FACTOR(double val);

        public double postInc$MIN_EXTENSION_FACTOR();

        public double postDec$MIN_EXTENSION_FACTOR();

        public long get$MAX_EXTENSION();

        public long set$MAX_EXTENSION(long val);

        public long postInc$MAX_EXTENSION();

        public long postDec$MAX_EXTENSION();

        public long get$DRIFT_FACTOR();

        public long set$DRIFT_FACTOR(long val);

        public long postInc$DRIFT_FACTOR();

        public long postDec$DRIFT_FACTOR();

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

            public long get$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$MAX_EXTENSION();
            }

            public long set$MAX_EXTENSION(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$MAX_EXTENSION(val);
            }

            public long postInc$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$MAX_EXTENSION();
            }

            public long postDec$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$MAX_EXTENSION();
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

            public long get$MAX_EXTENSION() { return this.MAX_EXTENSION; }

            public long set$MAX_EXTENSION(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MAX_EXTENSION = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }

            public long postInc$MAX_EXTENSION() {
                long tmp = this.get$MAX_EXTENSION();
                this.set$MAX_EXTENSION((long) (tmp + 1));
                return tmp;
            }

            public long postDec$MAX_EXTENSION() {
                long tmp = this.get$MAX_EXTENSION();
                this.set$MAX_EXTENSION((long) (tmp - 1));
                return tmp;
            }

            public long MAX_EXTENSION;

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

            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.MIN_EXTENSION_FACTOR);
                out.writeLong(this.MAX_EXTENSION);
                out.writeLong(this.DRIFT_FACTOR);
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
                this.MIN_EXTENSION_FACTOR = in.readDouble();
                this.MAX_EXTENSION = in.readLong();
                this.DRIFT_FACTOR = in.readLong();
            }

            public _Impl(fabric.worker.Store store) { super(store); }

            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }

            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm26 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff27 = 1;
                        boolean $doBackoff28 = true;
                        $label22: for (boolean $commit23 = false; !$commit23;
                                       ) {
                            if ($doBackoff28) {
                                if ($backoff27 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff27);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e24) {

                                        }
                                    }
                                }
                                if ($backoff27 < 5000) $backoff27 *= 2;
                            }
                            $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                            $commit23 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1.5);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 1000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 50);
                            }
                            catch (final fabric.worker.RetryException $e24) {
                                $commit23 = false;
                                continue $label22;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit23 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25))
                                    continue $label22;
                                if ($currentTid25.parent != null) throw $e24;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e24) {
                                $commit23 = false;
                                if ($tm26.checkForStaleObjects())
                                    continue $label22;
                                throw new fabric.worker.AbortException($e24);
                            }
                            finally {
                                if ($commit23) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e24) {
                                        $commit23 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e24) {
                                        $commit23 = false;
                                        fabric.common.TransactionID
                                          $currentTid25 = $tm26.getCurrentTid();
                                        if ($currentTid25 != null) {
                                            if ($e24.tid.equals(
                                                           $currentTid25) ||
                                                  !$e24.tid.isDescendantOf(
                                                              $currentTid25)) {
                                                throw $e24;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit23) {  }
                            }
                        }
                    }
                }
            }
        }

    }

    public static final byte[] $classHash = new byte[] { 5, -110, 95, -74, -87,
    -1, 56, -95, -47, -89, -50, 74, -76, 67, -66, -22, 51, -27, -56, 62, -2, 16,
    -89, -44, -33, 41, 121, -7, -122, 47, 65, -36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492300656000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
