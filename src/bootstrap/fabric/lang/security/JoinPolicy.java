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
 * Abstract class representing the join of policies. All the policies should be
 * of the same kind, either all IntegPolicies or all ConfPolicies. This code is
 * mostly copied from Jif.
 */
public interface JoinPolicy
  extends fabric.lang.security.Policy, fabric.lang.security.AbstractPolicy {
    public fabric.util.Set get$components();
    
    public fabric.util.Set set$components(fabric.util.Set val);
    
    public fabric.lang.security.JoinPolicy fabric$lang$security$JoinPolicy$(
      fabric.util.Set policies);
    
    public fabric.util.Set joinComponents();
    
    public boolean relabelsTo(fabric.lang.security.Policy pol, java.util.Set s);
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public java.lang.String toString();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.JoinPolicy {
        public fabric.util.Set get$components() {
            return ((fabric.lang.security.JoinPolicy._Impl) fetch()).
              get$components();
        }
        
        public fabric.util.Set set$components(fabric.util.Set val) {
            return ((fabric.lang.security.JoinPolicy._Impl) fetch()).
              set$components(val);
        }
        
        public fabric.lang.security.JoinPolicy fabric$lang$security$JoinPolicy$(
          fabric.util.Set arg1) {
            return ((fabric.lang.security.JoinPolicy) fetch()).
              fabric$lang$security$JoinPolicy$(arg1);
        }
        
        public fabric.util.Set joinComponents() {
            return ((fabric.lang.security.JoinPolicy) fetch()).joinComponents();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.JoinPolicy) fetch()).relabelsTo(arg1,
                                                                          arg2);
        }
        
        public _Proxy(JoinPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.JoinPolicy {
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
        
        public fabric.lang.security.JoinPolicy fabric$lang$security$JoinPolicy$(
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
            return (fabric.lang.security.JoinPolicy) this.$getProxy();
        }
        
        public fabric.util.Set joinComponents() {
            return this.get$components();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy pol,
                                  java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.lang.security.JoinPolicy)
                                this.$getProxy()),
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(pol)) ||
                  this.equals(pol))
                return true;
            java.util.Set temp = new java.util.HashSet();
            boolean sat = true;
            for (fabric.util.Iterator i = this.get$components().iterator();
                 i.hasNext(); ) {
                fabric.lang.security.Policy Ci =
                  (fabric.lang.security.Policy)
                    fabric.lang.Object._Proxy.$getProxy(i.next());
                if (!fabric.lang.security.LabelUtil._Impl.relabelsTo(Ci, pol,
                                                                     temp)) {
                    sat = false;
                    break;
                }
            }
            if (sat) {
                s.addAll(temp);
                return true;
            }
            temp.clear();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        pol)) instanceof fabric.lang.security.MeetPolicy) {
                fabric.lang.security.MeetPolicy mp =
                  (fabric.lang.security.MeetPolicy)
                    fabric.lang.Object._Proxy.$getProxy(pol);
                sat = true;
                for (fabric.util.Iterator i = mp.meetComponents().iterator();
                     i.hasNext(); ) {
                    fabric.lang.security.Policy Di =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(i.next());
                    if (!fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.JoinPolicy)
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
                    temp.clear();
                    fabric.lang.security.Policy Di =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(i.next());
                    if (fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.JoinPolicy)
                                       this.$getProxy(), Di, temp)) {
                        s.addAll(temp);
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
                      $unwrap(o)) instanceof fabric.lang.security.JoinPolicy) {
                fabric.lang.security.JoinPolicy that =
                  (fabric.lang.security.JoinPolicy)
                    fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.lang.Object._Proxy.
                  idEquals((fabric.lang.security.JoinPolicy) this.$getProxy(),
                           that) ||
                  this.joinComponents().equals(that.joinComponents());
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
                if (iter.hasNext()) str += "; ";
            }
            return str;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.JoinPolicy._Proxy(this);
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
            fabric.lang.security.JoinPolicy._Impl src =
              (fabric.lang.security.JoinPolicy._Impl) other;
            this.components = src.components;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.JoinPolicy._Static {
            public _Proxy(fabric.lang.security.JoinPolicy._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.JoinPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  JoinPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.JoinPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.JoinPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.JoinPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.JoinPolicy._Static {
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
                return new fabric.lang.security.JoinPolicy._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, -26, 36, 47, 38,
    -104, -116, 102, 53, -6, -7, 54, 75, 87, 121, -63, -54, 66, -113, 103, -49,
    30, -59, -14, -42, 103, -79, 47, -66, 20, -18, 12 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
