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
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
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
                    fabric.worker.transaction.TransactionManager $tm683 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled686 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff684 = 1;
                    boolean $doBackoff685 = true;
                    boolean $retry680 = true;
                    $label678: for (boolean $commit679 = false; !$commit679; ) {
                        if ($backoffEnabled686) {
                            if ($doBackoff685) {
                                if ($backoff684 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff684);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e681) {
                                            
                                        }
                                    }
                                }
                                if ($backoff684 < 5000) $backoff684 *= 2;
                            }
                            $doBackoff685 = $backoff684 <= 32 || !$doBackoff685;
                        }
                        $commit679 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e681) {
                            $commit679 = false;
                            continue $label678;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e681) {
                            $commit679 = false;
                            fabric.common.TransactionID $currentTid682 =
                              $tm683.getCurrentTid();
                            if ($e681.tid.isDescendantOf($currentTid682))
                                continue $label678;
                            if ($currentTid682.parent != null) {
                                $retry680 = false;
                                throw $e681;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e681) {
                            $commit679 = false;
                            if ($tm683.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid682 =
                              $tm683.getCurrentTid();
                            if ($e681.tid.isDescendantOf($currentTid682)) {
                                $retry680 = true;
                            }
                            else if ($currentTid682.parent != null) {
                                $retry680 = false;
                                throw $e681;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e681) {
                            $commit679 = false;
                            if ($tm683.checkForStaleObjects())
                                continue $label678;
                            $retry680 = false;
                            throw new fabric.worker.AbortException($e681);
                        }
                        finally {
                            if ($commit679) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e681) {
                                    $commit679 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e681) {
                                    $commit679 = false;
                                    fabric.common.TransactionID $currentTid682 =
                                      $tm683.getCurrentTid();
                                    if ($currentTid682 != null) {
                                        if ($e681.tid.equals($currentTid682) ||
                                              !$e681.tid.isDescendantOf(
                                                           $currentTid682)) {
                                            throw $e681;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit679 && $retry680) {
                                {  }
                                continue $label678;
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
                    fabric.worker.transaction.TransactionManager $tm692 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled695 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff693 = 1;
                    boolean $doBackoff694 = true;
                    boolean $retry689 = true;
                    $label687: for (boolean $commit688 = false; !$commit688; ) {
                        if ($backoffEnabled695) {
                            if ($doBackoff694) {
                                if ($backoff693 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff693);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e690) {
                                            
                                        }
                                    }
                                }
                                if ($backoff693 < 5000) $backoff693 *= 2;
                            }
                            $doBackoff694 = $backoff693 <= 32 || !$doBackoff694;
                        }
                        $commit688 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e690) {
                            $commit688 = false;
                            continue $label687;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e690) {
                            $commit688 = false;
                            fabric.common.TransactionID $currentTid691 =
                              $tm692.getCurrentTid();
                            if ($e690.tid.isDescendantOf($currentTid691))
                                continue $label687;
                            if ($currentTid691.parent != null) {
                                $retry689 = false;
                                throw $e690;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e690) {
                            $commit688 = false;
                            if ($tm692.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid691 =
                              $tm692.getCurrentTid();
                            if ($e690.tid.isDescendantOf($currentTid691)) {
                                $retry689 = true;
                            }
                            else if ($currentTid691.parent != null) {
                                $retry689 = false;
                                throw $e690;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e690) {
                            $commit688 = false;
                            if ($tm692.checkForStaleObjects())
                                continue $label687;
                            $retry689 = false;
                            throw new fabric.worker.AbortException($e690);
                        }
                        finally {
                            if ($commit688) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e690) {
                                    $commit688 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e690) {
                                    $commit688 = false;
                                    fabric.common.TransactionID $currentTid691 =
                                      $tm692.getCurrentTid();
                                    if ($currentTid691 != null) {
                                        if ($e690.tid.equals($currentTid691) ||
                                              !$e690.tid.isDescendantOf(
                                                           $currentTid691)) {
                                            throw $e690;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit688 && $retry689) {
                                {  }
                                continue $label687;
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
                        boolean modified = unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              modified) {
                            fabric.worker.metrics.ImmutableObserverSet parents =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers();
                            for (java.util.Iterator iter = parents.iterator();
                                 iter.hasNext(); ) {
                                queue.add(iter.next());
                            }
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
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 28, -106, -25, -49,
    -89, -26, -18, 79, 27, 43, -64, 52, 120, -12, -108, -65, -86, 10, 85, -48,
    110, 59, 42, 94, -16, 73, 63, -19, 57, -64, 110, -70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
