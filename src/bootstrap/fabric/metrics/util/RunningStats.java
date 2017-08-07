package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Utility class for tracking the sample mean and sample standard deviation of a
 * value that is updated periodically.
 * <p>
 * This was originally based on a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 * <p>
 * That post assumed a stable distribution. This has since been modified to use
 * EWMA.
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
        
        public double get$startValue() { return this.startValue; }
        
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
                        fabric.worker.transaction.TransactionManager $tm164 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff165 = 1;
                        boolean $doBackoff166 = true;
                        $label160: for (boolean $commit161 = false; !$commit161;
                                        ) {
                            if ($doBackoff166) {
                                if ($backoff165 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff165);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e162) {
                                            
                                        }
                                    }
                                }
                                if ($backoff165 < 5000) $backoff165 *= 2;
                            }
                            $doBackoff166 = $backoff165 <= 32 || !$doBackoff166;
                            $commit161 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e162) {
                                $commit161 = false;
                                continue $label160;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e162) {
                                $commit161 = false;
                                fabric.common.TransactionID $currentTid163 =
                                  $tm164.getCurrentTid();
                                if ($e162.tid.isDescendantOf($currentTid163))
                                    continue $label160;
                                if ($currentTid163.parent != null) throw $e162;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e162) {
                                $commit161 = false;
                                if ($tm164.checkForStaleObjects())
                                    continue $label160;
                                throw new fabric.worker.AbortException($e162);
                            }
                            finally {
                                if ($commit161) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e162) {
                                        $commit161 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e162) {
                                        $commit161 = false;
                                        fabric.common.TransactionID
                                          $currentTid163 =
                                          $tm164.getCurrentTid();
                                        if ($currentTid163 != null) {
                                            if ($e162.tid.equals(
                                                            $currentTid163) ||
                                                  !$e162.tid.
                                                  isDescendantOf(
                                                    $currentTid163)) {
                                                throw $e162;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit161) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 111, -88, -37, -54, 65,
    44, -48, 60, -67, -48, 65, 34, -59, -120, 19, 71, -122, -6, 21, -5, -113,
    -81, -30, -102, -106, -114, -56, 34, 113, 38, 44, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1501602696000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
