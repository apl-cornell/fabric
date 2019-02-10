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
    
    public static final byte[] $classHash = new byte[] { -119, -11, -26, 40, 21,
    -97, -54, 33, -62, -112, 9, -58, 13, -44, 127, 123, 15, -28, 89, 99, -103,
    -52, -120, -76, 87, 4, 72, 127, 111, 112, -109, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
