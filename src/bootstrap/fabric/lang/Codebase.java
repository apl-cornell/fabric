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
    
    public static final byte[] $classHash = new byte[] { -122, -8, -64, -109,
    56, -104, -95, -72, 127, 76, 76, -60, 79, -75, 59, 38, -122, 59, -4, 57, 81,
    -90, -64, -24, -88, -62, -46, 102, 36, -95, 119, 52 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
