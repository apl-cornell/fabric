package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Utility class for tracking the sample mean and sample standard deviation
 * of a value that is updated periodically.
 *
 * This was originally based on a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 *
 * That post assumed a stable distribution. This has since been modified to
 * use EWMA.
 */
public interface RunningStats extends fabric.lang.Object {
    public double get$mean();
    
    public double set$mean(double val);
    
    public double postInc$mean();
    
    public double postDec$mean();
    
    public double get$var();
    
    public double set$var(double val);
    
    public double postInc$var();
    
    public double postDec$var();
    
    public double get$meanPrev();
    
    public double set$meanPrev(double val);
    
    public double postInc$meanPrev();
    
    public double postDec$meanPrev();
    
    public double get$varPrev();
    
    public double set$varPrev(double val);
    
    public double postInc$varPrev();
    
    public double postDec$varPrev();
    
    public double get$startValue();
    
    public double set$startValue(double val);
    
    public double postInc$startValue();
    
    public double postDec$startValue();
    
    /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
    public fabric.metrics.util.RunningStats fabric$metrics$util$RunningStats$(
      double startValue);
    
    /**
   * Reset estimation to just the startValue.
   */
    public void reset();
    
    /**
   * @return the current estimated mean.
   */
    public double getMean();
    
    /**
   * @return the current estimated variance.
   */
    public double getVar();
    
    /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
    public void update(double val);
    
    /** @return the ALPHA learning parameter for these statistics. */
    public double getAlpha();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.RunningStats {
        public double get$mean() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).get$mean(
                                                                        );
        }
        
        public double set$mean(double val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).set$mean(
                                                                        val);
        }
        
        public double postInc$mean() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postInc$mean();
        }
        
        public double postDec$mean() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postDec$mean();
        }
        
        public double get$var() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).get$var();
        }
        
        public double set$var(double val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).set$var(
                                                                        val);
        }
        
        public double postInc$var() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postInc$var();
        }
        
        public double postDec$var() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postDec$var();
        }
        
        public double get$meanPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              get$meanPrev();
        }
        
        public double set$meanPrev(double val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              set$meanPrev(val);
        }
        
        public double postInc$meanPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postInc$meanPrev();
        }
        
        public double postDec$meanPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postDec$meanPrev();
        }
        
        public double get$varPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              get$varPrev();
        }
        
        public double set$varPrev(double val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              set$varPrev(val);
        }
        
        public double postInc$varPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postInc$varPrev();
        }
        
        public double postDec$varPrev() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postDec$varPrev();
        }
        
        public double get$startValue() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              get$startValue();
        }
        
        public double set$startValue(double val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              set$startValue(val);
        }
        
        public double postInc$startValue() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postInc$startValue();
        }
        
        public double postDec$startValue() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).
              postDec$startValue();
        }
        
        public fabric.metrics.util.RunningStats
          fabric$metrics$util$RunningStats$(double arg1) {
            return ((fabric.metrics.util.RunningStats) fetch()).
              fabric$metrics$util$RunningStats$(arg1);
        }
        
        public void reset() {
            ((fabric.metrics.util.RunningStats) fetch()).reset();
        }
        
        public double getMean() {
            return ((fabric.metrics.util.RunningStats) fetch()).getMean();
        }
        
        public double getVar() {
            return ((fabric.metrics.util.RunningStats) fetch()).getVar();
        }
        
        public void update(double arg1) {
            ((fabric.metrics.util.RunningStats) fetch()).update(arg1);
        }
        
        public double getAlpha() {
            return ((fabric.metrics.util.RunningStats) fetch()).getAlpha();
        }
        
        public _Proxy(RunningStats._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.RunningStats {
        public double get$mean() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.mean;
        }
        
        public double set$mean(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.mean = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$mean() {
            double tmp = this.get$mean();
            this.set$mean((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$mean() {
            double tmp = this.get$mean();
            this.set$mean((double) (tmp - 1));
            return tmp;
        }
        
        private double mean = 0.0;
        
        public double get$var() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.var;
        }
        
        public double set$var(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.var = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$var() {
            double tmp = this.get$var();
            this.set$var((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$var() {
            double tmp = this.get$var();
            this.set$var((double) (tmp - 1));
            return tmp;
        }
        
        private double var = 0.0;
        
        public double get$meanPrev() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.meanPrev;
        }
        
        public double set$meanPrev(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.meanPrev = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$meanPrev() {
            double tmp = this.get$meanPrev();
            this.set$meanPrev((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$meanPrev() {
            double tmp = this.get$meanPrev();
            this.set$meanPrev((double) (tmp - 1));
            return tmp;
        }
        
        private double meanPrev = 0.0;
        
        public double get$varPrev() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.varPrev;
        }
        
        public double set$varPrev(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.varPrev = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$varPrev() {
            double tmp = this.get$varPrev();
            this.set$varPrev((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$varPrev() {
            double tmp = this.get$varPrev();
            this.set$varPrev((double) (tmp - 1));
            return tmp;
        }
        
        private double varPrev = 0.0;
        
        public double get$startValue() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.startValue;
        }
        
        public double set$startValue(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.startValue = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$startValue() {
            double tmp = this.get$startValue();
            this.set$startValue((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$startValue() {
            double tmp = this.get$startValue();
            this.set$startValue((double) (tmp - 1));
            return tmp;
        }
        
        private double startValue;
        
        /**
   * @param startValue
   *        initial guess for the mean of the value we're keeping
   *        statistics on.
   */
        public fabric.metrics.util.RunningStats
          fabric$metrics$util$RunningStats$(double startValue) {
            this.set$startValue((double) startValue);
            fabric$lang$Object$();
            this.set$meanPrev((double) startValue);
            this.set$mean((double) startValue);
            return (fabric.metrics.util.RunningStats) this.$getProxy();
        }
        
        /**
   * Reset estimation to just the startValue.
   */
        public void reset() {
            this.set$meanPrev((double) this.get$startValue());
            this.set$mean((double) this.get$startValue());
        }
        
        /**
   * @return the current estimated mean.
   */
        public double getMean() { return this.get$mean(); }
        
        /**
   * @return the current estimated variance.
   */
        public double getVar() { return this.get$var(); }
        
        /**
   * Update with a new observation.
   *
   * @param val
   *        the newly observed value.
   */
        public void update(double val) {
            this.
              set$mean(
                (double)
                  (this.get$meanPrev() +
                     fabric.metrics.util.RunningStats._Static._Proxy.$instance.
                     get$ALPHA() * (val - this.get$meanPrev())));
            this.
              set$var(
                (double)
                  ((1.0 -
                      fabric.metrics.util.RunningStats._Static._Proxy.$instance.
                      get$ALPHA()) *
                     this.
                     get$varPrev() +
                     fabric.metrics.util.RunningStats._Static._Proxy.$instance.
                     get$ALPHA() *
                     (val - this.get$meanPrev()) *
                     (val - this.get$mean())));
            this.set$meanPrev((double) this.get$mean());
            this.set$varPrev((double) this.get$var());
        }
        
        /** @return the ALPHA learning parameter for these statistics. */
        public double getAlpha() {
            return fabric.metrics.util.RunningStats._Static._Proxy.$instance.
              get$ALPHA();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.RunningStats._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.mean);
            out.writeDouble(this.var);
            out.writeDouble(this.meanPrev);
            out.writeDouble(this.varPrev);
            out.writeDouble(this.startValue);
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
            this.mean = in.readDouble();
            this.var = in.readDouble();
            this.meanPrev = in.readDouble();
            this.varPrev = in.readDouble();
            this.startValue = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.RunningStats._Impl src =
              (fabric.metrics.util.RunningStats._Impl) other;
            this.mean = src.mean;
            this.var = src.var;
            this.meanPrev = src.meanPrev;
            this.varPrev = src.varPrev;
            this.startValue = src.startValue;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$ALPHA();
        
        public double set$ALPHA(double val);
        
        public double postInc$ALPHA();
        
        public double postDec$ALPHA();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.RunningStats._Static {
            public double get$ALPHA() {
                return ((fabric.metrics.util.RunningStats._Static._Impl)
                          fetch()).get$ALPHA();
            }
            
            public double set$ALPHA(double val) {
                return ((fabric.metrics.util.RunningStats._Static._Impl)
                          fetch()).set$ALPHA(val);
            }
            
            public double postInc$ALPHA() {
                return ((fabric.metrics.util.RunningStats._Static._Impl)
                          fetch()).postInc$ALPHA();
            }
            
            public double postDec$ALPHA() {
                return ((fabric.metrics.util.RunningStats._Static._Impl)
                          fetch()).postDec$ALPHA();
            }
            
            public _Proxy(fabric.metrics.util.RunningStats._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.RunningStats._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  RunningStats.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.RunningStats._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.RunningStats._Static._Impl.class);
                $instance = (fabric.metrics.util.RunningStats._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.RunningStats._Static {
            public double get$ALPHA() { return this.ALPHA; }
            
            public double set$ALPHA(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.ALPHA = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$ALPHA() {
                double tmp = this.get$ALPHA();
                this.set$ALPHA((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$ALPHA() {
                double tmp = this.get$ALPHA();
                this.set$ALPHA((double) (tmp - 1));
                return tmp;
            }
            
            public double ALPHA;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.ALPHA);
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
                this.ALPHA = in.readDouble();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.RunningStats._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm40 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff41 = 1;
                        boolean $doBackoff42 = true;
                        $label36: for (boolean $commit37 = false; !$commit37;
                                       ) {
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
                                if ($backoff41 < 5000) $backoff41 *= 2;
                            }
                            $doBackoff42 = $backoff41 <= 32 || !$doBackoff42;
                            $commit37 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.01);
                            }
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
                                if ($tm40.checkForStaleObjects())
                                    continue $label36;
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
                                        fabric.common.TransactionID
                                          $currentTid39 = $tm40.getCurrentTid();
                                        if ($currentTid39 != null) {
                                            if ($e38.tid.equals(
                                                           $currentTid39) ||
                                                  !$e38.tid.isDescendantOf(
                                                              $currentTid39)) {
                                                throw $e38;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit37) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -112, -116, 124, -51,
    -69, 40, -102, -78, -42, 43, 37, -73, -60, -80, -38, 49, 126, -81, -39,
    -110, 98, 96, 102, 35, -124, -59, -69, 39, -18, -126, 124, -9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
