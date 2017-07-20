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
    
    public static final byte[] $classHash = new byte[] { -40, -102, -122, -10,
    18, 43, 53, -59, 25, -38, 31, 47, 94, -89, 1, 75, -28, 33, -63, 89, -75, 6,
    66, -27, -44, 24, 89, -49, 79, -82, -3, -106 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500320262000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1ZfWwcRxWfO9vnjzix43y7ifN1DeTrTkmhUmtA1Ke4cXKpTZwU4oi4c3tz9iZ7u5vdOftcGghfTdRCkFo3pEAjhAKhrZNKFQGJylCkpCSkJLRENK2ARIjQVCGIqqLkD0p5b2bubm/vfLUR4uSdNzvz3sybN+/93sx67CapcR2yLEUTuhHhIzZzI5000RXvoY7LkjGDuu42aO3XplV3Hb5+PNkWJME4adSoaZm6Ro1+0+VkRnw3HaJRk/Ho9q1d7TtJvYaCG6k7yElwZ0fWIUtsyxgZMCyuJikZ/4nV0dFv7mp+voo09ZEm3ezllOtazDI5y/I+0phm6QRz3HuSSZbsIzNNxpK9zNGpoT8IjJbZR1pcfcCkPOMwdytzLWMIGVvcjM0cMWeuEdW3QG0no3HLAfWbpfoZrhvRuO7y9jgJpXRmJN295POkOk5qUgYdAMa58dwqomLEaCe2A3uDDmo6KaqxnEj1Ht1McrLYL5FfcXgzMIBobZrxQSs/VbVJoYG0SJUMag5Ee7mjmwPAWmNlYBZOWiccFJjqbKrtoQOsn5P5fr4e2QVc9cIsKMLJHD+bGAn2rNW3Z57dunnfxw59ztxoBkkAdE4yzUD960CozSe0laWYw0yNScHGVfHDdO74wSAhwDzHxyx5fvLQ259c0/biWclzWxme7sRupvF+7VhixisLYyvvqkI16mzL1dEVilYudrVH9bRnbfD2ufkRsTOS63xx60s79j/DbgRJQxcJaZaRSYNXzdSstK0bzLmXmcyhnCW7SD0zkzHR30VqoR7XTSZbu1Mpl/EuUm2IppAl3sFEKRgCTVQLdd1MWbm6TfmgqGdtQkgtPCQAz25CGkaBTofXRzmh0UErzaIJI8OGwb2j8DDqaINRiFtH16Kuo0WdjMl1YFJN4EVA3Ci4Oneoxt0og2kdjaWZyaOf1rnJXLfHMnRtJALK2f+PSbK40ubhQAA2YbFmJVmCurCjyrs6egwIoI2WkWROv2YcGu8is8afFB5Wj1HhgmcLGwbAKxb68cQrO5rp2PD2yf7z0jtRVpmYk/VS84jSPJLXPOLRPFKkOSjbiNEYAXyLAL6NBbKR2NGuZ4XThVwRnfnxG2H8u22DchgsnSWBgFjsbCEvvA18ZQ9gEMBM48rez2564OCyKnBze7gadx5Yw/6gK0BVF9QoRFK/1nTg+rvPHd5nFcKPk3AJKpRKYlQv81vOsTSWBNQsDL9qCT3VP74vHEREqkcTUXBnQJ42/xxF0d2eQ0q0Rk2cTEMbUAO7cvDWwAcda7jQIjxiBhYt0jnQWD4FBch+vNd+6vKFt+4Q6SeHx00e4O5lvN2DAThYk4j2mQXbb3MYA74/HOl5/ImbB3YKwwPH8nIThrGMQexTCHrL+erZva9f+eOxS8HCZnESsjMJ8JCsWMvM9+EXgOff+GAgYwNSgPOYApEleRSxceYVBd0ATwzANFDdDW8301ZST+k0YTD0lH813b7u1F8PNcvtNqBFGs8haz54gEL7gg6y//yuf7aJYQIa5rOC/QpsEiRnFUa+x3HoCOqR/eKri578JX0KPB8gztUfZAK1iLAHERu4XthirSjX+fo+gsUyaa2For3KLU0YnZh5C77YFx37TmvsEzckDuR9EcdYWgYH7qeeMFn/TPofwWWhM0FS20eaRdKnJr+fAsKBG/RB2nZjqjFOphf1F6dgmW/a87G20B8Hnmn9UVDAH6gjN9YbpONLxwFDNKGRVsHTTEgwquhs7J1lYzk7GyCicrcQWS7KFVisFIYMYnUVJ/V6Op3huO1igtXQMiyxDP4cssh3zgMIFJsr8+6F47cWjIffuiXzrj/7exj/PnblxqvTF50U+FCNIC7W5D82lZ6Kig47QsPGvAmEn7RAXF0npGNA0R2cbPrv0xLmZepsEW8qyf0PR5OOPIeTD02YUyRzTL0je2s+MgIK8vH9TixiuMe+V6xsqrzlNSndpEZuu0MGMwf4YJm46nH0NEDjkDqIsYOjj7wfOTQqMUWeVpeXHBi9MvLEKiaaLmbLwixLK80iJDrffG7fCz/cd0B6VUvx2WuDmUmf+N17L0eOXD1XJltXgQfhS0d5EwSECeTSsbgPi08JgWzezkFprdxmSVzDqIbzrAU7CrEi+hZArGDyNiy41uT3VmZu3YrkLxsJeZDbmS3ZSbBGyT1qi3D4AiRdvbHortieawPSGot91vNzP71l7Ny9K7THgqQqjz0lV4piofZixGlwGNyIzG1FuLNE+tckLVsB0Qcq9OlYQJKp0dDMOXs2F8wvQVXaUuZ+4ft5QGjAoebCMwu272uK7i+DiekJ1sBJre3oQ5Bts/lBgzhovRrsC4pmPYNCCEFW050RIdKtHB3JVg6515JnvbK6LpT6Bi8r+lIZXYelrljYpUqh1BlFf1akVD3gh1hKspxetQnLMhgVp57mbGEKlp9C/ELqRvGIol/xTOHJygQDe9FElz8R1Me+NHo02f39dUG12b2gIbfstQYbYoZnqDpRfyCvBi6SpOBpA8BfK2nVb7x2KljXtwJhpDolclHRc/4VrPVHpUQDLB+t4Kxfx+JhTu6QUB5WUB7OQ3nYcz0IF10PwgWNfeucD8+HCQSAotumtk4U6VV0y8Tr9C5jtELfYSy+wUldzpPKOviQpSfLrWUePDBizeuKnp/aWlDkV4qentxavluh73tYfBv83mEpyODiBn2knNoIH5vgXv1TRU9MTW0UGVP0B5NT++kKfc9icSyPMPjGy2m9Ap5u0GCapLV/m5rWKHJT0TcnFSCdYtTnK6j+IyxOAJpT2zZGJrQ3+Cr5DGjxjqKXpqY5ivxW0V9PQfMXKmg+jsWPwVcyZmXdW+Hph/qXFXWmpjuK7FV0z+R85XSFPpE8fg7hyi35AbBMBvV0LPB/eii3wtvhSUL9hqIXp7ZCFLmg6NkP3J2cvi3qdOzJ+eU1Fhq8UsEkr2HxMobP3gyVWeahLCfTi+AY7663lfm2pL6KarHT7Ni1zWvmTPBdaX7Jd2old/JoU928o9tfk3ee3BfP+jipS2UMw3vH89RDNgCULpSvlzc+W5DfcxKezIcoTqZ53sSK35AjXOFk/kQjcHlPFnWvzJ84mVEsw8WNDGtevmtgYsmHb38Rm9zqK3K7e+ekvqdtKNTlLokxxGStGQf/RTD2zrxbobptV8X3FdjwJZe/9fC7Las/embBG4uju44HNv956S92nAp1XLs0f8fF7pPvHf4PdGuA+boYAAA=";
}
