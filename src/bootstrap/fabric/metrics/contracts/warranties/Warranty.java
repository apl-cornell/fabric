package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * A Warranty (also known as a General Contract) is a time-limited assertion of
 * the form <code>f(...) = y</code> and is enforced by an associated metric
 * contract.
 */
public interface Warranty extends fabric.metrics.contracts.Contract {
    public fabric.metrics.contracts.warranties.WarrantyComp get$computation();
    
    public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
      fabric.metrics.contracts.warranties.WarrantyComp val);
    
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.MetricContract get$witness();
    
    public fabric.metrics.contracts.MetricContract set$witness(
      fabric.metrics.contracts.MetricContract val);
    
    /**
   * @param computation
   *        the computation being warrantied.
   */
    public fabric.metrics.contracts.warranties.Warranty
      fabric$metrics$contracts$warranties$Warranty$(
      fabric.metrics.contracts.warranties.WarrantyComp computation);
    
    public boolean refresh();
    
    public long getExpiry();
    
    /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
    public fabric.lang.Object value();
    
    public java.lang.String toString();
    
    public fabric.util.Set getLeafSubjects();
    
    public void activate();
    
    public void deactivate();
    
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    public void attemptExtension();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$computation();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$computation(val);
        }
        
        public fabric.lang.Object get$value() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$value();
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$witness();
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$witness(val);
        }
        
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp arg1) {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              fabric$metrics$contracts$warranties$Warranty$(arg1);
        }
        
        public fabric.lang.Object value() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              value();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Warranty._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.computation;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.computation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp computation;
        
        public fabric.lang.Object get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.Object value;
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.witness;
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witness = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.MetricContract witness;
        
        /**
   * @param computation
   *        the computation being warrantied.
   */
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp computation) {
            this.set$computation(computation);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.warranties.Warranty)
                     this.$getProxy();
        }
        
        public boolean refresh() {
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  !this.get$witness().valid(currentTime)) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.INFO,
                      "REFRESHING WARRANTY WITNESS");
                this.get$witness().handleUpdates();
            }
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    this.set$value(curVal.get$value());
                }
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null) &&
                      !fabric.lang.Object._Proxy.idEquals(
                                                   this.get$witness(),
                                                   curVal.get$contract())) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
                this.set$witness(curVal.get$contract());
                this.get$witness().activate();
                this.get$witness().
                  addObserver((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy());
                return true;
            }
            return false;
        }
        
        public long getExpiry() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  isActive())
                return this.get$witness().getExpiry();
            return -1;
        }
        
        /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
        public fabric.lang.Object value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$value();
        }
        
        public java.lang.String toString() {
            return "Warranty " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$computation())) +
            " = " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$value())) +
            " until " +
            getExpiry();
        }
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null))
                return this.get$witness().getLeafSubjects();
            else
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
        }
        
        public void activate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            super.activate();
        }
        
        public void deactivate() {
            if (!isObserved()) {
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null)) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
                this.set$value(null);
            }
            super.deactivate();
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            
        }
        
        public void attemptExtension() {  }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.Warranty._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.computation, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.witness, refTypes, out, intraStoreRefs,
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
            this.computation =
              (fabric.metrics.contracts.warranties.WarrantyComp)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyComp._Proxy.class,
                  (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.witness =
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.Warranty._Impl src =
              (fabric.metrics.contracts.warranties.Warranty._Impl) other;
            this.computation = src.computation;
            this.value = src.value;
            this.witness = src.witness;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public _Proxy(fabric.metrics.contracts.warranties.Warranty._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.Warranty.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  Warranty.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    Warranty.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.Warranty._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.Warranty._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.Warranty._Static {
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
                return new fabric.metrics.contracts.warranties.Warranty._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 84, -116, -124, -76,
    -125, 60, -39, 78, -96, -41, 30, -118, 65, 103, -45, 59, -18, 82, -27, -78,
    -43, 12, 120, 11, 27, 101, 34, -4, -42, 44, 92, -110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUYbWwcxXXubJ999hE7zreT+CtHqgTnjkBVCQyI+Orgay7Esh1KncCxtzd33nhvd7M7Z58hRrS0ihWhSAWTEgksfgS1gIEKNUJVm4q2lAJpK6AlQBvaqFJU2hDUFPXjBy19b3bu9m7vo/aPnjTz5mbem3nvzfuaXbxMGiyT9KakhKKG2IxBrdBuKRGNDUumRZMRVbKsMZiNyy310RMffjvZ6SXeGAnIkqZriiypcc1iZEXskDQlhTXKwvtHov0HiF9GwiHJmmDEe2AgZ5JuQ1dn0qrOxCFl+z96TXj+W3e3vVhHWsdJq6KNMokpckTXGM2xcRLI0EyCmtauZJImx8lKjdLkKDUVSVXuBURdGyftlpLWJJY1qTVCLV2dQsR2K2tQk5+Zn0T2dWDbzMpMN4H9Npv9LFPUcEyxWH+M+FIKVZPWYXI/qY+RhpQqpQFxbSwvRZjvGN6N84DerACbZkqSaZ6kflLRkox0uSkKEgf3AAKQNmYom9ALR9VrEkyQdpslVdLS4VFmKloaUBv0LJzCSEfVTQGpyZDkSSlN44ysd+MN20uA5edqQRJG1rjR+E5wZx2uOyu6rcu333T8Pm1I8xIP8Jyksor8NwFRp4tohKaoSTWZ2oSB7bET0tozc15CAHmNC9nGeenIlVv7Ol9+zcbZWAFnX+IQlVlcPpVY8damyLYb6pCNJkO3FDSFEsn5rQ6Llf6cAda+trAjLobyiy+PvPqVB56hl7ykOUp8sq5mM2BVK2U9YygqNW+jGjUlRpNR4qdaMsLXo6QRxjFFo/bsvlTKoixK6lU+5dP5f1BRCrZAFTXCWNFSen5sSGyCj3MGIaQRGvFAswgJ/BZgG/z9hJED4Qk9Q8MJNUunwbzD0KhkyhNh8FtTkcOWKYfNrMYUQBJTYEUArDCYOjMlmVnhack0JcAB+i/bw5kQsGX8f7fPoXRt0x4PKL5L1pM0IVlwi8KiBoZVcJohXU1SMy6rx89EyaozJ7lV+dETLLBmrjcPWMImdwwppp3PDgxeeT5+1rZIpBVqZaTP5jkkeA4VeA45PIfyPAObAfS9EESzEESzRU8uFFmIPstNzGdxXyzsHICdbzRUiaV0M5MjHg8XczWn57YFljEJEQeCSmDb6F1fumeutw6M2piux3sG1KDbxZzAFIWRBH4Tl1uPfviPF07M6o6zMRIsiwHllOjDvW6dmbpMkxAjne23d0un42dmg16MP35UjgTGC3Gm031GiS/35+MiaqMhRlpQB5KKS/lg1swmTH3ameG2sAK7dtssUFkuBnlIvXnUeOK9X/35ep5s8tG3tShMj1LWX+TxuFkr9+2Vju7HTEoB74PHhh959PLRA1zxgLGl0oFB7CPg6RK4uG5+47XD7//h96d+43UuixGfkU2oipzjsqz8DH4eaP/Bhm6LEwgheEdEyOguxAwDT97q8AbRQ4UIBqxbwf1aRk8qKUVKqBQt5dPWq3ee/uh4m33dKszYyjNJ3//ewJnfMEAeOHv3Pzv5Nh4Zs5ejPwfNDomrnJ13gR/MIB+5r769+eTPpSfA8iGgWcq9lMcowvVB+AVex3Wxg/c7XWufx67X1tYmPl9nlaeH3ZhnHVscDy8+3hG55ZIdAQq2iHv0VIgAd0hFbnLdM5m/e3t9P/OSxnHSxlM8OPQdEkQ1MINxSNJWREzGyFUl66UJ184u/QVf2+T2g6Jj3V7gRB4YIzaOm23Dtw0HFLEKlXQ9tHZCvMMCXourqwzsV+c8hA9u5CRbeL8Vu222MeJwe66wnxf3axL7XC1gV9F+jLRgGssyXipxujWMXLucuIg2jXQdtuNi/4UCA+3IQJctmdcQUK4g0EAVgRjxG6bOQO006ZKrRWx3UMDRErkapvD+8hK1C4nwGkP2NfKlDe5QXUmIAJ62BdpqOGVewNkKQuy1hcBusJxXpDoioFXCa+O0wjRqWXluP1dV/3v5TET8d9Sec06+uXAy//lEsfA3AT8qOrnIBUkOfHBztbqO16Snvja/kNz31E67+movrZUGtWzmuXP//kXosQuvV8i0fqYbO1Q6RdWiM5vhyJ6yB8ZeXvY63nvh0uYbIpMX0/axXS4W3dhP7118/bat8sNeUldw07Jau5Sov9Q5m00KTwVtrMRFuwtK9aOy7oHWAXFryIbeK8XG4JiQ6z4K3ogkfxXwL+77cIKmx/HpW/muyRpRNYUd1PY7bNsJCtsJFmwn6PhuMO+7QYfXg6XmvhlaD0h4XsCzNSSsYOtI8oaAP64uYbEAmRprvCqGJ2OjSVNQg05UyBfDppKBlD8lnhN0bv7YZ6Hj87Yt2m+uLWXPnmIa+93Fj7sKu2vQI3pqncIpdv/phdkffGf2qFeweidwmdB1lUqaS6/ccjZA64d6fr+Ag8uzHCT5ooC3LE2vR2qs3Y/dNLhnmrLBnKGYMxztsJAfAWNQauha2iVMa95IBoGxuwTctzxhkGRIwIGlCTNXY+0Ydg/mIz/+iVa6AXA6sgdO/KOAby2PaSR5U8A3lsb0N2usPYLdQ4w0Md1+zudzQBsvvHi+Klooy1eVJMQzoHjw3yfg6PIkRJIRAWNLk3ChxtqT2J2EKh1sLEal1GjWTr95QVtFsuNpBOoxntQqibUe2p2QNTYI2Lw8sZDEL2Dd0sR6usbas9idgouD2KpMQSlf0XOmdCVZzQjjwMjHAn6wPFmQ5LyA55Ymy+kaay9h911GmpM0Lw3OPFeJ8xi0SUgSPhu2/HR5nCPJTwT8YXXORerL20hncflmUTlrKmwGg7EmK4akVnYNzs2Paoj9CnbfZ2SdxBjNGBABGdUsqIPjJs3oNXTQDQ2K2cCcgIeXpwMkMQQ8tLTbO1tj7ZfYvQoRwy0G5z8HJppP9/ig21jhU4v4MChHXqGnLu7pW1PlM8v6sk+1gu75hdamdQv73+UfCgof/fzwDk9lVbX44VM09hmQzBUugd9+BhkcvA2JegnvD7BW5w/Xy5s2/TuMrK9Gz+ynIx8X07zHyIpSGsa/v+KoGO938Ni38fDfeefdU9TlbbanqhAl5TvftyNr4ufwxU/W/cvXNHaBf11AOxt76Ovfe/Cm929/8t3OY7vSv+7/eOTii+8Eci0bae+n5/oOPvxffJg7taYXAAA=";
}
