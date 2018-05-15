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
                    fabric.worker.transaction.TransactionManager $tm664 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled667 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff665 = 1;
                    boolean $doBackoff666 = true;
                    boolean $retry661 = true;
                    $label659: for (boolean $commit660 = false; !$commit660; ) {
                        if ($backoffEnabled667) {
                            if ($doBackoff666) {
                                if ($backoff665 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff665);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e662) {
                                            
                                        }
                                    }
                                }
                                if ($backoff665 < 5000) $backoff665 *= 2;
                            }
                            $doBackoff666 = $backoff665 <= 32 || !$doBackoff666;
                        }
                        $commit660 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e662) {
                            $commit660 = false;
                            continue $label659;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e662) {
                            $commit660 = false;
                            fabric.common.TransactionID $currentTid663 =
                              $tm664.getCurrentTid();
                            if ($e662.tid.isDescendantOf($currentTid663))
                                continue $label659;
                            if ($currentTid663.parent != null) {
                                $retry661 = false;
                                throw $e662;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e662) {
                            $commit660 = false;
                            if ($tm664.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid663 =
                              $tm664.getCurrentTid();
                            if ($e662.tid.isDescendantOf($currentTid663)) {
                                $retry661 = true;
                            }
                            else if ($currentTid663.parent != null) {
                                $retry661 = false;
                                throw $e662;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e662) {
                            $commit660 = false;
                            if ($tm664.checkForStaleObjects())
                                continue $label659;
                            $retry661 = false;
                            throw new fabric.worker.AbortException($e662);
                        }
                        finally {
                            if ($commit660) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e662) {
                                    $commit660 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e662) {
                                    $commit660 = false;
                                    fabric.common.TransactionID $currentTid663 =
                                      $tm664.getCurrentTid();
                                    if ($currentTid663 != null) {
                                        if ($e662.tid.equals($currentTid663) ||
                                              !$e662.tid.isDescendantOf(
                                                           $currentTid663)) {
                                            throw $e662;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit660 && $retry661) {
                                {  }
                                continue $label659;
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
                    fabric.worker.transaction.TransactionManager $tm673 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled676 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff674 = 1;
                    boolean $doBackoff675 = true;
                    boolean $retry670 = true;
                    $label668: for (boolean $commit669 = false; !$commit669; ) {
                        if ($backoffEnabled676) {
                            if ($doBackoff675) {
                                if ($backoff674 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff674);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e671) {
                                            
                                        }
                                    }
                                }
                                if ($backoff674 < 5000) $backoff674 *= 2;
                            }
                            $doBackoff675 = $backoff674 <= 32 || !$doBackoff675;
                        }
                        $commit669 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e671) {
                            $commit669 = false;
                            continue $label668;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e671) {
                            $commit669 = false;
                            fabric.common.TransactionID $currentTid672 =
                              $tm673.getCurrentTid();
                            if ($e671.tid.isDescendantOf($currentTid672))
                                continue $label668;
                            if ($currentTid672.parent != null) {
                                $retry670 = false;
                                throw $e671;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e671) {
                            $commit669 = false;
                            if ($tm673.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid672 =
                              $tm673.getCurrentTid();
                            if ($e671.tid.isDescendantOf($currentTid672)) {
                                $retry670 = true;
                            }
                            else if ($currentTid672.parent != null) {
                                $retry670 = false;
                                throw $e671;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e671) {
                            $commit669 = false;
                            if ($tm673.checkForStaleObjects())
                                continue $label668;
                            $retry670 = false;
                            throw new fabric.worker.AbortException($e671);
                        }
                        finally {
                            if ($commit669) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e671) {
                                    $commit669 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e671) {
                                    $commit669 = false;
                                    fabric.common.TransactionID $currentTid672 =
                                      $tm673.getCurrentTid();
                                    if ($currentTid672 != null) {
                                        if ($e671.tid.equals($currentTid672) ||
                                              !$e671.tid.isDescendantOf(
                                                           $currentTid672)) {
                                            throw $e671;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit669 && $retry670) {
                                {  }
                                continue $label668;
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
    
    public static final byte[] $classHash = new byte[] { -43, 11, -48, -59, -67,
    21, 94, -111, -87, -102, -17, -18, 6, 101, 35, -81, 103, -23, 115, -10, 25,
    107, -19, -83, -113, 126, 48, 24, -101, 109, 82, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
