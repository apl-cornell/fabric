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
            {
                fabric.worker.transaction.TransactionManager $tm189 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff190 = 1;
                boolean $doBackoff191 = true;
                $label185: for (boolean $commit186 = false; !$commit186; ) {
                    if ($doBackoff191) {
                        if ($backoff190 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff190);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e187) {
                                    
                                }
                            }
                        }
                        if ($backoff190 < 5000) $backoff190 *= 2;
                    }
                    $doBackoff191 = $backoff190 <= 32 || !$doBackoff191;
                    $commit186 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.util.LinkedList queue = new java.util.LinkedList();
                        while (!unobserved.isEmpty()) {
                            fabric.metrics.SampledMetric
                              sm =
                              (fabric.metrics.SampledMetric)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(unobserved.poll()));
                            fabric.util.Iterator obsIter =
                              sm.getObservers().iterator();
                            while (obsIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(obsIter.next()));
                            }
                            while (!queue.isEmpty()) {
                                fabric.metrics.util.Observer
                                  unhandled =
                                  (fabric.metrics.util.Observer)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      fabric.lang.WrappedJavaInlineable.
                                          $wrap(queue.poll()));
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
                                    boolean modified =
                                      unhandled.handleUpdates();
                                    if (fabric.lang.Object._Proxy.
                                          $getProxy(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                unhandled)) instanceof fabric.metrics.util.Subject &&
                                          modified) {
                                        fabric.util.Iterator
                                          parentIter =
                                          ((fabric.metrics.util.Subject)
                                             fabric.lang.Object._Proxy.
                                             $getProxy(unhandled)).getObservers(
                                                                     ).iterator(
                                                                         );
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
                    catch (final fabric.worker.RetryException $e187) {
                        $commit186 = false;
                        continue $label185;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e187) {
                        $commit186 = false;
                        fabric.common.TransactionID $currentTid188 =
                          $tm189.getCurrentTid();
                        if ($e187.tid.isDescendantOf($currentTid188))
                            continue $label185;
                        if ($currentTid188.parent != null) throw $e187;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e187) {
                        $commit186 = false;
                        if ($tm189.checkForStaleObjects()) continue $label185;
                        throw new fabric.worker.AbortException($e187);
                    }
                    finally {
                        if ($commit186) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e187) {
                                $commit186 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e187) {
                                $commit186 = false;
                                fabric.common.TransactionID $currentTid188 =
                                  $tm189.getCurrentTid();
                                if ($currentTid188 != null) {
                                    if ($e187.tid.equals($currentTid188) ||
                                          !$e187.tid.isDescendantOf(
                                                       $currentTid188)) {
                                        throw $e187;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit186) {  }
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
    
    public static final byte[] $classHash = new byte[] { -37, 94, 44, 66, -89,
    86, 36, -32, 52, 91, 127, 1, -108, 118, 1, -55, 2, -9, 105, 71, -34, -119,
    -93, -22, 21, 3, -41, 105, 115, -88, 127, 62 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740614000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
