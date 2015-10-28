package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.IOException;
import java.util.Properties;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.util.Collections;
import fabric.util.HashMap;
import fabric.util.Map;
import fabric.util.Iterator;

/**
 * A codebase is a Fabric object that provides a mapping from fully qualified 
 * fabric class names to Fabric FClass objects. 
 * 
 * @author Lucas Waye <lrw48@cornell.edu>
 */
public interface Codebase
  extends fabric.lang.Object
{
    
    public fabric.util.Map get$classes();
    
    public fabric.util.Map set$classes(fabric.util.Map val);
    
    public fabric.util.Map get$codebases();
    
    public fabric.util.Map set$codebases(fabric.util.Map val);
    
    /**
     * @param  name
     *    a fabric name
     * @return
     *    the associated FClass, or null if there is none
     */
    public fabric.lang.FClass resolveClassName(java.lang.String name);
    
    public fabric.lang.Codebase resolveCodebaseName(java.lang.String name);
    
    public void addCodebaseName(java.lang.String name,
                                fabric.lang.Codebase codebase);
    
    public fabric.lang.Codebase fabric$lang$Codebase$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy);
    
    public fabric.lang.Codebase fabric$lang$Codebase$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, fabric.util.Map classes);
    
    public void insertClass(java.lang.String name, fabric.lang.FClass fcls);
    
    public fabric.util.Map getClasses();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.Codebase
    {
        
        public fabric.util.Map get$classes() {
            return ((fabric.lang.Codebase._Impl) fetch()).get$classes();
        }
        
        public fabric.util.Map set$classes(fabric.util.Map val) {
            return ((fabric.lang.Codebase._Impl) fetch()).set$classes(val);
        }
        
        public fabric.util.Map get$codebases() {
            return ((fabric.lang.Codebase._Impl) fetch()).get$codebases();
        }
        
        public fabric.util.Map set$codebases(fabric.util.Map val) {
            return ((fabric.lang.Codebase._Impl) fetch()).set$codebases(val);
        }
        
        public native fabric.lang.FClass resolveClassName(
          java.lang.String arg1);
        
        public native fabric.lang.Codebase resolveCodebaseName(
          java.lang.String arg1);
        
        public native void addCodebaseName(java.lang.String arg1,
                                           fabric.lang.Codebase arg2);
        
        public native fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label arg1,
          fabric.lang.security.ConfPolicy arg2);
        
        public native fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.util.Map arg3);
        
        public native void insertClass(java.lang.String arg1,
                                       fabric.lang.FClass arg2);
        
        public native fabric.util.Map getClasses();
        
        public _Proxy(Codebase._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.Codebase
    {
        
        public fabric.util.Map get$classes() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.classes;
        }
        
        public fabric.util.Map set$classes(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.classes = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** All of the classes in the codebase **/
        private fabric.util.Map classes;
        
        public fabric.util.Map get$codebases() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.codebases;
        }
        
        public fabric.util.Map set$codebases(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.codebases = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** All of the explicit codebases referred to in this codebase **/
        private fabric.util.Map codebases;
        
        /**
         * @param  name
         *    a fabric name
         * @return
         *    the associated FClass, or null if there is none
         */
        public native fabric.lang.FClass resolveClassName(
          java.lang.String name);
        
        public native fabric.lang.Codebase resolveCodebaseName(
          java.lang.String name);
        
        public native void addCodebaseName(java.lang.String name,
                                           fabric.lang.Codebase codebase);
        
        public native fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy);
        
        public native fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          fabric.util.Map classes);
        
        public native void insertClass(java.lang.String name,
                                       fabric.lang.FClass fcls);
        
        public native fabric.util.Map getClasses();
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.Codebase._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.classes, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.codebases, refTypes, out,
                      intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.classes = (fabric.util.Map)
                             $readRef(fabric.util.Map._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(),
                                      in,
                                      store,
                                      intraStoreRefs,
                                      interStoreRefs);
            this.codebases = (fabric.util.Map)
                               $readRef(fabric.util.Map._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(),
                                        in,
                                        store,
                                        intraStoreRefs,
                                        interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.Codebase._Impl src = (fabric.lang.Codebase._Impl) other;
            this.classes = src.classes;
            this.codebases = src.codebases;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.Codebase._Static
        {
            
            public _Proxy(fabric.lang.Codebase._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.Codebase._Static $instance;
            
            static {
                fabric.
                  lang.
                  Codebase.
                  _Static.
                  _Impl impl =
                  (fabric.lang.Codebase._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.Codebase._Static._Impl.class);
                $instance = (fabric.lang.Codebase._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.Codebase._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.Codebase._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
