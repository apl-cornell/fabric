package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.Collections;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import fabric.util.Set;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;

/**
 * An {@link EnforcementPolicy} which enforces a {@link MetricContract} by
 * relying on a set of <em>witnesses</em>, other {@link MetricContract}s that in
 * conjunction imply the enforced {@link MetricContract}.
 */
public interface WitnessPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.util.Set get$witnesses();
    
    public fabric.util.Set set$witnesses(fabric.util.Set val);
    
    /**
   * @param witnesses
   *        the set of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.util.Collection witnesses);
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
      implements fabric.metrics.contracts.enforcement.WitnessPolicy {
        public fabric.util.Set get$witnesses() {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).get$witnesses();
        }
        
        public fabric.util.Set set$witnesses(fabric.util.Set val) {
            return ((fabric.metrics.contracts.enforcement.WitnessPolicy._Impl)
                      fetch()).set$witnesses(val);
        }
        
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.util.Collection arg1) {
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
        public fabric.util.Set get$witnesses() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.witnesses;
        }
        
        public fabric.util.Set set$witnesses(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witnesses = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The set of {@link MetricContract}s used to enforce this policy. */
        public fabric.util.Set witnesses;
        
        /**
   * @param witnesses
   *        the set of {@link MetricContract}s used to enforce this
   *        policy. If any of the witnesses weren't already active, they
   *        are {@link Contract#activate() activated} here.
   */
        public fabric.metrics.contracts.enforcement.WitnessPolicy
          fabric$metrics$contracts$enforcement$WitnessPolicy$(
          fabric.util.Collection witnesses) {
            this.set$witnesses(
                   ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$util$LinkedHashSet$(
                                                 witnesses));
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            fabric.util.Iterator itr = witnesses.iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                w.activate();
            }
            return (fabric.metrics.contracts.enforcement.WitnessPolicy)
                     this.$getProxy();
        }
        
        public long expiry() {
            boolean atLeastOnce = false;
            long result = 0;
            fabric.util.Iterator itr = this.get$witnesses().iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                if (!atLeastOnce || w.getExpiryLower() < result) {
                    atLeastOnce = true;
                    result = w.getExpiryLower();
                }
            }
            return result;
        }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            fabric.util.Iterator itr = this.get$witnesses().iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                w.addObserver(mc);
            }
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            fabric.util.Iterator itr = this.get$witnesses().iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                w.removeObserver(mc);
                w.deactivate();
            }
        }
        
        public java.lang.String toString() {
            return this.get$witnesses().toString();
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
            this.witnesses = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.WitnessPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.WitnessPolicy._Impl) other;
            this.witnesses = src.witnesses;
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
    
    public static final byte[] $classHash = new byte[] { -34, -49, 92, -121,
    -31, -115, 54, -108, -67, -30, 49, -30, 116, -85, -49, -110, -68, 95, -57,
    -54, 3, 113, -122, 114, -21, -71, -37, 19, -11, -90, -89, -7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492109732000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
