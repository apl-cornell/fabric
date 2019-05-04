package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
        
        public fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.Codebase arg3, java.lang.String arg4,
          java.lang.String arg5, fabric.lang.arrays.byteArray arg6) {
            return ((fabric.lang.FClass) fetch()).fabric$lang$FClass$(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4,
                                                                      arg5,
                                                                      arg6);
        }
        
        public fabric.lang.Codebase getCodebase() {
            return ((fabric.lang.FClass) fetch()).getCodebase();
        }
        
        public java.lang.String getName() {
            return ((fabric.lang.FClass) fetch()).getName();
        }
        
        public java.lang.String getSource() {
            return ((fabric.lang.FClass) fetch()).getSource();
        }
        
        public fabric.lang.arrays.byteArray getBytecode() {
            return ((fabric.lang.FClass) fetch()).getBytecode();
        }
        
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
        
        public fabric.lang.FClass fabric$lang$FClass$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          fabric.lang.Codebase codebase, java.lang.String name,
          java.lang.String source, fabric.lang.arrays.byteArray bytecode) {
            this.set$$updateLabel(updateLabel);
            this.set$$accessPolicy(accessPolicy);
            this.set$codebase(codebase);
            this.set$name(name);
            this.set$source(source);
            this.set$bytecode(bytecode);
            fabric$lang$Object$();
            return (fabric.lang.FClass) this.$getProxy();
        }
        
        public fabric.lang.Codebase getCodebase() {
            return this.get$codebase();
        }
        
        public java.lang.String getName() { return this.get$name(); }
        
        public java.lang.String getSource() { return this.get$source(); }
        
        public fabric.lang.arrays.byteArray getBytecode() {
            {
                fabric.worker.transaction.TransactionManager $tm6 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled9 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                long $backoff7 = 1;
                boolean $doBackoff8 = true;
                boolean $retry2 = true;
                boolean $keepReads3 = false;
                $label0: for (boolean $commit1 = false; !$commit1; ) {
                    if ($backoffEnabled9) {
                        if ($doBackoff8) {
                            if ($backoff7 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff7));
                                        break;
                                    }
                                    catch (java.lang.InterruptedException $e4) {
                                        
                                    }
                                }
                            }
                            if ($backoff7 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff7 =
                                  java.lang.Math.
                                    min(
                                      $backoff7 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                    }
                    $commit1 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.lang.arrays.byteArray copy =
                          (fabric.lang.arrays.byteArray)
                            new fabric.lang.arrays.byteArray._Impl(
                              this.$getStore()).fabric$lang$arrays$byteArray$(
                                                  this.get$$updateLabel(),
                                                  this.get$$updateLabel(
                                                         ).confPolicy(),
                                                  this.get$bytecode(
                                                         ).get$length(
                                                             )).$getProxy();
                        for (int i = 0; i < copy.get$length(); i++)
                            copy.set(i, (byte) this.get$bytecode().get(i));
                        return copy;
                    }
                    catch (final fabric.worker.RetryException $e4) {
                        $commit1 = false;
                        continue $label0;
                    }
                    catch (fabric.worker.TransactionAbortingException $e4) {
                        $commit1 = false;
                        $retry2 = false;
                        $keepReads3 = $e4.keepReads;
                        fabric.common.TransactionID $currentTid5 =
                          $tm6.getCurrentTid();
                        if ($e4.tid == null ||
                              !$e4.tid.isDescendantOf($currentTid5)) {
                            throw $e4;
                        }
                        throw new fabric.worker.UserAbortException($e4);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e4) {
                        $commit1 = false;
                        fabric.common.TransactionID $currentTid5 =
                          $tm6.getCurrentTid();
                        if ($e4.tid.isDescendantOf($currentTid5))
                            continue $label0;
                        if ($currentTid5.parent != null) {
                            $retry2 = false;
                            throw $e4;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e4) {
                        $commit1 = false;
                        $retry2 = false;
                        if ($tm6.inNestedTxn()) { $keepReads3 = true; }
                        throw $e4;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid5 =
                          $tm6.getCurrentTid();
                        if ($commit1) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e4) {
                                $commit1 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e4) {
                                $commit1 = false;
                                $retry2 = false;
                                $keepReads3 = $e4.keepReads;
                                if ($e4.tid == null ||
                                      !$e4.tid.isDescendantOf($currentTid5))
                                    throw $e4;
                                throw new fabric.worker.UserAbortException($e4);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e4) {
                                $commit1 = false;
                                $currentTid5 = $tm6.getCurrentTid();
                                if ($currentTid5 != null) {
                                    if ($e4.tid.equals($currentTid5) ||
                                          !$e4.tid.isDescendantOf(
                                                     $currentTid5)) {
                                        throw $e4;
                                    }
                                }
                            }
                        } else {
                            if (!$tm6.inNestedTxn() &&
                                  $tm6.checkForStaleObjects()) {
                                $retry2 = true;
                                $keepReads3 = false;
                            }
                            if ($keepReads3) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e4) {
                                    $currentTid5 = $tm6.getCurrentTid();
                                    if ($currentTid5 !=
                                          null &&
                                          ($e4.tid.equals($currentTid5) ||
                                             !$e4.tid.isDescendantOf($currentTid5))) {
                                        throw $e4;
                                    } else {
                                        $retry2 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
                        }
                        if (!$commit1) {
                            {  }
                            if ($retry2) { continue $label0; }
                        }
                    }
                }
            }
            return null;
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.FClass) this.$getProxy();
        }
        
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
                return new fabric.lang.FClass._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 110, -62, -87, 59, -63,
    117, 45, -71, 32, -5, -105, -72, -46, 52, 95, 88, -13, -75, 47, -86, 47,
    -111, -105, -68, -107, -122, -30, -54, 21, 99, 3, 27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
