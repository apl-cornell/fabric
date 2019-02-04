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
 * Utility to make observers exist outside of metrics for the purposes of
 * managing contention and conflicts between transactions.
 *
 * This acts as a proxy for the Metric's treaties and observers.
 * TODO: Should this still be a proxy?
 */
public interface ObserversBox extends fabric.lang.Object {
    public fabric.metrics.util.Subject get$owner();
    
    public fabric.metrics.util.Subject set$owner(
      fabric.metrics.util.Subject val);
    
    public fabric.worker.metrics.ImmutableObserverSet get$observers();
    
    public fabric.worker.metrics.ImmutableObserverSet set$observers(
      fabric.worker.metrics.ImmutableObserverSet val);
    
    public fabric.metrics.util.ObserversBox fabric$metrics$util$ObserversBox$(
      fabric.metrics.util.Subject owner);
    
    public boolean contains(fabric.metrics.util.Observer o);
    
    public void add(fabric.metrics.util.Observer o);
    
    public void remove(fabric.metrics.util.Observer o);
    
    public boolean isEmpty();
    
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ObserversBox {
        public fabric.metrics.util.Subject get$owner() {
            return ((fabric.metrics.util.ObserversBox._Impl) fetch()).get$owner(
                                                                        );
        }
        
        public fabric.metrics.util.Subject set$owner(
          fabric.metrics.util.Subject val) {
            return ((fabric.metrics.util.ObserversBox._Impl) fetch()).set$owner(
                                                                        val);
        }
        
        public fabric.worker.metrics.ImmutableObserverSet get$observers() {
            return ((fabric.metrics.util.ObserversBox._Impl) fetch()).
              get$observers();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$observers(
          fabric.worker.metrics.ImmutableObserverSet val) {
            return ((fabric.metrics.util.ObserversBox._Impl) fetch()).
              set$observers(val);
        }
        
        public fabric.metrics.util.ObserversBox
          fabric$metrics$util$ObserversBox$(fabric.metrics.util.Subject arg1) {
            return ((fabric.metrics.util.ObserversBox) fetch()).
              fabric$metrics$util$ObserversBox$(arg1);
        }
        
        public boolean contains(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.ObserversBox) fetch()).contains(arg1);
        }
        
        public void add(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserversBox) fetch()).add(arg1);
        }
        
        public void remove(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserversBox) fetch()).remove(arg1);
        }
        
        public boolean isEmpty() {
            return ((fabric.metrics.util.ObserversBox) fetch()).isEmpty();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return ((fabric.metrics.util.ObserversBox) fetch()).getObservers();
        }
        
        public _Proxy(ObserversBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ObserversBox {
        public fabric.metrics.util.Subject get$owner() { return this.owner; }
        
        public fabric.metrics.util.Subject set$owner(
          fabric.metrics.util.Subject val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.util.Subject owner;
        
        public fabric.worker.metrics.ImmutableObserverSet get$observers() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observers;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$observers(
          fabric.worker.metrics.ImmutableObserverSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet observers;
        
        public fabric.metrics.util.ObserversBox
          fabric$metrics$util$ObserversBox$(fabric.metrics.util.Subject owner) {
            this.set$owner(owner);
            fabric$lang$Object$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet(
                                                              ).add(owner));
            this.set$observers(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            return (fabric.metrics.util.ObserversBox) this.$getProxy();
        }
        
        public boolean contains(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public void add(fabric.metrics.util.Observer o) {
            this.set$observers(this.get$observers().add(o));
        }
        
        public void remove(fabric.metrics.util.Observer o) {
            this.set$observers(this.get$observers().remove(o));
        }
        
        public boolean isEmpty() { return this.get$observers().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$observers();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ObserversBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.observers);
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
            this.owner = (fabric.metrics.util.Subject)
                           $readRef(fabric.metrics.util.Subject._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.observers = (fabric.worker.metrics.ImmutableObserverSet)
                               in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ObserversBox._Impl src =
              (fabric.metrics.util.ObserversBox._Impl) other;
            this.owner = src.owner;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.ObserversBox._Static {
            public _Proxy(fabric.metrics.util.ObserversBox._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.ObserversBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ObserversBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.ObserversBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ObserversBox._Static._Impl.class);
                $instance = (fabric.metrics.util.ObserversBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.ObserversBox._Static {
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
                return new fabric.metrics.util.ObserversBox._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 55, -121, 17, 8, -124,
    38, -32, -93, -3, -9, 11, 64, 122, -88, -17, 113, 96, 33, -107, 105, 119,
    -39, -37, 14, 67, 80, 46, -118, 108, 101, -29, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549294072000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
