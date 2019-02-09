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
            this.set$$associates(this.get$$associates().add(o));
        }
        
        public void remove(fabric.metrics.util.Observer o) {
            this.set$observers(this.get$observers().remove(o));
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
    
    public static final byte[] $classHash = new byte[] { 86, -78, -72, 40, -14,
    -13, 97, 27, 14, 14, 27, 84, 102, -111, -98, 58, -86, 16, -21, 122, 86, 74,
    24, 31, -58, 110, 3, 33, -83, -14, -115, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549745957000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
