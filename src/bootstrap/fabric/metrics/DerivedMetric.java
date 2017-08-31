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
    public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(fabric.lang.arrays.ObjectArray terms);
    
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
                this.get$t().refreshWeakEstimates();
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
        
        public static final byte[] $classHash = new byte[] { -59, -87, -20, 74,
        -16, 41, 4, -11, -37, 101, -18, -37, -125, 100, 62, 48, -16, 110, -36,
        104, 35, -22, 35, -53, -11, 106, -15, -7, 7, -57, -57, 90 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1504028847000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public void refreshWeakEstimates();
    
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
        
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).
              fabric$metrics$DerivedMetric$(arg1);
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
            fabric$metrics$Metric$();
            return (fabric.metrics.DerivedMetric) this.$getProxy();
        }
        
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
              log(java.util.logging.Level.FINE,
                  "CHECKING FOR UPDATE ON DERIVED METRIC");
            double newValue = computeValue(false);
            if (newValue != (double) this.get$lastStats().get(0)) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINE, "UPDATE ON DERIVED METRIC");
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
                    fabric.worker.transaction.TransactionManager $tm26 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff27 = 1;
                    boolean $doBackoff28 = true;
                    $label22: for (boolean $commit23 = false; !$commit23; ) {
                        if ($doBackoff28) {
                            if ($backoff27 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff27);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e24) {
                                        
                                    }
                                }
                            }
                            if ($backoff27 < 5000) $backoff27 *= 1;
                        }
                        $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                        $commit23 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(0); }
                        catch (final fabric.worker.RetryException $e24) {
                            $commit23 = false;
                            continue $label22;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e24) {
                            $commit23 = false;
                            fabric.common.TransactionID $currentTid25 =
                              $tm26.getCurrentTid();
                            if ($e24.tid.isDescendantOf($currentTid25))
                                continue $label22;
                            if ($currentTid25.parent != null) throw $e24;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e24) {
                            $commit23 = false;
                            if ($tm26.checkForStaleObjects()) continue $label22;
                            throw new fabric.worker.AbortException($e24);
                        }
                        finally {
                            if ($commit23) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e24) {
                                    $commit23 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e24) {
                                    $commit23 = false;
                                    fabric.common.TransactionID $currentTid25 =
                                      $tm26.getCurrentTid();
                                    if ($currentTid25 != null) {
                                        if ($e24.tid.equals($currentTid25) ||
                                              !$e24.tid.isDescendantOf(
                                                          $currentTid25)) {
                                            throw $e24;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit23) {
                                {  }
                                continue $label22;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.get$lastStats().set(0, computeValue(false));
            }
            return (double) this.get$lastStats().get(0);
        }
        
        public double velocity(boolean useWeakCache) {
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm33 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff34 = 1;
                    boolean $doBackoff35 = true;
                    $label29: for (boolean $commit30 = false; !$commit30; ) {
                        if ($doBackoff35) {
                            if ($backoff34 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff34);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e31) {
                                        
                                    }
                                }
                            }
                            if ($backoff34 < 5000) $backoff34 *= 1;
                        }
                        $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                        $commit30 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(1); }
                        catch (final fabric.worker.RetryException $e31) {
                            $commit30 = false;
                            continue $label29;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e31) {
                            $commit30 = false;
                            fabric.common.TransactionID $currentTid32 =
                              $tm33.getCurrentTid();
                            if ($e31.tid.isDescendantOf($currentTid32))
                                continue $label29;
                            if ($currentTid32.parent != null) throw $e31;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e31) {
                            $commit30 = false;
                            if ($tm33.checkForStaleObjects()) continue $label29;
                            throw new fabric.worker.AbortException($e31);
                        }
                        finally {
                            if ($commit30) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e31) {
                                    $commit30 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e31) {
                                    $commit30 = false;
                                    fabric.common.TransactionID $currentTid32 =
                                      $tm33.getCurrentTid();
                                    if ($currentTid32 != null) {
                                        if ($e31.tid.equals($currentTid32) ||
                                              !$e31.tid.isDescendantOf(
                                                          $currentTid32)) {
                                            throw $e31;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit30) {
                                {  }
                                continue $label29;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.get$lastStats().set(1, computeVelocity(false));
            }
            return (double) this.get$lastStats().get(1);
        }
        
        public double noise(boolean useWeakCache) {
            if (useWeakCache) {
                {
                    fabric.worker.transaction.TransactionManager $tm40 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff41 = 1;
                    boolean $doBackoff42 = true;
                    $label36: for (boolean $commit37 = false; !$commit37; ) {
                        if ($doBackoff42) {
                            if ($backoff41 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff41);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e38) {
                                        
                                    }
                                }
                            }
                            if ($backoff41 < 5000) $backoff41 *= 1;
                        }
                        $doBackoff42 = $backoff41 <= 32 || !$doBackoff42;
                        $commit37 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { return (double) this.get$weakStats().get(2); }
                        catch (final fabric.worker.RetryException $e38) {
                            $commit37 = false;
                            continue $label36;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e38) {
                            $commit37 = false;
                            fabric.common.TransactionID $currentTid39 =
                              $tm40.getCurrentTid();
                            if ($e38.tid.isDescendantOf($currentTid39))
                                continue $label36;
                            if ($currentTid39.parent != null) throw $e38;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e38) {
                            $commit37 = false;
                            if ($tm40.checkForStaleObjects()) continue $label36;
                            throw new fabric.worker.AbortException($e38);
                        }
                        finally {
                            if ($commit37) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e38) {
                                    $commit37 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e38) {
                                    $commit37 = false;
                                    fabric.common.TransactionID $currentTid39 =
                                      $tm40.getCurrentTid();
                                    if ($currentTid39 != null) {
                                        if ($e38.tid.equals($currentTid39) ||
                                              !$e38.tid.isDescendantOf(
                                                          $currentTid39)) {
                                            throw $e38;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit37) {
                                {  }
                                continue $label36;
                            }
                        }
                    }
                }
            }
            else if (isObserved()) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  resolveObservations();
            } else {
                this.get$lastStats().set(2, computeNoise(false));
            }
            return (double) this.get$lastStats().get(2);
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
                    {
                        java.util.concurrent.Callable c$var43 = c;
                        int i$var44 = i;
                        fabric.worker.transaction.TransactionManager $tm49 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff50 = 1;
                        boolean $doBackoff51 = true;
                        $label45: for (boolean $commit46 = false; !$commit46;
                                       ) {
                            if ($doBackoff51) {
                                if ($backoff50 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff50);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e47) {
                                            
                                        }
                                    }
                                }
                                if ($backoff50 < 5000) $backoff50 *= 1;
                            }
                            $doBackoff51 = $backoff50 <= 32 || !$doBackoff51;
                            $commit46 = true;
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
                            catch (final fabric.worker.RetryException $e47) {
                                $commit46 = false;
                                continue $label45;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e47) {
                                $commit46 = false;
                                fabric.common.TransactionID $currentTid48 =
                                  $tm49.getCurrentTid();
                                if ($e47.tid.isDescendantOf($currentTid48))
                                    continue $label45;
                                if ($currentTid48.parent != null) throw $e47;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e47) {
                                $commit46 = false;
                                if ($tm49.checkForStaleObjects())
                                    continue $label45;
                                throw new fabric.worker.AbortException($e47);
                            }
                            finally {
                                if ($commit46) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e47) {
                                        $commit46 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e47) {
                                        $commit46 = false;
                                        fabric.common.TransactionID
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
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit46) {
                                    {
                                        c = c$var43;
                                        i = i$var44;
                                    }
                                    continue $label45;
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
            super.refreshWeakEstimates();
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.lastStats = src.lastStats;
            this.terms = src.terms;
            this.leafMetrics = src.leafMetrics;
            this.singleStore = src.singleStore;
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
                        int $backoff57 = 1;
                        boolean $doBackoff58 = true;
                        $label52: for (boolean $commit53 = false; !$commit53;
                                       ) {
                            if ($doBackoff58) {
                                if ($backoff57 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff57);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e54) {
                                            
                                        }
                                    }
                                }
                                if ($backoff57 < 5000) $backoff57 *= 1;
                            }
                            $doBackoff58 = $backoff57 <= 32 || !$doBackoff58;
                            $commit53 = true;
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
                                $commit53 = false;
                                continue $label52;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e54) {
                                $commit53 = false;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid.isDescendantOf($currentTid55))
                                    continue $label52;
                                if ($currentTid55.parent != null) throw $e54;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e54) {
                                $commit53 = false;
                                if ($tm56.checkForStaleObjects())
                                    continue $label52;
                                throw new fabric.worker.AbortException($e54);
                            }
                            finally {
                                if ($commit53) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e54) {
                                        $commit53 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e54) {
                                        $commit53 = false;
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
                                if (!$commit53) {
                                    {  }
                                    continue $label52;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -29, -29, -58, -12,
    123, -8, -56, 52, -97, 11, -67, 88, -25, -110, 28, 109, 36, -20, -46, -20,
    -6, 25, -117, 125, -15, 4, -12, 30, 41, -81, 113, -31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ZC2wUxxmeO9tnG2xsDOZh/MIcUPO4EwRVSpzQBvNyOWrXhhRME3dvd85e2Ns9786ZI8ERJaqgjUSjhJBQNaRSobTEhQolQmprlEoJJQlKFVI1SdU2pBQlBGiDEh6qSNL/n517r7c+qZZn/t2d/5/55p//tXsj10iJZZKWiBRWtQDbEaNWYLUU7gh1SaZFlXZNsqwN8LRPnljcceCjo0qjl3hDpEKWdENXZUnr0y1GJoW2SkNSUKcsuLG7o20LKZdRcK1kDTDi3bIiYZLmmKHt6NcMJhbJm//phcH9zzxUfbKIVPWSKlXvYRJT5XZDZzTBeklFlEbD1LTuVxSq9JLJOqVKDzVVSVMfBkZD7yU1ltqvSyxuUqubWoY2hIw1VjxGTb5m8iHCNwC2GZeZYQL8aht+nKlaMKRarC1EfBGVaoo1SB4lxSFSEtGkfmCcFkruIshnDK7G58A+QQWYZkSSaVKkeJuqK4w05UqkduxfBwwgWhqlbMBILVWsS/CA1NiQNEnvD/YwU9X7gbXEiMMqjNSNOSkwlcUkeZvUT/sYmZHL12UPAVc5VwuKMFKby8ZngjOryzmzjNO69s179z2ir9W9xAOYFSpriL8MhBpzhLpphJpUl6ktWLEgdECaNrrXSwgw1+Yw2zyndl7/+qLGl8/aPLMceDrDW6nM+uTD4Ulv1be33l2EMMpihqWiKWTtnJ9qlxhpS8TA2qelZsTBQHLw5e4zm3cdo1e8ZEIH8cmGFo+CVU2WjWhM1ai5hurUlBhVOkg51ZV2Pt5BSuE6pOrUftoZiViUdZBijT/yGfweVBSBKVBFpXCt6hEjeR2T2AC/TsQIIdXQiAf+mwjxK3A9n5CS+xhZFxwwojQY1uJ0O5h3EBqVTHkgCH5rqnLQMuWgGdeZCkziEVgRECu4EpwEjH49vw0AjNj/d7oEoq/e7vGAYptkQ6FhyYJTEhazoksDp1hraAo1+2Rt32gHmTJ6kFtNOVq6BdbK9eKBk67PjRGZsvvjK1ZdP973hm1xKCvUxki9jTEgMAayMAKsCvSlAESnAESnEU8i0H6o4wVuMj6L+1ZqpgqY6Z6YJrGIYUYTxOPh25rK5bmtwElvgwgCQaKitefBb3x3b0sRGGlsezGeG7D6c10mHWg64EoCP+iTq/Z8dPPEgWEj7TyM+PN8Ol8SfbIlV0emIVMFYl56+gXN0kt9o8N+L8aTcgh1TAJjhLjRmLtGlm+2JeMcaqMkRCaiDiQNh5LBaQIbMI3t6Sf87CdhV2ObASorByAPkff1xJ57983Ld/HkkYymVRlht4eytgwPxsmquK9OTut+g0kp8P3t2a6nnr62ZwtXPHDMcVrQj307eK4ELmuY3z87+N77fz/8J2/6sBjxxeJhTZUTfC+Tv4Q/D7QvsKEb4gOkEIzbRQhoTsWAGK48L40NooEGEQmgW/6NetRQ1IgqhTWKlnKnau6Sl67uq7aPW4MntvJMsuh/T5B+PnMF2fXGQ7ca+TQeGbNRWn9pNjvETUnPfL9pSjsQR+J75xsO/kF6DiwfApSlPkx5zCFcH4Qf4FKui8W8X5Iztgy7Fltb9fy5z8oP96sxb6ZtsTc48pO69uVXbI9P2SLOMdvB4x+QMtxk6bHoDW+L71UvKe0l1TxlSzp7QIKoBWbQC0nXahcPQ6Qyazw7gdrZoi3la/W5fpCxbK4XpCMNXCM3Xk+wDd82HFBEDSppLrRWCNcfC/oOjk6JYT814SH84h4uMof387Br5Yr0MlIeMw0GKCkUDeVqNBpnePp8nYXwBDIvw4rIApU35BRrEOT4GdvJ882jt2eO+i/ftpNnbgrPYPxk5P0r5ysbjvMwUYxRm28tt/bJL22yKhaOsILDTDiYQ5epRsGjh0T2p3v3//DLwL79tivYJdKcvColU8Yuk/gqlalVZrutwiVWf3hi+Le/GN5ja6EmO+Gv0uPRX/3583OBZy+85pBOfIoBgYGmnMAjojvef9XGAMeZc4sX68c4XbxcwMCuVF3Skkfq06jezwY480qxMyRrGCkCleNlu/N8Hj6fPQ9238KumwskUqC99tL8vpaJeIDeAFWcoVMpucGZYFyY9DQDivlEkt3OeKoRSJXYYbt86U3kqQWOI+/tYT23kLQrX7jScHf7tkv99nE05RxfLvcv14+8tmae/KSXFKV8Nq+QzhZqy/bUCSaF9wB9Q5a/NttaHqdmXSJhxGWMF3NhOGwZ1ZzUZ3Va/XYwsnVp50xuSNmxZDa0IARYXdA+h1iiuVjbcuy+ljS1EnDgqMUZ789eZ4aYnzqtkwRfm1Ne2XUVDtaN7SNmto+YSdTb3XwEuy4nwx4s1BMG8zwBbzdh9518E8b7PhsmF8ZOdjnkXS5ju7F7FDsVu62Oh8zr/LuggbTvZ4Lucjjkx8ZMGKUxKHKhFMk66YkalSKi7k0eX5U4Ph76IHHygxsTlB/aUgBzVtAXHUA97nKGe7LxWFBda7QHSjDqFOlKw4YBkHUnONMRzgJodwhpOino8w5wnnKGU8ThMKzx8UtCFq7yrs7OUF9PR++qlMU4Lr8Z2ueENFfYtOl1h+UPui2P3TNZS5da1BxS5VRo8KcrNShh5LgJBTkLrEpQOQ5a67GZebjgKBPuOaZMCsNbgySzRGoj/K9KvEveK+jijI1kVHSeJKrcVyqOrzOM2O3qrQ7zcMNYHwh4Dj68e/8hpfPIEq/wjLWgd2bEFmt0iGoZi1ba/pcCXI6AV0JrI6T0H4IezdR8+rzmYKekRL0oWiZEfi7oT3P3mnZcTzqMDPJZf+3i2Sexe4Hx+guU4he68We9bvrT2NI7QvMhDdA2wXWboAvG2JFjkFueyN7jRDFJq6DNY+8xcwu/cRn7HXYvMiz4oJbCpO/ossVDhqo4nVgztAghk5YJ2lDYiaFIvaDTxrebMy5jZ7H7PSOVA5KuaHRjTIFYyTmfcAK/ENoQhMAjgj5WGHgU2S3oznGZ2xN81j+67OAt7F6H7D2EbzR4s9oJeQDaXkjntwV9uzDkKHJe0HMFIH/XBflfsHsbghH4uSGrbMeY4FHtPyakdp2grYWBR5GvCNpSAPgPXMBfxO6voHbdUK2x1Y7WfgyyRKVNp90sDDmK3BD03+Oz9o9dxq5idwmsXbV60hl3TGuHDEBOQfW3W1ClMPAoIgv64LjUfoTP+qnLDm5g9y+oGCRFycwzp53wY1VwjpBZM2xad6sw/ChyU9BPCsB/xwX/F9jdYmSSSaPGEHXbAs8GGOreA7JV0F6XLZzKj/0oslnQrnHZj6fEZawUO5J8U0COQSfQjdD+CZnsR4IOFwYaRXYKao1L790c3SQX5NXYlUNaQuTIYDkZDEpdhlWvCfpqYQaDIq8Ienp82p7uMjYTuxqozPspC0HB3hPn74Sc9wdO8P38RwHSvELQ+QXB5yLzBB1foeBpdhnDSOuZxchUk0agrB/4NpW2rbKYGk1m2NMJiERZVRF+kpzl8OOA+KlKbn+FHr60blHtGD8MzMj78VDIHT9UVTb90MZ37G9YyZ+hykOkLBLXtMxPdxnXvhgAV7neyu0PeTG+r/ngvdllL+PfupIvU565Nh+U2T6bD+8Wcl3WpTqLT1kXN/GHz5FPp9/2lW24wL87o/ovXjzz2SO3zy57fuLopg+frI/6r56/+p+Zjw9fL/6ssfXE4Af/BQrZ93GQHQAA";
}
