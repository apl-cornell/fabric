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
    
    public static final byte[] $classHash = new byte[] { -53, -118, -113, 108,
    -67, -117, -90, 82, 30, -63, -39, -4, 61, 69, -21, 118, 105, -78, -50, -85,
    22, -25, -93, 116, 17, 90, -45, -52, 55, 125, 0, -75 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
