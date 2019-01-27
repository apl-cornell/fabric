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
    
    public static final byte[] $classHash = new byte[] { 38, -30, 106, -9, 16,
    40, 9, 110, -41, 53, 91, 80, 73, 29, 48, 9, 13, -72, 51, 83, 73, -56, -76,
    37, 72, -46, -68, 78, 76, 117, -44, -85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
