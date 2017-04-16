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
                            sm.clearModified();
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
                                    unhandled.handleUpdates();
                                    if (fabric.lang.Object._Proxy.
                                          $getProxy(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                unhandled)) instanceof fabric.metrics.util.Subject &&
                                          ((fabric.metrics.util.Subject)
                                             fabric.lang.Object._Proxy.
                                             $getProxy(unhandled)).isModified(
                                                                     )) {
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
                                        s.clearModified();
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
    
    public static final byte[] $classHash = new byte[] { 44, -103, 92, 40, -82,
    102, -17, 57, -40, 114, 127, 91, 75, -97, 17, 33, -118, 124, 107, 40, 48,
    54, 47, 16, -90, -39, -68, 82, -114, 116, -102, 82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
