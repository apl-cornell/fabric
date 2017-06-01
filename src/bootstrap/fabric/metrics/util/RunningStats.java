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
                        fabric.worker.transaction.TransactionManager $tm189 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff190 = 1;
                        boolean $doBackoff191 = true;
                        $label185: for (boolean $commit186 = false; !$commit186;
                                        ) {
                            if ($doBackoff191) {
                                if ($backoff190 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff190);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e187) {
                                            
                                        }
                                    }
                                }
                                if ($backoff190 < 5000) $backoff190 *= 2;
                            }
                            $doBackoff191 = $backoff190 <= 32 || !$doBackoff191;
                            $commit186 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e187) {
                                $commit186 = false;
                                continue $label185;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e187) {
                                $commit186 = false;
                                fabric.common.TransactionID $currentTid188 =
                                  $tm189.getCurrentTid();
                                if ($e187.tid.isDescendantOf($currentTid188))
                                    continue $label185;
                                if ($currentTid188.parent != null) throw $e187;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e187) {
                                $commit186 = false;
                                if ($tm189.checkForStaleObjects())
                                    continue $label185;
                                throw new fabric.worker.AbortException($e187);
                            }
                            finally {
                                if ($commit186) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e187) {
                                        $commit186 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e187) {
                                        $commit186 = false;
                                        fabric.common.TransactionID
                                          $currentTid188 =
                                          $tm189.getCurrentTid();
                                        if ($currentTid188 != null) {
                                            if ($e187.tid.equals(
                                                            $currentTid188) ||
                                                  !$e187.tid.
                                                  isDescendantOf(
                                                    $currentTid188)) {
                                                throw $e187;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit186) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 68, 107, 21, -70, 65,
    -76, 99, 82, 122, -91, -104, 72, -75, -46, -10, 50, 26, 30, 68, 82, 124, 80,
    92, -32, 18, 77, 4, 99, 119, -10, -40, -44 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496244158000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
