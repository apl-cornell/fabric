package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.util.ArrayList;
import fabric.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;
import fabric.metrics.SampledMetric;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import fabric.common.util.Triple;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.metrics.ImmutableObserverSet get$observers();
    
    public fabric.worker.metrics.ImmutableObserverSet set$observers(
      fabric.worker.metrics.ImmutableObserverSet val);
    
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
        public fabric.worker.metrics.ImmutableObserverSet get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$observers(
          fabric.worker.metrics.ImmutableObserverSet val) {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              set$observers(val);
        }
        
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
        public fabric.worker.metrics.ImmutableObserverSet get$observers() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observers;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$observers(
          fabric.worker.metrics.ImmutableObserverSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableObserverSet observers;
        
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            fabric$lang$Object$();
            this.set$observers(
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
                if (!tmp.get$observers().contains(o))
                    tmp.set$observers(tmp.get$observers().add(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm501 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled504 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff502 = 1;
                    boolean $doBackoff503 = true;
                    boolean $retry497 = true;
                    boolean $keepReads498 = false;
                    $label495: for (boolean $commit496 = false; !$commit496; ) {
                        if ($backoffEnabled504) {
                            if ($doBackoff503) {
                                if ($backoff502 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff502));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e499) {
                                            
                                        }
                                    }
                                }
                                if ($backoff502 < 5000) $backoff502 *= 2;
                            }
                            $doBackoff503 = $backoff502 <= 32 || !$doBackoff503;
                        }
                        $commit496 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$observers().contains(o))
                                    tmp.set$observers(
                                          tmp.get$observers().add(o));
                            }
                            catch (final fabric.worker.RetryException $e499) {
                                throw $e499;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e499) {
                                throw $e499;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e499) {
                                throw $e499;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e499) {
                                throw $e499;
                            }
                            catch (final Throwable $e499) {
                                $tm501.getCurrentLog().checkRetrySignal();
                                throw $e499;
                            }
                        }
                        catch (final fabric.worker.RetryException $e499) {
                            $commit496 = false;
                            continue $label495;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e499) {
                            $commit496 = false;
                            $retry497 = false;
                            $keepReads498 = $e499.keepReads;
                            if ($tm501.checkForStaleObjects()) {
                                $retry497 = true;
                                $keepReads498 = false;
                                continue $label495;
                            }
                            fabric.common.TransactionID $currentTid500 =
                              $tm501.getCurrentTid();
                            if ($e499.tid == null ||
                                  !$e499.tid.isDescendantOf($currentTid500)) {
                                throw $e499;
                            }
                            throw new fabric.worker.UserAbortException($e499);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e499) {
                            $commit496 = false;
                            fabric.common.TransactionID $currentTid500 =
                              $tm501.getCurrentTid();
                            if ($e499.tid.isDescendantOf($currentTid500))
                                continue $label495;
                            if ($currentTid500.parent != null) {
                                $retry497 = false;
                                throw $e499;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e499) {
                            $commit496 = false;
                            if ($tm501.checkForStaleObjects())
                                continue $label495;
                            fabric.common.TransactionID $currentTid500 =
                              $tm501.getCurrentTid();
                            if ($e499.tid.isDescendantOf($currentTid500)) {
                                $retry497 = true;
                            }
                            else if ($currentTid500.parent != null) {
                                $retry497 = false;
                                throw $e499;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e499) {
                            $commit496 = false;
                            if ($tm501.checkForStaleObjects())
                                continue $label495;
                            $retry497 = false;
                            throw new fabric.worker.AbortException($e499);
                        }
                        finally {
                            if ($commit496) {
                                fabric.common.TransactionID $currentTid500 =
                                  $tm501.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e499) {
                                    $commit496 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e499) {
                                    $commit496 = false;
                                    $retry497 = false;
                                    $keepReads498 = $e499.keepReads;
                                    if ($tm501.checkForStaleObjects()) {
                                        $retry497 = true;
                                        $keepReads498 = false;
                                        continue $label495;
                                    }
                                    if ($e499.tid ==
                                          null ||
                                          !$e499.tid.isDescendantOf(
                                                       $currentTid500))
                                        throw $e499;
                                    throw new fabric.worker.UserAbortException(
                                            $e499);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e499) {
                                    $commit496 = false;
                                    $currentTid500 = $tm501.getCurrentTid();
                                    if ($currentTid500 != null) {
                                        if ($e499.tid.equals($currentTid500) ||
                                              !$e499.tid.isDescendantOf(
                                                           $currentTid500)) {
                                            throw $e499;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads498) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit496) {
                                {  }
                                if ($retry497) { continue $label495; }
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
                if (!tmp.get$observers().contains(o, id))
                    tmp.set$observers(tmp.get$observers().add(o, id));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm511 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled514 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff512 = 1;
                    boolean $doBackoff513 = true;
                    boolean $retry507 = true;
                    boolean $keepReads508 = false;
                    $label505: for (boolean $commit506 = false; !$commit506; ) {
                        if ($backoffEnabled514) {
                            if ($doBackoff513) {
                                if ($backoff512 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff512));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e509) {
                                            
                                        }
                                    }
                                }
                                if ($backoff512 < 5000) $backoff512 *= 2;
                            }
                            $doBackoff513 = $backoff512 <= 32 || !$doBackoff513;
                        }
                        $commit506 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$observers().contains(o, id))
                                    tmp.set$observers(
                                          tmp.get$observers().add(o, id));
                            }
                            catch (final fabric.worker.RetryException $e509) {
                                throw $e509;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e509) {
                                throw $e509;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e509) {
                                throw $e509;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e509) {
                                throw $e509;
                            }
                            catch (final Throwable $e509) {
                                $tm511.getCurrentLog().checkRetrySignal();
                                throw $e509;
                            }
                        }
                        catch (final fabric.worker.RetryException $e509) {
                            $commit506 = false;
                            continue $label505;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e509) {
                            $commit506 = false;
                            $retry507 = false;
                            $keepReads508 = $e509.keepReads;
                            if ($tm511.checkForStaleObjects()) {
                                $retry507 = true;
                                $keepReads508 = false;
                                continue $label505;
                            }
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid == null ||
                                  !$e509.tid.isDescendantOf($currentTid510)) {
                                throw $e509;
                            }
                            throw new fabric.worker.UserAbortException($e509);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e509) {
                            $commit506 = false;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510))
                                continue $label505;
                            if ($currentTid510.parent != null) {
                                $retry507 = false;
                                throw $e509;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e509) {
                            $commit506 = false;
                            if ($tm511.checkForStaleObjects())
                                continue $label505;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510)) {
                                $retry507 = true;
                            }
                            else if ($currentTid510.parent != null) {
                                $retry507 = false;
                                throw $e509;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e509) {
                            $commit506 = false;
                            if ($tm511.checkForStaleObjects())
                                continue $label505;
                            $retry507 = false;
                            throw new fabric.worker.AbortException($e509);
                        }
                        finally {
                            if ($commit506) {
                                fabric.common.TransactionID $currentTid510 =
                                  $tm511.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e509) {
                                    $commit506 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e509) {
                                    $commit506 = false;
                                    $retry507 = false;
                                    $keepReads508 = $e509.keepReads;
                                    if ($tm511.checkForStaleObjects()) {
                                        $retry507 = true;
                                        $keepReads508 = false;
                                        continue $label505;
                                    }
                                    if ($e509.tid ==
                                          null ||
                                          !$e509.tid.isDescendantOf(
                                                       $currentTid510))
                                        throw $e509;
                                    throw new fabric.worker.UserAbortException(
                                            $e509);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e509) {
                                    $commit506 = false;
                                    $currentTid510 = $tm511.getCurrentTid();
                                    if ($currentTid510 != null) {
                                        if ($e509.tid.equals($currentTid510) ||
                                              !$e509.tid.isDescendantOf(
                                                           $currentTid510)) {
                                            throw $e509;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads508) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit506) {
                                {  }
                                if ($retry507) { continue $label505; }
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
                if (tmp.get$observers().contains(o))
                    tmp.set$observers(tmp.get$observers().remove(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm521 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled524 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff522 = 1;
                    boolean $doBackoff523 = true;
                    boolean $retry517 = true;
                    boolean $keepReads518 = false;
                    $label515: for (boolean $commit516 = false; !$commit516; ) {
                        if ($backoffEnabled524) {
                            if ($doBackoff523) {
                                if ($backoff522 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff522));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e519) {
                                            
                                        }
                                    }
                                }
                                if ($backoff522 < 5000) $backoff522 *= 2;
                            }
                            $doBackoff523 = $backoff522 <= 32 || !$doBackoff523;
                        }
                        $commit516 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o))
                                    tmp.set$observers(
                                          tmp.get$observers().remove(o));
                            }
                            catch (final fabric.worker.RetryException $e519) {
                                throw $e519;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e519) {
                                throw $e519;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e519) {
                                throw $e519;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e519) {
                                throw $e519;
                            }
                            catch (final Throwable $e519) {
                                $tm521.getCurrentLog().checkRetrySignal();
                                throw $e519;
                            }
                        }
                        catch (final fabric.worker.RetryException $e519) {
                            $commit516 = false;
                            continue $label515;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e519) {
                            $commit516 = false;
                            $retry517 = false;
                            $keepReads518 = $e519.keepReads;
                            if ($tm521.checkForStaleObjects()) {
                                $retry517 = true;
                                $keepReads518 = false;
                                continue $label515;
                            }
                            fabric.common.TransactionID $currentTid520 =
                              $tm521.getCurrentTid();
                            if ($e519.tid == null ||
                                  !$e519.tid.isDescendantOf($currentTid520)) {
                                throw $e519;
                            }
                            throw new fabric.worker.UserAbortException($e519);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e519) {
                            $commit516 = false;
                            fabric.common.TransactionID $currentTid520 =
                              $tm521.getCurrentTid();
                            if ($e519.tid.isDescendantOf($currentTid520))
                                continue $label515;
                            if ($currentTid520.parent != null) {
                                $retry517 = false;
                                throw $e519;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e519) {
                            $commit516 = false;
                            if ($tm521.checkForStaleObjects())
                                continue $label515;
                            fabric.common.TransactionID $currentTid520 =
                              $tm521.getCurrentTid();
                            if ($e519.tid.isDescendantOf($currentTid520)) {
                                $retry517 = true;
                            }
                            else if ($currentTid520.parent != null) {
                                $retry517 = false;
                                throw $e519;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e519) {
                            $commit516 = false;
                            if ($tm521.checkForStaleObjects())
                                continue $label515;
                            $retry517 = false;
                            throw new fabric.worker.AbortException($e519);
                        }
                        finally {
                            if ($commit516) {
                                fabric.common.TransactionID $currentTid520 =
                                  $tm521.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e519) {
                                    $commit516 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e519) {
                                    $commit516 = false;
                                    $retry517 = false;
                                    $keepReads518 = $e519.keepReads;
                                    if ($tm521.checkForStaleObjects()) {
                                        $retry517 = true;
                                        $keepReads518 = false;
                                        continue $label515;
                                    }
                                    if ($e519.tid ==
                                          null ||
                                          !$e519.tid.isDescendantOf(
                                                       $currentTid520))
                                        throw $e519;
                                    throw new fabric.worker.UserAbortException(
                                            $e519);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e519) {
                                    $commit516 = false;
                                    $currentTid520 = $tm521.getCurrentTid();
                                    if ($currentTid520 != null) {
                                        if ($e519.tid.equals($currentTid520) ||
                                              !$e519.tid.isDescendantOf(
                                                           $currentTid520)) {
                                            throw $e519;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads518) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit516) {
                                {  }
                                if ($retry517) { continue $label515; }
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
                if (tmp.get$observers().contains(o, id))
                    tmp.set$observers(tmp.get$observers().remove(o, id));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm531 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled534 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff532 = 1;
                    boolean $doBackoff533 = true;
                    boolean $retry527 = true;
                    boolean $keepReads528 = false;
                    $label525: for (boolean $commit526 = false; !$commit526; ) {
                        if ($backoffEnabled534) {
                            if ($doBackoff533) {
                                if ($backoff532 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff532));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e529) {
                                            
                                        }
                                    }
                                }
                                if ($backoff532 < 5000) $backoff532 *= 2;
                            }
                            $doBackoff533 = $backoff532 <= 32 || !$doBackoff533;
                        }
                        $commit526 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o, id))
                                    tmp.set$observers(
                                          tmp.get$observers().remove(o, id));
                            }
                            catch (final fabric.worker.RetryException $e529) {
                                throw $e529;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e529) {
                                throw $e529;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e529) {
                                throw $e529;
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e529) {
                                throw $e529;
                            }
                            catch (final Throwable $e529) {
                                $tm531.getCurrentLog().checkRetrySignal();
                                throw $e529;
                            }
                        }
                        catch (final fabric.worker.RetryException $e529) {
                            $commit526 = false;
                            continue $label525;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e529) {
                            $commit526 = false;
                            $retry527 = false;
                            $keepReads528 = $e529.keepReads;
                            if ($tm531.checkForStaleObjects()) {
                                $retry527 = true;
                                $keepReads528 = false;
                                continue $label525;
                            }
                            fabric.common.TransactionID $currentTid530 =
                              $tm531.getCurrentTid();
                            if ($e529.tid == null ||
                                  !$e529.tid.isDescendantOf($currentTid530)) {
                                throw $e529;
                            }
                            throw new fabric.worker.UserAbortException($e529);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e529) {
                            $commit526 = false;
                            fabric.common.TransactionID $currentTid530 =
                              $tm531.getCurrentTid();
                            if ($e529.tid.isDescendantOf($currentTid530))
                                continue $label525;
                            if ($currentTid530.parent != null) {
                                $retry527 = false;
                                throw $e529;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e529) {
                            $commit526 = false;
                            if ($tm531.checkForStaleObjects())
                                continue $label525;
                            fabric.common.TransactionID $currentTid530 =
                              $tm531.getCurrentTid();
                            if ($e529.tid.isDescendantOf($currentTid530)) {
                                $retry527 = true;
                            }
                            else if ($currentTid530.parent != null) {
                                $retry527 = false;
                                throw $e529;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e529) {
                            $commit526 = false;
                            if ($tm531.checkForStaleObjects())
                                continue $label525;
                            $retry527 = false;
                            throw new fabric.worker.AbortException($e529);
                        }
                        finally {
                            if ($commit526) {
                                fabric.common.TransactionID $currentTid530 =
                                  $tm531.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e529) {
                                    $commit526 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e529) {
                                    $commit526 = false;
                                    $retry527 = false;
                                    $keepReads528 = $e529.keepReads;
                                    if ($tm531.checkForStaleObjects()) {
                                        $retry527 = true;
                                        $keepReads528 = false;
                                        continue $label525;
                                    }
                                    if ($e529.tid ==
                                          null ||
                                          !$e529.tid.isDescendantOf(
                                                       $currentTid530))
                                        throw $e529;
                                    throw new fabric.worker.UserAbortException(
                                            $e529);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e529) {
                                    $commit526 = false;
                                    $currentTid530 = $tm531.getCurrentTid();
                                    if ($currentTid530 != null) {
                                        if ($e529.tid.equals($currentTid530) ||
                                              !$e529.tid.isDescendantOf(
                                                           $currentTid530)) {
                                            throw $e529;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads528) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit526) {
                                {  }
                                if ($retry527) { continue $label525; }
                            }
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$observers();
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
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINE,
                            "HANDLING OBSERVER {0} IN {1}",
                            new java.lang.Object[] { unhandled,
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentTid() });
                        fabric.worker.metrics.ImmutableObserverSet
                          parentsToCheck =
                          ((fabric.metrics.util.Observer)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               fabric.lang.WrappedJavaInlineable.
                                   $wrap(unhandled.first))).
                          handleUpdates(((java.lang.Boolean)
                                           unhandled.second).booleanValue(),
                                        (java.util.SortedSet) unhandled.third);
                        for (java.util.Iterator iter =
                               parentsToCheck.iterator();
                             iter.hasNext();
                             ) {
                            queue.add(iter.next());
                        }
                    }
                    else {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINE,
                            "DELAYED HANDLING OBSERVER {0} IN {1}",
                            new java.lang.Object[] { unhandled,
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentTid() });
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
            $writeInline(out, this.observers);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.observers = (fabric.worker.metrics.ImmutableObserverSet)
                               in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.AbstractSubject._Impl src =
              (fabric.metrics.util.AbstractSubject._Impl) other;
            this.observers = src.observers;
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -107, -62, 59, 11, -30,
    -101, 70, -24, 111, -35, 51, 112, 13, -58, -105, 58, -80, 99, 72, -52, 100,
    -96, -12, -47, 123, 44, 47, -74, -52, -113, 93, 51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548271113000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
