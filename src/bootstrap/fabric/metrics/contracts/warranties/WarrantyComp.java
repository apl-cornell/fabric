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
import fabric.worker.transaction.TransactionManager;

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
    
    public fabric.util.ArrayList get$proxies();
    
    public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val);
    
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
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
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
    public ProxyComp makeProxy(final fabric.worker.Store proxyStore);
    
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
        
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long time, boolean autoRetry);
        
        public ProxyComp makeProxy(final fabric.worker.Store proxyStore);
        
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
                this.set$baseComputation(baseComputation);
                fabric$metrics$contracts$warranties$WarrantyComp$();
                this.get$baseComputation().addObserver((ProxyComp)
                                                         this.$getProxy());
                return (ProxyComp) this.$getProxy();
            }
            
            public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
              long time) {
                return this.get$baseComputation().updatedVal(time);
            }
            
            public fabric.metrics.contracts.warranties.WarrantyValue apply(
              long time, boolean autoRetry) {
                return fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp._Impl.
                  static_apply((ProxyComp) this.$getProxy(), time, autoRetry);
            }
            
            private static fabric.metrics.contracts.warranties.WarrantyValue
              static_apply(ProxyComp tmp, long time, boolean autoRetry) {
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 tmp.get$curVal().get$contract(
                                                                    ), null) &&
                      tmp.get$curVal().get$contract().valid()) {
                    return tmp.get$curVal();
                }
                return tmp.get$baseComputation().apply(time, autoRetry);
            }
            
            public ProxyComp makeProxy(final fabric.worker.Store proxyStore) {
                return fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp._Impl.
                  static_makeProxy((ProxyComp) this.$getProxy(), proxyStore);
            }
            
            private static ProxyComp static_makeProxy(
              ProxyComp tmp, final fabric.worker.Store proxyStore) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    return tmp.get$baseComputation().makeProxy(proxyStore);
                }
                else {
                    ProxyComp rtn = null;
                    {
                        fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                          rtn$var610 = rtn;
                        fabric.worker.transaction.TransactionManager $tm616 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled619 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff617 = 1;
                        boolean $doBackoff618 = true;
                        boolean $retry613 = true;
                        $label611: for (boolean $commit612 = false; !$commit612;
                                        ) {
                            if ($backoffEnabled619) {
                                if ($doBackoff618) {
                                    if ($backoff617 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff617);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e614) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff617 < 5000) $backoff617 *= 2;
                                }
                                $doBackoff618 = $backoff617 <= 32 ||
                                                  !$doBackoff618;
                            }
                            $commit612 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                rtn =
                                  tmp.get$baseComputation().makeProxy(
                                                              proxyStore);
                            }
                            catch (final fabric.worker.RetryException $e614) {
                                $commit612 = false;
                                continue $label611;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e614) {
                                $commit612 = false;
                                fabric.common.TransactionID $currentTid615 =
                                  $tm616.getCurrentTid();
                                if ($e614.tid.isDescendantOf($currentTid615))
                                    continue $label611;
                                if ($currentTid615.parent != null) {
                                    $retry613 = false;
                                    throw $e614;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e614) {
                                $commit612 = false;
                                if ($tm616.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid615 =
                                  $tm616.getCurrentTid();
                                if ($e614.tid.isDescendantOf($currentTid615)) {
                                    $retry613 = true;
                                }
                                else if ($currentTid615.parent != null) {
                                    $retry613 = false;
                                    throw $e614;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e614) {
                                $commit612 = false;
                                if ($tm616.checkForStaleObjects())
                                    continue $label611;
                                $retry613 = false;
                                throw new fabric.worker.AbortException($e614);
                            }
                            finally {
                                if ($commit612) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e614) {
                                        $commit612 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e614) {
                                        $commit612 = false;
                                        fabric.common.TransactionID
                                          $currentTid615 =
                                          $tm616.getCurrentTid();
                                        if ($currentTid615 != null) {
                                            if ($e614.tid.equals(
                                                            $currentTid615) ||
                                                  !$e614.tid.
                                                  isDescendantOf(
                                                    $currentTid615)) {
                                                throw $e614;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit612 && $retry613) {
                                    { rtn = rtn$var610; }
                                    continue $label611;
                                }
                            }
                        }
                    }
                    return rtn;
                }
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
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
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
                    return new fabric.metrics.contracts.warranties.WarrantyComp.
                             ProxyComp._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 28, -62, 27, -44,
        -8, -16, -113, 44, 42, -26, 89, 74, -47, 7, -94, -11, -11, -76, -20,
        -117, 116, -18, 73, -122, -87, 98, 28, 48, 99, -121, 107, 95 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1521832678000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYbWwcRxmeO9tnn+PYjp1Px3Ec5+o0H71rmgrRGFCTo2muvWLLdlrFUXPM7c3ZG+/tbmbn7HNpaAKCBJAMAjekahNVJdA2Na2EqPiBIqqKllZFSEUV0B9ABCoUpUFUKNAfhfK+M+vbu72PuD846WbmZt935v143mdmb/EaaXI4GcjStG5ExZzNnOgBmk4kRyh3WCZuUMcZh9mUtqIxcfbdpzN9QRJMkjaNmpapa9RImY4g7cljdIbGTCZih0YTQ0dIWEPFg9SZEiR4ZH+Bk37bMuYmDUu4m1Ss/+jO2MJ3j3b+qIF0TJAO3RwTVOha3DIFK4gJ0pZjuTTjzr5MhmUmyCqTscwY4zo19IdA0DInSJejT5pU5DlzRpljGTMo2OXkbcblnkuTaL4FZvO8JiwO5ncq8/NCN2JJ3RFDSRLK6szIOMfJF0ljkjRlDToJgmuTS17E5IqxAzgP4q06mMmzVGNLKo3TupkRZLNfo+hx5F4QANXmHBNTVnGrRpPCBOlSJhnUnIyNCa6bkyDaZOVhF0F6ai4KQi021abpJEsJst4vN6IegVRYhgVVBFnjF5MrQc56fDkryda1z31q/gvmQTNIAmBzhmkG2t8CSn0+pVGWZZyZGlOKbTuSZ+nay2eChIDwGp+wkvnJw+/fuavvpdeUzMYqMsPpY0wTKe1iuv3N3vj2OxrQjBbbcnSEQpnnMqsj7pOhgg1oX1tcER9Glx6+NPrq4ZOX2NUgaU2QkGYZ+RygapVm5WzdYPxuZjJOBcskSJiZmbh8niDNME7qJlOzw9msw0SCNBpyKmTJ3xCiLCyBIWqGsW5mraWxTcWUHBdsQkg3fEkDIYHbCbllD/QBQgbXCZKKTVk5FksbeTYL8I7Bl1GuTcWgbrmuxRyuxXjeFDoIuVOAIuicGEBdcKoJJzZLOacgA/oPqOFcHHyLgmn2/3+LAnrZORsIQAI2a1aGpakD2XSRtX/EgOI5aBkZxlOaMX85QbovPybRFcaKcADVMn4BQESvn0tKdRfy++96//nUGwqZqOuGV5C9yu6oa3e0aHfUsztaandkhFsFOQKj27Aio8BxUeC4xUAhGr+QeE4CL+TICi3u0wb77LUNKrIWzxUgidLp1VJfIg7wMg08BFTTtn3swXs+f2YAcl6wZxsh+yga8ReeR1cJGFGoppTWcfrdf71w9oTllaAgkQpmqNTEyh7wR5BbGssAc3rL7+inL6Yun4gEkZXCGCoKkAb26fPvUVbhQ0tsidFoSpIVGANq4KMlimsVU9ya9WYkMtqx6VIgwWD5DJRE++kx+/zvfvW3PfIIWuLkjhLyHmNiqIQHcLEOWfGrvNiPc8ZA7vfnRr7z6LXTR2TgQWJrtQ0j2GL6KRS+xb/y2vG3//iHi28FvWQJEra5JYCMWKYg3Vn1EXwC8P0vfrGecQJ7YPW4yyX9RTKxcfNBzzygFQNWA+udyCEzZ2X0rE7TBkOwfNhx0+4X35vvVBk3YEbFj5NdN17Am9+wn5x84+i/++QyAQ2PNS+Enpjiym5v5X1QGHNoR+HUrzc99gt6HsAPTOfoDzFJXkSGhMgc3iZjcYtsd/ue3Y7NgIpWbxHz/nPjAB7AHhwnYotP9MQ/c1VRQhGOuMaWKpRwPy2plNsu5a4HB0KvBEnzBOmUZz9U+P0UqA6QMAGntxN3J5NkZdnz8pNYHTtDxXLr9ZdCybb+QvCoCMYojeNWhX0FHAjEagwSxCsAsBx8wu0fwafdNrarCwEiB3ulylbZDmKzXQYyiMMdAEo9l8sLTLvcYCeUCdItwi8v5HVJaq4R5NaPy4mo1yPLtFDfBmBFvMEVis4F0bku91Bb6/ahEudKEEEKAIlNte4f8u508UsLFzLD39+tbgld5Wf6XWY+98Pf/OeX0XNXXq9yEoTc26S3YQj221JxC75P3s08JF25uumO+PQ7k2rPzT77/NLP3rf4+t2D2reDpKEImYoLYbnSUDlQWjmD+6w5XgaX/mJE2zFSxyGSTYRs61D94GIpXBSfVs1TQObJS48M+0p3kefc/il/erySDnir3Cn3OVSn5h/AZliQIQW3iAu3SBFuEQ9ukepHcMTzJVkegU+CGWD4zStVv+3PNSKAzWilv6jyJ7d/+4b+VqGqEa7n4MCZca+47MzC1z+Kzi8o3Kn3gK0VV/FSHfUuIE1dKesV0b+l3i5S48BfXzjx02dOnA66QT4o4FSwzEn542idbGSxOSxIa97O4CEEVLfEB7s/Dh9IipSE4EvLCtxqH0QLCO3mD93+1eUCE+rTzqeNUu6Qtre6C73i9j+rnaygR0VUTqbcuGKXFqQ5bVkGo6a043idUM1gc0yQJmrbxhz+mPL5Ku/sKTBpAyHbL7n90WX6GgRTbK7PQBZw8rM+j7vc5R50++HaHjfI9RrkXtJtbLjc+5E6/p3CpgB3VsXWqZpuypR+AuzYSsjOPW7fUqfScpXJQ5Vmtw/cuNJcSHa7kJy1+DTj0TG4iamb4wb/VVuacKaOt/PYfBnOxxydZpJZqpGKzOg4WLAN7HzZ7U8tN6M4fLhWMnGlk27PlwVfmcyvyR3P1vHsHDbfEmCVymO5g5DgcJFIJWMDwWys8hbm/negxX/OLr5z7641Nd7A1lf8m+PqPX+ho2XdhUO/lW8Nxf8FwnApz+YNo/QKVDIO2ZxldelGWF2IbNmdB95cBh0Bj3k/ZHAeV/pPCrK+lr5Ql0g5LtX5niDt5TpC/kWDo1K5HwBJKTn89bR3LfI16mjsyXP8H2zxn+s+CLWMX5EvEJC5/t6XN771wT++uWvHXw7f82bzU9ev//i9b4i/J776bLr3Vu30dOp/u/K0OZ8TAAA=";
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
        
        public fabric.util.ArrayList get$proxies() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proxies();
        }
        
        public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val) {
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
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
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
        
        public fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
          makeProxy(fabric.worker.Store arg1) {
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
        
        public fabric.util.ArrayList get$proxies() { return this.proxies; }
        
        public fabric.util.ArrayList set$proxies(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxies = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.util.ArrayList proxies;
        
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
            boolean loop = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                loop =
                  fabric.lang.Object._Proxy.idEquals(
                                              tmp.get$curVal().get$contract(),
                                              null) ||
                    !tmp.get$curVal().get$contract().valid();
            }
            else {
                {
                    boolean loop$var620 = loop;
                    fabric.worker.transaction.TransactionManager $tm626 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled629 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff627 = 1;
                    boolean $doBackoff628 = true;
                    boolean $retry623 = true;
                    $label621: for (boolean $commit622 = false; !$commit622; ) {
                        if ($backoffEnabled629) {
                            if ($doBackoff628) {
                                if ($backoff627 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff627);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e624) {
                                            
                                        }
                                    }
                                }
                                if ($backoff627 < 5000) $backoff627 *= 2;
                            }
                            $doBackoff628 = $backoff627 <= 32 || !$doBackoff628;
                        }
                        $commit622 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            loop =
                              fabric.lang.Object._Proxy.idEquals(
                                                          tmp.get$curVal(
                                                                ).get$contract(
                                                                    ), null) ||
                                !tmp.get$curVal().get$contract().valid();
                        }
                        catch (final fabric.worker.RetryException $e624) {
                            $commit622 = false;
                            continue $label621;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e624) {
                            $commit622 = false;
                            fabric.common.TransactionID $currentTid625 =
                              $tm626.getCurrentTid();
                            if ($e624.tid.isDescendantOf($currentTid625))
                                continue $label621;
                            if ($currentTid625.parent != null) {
                                $retry623 = false;
                                throw $e624;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e624) {
                            $commit622 = false;
                            if ($tm626.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid625 =
                              $tm626.getCurrentTid();
                            if ($e624.tid.isDescendantOf($currentTid625)) {
                                $retry623 = true;
                            }
                            else if ($currentTid625.parent != null) {
                                $retry623 = false;
                                throw $e624;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e624) {
                            $commit622 = false;
                            if ($tm626.checkForStaleObjects())
                                continue $label621;
                            $retry623 = false;
                            throw new fabric.worker.AbortException($e624);
                        }
                        finally {
                            if ($commit622) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e624) {
                                    $commit622 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e624) {
                                    $commit622 = false;
                                    fabric.common.TransactionID $currentTid625 =
                                      $tm626.getCurrentTid();
                                    if ($currentTid625 != null) {
                                        if ($e624.tid.equals($currentTid625) ||
                                              !$e624.tid.isDescendantOf(
                                                           $currentTid625)) {
                                            throw $e624;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit622 && $retry623) {
                                { loop = loop$var620; }
                                continue $label621;
                            }
                        }
                    }
                }
            }
            while (loop) {
                fabric.metrics.contracts.warranties.WarrantyValue newVal =
                  tmp.updatedVal(java.lang.System.currentTimeMillis());
                if (fabric.lang.Object._Proxy.idEquals(newVal.get$contract(),
                                                       null)) return newVal;
                if (fabric.lang.Object._Proxy.
                      idEquals(
                        fabric.worker.transaction.TransactionManager.
                            getInstance().getCurrentLog(),
                        null) &&
                      !newVal.get$contract().$getStore().name().
                      equals(fabric.worker.Worker.getWorkerName())) {
                    fabric.worker.remote.RemoteWorker w =
                      fabric.worker.Worker.getWorker().getWorker(
                                                         newVal.get$contract(
                                                                  ).$getStore(
                                                                      ).name());
                    ((fabric.metrics.contracts.Contract._Proxy)
                       newVal.get$contract()).activate$remote(w, null);
                }
                else {
                    newVal.get$contract().activate();
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    if (fabric.lang.Object._Proxy.idEquals(
                                                    tmp.get$curVal(
                                                          ).get$contract(),
                                                    null) ||
                          !tmp.get$curVal().get$contract().valid()) {
                        tmp.get$curVal().set$value(newVal.get$value());
                        if (!fabric.lang.Object._Proxy.idEquals(
                                                         tmp.get$curVal(
                                                               ).get$contract(),
                                                         newVal.get$contract(
                                                                  ))) {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$curVal().get$contract(),
                                           null)) {
                                tmp.get$curVal().get$contract().removeObserver(
                                                                  tmp);
                            }
                            tmp.get$curVal().set$contract(
                                               newVal.get$contract());
                            tmp.get$curVal().get$contract().addObserver(tmp);
                            int size = tmp.get$proxies().size();
                            for (int i = 0; i < size; i++) {
                                ProxyComp p =
                                  (ProxyComp)
                                    fabric.lang.Object._Proxy.$getProxy(
                                                                tmp.get$proxies(
                                                                      ).get(i));
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(p.get$curVal().get$contract(),
                                               null)) {
                                    p.get$curVal().get$contract().
                                      removeObserver(p);
                                }
                                p.get$curVal().set$value(newVal.get$value());
                                p.get$curVal().set$contract(
                                                 newVal.get$contract(
                                                          ).getProxyContract(
                                                              p.$getStore()));
                                p.get$curVal().get$contract().addObserver(p);
                            }
                        }
                    }
                }
                else {
                    {
                        boolean loop$var630 = loop;
                        fabric.worker.transaction.TransactionManager $tm636 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled639 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff637 = 1;
                        boolean $doBackoff638 = true;
                        boolean $retry633 = true;
                        $label631: for (boolean $commit632 = false; !$commit632;
                                        ) {
                            if ($backoffEnabled639) {
                                if ($doBackoff638) {
                                    if ($backoff637 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff637);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e634) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff637 < 5000) $backoff637 *= 2;
                                }
                                $doBackoff638 = $backoff637 <= 32 ||
                                                  !$doBackoff638;
                            }
                            $commit632 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                if (fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$curVal().get$contract(),
                                               null) ||
                                      !tmp.get$curVal().get$contract().valid(
                                                                         )) {
                                    tmp.get$curVal().set$value(
                                                       newVal.get$value());
                                    if (!fabric.lang.Object._Proxy.
                                          idEquals(
                                            tmp.get$curVal().get$contract(),
                                            newVal.get$contract())) {
                                        if (!fabric.lang.Object._Proxy.
                                              idEquals(
                                                tmp.get$curVal().get$contract(),
                                                null)) {
                                            tmp.get$curVal().get$contract().
                                              removeObserver(tmp);
                                        }
                                        tmp.get$curVal().set$contract(
                                                           newVal.get$contract(
                                                                    ));
                                        tmp.get$curVal().get$contract().
                                          addObserver(tmp);
                                        int size = tmp.get$proxies().size();
                                        for (int i = 0; i < size; i++) {
                                            ProxyComp
                                              p =
                                              (ProxyComp)
                                                fabric.lang.Object._Proxy.
                                                $getProxy(
                                                  tmp.get$proxies().get(i));
                                            if (!fabric.lang.Object._Proxy.
                                                  idEquals(
                                                    p.get$curVal().get$contract(
                                                                     ), null)) {
                                                p.get$curVal().get$contract().
                                                  removeObserver(p);
                                            }
                                            p.get$curVal().
                                              set$value(
                                                tmp.makeProxyResult(
                                                      newVal, p.$getStore()));
                                            p.get$curVal().
                                              set$contract(
                                                newVal.get$contract(
                                                         ).getProxyContract(
                                                             p.$getStore()));
                                            p.get$curVal().get$contract().
                                              addObserver(p);
                                        }
                                    }
                                }
                            }
                            catch (final fabric.worker.RetryException $e634) {
                                $commit632 = false;
                                continue $label631;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e634) {
                                $commit632 = false;
                                fabric.common.TransactionID $currentTid635 =
                                  $tm636.getCurrentTid();
                                if ($e634.tid.isDescendantOf($currentTid635))
                                    continue $label631;
                                if ($currentTid635.parent != null) {
                                    $retry633 = false;
                                    throw $e634;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e634) {
                                $commit632 = false;
                                if ($tm636.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid635 =
                                  $tm636.getCurrentTid();
                                if ($e634.tid.isDescendantOf($currentTid635)) {
                                    $retry633 = true;
                                }
                                else if ($currentTid635.parent != null) {
                                    $retry633 = false;
                                    throw $e634;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e634) {
                                $commit632 = false;
                                if ($tm636.checkForStaleObjects())
                                    continue $label631;
                                $retry633 = false;
                                throw new fabric.worker.AbortException($e634);
                            }
                            finally {
                                if ($commit632) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e634) {
                                        $commit632 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e634) {
                                        $commit632 = false;
                                        fabric.common.TransactionID
                                          $currentTid635 =
                                          $tm636.getCurrentTid();
                                        if ($currentTid635 != null) {
                                            if ($e634.tid.equals(
                                                            $currentTid635) ||
                                                  !$e634.tid.
                                                  isDescendantOf(
                                                    $currentTid635)) {
                                                throw $e634;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit632 && $retry633) {
                                    { loop = loop$var630; }
                                    continue $label631;
                                }
                            }
                        }
                    }
                }
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    loop =
                      autoRetry &&
                        !fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$curVal(
                                                           ).get$contract(),
                                                     null) &&
                        !tmp.get$curVal().get$contract().valid();
                }
                else {
                    {
                        boolean loop$var640 = loop;
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
                                loop =
                                  autoRetry &&
                                    !fabric.lang.Object._Proxy.
                                    idEquals(tmp.get$curVal().get$contract(),
                                             null) &&
                                    !tmp.get$curVal().get$contract().valid();
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
                                    { loop = loop$var640; }
                                    continue $label641;
                                }
                            }
                        }
                    }
                }
            }
            return tmp.get$curVal();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(
                                             this.get$curVal().get$contract(),
                                             null))
                return this.get$curVal().get$contract().getLeafSubjects();
            final fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            return (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(local).
                     fabric$lang$arrays$ObjectArray$(
                       this.get$$updateLabel(),
                       this.get$$updateLabel().confPolicy(),
                       fabric.metrics.SampledMetric._Proxy.class, 0).$getProxy(
                                                                       );
        }
        
        public boolean handleUpdates() {
            long time = java.lang.System.currentTimeMillis();
            if (fabric.lang.Object._Proxy.idEquals(
                                            this.get$curVal().get$contract(),
                                            null)) {
                return false;
            }
            else if (!this.get$curVal().get$contract().valid(time)) {
                this.get$curVal().get$contract().
                  removeObserver(
                    (fabric.metrics.contracts.warranties.WarrantyComp)
                      this.$getProxy());
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
        public ProxyComp makeProxy(final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_makeProxy(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), proxyStore);
        }
        
        private static ProxyComp static_makeProxy(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          final fabric.worker.Store proxyStore) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ProxyComp
                  p =
                  ((ProxyComp)
                     new fabric.metrics.contracts.warranties.WarrantyComp.
                       ProxyComp._Impl(proxyStore).
                     $getProxy()).
                  fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                    tmp);
                tmp.get$proxies().add(p);
                return p;
            }
            else {
                ProxyComp rtn = null;
                {
                    fabric.metrics.contracts.warranties.WarrantyComp.ProxyComp
                      rtn$var650 = rtn;
                    fabric.worker.transaction.TransactionManager $tm656 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled659 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff657 = 1;
                    boolean $doBackoff658 = true;
                    boolean $retry653 = true;
                    $label651: for (boolean $commit652 = false; !$commit652; ) {
                        if ($backoffEnabled659) {
                            if ($doBackoff658) {
                                if ($backoff657 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff657);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e654) {
                                            
                                        }
                                    }
                                }
                                if ($backoff657 < 5000) $backoff657 *= 2;
                            }
                            $doBackoff658 = $backoff657 <= 32 || !$doBackoff658;
                        }
                        $commit652 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((ProxyComp)
                                 new fabric.metrics.contracts.warranties.
                                   WarrantyComp.ProxyComp._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$warranties$WarrantyComp$ProxyComp$(
                                  tmp);
                            tmp.get$proxies().add(rtn);
                        }
                        catch (final fabric.worker.RetryException $e654) {
                            $commit652 = false;
                            continue $label651;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e654) {
                            $commit652 = false;
                            fabric.common.TransactionID $currentTid655 =
                              $tm656.getCurrentTid();
                            if ($e654.tid.isDescendantOf($currentTid655))
                                continue $label651;
                            if ($currentTid655.parent != null) {
                                $retry653 = false;
                                throw $e654;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e654) {
                            $commit652 = false;
                            if ($tm656.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid655 =
                              $tm656.getCurrentTid();
                            if ($e654.tid.isDescendantOf($currentTid655)) {
                                $retry653 = true;
                            }
                            else if ($currentTid655.parent != null) {
                                $retry653 = false;
                                throw $e654;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e654) {
                            $commit652 = false;
                            if ($tm656.checkForStaleObjects())
                                continue $label651;
                            $retry653 = false;
                            throw new fabric.worker.AbortException($e654);
                        }
                        finally {
                            if ($commit652) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e654) {
                                    $commit652 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e654) {
                                    $commit652 = false;
                                    fabric.common.TransactionID $currentTid655 =
                                      $tm656.getCurrentTid();
                                    if ($currentTid655 != null) {
                                        if ($e654.tid.equals($currentTid655) ||
                                              !$e654.tid.isDescendantOf(
                                                           $currentTid655)) {
                                            throw $e654;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit652 && $retry653) {
                                { rtn = rtn$var650; }
                                continue $label651;
                            }
                        }
                    }
                }
                return rtn;
            }
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
            this.set$proxies(
                   ((fabric.util.ArrayList)
                      new fabric.util.ArrayList._Impl(
                        this.$getStore()).$getProxy()).fabric$util$ArrayList$(
                                                         4));
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
            $writeRef($getStore(), this.proxies, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.proxies = (fabric.util.ArrayList)
                             $readRef(fabric.util.ArrayList._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
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
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
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
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -50, -21, 111, 30,
    -100, -113, -119, -115, 11, 57, -83, 124, -20, 85, -15, 32, -97, 88, -30,
    62, 65, -67, 20, 68, -125, -104, -32, 2, -93, 42, 66, 64 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521832678000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
