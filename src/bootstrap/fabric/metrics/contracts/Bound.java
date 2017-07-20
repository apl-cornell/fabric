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
    
    public static final byte[] $classHash = new byte[] { -37, 92, -18, -107,
    -104, 0, -1, 74, 52, -7, -43, 39, 46, -31, -120, -95, -121, 95, -47, -33,
    126, -20, 88, -11, 25, 119, 77, 84, 44, -22, -118, -9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579636000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
