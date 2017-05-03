package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.util.Set;
import fabric.util.HashSet;

/**
 * A Fabric class; it has the bytecode and source of the fabric class and the 
 * codebase it is associated to.
 * 
 * @author Lucas Waye <lrw48@cornell.edu>
 */
public interface FClass extends fabric.lang.Object {
    public fabric.lang.Codebase get$codebase();
    
    public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);
    
    public fabric.lang.Object get$staticInstance();
    
    public fabric.lang.Object set$staticInstance(fabric.lang.Object val);
    
    public java.lang.String get$name();
    
    public java.lang.String set$name(java.lang.String val);
    
    public java.lang.String get$source();
    
    public java.lang.String set$source(java.lang.String val);
    
    public fabric.lang.arrays.byteArray get$bytecode();
    
    public fabric.lang.arrays.byteArray set$bytecode(
      fabric.lang.arrays.byteArray val);
    
    public fabric.lang.FClass fabric$lang$FClass$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy,
      fabric.lang.Codebase codebase, java.lang.String name,
      java.lang.String source, fabric.lang.arrays.byteArray bytecode);
    
    public fabric.lang.Codebase getCodebase();
    
    public java.lang.String getName();
    
    public java.lang.String getSource();
    
    public fabric.lang.arrays.byteArray getBytecode();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.FClass {
        public fabric.lang.Codebase get$codebase() {
            return ((fabric.lang.FClass._Impl) fetch()).get$codebase();
        }
        
        public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val) {
            return ((fabric.lang.FClass._Impl) fetch()).set$codebase(val);
        }
        
        public fabric.lang.Object get$staticInstance() {
            return ((fabric.lang.FClass._Impl) fetch()).get$staticInstance();
        }
        
        public fabric.lang.Object set$staticInstance(fabric.lang.Object val) {
            return ((fabric.lang.FClass._Impl) fetch()).set$staticInstance(val);
        }
        
        public java.lang.String get$name() {
            return ((fabric.lang.FClass._Impl) fetch()).get$name();
        }
        
        public java.lang.String set$name(java.lang.String val) {
            return ((fabric.lang.FClass._Impl) fetch()).set$name(val);
        }
        
        public java.lang.String get$source() {
            return ((fabric.lang.FClass._Impl) fetch()).get$source();
        }
        
        public java.lang.String set$source(java.lang.String val) {
            return ((fabric.lang.FClass._Impl) fetch()).set$source(val);
        }
        
        public fabric.lang.arrays.byteArray get$bytecode() {
            return ((fabric.lang.FClass._Impl) fetch()).get$bytecode();
        }
        
        public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val) {
            return ((fabric.lang.FClass._Impl) fetch()).set$bytecode(val);
        }
        
        public native fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.Codebase arg3, java.lang.String arg4,
          java.lang.String arg5, fabric.lang.arrays.byteArray arg6);
        
        public native fabric.lang.Codebase getCodebase();
        
        public native java.lang.String getName();
        
        public native java.lang.String getSource();
        
        public native fabric.lang.arrays.byteArray getBytecode();
        
        public _Proxy(FClass._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.FClass {
        public fabric.lang.Codebase get$codebase() { return this.codebase; }
        
        public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.codebase = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.Codebase codebase;
        
        public fabric.lang.Object get$staticInstance() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.staticInstance;
        }
        
        public fabric.lang.Object set$staticInstance(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.staticInstance = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.Object staticInstance;
        
        public java.lang.String get$name() { return this.name; }
        
        public java.lang.String set$name(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.name = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected java.lang.String name;
        
        public java.lang.String get$source() { return this.source; }
        
        public java.lang.String set$source(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.source = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected java.lang.String source;
        
        public fabric.lang.arrays.byteArray get$bytecode() {
            return this.bytecode;
        }
        
        public fabric.lang.arrays.byteArray set$bytecode(
          fabric.lang.arrays.byteArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.bytecode = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.byteArray bytecode;
        
        public native fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          fabric.lang.Codebase codebase, java.lang.String name,
          java.lang.String source, fabric.lang.arrays.byteArray bytecode);
        
        public native fabric.lang.Codebase getCodebase();
        
        public native java.lang.String getName();
        
        public native java.lang.String getSource();
        
        public native fabric.lang.arrays.byteArray getBytecode();
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.FClass._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.codebase, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.staticInstance, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeInline(out, this.name);
            $writeInline(out, this.source);
            $writeRef($getStore(), this.bytecode, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.codebase = (fabric.lang.Codebase)
                              $readRef(fabric.lang.Codebase._Proxy.class,
                                       (fabric.common.RefTypeEnum)
                                         refTypes.next(), in, store,
                                       intraStoreRefs, interStoreRefs);
            this.staticInstance = (fabric.lang.Object)
                                    $readRef(fabric.lang.Object._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
            this.name = (java.lang.String) in.readObject();
            this.source = (java.lang.String) in.readObject();
            this.bytecode =
              (fabric.lang.arrays.byteArray)
                $readRef(fabric.lang.arrays.byteArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.FClass._Impl src = (fabric.lang.FClass._Impl) other;
            this.codebase = src.codebase;
            this.staticInstance = src.staticInstance;
            this.name = src.name;
            this.source = src.source;
            this.bytecode = src.bytecode;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.FClass._Static {
            public _Proxy(fabric.lang.FClass._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.FClass._Static $instance;
            
            static {
                fabric.
                  lang.
                  FClass.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.FClass._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(fabric.lang.FClass._Static._Impl.class);
                $instance = (fabric.lang.FClass._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.FClass._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.FClass._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
