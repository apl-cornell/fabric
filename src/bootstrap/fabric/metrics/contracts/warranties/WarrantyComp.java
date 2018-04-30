package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A computation that uses {@link Contract}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Contract}.
 * This helps to ensure that the {@link Contract} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
      fabric.metrics.contracts.warranties.WarrantyValue val);
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(fabric.worker.metrics.ImmutableSet val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.metrics.contracts.warranties.WarrantyValue
      updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.metrics.contracts.warranties.WarrantyValue apply(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.metrics.contracts.warranties.WarrantyValue apply(
      long time, boolean autoRetry);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public boolean handleUpdates();
    
    /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
    public fabric.lang.Object makeProxyResult(
      fabric.metrics.contracts.warranties.WarrantyValue val, final fabric.worker.Store proxyStore);
    
    /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
    public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
      final fabric.worker.Store proxyStore);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$();
    
    /**
   * A "Proxy" computation to allow for avoiding contacting a remote store for
   * memoized results.
   */
    public static interface ProxyComp
      extends fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyComp
          get$baseComputation();
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          set$baseComputation(
          fabric.metrics.contracts.warranties.WarrantyComp val);
        
        public ProxyComp
          fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
          fabric.metrics.contracts.warranties.WarrantyComp baseComputation);
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long time);
        
        public static class _Proxy
        extends fabric.metrics.contracts.warranties.WarrantyComp._Proxy
          implements ProxyComp {
            public fabric.metrics.contracts.warranties.WarrantyComp
              get$baseComputation() {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).get$baseComputation();
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp
              set$baseComputation(
              fabric.metrics.contracts.warranties.WarrantyComp val) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp._Impl) fetch()).set$baseComputation(val);
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
              fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
              fabric.metrics.contracts.warranties.WarrantyComp arg1) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          ProxyComp) fetch()).
                  fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                    arg1);
            }
            
            public _Proxy(ProxyComp._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.metrics.contracts.warranties.WarrantyComp._Impl
          implements ProxyComp {
            public fabric.metrics.contracts.warranties.WarrantyComp
              get$baseComputation() {
                return this.baseComputation;
            }
            
            public fabric.metrics.contracts.warranties.WarrantyComp
              set$baseComputation(
              fabric.metrics.contracts.warranties.WarrantyComp val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.baseComputation = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.warranties.WarrantyComp
              baseComputation;
            
            public ProxyComp
              fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
              fabric.metrics.contracts.warranties.WarrantyComp baseComputation) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(baseComputation)) instanceof ProxyComp) {
                    this.
                      set$baseComputation(
                        ((ProxyComp)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       baseComputation)).
                            get$baseComputation());
                }
                else {
                    this.set$baseComputation(baseComputation);
                }
                fabric$metrics$contracts$warranties$WarrantyComp$();
                return (ProxyComp) this.$getProxy();
            }
            
            public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
              long time) {
                return this.get$baseComputation().updatedVal(time);
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         ProxyComp._Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.baseComputation, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.baseComputation =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp)
                    $readRef(
                      fabric.metrics.contracts.warranties.WarrantyComp.
                        _Proxy.class,
                      (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                      intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  ProxyComp.
                  _Impl
                  src =
                  (fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp.
                    _Impl) other;
                this.baseComputation = src.baseComputation;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.metrics.contracts.warranties.WarrantyComp.
                           ProxyComp._Static
            {
                public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                                ProxyComp._Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.warranties.
                  WarrantyComp.ProxyComp._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      warranties.
                      WarrantyComp.
                      ProxyComp.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        warranties.
                        WarrantyComp.
                        ProxyComp.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.warranties.WarrantyComp.
                            ProxyComp._Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.warranties.WarrantyComp.
                        ProxyComp._Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.metrics.contracts.warranties.WarrantyComp.
                           ProxyComp._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.warranties.WarrantyComp.
                             ProxyComp._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -6, -110, 2, 20,
        93, -114, -79, 1, 35, -106, 127, 64, 102, -51, -101, -119, -17, 31, -14,
        43, 17, -81, 118, -98, -41, 0, -23, 8, 18, 59, -51, -71 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525096751000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.worker.metrics.ImmutableSet get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.worker.metrics.ImmutableSet set$proxies(
          fabric.worker.metrics.ImmutableSet val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$proxies(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1, boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1, arg2);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
        }
        
        public fabric.lang.Object makeProxyResult(
          fabric.metrics.contracts.warranties.WarrantyValue arg1,
          fabric.worker.Store arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxyResult(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxy(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$();
        }
        
        public _Proxy(WarrantyComp._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return this.curVal;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
        public fabric.worker.metrics.ImmutableSet get$proxies() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.proxies;
        }
        
        public fabric.worker.metrics.ImmutableSet set$proxies(
          fabric.worker.metrics.ImmutableSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The set of proxy computations for this computation. */
        protected fabric.worker.metrics.ImmutableSet proxies;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.metrics.contracts.warranties.WarrantyValue
          updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time) {
            return apply(time, true);
        }
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time, boolean autoRetry) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time, autoRetry);
        }
        
        private static fabric.metrics.contracts.warranties.WarrantyValue
          static_apply(fabric.metrics.contracts.warranties.WarrantyComp tmp,
                       long time, boolean autoRetry) {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            int i = 0;
            boolean loop =
              ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                 tmp.fetch()).dropOldValue();
            while (loop) {
                i++;
                fabric.metrics.contracts.warranties.WarrantyValue newVal = null;
                if (!fabric.metrics.contracts.warranties.WarrantyComp._Static._Proxy.$instance.
                      get$PROACTIVE()) {
                    for (java.util.Iterator iter = tmp.get$proxies().iterator();
                         iter.hasNext(); ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                        if (!((fabric.metrics.contracts.warranties.WarrantyComp.
                                _Impl) proxy.fetch()).dropOldValue()) {
                            newVal = proxy.get$curVal();
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal);
                            if (!((fabric.metrics.contracts.warranties.WarrantyComp.
                                    _Impl) tmp.fetch()).dropOldValue()) {
                                break;
                            } else {
                                newVal = null;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(newVal, null)) {
                    newVal =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(
                                                    newVal.get$contract(),
                                                    null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return newVal;
                    }
                    java.lang.String contractStoreName =
                      newVal.get$contract().$getStore().name();
                    if (!fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn() &&
                          !contractStoreName.
                          equals(fabric.worker.Worker.getWorkerName())) {
                        fabric.worker.remote.RemoteWorker w =
                          fabric.worker.Worker.getWorker().getWorker(
                                                             contractStoreName);
                        ((fabric.metrics.contracts.Contract._Proxy)
                           newVal.get$contract()).activate$remote(w, null);
                    }
                    else {
                        newVal.get$contract().activate();
                    }
                    ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       tmp.fetch()).setNewValue(newVal);
                    if (fabric.metrics.contracts.warranties.WarrantyComp._Static._Proxy.$instance.
                          get$PROACTIVE()) {
                        for (java.util.Iterator iter =
                               tmp.get$proxies().iterator();
                             iter.hasNext();
                             ) {
                            fabric.metrics.contracts.warranties.WarrantyComp
                              proxy =
                              (fabric.metrics.contracts.warranties.WarrantyComp)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(iter.next()));
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) proxy.fetch()).setNewValue(newVal);
                        }
                    }
                }
                loop =
                  autoRetry &&
                    ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       tmp.fetch()).dropOldValue();
                if (!loop)
                    fabric.worker.transaction.TransactionManager.getInstance().
                      stats.
                      addMsg("Memoized: true");
            }
            return tmp.get$curVal();
        }
        
        private boolean dropOldValue() {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_dropOldValue(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy());
        }
        
        private static boolean static_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            boolean rtn = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                rtn =
                  fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                    inner_dropOldValue(tmp);
            }
            else {
                {
                    boolean rtn$var612 = rtn;
                    fabric.worker.transaction.TransactionManager $tm618 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled621 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff619 = 1;
                    boolean $doBackoff620 = true;
                    boolean $retry615 = true;
                    $label613: for (boolean $commit614 = false; !$commit614; ) {
                        if ($backoffEnabled621) {
                            if ($doBackoff620) {
                                if ($backoff619 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff619);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e616) {
                                            
                                        }
                                    }
                                }
                                if ($backoff619 < 5000) $backoff619 *= 2;
                            }
                            $doBackoff620 = $backoff619 <= 32 || !$doBackoff620;
                        }
                        $commit614 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                inner_dropOldValue(tmp);
                        }
                        catch (final fabric.worker.RetryException $e616) {
                            $commit614 = false;
                            continue $label613;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e616) {
                            $commit614 = false;
                            fabric.common.TransactionID $currentTid617 =
                              $tm618.getCurrentTid();
                            if ($e616.tid.isDescendantOf($currentTid617))
                                continue $label613;
                            if ($currentTid617.parent != null) {
                                $retry615 = false;
                                throw $e616;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e616) {
                            $commit614 = false;
                            if ($tm618.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid617 =
                              $tm618.getCurrentTid();
                            if ($e616.tid.isDescendantOf($currentTid617)) {
                                $retry615 = true;
                            }
                            else if ($currentTid617.parent != null) {
                                $retry615 = false;
                                throw $e616;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e616) {
                            $commit614 = false;
                            if ($tm618.checkForStaleObjects())
                                continue $label613;
                            $retry615 = false;
                            throw new fabric.worker.AbortException($e616);
                        }
                        finally {
                            if ($commit614) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e616) {
                                    $commit614 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e616) {
                                    $commit614 = false;
                                    fabric.common.TransactionID $currentTid617 =
                                      $tm618.getCurrentTid();
                                    if ($currentTid617 != null) {
                                        if ($e616.tid.equals($currentTid617) ||
                                              !$e616.tid.isDescendantOf(
                                                           $currentTid617)) {
                                            throw $e616;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit614 && $retry615) {
                                { rtn = rtn$var612; }
                                continue $label613;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private static boolean inner_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            fabric.metrics.contracts.Contract curContract =
              tmp.get$curVal().get$contract();
            if (!fabric.lang.Object._Proxy.idEquals(curContract, null) &&
                  !curContract.valid()) {
                curContract.removeObserver(tmp);
                tmp.get$curVal().set$contract(null);
            }
            return fabric.lang.Object._Proxy.idEquals(
                                               tmp.get$curVal().get$contract(),
                                               null);
        }
        
        private void setNewValue(
          fabric.metrics.contracts.warranties.WarrantyValue newVal) {
            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_setNewValue(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), newVal);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.metrics.contracts.warranties.WarrantyValue newVal) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_setNewValue(tmp, newVal);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm627 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled630 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff628 = 1;
                    boolean $doBackoff629 = true;
                    boolean $retry624 = true;
                    $label622: for (boolean $commit623 = false; !$commit623; ) {
                        if ($backoffEnabled630) {
                            if ($doBackoff629) {
                                if ($backoff628 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff628);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e625) {
                                            
                                        }
                                    }
                                }
                                if ($backoff628 < 5000) $backoff628 *= 2;
                            }
                            $doBackoff629 = $backoff628 <= 32 || !$doBackoff629;
                        }
                        $commit623 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              inner_setNewValue(tmp, newVal);
                        }
                        catch (final fabric.worker.RetryException $e625) {
                            $commit623 = false;
                            continue $label622;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e625) {
                            $commit623 = false;
                            fabric.common.TransactionID $currentTid626 =
                              $tm627.getCurrentTid();
                            if ($e625.tid.isDescendantOf($currentTid626))
                                continue $label622;
                            if ($currentTid626.parent != null) {
                                $retry624 = false;
                                throw $e625;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e625) {
                            $commit623 = false;
                            if ($tm627.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid626 =
                              $tm627.getCurrentTid();
                            if ($e625.tid.isDescendantOf($currentTid626)) {
                                $retry624 = true;
                            }
                            else if ($currentTid626.parent != null) {
                                $retry624 = false;
                                throw $e625;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e625) {
                            $commit623 = false;
                            if ($tm627.checkForStaleObjects())
                                continue $label622;
                            $retry624 = false;
                            throw new fabric.worker.AbortException($e625);
                        }
                        finally {
                            if ($commit623) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e625) {
                                    $commit623 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e625) {
                                    $commit623 = false;
                                    fabric.common.TransactionID $currentTid626 =
                                      $tm627.getCurrentTid();
                                    if ($currentTid626 != null) {
                                        if ($e625.tid.equals($currentTid626) ||
                                              !$e625.tid.isDescendantOf(
                                                           $currentTid626)) {
                                            throw $e625;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit623 && $retry624) {
                                {  }
                                continue $label622;
                            }
                        }
                    }
                }
            }
        }
        
        private static void inner_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.metrics.contracts.warranties.WarrantyValue newVal) {
            if (fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_dropOldValue(tmp) &&
                  !fabric.lang.Object._Proxy.idEquals(newVal.get$contract(),
                                                      null) &&
                  newVal.get$contract().valid()) {
                tmp.get$curVal().set$value(newVal.get$value());
                tmp.get$curVal().set$contract(
                                   newVal.get$contract().getProxyContract(
                                                           tmp.$getStore()));
                tmp.get$curVal().get$contract().addObserver(tmp);
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$curVal().get$contract(),
                                             null))
                return this.get$curVal().get$contract().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
        }
        
        public boolean handleUpdates() {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue()) {
                apply(java.lang.System.currentTimeMillis(), false);
                return true;
            }
            return false;
        }
        
        /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
        public fabric.lang.Object makeProxyResult(
          fabric.metrics.contracts.warranties.WarrantyValue val,
          final fabric.worker.Store proxyStore) {
            return val.get$value();
        }
        
        /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
        public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_makeProxy(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), proxyStore);
        }
        
        private static fabric.metrics.contracts.warranties.WarrantyComp
          static_makeProxy(fabric.metrics.contracts.warranties.WarrantyComp tmp,
                           final fabric.worker.Store proxyStore) {
            fabric.metrics.contracts.warranties.WarrantyComp rtn = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.$getStore().equals(proxyStore)) {
                    rtn = tmp;
                }
                else {
                    for (java.util.Iterator iter =
                           tmp.get$proxies().iterator();
                         iter.hasNext();
                         ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(
                                                                  iter.next()));
                        if (proxy.$getStore().equals(proxyStore)) {
                            rtn = proxy;
                            break;
                        }
                    }
                    if (fabric.lang.Object._Proxy.idEquals(rtn, null)) {
                        rtn =
                          ((ProxyComp)
                             new fabric.metrics.contracts.warranties.
                               WarrantyComp.ProxyComp._Impl(proxyStore).
                             $getProxy()).
                            fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                              tmp);
                        for (java.util.Iterator iter =
                               tmp.get$proxies().iterator();
                             iter.hasNext();
                             ) {
                            fabric.metrics.contracts.warranties.WarrantyComp
                              proxy =
                              (fabric.metrics.contracts.warranties.WarrantyComp)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(iter.next()));
                            proxy.set$proxies(proxy.get$proxies().add(rtn));
                        }
                        rtn.set$proxies(tmp.get$proxies().add(tmp));
                        tmp.set$proxies(tmp.get$proxies().add(rtn));
                    }
                }
            }
            else {
                {
                    fabric.metrics.contracts.warranties.WarrantyComp
                      rtn$var631 = rtn;
                    fabric.worker.transaction.TransactionManager $tm637 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled640 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff638 = 1;
                    boolean $doBackoff639 = true;
                    boolean $retry634 = true;
                    $label632: for (boolean $commit633 = false; !$commit633; ) {
                        if ($backoffEnabled640) {
                            if ($doBackoff639) {
                                if ($backoff638 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff638);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e635) {
                                            
                                        }
                                    }
                                }
                                if ($backoff638 < 5000) $backoff638 *= 2;
                            }
                            $doBackoff639 = $backoff638 <= 32 || !$doBackoff639;
                        }
                        $commit633 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.$getStore().equals(proxyStore)) {
                                rtn = tmp;
                            }
                            else {
                                for (java.util.Iterator iter =
                                       tmp.get$proxies().iterator();
                                     iter.hasNext();
                                     ) {
                                    fabric.metrics.contracts.warranties.WarrantyComp
                                      proxy =
                                      (fabric.metrics.contracts.warranties.WarrantyComp)
                                        fabric.lang.Object._Proxy.
                                        $getProxy(
                                          fabric.lang.WrappedJavaInlineable.
                                              $wrap(iter.next()));
                                    if (proxy.$getStore().equals(proxyStore)) {
                                        rtn = proxy;
                                        break;
                                    }
                                }
                                if (fabric.lang.Object._Proxy.idEquals(rtn,
                                                                       null)) {
                                    rtn =
                                      ((ProxyComp)
                                         new fabric.metrics.contracts.
                                           warranties.WarrantyComp.ProxyComp.
                                           _Impl(proxyStore).
                                         $getProxy()).
                                        fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                          tmp);
                                    for (java.util.Iterator iter =
                                           tmp.get$proxies().iterator();
                                         iter.hasNext();
                                         ) {
                                        fabric.metrics.contracts.warranties.WarrantyComp
                                          proxy =
                                          (fabric.metrics.contracts.warranties.WarrantyComp)
                                            fabric.lang.Object._Proxy.
                                            $getProxy(
                                              fabric.lang.WrappedJavaInlineable.
                                                  $wrap(iter.next()));
                                        proxy.set$proxies(
                                                proxy.get$proxies().add(rtn));
                                    }
                                    rtn.set$proxies(tmp.get$proxies().add(tmp));
                                    tmp.set$proxies(tmp.get$proxies().add(rtn));
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e635) {
                            $commit633 = false;
                            continue $label632;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e635) {
                            $commit633 = false;
                            fabric.common.TransactionID $currentTid636 =
                              $tm637.getCurrentTid();
                            if ($e635.tid.isDescendantOf($currentTid636))
                                continue $label632;
                            if ($currentTid636.parent != null) {
                                $retry634 = false;
                                throw $e635;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e635) {
                            $commit633 = false;
                            if ($tm637.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid636 =
                              $tm637.getCurrentTid();
                            if ($e635.tid.isDescendantOf($currentTid636)) {
                                $retry634 = true;
                            }
                            else if ($currentTid636.parent != null) {
                                $retry634 = false;
                                throw $e635;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e635) {
                            $commit633 = false;
                            if ($tm637.checkForStaleObjects())
                                continue $label632;
                            $retry634 = false;
                            throw new fabric.worker.AbortException($e635);
                        }
                        finally {
                            if ($commit633) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e635) {
                                    $commit633 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e635) {
                                    $commit633 = false;
                                    fabric.common.TransactionID $currentTid636 =
                                      $tm637.getCurrentTid();
                                    if ($currentTid636 != null) {
                                        if ($e635.tid.equals($currentTid636) ||
                                              !$e635.tid.isDescendantOf(
                                                           $currentTid636)) {
                                            throw $e635;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit633 && $retry634) {
                                { rtn = rtn$var631; }
                                continue $label632;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            this.
              set$curVal(
                ((fabric.metrics.contracts.warranties.WarrantyValue)
                   new fabric.metrics.contracts.warranties.WarrantyValue._Impl(
                     this.$getStore()).$getProxy()).
                    fabric$metrics$contracts$warranties$WarrantyValue$(null,
                                                                       null));
            this.set$proxies(fabric.worker.metrics.ImmutableSet.emptySet());
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.WarrantyComp._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.curVal, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.proxies);
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
            this.curVal =
              (fabric.
                metrics.
                contracts.
                warranties.
                WarrantyValue)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyValue.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.proxies = src.proxies;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public boolean get$PROACTIVE();
        
        public boolean set$PROACTIVE(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public boolean get$PROACTIVE() {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          _Static._Impl) fetch()).get$PROACTIVE();
            }
            
            public boolean set$PROACTIVE(boolean val) {
                return ((fabric.metrics.contracts.warranties.WarrantyComp.
                          _Static._Impl) fetch()).set$PROACTIVE(val);
            }
            
            public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.
              WarrantyComp._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.WarrantyComp._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.WarrantyComp._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public boolean get$PROACTIVE() { return this.PROACTIVE; }
            
            public boolean set$PROACTIVE(boolean val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.PROACTIVE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private boolean PROACTIVE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeBoolean(this.PROACTIVE);
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
                this.PROACTIVE = in.readBoolean();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm646 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled649 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff647 = 1;
                        boolean $doBackoff648 = true;
                        boolean $retry643 = true;
                        $label641: for (boolean $commit642 = false; !$commit642;
                                        ) {
                            if ($backoffEnabled649) {
                                if ($doBackoff648) {
                                    if ($backoff647 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff647);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e644) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff647 < 5000) $backoff647 *= 2;
                                }
                                $doBackoff648 = $backoff647 <= 32 ||
                                                  !$doBackoff648;
                            }
                            $commit642 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.warranties.WarrantyComp.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$PROACTIVE(true);
                            }
                            catch (final fabric.worker.RetryException $e644) {
                                $commit642 = false;
                                continue $label641;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e644) {
                                $commit642 = false;
                                fabric.common.TransactionID $currentTid645 =
                                  $tm646.getCurrentTid();
                                if ($e644.tid.isDescendantOf($currentTid645))
                                    continue $label641;
                                if ($currentTid645.parent != null) {
                                    $retry643 = false;
                                    throw $e644;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e644) {
                                $commit642 = false;
                                if ($tm646.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid645 =
                                  $tm646.getCurrentTid();
                                if ($e644.tid.isDescendantOf($currentTid645)) {
                                    $retry643 = true;
                                }
                                else if ($currentTid645.parent != null) {
                                    $retry643 = false;
                                    throw $e644;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e644) {
                                $commit642 = false;
                                if ($tm646.checkForStaleObjects())
                                    continue $label641;
                                $retry643 = false;
                                throw new fabric.worker.AbortException($e644);
                            }
                            finally {
                                if ($commit642) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e644) {
                                        $commit642 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e644) {
                                        $commit642 = false;
                                        fabric.common.TransactionID
                                          $currentTid645 =
                                          $tm646.getCurrentTid();
                                        if ($currentTid645 != null) {
                                            if ($e644.tid.equals(
                                                            $currentTid645) ||
                                                  !$e644.tid.
                                                  isDescendantOf(
                                                    $currentTid645)) {
                                                throw $e644;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit642 && $retry643) {
                                    {  }
                                    continue $label641;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -124, 70, 108, 45, -71,
    -123, -92, 22, -80, 58, 110, -41, -31, 30, -47, -118, -22, -88, -41, -30,
    -116, 87, -113, -70, 37, -85, 90, 101, -19, -77, -49, 123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525096751000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
