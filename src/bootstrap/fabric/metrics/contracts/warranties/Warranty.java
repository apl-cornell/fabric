package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * A Warranty (also known as a General Contract) is a time-limited assertion of
 * the form <code>f(...) = y</code> and is enforced by an associated metric
 * contract.
 */
public interface Warranty extends fabric.metrics.contracts.Contract {
    public fabric.metrics.contracts.warranties.WarrantyComp get$computation();
    
    public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
      fabric.metrics.contracts.warranties.WarrantyComp val);
    
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.MetricContract get$witness();
    
    public fabric.metrics.contracts.MetricContract set$witness(
      fabric.metrics.contracts.MetricContract val);
    
    /**
   * @param computation
   *        the computation being warrantied.
   */
    public fabric.metrics.contracts.warranties.Warranty
      fabric$metrics$contracts$warranties$Warranty$(
      fabric.metrics.contracts.warranties.WarrantyComp computation);
    
    public boolean refresh(boolean force);
    
    public long getExpiry();
    
    /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
    public fabric.lang.Object value();
    
    public java.lang.String toString();
    
    public fabric.util.Set getLeafSubjects();
    
    public void activate();
    
    public void deactivate();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$computation();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$computation(val);
        }
        
        public fabric.lang.Object get$value() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$value();
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$witness();
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$witness(val);
        }
        
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp arg1) {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              fabric$metrics$contracts$warranties$Warranty$(arg1);
        }
        
        public fabric.lang.Object value() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              value();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Warranty._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.computation;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.computation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp computation;
        
        public fabric.lang.Object get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.Object value;
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.witness;
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witness = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.MetricContract witness;
        
        /**
   * @param computation
   *        the computation being warrantied.
   */
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp computation) {
            this.set$computation(computation);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.warranties.Warranty)
                     this.$getProxy();
        }
        
        public boolean refresh(boolean force) {
            long currentTime = java.lang.System.currentTimeMillis();
            if (!force &&
                  !fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                      null) &&
                  this.get$witness().valid(currentTime))
                return false;
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (fabric.lang.Object._Proxy.idEquals(curVal.get$value(),
                                                       null) &&
                      !fabric.lang.Object._Proxy.idEquals(this.get$value(),
                                                          null) ||
                      !fabric.lang.Object._Proxy.idEquals(curVal.get$value(),
                                                          null) &&
                      !curVal.get$value().equals(this.get$value())) {
                    this.set$value(curVal.get$value());
                }
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null) &&
                      !fabric.lang.Object._Proxy.idEquals(
                                                   this.get$witness(),
                                                   curVal.get$contract())) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
                this.set$witness(curVal.get$contract());
                this.get$witness().activate();
                this.get$witness().
                  addObserver((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy());
                return true;
            }
            return false;
        }
        
        public long getExpiry() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  isActive())
                return this.get$witness().getExpiry();
            return -1;
        }
        
        /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
        public fabric.lang.Object value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            refresh(false);
            return this.get$value();
        }
        
        public java.lang.String toString() {
            return "Warranty " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$computation())) +
            " = " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$value())) +
            " until " +
            getExpiry();
        }
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null))
                return this.get$witness().getLeafSubjects();
            else
                return ((fabric.util.HashSet)
                          new fabric.util.HashSet._Impl(this.$getStore()).
                          $getProxy()).fabric$util$HashSet$();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm157 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff158 = 1;
                boolean $doBackoff159 = true;
                $label153: for (boolean $commit154 = false; !$commit154; ) {
                    if ($doBackoff159) {
                        if ($backoff158 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff158);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e155) {
                                    
                                }
                            }
                        }
                        if ($backoff158 < 5000) $backoff158 *= 2;
                    }
                    $doBackoff159 = $backoff158 <= 32 || !$doBackoff159;
                    $commit154 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActive()) return; }
                    catch (final fabric.worker.RetryException $e155) {
                        $commit154 = false;
                        continue $label153;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e155) {
                        $commit154 = false;
                        fabric.common.TransactionID $currentTid156 =
                          $tm157.getCurrentTid();
                        if ($e155.tid.isDescendantOf($currentTid156))
                            continue $label153;
                        if ($currentTid156.parent != null) throw $e155;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e155) {
                        $commit154 = false;
                        if ($tm157.checkForStaleObjects()) continue $label153;
                        throw new fabric.worker.AbortException($e155);
                    }
                    finally {
                        if ($commit154) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e155) {
                                $commit154 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e155) {
                                $commit154 = false;
                                fabric.common.TransactionID $currentTid156 =
                                  $tm157.getCurrentTid();
                                if ($currentTid156 != null) {
                                    if ($e155.tid.equals($currentTid156) ||
                                          !$e155.tid.isDescendantOf(
                                                       $currentTid156)) {
                                        throw $e155;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit154) {  }
                    }
                }
            }
            fabric.metrics.contracts.warranties.WarrantyValue curVal = null;
            fabric.metrics.contracts.MetricContract w = null;
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var160 = curVal;
                fabric.metrics.contracts.MetricContract w$var161 = w;
                fabric.worker.transaction.TransactionManager $tm166 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff167 = 1;
                boolean $doBackoff168 = true;
                $label162: for (boolean $commit163 = false; !$commit163; ) {
                    if ($doBackoff168) {
                        if ($backoff167 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff167);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e164) {
                                    
                                }
                            }
                        }
                        if ($backoff167 < 5000) $backoff167 *= 2;
                    }
                    $doBackoff168 = $backoff167 <= 32 || !$doBackoff168;
                    $commit163 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF TRYING TO COMPUTE");
                        long currentTime = java.lang.System.currentTimeMillis();
                        curVal = this.get$computation().apply(currentTime);
                        w = curVal.get$contract();
                    }
                    catch (final fabric.worker.RetryException $e164) {
                        $commit163 = false;
                        continue $label162;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e164) {
                        $commit163 = false;
                        fabric.common.TransactionID $currentTid165 =
                          $tm166.getCurrentTid();
                        if ($e164.tid.isDescendantOf($currentTid165))
                            continue $label162;
                        if ($currentTid165.parent != null) throw $e164;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e164) {
                        $commit163 = false;
                        if ($tm166.checkForStaleObjects()) continue $label162;
                        throw new fabric.worker.AbortException($e164);
                    }
                    finally {
                        if ($commit163) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e164) {
                                $commit163 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e164) {
                                $commit163 = false;
                                fabric.common.TransactionID $currentTid165 =
                                  $tm166.getCurrentTid();
                                if ($currentTid165 != null) {
                                    if ($e164.tid.equals($currentTid165) ||
                                          !$e164.tid.isDescendantOf(
                                                       $currentTid165)) {
                                        throw $e164;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit163) {
                            curVal = curVal$var160;
                            w = w$var161;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATING");
            w.activate();
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var169 = curVal;
                fabric.metrics.contracts.MetricContract w$var170 = w;
                fabric.worker.transaction.TransactionManager $tm175 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff176 = 1;
                boolean $doBackoff177 = true;
                $label171: for (boolean $commit172 = false; !$commit172; ) {
                    if ($doBackoff177) {
                        if ($backoff176 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff176);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e173) {
                                    
                                }
                            }
                        }
                        if ($backoff176 < 5000) $backoff176 *= 2;
                    }
                    $doBackoff177 = $backoff176 <= 32 || !$doBackoff177;
                    $commit172 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF OBSERVING");
                        this.set$value(curVal.get$value());
                        this.set$witness(curVal.get$contract());
                        this.get$witness().
                          addObserver(
                            (fabric.metrics.contracts.warranties.Warranty)
                              this.$getProxy());
                        super.activate();
                    }
                    catch (final fabric.worker.RetryException $e173) {
                        $commit172 = false;
                        continue $label171;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e173) {
                        $commit172 = false;
                        fabric.common.TransactionID $currentTid174 =
                          $tm175.getCurrentTid();
                        if ($e173.tid.isDescendantOf($currentTid174))
                            continue $label171;
                        if ($currentTid174.parent != null) throw $e173;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e173) {
                        $commit172 = false;
                        if ($tm175.checkForStaleObjects()) continue $label171;
                        throw new fabric.worker.AbortException($e173);
                    }
                    finally {
                        if ($commit172) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e173) {
                                $commit172 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e173) {
                                $commit172 = false;
                                fabric.common.TransactionID $currentTid174 =
                                  $tm175.getCurrentTid();
                                if ($currentTid174 != null) {
                                    if ($e173.tid.equals($currentTid174) ||
                                          !$e173.tid.isDescendantOf(
                                                       $currentTid174)) {
                                        throw $e173;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit172) {
                            curVal = curVal$var169;
                            w = w$var170;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATE");
        }
        
        public void deactivate() {
            {
                fabric.worker.transaction.TransactionManager $tm182 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff183 = 1;
                boolean $doBackoff184 = true;
                $label178: for (boolean $commit179 = false; !$commit179; ) {
                    if ($doBackoff184) {
                        if ($backoff183 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff183);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e180) {
                                    
                                }
                            }
                        }
                        if ($backoff183 < 5000) $backoff183 *= 2;
                    }
                    $doBackoff184 = $backoff183 <= 32 || !$doBackoff184;
                    $commit179 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!isObserved()) {
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             this.get$witness(),
                                                             null)) {
                                this.get$witness().
                                  removeObserver(
                                    (fabric.metrics.contracts.warranties.Warranty)
                                      this.$getProxy());
                                this.get$witness().deactivate();
                            }
                            this.set$value(null);
                        }
                        super.deactivate();
                    }
                    catch (final fabric.worker.RetryException $e180) {
                        $commit179 = false;
                        continue $label178;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e180) {
                        $commit179 = false;
                        fabric.common.TransactionID $currentTid181 =
                          $tm182.getCurrentTid();
                        if ($e180.tid.isDescendantOf($currentTid181))
                            continue $label178;
                        if ($currentTid181.parent != null) throw $e180;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e180) {
                        $commit179 = false;
                        if ($tm182.checkForStaleObjects()) continue $label178;
                        throw new fabric.worker.AbortException($e180);
                    }
                    finally {
                        if ($commit179) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e180) {
                                $commit179 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e180) {
                                $commit179 = false;
                                fabric.common.TransactionID $currentTid181 =
                                  $tm182.getCurrentTid();
                                if ($currentTid181 != null) {
                                    if ($e180.tid.equals($currentTid181) ||
                                          !$e180.tid.isDescendantOf(
                                                       $currentTid181)) {
                                        throw $e180;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit179) {  }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.Warranty._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.computation, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.witness, refTypes, out, intraStoreRefs,
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
            this.computation =
              (fabric.metrics.contracts.warranties.WarrantyComp)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyComp._Proxy.class,
                  (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.witness =
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.Warranty._Impl src =
              (fabric.metrics.contracts.warranties.Warranty._Impl) other;
            this.computation = src.computation;
            this.value = src.value;
            this.witness = src.witness;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public _Proxy(fabric.metrics.contracts.warranties.Warranty._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.Warranty.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  Warranty.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    Warranty.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.Warranty._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.Warranty._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.Warranty._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 112, 113, 85, -94, -85,
    43, 54, 57, -49, 12, 62, -40, -73, 92, 74, 124, 52, 42, 47, -26, -122, 82,
    -99, 0, 70, 26, -80, 92, -14, -125, -108, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
