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
                    fabric.worker.transaction.TransactionManager $tm434 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled437 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff435 = 1;
                    boolean $doBackoff436 = true;
                    boolean $retry431 = true;
                    $label429: for (boolean $commit430 = false; !$commit430; ) {
                        if ($backoffEnabled437) {
                            if ($doBackoff436) {
                                if ($backoff435 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff435);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e432) {
                                            
                                        }
                                    }
                                }
                                if ($backoff435 < 5000) $backoff435 *= 2;
                            }
                            $doBackoff436 = $backoff435 <= 32 || !$doBackoff436;
                        }
                        $commit430 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e432) {
                            $commit430 = false;
                            continue $label429;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e432) {
                            $commit430 = false;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433))
                                continue $label429;
                            if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433)) {
                                $retry431 = true;
                            }
                            else if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects())
                                continue $label429;
                            $retry431 = false;
                            throw new fabric.worker.AbortException($e432);
                        }
                        finally {
                            if ($commit430) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e432) {
                                    $commit430 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e432) {
                                    $commit430 = false;
                                    fabric.common.TransactionID $currentTid433 =
                                      $tm434.getCurrentTid();
                                    if ($currentTid433 != null) {
                                        if ($e432.tid.equals($currentTid433) ||
                                              !$e432.tid.isDescendantOf(
                                                           $currentTid433)) {
                                            throw $e432;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit430 && $retry431) {
                                {  }
                                continue $label429;
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
                    fabric.worker.transaction.TransactionManager $tm443 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled446 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff444 = 1;
                    boolean $doBackoff445 = true;
                    boolean $retry440 = true;
                    $label438: for (boolean $commit439 = false; !$commit439; ) {
                        if ($backoffEnabled446) {
                            if ($doBackoff445) {
                                if ($backoff444 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff444);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e441) {
                                            
                                        }
                                    }
                                }
                                if ($backoff444 < 5000) $backoff444 *= 2;
                            }
                            $doBackoff445 = $backoff444 <= 32 || !$doBackoff445;
                        }
                        $commit439 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().add(o, id));
                        }
                        catch (final fabric.worker.RetryException $e441) {
                            $commit439 = false;
                            continue $label438;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e441) {
                            $commit439 = false;
                            fabric.common.TransactionID $currentTid442 =
                              $tm443.getCurrentTid();
                            if ($e441.tid.isDescendantOf($currentTid442))
                                continue $label438;
                            if ($currentTid442.parent != null) {
                                $retry440 = false;
                                throw $e441;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e441) {
                            $commit439 = false;
                            if ($tm443.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid442 =
                              $tm443.getCurrentTid();
                            if ($e441.tid.isDescendantOf($currentTid442)) {
                                $retry440 = true;
                            }
                            else if ($currentTid442.parent != null) {
                                $retry440 = false;
                                throw $e441;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e441) {
                            $commit439 = false;
                            if ($tm443.checkForStaleObjects())
                                continue $label438;
                            $retry440 = false;
                            throw new fabric.worker.AbortException($e441);
                        }
                        finally {
                            if ($commit439) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e441) {
                                    $commit439 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e441) {
                                    $commit439 = false;
                                    fabric.common.TransactionID $currentTid442 =
                                      $tm443.getCurrentTid();
                                    if ($currentTid442 != null) {
                                        if ($e441.tid.equals($currentTid442) ||
                                              !$e441.tid.isDescendantOf(
                                                           $currentTid442)) {
                                            throw $e441;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit439 && $retry440) {
                                {  }
                                continue $label438;
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
                    fabric.worker.transaction.TransactionManager $tm452 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled455 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff453 = 1;
                    boolean $doBackoff454 = true;
                    boolean $retry449 = true;
                    $label447: for (boolean $commit448 = false; !$commit448; ) {
                        if ($backoffEnabled455) {
                            if ($doBackoff454) {
                                if ($backoff453 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff453);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e450) {
                                            
                                        }
                                    }
                                }
                                if ($backoff453 < 5000) $backoff453 *= 2;
                            }
                            $doBackoff454 = $backoff453 <= 32 || !$doBackoff454;
                        }
                        $commit448 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e450) {
                            $commit448 = false;
                            continue $label447;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e450) {
                            $commit448 = false;
                            fabric.common.TransactionID $currentTid451 =
                              $tm452.getCurrentTid();
                            if ($e450.tid.isDescendantOf($currentTid451))
                                continue $label447;
                            if ($currentTid451.parent != null) {
                                $retry449 = false;
                                throw $e450;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e450) {
                            $commit448 = false;
                            if ($tm452.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid451 =
                              $tm452.getCurrentTid();
                            if ($e450.tid.isDescendantOf($currentTid451)) {
                                $retry449 = true;
                            }
                            else if ($currentTid451.parent != null) {
                                $retry449 = false;
                                throw $e450;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e450) {
                            $commit448 = false;
                            if ($tm452.checkForStaleObjects())
                                continue $label447;
                            $retry449 = false;
                            throw new fabric.worker.AbortException($e450);
                        }
                        finally {
                            if ($commit448) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e450) {
                                    $commit448 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e450) {
                                    $commit448 = false;
                                    fabric.common.TransactionID $currentTid451 =
                                      $tm452.getCurrentTid();
                                    if ($currentTid451 != null) {
                                        if ($e450.tid.equals($currentTid451) ||
                                              !$e450.tid.isDescendantOf(
                                                           $currentTid451)) {
                                            throw $e450;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit448 && $retry449) {
                                {  }
                                continue $label447;
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
                    fabric.worker.transaction.TransactionManager $tm461 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled464 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff462 = 1;
                    boolean $doBackoff463 = true;
                    boolean $retry458 = true;
                    $label456: for (boolean $commit457 = false; !$commit457; ) {
                        if ($backoffEnabled464) {
                            if ($doBackoff463) {
                                if ($backoff462 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff462);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e459) {
                                            
                                        }
                                    }
                                }
                                if ($backoff462 < 5000) $backoff462 *= 2;
                            }
                            $doBackoff463 = $backoff462 <= 32 || !$doBackoff463;
                        }
                        $commit457 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o, id));
                        }
                        catch (final fabric.worker.RetryException $e459) {
                            $commit457 = false;
                            continue $label456;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e459) {
                            $commit457 = false;
                            fabric.common.TransactionID $currentTid460 =
                              $tm461.getCurrentTid();
                            if ($e459.tid.isDescendantOf($currentTid460))
                                continue $label456;
                            if ($currentTid460.parent != null) {
                                $retry458 = false;
                                throw $e459;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e459) {
                            $commit457 = false;
                            if ($tm461.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid460 =
                              $tm461.getCurrentTid();
                            if ($e459.tid.isDescendantOf($currentTid460)) {
                                $retry458 = true;
                            }
                            else if ($currentTid460.parent != null) {
                                $retry458 = false;
                                throw $e459;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e459) {
                            $commit457 = false;
                            if ($tm461.checkForStaleObjects())
                                continue $label456;
                            $retry458 = false;
                            throw new fabric.worker.AbortException($e459);
                        }
                        finally {
                            if ($commit457) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e459) {
                                    $commit457 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e459) {
                                    $commit457 = false;
                                    fabric.common.TransactionID $currentTid460 =
                                      $tm461.getCurrentTid();
                                    if ($currentTid460 != null) {
                                        if ($e459.tid.equals($currentTid460) ||
                                              !$e459.tid.isDescendantOf(
                                                           $currentTid460)) {
                                            throw $e459;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit457 && $retry458) {
                                {  }
                                continue $label456;
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
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
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
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 16, 16, 96, -16, 98,
    16, -72, -101, -83, -44, -123, -57, 118, -126, -15, 25, -95, -119, 14, -12,
    -79, 6, 17, 97, -102, 103, 7, 30, 65, -27, 21, 105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528404283000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
