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
    
    public static final byte[] $classHash = new byte[] { -28, 103, 48, -52, 115,
    65, -111, 102, -30, -97, -37, 59, 71, 90, 73, 69, 89, -117, -122, -91, 54,
    -94, 106, -37, -111, -64, -66, 85, 48, -16, -85, -61 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492300836000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
