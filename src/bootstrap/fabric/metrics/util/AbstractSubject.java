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
                    fabric.worker.transaction.TransactionManager $tm444 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled447 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff445 = 1;
                    boolean $doBackoff446 = true;
                    boolean $retry441 = true;
                    $label439: for (boolean $commit440 = false; !$commit440; ) {
                        if ($backoffEnabled447) {
                            if ($doBackoff446) {
                                if ($backoff445 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff445);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e442) {
                                            
                                        }
                                    }
                                }
                                if ($backoff445 < 5000) $backoff445 *= 2;
                            }
                            $doBackoff446 = $backoff445 <= 32 || !$doBackoff446;
                        }
                        $commit440 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e442) {
                            $commit440 = false;
                            continue $label439;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e442) {
                            $commit440 = false;
                            fabric.common.TransactionID $currentTid443 =
                              $tm444.getCurrentTid();
                            if ($e442.tid.isDescendantOf($currentTid443))
                                continue $label439;
                            if ($currentTid443.parent != null) {
                                $retry441 = false;
                                throw $e442;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e442) {
                            $commit440 = false;
                            if ($tm444.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid443 =
                              $tm444.getCurrentTid();
                            if ($e442.tid.isDescendantOf($currentTid443)) {
                                $retry441 = true;
                            }
                            else if ($currentTid443.parent != null) {
                                $retry441 = false;
                                throw $e442;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e442) {
                            $commit440 = false;
                            if ($tm444.checkForStaleObjects())
                                continue $label439;
                            $retry441 = false;
                            throw new fabric.worker.AbortException($e442);
                        }
                        finally {
                            if ($commit440) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e442) {
                                    $commit440 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e442) {
                                    $commit440 = false;
                                    fabric.common.TransactionID $currentTid443 =
                                      $tm444.getCurrentTid();
                                    if ($currentTid443 != null) {
                                        if ($e442.tid.equals($currentTid443) ||
                                              !$e442.tid.isDescendantOf(
                                                           $currentTid443)) {
                                            throw $e442;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit440 && $retry441) {
                                {  }
                                continue $label439;
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
                    fabric.worker.transaction.TransactionManager $tm453 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled456 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff454 = 1;
                    boolean $doBackoff455 = true;
                    boolean $retry450 = true;
                    $label448: for (boolean $commit449 = false; !$commit449; ) {
                        if ($backoffEnabled456) {
                            if ($doBackoff455) {
                                if ($backoff454 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff454);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e451) {
                                            
                                        }
                                    }
                                }
                                if ($backoff454 < 5000) $backoff454 *= 2;
                            }
                            $doBackoff455 = $backoff454 <= 32 || !$doBackoff455;
                        }
                        $commit449 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().add(o, id));
                        }
                        catch (final fabric.worker.RetryException $e451) {
                            $commit449 = false;
                            continue $label448;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e451) {
                            $commit449 = false;
                            fabric.common.TransactionID $currentTid452 =
                              $tm453.getCurrentTid();
                            if ($e451.tid.isDescendantOf($currentTid452))
                                continue $label448;
                            if ($currentTid452.parent != null) {
                                $retry450 = false;
                                throw $e451;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e451) {
                            $commit449 = false;
                            if ($tm453.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid452 =
                              $tm453.getCurrentTid();
                            if ($e451.tid.isDescendantOf($currentTid452)) {
                                $retry450 = true;
                            }
                            else if ($currentTid452.parent != null) {
                                $retry450 = false;
                                throw $e451;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e451) {
                            $commit449 = false;
                            if ($tm453.checkForStaleObjects())
                                continue $label448;
                            $retry450 = false;
                            throw new fabric.worker.AbortException($e451);
                        }
                        finally {
                            if ($commit449) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e451) {
                                    $commit449 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e451) {
                                    $commit449 = false;
                                    fabric.common.TransactionID $currentTid452 =
                                      $tm453.getCurrentTid();
                                    if ($currentTid452 != null) {
                                        if ($e451.tid.equals($currentTid452) ||
                                              !$e451.tid.isDescendantOf(
                                                           $currentTid452)) {
                                            throw $e451;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit449 && $retry450) {
                                {  }
                                continue $label448;
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
                    fabric.worker.transaction.TransactionManager $tm462 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled465 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff463 = 1;
                    boolean $doBackoff464 = true;
                    boolean $retry459 = true;
                    $label457: for (boolean $commit458 = false; !$commit458; ) {
                        if ($backoffEnabled465) {
                            if ($doBackoff464) {
                                if ($backoff463 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff463);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e460) {
                                            
                                        }
                                    }
                                }
                                if ($backoff463 < 5000) $backoff463 *= 2;
                            }
                            $doBackoff464 = $backoff463 <= 32 || !$doBackoff464;
                        }
                        $commit458 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e460) {
                            $commit458 = false;
                            continue $label457;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e460) {
                            $commit458 = false;
                            fabric.common.TransactionID $currentTid461 =
                              $tm462.getCurrentTid();
                            if ($e460.tid.isDescendantOf($currentTid461))
                                continue $label457;
                            if ($currentTid461.parent != null) {
                                $retry459 = false;
                                throw $e460;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e460) {
                            $commit458 = false;
                            if ($tm462.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid461 =
                              $tm462.getCurrentTid();
                            if ($e460.tid.isDescendantOf($currentTid461)) {
                                $retry459 = true;
                            }
                            else if ($currentTid461.parent != null) {
                                $retry459 = false;
                                throw $e460;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e460) {
                            $commit458 = false;
                            if ($tm462.checkForStaleObjects())
                                continue $label457;
                            $retry459 = false;
                            throw new fabric.worker.AbortException($e460);
                        }
                        finally {
                            if ($commit458) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e460) {
                                    $commit458 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e460) {
                                    $commit458 = false;
                                    fabric.common.TransactionID $currentTid461 =
                                      $tm462.getCurrentTid();
                                    if ($currentTid461 != null) {
                                        if ($e460.tid.equals($currentTid461) ||
                                              !$e460.tid.isDescendantOf(
                                                           $currentTid461)) {
                                            throw $e460;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit458 && $retry459) {
                                {  }
                                continue $label457;
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
                    fabric.worker.transaction.TransactionManager $tm471 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled474 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff472 = 1;
                    boolean $doBackoff473 = true;
                    boolean $retry468 = true;
                    $label466: for (boolean $commit467 = false; !$commit467; ) {
                        if ($backoffEnabled474) {
                            if ($doBackoff473) {
                                if ($backoff472 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff472);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e469) {
                                            
                                        }
                                    }
                                }
                                if ($backoff472 < 5000) $backoff472 *= 2;
                            }
                            $doBackoff473 = $backoff472 <= 32 || !$doBackoff473;
                        }
                        $commit467 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o, id))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o, id));
                        }
                        catch (final fabric.worker.RetryException $e469) {
                            $commit467 = false;
                            continue $label466;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e469) {
                            $commit467 = false;
                            fabric.common.TransactionID $currentTid470 =
                              $tm471.getCurrentTid();
                            if ($e469.tid.isDescendantOf($currentTid470))
                                continue $label466;
                            if ($currentTid470.parent != null) {
                                $retry468 = false;
                                throw $e469;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e469) {
                            $commit467 = false;
                            if ($tm471.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid470 =
                              $tm471.getCurrentTid();
                            if ($e469.tid.isDescendantOf($currentTid470)) {
                                $retry468 = true;
                            }
                            else if ($currentTid470.parent != null) {
                                $retry468 = false;
                                throw $e469;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e469) {
                            $commit467 = false;
                            if ($tm471.checkForStaleObjects())
                                continue $label466;
                            $retry468 = false;
                            throw new fabric.worker.AbortException($e469);
                        }
                        finally {
                            if ($commit467) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e469) {
                                    $commit467 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e469) {
                                    $commit467 = false;
                                    fabric.common.TransactionID $currentTid470 =
                                      $tm471.getCurrentTid();
                                    if ($currentTid470 != null) {
                                        if ($e469.tid.equals($currentTid470) ||
                                              !$e469.tid.isDescendantOf(
                                                           $currentTid470)) {
                                            throw $e469;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit467 && $retry468) {
                                {  }
                                continue $label466;
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
    
    public static final byte[] $classHash = new byte[] { 33, -45, 102, -21, 98,
    -124, -52, 2, 45, 63, 29, -9, 58, -88, -61, -71, -51, 83, -127, 5, 19, 123,
    -125, -33, 93, 10, -58, -85, 46, 71, -38, -30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
