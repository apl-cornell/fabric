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
    
    public static final byte[] $classHash = new byte[] { -86, -92, -103, -5,
    103, -69, 76, 95, -123, -32, -31, 37, 8, 79, -82, -106, -109, 18, 67, -56,
    124, 87, 79, -55, -103, -26, -38, -18, -120, 101, -42, -95 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
