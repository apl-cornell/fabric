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
    
    public static final byte[] $classHash = new byte[] { 64, 90, 7, -100, 70,
    -94, 59, 80, 125, -91, -11, -8, 41, 74, 89, 8, -88, 5, -95, -113, 85, -70,
    53, 1, -99, -16, -88, 49, 79, -34, 108, -103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
