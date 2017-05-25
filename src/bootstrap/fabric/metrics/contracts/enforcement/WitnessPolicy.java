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
                ((fabric.metrics.contracts.MetricContract)
                   this.get$witnesses().get(i)).deactivate();
            }
        }
        
        public java.lang.String toString() {
            return ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$witnesses(
                                                                 ))).toString();
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
    
    public static final byte[] $classHash = new byte[] { 98, 47, 55, 41, 56, -7,
    -78, 91, 40, -41, 35, 61, 103, 99, 34, 63, -73, -120, -66, -25, -126, -5,
    -116, -13, 118, -110, 18, 94, 11, -57, -22, -116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
