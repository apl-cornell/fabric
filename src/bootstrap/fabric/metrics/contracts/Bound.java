package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.Store;

/**
 * A Bound is a linear time-varying bound compared with a {@link Metric} for a
 * {@link MetricContract} of <code>r\u20d7 * (t - startTime) + b\u20d7</code>.
 */
public interface Bound extends fabric.lang.Object {
    public double get$rate();

    public double set$rate(double val);

    public double postInc$rate();

    public double postDec$rate();

    public double get$base();

    public double set$base(double val);

    public double postInc$base();

    public double postDec$base();

    /**
   * @param rate
   *        the rate the value changes with time
   * @param base
   *        the value of the bound associated with startTime
   * @param startTime
   *        the time this bound's value is computed relative to
   */
    public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
      double rate, double base, long startTime);

    /**
   * @param time
   *        the time we're computing the bound value for
   * @return the value of the bound at the given time
   */
    public double value(long time);

    /**
   * @param m
   *        a {@link Metric} we're testing this bound on
   * @param time
   *        the time this bound is being applied to m
   * @return true iff m's current value satisfies the bound at the given time.
   */
    public boolean test(fabric.metrics.Metric m, long time);

    /**
   * @param x
   *        a vector of doubles we're testing this bound on
   * @param time
   *        the time this bound is being applied to m
   * @return true iff x satisfies the bound at the given time.
   */
    public boolean test(double x, long time);

    /**
   * @param metric
   *        the {@link Metric} we're finding the true expiry for
   * @param time
   *        the time we're running this calculation relative to
   * @return the "true expiry" of this bound for the given {@link Metric}, the
   *       time at which point the bound would no longer be satisfied by the
   *       {@link Metric}'s current value.
   */
    public long trueExpiry(fabric.metrics.Metric metric, long time);

    /**
   * @param value
   *        the current value we're finding the true expiry for
   * @param time
   *        the time we're running this calculation relative to
   * @return the "true expiry" of this bound for the given value, the time at
   *       which point the bound would no longer be satisfied by the value.
   */
    public long trueExpiry(double value, long time);

    /**
   * @param other
   *        another {@link Bound}
   * @return true iff this is at least as strong of a bound as other
   */
    public boolean implies(fabric.metrics.contracts.Bound other);

    public boolean equals(fabric.lang.Object o);

    public int hashCode();

    public java.lang.String toString();

    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.Bound {
        public double get$rate() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).get$rate();
        }

        public double set$rate(double val) {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).set$rate(
                                                                      val);
        }

        public double postInc$rate() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postInc$rate();
        }

        public double postDec$rate() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postDec$rate();
        }

        public double get$base() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).get$base();
        }

        public double set$base(double val) {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).set$base(
                                                                      val);
        }

        public double postInc$base() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postInc$base();
        }

        public double postDec$base() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postDec$base();
        }

        public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.contracts.Bound) fetch()).
              fabric$metrics$contracts$Bound$(arg1, arg2, arg3);
        }

        public double value(long arg1) {
            return ((fabric.metrics.contracts.Bound) fetch()).value(arg1);
        }

        public boolean test(fabric.metrics.Metric arg1, long arg2) {
            return ((fabric.metrics.contracts.Bound) fetch()).test(arg1, arg2);
        }

        public boolean test(double arg1, long arg2) {
            return ((fabric.metrics.contracts.Bound) fetch()).test(arg1, arg2);
        }

        public long trueExpiry(fabric.metrics.Metric arg1, long arg2) {
            return ((fabric.metrics.contracts.Bound) fetch()).trueExpiry(arg1,
                                                                         arg2);
        }

        public long trueExpiry(double arg1, long arg2) {
            return ((fabric.metrics.contracts.Bound) fetch()).trueExpiry(arg1,
                                                                         arg2);
        }

        public boolean implies(fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.contracts.Bound) fetch()).implies(arg1);
        }

        public _Proxy(Bound._Impl impl) { super(impl); }

        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }

    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.Bound {
        public double get$rate() { return this.rate; }

        public double set$rate(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.rate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }

        public double postInc$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp + 1));
            return tmp;
        }

        public double postDec$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp - 1));
            return tmp;
        }

        public double rate;

        public double get$base() { return this.base; }

        public double set$base(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.base = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }

        public double postInc$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp + 1));
            return tmp;
        }

        public double postDec$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp - 1));
            return tmp;
        }

        public double base;

        /**
   * @param rate
   *        the rate the value changes with time
   * @param base
   *        the value of the bound associated with startTime
   * @param startTime
   *        the time this bound's value is computed relative to
   */
        public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
          double rate, double base, long startTime) {
            this.set$rate((double) rate);
            this.set$base((double) (base - rate * startTime));
            fabric$lang$Object$();
            return (fabric.metrics.contracts.Bound) this.$getProxy();
        }

        /**
   * @param time
   *        the time we're computing the bound value for
   * @return the value of the bound at the given time
   */
        public double value(long time) {
            return this.get$rate() * time + this.get$base();
        }

        /**
   * @param m
   *        a {@link Metric} we're testing this bound on
   * @param time
   *        the time this bound is being applied to m
   * @return true iff m's current value satisfies the bound at the given time.
   */
        public boolean test(fabric.metrics.Metric m, long time) {
            return test(m.value(), time);
        }

        /**
   * @param x
   *        a vector of doubles we're testing this bound on
   * @param time
   *        the time this bound is being applied to m
   * @return true iff x satisfies the bound at the given time.
   */
        public boolean test(double x, long time) { return x >= value(time); }

        /**
   * @param metric
   *        the {@link Metric} we're finding the true expiry for
   * @param time
   *        the time we're running this calculation relative to
   * @return the "true expiry" of this bound for the given {@link Metric}, the
   *       time at which point the bound would no longer be satisfied by the
   *       {@link Metric}'s current value.
   */
        public long trueExpiry(fabric.metrics.Metric metric, long time) {
            return trueExpiry(metric.value(), time);
        }

        /**
   * @param value
   *        the current value we're finding the true expiry for
   * @param time
   *        the time we're running this calculation relative to
   * @return the "true expiry" of this bound for the given value, the time at
   *       which point the bound would no longer be satisfied by the value.
   */
        public long trueExpiry(double value, long time) {
            if (this.get$rate() > 0) {
                return (long) (time + (value - value(time)) / this.get$rate());
            } else if (value < value(time)) {
                return 0;
            }
            return java.lang.Long.MAX_VALUE;
        }

        /**
   * @param other
   *        another {@link Bound}
   * @return true iff this is at least as strong of a bound as other
   */
        public boolean implies(fabric.metrics.contracts.Bound other) {
            return this.get$rate() >= other.get$rate() && this.get$base() >=
              other.get$base();
        }

        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.metrics.contracts.Bound) {
                fabric.metrics.contracts.Bound other =
                  (fabric.metrics.contracts.Bound)
                    fabric.lang.Object._Proxy.$getProxy(o);
                return this.get$rate() == other.get$rate() && this.get$base() ==
                  other.get$base();
            }
            return false;
        }

        public int hashCode() {
            return java.lang.Double.hashCode(this.get$rate()) * 32 +
              java.lang.Double.hashCode(this.get$base());
        }

        public java.lang.String toString() {
            return ">= " + this.get$rate() + " * t + " + this.get$base();
        }

        public _Impl(fabric.worker.Store $location) { super($location); }

        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.Bound._Proxy(this);
        }

        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.rate);
            out.writeDouble(this.base);
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
            this.rate = in.readDouble();
            this.base = in.readDouble();
        }

        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Bound._Impl src =
              (fabric.metrics.contracts.Bound._Impl) other;
            this.rate = src.rate;
            this.base = src.base;
        }
    }

    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Bound._Static {
            public _Proxy(fabric.metrics.contracts.Bound._Static._Impl impl) {
                super(impl);
            }

            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }

            public static final fabric.metrics.contracts.Bound._Static
              $instance;

            static {
                fabric.
                  metrics.
                  contracts.
                  Bound.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.contracts.Bound._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.Bound._Static._Impl.class);
                $instance = (fabric.metrics.contracts.Bound._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }

        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.Bound._Static {
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
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }

            private void $init() {  }
        }

    }

    public static final byte[] $classHash = new byte[] { 6, -3, -37, 72, -128,
    -28, 112, 57, 120, -119, -3, 8, 75, 37, -108, -113, 122, 31, -47, -15, 116,
    113, -114, 16, -45, -78, -36, -40, 37, 67, 0, -97 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492300546000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
