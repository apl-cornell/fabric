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
    
    public static final byte[] $classHash = new byte[] { 78, 112, 34, -106, 91,
    79, -74, -81, 93, -94, 126, -71, -52, -63, -109, -106, -73, -124, -25, 80,
    -83, -91, -85, -102, -117, 68, 98, 72, 114, -81, -10, -93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
