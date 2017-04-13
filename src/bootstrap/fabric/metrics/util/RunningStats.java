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
                        fabric.worker.transaction.TransactionManager $tm47 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff48 = 1;
                        boolean $doBackoff49 = true;
                        $label43: for (boolean $commit44 = false; !$commit44;
                                       ) {
                            if ($doBackoff49) {
                                if ($backoff48 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff48);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e45) {
                                            
                                        }
                                    }
                                }
                                if ($backoff48 < 5000) $backoff48 *= 2;
                            }
                            $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                            $commit44 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.01);
                            }
                            catch (final fabric.worker.RetryException $e45) {
                                $commit44 = false;
                                continue $label43;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e45) {
                                $commit44 = false;
                                fabric.common.TransactionID $currentTid46 =
                                  $tm47.getCurrentTid();
                                if ($e45.tid.isDescendantOf($currentTid46))
                                    continue $label43;
                                if ($currentTid46.parent != null) throw $e45;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e45) {
                                $commit44 = false;
                                if ($tm47.checkForStaleObjects())
                                    continue $label43;
                                throw new fabric.worker.AbortException($e45);
                            }
                            finally {
                                if ($commit44) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e45) {
                                        $commit44 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e45) {
                                        $commit44 = false;
                                        fabric.common.TransactionID
                                          $currentTid46 = $tm47.getCurrentTid();
                                        if ($currentTid46 != null) {
                                            if ($e45.tid.equals(
                                                           $currentTid46) ||
                                                  !$e45.tid.isDescendantOf(
                                                              $currentTid46)) {
                                                throw $e45;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit44) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -110, 115, 23, 62,
    -107, 114, -20, -93, -101, -93, -109, -79, 0, 114, -121, -53, -116, -9, 109,
    -71, 5, -2, 66, 16, 38, 47, 125, 83, 44, -26, 22, 43 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492109732000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
