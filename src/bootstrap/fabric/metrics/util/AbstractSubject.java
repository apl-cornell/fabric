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
                    fabric.worker.transaction.TransactionManager $tm604 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled607 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff605 = 1;
                    boolean $doBackoff606 = true;
                    boolean $retry601 = true;
                    $label599: for (boolean $commit600 = false; !$commit600; ) {
                        if ($backoffEnabled607) {
                            if ($doBackoff606) {
                                if ($backoff605 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff605);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e602) {
                                            
                                        }
                                    }
                                }
                                if ($backoff605 < 5000) $backoff605 *= 2;
                            }
                            $doBackoff606 = $backoff605 <= 32 || !$doBackoff606;
                        }
                        $commit600 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e602) {
                            $commit600 = false;
                            continue $label599;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e602) {
                            $commit600 = false;
                            fabric.common.TransactionID $currentTid603 =
                              $tm604.getCurrentTid();
                            if ($e602.tid.isDescendantOf($currentTid603))
                                continue $label599;
                            if ($currentTid603.parent != null) {
                                $retry601 = false;
                                throw $e602;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e602) {
                            $commit600 = false;
                            if ($tm604.checkForStaleObjects())
                                continue $label599;
                            $retry601 = false;
                            throw new fabric.worker.AbortException($e602);
                        }
                        finally {
                            if ($commit600) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e602) {
                                    $commit600 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e602) {
                                    $commit600 = false;
                                    fabric.common.TransactionID $currentTid603 =
                                      $tm604.getCurrentTid();
                                    if ($currentTid603 != null) {
                                        if ($e602.tid.equals($currentTid603) ||
                                              !$e602.tid.isDescendantOf(
                                                           $currentTid603)) {
                                            throw $e602;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit600 && $retry601) {
                                {  }
                                continue $label599;
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
                    fabric.worker.transaction.TransactionManager $tm613 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled616 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff614 = 1;
                    boolean $doBackoff615 = true;
                    boolean $retry610 = true;
                    $label608: for (boolean $commit609 = false; !$commit609; ) {
                        if ($backoffEnabled616) {
                            if ($doBackoff615) {
                                if ($backoff614 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff614);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e611) {
                                            
                                        }
                                    }
                                }
                                if ($backoff614 < 5000) $backoff614 *= 2;
                            }
                            $doBackoff615 = $backoff614 <= 32 || !$doBackoff615;
                        }
                        $commit609 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.get$observers().remove(o); }
                        catch (final fabric.worker.RetryException $e611) {
                            $commit609 = false;
                            continue $label608;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e611) {
                            $commit609 = false;
                            fabric.common.TransactionID $currentTid612 =
                              $tm613.getCurrentTid();
                            if ($e611.tid.isDescendantOf($currentTid612))
                                continue $label608;
                            if ($currentTid612.parent != null) {
                                $retry610 = false;
                                throw $e611;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e611) {
                            $commit609 = false;
                            if ($tm613.checkForStaleObjects())
                                continue $label608;
                            $retry610 = false;
                            throw new fabric.worker.AbortException($e611);
                        }
                        finally {
                            if ($commit609) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e611) {
                                    $commit609 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e611) {
                                    $commit609 = false;
                                    fabric.common.TransactionID $currentTid612 =
                                      $tm613.getCurrentTid();
                                    if ($currentTid612 != null) {
                                        if ($e611.tid.equals($currentTid612) ||
                                              !$e611.tid.isDescendantOf(
                                                           $currentTid612)) {
                                            throw $e611;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit609 && $retry610) {
                                {  }
                                continue $label608;
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
