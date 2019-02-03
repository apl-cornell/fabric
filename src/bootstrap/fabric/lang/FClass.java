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
                int $backoff7 = 1;
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
                            if ($backoff7 < 5000) $backoff7 *= 2;
                        }
                        $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                    }
                    $commit1 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        try {
                            fabric.lang.arrays.byteArray copy =
                              (fabric.lang.arrays.byteArray)
                                new fabric.lang.arrays.byteArray._Impl(
                                  this.$getStore(
                                         )).fabric$lang$arrays$byteArray$(
                                              this.get$$updateLabel(),
                                              this.get$$updateLabel(
                                                     ).confPolicy(),
                                              this.get$bytecode(
                                                     ).get$length()).$getProxy(
                                                                       );
                            for (int i = 0; i < copy.get$length(); i++)
                                copy.set(i, (byte) this.get$bytecode().get(i));
                            return copy;
                        }
                        catch (final fabric.worker.RetryException $e4) {
                            throw $e4;
                        }
                        catch (final fabric.worker.
                                 TransactionAbortingException $e4) {
                            throw $e4;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e4) {
                            throw $e4;
                        }
                        catch (final Throwable $e4) {
                            $tm6.getCurrentLog().checkRetrySignal();
                            throw $e4;
                        }
                    }
                    catch (final fabric.worker.RetryException $e4) {
                        $commit1 = false;
                        continue $label0;
                    }
                    catch (fabric.worker.TransactionAbortingException $e4) {
                        $commit1 = false;
                        $retry2 = false;
                        $keepReads3 = $e4.keepReads;
                        if ($tm6.checkForStaleObjects()) {
                            $retry2 = true;
                            $keepReads3 = false;
                            continue $label0;
                        }
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
                        if ($tm6.checkForStaleObjects()) continue $label0;
                        $retry2 = false;
                        throw new fabric.worker.AbortException($e4);
                    }
                    finally {
                        if ($commit1) {
                            fabric.common.TransactionID $currentTid5 =
                              $tm6.getCurrentTid();
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
                                if ($tm6.checkForStaleObjects()) {
                                    $retry2 = true;
                                    $keepReads3 = false;
                                    continue $label0;
                                }
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
                        }
                        else if ($keepReads3) {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransactionUpdates();
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { 19, -54, -32, 74, -38,
    17, 38, 31, -66, 93, 2, -37, 22, -128, 7, -124, 97, 66, -34, -17, 75, 61,
    72, -41, -69, -62, -38, -116, 67, -123, 127, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
