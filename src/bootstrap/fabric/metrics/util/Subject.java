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
    
    /**
   * @return the set of the current observers of this subject.
   */
    public fabric.util.Set getObservers();
    
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
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
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
        
        /**
   * @return the set of the current observers of this subject.
   */
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
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
    
    public static final byte[] $classHash = new byte[] { -85, 60, 82, -127, -37,
    -42, 105, 48, -79, -63, 92, -93, -69, -1, 9, -15, 109, -74, 57, -119, 43,
    71, 96, 2, 124, 56, -66, -97, -119, -26, 106, 118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491926740000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
