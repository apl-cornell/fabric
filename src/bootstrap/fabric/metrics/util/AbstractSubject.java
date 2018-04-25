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
                    fabric.worker.transaction.TransactionManager $tm164 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled167 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff165 = 1;
                    boolean $doBackoff166 = true;
                    boolean $retry161 = true;
                    $label159: for (boolean $commit160 = false; !$commit160; ) {
                        if ($backoffEnabled167) {
                            if ($doBackoff166) {
                                if ($backoff165 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff165);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e162) {
                                            
                                        }
                                    }
                                }
                                if ($backoff165 < 5000) $backoff165 *= 2;
                            }
                            $doBackoff166 = $backoff165 <= 32 || !$doBackoff166;
                        }
                        $commit160 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.set$observers(tmp.get$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e162) {
                            $commit160 = false;
                            continue $label159;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e162) {
                            $commit160 = false;
                            fabric.common.TransactionID $currentTid163 =
                              $tm164.getCurrentTid();
                            if ($e162.tid.isDescendantOf($currentTid163))
                                continue $label159;
                            if ($currentTid163.parent != null) {
                                $retry161 = false;
                                throw $e162;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e162) {
                            $commit160 = false;
                            if ($tm164.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid163 =
                              $tm164.getCurrentTid();
                            if ($e162.tid.isDescendantOf($currentTid163)) {
                                $retry161 = true;
                            }
                            else if ($currentTid163.parent != null) {
                                $retry161 = false;
                                throw $e162;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e162) {
                            $commit160 = false;
                            if ($tm164.checkForStaleObjects())
                                continue $label159;
                            $retry161 = false;
                            throw new fabric.worker.AbortException($e162);
                        }
                        finally {
                            if ($commit160) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e162) {
                                    $commit160 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e162) {
                                    $commit160 = false;
                                    fabric.common.TransactionID $currentTid163 =
                                      $tm164.getCurrentTid();
                                    if ($currentTid163 != null) {
                                        if ($e162.tid.equals($currentTid163) ||
                                              !$e162.tid.isDescendantOf(
                                                           $currentTid163)) {
                                            throw $e162;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit160 && $retry161) {
                                {  }
                                continue $label159;
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
                    fabric.worker.transaction.TransactionManager $tm173 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled176 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff174 = 1;
                    boolean $doBackoff175 = true;
                    boolean $retry170 = true;
                    $label168: for (boolean $commit169 = false; !$commit169; ) {
                        if ($backoffEnabled176) {
                            if ($doBackoff175) {
                                if ($backoff174 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff174);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e171) {
                                            
                                        }
                                    }
                                }
                                if ($backoff174 < 5000) $backoff174 *= 2;
                            }
                            $doBackoff175 = $backoff174 <= 32 || !$doBackoff175;
                        }
                        $commit169 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$observers().contains(o))
                                tmp.set$observers(
                                      tmp.get$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e171) {
                            $commit169 = false;
                            continue $label168;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e171) {
                            $commit169 = false;
                            fabric.common.TransactionID $currentTid172 =
                              $tm173.getCurrentTid();
                            if ($e171.tid.isDescendantOf($currentTid172))
                                continue $label168;
                            if ($currentTid172.parent != null) {
                                $retry170 = false;
                                throw $e171;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e171) {
                            $commit169 = false;
                            if ($tm173.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid172 =
                              $tm173.getCurrentTid();
                            if ($e171.tid.isDescendantOf($currentTid172)) {
                                $retry170 = true;
                            }
                            else if ($currentTid172.parent != null) {
                                $retry170 = false;
                                throw $e171;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e171) {
                            $commit169 = false;
                            if ($tm173.checkForStaleObjects())
                                continue $label168;
                            $retry170 = false;
                            throw new fabric.worker.AbortException($e171);
                        }
                        finally {
                            if ($commit169) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e171) {
                                    $commit169 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e171) {
                                    $commit169 = false;
                                    fabric.common.TransactionID $currentTid172 =
                                      $tm173.getCurrentTid();
                                    if ($currentTid172 != null) {
                                        if ($e171.tid.equals($currentTid172) ||
                                              !$e171.tid.isDescendantOf(
                                                           $currentTid172)) {
                                            throw $e171;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit169 && $retry170) {
                                {  }
                                continue $label168;
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
    
    public static final byte[] $classHash = new byte[] { -84, 28, -80, -101, 14,
    -109, 11, 79, -17, -73, 127, 115, 38, 45, 37, -53, 96, -84, 115, -124, 55,
    -122, 69, 112, 55, -3, -33, -115, -58, 58, 11, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613375000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
