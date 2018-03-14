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
import fabric.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
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
    
    public fabric.util.ArrayList get$observers();
    
    public fabric.util.ArrayList set$observers(fabric.util.ArrayList val);
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.util.List getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
        public fabric.util.ArrayList get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.util.ArrayList set$observers(fabric.util.ArrayList val) {
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
        
        public fabric.util.List getObservers() {
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
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         ));
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public fabric.util.ArrayList get$observers() { return this.observers; }
        
        public fabric.util.ArrayList set$observers(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.ArrayList observers;
        
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
                    fabric.worker.transaction.TransactionManager $tm702 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled705 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff703 = 1;
                    boolean $doBackoff704 = true;
                    boolean $retry699 = true;
                    $label697: for (boolean $commit698 = false; !$commit698; ) {
                        if ($backoffEnabled705) {
                            if ($doBackoff704) {
                                if ($backoff703 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff703);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e700) {
                                            
                                        }
                                    }
                                }
                                if ($backoff703 < 5000) $backoff703 *= 2;
                            }
                            $doBackoff704 = $backoff703 <= 32 || !$doBackoff704;
                        }
                        $commit698 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$observers().contains(o))
                                tmp.get$observers().add(o);
                        }
                        catch (final fabric.worker.RetryException $e700) {
                            $commit698 = false;
                            continue $label697;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e700) {
                            $commit698 = false;
                            fabric.common.TransactionID $currentTid701 =
                              $tm702.getCurrentTid();
                            if ($e700.tid.isDescendantOf($currentTid701))
                                continue $label697;
                            if ($currentTid701.parent != null) {
                                $retry699 = false;
                                throw $e700;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e700) {
                            $commit698 = false;
                            if ($tm702.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid701 =
                              $tm702.getCurrentTid();
                            if ($e700.tid.isDescendantOf($currentTid701)) {
                                $retry699 = true;
                            }
                            else if ($currentTid701.parent != null) {
                                $retry699 = false;
                                throw $e700;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e700) {
                            $commit698 = false;
                            if ($tm702.checkForStaleObjects())
                                continue $label697;
                            $retry699 = false;
                            throw new fabric.worker.AbortException($e700);
                        }
                        finally {
                            if ($commit698) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e700) {
                                    $commit698 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e700) {
                                    $commit698 = false;
                                    fabric.common.TransactionID $currentTid701 =
                                      $tm702.getCurrentTid();
                                    if ($currentTid701 != null) {
                                        if ($e700.tid.equals($currentTid701) ||
                                              !$e700.tid.isDescendantOf(
                                                           $currentTid701)) {
                                            throw $e700;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit698 && $retry699) {
                                {  }
                                continue $label697;
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
                tmp.get$observers().remove(o);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm711 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled714 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff712 = 1;
                    boolean $doBackoff713 = true;
                    boolean $retry708 = true;
                    $label706: for (boolean $commit707 = false; !$commit707; ) {
                        if ($backoffEnabled714) {
                            if ($doBackoff713) {
                                if ($backoff712 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff712);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e709) {
                                            
                                        }
                                    }
                                }
                                if ($backoff712 < 5000) $backoff712 *= 2;
                            }
                            $doBackoff713 = $backoff712 <= 32 || !$doBackoff713;
                        }
                        $commit707 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.get$observers().remove(o); }
                        catch (final fabric.worker.RetryException $e709) {
                            $commit707 = false;
                            continue $label706;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e709) {
                            $commit707 = false;
                            fabric.common.TransactionID $currentTid710 =
                              $tm711.getCurrentTid();
                            if ($e709.tid.isDescendantOf($currentTid710))
                                continue $label706;
                            if ($currentTid710.parent != null) {
                                $retry708 = false;
                                throw $e709;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e709) {
                            $commit707 = false;
                            if ($tm711.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid710 =
                              $tm711.getCurrentTid();
                            if ($e709.tid.isDescendantOf($currentTid710)) {
                                $retry708 = true;
                            }
                            else if ($currentTid710.parent != null) {
                                $retry708 = false;
                                throw $e709;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e709) {
                            $commit707 = false;
                            if ($tm711.checkForStaleObjects())
                                continue $label706;
                            $retry708 = false;
                            throw new fabric.worker.AbortException($e709);
                        }
                        finally {
                            if ($commit707) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e709) {
                                    $commit707 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e709) {
                                    $commit707 = false;
                                    fabric.common.TransactionID $currentTid710 =
                                      $tm711.getCurrentTid();
                                    if ($currentTid710 != null) {
                                        if ($e709.tid.equals($currentTid710) ||
                                              !$e709.tid.isDescendantOf(
                                                           $currentTid710)) {
                                            throw $e709;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit707 && $retry708) {
                                {  }
                                continue $label706;
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
        
        public fabric.util.List getObservers() { return this.get$observers(); }
        
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
                fabric.util.List obs = sm.getObservers();
                int size = obs.size();
                for (int i = 0; i < size; i++) {
                    queue.add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  obs.get(i)));
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
                    fabric.lang.arrays.ObjectArray leaves =
                      unhandled.getLeafSubjects();
                    for (int i = 0; i < leaves.get$length(); i++) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap((fabric.metrics.SampledMetric)
                                            leaves.get(i)))) {
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
                            fabric.util.List parents =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers();
                            int pSize = parents.size();
                            for (int i = 0; i < pSize; i++) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parents.get(i)));
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
                            fabric.lang.arrays.ObjectArray withheldLeaves =
                              withheld.getLeafSubjects();
                            for (int i = 0; i < withheldLeaves.get$length();
                                 i++) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap((fabric.metrics.SampledMetric)
                                                    withheldLeaves.get(i)))) {
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
            this.observers = (fabric.util.ArrayList)
                               $readRef(fabric.util.ArrayList._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
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
    
    public static final byte[] $classHash = new byte[] { -100, 55, 91, 114, -44,
    119, 33, -24, -71, 49, -27, -58, -64, 46, -22, 35, 42, -12, 37, -85, -99,
    -52, 62, 45, -69, -36, 95, -31, 18, 66, 15, -35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
