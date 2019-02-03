package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.util.*;

/**
 * Abstract class representing the meet of policies. All the policies should be
 * of the same kind, either all IntegPolicies or all ConfPolicies. This code is
 * mostly copied from Jif.
 */
public interface MeetPolicy
  extends fabric.lang.security.Policy, fabric.lang.security.AbstractPolicy {
    public fabric.util.Set get$components();
    
    public fabric.util.Set set$components(fabric.util.Set val);
    
    public fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
      fabric.util.Set policies);
    
    public fabric.util.Set meetComponents();
    
    public boolean relabelsTo(fabric.lang.security.Policy pol, java.util.Set s);
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public java.lang.String toString();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.MeetPolicy {
        public fabric.util.Set get$components() {
            return ((fabric.lang.security.MeetPolicy._Impl) fetch()).
              get$components();
        }
        
        public fabric.util.Set set$components(fabric.util.Set val) {
            return ((fabric.lang.security.MeetPolicy._Impl) fetch()).
              set$components(val);
        }
        
        public fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
          fabric.util.Set arg1) {
            return ((fabric.lang.security.MeetPolicy) fetch()).
              fabric$lang$security$MeetPolicy$(arg1);
        }
        
        public fabric.util.Set meetComponents() {
            return ((fabric.lang.security.MeetPolicy) fetch()).meetComponents();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.MeetPolicy) fetch()).relabelsTo(arg1,
                                                                          arg2);
        }
        
        public _Proxy(MeetPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.MeetPolicy {
        public fabric.util.Set get$components() { return this.components; }
        
        public fabric.util.Set set$components(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.components = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set components;
        
        public fabric.lang.security.MeetPolicy fabric$lang$security$MeetPolicy$(
          fabric.util.Set policies) {
            this.
              set$components(
                fabric.util.Collections._Impl.
                    unmodifiableSet(
                      $getStore(),
                      (fabric.util.LinkedHashSet)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.util.LinkedHashSet)
                             new fabric.util.LinkedHashSet._Impl(
                               this.$getStore()).$getProxy()).
                              fabric$util$LinkedHashSet$(policies))));
            fabric$lang$security$AbstractPolicy$();
            return (fabric.lang.security.MeetPolicy) this.$getProxy();
        }
        
        public fabric.util.Set meetComponents() {
            return this.get$components();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy pol,
                                  java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.lang.security.MeetPolicy)
                                this.$getProxy()),
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(pol)) ||
                  this.equals(pol))
                return true;
            for (fabric.util.Iterator i = this.get$components().iterator();
                 i.hasNext(); ) {
                fabric.lang.security.Policy Ci =
                  (fabric.lang.security.Policy)
                    fabric.lang.Object._Proxy.$getProxy(i.next());
                if (fabric.lang.security.LabelUtil._Impl.relabelsTo(Ci, pol,
                                                                    s)) {
                    return true;
                }
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        pol)) instanceof fabric.lang.security.MeetPolicy) {
                fabric.lang.security.MeetPolicy mp =
                  (fabric.lang.security.MeetPolicy)
                    fabric.lang.Object._Proxy.$getProxy(pol);
                boolean sat = true;
                java.util.Set temp = new java.util.HashSet();
                for (fabric.util.Iterator i = mp.meetComponents().iterator();
                     i.hasNext(); ) {
                    fabric.lang.security.Policy Di =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(i.next());
                    if (!fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.MeetPolicy)
                                       this.$getProxy(), Di, temp)) {
                        sat = false;
                        break;
                    }
                }
                if (sat) {
                    s.addAll(temp);
                    return true;
                }
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        pol)) instanceof fabric.lang.security.JoinPolicy) {
                fabric.lang.security.JoinPolicy jp =
                  (fabric.lang.security.JoinPolicy)
                    fabric.lang.Object._Proxy.$getProxy(pol);
                for (fabric.util.Iterator i = jp.joinComponents().iterator();
                     i.hasNext(); ) {
                    fabric.lang.security.Policy Di =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(i.next());
                    if (fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.MeetPolicy)
                                       this.$getProxy(), Di, s)) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.lang.security.MeetPolicy) {
                fabric.lang.security.MeetPolicy that =
                  (fabric.lang.security.MeetPolicy)
                    fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.lang.Object._Proxy.
                  idEquals((fabric.lang.security.MeetPolicy) this.$getProxy(),
                           that) ||
                  this.meetComponents().equals(that.meetComponents());
            }
            return false;
        }
        
        public final int hashCode() { return this.get$components().hashCode(); }
        
        public final java.lang.String toString() {
            java.lang.String str = "";
            for (fabric.util.Iterator iter = this.get$components().iterator();
                 iter.hasNext(); ) {
                str +=
                  ((fabric.lang.security.Policy)
                     fabric.lang.Object._Proxy.$getProxy(iter.next())).toString(
                                                                         );
                if (iter.hasNext()) str += " meet ";
            }
            return str;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.MeetPolicy._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.components, refTypes, out,
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
            this.components = (fabric.util.Set)
                                $readRef(fabric.util.Set._Proxy.class,
                                         (fabric.common.RefTypeEnum)
                                           refTypes.next(), in, store,
                                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.MeetPolicy._Impl src =
              (fabric.lang.security.MeetPolicy._Impl) other;
            this.components = src.components;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.MeetPolicy._Static {
            public _Proxy(fabric.lang.security.MeetPolicy._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.MeetPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  MeetPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.MeetPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.MeetPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.MeetPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.MeetPolicy._Static {
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
                return new fabric.lang.security.MeetPolicy._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 17, 35, 115, 34, 106,
    103, 109, 37, -75, -50, -89, -19, -69, -81, -9, 63, 113, 121, 55, -110, -27,
    -97, 61, -78, -76, -61, -125, -4, 43, 82, -128, 8 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYb2wcxRWfO9tnn3FytvMPnMRx7CNS4uROSStRMBTsU4KPXIhrO1Q4ImZub87eZG9nMzvrnAFXAYQSEPIHMIGIJpWoq9DgJipK1A8oElL/gaBIrRB/PtDmS1Ro6g+oaqlUWngzs3t7t3d2wwcs7czczJuZN+/93m/eeGERNdgMdedxVjcSfNoidmIPzqYzQ5jZJJcysG2PQu+4dlN9+tSn53KdYRTOoBYNm9TUNWyMmzZHKzOH8RROmoQnDwyn+w6iqCYmDmJ7kqPwwYEiQ10WNaYnDMrdTarWf6E3OffiodbX61BsDMV0c4RjrmspanJS5GOopUAKWcLs/lyO5MZQm0lIboQwHRv6IyBIzTHUbusTJuYOI/YwsakxJQTbbcciTO7pdQr1KajNHI1TBuq3KvUdrhvJjG7zvgyK5HVi5Oyj6EeoPoMa8gaeAMG1Ge8USblico/oB/FmHdRkeawRb0r9Ed3McbQpOKN04vheEICpjQXCJ2lpq3oTQwdqVyoZ2JxIjnCmmxMg2kAd2IWjjiUXBaEmC2tH8AQZ5+jmoNyQGgKpqDSLmMLRmqCYXAl81hHwWZm3Fu+/c/ZRc9AMoxDonCOaIfRvgkmdgUnDJE8YMTWiJrZsy5zCa6+cDCMEwmsCwkrmV499fs/2zjffUjLra8jszx4mGh/X5rMr/7ghtfX2OqFGk0VtXUCh4uTSq0PuSF/RArSvLa0oBhPe4JvDv3vw+HlyPYya0yiiUcMpAKraNFqwdIOwe4lJGOYkl0ZRYuZScjyNGqGd0U2ievfn8zbhaVRvyK4Ilb/BRHlYQpioEdq6made28J8UraLFkKoFT4Ugu8hhJrfhboFfg5wNJKcpAWSzBoOOQbwTsJHMNMmkxC3TNd2aNSaTtpMSzLH5DpIqn6FH5toDtP5dHIfIXyIGro2nQB1rG9n2aI4TeuxUAgMvUmjOZLFNnjNRdDAkAFBMkiNHGHjmjF7JY1WXTktURQVyLcBvdJOIfD8hiBnlM+dcwZ2f35h/B2FQDHXNSMEnVIzIdRMeGomfDVBsxYRXgkgrAQQ1kKomEidTb8mURSxZbiVFmuBxe6wDMzzlBWKKBSSJ1st50v4gPOPAKkAb7RsHXnovodPdtcBbq1j9cKVIBoPRpHPPWloYQiNcS124tN/XTw1Q/144iheFebVM0WYdgfNxKhGckCD/vLbuvDl8Ssz8bCgmCiwH8eAT6CSzuAeFeHa51GfsEZDBt0kbIANMeTxVTOfZPSY3yPdv1IU7QoJwlgBBSVr3jVinfnovc++I+8Tj2BjZUw8QnhfWVCLxWIyfNt8248yQkDuk5eGnn9h8cRBaXiQ6Km1YVyUKQhmDFFM2VNvHf34L3+efz/sO4ujiOVkASFFeZa2r+AvBN//xCciU3SIGvg55bJCV4kWLLHzFl83IAgDSApUt+MHzALN6XkdZw0ikPJl7Nadl/8+26rcbUCPMh5D2///An7/LQPo+DuHvuiUy4Q0cUH59vPFFOut8lfuZwxPCz2Kj/9p4+nf4zOAfOAsW3+ESBpC0h5IOnCXtMUOWe4MjH1XFN3KWhtKgA/eAHvEVepjcSy58OOO1Pevq6AvYVGssblG0D+Ay8Jk1/nCP8Pdkd+GUeMYapW3ODb5AxgIDGAwBvewnXI7M2hFxXjlnaoukL5SrG0IxkHZtsEo8MkG2kJatJsV8BVwPCbfBN8KMMoZt54Vo6ssUa4uhpBs3CGn9Mhyiyi2SkOGOWq0mD4FyOIoqhcKDhe+l7v0ctQsbiVqEpPbcuYaiB2X9aSHwRqiu0OGYHGJLURzG0dNOAu0gjVeLGkv/2Lu/dPv1rvKtK9wuavA+pq0qyhXKlMEcGxcKqeQ+dD8E3Nnc/t/tlPd/O2V9/Ru0yn84oP/vpt46erbNVg/yqm1wyBTxChTLgJbbq5KbvfJlMuH1dXrG29PHbk2obbdFFAxKP3zfQtv37tFey6M6kr4qcrzKif1VaKmmRFIU83RCux0lawfFtbvUx4ILbr1pXLsKGat6VUVmb2BqA0pV4mf/VLgh8uE9YOiGOaoSzk0Lhwa9xwa9+/RuK/G/pLyUbHMRvjWIVTX69brblB5qeC2YqUlmtxF1rp1axCHtU+BlxnTRHEQHi4FOEyqFErSPLXO8z341sPWT7j1yBLnEcWhau3FlGG3ziytfdgPy0EvqFb4rA0xLXtvAayLpMSg8P4qyu0PL3NWuRWQCIDOwFmIjlFag6SHmF6Ae3bKTdPJyblnvkrMzqk4U2+ZnqrnRPkc9Z6RO66QCBTRvnm5XeSMPX+9OPPGqzMnwq62e4H3spQaBJu1HLEZvkEw6h/c+vI3c4SYcsmtLyztiFAlsbWXE5u6OTxHVGaHUoOZZXzxpCiKkGeQow5WPMUDx4x58fMDOPIv3fqnNxg/HqU35HUTsFFpgJXuYq+49akbi6NnlxmTF9pTcIVMwlM/Bcm+lGKu/0XlcFSnmzUP2QXfAdDjP279t290SFE8XeOEYqXP3PqTGzvh6WXGXhbF83BCTtU73ANFqwxMCYmygWpIQOD5jCnysPU1HkXuk11L/YbMX9u7fc0SD6Kbq/6J4s67cDbWtO7sgQ9lfl96jkchfc47hlGer5S1IxYjeV2eMqqyF0tW8xytrnWVgxG8pjzrK0r8HId3gS/OIZ/FrjVcifMAeCUhfr0m/dHhF55Fe2rmD/1uclKeR8jSYeJfSwv/WPfvSNPoVZnGC1C19djdhycKt15+79ziGxe/uPvo9G3PXfvJXa9f+vWTX/YOH2/6Gt6VDPDyEgAA";
}
