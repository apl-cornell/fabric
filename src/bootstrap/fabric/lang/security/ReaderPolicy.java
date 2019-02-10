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
import java.util.HashSet;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface ReaderPolicy
  extends fabric.lang.security.ConfPolicy, fabric.lang.security.AbstractPolicy {
    public fabric.lang.security.Principal get$owner();
    
    public fabric.lang.security.Principal set$owner(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.Principal get$reader();
    
    public fabric.lang.security.Principal set$reader(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.ReaderPolicy fabric$lang$security$ReaderPolicy$(
      fabric.lang.security.Principal owner,
      fabric.lang.security.Principal reader);
    
    public fabric.lang.security.Principal owner();
    
    public fabric.lang.security.Principal reader();
    
    public boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      boolean simplify);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      boolean simplify);
    
    public fabric.lang.security.ConfPolicy join(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.security.ConfPolicy meet(
      fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
      java.util.Set s, boolean simplify);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPolicy._Proxy
      implements fabric.lang.security.ReaderPolicy {
        public fabric.lang.security.Principal get$owner() {
            return ((fabric.lang.security.ReaderPolicy._Impl) fetch()).
              get$owner();
        }
        
        public fabric.lang.security.Principal set$owner(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ReaderPolicy._Impl) fetch()).
              set$owner(val);
        }
        
        public fabric.lang.security.Principal get$reader() {
            return ((fabric.lang.security.ReaderPolicy._Impl) fetch()).
              get$reader();
        }
        
        public fabric.lang.security.Principal set$reader(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ReaderPolicy._Impl) fetch()).
              set$reader(val);
        }
        
        public fabric.lang.security.ReaderPolicy
          fabric$lang$security$ReaderPolicy$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).
              fabric$lang$security$ReaderPolicy$(arg1, arg2);
        }
        
        public fabric.lang.security.Principal owner() {
            return ((fabric.lang.security.ReaderPolicy) fetch()).owner();
        }
        
        public fabric.lang.security.Principal reader() {
            return ((fabric.lang.security.ReaderPolicy) fetch()).reader();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).relabelsTo(
                                                                   arg1, arg2);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).join(arg1,
                                                                      arg2);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).meet(arg1,
                                                                      arg2);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).join(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.ReaderPolicy) fetch()).meet(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4);
        }
        
        public _Proxy(ReaderPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.security.AbstractPolicy._Impl
      implements fabric.lang.security.ReaderPolicy {
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
        
        public fabric.lang.security.Principal get$reader() {
            return this.reader;
        }
        
        public fabric.lang.security.Principal set$reader(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.reader = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal reader;
        
        public fabric.lang.security.ReaderPolicy
          fabric$lang$security$ReaderPolicy$(
          fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader) {
            this.set$owner(owner);
            this.set$reader(reader);
            fabric$lang$security$AbstractPolicy$();
            return (fabric.lang.security.ReaderPolicy) this.$getProxy();
        }
        
        public fabric.lang.security.Principal owner() {
            return this.get$owner();
        }
        
        public fabric.lang.security.Principal reader() {
            return this.get$reader();
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy p,
                                  java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  idEquals(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.lang.security.ReaderPolicy)
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
                        p)) instanceof fabric.lang.security.JoinConfPolicy) {
                fabric.lang.security.JoinPolicy jp =
                  (fabric.lang.security.JoinPolicy)
                    fabric.lang.Object._Proxy.$getProxy(p);
                for (fabric.util.Iterator iter = jp.joinComponents().iterator();
                     iter.hasNext(); ) {
                    fabric.lang.security.Policy pi =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (fabric.lang.security.LabelUtil._Impl.
                          relabelsTo((fabric.lang.security.ReaderPolicy)
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
                             p)) instanceof fabric.lang.security.MeetConfPolicy) {
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
                          relabelsTo((fabric.lang.security.ReaderPolicy)
                                       this.$getProxy(), pi, temp))
                        return false;
                }
                s.addAll(temp);
                return true;
            }
            else if (!(fabric.lang.Object._Proxy.
                         $getProxy((java.lang.Object)
                                     fabric.lang.WrappedJavaInlineable.$unwrap(p)) instanceof fabric.lang.security.ReaderPolicy))
                return false;
            fabric.lang.security.ReaderPolicy pp =
              (fabric.lang.security.ReaderPolicy)
                fabric.lang.Object._Proxy.$getProxy(p);
            fabric.lang.security.ActsForProof ownersProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         pp.get$owner(),
                                                         this.get$owner());
            if (fabric.lang.Object._Proxy.idEquals(ownersProof, null)) {
                return false;
            }
            fabric.lang.security.ActsForProof readerReaderProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         pp.get$reader(),
                                                         this.get$reader());
            if (!fabric.lang.Object._Proxy.idEquals(readerReaderProof, null)) {
                ownersProof.gatherDelegationDependencies(s);
                readerReaderProof.gatherDelegationDependencies(s);
                return true;
            }
            fabric.lang.security.ActsForProof readerOwnerProof =
              fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                         localStore,
                                                         pp.get$reader(),
                                                         this.get$owner());
            if (!fabric.lang.Object._Proxy.idEquals(readerOwnerProof, null)) {
                ownersProof.gatherDelegationDependencies(s);
                readerOwnerProof.gatherDelegationDependencies(s);
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
              (fabric.lang.Object._Proxy.idEquals(this.get$reader(), null)
                 ? 0
                 : ((java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                          this.get$reader())).
                 hashCode()) ^
              4238;
        }
        
        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(
                                            (fabric.lang.security.ReaderPolicy)
                                              this.$getProxy(), o)) return true;
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.
                        $unwrap(
                          o)) instanceof fabric.lang.security.ReaderPolicy)) {
                return false;
            }
            fabric.lang.security.ReaderPolicy policy =
              (fabric.lang.security.ReaderPolicy)
                fabric.lang.Object._Proxy.$getProxy(o);
            if (fabric.lang.Object._Proxy.idEquals(this.get$owner(),
                                                   policy.get$owner()) ||
                  !fabric.lang.Object._Proxy.idEquals(this.get$owner(), null) &&
                  this.get$owner().equals(policy.get$owner()) &&
                  !fabric.lang.Object._Proxy.idEquals(policy.get$owner(),
                                                      null) &&
                  policy.get$owner().equals(this.get$owner())) {
                return fabric.lang.Object._Proxy.idEquals(
                                                   this.get$reader(),
                                                   policy.get$reader()) ||
                  !fabric.lang.Object._Proxy.idEquals(this.get$reader(),
                                                      null) &&
                  this.get$reader().equals(policy.get$reader()) &&
                  !fabric.lang.Object._Proxy.idEquals(policy.get$reader(),
                                                      null) &&
                  policy.get$reader().equals(this.get$reader());
            }
            return false;
        }
        
        public java.lang.String toString() {
            java.lang.String str =
              fabric.lang.security.PrincipalUtil._Impl.toString(
                                                         this.get$owner()) +
            ": ";
            if (!fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(
                                                            this.get$reader()))
                str +=
                  fabric.lang.security.PrincipalUtil._Impl.toString(
                                                             this.get$reader());
            return str;
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s) {
            return join(store, p, s, true);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s) {
            return meet(store, p, s, true);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p) {
            return join(store, p, true);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p) {
            return meet(store, p, true);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.ReaderPolicy) this.$getProxy(),
                   p, simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.ReaderPolicy) this.$getProxy(),
                   p, simplify);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.ReaderPolicy) this.$getProxy(),
                   p, s, simplify);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p,
          java.util.Set s, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.ReaderPolicy) this.$getProxy(),
                   p, s, simplify);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.ReaderPolicy) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ReaderPolicy._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.reader, refTypes, out, intraStoreRefs,
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
            this.reader =
              (fabric.lang.security.Principal)
                $readRef(fabric.lang.security.Principal._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ReaderPolicy._Impl src =
              (fabric.lang.security.ReaderPolicy._Impl) other;
            this.owner = src.owner;
            this.reader = src.reader;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ReaderPolicy._Static {
            public _Proxy(fabric.lang.security.ReaderPolicy._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ReaderPolicy._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ReaderPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.ReaderPolicy._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ReaderPolicy._Static._Impl.class);
                $instance = (fabric.lang.security.ReaderPolicy._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ReaderPolicy._Static {
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
                return new fabric.lang.security.ReaderPolicy._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 56, 27, -36, 90, -96,
    93, -16, 98, 84, -82, -80, -9, 77, -17, -2, -88, 0, -107, 86, 80, 74, 101,
    -39, 65, -23, 72, 8, 109, -73, -89, -7, 120 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
