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
    
    public static final byte[] $classHash = new byte[] { -9, 117, -126, 55, -39,
    -82, 6, -114, -96, 31, 84, 55, 19, 84, -5, 13, 60, 73, -116, 22, 33, -84,
    -69, 5, -96, 18, -97, -45, -14, -12, -74, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
