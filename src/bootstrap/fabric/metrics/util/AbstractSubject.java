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
    
    public fabric.worker.metrics.ImmutableObserverSet get$observers();
    
    public fabric.worker.metrics.ImmutableObserverSet set$observers(
      fabric.worker.metrics.ImmutableObserverSet val);
    
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
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            this.set$observers(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
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
                    fabric.worker.transaction.TransactionManager $tm743 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled746 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff744 = 1;
                    boolean $doBackoff745 = true;
                    boolean $retry740 = true;
                    $label738: for (boolean $commit739 = false; !$commit739; ) {
                        if ($backoffEnabled746) {
                            if ($doBackoff745) {
                                if ($backoff744 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff744);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e741) {
                                            
                                        }
                                    }
                                }
                                if ($backoff744 < 5000) $backoff744 *= 2;
                            }
                            $doBackoff745 = $backoff744 <= 32 || !$doBackoff745;
                        }
                        $commit739 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.set$observers(tmp.get$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e741) {
                            $commit739 = false;
                            continue $label738;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e741) {
                            $commit739 = false;
                            fabric.common.TransactionID $currentTid742 =
                              $tm743.getCurrentTid();
                            if ($e741.tid.isDescendantOf($currentTid742))
                                continue $label738;
                            if ($currentTid742.parent != null) {
                                $retry740 = false;
                                throw $e741;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e741) {
                            $commit739 = false;
                            if ($tm743.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid742 =
                              $tm743.getCurrentTid();
                            if ($e741.tid.isDescendantOf($currentTid742)) {
                                $retry740 = true;
                            }
                            else if ($currentTid742.parent != null) {
                                $retry740 = false;
                                throw $e741;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e741) {
                            $commit739 = false;
                            if ($tm743.checkForStaleObjects())
                                continue $label738;
                            $retry740 = false;
                            throw new fabric.worker.AbortException($e741);
                        }
                        finally {
                            if ($commit739) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e741) {
                                    $commit739 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e741) {
                                    $commit739 = false;
                                    fabric.common.TransactionID $currentTid742 =
                                      $tm743.getCurrentTid();
                                    if ($currentTid742 != null) {
                                        if ($e741.tid.equals($currentTid742) ||
                                              !$e741.tid.isDescendantOf(
                                                           $currentTid742)) {
                                            throw $e741;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit739 && $retry740) {
                                {  }
                                continue $label738;
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
                    fabric.worker.transaction.TransactionManager $tm752 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled755 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff753 = 1;
                    boolean $doBackoff754 = true;
                    boolean $retry749 = true;
                    $label747: for (boolean $commit748 = false; !$commit748; ) {
                        if ($backoffEnabled755) {
                            if ($doBackoff754) {
                                if ($backoff753 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff753);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e750) {
                                            
                                        }
                                    }
                                }
                                if ($backoff753 < 5000) $backoff753 *= 2;
                            }
                            $doBackoff754 = $backoff753 <= 32 || !$doBackoff754;
                        }
                        $commit748 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$observers().contains(o))
                                tmp.set$observers(
                                      tmp.get$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e750) {
                            $commit748 = false;
                            continue $label747;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e750) {
                            $commit748 = false;
                            fabric.common.TransactionID $currentTid751 =
                              $tm752.getCurrentTid();
                            if ($e750.tid.isDescendantOf($currentTid751))
                                continue $label747;
                            if ($currentTid751.parent != null) {
                                $retry749 = false;
                                throw $e750;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e750) {
                            $commit748 = false;
                            if ($tm752.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid751 =
                              $tm752.getCurrentTid();
                            if ($e750.tid.isDescendantOf($currentTid751)) {
                                $retry749 = true;
                            }
                            else if ($currentTid751.parent != null) {
                                $retry749 = false;
                                throw $e750;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e750) {
                            $commit748 = false;
                            if ($tm752.checkForStaleObjects())
                                continue $label747;
                            $retry749 = false;
                            throw new fabric.worker.AbortException($e750);
                        }
                        finally {
                            if ($commit748) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e750) {
                                    $commit748 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e750) {
                                    $commit748 = false;
                                    fabric.common.TransactionID $currentTid751 =
                                      $tm752.getCurrentTid();
                                    if ($currentTid751 != null) {
                                        if ($e750.tid.equals($currentTid751) ||
                                              !$e750.tid.isDescendantOf(
                                                           $currentTid751)) {
                                            throw $e750;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit748 && $retry749) {
                                {  }
                                continue $label747;
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
            $writeInline(out, this.observers);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -119, -50, -109, 85,
    64, -76, -42, -52, 127, -13, -72, -36, 95, 76, -2, 0, 106, 32, 110, 74, 97,
    106, 65, -51, 122, 103, 8, -105, 107, -113, -104, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349484000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
