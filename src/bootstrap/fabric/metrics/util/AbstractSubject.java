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
                    fabric.worker.transaction.TransactionManager $tm464 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled467 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff465 = 1;
                    boolean $doBackoff466 = true;
                    boolean $retry461 = true;
                    $label459: for (boolean $commit460 = false; !$commit460; ) {
                        if ($backoffEnabled467) {
                            if ($doBackoff466) {
                                if ($backoff465 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff465);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e462) {
                                            
                                        }
                                    }
                                }
                                if ($backoff465 < 5000) $backoff465 *= 2;
                            }
                            $doBackoff466 = $backoff465 <= 32 || !$doBackoff466;
                        }
                        $commit460 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$$observers().contains(o))
                                    tmp.set$$observers(
                                          tmp.get$$observers().add(o));
                            }
                            catch (final fabric.worker.RetryException $e462) {
                                throw $e462;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e462) {
                                throw $e462;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e462) {
                                throw $e462;
                            }
                            catch (final Throwable $e462) {
                                $tm464.getCurrentLog().checkRetrySignal();
                                throw $e462;
                            }
                        }
                        catch (final fabric.worker.RetryException $e462) {
                            $commit460 = false;
                            continue $label459;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e462) {
                            $commit460 = false;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463))
                                continue $label459;
                            if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463)) {
                                $retry461 = true;
                            }
                            else if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects())
                                continue $label459;
                            $retry461 = false;
                            throw new fabric.worker.AbortException($e462);
                        }
                        finally {
                            if ($commit460) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e462) {
                                    $commit460 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e462) {
                                    $commit460 = false;
                                    fabric.common.TransactionID $currentTid463 =
                                      $tm464.getCurrentTid();
                                    if ($currentTid463 != null) {
                                        if ($e462.tid.equals($currentTid463) ||
                                              !$e462.tid.isDescendantOf(
                                                           $currentTid463)) {
                                            throw $e462;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit460 && $retry461) {
                                {  }
                                continue $label459;
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
                    fabric.worker.transaction.TransactionManager $tm473 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled476 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff474 = 1;
                    boolean $doBackoff475 = true;
                    boolean $retry470 = true;
                    $label468: for (boolean $commit469 = false; !$commit469; ) {
                        if ($backoffEnabled476) {
                            if ($doBackoff475) {
                                if ($backoff474 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff474);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e471) {
                                            
                                        }
                                    }
                                }
                                if ($backoff474 < 5000) $backoff474 *= 2;
                            }
                            $doBackoff475 = $backoff474 <= 32 || !$doBackoff475;
                        }
                        $commit469 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$$observers().contains(o, id))
                                    tmp.set$$observers(
                                          tmp.get$$observers().add(o, id));
                            }
                            catch (final fabric.worker.RetryException $e471) {
                                throw $e471;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e471) {
                                throw $e471;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e471) {
                                throw $e471;
                            }
                            catch (final Throwable $e471) {
                                $tm473.getCurrentLog().checkRetrySignal();
                                throw $e471;
                            }
                        }
                        catch (final fabric.worker.RetryException $e471) {
                            $commit469 = false;
                            continue $label468;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e471) {
                            $commit469 = false;
                            fabric.common.TransactionID $currentTid472 =
                              $tm473.getCurrentTid();
                            if ($e471.tid.isDescendantOf($currentTid472))
                                continue $label468;
                            if ($currentTid472.parent != null) {
                                $retry470 = false;
                                throw $e471;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e471) {
                            $commit469 = false;
                            if ($tm473.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid472 =
                              $tm473.getCurrentTid();
                            if ($e471.tid.isDescendantOf($currentTid472)) {
                                $retry470 = true;
                            }
                            else if ($currentTid472.parent != null) {
                                $retry470 = false;
                                throw $e471;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e471) {
                            $commit469 = false;
                            if ($tm473.checkForStaleObjects())
                                continue $label468;
                            $retry470 = false;
                            throw new fabric.worker.AbortException($e471);
                        }
                        finally {
                            if ($commit469) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e471) {
                                    $commit469 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e471) {
                                    $commit469 = false;
                                    fabric.common.TransactionID $currentTid472 =
                                      $tm473.getCurrentTid();
                                    if ($currentTid472 != null) {
                                        if ($e471.tid.equals($currentTid472) ||
                                              !$e471.tid.isDescendantOf(
                                                           $currentTid472)) {
                                            throw $e471;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit469 && $retry470) {
                                {  }
                                continue $label468;
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
                    fabric.worker.transaction.TransactionManager $tm482 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled485 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff483 = 1;
                    boolean $doBackoff484 = true;
                    boolean $retry479 = true;
                    $label477: for (boolean $commit478 = false; !$commit478; ) {
                        if ($backoffEnabled485) {
                            if ($doBackoff484) {
                                if ($backoff483 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff483);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e480) {
                                            
                                        }
                                    }
                                }
                                if ($backoff483 < 5000) $backoff483 *= 2;
                            }
                            $doBackoff484 = $backoff483 <= 32 || !$doBackoff484;
                        }
                        $commit478 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$$observers().contains(o))
                                    tmp.set$$observers(
                                          tmp.get$$observers().remove(o));
                            }
                            catch (final fabric.worker.RetryException $e480) {
                                throw $e480;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e480) {
                                throw $e480;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e480) {
                                throw $e480;
                            }
                            catch (final Throwable $e480) {
                                $tm482.getCurrentLog().checkRetrySignal();
                                throw $e480;
                            }
                        }
                        catch (final fabric.worker.RetryException $e480) {
                            $commit478 = false;
                            continue $label477;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e480) {
                            $commit478 = false;
                            fabric.common.TransactionID $currentTid481 =
                              $tm482.getCurrentTid();
                            if ($e480.tid.isDescendantOf($currentTid481))
                                continue $label477;
                            if ($currentTid481.parent != null) {
                                $retry479 = false;
                                throw $e480;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e480) {
                            $commit478 = false;
                            if ($tm482.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid481 =
                              $tm482.getCurrentTid();
                            if ($e480.tid.isDescendantOf($currentTid481)) {
                                $retry479 = true;
                            }
                            else if ($currentTid481.parent != null) {
                                $retry479 = false;
                                throw $e480;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e480) {
                            $commit478 = false;
                            if ($tm482.checkForStaleObjects())
                                continue $label477;
                            $retry479 = false;
                            throw new fabric.worker.AbortException($e480);
                        }
                        finally {
                            if ($commit478) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e480) {
                                    $commit478 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e480) {
                                    $commit478 = false;
                                    fabric.common.TransactionID $currentTid481 =
                                      $tm482.getCurrentTid();
                                    if ($currentTid481 != null) {
                                        if ($e480.tid.equals($currentTid481) ||
                                              !$e480.tid.isDescendantOf(
                                                           $currentTid481)) {
                                            throw $e480;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit478 && $retry479) {
                                {  }
                                continue $label477;
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
                    fabric.worker.transaction.TransactionManager $tm491 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled494 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff492 = 1;
                    boolean $doBackoff493 = true;
                    boolean $retry488 = true;
                    $label486: for (boolean $commit487 = false; !$commit487; ) {
                        if ($backoffEnabled494) {
                            if ($doBackoff493) {
                                if ($backoff492 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff492);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e489) {
                                            
                                        }
                                    }
                                }
                                if ($backoff492 < 5000) $backoff492 *= 2;
                            }
                            $doBackoff493 = $backoff492 <= 32 || !$doBackoff493;
                        }
                        $commit487 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$$observers().contains(o, id))
                                    tmp.set$$observers(
                                          tmp.get$$observers().remove(o, id));
                            }
                            catch (final fabric.worker.RetryException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e489) {
                                throw $e489;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e489) {
                                throw $e489;
                            }
                            catch (final Throwable $e489) {
                                $tm491.getCurrentLog().checkRetrySignal();
                                throw $e489;
                            }
                        }
                        catch (final fabric.worker.RetryException $e489) {
                            $commit487 = false;
                            continue $label486;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e489) {
                            $commit487 = false;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490))
                                continue $label486;
                            if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490)) {
                                $retry488 = true;
                            }
                            else if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label486;
                            $retry488 = false;
                            throw new fabric.worker.AbortException($e489);
                        }
                        finally {
                            if ($commit487) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e489) {
                                    $commit487 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e489) {
                                    $commit487 = false;
                                    fabric.common.TransactionID $currentTid490 =
                                      $tm491.getCurrentTid();
                                    if ($currentTid490 != null) {
                                        if ($e489.tid.equals($currentTid490) ||
                                              !$e489.tid.isDescendantOf(
                                                           $currentTid490)) {
                                            throw $e489;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit487 && $retry488) {
                                {  }
                                continue $label486;
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
    
    public static final byte[] $classHash = new byte[] { -14, -11, 80, -116,
    -22, 115, 117, 80, -70, -103, -49, -122, -98, -21, 96, 40, -3, 109, -108,
    76, -6, 40, 36, -107, 3, 112, -43, 5, 96, 61, -30, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
