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
   *            the set of {@link MetricContract}s used to enforce this
   *            policy. If any of the witnesses weren't already active, they
   *            are {@link Contract#activate() activated} here.
   */
    public fabric.metrics.contracts.enforcement.WitnessPolicy
      fabric$metrics$contracts$enforcement$WitnessPolicy$(
      fabric.util.Collection witnesses);
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
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
   *            the set of {@link MetricContract}s used to enforce this
   *            policy. If any of the witnesses weren't already active, they
   *            are {@link Contract#activate() activated} here.
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
            long lowest = -1;
            fabric.util.Iterator itr = this.get$witnesses().iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                if (lowest == -1 || w.getExpiry() < lowest)
                    lowest = w.getExpiry();
            }
            return lowest;
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
    
    public static final byte[] $classHash = new byte[] { -92, 91, -34, 70, -65,
    -36, 62, -111, -104, -7, 43, -55, 19, 27, -48, -112, 64, -58, -47, 64, -56,
    33, 59, -71, -12, 45, 119, 42, 35, -35, -18, -25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
