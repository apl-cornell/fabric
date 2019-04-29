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
                    fabric.worker.transaction.TransactionManager $tm509 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled512 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff510 = 1;
                    boolean $doBackoff511 = true;
                    boolean $retry505 = true;
                    boolean $keepReads506 = false;
                    $label503: for (boolean $commit504 = false; !$commit504; ) {
                        if ($backoffEnabled512) {
                            if ($doBackoff511) {
                                if ($backoff510 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff510));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e507) {
                                            
                                        }
                                    }
                                }
                                if ($backoff510 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff510 =
                                      java.lang.Math.
                                        min(
                                          $backoff510 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff511 = $backoff510 <= 32 || !$doBackoff511;
                        }
                        $commit504 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e507) {
                            $commit504 = false;
                            continue $label503;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e507) {
                            $commit504 = false;
                            $retry505 = false;
                            $keepReads506 = $e507.keepReads;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid == null ||
                                  !$e507.tid.isDescendantOf($currentTid508)) {
                                throw $e507;
                            }
                            throw new fabric.worker.UserAbortException($e507);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e507) {
                            $commit504 = false;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508))
                                continue $label503;
                            if ($currentTid508.parent != null) {
                                $retry505 = false;
                                throw $e507;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e507) {
                            $commit504 = false;
                            $retry505 = false;
                            if ($tm509.inNestedTxn()) { $keepReads506 = true; }
                            throw $e507;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($commit504) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e507) {
                                    $commit504 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e507) {
                                    $commit504 = false;
                                    $retry505 = false;
                                    $keepReads506 = $e507.keepReads;
                                    if ($e507.tid ==
                                          null ||
                                          !$e507.tid.isDescendantOf(
                                                       $currentTid508))
                                        throw $e507;
                                    throw new fabric.worker.UserAbortException(
                                            $e507);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e507) {
                                    $commit504 = false;
                                    $currentTid508 = $tm509.getCurrentTid();
                                    if ($currentTid508 != null) {
                                        if ($e507.tid.equals($currentTid508) ||
                                              !$e507.tid.isDescendantOf(
                                                           $currentTid508)) {
                                            throw $e507;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm509.inNestedTxn() &&
                                      $tm509.checkForStaleObjects()) {
                                    $retry505 = true;
                                    $keepReads506 = false;
                                }
                                if ($keepReads506) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e507) {
                                        $currentTid508 = $tm509.getCurrentTid();
                                        if ($currentTid508 != null &&
                                              ($e507.tid.equals($currentTid508) || !$e507.tid.isDescendantOf($currentTid508))) {
                                            throw $e507;
                                        } else {
                                            $retry505 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit504) {
                                {  }
                                if ($retry505) { continue $label503; }
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
                    fabric.worker.transaction.TransactionManager $tm519 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled522 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    long $backoff520 = 1;
                    boolean $doBackoff521 = true;
                    boolean $retry515 = true;
                    boolean $keepReads516 = false;
                    $label513: for (boolean $commit514 = false; !$commit514; ) {
                        if ($backoffEnabled522) {
                            if ($doBackoff521) {
                                if ($backoff520 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff520));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e517) {
                                            
                                        }
                                    }
                                }
                                if ($backoff520 <
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff)
                                    $backoff520 =
                                      java.lang.Math.
                                        min(
                                          $backoff520 * 2,
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff);
                            }
                            $doBackoff521 = $backoff520 <= 32 || !$doBackoff521;
                        }
                        $commit514 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$observers().contains(o))
                                tmp.get$observers().remove(o);
                        }
                        catch (final fabric.worker.RetryException $e517) {
                            $commit514 = false;
                            continue $label513;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e517) {
                            $commit514 = false;
                            $retry515 = false;
                            $keepReads516 = $e517.keepReads;
                            fabric.common.TransactionID $currentTid518 =
                              $tm519.getCurrentTid();
                            if ($e517.tid == null ||
                                  !$e517.tid.isDescendantOf($currentTid518)) {
                                throw $e517;
                            }
                            throw new fabric.worker.UserAbortException($e517);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e517) {
                            $commit514 = false;
                            fabric.common.TransactionID $currentTid518 =
                              $tm519.getCurrentTid();
                            if ($e517.tid.isDescendantOf($currentTid518))
                                continue $label513;
                            if ($currentTid518.parent != null) {
                                $retry515 = false;
                                throw $e517;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e517) {
                            $commit514 = false;
                            $retry515 = false;
                            if ($tm519.inNestedTxn()) { $keepReads516 = true; }
                            throw $e517;
                        }
                        finally {
                            fabric.common.TransactionID $currentTid518 =
                              $tm519.getCurrentTid();
                            if ($commit514) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e517) {
                                    $commit514 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e517) {
                                    $commit514 = false;
                                    $retry515 = false;
                                    $keepReads516 = $e517.keepReads;
                                    if ($e517.tid ==
                                          null ||
                                          !$e517.tid.isDescendantOf(
                                                       $currentTid518))
                                        throw $e517;
                                    throw new fabric.worker.UserAbortException(
                                            $e517);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e517) {
                                    $commit514 = false;
                                    $currentTid518 = $tm519.getCurrentTid();
                                    if ($currentTid518 != null) {
                                        if ($e517.tid.equals($currentTid518) ||
                                              !$e517.tid.isDescendantOf(
                                                           $currentTid518)) {
                                            throw $e517;
                                        }
                                    }
                                }
                            } else {
                                if (!$tm519.inNestedTxn() &&
                                      $tm519.checkForStaleObjects()) {
                                    $retry515 = true;
                                    $keepReads516 = false;
                                }
                                if ($keepReads516) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                    }
                                    catch (final fabric.worker.TransactionRestartingException $e517) {
                                        $currentTid518 = $tm519.getCurrentTid();
                                        if ($currentTid518 != null &&
                                              ($e517.tid.equals($currentTid518) || !$e517.tid.isDescendantOf($currentTid518))) {
                                            throw $e517;
                                        } else {
                                            $retry515 = true;
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                            }
                            if (!$commit514) {
                                {  }
                                if ($retry515) { continue $label513; }
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
    
    public static final byte[] $classHash = new byte[] { 11, 91, 11, 17, -99,
    -124, 46, 1, 56, 28, -48, -78, -75, -128, 2, -103, 46, -57, -21, 74, -5, 89,
    9, -1, 117, 119, 37, -47, 89, 39, 45, 1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556306458000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
