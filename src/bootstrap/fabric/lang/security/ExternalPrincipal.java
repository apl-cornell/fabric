package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * An ExternalPrincipal is used primarily for test purposes, to represent
 * principals like "Alice" and "Bob".
 */
public interface ExternalPrincipal
  extends fabric.lang.security.AbstractPrincipal {
    public fabric.lang.security.ExternalPrincipal
      fabric$lang$security$ExternalPrincipal$(final java.lang.String name);
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.3.0";
    public static final long jlc$SourceLastModified$fabric = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabric =
      "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";
    
    public fabric.lang.Object $initLabels();
    
    public void $initPartitions();
    
    public static class _Proxy
    extends fabric.lang.security.AbstractPrincipal._Proxy
      implements fabric.lang.security.ExternalPrincipal {
        public fabric.lang.security.ExternalPrincipal
          fabric$lang$security$ExternalPrincipal$(java.lang.String arg1) {
            return ((fabric.lang.security.ExternalPrincipal) fetch()).
              fabric$lang$security$ExternalPrincipal$(arg1);
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return fabric.lang.security.ExternalPrincipal._Impl.jif$Instanceof(
                                                                  arg1);
        }
        
        public static fabric.lang.security.ExternalPrincipal
          jif$cast$fabric_lang_security_ExternalPrincipal(
          fabric.lang.Object arg1) {
            return fabric.lang.security.ExternalPrincipal._Impl.
              jif$cast$fabric_lang_security_ExternalPrincipal(arg1);
        }
        
        public _Proxy(ExternalPrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.lang.security.AbstractPrincipal._Impl
      implements fabric.lang.security.ExternalPrincipal {
        public fabric.lang.security.ExternalPrincipal
          fabric$lang$security$ExternalPrincipal$(final java.lang.String name) {
            ((fabric.lang.security.ExternalPrincipal._Impl) this.fetch()).
              jif$init();
            { this.fabric$lang$security$AbstractPrincipal$(name); }
            return (fabric.lang.security.ExternalPrincipal) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return false;
            fabric.lang.security.LabelUtil._Impl.
              accessCheck(
                fabric.lang.security.LabelUtil._Impl.
                    toLabel(
                      fabric.worker.Worker.getWorker().getLocalStore(),
                      fabric.lang.security.LabelUtil._Impl.
                          readerPolicy(
                            fabric.worker.Worker.getWorker().getLocalStore(),
                            o.fetch().$getStore().getPrincipal(),
                            fabric.lang.security.PrincipalUtil._Impl.
                                topPrincipal()),
                      fabric.lang.security.LabelUtil._Impl.topInteg()),
                o);
            return fabric.lang.Object._Proxy.
              $getProxy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap(o)) instanceof fabric.lang.security.ExternalPrincipal;
        }
        
        public static fabric.lang.security.ExternalPrincipal
          jif$cast$fabric_lang_security_ExternalPrincipal(
          final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (fabric.lang.security.ExternalPrincipal._Impl.jif$Instanceof(o))
                return (fabric.lang.security.ExternalPrincipal)
                         fabric.lang.Object._Proxy.$getProxy(o);
            throw new java.lang.ClassCastException();
        }
        
        public fabric.lang.Object $initLabels() {
            this.
              set$$updateLabel(
                fabric.lang.security.LabelUtil._Impl.
                    toLabel(
                      this.$getStore(),
                      fabric.lang.security.LabelUtil._Impl.bottomConf(),
                      fabric.lang.security.LabelUtil._Impl.
                          writerPolicy(
                            this.$getStore(),
                            fabric.lang.security.PrincipalUtil._Impl.
                                topPrincipal(),
                            fabric.lang.security.PrincipalUtil._Impl.
                                topPrincipal())));
            this.
              set$$accessPolicy(
                fabric.lang.security.LabelUtil._Impl.
                    toLabel(
                      this.$getStore(),
                      fabric.lang.security.LabelUtil._Impl.
                          readerPolicy(
                            this.$getStore(),
                            this.fetch().$getStore().getPrincipal(),
                            fabric.lang.security.PrincipalUtil._Impl.
                                topPrincipal()),
                      fabric.lang.security.LabelUtil._Impl.topInteg()).
                    confPolicy());
            $initPartitions();
            return (fabric.lang.security.ExternalPrincipal) this.$getProxy();
        }
        
        public void $initPartitions() { super.$initPartitions(); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ExternalPrincipal._Proxy(this);
        }
        
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
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ExternalPrincipal._Static {
            public fabric.worker.Worker get$worker$() {
                return ((fabric.lang.security.ExternalPrincipal._Static._Impl)
                          fetch()).get$worker$();
            }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() {
                return ((fabric.lang.security.ExternalPrincipal._Static._Impl)
                          fetch()).get$jlc$CompilerVersion$fabric();
            }
            
            public long get$jlc$SourceLastModified$fabric() {
                return ((fabric.lang.security.ExternalPrincipal._Static._Impl)
                          fetch()).get$jlc$SourceLastModified$fabric();
            }
            
            public java.lang.String get$jlc$ClassType$fabric() {
                return ((fabric.lang.security.ExternalPrincipal._Static._Impl)
                          fetch()).get$jlc$ClassType$fabric();
            }
            
            public _Proxy(fabric.lang.security.ExternalPrincipal._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ExternalPrincipal._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ExternalPrincipal.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    lang.
                    security.
                    ExternalPrincipal.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ExternalPrincipal._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.ExternalPrincipal._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ExternalPrincipal._Static {
            public fabric.worker.Worker get$worker$() { return this.worker$; }
            
            fabric.worker.Worker worker$;
            
            public java.lang.String get$jlc$CompilerVersion$fabric() {
                return this.jlc$CompilerVersion$fabric;
            }
            
            public java.lang.String jlc$CompilerVersion$fabric;
            
            public long get$jlc$SourceLastModified$fabric() {
                return this.jlc$SourceLastModified$fabric;
            }
            
            public long jlc$SourceLastModified$fabric;
            
            public java.lang.String get$jlc$ClassType$fabric() {
                return this.jlc$ClassType$fabric;
            }
            
            public java.lang.String jlc$ClassType$fabric;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeInline(out, this.jlc$CompilerVersion$fabric);
                out.writeLong(this.jlc$SourceLastModified$fabric);
                $writeInline(out, this.jlc$ClassType$fabric);
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
                this.jlc$CompilerVersion$fabric = (java.lang.String)
                                                    in.readObject();
                this.jlc$SourceLastModified$fabric = in.readLong();
                this.jlc$ClassType$fabric = (java.lang.String) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.ExternalPrincipal._Static.
                         _Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm6 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled9 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
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
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff7));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e4) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff7 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff7 =
                                          java.lang.Math.
                                            min(
                                              $backoff7 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                this.worker$ = fabric.worker.Worker.getWorker();
                            }
                            catch (final fabric.worker.RetryException $e4) {
                                $commit1 = false;
                                continue $label0;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e4) {
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
                                    catch (final fabric.worker.
                                             AbortException $e4) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e4) {
                                        $commit1 = false;
                                        $retry2 = false;
                                        $keepReads3 = $e4.keepReads;
                                        if ($e4.tid ==
                                              null ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5))
                                            throw $e4;
                                        throw new fabric.worker.
                                                UserAbortException($e4);
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
                                            if ($currentTid5 != null &&
                                                  ($e4.tid.equals($currentTid5) || !$e4.tid.isDescendantOf($currentTid5))) {
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
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -82, 114, -10, 84, 67,
    35, -22, -92, 29, -30, -39, -21, -57, 107, -29, -106, 73, -115, 5, -11, -69,
    9, 19, -24, 11, 13, 56, 54, -107, -27, -99, 19 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
    public static final java.lang.String jlc$ClassType$fabil$1 =
      "r+IY4hcS6Orpl/Avll9bz54WJ4HJ/S/gXVChBwQrz6ZRcY347T//rv919ZHVO8c3LqCDr0R/ucJf/JO3nvvD3/3mL9v//QvkP3/4L37p+jPfeOx9reYbX//yM/8XbVHvcN4sAAA=";
}
