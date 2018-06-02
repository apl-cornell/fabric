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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
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
    
    public static final byte[] $classHash = new byte[] { 47, -15, -120, 17, -83,
    112, -81, 124, 28, 7, -87, -75, -83, -46, -100, 101, -88, 49, -40, 30, -50,
    83, -34, 15, -73, -83, 62, -15, -63, 61, -93, -120 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
