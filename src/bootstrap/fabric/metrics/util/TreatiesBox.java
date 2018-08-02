package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.common.util.LongSet;

/**
 * Utility to make treaties and observers exist outside of metrics for the
 * purposes of managing contention and conflicts between transactions.
 *
 * This acts as a proxy for the Metric's treaties and observers.
 */
public interface TreatiesBox
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.Metric get$owner();
    
    public fabric.metrics.Metric set$owner(fabric.metrics.Metric val);
    
    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, fabric.common.util.LongSet treaties);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).get$owner(
                                                                       );
        }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).set$owner(
                                                                       val);
        }
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).
              fabric$metrics$util$TreatiesBox$(arg1);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).handleUpdates(
                                                                 arg1, arg2);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.util.TreatiesBox) fetch()).getLeafSubjects(
                                                                 );
        }
        
        public _Proxy(TreatiesBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() { return this.owner; }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric owner;
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            return this.get$owner().handleUpdates(includesObserver, treaties);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$owner().getLeafSubjects();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.TreatiesBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.owner = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.TreatiesBox._Impl src =
              (fabric.metrics.util.TreatiesBox._Impl) other;
            this.owner = src.owner;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.TreatiesBox._Static {
            public _Proxy(fabric.metrics.util.TreatiesBox._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.TreatiesBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  TreatiesBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.TreatiesBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.TreatiesBox._Static._Impl.class);
                $instance = (fabric.metrics.util.TreatiesBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.TreatiesBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.TreatiesBox._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -27, -75, 2, -75, -51,
    -55, -13, -88, -5, -107, 54, -58, 85, -86, 36, -36, -73, -122, -80, 101,
    -14, -11, 15, 62, 112, -55, 40, 118, -28, -56, 111, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241129000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
