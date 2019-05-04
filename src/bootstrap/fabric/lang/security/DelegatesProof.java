package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface DelegatesProof extends fabric.lang.security.ActsForProof {
    public fabric.lang.security.DelegatesProof
      fabric$lang$security$DelegatesProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.DelegatesProof {
        public fabric.lang.security.DelegatesProof
          fabric$lang$security$DelegatesProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.DelegatesProof) fetch()).
              fabric$lang$security$DelegatesProof$(arg1, arg2);
        }
        
        public _Proxy(DelegatesProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.DelegatesProof {
        public fabric.lang.security.DelegatesProof
          fabric$lang$security$DelegatesProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.Principal granter) {
            fabric$lang$security$ActsForProof$(actor, granter);
            return (fabric.lang.security.DelegatesProof) this.$getProxy();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getGranter(
                          ))) instanceof fabric.lang.security.DisjunctivePrincipal ||
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getGranter(
                          ))) instanceof fabric.lang.security.ConjunctivePrincipal) {
                return;
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getActor(
                          ))) instanceof fabric.lang.security.ConjunctivePrincipal &&
                  ((fabric.lang.security.ConjunctivePrincipal)
                     fabric.lang.Object._Proxy.$getProxy(getActor())).
                  get$conjuncts().contains(getGranter())) {
                return;
            }
            s.add(
                new fabric.lang.security.SecurityCache.DelegationPair(
                  getActor(), getGranter()));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.DelegatesProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.DelegatesProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.DelegatesProof._Static {
            public _Proxy(fabric.lang.security.DelegatesProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.DelegatesProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  DelegatesProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.DelegatesProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.DelegatesProof._Static._Impl.class);
                $instance = (fabric.lang.security.DelegatesProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.DelegatesProof._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.DelegatesProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 47, 102, -65, 98, 45,
    -5, 40, -102, -13, -1, -53, -70, -48, 78, 95, -40, 31, 32, -9, -118, 120, 2,
    105, -103, -32, -57, 125, 61, 21, 92, -74, 68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVXXWxURRSe3ZbSLYX+QPmnLe1K+OtuUF+wkAALhYWF1hZUfmSZvXd2O/TuvZe5s+0WqIKJgfjQBy0IJpCY1IBaITFBE00jD0YgGKPGKD6ofSFikAdCIib+1DNz7/buXzE+uMmduXfmnDNnvnPON7Mj99AUi6GmOI5RLcD7TWIF2nAsHOnAzCJqSMOWtRNGo8q00vDpOxfUei/yRlClgnVDpwrWorrF0YzIQdyLgzrhwV2d4da9yKcIxS3Y6ubIu3dDmqFG09D6E5rBnUUK7J9aERx6fX/1+yWoag+qonoXx5wqIUPnJM33oMokScYIs9arKlH3oBqdELWLMIo1ehgEDX0PqrVoQsc8xYjVSSxD6xWCtVbKJEyumRkU7hvgNksp3GDgfrXtfopTLRihFm+NoLI4JZpqHUIvoNIImhLXcAIEZ0cyuwhKi8E2MQ7iFRTcZHGskIxKaQ/VVY4a8jUmduzfBgKgOjVJeLcxsVSpjmEA1douaVhPBLs4o3oCRKcYKViFo/mTGgWhchMrPThBohzNzZfrsKdAyidhESoc1eWLSUsQs/l5McuK1r0dawaP6Ft0L/KAzypRNOF/OSjV5yl1kjhhRFeIrVi5PHIazx496UUIhOvyhG2ZD4/eX7ey/up1W2ZBEZn22EGi8KgyHJvx1cLQstUlwo1y07CoSIWcncuodjgzrWkTsn32hEUxGchMXu38bPexd8hdL6oIozLF0FJJyKoaxUiaVCNsM9EJw5yoYeQjuhqS82E0Fd4jVCf2aHs8bhEeRqWaHCoz5DdAFAcTAqKp8E71uJF5NzHvlu9pEyE0HR7kgWcpQqUvQl8ODyTes8FuI0mCMS1F+iC9g/AQzJTuINQto0qLYpj9QYspQZbSOQVJe9zOH4soKUZ5f3Aj0UgCdmB1MMOIB8Al8/8znRa7qu7zeADwBsVQSQxbED0nkzZ0aFAsWwxNJSyqaIOjYTRz9KzMJp+oAAuyWOLlgQxYmM8d2bpDqQ2b7l+K3rQzUeg6cHLUbLsaEK4GMq4Gcl0F7ypFqQWAvAJAXiOedCB0PvyuzKgyS5behMFKMPiUqWEeN1gyjTweubtZUl+mEiRCDxAMcEjlsq7ntx442VQCOWz2lYqwgqg/v6JcHgrDG4YyiSpVJ+78dvn0gOHWFkf+gpIv1BQl25QPFTMUogIluuaXN+Ir0dEBv1fQjQ+YkGPIVaCV+vw1ckq3NUODAo0pETRNYIA1MZXhrgrezYw+d0SmwAzR1NrZIMDKc1Ay6Nou89ytL355Qp4tGbKtymLlLsJbswpcGKuSpVzjYr+TEQJyP5zpeO3UvRN7JfAg0VxsQb9oQ1DYGCraYC9fP/T9Tz8Of+N1g8VRmZmKaVRJy73UjMPPA8/f4hFVKgZED1wdchiicYIiTLHyEtc3IAsNCAtct/y79KSh0jjFMY2ITPmz6rFVV34drLbDrcGIDR5DK//dgDs+bwM6dnP/w3ppxqOIw8rFzxWzGXCma3k9Y7hf+JE+/vWis9fwOch84C+LHiaSkpDEA8kAPi6xaJHtqry5J0XTZKO10BmXH82yXSKaZXLcK16Xcwg01bHm4IucX6VDeszp42J2pinaWbm2GVo02fkkz9bhl4bOq+1vrbJPkdpczt+kp5LvffvX54EzYzeKMIePG2aLRnqJlrVmCSy5uOCitF0e3255jd1dtDrUczthL9uQ52K+9NvbR25sXqK86kUlE7VecGfIVWrNdhaKjhG48uhi22KkQgajcQJUnwDrgPMy7vQXs0B1KrNopDwyUm6EvMiJijBywenfzI+Qmx9eO97yuw4IpigfdwDFKtTENtTz8llWDG6VbfgR2dcumhDwpL2EXyzhzyzhz6V8v7vjdbk4CaszYNvHnX73JDiJpq0QFaHynNN3To6KxzkOHFSmu7UI9JUBwSdA0Ay4Yaflys88YvP7RPM0Rwthj92EObuFYt9ITLiTAINTYlmFVzNAPgl82utczcjJoVfGA4NDdj3Y99fmgitkto59h5U+TBfNClGVix+1itRo+/nywMcXB054Hf/XcFTaa1C1WEgWwdMAtXfU6bv/W0iESsLp8eQhyUaTPmKuRzQAyzQ/1SmP4BgQRCaOtdnZbV9Qi2d0Gv4w5WakoOQFRe5Izk1eCX1Khm9vW1k3yf1obsF/K0fv0vmq8jnnd30nj/qJW7oPTtJ4StOySCSbUMpMRuJUbtdnn+Cm7DhHs4oVMEflmVe5X2aL9wFKWeIQYtFlSxyGM9aWEF9HZGDmu00G18VFWWM9oNtmMAmfFJcm56eY+L858mDO72XlO8fkeQ6xawzGP4m1/LH0jQfjNz/6ckf0VkPjw1fSXnp27NrA2rp9H2z8B9GAxvAHDwAA";
}
