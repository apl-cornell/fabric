package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.util.TreeSet;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.metrics.contracts.Bound;
import fabric.metrics.util.Observer;
import fabric.common.ConfigProperties;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.RunningMetricStats;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.NoPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;
import java.util.logging.Level;
import fabric.common.Logging;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * General base class for {@link Metric}s built by computing over other
 * {@link Metrics}. Each {@link DerivedMetric} implementation is responsible for
 * defining how to
 * <ul>
 * <li>construct the {@link #value()}, {@link #velocity()}, and {@link #noise()}
 * from its terms</li>
 * <li>provide a {@link EnforcementPolicy} to enforce a
 * {@link Treaty} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link Treaty}s on the terms it is
 * derived from.</li>
 * </ul>
 */
public interface DerivedMetric
  extends fabric.metrics.util.Observer, fabric.metrics.Metric {
    public fabric.worker.metrics.ImmutableMetricsVector get$terms();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$terms(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public double get$cachedValue();
    
    public double set$cachedValue(double val);
    
    public double postInc$cachedValue();
    
    public double postDec$cachedValue();
    
    public double get$cachedVelocity();
    
    public double set$cachedVelocity(double val);
    
    public double postInc$cachedVelocity();
    
    public double postDec$cachedVelocity();
    
    public double get$cachedNoise();
    
    public double set$cachedNoise(double val);
    
    public double postInc$cachedNoise();
    
    public double postDec$cachedNoise();
    
    public long get$cachedSamples();
    
    public long set$cachedSamples(long val);
    
    public long postInc$cachedSamples();
    
    public long postDec$cachedSamples();
    
    public long get$cachedLastUpdate();
    
    public long set$cachedLastUpdate(long val);
    
    public long postInc$cachedLastUpdate();
    
    public long postDec$cachedLastUpdate();
    
    public double get$cachedUpdateInterval();
    
    public double set$cachedUpdateInterval(double val);
    
    public double postInc$cachedUpdateInterval();
    
    public double postDec$cachedUpdateInterval();
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public boolean get$singleStore();
    
    public boolean set$singleStore(boolean val);
    
    /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
    public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
      fabric.metrics.Metric[] terms);
    
    public boolean getUsePreset();
    
    public double get$presetR();
    
    public double set$presetR(double val);
    
    public double postInc$presetR();
    
    public double postDec$presetR();
    
    public double getPresetR();
    
    public abstract double computePresetR();
    
    public double get$presetB();
    
    public double set$presetB(double val);
    
    public double postInc$presetB();
    
    public double postDec$presetB();
    
    public double getPresetB();
    
    public abstract double computePresetB();
    
    public double get$presetV();
    
    public double set$presetV(double val);
    
    public double postInc$presetV();
    
    public double postDec$presetV();
    
    public double getPresetV();
    
    public abstract double computePresetV();
    
    public double get$presetN();
    
    public double set$presetN(double val);
    
    public double postInc$presetN();
    
    public double postDec$presetN();
    
    public double getPresetN();
    
    public abstract double computePresetN();
    
    /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link getLeafSubjects()} result
   * is precomputed after the representation has been normalized.
   */
    public void initialize();
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
    public double value(fabric.worker.metrics.StatsMap weakStats);
    
    public long samples(fabric.worker.metrics.StatsMap weakStats);
    
    public long computeSamples(fabric.worker.metrics.StatsMap weakStats);
    
    public long lastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    public long computeLastUpdate(fabric.worker.metrics.StatsMap weakStats);
    
    public double updateInterval(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeUpdateInterval(
      fabric.worker.metrics.StatsMap weakStats);
    
    public double velocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double noise(fabric.worker.metrics.StatsMap weakStats);
    
    public boolean isSingleStore();
    
    /**
   * {@inheritDoc}
   *
   * If this is the first observer, then this metric is being monitored for
   * changes and so it stops computing on demand and instead caches the last
   * updated value (computed on checks). This metric then becomes an observer
   * of its terms.
   */
    public void addObserver(fabric.metrics.util.Observer obs);
    
    /**
   * {@inheritDoc}
   *
   * If there are no observers after removing the given one, this metric stops
   * acting as an observer of its terms (and goes back to computing on demand
   * rather than caching the last updated value).
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
    public fabric.metrics.Metric term(int i);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public static interface Refresher
      extends java.util.concurrent.Callable, fabric.lang.Object {
        public fabric.metrics.Metric get$t();
        
        public fabric.metrics.Metric set$t(fabric.metrics.Metric val);
        
        public fabric.worker.metrics.StatsMap get$weakStats();
        
        public fabric.worker.metrics.StatsMap set$weakStats(
          fabric.worker.metrics.StatsMap val);
        
        public Refresher fabric$metrics$DerivedMetric$Refresher$(
          fabric.metrics.Metric t, fabric.worker.metrics.StatsMap weakStats);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Refresher {
            public fabric.metrics.Metric get$t() {
                return ((fabric.metrics.DerivedMetric.Refresher._Impl) fetch()).
                  get$t();
            }
            
            public fabric.metrics.Metric set$t(fabric.metrics.Metric val) {
                return ((fabric.metrics.DerivedMetric.Refresher._Impl) fetch()).
                  set$t(val);
            }
            
            public fabric.worker.metrics.StatsMap get$weakStats() {
                return ((fabric.metrics.DerivedMetric.Refresher._Impl) fetch()).
                  get$weakStats();
            }
            
            public fabric.worker.metrics.StatsMap set$weakStats(
              fabric.worker.metrics.StatsMap val) {
                return ((fabric.metrics.DerivedMetric.Refresher._Impl) fetch()).
                  set$weakStats(val);
            }
            
            public fabric.metrics.DerivedMetric.Refresher
              fabric$metrics$DerivedMetric$Refresher$(
              fabric.metrics.Metric arg1, fabric.worker.metrics.StatsMap arg2) {
                return ((fabric.metrics.DerivedMetric.Refresher) fetch()).
                  fabric$metrics$DerivedMetric$Refresher$(arg1, arg2);
            }
            
            public java.lang.Object call() {
                return ((fabric.metrics.DerivedMetric.Refresher) fetch()).call(
                                                                            );
            }
            
            public _Proxy(Refresher._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements Refresher {
            public fabric.metrics.Metric get$t() { return this.t; }
            
            public fabric.metrics.Metric set$t(fabric.metrics.Metric val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.t = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.metrics.Metric t;
            
            public fabric.worker.metrics.StatsMap get$weakStats() {
                return this.weakStats;
            }
            
            public fabric.worker.metrics.StatsMap set$weakStats(
              fabric.worker.metrics.StatsMap val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.weakStats = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            fabric.worker.metrics.StatsMap weakStats;
            
            public Refresher fabric$metrics$DerivedMetric$Refresher$(
              fabric.metrics.Metric t,
              fabric.worker.metrics.StatsMap weakStats) {
                this.set$t(t);
                this.set$weakStats(weakStats);
                fabric$lang$Object$();
                return (Refresher) this.$getProxy();
            }
            
            public java.lang.Object call() {
                if (!this.get$t().$getStore().name().
                      equals(fabric.worker.Worker.getWorkerName())) {
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         this.get$t().$getStore(
                                                                        ).name(
                                                                            ));
                    return ((fabric.metrics.Metric._Proxy) this.get$t()).
                      refreshWeakEstimates$remote(w, null,
                                                  this.get$weakStats());
                }
                return this.get$t().refreshWeakEstimates(this.get$weakStats());
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.DerivedMetric.Refresher._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.t, refTypes, out, intraStoreRefs,
                          interStoreRefs);
                $writeInline(out, this.weakStats);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.t = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
                this.weakStats = (fabric.worker.metrics.StatsMap)
                                   in.readObject();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.DerivedMetric.Refresher._Impl src =
                  (fabric.metrics.DerivedMetric.Refresher._Impl) other;
                this.t = src.t;
                this.weakStats = src.weakStats;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.metrics.DerivedMetric.Refresher._Static {
                public _Proxy(fabric.metrics.DerivedMetric.Refresher._Static.
                                _Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.DerivedMetric.Refresher.
                  _Static $instance;
                
                static {
                    fabric.
                      metrics.
                      DerivedMetric.
                      Refresher.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        DerivedMetric.
                        Refresher.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.DerivedMetric.Refresher._Static.
                            _Impl.class);
                    $instance = (fabric.metrics.DerivedMetric.Refresher._Static)
                                  impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.metrics.DerivedMetric.Refresher._Static {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.DerivedMetric.Refresher._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 72, -121, -4, 51,
        78, -100, 117, -54, -45, 118, 18, 33, -109, 126, 125, -19, -61, -11,
        -116, -77, -9, -14, 9, -91, -13, -61, -95, -65, -64, 54, -75, 20 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1549232325000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVXa2xURRSe3bbbbqn0RXmUtrRlreG1G0BRWCXQFejKAk1bSCjKOnt3tr307r2XuXPpAkLQxMAPQ6I8hASamECMUDHREP9IJMEHiNH4iI8fIH9IMNgfSBB/4OPM3Lt7d2+3+MsmOzOdOXPmnDPf+ebc0TFUZlDUnsIJWQmynToxgqtxIhrrxtQgyYiCDaMPZuPSpNLo0VtvJ1u8yBtDVRJWNVWWsBJXDYYmx7bhHTikEhba2BMNb0F+iW/swsYgQ94tnRmKWnVN2TmgaMw+ZJz+I/NCh9/cWvN+CaruR9Wy2sswk6WIpjKSYf2oKk3SCUKNlckkSfajWpWQZC+hMlbkXSCoqf2ozpAHVMxMSoweYmjKDi5YZ5g6oeLM7CQ3XwOzqSkxjYL5NZb5JpOVUEw2WDiGfCmZKEljO9qLSmOoLKXgARCcGst6ERIaQ6v5PIhXymAmTWGJZLeUDslqkqFZ7h05jwNrQQC2lqcJG9RyR5WqGCZQnWWSgtWBUC+jsjoAomWaCacw1DihUhCq0LE0hAdInKHpbrluawmk/CIsfAtDDW4xoQnurNF1Z3m3Nbb+6YO71S7Vizxgc5JICre/Aja1uDb1kBShRJWItbFqbuwonnrhgBchEG5wCVsyH750Z8X8louXLZmZRWQ2JLYRicWlU4nJ3zRF5iwt4WZU6JohcygUeC5utdteCWd0QPvUnEa+GMwuXuz5bPO+M+S2F1VGkU/SFDMNqKqVtLQuK4SuISqhmJFkFPmJmoyI9Sgqh3FMVok1uyGVMgiLolJFTPk08T+EKAUqeIjKYSyrKS071jEbFOOMjhCqhR8qQcjzEULhJ6E/g9BT1xnqDg1qaRJKKCYZBniH4EcwlQZDkLdUlhZImr4zZFApRE2VySBpzYcAStAZoWchUwD568S/QbBF/x90ZrgfNcMeD4R4lqQlSQIbcF82djq7FUiPLk1JEhqXlIMXoqj+wnGBHz/HvAG4FRHywJ03udkif+9hs3PVnXPxqxb2+F47gAx1WDYGbRuDBTYGAIrg5CCwCEVVPL+CwFhBYKxRTyYYGYmeFTDyGSLfcjqrQOcyXcEspdF0Bnk8wsEpYr/AD9z+ELAKEEfVnN4XnnvxQDvcYEYfLoW75KIBdxo55BOFEYbciEvV+2/98d7RPZqTUAwFxuX5+J08T9vd0aKaRJLAg476ua34fPzCnoCXc4wf6I9hAChwSYv7jIJ8DWe5j0ejLIYm8RhghS9lCauSDVJt2JkRKJjMmzoLEDxYLgMFbT7Tq5/86atfF4sHJcuw1XlU3EtYOC+rubJqkb+1Tuz7KCEgd+1Y96EjY/u3iMCDxOxiBwZ4G4FsxpDGGn318vaff7l+6nuvc1kMlesAGEjyjHCm9h/488Dvb/7juckneA8MHbF5oTVHDDo/usMxDihCAZoC243ARjWtJeWUjBMK4VB5UP3owvO/Hayx7luBGSt6FM3/bwXO/IxOtO/q1vstQo1H4k+UE0BHzOK9ekfzSkrxTm5H5uVvm49/jk8C9IG1DHkXEUSERECQuMFFIhYLRLvQtfY4b9qtaDWJea8x/g1YzR9TB4z9odETjZHlt63kz4GR62grkvybcF6eLDqTvudt933qReX9qEa841hlmzCwGOCgH15iI2JPxtAjBeuFr6r1hIRzydbkToS8Y91p4JAOjLk0H1dayLeAA4Go5EGqB1SdRWjpNLsXiVGv83ZKxoPEYJnYMlu0HbyZk0WjX06nTcZvXOiex2BSiDXAq+2iOovj+GKjlXa8XVJoTTNoHQUrnrD79iLWdE5gDR8uz5rhHyZ4iFdoRtacFtucYY0OEZqzSsisw7oQm+EmU2FopviBXj6cyzgd80Iwk/PEyz2psd/Ga3b/dZ4neWD0ZM1rdqAPmJBMChTHghGsKDy6Wev83DpFg7I2kwEYN09U/4ja7dQrh0eSG04vtKqUusKaYpVqpt/94a8vg8duXCnyTvnsarYwbdrGVeHrRG3ooP/G7ealkaGbA9aZs1z2uaXfWTd6ZU2H9IYXleRgPq4gLdwULgR3JSVQT6t9BRBvzV3FJH4V/RDmcwCm1+2+Mx9UDhSL4dunmwkl/24FsVTailbafdh9tw4VeR2orODNGnGg9BDCEn48z9BjFl4DNlADE1QKAceBzYVut4N15xFa1mb3lRO4zZv4eAf5Fr/dl0zsYL7pykPWxOs4wFApYFfJgr5GgJ6zXdBiOz7fkwGcO5WQLdr0sMJJkArgc2aRos7+2JAin5BTN9fOb5igoJs+7vPP3ndupLpi2sjGH0VhkvuQ8MO7nzIVJZ9n88Y+nZKULBz3W6yri24YPkULHWHi+4qPhKemJbcLoGfJ8f926znWbMzGo85Wkxe84hQm9DWalH/mjt6d9qevou+GqCjgZlq79j9YvP6E+cV3O+raDu3dM3bp3msf3P/df/rupbc+vrjk/JR/AfR1FIJ+DwAA";
    }
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates(
      final fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller,
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                     final fabric.worker.Store s);
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.DerivedMetric {
        public fabric.worker.metrics.ImmutableMetricsVector get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$terms(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public double get$cachedValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedValue();
        }
        
        public double set$cachedValue(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedValue(val);
        }
        
        public double postInc$cachedValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedValue();
        }
        
        public double postDec$cachedValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedValue();
        }
        
        public double get$cachedVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedVelocity();
        }
        
        public double set$cachedVelocity(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedVelocity(val);
        }
        
        public double postInc$cachedVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedVelocity();
        }
        
        public double postDec$cachedVelocity() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedVelocity();
        }
        
        public double get$cachedNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedNoise();
        }
        
        public double set$cachedNoise(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedNoise(val);
        }
        
        public double postInc$cachedNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedNoise();
        }
        
        public double postDec$cachedNoise() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedNoise();
        }
        
        public long get$cachedSamples() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedSamples();
        }
        
        public long set$cachedSamples(long val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedSamples(val);
        }
        
        public long postInc$cachedSamples() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedSamples();
        }
        
        public long postDec$cachedSamples() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedSamples();
        }
        
        public long get$cachedLastUpdate() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedLastUpdate();
        }
        
        public long set$cachedLastUpdate(long val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedLastUpdate(val);
        }
        
        public long postInc$cachedLastUpdate() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedLastUpdate();
        }
        
        public long postDec$cachedLastUpdate() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedLastUpdate();
        }
        
        public double get$cachedUpdateInterval() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedUpdateInterval();
        }
        
        public double set$cachedUpdateInterval(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedUpdateInterval(val);
        }
        
        public double postInc$cachedUpdateInterval() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedUpdateInterval();
        }
        
        public double postDec$cachedUpdateInterval() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedUpdateInterval();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$leafMetrics(val);
        }
        
        public boolean get$singleStore() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$singleStore();
        }
        
        public boolean set$singleStore(boolean val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$singleStore(val);
        }
        
        public double get$presetR() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$presetR();
        }
        
        public double set$presetR(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$presetR(
                                                                    val);
        }
        
        public double postInc$presetR() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$presetR();
        }
        
        public double postDec$presetR() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$presetR();
        }
        
        public double get$presetB() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$presetB();
        }
        
        public double set$presetB(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$presetB(
                                                                    val);
        }
        
        public double postInc$presetB() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$presetB();
        }
        
        public double postDec$presetB() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$presetB();
        }
        
        public double get$presetV() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$presetV();
        }
        
        public double set$presetV(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$presetV(
                                                                    val);
        }
        
        public double postInc$presetV() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$presetV();
        }
        
        public double postDec$presetV() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$presetV();
        }
        
        public double get$presetN() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$presetN();
        }
        
        public double set$presetN(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$presetN(
                                                                    val);
        }
        
        public double postInc$presetN() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$presetN();
        }
        
        public double postDec$presetN() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$presetN();
        }
        
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).
              fabric$metrics$DerivedMetric$(arg1);
        }
        
        public double computePresetR() {
            return ((fabric.metrics.DerivedMetric) fetch()).computePresetR();
        }
        
        public double computePresetB() {
            return ((fabric.metrics.DerivedMetric) fetch()).computePresetB();
        }
        
        public double computePresetV() {
            return ((fabric.metrics.DerivedMetric) fetch()).computePresetV();
        }
        
        public double computePresetN() {
            return ((fabric.metrics.DerivedMetric) fetch()).computePresetN();
        }
        
        public void initialize() {
            ((fabric.metrics.DerivedMetric) fetch()).initialize();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            return ((fabric.metrics.DerivedMetric) fetch()).handleUpdates();
        }
        
        public fabric.metrics.Metric term(int arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).term(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.DerivedMetric) fetch()).getLeafSubjects();
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.DerivedMetric {
        public fabric.worker.metrics.ImmutableMetricsVector get$terms() {
            return this.terms;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$terms(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.terms = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.worker.metrics.ImmutableMetricsVector terms;
        
        public double get$cachedValue() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedValue;
        }
        
        public double set$cachedValue(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedValue = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedValue() {
            double tmp = this.get$cachedValue();
            this.set$cachedValue((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedValue() {
            double tmp = this.get$cachedValue();
            this.set$cachedValue((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedValue;
        
        public double get$cachedVelocity() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedVelocity;
        }
        
        public double set$cachedVelocity(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedVelocity = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedVelocity() {
            double tmp = this.get$cachedVelocity();
            this.set$cachedVelocity((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedVelocity() {
            double tmp = this.get$cachedVelocity();
            this.set$cachedVelocity((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedVelocity;
        
        public double get$cachedNoise() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedNoise;
        }
        
        public double set$cachedNoise(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedNoise = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedNoise() {
            double tmp = this.get$cachedNoise();
            this.set$cachedNoise((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedNoise() {
            double tmp = this.get$cachedNoise();
            this.set$cachedNoise((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedNoise;
        
        public long get$cachedSamples() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedSamples;
        }
        
        public long set$cachedSamples(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedSamples = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$cachedSamples() {
            long tmp = this.get$cachedSamples();
            this.set$cachedSamples((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$cachedSamples() {
            long tmp = this.get$cachedSamples();
            this.set$cachedSamples((long) (tmp - 1));
            return tmp;
        }
        
        protected long cachedSamples;
        
        public long get$cachedLastUpdate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedLastUpdate;
        }
        
        public long set$cachedLastUpdate(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedLastUpdate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$cachedLastUpdate() {
            long tmp = this.get$cachedLastUpdate();
            this.set$cachedLastUpdate((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$cachedLastUpdate() {
            long tmp = this.get$cachedLastUpdate();
            this.set$cachedLastUpdate((long) (tmp - 1));
            return tmp;
        }
        
        protected long cachedLastUpdate;
        
        public double get$cachedUpdateInterval() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedUpdateInterval;
        }
        
        public double set$cachedUpdateInterval(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedUpdateInterval = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedUpdateInterval() {
            double tmp = this.get$cachedUpdateInterval();
            this.set$cachedUpdateInterval((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedUpdateInterval() {
            double tmp = this.get$cachedUpdateInterval();
            this.set$cachedUpdateInterval((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedUpdateInterval;
        
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
        
        public boolean get$singleStore() { return this.singleStore; }
        
        public boolean set$singleStore(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.singleStore = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean singleStore;
        
        /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.metrics.Metric[] terms) {
            boolean single = true;
            for (int i = 0; i < terms.length; i++) {
                if (!terms[i].isSingleStore() ||
                      !terms[i].$getStore().equals($getStore())) {
                    single = false;
                    break;
                }
            }
            this.set$singleStore(single);
            this.set$terms(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  terms));
            if (getUsePreset()) {
                this.set$presetR((double) computePresetR());
                this.set$presetB((double) computePresetB());
                this.set$presetV((double) computePresetV());
                this.set$presetN((double) computePresetN());
            } else {
                this.set$presetR((double) 0.0);
                this.set$presetB((double) 0.0);
                this.set$presetV((double) 0.0);
                this.set$presetN((double) 0.0);
            }
            fabric$metrics$Metric$();
            return (fabric.metrics.DerivedMetric) this.$getProxy();
        }
        
        public boolean getUsePreset() {
            return fabric.worker.Worker.getWorker().config.usePreset;
        }
        
        public double get$presetR() { return this.presetR; }
        
        public double set$presetR(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetR = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetR() {
            double tmp = this.get$presetR();
            this.set$presetR((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetR() {
            double tmp = this.get$presetR();
            this.set$presetR((double) (tmp - 1));
            return tmp;
        }
        
        public double presetR;
        
        public double getPresetR() { return this.get$presetR(); }
        
        public abstract double computePresetR();
        
        public double get$presetB() { return this.presetB; }
        
        public double set$presetB(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetB = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetB() {
            double tmp = this.get$presetB();
            this.set$presetB((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetB() {
            double tmp = this.get$presetB();
            this.set$presetB((double) (tmp - 1));
            return tmp;
        }
        
        public double presetB;
        
        public double getPresetB() { return this.get$presetB(); }
        
        public abstract double computePresetB();
        
        public double get$presetV() { return this.presetV; }
        
        public double set$presetV(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetV = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetV() {
            double tmp = this.get$presetV();
            this.set$presetV((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetV() {
            double tmp = this.get$presetV();
            this.set$presetV((double) (tmp - 1));
            return tmp;
        }
        
        public double presetV;
        
        public double getPresetV() { return this.get$presetV(); }
        
        public abstract double computePresetV();
        
        public double get$presetN() { return this.presetN; }
        
        public double set$presetN(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetN = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetN() {
            double tmp = this.get$presetN();
            this.set$presetN((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetN() {
            double tmp = this.get$presetN();
            this.set$presetN((double) (tmp - 1));
            return tmp;
        }
        
        public double presetN;
        
        public double getPresetN() { return this.get$presetN(); }
        
        public abstract double computePresetN();
        
        /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link getLeafSubjects()} result
   * is precomputed after the representation has been normalized.
   */
        public void initialize() {
            java.util.Set leafMetricsTmp = new java.util.HashSet();
            for (int i = 0; i < this.get$terms().length(); i++) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            this.get$terms().
                                get(
                                  i))) instanceof fabric.metrics.SampledMetric) {
                    leafMetricsTmp.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$terms(
                                                                     ).get(i)));
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 this.get$terms().
                                     get(
                                       i))) instanceof fabric.metrics.DerivedMetric) {
                    fabric.metrics.DerivedMetric termI =
                      (fabric.metrics.DerivedMetric)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    this.get$terms().get(i));
                    fabric.worker.metrics.ImmutableMetricsVector termILeaves =
                      termI.getLeafSubjects();
                    for (int j = 0; j < termILeaves.length(); j++)
                        leafMetricsTmp.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(termILeaves.get(j)));
                }
                else {
                    throw new java.lang.IllegalStateException(
                            "This shouldn\'t happen, all metrics should either be a SampledMetric or a DerivedMetric!");
                }
            }
            fabric.metrics.Metric[] leafMetricsArr =
              new fabric.metrics.Metric[leafMetricsTmp.size()];
            int lmIdx = 0;
            for (java.util.Iterator it = leafMetricsTmp.iterator();
                 it.hasNext(); ) {
                leafMetricsArr[lmIdx++] =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(it.next()));
            }
            this.
              set$leafMetrics(
                fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                               leafMetricsArr));
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            double newValue =
              computeValue(fabric.worker.metrics.StatsMap.emptyStats());
            if (newValue != this.get$cachedValue()) {
                this.set$cachedValue((double) newValue);
                this.set$cachedVelocity(
                       (double)
                         computeVelocity(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedNoise(
                       (double)
                         computeNoise(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedSamples(
                       (long)
                         computeSamples(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedLastUpdate(
                       (long) java.lang.System.currentTimeMillis());
                this.set$cachedUpdateInterval(
                       (double)
                         computeUpdateInterval(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                affected = affected.addAll(getObservers());
            }
            return affected;
        }
        
        public double value(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_value((fabric.metrics.DerivedMetric) this.$getProxy(),
                           weakStats);
        }
        
        private static double static_value(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getValue(tmp);
            if (tmp.isObserved()) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      resolveObservations();
                    return tmp.get$cachedValue();
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var10 = rtn;
                        fabric.worker.transaction.TransactionManager $tm17 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled20 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff18 = 1;
                        boolean $doBackoff19 = true;
                        boolean $retry13 = true;
                        boolean $keepReads14 = false;
                        $label11: for (boolean $commit12 = false; !$commit12;
                                       ) {
                            if ($backoffEnabled20) {
                                if ($doBackoff19) {
                                    if ($backoff18 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff18));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e15) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff18 < 5000) $backoff18 *= 2;
                                }
                                $doBackoff19 = $backoff18 <= 32 ||
                                                 !$doBackoff19;
                            }
                            $commit12 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedValue(); }
                                catch (final fabric.worker.
                                         RetryException $e15) {
                                    throw $e15;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e15) {
                                    throw $e15;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e15) {
                                    throw $e15;
                                }
                                catch (final Throwable $e15) {
                                    $tm17.getCurrentLog().checkRetrySignal();
                                    throw $e15;
                                }
                            }
                            catch (final fabric.worker.RetryException $e15) {
                                $commit12 = false;
                                continue $label11;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e15) {
                                $commit12 = false;
                                $retry13 = false;
                                $keepReads14 = $e15.keepReads;
                                if ($tm17.checkForStaleObjects()) {
                                    $retry13 = true;
                                    $keepReads14 = false;
                                    continue $label11;
                                }
                                fabric.common.TransactionID $currentTid16 =
                                  $tm17.getCurrentTid();
                                if ($e15.tid == null ||
                                      !$e15.tid.isDescendantOf($currentTid16)) {
                                    throw $e15;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e15);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e15) {
                                $commit12 = false;
                                fabric.common.TransactionID $currentTid16 =
                                  $tm17.getCurrentTid();
                                if ($e15.tid.isDescendantOf($currentTid16))
                                    continue $label11;
                                if ($currentTid16.parent != null) {
                                    $retry13 = false;
                                    throw $e15;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e15) {
                                $commit12 = false;
                                if ($tm17.checkForStaleObjects())
                                    continue $label11;
                                $retry13 = false;
                                throw new fabric.worker.AbortException($e15);
                            }
                            finally {
                                if ($commit12) {
                                    fabric.common.TransactionID $currentTid16 =
                                      $tm17.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e15) {
                                        $commit12 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e15) {
                                        $commit12 = false;
                                        $retry13 = false;
                                        $keepReads14 = $e15.keepReads;
                                        if ($tm17.checkForStaleObjects()) {
                                            $retry13 = true;
                                            $keepReads14 = false;
                                            continue $label11;
                                        }
                                        if ($e15.tid ==
                                              null ||
                                              !$e15.tid.isDescendantOf(
                                                          $currentTid16))
                                            throw $e15;
                                        throw new fabric.worker.
                                                UserAbortException($e15);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e15) {
                                        $commit12 = false;
                                        $currentTid16 = $tm17.getCurrentTid();
                                        if ($currentTid16 != null) {
                                            if ($e15.tid.equals(
                                                           $currentTid16) ||
                                                  !$e15.tid.isDescendantOf(
                                                              $currentTid16)) {
                                                throw $e15;
                                            }
                                        }
                                    }
                                } else if ($keepReads14) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit12) {
                                    { rtn = rtn$var10; }
                                    if ($retry13) { continue $label11; }
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            return tmp.computeValue(weakStats);
        }
        
        public long samples(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_samples((fabric.metrics.DerivedMetric) this.$getProxy(),
                             weakStats);
        }
        
        private static long static_samples(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getSamples(tmp);
            if (tmp.isObserved()) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      resolveObservations();
                    return tmp.get$cachedSamples();
                }
                else {
                    long rtn = 0;
                    {
                        long rtn$var21 = rtn;
                        fabric.worker.transaction.TransactionManager $tm28 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled31 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff29 = 1;
                        boolean $doBackoff30 = true;
                        boolean $retry24 = true;
                        boolean $keepReads25 = false;
                        $label22: for (boolean $commit23 = false; !$commit23;
                                       ) {
                            if ($backoffEnabled31) {
                                if ($doBackoff30) {
                                    if ($backoff29 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff29));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e26) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff29 < 5000) $backoff29 *= 2;
                                }
                                $doBackoff30 = $backoff29 <= 32 ||
                                                 !$doBackoff30;
                            }
                            $commit23 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedSamples(); }
                                catch (final fabric.worker.
                                         RetryException $e26) {
                                    throw $e26;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e26) {
                                    throw $e26;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e26) {
                                    throw $e26;
                                }
                                catch (final Throwable $e26) {
                                    $tm28.getCurrentLog().checkRetrySignal();
                                    throw $e26;
                                }
                            }
                            catch (final fabric.worker.RetryException $e26) {
                                $commit23 = false;
                                continue $label22;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e26) {
                                $commit23 = false;
                                $retry24 = false;
                                $keepReads25 = $e26.keepReads;
                                if ($tm28.checkForStaleObjects()) {
                                    $retry24 = true;
                                    $keepReads25 = false;
                                    continue $label22;
                                }
                                fabric.common.TransactionID $currentTid27 =
                                  $tm28.getCurrentTid();
                                if ($e26.tid == null ||
                                      !$e26.tid.isDescendantOf($currentTid27)) {
                                    throw $e26;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e26);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e26) {
                                $commit23 = false;
                                fabric.common.TransactionID $currentTid27 =
                                  $tm28.getCurrentTid();
                                if ($e26.tid.isDescendantOf($currentTid27))
                                    continue $label22;
                                if ($currentTid27.parent != null) {
                                    $retry24 = false;
                                    throw $e26;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e26) {
                                $commit23 = false;
                                if ($tm28.checkForStaleObjects())
                                    continue $label22;
                                $retry24 = false;
                                throw new fabric.worker.AbortException($e26);
                            }
                            finally {
                                if ($commit23) {
                                    fabric.common.TransactionID $currentTid27 =
                                      $tm28.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e26) {
                                        $commit23 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e26) {
                                        $commit23 = false;
                                        $retry24 = false;
                                        $keepReads25 = $e26.keepReads;
                                        if ($tm28.checkForStaleObjects()) {
                                            $retry24 = true;
                                            $keepReads25 = false;
                                            continue $label22;
                                        }
                                        if ($e26.tid ==
                                              null ||
                                              !$e26.tid.isDescendantOf(
                                                          $currentTid27))
                                            throw $e26;
                                        throw new fabric.worker.
                                                UserAbortException($e26);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e26) {
                                        $commit23 = false;
                                        $currentTid27 = $tm28.getCurrentTid();
                                        if ($currentTid27 != null) {
                                            if ($e26.tid.equals(
                                                           $currentTid27) ||
                                                  !$e26.tid.isDescendantOf(
                                                              $currentTid27)) {
                                                throw $e26;
                                            }
                                        }
                                    }
                                } else if ($keepReads25) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit23) {
                                    { rtn = rtn$var21; }
                                    if ($retry24) { continue $label22; }
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            return tmp.computeSamples(weakStats);
        }
        
        public long computeSamples(fabric.worker.metrics.StatsMap weakStats) {
            long samples = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                samples = samples + term(i).samples(weakStats);
            }
            return samples;
        }
        
        public long lastUpdate(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_lastUpdate((fabric.metrics.DerivedMetric) this.$getProxy(),
                                weakStats);
        }
        
        private static long static_lastUpdate(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp)) return weakStats.getLastUpdate(tmp);
            if (tmp.isObserved()) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      resolveObservations();
                    return tmp.get$cachedLastUpdate();
                }
                else {
                    long rtn = 0;
                    {
                        long rtn$var32 = rtn;
                        fabric.worker.transaction.TransactionManager $tm39 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled42 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff40 = 1;
                        boolean $doBackoff41 = true;
                        boolean $retry35 = true;
                        boolean $keepReads36 = false;
                        $label33: for (boolean $commit34 = false; !$commit34;
                                       ) {
                            if ($backoffEnabled42) {
                                if ($doBackoff41) {
                                    if ($backoff40 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff40));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e37) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff40 < 5000) $backoff40 *= 2;
                                }
                                $doBackoff41 = $backoff40 <= 32 ||
                                                 !$doBackoff41;
                            }
                            $commit34 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedLastUpdate(); }
                                catch (final fabric.worker.
                                         RetryException $e37) {
                                    throw $e37;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e37) {
                                    throw $e37;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e37) {
                                    throw $e37;
                                }
                                catch (final Throwable $e37) {
                                    $tm39.getCurrentLog().checkRetrySignal();
                                    throw $e37;
                                }
                            }
                            catch (final fabric.worker.RetryException $e37) {
                                $commit34 = false;
                                continue $label33;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e37) {
                                $commit34 = false;
                                $retry35 = false;
                                $keepReads36 = $e37.keepReads;
                                if ($tm39.checkForStaleObjects()) {
                                    $retry35 = true;
                                    $keepReads36 = false;
                                    continue $label33;
                                }
                                fabric.common.TransactionID $currentTid38 =
                                  $tm39.getCurrentTid();
                                if ($e37.tid == null ||
                                      !$e37.tid.isDescendantOf($currentTid38)) {
                                    throw $e37;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e37);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e37) {
                                $commit34 = false;
                                fabric.common.TransactionID $currentTid38 =
                                  $tm39.getCurrentTid();
                                if ($e37.tid.isDescendantOf($currentTid38))
                                    continue $label33;
                                if ($currentTid38.parent != null) {
                                    $retry35 = false;
                                    throw $e37;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e37) {
                                $commit34 = false;
                                if ($tm39.checkForStaleObjects())
                                    continue $label33;
                                $retry35 = false;
                                throw new fabric.worker.AbortException($e37);
                            }
                            finally {
                                if ($commit34) {
                                    fabric.common.TransactionID $currentTid38 =
                                      $tm39.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e37) {
                                        $commit34 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e37) {
                                        $commit34 = false;
                                        $retry35 = false;
                                        $keepReads36 = $e37.keepReads;
                                        if ($tm39.checkForStaleObjects()) {
                                            $retry35 = true;
                                            $keepReads36 = false;
                                            continue $label33;
                                        }
                                        if ($e37.tid ==
                                              null ||
                                              !$e37.tid.isDescendantOf(
                                                          $currentTid38))
                                            throw $e37;
                                        throw new fabric.worker.
                                                UserAbortException($e37);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e37) {
                                        $commit34 = false;
                                        $currentTid38 = $tm39.getCurrentTid();
                                        if ($currentTid38 != null) {
                                            if ($e37.tid.equals(
                                                           $currentTid38) ||
                                                  !$e37.tid.isDescendantOf(
                                                              $currentTid38)) {
                                                throw $e37;
                                            }
                                        }
                                    }
                                } else if ($keepReads36) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit34) {
                                    { rtn = rtn$var32; }
                                    if ($retry35) { continue $label33; }
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            return tmp.computeLastUpdate(weakStats);
        }
        
        public long computeLastUpdate(
          fabric.worker.metrics.StatsMap weakStats) {
            long lastUpdate = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                lastUpdate = java.lang.Math.max(lastUpdate,
                                                term(i).lastUpdate(weakStats));
            }
            return lastUpdate;
        }
        
        public double updateInterval(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_updateInterval((fabric.metrics.DerivedMetric)
                                      this.$getProxy(), weakStats);
        }
        
        private static double static_updateInterval(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey(tmp))
                return weakStats.getUpdateInterval(tmp);
            if (tmp.isObserved()) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      resolveObservations();
                    return tmp.get$cachedUpdateInterval();
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var43 = rtn;
                        fabric.worker.transaction.TransactionManager $tm50 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled53 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff51 = 1;
                        boolean $doBackoff52 = true;
                        boolean $retry46 = true;
                        boolean $keepReads47 = false;
                        $label44: for (boolean $commit45 = false; !$commit45;
                                       ) {
                            if ($backoffEnabled53) {
                                if ($doBackoff52) {
                                    if ($backoff51 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff51));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e48) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff51 < 5000) $backoff51 *= 2;
                                }
                                $doBackoff52 = $backoff51 <= 32 ||
                                                 !$doBackoff52;
                            }
                            $commit45 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedUpdateInterval(); }
                                catch (final fabric.worker.
                                         RetryException $e48) {
                                    throw $e48;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e48) {
                                    throw $e48;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e48) {
                                    throw $e48;
                                }
                                catch (final Throwable $e48) {
                                    $tm50.getCurrentLog().checkRetrySignal();
                                    throw $e48;
                                }
                            }
                            catch (final fabric.worker.RetryException $e48) {
                                $commit45 = false;
                                continue $label44;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e48) {
                                $commit45 = false;
                                $retry46 = false;
                                $keepReads47 = $e48.keepReads;
                                if ($tm50.checkForStaleObjects()) {
                                    $retry46 = true;
                                    $keepReads47 = false;
                                    continue $label44;
                                }
                                fabric.common.TransactionID $currentTid49 =
                                  $tm50.getCurrentTid();
                                if ($e48.tid == null ||
                                      !$e48.tid.isDescendantOf($currentTid49)) {
                                    throw $e48;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e48);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e48) {
                                $commit45 = false;
                                fabric.common.TransactionID $currentTid49 =
                                  $tm50.getCurrentTid();
                                if ($e48.tid.isDescendantOf($currentTid49))
                                    continue $label44;
                                if ($currentTid49.parent != null) {
                                    $retry46 = false;
                                    throw $e48;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e48) {
                                $commit45 = false;
                                if ($tm50.checkForStaleObjects())
                                    continue $label44;
                                $retry46 = false;
                                throw new fabric.worker.AbortException($e48);
                            }
                            finally {
                                if ($commit45) {
                                    fabric.common.TransactionID $currentTid49 =
                                      $tm50.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e48) {
                                        $commit45 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e48) {
                                        $commit45 = false;
                                        $retry46 = false;
                                        $keepReads47 = $e48.keepReads;
                                        if ($tm50.checkForStaleObjects()) {
                                            $retry46 = true;
                                            $keepReads47 = false;
                                            continue $label44;
                                        }
                                        if ($e48.tid ==
                                              null ||
                                              !$e48.tid.isDescendantOf(
                                                          $currentTid49))
                                            throw $e48;
                                        throw new fabric.worker.
                                                UserAbortException($e48);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e48) {
                                        $commit45 = false;
                                        $currentTid49 = $tm50.getCurrentTid();
                                        if ($currentTid49 != null) {
                                            if ($e48.tid.equals(
                                                           $currentTid49) ||
                                                  !$e48.tid.isDescendantOf(
                                                              $currentTid49)) {
                                                throw $e48;
                                            }
                                        }
                                    }
                                } else if ($keepReads47) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit45) {
                                    { rtn = rtn$var43; }
                                    if ($retry46) { continue $label44; }
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            return tmp.computeUpdateInterval(weakStats);
        }
        
        public double computeUpdateInterval(
          fabric.worker.metrics.StatsMap weakStats) {
            double updateInterval = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                updateInterval =
                  java.lang.Math.min(updateInterval,
                                     term(i).updateInterval(weakStats));
            }
            return updateInterval;
        }
        
        public double velocity(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_velocity((fabric.metrics.DerivedMetric) this.$getProxy(),
                              weakStats);
        }
        
        private static double static_velocity(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (tmp.getUsePreset()) return tmp.get$presetV();
            if (weakStats.containsKey(tmp)) return weakStats.getVelocity(tmp);
            if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return fabric.worker.metrics.RunningMetricStats.
                  updatedVelocity(tmp.get$cachedVelocity(),
                                  tmp.get$cachedUpdateInterval(),
                                  tmp.get$cachedSamples(),
                                  tmp.get$cachedLastUpdate(),
                                  java.lang.System.currentTimeMillis());
            }
            return tmp.computeVelocity(weakStats);
        }
        
        public double noise(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_noise((fabric.metrics.DerivedMetric) this.$getProxy(),
                           weakStats);
        }
        
        private static double static_noise(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (tmp.getUsePreset()) return tmp.get$presetN();
            if (weakStats.containsKey(tmp)) return weakStats.getNoise(tmp);
            if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return fabric.worker.metrics.RunningMetricStats.
                  updatedNoise(tmp.get$cachedVelocity(), tmp.get$cachedNoise(),
                               tmp.get$cachedUpdateInterval(),
                               tmp.get$cachedSamples(),
                               tmp.get$cachedLastUpdate(),
                               java.lang.System.currentTimeMillis());
            }
            return tmp.computeNoise(weakStats);
        }
        
        public boolean isSingleStore() { return this.get$singleStore(); }
        
        /**
   * {@inheritDoc}
   *
   * If this is the first observer, then this metric is being monitored for
   * changes and so it stops computing on demand and instead caches the last
   * updated value (computed on checks). This metric then becomes an observer
   * of its terms.
   */
        public void addObserver(fabric.metrics.util.Observer obs) {
            if (!isObserved()) {
                for (int i = 0; i < this.get$terms().length(); i++) {
                    term(i).addObserver((fabric.metrics.DerivedMetric)
                                          this.$getProxy());
                }
                this.set$cachedValue(
                       (double)
                         computeValue(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedVelocity(
                       (double)
                         computeVelocity(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedNoise(
                       (double)
                         computeNoise(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedSamples(
                       (long)
                         computeSamples(
                           fabric.worker.metrics.StatsMap.emptyStats()));
                this.set$cachedLastUpdate(
                       (long) java.lang.System.currentTimeMillis());
                this.set$cachedUpdateInterval(
                       (double)
                         computeUpdateInterval(
                           fabric.worker.metrics.StatsMap.emptyStats()));
            }
            super.addObserver(obs);
        }
        
        /**
   * {@inheritDoc}
   *
   * If there are no observers after removing the given one, this metric stops
   * acting as an observer of its terms (and goes back to computing on demand
   * rather than caching the last updated value).
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            super.removeObserver(obs);
            if (!isObserved()) {
                for (int i = 0; i < this.get$terms().length(); i++) {
                    term(i).removeObserver((fabric.metrics.DerivedMetric)
                                             this.$getProxy());
                }
            }
        }
        
        /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
        public fabric.metrics.Metric term(int i) {
            return this.get$terms().get(i);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
        }
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates(
          final fabric.worker.metrics.StatsMap weakStats) {
            fabric.common.Logging.METRICS_LOGGER.
              finest(
                "REFRESHING" +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.DerivedMetric)
                                    this.$getProxy())));
            fabric.worker.metrics.StatsMap updated = weakStats;
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[this.get$terms().length() -
                                                    1];
                for (int i = 1; i < this.get$terms().length(); i++) {
                    final fabric.metrics.Metric t = term(i);
                    fabric.common.Logging.METRICS_LOGGER.
                      finest(
                        "(PARALLEL) CHILD " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(t)) +
                          " OF " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.DerivedMetric)
                                            this.$getProxy())));
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var54 = c;
                        int i$var55 = i;
                        fabric.worker.transaction.TransactionManager $tm62 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled65 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff63 = 1;
                        boolean $doBackoff64 = true;
                        boolean $retry58 = true;
                        boolean $keepReads59 = false;
                        $label56: for (boolean $commit57 = false; !$commit57;
                                       ) {
                            if ($backoffEnabled65) {
                                if ($doBackoff64) {
                                    if ($backoff63 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff63));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e60) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff63 < 5000) $backoff63 *= 2;
                                }
                                $doBackoff64 = $backoff63 <= 32 ||
                                                 !$doBackoff64;
                            }
                            $commit57 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    c =
                                      (java.util.concurrent.Callable)
                                        fabric.lang.WrappedJavaInlineable.
                                        $unwrap(
                                          ((Refresher)
                                             new fabric.metrics.DerivedMetric.
                                               Refresher._Impl(
                                               this.$getStore()).
                                             $getProxy()).
                                              fabric$metrics$DerivedMetric$Refresher$(
                                                t, weakStats));
                                }
                                catch (final fabric.worker.
                                         RetryException $e60) {
                                    throw $e60;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e60) {
                                    throw $e60;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e60) {
                                    throw $e60;
                                }
                                catch (final Throwable $e60) {
                                    $tm62.getCurrentLog().checkRetrySignal();
                                    throw $e60;
                                }
                            }
                            catch (final fabric.worker.RetryException $e60) {
                                $commit57 = false;
                                continue $label56;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e60) {
                                $commit57 = false;
                                $retry58 = false;
                                $keepReads59 = $e60.keepReads;
                                if ($tm62.checkForStaleObjects()) {
                                    $retry58 = true;
                                    $keepReads59 = false;
                                    continue $label56;
                                }
                                fabric.common.TransactionID $currentTid61 =
                                  $tm62.getCurrentTid();
                                if ($e60.tid == null ||
                                      !$e60.tid.isDescendantOf($currentTid61)) {
                                    throw $e60;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e60);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e60) {
                                $commit57 = false;
                                fabric.common.TransactionID $currentTid61 =
                                  $tm62.getCurrentTid();
                                if ($e60.tid.isDescendantOf($currentTid61))
                                    continue $label56;
                                if ($currentTid61.parent != null) {
                                    $retry58 = false;
                                    throw $e60;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e60) {
                                $commit57 = false;
                                if ($tm62.checkForStaleObjects())
                                    continue $label56;
                                $retry58 = false;
                                throw new fabric.worker.AbortException($e60);
                            }
                            finally {
                                if ($commit57) {
                                    fabric.common.TransactionID $currentTid61 =
                                      $tm62.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e60) {
                                        $commit57 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e60) {
                                        $commit57 = false;
                                        $retry58 = false;
                                        $keepReads59 = $e60.keepReads;
                                        if ($tm62.checkForStaleObjects()) {
                                            $retry58 = true;
                                            $keepReads59 = false;
                                            continue $label56;
                                        }
                                        if ($e60.tid ==
                                              null ||
                                              !$e60.tid.isDescendantOf(
                                                          $currentTid61))
                                            throw $e60;
                                        throw new fabric.worker.
                                                UserAbortException($e60);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e60) {
                                        $commit57 = false;
                                        $currentTid61 = $tm62.getCurrentTid();
                                        if ($currentTid61 != null) {
                                            if ($e60.tid.equals(
                                                           $currentTid61) ||
                                                  !$e60.tid.isDescendantOf(
                                                              $currentTid61)) {
                                                throw $e60;
                                            }
                                        }
                                    }
                                } else if ($keepReads59) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit57) {
                                    {
                                        c = c$var54;
                                        i = i$var55;
                                    }
                                    if ($retry58) { continue $label56; }
                                }
                            }
                        }
                    }
                    futures[i - 1] =
                      fabric.metrics.DerivedMetric._Static._Proxy.$instance.
                        get$service().submit(c);
                }
                fabric.metrics.Metric t = term(0);
                if (!t.$getStore().name().equals(
                                            fabric.worker.Worker.getWorkerName(
                                                                   ))) {
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         t.$getStore().name());
                    updated =
                      updated.merge(((fabric.metrics.Metric._Proxy)
                                       t).refreshWeakEstimates$remote(w, null,
                                                                      updated));
                } else {
                    updated = updated.merge(t.refreshWeakEstimates(updated));
                }
                for (int i = 0; i < futures.length; i++) {
                    try {
                        updated = updated.merge((fabric.worker.metrics.StatsMap)
                                                  futures[i].get());
                    }
                    catch (java.util.concurrent.ExecutionException e) {
                        java.io.StringWriter sw = new java.io.StringWriter();
                        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                        e.printStackTrace(pw);
                        java.lang.String sStackTrace = sw.toString();
                        fabric.common.Logging.METRICS_LOGGER.
                          warning("Failure of parallel child: " + e +
                                    " Stack\n" + sStackTrace);
                    }
                    catch (java.lang.InterruptedException e) {
                        java.io.StringWriter sw = new java.io.StringWriter();
                        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                        e.printStackTrace(pw);
                        java.lang.String sStackTrace = sw.toString();
                        fabric.common.Logging.METRICS_LOGGER.
                          warning("Failure of parallel child: " + e +
                                    " Stack\n" + sStackTrace);
                    }
                }
            }
            else {
                for (int i = 0; i < this.get$terms().length(); i++) {
                    fabric.common.Logging.METRICS_LOGGER.
                      finest(
                        "(SERIAL) CHILD " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  term(i))) +
                          " OF " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.DerivedMetric)
                                            this.$getProxy())));
                    updated =
                      updated.merge(term(i).refreshWeakEstimates(weakStats));
                }
            }
            return refreshLocally(updated);
        }
        
        public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
          fabric.lang.security.Principal caller,
          fabric.worker.metrics.StatsMap weakStats) {
            return refreshWeakEstimates(weakStats);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                         final fabric.worker.Store s) {
            if (value == value(weakStats)) {
                if (!isSingleStore()) {
                    com.google.common.collect.Multimap witnesses =
                      com.google.common.collect.HashMultimap.create();
                    for (int i = 0; i < this.get$terms().length(); i++) {
                        witnesses.
                          put(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  term(i)),
                            fabric.worker.metrics.treaties.statements.EqualityStatement.
                                create(term(i).value(weakStats)));
                    }
                    return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
                      create(witnesses);
                } else {
                    return fabric.worker.metrics.treaties.enforcement.DirectPolicy.singleton;
                }
            }
            else {
                return fabric.worker.metrics.treaties.enforcement.NoPolicy.
                         singleton;
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.DerivedMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.terms);
            out.writeDouble(this.cachedValue);
            out.writeDouble(this.cachedVelocity);
            out.writeDouble(this.cachedNoise);
            out.writeLong(this.cachedSamples);
            out.writeLong(this.cachedLastUpdate);
            out.writeDouble(this.cachedUpdateInterval);
            $writeInline(out, this.leafMetrics);
            out.writeBoolean(this.singleStore);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.terms = (fabric.worker.metrics.ImmutableMetricsVector)
                           in.readObject();
            this.cachedValue = in.readDouble();
            this.cachedVelocity = in.readDouble();
            this.cachedNoise = in.readDouble();
            this.cachedSamples = in.readLong();
            this.cachedLastUpdate = in.readLong();
            this.cachedUpdateInterval = in.readDouble();
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.singleStore = in.readBoolean();
            this.presetR = in.readDouble();
            this.presetB = in.readDouble();
            this.presetV = in.readDouble();
            this.presetN = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.terms = src.terms;
            this.cachedValue = src.cachedValue;
            this.cachedVelocity = src.cachedVelocity;
            this.cachedNoise = src.cachedNoise;
            this.cachedSamples = src.cachedSamples;
            this.cachedLastUpdate = src.cachedLastUpdate;
            this.cachedUpdateInterval = src.cachedUpdateInterval;
            this.leafMetrics = src.leafMetrics;
            this.singleStore = src.singleStore;
            this.presetR = src.presetR;
            this.presetB = src.presetB;
            this.presetV = src.presetV;
            this.presetN = src.presetN;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$POOL_SIZE();
        
        public int set$POOL_SIZE(int val);
        
        public int postInc$POOL_SIZE();
        
        public int postDec$POOL_SIZE();
        
        public java.util.concurrent.ExecutorService get$service();
        
        public java.util.concurrent.ExecutorService set$service(
          java.util.concurrent.ExecutorService val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.DerivedMetric._Static {
            public int get$POOL_SIZE() {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  get$POOL_SIZE();
            }
            
            public int set$POOL_SIZE(int val) {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  set$POOL_SIZE(val);
            }
            
            public int postInc$POOL_SIZE() {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  postInc$POOL_SIZE();
            }
            
            public int postDec$POOL_SIZE() {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  postDec$POOL_SIZE();
            }
            
            public java.util.concurrent.ExecutorService get$service() {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  get$service();
            }
            
            public java.util.concurrent.ExecutorService set$service(
              java.util.concurrent.ExecutorService val) {
                return ((fabric.metrics.DerivedMetric._Static._Impl) fetch()).
                  set$service(val);
            }
            
            public _Proxy(fabric.metrics.DerivedMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.DerivedMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  DerivedMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.DerivedMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.DerivedMetric._Static._Impl.class);
                $instance = (fabric.metrics.DerivedMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.DerivedMetric._Static {
            public int get$POOL_SIZE() { return this.POOL_SIZE; }
            
            public int set$POOL_SIZE(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POOL_SIZE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$POOL_SIZE() {
                int tmp = this.get$POOL_SIZE();
                this.set$POOL_SIZE((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$POOL_SIZE() {
                int tmp = this.get$POOL_SIZE();
                this.set$POOL_SIZE((int) (tmp - 1));
                return tmp;
            }
            
            private int POOL_SIZE;
            
            public java.util.concurrent.ExecutorService get$service() {
                return this.service;
            }
            
            public java.util.concurrent.ExecutorService set$service(
              java.util.concurrent.ExecutorService val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.service = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private java.util.concurrent.ExecutorService service;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.POOL_SIZE);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.POOL_SIZE = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.DerivedMetric._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm72 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled75 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff73 = 1;
                        boolean $doBackoff74 = true;
                        boolean $retry68 = true;
                        boolean $keepReads69 = false;
                        $label66: for (boolean $commit67 = false; !$commit67;
                                       ) {
                            if ($backoffEnabled75) {
                                if ($doBackoff74) {
                                    if ($backoff73 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff73));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e70) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff73 < 5000) $backoff73 *= 2;
                                }
                                $doBackoff74 = $backoff73 <= 32 ||
                                                 !$doBackoff74;
                            }
                            $commit67 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.metrics.DerivedMetric._Static._Proxy.
                                      $instance.
                                      set$POOL_SIZE((int) 32);
                                    fabric.metrics.DerivedMetric._Static._Proxy.
                                      $instance.
                                      set$service(
                                        java.util.concurrent.Executors.
                                            newFixedThreadPool(
                                              fabric.metrics.DerivedMetric._Static._Proxy.$instance.
                                                  get$POOL_SIZE()));
                                }
                                catch (final fabric.worker.
                                         RetryException $e70) {
                                    throw $e70;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e70) {
                                    throw $e70;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e70) {
                                    throw $e70;
                                }
                                catch (final Throwable $e70) {
                                    $tm72.getCurrentLog().checkRetrySignal();
                                    throw $e70;
                                }
                            }
                            catch (final fabric.worker.RetryException $e70) {
                                $commit67 = false;
                                continue $label66;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e70) {
                                $commit67 = false;
                                $retry68 = false;
                                $keepReads69 = $e70.keepReads;
                                if ($tm72.checkForStaleObjects()) {
                                    $retry68 = true;
                                    $keepReads69 = false;
                                    continue $label66;
                                }
                                fabric.common.TransactionID $currentTid71 =
                                  $tm72.getCurrentTid();
                                if ($e70.tid == null ||
                                      !$e70.tid.isDescendantOf($currentTid71)) {
                                    throw $e70;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e70);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e70) {
                                $commit67 = false;
                                fabric.common.TransactionID $currentTid71 =
                                  $tm72.getCurrentTid();
                                if ($e70.tid.isDescendantOf($currentTid71))
                                    continue $label66;
                                if ($currentTid71.parent != null) {
                                    $retry68 = false;
                                    throw $e70;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e70) {
                                $commit67 = false;
                                if ($tm72.checkForStaleObjects())
                                    continue $label66;
                                $retry68 = false;
                                throw new fabric.worker.AbortException($e70);
                            }
                            finally {
                                if ($commit67) {
                                    fabric.common.TransactionID $currentTid71 =
                                      $tm72.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e70) {
                                        $commit67 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e70) {
                                        $commit67 = false;
                                        $retry68 = false;
                                        $keepReads69 = $e70.keepReads;
                                        if ($tm72.checkForStaleObjects()) {
                                            $retry68 = true;
                                            $keepReads69 = false;
                                            continue $label66;
                                        }
                                        if ($e70.tid ==
                                              null ||
                                              !$e70.tid.isDescendantOf(
                                                          $currentTid71))
                                            throw $e70;
                                        throw new fabric.worker.
                                                UserAbortException($e70);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e70) {
                                        $commit67 = false;
                                        $currentTid71 = $tm72.getCurrentTid();
                                        if ($currentTid71 != null) {
                                            if ($e70.tid.equals(
                                                           $currentTid71) ||
                                                  !$e70.tid.isDescendantOf(
                                                              $currentTid71)) {
                                                throw $e70;
                                            }
                                        }
                                    }
                                } else if ($keepReads69) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit67) {
                                    {  }
                                    if ($retry68) { continue $label66; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 67, -29, 119, -39, 79,
    16, -27, 72, -18, 101, 50, -120, 10, -72, -47, -128, 88, 25, 64, 102, -34,
    -70, -81, 66, 54, -55, -32, 127, -127, 117, -37, -2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232325000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVbC3AV1Rk+e8mbQMIjIgFCgIjyyh1QGTT4ICFIJJAMAdSgppu95yZr9u5eds8NFxCFThVap2gRUWxl6ohVEbDWWkWL2o5VEOuo9YUKIpSiRUrxPa1K///suc/sLtkZysyeb9k9//mf5z//OXuz7TjJtUwyOiy3q1o1WxalVvUsub2hsVk2LRqq02TLWgBP25S+OQ0bP3koVBEggUZSrMi6oauKrLXpFiP9G2+Qu+WgTllw4fyGmsWkUEHC2bLVyUhgcW3cJJVRQ1vWoRlMMOkx/l0Tghvuvr70d31ISSspUfUWJjNVqTN0RuOslRRHaKSdmtaMUIiGWskAndJQCzVVWVOXQ0dDbyUDLbVDl1nMpNZ8ahlaN3YcaMWi1OQ8Ew9RfAPENmMKM0wQv9QWP8ZULdioWqymkeSFVaqFrCXkJpLTSHLDmtwBHc9qTGgR5CMGZ+Fz6F6kgphmWFZogiSnS9VDjIzMpkhqXDUHOgBpfoSyTiPJKkeX4QEZaIukyXpHsIWZqt4BXXONGHBhpNx1UOhUEJWVLrmDtjFydna/ZvsV9CrkZkESRsqyu/GRwGflWT5L89bxedPXrdBn6wEigcwhqmgofwEQVWQRzadhalJdoTZh8fjGjfJZu9YGCIHOZVmd7T5P3Xjy8okVL+y2+wxz6NPUfgNVWJuypb3/G8Prxl3UB8UoiBqWiqGQoTn3arN4UxOPQrSflRwRX1YnXr4w/6VrVm2lxwKkqIHkKYYWi0BUDVCMSFTVqHkF1akpMxpqIIVUD9Xx9w0kH+4bVZ3aT5vCYYuyBpKj8Ud5Bv8/mCgMQ6CJ8uFe1cNG4j4qs05+H48SQkrhIhIhgTJCrqyC+6mE5B9ipDnYaURosF2L0aUQ3kG4qGwqnUGYt6aqTFKM6LKgZSpBM6YzFXraz4MQSgBWcCbMFIj8ufy/1SBL9P8wZhz1KF0qSWDikYoRou2yBf4SsVPbrMH0mG1oIWq2Kdq6XQ1k0K5NPH4KMeYtiFtuIQl8Pjw7W6TTbojV1p/c0bbXjj2kFQZkZLgtY7WQsTpDRhCrGGdVNeSpashT26R4dd3mhkd58ORZfJYlRyqGkS6OajILG2YkTiSJqzWY0/OoAZ93QS6BdFE8ruW6K3+0dnQfCNfo0hz0IHStyp48qZTTAHcyzIg2pWTNJ18/tnGlkZpGjFT1mN09KXF2js62kWkoNATZLzX8+Er5ybZdK6sCmFkKIekxGcISMkhFNo+MWVqTyHhojdxG0hdtIGv4KpGmilinaSxNPeG+74/NQDsM0FhZAvJkeUlL9L73Xvv0fL6MJPJqSVoCbqGsJm0u42AlfNYOSNl+gUkp9Nt/T/Oddx1fs5gbHnqMcWJYhW0dzGEZJq9h3rJ7yb6PDmx5K5ByFiN50Vi7pipxrsuAU/BPgusHvHBC4gNESMt1IhlUJrNBFDmPTckGeUGD3ASiW1UL9YgRUsOq3K5RjJTvSs6Z/ORn60ptd2vwxDaeSSaefoDU86G1ZNXe67+p4MNICq5LKfulutnJblBq5BmmKS9DOeKr3xyx6WX5Poh8SFWWupzy7EO4PQh34BRui0m8nZz17gJsRtvWGs6fl1g9E/8sXEFTsdga3Par8rpLj9kzPhmLOMYohxm/SE6bJlO2Rr4KjM77S4Dkt5JSvnjLOlskQ+qCMGiF5deqEw8bSb+M95lLqb1u1CTn2vDseZDGNnsWpDIN3GNvvC+yA98OHDDEQDRSEK7phBRsE3g7vh0UxXZwXCL85mJOMoa3Y7EZxw0ZYKQwahoMpKRQPhSqkUiMofc5nwmM5EIFELE4WRkjE0W+W2qYXdRMpr2GBJVIfIsor3SQaGh2arNnK7ZTk1oUoxYj4ZoB0n8u8CMHLeqdtZDw9tJ4crwAjtdXjHNA4Ntp4zHSV5GVThriPnMIpmZTjUA+6BZVBF274WenqtdtsCeSXWqN6VHtpNPY5RbXth83ZRy4jPLiwilmHX1s5bMPr1xjlyIDMwuHej0W2f7O969W33Nwj8NilBcyIK1QVwOPhquWkMIyGwtOORh4vm1gbGb3NCdS/SDwqwxz9hfmpJqhqGwZPp3r6ek6EORCgaMdBLnKUxCkGiXwbCe/zjNUi55eiplAv1BgrYMU13pKgVQzBE7LkKKfLUWLHIlq1J49c0QUIMxjkI0Ne/V3dVU9jBoX2O4gW8hTNqSSBV6dIVupLVujbLGF0RAsKfhcdhXlPLhmwSD3C7zFQRTVUxSk+onAFRmiDLZFscVowC1Nt6y5eo0Xq+fDNRsGekfgEw7i6K65Lj8K9ZlQ+bJEiuurUTksMhc+muHKHCvkBmD6X4FHHZgzF+Z4a2bytaAA1GgLpErqFCL57YYBoulO4pSgOBW4QML9boE7HcS50UOc8RnigG2gImfzXe2f5HkzmKNV4BwHnqv98qw9Pc9bgddnAvc78LzVL89Fp+f5c0IG3CTQcOB5m1+e81x5DkGe42Ele46QGkVgswPPO5x59uHhxXBbgccYGcwLm5uaGttaGlrrnaKsj6ozV4muAUmeB0l2CVzhINEmL4mw2ZBpCgtmuSoOLqCkqErVi1BIKTETtgWsuj5OlRhMjJa0zlBKFGIpAUuMLDZ/cS8PMFIgt8NORlZYqjTg/9DDuNP9WOCbaWqlVZlSQsbsbR6XtqkdNbErynJc3Ue4HV/wlX3LjzdsDjU9ODkgitom0IYZ0Uka7aZaGtMxWCf0OB6byw9tUhXqwWMjLqrrOtJh1wkjszhn935k7rY9V4xV1gdIn2Qp2uOkKJOoJrMALTIpi5n6gowytDJp1UK06hy45kPKH25j0fvpwZIKMefqbXxW9VYgBtkn8G/ZLkptFaTkHnhYut2uhLjiOxC7hLse9nOvLzux0bZY9lFTWsd/b/vo2Jv9Ruzgm9gcPFPgGmef0fU8gss4WeMKFyd1wlKH1AgDFQvMSzdQItbKsmLNXph4kPXQmM9YbJ5JTMbnvCdEbljVxdyBqZinUb3DPg7aiM3OeJJDwCZLCGVv6nBLA1Ft6FQWdeavEx3sgwrVqE6ekSZ6xB2l3m5Lzbmmige+mE/w2Am+6vHuNWz2gI4KSpgQrDQlub0Zs4XiFH/wGI3H228ZGWG7o0q4oyrjhKcqFdXbM+cCFpcMEo0p8FqXucC17hn5SLJY4EL3yE8X+H2Pdx9i8zbsxDooW2jRZr4Y4bNlTrLj/F0OjL8SeMif7EjyscAPeif7YY93R7A5wEgRyN6cVqFkSc43wuPgWgkFwkUCR7tI7r5ib8lKQwPESKMEDuydQsc93p3A5ihumYxINMaoh1JJd6wG1msEMn/uQBJLYKR30n/t8e5bbE6mu6PW0x1Q+JfuFfj7M+IOHOkJgVt6pZBEPN5xZv/JdoejUkl3rAVJhgrMc1HKxR1Ikmtj6aneSV/o8a4vNjnp7ljk6Y7bgP11AmedEXfgSPUCp/ZOoUEe78qw6ZftDkelku64HVg/KvAef+5AkrsF/qJ30g/3eFeBzZB0d8zzdMd6YHtC4HtnxB040rsCd/dOobEe787DpjLbHY5K8dOCEXBtAgW/FHjYwx0ORwVIckjgvt5JH/R4NxkbKHugVlMZL0scN9o53YYacgquSXA9DluhCwSW+gsuJCkRWNg7bWo83l2CzVRG+nXKekij9qlJ8mh2/GmOZhPblRZqn1D0OJh1MgDG6IuElK8V2OnPAEjSIVB2N0B6YQiaVDhrgh/orbly1Fl6bqHZHtbDmJXqoDzsxiNfpwDm20PcxOwmZFiujeV7/cxKe7ubNStLxUivCPyjuyECqaEw1KQGLvkiD63waE9qBmPY+/42V+WS7vwQJikTuMCfO5GkReDc07ozpUCbhwIYFlIrHgvYZ6XYR3ZzzEFg/IXAnS6y+3MMjvS0wK1+HaN66NWFjQKJUzjGQz2eOPFcE1SrnC6wzF/iRJLBAvv6cI3poQIWmlIklftb3FXg0TURru8IGVVmY+Xn/qILSU4KPOZDhRUeKqzEphsWAC3ztNspwObxX0KQUfcKvO5MBBgf6VqB8/wG2C0eqq3B5mZGBogA89aQx9hUkGMQIWM+F7jbRUPnGOMkLwt8zoeD1nlocQc2P2X2r14gxho9teBhNgUGh6qv6lOBL/gKM07yvMCdPrS420OLTdish5kS6/klwynUFsDIlYScExd44RkJNRzpAoFj/Yba/R7qPYDNLxkpE6F2ei15uEEqkyCtnfuJwKf9hRuSPCVwhw9HbfXQZBs2D4ImItwcvjy5ZDbpMlg+/ypwq4cmDiGHJI8IfMCHJk94aPIkNjsYKehO/+TqltfqCBnfLHDQGQk2HGmgwBy/wbbLQ7HnsXmKkZJEReOhX6Koka4ipDrHxkl/9+ccJDkscL+7Ij2c85KHDphYpT9BraknP0O7lDTSYpB8tcArzohncKRZAqe5K+Tsmdc9tMKvJNIrqVrTVTnuFshCUoSQyY8LvNefW5Bkk8A73bVIl2+fxzs8iJTegr2TarWkvrpiT8cj0HOB83IoysYKLPAnPJLkC5R6E1PkIS7lIQ8NMKyl/Yz0lUOhtE9P0vlO8uOXxPWw4D8scI0/+ZHkVoGrfMh/zEP+49j8A9ZIk0aMbuqlAl89KmDk+wmZNk3gWA8VHFYPJDlH4PBeqbCRi/mlhwpfY3OCkRz8TRR2eNbJ9jNhvN8A1+0CTX+2R5IlArt6F/jfe7zDM0bpW0inHZQ1UjncEuNfQXjfGU7iXw68/wyLd4mNNW7HUi7iI8m7At/ojd3txBPIc9chgLMvIDEy2KRhk1qdV1G5q95iagTPP/gQTop0wvjfEFLfYOPMf/pTBEk+FXjYXZGsD2WJswv+wcmiSsyEpQt/56UralS2v/M6/SROWKHEwwq4IwwUMTLMyQptOKt40exsjFbQBDZW9UcFbvRnDCS5S+Dt7sbok/ryPzclCn4/zDzSSf3kxfEcJ+BxxBrAbzqBIZBH6JKYrIF1mw1NVZYlOE13PjxiJoU1i1rVVAc2Co3wHxmk7tMG6SkSLBsZn/3wZ67DHH5wLv4QQql7kW45MmdimcuPzc/u8acpgm7H5pKCIZsXvmt/eU78kUNhIykIxzQt/eegafd5UYgHlfuwkLf9o9xOE8BEmZ+SGf9CjXeoZ2Cc3a+akTy7H/4vyP1anmye5UOWx0z8s5ptXwz5Nq9gwUH+W2Zc4usOL93XVHpk9r/olLVFT7+x6uqhl4cPPPNY7dQ9B29eHfvgh/8BLA1Qbu4zAAA=";
}
