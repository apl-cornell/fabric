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
        
        public static final byte[] $classHash = new byte[] { -117, 106, 65, -32,
        -26, -16, 119, 19, -45, 125, 28, 20, 58, -53, -23, -109, -51, -64, -17,
        -102, 68, 122, 46, -84, -118, 95, 18, 37, -52, -115, -93, 106 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1507151083000L;
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
                        try { return (double) this.get$weakStats().get(0); }
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
                        try { return (double) this.get$weakStats().get(1); }
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
                        try { return (double) this.get$weakStats().get(2); }
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
                                           Refresher._Impl(
                                           this.$getStore(),
                                           (fabric.metrics.DerivedMetric)
                                             this.$getProxy()).
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
    
    public static final byte[] $classHash = new byte[] { 62, -55, 16, 125, -94,
    -97, -101, -69, -5, -124, -54, -33, 11, 119, -65, -47, 48, -16, -120, 14,
    -87, 103, -82, -5, 91, -17, 2, -85, -16, 89, -41, 11 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151083000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
