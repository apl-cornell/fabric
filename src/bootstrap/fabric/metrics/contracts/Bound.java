package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.Store;

/**
 * A linear time-varying bound that can be applied to a {@link Metric}'s value
 * and enforced by a {@link MetricContract} of
 * <code>&gt;= r * (t - startTime) + b</code>.
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
    
    public fabric.worker.Store getStore();
    
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
   *        a double we're testing this bound on
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
   * @return true iff this is at least as strong of a bound as other (so this
   *         bound being satisfied imples the other bound is also satisfied)
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
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.contracts.Bound) fetch()).getStore();
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
        
        public fabric.worker.Store getStore() { return $getStore(); }
        
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
            if (java.lang.Double.isNaN(rate) || java.lang.Double.isNaN(base)) {
                throw new java.lang.RuntimeException("This shouldn\'t happen");
            }
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
   *        a double we're testing this bound on
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
   * @return true iff this is at least as strong of a bound as other (so this
   *         bound being satisfied imples the other bound is also satisfied)
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
    
    public static final byte[] $classHash = new byte[] { 71, 3, 36, 23, 82, 96,
    -74, 41, 34, 107, 83, 61, -48, -95, 36, 58, 81, 93, -32, -98, 29, -107, 33,
    -29, -128, -94, 86, -21, 42, 68, -12, -99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506966071000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
