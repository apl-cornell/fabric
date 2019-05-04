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
    
    public static final byte[] $classHash = new byte[] { 121, 88, -67, 21, 74,
    52, 126, -102, 92, -118, 92, 73, 117, -40, -62, 72, -65, -70, 117, -84, -72,
    -126, -102, 75, 108, 49, 41, -49, 15, -6, -102, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
