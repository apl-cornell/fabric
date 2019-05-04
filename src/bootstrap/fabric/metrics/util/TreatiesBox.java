package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;
import java.util.Iterator;

/**
 * Utility to make treaties and observers exist outside of metrics for the
 * purposes of managing contention and conflicts between transactions.
 *
 * This acts as a proxy for the Metric's treaties and observers.
 * TODO: Should this still be a proxy?
 */
public interface TreatiesBox extends java.lang.Iterable, fabric.lang.Object {
    public fabric.metrics.Metric get$owner();
    
    public fabric.metrics.Metric set$owner(fabric.metrics.Metric val);
    
    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);
    
    public int size();
    
    public java.util.Iterator iterator();
    
    public void remove(fabric.metrics.treaties.Treaty treaty);
    
    public fabric.metrics.treaties.Treaty get(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt);
    
    public fabric.metrics.treaties.Treaty create(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
      fabric.worker.metrics.StatsMap statsMap);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
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
        
        public int size() {
            return ((fabric.metrics.util.TreatiesBox) fetch()).size();
        }
        
        public java.util.Iterator iterator() {
            return ((fabric.metrics.util.TreatiesBox) fetch()).iterator();
        }
        
        public void remove(fabric.metrics.treaties.Treaty arg1) {
            ((fabric.metrics.util.TreatiesBox) fetch()).remove(arg1);
        }
        
        public fabric.metrics.treaties.Treaty get(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).get(arg1);
        }
        
        public fabric.metrics.treaties.Treaty create(
          fabric.worker.metrics.treaties.statements.TreatyStatement arg1,
          fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).create(arg1,
                                                                      arg2);
        }
        
        public void forEach(java.util.function.Consumer arg1) {
            ((fabric.metrics.util.TreatiesBox) fetch()).forEach(arg1);
        }
        
        public java.util.Spliterator spliterator() {
            return ((fabric.metrics.util.TreatiesBox) fetch()).spliterator();
        }
        
        public _Proxy(TreatiesBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
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
            fabric$lang$Object$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet().add(m));
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public int size() { return this.get$$treaties().size(); }
        
        public java.util.Iterator iterator() {
            return this.get$$treaties().iterator();
        }
        
        public void remove(fabric.metrics.treaties.Treaty treaty) {
            this.get$$treaties().remove(treaty);
        }
        
        public fabric.metrics.treaties.Treaty get(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt) {
            return this.get$$treaties().get(stmt);
        }
        
        public fabric.metrics.treaties.Treaty create(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          fabric.worker.metrics.StatsMap statsMap) {
            return this.get$$treaties().create(stmt, statsMap);
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
    
    public static final byte[] $classHash = new byte[] { 34, -25, 110, 36, 65,
    55, -43, 83, 57, 121, -114, 23, -60, -70, 82, 10, -95, 5, -27, 24, 36, -97,
    -36, -105, 92, -44, 21, 7, -13, -35, -94, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
