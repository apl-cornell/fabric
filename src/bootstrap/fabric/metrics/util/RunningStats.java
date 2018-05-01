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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
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
                        fabric.worker.transaction.TransactionManager $tm710 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled713 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff711 = 1;
                        boolean $doBackoff712 = true;
                        boolean $retry707 = true;
                        $label705: for (boolean $commit706 = false; !$commit706;
                                        ) {
                            if ($backoffEnabled713) {
                                if ($doBackoff712) {
                                    if ($backoff711 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff711);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e708) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff711 < 5000) $backoff711 *= 2;
                                }
                                $doBackoff712 = $backoff711 <= 32 ||
                                                  !$doBackoff712;
                            }
                            $commit706 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.RunningStats._Static._Proxy.
                                  $instance.
                                  set$ALPHA((double) 0.001);
                            }
                            catch (final fabric.worker.RetryException $e708) {
                                $commit706 = false;
                                continue $label705;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e708) {
                                $commit706 = false;
                                fabric.common.TransactionID $currentTid709 =
                                  $tm710.getCurrentTid();
                                if ($e708.tid.isDescendantOf($currentTid709))
                                    continue $label705;
                                if ($currentTid709.parent != null) {
                                    $retry707 = false;
                                    throw $e708;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e708) {
                                $commit706 = false;
                                if ($tm710.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid709 =
                                  $tm710.getCurrentTid();
                                if ($e708.tid.isDescendantOf($currentTid709)) {
                                    $retry707 = true;
                                }
                                else if ($currentTid709.parent != null) {
                                    $retry707 = false;
                                    throw $e708;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e708) {
                                $commit706 = false;
                                if ($tm710.checkForStaleObjects())
                                    continue $label705;
                                $retry707 = false;
                                throw new fabric.worker.AbortException($e708);
                            }
                            finally {
                                if ($commit706) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e708) {
                                        $commit706 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e708) {
                                        $commit706 = false;
                                        fabric.common.TransactionID
                                          $currentTid709 =
                                          $tm710.getCurrentTid();
                                        if ($currentTid709 != null) {
                                            if ($e708.tid.equals(
                                                            $currentTid709) ||
                                                  !$e708.tid.
                                                  isDescendantOf(
                                                    $currentTid709)) {
                                                throw $e708;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit706 && $retry707) {
                                    {  }
                                    continue $label705;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -14, 89, -12, -118,
    -57, 4, -22, -39, -60, 90, 66, 75, 36, -113, -87, 6, 87, -16, 121, -87, -61,
    69, 50, 12, -70, -58, -109, -125, -85, -65, -69, 63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
