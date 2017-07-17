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
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import java.util.logging.Level;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              set$observers(val);
        }
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getStore();
        }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).
              fabric$metrics$util$AbstractSubject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).removeObserver(
                                                              arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.AbstractSubject) fetch()).observedBy(
                                                                     arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getObservers(
                                                                     );
        }
        
        public static void processSamples(java.util.LinkedList arg1) {
            fabric.metrics.util.AbstractSubject._Impl.processSamples(arg1);
        }
        
        public _Proxy(AbstractSubject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.AbstractSubject {
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            this.set$observers(
                   ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$util$LinkedHashSet$());
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() { return this.observers; }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers;
        
        public void addObserver(fabric.metrics.util.Observer o) {
            this.get$observers().add(o);
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            this.get$observers().remove(o);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved) {
            java.util.LinkedList queue = new java.util.LinkedList();
            java.util.LinkedList delayed = new java.util.LinkedList();
            while (!unobserved.isEmpty()) {
                fabric.metrics.SampledMetric
                  sm =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(
                                                          unobserved.poll()));
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
                    delayed.remove(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                    unhandled));
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
                        boolean modified = unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              modified) {
                            fabric.util.Iterator parentIter =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers().iterator();
                            while (parentIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parentIter.next()));
                            }
                        }
                        else {
                            delayed.
                              add(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(unhandled));
                        }
                        if (queue.isEmpty()) {
                            java.util.Iterator delayedIter = delayed.iterator();
                            while (delayedIter.hasNext()) {
                                fabric.metrics.util.Observer
                                  withheld =
                                  (fabric.metrics.util.Observer)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      fabric.lang.WrappedJavaInlineable.
                                          $wrap(delayedIter.next()));
                                boolean doneWaiting = true;
                                fabric.util.Iterator withheldLeavesIter =
                                  withheld.getLeafSubjects().iterator();
                                while (withheldLeavesIter.hasNext()) {
                                    if (unobserved.
                                          contains(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                withheldLeavesIter.next()))) {
                                        doneWaiting = false;
                                        break;
                                    }
                                }
                                if (doneWaiting) {
                                    queue.
                                      add(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(withheld));
                                }
                            }
                        }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.AbstractSubject._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.observers, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.AbstractSubject._Impl src =
              (fabric.metrics.util.AbstractSubject._Impl) other;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.AbstractSubject._Static {
            public _Proxy(fabric.metrics.util.AbstractSubject._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.AbstractSubject._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  AbstractSubject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.AbstractSubject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.AbstractSubject._Static._Impl.class);
                $instance = (fabric.metrics.util.AbstractSubject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.AbstractSubject._Static {
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
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 67, -53, -23, 32, -54,
    -4, -118, -59, 54, 69, 14, 39, -15, 68, 28, -19, 124, 55, -4, -59, 71, 69,
    -75, 72, 96, 73, 117, 61, 86, -8, -56, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500326775000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
