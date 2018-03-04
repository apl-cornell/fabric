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
    public fabric.lang.arrays.doubleArray get$lastStats();
    
    public fabric.lang.arrays.doubleArray set$lastStats(
      fabric.lang.arrays.doubleArray val);
    
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
    
    public double samples(boolean useWeakCache);
    
    public double computeSamples(boolean useWeakCache);
    
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
        
        public static final byte[] $classHash = new byte[] { -20, 104, 48, 3,
        63, 122, 76, 57, -31, 25, -115, -112, -101, -19, 68, 107, 10, 90, -106,
        -57, -56, 105, 65, 24, -54, -100, 31, -100, -84, -51, 47, -30 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1520201027000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public void refreshWeakEstimates();
    
    public void refreshWeakEstimates_remote(
      fabric.lang.security.Principal caller);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      equalityPolicy(double value, boolean useWeakCache,
                     final fabric.worker.Store s);
    
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
            this.set$lastStats(
                   (fabric.lang.arrays.doubleArray)
                     new fabric.lang.arrays.doubleArray._Impl(
                       s).fabric$lang$arrays$doubleArray$(lbl, lbl.confPolicy(),
                                                          4).$getProxy());
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
            if (newValue != (double) this.get$lastStats().get(0)) {
                this.get$lastStats().set(0, newValue);
                this.get$lastStats().set(1, computeVelocity(false));
                this.get$lastStats().set(2, computeNoise(false));
                this.get$lastStats().set(3, computeSamples(false));
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
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return (double) tmp.get$weakStats().get(0);
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
                            try { rtn = (double) tmp.get$weakStats().get(0); }
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
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) tmp.get$lastStats().get(0);
            }
            return tmp.computeValue(false);
        }
        
        public double samples(boolean useWeakCache) {
            return fabric.metrics.DerivedMetric._Impl.
              static_samples((fabric.metrics.DerivedMetric) this.$getProxy(),
                             useWeakCache);
        }
        
        private static double static_samples(fabric.metrics.DerivedMetric tmp,
                                             boolean useWeakCache) {
            if (useWeakCache) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return (double) tmp.get$weakStats().get(3);
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var10 = rtn;
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
                            try { rtn = (double) tmp.get$weakStats().get(3); }
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
            else if (tmp.isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
                return (double) tmp.get$lastStats().get(3);
            }
            return tmp.computeSamples(false);
        }
        
        public double computeSamples(boolean useWeakCache) {
            double samples = 0;
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                samples =
                  java.lang.Math.max(
                                   samples,
                                   ((fabric.metrics.Metric)
                                      this.get$terms().get(i)).samples(
                                                                 useWeakCache));
            }
            return samples;
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
                    return (double) tmp.get$weakStats().get(1);
                }
                else {
                    double rtn = 0;
                    {
                        double rtn$var20 = rtn;
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
                            try { rtn = (double) tmp.get$weakStats().get(1); }
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
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return (double) tmp.get$weakStats().get(2);
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
                            try { rtn = (double) tmp.get$weakStats().get(2); }
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
                java.util.concurrent.Future[] futures =
                  new java.util.concurrent.Future[this.get$terms().get$length(
                                                                     )];
                for (int i = 0; i < this.get$terms().get$length(); i++) {
                    final fabric.metrics.Metric t = (fabric.metrics.Metric)
                                                      this.get$terms().get(i);
                    java.util.concurrent.Callable c = null;
                    if (fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn()) {
                        c =
                          (java.util.concurrent.Callable)
                            fabric.lang.WrappedJavaInlineable.
                            $unwrap(
                              ((Refresher)
                                 new fabric.metrics.DerivedMetric.Refresher.
                                   _Impl(this.$getStore()).
                                 $getProxy()).
                                  fabric$metrics$DerivedMetric$Refresher$(t));
                    }
                    else {
                        {
                            java.util.concurrent.Callable c$var40 = c;
                            int i$var41 = i;
                            fabric.worker.transaction.TransactionManager $tm47 =
                              fabric.worker.transaction.TransactionManager.
                              getInstance();
                            boolean $backoffEnabled50 =
                              fabric.worker.Worker.getWorker(
                                                     ).config.txRetryBackoff;
                            int $backoff48 = 1;
                            boolean $doBackoff49 = true;
                            boolean $retry44 = true;
                            $label42: for (boolean $commit43 = false;
                                           !$commit43; ) {
                                if ($backoffEnabled50) {
                                    if ($doBackoff49) {
                                        if ($backoff48 > 32) {
                                            while (true) {
                                                try {
                                                    java.lang.Thread.
                                                      sleep($backoff48);
                                                    break;
                                                }
                                                catch (java.lang.
                                                         InterruptedException $e45) {
                                                    
                                                }
                                            }
                                        }
                                        if ($backoff48 < 5000) $backoff48 *= 2;
                                    }
                                    $doBackoff49 = $backoff48 <= 32 ||
                                                     !$doBackoff49;
                                }
                                $commit43 = true;
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
                                               this.$getStore()).
                                             $getProxy()).
                                              fabric$metrics$DerivedMetric$Refresher$(
                                                t));
                                }
                                catch (final fabric.worker.
                                         RetryException $e45) {
                                    $commit43 = false;
                                    continue $label42;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e45) {
                                    $commit43 = false;
                                    fabric.common.TransactionID $currentTid46 =
                                      $tm47.getCurrentTid();
                                    if ($e45.tid.isDescendantOf($currentTid46))
                                        continue $label42;
                                    if ($currentTid46.parent != null) {
                                        $retry44 = false;
                                        throw $e45;
                                    }
                                    throw new InternalError(
                                            "Something is broken with " +
                                                "transaction management. Got a signal to restart a " +
                                                "different transaction than the one being managed.");
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e45) {
                                    $commit43 = false;
                                    if ($tm47.checkForStaleObjects()) continue;
                                    fabric.common.TransactionID $currentTid46 =
                                      $tm47.getCurrentTid();
                                    if ($e45.tid.isDescendantOf(
                                                   $currentTid46)) {
                                        $retry44 = true;
                                    }
                                    else if ($currentTid46.parent != null) {
                                        $retry44 = false;
                                        throw $e45;
                                    }
                                    else {
                                        throw new InternalError(
                                                "Something is broken with transaction " +
                                                    "management. Got a signal for a lock conflict in a different " +
                                                    "transaction than the one being managed.");
                                    }
                                }
                                catch (final Throwable $e45) {
                                    $commit43 = false;
                                    if ($tm47.checkForStaleObjects())
                                        continue $label42;
                                    $retry44 = false;
                                    throw new fabric.worker.AbortException(
                                            $e45);
                                }
                                finally {
                                    if ($commit43) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.
                                              getInstance().commitTransaction();
                                        }
                                        catch (final fabric.worker.
                                                 AbortException $e45) {
                                            $commit43 = false;
                                        }
                                        catch (final fabric.worker.
                                                 TransactionRestartingException $e45) {
                                            $commit43 = false;
                                            fabric.common.TransactionID
                                              $currentTid46 =
                                              $tm47.getCurrentTid();
                                            if ($currentTid46 != null) {
                                                if ($e45.tid.equals(
                                                               $currentTid46) ||
                                                      !$e45.tid.
                                                      isDescendantOf(
                                                        $currentTid46)) {
                                                    throw $e45;
                                                }
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                    if (!$commit43 && $retry44) {
                                        {
                                            c = c$var40;
                                            i = i$var41;
                                        }
                                        continue $label42;
                                    }
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
                                    {  }
                                    continue $label51;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -100, 93, -86, 48,
    -128, 30, -89, -6, 67, 55, -93, 67, 112, -7, -114, -57, 101, 90, -9, 48,
    -62, 24, -24, 50, 117, -55, -17, 87, 119, -90, -5, -32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520201027000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
