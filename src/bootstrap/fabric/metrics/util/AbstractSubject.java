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
    
    public static final byte[] $classHash = new byte[] { 111, 82, 110, -73, -24,
    -64, 69, -37, 47, -31, -43, 36, -74, 15, 34, 51, 46, 37, -75, 36, 106, 101,
    114, -32, 97, 36, 116, 98, 6, -43, 90, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527695332000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
