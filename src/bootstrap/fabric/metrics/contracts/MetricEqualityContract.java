package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link MetricEqualityContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricEqualityContract
  extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricEqualityContract
      fabric$metrics$contracts$MetricEqualityContract$(
      fabric.metrics.Metric metric, double value);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy getNewPolicy(
      fabric.worker.metrics.StatsMap weakStats);
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
                           double otherBase);
    
    public java.lang.String toString();
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$metric(val);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$leafMetrics(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postDec$value();
        }
        
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric arg1, double arg2) {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              fabric$metrics$contracts$MetricEqualityContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getMetric();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(MetricEqualityContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableMetricsVector leafMetrics;
        
        public double get$value() { return this.value; }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        public double value;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric metric, double value) {
            this.set$metric(metric);
            this.set$value((double) value);
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        metric)) instanceof fabric.metrics.SampledMetric) {
                this.
                  set$leafMetrics(
                    fabric.worker.metrics.ImmutableMetricsVector.
                        createVector(
                          new fabric.metrics.SampledMetric[] { (fabric.metrics.SampledMetric)
                                                                 fabric.lang.Object._Proxy.
                                                                 $getProxy(
                                                                   metric) }));
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             metric)) instanceof fabric.metrics.DerivedMetric) {
                this.set$leafMetrics(
                       ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      metric)).getLeafSubjects(
                                                                 ));
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricEqualityContract)
                     this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
            return null;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) ||
                  !fabric.metrics.contracts.Bound._Impl.
                  test(otherRate, otherBase, this.get$value(),
                       java.lang.System.currentTimeMillis()))
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() + " == " + this.get$value() + " until " + getExpiry();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.MetricEqualityContract._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.leafMetrics);
            out.writeDouble(this.value);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.value = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.leafMetrics = src.leafMetrics;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricEqualityContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricEqualityContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricEqualityContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricEqualityContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricEqualityContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.MetricEqualityContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricEqualityContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -54, 19, 61, 78, -21,
    -25, -83, 4, -36, -50, -43, 111, -48, -128, 40, 4, -28, 51, -72, 126, -28,
    -17, -23, -16, 124, -31, 37, 20, -43, -75, 104, -87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629388000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYb2wcxRWfO5/PPtuxHScOwXEcx75ETQh3hFRI4JImuSbk4JxYthMJp407tzdnL97b3czO2WeaoBSpTUTVfKAmEKmkXwxtwAQBilqpSsWHUAhUqK0KtBK0qQQKKE1bVLX0Q9v0vdm527v1nUs+9KT5czPvzbz35vfevNmF66Te4aQvS9O6EROzNnNie2g6mRqi3GGZhEEdZxRGx7XmUPL0xz/M9ARJMEVaNGpapq5RY9x0BGlNPUSnadxkIn5gODlwiEQ0ZNxLnUlBgod2FTjptS1jdsKwhNpk0fpP3Bafe/Jw+8t1pG2MtOnmiKBC1xKWKVhBjJGWHMulGXd2ZjIsM0aWm4xlRhjXqaE/DISWOUY6HH3CpCLPmTPMHMuYRsIOJ28zLvcsDqL4FojN85qwOIjf7oqfF7oRT+mOGEiRcFZnRsY5Qh4hoRSpzxp0AghXpYpaxOWK8T04DuRNOojJs1RjRZbQlG5mBFnn5yhpHH0ACIC1IcfEpFXaKmRSGCAdrkgGNSfiI4Lr5gSQ1lt52EWQrpqLAlGjTbUpOsHGBVntpxtyp4AqIs2CLIJ0+snkSnBmXb4zKzut6/u+dOob5l4zSAIgc4ZpBsrfCEw9PqZhlmWcmRpzGVs2p07TVRdPBgkB4k4fsUvz46Of7tjS8+obLs2aKjT70w8xTYxr8+nWX3UnNt1dh2I02pajIxQqNJenOqRmBgo2oH1VaUWcjBUnXx3++YPHn2PXgqQpScKaZeRzgKrlmpWzdYPx+5jJOBUskyQRZmYScj5JGqCf0k3mju7PZh0mkiRkyKGwJf+DibKwBJqoAfq6mbWKfZuKSdkv2ISQBigkAOWrhDRfhnY9IaG0IIfjk1aOxdNGns0AvONQGOXaZBz8luta3OFanOdNoQORGgIUQePEAeqCU0048UE5svtIHnxGzCbUeAwks//vOxRQx/aZQADMv06zMixNHThLhatdQwa4zl7LyDA+rhmnLibJiotnJLYi6A8OYFpaLwB46PZHknLeufyu3Z+eH3/LxSXyKuMKosSOKbFjJbFj1cUGSVvQCWMQ1mIQ1hYChVjibPJ5ibWwI52ytHgLLH6PbVCRtXiuQAIBqelKyS9BBhCZgtAD0aVl08jX7v/6yb46QLc9E8IDB9Ko39e8CJWEHgUHGtfaTnz8jxdPH7M8rxMkuigYLOZEZ+7zm41bGstAsPSW39xLL4xfPBYNYiCKoH0ooBgCTo9/jwqnHigGSLRGfYo0ow2ogVPFqNYkJrk1441IOLRi1eEiA43lE1DG1ntH7Kd/+/Yn2+StUwzDbWXxeoSJgTLXx8XapJMv92w/yhkDug+eGvreE9dPHJKGB4r+ahtGsU6Ay1PwdYt/640jv/vD7+d/E/QOS5CwnU8bulaQuiy/Ab8AlP9gQf/FAWwhiidU7OgtBQ8bd97oyQZhxIBQBqI70QNmzsroWZ2mDYZI+Vfbhq0X/nSq3T1uA0Zc43Gy5X8v4I3fuoscf+vwZz1ymYCG15hnP4/MjY0rvJV3ck5nUY7CN3+99szr9GlAPkQ2R3+YyWBFpD2IPMA7pS1ul/VW39wXsepzrdUtx+ucxffEHrxwPSyOxRe+35XYfs0NAiUs4hrrqwSBg7TMTe58Lvf3YF/4tSBpGCPt8q6npjhIIbYBDMbgtnYSajBFllXMV9687jUzUPK1br8flG3r9wIv+EAfqbHf5ALfBQ4YogON1AulH+L8K6p9BmdX2FivLASI7NwjWfplvRGrTdKQQUEiNrcESMkg24jouVxe4OnLfW4DqKpoh3874ar3xUA38uFkl+uGWN9VEq8dxdsGJQpiXVXtpSriJWqK12BzfRqAj4NfLkrVbDCadfd2iqJtUaLNWHyK8ZKEyaJGivwgk0kbMt3qD7vVVGhDFbqhbCCkPuS2oc+qqDBYQwXsbq6Qvn4akVIFwkNcz0EUmlapDjs599iN2Kk5133dfLB/UUpWzuPmhHKfZXKzAuyyfqldJMeeqy8e++mPjp1w86WOyuxmt5nPvfDuv38Re+rK5Sq3YjhjQTCT/9sL1U0QkCYolEwqf2GVnFDVHiozaZmnE9Rgba08Uko//+jc2cz+Z7YGVbg4CDgWln27waaZUbZUGG2x6J0yKLNnz/evXFt7d2LqownXFut8O/upzw0uXL5vo/Z4kNSVnHxRyl7JNFDp2k2cwYvDHK1w8N6SrSJogxyUO0CB46rtLoefB9p+rMZKrEFkbVQsa1Tb6TezF3KDHl53YjUql9aXCMxTWAEo73BdL6p8LlrKjKLVM6OoJzOt1BTF3AFSv6fa125OU2S5pNqf1da0XAe+xJy8LnMApwkmvEi30yd4C9Jvh7IblNih2tYaglf1je2FSlWa1SLLVBuqrUpAJYAqCPZUD4L4FnYGqV097En5ji5hh0exmgY2sMM+NjNkQfIyW9zyrpppMYNnCtdYjpkCokip77LLO6MaBIagpAhpirpt5IObgwCyvK/ad2vbrc7NIzywe4j/zhKW+C5W34ZbSc/Zhs7cy2dYhVpsDsBc2rLgfjKrKdcFZRSUu6HaP9+ccshyXbVXPx++n1xi7gxWjwvSKCz3G0HxTNtlEocpTKxsYhFwqmn4FSgPAoTPqda8OQ2RJafaic+n4fwSc89i9QPI+AG5KcgZRvIyI5O0yQJkstUDFCaya6q8MtWXES1xic1/9MCWzhovzNWLvlUpvvNn2xpvOXvgPflAKn31iMD7I5s3jPKEr6wftjnL6lKdiJv+2bJ5XpDVtXxPuCmv7Etlz7k85wVpreQR8gNSKZFSdC/Bxe7S4b+X5Rl0eVURJ+tr+n7RkpJcLtmV5/gpb+Fvt/wz3Dh6RT6I4Jh631xx775rV18Ivf/2O9Yvj38h9OG2nzzy4V8++evRP25Y+c6FyXP/BS8YCv5iFAAA";
}
