package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.lang.security.Principal;
import fabric.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * This code is mostly copied from Jif.
 */
public interface WriterPolicy
  extends fabric.lang.security.IntegPolicy, fabric.lang.security.AbstractPolicy
{
    public fabric.lang.security.Principal get$owner();
    
    public fabric.lang.security.Principal set$owner(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.Principal get$writer();
    
    public fabric.lang.security.Principal set$writer(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.WriterPolicy fabric$lang$security$WriterPolicy$(
      fabric.lang.security.Principal owner,
      fabric.lang.security.Principal writer);
    
    public fabric.lang.security.Principal owner();
    
    public fabric.lang.security.Principal writer();
    
    public boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      boolean simplify);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      boolean simplify);
    
    public fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.security.IntegPolicy meet(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.WriterPolicy {
        public fabric.lang.security.Principal get$owner() {
            return ((fabric.lang.security.WriterPolicy._Impl) fetch()).
              get$owner();
        }
        
        public fabric.lang.security.Principal set$owner(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.WriterPolicy._Impl) fetch()).
              set$owner(val);
        }
        
        public fabric.lang.security.Principal get$writer() {
            return ((fabric.lang.security.WriterPolicy._Impl) fetch()).
              get$writer();
        }
        
        public fabric.lang.security.Principal set$writer(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.WriterPolicy._Impl) fetch()).
              set$writer(val);
        }
        
        public fabric.lang.security.WriterPolicy
          fabric$lang$security$WriterPolicy$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.WriterPolicy) fetch()).
              fabric$lang$security$WriterPolicy$(arg1, arg2);
        }
        
        public fabric.lang.security.Principal owner() {
            return ((fabric.lang.security.WriterPolicy) fetch()).owner();
        }
        
        public fabric.lang.security.Principal writer() {
            return ((fabric.lang.security.WriterPolicy) fetch()).writer();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.WriterPolicy) fetch()).relabelsTo(
                                                                   arg1, arg2);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.WriterPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.WriterPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.WriterPolicy) fetch()).join(arg1,
                                                                      arg2);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.WriterPolicy) fetch()).meet(arg1,
                                                                      arg2);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.WriterPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.WriterPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.WriterPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.WriterPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4);
        }
        
        public _Proxy(WriterPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.WriterPolicy {
        public fabric.lang.security.Principal get$owner() { return this.owner; }
        
        public fabric.lang.security.Principal set$owner(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal owner;
        
        public fabric.lang.security.Principal get$writer() {
            return this.writer;
        }
        
        public fabric.lang.security.Principal set$writer(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.writer = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal writer;
        
        public fabric.lang.security.WriterPolicy
          fabric$lang$security$WriterPolicy$(
          fabric.lang.security.Principal owner,
          fabric.lang.security.Principal writer) {
            this.set$owner(owner);
            this.set$writer(writer);
            fabric$lang$security$AbstractPolicy$();
            return (fabric.lang.security.WriterPolicy) this.$getProxy();
        }
        
        public fabric.lang.security.Principal owner() {
            return this.get$owner();
        }
        
        public fabric.lang.security.Principal writer() {
            return this.get$writer();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy p,
                                  java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.lang.security.WriterPolicy)
                                this.$getProxy()),
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(p)) ||
                  this.equals(p))
                return true;
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.JoinIntegPolicy) {
                fabric.lang.security.JoinPolicy jp =
                  (fabric.lang.security.JoinPolicy)
                    fabric.lang.Object._Proxy.$getProxy(p);
                for (fabric.util.Iterator iter = jp.joinComponents().iterator();
                     iter.hasNext(); ) {
                    fabric.lang.security.Policy pi =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.WriterPolicy)
                                       this.$getProxy(), pi, s))
                        return true;
                }
                return false;
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             p)) instanceof fabric.lang.security.MeetIntegPolicy) {
                fabric.lang.security.MeetPolicy mp =
                  (fabric.lang.security.MeetPolicy)
                    fabric.lang.Object._Proxy.$getProxy(p);
                java.util.Set temp = new java.util.HashSet();
                for (fabric.util.Iterator iter = mp.meetComponents().iterator();
                     iter.hasNext(); ) {
                    fabric.lang.security.Policy pi =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (!fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.WriterPolicy)
                                       this.$getProxy(), pi, temp))
                        return false;
                }
                s.addAll(temp);
                return true;
            }
            else if (!(fabric.lang.Object._Proxy.
                         $getProxy((java.lang.Object)
                                     fabric.lang.WrappedJavaInlineable.$unwrap(p)) instanceof fabric.lang.security.WriterPolicy))
                return false;
            fabric.lang.security.WriterPolicy pp =
              (fabric.lang.security.WriterPolicy)
                fabric.lang.Object._Proxy.$getProxy(p);
            fabric.lang.security.ActsForProof ownersProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         this.get$owner(),
                                                         pp.get$owner());
            if (fabric.lang.Object._Proxy.idEquals(ownersProof, null)) {
                return false;
            }
            fabric.lang.security.ActsForProof writerWriterProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         this.get$writer(),
                                                         pp.get$writer());
            if (!fabric.lang.Object._Proxy.idEquals(writerWriterProof, null)) {
                ownersProof.gatherDelegationDependencies(s);
                writerWriterProof.gatherDelegationDependencies(s);
                return true;
            }
            fabric.lang.security.ActsForProof writerOwnerProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         this.get$writer(),
                                                         pp.get$owner());
            if (!fabric.lang.Object._Proxy.idEquals(writerOwnerProof, null)) {
                ownersProof.gatherDelegationDependencies(s);
                writerOwnerProof.gatherDelegationDependencies(s);
                return true;
            }
            return false;
        }
        
        public int hashCode() {
            return (fabric.lang.Object._Proxy.idEquals(this.get$owner(), null)
                      ? 0
                      : ((java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.$unwrap(
                                                               this.get$owner(
                                                                      ))).
                      hashCode()) ^
              (fabric.lang.Object._Proxy.idEquals(this.get$writer(), null)
                 ? 0
                 : ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$writer())).
                 hashCode()) ^
              -124978;
        }
        
        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(
                                            (fabric.lang.security.WriterPolicy)
                                              this.$getProxy(), o)) return true;
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.
                        $unwrap(
                          o)) instanceof fabric.lang.security.WriterPolicy)) {
                return false;
            }
            fabric.lang.security.WriterPolicy policy =
              (fabric.lang.security.WriterPolicy)
                fabric.lang.Object._Proxy.$getProxy(o);
            if (fabric.lang.Object._Proxy.idEquals(this.get$owner(),
                                                   policy.get$owner()) ||
                  !fabric.lang.Object._Proxy.idEquals(this.get$owner(), null) &&
                  this.get$owner().equals(policy.get$owner()) &&
                  !fabric.lang.Object._Proxy.idEquals(policy.get$owner(),
                                                      null) &&
                  policy.get$owner().equals(this.get$owner())) {
                return fabric.lang.Object._Proxy.idEquals(
                                                   this.get$writer(),
                                                   policy.get$writer()) ||
                  !fabric.lang.Object._Proxy.idEquals(this.get$writer(),
                                                      null) &&
                  this.get$writer().equals(policy.get$writer()) &&
                  !fabric.lang.Object._Proxy.idEquals(policy.get$writer(),
                                                      null) &&
                  policy.get$writer().equals(this.get$writer());
            }
            return false;
        }
        
        public java.lang.String toString() {
            java.lang.String str =
              fabric.lang.security.PrincipalUtil._Impl.toString(
                                                         this.get$owner()) +
            "!: ";
            if (!fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(
                                                            this.get$writer()))
                str +=
                  fabric.lang.security.PrincipalUtil._Impl.toString(
                                                             this.get$writer());
            return str;
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s) {
            return join(store, p, s, true);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s) {
            return meet(store, p, s, true);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p) {
            return join(store, p, true);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p) {
            return meet(store, p, true);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.WriterPolicy) this.$getProxy(),
                   p, simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.WriterPolicy) this.$getProxy(),
                   p, simplify);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.WriterPolicy) this.$getProxy(),
                   p, s, simplify);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.WriterPolicy) this.$getProxy(),
                   p, s, simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.WriterPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.WriterPolicy._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.writer, refTypes, out, intraStoreRefs,
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
            this.owner = (fabric.lang.security.Principal)
                           $readRef(fabric.lang.security.Principal._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.writer =
              (fabric.lang.security.Principal)
                $readRef(fabric.lang.security.Principal._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.WriterPolicy._Impl src =
              (fabric.lang.security.WriterPolicy._Impl) other;
            this.owner = src.owner;
            this.writer = src.writer;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.WriterPolicy._Static {
            public _Proxy(fabric.lang.security.WriterPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.WriterPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  WriterPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.WriterPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.WriterPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.WriterPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.WriterPolicy._Static {
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
                return new fabric.lang.security.WriterPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 23, 94, -50, -93, -113,
    -94, -116, 8, -13, 16, -45, 110, 115, 29, -58, 9, 60, -68, -122, -21, 28,
    10, 30, 74, -27, 85, 108, 81, 56, -11, 71, -38 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
