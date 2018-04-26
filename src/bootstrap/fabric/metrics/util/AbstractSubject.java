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
                    fabric.worker.transaction.TransactionManager $tm705 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled708 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff706 = 1;
                    boolean $doBackoff707 = true;
                    boolean $retry702 = true;
                    $label700: for (boolean $commit701 = false; !$commit701; ) {
                        if ($backoffEnabled708) {
                            if ($doBackoff707) {
                                if ($backoff706 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff706);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e703) {
                                            
                                        }
                                    }
                                }
                                if ($backoff706 < 5000) $backoff706 *= 2;
                            }
                            $doBackoff707 = $backoff706 <= 32 || !$doBackoff707;
                        }
                        $commit701 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e703) {
                            $commit701 = false;
                            continue $label700;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e703) {
                            $commit701 = false;
                            fabric.common.TransactionID $currentTid704 =
                              $tm705.getCurrentTid();
                            if ($e703.tid.isDescendantOf($currentTid704))
                                continue $label700;
                            if ($currentTid704.parent != null) {
                                $retry702 = false;
                                throw $e703;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e703) {
                            $commit701 = false;
                            if ($tm705.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid704 =
                              $tm705.getCurrentTid();
                            if ($e703.tid.isDescendantOf($currentTid704)) {
                                $retry702 = true;
                            }
                            else if ($currentTid704.parent != null) {
                                $retry702 = false;
                                throw $e703;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e703) {
                            $commit701 = false;
                            if ($tm705.checkForStaleObjects())
                                continue $label700;
                            $retry702 = false;
                            throw new fabric.worker.AbortException($e703);
                        }
                        finally {
                            if ($commit701) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e703) {
                                    $commit701 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e703) {
                                    $commit701 = false;
                                    fabric.common.TransactionID $currentTid704 =
                                      $tm705.getCurrentTid();
                                    if ($currentTid704 != null) {
                                        if ($e703.tid.equals($currentTid704) ||
                                              !$e703.tid.isDescendantOf(
                                                           $currentTid704)) {
                                            throw $e703;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit701 && $retry702) {
                                {  }
                                continue $label700;
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
                    fabric.worker.transaction.TransactionManager $tm714 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled717 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff715 = 1;
                    boolean $doBackoff716 = true;
                    boolean $retry711 = true;
                    $label709: for (boolean $commit710 = false; !$commit710; ) {
                        if ($backoffEnabled717) {
                            if ($doBackoff716) {
                                if ($backoff715 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff715);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e712) {
                                            
                                        }
                                    }
                                }
                                if ($backoff715 < 5000) $backoff715 *= 2;
                            }
                            $doBackoff716 = $backoff715 <= 32 || !$doBackoff716;
                        }
                        $commit710 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e712) {
                            $commit710 = false;
                            continue $label709;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e712) {
                            $commit710 = false;
                            fabric.common.TransactionID $currentTid713 =
                              $tm714.getCurrentTid();
                            if ($e712.tid.isDescendantOf($currentTid713))
                                continue $label709;
                            if ($currentTid713.parent != null) {
                                $retry711 = false;
                                throw $e712;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e712) {
                            $commit710 = false;
                            if ($tm714.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid713 =
                              $tm714.getCurrentTid();
                            if ($e712.tid.isDescendantOf($currentTid713)) {
                                $retry711 = true;
                            }
                            else if ($currentTid713.parent != null) {
                                $retry711 = false;
                                throw $e712;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e712) {
                            $commit710 = false;
                            if ($tm714.checkForStaleObjects())
                                continue $label709;
                            $retry711 = false;
                            throw new fabric.worker.AbortException($e712);
                        }
                        finally {
                            if ($commit710) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e712) {
                                    $commit710 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e712) {
                                    $commit710 = false;
                                    fabric.common.TransactionID $currentTid713 =
                                      $tm714.getCurrentTid();
                                    if ($currentTid713 != null) {
                                        if ($e712.tid.equals($currentTid713) ||
                                              !$e712.tid.isDescendantOf(
                                                           $currentTid713)) {
                                            throw $e712;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit710 && $retry711) {
                                {  }
                                continue $label709;
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
    public static final long jlc$SourceLastModified$fabil = 1524692850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
