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
import fabric.metrics.util.RunningMetricStats;
import fabric.common.ConfigProperties;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.transaction.TransactionManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    
    public boolean handleUpdates();
    
    public double value(boolean useWeakCache);
    
    public long samples(boolean useWeakCache);
    
    public long computeSamples(boolean useWeakCache);
    
    public long lastUpdate(boolean useWeakCache);
    
    public long computeLastUpdate(boolean useWeakCache);
    
    public double updateInterval(boolean useWeakCache);
    
    public double computeUpdateInterval(boolean useWeakCache);
    
    public double velocity(boolean useWeakCache);
    
    public double noise(boolean useWeakCache);
    
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
        
        public Refresher fabric$metrics$DerivedMetric$Refresher$(
          fabric.metrics.Metric t);
        
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
            
            public fabric.metrics.DerivedMetric.Refresher
              fabric$metrics$DerivedMetric$Refresher$(
              fabric.metrics.Metric arg1) {
                return ((fabric.metrics.DerivedMetric.Refresher) fetch()).
                  fabric$metrics$DerivedMetric$Refresher$(arg1);
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
            
            public Refresher fabric$metrics$DerivedMetric$Refresher$(
              fabric.metrics.Metric t) {
                this.set$t(t);
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
                    ((fabric.metrics.Metric._Proxy) this.get$t()).
                      refreshWeakEstimates$remote(w, null);
                }
                else {
                    this.get$t().refreshWeakEstimates();
                }
                return null;
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
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.DerivedMetric.Refresher._Impl src =
                  (fabric.metrics.DerivedMetric.Refresher._Impl) other;
                this.t = src.t;
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
        
        public static final byte[] $classHash = new byte[] { 86, -103, -4, 51,
        13, 10, -66, -61, 53, -35, -68, -44, -64, 35, -100, 101, 101, 65, 22,
        127, -86, 58, -42, 10, -73, -30, 39, 63, 64, 70, 63, 68 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525439166000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public void refreshWeakEstimates();
    
    public void refreshWeakEstimates_remote(fabric.lang.security.Principal caller);
    
    /**
   * Utility to allow for running updates as close to the metric as possible.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
    public void refreshLocally();
    
    public void refreshLocally_remote(fabric.lang.security.Principal caller);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      equalityPolicy(double value, boolean useWeakCache,
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
        
        public boolean handleUpdates() {
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
        
        public boolean handleUpdates() {
            double newValue = computeValue(false);
            if (newValue != this.get$cachedValue()) {
                this.set$cachedValue((double) newValue);
                this.set$cachedVelocity((double) computeVelocity(false));
                this.set$cachedNoise((double) computeNoise(false));
                this.set$cachedSamples((long) computeSamples(false));
                this.set$cachedLastUpdate(
                       (long) java.lang.System.currentTimeMillis());
                this.set$cachedUpdateInterval((double)
                                                computeUpdateInterval(false));
                return true;
            }
            return false;
        }
        
        public double value(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_value((fabric.metrics.DerivedMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_value(fabric.metrics.DerivedMetric tmp,
                                           boolean useWeakCache) {
            if (useWeakCache || tmp.isObserved()) {
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
            return tmp.computeValue(false);
        }
        
        public long samples(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_samples((fabric.metrics.DerivedMetric) this.$getProxy(),
                             useWeakCache);
        }
        
        private static long static_samples(fabric.metrics.DerivedMetric tmp,
                                           boolean useWeakCache) {
            if (useWeakCache || tmp.isObserved()) {
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
            return tmp.computeSamples(false);
        }
        
        public long computeSamples(boolean useWeakCache) {
            long samples = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                samples = samples + term(i).samples(useWeakCache);
            }
            return samples;
        }
        
        public long lastUpdate(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_lastUpdate((fabric.metrics.DerivedMetric) this.$getProxy(),
                                useWeakCache);
        }
        
        private static long static_lastUpdate(fabric.metrics.DerivedMetric tmp,
                                              boolean useWeakCache) {
            if (useWeakCache || tmp.isObserved()) {
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
            return tmp.computeLastUpdate(false);
        }
        
        public long computeLastUpdate(boolean useWeakCache) {
            long lastUpdate = 0;
            for (int i = 0; i < this.get$terms().length(); i++) {
                lastUpdate =
                  java.lang.Math.max(lastUpdate,
                                     term(i).lastUpdate(useWeakCache));
            }
            return lastUpdate;
        }
        
        public double updateInterval(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_updateInterval((fabric.metrics.DerivedMetric)
                                      this.$getProxy(), useWeakCache);
        }
        
        private static double static_updateInterval(
          fabric.metrics.DerivedMetric tmp, boolean useWeakCache) {
            if (useWeakCache || tmp.isObserved()) {
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
            return tmp.computeUpdateInterval(false);
        }
        
        public double computeUpdateInterval(boolean useWeakCache) {
            double updateInterval = java.lang.Double.MAX_VALUE;
            for (int i = 0; i < this.get$terms().length(); i++) {
                double termInterval = term(i).updateInterval(useWeakCache);
                updateInterval = java.lang.Math.min(updateInterval,
                                                    termInterval);
            }
            return updateInterval;
        }
        
        public double velocity(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_velocity((fabric.metrics.DerivedMetric) this.$getProxy(),
                              useWeakCache);
        }
        
        private static double static_velocity(fabric.metrics.DerivedMetric tmp,
                                              boolean useWeakCache) {
            if (tmp.get$usePreset()) return tmp.get$presetV();
            if (useWeakCache) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return tmp.get$cachedVelocity();
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var68 = rtn;
                        fabric.worker.transaction.TransactionManager $tm74 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled77 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff75 = 1;
                        boolean $doBackoff76 = true;
                        boolean $retry71 = true;
                        $label69: for (boolean $commit70 = false; !$commit70;
                                       ) {
                            if ($backoffEnabled77) {
                                if ($doBackoff76) {
                                    if ($backoff75 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff75);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e72) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff75 < 5000) $backoff75 *= 2;
                                }
                                $doBackoff76 = $backoff75 <= 32 ||
                                                 !$doBackoff76;
                            }
                            $commit70 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedVelocity(); }
                            catch (final fabric.worker.RetryException $e72) {
                                $commit70 = false;
                                continue $label69;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e72) {
                                $commit70 = false;
                                fabric.common.TransactionID $currentTid73 =
                                  $tm74.getCurrentTid();
                                if ($e72.tid.isDescendantOf($currentTid73))
                                    continue $label69;
                                if ($currentTid73.parent != null) {
                                    $retry71 = false;
                                    throw $e72;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e72) {
                                $commit70 = false;
                                if ($tm74.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid73 =
                                  $tm74.getCurrentTid();
                                if ($e72.tid.isDescendantOf($currentTid73)) {
                                    $retry71 = true;
                                }
                                else if ($currentTid73.parent != null) {
                                    $retry71 = false;
                                    throw $e72;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e72) {
                                $commit70 = false;
                                if ($tm74.checkForStaleObjects())
                                    continue $label69;
                                $retry71 = false;
                                throw new fabric.worker.AbortException($e72);
                            }
                            finally {
                                if ($commit70) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e72) {
                                        $commit70 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e72) {
                                        $commit70 = false;
                                        fabric.common.TransactionID
                                          $currentTid73 = $tm74.getCurrentTid();
                                        if ($currentTid73 != null) {
                                            if ($e72.tid.equals(
                                                           $currentTid73) ||
                                                  !$e72.tid.isDescendantOf(
                                                              $currentTid73)) {
                                                throw $e72;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit70 && $retry71) {
                                    { rtn = rtn$var68; }
                                    continue $label69;
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return fabric.metrics.util.RunningMetricStats._Impl.
                  updatedVelocity(tmp.get$cachedVelocity(),
                                  tmp.get$cachedUpdateInterval(),
                                  tmp.get$cachedSamples(),
                                  tmp.get$cachedLastUpdate(),
                                  java.lang.System.currentTimeMillis());
            }
            return tmp.computeVelocity(false);
        }
        
        public double noise(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_noise((fabric.metrics.DerivedMetric) this.$getProxy(),
                           useWeakCache);
        }
        
        private static double static_noise(fabric.metrics.DerivedMetric tmp,
                                           boolean useWeakCache) {
            if (tmp.get$usePreset()) return tmp.get$presetN();
            if (useWeakCache) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return tmp.get$cachedNoise();
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var78 = rtn;
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
                            try { rtn = tmp.get$cachedNoise(); }
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
                                    { rtn = rtn$var78; }
                                    continue $label79;
                                }
                            }
                        }
                    }
                    return rtn;
                }
            }
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return fabric.metrics.util.RunningMetricStats._Impl.
                  updatedNoise(tmp.get$cachedVelocity(), tmp.get$cachedNoise(),
                               tmp.get$cachedUpdateInterval(),
                               tmp.get$cachedSamples(),
                               tmp.get$cachedLastUpdate(),
                               java.lang.System.currentTimeMillis());
            }
            return tmp.computeNoise(false);
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
                this.set$cachedValue((double) computeValue(false));
                this.set$cachedVelocity((double) computeVelocity(false));
                this.set$cachedNoise((double) computeNoise(false));
                this.set$cachedSamples((long) computeSamples(false));
                this.set$cachedLastUpdate(
                       (long) java.lang.System.currentTimeMillis());
                this.set$cachedUpdateInterval((double)
                                                computeUpdateInterval(false));
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
        
        public void refreshWeakEstimates() {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                {
                    fabric.worker.transaction.TransactionManager $tm93 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled96 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff94 = 1;
                    boolean $doBackoff95 = true;
                    boolean $retry90 = true;
                    $label88: for (boolean $commit89 = false; !$commit89; ) {
                        if ($backoffEnabled96) {
                            if ($doBackoff95) {
                                if ($backoff94 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff94);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e91) {
                                            
                                        }
                                    }
                                }
                                if ($backoff94 < 5000) $backoff94 *= 2;
                            }
                            $doBackoff95 = $backoff94 <= 32 || !$doBackoff95;
                        }
                        $commit89 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (isObserved()) return; }
                        catch (final fabric.worker.RetryException $e91) {
                            $commit89 = false;
                            continue $label88;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e91) {
                            $commit89 = false;
                            fabric.common.TransactionID $currentTid92 =
                              $tm93.getCurrentTid();
                            if ($e91.tid.isDescendantOf($currentTid92))
                                continue $label88;
                            if ($currentTid92.parent != null) {
                                $retry90 = false;
                                throw $e91;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e91) {
                            $commit89 = false;
                            if ($tm93.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid92 =
                              $tm93.getCurrentTid();
                            if ($e91.tid.isDescendantOf($currentTid92)) {
                                $retry90 = true;
                            }
                            else if ($currentTid92.parent != null) {
                                $retry90 = false;
                                throw $e91;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e91) {
                            $commit89 = false;
                            if ($tm93.checkForStaleObjects()) continue $label88;
                            $retry90 = false;
                            throw new fabric.worker.AbortException($e91);
                        }
                        finally {
                            if ($commit89) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e91) {
                                    $commit89 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e91) {
                                    $commit89 = false;
                                    fabric.common.TransactionID $currentTid92 =
                                      $tm93.getCurrentTid();
                                    if ($currentTid92 != null) {
                                        if ($e91.tid.equals($currentTid92) ||
                                              !$e91.tid.isDescendantOf(
                                                          $currentTid92)) {
                                            throw $e91;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit89 && $retry90) {
                                {  }
                                continue $label88;
                            }
                        }
                    }
                }
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[this.get$terms().length()];
                for (int i = 0; i < this.get$terms().length(); i++) {
                    final fabric.metrics.Metric t = term(i);
                    java.util.concurrent.Callable c = null;
                    {
                        java.util.concurrent.Callable c$var97 = c;
                        int i$var98 = i;
                        fabric.worker.transaction.TransactionManager $tm104 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled107 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff105 = 1;
                        boolean $doBackoff106 = true;
                        boolean $retry101 = true;
                        $label99: for (boolean $commit100 = false; !$commit100;
                                       ) {
                            if ($backoffEnabled107) {
                                if ($doBackoff106) {
                                    if ($backoff105 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff105);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e102) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff105 < 5000) $backoff105 *= 2;
                                }
                                $doBackoff106 = $backoff105 <= 32 ||
                                                  !$doBackoff106;
                            }
                            $commit100 = true;
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
                                            t));
                            }
                            catch (final fabric.worker.RetryException $e102) {
                                $commit100 = false;
                                continue $label99;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e102) {
                                $commit100 = false;
                                fabric.common.TransactionID $currentTid103 =
                                  $tm104.getCurrentTid();
                                if ($e102.tid.isDescendantOf($currentTid103))
                                    continue $label99;
                                if ($currentTid103.parent != null) {
                                    $retry101 = false;
                                    throw $e102;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e102) {
                                $commit100 = false;
                                if ($tm104.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid103 =
                                  $tm104.getCurrentTid();
                                if ($e102.tid.isDescendantOf($currentTid103)) {
                                    $retry101 = true;
                                }
                                else if ($currentTid103.parent != null) {
                                    $retry101 = false;
                                    throw $e102;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e102) {
                                $commit100 = false;
                                if ($tm104.checkForStaleObjects())
                                    continue $label99;
                                $retry101 = false;
                                throw new fabric.worker.AbortException($e102);
                            }
                            finally {
                                if ($commit100) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e102) {
                                        $commit100 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e102) {
                                        $commit100 = false;
                                        fabric.common.TransactionID
                                          $currentTid103 =
                                          $tm104.getCurrentTid();
                                        if ($currentTid103 != null) {
                                            if ($e102.tid.equals(
                                                            $currentTid103) ||
                                                  !$e102.tid.
                                                  isDescendantOf(
                                                    $currentTid103)) {
                                                throw $e102;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit100 && $retry101) {
                                    {
                                        c = c$var97;
                                        i = i$var98;
                                    }
                                    continue $label99;
                                }
                            }
                        }
                    }
                    futures[i] =
                      fabric.metrics.DerivedMetric._Static._Proxy.$instance.
                        get$service().submit(c);
                }
                for (int i = 0; i < this.get$terms().length(); i++) {
                    try { futures[i].get(); }
                    catch (java.util.concurrent.ExecutionException e) {  }
                    catch (java.lang.InterruptedException e) {  }
                }
            }
            else {
                if (isObserved()) return;
                for (int i = 0; i < this.get$terms().length(); i++) {
                    term(i).refreshWeakEstimates();
                }
            }
            refreshLocally();
        }
        
        public void refreshWeakEstimates_remote(
          fabric.lang.security.Principal caller) {
            refreshWeakEstimates();
        }
        
        /**
   * Utility to allow for running updates as close to the metric as possible.
   *
   * Ugh, public because we don't allow remote calls for non public methods.
   */
        public void refreshLocally() {
            fabric.metrics.DerivedMetric._Impl.static_refreshLocally(
                                                 (fabric.metrics.DerivedMetric)
                                                   this.$getProxy());
        }
        
        private static void static_refreshLocally(
          fabric.metrics.DerivedMetric tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.isObserved()) {
                    tmp.set$cachedValue((double) tmp.computeValue(true));
                    tmp.set$cachedVelocity((double) tmp.computeVelocity(true));
                    tmp.set$cachedNoise((double) tmp.computeNoise(true));
                    tmp.set$cachedSamples((long) tmp.computeSamples(true));
                    tmp.set$cachedLastUpdate(
                          (long) java.lang.System.currentTimeMillis());
                    tmp.set$cachedUpdateInterval(
                          (double) tmp.computeUpdateInterval(true));
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm113 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled116 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff114 = 1;
                    boolean $doBackoff115 = true;
                    boolean $retry110 = true;
                    $label108: for (boolean $commit109 = false; !$commit109; ) {
                        if ($backoffEnabled116) {
                            if ($doBackoff115) {
                                if ($backoff114 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff114);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e111) {
                                            
                                        }
                                    }
                                }
                                if ($backoff114 < 5000) $backoff114 *= 2;
                            }
                            $doBackoff115 = $backoff114 <= 32 || !$doBackoff115;
                        }
                        $commit109 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.isObserved()) {
                                tmp.set$cachedValue((double)
                                                      tmp.computeValue(true));
                                tmp.set$cachedVelocity(
                                      (double) tmp.computeVelocity(true));
                                tmp.set$cachedNoise((double)
                                                      tmp.computeNoise(true));
                                tmp.set$cachedSamples(
                                      (long) tmp.computeSamples(true));
                                tmp.set$cachedLastUpdate(
                                      (long)
                                        java.lang.System.currentTimeMillis());
                                tmp.set$cachedUpdateInterval(
                                      (double) tmp.computeUpdateInterval(true));
                            }
                        }
                        catch (final fabric.worker.RetryException $e111) {
                            $commit109 = false;
                            continue $label108;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e111) {
                            $commit109 = false;
                            fabric.common.TransactionID $currentTid112 =
                              $tm113.getCurrentTid();
                            if ($e111.tid.isDescendantOf($currentTid112))
                                continue $label108;
                            if ($currentTid112.parent != null) {
                                $retry110 = false;
                                throw $e111;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e111) {
                            $commit109 = false;
                            if ($tm113.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid112 =
                              $tm113.getCurrentTid();
                            if ($e111.tid.isDescendantOf($currentTid112)) {
                                $retry110 = true;
                            }
                            else if ($currentTid112.parent != null) {
                                $retry110 = false;
                                throw $e111;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e111) {
                            $commit109 = false;
                            if ($tm113.checkForStaleObjects())
                                continue $label108;
                            $retry110 = false;
                            throw new fabric.worker.AbortException($e111);
                        }
                        finally {
                            if ($commit109) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e111) {
                                    $commit109 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e111) {
                                    $commit109 = false;
                                    fabric.common.TransactionID $currentTid112 =
                                      $tm113.getCurrentTid();
                                    if ($currentTid112 != null) {
                                        if ($e111.tid.equals($currentTid112) ||
                                              !$e111.tid.isDescendantOf(
                                                           $currentTid112)) {
                                            throw $e111;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit109 && $retry110) {
                                {  }
                                continue $label108;
                            }
                        }
                    }
                }
            }
        }
        
        public void refreshLocally_remote(
          fabric.lang.security.Principal caller) {
            refreshLocally();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          equalityPolicy(double value, boolean useWeakCache,
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
                        fabric.worker.transaction.TransactionManager $tm122 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled125 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff123 = 1;
                        boolean $doBackoff124 = true;
                        boolean $retry119 = true;
                        $label117: for (boolean $commit118 = false; !$commit118;
                                        ) {
                            if ($backoffEnabled125) {
                                if ($doBackoff124) {
                                    if ($backoff123 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff123);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e120) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff123 < 5000) $backoff123 *= 2;
                                }
                                $doBackoff124 = $backoff123 <= 32 ||
                                                  !$doBackoff124;
                            }
                            $commit118 = true;
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
                            catch (final fabric.worker.RetryException $e120) {
                                $commit118 = false;
                                continue $label117;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e120) {
                                $commit118 = false;
                                fabric.common.TransactionID $currentTid121 =
                                  $tm122.getCurrentTid();
                                if ($e120.tid.isDescendantOf($currentTid121))
                                    continue $label117;
                                if ($currentTid121.parent != null) {
                                    $retry119 = false;
                                    throw $e120;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e120) {
                                $commit118 = false;
                                if ($tm122.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid121 =
                                  $tm122.getCurrentTid();
                                if ($e120.tid.isDescendantOf($currentTid121)) {
                                    $retry119 = true;
                                }
                                else if ($currentTid121.parent != null) {
                                    $retry119 = false;
                                    throw $e120;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e120) {
                                $commit118 = false;
                                if ($tm122.checkForStaleObjects())
                                    continue $label117;
                                $retry119 = false;
                                throw new fabric.worker.AbortException($e120);
                            }
                            finally {
                                if ($commit118) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e120) {
                                        $commit118 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e120) {
                                        $commit118 = false;
                                        fabric.common.TransactionID
                                          $currentTid121 =
                                          $tm122.getCurrentTid();
                                        if ($currentTid121 != null) {
                                            if ($e120.tid.equals(
                                                            $currentTid121) ||
                                                  !$e120.tid.
                                                  isDescendantOf(
                                                    $currentTid121)) {
                                                throw $e120;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit118 && $retry119) {
                                    {  }
                                    continue $label117;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -19, -29, 104, -42,
    -34, 105, 21, 27, 13, -124, -107, 1, -63, -85, -98, 46, -4, -30, 22, -24,
    18, 73, -87, 108, -118, -123, -29, 18, -95, 38, -119, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525439166000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
