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
                    fabric.worker.transaction.TransactionManager $tm406 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled409 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff407 = 1;
                    boolean $doBackoff408 = true;
                    boolean $retry403 = true;
                    $label401: for (boolean $commit402 = false; !$commit402; ) {
                        if ($backoffEnabled409) {
                            if ($doBackoff408) {
                                if ($backoff407 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff407);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e404) {
                                            
                                        }
                                    }
                                }
                                if ($backoff407 < 5000) $backoff407 *= 2;
                            }
                            $doBackoff408 = $backoff407 <= 32 || !$doBackoff408;
                        }
                        $commit402 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e404) {
                            $commit402 = false;
                            continue $label401;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e404) {
                            $commit402 = false;
                            fabric.common.TransactionID $currentTid405 =
                              $tm406.getCurrentTid();
                            if ($e404.tid.isDescendantOf($currentTid405))
                                continue $label401;
                            if ($currentTid405.parent != null) {
                                $retry403 = false;
                                throw $e404;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e404) {
                            $commit402 = false;
                            if ($tm406.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid405 =
                              $tm406.getCurrentTid();
                            if ($e404.tid.isDescendantOf($currentTid405)) {
                                $retry403 = true;
                            }
                            else if ($currentTid405.parent != null) {
                                $retry403 = false;
                                throw $e404;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e404) {
                            $commit402 = false;
                            if ($tm406.checkForStaleObjects())
                                continue $label401;
                            $retry403 = false;
                            throw new fabric.worker.AbortException($e404);
                        }
                        finally {
                            if ($commit402) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e404) {
                                    $commit402 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e404) {
                                    $commit402 = false;
                                    fabric.common.TransactionID $currentTid405 =
                                      $tm406.getCurrentTid();
                                    if ($currentTid405 != null) {
                                        if ($e404.tid.equals($currentTid405) ||
                                              !$e404.tid.isDescendantOf(
                                                           $currentTid405)) {
                                            throw $e404;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit402 && $retry403) {
                                {  }
                                continue $label401;
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
                    fabric.worker.transaction.TransactionManager $tm415 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled418 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff416 = 1;
                    boolean $doBackoff417 = true;
                    boolean $retry412 = true;
                    $label410: for (boolean $commit411 = false; !$commit411; ) {
                        if ($backoffEnabled418) {
                            if ($doBackoff417) {
                                if ($backoff416 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff416);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e413) {
                                            
                                        }
                                    }
                                }
                                if ($backoff416 < 5000) $backoff416 *= 2;
                            }
                            $doBackoff417 = $backoff416 <= 32 || !$doBackoff417;
                        }
                        $commit411 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().add(o, id));
                        }
                        catch (final fabric.worker.RetryException $e413) {
                            $commit411 = false;
                            continue $label410;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e413) {
                            $commit411 = false;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414))
                                continue $label410;
                            if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid414 =
                              $tm415.getCurrentTid();
                            if ($e413.tid.isDescendantOf($currentTid414)) {
                                $retry412 = true;
                            }
                            else if ($currentTid414.parent != null) {
                                $retry412 = false;
                                throw $e413;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e413) {
                            $commit411 = false;
                            if ($tm415.checkForStaleObjects())
                                continue $label410;
                            $retry412 = false;
                            throw new fabric.worker.AbortException($e413);
                        }
                        finally {
                            if ($commit411) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e413) {
                                    $commit411 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e413) {
                                    $commit411 = false;
                                    fabric.common.TransactionID $currentTid414 =
                                      $tm415.getCurrentTid();
                                    if ($currentTid414 != null) {
                                        if ($e413.tid.equals($currentTid414) ||
                                              !$e413.tid.isDescendantOf(
                                                           $currentTid414)) {
                                            throw $e413;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit411 && $retry412) {
                                {  }
                                continue $label410;
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
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry421 = true;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff425);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label419;
                            if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry421 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label419;
                            $retry421 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit420 = false;
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit420 && $retry421) {
                                {  }
                                continue $label419;
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
                    fabric.worker.transaction.TransactionManager $tm433 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled436 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff434 = 1;
                    boolean $doBackoff435 = true;
                    boolean $retry430 = true;
                    $label428: for (boolean $commit429 = false; !$commit429; ) {
                        if ($backoffEnabled436) {
                            if ($doBackoff435) {
                                if ($backoff434 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff434);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e431) {
                                            
                                        }
                                    }
                                }
                                if ($backoff434 < 5000) $backoff434 *= 2;
                            }
                            $doBackoff435 = $backoff434 <= 32 || !$doBackoff435;
                        }
                        $commit429 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o, id));
                        }
                        catch (final fabric.worker.RetryException $e431) {
                            $commit429 = false;
                            continue $label428;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e431) {
                            $commit429 = false;
                            fabric.common.TransactionID $currentTid432 =
                              $tm433.getCurrentTid();
                            if ($e431.tid.isDescendantOf($currentTid432))
                                continue $label428;
                            if ($currentTid432.parent != null) {
                                $retry430 = false;
                                throw $e431;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e431) {
                            $commit429 = false;
                            if ($tm433.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid432 =
                              $tm433.getCurrentTid();
                            if ($e431.tid.isDescendantOf($currentTid432)) {
                                $retry430 = true;
                            }
                            else if ($currentTid432.parent != null) {
                                $retry430 = false;
                                throw $e431;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e431) {
                            $commit429 = false;
                            if ($tm433.checkForStaleObjects())
                                continue $label428;
                            $retry430 = false;
                            throw new fabric.worker.AbortException($e431);
                        }
                        finally {
                            if ($commit429) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e431) {
                                    $commit429 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e431) {
                                    $commit429 = false;
                                    fabric.common.TransactionID $currentTid432 =
                                      $tm433.getCurrentTid();
                                    if ($currentTid432 != null) {
                                        if ($e431.tid.equals($currentTid432) ||
                                              !$e431.tid.isDescendantOf(
                                                           $currentTid432)) {
                                            throw $e431;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit429 && $retry430) {
                                {  }
                                continue $label428;
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
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
