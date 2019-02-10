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
    
    public static final byte[] $classHash = new byte[] { 12, 45, -63, 55, -10,
    24, 46, 9, 98, -47, -80, -50, 19, -38, 38, 31, 66, 79, -50, -26, -81, -8,
    -38, -50, -54, -100, 9, 53, -47, -118, 44, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
