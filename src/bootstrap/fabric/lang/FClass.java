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
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e4) {
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
                    catch (final fabric.worker.metrics.
                             LockConflictException $e4) {
                        $commit1 = false;
                        if ($tm6.checkForStaleObjects()) continue $label0;
                        fabric.common.TransactionID $currentTid5 =
                          $tm6.getCurrentTid();
                        if ($e4.tid.isDescendantOf($currentTid5)) {
                            $retry2 = true;
                        }
                        else if ($currentTid5.parent != null) {
                            $retry2 = false;
                            throw $e4;
                        }
                        else {
                            throw new InternalError(
                                    "Something is broken with transaction " +
                                        "management. Got a signal for a lock conflict in a different " +
                                        "transaction than the one being managed.");
                        }
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
    
    public static final byte[] $classHash = new byte[] { -1, 8, 102, -99, 53,
    -114, 76, -53, -37, 79, -88, 107, -125, -114, 120, 3, -29, 43, 108, -67, 94,
    -41, 119, -72, -108, 10, 90, -81, 93, 63, -41, -81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
