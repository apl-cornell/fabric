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
                fabric.worker.transaction.TransactionManager $tm119 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff120 = 1;
                boolean $doBackoff121 = true;
                $label115: for (boolean $commit116 = false; !$commit116; ) {
                    if ($doBackoff121) {
                        if ($backoff120 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff120);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e117) {
                                    
                                }
                            }
                        }
                        if ($backoff120 < 5000) $backoff120 *= 2;
                    }
                    $doBackoff121 = $backoff120 <= 32 || !$doBackoff121;
                    $commit116 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e117) {
                        $commit116 = false;
                        continue $label115;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e117) {
                        $commit116 = false;
                        fabric.common.TransactionID $currentTid118 =
                          $tm119.getCurrentTid();
                        if ($e117.tid.isDescendantOf($currentTid118))
                            continue $label115;
                        if ($currentTid118.parent != null) throw $e117;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e117) {
                        $commit116 = false;
                        if ($tm119.checkForStaleObjects()) continue $label115;
                        throw new fabric.worker.AbortException($e117);
                    }
                    finally {
                        if ($commit116) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e117) {
                                $commit116 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e117) {
                                $commit116 = false;
                                fabric.common.TransactionID $currentTid118 =
                                  $tm119.getCurrentTid();
                                if ($currentTid118 != null) {
                                    if ($e117.tid.equals($currentTid118) ||
                                          !$e117.tid.isDescendantOf(
                                                       $currentTid118)) {
                                        throw $e117;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit116) {  }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
            int len = 0;
            {
                int len$var122 = len;
                fabric.worker.transaction.TransactionManager $tm127 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff128 = 1;
                boolean $doBackoff129 = true;
                $label123: for (boolean $commit124 = false; !$commit124; ) {
                    if ($doBackoff129) {
                        if ($backoff128 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff128);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e125) {
                                    
                                }
                            }
                        }
                        if ($backoff128 < 5000) $backoff128 *= 2;
                    }
                    $doBackoff129 = $backoff128 <= 32 || !$doBackoff129;
                    $commit124 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { len = this.get$witnesses().get$length(); }
                    catch (final fabric.worker.RetryException $e125) {
                        $commit124 = false;
                        continue $label123;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e125) {
                        $commit124 = false;
                        fabric.common.TransactionID $currentTid126 =
                          $tm127.getCurrentTid();
                        if ($e125.tid.isDescendantOf($currentTid126))
                            continue $label123;
                        if ($currentTid126.parent != null) throw $e125;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e125) {
                        $commit124 = false;
                        if ($tm127.checkForStaleObjects()) continue $label123;
                        throw new fabric.worker.AbortException($e125);
                    }
                    finally {
                        if ($commit124) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e125) {
                                $commit124 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e125) {
                                $commit124 = false;
                                fabric.common.TransactionID $currentTid126 =
                                  $tm127.getCurrentTid();
                                if ($currentTid126 != null) {
                                    if ($e125.tid.equals($currentTid126) ||
                                          !$e125.tid.isDescendantOf(
                                                       $currentTid126)) {
                                        throw $e125;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit124) { len = len$var122; }
                    }
                }
            }
            boolean atLeastOnce = false;
            for (int i = 0; i < len; i++) {
                fabric.metrics.contracts.MetricContract w = null;
                {
                    int len$var130 = len;
                    fabric.metrics.contracts.MetricContract w$var131 = w;
                    int i$var132 = i;
                    fabric.worker.transaction.TransactionManager $tm137 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff138 = 1;
                    boolean $doBackoff139 = true;
                    $label133: for (boolean $commit134 = false; !$commit134; ) {
                        if ($doBackoff139) {
                            if ($backoff138 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff138);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e135) {
                                        
                                    }
                                }
                            }
                            if ($backoff138 < 5000) $backoff138 *= 2;
                        }
                        $doBackoff139 = $backoff138 <= 32 || !$doBackoff139;
                        $commit134 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            w = (fabric.metrics.contracts.MetricContract)
                                  this.get$witnesses().get(i);
                        }
                        catch (final fabric.worker.RetryException $e135) {
                            $commit134 = false;
                            continue $label133;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e135) {
                            $commit134 = false;
                            fabric.common.TransactionID $currentTid136 =
                              $tm137.getCurrentTid();
                            if ($e135.tid.isDescendantOf($currentTid136))
                                continue $label133;
                            if ($currentTid136.parent != null) throw $e135;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e135) {
                            $commit134 = false;
                            if ($tm137.checkForStaleObjects())
                                continue $label133;
                            throw new fabric.worker.AbortException($e135);
                        }
                        finally {
                            if ($commit134) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e135) {
                                    $commit134 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e135) {
                                    $commit134 = false;
                                    fabric.common.TransactionID $currentTid136 =
                                      $tm137.getCurrentTid();
                                    if ($currentTid136 != null) {
                                        if ($e135.tid.equals($currentTid136) ||
                                              !$e135.tid.isDescendantOf(
                                                           $currentTid136)) {
                                            throw $e135;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit134) {
                                len = len$var130;
                                w = w$var131;
                                i = i$var132;
                            }
                        }
                    }
                }
                w.activate();
                {
                    int len$var140 = len;
                    fabric.metrics.contracts.MetricContract w$var141 = w;
                    int i$var142 = i;
                    boolean atLeastOnce$var143 = atLeastOnce;
                    fabric.worker.transaction.TransactionManager $tm148 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    int $backoff149 = 1;
                    boolean $doBackoff150 = true;
                    $label144: for (boolean $commit145 = false; !$commit145; ) {
                        if ($doBackoff150) {
                            if ($backoff149 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff149);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e146) {
                                        
                                    }
                                }
                            }
                            if ($backoff149 < 5000) $backoff149 *= 2;
                        }
                        $doBackoff150 = $backoff149 <= 32 || !$doBackoff150;
                        $commit145 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!atLeastOnce || w.getExpiry() <
                                  this.get$expiry()) {
                                atLeastOnce = true;
                                this.set$expiry((long) w.getExpiry());
                            }
                        }
                        catch (final fabric.worker.RetryException $e146) {
                            $commit145 = false;
                            continue $label144;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e146) {
                            $commit145 = false;
                            fabric.common.TransactionID $currentTid147 =
                              $tm148.getCurrentTid();
                            if ($e146.tid.isDescendantOf($currentTid147))
                                continue $label144;
                            if ($currentTid147.parent != null) throw $e146;
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e146) {
                            $commit145 = false;
                            if ($tm148.checkForStaleObjects())
                                continue $label144;
                            throw new fabric.worker.AbortException($e146);
                        }
                        finally {
                            if ($commit145) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e146) {
                                    $commit145 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e146) {
                                    $commit145 = false;
                                    fabric.common.TransactionID $currentTid147 =
                                      $tm148.getCurrentTid();
                                    if ($currentTid147 != null) {
                                        if ($e146.tid.equals($currentTid147) ||
                                              !$e146.tid.isDescendantOf(
                                                           $currentTid147)) {
                                            throw $e146;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit145) {
                                len = len$var140;
                                w = w$var141;
                                i = i$var142;
                                atLeastOnce = atLeastOnce$var143;
                            }
                        }
                    }
                }
            }
            {
                int len$var151 = len;
                boolean atLeastOnce$var152 = atLeastOnce;
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
                    try { this.set$activated(true); }
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
                        if (!$commit154) {
                            len = len$var151;
                            atLeastOnce = atLeastOnce$var152;
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
    public static final long jlc$SourceLastModified$fabil = 1501602696000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
