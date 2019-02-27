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
                    fabric.worker.transaction.TransactionManager $tm37 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled40 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff38 = 1;
                    boolean $doBackoff39 = true;
                    boolean $retry33 = true;
                    boolean $keepReads34 = false;
                    $label31: for (boolean $commit32 = false; !$commit32; ) {
                        if ($backoffEnabled40) {
                            if ($doBackoff39) {
                                if ($backoff38 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff38));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e35) {
                                            
                                        }
                                    }
                                }
                                if ($backoff38 < 5000) $backoff38 *= 2;
                            }
                            $doBackoff39 = $backoff38 <= 32 || !$doBackoff39;
                        }
                        $commit32 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (!tmp.get$observers().contains(o))
                                    tmp.get$observers().add(o);
                            }
                            catch (final fabric.worker.RetryException $e35) {
                                throw $e35;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e35) {
                                throw $e35;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e35) {
                                throw $e35;
                            }
                            catch (final Throwable $e35) {
                                $tm37.getCurrentLog().checkRetrySignal();
                                throw $e35;
                            }
                        }
                        catch (final fabric.worker.RetryException $e35) {
                            $commit32 = false;
                            continue $label31;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e35) {
                            $commit32 = false;
                            $retry33 = false;
                            $keepReads34 = $e35.keepReads;
                            if ($tm37.checkForStaleObjects()) {
                                $retry33 = true;
                                $keepReads34 = false;
                                continue $label31;
                            }
                            fabric.common.TransactionID $currentTid36 =
                              $tm37.getCurrentTid();
                            if ($e35.tid == null ||
                                  !$e35.tid.isDescendantOf($currentTid36)) {
                                throw $e35;
                            }
                            throw new fabric.worker.UserAbortException($e35);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e35) {
                            $commit32 = false;
                            fabric.common.TransactionID $currentTid36 =
                              $tm37.getCurrentTid();
                            if ($e35.tid.isDescendantOf($currentTid36))
                                continue $label31;
                            if ($currentTid36.parent != null) {
                                $retry33 = false;
                                throw $e35;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e35) {
                            $commit32 = false;
                            if ($tm37.checkForStaleObjects()) continue $label31;
                            $retry33 = false;
                            throw new fabric.worker.AbortException($e35);
                        }
                        finally {
                            if ($commit32) {
                                fabric.common.TransactionID $currentTid36 =
                                  $tm37.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e35) {
                                    $commit32 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e35) {
                                    $commit32 = false;
                                    $retry33 = false;
                                    $keepReads34 = $e35.keepReads;
                                    if ($tm37.checkForStaleObjects()) {
                                        $retry33 = true;
                                        $keepReads34 = false;
                                        continue $label31;
                                    }
                                    if ($e35.tid ==
                                          null ||
                                          !$e35.tid.isDescendantOf(
                                                      $currentTid36))
                                        throw $e35;
                                    throw new fabric.worker.UserAbortException(
                                            $e35);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e35) {
                                    $commit32 = false;
                                    $currentTid36 = $tm37.getCurrentTid();
                                    if ($currentTid36 != null) {
                                        if ($e35.tid.equals($currentTid36) ||
                                              !$e35.tid.isDescendantOf(
                                                          $currentTid36)) {
                                            throw $e35;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads34) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit32) {
                                {  }
                                if ($retry33) { continue $label31; }
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
                    fabric.worker.transaction.TransactionManager $tm47 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled50 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff48 = 1;
                    boolean $doBackoff49 = true;
                    boolean $retry43 = true;
                    boolean $keepReads44 = false;
                    $label41: for (boolean $commit42 = false; !$commit42; ) {
                        if ($backoffEnabled50) {
                            if ($doBackoff49) {
                                if ($backoff48 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.
                                              sleep(
                                                java.lang.Math.
                                                    round(
                                                      java.lang.Math.random() *
                                                          $backoff48));
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e45) {
                                            
                                        }
                                    }
                                }
                                if ($backoff48 < 5000) $backoff48 *= 2;
                            }
                            $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                        }
                        $commit42 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            try {
                                if (tmp.get$observers().contains(o))
                                    tmp.get$observers().remove(o);
                            }
                            catch (final fabric.worker.RetryException $e45) {
                                throw $e45;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e45) {
                                throw $e45;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e45) {
                                throw $e45;
                            }
                            catch (final Throwable $e45) {
                                $tm47.getCurrentLog().checkRetrySignal();
                                throw $e45;
                            }
                        }
                        catch (final fabric.worker.RetryException $e45) {
                            $commit42 = false;
                            continue $label41;
                        }
                        catch (fabric.worker.
                                 TransactionAbortingException $e45) {
                            $commit42 = false;
                            $retry43 = false;
                            $keepReads44 = $e45.keepReads;
                            if ($tm47.checkForStaleObjects()) {
                                $retry43 = true;
                                $keepReads44 = false;
                                continue $label41;
                            }
                            fabric.common.TransactionID $currentTid46 =
                              $tm47.getCurrentTid();
                            if ($e45.tid == null ||
                                  !$e45.tid.isDescendantOf($currentTid46)) {
                                throw $e45;
                            }
                            throw new fabric.worker.UserAbortException($e45);
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e45) {
                            $commit42 = false;
                            fabric.common.TransactionID $currentTid46 =
                              $tm47.getCurrentTid();
                            if ($e45.tid.isDescendantOf($currentTid46))
                                continue $label41;
                            if ($currentTid46.parent != null) {
                                $retry43 = false;
                                throw $e45;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e45) {
                            $commit42 = false;
                            if ($tm47.checkForStaleObjects()) continue $label41;
                            $retry43 = false;
                            throw new fabric.worker.AbortException($e45);
                        }
                        finally {
                            if ($commit42) {
                                fabric.common.TransactionID $currentTid46 =
                                  $tm47.getCurrentTid();
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e45) {
                                    $commit42 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e45) {
                                    $commit42 = false;
                                    $retry43 = false;
                                    $keepReads44 = $e45.keepReads;
                                    if ($tm47.checkForStaleObjects()) {
                                        $retry43 = true;
                                        $keepReads44 = false;
                                        continue $label41;
                                    }
                                    if ($e45.tid ==
                                          null ||
                                          !$e45.tid.isDescendantOf(
                                                      $currentTid46))
                                        throw $e45;
                                    throw new fabric.worker.UserAbortException(
                                            $e45);
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e45) {
                                    $commit42 = false;
                                    $currentTid46 = $tm47.getCurrentTid();
                                    if ($currentTid46 != null) {
                                        if ($e45.tid.equals($currentTid46) ||
                                              !$e45.tid.isDescendantOf(
                                                          $currentTid46)) {
                                            throw $e45;
                                        }
                                    }
                                }
                            }
                            else if ($keepReads44) {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransactionUpdates();
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit42) {
                                {  }
                                if ($retry43) { continue $label41; }
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
    public static final long jlc$SourceLastModified$fabil = 1551213340000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
