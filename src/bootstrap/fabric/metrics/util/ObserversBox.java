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
import fabric.worker.transaction.TransactionManager;
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
            if (fabric.worker.transaction.TransactionManager.usingPrefetching())
                this.set$$associates(this.get$$associates().add(o));
        }
        
        public void remove(fabric.metrics.util.Observer o) {
            this.set$observers(this.get$observers().remove(o));
            if (fabric.worker.transaction.TransactionManager.usingPrefetching())
                this.set$$associates(this.get$$associates().remove(o));
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
    
    public static final byte[] $classHash = new byte[] { 90, -24, 9, 116, -91,
    82, -29, -60, -73, 52, -17, -24, 16, -4, 20, 48, 98, 67, 61, 26, -75, -47,
    40, -73, -114, -2, -106, -19, -32, 57, -66, -51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
