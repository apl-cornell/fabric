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
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.common.ConfigProperties;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
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
 * {@link DerivedMetricContract} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link MetricContract}s on the terms it is
 * derived from.</li>
 * </ul>
 */
public interface DerivedMetric
  extends fabric.metrics.util.Observer, fabric.metrics.Metric {
    public fabric.lang.arrays.doubleArray get$lastStats();
    
    public fabric.lang.arrays.doubleArray set$lastStats(
      fabric.lang.arrays.doubleArray val);
    
    public fabric.lang.arrays.ObjectArray get$terms();
    
    public fabric.lang.arrays.ObjectArray set$terms(
      fabric.lang.arrays.ObjectArray val);
    
    public fabric.util.Set get$leafMetrics();
    
    public fabric.util.Set set$leafMetrics(fabric.util.Set val);
    
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
      fabric.lang.arrays.ObjectArray terms);
    
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
    
    public fabric.util.Set getLeafSubjects();
    
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
        
        public static final byte[] $classHash = new byte[] { 60, -34, 109, -28,
        -75, -36, 92, 123, 118, 77, -127, 74, -60, -58, 27, -19, 9, 95, 26, 60,
        -20, -93, 105, -110, -98, -110, 110, 76, -17, -52, 82, -23 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1507317673000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public void refreshWeakEstimates();
    
    public void refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller);
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.DerivedMetric {
        public fabric.lang.arrays.doubleArray get$lastStats() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$lastStats(
                                                                    );
        }
        
        public fabric.lang.arrays.doubleArray set$lastStats(
          fabric.lang.arrays.doubleArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$lastStats(
                                                                    val);
        }
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public fabric.util.Set get$leafMetrics() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.util.Set set$leafMetrics(fabric.util.Set val) {
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
          fabric.lang.arrays.ObjectArray arg1) {
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.DerivedMetric) fetch()).getLeafSubjects();
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.DerivedMetric {
        public fabric.lang.arrays.doubleArray get$lastStats() {
            return this.lastStats;
        }
        
        public fabric.lang.arrays.doubleArray set$lastStats(
          fabric.lang.arrays.doubleArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastStats = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.doubleArray lastStats;
        
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
        
        public fabric.util.Set get$leafMetrics() { return this.leafMetrics; }
        
        public fabric.util.Set set$leafMetrics(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set leafMetrics;
        
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
          fabric.lang.arrays.ObjectArray terms) {
            if (((fabric.util.TreeSet)
                   new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
                  fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms)).
                  size() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "DerivedMetric terms must not contain duplicates: " +
                          fabric.util.Arrays._Impl.deepToString(terms));
            this.set$leafMetrics(
                   ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(
                        this.$getStore()).$getProxy()).fabric$util$HashSet$());
            boolean single = true;
            for (int i = 0; i < terms.get$length(); i++) {
                if (!((fabric.metrics.Metric) terms.get(i)).isSingleStore() ||
                      !((fabric.metrics.Metric) terms.get(i)).$getStore().
                      equals($getStore())) {
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
                            terms.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(terms, 0, this.get$terms(), 0,
                                               terms.get$length());
            this.set$lastStats(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       s).fabric$lang$arrays$doubleArray$(lbl, lbl.confPolicy(),
                                                          3).$getProxy());
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
                    this.get$leafMetrics().add((fabric.metrics.Metric)
                                                 this.get$terms().get(i));
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(
                                 (fabric.metrics.Metric) this.get$terms().get(i))) instanceof fabric.metrics.DerivedMetric) {
                    this.
                      get$leafMetrics().
                      addAll(
                        ((fabric.metrics.DerivedMetric)
                           fabric.lang.Object._Proxy.
                           $getProxy((fabric.metrics.Metric)
                                       this.get$terms().get(i))).
                            getLeafSubjects());
                }
                else {
                    throw new java.lang.IllegalStateException(
                            "This shouldn\'t happen, all metrics should either be a SampledMetric or a DerivedMetric!");
                }
            }
        }
        
        public boolean handleUpdates() {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINER,
                  "CHECKING FOR UPDATE ON DERIVED METRIC");
            double newValue = computeValue(false);
            if (newValue != (double) this.get$lastStats().get(0)) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINER,
                      "UPDATE ON DERIVED METRIC");
                this.get$lastStats().set(0, newValue);
                this.get$lastStats().set(1, computeVelocity(false));
                this.get$lastStats().set(2, computeNoise(false));
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
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm29 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled32 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff30 = 1;
                    boolean $doBackoff31 = true;
                    $label25: for (boolean $commit26 = false; !$commit26; ) {
                        if ($backoffEnabled32) {
                            if ($doBackoff31) {
                                if ($backoff30 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff30);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e27) {
                                            
                                        }
                                    }
                                }
                                if ($backoff30 < 5000) $backoff30 *= 2;
                            }
                            $doBackoff31 = $backoff30 <= 32 || !$doBackoff31;
                        }
                        $commit26 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) tmp.get$weakStats().get(0); }
                        catch (final fabric.worker.RetryException $e27) {
                            $commit26 = false;
                            continue $label25;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e27) {
                            $commit26 = false;
                            fabric.common.TransactionID $currentTid28 =
                              $tm29.getCurrentTid();
                            if ($e27.tid.isDescendantOf($currentTid28))
                                continue $label25;
                            if ($currentTid28.parent != null) throw $e27;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e27) {
                            $commit26 = false;
                            if ($tm29.checkForStaleObjects()) continue $label25;
                            throw new fabric.worker.AbortException($e27);
                        }
                        finally {
                            if ($commit26) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e27) {
                                    $commit26 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e27) {
                                    $commit26 = false;
                                    fabric.common.TransactionID $currentTid28 =
                                      $tm29.getCurrentTid();
                                    if ($currentTid28 != null) {
                                        if ($e27.tid.equals($currentTid28) ||
                                              !$e27.tid.isDescendantOf(
                                                          $currentTid28)) {
                                            throw $e27;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit26) {
                                {  }
                                continue $label25;
                            }
                        }
                    }
                }
            }
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) tmp.get$lastStats().get(0);
            }
            return tmp.computeValue(false);
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
                {
                    fabric.worker.transaction.TransactionManager $tm37 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled40 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff38 = 1;
                    boolean $doBackoff39 = true;
                    $label33: for (boolean $commit34 = false; !$commit34; ) {
                        if ($backoffEnabled40) {
                            if ($doBackoff39) {
                                if ($backoff38 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff38);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e35) {
                                            
                                        }
                                    }
                                }
                                if ($backoff38 < 5000) $backoff38 *= 2;
                            }
                            $doBackoff39 = $backoff38 <= 32 || !$doBackoff39;
                        }
                        $commit34 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) tmp.get$weakStats().get(1); }
                        catch (final fabric.worker.RetryException $e35) {
                            $commit34 = false;
                            continue $label33;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e35) {
                            $commit34 = false;
                            fabric.common.TransactionID $currentTid36 =
                              $tm37.getCurrentTid();
                            if ($e35.tid.isDescendantOf($currentTid36))
                                continue $label33;
                            if ($currentTid36.parent != null) throw $e35;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e35) {
                            $commit34 = false;
                            if ($tm37.checkForStaleObjects()) continue $label33;
                            throw new fabric.worker.AbortException($e35);
                        }
                        finally {
                            if ($commit34) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e35) {
                                    $commit34 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e35) {
                                    $commit34 = false;
                                    fabric.common.TransactionID $currentTid36 =
                                      $tm37.getCurrentTid();
                                    if ($currentTid36 != null) {
                                        if ($e35.tid.equals($currentTid36) ||
                                              !$e35.tid.isDescendantOf(
                                                          $currentTid36)) {
                                            throw $e35;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit34) {
                                {  }
                                continue $label33;
                            }
                        }
                    }
                }
            }
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) tmp.get$lastStats().get(1);
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
                {
                    fabric.worker.transaction.TransactionManager $tm45 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled48 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff46 = 1;
                    boolean $doBackoff47 = true;
                    $label41: for (boolean $commit42 = false; !$commit42; ) {
                        if ($backoffEnabled48) {
                            if ($doBackoff47) {
                                if ($backoff46 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff46);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e43) {
                                            
                                        }
                                    }
                                }
                                if ($backoff46 < 5000) $backoff46 *= 2;
                            }
                            $doBackoff47 = $backoff46 <= 32 || !$doBackoff47;
                        }
                        $commit42 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) tmp.get$weakStats().get(2); }
                        catch (final fabric.worker.RetryException $e43) {
                            $commit42 = false;
                            continue $label41;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e43) {
                            $commit42 = false;
                            fabric.common.TransactionID $currentTid44 =
                              $tm45.getCurrentTid();
                            if ($e43.tid.isDescendantOf($currentTid44))
                                continue $label41;
                            if ($currentTid44.parent != null) throw $e43;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e43) {
                            $commit42 = false;
                            if ($tm45.checkForStaleObjects()) continue $label41;
                            throw new fabric.worker.AbortException($e43);
                        }
                        finally {
                            if ($commit42) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e43) {
                                    $commit42 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e43) {
                                    $commit42 = false;
                                    fabric.common.TransactionID $currentTid44 =
                                      $tm45.getCurrentTid();
                                    if ($currentTid44 != null) {
                                        if ($e43.tid.equals($currentTid44) ||
                                              !$e43.tid.isDescendantOf(
                                                          $currentTid44)) {
                                            throw $e43;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit42) {
                                {  }
                                continue $label41;
                            }
                        }
                    }
                }
            }
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) tmp.get$lastStats().get(2);
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
                this.get$lastStats().set(0, computeValue(false));
                this.get$lastStats().set(1, computeVelocity(false));
                this.get$lastStats().set(2, computeNoise(false));
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
            fabric.lang.arrays.ObjectArray copy =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
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
        
        public fabric.util.Set getLeafSubjects() {
            return fabric.util.Collections._Impl.
              unmodifiableSet(fabric.worker.Worker.getWorker().getLocalStore(),
                              this.get$leafMetrics());
        }
        
        public void refreshWeakEstimates() {
            fabric.common.Logging.METRICS_LOGGER.
              finest(
                "REFRESHING" +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.DerivedMetric)
                                    this.$getProxy())));
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    fabric.worker.transaction.TransactionManager.getInstance().
                        getCurrentLog(),
                    null)) {
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[this.get$terms().get$length(
                                                                     )];
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    final fabric.metrics.Metric t = (fabric.metrics.Metric)
                                                      this.get$terms().get(i);
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
                        java.util.concurrent.Callable c$var49 = c;
                        int i$var50 = i;
                        fabric.worker.transaction.TransactionManager $tm55 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled58 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff56 = 1;
                        boolean $doBackoff57 = true;
                        $label51: for (boolean $commit52 = false; !$commit52;
                                       ) {
                            if ($backoffEnabled58) {
                                if ($doBackoff57) {
                                    if ($backoff56 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff56);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e53) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff56 < 5000) $backoff56 *= 2;
                                }
                                $doBackoff57 = $backoff56 <= 32 ||
                                                 !$doBackoff57;
                            }
                            $commit52 = true;
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
                            catch (final fabric.worker.RetryException $e53) {
                                $commit52 = false;
                                continue $label51;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e53) {
                                $commit52 = false;
                                fabric.common.TransactionID $currentTid54 =
                                  $tm55.getCurrentTid();
                                if ($e53.tid.isDescendantOf($currentTid54))
                                    continue $label51;
                                if ($currentTid54.parent != null) throw $e53;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e53) {
                                $commit52 = false;
                                if ($tm55.checkForStaleObjects())
                                    continue $label51;
                                throw new fabric.worker.AbortException($e53);
                            }
                            finally {
                                if ($commit52) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e53) {
                                        $commit52 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e53) {
                                        $commit52 = false;
                                        fabric.common.TransactionID
                                          $currentTid54 = $tm55.getCurrentTid();
                                        if ($currentTid54 != null) {
                                            if ($e53.tid.equals(
                                                           $currentTid54) ||
                                                  !$e53.tid.isDescendantOf(
                                                              $currentTid54)) {
                                                throw $e53;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit52) {
                                    {
                                        c = c$var49;
                                        i = i$var50;
                                    }
                                    continue $label51;
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
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    fabric.common.Logging.METRICS_LOGGER.
                      finest(
                        "(SERIAL) CHILD " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.Metric)
                                            this.get$terms().get(i))) +
                          " OF " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.DerivedMetric)
                                            this.$getProxy())));
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
            $writeRef($getStore(), this.lastStats, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.lastStats =
              (fabric.lang.arrays.doubleArray)
                $readRef(fabric.lang.arrays.doubleArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.terms = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.leafMetrics = (fabric.util.Set)
                                 $readRef(fabric.util.Set._Proxy.class,
                                          (fabric.common.RefTypeEnum)
                                            refTypes.next(), in, store,
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
            this.lastStats = src.lastStats;
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
                        fabric.worker.transaction.TransactionManager $tm63 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled66 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff64 = 1;
                        boolean $doBackoff65 = true;
                        $label59: for (boolean $commit60 = false; !$commit60;
                                       ) {
                            if ($backoffEnabled66) {
                                if ($doBackoff65) {
                                    if ($backoff64 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff64);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e61) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff64 < 5000) $backoff64 *= 2;
                                }
                                $doBackoff65 = $backoff64 <= 32 ||
                                                 !$doBackoff65;
                            }
                            $commit60 = true;
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
                            catch (final fabric.worker.RetryException $e61) {
                                $commit60 = false;
                                continue $label59;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e61) {
                                $commit60 = false;
                                fabric.common.TransactionID $currentTid62 =
                                  $tm63.getCurrentTid();
                                if ($e61.tid.isDescendantOf($currentTid62))
                                    continue $label59;
                                if ($currentTid62.parent != null) throw $e61;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e61) {
                                $commit60 = false;
                                if ($tm63.checkForStaleObjects())
                                    continue $label59;
                                throw new fabric.worker.AbortException($e61);
                            }
                            finally {
                                if ($commit60) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e61) {
                                        $commit60 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e61) {
                                        $commit60 = false;
                                        fabric.common.TransactionID
                                          $currentTid62 = $tm63.getCurrentTid();
                                        if ($currentTid62 != null) {
                                            if ($e61.tid.equals(
                                                           $currentTid62) ||
                                                  !$e61.tid.isDescendantOf(
                                                              $currentTid62)) {
                                                throw $e61;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit60) {
                                    {  }
                                    continue $label59;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 80, -13, 41, -82, -46,
    -51, 125, -88, 29, 7, 34, 32, -115, -110, 10, 17, 14, 55, 18, 68, 66, 38,
    -18, -20, -105, 58, 93, 49, 59, -21, 68, -7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507317673000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
