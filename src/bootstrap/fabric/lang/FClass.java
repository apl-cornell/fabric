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
