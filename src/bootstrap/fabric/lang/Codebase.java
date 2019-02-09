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
    
    public static final byte[] $classHash = new byte[] { -15, 90, 4, -115, 33,
    -8, 107, 4, 19, 64, 47, 2, 56, -93, -13, -3, 118, 98, 76, 90, 75, 123, -21,
    80, 121, -118, -93, 80, -5, 96, -106, -70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
