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
    
    public static final byte[] $classHash = new byte[] { 18, -127, 85, -7, 98,
    -92, -114, -107, 54, -60, 92, 52, 26, -128, 119, -65, 26, -107, -23, -58,
    -86, 32, 97, -118, -65, 48, 2, 72, 56, 20, 1, -15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
