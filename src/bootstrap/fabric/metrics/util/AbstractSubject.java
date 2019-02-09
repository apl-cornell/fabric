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
                    fabric.worker.transaction.TransactionManager $tm500 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled503 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff501 = 1;
                    boolean $doBackoff502 = true;
                    boolean $retry496 = true;
                    boolean $keepReads497 = false;
                    $label494: for (boolean $commit495 = false; !$commit495; ) {
                        if ($backoffEnabled503) {
                            if ($doBackoff502) {
                                if ($backoff501 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff501));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e498) {
                                            
                                        }
                                    }
                                }
                                if ($backoff501 < 5000) $backoff501 *= 2;
                            }
                            $doBackoff502 = $backoff501 <= 32 || !$doBackoff502;
                        }
                        $commit495 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$observers().contains(o))
                                    tmp.get$observers().add(o);
                            }
                            catch (final fabric.worker.RetryException $e498) {
                                throw $e498;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e498) {
                                throw $e498;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e498) {
                                throw $e498;
                            }
                            catch (final Throwable $e498) {
                                $tm500.getCurrentLog().checkRetrySignal();
                                throw $e498;
                            }
                        }
                        catch (final fabric.worker.RetryException $e498) {
                            $commit495 = false;
                            continue $label494;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e498) {
                            $commit495 = false;
                            $retry496 = false;
                            $keepReads497 = $e498.keepReads;
                            if ($tm500.checkForStaleObjects()) {
                                $retry496 = true;
                                $keepReads497 = false;
                                continue $label494;
                            }
                            fabric.common.TransactionID $currentTid499 =
                              $tm500.getCurrentTid();
                            if ($e498.tid == null ||
                                  !$e498.tid.isDescendantOf($currentTid499)) {
                                throw $e498;
                            }
                            throw new fabric.worker.UserAbortException($e498);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e498) {
                            $commit495 = false;
                            fabric.common.TransactionID $currentTid499 =
                              $tm500.getCurrentTid();
                            if ($e498.tid.isDescendantOf($currentTid499))
                                continue $label494;
                            if ($currentTid499.parent != null) {
                                $retry496 = false;
                                throw $e498;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e498) {
                            $commit495 = false;
                            if ($tm500.checkForStaleObjects())
                                continue $label494;
                            $retry496 = false;
                            throw new fabric.worker.AbortException($e498);
                        }
                        finally {
                            if ($commit495) {
                                fabric.common.TransactionID $currentTid499 =
                                  $tm500.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e498) {
                                    $commit495 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e498) {
                                    $commit495 = false;
                                    $retry496 = false;
                                    $keepReads497 = $e498.keepReads;
                                    if ($tm500.checkForStaleObjects()) {
                                        $retry496 = true;
                                        $keepReads497 = false;
                                        continue $label494;
                                    }
                                    if ($e498.tid ==
                                          null ||
                                          !$e498.tid.isDescendantOf(
                                                       $currentTid499))
                                        throw $e498;
                                    throw new fabric.worker.UserAbortException(
                                            $e498);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e498) {
                                    $commit495 = false;
                                    $currentTid499 = $tm500.getCurrentTid();
                                    if ($currentTid499 != null) {
                                        if ($e498.tid.equals($currentTid499) ||
                                              !$e498.tid.isDescendantOf(
                                                           $currentTid499)) {
                                            throw $e498;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads497) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit495) {
                                {  }
                                if ($retry496) { continue $label494; }
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
                    fabric.worker.transaction.TransactionManager $tm510 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled513 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff511 = 1;
                    boolean $doBackoff512 = true;
                    boolean $retry506 = true;
                    boolean $keepReads507 = false;
                    $label504: for (boolean $commit505 = false; !$commit505; ) {
                        if ($backoffEnabled513) {
                            if ($doBackoff512) {
                                if ($backoff511 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff511));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e508) {
                                            
                                        }
                                    }
                                }
                                if ($backoff511 < 5000) $backoff511 *= 2;
                            }
                            $doBackoff512 = $backoff511 <= 32 || !$doBackoff512;
                        }
                        $commit505 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o))
                                    tmp.get$observers().remove(o);
                            }
                            catch (final fabric.worker.RetryException $e508) {
                                throw $e508;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e508) {
                                throw $e508;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e508) {
                                throw $e508;
                            }
                            catch (final Throwable $e508) {
                                $tm510.getCurrentLog().checkRetrySignal();
                                throw $e508;
                            }
                        }
                        catch (final fabric.worker.RetryException $e508) {
                            $commit505 = false;
                            continue $label504;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e508) {
                            $commit505 = false;
                            $retry506 = false;
                            $keepReads507 = $e508.keepReads;
                            if ($tm510.checkForStaleObjects()) {
                                $retry506 = true;
                                $keepReads507 = false;
                                continue $label504;
                            }
                            fabric.common.TransactionID $currentTid509 =
                              $tm510.getCurrentTid();
                            if ($e508.tid == null ||
                                  !$e508.tid.isDescendantOf($currentTid509)) {
                                throw $e508;
                            }
                            throw new fabric.worker.UserAbortException($e508);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e508) {
                            $commit505 = false;
                            fabric.common.TransactionID $currentTid509 =
                              $tm510.getCurrentTid();
                            if ($e508.tid.isDescendantOf($currentTid509))
                                continue $label504;
                            if ($currentTid509.parent != null) {
                                $retry506 = false;
                                throw $e508;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e508) {
                            $commit505 = false;
                            if ($tm510.checkForStaleObjects())
                                continue $label504;
                            $retry506 = false;
                            throw new fabric.worker.AbortException($e508);
                        }
                        finally {
                            if ($commit505) {
                                fabric.common.TransactionID $currentTid509 =
                                  $tm510.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e508) {
                                    $commit505 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e508) {
                                    $commit505 = false;
                                    $retry506 = false;
                                    $keepReads507 = $e508.keepReads;
                                    if ($tm510.checkForStaleObjects()) {
                                        $retry506 = true;
                                        $keepReads507 = false;
                                        continue $label504;
                                    }
                                    if ($e508.tid ==
                                          null ||
                                          !$e508.tid.isDescendantOf(
                                                       $currentTid509))
                                        throw $e508;
                                    throw new fabric.worker.UserAbortException(
                                            $e508);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e508) {
                                    $commit505 = false;
                                    $currentTid509 = $tm510.getCurrentTid();
                                    if ($currentTid509 != null) {
                                        if ($e508.tid.equals($currentTid509) ||
                                              !$e508.tid.isDescendantOf(
                                                           $currentTid509)) {
                                            throw $e508;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads507) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit505) {
                                {  }
                                if ($retry506) { continue $label504; }
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
