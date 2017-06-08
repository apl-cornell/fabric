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
    
    public static final byte[] $classHash = new byte[] { 3, -83, 32, -65, -124,
    -107, -44, -54, -17, -117, -33, 113, -74, -48, -41, -124, 36, -61, 9, 108,
    45, -51, -1, -82, 109, -40, 28, -52, -30, -5, 99, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496782676000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
