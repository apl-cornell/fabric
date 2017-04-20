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
            this.set$observers(
                   ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(
                        this.$getStore()).$getProxy()).fabric$util$HashSet$());
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
        
        private fabric.util.Set observers;
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm60 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff61 = 1;
                boolean $doBackoff62 = true;
                $label56: for (boolean $commit57 = false; !$commit57; ) {
                    if ($doBackoff62) {
                        if ($backoff61 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff61);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e58) {  }
                            }
                        }
                        if ($backoff61 < 5000) $backoff61 *= 2;
                    }
                    $doBackoff62 = $backoff61 <= 32 || !$doBackoff62;
                    $commit57 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().add(o); }
                    catch (final fabric.worker.RetryException $e58) {
                        $commit57 = false;
                        continue $label56;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e58) {
                        $commit57 = false;
                        fabric.common.TransactionID $currentTid59 =
                          $tm60.getCurrentTid();
                        if ($e58.tid.isDescendantOf($currentTid59))
                            continue $label56;
                        if ($currentTid59.parent != null) throw $e58;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e58) {
                        $commit57 = false;
                        if ($tm60.checkForStaleObjects()) continue $label56;
                        throw new fabric.worker.AbortException($e58);
                    }
                    finally {
                        if ($commit57) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e58) {
                                $commit57 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e58) {
                                $commit57 = false;
                                fabric.common.TransactionID $currentTid59 =
                                  $tm60.getCurrentTid();
                                if ($currentTid59 != null) {
                                    if ($e58.tid.equals($currentTid59) ||
                                          !$e58.tid.isDescendantOf(
                                                      $currentTid59)) {
                                        throw $e58;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit57) {  }
                    }
                }
            }
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm67 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff68 = 1;
                boolean $doBackoff69 = true;
                $label63: for (boolean $commit64 = false; !$commit64; ) {
                    if ($doBackoff69) {
                        if ($backoff68 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff68);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e65) {  }
                            }
                        }
                        if ($backoff68 < 5000) $backoff68 *= 2;
                    }
                    $doBackoff69 = $backoff68 <= 32 || !$doBackoff69;
                    $commit64 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().remove(o); }
                    catch (final fabric.worker.RetryException $e65) {
                        $commit64 = false;
                        continue $label63;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e65) {
                        $commit64 = false;
                        fabric.common.TransactionID $currentTid66 =
                          $tm67.getCurrentTid();
                        if ($e65.tid.isDescendantOf($currentTid66))
                            continue $label63;
                        if ($currentTid66.parent != null) throw $e65;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e65) {
                        $commit64 = false;
                        if ($tm67.checkForStaleObjects()) continue $label63;
                        throw new fabric.worker.AbortException($e65);
                    }
                    finally {
                        if ($commit64) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e65) {
                                $commit64 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e65) {
                                $commit64 = false;
                                fabric.common.TransactionID $currentTid66 =
                                  $tm67.getCurrentTid();
                                if ($currentTid66 != null) {
                                    if ($e65.tid.equals($currentTid66) ||
                                          !$e65.tid.isDescendantOf(
                                                      $currentTid66)) {
                                        throw $e65;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit64) {  }
                    }
                }
            }
        }
        
        /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
        public boolean observedBy(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm74 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff75 = 1;
                boolean $doBackoff76 = true;
                $label70: for (boolean $commit71 = false; !$commit71; ) {
                    if ($doBackoff76) {
                        if ($backoff75 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff75);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e72) {  }
                            }
                        }
                        if ($backoff75 < 5000) $backoff75 *= 2;
                    }
                    $doBackoff76 = $backoff75 <= 32 || !$doBackoff76;
                    $commit71 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return this.get$observers().contains(o); }
                    catch (final fabric.worker.RetryException $e72) {
                        $commit71 = false;
                        continue $label70;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e72) {
                        $commit71 = false;
                        fabric.common.TransactionID $currentTid73 =
                          $tm74.getCurrentTid();
                        if ($e72.tid.isDescendantOf($currentTid73))
                            continue $label70;
                        if ($currentTid73.parent != null) throw $e72;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e72) {
                        $commit71 = false;
                        if ($tm74.checkForStaleObjects()) continue $label70;
                        throw new fabric.worker.AbortException($e72);
                    }
                    finally {
                        if ($commit71) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e72) {
                                $commit71 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e72) {
                                $commit71 = false;
                                fabric.common.TransactionID $currentTid73 =
                                  $tm74.getCurrentTid();
                                if ($currentTid73 != null) {
                                    if ($e72.tid.equals($currentTid73) ||
                                          !$e72.tid.isDescendantOf(
                                                      $currentTid73)) {
                                        throw $e72;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit71) {  }
                    }
                }
            }
            return false;
        }
        
        /**
   * @return true iff there are any observers of this subject, currently.
   */
        public boolean isObserved() {
            {
                fabric.worker.transaction.TransactionManager $tm81 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff82 = 1;
                boolean $doBackoff83 = true;
                $label77: for (boolean $commit78 = false; !$commit78; ) {
                    if ($doBackoff83) {
                        if ($backoff82 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff82);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e79) {  }
                            }
                        }
                        if ($backoff82 < 5000) $backoff82 *= 2;
                    }
                    $doBackoff83 = $backoff82 <= 32 || !$doBackoff83;
                    $commit78 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { return !this.get$observers().isEmpty(); }
                    catch (final fabric.worker.RetryException $e79) {
                        $commit78 = false;
                        continue $label77;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e79) {
                        $commit78 = false;
                        fabric.common.TransactionID $currentTid80 =
                          $tm81.getCurrentTid();
                        if ($e79.tid.isDescendantOf($currentTid80))
                            continue $label77;
                        if ($currentTid80.parent != null) throw $e79;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e79) {
                        $commit78 = false;
                        if ($tm81.checkForStaleObjects()) continue $label77;
                        throw new fabric.worker.AbortException($e79);
                    }
                    finally {
                        if ($commit78) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e79) {
                                $commit78 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e79) {
                                $commit78 = false;
                                fabric.common.TransactionID $currentTid80 =
                                  $tm81.getCurrentTid();
                                if ($currentTid80 != null) {
                                    if ($e79.tid.equals($currentTid80) ||
                                          !$e79.tid.isDescendantOf(
                                                      $currentTid80)) {
                                        throw $e79;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit78) {  }
                    }
                }
            }
            return false;
        }
        
        /**
   * @return a copy of the set of the current observers of this subject.
   */
        public fabric.util.Set getObserversCopy() {
            {
                fabric.worker.transaction.TransactionManager $tm88 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff89 = 1;
                boolean $doBackoff90 = true;
                $label84: for (boolean $commit85 = false; !$commit85; ) {
                    if ($doBackoff90) {
                        if ($backoff89 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff89);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e86) {  }
                            }
                        }
                        if ($backoff89 < 5000) $backoff89 *= 2;
                    }
                    $doBackoff90 = $backoff89 <= 32 || !$doBackoff90;
                    $commit85 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        return ((fabric.util.LinkedHashSet)
                                  new fabric.util.LinkedHashSet._Impl(
                                    this.$getStore()).$getProxy()).
                          fabric$util$LinkedHashSet$(this.get$observers());
                    }
                    catch (final fabric.worker.RetryException $e86) {
                        $commit85 = false;
                        continue $label84;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e86) {
                        $commit85 = false;
                        fabric.common.TransactionID $currentTid87 =
                          $tm88.getCurrentTid();
                        if ($e86.tid.isDescendantOf($currentTid87))
                            continue $label84;
                        if ($currentTid87.parent != null) throw $e86;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e86) {
                        $commit85 = false;
                        if ($tm88.checkForStaleObjects()) continue $label84;
                        throw new fabric.worker.AbortException($e86);
                    }
                    finally {
                        if ($commit85) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e86) {
                                $commit85 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e86) {
                                $commit85 = false;
                                fabric.common.TransactionID $currentTid87 =
                                  $tm88.getCurrentTid();
                                if ($currentTid87 != null) {
                                    if ($e86.tid.equals($currentTid87) ||
                                          !$e86.tid.isDescendantOf(
                                                      $currentTid87)) {
                                        throw $e86;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit85) {  }
                    }
                }
            }
            return null;
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
                fabric.worker.transaction.TransactionManager $tm95 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff96 = 1;
                boolean $doBackoff97 = true;
                $label91: for (boolean $commit92 = false; !$commit92; ) {
                    if ($doBackoff97) {
                        if ($backoff96 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff96);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e93) {  }
                            }
                        }
                        if ($backoff96 < 5000) $backoff96 *= 2;
                    }
                    $doBackoff97 = $backoff96 <= 32 || !$doBackoff97;
                    $commit92 = true;
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
                    catch (final fabric.worker.RetryException $e93) {
                        $commit92 = false;
                        continue $label91;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e93) {
                        $commit92 = false;
                        fabric.common.TransactionID $currentTid94 =
                          $tm95.getCurrentTid();
                        if ($e93.tid.isDescendantOf($currentTid94))
                            continue $label91;
                        if ($currentTid94.parent != null) throw $e93;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e93) {
                        $commit92 = false;
                        if ($tm95.checkForStaleObjects()) continue $label91;
                        throw new fabric.worker.AbortException($e93);
                    }
                    finally {
                        if ($commit92) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e93) {
                                $commit92 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e93) {
                                $commit92 = false;
                                fabric.common.TransactionID $currentTid94 =
                                  $tm95.getCurrentTid();
                                if ($currentTid94 != null) {
                                    if ($e93.tid.equals($currentTid94) ||
                                          !$e93.tid.isDescendantOf(
                                                      $currentTid94)) {
                                        throw $e93;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit92) {  }
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
    
    public static final byte[] $classHash = new byte[] { -113, -25, -27, 63,
    -25, 0, -68, 28, 27, 5, 115, -78, 0, 71, -55, 3, 66, -2, -105, -14, 112, 65,
    96, -18, -102, 40, -14, 2, -39, 90, 4, 101 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492661970000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
