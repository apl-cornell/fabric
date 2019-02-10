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
        
        public static final byte[] $classHash = new byte[] { 100, -60, 119, -32,
        -116, 58, 85, -82, 96, 17, -26, 65, -8, -80, -6, -68, 72, 56, 47, -34,
        -70, -16, 9, 4, 59, 59, 88, 62, -21, 82, 66, 95 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1549748453000L;
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
                        double rtn$var31 = rtn;
                        fabric.worker.transaction.TransactionManager $tm38 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled41 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff39 = 1;
                        boolean $doBackoff40 = true;
                        boolean $retry34 = true;
                        boolean $keepReads35 = false;
                        $label32: for (boolean $commit33 = false; !$commit33;
                                       ) {
                            if ($backoffEnabled41) {
                                if ($doBackoff40) {
                                    if ($backoff39 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff39));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e36) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff39 < 5000) $backoff39 *= 2;
                                }
                                $doBackoff40 = $backoff39 <= 32 ||
                                                 !$doBackoff40;
                            }
                            $commit33 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedValue(); }
                                catch (final fabric.worker.
                                         RetryException $e36) {
                                    throw $e36;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e36) {
                                    throw $e36;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e36) {
                                    throw $e36;
                                }
                                catch (final Throwable $e36) {
                                    $tm38.getCurrentLog().checkRetrySignal();
                                    throw $e36;
                                }
                            }
                            catch (final fabric.worker.RetryException $e36) {
                                $commit33 = false;
                                continue $label32;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e36) {
                                $commit33 = false;
                                $retry34 = false;
                                $keepReads35 = $e36.keepReads;
                                if ($tm38.checkForStaleObjects()) {
                                    $retry34 = true;
                                    $keepReads35 = false;
                                    continue $label32;
                                }
                                fabric.common.TransactionID $currentTid37 =
                                  $tm38.getCurrentTid();
                                if ($e36.tid == null ||
                                      !$e36.tid.isDescendantOf($currentTid37)) {
                                    throw $e36;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e36);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e36) {
                                $commit33 = false;
                                fabric.common.TransactionID $currentTid37 =
                                  $tm38.getCurrentTid();
                                if ($e36.tid.isDescendantOf($currentTid37))
                                    continue $label32;
                                if ($currentTid37.parent != null) {
                                    $retry34 = false;
                                    throw $e36;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e36) {
                                $commit33 = false;
                                if ($tm38.checkForStaleObjects())
                                    continue $label32;
                                $retry34 = false;
                                throw new fabric.worker.AbortException($e36);
                            }
                            finally {
                                if ($commit33) {
                                    fabric.common.TransactionID $currentTid37 =
                                      $tm38.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e36) {
                                        $commit33 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e36) {
                                        $commit33 = false;
                                        $retry34 = false;
                                        $keepReads35 = $e36.keepReads;
                                        if ($tm38.checkForStaleObjects()) {
                                            $retry34 = true;
                                            $keepReads35 = false;
                                            continue $label32;
                                        }
                                        if ($e36.tid ==
                                              null ||
                                              !$e36.tid.isDescendantOf(
                                                          $currentTid37))
                                            throw $e36;
                                        throw new fabric.worker.
                                                UserAbortException($e36);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e36) {
                                        $commit33 = false;
                                        $currentTid37 = $tm38.getCurrentTid();
                                        if ($currentTid37 != null) {
                                            if ($e36.tid.equals(
                                                           $currentTid37) ||
                                                  !$e36.tid.isDescendantOf(
                                                              $currentTid37)) {
                                                throw $e36;
                                            }
                                        }
                                    }
                                } else if ($keepReads35) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit33) {
                                    { rtn = rtn$var31; }
                                    if ($retry34) { continue $label32; }
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
                        long rtn$var42 = rtn;
                        fabric.worker.transaction.TransactionManager $tm49 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled52 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff50 = 1;
                        boolean $doBackoff51 = true;
                        boolean $retry45 = true;
                        boolean $keepReads46 = false;
                        $label43: for (boolean $commit44 = false; !$commit44;
                                       ) {
                            if ($backoffEnabled52) {
                                if ($doBackoff51) {
                                    if ($backoff50 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff50));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e47) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff50 < 5000) $backoff50 *= 2;
                                }
                                $doBackoff51 = $backoff50 <= 32 ||
                                                 !$doBackoff51;
                            }
                            $commit44 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedSamples(); }
                                catch (final fabric.worker.
                                         RetryException $e47) {
                                    throw $e47;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e47) {
                                    throw $e47;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e47) {
                                    throw $e47;
                                }
                                catch (final Throwable $e47) {
                                    $tm49.getCurrentLog().checkRetrySignal();
                                    throw $e47;
                                }
                            }
                            catch (final fabric.worker.RetryException $e47) {
                                $commit44 = false;
                                continue $label43;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e47) {
                                $commit44 = false;
                                $retry45 = false;
                                $keepReads46 = $e47.keepReads;
                                if ($tm49.checkForStaleObjects()) {
                                    $retry45 = true;
                                    $keepReads46 = false;
                                    continue $label43;
                                }
                                fabric.common.TransactionID $currentTid48 =
                                  $tm49.getCurrentTid();
                                if ($e47.tid == null ||
                                      !$e47.tid.isDescendantOf($currentTid48)) {
                                    throw $e47;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e47);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e47) {
                                $commit44 = false;
                                fabric.common.TransactionID $currentTid48 =
                                  $tm49.getCurrentTid();
                                if ($e47.tid.isDescendantOf($currentTid48))
                                    continue $label43;
                                if ($currentTid48.parent != null) {
                                    $retry45 = false;
                                    throw $e47;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e47) {
                                $commit44 = false;
                                if ($tm49.checkForStaleObjects())
                                    continue $label43;
                                $retry45 = false;
                                throw new fabric.worker.AbortException($e47);
                            }
                            finally {
                                if ($commit44) {
                                    fabric.common.TransactionID $currentTid48 =
                                      $tm49.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e47) {
                                        $commit44 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e47) {
                                        $commit44 = false;
                                        $retry45 = false;
                                        $keepReads46 = $e47.keepReads;
                                        if ($tm49.checkForStaleObjects()) {
                                            $retry45 = true;
                                            $keepReads46 = false;
                                            continue $label43;
                                        }
                                        if ($e47.tid ==
                                              null ||
                                              !$e47.tid.isDescendantOf(
                                                          $currentTid48))
                                            throw $e47;
                                        throw new fabric.worker.
                                                UserAbortException($e47);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e47) {
                                        $commit44 = false;
                                        $currentTid48 = $tm49.getCurrentTid();
                                        if ($currentTid48 != null) {
                                            if ($e47.tid.equals(
                                                           $currentTid48) ||
                                                  !$e47.tid.isDescendantOf(
                                                              $currentTid48)) {
                                                throw $e47;
                                            }
                                        }
                                    }
                                } else if ($keepReads46) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit44) {
                                    { rtn = rtn$var42; }
                                    if ($retry45) { continue $label43; }
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
                        long rtn$var53 = rtn;
                        fabric.worker.transaction.TransactionManager $tm60 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled63 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff61 = 1;
                        boolean $doBackoff62 = true;
                        boolean $retry56 = true;
                        boolean $keepReads57 = false;
                        $label54: for (boolean $commit55 = false; !$commit55;
                                       ) {
                            if ($backoffEnabled63) {
                                if ($doBackoff62) {
                                    if ($backoff61 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff61));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e58) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff61 < 5000) $backoff61 *= 2;
                                }
                                $doBackoff62 = $backoff61 <= 32 ||
                                                 !$doBackoff62;
                            }
                            $commit55 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedLastUpdate(); }
                                catch (final fabric.worker.
                                         RetryException $e58) {
                                    throw $e58;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e58) {
                                    throw $e58;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e58) {
                                    throw $e58;
                                }
                                catch (final Throwable $e58) {
                                    $tm60.getCurrentLog().checkRetrySignal();
                                    throw $e58;
                                }
                            }
                            catch (final fabric.worker.RetryException $e58) {
                                $commit55 = false;
                                continue $label54;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e58) {
                                $commit55 = false;
                                $retry56 = false;
                                $keepReads57 = $e58.keepReads;
                                if ($tm60.checkForStaleObjects()) {
                                    $retry56 = true;
                                    $keepReads57 = false;
                                    continue $label54;
                                }
                                fabric.common.TransactionID $currentTid59 =
                                  $tm60.getCurrentTid();
                                if ($e58.tid == null ||
                                      !$e58.tid.isDescendantOf($currentTid59)) {
                                    throw $e58;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e58);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e58) {
                                $commit55 = false;
                                fabric.common.TransactionID $currentTid59 =
                                  $tm60.getCurrentTid();
                                if ($e58.tid.isDescendantOf($currentTid59))
                                    continue $label54;
                                if ($currentTid59.parent != null) {
                                    $retry56 = false;
                                    throw $e58;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e58) {
                                $commit55 = false;
                                if ($tm60.checkForStaleObjects())
                                    continue $label54;
                                $retry56 = false;
                                throw new fabric.worker.AbortException($e58);
                            }
                            finally {
                                if ($commit55) {
                                    fabric.common.TransactionID $currentTid59 =
                                      $tm60.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e58) {
                                        $commit55 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e58) {
                                        $commit55 = false;
                                        $retry56 = false;
                                        $keepReads57 = $e58.keepReads;
                                        if ($tm60.checkForStaleObjects()) {
                                            $retry56 = true;
                                            $keepReads57 = false;
                                            continue $label54;
                                        }
                                        if ($e58.tid ==
                                              null ||
                                              !$e58.tid.isDescendantOf(
                                                          $currentTid59))
                                            throw $e58;
                                        throw new fabric.worker.
                                                UserAbortException($e58);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e58) {
                                        $commit55 = false;
                                        $currentTid59 = $tm60.getCurrentTid();
                                        if ($currentTid59 != null) {
                                            if ($e58.tid.equals(
                                                           $currentTid59) ||
                                                  !$e58.tid.isDescendantOf(
                                                              $currentTid59)) {
                                                throw $e58;
                                            }
                                        }
                                    }
                                } else if ($keepReads57) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit55) {
                                    { rtn = rtn$var53; }
                                    if ($retry56) { continue $label54; }
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
                        double rtn$var64 = rtn;
                        fabric.worker.transaction.TransactionManager $tm71 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled74 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff72 = 1;
                        boolean $doBackoff73 = true;
                        boolean $retry67 = true;
                        boolean $keepReads68 = false;
                        $label65: for (boolean $commit66 = false; !$commit66;
                                       ) {
                            if ($backoffEnabled74) {
                                if ($doBackoff73) {
                                    if ($backoff72 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff72));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e69) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff72 < 5000) $backoff72 *= 2;
                                }
                                $doBackoff73 = $backoff72 <= 32 ||
                                                 !$doBackoff73;
                            }
                            $commit66 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try { rtn = tmp.get$cachedUpdateInterval(); }
                                catch (final fabric.worker.
                                         RetryException $e69) {
                                    throw $e69;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e69) {
                                    throw $e69;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e69) {
                                    throw $e69;
                                }
                                catch (final Throwable $e69) {
                                    $tm71.getCurrentLog().checkRetrySignal();
                                    throw $e69;
                                }
                            }
                            catch (final fabric.worker.RetryException $e69) {
                                $commit66 = false;
                                continue $label65;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e69) {
                                $commit66 = false;
                                $retry67 = false;
                                $keepReads68 = $e69.keepReads;
                                if ($tm71.checkForStaleObjects()) {
                                    $retry67 = true;
                                    $keepReads68 = false;
                                    continue $label65;
                                }
                                fabric.common.TransactionID $currentTid70 =
                                  $tm71.getCurrentTid();
                                if ($e69.tid == null ||
                                      !$e69.tid.isDescendantOf($currentTid70)) {
                                    throw $e69;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e69);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e69) {
                                $commit66 = false;
                                fabric.common.TransactionID $currentTid70 =
                                  $tm71.getCurrentTid();
                                if ($e69.tid.isDescendantOf($currentTid70))
                                    continue $label65;
                                if ($currentTid70.parent != null) {
                                    $retry67 = false;
                                    throw $e69;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e69) {
                                $commit66 = false;
                                if ($tm71.checkForStaleObjects())
                                    continue $label65;
                                $retry67 = false;
                                throw new fabric.worker.AbortException($e69);
                            }
                            finally {
                                if ($commit66) {
                                    fabric.common.TransactionID $currentTid70 =
                                      $tm71.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e69) {
                                        $commit66 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e69) {
                                        $commit66 = false;
                                        $retry67 = false;
                                        $keepReads68 = $e69.keepReads;
                                        if ($tm71.checkForStaleObjects()) {
                                            $retry67 = true;
                                            $keepReads68 = false;
                                            continue $label65;
                                        }
                                        if ($e69.tid ==
                                              null ||
                                              !$e69.tid.isDescendantOf(
                                                          $currentTid70))
                                            throw $e69;
                                        throw new fabric.worker.
                                                UserAbortException($e69);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e69) {
                                        $commit66 = false;
                                        $currentTid70 = $tm71.getCurrentTid();
                                        if ($currentTid70 != null) {
                                            if ($e69.tid.equals(
                                                           $currentTid70) ||
                                                  !$e69.tid.isDescendantOf(
                                                              $currentTid70)) {
                                                throw $e69;
                                            }
                                        }
                                    }
                                } else if ($keepReads68) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit66) {
                                    { rtn = rtn$var64; }
                                    if ($retry67) { continue $label65; }
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
                        java.util.concurrent.Callable c$var75 = c;
                        int i$var76 = i;
                        fabric.worker.transaction.TransactionManager $tm83 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled86 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff84 = 1;
                        boolean $doBackoff85 = true;
                        boolean $retry79 = true;
                        boolean $keepReads80 = false;
                        $label77: for (boolean $commit78 = false; !$commit78;
                                       ) {
                            if ($backoffEnabled86) {
                                if ($doBackoff85) {
                                    if ($backoff84 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff84));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e81) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff84 < 5000) $backoff84 *= 2;
                                }
                                $doBackoff85 = $backoff84 <= 32 ||
                                                 !$doBackoff85;
                            }
                            $commit78 = true;
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
                                         RetryException $e81) {
                                    throw $e81;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e81) {
                                    throw $e81;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e81) {
                                    throw $e81;
                                }
                                catch (final Throwable $e81) {
                                    $tm83.getCurrentLog().checkRetrySignal();
                                    throw $e81;
                                }
                            }
                            catch (final fabric.worker.RetryException $e81) {
                                $commit78 = false;
                                continue $label77;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e81) {
                                $commit78 = false;
                                $retry79 = false;
                                $keepReads80 = $e81.keepReads;
                                if ($tm83.checkForStaleObjects()) {
                                    $retry79 = true;
                                    $keepReads80 = false;
                                    continue $label77;
                                }
                                fabric.common.TransactionID $currentTid82 =
                                  $tm83.getCurrentTid();
                                if ($e81.tid == null ||
                                      !$e81.tid.isDescendantOf($currentTid82)) {
                                    throw $e81;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e81);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e81) {
                                $commit78 = false;
                                fabric.common.TransactionID $currentTid82 =
                                  $tm83.getCurrentTid();
                                if ($e81.tid.isDescendantOf($currentTid82))
                                    continue $label77;
                                if ($currentTid82.parent != null) {
                                    $retry79 = false;
                                    throw $e81;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e81) {
                                $commit78 = false;
                                if ($tm83.checkForStaleObjects())
                                    continue $label77;
                                $retry79 = false;
                                throw new fabric.worker.AbortException($e81);
                            }
                            finally {
                                if ($commit78) {
                                    fabric.common.TransactionID $currentTid82 =
                                      $tm83.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e81) {
                                        $commit78 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e81) {
                                        $commit78 = false;
                                        $retry79 = false;
                                        $keepReads80 = $e81.keepReads;
                                        if ($tm83.checkForStaleObjects()) {
                                            $retry79 = true;
                                            $keepReads80 = false;
                                            continue $label77;
                                        }
                                        if ($e81.tid ==
                                              null ||
                                              !$e81.tid.isDescendantOf(
                                                          $currentTid82))
                                            throw $e81;
                                        throw new fabric.worker.
                                                UserAbortException($e81);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e81) {
                                        $commit78 = false;
                                        $currentTid82 = $tm83.getCurrentTid();
                                        if ($currentTid82 != null) {
                                            if ($e81.tid.equals(
                                                           $currentTid82) ||
                                                  !$e81.tid.isDescendantOf(
                                                              $currentTid82)) {
                                                throw $e81;
                                            }
                                        }
                                    }
                                } else if ($keepReads80) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit78) {
                                    {
                                        c = c$var75;
                                        i = i$var76;
                                    }
                                    if ($retry79) { continue $label77; }
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
                        fabric.worker.transaction.TransactionManager $tm93 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled96 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff94 = 1;
                        boolean $doBackoff95 = true;
                        boolean $retry89 = true;
                        boolean $keepReads90 = false;
                        $label87: for (boolean $commit88 = false; !$commit88;
                                       ) {
                            if ($backoffEnabled96) {
                                if ($doBackoff95) {
                                    if ($backoff94 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff94));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e91) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff94 < 5000) $backoff94 *= 2;
                                }
                                $doBackoff95 = $backoff94 <= 32 ||
                                                 !$doBackoff95;
                            }
                            $commit88 = true;
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
                                         RetryException $e91) {
                                    throw $e91;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e91) {
                                    throw $e91;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e91) {
                                    throw $e91;
                                }
                                catch (final Throwable $e91) {
                                    $tm93.getCurrentLog().checkRetrySignal();
                                    throw $e91;
                                }
                            }
                            catch (final fabric.worker.RetryException $e91) {
                                $commit88 = false;
                                continue $label87;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e91) {
                                $commit88 = false;
                                $retry89 = false;
                                $keepReads90 = $e91.keepReads;
                                if ($tm93.checkForStaleObjects()) {
                                    $retry89 = true;
                                    $keepReads90 = false;
                                    continue $label87;
                                }
                                fabric.common.TransactionID $currentTid92 =
                                  $tm93.getCurrentTid();
                                if ($e91.tid == null ||
                                      !$e91.tid.isDescendantOf($currentTid92)) {
                                    throw $e91;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e91);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e91) {
                                $commit88 = false;
                                fabric.common.TransactionID $currentTid92 =
                                  $tm93.getCurrentTid();
                                if ($e91.tid.isDescendantOf($currentTid92))
                                    continue $label87;
                                if ($currentTid92.parent != null) {
                                    $retry89 = false;
                                    throw $e91;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e91) {
                                $commit88 = false;
                                if ($tm93.checkForStaleObjects())
                                    continue $label87;
                                $retry89 = false;
                                throw new fabric.worker.AbortException($e91);
                            }
                            finally {
                                if ($commit88) {
                                    fabric.common.TransactionID $currentTid92 =
                                      $tm93.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e91) {
                                        $commit88 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e91) {
                                        $commit88 = false;
                                        $retry89 = false;
                                        $keepReads90 = $e91.keepReads;
                                        if ($tm93.checkForStaleObjects()) {
                                            $retry89 = true;
                                            $keepReads90 = false;
                                            continue $label87;
                                        }
                                        if ($e91.tid ==
                                              null ||
                                              !$e91.tid.isDescendantOf(
                                                          $currentTid92))
                                            throw $e91;
                                        throw new fabric.worker.
                                                UserAbortException($e91);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e91) {
                                        $commit88 = false;
                                        $currentTid92 = $tm93.getCurrentTid();
                                        if ($currentTid92 != null) {
                                            if ($e91.tid.equals(
                                                           $currentTid92) ||
                                                  !$e91.tid.isDescendantOf(
                                                              $currentTid92)) {
                                                throw $e91;
                                            }
                                        }
                                    }
                                } else if ($keepReads90) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit88) {
                                    {  }
                                    if ($retry89) { continue $label87; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -128, -87, 93, 116, 6,
    81, 88, 28, -22, -117, 6, 51, 36, 59, 86, -85, 75, -71, -86, 101, 47, -115,
    47, 65, 40, -56, -81, 56, -17, 77, -119, 14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
