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
    
    public double get$cachedNoiseTerm();
    
    public double set$cachedNoiseTerm(double val);
    
    public double postInc$cachedNoiseTerm();
    
    public double postDec$cachedNoiseTerm();
    
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
    
    public double get$presetNTerm();
    
    public double set$presetNTerm(double val);
    
    public double postInc$presetNTerm();
    
    public double postDec$presetNTerm();
    
    public double getPresetNTerm();
    
    public abstract double computePresetNTerm();
    
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
    
    public double noiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public static final byte[] $classHash = new byte[] { 80, -60, -76, -27,
        -95, 32, -128, -91, 42, -34, -72, 55, -51, -33, -71, -29, -74, -99, 77,
        -98, -89, 6, -4, 107, 1, 19, -124, -79, 19, 39, -16, -118 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1556815255000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates(
      final fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller,
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(double value, long time,
                     fabric.worker.metrics.StatsMap weakStats,
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
        
        public double get$cachedNoiseTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$cachedNoiseTerm();
        }
        
        public double set$cachedNoiseTerm(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$cachedNoiseTerm(val);
        }
        
        public double postInc$cachedNoiseTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$cachedNoiseTerm();
        }
        
        public double postDec$cachedNoiseTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$cachedNoiseTerm();
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
        
        public double get$presetNTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$presetNTerm();
        }
        
        public double set$presetNTerm(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              set$presetNTerm(val);
        }
        
        public double postInc$presetNTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$presetNTerm();
        }
        
        public double postDec$presetNTerm() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$presetNTerm();
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
        
        public double computePresetNTerm() {
            return ((fabric.metrics.DerivedMetric) fetch()).computePresetNTerm(
                                                              );
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
        
        public double get$cachedNoiseTerm() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.cachedNoiseTerm;
        }
        
        public double set$cachedNoiseTerm(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.cachedNoiseTerm = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$cachedNoiseTerm() {
            double tmp = this.get$cachedNoiseTerm();
            this.set$cachedNoiseTerm((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$cachedNoiseTerm() {
            double tmp = this.get$cachedNoiseTerm();
            this.set$cachedNoiseTerm((double) (tmp - 1));
            return tmp;
        }
        
        protected double cachedNoiseTerm;
        
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
                this.set$presetNTerm((double) computePresetNTerm());
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
        
        public double get$presetNTerm() { return this.presetNTerm; }
        
        public double set$presetNTerm(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.presetNTerm = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$presetNTerm() {
            double tmp = this.get$presetNTerm();
            this.set$presetNTerm((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$presetNTerm() {
            double tmp = this.get$presetNTerm();
            this.set$presetNTerm((double) (tmp - 1));
            return tmp;
        }
        
        public double presetNTerm;
        
        public double getPresetNTerm() { return this.get$presetNTerm(); }
        
        public abstract double computePresetNTerm();
        
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
                this.set$cachedNoiseTerm(
                       (double)
                         computeNoiseTerm(
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
                        double rtn$var0 = rtn;
                        fabric.worker.transaction.TransactionManager $tm7 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled10 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff8 = 1;
                        boolean $doBackoff9 = true;
                        boolean $retry3 = true;
                        boolean $keepReads4 = false;
                        $label1: for (boolean $commit2 = false; !$commit2; ) {
                            if ($backoffEnabled10) {
                                if ($doBackoff9) {
                                    if ($backoff8 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff8));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e5) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff8 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff8 =
                                          java.lang.Math.
                                            min(
                                              $backoff8 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff9 = $backoff8 <= 32 || !$doBackoff9;
                            }
                            $commit2 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedValue(); }
                            catch (final fabric.worker.RetryException $e5) {
                                $commit2 = false;
                                continue $label1;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e5) {
                                $commit2 = false;
                                $retry3 = false;
                                $keepReads4 = $e5.keepReads;
                                fabric.common.TransactionID $currentTid6 =
                                  $tm7.getCurrentTid();
                                if ($e5.tid == null ||
                                      !$e5.tid.isDescendantOf($currentTid6)) {
                                    throw $e5;
                                }
                                throw new fabric.worker.UserAbortException($e5);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e5) {
                                $commit2 = false;
                                fabric.common.TransactionID $currentTid6 =
                                  $tm7.getCurrentTid();
                                if ($e5.tid.isDescendantOf($currentTid6))
                                    continue $label1;
                                if ($currentTid6.parent != null) {
                                    $retry3 = false;
                                    throw $e5;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e5) {
                                $commit2 = false;
                                $retry3 = false;
                                if ($tm7.inNestedTxn()) { $keepReads4 = true; }
                                throw $e5;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid6 =
                                  $tm7.getCurrentTid();
                                if ($commit2) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e5) {
                                        $commit2 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e5) {
                                        $commit2 = false;
                                        $retry3 = false;
                                        $keepReads4 = $e5.keepReads;
                                        if ($e5.tid ==
                                              null ||
                                              !$e5.tid.isDescendantOf(
                                                         $currentTid6))
                                            throw $e5;
                                        throw new fabric.worker.
                                                UserAbortException($e5);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e5) {
                                        $commit2 = false;
                                        $currentTid6 = $tm7.getCurrentTid();
                                        if ($currentTid6 != null) {
                                            if ($e5.tid.equals($currentTid6) ||
                                                  !$e5.tid.isDescendantOf(
                                                             $currentTid6)) {
                                                throw $e5;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm7.inNestedTxn() &&
                                          $tm7.checkForStaleObjects()) {
                                        $retry3 = true;
                                        $keepReads4 = false;
                                    }
                                    if ($keepReads4) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e5) {
                                            $currentTid6 = $tm7.getCurrentTid();
                                            if ($currentTid6 != null &&
                                                  ($e5.tid.equals($currentTid6) || !$e5.tid.isDescendantOf($currentTid6))) {
                                                throw $e5;
                                            } else {
                                                $retry3 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit2) {
                                    { rtn = rtn$var0; }
                                    if ($retry3) { continue $label1; }
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
                        long rtn$var11 = rtn;
                        fabric.worker.transaction.TransactionManager $tm18 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled21 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff19 = 1;
                        boolean $doBackoff20 = true;
                        boolean $retry14 = true;
                        boolean $keepReads15 = false;
                        $label12: for (boolean $commit13 = false; !$commit13;
                                       ) {
                            if ($backoffEnabled21) {
                                if ($doBackoff20) {
                                    if ($backoff19 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff19));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e16) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff19 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff19 =
                                          java.lang.Math.
                                            min(
                                              $backoff19 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff20 = $backoff19 <= 32 ||
                                                 !$doBackoff20;
                            }
                            $commit13 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedSamples(); }
                            catch (final fabric.worker.RetryException $e16) {
                                $commit13 = false;
                                continue $label12;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e16) {
                                $commit13 = false;
                                $retry14 = false;
                                $keepReads15 = $e16.keepReads;
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                if ($e16.tid == null ||
                                      !$e16.tid.isDescendantOf($currentTid17)) {
                                    throw $e16;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e16);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e16) {
                                $commit13 = false;
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                if ($e16.tid.isDescendantOf($currentTid17))
                                    continue $label12;
                                if ($currentTid17.parent != null) {
                                    $retry14 = false;
                                    throw $e16;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e16) {
                                $commit13 = false;
                                $retry14 = false;
                                if ($tm18.inNestedTxn()) {
                                    $keepReads15 = true;
                                }
                                throw $e16;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                if ($commit13) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e16) {
                                        $commit13 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e16) {
                                        $commit13 = false;
                                        $retry14 = false;
                                        $keepReads15 = $e16.keepReads;
                                        if ($e16.tid ==
                                              null ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17))
                                            throw $e16;
                                        throw new fabric.worker.
                                                UserAbortException($e16);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e16) {
                                        $commit13 = false;
                                        $currentTid17 = $tm18.getCurrentTid();
                                        if ($currentTid17 != null) {
                                            if ($e16.tid.equals(
                                                           $currentTid17) ||
                                                  !$e16.tid.isDescendantOf(
                                                              $currentTid17)) {
                                                throw $e16;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm18.inNestedTxn() &&
                                          $tm18.checkForStaleObjects()) {
                                        $retry14 = true;
                                        $keepReads15 = false;
                                    }
                                    if ($keepReads15) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e16) {
                                            $currentTid17 = $tm18.getCurrentTid();
                                            if ($currentTid17 != null &&
                                                  ($e16.tid.equals($currentTid17) || !$e16.tid.isDescendantOf($currentTid17))) {
                                                throw $e16;
                                            } else {
                                                $retry14 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit13) {
                                    { rtn = rtn$var11; }
                                    if ($retry14) { continue $label12; }
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
                        long rtn$var22 = rtn;
                        fabric.worker.transaction.TransactionManager $tm29 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled32 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff30 = 1;
                        boolean $doBackoff31 = true;
                        boolean $retry25 = true;
                        boolean $keepReads26 = false;
                        $label23: for (boolean $commit24 = false; !$commit24;
                                       ) {
                            if ($backoffEnabled32) {
                                if ($doBackoff31) {
                                    if ($backoff30 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff30));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e27) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff30 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff30 =
                                          java.lang.Math.
                                            min(
                                              $backoff30 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff31 = $backoff30 <= 32 ||
                                                 !$doBackoff31;
                            }
                            $commit24 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedLastUpdate(); }
                            catch (final fabric.worker.RetryException $e27) {
                                $commit24 = false;
                                continue $label23;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e27) {
                                $commit24 = false;
                                $retry25 = false;
                                $keepReads26 = $e27.keepReads;
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($e27.tid == null ||
                                      !$e27.tid.isDescendantOf($currentTid28)) {
                                    throw $e27;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e27);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e27) {
                                $commit24 = false;
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($e27.tid.isDescendantOf($currentTid28))
                                    continue $label23;
                                if ($currentTid28.parent != null) {
                                    $retry25 = false;
                                    throw $e27;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e27) {
                                $commit24 = false;
                                $retry25 = false;
                                if ($tm29.inNestedTxn()) {
                                    $keepReads26 = true;
                                }
                                throw $e27;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid28 =
                                  $tm29.getCurrentTid();
                                if ($commit24) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e27) {
                                        $commit24 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e27) {
                                        $commit24 = false;
                                        $retry25 = false;
                                        $keepReads26 = $e27.keepReads;
                                        if ($e27.tid ==
                                              null ||
                                              !$e27.tid.isDescendantOf(
                                                          $currentTid28))
                                            throw $e27;
                                        throw new fabric.worker.
                                                UserAbortException($e27);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e27) {
                                        $commit24 = false;
                                        $currentTid28 = $tm29.getCurrentTid();
                                        if ($currentTid28 != null) {
                                            if ($e27.tid.equals(
                                                           $currentTid28) ||
                                                  !$e27.tid.isDescendantOf(
                                                              $currentTid28)) {
                                                throw $e27;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm29.inNestedTxn() &&
                                          $tm29.checkForStaleObjects()) {
                                        $retry25 = true;
                                        $keepReads26 = false;
                                    }
                                    if ($keepReads26) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e27) {
                                            $currentTid28 = $tm29.getCurrentTid();
                                            if ($currentTid28 != null &&
                                                  ($e27.tid.equals($currentTid28) || !$e27.tid.isDescendantOf($currentTid28))) {
                                                throw $e27;
                                            } else {
                                                $retry25 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit24) {
                                    { rtn = rtn$var22; }
                                    if ($retry25) { continue $label23; }
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
                        double rtn$var33 = rtn;
                        fabric.worker.transaction.TransactionManager $tm40 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled43 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff41 = 1;
                        boolean $doBackoff42 = true;
                        boolean $retry36 = true;
                        boolean $keepReads37 = false;
                        $label34: for (boolean $commit35 = false; !$commit35;
                                       ) {
                            if ($backoffEnabled43) {
                                if ($doBackoff42) {
                                    if ($backoff41 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff41));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e38) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff41 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff41 =
                                          java.lang.Math.
                                            min(
                                              $backoff41 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff42 = $backoff41 <= 32 ||
                                                 !$doBackoff42;
                            }
                            $commit35 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedUpdateInterval(); }
                            catch (final fabric.worker.RetryException $e38) {
                                $commit35 = false;
                                continue $label34;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e38) {
                                $commit35 = false;
                                $retry36 = false;
                                $keepReads37 = $e38.keepReads;
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($e38.tid == null ||
                                      !$e38.tid.isDescendantOf($currentTid39)) {
                                    throw $e38;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e38);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e38) {
                                $commit35 = false;
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($e38.tid.isDescendantOf($currentTid39))
                                    continue $label34;
                                if ($currentTid39.parent != null) {
                                    $retry36 = false;
                                    throw $e38;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e38) {
                                $commit35 = false;
                                $retry36 = false;
                                if ($tm40.inNestedTxn()) {
                                    $keepReads37 = true;
                                }
                                throw $e38;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($commit35) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e38) {
                                        $commit35 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e38) {
                                        $commit35 = false;
                                        $retry36 = false;
                                        $keepReads37 = $e38.keepReads;
                                        if ($e38.tid ==
                                              null ||
                                              !$e38.tid.isDescendantOf(
                                                          $currentTid39))
                                            throw $e38;
                                        throw new fabric.worker.
                                                UserAbortException($e38);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e38) {
                                        $commit35 = false;
                                        $currentTid39 = $tm40.getCurrentTid();
                                        if ($currentTid39 != null) {
                                            if ($e38.tid.equals(
                                                           $currentTid39) ||
                                                  !$e38.tid.isDescendantOf(
                                                              $currentTid39)) {
                                                throw $e38;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm40.inNestedTxn() &&
                                          $tm40.checkForStaleObjects()) {
                                        $retry36 = true;
                                        $keepReads37 = false;
                                    }
                                    if ($keepReads37) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e38) {
                                            $currentTid39 = $tm40.getCurrentTid();
                                            if ($currentTid39 != null &&
                                                  ($e38.tid.equals($currentTid39) || !$e38.tid.isDescendantOf($currentTid39))) {
                                                throw $e38;
                                            } else {
                                                $retry36 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit35) {
                                    { rtn = rtn$var33; }
                                    if ($retry36) { continue $label34; }
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
                               tmp.get$cachedNoiseTerm(),
                               tmp.get$cachedUpdateInterval(),
                               tmp.get$cachedSamples(),
                               tmp.get$cachedLastUpdate(),
                               java.lang.System.currentTimeMillis());
            }
            return tmp.computeNoise(weakStats);
        }
        
        public double noiseTerm(fabric.worker.metrics.StatsMap weakStats) {
            return fabric.metrics.DerivedMetric._Impl.
              static_noiseTerm((fabric.metrics.DerivedMetric) this.$getProxy(),
                               weakStats);
        }
        
        private static double static_noiseTerm(
          fabric.metrics.DerivedMetric tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (tmp.getUsePreset()) return tmp.get$presetNTerm();
            if (weakStats.containsKey(tmp)) return weakStats.getNoiseTerm(tmp);
            if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return fabric.worker.metrics.RunningMetricStats.
                  updatedNoiseTerm(tmp.get$cachedVelocity(),
                                   tmp.get$cachedNoise(),
                                   tmp.get$cachedNoiseTerm(),
                                   tmp.get$cachedUpdateInterval(),
                                   tmp.get$cachedSamples(),
                                   tmp.get$cachedLastUpdate(),
                                   java.lang.System.currentTimeMillis());
            }
            return tmp.computeNoiseTerm(weakStats);
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
                this.set$cachedNoiseTerm(
                       (double)
                         computeNoiseTerm(
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
                        java.util.concurrent.Callable c$var44 = c;
                        int i$var45 = i;
                        fabric.worker.transaction.TransactionManager $tm52 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled55 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff53 = 1;
                        boolean $doBackoff54 = true;
                        boolean $retry48 = true;
                        boolean $keepReads49 = false;
                        $label46: for (boolean $commit47 = false; !$commit47;
                                       ) {
                            if ($backoffEnabled55) {
                                if ($doBackoff54) {
                                    if ($backoff53 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff53));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e50) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff53 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff53 =
                                          java.lang.Math.
                                            min(
                                              $backoff53 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff54 = $backoff53 <= 32 ||
                                                 !$doBackoff54;
                            }
                            $commit47 = true;
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
                            catch (final fabric.worker.RetryException $e50) {
                                $commit47 = false;
                                continue $label46;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e50) {
                                $commit47 = false;
                                $retry48 = false;
                                $keepReads49 = $e50.keepReads;
                                fabric.common.TransactionID $currentTid51 =
                                  $tm52.getCurrentTid();
                                if ($e50.tid == null ||
                                      !$e50.tid.isDescendantOf($currentTid51)) {
                                    throw $e50;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e50);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e50) {
                                $commit47 = false;
                                fabric.common.TransactionID $currentTid51 =
                                  $tm52.getCurrentTid();
                                if ($e50.tid.isDescendantOf($currentTid51))
                                    continue $label46;
                                if ($currentTid51.parent != null) {
                                    $retry48 = false;
                                    throw $e50;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e50) {
                                $commit47 = false;
                                $retry48 = false;
                                if ($tm52.inNestedTxn()) {
                                    $keepReads49 = true;
                                }
                                throw $e50;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid51 =
                                  $tm52.getCurrentTid();
                                if ($commit47) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e50) {
                                        $commit47 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e50) {
                                        $commit47 = false;
                                        $retry48 = false;
                                        $keepReads49 = $e50.keepReads;
                                        if ($e50.tid ==
                                              null ||
                                              !$e50.tid.isDescendantOf(
                                                          $currentTid51))
                                            throw $e50;
                                        throw new fabric.worker.
                                                UserAbortException($e50);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e50) {
                                        $commit47 = false;
                                        $currentTid51 = $tm52.getCurrentTid();
                                        if ($currentTid51 != null) {
                                            if ($e50.tid.equals(
                                                           $currentTid51) ||
                                                  !$e50.tid.isDescendantOf(
                                                              $currentTid51)) {
                                                throw $e50;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm52.inNestedTxn() &&
                                          $tm52.checkForStaleObjects()) {
                                        $retry48 = true;
                                        $keepReads49 = false;
                                    }
                                    if ($keepReads49) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e50) {
                                            $currentTid51 = $tm52.getCurrentTid();
                                            if ($currentTid51 != null &&
                                                  ($e50.tid.equals($currentTid51) || !$e50.tid.isDescendantOf($currentTid51))) {
                                                throw $e50;
                                            } else {
                                                $retry48 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit47) {
                                    {
                                        c = c$var44;
                                        i = i$var45;
                                    }
                                    if ($retry48) { continue $label46; }
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
          equalityPolicy(double value, long time,
                         fabric.worker.metrics.StatsMap weakStats,
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
            out.writeDouble(this.cachedNoiseTerm);
            out.writeLong(this.cachedSamples);
            out.writeLong(this.cachedLastUpdate);
            out.writeDouble(this.cachedUpdateInterval);
            $writeInline(out, this.leafMetrics);
            out.writeBoolean(this.singleStore);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
            out.writeDouble(this.presetNTerm);
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
            this.cachedNoiseTerm = in.readDouble();
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
            this.presetNTerm = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.terms = src.terms;
            this.cachedValue = src.cachedValue;
            this.cachedVelocity = src.cachedVelocity;
            this.cachedNoise = src.cachedNoise;
            this.cachedNoiseTerm = src.cachedNoiseTerm;
            this.cachedSamples = src.cachedSamples;
            this.cachedLastUpdate = src.cachedLastUpdate;
            this.cachedUpdateInterval = src.cachedUpdateInterval;
            this.leafMetrics = src.leafMetrics;
            this.singleStore = src.singleStore;
            this.presetR = src.presetR;
            this.presetB = src.presetB;
            this.presetV = src.presetV;
            this.presetN = src.presetN;
            this.presetNTerm = src.presetNTerm;
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
                        fabric.worker.transaction.TransactionManager $tm62 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled65 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff63 = 1;
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
                                    if ($backoff63 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff63 =
                                          java.lang.Math.
                                            min(
                                              $backoff63 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff64 = $backoff63 <= 32 ||
                                                 !$doBackoff64;
                            }
                            $commit57 = true;
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
                            catch (final fabric.worker.RetryException $e60) {
                                $commit57 = false;
                                continue $label56;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e60) {
                                $commit57 = false;
                                $retry58 = false;
                                $keepReads59 = $e60.keepReads;
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
                                $retry58 = false;
                                if ($tm62.inNestedTxn()) {
                                    $keepReads59 = true;
                                }
                                throw $e60;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid61 =
                                  $tm62.getCurrentTid();
                                if ($commit57) {
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
                                } else {
                                    if (!$tm62.inNestedTxn() &&
                                          $tm62.checkForStaleObjects()) {
                                        $retry58 = true;
                                        $keepReads59 = false;
                                    }
                                    if ($keepReads59) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e60) {
                                            $currentTid61 = $tm62.getCurrentTid();
                                            if ($currentTid61 != null &&
                                                  ($e60.tid.equals($currentTid61) || !$e60.tid.isDescendantOf($currentTid61))) {
                                                throw $e60;
                                            } else {
                                                $retry58 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit57) {
                                    {  }
                                    if ($retry58) { continue $label56; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -89, 75, -45, 7, -109,
    -8, 100, -97, 88, -32, 86, -13, 121, -128, -55, 19, -64, 13, 89, -108, -4,
    -84, -6, -8, 42, 87, -114, -126, -55, -4, 55, -113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556815255000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
