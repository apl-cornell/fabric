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
    
    public boolean equals(fabric.lang.Object other);
    
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
                if (!atLeastOnce || w.getExpiry() < result) {
                    atLeastOnce = true;
                    result = w.getExpiry();
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
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.WitnessPolicy))
                return false;
            fabric.metrics.contracts.enforcement.WitnessPolicy that =
              (fabric.metrics.contracts.enforcement.WitnessPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return this.get$witnesses().equals(that.get$witnesses());
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
    
    public static final byte[] $classHash = new byte[] { 64, -40, 58, -115, -35,
    -116, -72, 33, 113, -21, -119, -84, 59, -30, 7, 101, -59, 3, -57, 41, -12,
    124, -88, 82, 121, 34, -11, 53, -41, -46, 98, -111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492455079000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
