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
                    fabric.worker.transaction.TransactionManager $tm487 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled490 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff488 = 1;
                    boolean $doBackoff489 = true;
                    boolean $retry483 = true;
                    boolean $keepReads484 = false;
                    $label481: for (boolean $commit482 = false; !$commit482; ) {
                        if ($backoffEnabled490) {
                            if ($doBackoff489) {
                                if ($backoff488 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff488));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e485) {
                                            
                                        }
                                    }
                                }
                                if ($backoff488 < 5000) $backoff488 *= 2;
                            }
                            $doBackoff489 = $backoff488 <= 32 || !$doBackoff489;
                        }
                        $commit482 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$observers().contains(o))
                                    tmp.get$observers().add(o);
                            }
                            catch (final fabric.worker.RetryException $e485) {
                                throw $e485;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e485) {
                                throw $e485;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e485) {
                                throw $e485;
                            }
                            catch (final Throwable $e485) {
                                $tm487.getCurrentLog().checkRetrySignal();
                                throw $e485;
                            }
                        }
                        catch (final fabric.worker.RetryException $e485) {
                            $commit482 = false;
                            continue $label481;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e485) {
                            $commit482 = false;
                            $retry483 = false;
                            $keepReads484 = $e485.keepReads;
                            if ($tm487.checkForStaleObjects()) {
                                $retry483 = true;
                                $keepReads484 = false;
                                continue $label481;
                            }
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid == null ||
                                  !$e485.tid.isDescendantOf($currentTid486)) {
                                throw $e485;
                            }
                            throw new fabric.worker.UserAbortException($e485);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e485) {
                            $commit482 = false;
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid.isDescendantOf($currentTid486))
                                continue $label481;
                            if ($currentTid486.parent != null) {
                                $retry483 = false;
                                throw $e485;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e485) {
                            $commit482 = false;
                            if ($tm487.checkForStaleObjects())
                                continue $label481;
                            $retry483 = false;
                            throw new fabric.worker.AbortException($e485);
                        }
                        finally {
                            if ($commit482) {
                                fabric.common.TransactionID $currentTid486 =
                                  $tm487.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e485) {
                                    $commit482 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e485) {
                                    $commit482 = false;
                                    $retry483 = false;
                                    $keepReads484 = $e485.keepReads;
                                    if ($tm487.checkForStaleObjects()) {
                                        $retry483 = true;
                                        $keepReads484 = false;
                                        continue $label481;
                                    }
                                    if ($e485.tid ==
                                          null ||
                                          !$e485.tid.isDescendantOf(
                                                       $currentTid486))
                                        throw $e485;
                                    throw new fabric.worker.UserAbortException(
                                            $e485);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e485) {
                                    $commit482 = false;
                                    $currentTid486 = $tm487.getCurrentTid();
                                    if ($currentTid486 != null) {
                                        if ($e485.tid.equals($currentTid486) ||
                                              !$e485.tid.isDescendantOf(
                                                           $currentTid486)) {
                                            throw $e485;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads484) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit482) {
                                {  }
                                if ($retry483) { continue $label481; }
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
                    fabric.worker.transaction.TransactionManager $tm497 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled500 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff498 = 1;
                    boolean $doBackoff499 = true;
                    boolean $retry493 = true;
                    boolean $keepReads494 = false;
                    $label491: for (boolean $commit492 = false; !$commit492; ) {
                        if ($backoffEnabled500) {
                            if ($doBackoff499) {
                                if ($backoff498 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff498));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e495) {
                                            
                                        }
                                    }
                                }
                                if ($backoff498 < 5000) $backoff498 *= 2;
                            }
                            $doBackoff499 = $backoff498 <= 32 || !$doBackoff499;
                        }
                        $commit492 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o))
                                    tmp.get$observers().remove(o);
                            }
                            catch (final fabric.worker.RetryException $e495) {
                                throw $e495;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e495) {
                                throw $e495;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e495) {
                                throw $e495;
                            }
                            catch (final Throwable $e495) {
                                $tm497.getCurrentLog().checkRetrySignal();
                                throw $e495;
                            }
                        }
                        catch (final fabric.worker.RetryException $e495) {
                            $commit492 = false;
                            continue $label491;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e495) {
                            $commit492 = false;
                            $retry493 = false;
                            $keepReads494 = $e495.keepReads;
                            if ($tm497.checkForStaleObjects()) {
                                $retry493 = true;
                                $keepReads494 = false;
                                continue $label491;
                            }
                            fabric.common.TransactionID $currentTid496 =
                              $tm497.getCurrentTid();
                            if ($e495.tid == null ||
                                  !$e495.tid.isDescendantOf($currentTid496)) {
                                throw $e495;
                            }
                            throw new fabric.worker.UserAbortException($e495);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e495) {
                            $commit492 = false;
                            fabric.common.TransactionID $currentTid496 =
                              $tm497.getCurrentTid();
                            if ($e495.tid.isDescendantOf($currentTid496))
                                continue $label491;
                            if ($currentTid496.parent != null) {
                                $retry493 = false;
                                throw $e495;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e495) {
                            $commit492 = false;
                            if ($tm497.checkForStaleObjects())
                                continue $label491;
                            $retry493 = false;
                            throw new fabric.worker.AbortException($e495);
                        }
                        finally {
                            if ($commit492) {
                                fabric.common.TransactionID $currentTid496 =
                                  $tm497.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e495) {
                                    $commit492 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e495) {
                                    $commit492 = false;
                                    $retry493 = false;
                                    $keepReads494 = $e495.keepReads;
                                    if ($tm497.checkForStaleObjects()) {
                                        $retry493 = true;
                                        $keepReads494 = false;
                                        continue $label491;
                                    }
                                    if ($e495.tid ==
                                          null ||
                                          !$e495.tid.isDescendantOf(
                                                       $currentTid496))
                                        throw $e495;
                                    throw new fabric.worker.UserAbortException(
                                            $e495);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e495) {
                                    $commit492 = false;
                                    $currentTid496 = $tm497.getCurrentTid();
                                    if ($currentTid496 != null) {
                                        if ($e495.tid.equals($currentTid496) ||
                                              !$e495.tid.isDescendantOf(
                                                           $currentTid496)) {
                                            throw $e495;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads494) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit492) {
                                {  }
                                if ($retry493) { continue $label491; }
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
    
    public static final byte[] $classHash = new byte[] { 90, -7, -40, 97, -11,
    55, 84, -116, -14, -65, -117, 105, 31, 47, 16, -29, 22, 52, -3, 20, 1, -35,
    -103, -100, -25, -128, -80, -80, 19, -54, 118, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
