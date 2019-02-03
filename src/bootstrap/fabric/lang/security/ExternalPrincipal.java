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
                                    if ($backoff7 < 5000) $backoff7 *= 2;
                                }
                                $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    this.worker$ =
                                      fabric.worker.Worker.getWorker();
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
                            catch (fabric.worker.
                                     TransactionAbortingException $e4) {
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
                                if ($tm6.checkForStaleObjects())
                                    continue $label0;
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
                                    catch (final fabric.worker.
                                             AbortException $e4) {
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
                                } else if ($keepReads3) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { -106, 117, 19, -108,
    76, 110, -112, 79, -16, 9, 54, -61, -101, -7, -118, 80, 24, 94, -63, 82,
    -39, -58, -74, -15, 96, -5, 2, 29, 115, -104, -68, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
    public static final java.lang.String jlc$ClassType$fabil$1 =
      "4gTi6yl07exL+Feqr63nzorTwOT+F/AuqdADgpXnsri8RvzWf/n+/3Ht0fXbJzcuoKMvZe95g/a/MP/PN9r/7Kf/508sPvCJf8r8zq9+/Ts/+r+uPp98+Z8s/y/eFF/x3iwAAA==";
}
