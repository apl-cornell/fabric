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
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.
 */
public interface Subject extends fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.Subject fabric$metrics$util$Subject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
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
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.Subject) fetch()).getStore();
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
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public static void processSamples(java.util.LinkedList arg1) {
            fabric.metrics.util.Subject._Impl.processSamples(arg1);
        }
        
        public _Proxy(Subject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.Subject {
        public fabric.worker.Store getStore() { return $getStore(); }
        
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
   * @return the set of the current observers of this subject.
   */
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved) {
            {
                fabric.worker.transaction.TransactionManager $tm54 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff55 = 1;
                boolean $doBackoff56 = true;
                $label50: for (boolean $commit51 = false; !$commit51; ) {
                    if ($doBackoff56) {
                        if ($backoff55 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff55);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e52) {  }
                            }
                        }
                        if ($backoff55 < 5000) $backoff55 *= 2;
                    }
                    $doBackoff56 = $backoff55 <= 32 || !$doBackoff56;
                    $commit51 = true;
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
                    catch (final fabric.worker.RetryException $e52) {
                        $commit51 = false;
                        continue $label50;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e52) {
                        $commit51 = false;
                        fabric.common.TransactionID $currentTid53 =
                          $tm54.getCurrentTid();
                        if ($e52.tid.isDescendantOf($currentTid53))
                            continue $label50;
                        if ($currentTid53.parent != null) throw $e52;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e52) {
                        $commit51 = false;
                        if ($tm54.checkForStaleObjects()) continue $label50;
                        throw new fabric.worker.AbortException($e52);
                    }
                    finally {
                        if ($commit51) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e52) {
                                $commit51 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e52) {
                                $commit51 = false;
                                fabric.common.TransactionID $currentTid53 =
                                  $tm54.getCurrentTid();
                                if ($currentTid53 != null) {
                                    if ($e52.tid.equals($currentTid53) ||
                                          !$e52.tid.isDescendantOf(
                                                      $currentTid53)) {
                                        throw $e52;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit51) {  }
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
            fabric.metrics.util.Subject._Impl src =
              (fabric.metrics.util.Subject._Impl) other;
            this.observers = src.observers;
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
    
    public static final byte[] $classHash = new byte[] { 118, -54, -115, 102,
    -53, -123, -117, -73, -84, -119, 30, -15, -107, 98, -110, 29, 122, 98, 108,
    102, -39, -12, 91, -51, -82, -71, 125, 84, 101, -60, -93, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492535467000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
