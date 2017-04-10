package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.HashSet;
import fabric.util.LinkedHashSet;
import fabric.util.Set;

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.  */
public interface Subject extends fabric.lang.Object {
    public fabric.metrics.util.Subject fabric$metrics$util$Subject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    public boolean get$modified();
    
    public boolean set$modified(boolean val);
    
    /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
    public void addObserver(fabric.metrics.util.Observer o);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    /**
   * @return true iff there are any observers of this subject, currently.
   */
    public boolean isObserved();
    
    /**
   * @return a copy of the set of the current observers of this subject.
   */
    public fabric.util.Set getObserversCopy();
    
    /**
   * Mark this subject as modified.
   */
    public void markModified();
    
    /**
   * Clear the modified flag on this subject.
   */
    public void clearModified();
    
    /**
   * @return true iff the modified flag is set.
   */
    public boolean isModified();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Subject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.Subject._Impl) fetch()).get$observers(
                                                                   );
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.Subject._Impl) fetch()).set$observers(
                                                                   val);
        }
        
        public boolean get$modified() {
            return ((fabric.metrics.util.Subject._Impl) fetch()).get$modified();
        }
        
        public boolean set$modified(boolean val) {
            return ((fabric.metrics.util.Subject._Impl) fetch()).set$modified(
                                                                   val);
        }
        
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            return ((fabric.metrics.util.Subject) fetch()).
              fabric$metrics$util$Subject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.Subject) fetch()).observedBy(arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.Subject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObserversCopy() {
            return ((fabric.metrics.util.Subject) fetch()).getObserversCopy();
        }
        
        public void markModified() {
            ((fabric.metrics.util.Subject) fetch()).markModified();
        }
        
        public void clearModified() {
            ((fabric.metrics.util.Subject) fetch()).clearModified();
        }
        
        public boolean isModified() {
            return ((fabric.metrics.util.Subject) fetch()).isModified();
        }
        
        public _Proxy(Subject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.Subject {
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            fabric$lang$Object$();
            return (fabric.metrics.util.Subject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observers;
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers =
          ((fabric.util.HashSet)
             new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
          fabric$util$HashSet$();
        
        public boolean get$modified() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.modified;
        }
        
        public boolean set$modified(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.modified = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean modified = false;
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            this.get$observers().add(o);
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            this.get$observers().remove(o);
        }
        
        /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        /**
   * @return true iff there are any observers of this subject, currently.
   */
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        /**
   * @return a copy of the set of the current observers of this subject.
   */
        public fabric.util.Set getObserversCopy() {
            return ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(this.$getStore()).$getProxy(
                                                                        )).
              fabric$util$HashSet$(this.get$observers());
        }
        
        /**
   * Mark this subject as modified.
   */
        public void markModified() { this.set$modified(true); }
        
        /**
   * Clear the modified flag on this subject.
   */
        public void clearModified() { this.set$modified(false); }
        
        /**
   * @return true iff the modified flag is set.
   */
        public boolean isModified() { return this.get$modified(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.Subject._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.observers, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.modified);
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
            this.observers = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
            this.modified = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.Subject._Impl src =
              (fabric.metrics.util.Subject._Impl) other;
            this.observers = src.observers;
            this.modified = src.modified;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.Subject._Static {
            public _Proxy(fabric.metrics.util.Subject._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.Subject._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  Subject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.Subject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.Subject._Static._Impl.class);
                $instance = (fabric.metrics.util.Subject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.Subject._Static {
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
                return new fabric.metrics.util.Subject._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 122, -93, -16, -57, 81,
    -13, -83, -111, -15, -124, 19, 78, 56, 94, 108, -47, -70, 58, 54, -66, 83,
    88, 84, 72, 113, -68, 114, -95, -63, 42, 72, 51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
