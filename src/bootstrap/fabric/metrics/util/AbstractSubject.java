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
    
    public static final byte[] $classHash = new byte[] { -9, -112, -14, -43, 19,
    67, -47, -47, -74, -72, -105, 40, -118, 2, -56, -121, -108, -34, 93, 83, 72,
    -54, 54, -51, -112, 11, 64, -87, 59, -49, 121, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527105303000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
