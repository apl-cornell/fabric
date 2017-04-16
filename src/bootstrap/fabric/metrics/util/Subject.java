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
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            fabric.common.Logging.METRICS_LOGGER.
              info(
                "SUBJECT " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.util.Subject)
                                    this.$getProxy())) +
                  " OBSERVED BY " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         o)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            this.get$observers().add(o);
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            fabric.common.Logging.METRICS_LOGGER.
              info(
                "SUBJECT " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.util.Subject)
                                    this.$getProxy())) +
                  " NO LONGER OBSERVED BY " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         o)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
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
        public static void processSamples(java.util.LinkedList unobserved,
                                          java.util.List extensions) {
            {
                fabric.worker.transaction.TransactionManager $tm47 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff48 = 1;
                boolean $doBackoff49 = true;
                $label43: for (boolean $commit44 = false; !$commit44; ) {
                    if ($doBackoff49) {
                        if ($backoff48 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff48);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e45) {  }
                            }
                        }
                        if ($backoff48 < 5000) $backoff48 *= 2;
                    }
                    $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                    $commit44 = true;
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
                                        fabric.metrics.util.Subject
                                          s =
                                          (fabric.metrics.util.Subject)
                                            fabric.lang.Object._Proxy.
                                            $getProxy(unhandled);
                                        if (fabric.lang.Object._Proxy.
                                              $getProxy(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(
                                                    s)) instanceof fabric.metrics.contracts.Contract) {
                                            extensions.
                                              remove(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(s));
                                        }
                                        fabric.util.Iterator parentIter =
                                          s.getObservers().iterator();
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
                    catch (final fabric.worker.RetryException $e45) {
                        $commit44 = false;
                        continue $label43;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e45) {
                        $commit44 = false;
                        fabric.common.TransactionID $currentTid46 =
                          $tm47.getCurrentTid();
                        if ($e45.tid.isDescendantOf($currentTid46))
                            continue $label43;
                        if ($currentTid46.parent != null) throw $e45;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e45) {
                        $commit44 = false;
                        if ($tm47.checkForStaleObjects()) continue $label43;
                        throw new fabric.worker.AbortException($e45);
                    }
                    finally {
                        if ($commit44) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e45) {
                                $commit44 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e45) {
                                $commit44 = false;
                                fabric.common.TransactionID $currentTid46 =
                                  $tm47.getCurrentTid();
                                if ($currentTid46 != null) {
                                    if ($e45.tid.equals($currentTid46) ||
                                          !$e45.tid.isDescendantOf(
                                                      $currentTid46)) {
                                        throw $e45;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit44) {  }
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
    
    public static final byte[] $classHash = new byte[] { 83, 42, 95, -12, -101,
    -23, 92, 122, 110, 2, 3, 30, 37, 48, -53, 118, -125, 61, -61, -9, -73, -26,
    16, 57, -11, 95, 36, 127, -105, 21, -107, 90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492364524000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
