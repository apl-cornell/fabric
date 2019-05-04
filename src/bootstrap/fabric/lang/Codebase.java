package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
public interface Codebase extends fabric.lang.Object {
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
      implements fabric.lang.Codebase {
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
        
        public fabric.lang.FClass resolveClassName(java.lang.String arg1) {
            return ((fabric.lang.Codebase) fetch()).resolveClassName(arg1);
        }
        
        public fabric.lang.Codebase resolveCodebaseName(java.lang.String arg1) {
            return ((fabric.lang.Codebase) fetch()).resolveCodebaseName(arg1);
        }
        
        public void addCodebaseName(java.lang.String arg1,
                                    fabric.lang.Codebase arg2) {
            ((fabric.lang.Codebase) fetch()).addCodebaseName(arg1, arg2);
        }
        
        public fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label arg1,
          fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.Codebase) fetch()).fabric$lang$Codebase$(arg1,
                                                                          arg2);
        }
        
        public fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.util.Map arg3) {
            return ((fabric.lang.Codebase) fetch()).fabric$lang$Codebase$(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public void insertClass(java.lang.String arg1,
                                fabric.lang.FClass arg2) {
            ((fabric.lang.Codebase) fetch()).insertClass(arg1, arg2);
        }
        
        public fabric.util.Map getClasses() {
            return ((fabric.lang.Codebase) fetch()).getClasses();
        }
        
        public _Proxy(Codebase._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.Codebase {
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
        public fabric.lang.FClass resolveClassName(java.lang.String name) {
            return (fabric.lang.FClass)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       this.get$classes().
                           get(fabric.lang.WrappedJavaInlineable.$wrap(name)));
        }
        
        public fabric.lang.Codebase resolveCodebaseName(java.lang.String name) {
            return (fabric.lang.Codebase)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       this.get$codebases().
                           get(fabric.lang.WrappedJavaInlineable.$wrap(name)));
        }
        
        public void addCodebaseName(java.lang.String name,
                                    fabric.lang.Codebase codebase) {
            this.get$codebases().put(
                                   fabric.lang.WrappedJavaInlineable.$wrap(
                                                                       name),
                                   codebase);
        }
        
        public fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy) {
            return fabric$lang$Codebase$(
                     updateLabel,
                     accessPolicy,
                     (fabric.util.Map)
                       fabric.lang.Object._Proxy.
                       $getProxy(
                         ((fabric.util.HashMap)
                            new fabric.util.HashMap._Impl(this.$getStore()).
                            $getProxy()).fabric$util$HashMap$()));
        }
        
        public fabric.lang.Codebase fabric$lang$Codebase$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          fabric.util.Map classes) {
            this.set$$updateLabel(updateLabel);
            this.set$$accessPolicy(accessPolicy);
            fabric$lang$Object$();
            this.
              set$classes(
                (fabric.util.Map)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.HashMap)
                       new fabric.util.HashMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$HashMap$(classes)));
            this.
              set$codebases(
                (fabric.util.Map)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.HashMap)
                       new fabric.util.HashMap._Impl(this.$getStore()).
                       $getProxy()).fabric$util$HashMap$()));
            return (fabric.lang.Codebase) this.$getProxy();
        }
        
        public void insertClass(java.lang.String name,
                                fabric.lang.FClass fcls) {
            this.get$classes().put(
                                 fabric.lang.WrappedJavaInlineable.$wrap(name),
                                 fcls);
        }
        
        public fabric.util.Map getClasses() { return this.get$classes(); }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.Codebase) this.$getProxy();
        }
        
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
            this.classes = (fabric.util.Map)
                             $readRef(fabric.util.Map._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
            this.codebases = (fabric.util.Map)
                               $readRef(fabric.util.Map._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
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
          implements fabric.lang.Codebase._Static {
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
                  _Impl
                  impl =
                  (fabric.lang.Codebase._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.Codebase._Static._Impl.class);
                $instance = (fabric.lang.Codebase._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.Codebase._Static {
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
                return new fabric.lang.Codebase._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -94, -15, -113, 80, -5,
    6, -80, 70, 37, -104, -56, 43, 35, -81, 8, -60, 21, 2, 29, 104, 123, 106,
    22, -45, 26, -46, 110, -53, 82, -106, 127, -99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
