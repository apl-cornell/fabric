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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e4) {
                                $commit1 = false;
                                if ($tm6.checkForStaleObjects())
                                    continue $label0;
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
    
    public static final byte[] $classHash = new byte[] { 96, -110, 108, -29,
    108, -10, 121, 102, -53, 28, 102, -115, 35, -5, -24, 103, -128, 29, 47, 121,
    -25, -25, -94, 11, -94, -84, 32, 126, 49, -96, -106, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
    public static final java.lang.String jlc$ClassType$fabil$1 =
      "TiB+PYWunX0J/0b1tfXsWXEamNz7At4lFbpPsPJsFpfXiN/6b9//P689sn775MYFdPRjn3f/s/sne+Mbzxl/+6X/9S3zE8/D+29+8xce+4W3jn4C+XtffOn/Aiou0IveLAAA";
}
