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
    
    public static final byte[] $classHash = new byte[] { -33, -115, 105, -25,
    -128, 50, 11, 87, 70, -64, 21, -126, 25, 37, 123, 45, -19, -127, 92, -32,
    85, -63, 122, 11, -27, 92, -106, -22, 89, 41, -81, 56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
