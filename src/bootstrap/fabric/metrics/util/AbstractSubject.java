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
    public fabric.metrics.util.ObserversBox get$observers();
    
    public fabric.metrics.util.ObserversBox set$observers(
      fabric.metrics.util.ObserversBox val);
    
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
        public fabric.metrics.util.ObserversBox get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.metrics.util.ObserversBox set$observers(
          fabric.metrics.util.ObserversBox val) {
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
        public fabric.metrics.util.ObserversBox get$observers() {
            return this.observers;
        }
        
        public fabric.metrics.util.ObserversBox set$observers(
          fabric.metrics.util.ObserversBox val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.metrics.util.ObserversBox observers;
        
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            fabric$lang$Object$();
            this.set$observers(
                   ((fabric.metrics.util.ObserversBox)
                      new fabric.metrics.util.ObserversBox._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$ObserversBox$(
                                         (fabric.metrics.util.AbstractSubject)
                                           this.$getProxy()));
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
                    tmp.get$observers().add(o);
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
                                if (!tmp.get$observers().contains(o))
                                    tmp.get$observers().add(o);
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
                    tmp.get$observers().remove(o);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm541 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled544 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff542 = 1;
                    boolean $doBackoff543 = true;
                    boolean $retry537 = true;
                    boolean $keepReads538 = false;
                    $label535: for (boolean $commit536 = false; !$commit536; ) {
                        if ($backoffEnabled544) {
                            if ($doBackoff543) {
                                if ($backoff542 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff542));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e539) {
                                            
                                        }
                                    }
                                }
                                if ($backoff542 < 5000) $backoff542 *= 2;
                            }
                            $doBackoff543 = $backoff542 <= 32 || !$doBackoff543;
                        }
                        $commit536 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o))
                                    tmp.get$observers().remove(o);
                            }
                            catch (final fabric.worker.RetryException $e539) {
                                throw $e539;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e539) {
                                throw $e539;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e539) {
                                throw $e539;
                            }
                            catch (final Throwable $e539) {
                                $tm541.getCurrentLog().checkRetrySignal();
                                throw $e539;
                            }
                        }
                        catch (final fabric.worker.RetryException $e539) {
                            $commit536 = false;
                            continue $label535;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e539) {
                            $commit536 = false;
                            $retry537 = false;
                            $keepReads538 = $e539.keepReads;
                            if ($tm541.checkForStaleObjects()) {
                                $retry537 = true;
                                $keepReads538 = false;
                                continue $label535;
                            }
                            fabric.common.TransactionID $currentTid540 =
                              $tm541.getCurrentTid();
                            if ($e539.tid == null ||
                                  !$e539.tid.isDescendantOf($currentTid540)) {
                                throw $e539;
                            }
                            throw new fabric.worker.UserAbortException($e539);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e539) {
                            $commit536 = false;
                            fabric.common.TransactionID $currentTid540 =
                              $tm541.getCurrentTid();
                            if ($e539.tid.isDescendantOf($currentTid540))
                                continue $label535;
                            if ($currentTid540.parent != null) {
                                $retry537 = false;
                                throw $e539;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e539) {
                            $commit536 = false;
                            if ($tm541.checkForStaleObjects())
                                continue $label535;
                            $retry537 = false;
                            throw new fabric.worker.AbortException($e539);
                        }
                        finally {
                            if ($commit536) {
                                fabric.common.TransactionID $currentTid540 =
                                  $tm541.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e539) {
                                    $commit536 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e539) {
                                    $commit536 = false;
                                    $retry537 = false;
                                    $keepReads538 = $e539.keepReads;
                                    if ($tm541.checkForStaleObjects()) {
                                        $retry537 = true;
                                        $keepReads538 = false;
                                        continue $label535;
                                    }
                                    if ($e539.tid ==
                                          null ||
                                          !$e539.tid.isDescendantOf(
                                                       $currentTid540))
                                        throw $e539;
                                    throw new fabric.worker.UserAbortException(
                                            $e539);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e539) {
                                    $commit536 = false;
                                    $currentTid540 = $tm541.getCurrentTid();
                                    if ($currentTid540 != null) {
                                        if ($e539.tid.equals($currentTid540) ||
                                              !$e539.tid.isDescendantOf(
                                                           $currentTid540)) {
                                            throw $e539;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads538) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit536) {
                                {  }
                                if ($retry537) { continue $label535; }
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
            return this.get$observers().getObservers();
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
                    fabric.metrics.util.Observer
                      unhandled =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              queue.poll()));
                    delayed.remove(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                    unhandled));
                    boolean needToWait = false;
                    fabric.worker.metrics.ImmutableMetricsVector leaves =
                      unhandled.getLeafSubjects();
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
                            new java.lang.Object[] { (java.lang.Object)
                                                       fabric.lang.WrappedJavaInlineable.
                                                       $unwrap(unhandled),
                              fabric.worker.transaction.TransactionManager.
                                getInstance().
                                getCurrentTid() });
                        fabric.worker.metrics.ImmutableObserverSet
                          parentsToCheck = unhandled.handleUpdates();
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
                            new java.lang.Object[] { (java.lang.Object)
                                                       fabric.lang.WrappedJavaInlineable.
                                                       $unwrap(unhandled),
                              fabric.worker.transaction.TransactionManager.
                                getInstance().
                                getCurrentTid() });
                        delayed.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  unhandled));
                    }
                    if (queue.isEmpty()) {
                        java.util.Iterator delayedIter = delayed.iterator();
                        while (delayedIter.hasNext()) {
                            fabric.metrics.util.Observer
                              withheld =
                              (fabric.metrics.util.Observer)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(delayedIter.next()));
                            boolean doneWaiting = true;
                            fabric.worker.metrics.ImmutableMetricsVector
                              withheldLeaves = withheld.getLeafSubjects();
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
                            if (doneWaiting) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(withheld));
                            }
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
            $writeRef($getStore(), this.observers, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.observers =
              (fabric.metrics.util.ObserversBox)
                $readRef(fabric.metrics.util.ObserversBox._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
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
    
    public static final byte[] $classHash = new byte[] { -39, 33, -53, 41, 34,
    95, -9, -93, 6, -107, -57, 22, 101, -29, -11, -20, 101, 91, -7, -11, 53,
    -23, -119, 10, 32, 6, 124, -102, -93, 101, 62, -19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
