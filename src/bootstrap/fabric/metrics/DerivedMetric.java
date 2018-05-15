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
        public static final long jlc$SourceLastModified$fabil = 1526344812000L;
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
                        double rtn$var0 = rtn;
                        fabric.worker.transaction.TransactionManager $tm6 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled9 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff7 = 1;
                        boolean $doBackoff8 = true;
                        boolean $retry3 = true;
                        $label1: for (boolean $commit2 = false; !$commit2; ) {
                            if ($backoffEnabled9) {
                                if ($doBackoff8) {
                                    if ($backoff7 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff7);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e4) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff7 < 5000) $backoff7 *= 2;
                                }
                                $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                            }
                            $commit2 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedValue(); }
                            catch (final fabric.worker.RetryException $e4) {
                                $commit2 = false;
                                continue $label1;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e4) {
                                $commit2 = false;
                                fabric.common.TransactionID $currentTid5 =
                                  $tm6.getCurrentTid();
                                if ($e4.tid.isDescendantOf($currentTid5))
                                    continue $label1;
                                if ($currentTid5.parent != null) {
                                    $retry3 = false;
                                    throw $e4;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e4) {
                                $commit2 = false;
                                if ($tm6.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid5 =
                                  $tm6.getCurrentTid();
                                if ($e4.tid.isDescendantOf($currentTid5)) {
                                    $retry3 = true;
                                }
                                else if ($currentTid5.parent != null) {
                                    $retry3 = false;
                                    throw $e4;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e4) {
                                $commit2 = false;
                                if ($tm6.checkForStaleObjects())
                                    continue $label1;
                                $retry3 = false;
                                throw new fabric.worker.AbortException($e4);
                            }
                            finally {
                                if ($commit2) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e4) {
                                        $commit2 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e4) {
                                        $commit2 = false;
                                        fabric.common.TransactionID
                                          $currentTid5 = $tm6.getCurrentTid();
                                        if ($currentTid5 != null) {
                                            if ($e4.tid.equals($currentTid5) ||
                                                  !$e4.tid.isDescendantOf(
                                                             $currentTid5)) {
                                                throw $e4;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit2 && $retry3) {
                                    { rtn = rtn$var0; }
                                    continue $label1;
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
                        long rtn$var10 = rtn;
                        fabric.worker.transaction.TransactionManager $tm16 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled19 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff17 = 1;
                        boolean $doBackoff18 = true;
                        boolean $retry13 = true;
                        $label11: for (boolean $commit12 = false; !$commit12;
                                       ) {
                            if ($backoffEnabled19) {
                                if ($doBackoff18) {
                                    if ($backoff17 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff17);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e14) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff17 < 5000) $backoff17 *= 2;
                                }
                                $doBackoff18 = $backoff17 <= 32 ||
                                                 !$doBackoff18;
                            }
                            $commit12 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedSamples(); }
                            catch (final fabric.worker.RetryException $e14) {
                                $commit12 = false;
                                continue $label11;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e14) {
                                $commit12 = false;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid.isDescendantOf($currentTid15))
                                    continue $label11;
                                if ($currentTid15.parent != null) {
                                    $retry13 = false;
                                    throw $e14;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e14) {
                                $commit12 = false;
                                if ($tm16.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid.isDescendantOf($currentTid15)) {
                                    $retry13 = true;
                                }
                                else if ($currentTid15.parent != null) {
                                    $retry13 = false;
                                    throw $e14;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e14) {
                                $commit12 = false;
                                if ($tm16.checkForStaleObjects())
                                    continue $label11;
                                $retry13 = false;
                                throw new fabric.worker.AbortException($e14);
                            }
                            finally {
                                if ($commit12) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e14) {
                                        $commit12 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e14) {
                                        $commit12 = false;
                                        fabric.common.TransactionID
                                          $currentTid15 = $tm16.getCurrentTid();
                                        if ($currentTid15 != null) {
                                            if ($e14.tid.equals(
                                                           $currentTid15) ||
                                                  !$e14.tid.isDescendantOf(
                                                              $currentTid15)) {
                                                throw $e14;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit12 && $retry13) {
                                    { rtn = rtn$var10; }
                                    continue $label11;
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
                        long rtn$var20 = rtn;
                        fabric.worker.transaction.TransactionManager $tm26 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled29 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff27 = 1;
                        boolean $doBackoff28 = true;
                        boolean $retry23 = true;
                        $label21: for (boolean $commit22 = false; !$commit22;
                                       ) {
                            if ($backoffEnabled29) {
                                if ($doBackoff28) {
                                    if ($backoff27 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff27);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e24) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff27 < 5000) $backoff27 *= 2;
                                }
                                $doBackoff28 = $backoff27 <= 32 ||
                                                 !$doBackoff28;
                            }
                            $commit22 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedLastUpdate(); }
                            catch (final fabric.worker.RetryException $e24) {
                                $commit22 = false;
                                continue $label21;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit22 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25))
                                    continue $label21;
                                if ($currentTid25.parent != null) {
                                    $retry23 = false;
                                    throw $e24;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e24) {
                                $commit22 = false;
                                if ($tm26.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25)) {
                                    $retry23 = true;
                                }
                                else if ($currentTid25.parent != null) {
                                    $retry23 = false;
                                    throw $e24;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e24) {
                                $commit22 = false;
                                if ($tm26.checkForStaleObjects())
                                    continue $label21;
                                $retry23 = false;
                                throw new fabric.worker.AbortException($e24);
                            }
                            finally {
                                if ($commit22) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e24) {
                                        $commit22 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e24) {
                                        $commit22 = false;
                                        fabric.common.TransactionID
                                          $currentTid25 = $tm26.getCurrentTid();
                                        if ($currentTid25 != null) {
                                            if ($e24.tid.equals(
                                                           $currentTid25) ||
                                                  !$e24.tid.isDescendantOf(
                                                              $currentTid25)) {
                                                throw $e24;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit22 && $retry23) {
                                    { rtn = rtn$var20; }
                                    continue $label21;
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
                        double rtn$var30 = rtn;
                        fabric.worker.transaction.TransactionManager $tm36 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled39 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff37 = 1;
                        boolean $doBackoff38 = true;
                        boolean $retry33 = true;
                        $label31: for (boolean $commit32 = false; !$commit32;
                                       ) {
                            if ($backoffEnabled39) {
                                if ($doBackoff38) {
                                    if ($backoff37 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff37);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e34) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff37 < 5000) $backoff37 *= 2;
                                }
                                $doBackoff38 = $backoff37 <= 32 ||
                                                 !$doBackoff38;
                            }
                            $commit32 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedUpdateInterval(); }
                            catch (final fabric.worker.RetryException $e34) {
                                $commit32 = false;
                                continue $label31;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e34) {
                                $commit32 = false;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35))
                                    continue $label31;
                                if ($currentTid35.parent != null) {
                                    $retry33 = false;
                                    throw $e34;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e34) {
                                $commit32 = false;
                                if ($tm36.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35)) {
                                    $retry33 = true;
                                }
                                else if ($currentTid35.parent != null) {
                                    $retry33 = false;
                                    throw $e34;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e34) {
                                $commit32 = false;
                                if ($tm36.checkForStaleObjects())
                                    continue $label31;
                                $retry33 = false;
                                throw new fabric.worker.AbortException($e34);
                            }
                            finally {
                                if ($commit32) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e34) {
                                        $commit32 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e34) {
                                        $commit32 = false;
                                        fabric.common.TransactionID
                                          $currentTid35 = $tm36.getCurrentTid();
                                        if ($currentTid35 != null) {
                                            if ($e34.tid.equals(
                                                           $currentTid35) ||
                                                  !$e34.tid.isDescendantOf(
                                                              $currentTid35)) {
                                                throw $e34;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit32 && $retry33) {
                                    { rtn = rtn$var30; }
                                    continue $label31;
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
                        double rtn$var40 = rtn;
                        fabric.worker.transaction.TransactionManager $tm46 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled49 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff47 = 1;
                        boolean $doBackoff48 = true;
                        boolean $retry43 = true;
                        $label41: for (boolean $commit42 = false; !$commit42;
                                       ) {
                            if ($backoffEnabled49) {
                                if ($doBackoff48) {
                                    if ($backoff47 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff47);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e44) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff47 < 5000) $backoff47 *= 2;
                                }
                                $doBackoff48 = $backoff47 <= 32 ||
                                                 !$doBackoff48;
                            }
                            $commit42 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedVelocity(); }
                            catch (final fabric.worker.RetryException $e44) {
                                $commit42 = false;
                                continue $label41;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e44) {
                                $commit42 = false;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($e44.tid.isDescendantOf($currentTid45))
                                    continue $label41;
                                if ($currentTid45.parent != null) {
                                    $retry43 = false;
                                    throw $e44;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e44) {
                                $commit42 = false;
                                if ($tm46.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($e44.tid.isDescendantOf($currentTid45)) {
                                    $retry43 = true;
                                }
                                else if ($currentTid45.parent != null) {
                                    $retry43 = false;
                                    throw $e44;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e44) {
                                $commit42 = false;
                                if ($tm46.checkForStaleObjects())
                                    continue $label41;
                                $retry43 = false;
                                throw new fabric.worker.AbortException($e44);
                            }
                            finally {
                                if ($commit42) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e44) {
                                        $commit42 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e44) {
                                        $commit42 = false;
                                        fabric.common.TransactionID
                                          $currentTid45 = $tm46.getCurrentTid();
                                        if ($currentTid45 != null) {
                                            if ($e44.tid.equals(
                                                           $currentTid45) ||
                                                  !$e44.tid.isDescendantOf(
                                                              $currentTid45)) {
                                                throw $e44;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit42 && $retry43) {
                                    { rtn = rtn$var40; }
                                    continue $label41;
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
                        double rtn$var50 = rtn;
                        fabric.worker.transaction.TransactionManager $tm56 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled59 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff57 = 1;
                        boolean $doBackoff58 = true;
                        boolean $retry53 = true;
                        $label51: for (boolean $commit52 = false; !$commit52;
                                       ) {
                            if ($backoffEnabled59) {
                                if ($doBackoff58) {
                                    if ($backoff57 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff57);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e54) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff57 < 5000) $backoff57 *= 2;
                                }
                                $doBackoff58 = $backoff57 <= 32 ||
                                                 !$doBackoff58;
                            }
                            $commit52 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { rtn = tmp.get$cachedNoise(); }
                            catch (final fabric.worker.RetryException $e54) {
                                $commit52 = false;
                                continue $label51;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e54) {
                                $commit52 = false;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid.isDescendantOf($currentTid55))
                                    continue $label51;
                                if ($currentTid55.parent != null) {
                                    $retry53 = false;
                                    throw $e54;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e54) {
                                $commit52 = false;
                                if ($tm56.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid.isDescendantOf($currentTid55)) {
                                    $retry53 = true;
                                }
                                else if ($currentTid55.parent != null) {
                                    $retry53 = false;
                                    throw $e54;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e54) {
                                $commit52 = false;
                                if ($tm56.checkForStaleObjects())
                                    continue $label51;
                                $retry53 = false;
                                throw new fabric.worker.AbortException($e54);
                            }
                            finally {
                                if ($commit52) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e54) {
                                        $commit52 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e54) {
                                        $commit52 = false;
                                        fabric.common.TransactionID
                                          $currentTid55 = $tm56.getCurrentTid();
                                        if ($currentTid55 != null) {
                                            if ($e54.tid.equals(
                                                           $currentTid55) ||
                                                  !$e54.tid.isDescendantOf(
                                                              $currentTid55)) {
                                                throw $e54;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit52 && $retry53) {
                                    { rtn = rtn$var50; }
                                    continue $label51;
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
                    fabric.worker.transaction.TransactionManager $tm65 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled68 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff66 = 1;
                    boolean $doBackoff67 = true;
                    boolean $retry62 = true;
                    $label60: for (boolean $commit61 = false; !$commit61; ) {
                        if ($backoffEnabled68) {
                            if ($doBackoff67) {
                                if ($backoff66 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff66);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e63) {
                                            
                                        }
                                    }
                                }
                                if ($backoff66 < 5000) $backoff66 *= 2;
                            }
                            $doBackoff67 = $backoff66 <= 32 || !$doBackoff67;
                        }
                        $commit61 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (isObserved()) return; }
                        catch (final fabric.worker.RetryException $e63) {
                            $commit61 = false;
                            continue $label60;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e63) {
                            $commit61 = false;
                            fabric.common.TransactionID $currentTid64 =
                              $tm65.getCurrentTid();
                            if ($e63.tid.isDescendantOf($currentTid64))
                                continue $label60;
                            if ($currentTid64.parent != null) {
                                $retry62 = false;
                                throw $e63;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e63) {
                            $commit61 = false;
                            if ($tm65.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid64 =
                              $tm65.getCurrentTid();
                            if ($e63.tid.isDescendantOf($currentTid64)) {
                                $retry62 = true;
                            }
                            else if ($currentTid64.parent != null) {
                                $retry62 = false;
                                throw $e63;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e63) {
                            $commit61 = false;
                            if ($tm65.checkForStaleObjects()) continue $label60;
                            $retry62 = false;
                            throw new fabric.worker.AbortException($e63);
                        }
                        finally {
                            if ($commit61) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e63) {
                                    $commit61 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e63) {
                                    $commit61 = false;
                                    fabric.common.TransactionID $currentTid64 =
                                      $tm65.getCurrentTid();
                                    if ($currentTid64 != null) {
                                        if ($e63.tid.equals($currentTid64) ||
                                              !$e63.tid.isDescendantOf(
                                                          $currentTid64)) {
                                            throw $e63;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit61 && $retry62) {
                                {  }
                                continue $label60;
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
                        java.util.concurrent.Callable c$var69 = c;
                        int i$var70 = i;
                        fabric.worker.transaction.TransactionManager $tm76 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled79 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff77 = 1;
                        boolean $doBackoff78 = true;
                        boolean $retry73 = true;
                        $label71: for (boolean $commit72 = false; !$commit72;
                                       ) {
                            if ($backoffEnabled79) {
                                if ($doBackoff78) {
                                    if ($backoff77 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff77);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e74) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff77 < 5000) $backoff77 *= 2;
                                }
                                $doBackoff78 = $backoff77 <= 32 ||
                                                 !$doBackoff78;
                            }
                            $commit72 = true;
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
                            catch (final fabric.worker.RetryException $e74) {
                                $commit72 = false;
                                continue $label71;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e74) {
                                $commit72 = false;
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($e74.tid.isDescendantOf($currentTid75))
                                    continue $label71;
                                if ($currentTid75.parent != null) {
                                    $retry73 = false;
                                    throw $e74;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e74) {
                                $commit72 = false;
                                if ($tm76.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($e74.tid.isDescendantOf($currentTid75)) {
                                    $retry73 = true;
                                }
                                else if ($currentTid75.parent != null) {
                                    $retry73 = false;
                                    throw $e74;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e74) {
                                $commit72 = false;
                                if ($tm76.checkForStaleObjects())
                                    continue $label71;
                                $retry73 = false;
                                throw new fabric.worker.AbortException($e74);
                            }
                            finally {
                                if ($commit72) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e74) {
                                        $commit72 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e74) {
                                        $commit72 = false;
                                        fabric.common.TransactionID
                                          $currentTid75 = $tm76.getCurrentTid();
                                        if ($currentTid75 != null) {
                                            if ($e74.tid.equals(
                                                           $currentTid75) ||
                                                  !$e74.tid.isDescendantOf(
                                                              $currentTid75)) {
                                                throw $e74;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit72 && $retry73) {
                                    {
                                        c = c$var69;
                                        i = i$var70;
                                    }
                                    continue $label71;
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
                    fabric.worker.transaction.TransactionManager $tm85 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled88 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff86 = 1;
                    boolean $doBackoff87 = true;
                    boolean $retry82 = true;
                    $label80: for (boolean $commit81 = false; !$commit81; ) {
                        if ($backoffEnabled88) {
                            if ($doBackoff87) {
                                if ($backoff86 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff86);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e83) {
                                            
                                        }
                                    }
                                }
                                if ($backoff86 < 5000) $backoff86 *= 2;
                            }
                            $doBackoff87 = $backoff86 <= 32 || !$doBackoff87;
                        }
                        $commit81 = true;
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
                        catch (final fabric.worker.RetryException $e83) {
                            $commit81 = false;
                            continue $label80;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e83) {
                            $commit81 = false;
                            fabric.common.TransactionID $currentTid84 =
                              $tm85.getCurrentTid();
                            if ($e83.tid.isDescendantOf($currentTid84))
                                continue $label80;
                            if ($currentTid84.parent != null) {
                                $retry82 = false;
                                throw $e83;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e83) {
                            $commit81 = false;
                            if ($tm85.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid84 =
                              $tm85.getCurrentTid();
                            if ($e83.tid.isDescendantOf($currentTid84)) {
                                $retry82 = true;
                            }
                            else if ($currentTid84.parent != null) {
                                $retry82 = false;
                                throw $e83;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e83) {
                            $commit81 = false;
                            if ($tm85.checkForStaleObjects()) continue $label80;
                            $retry82 = false;
                            throw new fabric.worker.AbortException($e83);
                        }
                        finally {
                            if ($commit81) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e83) {
                                    $commit81 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e83) {
                                    $commit81 = false;
                                    fabric.common.TransactionID $currentTid84 =
                                      $tm85.getCurrentTid();
                                    if ($currentTid84 != null) {
                                        if ($e83.tid.equals($currentTid84) ||
                                              !$e83.tid.isDescendantOf(
                                                          $currentTid84)) {
                                            throw $e83;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit81 && $retry82) {
                                {  }
                                continue $label80;
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
                        fabric.worker.transaction.TransactionManager $tm94 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled97 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff95 = 1;
                        boolean $doBackoff96 = true;
                        boolean $retry91 = true;
                        $label89: for (boolean $commit90 = false; !$commit90;
                                       ) {
                            if ($backoffEnabled97) {
                                if ($doBackoff96) {
                                    if ($backoff95 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff95);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e92) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff95 < 5000) $backoff95 *= 2;
                                }
                                $doBackoff96 = $backoff95 <= 32 ||
                                                 !$doBackoff96;
                            }
                            $commit90 = true;
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
                            catch (final fabric.worker.RetryException $e92) {
                                $commit90 = false;
                                continue $label89;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e92) {
                                $commit90 = false;
                                fabric.common.TransactionID $currentTid93 =
                                  $tm94.getCurrentTid();
                                if ($e92.tid.isDescendantOf($currentTid93))
                                    continue $label89;
                                if ($currentTid93.parent != null) {
                                    $retry91 = false;
                                    throw $e92;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e92) {
                                $commit90 = false;
                                if ($tm94.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid93 =
                                  $tm94.getCurrentTid();
                                if ($e92.tid.isDescendantOf($currentTid93)) {
                                    $retry91 = true;
                                }
                                else if ($currentTid93.parent != null) {
                                    $retry91 = false;
                                    throw $e92;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e92) {
                                $commit90 = false;
                                if ($tm94.checkForStaleObjects())
                                    continue $label89;
                                $retry91 = false;
                                throw new fabric.worker.AbortException($e92);
                            }
                            finally {
                                if ($commit90) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e92) {
                                        $commit90 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e92) {
                                        $commit90 = false;
                                        fabric.common.TransactionID
                                          $currentTid93 = $tm94.getCurrentTid();
                                        if ($currentTid93 != null) {
                                            if ($e92.tid.equals(
                                                           $currentTid93) ||
                                                  !$e92.tid.isDescendantOf(
                                                              $currentTid93)) {
                                                throw $e92;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit90 && $retry91) {
                                    {  }
                                    continue $label89;
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
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
