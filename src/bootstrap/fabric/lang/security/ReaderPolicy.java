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
    
    public static final byte[] $classHash = new byte[] { -20, -32, 65, -14, 108,
    59, 19, -52, 85, 108, 40, 125, -31, 63, -67, 81, 66, 114, -22, -128, -38,
    -98, 1, 115, 37, -88, -23, -88, 4, -33, 89, 94 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
