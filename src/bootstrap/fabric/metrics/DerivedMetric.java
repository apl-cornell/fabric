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
        public static final long jlc$SourceLastModified$fabil = 1524080058000L;
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
                  new java.util.concurrent.Future[this.get$terms().get$length(
                                                                     )];
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    final fabric.metrics.Metric t = (fabric.metrics.Metric)
                                                      this.get$terms().get(i);
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
    
    public static final byte[] $classHash = new byte[] { -46, -69, 1, -90, 69,
    44, -36, 14, -43, -22, 18, 64, 60, 19, -107, -40, -53, 47, -27, 85, -52,
    127, -109, 112, -110, -56, -104, 101, 45, -102, 18, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524080058000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0bCZAU1fX3sCy7sOzBIdeyHK4QrpngiawXuy66MMDKAspiWHt6/uw29HQP3X+W2RjEsxArRRJFlBippMCLEDwiksTgVYmAGgNG45GoaIIXWsTyCKZE897vP3dP77S1W9Xv9fT/7/93//e7/+76hPS3TDIxIodUzc96YtTyz5VDLcFW2bRouEmTLWsJPO1QBpW0bPngvnCdj/iCpEKRdUNXFVnr0C1GKoOr5G45oFMWWLq4pWEFKVeQ8FLZ6mLEt6IxYZLxMUPr6dQMJibJG//2aYHNd6ysfqQfqWonVarexmSmKk2GzmiCtZOKKI2GqGnNCYdpuJ3U6JSG26ipypr6Q+ho6O1kiKV26jKLm9RaTC1D68aOQ6x4jJp8zuRDZN8Ats24wgwT2K+22Y8zVQsEVYs1BElpRKVa2FpDriElQdI/osmd0PGUYFKKAB8xMBefQ/eBKrBpRmSFJklKVqt6mJFxuRQpievnQwcgHRClrMtITVWiy/CADLFZ0mS9M9DGTFXvhK79jTjMwsjogoNCp7KYrKyWO2kHIyNz+7XaTdCrnKsFSRgZntuNjwQ2G51jswxrfbLwvE1X65fqPiIBz2GqaMh/GRDV5RAtphFqUl2hNmHF1OAW+ZR9N/sIgc7Dczrbffb+6NOLptc9dcDuM8ahz6LQKqqwDmVHqPJwbdOUc/shG2Uxw1LRFbIk51ZtFS0NiRh4+ympEbHRn2x8avGzy6/dSY/5yMAWUqoYWjwKXlWjGNGYqlHzEqpTU2Y03ELKqR5u4u0tZADcB1Wd2k8XRSIWZS2kROOPSg3+G1QUgSFQRQPgXtUjRvI+JrMufp+IEUKq4SISIb7zCJn3S7ifQUhpHSPzA11GlAZCWpyuBfcOwEVlU+kKQNyaqhKwTCVgxnWmQifxCLwIkBW4GIIEnH4B/+kHNmJ9O1wCua9eK0mg2HGKEaYh2QIrCY9pbNUgKC41tDA1OxRt074WMnTfVu415ejpFngr14sElq7NzRGZtJvjjc2f7u543vY4pBVqY6TW5tEvePRn8QhsVWAs+SE7+SE77ZIS/qZtLb/mLlNq8dhKjVQBI82OaTKLGGY0QSSJizWM03NfAUuvhgwCSaJiStsP5l1188R+4KSxtSVoN+hanxsy6UTTAncyxEGHUrXhgy8f3LLOSAcPI/V5MZ1PiTE5MVdHpqHQMOS89PBTx8t7Ovatq/dhPimHVMdkcEbIG3W5c2TFZkMyz6E2+gfJINSBrGFTMjkNZF2msTb9hNu+EsEQ2w1QWTkM8hR5flvs7tde/PAMvngks2lVRtpto6whI4JxsCoeqzVp3S8xKYV+b97Zetvtn2xYwRUPPU51mrAeYRNErgwha5g3HVjz+ttv7XjZlzYWI6WxeEhTlQSXpeZb+JPg+gYvDEN8gBiScZNIAeNTOSCGM09K8wbZQIOMBKxb9Uv1qBFWI6oc0ih6ytdVp83c8/GmatvcGjyxlWeS6b0PkH4+qpFc+/zK/9bxYSQFV6O0/tLd7BQ3ND3yHNOUe5CPxHUvjd26X74bPB8SlKX+kPKcQ7g+CDfg6VwXMzicmdN2JoKJtrZq+fOBVn66n4vrZtoX2wO7fjG66YJjdsSnfBHHmOAQ8cvkjDA5fWf0C9/E0j/7yIB2Us2XbFlny2TIWuAG7bDoWk3iYZAMzmrPXkDt1aIhFWu1uXGQMW1uFKQzDdxjb7wfaDu+7TigiCGopAlwnQHp+g2Bn8fWoTGEwxIS4TezOcmpHE5CMIUr0sdIecw0GHBJoWgoV6PROEPr83mmMdIf1v2oBeoem1OoQYLj9rUXzhfvOzFqX/2HJ+yFM3f5zuj4n11vH3tp8NjdPEWUYMbmYuXWPfllTVa1wrmryNbCSCH9YSct8K7DoeLISdh2psbG0SnXk0ROxd9nI7gYlZjzE29aCugUb6eC6iKqLmtJRZZqVO9kXQ6O22qqUcg93aJOoTdvvuVb/6bNdtDaxdypefVUJo1d0PGJBvPZEjDLBLdZOMXc9x9c9/j96zbYNhuSXZo06/Hob/5+8gX/nUcOOix8/cA++KPJWQUSV4EtOoIFCFo5QSKlZ5+traRp7MSBYQPlnqFT9ELeNgr8EldHzYCqP2VJe2lUDX+qFg/ZdU57Is+SoI28bcYC7k7pmD9ybOy5TauPdtraGJejvdzeDyzYdfCSScqtPtIvFdx5FXc2UUN2SA80KWwY9CVZgT3e9q8iNeuSMiMubbzqC4F/KqjmpD6r0+q3s5atS3tx5b6fCjdeK06H6yxCBpTZuPRjh6SjFUw6A2JQKMFyhg8vTIbIII3KEVE7cZo52VOOFVN95TRlUo7ckqxNjsa0ZEnWS6Bb2YFuJeVIuAU6gqCTq5teY8PMiw38eQWCK/OdGn932GxyYgSKi9mvc2m7AcF6BCqCVYXNXg/X2WD2GQKPcjD7jS7qimVb3IIaWKNtUCjZjrhQ5C9El4GbhAwDnEJ3YqcqufR1EjL4HoFvc2Dnx71ZL8VOedyirbgl4c6/seCsdXBFCamsEdjnMOvPip4VYgGnXOykgNKwERd5rSAnDDjYLvBmB062euWkEX9ucZ3zarg/S+BJDnNu8zrnst7nvB7u9wv8O4c5t3udc2HBOUfgnFMhyl4g5NyjAr/kMOf9znP2467OcJeH75Kynax10aJgR1tLe3Mq8B2nXw7T/oWQ2Rfa+NyTDtPvdpsewa5suS1qdqtKKufXp2t1KGKVuAlbMuZvTlAlDhHZZnfGvu2cS9c0yEiZHIJ9o6ywREoQ/lcl3iaMFbgyQ5CMml4qlME5f4tCyDs1kxVB9n4ZC56xhd4Z8WJnx/Wbt4UX3TPTJzLeYjAEM2IzNNpNtQwuJtt5NSVBOUowHy4wQ9lCgSsyTZE24KkIwilSH5KWCZJBAvfPFT6dkNPVypjMamUe2CijgF4J+9JDPce3JAVpTM2H8UgaxDwXCDwrl9Xmwsvfc0m3euE7rXcHv9vy9Rwf6yACt+Xrby5tryA4jEAsX/zpfheKVxE8w/iuBpysXvhafdYLnPq0aXMcYhxcKiw3rwj8pDeHQJInBM7MZC4Mv+XSdgTB6xATnZQtzVrBnHivhcuApeICgad54x1JpgpcXxzv77u0fYjgXUYGAu8244vxyZYczvkObwpcJkx7vcCrC3Be2G/3JrIFqhEjrRJ4RXECferS9hmCY4xU4tvbOKMuQqXM0Q1THxJ4nzdzIMkfBX6sOO7/59L2NYIvMs3R6GqOHkjvw2xcebIA597MgSN9LfAnRQkklbi0leLDb3LN4ShUyhzrQKiQwIu8mQNJFgp8aXHcD3Zpq0JQlmmOZa7muBamvVfgn/SJOXCkTQKvL06gES5tuF2QanLN4ShUyhw3wtSfC/yON3MgyRGB3yiO+/EubRMRjMk0x0JXc2yATdMsgSf0iTlwpPEC1xQn0FSXtukI6nPN4ShUBRJhCQfeUHO+wNOLFIrveS/IkWeQGGSawBOLk+dMlzYsYyQ/w9eHKuNvhRy3lSXdhhp2cjdULWwlh70t8AFv7oYk+wV+ujhpLnJpwywlNTAyuEvWwxpdGgvLzJbHcWFHPf4eNg9HBH7CG/NIsk/gvYWZl9IG3ci5bHGRYD6Cixnp343vxp3ciu8SFsAFU48MCTypAOeOsWLvc3J8q1qMdJrAIwoLlFGrVqelanORaimCBVBv2bu7joLCpcwCi/vouwTu8WYWJEkIbHowy5UuAqxEcDnuB/nLMcsxRjRD7yxkrJcJGTPOxqP/1SfGwpHeFfhlr8ZSXWTFIhG/WVUKYwmR8elVTikOd1HvQab7vsADCptLmpmf0JCk1Ma1JwvLkWcu00UErOWlaDpLtxUWgXucH67jhNQNtvHYD7x5HJK8L/A7HkS42kWEdQi6ITHDppzZicyJfe5grXB9BuzfIfAVfeJgONLlArd4dbCbXETbgGA9IzXCwdwl5D4GNYHUH1abRwW+2ZOPcZINAl/jwUCbXKT4KYKNzD50Aj4WdJWCu9mZMHglIRMeFPhGT27GSW4QeJ0HKe5wkWIrglshUuKc9Rb8iAjZeY1DbuautgxGhmptYpONJ3zTF67GRzop8HGvrvYrF/G2I7iLkeHC1XqXkrsb7PSlekKgzrNxozd3Q5I5As8qLE6eoXa6SILvJaV7QBLhbkt7lSSZ2SQYatIGgUPeXA5JZIFdtvp5kvzWRZI9CHYzUtZNNUNRWU9BZ4O8JsHqMrnKxpMO9Ymz4Uh/Fdil5nR2tn0uguE7LWkvI1XJKsdFvmShIy0GZAi8wJtxkCQo8FwPxnnWRQas36Wnof7UDdVyrT8lSAXTPhV4T59YBkd6VOB7vVrmkItU+CFCei5dfxYULrmnAW2RwAGBH/ZmFiR5SOCdhaXI5O91l7Z/IHgZ9jSq1Zb+9sfldmJ+Msx8DSEzzxd4nDfmkaRO4JFF+dQfOJfvukjwbwRvMjJIDoeTXyXw0Swn/vEb0hZYKmMCe3udxEkWCuzyOimP/2Mu/ONLNek9WCNNGjW6qZsIfPWohZG3Q138jMCPFBbBafVAkocFvr84//ncpe1LBMeT55KwR5MT02h0qEvOmSrwSG9MI8kIgfM+VznqvZVzd9KF828RnICNFXKOHZoLOQwkjXOuE/gqbw6DJB0CLy9K275Sl7YyBBKsAZ2UBakcaYvzoyG8r+nEPtQIEuxUZ8cFbvfGPpIsF7itOParXNrwDZlvICPDTBoxqdV1OZVXN1tMjYr3KM4xi+v014RcdFjgW7zJgCQbBb6hV9/hv4czUic+evIDOBZV4iYstXhoS1fUmGx/ocz/7InyjXRRQB2CoYyMcVJAByYAu7531APEkA+2GHOeFHinJz1wkgcE3l6cLU9zaZuMYDxPW1yUIJ4G03qcuOdrOhS9PhCjcZKN53xVgHtPazof6YTAHxWVGKo59wEXyWYimJKu6nsXkJtnHrABws09IfDeAgIWMA+SPCbwQ8VI4hvFuZ3lIslsBGeAJNki9OZoVwIbUDxecr3AAW+SIIlf4O8VlqRf+mgEP/OxMRl6Q0XorTXM1dT0pw8jOcfbHBcFXILgPHBSuiYuaxDBrYamKj3Jmc7OOdmAp5LxwITlpzpMoNAoP3+Rvs8gz2cGiqisz9V4+nqMw/9BiP/KUZr+RHccnT99eIH/gRiZ939Sgm73tqqyEduWvmof2U3+x015kJRF4pqWeUo54740Bj6gcuuVc1gZ4xpqBeVka4Hxo73JM3++hXa/NkZK7X74awm36OgUaOZDjo6b+D9euz4bcaK0bMkRfsQeLDH+pcele5un/7PylY+GXHTe0Ntfez5wdOkL62+L3XrgTjrj50Mu+z+fFlxeezYAAA==";
}
