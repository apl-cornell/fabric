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
    
    public static final byte[] $classHash = new byte[] { -9, -121, -10, -62, -7,
    73, 38, 59, -62, -105, 43, -37, -17, -79, -88, 125, 87, 12, 60, -123, -107,
    -41, -46, -101, 41, -39, -76, -45, -16, 76, -103, 69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549833659000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
