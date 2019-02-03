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
    
    public static final byte[] $classHash = new byte[] { -102, -40, 12, -63,
    -53, 69, 44, -63, 16, 16, 26, 93, 62, 62, -24, -1, 76, -92, -61, -44, -62,
    19, -116, -104, -105, -73, 14, -54, 126, -107, 59, -123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
