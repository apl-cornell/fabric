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
    
    public static final byte[] $classHash = new byte[] { 8, -91, -40, -36, -124,
    -20, 85, 53, 97, 64, -83, 100, 1, 55, -106, -22, -114, -9, -69, 6, -69, 86,
    -105, -116, -6, -104, 88, 115, 92, -73, -16, -46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
