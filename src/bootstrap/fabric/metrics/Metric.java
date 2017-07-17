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
    
    public static final byte[] $classHash = new byte[] { -67, -9, 46, -41, 18,
    26, -34, -20, 31, 31, -92, -76, -1, 3, 42, 85, -28, 73, 56, 76, 97, 109,
    -81, 67, -94, -99, -16, 29, 55, -26, -49, -106 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1500324933000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
