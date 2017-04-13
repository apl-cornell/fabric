package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.
 */
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
        
        public static void processSamples(java.util.LinkedList arg1,
                                          java.util.List arg2) {
            fabric.metrics.util.Subject._Impl.processSamples(arg1, arg2);
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
            return ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(this.$getStore()).
                      $getProxy()).fabric$util$LinkedHashSet$(
                                     this.get$observers());
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
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved,
                                          java.util.List extensions) {
            while (!unobserved.isEmpty()) {
                fabric.metrics.SampledMetric
                  sm =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(
                                                          unobserved.poll()));
                sm.clearModified();
                java.util.LinkedList queue = new java.util.LinkedList();
                fabric.util.Iterator obsIter = sm.getObservers().iterator();
                while (obsIter.hasNext()) {
                    queue.add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  obsIter.next(
                                                                            )));
                }
                while (!queue.isEmpty()) {
                    fabric.metrics.util.Observer
                      unhandled =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              queue.poll()));
                    boolean needToWait = false;
                    fabric.util.Iterator leavesIter =
                      unhandled.getLeafSubjects().iterator();
                    while (leavesIter.hasNext()) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(leavesIter.next()))) {
                            needToWait = true;
                            break;
                        }
                    }
                    if (!needToWait) {
                        fabric.util.Set parents =
                          fabric.util.Collections._Static._Proxy.$instance.
                          get$EMPTY_SET();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject) {
                            parents =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                                getObserversCopy();
                        }
                        unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              isModified()) {
                            fabric.metrics.util.Subject s =
                              (fabric.metrics.util.Subject)
                                fabric.lang.Object._Proxy.$getProxy(unhandled);
                            if (fabric.lang.Object._Proxy.
                                  $getProxy(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(s)) instanceof fabric.metrics.contracts.Contract) {
                                extensions.
                                  remove(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          s));
                            }
                            fabric.util.Iterator parentIter =
                              parents.iterator();
                            while (parentIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parentIter.next()));
                            }
                            s.clearModified();
                        }
                    }
                }
            }
        }
        
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
    
    public static final byte[] $classHash = new byte[] { -56, -13, 102, 105,
    -40, -79, 55, -105, -17, -32, 120, 19, -48, 123, -80, 78, 91, -95, 107, -5,
    95, -37, 125, -26, -105, 61, 67, -117, -36, 120, -30, -98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492108498000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
