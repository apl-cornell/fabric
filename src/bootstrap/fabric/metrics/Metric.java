package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;

/**
 * Represents an observable quantity that changes over time. Internally, this
 * class estimates the velocity of the observed quantity, and the interval
 * between updates. Instances of this class can be observed by
 * {@link LinearMetric}s and {@link MetricContract}s.
 */
public interface Metric
  extends fabric.metrics.util.Subject, java.lang.Comparable, fabric.lang.Object
{
    /** @return the current value of the quantity being measured. */
    public double value();
    
    /** @return the estimated value of the measured quantity's velocity */
    public double velocity();
    
    /**
   * @return the estimated value of the measured quantity's noise (the
   *         <em>variance</em> of the velocity estimate)
   */
    public double noise();
    
    /**
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
   * @return a {@link MetricContract} that enforces that the metric satisfies
   *       the given {@link Bound}
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
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the given time.
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
   * @return a {@link MetricContract} which enforces that the metric satisfies
   *       a {@link Bound} that enforces a {@link Bound} with the given
   *       parameters at the current time.
   */
    public fabric.metrics.contracts.MetricContract getContract(double rate, double base);
    
    /**
   * @param contract
   *        a {@link MetricContract} to store with this {@link Metric}
   * @throws IllegalArgumentException
   *         if contract isn't defined on this {@link Metric}
   */
    public void addContract(fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @param scalar
   *        a double scalar to scale this metric by
   * @return A {@link LinearMetric} that tracks the scaled value of this
   *       {@link Metric}
   */
    public fabric.metrics.DerivedMetric times(double scalar);
    
    /**
   * @param other
   *        another {@link Metric} to add with this {@link Metric},
   *        element wise.
   * @return a {@link LinearMetric} that tracks the value of the sum of other
   *       and this
   */
    public fabric.metrics.DerivedMetric plus(fabric.metrics.Metric other);
    
    /**
   * @param other
   *        another {@link Metric} to take the minimum of along with this
   *        {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the minimum of
   *       other and this's entries.
   */
    public fabric.metrics.Metric min(fabric.metrics.Metric other);
    
    /**
   * @param other
   *            another {@link Metric} to take the maximum of along with this
   *            {@link Metric}
   * @return a {@link LinearMetric} that tracks the value of the maximum of
   *         other and this's entries.
   */
    public fabric.metrics.Metric max(fabric.metrics.Metric other);
    
    /**
   * Track directly by an Observer.
   */
    public void startTracking(fabric.metrics.util.Observer obs);
    
    /**
   * Stop tracking directly by an Observer.
   */
    public void stopTracking(fabric.metrics.util.Observer obs);
    
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
        
        public void startTracking(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.Metric) fetch()).startTracking(arg1);
        }
        
        public void stopTracking(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.Metric) fetch()).stopTracking(arg1);
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
    
    public static final byte[] $classHash = new byte[] { -64, -56, 42, -47, -33,
    -125, 89, -75, -53, -25, 3, 104, 22, -116, -123, 102, 86, 102, -1, -126,
    100, 5, 39, 60, 44, -17, 62, 125, 82, -54, -6, 90 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1495740956000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
