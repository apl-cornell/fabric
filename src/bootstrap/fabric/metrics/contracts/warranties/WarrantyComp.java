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
import fabric.worker.metrics.WarrantyValue;
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
    public fabric.lang.Object get$curVal();
    
    public fabric.lang.Object set$curVal(fabric.lang.Object val);
    
    public fabric.metrics.contracts.Contract get$curContract();
    
    public fabric.metrics.contracts.Contract set$curContract(
      fabric.metrics.contracts.Contract val);
    
    public fabric.worker.metrics.ImmutableSet get$proxies();
    
    public fabric.worker.metrics.ImmutableSet set$proxies(fabric.worker.metrics.ImmutableSet val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
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
   * contract associated with it (guaranteed active when returned) that asserts
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
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
   * that the return value is consistent.
   */
    public fabric.worker.metrics.WarrantyValue apply(long time,
                                                     boolean autoRetry);
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
    public boolean handleUpdates();
    
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
                fabric$metrics$contracts$warranties$WarrantyComp$();
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
        
        public static final byte[] $classHash = new byte[] { 9, 122, -93, -118,
        -8, 36, -57, 114, 21, -110, 87, 51, 124, 86, 115, 121, -110, -107, 64,
        82, 127, 65, 24, 31, -64, 124, 85, -108, -36, -28, 126, -97 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525402905000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVXX2wURRifu7bXXim0tBRKgbaUkwQodwL6AFUDXCicHLZpKUiJnNPduXbp3u4yO0e3SBFNDIQHHrAgJIIP1qhYIDFBH0wNDyoQjInG+CfxDw8SMcgDUZQHFb+Z3bu9215LfPCSm5md+b6Zb77v9/1mZuw2KjEpak7iXkUNsyGDmOE23BuLd2BqEjmqYtPcCr0JaVpx7MTNN+UGP/LHUYWENV1TJKwmNJOhGfHdeC+OaIRFujtjrTtRUOKKm7DZz5B/53qLoiZDV4f6VJ05i0yY//iyyMgru6reLUKVPahS0boYZooU1TVGLNaDKlIk1UuouU6WidyDZmqEyF2EKlhV9oGgrvWgalPp0zBLU2J2ElNX93LBajNtECrWzHRy83Uwm6YlplMwv8o2P80UNRJXTNYaR4GkQlTZ3IMOoOI4KkmquA8EZ8czu4iIGSNtvB/EyxUwkyaxRDIqxQOKJjPU6NXI7ji0GQRAtTRFWL+eXapYw9CBqm2TVKz1RboYVbQ+EC3R07AKQ/WTTgpCZQaWBnAfSTBU55XrsIdAKijcwlUYqvWKiZkgZvWemOVE6/ZTjx19Ttuk+ZEPbJaJpHL7y0CpwaPUSZKEEk0itmLF0vgJPHv8sB8hEK71CNsy7++/s7al4dIVW2ZeAZn23t1EYglptHfG5/OjS1YXcTPKDN1UOBTydi6i2uGMtFoGoH12dkY+GM4MXur8ZMfBs+SWH5XHUEDS1XQKUDVT0lOGohK6kWiEYkbkGAoSTY6K8RgqhXZc0Yjd255MmoTFULEqugK6+AYXJWEK7qJSaCtaUs+0Dcz6RdsyEEI18EdFCPmiCC1/GuoVCC39kKFEpF9PkUivmiaDAO8I/AmmUn8E8pYqUsSkUoSmNaaAkNMFKILKjADUGcUSMyODmFIMMqC/3W4ORWFvYTDN+P+XsPguqwZ9PghAo6TLpBebEE0HWes7VEieTboqE5qQ1KPjMVQzfkqgK8gzwgRUC//5ABHzvVySqzuSXr/hzvnENRuZXNdxL0NrbLvDjt3hrN1h1+5wrt2hDqpbogVGV/CMDAPHhYHjxnxWOHom9o4AXsAUGZpdpwLWWWOomCV1mrKQzyc2PUvoC8QBXgaAh4BqKpZ0PfPks4ebIeaWMVgM0eeiIW/iuXQVgxaGbEpIlYdu/nHhxLDupiBDoQnMMFGTZ3az14NUl4gMzOlOv7QJX0yMD4f8nJWC3FUYIA3s0+BdIy/DWzNsyb1REkfTuA+wyocyFFfO+qk+6PYIZMzgRbUNEu4sj4GCaB/vMk5/89kvq8QRlOHkyhzy7iKsNYcH+GSVIuNnur7fSgkBue9Pdrx8/PahncLxILGo0IIhXvLwY0h8nb50Zc+3P/4w+qXfDRZDQYPqDMiIyJbYzsz78PPB/x/+5/nMO3gNrB51uKQpSyYGX3yxax7QigqzgfVmqFtL6bKSVHCvSjhY/qp8aMXFX49W2RFXocf2H0UtD57A7Z+7Hh28tuvPBjGNT+LHmutCV8zmyhp35nWQGEPcDuuFLxacuoxPA/iB6UxlHxHkhYRLkIjhSuGL5aJc4Rl7hBfNtrfmZzHvPTfa+AHswrEnMvZqffSJWzYlZOHI51hYgBK24ZxMWXk2ddffHPjYj0p7UJU4+yHDt2GgOkBCD5zeZtTpjKPpeeP5J7F97LRm022+NxVylvUmgktF0ObSvF1uY98GDjhiFncS+Mu3CqFlUadezEdrDF7OsnxINNYIlUWiFONLhCP9vLkUQKmkUmnGwy4WWAZpwumWwy/NxHVJaNYy9PB/5USuVy/S1JraBmBFfoOzspvz881VO4fauFOfy9lcDiKQBZBYMNn9Q9ydRl8cOSO3v7HCviVU55/pG7R06txXf38aPnn9aoGTIODcJt0F/bDewgm34C3ibuYi6fqtBaujAzf67DUbPfZ5pd/eMnZ142LpmB8VZSEz4UKYr9SaD5RySuA+q23Ng0tT1qMzuKf2gCcfBZi859RduXCx+bRgnHwiTm54hNunO5N0OnXMGx43pX3uLGvFOt1T5Px2XrQz1GrDLeTALZSFW8iFW6jwERxy9xLP9wBPmLVwYaqz65a7k3iAF50T98tVfnfqWw/cbwGq6qBKCg6cvc4VlxweOXI/fHTExp39Dlg04Sqeq2O/BYSp00W+cvQvnGoVodH284XhD94aPuR3nLyJwamga33iY9cU0UjyYgdD5WlD5ocQUF2GDxY5fDCo0wFCs7SQCYkgRSE7F1iG33NUHV6BlgVf2UgJSMAO5hW45jmPEyn6ERm9sbmldpIrXt2E56Kjd/5MZdmcM91fi2tJ9uERhFM/mVbVXI7NaQcMSpKK2HrQZlxDVLq736n4DxzlfojNp2x9uA3VTabP7FNKtHN10vBcztdh4g3IW7ly4NGALce/hlze9RR27tWnKX9oj/02516gbOt1cUOBaDcF971+5F7oMq09tn3V/m3m0LHjazufX1fXeGl/98h3Px147V80vzWtABAAAA==";
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
        
        public fabric.metrics.contracts.Contract get$curContract() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curContract();
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curContract(val);
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
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
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
        
        public fabric.metrics.contracts.Contract get$curContract() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curContract;
        }
        
        public fabric.metrics.contracts.Contract set$curContract(
          fabric.metrics.contracts.Contract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curContract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The currently cached result. */
        protected fabric.metrics.contracts.Contract curContract;
        
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
        public abstract fabric.worker.metrics.WarrantyValue updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
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
   *            retry if the contract goes stale before returning.
   * @return A {@link WarrantyValue} which holds the return value and the
   * contract associated with it (guaranteed active when returned) that asserts
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
              ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                 tmp.fetch()).dropOldValue();
            while (loop) {
                i++;
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
                            fabric.lang.Object newVal = proxy.get$curVal();
                            fabric.metrics.contracts.Contract newContract =
                              proxy.get$curContract();
                            ((fabric.metrics.contracts.warranties.WarrantyComp.
                               _Impl) tmp.fetch()).setNewValue(newVal, newContract);
                            if (!((fabric.metrics.contracts.warranties.WarrantyComp.
                                    _Impl) tmp.fetch()).dropOldValue()) {
                                break;
                            }
                        }
                    }
                }
                if (fabric.lang.Object._Proxy.idEquals(tmp.get$curContract(),
                                                       null)) {
                    fabric.worker.metrics.WarrantyValue computed =
                      tmp.updatedVal(java.lang.System.currentTimeMillis());
                    if (fabric.lang.Object._Proxy.idEquals(computed.contract,
                                                           null)) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().stats.
                          addMsg("Memoized: false");
                        return computed;
                    }
                    java.lang.String contractStoreName =
                      computed.contract.$getStore().name();
                    if (!fabric.worker.transaction.TransactionManager.
                          getInstance().inTxn() &&
                          !contractStoreName.
                          equals(fabric.worker.Worker.getWorkerName())) {
                        fabric.worker.remote.RemoteWorker w =
                          fabric.worker.Worker.getWorker().getWorker(
                                                             contractStoreName);
                        ((fabric.metrics.contracts.Contract._Proxy)
                           computed.contract).activate$remote(w, null);
                    }
                    else {
                        computed.contract.activate();
                    }
                    ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                       tmp.fetch()).setNewValue(computed.value, computed.contract);
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
                               _Impl) proxy.fetch()).setNewValue(
                                                       computed.value,
                                                       computed.contract);
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
            return fabric.worker.metrics.WarrantyValue.newValue(
                                                         tmp.get$curVal(),
                                                         tmp.get$curContract());
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
                    boolean rtn$var649 = rtn;
                    fabric.worker.transaction.TransactionManager $tm655 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled658 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff656 = 1;
                    boolean $doBackoff657 = true;
                    boolean $retry652 = true;
                    $label650: for (boolean $commit651 = false; !$commit651; ) {
                        if ($backoffEnabled658) {
                            if ($doBackoff657) {
                                if ($backoff656 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff656);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e653) {
                                            
                                        }
                                    }
                                }
                                if ($backoff656 < 5000) $backoff656 *= 2;
                            }
                            $doBackoff657 = $backoff656 <= 32 || !$doBackoff657;
                        }
                        $commit651 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            rtn =
                              fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                                inner_dropOldValue(tmp);
                        }
                        catch (final fabric.worker.RetryException $e653) {
                            $commit651 = false;
                            continue $label650;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e653) {
                            $commit651 = false;
                            fabric.common.TransactionID $currentTid654 =
                              $tm655.getCurrentTid();
                            if ($e653.tid.isDescendantOf($currentTid654))
                                continue $label650;
                            if ($currentTid654.parent != null) {
                                $retry652 = false;
                                throw $e653;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e653) {
                            $commit651 = false;
                            if ($tm655.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid654 =
                              $tm655.getCurrentTid();
                            if ($e653.tid.isDescendantOf($currentTid654)) {
                                $retry652 = true;
                            }
                            else if ($currentTid654.parent != null) {
                                $retry652 = false;
                                throw $e653;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e653) {
                            $commit651 = false;
                            if ($tm655.checkForStaleObjects())
                                continue $label650;
                            $retry652 = false;
                            throw new fabric.worker.AbortException($e653);
                        }
                        finally {
                            if ($commit651) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e653) {
                                    $commit651 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e653) {
                                    $commit651 = false;
                                    fabric.common.TransactionID $currentTid654 =
                                      $tm655.getCurrentTid();
                                    if ($currentTid654 != null) {
                                        if ($e653.tid.equals($currentTid654) ||
                                              !$e653.tid.isDescendantOf(
                                                           $currentTid654)) {
                                            throw $e653;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit651 && $retry652) {
                                { rtn = rtn$var649; }
                                continue $label650;
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
              tmp.get$curContract();
            if (!fabric.lang.Object._Proxy.idEquals(curContract, null) &&
                  !curContract.valid()) {
                curContract.removeObserver(tmp);
                tmp.set$curContract(null);
                tmp.set$$associated(null);
            }
            return fabric.lang.Object._Proxy.idEquals(tmp.get$curContract(),
                                                      null);
        }
        
        private void setNewValue(
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
              static_setNewValue(
                (fabric.metrics.contracts.warranties.WarrantyComp)
                  this.$getProxy(), newVal, newContract);
        }
        
        private static void static_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_setNewValue(tmp, newVal, newContract);
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
                            fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                              inner_setNewValue(tmp, newVal, newContract);
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
        
        private static void inner_setNewValue(
          fabric.metrics.contracts.warranties.WarrantyComp tmp,
          fabric.lang.Object newVal,
          fabric.metrics.contracts.Contract newContract) {
            if (fabric.metrics.contracts.warranties.WarrantyComp._Impl.
                  inner_dropOldValue(tmp) &&
                  !fabric.lang.Object._Proxy.idEquals(newContract, null) &&
                  newContract.valid()) {
                tmp.set$curVal(newVal);
                tmp.set$curContract(
                      newContract.getProxyContract(tmp.$getStore()));
                tmp.get$curContract().addObserver(tmp);
                tmp.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(tmp.get$curContract()));
            }
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curContract(),
                                                    null))
                return this.get$curContract().getLeafSubjects();
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
                    fabric.metrics.contracts.warranties.WarrantyComp
                      rtn$var668 = rtn;
                    fabric.worker.transaction.TransactionManager $tm674 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled677 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff675 = 1;
                    boolean $doBackoff676 = true;
                    boolean $retry671 = true;
                    $label669: for (boolean $commit670 = false; !$commit670; ) {
                        if ($backoffEnabled677) {
                            if ($doBackoff676) {
                                if ($backoff675 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff675);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e672) {
                                            
                                        }
                                    }
                                }
                                if ($backoff675 < 5000) $backoff675 *= 2;
                            }
                            $doBackoff676 = $backoff675 <= 32 || !$doBackoff676;
                        }
                        $commit670 = true;
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
                        catch (final fabric.worker.RetryException $e672) {
                            $commit670 = false;
                            continue $label669;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e672) {
                            $commit670 = false;
                            fabric.common.TransactionID $currentTid673 =
                              $tm674.getCurrentTid();
                            if ($e672.tid.isDescendantOf($currentTid673))
                                continue $label669;
                            if ($currentTid673.parent != null) {
                                $retry671 = false;
                                throw $e672;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e672) {
                            $commit670 = false;
                            if ($tm674.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid673 =
                              $tm674.getCurrentTid();
                            if ($e672.tid.isDescendantOf($currentTid673)) {
                                $retry671 = true;
                            }
                            else if ($currentTid673.parent != null) {
                                $retry671 = false;
                                throw $e672;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e672) {
                            $commit670 = false;
                            if ($tm674.checkForStaleObjects())
                                continue $label669;
                            $retry671 = false;
                            throw new fabric.worker.AbortException($e672);
                        }
                        finally {
                            if ($commit670) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e672) {
                                    $commit670 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e672) {
                                    $commit670 = false;
                                    fabric.common.TransactionID $currentTid673 =
                                      $tm674.getCurrentTid();
                                    if ($currentTid673 != null) {
                                        if ($e672.tid.equals($currentTid673) ||
                                              !$e672.tid.isDescendantOf(
                                                           $currentTid673)) {
                                            throw $e672;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit670 && $retry671) {
                                { rtn = rtn$var668; }
                                continue $label669;
                            }
                        }
                    }
                }
            }
            return rtn;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
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
            $writeRef($getStore(), this.curContract, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.curVal = (fabric.lang.Object)
                            $readRef(fabric.lang.Object._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.curContract =
              (fabric.metrics.contracts.Contract)
                $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.proxies = (fabric.worker.metrics.ImmutableSet) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
            this.curContract = src.curContract;
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
                        fabric.worker.transaction.TransactionManager $tm683 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled686 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff684 = 1;
                        boolean $doBackoff685 = true;
                        boolean $retry680 = true;
                        $label678: for (boolean $commit679 = false; !$commit679;
                                        ) {
                            if ($backoffEnabled686) {
                                if ($doBackoff685) {
                                    if ($backoff684 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff684);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e681) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff684 < 5000) $backoff684 *= 2;
                                }
                                $doBackoff685 = $backoff684 <= 32 ||
                                                  !$doBackoff685;
                            }
                            $commit679 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.warranties.WarrantyComp.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$PROACTIVE(true);
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
                                        fabric.common.TransactionID
                                          $currentTid682 =
                                          $tm683.getCurrentTid();
                                        if ($currentTid682 != null) {
                                            if ($e681.tid.equals(
                                                            $currentTid682) ||
                                                  !$e681.tid.
                                                  isDescendantOf(
                                                    $currentTid682)) {
                                                throw $e681;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -98, 35, -83, 97, 108,
    -58, 33, 108, 17, -25, -90, -5, 118, -121, 30, -75, -15, 28, 76, 102, -97,
    -77, -14, -28, 49, 60, 108, -60, 118, -69, -19, 18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525402905000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
