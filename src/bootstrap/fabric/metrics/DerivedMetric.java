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
    public fabric.lang.arrays.ObjectArray get$terms();
    
    public fabric.lang.arrays.ObjectArray set$terms(
      fabric.lang.arrays.ObjectArray val);
    
    public fabric.lang.arrays.ObjectArray get$leafMetrics();
    
    public fabric.lang.arrays.ObjectArray set$leafMetrics(
      fabric.lang.arrays.ObjectArray val);
    
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
   * @return the terms this {@link DerivedMetric} is defined over
   */
    public fabric.lang.arrays.ObjectArray terms();
    
    /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
    public fabric.metrics.Metric term(int i);
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.DerivedMetric.Refresher._Static.
                             _Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 53, -116, -34,
        -111, -125, 126, 110, -126, 48, 82, -127, 121, 98, -98, 6, 61, 77, 14,
        -70, 60, 68, 51, -127, -63, 101, 126, 67, 79, 96, -81, 21, 38 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524081841000L;
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
        public fabric.lang.arrays.ObjectArray get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public fabric.lang.arrays.ObjectArray get$leafMetrics() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.lang.arrays.ObjectArray set$leafMetrics(
          fabric.lang.arrays.ObjectArray val) {
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
        
        public fabric.lang.arrays.ObjectArray terms() {
            return ((fabric.metrics.DerivedMetric) fetch()).terms();
        }
        
        public fabric.metrics.Metric term(int arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).term(arg1);
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.DerivedMetric) fetch()).getLeafSubjects();
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.DerivedMetric {
        public fabric.lang.arrays.ObjectArray get$terms() { return this.terms; }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.terms = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.ObjectArray terms;
        
        public fabric.lang.arrays.ObjectArray get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.lang.arrays.ObjectArray set$leafMetrics(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.ObjectArray leafMetrics;
        
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
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            fabric.worker.Store s = $getStore();
            this.set$terms(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       s).fabric$lang$arrays$ObjectArray$(
                            lbl, lbl.confPolicy(),
                            fabric.metrics.Metric._Proxy.class,
                            terms.length).$getProxy());
            for (int i = 0; i < terms.length; i++)
                this.get$terms().set(i, terms[i]);
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
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.Metric)
                              this.get$terms().
                              get(
                                i))) instanceof fabric.metrics.SampledMetric) {
                    leafMetricsTmp.
                      add(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.Metric)
                                    this.get$terms().get(i)));
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                    fabric.metrics.DerivedMetric termI =
                      (fabric.metrics.DerivedMetric)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    (fabric.metrics.Metric)
                                                      this.get$terms().get(i));
                    fabric.lang.arrays.ObjectArray termILeaves =
                      termI.getLeafSubjects();
                    for (int j = 0; j < termILeaves.get$length(); j++)
                        leafMetricsTmp.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.SampledMetric)
                                        termILeaves.get(j)));
                }
                else {
                    throw new java.lang.IllegalStateException(
                            "This shouldn\'t happen, all metrics should either be a SampledMetric or a DerivedMetric!");
                }
            }
            this.set$leafMetrics(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore(
                              )).fabric$lang$arrays$ObjectArray$(
                                   this.get$$updateLabel(),
                                   this.get$$updateLabel().confPolicy(),
                                   fabric.metrics.SampledMetric._Proxy.class,
                                   leafMetricsTmp.size()).$getProxy());
            int lmIdx = 0;
            for (java.util.Iterator it = leafMetricsTmp.iterator();
                 it.hasNext(); ) {
                this.
                  get$leafMetrics().
                  set(
                    lmIdx++,
                    (fabric.metrics.SampledMetric)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        fabric.lang.WrappedJavaInlineable.$wrap(it.next())));
            }
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
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                samples = samples +
                            ((fabric.metrics.Metric)
                               this.get$terms().get(i)).samples(useWeakCache);
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
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                lastUpdate =
                  java.lang.Math.max(
                                   lastUpdate,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).lastUpdate(
                                                                 useWeakCache));
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
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                double termInterval =
                  ((fabric.metrics.Metric) this.get$terms().get(i)).
                  updateInterval(useWeakCache);
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      addObserver((fabric.metrics.DerivedMetric)
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      removeObserver((fabric.metrics.DerivedMetric)
                                       this.$getProxy());
                }
            }
        }
        
        /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
        public fabric.lang.arrays.ObjectArray terms() {
            final fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            fabric.lang.arrays.ObjectArray copy =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  local).fabric$lang$arrays$ObjectArray$(
                           this.get$$updateLabel(),
                           this.get$$updateLabel().confPolicy(),
                           fabric.metrics.Metric._Proxy.class,
                           this.get$terms().get$length()).$getProxy();
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, copy, 0,
                                               this.get$terms().get$length());
            return copy;
        }
        
        /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
        public fabric.metrics.Metric term(int i) {
            return (fabric.metrics.Metric) this.get$terms().get(i);
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
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
                  new java.util.concurrent.Future[this.get$terms().get$length(
                                                                     )];
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    final fabric.metrics.Metric t = (fabric.metrics.Metric)
                                                      this.get$terms().get(i);
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    try { futures[i].get(); }
                    catch (java.util.concurrent.ExecutionException e) {  }
                    catch (java.lang.InterruptedException e) {  }
                }
            }
            else {
                if (isObserved()) return;
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    ((fabric.metrics.Metric) this.get$terms().get(i)).
                      refreshWeakEstimates();
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
                  new fabric.metrics.contracts.Contract[this.get$terms(
                                                               ).get$length()];
                for (int i = 0; i < this.get$terms().get$length(); i++) {
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
            $writeRef($getStore(), this.terms, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.leafMetrics, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.singleStore);
            out.writeBoolean(this.usePreset);
            out.writeDouble(this.presetR);
            out.writeDouble(this.presetB);
            out.writeDouble(this.presetV);
            out.writeDouble(this.presetN);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.terms = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.leafMetrics =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
    
    public static final byte[] $classHash = new byte[] { -46, -69, 1, -90, 69,
    44, -36, 14, -43, -22, 18, 64, 60, 19, -107, -40, -53, 47, -27, 85, -52,
    127, -109, 112, -110, -56, -104, 101, 45, -102, 18, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
