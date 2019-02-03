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
    
    public static final byte[] $classHash = new byte[] { -46, -118, -53, -25,
    -18, -9, 49, 110, -87, 18, 117, -82, -26, 10, -122, 34, -122, 112, 1, -22,
    55, -103, 28, 19, -10, -44, -15, 70, -40, -3, 21, 123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
