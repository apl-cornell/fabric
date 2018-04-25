package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.metrics.ImmutableObserverSet;

/**
 * Allows for a separate object to hold the observer set, if we want.
 */
public interface ObserverSetBox extends fabric.lang.Object {
    public fabric.worker.metrics.ImmutableObserverSet get$value();
    
    public fabric.worker.metrics.ImmutableObserverSet set$value(
      fabric.worker.metrics.ImmutableObserverSet val);
    
    public fabric.metrics.util.ObserverSetBox
      fabric$metrics$util$ObserverSetBox$();
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ObserverSetBox {
        public fabric.worker.metrics.ImmutableObserverSet get$value() {
            return ((fabric.metrics.util.ObserverSetBox._Impl) fetch()).
              get$value();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$value(
          fabric.worker.metrics.ImmutableObserverSet val) {
            return ((fabric.metrics.util.ObserverSetBox._Impl) fetch()).
              set$value(val);
        }
        
        public fabric.metrics.util.ObserverSetBox
          fabric$metrics$util$ObserverSetBox$() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).
              fabric$metrics$util$ObserverSetBox$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserverSetBox) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserverSetBox) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).observedBy(
                                                                    arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).isObserved();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).getObservers(
                                                                    );
        }
        
        public _Proxy(ObserverSetBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ObserverSetBox {
        public fabric.worker.metrics.ImmutableObserverSet get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$value(
          fabric.worker.metrics.ImmutableObserverSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet value;
        
        public fabric.metrics.util.ObserverSetBox
          fabric$metrics$util$ObserverSetBox$() {
            fabric$lang$Object$();
            this.set$value(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            return (fabric.metrics.util.ObserverSetBox) this.$getProxy();
        }
        
        public void addObserver(fabric.metrics.util.Observer o) {
            if (!this.get$value().contains(o))
                this.set$value(this.get$value().add(o));
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            if (this.get$value().contains(o))
                this.set$value(this.get$value().remove(o));
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$value().contains(o);
        }
        
        public boolean isObserved() { return !this.get$value().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$value();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ObserverSetBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.value);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.worker.metrics.ImmutableObserverSet)
                           in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ObserverSetBox._Impl src =
              (fabric.metrics.util.ObserverSetBox._Impl) other;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.ObserverSetBox._Static {
            public _Proxy(fabric.metrics.util.ObserverSetBox._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.ObserverSetBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ObserverSetBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.ObserverSetBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ObserverSetBox._Static._Impl.class);
                $instance = (fabric.metrics.util.ObserverSetBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.ObserverSetBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.ObserverSetBox._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 13, 81, 41, 97, 115,
    71, -49, 31, 127, 104, -109, 115, -13, -96, 44, -52, 114, -103, 103, 64, 49,
    93, 80, 7, 27, -44, 16, -108, 34, 65, 7, -88 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613380000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
