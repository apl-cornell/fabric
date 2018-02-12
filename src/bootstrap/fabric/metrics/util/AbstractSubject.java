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
    
    public fabric.util.Collection getObservers();
    
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
        
        public fabric.util.Collection getObservers() {
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
                    fabric.worker.transaction.TransactionManager $tm632 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled635 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff633 = 1;
                    boolean $doBackoff634 = true;
                    boolean $retry629 = true;
                    $label627: for (boolean $commit628 = false; !$commit628; ) {
                        if ($backoffEnabled635) {
                            if ($doBackoff634) {
                                if ($backoff633 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff633);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e630) {
                                            
                                        }
                                    }
                                }
                                if ($backoff633 < 5000) $backoff633 *= 2;
                            }
                            $doBackoff634 = $backoff633 <= 32 || !$doBackoff634;
                        }
                        $commit628 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e630) {
                            $commit628 = false;
                            continue $label627;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e630) {
                            $commit628 = false;
                            fabric.common.TransactionID $currentTid631 =
                              $tm632.getCurrentTid();
                            if ($e630.tid.isDescendantOf($currentTid631))
                                continue $label627;
                            if ($currentTid631.parent != null) {
                                $retry629 = false;
                                throw $e630;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e630) {
                            $commit628 = false;
                            if ($tm632.checkForStaleObjects())
                                continue $label627;
                            $retry629 = false;
                            throw new fabric.worker.AbortException($e630);
                        }
                        finally {
                            if ($commit628) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e630) {
                                    $commit628 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e630) {
                                    $commit628 = false;
                                    fabric.common.TransactionID $currentTid631 =
                                      $tm632.getCurrentTid();
                                    if ($currentTid631 != null) {
                                        if ($e630.tid.equals($currentTid631) ||
                                              !$e630.tid.isDescendantOf(
                                                           $currentTid631)) {
                                            throw $e630;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit628 && $retry629) {
                                {  }
                                continue $label627;
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
                    fabric.worker.transaction.TransactionManager $tm641 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled644 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff642 = 1;
                    boolean $doBackoff643 = true;
                    boolean $retry638 = true;
                    $label636: for (boolean $commit637 = false; !$commit637; ) {
                        if ($backoffEnabled644) {
                            if ($doBackoff643) {
                                if ($backoff642 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff642);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e639) {
                                            
                                        }
                                    }
                                }
                                if ($backoff642 < 5000) $backoff642 *= 2;
                            }
                            $doBackoff643 = $backoff642 <= 32 || !$doBackoff643;
                        }
                        $commit637 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.get$observers().remove(o); }
                        catch (final fabric.worker.RetryException $e639) {
                            $commit637 = false;
                            continue $label636;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e639) {
                            $commit637 = false;
                            fabric.common.TransactionID $currentTid640 =
                              $tm641.getCurrentTid();
                            if ($e639.tid.isDescendantOf($currentTid640))
                                continue $label636;
                            if ($currentTid640.parent != null) {
                                $retry638 = false;
                                throw $e639;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e639) {
                            $commit637 = false;
                            if ($tm641.checkForStaleObjects())
                                continue $label636;
                            $retry638 = false;
                            throw new fabric.worker.AbortException($e639);
                        }
                        finally {
                            if ($commit637) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e639) {
                                    $commit637 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e639) {
                                    $commit637 = false;
                                    fabric.common.TransactionID $currentTid640 =
                                      $tm641.getCurrentTid();
                                    if ($currentTid640 != null) {
                                        if ($e639.tid.equals($currentTid640) ||
                                              !$e639.tid.isDescendantOf(
                                                           $currentTid640)) {
                                            throw $e639;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit637 && $retry638) {
                                {  }
                                continue $label636;
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
        
        public fabric.util.Collection getObservers() {
            return this.get$observers();
        }
        
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
    
    public static final byte[] $classHash = new byte[] { 109, -4, 99, 59, 81,
    93, -118, 79, 124, 119, -55, -17, -123, 50, 30, -27, -5, 27, -77, 21, 37,
    -23, -38, 35, 58, -83, 1, -13, 88, 106, -32, -110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
