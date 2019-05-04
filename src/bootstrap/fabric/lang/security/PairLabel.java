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
    
    public static final byte[] $classHash = new byte[] { -69, 68, -4, -74, 11,
    -58, -98, 69, -1, 81, 21, 6, 87, -31, -12, 91, 92, 60, 62, 1, -8, -73, 102,
    -55, 110, -31, -58, -88, 80, -37, -29, -124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
