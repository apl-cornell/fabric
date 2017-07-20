package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * Represents an observable quantity that changes over time and can be compared
 * with a {@link Bound} enforced by a {@link MetricContract}. Internally, this
 * class estimates the velocity and noise of the observed quantity, which are
 * used for creating {@link MetricContract} expiries. Instances of this class
 * can be observed by {@link DerivedMetric}s and {@link MetricContract}s.
 *
 * {@link Metric}s are {@link Comparable} to allow for predictable hashing and
 * normalized representations.
 */
public interface Metric
  extends fabric.metrics.util.Subject, java.lang.Comparable, fabric.lang.Object
{
    /** @return the current value of the {@link Metric}. */
    public double value();
    
    /** @return the estimated velocity of the {@link Metric}. */
    public double velocity();
    
    /**
   * @return the estimated noise (the <em>variance</em> of the velocity
   *         estimate) of the {@link Metric}.
   */
    public double noise();
    
    /**
   * Used to construct and enforce {@link MetricContract}s bounding this
   * {@link Metric}s value.
   *
   * @param bound
   *            a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *         being above bound.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      policy(fabric.metrics.contracts.Bound bound);
    
    /**
   * @return true iff all the sampling and transformations on this metric are
   *       stored on a single store.
   */
    public boolean isSingleStore();
    
    /**
   * @param bound
   *        the {@link Bound} that the returned {@link MetricContract}
   *        will enforce on this {@link Metric}
   * @return a {@link MetricContract} that enforces that the {@link Metric}
   *         satisfies the given {@link Bound}. If such a
   *         {@link MetricContract}, it is returned, otherwise a new one is
   *         created and returned (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(fabric.metrics.contracts.Bound bound);
    
    /**
   * @param rate
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param time
   *        the startTime parameter of the {@link Bound} on the resulting
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the given
   *         time. If such a {@link MetricContract} already exists, it is
   *         returned, otherwise a new one is created and returned
   *         (unactivated).
   *
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate,
                                                               double base, long time);
    
    /**
   * @param rate
   *        the rate parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @param base
   *        the base parameter for the {@link Bound} on the resuling
   *        {@link MetricContract}
   * @return a {@link MetricContract} which enforces that the {@link Metric}
   *         satisfies a {@link Bound} with the given parameters at the
   *         current time. If such a {@link MetricContract} already exists, it
   *         is returned, otherwise a new one is created and returned
   *         (unactivated).
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param contract
   *        a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *             if the {@link MetricContract} doesn't enforce a {@link Bound}
   *             on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link Metric} that tracks the scaled value of this
   *         {@link Metric}.
   */
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * @param other
   *            another {@link Metric} to add with this {@link Metric}.
   * @return a {@link Metric} that tracks the value of the sum of other and
   *         this.
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the minimum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the minimum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}.
   * @return a {@link Metric} that tracks the value of the maximum of this and
   *         the other {@link Metric}.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.Metric {
        public double value() {
            return ((fabric.metrics.Metric) fetch()).value();
        }
        
        public double velocity() {
            return ((fabric.metrics.Metric) fetch()).velocity();
        }
        
        public double noise() {
            return ((fabric.metrics.Metric) fetch()).noise();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policy(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).policy(arg1);
        }
        
        public boolean isSingleStore() {
            return ((fabric.metrics.Metric) fetch()).isSingleStore();
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(double arg1,
                                                                   double arg2,
                                                                   long arg3) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2,
                                                                 arg3);
        }
        
        public fabric.metrics.contracts.MetricContract getContract(
          double arg1, double arg2) {
            return ((fabric.metrics.Metric) fetch()).getContract(arg1, arg2);
        }
        
        public void addContract(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.Metric) fetch()).addContract(arg1);
        }
        
        public fabric.metrics.DerivedMetric times(double arg1) {
            return ((fabric.metrics.Metric) fetch()).times(arg1);
        }
        
        public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).plus(arg1);
        }
        
        public fabric.metrics.Metric min(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).min(arg1);
        }
        
        public fabric.metrics.Metric max(fabric.metrics.Metric arg1) {
            return ((fabric.metrics.Metric) fetch()).max(arg1);
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.Metric) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.Metric) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.Metric) fetch()).observedBy(arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.Metric) fetch()).isObserved();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.Metric) fetch()).getObservers();
        }
        
        public int compareTo(java.lang.Object arg1) {
            return ((fabric.metrics.Metric) fetch()).compareTo(arg1);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 123, 45, -73, 15, -67,
    -49, 66, 11, 93, -111, -64, -74, 2, 95, 11, -26, 60, -116, -112, -102, -94,
    70, 81, -39, -90, -56, 119, 22, 93, -33, -47, 115 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1500579673000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
