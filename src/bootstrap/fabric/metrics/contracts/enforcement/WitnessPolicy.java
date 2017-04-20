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
   *        the set of {@link MetricContract}s used to enforce this
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
        
        public long expiry;
        
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
        
        public boolean activated;
        
        /**
   * @param witnesses
   *        the set of {@link MetricContract}s used to enforce this
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
    
    public static final byte[] $classHash = new byte[] { 112, -118, -91, -98,
    52, 108, -68, 121, 106, 125, 41, -37, 6, -10, -28, -90, -128, 65, 57, -19,
    9, 56, -111, -22, -50, -120, 92, -48, 122, 116, 41, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492660216000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
