package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.NodeTime;
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
    
    public fabric.metrics.util.NodeTime get$startTime();
    
    public fabric.metrics.util.NodeTime set$startTime(fabric.metrics.util.NodeTime val);
    
    /**
   * @param rate
   *        the rate the value changes with time
   * @param base
   *        the value of the bound associated with startTime
   * @param startTime
   *        the time this bound's value is computed relative to
   */
    public fabric.metrics.contracts.Bound
      fabric$metrics$contracts$Bound$(double rate,
                                      double base, fabric.metrics.util.NodeTime startTime);
    
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
    public double value(fabric.metrics.util.NodeTime time);
    
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
        
        public fabric.metrics.util.NodeTime get$startTime() {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              get$startTime();
        }
        
        public fabric.metrics.util.NodeTime set$startTime(
          fabric.metrics.util.NodeTime val) {
            return ((fabric.metrics.contracts.Bound._Impl) fetch()).
              set$startTime(val);
        }
        
        public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
          double arg1, double arg2, fabric.metrics.util.NodeTime arg3) {
            return ((fabric.metrics.contracts.Bound) fetch()).
              fabric$metrics$contracts$Bound$(arg1, arg2, arg3);
        }
        
        public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
          double arg1, double arg2, long arg3) {
            return ((fabric.metrics.contracts.Bound) fetch()).
              fabric$metrics$contracts$Bound$(arg1, arg2, arg3);
        }
        
        public double value(fabric.metrics.util.NodeTime arg1) {
            return ((fabric.metrics.contracts.Bound) fetch()).value(arg1);
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
        
        public fabric.metrics.util.NodeTime get$startTime() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.startTime;
        }
        
        public fabric.metrics.util.NodeTime set$startTime(
          fabric.metrics.util.NodeTime val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.startTime = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.util.NodeTime startTime;
        
        /**
   * @param rate
   *        the rate the value changes with time
   * @param base
   *        the value of the bound associated with startTime
   * @param startTime
   *        the time this bound's value is computed relative to
   */
        public fabric.metrics.contracts.Bound fabric$metrics$contracts$Bound$(
          double rate, double base, fabric.metrics.util.NodeTime startTime) {
            this.set$rate((double) rate);
            this.set$base((double) base);
            this.set$startTime(startTime);
            fabric$lang$Object$();
            return (fabric.metrics.contracts.Bound) this.$getProxy();
        }
        
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
            this.set$startTime(
                   ((fabric.metrics.util.NodeTime)
                      new fabric.metrics.util.NodeTime._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$metrics$util$NodeTime$(
                                                 startTime));
            fabric$lang$Object$();
            return (fabric.metrics.contracts.Bound) this.$getProxy();
        }
        
        /**
   * @param time
   *        the time we're computing the bound value for
   * @return the value of the bound at the given time
   */
        public double value(fabric.metrics.util.NodeTime time) {
            return this.get$rate() *
              (time.get$time() - this.get$startTime().lowerBound()) +
              this.get$base();
        }
        
        /**
   * @param time
   *        the time we're computing the bound value for
   * @return the value of the bound at the given time
   */
        public double value(long time) {
            return value(
                     ((fabric.metrics.util.NodeTime)
                        new fabric.metrics.util.NodeTime._Impl(
                          this.$getStore()).$getProxy()).
                         fabric$metrics$util$NodeTime$(time));
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
            final fabric.worker.Store s = $getStore();
            return ((fabric.metrics.contracts.Bound)
                      new fabric.metrics.contracts.Bound._Impl(s).$getProxy()).
              fabric$metrics$contracts$Bound$(this.get$rate(),
                                              value(newStartTime),
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
            return ">= " +
            this.get$rate() +
            " * (t - " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$startTime())) +
            ") + " +
            this.get$base();
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
            $writeRef($getStore(), this.startTime, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.startTime =
              (fabric.metrics.util.NodeTime)
                $readRef(fabric.metrics.util.NodeTime._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
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
    
    public static final byte[] $classHash = new byte[] { -121, 24, 21, 59, -95,
    -53, 89, 82, 116, -51, -118, -1, 19, 88, 28, -26, 28, -79, -78, 35, -14, 79,
    34, 60, 100, 83, -2, 28, 20, 64, -57, 40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492107628000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
