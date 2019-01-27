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
    
    public static final byte[] $classHash = new byte[] { 26, 21, 95, -99, 85,
    62, 82, 79, -10, 82, 23, 104, 120, -47, -109, 59, 28, 18, -90, -69, 89, 69,
    -35, -84, -120, 41, -40, 8, 122, 89, 121, 49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
