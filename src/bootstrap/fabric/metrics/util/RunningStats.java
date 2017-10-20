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
    public boolean get$stamp();
    
    public boolean set$stamp(boolean val);
    
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
        public boolean get$stamp() {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).get$stamp(
                                                                        );
        }
        
        public boolean set$stamp(boolean val) {
            return ((fabric.metrics.util.RunningStats._Impl) fetch()).set$stamp(
                                                                        val);
        }
        
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
        public boolean get$stamp() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.stamp;
        }
        
        public boolean set$stamp(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.stamp = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean stamp = true;
        
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
        public double getMean() {
            this.set$stamp(true);
            return this.get$mean();
        }
        
        /**
   * @return the current estimated variance.
   */
        public double getVar() {
            this.set$stamp(true);
            return this.get$var();
        }
        
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
            out.writeBoolean(this.stamp);
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
            this.stamp = in.readBoolean();
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
            this.stamp = src.stamp;
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
                        fabric.worker.transaction.TransactionManager $tm554 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled557 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff555 = 1;
                        boolean $doBackoff556 = true;
                        $label550: for (boolean $commit551 = false; !$commit551;
                                        ) {
                            if ($backoffEnabled557) {
                                if ($doBackoff556) {
                                    if ($backoff555 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff555);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e552) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff555 < 5000) $backoff555 *= 2;
                                }
                                $doBackoff556 = $backoff555 <= 32 ||
                                                  !$doBackoff556;
                            }
                            $commit551 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e552) {
                                $commit551 = false;
                                continue $label550;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e552) {
                                $commit551 = false;
                                fabric.common.TransactionID $currentTid553 =
                                  $tm554.getCurrentTid();
                                if ($e552.tid.isDescendantOf($currentTid553))
                                    continue $label550;
                                if ($currentTid553.parent != null) throw $e552;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e552) {
                                $commit551 = false;
                                if ($tm554.checkForStaleObjects())
                                    continue $label550;
                                throw new fabric.worker.AbortException($e552);
                            }
                            finally {
                                if ($commit551) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e552) {
                                        $commit551 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e552) {
                                        $commit551 = false;
                                        fabric.common.TransactionID
                                          $currentTid553 =
                                          $tm554.getCurrentTid();
                                        if ($currentTid553 != null) {
                                            if ($e552.tid.equals(
                                                            $currentTid553) ||
                                                  !$e552.tid.
                                                  isDescendantOf(
                                                    $currentTid553)) {
                                                throw $e552;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit551) {
                                    {  }
                                    continue $label550;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 85, 97, -3, 19, -71,
    40, -103, 63, -11, 18, 25, -27, -26, 90, -48, 92, -100, -107, 28, -12, 122,
    -108, -68, 125, 43, -51, -61, 81, 125, -115, -5, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507317673000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
