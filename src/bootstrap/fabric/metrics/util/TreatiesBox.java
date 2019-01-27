package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.TreatySet;
import java.util.SortedSet;

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
    
    public fabric.worker.metrics.treaties.TreatySet get$treaties();
    
    public fabric.worker.metrics.treaties.TreatySet set$treaties(
      fabric.worker.metrics.treaties.TreatySet val);
    
    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, java.util.SortedSet treaties);
    
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
        
        public fabric.worker.metrics.treaties.TreatySet get$treaties() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).
              get$treaties();
        }
        
        public fabric.worker.metrics.treaties.TreatySet set$treaties(
          fabric.worker.metrics.treaties.TreatySet val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).
              set$treaties(val);
        }
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).
              fabric$metrics$util$TreatiesBox$(arg1);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, java.util.SortedSet arg2) {
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
        
        public fabric.worker.metrics.treaties.TreatySet get$treaties() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.treaties;
        }
        
        public fabric.worker.metrics.treaties.TreatySet set$treaties(
          fabric.worker.metrics.treaties.TreatySet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.treaties = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public transient fabric.worker.metrics.treaties.TreatySet treaties;
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$metrics$util$AbstractSubject$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet().add(m));
            this.
              set$treaties(
                fabric.worker.metrics.treaties.TreatySet.
                    emptySet((fabric.metrics.util.TreatiesBox)
                               this.$getProxy()));
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, java.util.SortedSet treaties) {
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
            this.treaties = src.treaties;
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 85, 110, -8, 37, -61,
    -117, 114, 95, 50, -81, -88, -100, 8, -12, 20, 3, -117, 90, 59, -25, 123,
    -37, 82, 127, -96, -51, 71, -53, -3, -103, -46, 41 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548621385000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
