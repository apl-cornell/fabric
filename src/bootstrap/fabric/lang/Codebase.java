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
