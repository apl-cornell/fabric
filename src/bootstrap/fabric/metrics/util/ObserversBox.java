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
    
    public static final byte[] $classHash = new byte[] { -73, -109, 54, -9,
    -123, 0, 41, -78, 110, 32, -82, -36, 12, -36, 59, -36, 102, 36, -68, 30,
    -111, 31, 104, -73, 65, 6, 85, 48, -80, 37, 105, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
