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
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
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
                                    tmp.set$observers(
                                          tmp.get$observers().add(o));
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
                    tmp.set$observers(tmp.get$observers().remove(o));
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
                                    tmp.set$observers(
                                          tmp.get$observers().remove(o));
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
    
    public static final byte[] $classHash = new byte[] { 30, 108, 27, 39, -104,
    -107, -15, 87, -47, 122, 107, 79, -105, -39, -43, 111, -65, -78, 101, -26,
    -7, -20, -84, 30, 18, -103, -9, -119, -32, -99, -69, 27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549063467000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
