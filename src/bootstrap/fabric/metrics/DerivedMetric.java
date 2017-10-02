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
        public fabric.metrics.DerivedMetric get$out$();
        
        public fabric.metrics.Metric get$t();
        
        public fabric.metrics.Metric set$t(fabric.metrics.Metric val);
        
        public Refresher fabric$metrics$DerivedMetric$Refresher$(
          fabric.metrics.Metric t);
        
        public java.lang.Object call();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements Refresher {
            public fabric.metrics.DerivedMetric get$out$() {
                return ((fabric.metrics.DerivedMetric.Refresher._Impl) fetch()).
                  get$out$();
            }
            
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
            public fabric.metrics.DerivedMetric get$out$() { return this.out$; }
            
            private fabric.metrics.DerivedMetric out$;
            
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
            
            public _Impl(fabric.worker.Store $location,
                         final fabric.metrics.DerivedMetric out$) {
                super($location);
                this.out$ = out$;
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.DerivedMetric.Refresher._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.out$, refTypes, out, intraStoreRefs,
                          interStoreRefs);
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
                this.out$ =
                  (fabric.metrics.DerivedMetric)
                    $readRef(fabric.metrics.DerivedMetric._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
                this.t = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.DerivedMetric.Refresher._Impl src =
                  (fabric.metrics.DerivedMetric.Refresher._Impl) other;
                this.out$ = src.out$;
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
        
        public static final byte[] $classHash = new byte[] { 30, 92, 101, 74,
        60, 85, 2, -31, 102, -92, -1, -75, -101, -26, 83, -8, -21, -82, 21, 105,
        -100, 52, -13, 113, 54, -104, -93, -49, -34, -88, -128, 72 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1506965771000L;
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
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm4 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff5 = 1;
                    boolean $doBackoff6 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($doBackoff6) {
                            if ($backoff5 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff5);
                                        break;
                                    }
                                    catch (java.lang.InterruptedException $e2) {
                                        
                                    }
                                }
                            }
                            if ($backoff5 < 5000) $backoff5 *= 1;
                        }
                        $doBackoff6 = $backoff5 <= 32 || !$doBackoff6;
                        $commit1 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(0); }
                        catch (final fabric.worker.RetryException $e2) {
                            $commit1 = false;
                            continue $label0;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e2) {
                            $commit1 = false;
                            fabric.common.TransactionID $currentTid3 =
                              $tm4.getCurrentTid();
                            if ($e2.tid.isDescendantOf($currentTid3))
                                continue $label0;
                            if ($currentTid3.parent != null) throw $e2;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e2) {
                            $commit1 = false;
                            if ($tm4.checkForStaleObjects()) continue $label0;
                            throw new fabric.worker.AbortException($e2);
                        }
                        finally {
                            if ($commit1) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e2) {
                                    $commit1 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e2) {
                                    $commit1 = false;
                                    fabric.common.TransactionID $currentTid3 =
                                      $tm4.getCurrentTid();
                                    if ($currentTid3 != null) {
                                        if ($e2.tid.equals($currentTid3) ||
                                              !$e2.tid.isDescendantOf(
                                                         $currentTid3)) {
                                            throw $e2;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit1) {
                                {  }
                                continue $label0;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) this.get$lastStats().get(0);
            }
            return computeValue(false);
        }
        
        public double velocity(boolean useWeakCache) {
            if (this.get$usePreset()) return this.get$presetV();
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm11 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff12 = 1;
                    boolean $doBackoff13 = true;
                    $label7: for (boolean $commit8 = false; !$commit8; ) {
                        if ($doBackoff13) {
                            if ($backoff12 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff12);
                                        break;
                                    }
                                    catch (java.lang.InterruptedException $e9) {
                                        
                                    }
                                }
                            }
                            if ($backoff12 < 5000) $backoff12 *= 1;
                        }
                        $doBackoff13 = $backoff12 <= 32 || !$doBackoff13;
                        $commit8 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(1); }
                        catch (final fabric.worker.RetryException $e9) {
                            $commit8 = false;
                            continue $label7;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e9) {
                            $commit8 = false;
                            fabric.common.TransactionID $currentTid10 =
                              $tm11.getCurrentTid();
                            if ($e9.tid.isDescendantOf($currentTid10))
                                continue $label7;
                            if ($currentTid10.parent != null) throw $e9;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e9) {
                            $commit8 = false;
                            if ($tm11.checkForStaleObjects()) continue $label7;
                            throw new fabric.worker.AbortException($e9);
                        }
                        finally {
                            if ($commit8) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e9) {
                                    $commit8 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e9) {
                                    $commit8 = false;
                                    fabric.common.TransactionID $currentTid10 =
                                      $tm11.getCurrentTid();
                                    if ($currentTid10 != null) {
                                        if ($e9.tid.equals($currentTid10) ||
                                              !$e9.tid.isDescendantOf(
                                                         $currentTid10)) {
                                            throw $e9;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit8) {
                                {  }
                                continue $label7;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) this.get$lastStats().get(1);
            }
            return computeVelocity(false);
        }
        
        public double noise(boolean useWeakCache) {
            if (this.get$usePreset()) return this.get$presetN();
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm18 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff19 = 1;
                    boolean $doBackoff20 = true;
                    $label14: for (boolean $commit15 = false; !$commit15; ) {
                        if ($doBackoff20) {
                            if ($backoff19 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff19);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e16) {
                                        
                                    }
                                }
                            }
                            if ($backoff19 < 5000) $backoff19 *= 1;
                        }
                        $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                        $commit15 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(2); }
                        catch (final fabric.worker.RetryException $e16) {
                            $commit15 = false;
                            continue $label14;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e16) {
                            $commit15 = false;
                            fabric.common.TransactionID $currentTid17 =
                              $tm18.getCurrentTid();
                            if ($e16.tid.isDescendantOf($currentTid17))
                                continue $label14;
                            if ($currentTid17.parent != null) throw $e16;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e16) {
                            $commit15 = false;
                            if ($tm18.checkForStaleObjects()) continue $label14;
                            throw new fabric.worker.AbortException($e16);
                        }
                        finally {
                            if ($commit15) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e16) {
                                    $commit15 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e16) {
                                    $commit15 = false;
                                    fabric.common.TransactionID $currentTid17 =
                                      $tm18.getCurrentTid();
                                    if ($currentTid17 != null) {
                                        if ($e16.tid.equals($currentTid17) ||
                                              !$e16.tid.isDescendantOf(
                                                          $currentTid17)) {
                                            throw $e16;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit15) {
                                {  }
                                continue $label14;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) this.get$lastStats().get(2);
            }
            return computeNoise(false);
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
                "REFRESHING CHILDREN OF " +
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
                        java.util.concurrent.Callable c$var21 = c;
                        int i$var22 = i;
                        fabric.worker.transaction.TransactionManager $tm27 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff28 = 1;
                        boolean $doBackoff29 = true;
                        $label23: for (boolean $commit24 = false; !$commit24;
                                       ) {
                            if ($doBackoff29) {
                                if ($backoff28 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff28);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e25) {
                                            
                                        }
                                    }
                                }
                                if ($backoff28 < 5000) $backoff28 *= 1;
                            }
                            $doBackoff29 = $backoff28 <= 32 || !$doBackoff29;
                            $commit24 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                c =
                                  (java.util.concurrent.Callable)
                                    fabric.lang.WrappedJavaInlineable.
                                    $unwrap(
                                      ((Refresher)
                                         new fabric.metrics.DerivedMetric.
                                           Refresher._Impl(
                                           this.$getStore(),
                                           (fabric.metrics.DerivedMetric)
                                             this.$getProxy()).
                                         $getProxy()).
                                          fabric$metrics$DerivedMetric$Refresher$(
                                            t));
                            }
                            catch (final fabric.worker.RetryException $e25) {
                                $commit24 = false;
                                continue $label23;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e25) {
                                $commit24 = false;
                                fabric.common.TransactionID $currentTid26 =
                                  $tm27.getCurrentTid();
                                if ($e25.tid.isDescendantOf($currentTid26))
                                    continue $label23;
                                if ($currentTid26.parent != null) throw $e25;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e25) {
                                $commit24 = false;
                                if ($tm27.checkForStaleObjects())
                                    continue $label23;
                                throw new fabric.worker.AbortException($e25);
                            }
                            finally {
                                if ($commit24) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e25) {
                                        $commit24 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e25) {
                                        $commit24 = false;
                                        fabric.common.TransactionID
                                          $currentTid26 = $tm27.getCurrentTid();
                                        if ($currentTid26 != null) {
                                            if ($e25.tid.equals(
                                                           $currentTid26) ||
                                                  !$e25.tid.isDescendantOf(
                                                              $currentTid26)) {
                                                throw $e25;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit24) {
                                    {
                                        c = c$var21;
                                        i = i$var22;
                                    }
                                    continue $label23;
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
            super.refreshWeakEstimates();
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
                        fabric.worker.transaction.TransactionManager $tm34 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff35 = 1;
                        boolean $doBackoff36 = true;
                        $label30: for (boolean $commit31 = false; !$commit31;
                                       ) {
                            if ($doBackoff36) {
                                if ($backoff35 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff35);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e32) {
                                            
                                        }
                                    }
                                }
                                if ($backoff35 < 5000) $backoff35 *= 1;
                            }
                            $doBackoff36 = $backoff35 <= 32 || !$doBackoff36;
                            $commit31 = true;
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
                            catch (final fabric.worker.RetryException $e32) {
                                $commit31 = false;
                                continue $label30;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e32) {
                                $commit31 = false;
                                fabric.common.TransactionID $currentTid33 =
                                  $tm34.getCurrentTid();
                                if ($e32.tid.isDescendantOf($currentTid33))
                                    continue $label30;
                                if ($currentTid33.parent != null) throw $e32;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e32) {
                                $commit31 = false;
                                if ($tm34.checkForStaleObjects())
                                    continue $label30;
                                throw new fabric.worker.AbortException($e32);
                            }
                            finally {
                                if ($commit31) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e32) {
                                        $commit31 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e32) {
                                        $commit31 = false;
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
                                if (!$commit31) {
                                    {  }
                                    continue $label30;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -58, -108, -94, 113,
    20, -22, -29, -95, 85, -68, 12, -69, -37, 29, 108, -67, 91, -40, 31, 22,
    -90, 38, 5, -3, -17, -86, -108, 114, 79, 36, 92, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965771000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
