package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.Metric;

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
    
    public long get$startTime();
    
    public long set$startTime(long val);
    
    public long postInc$startTime();
    
    public long postDec$startTime();
    
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
    
    /**
   * @param newStartTime
   *        a new time to use as a startTime in the returned {@link Bound}
   * @return an equivalent {@link Bound} with the startTime set to
   *       newStartTime
   */
    public fabric.metrics.contracts.Bound shifted(long newStartTime);
    
    public boolean equals(fabric.lang.Object o);
    
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
        
        public long get$startTime() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              get$startTime();
        }
        
        public long set$startTime(long val) {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              set$startTime(val);
        }
        
        public long postInc$startTime() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postInc$startTime();
        }
        
        public long postDec$startTime() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              postDec$startTime();
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
        
        public fabric.metrics.contracts.Bound shifted(long arg1) {
            return ((fabric.metrics.contracts.Bound) fetch()).shifted(arg1);
        }
        
        public _Proxy(Bound._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.Bound {
        public double get$rate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.rate;
        }
        
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
        
        public double get$base() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.base;
        }
        
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
        
        public long get$startTime() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.startTime;
        }
        
        public long set$startTime(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.startTime = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$startTime() {
            long tmp = this.get$startTime();
            this.set$startTime((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$startTime() {
            long tmp = this.get$startTime();
            this.set$startTime((long) (tmp - 1));
            return tmp;
        }
        
        public long startTime;
        
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
            this.set$base((double) base);
            this.set$startTime((long) startTime);
            fabric$lang$Object$();
            return (fabric.metrics.contracts.Bound) this.$getProxy();
        }
        
        /**
   * @param time
   *        the time we're computing the bound value for
   * @return the value of the bound at the given time
   */
        public double value(long time) {
            return this.get$rate() * (time - this.get$startTime()) +
              this.get$base();
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
            if (this.get$rate() >= 0) {
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
            return this.get$rate() >= other.get$rate() &&
              value(other.get$startTime()) >= other.get$base();
        }
        
        /**
   * @param newStartTime
   *        a new time to use as a startTime in the returned {@link Bound}
   * @return an equivalent {@link Bound} with the startTime set to
   *       newStartTime
   */
        public fabric.metrics.contracts.Bound shifted(long newStartTime) {
            return ((fabric.metrics.contracts.Bound)
                      new fabric.metrics.contracts.Bound._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$Bound$(this.get$rate(),
                                              this.get$base() +
                                                  this.get$rate() *
                                                  (newStartTime -
                                                     this.get$startTime()),
                                              newStartTime);
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
                return this.get$rate() == other.get$rate() &&
                  value(other.get$startTime()) == other.get$base();
            }
            return false;
        }
        
        public java.lang.String toString() {
            return "> " + this.get$rate() + " * (t - " + this.get$startTime() +
            ") + " + this.get$base();
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
            out.writeLong(this.startTime);
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
            this.startTime = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Bound._Impl src =
              (fabric.metrics.contracts.Bound._Impl) other;
            this.rate = src.rate;
            this.base = src.base;
            this.startTime = src.startTime;
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
    
    public static final byte[] $classHash = new byte[] { -1, 69, 43, -73, -60,
    22, 68, -38, -46, 4, 123, 56, 30, -59, 110, 100, -58, -115, -81, 68, -71,
    -98, -96, 73, -35, -27, -34, 122, 125, 86, 119, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
