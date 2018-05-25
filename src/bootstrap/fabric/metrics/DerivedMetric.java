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
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.DirectPolicy;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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
 * {@link MetricTreaty} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link MetricTreaty}s on the terms it is
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
    
    public boolean get$usePreset();
    
    public boolean set$usePreset(boolean val);
    
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
    
    public fabric.worker.metrics.ImmutableObserverSet handleDirectUpdates();
    
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
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
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
                    super(store, onum, version, expiry, observers, labelStore,
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
        
        public static final byte[] $classHash = new byte[] { 93, -24, -15, -22,
        68, -92, 46, -8, 9, -101, 67, -50, 5, -37, -92, -128, 73, -34, 77, -13,
        107, -16, -111, 52, -71, 48, -93, -5, -114, 48, 95, 62 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527269212000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
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
        
        public boolean get$usePreset() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$usePreset(
                                                                    );
        }
        
        public boolean set$usePreset(boolean val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$usePreset(
                                                                    val);
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
            fabric.common.ConfigProperties config =
              fabric.worker.Worker.getWorker().config;
            this.set$usePreset(config.usePreset);
            if (this.get$usePreset()) {
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
        
        public boolean get$usePreset() { return this.usePreset; }
        
        public boolean set$usePreset(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.usePreset = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public boolean usePreset;
        
        public boolean getUsePreset() { return this.get$usePreset(); }
        
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleDirectUpdates(
          ) {
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
                        double rtn$var28 = rtn;
                        fabric.worker.transaction.TransactionManager $tm34 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled37 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff35 = 1;
                        boolean $doBackoff36 = true;
                        boolean $retry31 = true;
                        $label29: for (boolean $commit30 = false; !$commit30;
                                       ) {
                            if ($backoffEnabled37) {
                                if ($doBackoff36) {
                                    if ($backoff35 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff35);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e32) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff35 < 5000) $backoff35 *= 2;
                                }
                                $doBackoff36 = $backoff35 <= 32 ||
                                                 !$doBackoff36;
                            }
                            $commit30 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedValue(); }
                            catch (final fabric.worker.RetryException $e32) {
                                $commit30 = false;
                                continue $label29;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e32) {
                                $commit30 = false;
                                fabric.common.TransactionID $currentTid33 =
                                  $tm34.getCurrentTid();
                                if ($e32.tid.isDescendantOf($currentTid33))
                                    continue $label29;
                                if ($currentTid33.parent != null) {
                                    $retry31 = false;
                                    throw $e32;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e32) {
                                $commit30 = false;
                                if ($tm34.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid33 =
                                  $tm34.getCurrentTid();
                                if ($e32.tid.isDescendantOf($currentTid33)) {
                                    $retry31 = true;
                                }
                                else if ($currentTid33.parent != null) {
                                    $retry31 = false;
                                    throw $e32;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e32) {
                                $commit30 = false;
                                if ($tm34.checkForStaleObjects())
                                    continue $label29;
                                $retry31 = false;
                                throw new fabric.worker.AbortException($e32);
                            }
                            finally {
                                if ($commit30) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e32) {
                                        $commit30 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e32) {
                                        $commit30 = false;
                                        fabric.common.TransactionID
                                          $currentTid33 = $tm34.getCurrentTid();
                                        if ($currentTid33 != null) {
                                            if ($e32.tid.equals(
                                                           $currentTid33) ||
                                                  !$e32.tid.isDescendantOf(
                                                              $currentTid33)) {
                                                throw $e32;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit30 && $retry31) {
                                    { rtn = rtn$var28; }
                                    continue $label29;
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
                        long rtn$var38 = rtn;
                        fabric.worker.transaction.TransactionManager $tm44 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled47 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff45 = 1;
                        boolean $doBackoff46 = true;
                        boolean $retry41 = true;
                        $label39: for (boolean $commit40 = false; !$commit40;
                                       ) {
                            if ($backoffEnabled47) {
                                if ($doBackoff46) {
                                    if ($backoff45 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff45);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e42) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff45 < 5000) $backoff45 *= 2;
                                }
                                $doBackoff46 = $backoff45 <= 32 ||
                                                 !$doBackoff46;
                            }
                            $commit40 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedSamples(); }
                            catch (final fabric.worker.RetryException $e42) {
                                $commit40 = false;
                                continue $label39;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e42) {
                                $commit40 = false;
                                fabric.common.TransactionID $currentTid43 =
                                  $tm44.getCurrentTid();
                                if ($e42.tid.isDescendantOf($currentTid43))
                                    continue $label39;
                                if ($currentTid43.parent != null) {
                                    $retry41 = false;
                                    throw $e42;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e42) {
                                $commit40 = false;
                                if ($tm44.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid43 =
                                  $tm44.getCurrentTid();
                                if ($e42.tid.isDescendantOf($currentTid43)) {
                                    $retry41 = true;
                                }
                                else if ($currentTid43.parent != null) {
                                    $retry41 = false;
                                    throw $e42;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e42) {
                                $commit40 = false;
                                if ($tm44.checkForStaleObjects())
                                    continue $label39;
                                $retry41 = false;
                                throw new fabric.worker.AbortException($e42);
                            }
                            finally {
                                if ($commit40) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e42) {
                                        $commit40 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e42) {
                                        $commit40 = false;
                                        fabric.common.TransactionID
                                          $currentTid43 = $tm44.getCurrentTid();
                                        if ($currentTid43 != null) {
                                            if ($e42.tid.equals(
                                                           $currentTid43) ||
                                                  !$e42.tid.isDescendantOf(
                                                              $currentTid43)) {
                                                throw $e42;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit40 && $retry41) {
                                    { rtn = rtn$var38; }
                                    continue $label39;
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
                        long rtn$var48 = rtn;
                        fabric.worker.transaction.TransactionManager $tm54 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled57 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff55 = 1;
                        boolean $doBackoff56 = true;
                        boolean $retry51 = true;
                        $label49: for (boolean $commit50 = false; !$commit50;
                                       ) {
                            if ($backoffEnabled57) {
                                if ($doBackoff56) {
                                    if ($backoff55 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff55);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e52) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff55 < 5000) $backoff55 *= 2;
                                }
                                $doBackoff56 = $backoff55 <= 32 ||
                                                 !$doBackoff56;
                            }
                            $commit50 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedLastUpdate(); }
                            catch (final fabric.worker.RetryException $e52) {
                                $commit50 = false;
                                continue $label49;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e52) {
                                $commit50 = false;
                                fabric.common.TransactionID $currentTid53 =
                                  $tm54.getCurrentTid();
                                if ($e52.tid.isDescendantOf($currentTid53))
                                    continue $label49;
                                if ($currentTid53.parent != null) {
                                    $retry51 = false;
                                    throw $e52;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e52) {
                                $commit50 = false;
                                if ($tm54.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid53 =
                                  $tm54.getCurrentTid();
                                if ($e52.tid.isDescendantOf($currentTid53)) {
                                    $retry51 = true;
                                }
                                else if ($currentTid53.parent != null) {
                                    $retry51 = false;
                                    throw $e52;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e52) {
                                $commit50 = false;
                                if ($tm54.checkForStaleObjects())
                                    continue $label49;
                                $retry51 = false;
                                throw new fabric.worker.AbortException($e52);
                            }
                            finally {
                                if ($commit50) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e52) {
                                        $commit50 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e52) {
                                        $commit50 = false;
                                        fabric.common.TransactionID
                                          $currentTid53 = $tm54.getCurrentTid();
                                        if ($currentTid53 != null) {
                                            if ($e52.tid.equals(
                                                           $currentTid53) ||
                                                  !$e52.tid.isDescendantOf(
                                                              $currentTid53)) {
                                                throw $e52;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit50 && $retry51) {
                                    { rtn = rtn$var48; }
                                    continue $label49;
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
                        double rtn$var58 = rtn;
                        fabric.worker.transaction.TransactionManager $tm64 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled67 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff65 = 1;
                        boolean $doBackoff66 = true;
                        boolean $retry61 = true;
                        $label59: for (boolean $commit60 = false; !$commit60;
                                       ) {
                            if ($backoffEnabled67) {
                                if ($doBackoff66) {
                                    if ($backoff65 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff65);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e62) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff65 < 5000) $backoff65 *= 2;
                                }
                                $doBackoff66 = $backoff65 <= 32 ||
                                                 !$doBackoff66;
                            }
                            $commit60 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedUpdateInterval(); }
                            catch (final fabric.worker.RetryException $e62) {
                                $commit60 = false;
                                continue $label59;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e62) {
                                $commit60 = false;
                                fabric.common.TransactionID $currentTid63 =
                                  $tm64.getCurrentTid();
                                if ($e62.tid.isDescendantOf($currentTid63))
                                    continue $label59;
                                if ($currentTid63.parent != null) {
                                    $retry61 = false;
                                    throw $e62;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e62) {
                                $commit60 = false;
                                if ($tm64.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid63 =
                                  $tm64.getCurrentTid();
                                if ($e62.tid.isDescendantOf($currentTid63)) {
                                    $retry61 = true;
                                }
                                else if ($currentTid63.parent != null) {
                                    $retry61 = false;
                                    throw $e62;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e62) {
                                $commit60 = false;
                                if ($tm64.checkForStaleObjects())
                                    continue $label59;
                                $retry61 = false;
                                throw new fabric.worker.AbortException($e62);
                            }
                            finally {
                                if ($commit60) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e62) {
                                        $commit60 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e62) {
                                        $commit60 = false;
                                        fabric.common.TransactionID
                                          $currentTid63 = $tm64.getCurrentTid();
                                        if ($currentTid63 != null) {
                                            if ($e62.tid.equals(
                                                           $currentTid63) ||
                                                  !$e62.tid.isDescendantOf(
                                                              $currentTid63)) {
                                                throw $e62;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit60 && $retry61) {
                                    { rtn = rtn$var58; }
                                    continue $label59;
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
            if (tmp.get$usePreset()) return tmp.get$presetV();
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
            if (tmp.get$usePreset()) return tmp.get$presetN();
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
                        java.util.concurrent.Callable c$var68 = c;
                        int i$var69 = i;
                        fabric.worker.transaction.TransactionManager $tm75 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled78 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff76 = 1;
                        boolean $doBackoff77 = true;
                        boolean $retry72 = true;
                        $label70: for (boolean $commit71 = false; !$commit71;
                                       ) {
                            if ($backoffEnabled78) {
                                if ($doBackoff77) {
                                    if ($backoff76 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff76);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e73) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff76 < 5000) $backoff76 *= 2;
                                }
                                $doBackoff77 = $backoff76 <= 32 ||
                                                 !$doBackoff77;
                            }
                            $commit71 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                c =
                                  (java.util.concurrent.Callable)
                                    fabric.lang.WrappedJavaInlineable.
                                    $unwrap(
                                      ((Refresher)
                                         new fabric.metrics.DerivedMetric.
                                           Refresher._Impl(this.$getStore()).
                                         $getProxy()).
                                          fabric$metrics$DerivedMetric$Refresher$(
                                            t, weakStats));
                            }
                            catch (final fabric.worker.RetryException $e73) {
                                $commit71 = false;
                                continue $label70;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e73) {
                                $commit71 = false;
                                fabric.common.TransactionID $currentTid74 =
                                  $tm75.getCurrentTid();
                                if ($e73.tid.isDescendantOf($currentTid74))
                                    continue $label70;
                                if ($currentTid74.parent != null) {
                                    $retry72 = false;
                                    throw $e73;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e73) {
                                $commit71 = false;
                                if ($tm75.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid74 =
                                  $tm75.getCurrentTid();
                                if ($e73.tid.isDescendantOf($currentTid74)) {
                                    $retry72 = true;
                                }
                                else if ($currentTid74.parent != null) {
                                    $retry72 = false;
                                    throw $e73;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e73) {
                                $commit71 = false;
                                if ($tm75.checkForStaleObjects())
                                    continue $label70;
                                $retry72 = false;
                                throw new fabric.worker.AbortException($e73);
                            }
                            finally {
                                if ($commit71) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e73) {
                                        $commit71 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e73) {
                                        $commit71 = false;
                                        fabric.common.TransactionID
                                          $currentTid74 = $tm75.getCurrentTid();
                                        if ($currentTid74 != null) {
                                            if ($e73.tid.equals(
                                                           $currentTid74) ||
                                                  !$e73.tid.isDescendantOf(
                                                              $currentTid74)) {
                                                throw $e73;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit71 && $retry72) {
                                    {
                                        c = c$var68;
                                        i = i$var69;
                                    }
                                    continue $label70;
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
            if (isSingleStore()) {
                return fabric.worker.metrics.treaties.enforcement.DirectPolicy.
                         singleton;
            }
            else {
                fabric.worker.metrics.treaties.MetricTreaty[]
                  witnesses =
                  new fabric.worker.metrics.treaties.MetricTreaty[this.
                                                                    get$terms().
                                                                    length()];
                for (int i = 0; i < this.get$terms().length(); i++) {
                    witnesses[i] = term(i).getEqualityTreaty(term(i).value());
                }
                return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                         witnesses);
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
            out.writeBoolean(this.usePreset);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
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
            this.usePreset = in.readBoolean();
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
            this.usePreset = src.usePreset;
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
                this.POOL_SIZE = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.DerivedMetric._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm84 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled87 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff85 = 1;
                        boolean $doBackoff86 = true;
                        boolean $retry81 = true;
                        $label79: for (boolean $commit80 = false; !$commit80;
                                       ) {
                            if ($backoffEnabled87) {
                                if ($doBackoff86) {
                                    if ($backoff85 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff85);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e82) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff85 < 5000) $backoff85 *= 2;
                                }
                                $doBackoff86 = $backoff85 <= 32 ||
                                                 !$doBackoff86;
                            }
                            $commit80 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
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
                            catch (final fabric.worker.RetryException $e82) {
                                $commit80 = false;
                                continue $label79;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e82) {
                                $commit80 = false;
                                fabric.common.TransactionID $currentTid83 =
                                  $tm84.getCurrentTid();
                                if ($e82.tid.isDescendantOf($currentTid83))
                                    continue $label79;
                                if ($currentTid83.parent != null) {
                                    $retry81 = false;
                                    throw $e82;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e82) {
                                $commit80 = false;
                                if ($tm84.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid83 =
                                  $tm84.getCurrentTid();
                                if ($e82.tid.isDescendantOf($currentTid83)) {
                                    $retry81 = true;
                                }
                                else if ($currentTid83.parent != null) {
                                    $retry81 = false;
                                    throw $e82;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e82) {
                                $commit80 = false;
                                if ($tm84.checkForStaleObjects())
                                    continue $label79;
                                $retry81 = false;
                                throw new fabric.worker.AbortException($e82);
                            }
                            finally {
                                if ($commit80) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e82) {
                                        $commit80 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e82) {
                                        $commit80 = false;
                                        fabric.common.TransactionID
                                          $currentTid83 = $tm84.getCurrentTid();
                                        if ($currentTid83 != null) {
                                            if ($e82.tid.equals(
                                                           $currentTid83) ||
                                                  !$e82.tid.isDescendantOf(
                                                              $currentTid83)) {
                                                throw $e82;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit80 && $retry81) {
                                    {  }
                                    continue $label79;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -8, 81, 94, 93, -63,
    90, 71, 76, -7, 8, 50, -30, 92, -24, 76, 6, 114, -1, -46, 80, -99, 72, -48,
    -38, 57, 41, -29, -1, 116, 27, -48, -22 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527269212000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
