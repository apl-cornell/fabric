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
    
    public static final byte[] $classHash = new byte[] { -74, -107, 34, 104, 8,
    45, 11, -22, -11, -80, -32, 78, 31, 91, 11, 28, -128, -109, 93, -57, 56,
    -124, 56, -11, 79, -127, -100, -90, -116, -74, 21, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
