package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;

/**
 * An {@link EnforcementPolicy} which enforces a {@link MetricContract} by
 * relying on a set of <em>witnesses</em>, other {@link MetricContract}s that in
 * conjunction imply the enforced {@link MetricContract}.
 */
public interface WitnessPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.lang.arrays.ObjectArray get$witnesses();
    
    public fabric.lang.arrays.ObjectArray set$witnesses(
      fabric.lang.arrays.ObjectArray val);
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param witnesses
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.lang.arrays.ObjectArray witnesses);
    
    public void activate();
    
    public void refresh();
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.lang.arrays.ObjectArray get$witnesses() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$witnesses();
        }
        
        public fabric.lang.arrays.ObjectArray set$witnesses(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$witnesses(val);
        }
        
        public long get$expiry() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).postDec$expiry();
        }
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$activated(val);
        }
        
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$WitnessPolicy$(arg1);
        }
        
        public _Proxy(WitnessPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.lang.arrays.ObjectArray get$witnesses() {
            return this.witnesses;
        }
        
        public fabric.lang.arrays.ObjectArray set$witnesses(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witnesses = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.lang.arrays.ObjectArray witnesses;
        
        public long get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public long set$expiry(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp - 1));
            return tmp;
        }
        
        /** The currently associated expiration based on the witnesses. */
        private long expiry;
        
        public boolean get$activated() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.activated;
        }
        
        public boolean set$activated(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.activated = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** Is this currently actively enforced? */
        private boolean activated;
        
        /**
   * @param witnesses
   *        the array of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.lang.arrays.ObjectArray witnesses) {
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            this.
              set$witnesses(
                (fabric.lang.arrays.ObjectArray)
                  new fabric.lang.arrays.ObjectArray._Impl(this.$getStore()).
                  fabric$lang$arrays$ObjectArray$(
                    this.get$$updateLabel(),
                    this.get$$updateLabel().
                        confPolicy(),
                    fabric.metrics.contracts.MetricContract._Proxy.class,
                    witnesses.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(witnesses, 0,
                                               this.get$witnesses(), 0,
                                               witnesses.get$length());
            this.set$expiry((long) -1);
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.WitnessPolicy)
                     this.$getProxy();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm112 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff113 = 1;
                boolean $doBackoff114 = true;
                $label108: for (boolean $commit109 = false; !$commit109; ) {
                    if ($doBackoff114) {
                        if ($backoff113 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff113);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e110) {
                                    
                                }
                            }
                        }
                        if ($backoff113 < 5000) $backoff113 *= 2;
                    }
                    $doBackoff114 = $backoff113 <= 32 || !$doBackoff114;
                    $commit109 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e110) {
                        $commit109 = false;
                        continue $label108;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e110) {
                        $commit109 = false;
                        fabric.common.TransactionID $currentTid111 =
                          $tm112.getCurrentTid();
                        if ($e110.tid.isDescendantOf($currentTid111))
                            continue $label108;
                        if ($currentTid111.parent != null) throw $e110;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e110) {
                        $commit109 = false;
                        if ($tm112.checkForStaleObjects()) continue $label108;
                        throw new fabric.worker.AbortException($e110);
                    }
                    finally {
                        if ($commit109) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e110) {
                                $commit109 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e110) {
                                $commit109 = false;
                                fabric.common.TransactionID $currentTid111 =
                                  $tm112.getCurrentTid();
                                if ($currentTid111 != null) {
                                    if ($e110.tid.equals($currentTid111) ||
                                          !$e110.tid.isDescendantOf(
                                                       $currentTid111)) {
                                        throw $e110;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit109) {  }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
            int len = 0;
            {
                int len$var115 = len;
                fabric.worker.transaction.TransactionManager $tm120 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff121 = 1;
                boolean $doBackoff122 = true;
                $label116: for (boolean $commit117 = false; !$commit117; ) {
                    if ($doBackoff122) {
                        if ($backoff121 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff121);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e118) {
                                    
                                }
                            }
                        }
                        if ($backoff121 < 5000) $backoff121 *= 2;
                    }
                    $doBackoff122 = $backoff121 <= 32 || !$doBackoff122;
                    $commit117 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { len = this.get$witnesses().get$length(); }
                    catch (final fabric.worker.RetryException $e118) {
                        $commit117 = false;
                        continue $label116;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e118) {
                        $commit117 = false;
                        fabric.common.TransactionID $currentTid119 =
                          $tm120.getCurrentTid();
                        if ($e118.tid.isDescendantOf($currentTid119))
                            continue $label116;
                        if ($currentTid119.parent != null) throw $e118;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e118) {
                        $commit117 = false;
                        if ($tm120.checkForStaleObjects()) continue $label116;
                        throw new fabric.worker.AbortException($e118);
                    }
                    finally {
                        if ($commit117) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e118) {
                                $commit117 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e118) {
                                $commit117 = false;
                                fabric.common.TransactionID $currentTid119 =
                                  $tm120.getCurrentTid();
                                if ($currentTid119 != null) {
                                    if ($e118.tid.equals($currentTid119) ||
                                          !$e118.tid.isDescendantOf(
                                                       $currentTid119)) {
                                        throw $e118;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit117) { len = len$var115; }
                    }
                }
            }
            boolean atLeastOnce = false;
            for (int i = 0; i < len; i++) {
                fabric.metrics.contracts.MetricContract w = null;
                {
                    int len$var123 = len;
                    fabric.metrics.contracts.MetricContract w$var124 = w;
                    int i$var125 = i;
                    fabric.worker.transaction.TransactionManager $tm130 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff131 = 1;
                    boolean $doBackoff132 = true;
                    $label126: for (boolean $commit127 = false; !$commit127; ) {
                        if ($doBackoff132) {
                            if ($backoff131 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff131);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e128) {
                                        
                                    }
                                }
                            }
                            if ($backoff131 < 5000) $backoff131 *= 2;
                        }
                        $doBackoff132 = $backoff131 <= 32 || !$doBackoff132;
                        $commit127 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            w = (fabric.metrics.contracts.MetricContract)
                                  this.get$witnesses().get(i);
                        }
                        catch (final fabric.worker.RetryException $e128) {
                            $commit127 = false;
                            continue $label126;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e128) {
                            $commit127 = false;
                            fabric.common.TransactionID $currentTid129 =
                              $tm130.getCurrentTid();
                            if ($e128.tid.isDescendantOf($currentTid129))
                                continue $label126;
                            if ($currentTid129.parent != null) throw $e128;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e128) {
                            $commit127 = false;
                            if ($tm130.checkForStaleObjects())
                                continue $label126;
                            throw new fabric.worker.AbortException($e128);
                        }
                        finally {
                            if ($commit127) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e128) {
                                    $commit127 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e128) {
                                    $commit127 = false;
                                    fabric.common.TransactionID $currentTid129 =
                                      $tm130.getCurrentTid();
                                    if ($currentTid129 != null) {
                                        if ($e128.tid.equals($currentTid129) ||
                                              !$e128.tid.isDescendantOf(
                                                           $currentTid129)) {
                                            throw $e128;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit127) {
                                len = len$var123;
                                w = w$var124;
                                i = i$var125;
                            }
                        }
                    }
                }
                w.activate();
                {
                    int len$var133 = len;
                    fabric.metrics.contracts.MetricContract w$var134 = w;
                    int i$var135 = i;
                    boolean atLeastOnce$var136 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm141 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff142 = 1;
                    boolean $doBackoff143 = true;
                    $label137: for (boolean $commit138 = false; !$commit138; ) {
                        if ($doBackoff143) {
                            if ($backoff142 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff142);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e139) {
                                        
                                    }
                                }
                            }
                            if ($backoff142 < 5000) $backoff142 *= 2;
                        }
                        $doBackoff143 = $backoff142 <= 32 || !$doBackoff143;
                        $commit138 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!atLeastOnce || w.getExpiry() <
                                  this.get$expiry()) {
                                atLeastOnce = true;
                                this.set$expiry((long) w.getExpiry());
                            }
                        }
                        catch (final fabric.worker.RetryException $e139) {
                            $commit138 = false;
                            continue $label137;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e139) {
                            $commit138 = false;
                            fabric.common.TransactionID $currentTid140 =
                              $tm141.getCurrentTid();
                            if ($e139.tid.isDescendantOf($currentTid140))
                                continue $label137;
                            if ($currentTid140.parent != null) throw $e139;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e139) {
                            $commit138 = false;
                            if ($tm141.checkForStaleObjects())
                                continue $label137;
                            throw new fabric.worker.AbortException($e139);
                        }
                        finally {
                            if ($commit138) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e139) {
                                    $commit138 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e139) {
                                    $commit138 = false;
                                    fabric.common.TransactionID $currentTid140 =
                                      $tm141.getCurrentTid();
                                    if ($currentTid140 != null) {
                                        if ($e139.tid.equals($currentTid140) ||
                                              !$e139.tid.isDescendantOf(
                                                           $currentTid140)) {
                                            throw $e139;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit138) {
                                len = len$var133;
                                w = w$var134;
                                i = i$var135;
                                atLeastOnce = atLeastOnce$var136;
                            }
                        }
                    }
                }
            }
            {
                int len$var144 = len;
                boolean atLeastOnce$var145 = atLeastOnce;
                fabric.worker.transaction.TransactionManager $tm150 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff151 = 1;
                boolean $doBackoff152 = true;
                $label146: for (boolean $commit147 = false; !$commit147; ) {
                    if ($doBackoff152) {
                        if ($backoff151 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff151);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e148) {
                                    
                                }
                            }
                        }
                        if ($backoff151 < 5000) $backoff151 *= 2;
                    }
                    $doBackoff152 = $backoff151 <= 32 || !$doBackoff152;
                    $commit147 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.set$activated(true); }
                    catch (final fabric.worker.RetryException $e148) {
                        $commit147 = false;
                        continue $label146;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e148) {
                        $commit147 = false;
                        fabric.common.TransactionID $currentTid149 =
                          $tm150.getCurrentTid();
                        if ($e148.tid.isDescendantOf($currentTid149))
                            continue $label146;
                        if ($currentTid149.parent != null) throw $e148;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e148) {
                        $commit147 = false;
                        if ($tm150.checkForStaleObjects()) continue $label146;
                        throw new fabric.worker.AbortException($e148);
                    }
                    finally {
                        if ($commit147) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e148) {
                                $commit147 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e148) {
                                $commit147 = false;
                                fabric.common.TransactionID $currentTid149 =
                                  $tm150.getCurrentTid();
                                if ($currentTid149 != null) {
                                    if ($e148.tid.equals($currentTid149) ||
                                          !$e148.tid.isDescendantOf(
                                                       $currentTid149)) {
                                        throw $e148;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit147) {
                            len = len$var144;
                            atLeastOnce = atLeastOnce$var145;
                        }
                    }
                }
            }
        }
        
        public long expiry() { return this.get$expiry(); }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            if (!this.get$activated()) activate();
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
                   this.get$witnesses().get(i)).
                  addObserver(
                    (fabric.metrics.util.Observer)
                      fabric.lang.Object._Proxy.$getProxy(mc.fetch()));
            }
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            for (int i = 0; i < this.get$witnesses().get$length(); i++) {
                ((fabric.metrics.contracts.MetricContract)
                   this.get$witnesses().get(i)).removeObserver(mc);
            }
        }
        
        public java.lang.String toString() {
            return fabric.util.Arrays._Impl.deepToString(this.get$witnesses());
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.WitnessPolicy))
                return false;
            fabric.metrics.contracts.enforcement.WitnessPolicy that =
              (fabric.metrics.contracts.enforcement.WitnessPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$witnesses(
                                                                 ))).
              equals(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                      that.get$witnesses()));
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.WitnessPolicy.
                     _Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.witnesses, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeLong(this.expiry);
            out.writeBoolean(this.activated);
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
            this.witnesses =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.expiry = in.readLong();
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.WitnessPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.WitnessPolicy._Impl) other;
            this.witnesses = src.witnesses;
            this.expiry = src.expiry;
            this.activated = src.activated;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.WitnessPolicy._Static
        {
            public _Proxy(fabric.metrics.contracts.enforcement.WitnessPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              WitnessPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  WitnessPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    WitnessPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.WitnessPolicy.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.WitnessPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.WitnessPolicy._Static
        {
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
                return new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 100, 113, -24, -125,
    -72, 40, -52, -57, -36, 6, -83, 19, -110, -69, 66, 44, -20, 44, 92, -121,
    -3, -106, 39, 74, 123, 22, 26, 124, 31, 122, -43, 70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500320262000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
