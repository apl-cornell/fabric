package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.LocalStore;
import fabric.common.ONumConstants;
import java.util.HashSet;
import java.util.Set;

/**
 * A Label is the runtime representation of a Jif label. A Label consists of a
 * set of components, each of which is a Policy. This code is mostly copied from
 * Jif.
 */
public interface PairLabel
  extends fabric.lang.security.Label, fabric.lang.Object {
    public fabric.lang.security.ConfPolicy get$confPol();
    
    public fabric.lang.security.ConfPolicy set$confPol(
      fabric.lang.security.ConfPolicy val);
    
    public fabric.lang.security.IntegPolicy get$integPol();
    
    public fabric.lang.security.IntegPolicy set$integPol(
      fabric.lang.security.IntegPolicy val);
    
    public fabric.lang.security.SecretKeyObject get$keyObject();
    
    public fabric.lang.security.SecretKeyObject set$keyObject(
      fabric.lang.security.SecretKeyObject val);
    
    public fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
      fabric.lang.security.ConfPolicy confPol,
      fabric.lang.security.IntegPolicy integPol);
    
    public boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);
    
    public int hashCode();
    
    public boolean equals(fabric.lang.Object o);
    
    public java.lang.String toString();
    
    public fabric.lang.security.Label join(fabric.worker.Store store,
                                           fabric.lang.security.Label l);
    
    public fabric.lang.security.Label meet(fabric.worker.Store store,
                                           fabric.lang.security.Label l);
    
    public fabric.lang.security.Label join(fabric.worker.Store store,
                                           fabric.lang.security.Label l,
                                           boolean simplify);
    
    public fabric.lang.security.Label meet(fabric.worker.Store store,
                                           fabric.lang.security.Label l,
                                           boolean simplify);
    
    public fabric.lang.security.ConfPolicy confPolicy();
    
    public fabric.lang.security.IntegPolicy integPolicy();
    
    public fabric.lang.security.SecretKeyObject keyObject();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PairLabel {
        public fabric.lang.security.ConfPolicy get$confPol() {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).get$confPol(
                                                                      );
        }
        
        public fabric.lang.security.ConfPolicy set$confPol(
          fabric.lang.security.ConfPolicy val) {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).set$confPol(
                                                                      val);
        }
        
        public fabric.lang.security.IntegPolicy get$integPol() {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).
              get$integPol();
        }
        
        public fabric.lang.security.IntegPolicy set$integPol(
          fabric.lang.security.IntegPolicy val) {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).
              set$integPol(val);
        }
        
        public fabric.lang.security.SecretKeyObject get$keyObject() {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).
              get$keyObject();
        }
        
        public fabric.lang.security.SecretKeyObject set$keyObject(
          fabric.lang.security.SecretKeyObject val) {
            return ((fabric.lang.security.PairLabel._Impl) fetch()).
              set$keyObject(val);
        }
        
        public fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
          fabric.lang.security.ConfPolicy arg1,
          fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.PairLabel) fetch()).
              fabric$lang$security$PairLabel$(arg1, arg2);
        }
        
        public boolean relabelsTo(fabric.lang.security.Label arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.PairLabel) fetch()).relabelsTo(arg1,
                                                                         arg2);
        }
        
        public final fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2) {
            return ((fabric.lang.security.PairLabel) fetch()).join(arg1, arg2);
        }
        
        public fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2) {
            return ((fabric.lang.security.PairLabel) fetch()).meet(arg1, arg2);
        }
        
        public final fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          boolean arg3) {
            return ((fabric.lang.security.PairLabel) fetch()).join(arg1, arg2,
                                                                   arg3);
        }
        
        public fabric.lang.security.Label meet(fabric.worker.Store arg1,
                                               fabric.lang.security.Label arg2,
                                               boolean arg3) {
            return ((fabric.lang.security.PairLabel) fetch()).meet(arg1, arg2,
                                                                   arg3);
        }
        
        public fabric.lang.security.ConfPolicy confPolicy() {
            return ((fabric.lang.security.PairLabel) fetch()).confPolicy();
        }
        
        public fabric.lang.security.IntegPolicy integPolicy() {
            return ((fabric.lang.security.PairLabel) fetch()).integPolicy();
        }
        
        public fabric.lang.security.SecretKeyObject keyObject() {
            return ((fabric.lang.security.PairLabel) fetch()).keyObject();
        }
        
        public _Proxy(PairLabel._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PairLabel {
        public fabric.lang.security.ConfPolicy get$confPol() {
            return this.confPol;
        }
        
        public fabric.lang.security.ConfPolicy set$confPol(
          fabric.lang.security.ConfPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.confPol = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.ConfPolicy confPol;
        
        public fabric.lang.security.IntegPolicy get$integPol() {
            return this.integPol;
        }
        
        public fabric.lang.security.IntegPolicy set$integPol(
          fabric.lang.security.IntegPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.integPol = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.IntegPolicy integPol;
        
        public fabric.lang.security.SecretKeyObject get$keyObject() {
            return this.keyObject;
        }
        
        public fabric.lang.security.SecretKeyObject set$keyObject(
          fabric.lang.security.SecretKeyObject val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.keyObject = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.SecretKeyObject keyObject;
        
        public fabric.lang.security.PairLabel fabric$lang$security$PairLabel$(
          fabric.lang.security.ConfPolicy confPol,
          fabric.lang.security.IntegPolicy integPol) {
            this.set$confPol(confPol);
            this.set$integPol(integPol);
            final fabric.worker.LocalStore localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (fabric.lang.Object._Proxy.idEquals(confPol, null) ||
                  fabric.lang.Object._Proxy.idEquals(integPol, null)) {
                throw new java.lang.NullPointerException();
            }
            if (fabric.lang.Object._Proxy.idEquals(
                                            confPol,
                                            localStore.getBottomConfidPolicy(
                                                         ))) {
                this.set$keyObject(null);
            }
            else {
                this.
                  set$keyObject(
                    (fabric.lang.security.SecretKeyObject)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.lang.security.SecretKeyObject)
                           new fabric.lang.security.SecretKeyObject._Impl(
                             this.$getStore()).$getProxy()).
                            fabric$lang$security$SecretKeyObject$()));
            }
            fabric$lang$Object$();
            return (fabric.lang.security.PairLabel) this.$getProxy();
        }
        
        public boolean relabelsTo(fabric.lang.security.Label l,
                                  java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l)) instanceof fabric.lang.security.PairLabel) {
                fabric.lang.security.PairLabel that =
                  (fabric.lang.security.PairLabel)
                    fabric.lang.Object._Proxy.$getProxy(l);
                if (fabric.lang.Object._Proxy.idEquals(
                                                (fabric.lang.security.PairLabel)
                                                  this.$getProxy(), that) ||
                      this.equals(that))
                    return true;
                final fabric.worker.Store localStore =
                  fabric.worker.Worker.getWorker().getLocalStore();
                java.util.Set temp = new java.util.HashSet();
                if (fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                           this.get$confPol(),
                                                           that.get$confPol(),
                                                           temp) &&
                      fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                             this.get$integPol(
                                                                    ),
                                                             that.get$integPol(
                                                                    ), temp)) {
                    s.addAll(temp);
                    return true;
                }
            }
            return false;
        }
        
        public int hashCode() {
            if (this.$memFlag0) return this.$memValue1;
            this.$memFlag0 = true;
            return this.$memValue1 = this.get$confPol().hashCode() ^
                                       this.get$integPol().hashCode();
        }
        
        public boolean equals(fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(o)) instanceof fabric.lang.security.PairLabel) {
                fabric.lang.security.PairLabel that =
                  (fabric.lang.security.PairLabel)
                    fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.lang.Object._Proxy.
                  idEquals((fabric.lang.security.PairLabel) this.$getProxy(),
                           that) || this.hashCode() == that.hashCode() &&
                  this.get$confPol().equals(that.get$confPol()) &&
                  this.get$integPol().equals(that.get$integPol());
            }
            return false;
        }
        
        public final java.lang.String toString() {
            java.lang.String c = this.get$confPol().toString();
            java.lang.String i = this.get$integPol().toString();
            if (c.length() > 0 && i.length() > 0) {
                return "{" + c + "; " + i + "}";
            }
            return "{" + c + i + "}";
        }
        
        public final fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.PairLabel) this.$getProxy(), l,
                   true);
        }
        
        public fabric.lang.security.Label meet(fabric.worker.Store store,
                                               fabric.lang.security.Label l) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.PairLabel) this.$getProxy(), l,
                   true);
        }
        
        public final fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l,
          boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              join(store, (fabric.lang.security.PairLabel) this.$getProxy(), l,
                   simplify);
        }
        
        public fabric.lang.security.Label meet(fabric.worker.Store store,
                                               fabric.lang.security.Label l,
                                               boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.
              meet(store, (fabric.lang.security.PairLabel) this.$getProxy(), l,
                   simplify);
        }
        
        public fabric.lang.security.ConfPolicy confPolicy() {
            return this.get$confPol();
        }
        
        public fabric.lang.security.IntegPolicy integPolicy() {
            return this.get$integPol();
        }
        
        public fabric.lang.security.SecretKeyObject keyObject() {
            return this.get$keyObject();
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.PairLabel) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.PairLabel._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.confPol, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.integPol, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.keyObject, refTypes, out,
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
            this.confPol =
              (fabric.lang.security.ConfPolicy)
                $readRef(fabric.lang.security.ConfPolicy._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.integPol =
              (fabric.lang.security.IntegPolicy)
                $readRef(fabric.lang.security.IntegPolicy._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.keyObject =
              (fabric.lang.security.SecretKeyObject)
                $readRef(fabric.lang.security.SecretKeyObject._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.PairLabel._Impl src =
              (fabric.lang.security.PairLabel._Impl) other;
            this.confPol = src.confPol;
            this.integPol = src.integPol;
            this.keyObject = src.keyObject;
        }
        
        private transient boolean $memFlag0 = false;
        private transient int $memValue1;
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.PairLabel._Static {
            public _Proxy(fabric.lang.security.PairLabel._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.PairLabel._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  PairLabel.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.PairLabel._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.PairLabel._Static._Impl.class);
                $instance = (fabric.lang.security.PairLabel._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PairLabel._Static {
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
                return new fabric.lang.security.PairLabel._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -47, -82, -87, 18, -91,
    -95, 104, 23, 0, 105, 78, 60, 53, -40, 19, 76, 65, -10, -46, -111, 65, 65,
    -35, 6, 108, 16, -13, -90, 76, 2, 70, -79 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
