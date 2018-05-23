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
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.enforcement.DeadPolicy;
import fabric.metrics.contracts.enforcement.DirectEqualityPolicy;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.common.ConfigProperties;
import fabric.common.util.LongSet;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.RunningMetricStats;
import fabric.worker.metrics.StatsMap;
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
 * {@link DerivedContract} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link Contract}s on the terms it is
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
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, fabric.common.util.LongSet treaties);
    
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
        
        public static final byte[] $classHash = new byte[] { 127, -10, -53, -10,
        37, -39, 18, 64, 74, -115, 25, -59, 42, 35, 37, -7, 69, 15, -50, 72,
        -84, 106, -66, 55, -13, 92, 31, -12, 61, -67, 32, 46 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527104933000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates(
      final fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.StatsMap refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller,
      fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.DerivedMetric) fetch()).handleUpdates(arg1,
                                                                          arg2);
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            if (includesObserver) {
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
                    return getObservers();
                }
            }
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                         final fabric.worker.Store s) {
            if (value() == value) {
                if (isSingleStore()) {
                    return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                              new fabric.metrics.contracts.enforcement.
                                DirectEqualityPolicy._Impl(s).
                              $getProxy()).
                      fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(
                        (fabric.metrics.DerivedMetric) this.$getProxy(), value);
                }
                fabric.metrics.contracts.Contract[] witnesses =
                  new fabric.metrics.contracts.Contract[this.get$terms().length(
                                                                           )];
                for (int i = 0; i < this.get$terms().length(); i++) {
                    witnesses[i] = term(i).getEqualityContract(term(i).value());
                }
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
                    witnesses);
            }
            else {
                return ((fabric.metrics.contracts.enforcement.DeadPolicy)
                          new fabric.metrics.contracts.enforcement.DeadPolicy.
                            _Impl(s).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$DeadPolicy$();
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
    
    public static final byte[] $classHash = new byte[] { -85, -1, 89, -93, 62,
    -116, 26, 100, -26, -75, -126, -75, 124, 1, 72, 102, 114, -105, 96, 56, -18,
    -62, 25, -116, -49, 1, 121, -85, -36, 104, 23, -98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527104933000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
