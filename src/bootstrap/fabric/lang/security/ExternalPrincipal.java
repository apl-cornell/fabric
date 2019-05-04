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
    
    public static final byte[] $classHash = new byte[] { 7, 8, 84, 127, -62, 20,
    75, -41, -82, -55, -116, -125, -122, 79, -128, 60, 58, -46, -52, -40, -75,
    7, -34, 87, 0, 62, 72, 19, 72, -89, 37, 7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
    public static final java.lang.String jlc$ClassType$fabil$1 =
      "xTHEbyfQ1dMv4d8pv7aeOy1OApMHX8C7oEIPCVaeS6PiGvFb/+2H/9fV66u3j29cQAfXrq9+5l88O/r2V77xc3/7U9NP/MQr3/rm73/12nc46KPD9wx/7cev/V/cmcAE3iwAAA==";
}
