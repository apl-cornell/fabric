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
            //this.set$$observers(
            //       fabric.worker.metrics.ImmutableObserverSet.emptySet());
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
                //if (!tmp.get$$observers().contains(o))
                //    tmp.set$$observers(tmp.get$$observers().add(o));
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
                                //if (!tmp.get$$observers().contains(o))
                                //    tmp.set$$observers(
                                //          tmp.get$$observers().add(o));
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                //if (!tmp.get$$observers().contains(o, id))
                //    tmp.set$$observers(tmp.get$$observers().add(o, id));
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
                                //if (!tmp.get$$observers().contains(o, id))
                                //    tmp.set$$observers(
                                //          tmp.get$$observers().add(o, id));
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                //if (tmp.get$$observers().contains(o))
                //    tmp.set$$observers(tmp.get$$observers().remove(o));
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
                                //if (tmp.get$$observers().contains(o))
                                //    tmp.set$$observers(
                                //          tmp.get$$observers().remove(o));
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
                //if (tmp.get$$observers().contains(o, id))
                //    tmp.set$$observers(tmp.get$$observers().remove(o, id));
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
                                //if (tmp.get$$observers().contains(o, id))
                                //    tmp.set$$observers(
                                //          tmp.get$$observers().remove(o, id));
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
                            throw new fabric.worker.UserAbortException();
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
                                            );
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
            return false;
            //return this.get$$observers().contains(o);
        }

        public boolean isObserved() {
          return false;
          //return !this.get$$observers().isEmpty();
        }

        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return null;
            //return this.get$$observers();
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
        }

        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,

                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties,
                  labelStore, labelOnum, accessPolicyStore, accessPolicyOnum,
                  in, refTypes, intraStoreRefs, interStoreRefs);
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

                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties,
                      labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs,
                      interStoreRefs);
            }

            public _Impl(fabric.worker.Store store) { super(store); }

            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }

            private void $init() {  }
        }

    }

    public static final byte[] $classHash = new byte[] { 73, 56, 105, 122, -60,
    10, -81, -7, 28, 13, -88, -55, -9, -20, -57, -28, 107, 86, -98, -42, 59, 83,
    -29, -108, -117, -10, 31, -126, -50, -34, -58, 29 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1537039040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
