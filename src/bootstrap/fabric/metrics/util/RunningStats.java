package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Utility class for tracking the sample mean and sample standard deviation of a
 * value that is updated periodically.
 *
 * This was originally based on a post on John D. Cook's blog here:
 * https://www.johndcook.com/blog/standard_deviation/
 *
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
                        fabric.worker.transaction.TransactionManager $tm196 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff197 = 1;
                        boolean $doBackoff198 = true;
                        $label192: for (boolean $commit193 = false; !$commit193;
                                        ) {
                            if ($doBackoff198) {
                                if ($backoff197 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff197);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e194) {
                                            
                                        }
                                    }
                                }
                                if ($backoff197 < 5000) $backoff197 *= 2;
                            }
                            $doBackoff198 = $backoff197 <= 32 || !$doBackoff198;
                            $commit193 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.01);
                            }
                            catch (final fabric.worker.RetryException $e194) {
                                $commit193 = false;
                                continue $label192;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e194) {
                                $commit193 = false;
                                fabric.common.TransactionID $currentTid195 =
                                  $tm196.getCurrentTid();
                                if ($e194.tid.isDescendantOf($currentTid195))
                                    continue $label192;
                                if ($currentTid195.parent != null) throw $e194;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e194) {
                                $commit193 = false;
                                if ($tm196.checkForStaleObjects())
                                    continue $label192;
                                throw new fabric.worker.AbortException($e194);
                            }
                            finally {
                                if ($commit193) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e194) {
                                        $commit193 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e194) {
                                        $commit193 = false;
                                        fabric.common.TransactionID
                                          $currentTid195 =
                                          $tm196.getCurrentTid();
                                        if ($currentTid195 != null) {
                                            if ($e194.tid.equals(
                                                            $currentTid195) ||
                                                  !$e194.tid.
                                                  isDescendantOf(
                                                    $currentTid195)) {
                                                throw $e194;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit193) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -27, 43, -39, -31, -76,
    -59, -24, 87, -118, 102, -42, 14, 63, -79, -89, 84, 72, -92, 127, 77, 6,
    106, 110, -54, 19, 6, -25, -58, -48, 30, 9, -71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
