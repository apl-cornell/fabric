package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Iterator;
import fabric.util.Map;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface FromDisjunctProof extends fabric.lang.security.ActsForProof {
    public fabric.util.Map get$disjunctProofs();
    
    public fabric.util.Map set$disjunctProofs(fabric.util.Map val);
    
    public fabric.lang.security.FromDisjunctProof
      fabric$lang$security$FromDisjunctProof$(
      fabric.lang.security.DisjunctivePrincipal actor,
      fabric.lang.security.Principal granter, fabric.util.Map disjunctProofs);
    
    public fabric.util.Map getDisjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.FromDisjunctProof {
        public fabric.util.Map get$disjunctProofs() {
            return ((fabric.lang.security.FromDisjunctProof._Impl) fetch()).
              get$disjunctProofs();
        }
        
        public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
            return ((fabric.lang.security.FromDisjunctProof._Impl) fetch()).
              set$disjunctProofs(val);
        }
        
        public fabric.lang.security.FromDisjunctProof
          fabric$lang$security$FromDisjunctProof$(
          fabric.lang.security.DisjunctivePrincipal arg1,
          fabric.lang.security.Principal arg2, fabric.util.Map arg3) {
            return ((fabric.lang.security.FromDisjunctProof) fetch()).
              fabric$lang$security$FromDisjunctProof$(arg1, arg2, arg3);
        }
        
        public fabric.util.Map getDisjunctProofs() {
            return ((fabric.lang.security.FromDisjunctProof) fetch()).
              getDisjunctProofs();
        }
        
        public _Proxy(FromDisjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.FromDisjunctProof {
        public fabric.util.Map get$disjunctProofs() {
            return this.disjunctProofs;
        }
        
        public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.disjunctProofs = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map disjunctProofs;
        
        public fabric.lang.security.FromDisjunctProof
          fabric$lang$security$FromDisjunctProof$(
          fabric.lang.security.DisjunctivePrincipal actor,
          fabric.lang.security.Principal granter,
          fabric.util.Map disjunctProofs) {
            fabric$lang$security$ActsForProof$(actor, granter);
            this.set$disjunctProofs(disjunctProofs);
            return (fabric.lang.security.FromDisjunctProof) this.$getProxy();
        }
        
        public fabric.util.Map getDisjunctProofs() {
            return this.get$disjunctProofs();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            fabric.lang.security.DisjunctivePrincipal dp =
              (fabric.lang.security.DisjunctivePrincipal)
                fabric.lang.Object._Proxy.$getProxy(getActor());
            for (fabric.util.Iterator iter = dp.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal disjunct =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof pr =
                  (fabric.lang.security.ActsForProof)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.getDisjunctProofs(
                                                       ).get(disjunct));
                pr.gatherDelegationDependencies(s);
            }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.FromDisjunctProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.FromDisjunctProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.disjunctProofs, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.disjunctProofs = (fabric.util.Map)
                                    $readRef(fabric.util.Map._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.FromDisjunctProof._Impl src =
              (fabric.lang.security.FromDisjunctProof._Impl) other;
            this.disjunctProofs = src.disjunctProofs;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.FromDisjunctProof._Static {
            public _Proxy(fabric.lang.security.FromDisjunctProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.FromDisjunctProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  FromDisjunctProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    lang.
                    security.
                    FromDisjunctProof.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.FromDisjunctProof._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.FromDisjunctProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.FromDisjunctProof._Static {
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
                return new fabric.lang.security.FromDisjunctProof._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -49, -82, -18, -76,
    -105, 58, -56, -69, -91, -40, -82, -20, 31, -115, 13, 87, -63, -124, -92,
    20, 81, -108, 112, -79, -98, 92, -49, 104, 85, 88, -92, 75 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYa2xcxRWeXW9sr2PHjzghMYljO9tQ57GrUP4QU2iywWSbTePaMW2dgjt77+x64tl7L3NnnXUgLVRFiZCI2mICqMW/jKDgBrUq4geKRKU+gqiKWlWUSqXkDwIUUikqtAi1hTMzd/fevbt2+dOV5uGZc2bOnMd3zvXyVbTG5Wgoj3OUJcW8Q9zkKM5lsmOYu8RMM+y6x2B12lgby5x/92mzP4qiWdRuYMu2qIHZtOUKtC57As/hlEVEanI8M3IcxQ3JeAi7MwJFjx8oczTg2Gy+wGzhXVJ3/qO7UguP3d318ybUOYU6qTUhsKBG2rYEKYsp1F4kxRzh7n7TJOYU6rYIMScIp5jRU0BoW1Oox6UFC4sSJ+44cW02Jwl73JJDuLqzsijFt0FsXjKEzUH8Li1+SVCWylJXjGRRc54SZrr3oG+jWBatyTNcAMKN2corUurE1KhcB/I2CmLyPDZIhSU2Sy1ToG1hjuqLE4eBAFhbikTM2NWrYhaGBdSjRWLYKqQmBKdWAUjX2CW4RaC+FQ8FolYHG7O4QKYF2hSmG9NbQBVXapEsAm0Ik6mTwGZ9IZsFrHX1K7ecu9c6ZEVRBGQ2icGk/K3A1B9iGid5wollEM3YvjN7Hm+8eDaKEBBvCBFrmhfvu/al3f0vX9I01zegOZo7QQwxbSzl1v1hS3r45iYpRqtju1S6Qs3LlVXHvJ2RsgPevrF6otxMVjZfHv/NN+5/llyJorYMajZsViqCV3UbdtGhjPA7iEU4FsTMoDixzLTaz6AWmGepRfTq0XzeJSKDYkwtNdvqb1BRHo6QKmqBObXydmXuYDGj5mUHIdQBDUWg7UQo9gGMbdCeEWgqNWMXSSrHSuQkuHcKGsHcmElB3HJq7DFsZz7lciPFS5agQKnXtf+4xChxKuZTo9wuHqTuiZJliDFu2/kkSOX8X08vy7d1nYxEQO3bDNskOeyCDT1/OjDGIGQO2cwkfNpg5y5m0PqLTyifiss4cMGXldYi4AdbwggS5F0oHbj92oXpV7U/Sl5PqQLt0NImpbTJirTJOmlBwHYZc0lAsSSg2HKknEwvZp5TrtXsqhisntkOZ+5zGBZ5mxfLKBJRD+xV/MqnwCNmAWkATNqHJ+768rfODjWBMzsnY9K+QJoIh5YPSBmYYYiXaaPzzLv/fP78adsPMoESdbFfzyljdyisLW4bxARs9I/fOYBfmL54OhGVuBMHSBQYnBbwpT98R00Mj1TwUGpjTRatlTrATG5VQKxNzHD7pL+ivGCd7Hq0Q0hlhQRUUPrFCefJN37/3hdUkqmgbmcAnieIGAlEujysU8V0t6/7Y5wQoHvz8bFHHr165rhSPFBsb3RhQvZpiHAMoW3zBy/d85e3/rb0p6hvLIGanVKOUaOs3tL9Cfwi0P4rmwxXuSBHAO20BxUDVaxw5M07fNkANRggF4juJiatom3SPMU5RqSn/Lvzc3tfeP9clzY3gxWtPI52/+8D/PXNB9D9r979r351TMSQWcvXn0+moXC9f/J+zvG8lKP8wB+3PvFb/CR4PgCZS08RhU1I6QMpA96odLFH9XtDezfJbkhra0vV4cNpYVTmV98Xp1LLP+5L33pFx37VF+UZgw1i/04cCJMbny1+GB1q/nUUtUyhLpXasSXuxABn4AZTkJzdtLeYRR01+7WJVmeVkWqsbQnHQeDacBT4mANzSS3nbdrxteOAIrqkkrZDWwvtY298R+6ud2TfW44gNdmnWLarfofshpUiowK1OJzOgWcJFKfFYklI26tbdkEZZtYAmuLeAPHjAaCy8hGsLuhTYVhe4Ro53SnggdTCrFwVX/3avaz0tDf+KCB+wOaoDEbfulIBoYqfpe8uLJpHn9qr03xPbVK+3SoVf/r6f36XfPzyKw1APS5sZw8jc4QF7ozBlYN1lewRVV/57nL5ytab07NvF/S120Iihql/cmT5lTt2GD+MoqaqX9QVdbVMI7Xe0MYJ1KTWsRqfGKgqNSqVtRnaOgiU53QNEBkO+oRGzIaW0hG3KxSNTWqvqWL/4YYJsJL86BwZg9xmUAerGq6vwtbfkK1Kq8g2h1OhXNyv+slVIOIu2Y0JdIO+IiGvSFSuSNSl5oSvgSP1euuF+W3e+Pk6vclufBVRcqvsmbKDOrq7QERtraCeGRIoLvnkEZtAkEe8cfYzGjKiQq5c+7pW75AT3miGQ80XO+IBrWe7Dh/WAQIrpopLUzEbvtrKSha2ytsV9OYBAwtQoBJ+kDBSUN9ZB4kDdS4UA5S4DXAd/KNIpVPpUp6cXXjok+S5BR3C+ptoe91nSZBHfxcpGTqUc0sgGVztFsUx+s7zp1965vSZqCd/RqDYnE3NRkbaCm0QAuSaN765gpFkR+tNIln+6o2vr2ySoDa/s8reA7I7JdDaBLWoyOIcYFrFjj3BGNTpqXHclcFL6+JGJvjrGxTd3geikf4VWXr78O4NKxTcm+o+2T2+C4udrdctTv5ZFY7Vj7841GX5EmPBRBiYNzuc5Kl6cVynRUcNDwnU2whpBGqtTNWTz2ryh0FRAXKwshyCFN+Hik1TyL9+4Ke7vhp4G2wIb/tBwaM2V+pT5OrIvhKX/8ZY/sd1HzW3HrusqkMw38BrF/7+i8f2XXrpqTcuvL/t4Y6v/fJ7S71fXXB+tvjN12Ymv750+FOKQuwiXhEAAA==";
}
