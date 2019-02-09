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
    
    public fabric.worker.metrics.treaties.TreatySet get$treaties();
    
    public fabric.worker.metrics.treaties.TreatySet set$treaties(
      fabric.worker.metrics.treaties.TreatySet val);
    
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
        
        private fabric.worker.metrics.treaties.TreatySet treaties;
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$lang$Object$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet().add(m));
            this.
              set$treaties(
                fabric.worker.metrics.treaties.TreatySet.
                    emptySet((fabric.metrics.util.TreatiesBox)
                               this.$getProxy()));
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public int size() { return this.get$treaties().size(); }
        
        public java.util.Iterator iterator() {
            return this.get$treaties().iterator();
        }
        
        public void remove(fabric.metrics.treaties.Treaty treaty) {
            this.get$treaties().remove(treaty);
            this.set$$associates(this.get$$associates().remove(treaty));
        }
        
        public fabric.metrics.treaties.Treaty get(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt) {
            return this.get$treaties().get(stmt);
        }
        
        public fabric.metrics.treaties.Treaty create(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          fabric.worker.metrics.StatsMap statsMap) {
            return this.get$treaties().create(stmt, statsMap);
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
            $writeInline(out, this.treaties);
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
            this.treaties = (fabric.worker.metrics.treaties.TreatySet)
                              in.readObject();
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
    
    public static final byte[] $classHash = new byte[] { 122, -32, -3, 125, -67,
    -30, -71, -21, -67, -46, 99, 33, -92, 73, -28, 67, 93, 98, 89, 0, -40, 124,
    -2, -26, 23, 52, -110, -48, -115, 19, -48, -112 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549746242000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
