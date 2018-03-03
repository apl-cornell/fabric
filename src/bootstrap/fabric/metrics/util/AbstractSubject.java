package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.Collections;
import java.util.LinkedList;
import fabric.util.Set;
import fabric.util.ArrayList;
import fabric.util.List;
import fabric.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public fabric.util.ArrayList get$observers();
    
    public fabric.util.ArrayList set$observers(fabric.util.ArrayList val);
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.util.List getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
        public fabric.util.ArrayList get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.util.ArrayList set$observers(fabric.util.ArrayList val) {
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
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject arg1,
          fabric.metrics.util.Observer arg2) {
            fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                        arg1, arg2);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.AbstractSubject) fetch()).observedBy(
                                                                     arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).isObserved();
        }
        
        public fabric.util.List getObservers() {
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
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public fabric.util.ArrayList get$observers() { return this.observers; }
        
        public fabric.util.ArrayList set$observers(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.ArrayList observers;
        
        public void addObserver(fabric.metrics.util.Observer o) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_addObserver((fabric.metrics.util.AbstractSubject)
                                   this.$getProxy(), o);
        }
        
        private static void static_addObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$observers().contains(o))
                    tmp.get$observers().add(o);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm600 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled603 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff601 = 1;
                    boolean $doBackoff602 = true;
                    boolean $retry597 = true;
                    $label595: for (boolean $commit596 = false; !$commit596; ) {
                        if ($backoffEnabled603) {
                            if ($doBackoff602) {
                                if ($backoff601 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff601);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e598) {
                                            
                                        }
                                    }
                                }
                                if ($backoff601 < 5000) $backoff601 *= 2;
                            }
                            $doBackoff602 = $backoff601 <= 32 || !$doBackoff602;
                        }
                        $commit596 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e598) {
                            $commit596 = false;
                            continue $label595;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e598) {
                            $commit596 = false;
                            fabric.common.TransactionID $currentTid599 =
                              $tm600.getCurrentTid();
                            if ($e598.tid.isDescendantOf($currentTid599))
                                continue $label595;
                            if ($currentTid599.parent != null) {
                                $retry597 = false;
                                throw $e598;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e598) {
                            $commit596 = false;
                            if ($tm600.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid599 =
                              $tm600.getCurrentTid();
                            if ($e598.tid.isDescendantOf($currentTid599)) {
                                $retry597 = true;
                            }
                            else if ($currentTid599.parent != null) {
                                $retry597 = false;
                                throw $e598;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e598) {
                            $commit596 = false;
                            if ($tm600.checkForStaleObjects())
                                continue $label595;
                            $retry597 = false;
                            throw new fabric.worker.AbortException($e598);
                        }
                        finally {
                            if ($commit596) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e598) {
                                    $commit596 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e598) {
                                    $commit596 = false;
                                    fabric.common.TransactionID $currentTid599 =
                                      $tm600.getCurrentTid();
                                    if ($currentTid599 != null) {
                                        if ($e598.tid.equals($currentTid599) ||
                                              !$e598.tid.isDescendantOf(
                                                           $currentTid599)) {
                                            throw $e598;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit596 && $retry597) {
                                {  }
                                continue $label595;
                            }
                        }
                    }
                }
            }
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_removeObserver((fabric.metrics.util.AbstractSubject)
                                      this.$getProxy(), o);
        }
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.get$observers().remove(o);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm609 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled612 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff610 = 1;
                    boolean $doBackoff611 = true;
                    boolean $retry606 = true;
                    $label604: for (boolean $commit605 = false; !$commit605; ) {
                        if ($backoffEnabled612) {
                            if ($doBackoff611) {
                                if ($backoff610 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff610);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e607) {
                                            
                                        }
                                    }
                                }
                                if ($backoff610 < 5000) $backoff610 *= 2;
                            }
                            $doBackoff611 = $backoff610 <= 32 || !$doBackoff611;
                        }
                        $commit605 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.get$observers().remove(o); }
                        catch (final fabric.worker.RetryException $e607) {
                            $commit605 = false;
                            continue $label604;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e607) {
                            $commit605 = false;
                            fabric.common.TransactionID $currentTid608 =
                              $tm609.getCurrentTid();
                            if ($e607.tid.isDescendantOf($currentTid608))
                                continue $label604;
                            if ($currentTid608.parent != null) {
                                $retry606 = false;
                                throw $e607;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e607) {
                            $commit605 = false;
                            if ($tm609.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid608 =
                              $tm609.getCurrentTid();
                            if ($e607.tid.isDescendantOf($currentTid608)) {
                                $retry606 = true;
                            }
                            else if ($currentTid608.parent != null) {
                                $retry606 = false;
                                throw $e607;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e607) {
                            $commit605 = false;
                            if ($tm609.checkForStaleObjects())
                                continue $label604;
                            $retry606 = false;
                            throw new fabric.worker.AbortException($e607);
                        }
                        finally {
                            if ($commit605) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e607) {
                                    $commit605 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e607) {
                                    $commit605 = false;
                                    fabric.common.TransactionID $currentTid608 =
                                      $tm609.getCurrentTid();
                                    if ($currentTid608 != null) {
                                        if ($e607.tid.equals($currentTid608) ||
                                              !$e607.tid.isDescendantOf(
                                                           $currentTid608)) {
                                            throw $e607;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit605 && $retry606) {
                                {  }
                                continue $label604;
                            }
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        public fabric.util.List getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   *
   * @param unobserved
   *            a {@link LinkedList} of {@link SampledMetric}s that have been
   *            updated and still need to have Observers compute the resulting
   *            effects for.
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
                fabric.util.List obs = sm.getObservers();
                int size = obs.size();
                for (int i = 0; i < size; i++) {
                    queue.add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  obs.get(i)));
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
                    fabric.lang.arrays.ObjectArray leaves =
                      unhandled.getLeafSubjects();
                    for (int i = 0; i < leaves.get$length(); i++) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.SampledMetric)
                                            leaves.get(i)))) {
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
                            fabric.util.List parents =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers();
                            int pSize = parents.size();
                            for (int i = 0; i < pSize; i++) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parents.get(i)));
                            }
                        }
                    }
                    else {
                        delayed.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  unhandled));
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
                            fabric.lang.arrays.ObjectArray withheldLeaves =
                              withheld.getLeafSubjects();
                            for (int i = 0; i < withheldLeaves.get$length();
                                 i++) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap((fabric.metrics.SampledMetric)
                                                    withheldLeaves.get(i)))) {
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
            this.observers = (fabric.util.ArrayList)
                               $readRef(fabric.util.ArrayList._Proxy.class,
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
    
    public static final byte[] $classHash = new byte[] { -100, 55, 91, 114, -44,
    119, 33, -24, -71, 49, -27, -58, -64, 46, -22, 35, 42, -12, 37, -85, -99,
    -52, 62, 45, -69, -36, 95, -31, 18, 66, 15, -35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
