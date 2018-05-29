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
    
    public void addObserver(fabric.metrics.util.Observer o, long id);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o, long id);
    
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
        
        public void addObserver(fabric.metrics.util.Observer arg1, long arg2) {
            ((fabric.metrics.util.AbstractSubject) fetch()).addObserver(arg1,
                                                                        arg2);
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
        
        public void removeObserver(fabric.metrics.util.Observer arg1,
                                   long arg2) {
            ((fabric.metrics.util.AbstractSubject) fetch()).removeObserver(
                                                              arg1, arg2);
        }
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject arg1,
          fabric.metrics.util.Observer arg2, long arg3) {
            fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                        arg1, arg2, arg3);
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
        
        public void addObserver(fabric.metrics.util.Observer o, long id) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_addObserver((fabric.metrics.util.AbstractSubject)
                                   this.$getProxy(), o, id);
        }
        
        private static void static_addObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o, long id) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$$observers().contains(o, id))
                    tmp.set$$observers(tmp.get$$observers().add(o, id));
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
                            if (!tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().add(o, id));
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
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e639) {
                            $commit637 = false;
                            if ($tm641.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid640 =
                              $tm641.getCurrentTid();
                            if ($e639.tid.isDescendantOf($currentTid640)) {
                                $retry638 = true;
                            }
                            else if ($currentTid640.parent != null) {
                                $retry638 = false;
                                throw $e639;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
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
        
        public void removeObserver(fabric.metrics.util.Observer o, long id) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_removeObserver((fabric.metrics.util.AbstractSubject)
                                      this.$getProxy(), o, id);
        }
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o, long id) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$$observers().contains(o, id))
                    tmp.set$$observers(tmp.get$$observers().remove(o, id));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm650 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled653 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff651 = 1;
                    boolean $doBackoff652 = true;
                    boolean $retry647 = true;
                    $label645: for (boolean $commit646 = false; !$commit646; ) {
                        if ($backoffEnabled653) {
                            if ($doBackoff652) {
                                if ($backoff651 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff651);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e648) {
                                            
                                        }
                                    }
                                }
                                if ($backoff651 < 5000) $backoff651 *= 2;
                            }
                            $doBackoff652 = $backoff651 <= 32 || !$doBackoff652;
                        }
                        $commit646 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o, id));
                        }
                        catch (final fabric.worker.RetryException $e648) {
                            $commit646 = false;
                            continue $label645;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e648) {
                            $commit646 = false;
                            fabric.common.TransactionID $currentTid649 =
                              $tm650.getCurrentTid();
                            if ($e648.tid.isDescendantOf($currentTid649))
                                continue $label645;
                            if ($currentTid649.parent != null) {
                                $retry647 = false;
                                throw $e648;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e648) {
                            $commit646 = false;
                            if ($tm650.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid649 =
                              $tm650.getCurrentTid();
                            if ($e648.tid.isDescendantOf($currentTid649)) {
                                $retry647 = true;
                            }
                            else if ($currentTid649.parent != null) {
                                $retry647 = false;
                                throw $e648;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e648) {
                            $commit646 = false;
                            if ($tm650.checkForStaleObjects())
                                continue $label645;
                            $retry647 = false;
                            throw new fabric.worker.AbortException($e648);
                        }
                        finally {
                            if ($commit646) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e648) {
                                    $commit646 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e648) {
                                    $commit646 = false;
                                    fabric.common.TransactionID $currentTid649 =
                                      $tm650.getCurrentTid();
                                    if ($currentTid649 != null) {
                                        if ($e648.tid.equals($currentTid649) ||
                                              !$e648.tid.isDescendantOf(
                                                           $currentTid649)) {
                                            throw $e648;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit646 && $retry647) {
                                {  }
                                continue $label645;
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
    
    public static final byte[] $classHash = new byte[] { 81, -30, -41, -37, -16,
    51, 37, 1, -33, -121, -66, -5, 114, -45, -127, 16, 8, 73, -12, 96, 15, -112,
    -63, -7, 0, 39, -70, -92, 107, 13, -112, -51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629388000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
