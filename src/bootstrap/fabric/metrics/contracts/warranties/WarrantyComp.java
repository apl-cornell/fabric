package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.ArrayList;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.WarrantyValue;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatyRef;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;
import fabric.common.util.LongIterator;
import fabric.common.util.Pair;

/**
 * A computation that uses {@link MetricTreaty}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link MetricTreaty}.
 * This helps to ensure that the {@link MetricTreaty} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.worker.metrics.treaties.TreatyRef get$curTreaty();
    
    public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
      fabric.worker.metrics.treaties.TreatyRef val);
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(
      fabric.worker.metrics.ImmutableSet val);
    
    public boolean get$recomputeOnInvalidation();
    
    public boolean set$recomputeOnInvalidation(boolean val);
    
    public boolean get$proactive();
    
    public boolean set$proactive(boolean val);
    
    /**
   * Create a new updated result paired with a treaty that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.worker.metrics.WarrantyValue updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the treaty goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time,
                                                     boolean autoRetry);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public fabric.worker.metrics.ImmutableObserverSet
      handleUpdates(boolean includesObserver, fabric.common.util.LongSet treaties);
    
    /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
    public fabric.lang.Object makeProxyResult(
      fabric.worker.metrics.WarrantyValue val, final fabric.worker.Store proxyStore);
    
    /**
   * Make a warranty comp that resides on another store that can be used locally
   * at that store when a memoized result is available.
   */
    public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
      final fabric.worker.Store proxyStore);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$(
      boolean recomputeOnInvalidation, boolean proactive);
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$(
      boolean recomputeOnInvalidation);
    
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
        
        public fabric.worker.metrics.WarrantyValue updatedVal(long time);
        
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
                fabric$metrics$contracts$warranties$WarrantyComp$(
                  baseComputation.get$recomputeOnInvalidation(),
                  baseComputation.get$proactive());
                return (ProxyComp) this.$getProxy();
            }
            
            public fabric.worker.metrics.WarrantyValue updatedVal(long time) {
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
        
        public static final byte[] $classHash = new byte[] { 24, -51, -7, 38,
        -118, 37, -91, 73, -24, -101, 20, 75, -30, -57, 26, 113, 61, -76, 63,
        -75, -30, -39, -72, -15, -78, -39, -37, -14, 93, -44, -50, 26 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527122445000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVXa2xURRSeXcr2QaGlvAuU0i4kPNyVhz+gosDKY2WRpqUQSmCZ3ju7vfTuvZe5s/QWxaCJgfiDH1oQovALomKFhEg0MST8UIGgRA0RiVGRhIhBfiDxlYB4Zubu3t3bB/GHm+zM3LnnzJw55zvfnNt3Bw23KWpI4Q5Nj7Aei9iRlbgjnmjG1CZqTMe2vR5mk8qIkvjBW2+rdUEUTKBKBRumoSlYTxo2Q6MS2/FOHDUIi7a1xJs2o3KFK67GdidDwc3LHYrqLVPvSesmczfpt/6BOdHeN7ZWnx6GqtpRlWa0Msw0JWYajDisHVVmSKaDUHuZqhK1HY02CFFbCdWwru0CQdNoRzW2ljYwy1JitxDb1HdywRo7axEq9sxNcvNNMJtmFWZSML9amp9lmh5NaDZrSqBQSiO6au9AL6KSBBqe0nEaBMcncqeIihWjK/k8iFdoYCZNYYXkVEq6NENlaJpfI3/i8BoQANXSDGGdZn6rEgPDBKqRJunYSEdbGdWMNIgON7OwC0O1gy4KQmUWVrpwmiQZmuiXa5avQKpcuIWrMDTOLyZWgpjV+mJWEK07zz25/3ljtRFEAbBZJYrO7S8DpTqfUgtJEUoMhUjFytmJg3j82X1BhEB4nE9Yynz4wt2lc+vOXZAykweQWdexnSgsqRzrGPXVlNisRcO4GWWWaWscCkUnF1Ftdt80ORagfXx+Rf4yknt5ruWzTXtOkNtBVBFHIcXUsxlA1WjFzFiaTugqYhCKGVHjqJwYaky8j6NSGCc0g8jZdamUTVgclehiKmSKZ3BRCpbgLiqFsWakzNzYwqxTjB0LITQG/mgYQoE0Qk88A30bQvMhh5LRTjNDoh16lnQDvKPwJ5gqnVHIW6opUZsqUZo1mAZC7hSgCDo7ClBnFCvMjnZjSjHIgP5GOeyJwdkiYJr1/2/h8FNWdwcCEIBpiqmSDmxDNF1kLW/WIXlWm7pKaFLR95+NozFnDwt0lfOMsAHVwn8BQMQUP5cU6vZml6+4ezJ5SSKT67ruZWixtDvi2h3J2x3x7I4U2h1upqYjRmB0Jc/ICHBcBDiuL+BEYkfj7wnghWyRofl9KmGfxZaOWcqkGQcFAuLQY4W+QBzgpQt4CKimclbrlme37WuAmDtWdwlEn4uG/Ynn0VUcRhiyKalU7b31x6mDu00vBRkK92OG/po8sxv8HqSmQlRgTm/52fX4TPLs7nCQs1I5dxUGSAP71Pn3KMrwphxbcm8MT6AR3AdY569yFFfBOqnZ7c0IZIziTY0ECXeWz0BBtEtarSPfXv5lgbiCcpxcVUDerYQ1FfAAX6xKZPxoz/frKSEg9/2h5tcP3Nm7WTgeJBoH2jDMWx5+DIlv0lcu7Lj24w/HrgS9YDFUblGTARkR1RHHGf0QfgH4/8P/PJ/5BO+B1WMul9TnycTim8/0zANa0WE1sN4OtxkZU9VSGu7QCQfL/aoZ8878ur9aRlyHGek/iuY+egFvftJytOfS1j/rxDIBhV9rngs9McmVY7yVl0Fi9HA7nJe+nnr4PD4C4Aems7VdRJAXEi5BIobzhS8eE+0837uFvGmQ3pqSx7z/3ljJL2APju3RvrdqY0/dlpSQhyNfY/oAlLABF2TK/BOZ34MNoU+DqLQdVYu7HzJ8AwaqAyS0w+1tx9zJBBpZ9L74JpbXTlM+3ab4U6FgW38ieFQEYy7NxxUS+xI44Iix3Engr8BG4P07bv8lfzvG4u1YJ4DEYLFQaRTtTN7MEo4M8uFsAKWWyWQZD7vYYA6kCadbDr8sE+WS0BzH0OP/lRO5Xq1IU2doG4AVeQXn5A8X5IercS+1tNtvLDhcASKQA5CYOlj9IWqnYy/3HlXXHZ8nq4Sa4jt9hZHNvP/Ng88jh65fHOAmCLnVpLdhEPab3q8KXitqMw9J129PXRTrupmWe07z2eeXfndt38VVM5XXgmhYHjL9CsJipaZioFRQAvWssb4ILvV5j47intoBntyE0IJtsp//oBAukk8HjFNAxMkLj3D7SHeR+25/zx8eL6UD3ipLxT5tQ+S8CPM6hpok3MIu3MJ5uIU9uIUHvoLD3lkSxR7gCaNCwVQi+4U/DeIB3rT0Py9Xue72Vx953gGoqplqGbhwdrolLtnX++rDyP5eiTv5HdDYrxQv1JHfAsLUkSJfOfqnD7WL0Fj586ndH7+ze2/QdfJqBreCaaTFw9YhopHizSaGKrKWyi8hoLocHzS6fNBt0i5C87SQC4kgRSE7CViG1zm6CV+BjgNP+UgJSMAJJg9Q5rkfJ0rsE3Ls5pq54wYp8Sb2+1x09U4erSqbcLTtqihL8h8e5XDrp7K6XsixBeOQRUlKE0cvl4xric70zjsU/4GjvAdx+IzUh2po4mD6TN5SYlyok4XP5WIdJr4B+ahQDjwaknL8qcfjXV8jc682S/mHdt+9CX+FytZfFxUKRLt+4hd/z3x1xvH4rTfHrrlxvnbHkg+ePnPj2kd3T1/77rctVy7X/gujTE+1ABAAAA==";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.lang.Object get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.lang.Object set$curVal(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.worker.metrics.treaties.TreatyRef get$curTreaty() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curTreaty();
        }
        
        public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
          fabric.worker.metrics.treaties.TreatyRef val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curTreaty(val);
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
        
        public boolean get$recomputeOnInvalidation() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$recomputeOnInvalidation();
        }
        
        public boolean set$recomputeOnInvalidation(boolean val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$recomputeOnInvalidation(val);
        }
        
        public boolean get$proactive() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$proactive();
        }
        
        public boolean set$proactive(boolean val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$proactive(val);
        }
        
        public fabric.worker.metrics.WarrantyValue updatedVal(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.worker.metrics.WarrantyValue apply(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.worker.metrics.WarrantyValue apply(long arg1,
                                                         boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1, arg2);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates(arg1, arg2);
        }
        
        public fabric.lang.Object makeProxyResult(
          fabric.worker.metrics.WarrantyValue arg1, fabric.worker.Store arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxyResult(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp makeProxy(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              makeProxy(arg1);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(boolean arg1,
                                                            boolean arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$(arg1, arg2);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(boolean arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$(arg1);
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
        public fabric.lang.Object get$curVal() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curVal;
        }
        
        public fabric.lang.Object set$curVal(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.lang.Object curVal;
        
        public fabric.worker.metrics.treaties.TreatyRef get$curTreaty() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curTreaty;
        }
        
        public fabric.worker.metrics.treaties.TreatyRef set$curTreaty(
          fabric.worker.metrics.treaties.TreatyRef val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curTreaty = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.worker.metrics.treaties.TreatyRef curTreaty;
        
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
        
        public boolean get$recomputeOnInvalidation() {
            return this.recomputeOnInvalidation;
        }
        
        public boolean set$recomputeOnInvalidation(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.recomputeOnInvalidation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected boolean recomputeOnInvalidation;
        
        public boolean get$proactive() { return this.proactive; }
        
        public boolean set$proactive(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proactive = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected boolean proactive;
        
        /**
   * Create a new updated result paired with a treaty that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.worker.metrics.WarrantyValue updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.worker.metrics.WarrantyValue apply(long time) {
            return apply(time, true);
        }
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @param autoRetry
   *            flag indicating whether the computation should automatically
   *            retry if the treaty goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * treaty associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
        public fabric.worker.metrics.WarrantyValue apply(long time,
                                                         boolean autoRetry) {
            return fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_apply((fabric.metrics.contracts.warranties.WarrantyComp)
                             this.$getProxy(), time, autoRetry);
        }
        
        private static fabric.worker.metrics.WarrantyValue static_apply(
          fabric.metrics.contracts.warranties.WarrantyComp tmp, long time,
          boolean autoRetry) {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            int i = 0;
            boolean loop =
              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_dropOldValue(tmp);
            while (loop) {
                i++;
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.INFO,
                    "RUNNING ATTEMPT " +
                      i +
                      " OF " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                      " IN " +
                      java.lang.Thread.currentThread());
                if (!tmp.get$proactive()) {
                    for (java.util.Iterator iter = tmp.get$proxies().iterator();
                         iter.hasNext(); ) {
                        fabric.metrics.contracts.warranties.WarrantyComp
                          proxy =
                          (fabric.metrics.contracts.warranties.WarrantyComp)
                            fabric.lang.Object._Proxy.
                            $getProxy(
                              fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                        if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_dropOldValue(proxy)) {
                            fabric.lang.Object newVal = proxy.get$curVal();
                            fabric.worker.metrics.treaties.TreatyRef newTreaty =
                              proxy.get$curTreaty();
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_setNewValue(tmp, newVal, newTreaty);
                            if (!fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                  static_dropOldValue(tmp)) {
                                break;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(tmp.get$curTreaty(),
                                                       null)) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "GENERATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.worker.metrics.WarrantyValue computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.treaty,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return computed;
                    }
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "ACTIVATING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.worker.metrics.treaties.TreatyRef activatedTreaty =
                      new fabric.worker.metrics.treaties.TreatyRef(
                        (fabric.worker.metrics.treaties.MetricTreaty)
                          computed.treaty.get().update(
                                                  false,
                                                  computed.weakStats).first);
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.INFO,
                        "SETTING RESULT FOR " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.$unwrap(tmp)) +
                          " IN " +
                          java.lang.Thread.currentThread());
                    fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                      static_setNewValue(tmp, computed.value, activatedTreaty);
                    if (tmp.get$proactive()) {
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
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              static_setNewValue(proxy, computed.value,
                                                 activatedTreaty);
                        }
                    }
                }
                loop =
                  autoRetry &&
                    fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                    static_dropOldValue(tmp);
                if (!loop)
                    fabric.worker.transaction.TransactionManager.getInstance().
                      stats.
                      addMsg("Memoized: true");
            }
            return fabric.worker.metrics.WarrantyValue.newValue(
                                                         tmp.get$curVal(),
                                                         tmp.get$curTreaty());
        }
        
        private static boolean static_dropOldValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp) {
            boolean rtn = false;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                rtn = ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                         tmp.fetch()).dropOldValue();
            }
            else {
                {
                    boolean rtn$var0 = rtn;
                    fabric.worker.transaction.TransactionManager $tm6 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled9 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff7 = 1;
                    boolean $doBackoff8 = true;
                    boolean $retry3 = true;
                    $label1: for (boolean $commit2 = false; !$commit2; ) {
                        if ($backoffEnabled9) {
                            if ($doBackoff8) {
                                if ($backoff7 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff7);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e4) {
                                            
                                        }
                                    }
                                }
                                if ($backoff7 < 5000) $backoff7 *= 2;
                            }
                            $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                        }
                        $commit2 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              ((fabric.metrics.contracts.warranties.WarrantyComp.
                                 _Impl) tmp.fetch()).dropOldValue();
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            $commit2 = false;
                            continue $label1;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            $commit2 = false;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5))
                                continue $label1;
                            if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
                            if ($e4.tid.isDescendantOf($currentTid5)) {
                                $retry3 = true;
                            }
                            else if ($currentTid5.parent != null) {
                                $retry3 = false;
                                throw $e4;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e4) {
                            $commit2 = false;
                            if ($tm6.checkForStaleObjects()) continue $label1;
                            $retry3 = false;
                            throw new fabric.worker.AbortException($e4);
                        }
                        finally {
                            if ($commit2) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e4) {
                                    $commit2 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    $commit2 = false;
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
                                    if ($currentTid5 != null) {
                                        if ($e4.tid.equals($currentTid5) ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5)) {
                                            throw $e4;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit2 && $retry3) {
                                { rtn = rtn$var0; }
                                continue $label1;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        private boolean dropOldValue() {
            fabric.worker.metrics.treaties.TreatyRef curTreaty =
              this.get$curTreaty();
            if (!fabric.lang.Object._Proxy.idEquals(curTreaty, null) &&
                  !curTreaty.get().valid()) {
                curTreaty.get().
                  removeObserver(
                    (fabric.metrics.contracts.warranties.WarrantyComp)
                      this.$getProxy());
                this.set$curTreaty(null);
                this.set$$associated(null);
            }
            return fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(),
                                                      null);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.worker.metrics.treaties.TreatyRef newTreaty) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   tmp.fetch()).setNewValue(newVal, newTreaty);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm15 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled18 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff16 = 1;
                    boolean $doBackoff17 = true;
                    boolean $retry12 = true;
                    $label10: for (boolean $commit11 = false; !$commit11; ) {
                        if ($backoffEnabled18) {
                            if ($doBackoff17) {
                                if ($backoff16 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff16);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e13) {
                                            
                                        }
                                    }
                                }
                                if ($backoff16 < 5000) $backoff16 *= 2;
                            }
                            $doBackoff17 = $backoff16 <= 32 || !$doBackoff17;
                        }
                        $commit11 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal,
                                                               newTreaty);
                        }
                        catch (final fabric.worker.RetryException $e13) {
                            $commit11 = false;
                            continue $label10;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e13) {
                            $commit11 = false;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14))
                                continue $label10;
                            if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid14 =
                              $tm15.getCurrentTid();
                            if ($e13.tid.isDescendantOf($currentTid14)) {
                                $retry12 = true;
                            }
                            else if ($currentTid14.parent != null) {
                                $retry12 = false;
                                throw $e13;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e13) {
                            $commit11 = false;
                            if ($tm15.checkForStaleObjects()) continue $label10;
                            $retry12 = false;
                            throw new fabric.worker.AbortException($e13);
                        }
                        finally {
                            if ($commit11) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e13) {
                                    $commit11 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e13) {
                                    $commit11 = false;
                                    fabric.common.TransactionID $currentTid14 =
                                      $tm15.getCurrentTid();
                                    if ($currentTid14 != null) {
                                        if ($e13.tid.equals($currentTid14) ||
                                              !$e13.tid.isDescendantOf(
                                                          $currentTid14)) {
                                            throw $e13;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit11 && $retry12) {
                                {  }
                                continue $label10;
                            }
                        }
                    }
                }
            }
        }
        
        private void setNewValue(
          fabric.lang.Object newVal,
          fabric.worker.metrics.treaties.TreatyRef newTreaty) {
            if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                   this.fetch()).dropOldValue() &&
                  !fabric.lang.Object._Proxy.idEquals(newTreaty, null) &&
                  newTreaty.get().valid()) {
                this.set$curVal(newVal);
                this.
                  set$curTreaty(
                    new fabric.worker.metrics.treaties.TreatyRef(
                      this.get$curTreaty().get().
                          addObserver(
                            (fabric.metrics.contracts.warranties.WarrantyComp)
                              this.$getProxy())));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curTreaty(), null))
                return this.get$curTreaty().get().getLeafSubjects();
            return fabric.worker.metrics.ImmutableMetricsVector.emptyVector();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            if (includesObserver) {
                if (((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       this.fetch()).dropOldValue()) {
                    if (this.get$recomputeOnInvalidation())
                        apply(java.lang.System.currentTimeMillis(), false);
                    affected = affected.addAll(getObservers());
                }
            }
            for (fabric.common.util.LongIterator iter = treaties.iterator();
                 iter.hasNext(); ) {
                long treatyId = iter.next();
                fabric.worker.metrics.treaties.MetricTreaty origTreaty =
                  this.get$$treaties().get(treatyId);
                fabric.common.util.Pair treatyUpdate =
                  origTreaty.update(
                               false,
                               fabric.worker.metrics.StatsMap.emptyStats());
                affected =
                  affected.addAll((fabric.worker.metrics.ImmutableObserverSet)
                                    treatyUpdate.second);
                fabric.worker.metrics.treaties.MetricTreaty newTreaty =
                  (fabric.worker.metrics.treaties.MetricTreaty)
                    treatyUpdate.first;
                if (!newTreaty.equals(origTreaty))
                    this.set$$treaties(this.get$$treaties().add(newTreaty));
            }
            return affected;
        }
        
        /**
   * Copy result for a proxy computation to use.
   *
   * Default is to just copy the reference.  Implementations should override
   * this to make and return copy on the proxyStore.
   */
        public fabric.lang.Object makeProxyResult(
          fabric.worker.metrics.WarrantyValue val,
          final fabric.worker.Store proxyStore) {
            return val.value;
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
                    fabric.metrics.contracts.warranties.WarrantyComp rtn$var19 =
                      rtn;
                    fabric.worker.transaction.TransactionManager $tm25 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled28 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff26 = 1;
                    boolean $doBackoff27 = true;
                    boolean $retry22 = true;
                    $label20: for (boolean $commit21 = false; !$commit21; ) {
                        if ($backoffEnabled28) {
                            if ($doBackoff27) {
                                if ($backoff26 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff26);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e23) {
                                            
                                        }
                                    }
                                }
                                if ($backoff26 < 5000) $backoff26 *= 2;
                            }
                            $doBackoff27 = $backoff26 <= 32 || !$doBackoff27;
                        }
                        $commit21 = true;
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
                        catch (final fabric.worker.RetryException $e23) {
                            $commit21 = false;
                            continue $label20;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e23) {
                            $commit21 = false;
                            fabric.common.TransactionID $currentTid24 =
                              $tm25.getCurrentTid();
                            if ($e23.tid.isDescendantOf($currentTid24))
                                continue $label20;
                            if ($currentTid24.parent != null) {
                                $retry22 = false;
                                throw $e23;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e23) {
                            $commit21 = false;
                            if ($tm25.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid24 =
                              $tm25.getCurrentTid();
                            if ($e23.tid.isDescendantOf($currentTid24)) {
                                $retry22 = true;
                            }
                            else if ($currentTid24.parent != null) {
                                $retry22 = false;
                                throw $e23;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e23) {
                            $commit21 = false;
                            if ($tm25.checkForStaleObjects()) continue $label20;
                            $retry22 = false;
                            throw new fabric.worker.AbortException($e23);
                        }
                        finally {
                            if ($commit21) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e23) {
                                    $commit21 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e23) {
                                    $commit21 = false;
                                    fabric.common.TransactionID $currentTid24 =
                                      $tm25.getCurrentTid();
                                    if ($currentTid24 != null) {
                                        if ($e23.tid.equals($currentTid24) ||
                                              !$e23.tid.isDescendantOf(
                                                          $currentTid24)) {
                                            throw $e23;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit21 && $retry22) {
                                { rtn = rtn$var19; }
                                continue $label20;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(
          boolean recomputeOnInvalidation, boolean proactive) {
            this.set$proxies(fabric.worker.metrics.ImmutableSet.emptySet());
            this.set$recomputeOnInvalidation(recomputeOnInvalidation);
            this.set$proactive(proactive);
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$(
          boolean recomputeOnInvalidation) {
            return fabric$metrics$contracts$warranties$WarrantyComp$(
                     recomputeOnInvalidation, true);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return fabric$metrics$contracts$warranties$WarrantyComp$(true,
                                                                     true);
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
            out.writeBoolean(this.recomputeOnInvalidation);
            out.writeBoolean(this.proactive);
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
            this.curVal = (fabric.lang.Object)
                            $readRef(fabric.lang.Object._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
            this.recomputeOnInvalidation = in.readBoolean();
            this.proactive = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.curTreaty = src.curTreaty;
            this.proxies = src.proxies;
            this.recomputeOnInvalidation = src.recomputeOnInvalidation;
            this.proactive = src.proactive;
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
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -87, -106, -97, 90, 0,
    47, 116, 14, -125, 42, 75, 26, 119, -41, -105, -35, 126, 125, 80, 94, 26,
    -64, 76, -65, -3, 90, -97, 69, 99, -58, -38, 5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527122445000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
