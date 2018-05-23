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
import fabric.common.util.LongSet;
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
                    fabric.worker.transaction.TransactionManager $tm623 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled626 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff624 = 1;
                    boolean $doBackoff625 = true;
                    boolean $retry620 = true;
                    $label618: for (boolean $commit619 = false; !$commit619; ) {
                        if ($backoffEnabled626) {
                            if ($doBackoff625) {
                                if ($backoff624 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff624);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e621) {
                                            
                                        }
                                    }
                                }
                                if ($backoff624 < 5000) $backoff624 *= 2;
                            }
                            $doBackoff625 = $backoff624 <= 32 || !$doBackoff625;
                        }
                        $commit619 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e621) {
                            $commit619 = false;
                            continue $label618;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e621) {
                            $commit619 = false;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622))
                                continue $label618;
                            if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622)) {
                                $retry620 = true;
                            }
                            else if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects())
                                continue $label618;
                            $retry620 = false;
                            throw new fabric.worker.AbortException($e621);
                        }
                        finally {
                            if ($commit619) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e621) {
                                    $commit619 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e621) {
                                    $commit619 = false;
                                    fabric.common.TransactionID $currentTid622 =
                                      $tm623.getCurrentTid();
                                    if ($currentTid622 != null) {
                                        if ($e621.tid.equals($currentTid622) ||
                                              !$e621.tid.isDescendantOf(
                                                           $currentTid622)) {
                                            throw $e621;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit619 && $retry620) {
                                {  }
                                continue $label618;
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
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e630) {
                            $commit628 = false;
                            if ($tm632.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid631 =
                              $tm632.getCurrentTid();
                            if ($e630.tid.isDescendantOf($currentTid631)) {
                                $retry629 = true;
                            }
                            else if ($currentTid631.parent != null) {
                                $retry629 = false;
                                throw $e630;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
                                   $wrap(unhandled.first))).
                          handleUpdates(((java.lang.Boolean)
                                           unhandled.second).booleanValue(),
                                        (fabric.common.util.LongSet)
                                          unhandled.third);
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
    
    public static final byte[] $classHash = new byte[] { 46, 85, -48, 98, 76,
    -65, 120, 5, -99, -63, 98, -118, -34, -78, -26, 107, -64, 63, -39, -22, 78,
    -36, 55, -89, 36, 27, -60, 47, -106, -56, -38, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527105303000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
