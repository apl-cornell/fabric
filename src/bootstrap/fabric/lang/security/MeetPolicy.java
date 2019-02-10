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
    
    public static final byte[] $classHash = new byte[] { 69, -2, 9, 127, 16,
    -54, -65, -86, -117, 15, -11, -51, -100, 50, 51, -78, 121, 53, 34, 19, -95,
    4, -107, 28, -33, 85, 127, 124, 28, 108, -93, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
