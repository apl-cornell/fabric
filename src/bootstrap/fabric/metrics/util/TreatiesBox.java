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
    
    public static final byte[] $classHash = new byte[] { -111, -69, -102, -82,
    -26, 56, 90, -102, 6, 107, -78, -8, -10, 53, -56, -19, -70, 14, 112, 5,
    -121, -88, -84, 53, -87, -63, -116, 62, 2, 2, 25, -34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549295381000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
