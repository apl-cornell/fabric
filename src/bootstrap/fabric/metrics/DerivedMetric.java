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
        public static final long jlc$SourceLastModified$fabil = 1507043120000L;
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
                    boolean $backoffEnabled7 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff5 = 1;
                    boolean $doBackoff6 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($backoffEnabled7) {
                            if ($doBackoff6) {
                                if ($backoff5 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff5);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e2) {
                                            
                                        }
                                    }
                                }
                                if ($backoff5 < 5000) $backoff5 *= 2;
                            }
                            $doBackoff6 = $backoff5 <= 32 || !$doBackoff6;
                        }
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
                    fabric.worker.transaction.TransactionManager $tm12 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled15 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff13 = 1;
                    boolean $doBackoff14 = true;
                    $label8: for (boolean $commit9 = false; !$commit9; ) {
                        if ($backoffEnabled15) {
                            if ($doBackoff14) {
                                if ($backoff13 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff13);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e10) {
                                            
                                        }
                                    }
                                }
                                if ($backoff13 < 5000) $backoff13 *= 2;
                            }
                            $doBackoff14 = $backoff13 <= 32 || !$doBackoff14;
                        }
                        $commit9 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(1); }
                        catch (final fabric.worker.RetryException $e10) {
                            $commit9 = false;
                            continue $label8;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e10) {
                            $commit9 = false;
                            fabric.common.TransactionID $currentTid11 =
                              $tm12.getCurrentTid();
                            if ($e10.tid.isDescendantOf($currentTid11))
                                continue $label8;
                            if ($currentTid11.parent != null) throw $e10;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e10) {
                            $commit9 = false;
                            if ($tm12.checkForStaleObjects()) continue $label8;
                            throw new fabric.worker.AbortException($e10);
                        }
                        finally {
                            if ($commit9) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e10) {
                                    $commit9 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e10) {
                                    $commit9 = false;
                                    fabric.common.TransactionID $currentTid11 =
                                      $tm12.getCurrentTid();
                                    if ($currentTid11 != null) {
                                        if ($e10.tid.equals($currentTid11) ||
                                              !$e10.tid.isDescendantOf(
                                                          $currentTid11)) {
                                            throw $e10;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit9) {
                                {  }
                                continue $label8;
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
                    fabric.worker.transaction.TransactionManager $tm20 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled23 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff21 = 1;
                    boolean $doBackoff22 = true;
                    $label16: for (boolean $commit17 = false; !$commit17; ) {
                        if ($backoffEnabled23) {
                            if ($doBackoff22) {
                                if ($backoff21 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff21);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e18) {
                                            
                                        }
                                    }
                                }
                                if ($backoff21 < 5000) $backoff21 *= 2;
                            }
                            $doBackoff22 = $backoff21 <= 32 || !$doBackoff22;
                        }
                        $commit17 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(2); }
                        catch (final fabric.worker.RetryException $e18) {
                            $commit17 = false;
                            continue $label16;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e18) {
                            $commit17 = false;
                            fabric.common.TransactionID $currentTid19 =
                              $tm20.getCurrentTid();
                            if ($e18.tid.isDescendantOf($currentTid19))
                                continue $label16;
                            if ($currentTid19.parent != null) throw $e18;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e18) {
                            $commit17 = false;
                            if ($tm20.checkForStaleObjects()) continue $label16;
                            throw new fabric.worker.AbortException($e18);
                        }
                        finally {
                            if ($commit17) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e18) {
                                    $commit17 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e18) {
                                    $commit17 = false;
                                    fabric.common.TransactionID $currentTid19 =
                                      $tm20.getCurrentTid();
                                    if ($currentTid19 != null) {
                                        if ($e18.tid.equals($currentTid19) ||
                                              !$e18.tid.isDescendantOf(
                                                          $currentTid19)) {
                                            throw $e18;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit17) {
                                {  }
                                continue $label16;
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
                        java.util.concurrent.Callable c$var24 = c;
                        int i$var25 = i;
                        fabric.worker.transaction.TransactionManager $tm30 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled33 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff31 = 1;
                        boolean $doBackoff32 = true;
                        $label26: for (boolean $commit27 = false; !$commit27;
                                       ) {
                            if ($backoffEnabled33) {
                                if ($doBackoff32) {
                                    if ($backoff31 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff31);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e28) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff31 < 5000) $backoff31 *= 2;
                                }
                                $doBackoff32 = $backoff31 <= 32 ||
                                                 !$doBackoff32;
                            }
                            $commit27 = true;
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
                            catch (final fabric.worker.RetryException $e28) {
                                $commit27 = false;
                                continue $label26;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e28) {
                                $commit27 = false;
                                fabric.common.TransactionID $currentTid29 =
                                  $tm30.getCurrentTid();
                                if ($e28.tid.isDescendantOf($currentTid29))
                                    continue $label26;
                                if ($currentTid29.parent != null) throw $e28;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e28) {
                                $commit27 = false;
                                if ($tm30.checkForStaleObjects())
                                    continue $label26;
                                throw new fabric.worker.AbortException($e28);
                            }
                            finally {
                                if ($commit27) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e28) {
                                        $commit27 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e28) {
                                        $commit27 = false;
                                        fabric.common.TransactionID
                                          $currentTid29 = $tm30.getCurrentTid();
                                        if ($currentTid29 != null) {
                                            if ($e28.tid.equals(
                                                           $currentTid29) ||
                                                  !$e28.tid.isDescendantOf(
                                                              $currentTid29)) {
                                                throw $e28;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit27) {
                                    {
                                        c = c$var24;
                                        i = i$var25;
                                    }
                                    continue $label26;
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
                        fabric.worker.transaction.TransactionManager $tm38 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled41 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff39 = 1;
                        boolean $doBackoff40 = true;
                        $label34: for (boolean $commit35 = false; !$commit35;
                                       ) {
                            if ($backoffEnabled41) {
                                if ($doBackoff40) {
                                    if ($backoff39 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff39);
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
                            $commit35 = true;
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
                            catch (final fabric.worker.RetryException $e36) {
                                $commit35 = false;
                                continue $label34;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e36) {
                                $commit35 = false;
                                fabric.common.TransactionID $currentTid37 =
                                  $tm38.getCurrentTid();
                                if ($e36.tid.isDescendantOf($currentTid37))
                                    continue $label34;
                                if ($currentTid37.parent != null) throw $e36;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e36) {
                                $commit35 = false;
                                if ($tm38.checkForStaleObjects())
                                    continue $label34;
                                throw new fabric.worker.AbortException($e36);
                            }
                            finally {
                                if ($commit35) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e36) {
                                        $commit35 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e36) {
                                        $commit35 = false;
                                        fabric.common.TransactionID
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
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit35) {
                                    {  }
                                    continue $label34;
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
    public static final long jlc$SourceLastModified$fabil = 1507043120000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
