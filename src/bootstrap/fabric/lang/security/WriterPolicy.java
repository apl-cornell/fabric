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
    
    public static final byte[] $classHash = new byte[] { -80, 52, -109, -31, 73,
    -48, 21, -5, -97, -63, 41, 82, 87, -121, -48, 109, -17, -20, 22, 123, 68,
    -14, -60, -88, -28, 108, 90, 117, -119, 25, -118, -91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
