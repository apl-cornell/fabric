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
import java.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import fabric.common.util.Triple;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
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
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
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
            fabric$lang$Object$();
            this.set$$observers(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
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
                if (!tmp.get$$observers().contains(o))
                    tmp.set$$observers(tmp.get$$observers().add(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm5 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled8 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff6 = 1;
                    boolean $doBackoff7 = true;
                    boolean $retry2 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($backoffEnabled8) {
                            if ($doBackoff7) {
                                if ($backoff6 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff6);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e3) {
                                            
                                        }
                                    }
                                }
                                if ($backoff6 < 5000) $backoff6 *= 2;
                            }
                            $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                        }
                        $commit1 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e3) {
                            $commit1 = false;
                            continue $label0;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e3) {
                            $commit1 = false;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4))
                                continue $label0;
                            if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid4 =
                              $tm5.getCurrentTid();
                            if ($e3.tid.isDescendantOf($currentTid4)) {
                                $retry2 = true;
                            }
                            else if ($currentTid4.parent != null) {
                                $retry2 = false;
                                throw $e3;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue $label0;
                            $retry2 = false;
                            throw new fabric.worker.AbortException($e3);
                        }
                        finally {
                            if ($commit1) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e3) {
                                    $commit1 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e3) {
                                    $commit1 = false;
                                    fabric.common.TransactionID $currentTid4 =
                                      $tm5.getCurrentTid();
                                    if ($currentTid4 != null) {
                                        if ($e3.tid.equals($currentTid4) ||
                                              !$e3.tid.isDescendantOf(
                                                         $currentTid4)) {
                                            throw $e3;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit1 && $retry2) {
                                {  }
                                continue $label0;
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
                if (tmp.get$$observers().contains(o))
                    tmp.set$$observers(tmp.get$$observers().remove(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm14 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled17 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff15 = 1;
                    boolean $doBackoff16 = true;
                    boolean $retry11 = true;
                    $label9: for (boolean $commit10 = false; !$commit10; ) {
                        if ($backoffEnabled17) {
                            if ($doBackoff16) {
                                if ($backoff15 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff15);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e12) {
                                            
                                        }
                                    }
                                }
                                if ($backoff15 < 5000) $backoff15 *= 2;
                            }
                            $doBackoff16 = $backoff15 <= 32 || !$doBackoff16;
                        }
                        $commit10 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e12) {
                            $commit10 = false;
                            continue $label9;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e12) {
                            $commit10 = false;
                            fabric.common.TransactionID $currentTid13 =
                              $tm14.getCurrentTid();
                            if ($e12.tid.isDescendantOf($currentTid13))
                                continue $label9;
                            if ($currentTid13.parent != null) {
                                $retry11 = false;
                                throw $e12;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e12) {
                            $commit10 = false;
                            if ($tm14.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid13 =
                              $tm14.getCurrentTid();
                            if ($e12.tid.isDescendantOf($currentTid13)) {
                                $retry11 = true;
                            }
                            else if ($currentTid13.parent != null) {
                                $retry11 = false;
                                throw $e12;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e12) {
                            $commit10 = false;
                            if ($tm14.checkForStaleObjects()) continue $label9;
                            $retry11 = false;
                            throw new fabric.worker.AbortException($e12);
                        }
                        finally {
                            if ($commit10) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e12) {
                                    $commit10 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e12) {
                                    $commit10 = false;
                                    fabric.common.TransactionID $currentTid13 =
                                      $tm14.getCurrentTid();
                                    if ($currentTid13 != null) {
                                        if ($e12.tid.equals($currentTid13) ||
                                              !$e12.tid.isDescendantOf(
                                                          $currentTid13)) {
                                            throw $e12;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit10 && $retry11) {
                                {  }
                                continue $label9;
                            }
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$$observers().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$$observers();
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
                fabric.worker.metrics.ImmutableObserverSet obs =
                  sm.getObservers();
                for (java.util.Iterator iter = obs.iterator(); iter.hasNext();
                     ) {
                    queue.add(iter.next());
                }
                while (!queue.isEmpty()) {
                    fabric.common.util.Triple unhandled =
                      (fabric.common.util.Triple) queue.poll();
                    delayed.remove(unhandled);
                    boolean needToWait = false;
                    fabric.worker.metrics.ImmutableMetricsVector
                      leaves =
                      ((fabric.metrics.util.Observer)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           fabric.lang.WrappedJavaInlineable.
                               $wrap(unhandled.first))).getLeafSubjects();
                    for (int i = 0; i < leaves.length(); i++) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(leaves.get(i)))) {
                            needToWait = true;
                            break;
                        }
                    }
                    if (!needToWait) {
                        fabric.worker.metrics.ImmutableObserverSet
                          parentsToCheck =
                          ((fabric.metrics.util.Observer)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               fabric.lang.WrappedJavaInlineable.
                                   $wrap(unhandled.first))).handleUpdates();
                        for (java.util.Iterator iter =
                               parentsToCheck.iterator();
                             iter.hasNext();
                             ) {
                            queue.add(iter.next());
                        }
                    } else {
                        delayed.add(unhandled);
                    }
                    if (queue.isEmpty()) {
                        java.util.Iterator delayedIter = delayed.iterator();
                        while (delayedIter.hasNext()) {
                            fabric.common.util.Triple withheld =
                              (fabric.common.util.Triple) delayedIter.next();
                            boolean doneWaiting = true;
                            fabric.worker.metrics.ImmutableMetricsVector
                              withheldLeaves =
                              ((fabric.metrics.util.Observer)
                                 fabric.lang.Object._Proxy.
                                 $getProxy(
                                   fabric.lang.WrappedJavaInlineable.
                                       $wrap(withheld.first))).getLeafSubjects(
                                                                 );
                            for (int i = 0; i < withheldLeaves.length(); i++) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(withheldLeaves.get(i)))) {
                                    doneWaiting = false;
                                    break;
                                }
                            }
                            if (doneWaiting) { queue.add(withheld); }
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
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -1, -17, -92, -65, -68,
    -78, -122, -120, -96, 31, -88, 70, -24, 40, -29, -105, -106, -30, -40, -120,
    70, -100, 10, -18, 123, -41, -80, -7, 44, 71, -3, 7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527104354000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
