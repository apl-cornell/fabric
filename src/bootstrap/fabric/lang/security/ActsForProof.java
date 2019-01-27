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
public interface ActsForProof extends fabric.lang.Object {
    public fabric.lang.security.Principal get$actor();
    
    public fabric.lang.security.Principal set$actor(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.Principal get$granter();
    
    public fabric.lang.security.Principal set$granter(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);
    
    public fabric.lang.security.Principal getActor();
    
    public fabric.lang.security.Principal getGranter();
    
    public abstract void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ActsForProof {
        public fabric.lang.security.Principal get$actor() {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              get$actor();
        }
        
        public fabric.lang.security.Principal set$actor(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              set$actor(val);
        }
        
        public fabric.lang.security.Principal get$granter() {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              get$granter();
        }
        
        public fabric.lang.security.Principal set$granter(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              set$granter(val);
        }
        
        public fabric.lang.security.ActsForProof
          fabric$lang$security$ActsForProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.ActsForProof) fetch()).
              fabric$lang$security$ActsForProof$(arg1, arg2);
        }
        
        public fabric.lang.security.Principal getActor() {
            return ((fabric.lang.security.ActsForProof) fetch()).getActor();
        }
        
        public fabric.lang.security.Principal getGranter() {
            return ((fabric.lang.security.ActsForProof) fetch()).getGranter();
        }
        
        public void gatherDelegationDependencies(java.util.Set arg1) {
            ((fabric.lang.security.ActsForProof) fetch()).
              gatherDelegationDependencies(arg1);
        }
        
        public _Proxy(ActsForProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.ActsForProof {
        public fabric.lang.security.Principal get$actor() { return this.actor; }
        
        public fabric.lang.security.Principal set$actor(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.actor = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal actor;
        
        public fabric.lang.security.Principal get$granter() {
            return this.granter;
        }
        
        public fabric.lang.security.Principal set$granter(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.granter = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal granter;
        
        public fabric.lang.security.ActsForProof
          fabric$lang$security$ActsForProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.Principal granter) {
            this.set$actor(actor);
            this.set$granter(granter);
            fabric$lang$Object$();
            return (fabric.lang.security.ActsForProof) this.$getProxy();
        }
        
        public fabric.lang.security.Principal getActor() {
            return this.get$actor();
        }
        
        public fabric.lang.security.Principal getGranter() {
            return this.get$granter();
        }
        
        public abstract void gatherDelegationDependencies(java.util.Set s);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ActsForProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.actor, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.granter, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.actor = (fabric.lang.security.Principal)
                           $readRef(fabric.lang.security.Principal._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.granter =
              (fabric.lang.security.Principal)
                $readRef(fabric.lang.security.Principal._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ActsForProof._Impl src =
              (fabric.lang.security.ActsForProof._Impl) other;
            this.actor = src.actor;
            this.granter = src.granter;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ActsForProof._Static {
            public _Proxy(fabric.lang.security.ActsForProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ActsForProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ActsForProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.ActsForProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ActsForProof._Static._Impl.class);
                $instance = (fabric.lang.security.ActsForProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ActsForProof._Static {
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
                return new fabric.lang.security.ActsForProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 6, 123, 81, 104, 11,
    -38, -64, 54, 70, 60, 119, -68, -47, -33, 46, -123, -51, -18, -70, -17,
    -119, 99, -33, 66, -17, 13, -105, -99, 12, 92, 35, 9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcRxWf29hnn+PEH/lqXcexnWukfN0pBVVqXQr2NU6OXomJ7SKcEndud+68yd7uZnbOPrcEClKVtBIBWidtBclfQUBqWglUVYJGyh9QWvVDaoX4kiCRUEVRCFWF+PgDKO/N7N3u7Z1N/sHSfNzMezNv3vzeb956+QZp9TgZLtC8aaXEosu81DjNZ3MTlHvMyFjU86ZgdFZf25I99/53jQGNaDnSqVPbsU2dWrO2J8j63DE6T9M2E+npw9mRIySho+JB6s0Joh0Zq3Ay6DrWYtFyhL9Jw/pnd6eXnjna/cM1pGuGdJn2pKDC1DOOLVhFzJDOEivlGfdGDYMZM6THZsyYZNyklvkICDr2DOn1zKJNRZkz7zDzHGseBXu9ssu43LM6iOY7YDYv68LhYH63Mr8sTCudMz0xkiPxgskswztBvkRacqS1YNEiCG7OVU+Rliumx3EcxDtMMJMXqM6qKi3HTdsQZFtUo3bi5P0gAKptJSbmnNpWLTaFAdKrTLKoXUxPCm7aRRBtdcqwiyB9Ky4KQu0u1Y/TIpsV5Jao3ISaAqmEdAuqCLIpKiZXgjvri9xZ6LZufOaeM4/aB22NxMBmg+kW2t8OSgMRpcOswDizdaYUO3flztHNl09rhIDwpoiwknn5ix9+as/AldeUzG1NZA7ljzFdzOoX8+vf6c/svGsNmtHuOp6JUKg7ubzVCX9mpOIC2jfXVsTJVHXyyuFXP//YJXZdIx1ZEtcdq1wCVPXoTsk1LcYPMJtxKpiRJQlmGxk5nyVt0M+ZNlOjhwoFj4ksabHkUNyRv8FFBVgCXdQGfdMuONW+S8Wc7FdcQkg3FBKDMkSI9jK07VDmBZlOzzklls5bZbYA8E5DYZTrc2mIW27qe3XHXUx7XE/zsi1MkFTjCj8e08vcFIvpUV144w6f4I5TSIFB7v9r4QqeqHshFgNnb9Mdg+WpBzfno2hswoJAOehYBuOzunXmcpZsuPycRFIC0e8BgqWvYnD7/VHeCOsulcf2f/jC7BsKhajru1KQIWVoCg1NVQ1NhQ0F2zoxyFJAWymgreVYJZW5kH1eYinuyaCrLdcJy93tWlQUHF6qkFhMnm2j1JcgAggcB2oB9ujcOfmFTz98engNoNddaMELBdFkNJYCBspCj0KAzOpdp97/+4vnTjpBVAmSbAj2Rk0M1uGoo7ijMwPIMFh+1yB9afbyyaSGRJMADhQUUAqEMhDdoy5oR6oEiN5ozZG16ANq4VSVtTrEHHcWghEJgPVY9SosoLMiBkru/MSke/7Xb//pY/JVqdJsV4iPJ5kYCYU2LtYlg7gn8P0UZwzkfvfsxNNnb5w6Ih0PEtubbZjEOgMhTSGWHf74ayd+c/X3F3+hBZclSNwt5y1Tr8iz9HwEfzEo/8GC8YkD2AJLZ3xuGKyRg4s77whsA5qwgKrAdC85bZccwyyYNG8xRMq/um7f99Kfz3Sr67ZgRDmPkz3/e4Fg/NYx8tgbR/8xIJeJ6fhMBf4LxBT3bQhWHuWcLqIdla+8u/W5n9PzgHxgLs98hEkyItIfRF7gHdIXe2W9LzL3cayGlbf65bjmNb4D4/igBlicSS9/uy9z73UV9jUs4hpDTcL+QRoKkzsulf6mDcd/ppG2GdIt33JqiwcpkBjAYAZeYy/jD+bIurr5+pdVPSMjtVjrj8ZBaNtoFAR0A32Uxn6HAr4CTpXPB6EkoDzvt9/C2Q0u1hsrMSI7d0uV7bLegdVO5UhB2lxuzgOyBEmYpVJZ4N3LXXYL0kplBoO/NkEUN6W8CeAx3XSpJcVujVKZik6s76y3ehhKB5S3/faVJlbvX8Fq7N6L1SerlrYVOcUUCX+Oyj0rq+juEqSd5oHl4HyVmlnyr8t/FMt+WwiZFUIgqQAEt66Uv8jc6+JXly4Yh76zT2UZvfU5wX67XPrBL//9ZurZa683eV0SwnH3WmyeWaE9W2DLoYZE+gGZ3gXgvXZ9612Z4+8V1bbbIiZGpb//wPLrB3boT2lkTQ2lDTllvdJIPTY7OIOU2J6qQ+hgzakaOmsaSidQ3+2qJe+G7zpASMNlqfjfHeEGLbhIedWjUuqhVRjkKFafE/IzBBCcRAQnqwhOhh/tZGDNVO0MGFRkK5QNcIZLfnvuJs8Qk4Cr1Duk3V/krN9+PYqy5ucorDIns7yHAdhFJkYxbKVrmp1kG5QtsOkf/PatFU6Cld5oN6q86bev3pzdzipzJ7A6JkgH2H0gFMQRy3tR4ZCyXvuR387f5B2ooMcqF7mIHn+lst82hHtgdMzPtXw2XBc8dPAoVMkvgeRnOfDhWpEGLa5y8i9jBelIfxFydMbvYxYryk/N+5gLqT6kRybzmrx0wLglSFbm/S8ednrpyY9SZ5YUjajPwu0NX2ZhHfVpKG1YJwMMyWxotV2kxvgfXzz5k++dPKX59k8I0jLvmEYFOD8cQ5gG3NYkK/e/G/XMT9nF9+7fs2mFjPyWhi95X++FC13tWy5M/0qml7VvwgRkb4WyZYWfy1A/7nJWMKW9CfV4urJ5UpCNzd4zCKBqV17aaSX+NQFpaSAOJ8cmLPENyOuUBP76poRQX1BVYdMb3lTlB80fTrloX5nj/zeW/7rln/H2qWsyi8QXP/7oZ+fW/vbKneP3LLzyztXU42/95ccfPKFfHftg3TPnOx/anvgvYbLyYHcRAAA=";
}
