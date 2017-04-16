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
    public static final long jlc$SourceLastModified$fabil = 1492299472000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
