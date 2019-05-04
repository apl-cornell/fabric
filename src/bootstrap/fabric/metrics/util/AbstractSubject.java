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
                    fabric.worker.transaction.TransactionManager $tm478 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled481 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff479 = 1;
                    boolean $doBackoff480 = true;
                    boolean $retry474 = true;
                    boolean $keepReads475 = false;
                    $label472: for (boolean $commit473 = false; !$commit473; ) {
                        if ($backoffEnabled481) {
                            if ($doBackoff480) {
                                if ($backoff479 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff479));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e476) {
                                            
                                        }
                                    }
                                }
                                if ($backoff479 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff479 =
                                      java.lang.Math.
                                        min(
                                          $backoff479 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff480 = $backoff479 <= 32 || !$doBackoff480;
                        }
                        $commit473 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e476) {
                            $commit473 = false;
                            continue $label472;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e476) {
                            $commit473 = false;
                            $retry474 = false;
                            $keepReads475 = $e476.keepReads;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid == null ||
                                  !$e476.tid.isDescendantOf($currentTid477)) {
                                throw $e476;
                            }
                            throw new fabric.worker.UserAbortException($e476);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e476) {
                            $commit473 = false;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477))
                                continue $label472;
                            if ($currentTid477.parent != null) {
                                $retry474 = false;
                                throw $e476;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e476) {
                            $commit473 = false;
                            $retry474 = false;
                            if ($tm478.inNestedTxn()) { $keepReads475 = true; }
                            throw $e476;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($commit473) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e476) {
                                    $commit473 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e476) {
                                    $commit473 = false;
                                    $retry474 = false;
                                    $keepReads475 = $e476.keepReads;
                                    if ($e476.tid ==
                                          null ||
                                          !$e476.tid.isDescendantOf(
                                                       $currentTid477))
                                        throw $e476;
                                    throw new fabric.worker.UserAbortException(
                                            $e476);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e476) {
                                    $commit473 = false;
                                    $currentTid477 = $tm478.getCurrentTid();
                                    if ($currentTid477 != null) {
                                        if ($e476.tid.equals($currentTid477) ||
                                              !$e476.tid.isDescendantOf(
                                                           $currentTid477)) {
                                            throw $e476;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm478.inNestedTxn() &&
                                      $tm478.checkForStaleObjects()) {
                                    $retry474 = true;
                                    $keepReads475 = false;
                                }
                                if ($keepReads475) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e476) {
                                        $currentTid477 = $tm478.getCurrentTid();
                                        if ($currentTid477 != null &&
                                              ($e476.tid.equals($currentTid477) || !$e476.tid.isDescendantOf($currentTid477))) {
                                            throw $e476;
                                        } else {
                                            $retry474 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit473) {
                                {  }
                                if ($retry474) { continue $label472; }
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
                    fabric.worker.transaction.TransactionManager $tm488 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled491 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff489 = 1;
                    boolean $doBackoff490 = true;
                    boolean $retry484 = true;
                    boolean $keepReads485 = false;
                    $label482: for (boolean $commit483 = false; !$commit483; ) {
                        if ($backoffEnabled491) {
                            if ($doBackoff490) {
                                if ($backoff489 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff489));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e486) {
                                            
                                        }
                                    }
                                }
                                if ($backoff489 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff489 =
                                      java.lang.Math.
                                        min(
                                          $backoff489 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff490 = $backoff489 <= 32 || !$doBackoff490;
                        }
                        $commit483 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$observers().contains(o))
                                tmp.get$observers().remove(o);
                        }
                        catch (final fabric.worker.RetryException $e486) {
                            $commit483 = false;
                            continue $label482;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e486) {
                            $commit483 = false;
                            $retry484 = false;
                            $keepReads485 = $e486.keepReads;
                            fabric.common.TransactionID $currentTid487 =
                              $tm488.getCurrentTid();
                            if ($e486.tid == null ||
                                  !$e486.tid.isDescendantOf($currentTid487)) {
                                throw $e486;
                            }
                            throw new fabric.worker.UserAbortException($e486);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e486) {
                            $commit483 = false;
                            fabric.common.TransactionID $currentTid487 =
                              $tm488.getCurrentTid();
                            if ($e486.tid.isDescendantOf($currentTid487))
                                continue $label482;
                            if ($currentTid487.parent != null) {
                                $retry484 = false;
                                throw $e486;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e486) {
                            $commit483 = false;
                            $retry484 = false;
                            if ($tm488.inNestedTxn()) { $keepReads485 = true; }
                            throw $e486;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid487 =
                              $tm488.getCurrentTid();
                            if ($commit483) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e486) {
                                    $commit483 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e486) {
                                    $commit483 = false;
                                    $retry484 = false;
                                    $keepReads485 = $e486.keepReads;
                                    if ($e486.tid ==
                                          null ||
                                          !$e486.tid.isDescendantOf(
                                                       $currentTid487))
                                        throw $e486;
                                    throw new fabric.worker.UserAbortException(
                                            $e486);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e486) {
                                    $commit483 = false;
                                    $currentTid487 = $tm488.getCurrentTid();
                                    if ($currentTid487 != null) {
                                        if ($e486.tid.equals($currentTid487) ||
                                              !$e486.tid.isDescendantOf(
                                                           $currentTid487)) {
                                            throw $e486;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm488.inNestedTxn() &&
                                      $tm488.checkForStaleObjects()) {
                                    $retry484 = true;
                                    $keepReads485 = false;
                                }
                                if ($keepReads485) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e486) {
                                        $currentTid487 = $tm488.getCurrentTid();
                                        if ($currentTid487 != null &&
                                              ($e486.tid.equals($currentTid487) || !$e486.tid.isDescendantOf($currentTid487))) {
                                            throw $e486;
                                        } else {
                                            $retry484 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit483) {
                                {  }
                                if ($retry484) { continue $label482; }
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
                        delayed.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  unhandled));
                    }
                    if (queue.isEmpty()) {
                        for (java.util.Iterator delayedIter =
                               delayed.iterator();
                             delayedIter.hasNext();
                             ) {
                            fabric.metrics.util.Observer
                              withheld =
                              (fabric.metrics.util.Observer)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(delayedIter.next()));
                            fabric.worker.metrics.ImmutableMetricsVector
                              withheldLeaves = withheld.getLeafSubjects();
                            boolean doneWaiting = true;
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
    
    public static final byte[] $classHash = new byte[] { -100, -79, -20, -93,
    -42, 60, -79, -17, -100, 126, 126, 107, 66, -2, 125, -82, 45, -125, 22, 13,
    52, -107, -42, 115, -1, -81, 88, 103, 116, 121, -26, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
