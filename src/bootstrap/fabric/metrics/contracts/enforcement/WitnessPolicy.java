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
            long lowest = 0;
            fabric.util.Iterator itr = this.get$witnesses().iterator();
            while (itr.hasNext()) {
                fabric.metrics.contracts.MetricContract w =
                  (fabric.metrics.contracts.MetricContract)
                    fabric.lang.Object._Proxy.$getProxy(itr.next());
                if (lowest == 0 || w.getExpiry() < lowest)
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
    
    public static final byte[] $classHash = new byte[] { -109, 22, 112, -117, 4,
    49, 90, -122, 82, -11, 69, 45, 38, 90, 19, 51, -31, 102, 36, 107, -128, 9,
    -10, -36, -13, 22, -72, 13, 95, -93, 40, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
