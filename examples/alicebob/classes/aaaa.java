import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.runtime.Runtime;
import fabricated.util.List;
import fabricated.util.IdComparator;
import fabricated.util.ArrayList;

public interface aaaa extends fabric.lang.Object {
    
    public aaaa aaaa$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1445481719000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements aaaa
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            aaaa._Impl.main(arg1);
        }
        
        public static void run(fabric.lang.security.Principal arg1,
                               fabric.lang.arrays.ObjectArray arg2)
              throws java.lang.Exception {
            aaaa._Impl.run(arg1, arg2);
        }
        
        public aaaa aaaa$() { return ((aaaa) fetch()).aaaa$(); }
        
        public void jif$invokeDefConstructor() {
            ((aaaa) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return aaaa._Impl.jif$Instanceof(arg1);
        }
        
        public static aaaa jif$cast$aaaa(fabric.lang.Object arg1) {
            return aaaa._Impl.jif$cast$aaaa(arg1);
        }
        
        public _Proxy(aaaa._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements aaaa {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm126 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff127 = 1;
                $label122: for (boolean $commit123 = false; !$commit123; ) { if ($backoff127 >
                                                                                   32) {
                                                                                 while (true) {
                                                                                     try {
                                                                                         java.lang.Thread.
                                                                                           sleep(
                                                                                             $backoff127);
                                                                                         break;
                                                                                     }
                                                                                     catch (java.
                                                                                              lang.
                                                                                              InterruptedException $e124) {
                                                                                         
                                                                                     }
                                                                                 }
                                                                             }
                                                                             if ($backoff127 <
                                                                                   5000)
                                                                                 $backoff127 *=
                                                                                   2;
                                                                             $commit123 =
                                                                               true;
                                                                             fabric.worker.transaction.TransactionManager.
                                                                               getInstance(
                                                                                 ).
                                                                               startTransaction(
                                                                                 );
                                                                             try {
                                                                                 {
                                                                                     fabric.
                                                                                       worker.
                                                                                       transaction.
                                                                                       TransactionManager $tm120 =
                                                                                       fabric.worker.transaction.TransactionManager.
                                                                                       getInstance(
                                                                                         );
                                                                                     int $backoff121 =
                                                                                       1;
                                                                                     $label116: for (boolean $commit117 =
                                                                                                       false;
                                                                                                     !$commit117;
                                                                                                     ) {
                                                                                         if ($backoff121 >
                                                                                               32) {
                                                                                             while (true) {
                                                                                                 try {
                                                                                                     java.lang.Thread.
                                                                                                       sleep(
                                                                                                         $backoff121);
                                                                                                     break;
                                                                                                 }
                                                                                                 catch (java.
                                                                                                          lang.
                                                                                                          InterruptedException $e118) {
                                                                                                     
                                                                                                 }
                                                                                             }
                                                                                         }
                                                                                         if ($backoff121 <
                                                                                               5000)
                                                                                             $backoff121 *=
                                                                                               2;
                                                                                         $commit117 =
                                                                                           true;
                                                                                         fabric.worker.transaction.TransactionManager.
                                                                                           getInstance(
                                                                                             ).
                                                                                           startTransaction(
                                                                                             );
                                                                                         try {
                                                                                             if (fabric.lang.security.LabelUtil._Impl.
                                                                                                   relabelsTo(
                                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                                       toLabel(
                                                                                                         fabric.worker.Worker.
                                                                                                           getWorker(
                                                                                                             ).
                                                                                                           getLocalStore(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           bottomConf(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           writerPolicy(
                                                                                                             fabric.worker.Worker.
                                                                                                               getWorker(
                                                                                                                 ).
                                                                                                               getLocalStore(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 ))),
                                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                                       toLabel(
                                                                                                         fabric.worker.Worker.
                                                                                                           getWorker(
                                                                                                             ).
                                                                                                           getLocalStore(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           readerPolicy(
                                                                                                             fabric.worker.Worker.
                                                                                                               getWorker(
                                                                                                                 ).
                                                                                                               getLocalStore(
                                                                                                                 ),
                                                                                                             p,
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 )),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           writerPolicy(
                                                                                                             fabric.worker.Worker.
                                                                                                               getWorker(
                                                                                                                 ).
                                                                                                               getLocalStore(
                                                                                                                 ),
                                                                                                             p,
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 )))) &&
                                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                                   relabelsTo(
                                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                                       toLabel(
                                                                                                         fabric.worker.Worker.
                                                                                                           getWorker(
                                                                                                             ).
                                                                                                           getLocalStore(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           bottomConf(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           writerPolicy(
                                                                                                             fabric.worker.Worker.
                                                                                                               getWorker(
                                                                                                                 ).
                                                                                                               getLocalStore(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 ))),
                                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                                       toLabel(
                                                                                                         fabric.worker.Worker.
                                                                                                           getWorker(
                                                                                                             ).
                                                                                                           getLocalStore(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           bottomConf(
                                                                                                             ),
                                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                                           writerPolicy(
                                                                                                             fabric.worker.Worker.
                                                                                                               getWorker(
                                                                                                                 ).
                                                                                                               getLocalStore(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 ),
                                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                                               topPrincipal(
                                                                                                                 )))))
                                                                                                 aaaa._Impl.
                                                                                                   run(
                                                                                                     p,
                                                                                                     args);
                                                                                             else
                                                                                                 throw new java.lang.Error(
                                                                                                   "Provider has insufficient integrity.");
                                                                                         }
                                                                                         catch (final fabric.
                                                                                                  worker.
                                                                                                  RetryException $e118) {
                                                                                             $commit117 =
                                                                                               false;
                                                                                             continue $label116;
                                                                                         }
                                                                                         catch (final fabric.
                                                                                                  worker.
                                                                                                  TransactionRestartingException $e118) {
                                                                                             $commit117 =
                                                                                               false;
                                                                                             fabric.
                                                                                               common.
                                                                                               TransactionID $currentTid119 =
                                                                                               $tm120.
                                                                                               getCurrentTid(
                                                                                                 );
                                                                                             if ($e118.tid.
                                                                                                   isDescendantOf(
                                                                                                     $currentTid119))
                                                                                                 continue $label116;
                                                                                             if ($currentTid119.
                                                                                                   parent !=
                                                                                                   null)
                                                                                                 throw $e118;
                                                                                             throw new InternalError(
                                                                                               ("Something is broken with transaction management. Got a signa" +
                                                                                                "l to restart a different transaction than the one being mana" +
                                                                                                "ged."));
                                                                                         }
                                                                                         catch (final Throwable $e118) {
                                                                                             $commit117 =
                                                                                               false;
                                                                                             if ($tm120.
                                                                                                   checkForStaleObjects(
                                                                                                     ))
                                                                                                 continue $label116;
                                                                                             throw new fabric.
                                                                                               worker.
                                                                                               AbortException(
                                                                                               $e118);
                                                                                         }
                                                                                         finally {
                                                                                             if ($commit117) {
                                                                                                 try {
                                                                                                     fabric.worker.transaction.TransactionManager.
                                                                                                       getInstance(
                                                                                                         ).
                                                                                                       commitTransaction(
                                                                                                         );
                                                                                                 }
                                                                                                 catch (final fabric.
                                                                                                          worker.
                                                                                                          AbortException $e118) {
                                                                                                     $commit117 =
                                                                                                       false;
                                                                                                 }
                                                                                                 catch (final fabric.
                                                                                                          worker.
                                                                                                          TransactionRestartingException $e118) {
                                                                                                     $commit117 =
                                                                                                       false;
                                                                                                     fabric.
                                                                                                       common.
                                                                                                       TransactionID $currentTid119 =
                                                                                                       $tm120.
                                                                                                       getCurrentTid(
                                                                                                         );
                                                                                                     if ($currentTid119 ==
                                                                                                           null ||
                                                                                                           $e118.tid.
                                                                                                           isDescendantOf(
                                                                                                             $currentTid119) &&
                                                                                                           !$currentTid119.
                                                                                                           equals(
                                                                                                             $e118.
                                                                                                               tid))
                                                                                                         continue $label116;
                                                                                                     throw $e118;
                                                                                                 }
                                                                                             }
                                                                                             else {
                                                                                                 fabric.worker.transaction.TransactionManager.
                                                                                                   getInstance(
                                                                                                     ).
                                                                                                   abortTransaction(
                                                                                                     );
                                                                                             }
                                                                                             if (!$commit117) {
                                                                                                 
                                                                                             }
                                                                                         }
                                                                                     }
                                                                                 }
                                                                             }
                                                                             catch (final fabric.
                                                                                      worker.
                                                                                      RetryException $e124) {
                                                                                 $commit123 =
                                                                                   false;
                                                                                 continue $label122;
                                                                             }
                                                                             catch (final fabric.
                                                                                      worker.
                                                                                      TransactionRestartingException $e124) {
                                                                                 $commit123 =
                                                                                   false;
                                                                                 fabric.
                                                                                   common.
                                                                                   TransactionID $currentTid125 =
                                                                                   $tm126.
                                                                                   getCurrentTid(
                                                                                     );
                                                                                 if ($e124.tid.
                                                                                       isDescendantOf(
                                                                                         $currentTid125))
                                                                                     continue $label122;
                                                                                 if ($currentTid125.
                                                                                       parent !=
                                                                                       null)
                                                                                     throw $e124;
                                                                                 throw new InternalError(
                                                                                   ("Something is broken with transaction management. Got a signa" +
                                                                                    "l to restart a different transaction than the one being mana" +
                                                                                    "ged."));
                                                                             }
                                                                             catch (final Throwable $e124) {
                                                                                 $commit123 =
                                                                                   false;
                                                                                 if ($tm126.
                                                                                       checkForStaleObjects(
                                                                                         ))
                                                                                     continue $label122;
                                                                                 throw new fabric.
                                                                                   worker.
                                                                                   AbortException(
                                                                                   $e124);
                                                                             }
                                                                             finally {
                                                                                 if ($commit123) {
                                                                                     try {
                                                                                         fabric.worker.transaction.TransactionManager.
                                                                                           getInstance(
                                                                                             ).
                                                                                           commitTransaction(
                                                                                             );
                                                                                     }
                                                                                     catch (final fabric.
                                                                                              worker.
                                                                                              AbortException $e124) {
                                                                                         $commit123 =
                                                                                           false;
                                                                                     }
                                                                                     catch (final fabric.
                                                                                              worker.
                                                                                              TransactionRestartingException $e124) {
                                                                                         $commit123 =
                                                                                           false;
                                                                                         fabric.
                                                                                           common.
                                                                                           TransactionID $currentTid125 =
                                                                                           $tm126.
                                                                                           getCurrentTid(
                                                                                             );
                                                                                         if ($currentTid125 ==
                                                                                               null ||
                                                                                               $e124.tid.
                                                                                               isDescendantOf(
                                                                                                 $currentTid125) &&
                                                                                               !$currentTid125.
                                                                                               equals(
                                                                                                 $e124.
                                                                                                   tid))
                                                                                             continue $label122;
                                                                                         throw $e124;
                                                                                     }
                                                                                 }
                                                                                 else {
                                                                                     fabric.worker.transaction.TransactionManager.
                                                                                       getInstance(
                                                                                         ).
                                                                                       abortTransaction(
                                                                                         );
                                                                                 }
                                                                                 if (!$commit123) {
                                                                                     
                                                                                 }
                                                                             } }
            } }
        
        public static void run(final fabric.lang.security.Principal p, final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception { final fabric.worker.Store store = fabric.worker.Worker.
                                             getWorker().getStore("store");
                                           if (fabric.lang.security.PrincipalUtil._Impl.
                                                 actsFor(store.getPrincipal(), p)) {
                                               final fabric.lang.security.Label l0 =
                                                 fabric.lang.security.LabelUtil._Impl.
                                                 toLabel(fabric.worker.Worker.getWorker(
                                                                                ).
                                                           getLocalStore(), fabric.lang.security.LabelUtil._Impl.
                                                           readerPolicy(fabric.worker.Worker.
                                                                          getWorker(
                                                                            ).getLocalStore(
                                                                                ),
                                                                        p, fabric.lang.security.PrincipalUtil._Impl.
                                                                          topPrincipal(
                                                                            )), fabric.lang.security.LabelUtil._Impl.
                                                           writerPolicy(fabric.worker.Worker.
                                                                          getWorker(
                                                                            ).getLocalStore(
                                                                                ),
                                                                        p, fabric.lang.security.PrincipalUtil._Impl.
                                                                          topPrincipal(
                                                                            )));
                                               final internal baz = (internal) fabric.lang.Object._Proxy.
                                                                      $getProxy(
                                                                        ((internal)
                                                                           new internal.
                                                                           _Impl(
                                                                           store,
                                                                           l0).$getProxy(
                                                                                 )).
                                                                          internal$(
                                                                            ));
                                               fabricated.util.List storeList = (fabricated.util.ArrayList)
                                                                                  fabric.lang.Object._Proxy.
                                                                                  $getProxy(
                                                                                    ((fabricated.util.ArrayList)
                                                                                       new fabricated.
                                                                                       util.
                                                                                       ArrayList.
                                                                                       _Impl(
                                                                                       aaaa._Static._Proxy.
                                                                                         $instance.
                                                                                         $getStore(
                                                                                           ),
                                                                                       l0).
                                                                                       $getProxy(
                                                                                         )).
                                                                                      fabricated$util$ArrayList$(
                                                                                        (fabricated.util.IdComparator)
                                                                                          fabric.lang.Object._Proxy.
                                                                                          $getProxy(
                                                                                            ((fabricated.util.IdComparator)
                                                                                               new fabricated.
                                                                                               util.
                                                                                               IdComparator.
                                                                                               _Impl(
                                                                                               aaaa._Static._Proxy.
                                                                                                 $instance.
                                                                                                 $getStore(
                                                                                                   ),
                                                                                               l0).
                                                                                               $getProxy(
                                                                                                 )).
                                                                                              fabricated$util$IdComparator$(
                                                                                                )),
                                                                                        3));
                                               storeList.set(0, fabric.lang.WrappedJavaInlineable.
                                                               $wrap("alicenode"));
                                               final fabric.lang.security.Principal z =
                                                 baz.foo(storeList); } }
        
        public aaaa aaaa$() { ((aaaa._Impl) this.fetch()).jif$init();
                              { this.fabric$lang$Object$(); }
                              return (aaaa) this.$getProxy(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.aaaa$(); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                 idEquals(
                                                                                   o,
                                                                                   null))
                                                                               return false;
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             accessCheck(
                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                 noComponents(
                                                                                   ),
                                                                               o);
                                                                           return fabric.lang.Object._Proxy.
                                                                             $getProxy(
                                                                               (java.lang.Object)
                                                                                 fabric.lang.WrappedJavaInlineable.
                                                                                 $unwrap(
                                                                                   o)) instanceof aaaa;
        }
        
        public static aaaa jif$cast$aaaa(final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                             idEquals(
                                                                               o,
                                                                               null))
                                                                           return null;
                                                                       if (aaaa._Impl.
                                                                             jif$Instanceof(
                                                                               o))
                                                                           return (aaaa)
                                                                                    fabric.lang.Object._Proxy.
                                                                                    $getProxy(
                                                                                      o);
                                                                       throw new java.lang.ClassCastException(
                                                                         ); }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (aaaa) this.$getProxy();
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new aaaa._Proxy(
                                                             this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                            interStoreRefs); }
        
        public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                     fabric.worker.Store labelStore, long labelOnum, fabric.worker.
                       Store accessPolicyStore, long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs) throws java.io.IOException,
            java.lang.ClassNotFoundException { super(store, onum, version, expiry,
                                                     labelStore, labelOnum, accessPolicyStore,
                                                     accessPolicyOnum, in, refTypes,
                                                     intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements aaaa._Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((aaaa._Static._Impl)
                                                                  fetch()).get$worker$(
                                                                             ); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((aaaa.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((aaaa._Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((aaaa._Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(aaaa._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final aaaa._Static $instance;
            
            static { aaaa._Static._Impl impl = (aaaa._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                 $makeStaticInstance(aaaa._Static.
                                                                       _Impl.class);
                     $instance = (aaaa._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements aaaa._Static {
            
            public fabric.worker.Worker get$worker$() { return this.worker$; }
            
            fabric.worker.Worker worker$;
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return this.
                                                                                jlc$CompilerVersion$fabric;
            }
            
            public java.lang.String jlc$CompilerVersion$fabric;
            
            public long get$jlc$SourceLastModified$fabric() { return this.jlc$SourceLastModified$fabric;
            }
            
            public long jlc$SourceLastModified$fabric;
            
            public java.lang.String get$jlc$ClassType$fabric() { return this.jlc$ClassType$fabric;
            }
            
            public java.lang.String jlc$ClassType$fabric;
            
            public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                   java.util.List intraStoreRefs, java.util.List interStoreRefs)
                  throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                                interStoreRefs);
                                               $writeInline(out, this.jlc$CompilerVersion$fabric);
                                               out.writeLong(this.jlc$SourceLastModified$fabric);
                                               $writeInline(out, this.jlc$ClassType$fabric);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         fabric.worker.Store labelStore, long labelOnum, fabric.
                           worker.Store accessPolicyStore, long accessPolicyOnum,
                         java.io.ObjectInput in, java.util.Iterator refTypes, java.
                           util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs, interStoreRefs);
                this.jlc$CompilerVersion$fabric = (java.lang.String) in.readObject(
                                                                          );
                this.jlc$SourceLastModified$fabric = in.readLong();
                this.jlc$ClassType$fabric = (java.lang.String) in.readObject(); }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new aaaa._Static.
                                                                 _Proxy(this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm132 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff133 = 1;
                                       $label128: for (boolean $commit129 = false;
                                                       !$commit129; ) { if ($backoff133 >
                                                                              32) {
                                                                            while (true) {
                                                                                try {
                                                                                    java.lang.Thread.
                                                                                      sleep(
                                                                                        $backoff133);
                                                                                    break;
                                                                                }
                                                                                catch (java.
                                                                                         lang.
                                                                                         InterruptedException $e130) {
                                                                                    
                                                                                }
                                                                            } }
                                                                        if ($backoff133 <
                                                                              5000)
                                                                            $backoff133 *=
                                                                              2;
                                                                        $commit129 =
                                                                          true;
                                                                        fabric.worker.transaction.TransactionManager.
                                                                          getInstance(
                                                                            ).startTransaction(
                                                                                );
                                                                        try { this.
                                                                                worker$ =
                                                                                fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    );
                                                                        }
                                                                        catch (final fabric.
                                                                                 worker.
                                                                                 RetryException $e130) {
                                                                            $commit129 =
                                                                              false;
                                                                            continue $label128;
                                                                        }
                                                                        catch (final fabric.
                                                                                 worker.
                                                                                 TransactionRestartingException $e130) {
                                                                            $commit129 =
                                                                              false;
                                                                            fabric.
                                                                              common.
                                                                              TransactionID $currentTid131 =
                                                                              $tm132.
                                                                              getCurrentTid(
                                                                                );
                                                                            if ($e130.tid.
                                                                                  isDescendantOf(
                                                                                    $currentTid131))
                                                                                continue $label128;
                                                                            if ($currentTid131.
                                                                                  parent !=
                                                                                  null)
                                                                                throw $e130;
                                                                            throw new InternalError(
                                                                              ("Something is broken with transaction management. Got a signa" +
                                                                               "l to restart a different transaction than the one being mana" +
                                                                               "ged."));
                                                                        }
                                                                        catch (final Throwable $e130) {
                                                                            $commit129 =
                                                                              false;
                                                                            if ($tm132.
                                                                                  checkForStaleObjects(
                                                                                    ))
                                                                                continue $label128;
                                                                            throw new fabric.
                                                                              worker.
                                                                              AbortException(
                                                                              $e130);
                                                                        }
                                                                        finally {
                                                                            if ($commit129) {
                                                                                try {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      commitTransaction(
                                                                                        );
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         AbortException $e130) {
                                                                                    $commit129 =
                                                                                      false;
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         TransactionRestartingException $e130) {
                                                                                    $commit129 =
                                                                                      false;
                                                                                    fabric.
                                                                                      common.
                                                                                      TransactionID $currentTid131 =
                                                                                      $tm132.
                                                                                      getCurrentTid(
                                                                                        );
                                                                                    if ($currentTid131 ==
                                                                                          null ||
                                                                                          $e130.tid.
                                                                                          isDescendantOf(
                                                                                            $currentTid131) &&
                                                                                          !$currentTid131.
                                                                                          equals(
                                                                                            $e130.
                                                                                              tid))
                                                                                        continue $label128;
                                                                                    throw $e130;
                                                                                }
                                                                            } else {
                                                                                fabric.worker.transaction.TransactionManager.
                                                                                  getInstance(
                                                                                    ).
                                                                                  abortTransaction(
                                                                                    );
                                                                            }
                                                                            if (!$commit129) {
                                                                                
                                                                            } } }
                                     } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 126, 113, 66, -111, -74,
    127, 96, 8, -103, -23, 117, 42, 55, -25, 116, -80, 86, -6, -120, -95, 92, 5,
    94, -55, 69, -100, -123, 33, -32, -109, 40, -113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1445481719000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM17Dcwr2VWY38v+JpvsZhPSJM2GR/Ia7cbhzYzt8djZBjoe" +
                                                                "z3g8f/b8eTxOYRnPv+f//wdCaStIBCVQCCmRgAppWyhdfoRKK0FTIRVaKIiq" +
                                                                "CCFaqRChVoBSpKKqLeofnbG/97PvvX0RVVCxNHfu3Hvuueeee8655+ocv/aH" +
                                                                "g8ezdPABSzu6/q28ic3sFqEd18xWSzPTwHwty6Su9RX9LY+tP/v7P2K8//rg" +
                                                                "OjN4RtfCKHR1zX8lzPLB25iTVmpAaOaALKxf/vjgab0fSGqZkw+uf3xRp4Mb" +
                                                                "ceQ3th/lV5M8gP/7hsBn/s7XP/fTbxo8exg864ZiruWujkVhbtb5YfBMYAZH" +
                                                                "M81QwzCNw+DtoWkaopm6mu+2HWAUHgbPZ64danmRmplgZpFf9oDPZ0Vspuc5" +
                                                                "bzf25Ecd2Wmh51Hakf/chfwid32AcbP8ZWbwhOWavpElg28ePMYMHrd8ze4A" +
                                                                "38XcXgVwxggQfXsH/ma3IzO1NN28PeQxzw2NfPCV94+4s+KbdAfQDX0yMHMn" +
                                                                "ujPVY6HWNQyev5Dka6ENiHnqhnYH+nhUdLPkg/e+IdIO6KlY0z3NNl/JB+++" +
                                                                "H2576eqgnj6zpR+SD77ifrAzpm7P3nvfnt2zW3/I/eVPf2NIhtcH1zqaDVP3" +
                                                                "e/qf6ga9/75BgmmZqRnq5mXgMx9mPqu96/Ofuj4YdMBfcR/wBeaffNMf/ZWP" +
                                                                "vP/nf+kC8xcfArM5nkw9f0V/9fi2f/M+7KX5m3oynoqjzO1F4XUrP+/q9qrn" +
                                                                "5TrupP1ddzD2nbdud/688C/Ub/kx84vXB29eD57QI78IOql6ux4Fseub6coM" +
                                                                "zVTLTWM9eNoMDezcvx482dUZNzQvrRvLysx8PXjMPzc9EZ2/OxZZHYqeRU92" +
                                                                "dTe0otv1WMudc72OB4PBk90zeHv3PNE9P3L1/sF8IAFOFJiAmzmmZQHLNIqP" +
                                                                "UQ0sI70IzDDvBCBKQ9P3AS32AdvNgU6dU1cHzFoLYr/b0U5LdPMYHYEs1QGt" +
                                                                "+93qIOI/I7x1v57nqmvXOlZ/pR4Z5lHLun27kqHF1u/UhIx8w0xf0f1Pf349" +
                                                                "eMfnP3eWo6d72c86+T1z6lq39++732rcO/YzxQL/o5945VcuMtiPvWJkPnis" +
                                                                "p6Wb/plei251dulWZ5deu1bfwn5o/Q/PwvJEdtaqOyOe7gj/qB91Fq0eXLt2" +
                                                                "pv2d58FnEek22OsMR2cbnnlJ/DrqGz71gTd1shlXj3Xb04PevF9T7tqXdVfT" +
                                                                "OvF/RX/2k7//337ys5+I7upMPrj5gCo/OLJXxQ/cz4g00k2jM3V30X/4hvYz" +
                                                                "r3z+Ezev92bk6c7C5Vong525eP/9c7xOJV++bd56VjzODN5iRWmg+X3XbZv0" +
                                                                "5txJo+puy3mD33auv/1Put+17vk//dNLb9/Qvzsbhl1pzo07qpMPfvd3v/3V" +
                                                                "3/227/nog303XvySXVWUemZ6M+52Tndjzf/TYon7jj8Lkf/oaPyRMfLVE/Cl" +
                                                                "7hdfFKCXoPt27XxcfEyMf/C3fu0PxueD9PbJ8uw9R5Bo5i/fY816ZM+e7dbb" +
                                                                "7wqklJpmB/fvv3/7vd/3h5/8+FkaO4gPPmzCm33Zs0Pr2BCl3/pLyb/9nd9+" +
                                                                "9Teu35XgfPBEXBy7hZ0p/2CH6EN3p+oMnd8Z246S7KYcBpHhWq529M1eG/7X" +
                                                                "s38J+pn/9OnnLiLtdy0XAUkHH/nSCO62v2cx+JZf+fr//v4zmmt6f9DeZcdd" +
                                                                "sIv1fsddzGiaak1PR/3Xf/2Fz/1L7Qc71e5sb+a25tmcDs7LG5xXNT7L661z" +
                                                                "ObqvD+6LD9Tnvved2x/LHjzJiN4luKtvB+C1H3gv9jVfvJiuO/rW4/iqh5iu" +
                                                                "nXaPKRj9WPBfr3/giV+8PnjyMHju7I1oYb7T/KLf1UPnT2TYVSMzeOvr+l/v" +
                                                                "G1wOwpfv2JP33a/r90x7v6bfNZldvYfu62++KPdZDuprg7ivvHwe8aFz+VJf" +
                                                                "fOTMo+t5b0V7Ly3vELthZzrPw/LBk1eKeh7xFfngnRclunVpvqWcX33fey+a" +
                                                                "0pfI1ZSdMD4O3hrdGvXf2MNnflNf/eq++Jq++Nrb87735Os3byv+rvMWO6G5" +
                                                                "eZn7NinPnaWn592ti1917nhPPnimV+PY1/Le/tUPoauTiLfdHcxEnUv2Hf/h" +
                                                                "u3/1uz74O50EUIPHy353uo2/Zwau6H3Wb3vt+154y2e+8B1nheu0bfud/qu/" +
                                                                "1WNl+gLPBy/0ZItRkeomo2U5e9YQ07hN+YOSuE3doLMN5ZVPZX7qM9/+J7c+" +
                                                                "/ZmLFl4czw8+4PvdO+bifJ5X+dbL+rpZvupRs5xHEL/3k5/4uR/9xCcvjtnz" +
                                                                "r3ej8LAIfvw3//ev3vr+L/zyw45kP7oct/dv93u+iZxka/T2jwERbMzvoOM+" +
                                                                "HEcijqIRBUDDGY8vYVhYCzLO+N4EpRaeLfHrdjcdScV4Jdh6YKqBkBeTDSnm" +
                                                                "NkXEm5kjk/aOTBAXd2vX0kYjsUqn6UKOlGAM7dR6m29PipyUsjTMSAUQjVIf" +
                                                                "HoBGKsH8EKT7ctNSQTufh0Df3mhEG69Nx0NoGaRbRk3DQyT7qyFPzxLBAHkw" +
                                                                "VmiVchh6v8QsUrdX1ujQGuDeAX1zr3inBqO9gJSTSZQZseMJHERvhB05Y2yX" +
                                                                "w3O6xGMunp8k8RQfCY5KFEclqAM+kkgqHsuJs1fdkJI2w5ReKVtnqBJ55PE1" +
                                                                "hye7VIxJQezEpxGwbk6BbeOkxVeN0rJJIXl73pEKA9voyjre5CC7SLdjwMj0" +
                                                                "3ORGRS7I+QI3s3SVpbwT0/xUdgWvJQ47H4RorZ3twVN6YDfhtJSVHZjITOeY" +
                                                                "gLt4exTXsynoDYM1yGg+upNEF+EhSZNOdCpN8XXuBukq1qoDVAmGtmFn0HSj" +
                                                                "07SKkYzCzzkBzHfQRkxjirHzdUJNgGTCIioGBZFpzRaSWKkjajYEjGJqdc6K" +
                                                                "JpT40EvjOueqg0DQQSCBOYcPU4E66iPxmNUdx3Ye3vgbEaIb2aB8/kDuwchP" +
                                                                "iiiQVyTFKCe0LQIeFNdakrNbHHc3J1mgHEnJ+N1adRvVDRK/zAha8ETZ14wW" +
                                                                "Et18gfmStMb5/HSsVqwMhWBU0PJ8Ie0SWxFLMN5QIK5sbfdYI0w65Xlaye1A" +
                                                                "3o8P4lBZWgfXc1b+BjfW+Uw+SP6czY1VtOWUDWavDcg98iKBrdbJhFdoZSWu" +
                                                                "LRiem5UQ7hTctlHeq1pQAjl2h5dLi4gbc3ZylV25j5VSlAXa4TDdl3lyOoQw" +
                                                                "W3bmvA2LorFKfdzeZuyUKOJ2GU1pNVrvpYbn5h5XmCEAME6IB74Ew7Y+0ciW" +
                                                                "CMWSWIutKk5KIBTHkyZahXOOjJeCDLHc6iCVE6HhSrjbo84/4FCVzU4kVo2Z" +
                                                                "dHJguXZeT4aTrVhApJjAquzSiahp3GiHNcnxkGTiNoZkPxUreZeIAgGNsXi2" +
                                                                "9zJ6uleMkDqqU4qSm3jJKjFdTghs4ZaGLGABjXqSHEAIMz0IyV4psJGa7hUV" +
                                                                "5GXPAWLqNMSKU4kaSMnqoWjSpUZlOz+CdnRsMrsiiaBk7k9yMQ5GrpQ0Sx6f" +
                                                                "Kj4lmzQWZ8LxsNxubKeF9hrByVNnleyGazndauTSAFkR4cvxesoDBT1baYuw" +
                                                                "ItK1gyfTLMc50YOw6rhbIWbnPMq0cWC1nUbWawxPlAmgeCVMGlPWIIygxqUF" +
                                                                "npgwOdxs8NX4gBxyZLasZqF0wo8Lwi5EauuhDQQOJcQWTBkcxkdpSmaR547K" +
                                                                "kV8ifCsqqL2ZN5EaRZG03RG8liOsh3uktmjQKrCd3QyLNDqw10hnF2amRuSj" +
                                                                "iRbKRi5DhSTN5aUMGggfKjscnCfsUaQh2rFCemwU4V6bEb4AJhvK8ZuT4kXU" +
                                                                "SleyFE7CQ74GOXRUOXXkrdK9uMLy5WSsOJRLsyKvkmt3xGQLgTVgFB0t7KRd" +
                                                                "0sBmYgqjvXUawbNZSW7m05M7EvUIm4nqGrMINyfmx8KNaVFIFqTFS2pGn1xt" +
                                                                "mzbTtRcOk+MsOIitiwW+le5nrMiJbrZCmznhMyM5SXUKOzVNzDHMkdvvOH2y" +
                                                                "VgKc4tYblVeplcUL7nALaxFyDOc0YAVC5MuWnpjaFEVLa87ueXnTavWO0r1O" +
                                                                "wa0RtAPaCdyuRnPMnqJtHYm5zB9yggc9brUTIwOC1VJOUxg4ABAjsyItwTnH" +
                                                                "smMlZOjJcIaXU2K6ApghSso8vopoE47UmIwp2/C8pjskRzNg2KyORspaU4Hk" +
                                                                "QFpMaKPIIifBdjUCp1LsNUkgUZDOKvJqtkw3Hq9yThVxzU5QhTxoDKrQfGLT" +
                                                                "gHS9niw5AJGK0SQnx3l5OHHJnsc11QnldgrU+GQCbkPPyLdji4ppjaZcCtuE" +
                                                                "FrMK9INrkTNyBtUG0SboqaiMsh7BQ8StnEpFVwnOmdoI5w+mvbAdrTHkCNBC" +
                                                                "tS3ReVCFHCHx4HABiLCqIiiPGt5OBVk3H+MFbZ/AI5nz+91xii3mApNrDsnL" +
                                                                "ODzlh2NejZX9umKLvZnNFq5+RIghlcIUoDctLCudPTOdQ3nYiGszaQ6OV7aS" +
                                                                "OF9SfhKg8U4MKDTCSH63MMS14RFBM/WW6k7Dq8wVAdIB6CbgkZBnCjdamqqm" +
                                                                "7FE8mNTTBGWlSdQgXJEmATuWIqjWKb3hE3VBY6uZHdSjygztqbLmRiKlKYnn" +
                                                                "KcCK1lbEJkuWyl4MtWZ3XBxhruN6bVVyLG8aNOYDZxmSRraf7luoXReZsXCX" +
                                                                "ApegjIMx+UScHIbLseUjTTWC2pUu+PvohCKELyUn1UvD1NtYgp4xnFi2kUat" +
                                                                "iYCaaqWzPQljbxqrRgpvEcaOEpgaVrl3UlRHNlScphiYMCRlE6wXK0MQk0jF" +
                                                                "03VxCsfIuIysbedTWXaDOOBpIfNrvHMKCp4UrHCiKAY3Hh/mFF/uJOUYr4VJ" +
                                                                "M0X3VIRMhurY0tWSWgwxrbT2dqnuS6lNgOU8GCFKw7YzOLNXcL40IvhETNiN" +
                                                                "MCEK1Qeno7CqOMPc5IQ5mxnVpnKXATn27RVXLutgegoyhfBbfDfZF9MpSZcR" +
                                                                "WWR17dezNA3DxM02xJ4JlNMi8dZQscBjbx4RbUQXHd2kkwXtOEg0Lc6ZxB8e" +
                                                                "5TZssogpgYCClRVz1AJ23jKRPRZbRVo6DYfl6Xw6rFlGqAjHmCg05PJjDkw7" +
                                                                "T/xo2fujvIh0Y49z8lpwc87WzJ0P56NyqTk+Gh59kemsk5ki86aejXSgVNOd" +
                                                                "RIL2KmtMvnKZUMVmgsdpAq7RsNudBEihSwrkYzmVwXunVIRhOzlt9dPaCDxn" +
                                                                "JM3oIQoMER+Q4b20pdyO8jo9HZYFRR6B2Tg3YY5Bkrm9dk/18bQM4pbLl8C4" +
                                                                "qNKtX8wnk9WOzog4SDg/EEbunsiVozxbzFCGpeaRz9Ht0S2LoMXlaTEGCpYW" +
                                                                "48m+ClKyGbf6cOLBxebEN2a5co2yqtfi/qSu5mltRqALkqt0rCbuQlgxngxJ" +
                                                                "7QbydQBkOX5Y5JQFAi0Q2YiDoO4SUjhEFqIm9qqJ1AlSjm/ryjmsRpmcbY/+" +
                                                                "aIGEgMwN6fGStjU3X0G6JngEJR4P49IC0Ejf7CweMU/tysZoFm/VWUCMoK3N" +
                                                                "LIuVWhLciTjZBzcO13ZbksCiFSnDgG1gwY53ytgm9A0m7uHJlBtJC0yr0ChZ" +
                                                                "s7Us2jo+p/e6B0Rzwm1HIAnu1cMpzWc0pTJpHFkHH05ZCDXrDE3HpwZYcBiU" +
                                                                "HFOFLjmchDiJ3R03dSXOF3opB6i2RFaoB3kHjtsfCLZGA0rhsGweFfUhQtSi" +
                                                                "ALO6HBoWNGnVqWmUYornVAzxjYfYlGCVgr4l9ws3rBiFW0TOSM2zGo6gUHH0" +
                                                                "1AVQOC5ZawtzJ/FY2skG3RIGARq6TpszhgETSWJ5dpauxpHebV00HR4lwjzh" +
                                                                "oAwsGgo5Hjt1PB2JrStxNrEUoe1sQoxIrLMf6GRzwJ3NorUE1CLHc7eFkU2z" +
                                                                "ZXC6OFhtbTHFwTCA8fIQedWJFfG2E0V2NF2d1DXCD/Udc6QCcIPxszxPJuAS" +
                                                                "2RmNNh5xc3g8bEgGTU/QTDkmU7uNBEY2DY4ZZ1XGOPBsbgA1z8z3SxQfH5It" +
                                                                "ZRp0vgnbNaiYVaBVtg9zamWLXg5HWDvCh3TsjubHoQkh5WmazMWREjaHCdmd" +
                                                                "/dP9ifOL45RsgGnYzJrOJi5kAKnXK76VK4OkFwpUjY6sFias6GKlK4i+pqQS" +
                                                                "MdLlIGxc12kaSDZVkMHk5RGPDlNw1dm2EDjkzZC1pHHprdSmUklzTGnlBPIN" +
                                                                "3UgEbz8Xql3lpnPlMG7xbhXdBU1fjnBKNwNl0sy7+0/HCE8S+DEZCsAwjquO" +
                                                                "am2mp0De1tPZwd/DlqHuZYc8+fYItARQ3wNLiu48FK0e8XnMdjYwphfgRA1A" +
                                                                "LxslpWOTxxCfzWvkyLYbEcuNGmGPFq1HSIlZx+4ie5x4Yxocn3als1IIjcaZ" +
                                                                "erJdtmPBa6YJVSkEyyP1/hAYTQttkOOpZQtno7p2OcMWC4jYLTdVFQCzNVgj" +
                                                                "IsqCklQSQ5AYzRbgNISXhwQdjmcqkWqdm9D6w90xbzflQbPGarUmp6uJanQG" +
                                                                "apl0l9EdaaTqmsfm8NTcjgR0mHXeHUxwE4EwTidY81lmQoImWBWFPi1KIjJr" +
                                                                "RnQW2nbCLPh0akflKQ+dLWvtD4tofVCX6vi0BCogqcI957OlLB6UOJJkQMKa" +
                                                                "NV4aRUQc+CVDGIvCBjgLy2C2AfOQ06wSkNCptKOyUKb4YafqO76YV6GBqhLV" +
                                                                "6p3Rboohq44bY5xBlL5yYRTQAm+qT+1pyGwAXl0NqWI+VvdH29zXTWOpAG1v" +
                                                                "nWUzdzbbGra3uwUJp2IIJO1860LGLEYcFWVLqOTXhL8gypRVUJilWmuTdGdo" +
                                                                "WQ73fDVxQw/l8yU0WkMnSXeOsKF3dlk3y7ExhibN7LRQsjKshjZ68oAhtN9i" +
                                                                "eTA0/NWJsU6wZ5zsChIlQA9xasaszBKylovO1wdtg+NO2mHJ4rzmSgIGm9lo" +
                                                                "v3KAxWxkRfsANitjSszAFcRvuSHgeNrYFeA9G1qoDLrbKT1l2GZsz0DXcsek" +
                                                                "2WKshrRUzmvGkcEBuoomEu5YzRZsITuYAlt2oS8bvJgKIbAd8ogRDr1ouAKo" +
                                                                "qU207FpHWsBhtxSbafvInm7LrcSiDqBFPu4KIHWUhxucnoUjwC1RoGI2xnRr" +
                                                                "WkNh1c7ROSAIzg6FrK1mVQmK7bbyJpqma75WoDkw3ZRGlmBLA+Y4YhvJ+HY8" +
                                                                "GbVSSc8W+ajl1g2YjNdNue0smnUwHKuALJCeGTOktdqxmZXxnmO3YpMdhhBk" +
                                                                "7egoSOMVP4rpFlzvHALKAWjOauKmxTKCDVfyCToFW3RVdofeMtjMFnBHbAYP" +
                                                                "uzvCiNzUPN3SmHeqahmnEKjcym21AWb6EoOA+WnuLJSKGBdbEADtZUjoI8lH" +
                                                                "3B3k7IZFxph6tMOXPCCjE1/ACJAebRaEG0i0dmAMBUt1wog0R7DJ7t4Ag8ho" +
                                                                "mmMKggSumOzNadwq2KSkchxbbYeBC4meXHJOUC522nKls7vCqIWVP7YPo2YE" +
                                                                "FpGkG6GvVsxoXkxQprWCTBg66EyrdT0E8ZE6ZlsPdE5MYQtJ3IKVTSJVdyHh" +
                                                                "u21hKrmT/ZWJia0uzVus6a7F8g4gxyxHzHR9GyTddX5lzu2RUwJTXFjn8B4R" +
                                                                "tT0rxGQrpTyITFGC20KeNZxkTJTV+22mDrmDJNiFGp8CvUUmynQaMKSGLrZL" +
                                                                "lE11lPGUejNJ9rM5uDOFMpnUB3hEo1bKtaeFcFCF2UlQkOzkB6pHYjrjUrOD" +
                                                                "yGNG56rQa+G4pSVSngZrvEDs1pbUMWgULO6ZFG+QFbzeL8nGSoXZzCXXW9Oe" +
                                                                "ugnR2UcvtRH0uOVl8uCNbWCcQKAFKRi+2M3gqPPe1iiTKht9u9Gg1vR0zoGD" +
                                                                "YNSSOOPlfj1aarBRKDI7gdqlOtFhbjqjonqvOEPtIKWkAMoyvajXJ6JaKDNy" +
                                                                "mLdAZithZQtNSVe6QvDokR6uwFQZC4cDSCFTCrAXa264KfPFTo1Az5O7Q5iS" +
                                                                "jmlnekZ8gBjDLb5XJ2aZZ35VH0EuKucVhQw134GXjuVyx6bEWBXmGWzfJPv6" +
                                                                "OA8lo1lpHgbND6v9xHcQKmQBqmg1Vlo51oKvgAYLIUTWnW2BqhWAoGQ+Gedc" +
                                                                "tdhM+CUN1e2+b5gvTse1g8DYBjZp/mRvTXW0IRsUmG98FSLn3mrpzptwxB9B" +
                                                                "vOZxxj5xUWu3vlIIBzwzuKaE9lwClRRw8sN4bwvkaH88CejE1sYZMOa3yWzl" +
                                                                "4srs4DGVi2JCOfXUobgBIxjClQLFuEpC");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("czjpYKEajkOL4B2f3cPlEotQQg6rznIjdgSVzBFZSfyYRzeWS8bJZm4xZA4w" +
                                                                  "dRvHaUCsN0FbdPfM2D3lpml6SGKV1U5pzCqfyqfpal+EUGzBeL02W4as9RE0" +
                                                                  "0Q9lsHUmqhim/EqOGWXmdxxbDfE21MtlRBxD9RSPZBtbL/Pp2BjpU8Oj0UAp" +
                                                                  "sTVK0VO7lsDCKyRUb/D5YWwvraU5B0tbMmY7jJTNJXLE1iwtb3fBilAAA4ay" +
                                                                  "IYSEBj3GOx+hwUyyEwtANSoM0G2c9QQjA5Pu9LVBD5DKEgJyyq1aCPWlxt2b" +
                                                                  "K1RbOCG4qTxt5fgz5dB5B7GrGLDGE9aO4sXR2HYR4LRcjiqmwU6FwXKTPdww" +
                                                                  "SxXEm4VLrtgU4Y3xAcdpeRYHy6pBsVEZzSSisAyFWPIof6gsfAOTI6qdaG6N" +
                                                                  "6zveD/R5NVzOfHbX8oVDO2ktr/f++LCqZsVULZAayfJijdXGdjMJ6w3HWO0E" +
                                                                  "H44UyHPWZVji+lYmUezk4TBszjb71TxiMiUqLQuEoKUA5nVCoxOTpilFrdcb" +
                                                                  "aiV4J9c4QVOCWC8AK1xlQ7iIMDezQmrRZBRNS7rP+nAztZtJqhtsuAihPKeO" +
                                                                  "ajQOTWu0QvWxW6/2pWjsQWkYAARsyPAszI4Q7UDYBimoOdXybDW1StCqNjwy" +
                                                                  "CeYj/IAiQ+IkAPrCmSxrb+JANTprzH17goMSa9fuwdp6lKM58GlbTLxJSNnl" +
                                                                  "kZhGDrhBRqi2hUO5Uzlnki8X9j7qbh47hAFapC2K0G2xkgHmu2VWDWci4Dqd" +
                                                                  "twxnkGUPMWcyzsZZC2w4deOMx3NcQYaGOcN80ynB/R5nZhM2oacBG9bOOBzD" +
                                                                  "oZYiopcSwQao56ElzkO+bWbGkHQNYb451MP1XEB3qglpCiNsSdH3ho3iodJ0" +
                                                                  "Q85ln7PzPa+PJyXUTqMpXc9daw8SJwBxljbjqJPVTBr7pW3IlDgndqfForsE" +
                                                                  "AzwjkhE92m0zAF8CU96rYBRFP9aHYfZXcah3nsNndxKULuGnvo88x20u8dEP" +
                                                                  "9cWHL8H/wdXviavnB67en+1733GOHb7znqDqoI8yvfBGSUXnCNOrf+MzP2Rs" +
                                                                  "/h50/SoyK+eDp/Mo/mrfLE3/HlRP9fGqB5LW2HMq1d0w6xe++MIc8/6jfYlX" +
                                                                  "feV9M98P/Q/Y13559SH9e64P3nQnnvpA/tbrB738+ijqm1MzL9JQel0s9cYd" +
                                                                  "Xj3X8+A9PfXd8wtX75+7h1f3hB0fjLbejXnWdzBe7zE+e4XpZ6/eP34/9+8G" +
                                                                  "va/dyVh54T7mdTt9jqZfIoq/9iN//J7P3/yDP74w7v48sHsA//Nrv/PFX3/r" +
                                                                  "Cz9xTjp5rE/0OS/8/gS6B/PjXpf2dl73M3dWhfSr+trueVe3wl+5ev/zfHD4" +
                                                                  "cqZuLKPi6JtbN7SvcqH+DLGf10c+sAt34qBRH2m/77Ov5F9KFO5Evp/wzdDO" +
                                                                  "nTMkfxXR7V9SPnhTx/u+Gj4c2bUzsguevqj64vwZ1ncovn6Z93YQ/R13Q9yY" +
                                                                  "H4Vmn81xDuffCfifAdzo1p0UzdsQ9UO5cLws+zxrX7z4iGSNb31E3yf74m/m" +
                                                                  "g8f1nqyHxPwv+RIXSs4jvDfQkAdXite6Gd9OyHnvefDf6gszHzwWaG74MN4/" +
                                                                  "VkbuRcaPr7cC7+6ed3QcffvV++kvixXoMT11eV/7n29sBe7bzfdfZWecV5mZ" +
                                                                  "epG6edNH/y9JVm+cIBGey889nIP953eeAf5uX3xvJ4hpcebdd9/HkOcHV0V/" +
                                                                  "dPzo7fcDDPlScvHqI/r+fl/8cCcXfcLWzbv8vUvEOfPyQz3hV2fatUsS28/9" +
                                                                  "f8oj++iNpNAyNymi3Hzxkpx1oxemGyfXuumGZeSZS9O6J1vwxZdufGPuuNmt" +
                                                                  "8xJffOnlT7wUx/eK0huc269bb9/7Y3H8CE7+xCP6fqovfjQfvPuNaHzY7vfy" +
                                                                  "eq7cx/hX/1wwPnXLbuZ7Oe/mPadvfPzrxBv3c/gB05oPnrzCUL+e8U8+jPH/" +
                                                                  "6JGM/9lH9P3TvvjpfPDUbRIfxui39eAvPMDoa5/888DoSx7ZjStBP0aRb2rh" +
                                                                  "meO3Xa3IevHj5ySzG5d5vlELjp8426xL7WLZz/Ub0dcR3R651o0Xoxsf+9iN" +
                                                                  "sPD9l25cfLNutJ+ZLz/U5DHa0fTlPpdJ0ztPJcMcU/defPh0twddvu6MvHyG" +
                                                                  "Uc/T7gTq2PTiSx+5EZ2bX3r5ioSOKvfOqm70nHlQWx9l+M9nzKPPrefvXeCF" +
                                                                  "NQ+342dkv/AI2fpXffHP8sHbXr8ZDzvtnrzat3sF73Y68LtvC9zthr735pdl" +
                                                                  "zf3nL54BfuMR6/jNvvjX+eCt/Tp0Lctv9ox/w6Pg+QcVxf/zoChXGnLZ0htn" +
                                                                  "ZT8LX3a2SucD4GYRGx1x5+YbH3sDdflTyO/LN67wXrRiG3UENF8OxLf0KLQu" +
                                                                  "6F68oxz9VP/PZ9e/e6QJ/e1H9H2hL34rH7zlHpZeBOvq3wRxfFfO3luk/d+a" +
                                                                  "Xvsvf+GPn3hK+sJVYufgxjcni7/9j//aNzz1uT8oPoz8Xv5Tu//xqR/+q49/" +
                                                                  "/S/jP/CtX/WF733xu/4vxOEUIm41AAA=");
}

interface internal extends fabric.lang.Object {
    
    public fabric.lang.security.Principal foo(final fabricated.util.List storeList);
    
    public internal internal$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1445481719000L;
    public static final java.lang.String jlc$ClassType$fabric = ("H4sIAAAAAAAAAO1ae3QU13m/uxISEjIPIQSY1yLJGGGhlcAIjLB5SJYRXkCV" +
                                                                 "BAnCeD07e1camJ0ZZmalxYSWOLVxoCY9FD84DdjOwY0fNLg+pW4S4zhOYrCd" +
                                                                 "R10nDiHHdo5zckIeOMGnbus2xP2+e+c9u9jQJH9lz9k7M/f53e/x+777zRw7" +
                                                                 "T0YZOmnICCldEpvNHRo1mrvYQ4+gGzTdIQuG0Q/VSfF8zUtH8/ecXRIlJQlS" +
                                                                 "JYgiNYweVZbEHSaZntgqZeJseFwWUlSOd6hKhre2Q29RUFRFEgU5qRgmGZvY" +
                                                                 "KgwLcYWa8Q293dA+SRGy1NAEkXZSjSppqogShY7jeMecKcnxPmq253UyAxay" +
                                                                 "6FwjZTiRsM4OTuMHj347/dAm7e0oKRsgoyVjg2IIGZogFULOHFJ1yQRax3sm" +
                                                                 "TUiGCeuPEVWgSxckxTS2k78koxJkvAQ1gmJKgknTXbqaNcnshAYLDcqqGad5" +
                                                                 "M64JupC19tzD+AQzlbFae5LRmq4OS2mqm2RWiEM9VlsCn3BrMXt6a38hCdx/" +
                                                                 "Xfzgg7ePf6aEjBsg4ySlzxRMSQROm0DPAKnK0myK6sbKdJqmB8gEhdJ0H9Ul" +
                                                                 "QZbuhI6qMkCqDWlQEcycTo1eaqjyMHasNnIakIhr2pUoMsaSnGiqur2dsoxE" +
                                                                 "5bT9NCojC4MgpFqXLXx7XVgPvKgEdlI9A1K1h5Ruk5Q08iIwwtljw63QAYaW" +
                                                                 "ZynIy1mqFPXDJNVccrKgDMb7TF1SBqHrKDVnIoOvLjppOwpCELcJgzRpkinB" +
                                                                 "fj28CXpVMEbgEJNMCnZjM4GUrg5IyWsh65bt36msVqIkAjSnqSgj/VUwaGZg" +
                                                                 "UC/NUB20nPKBVfMSDwi1J++NEgKdJwU68z7PfurCiqaZL5zmfaYV6LM+tZWK" +
                                                                 "ZlI8mhr72vSOxhtKkIzRmmpIKHzfzpny91gt7XkNMKDWmREbm+3GF3pf2rT7" +
                                                                 "SfqrKKnsJmWiKueyoEcTRDWrSTLVb6EK1dFEukkFGG4Ha+8m5XCfkBTKa9dn" +
                                                                 "MgY1u0mpzKrKVPYMLMrAFMiiMXAvKRnVvtcEc4jd5zVi/cbCfzYhpdOs61Um" +
                                                                 "6Y8PqVkal4whmsnEO3VVS6n5eKcq5rIUDBkgSFeoLMcFTY4PSmacgxzYrpDV" +
                                                                 "ZJAo2IVIU2oqbuhiXIBfM/TQ/kjz5nE/NSORCLB6lqimaUowQG6WDq3qkcFM" +
                                                                 "Vqsy4EFSlPef7CYTTx5ielThYCPOEAXZTw/ihHfswdyqmy98Kfkq10EcazHS" +
                                                                 "BDxEe1QEGUioQktqBgRuBgQ+Fsk3dxzpfoopTJnBLMsZVQHEL5VVwO48iUQY" +
                                                                 "/TVsMFMTEPI2gAuA0arGvi1r7ri3rgT0UxspBTFh1zofXHe4mNLN4FUExX59" +
                                                                 "uXbH/kXTlkXJqAGAXaOTZoScbPZ0rFJzCsBTjVPVSwG5FIaXBTG7XBPZGJNM" +
                                                                 "DqEtR1kYpruT4LB6YGhD0KQLkTluz7n/PP7ALtU1bpM0hDAnPBIxoy4oMV0V" +
                                                                 "aRpQ2J1+Xkw4kTy5qyFKSkHmsDcTdoa4NjO4hg872m0cxr1UwPYyqp4VZGyy" +
                                                                 "uVJpDunqiFvDNLEai1qulCjRAIEMwm/s0w6f+e4vFkZJ1LtKiQdU8Hkig48J" +
                                                                 "rk7065SCt37zoZ6/u//8ns1MIaBHfaE1GrDsADABtwlMu/v09h+9/dbR70dd" +
                                                                 "JTLBp+ZSYFJ5tvqkD+EXgf/v8Y/IgBV4Bf/QYaFSzIElk2jv7D36zj0HtKV/" +
                                                                 "SKvuQ0fRA2ayFqSE5r10UVtTa8vC+a0ti3Gvc1xuACTKAMvALKNhg5JV01JG" +
                                                                 "ElIyRZv53bhrWk/8ev94rlMy1HAJ6aTpoydw66euIrtfvf2/ZrJpIiK6ZDfE" +
                                                                 "cbtxnJ/ozrxS14UdSEf+0/8+49Ap4TAAAKC0Id1JOfAyCRCmJUsY9xeycnGg" +
                                                                 "bSkWcwEago2w3DQXAJghQlQj8ZAnKda+VxfXujp/whSsUnQiRpwGdjIG8WrQ" +
                                                                 "DjFnhEy6221GI5scXMlapXRLLP1erG4zs6wxaWqIuqTZygveptKQQMLAVJpm" +
                                                                 "gAAQaKprgElO0KYLiiGDNnEQ6WeNN+c1HUOGYUFn0uCmkUdDcMjowVgwKS7e" +
                                                                 "t0dX6/e2RS12VXNFBgZNJVaBHm2OfcXWyRqWU/IQrqY5+MU0MSbbqLUSODvf" +
                                                                 "Dtpt8pzA3WJwvwoVkoyUJsVVzz8SOfvjt17n4UM9I9IZ6Bli7y0pHp704HPV" +
                                                                 "Tx1YyUfM8o8I9V7W0nFP8vp/+k7UsvbJQR+xWjCGABXOyD8cuP/NeTP5rB7U" +
                                                                 "sNq/0nn3/Q/867PXczdShQxavoKHAKhOM4NC7qUC+D2uBUnxvSNnae+iD97l" +
                                                                 "kKWOKMHQWwPfJkqagOG3dYdRu85mQc4mgKopIZ21pm+775Hj59/qWcEMzaM3" +
                                                                 "GFuFwntLMR20XcVu1/udokNPc7+qOSQlxdtr/+266c9t+qyX+YEBnt77n/h8" +
                                                                 "+W+aPniEbdvR3vqA9joDLqnBWK7g9DIg84ndS6RX+pMnvfn908Or3y2sXYVG" +
                                                                 "LF9Q8/y5KVN3Mn3R2No91qp46dcKCfsTcIJzhR1rTrz4tfLeVzzCZhIEFoyw" +
                                                                 "jlyeWK51BTAAE19TiJ+rVNNUsx6u3lh/dmv7xdf+2bbbv3C40ujfYGCkd5tl" +
                                                                 "8746df+Pd6+359jAt7rZs9UtvGqZlmc2L7CnTlauDhoAVt6qsZn44Wiz5psk" +
                                                                 "8LiJd5U4ezWHtf5Hfo3ZmI0Pjay8DotmD9C3+HvCabzYYYgd5I7edfBIev1j" +
                                                                 "rVwrqv0HjJuVXPYf37j47eaHfvJygWC1wlS1+TIdprJnzagRTACsZedEN4xa" +
                                                                 "/Ghnw/QXt+//wwWTlk8oFDfOCuw+SMwTa4+9fMsc8QDLmFghY+js6x/U7uUD" +
                                                                 "YAxfFTmKNWOZFOY4/qMC5XA1/OvBb9xqXZd6/QcP8JhIsWhyhkZx6GhryA3W" +
                                                                 "daFnaMDfR5yQ3uNnGTdomh9QH3v82Jfaq554jJljBTNYcICmxb/ROMJ+5vuY" +
                                                                 "4BAzC4mJW0TcYV17vPuAda8OosFKfdBy8Y9f9a1Xz0/pOs1cfFSUMFoIReZp" +
                                                                 "WozJOQ2iOa+wo8MS5rQCU2wUPEE99rzJ2cC8QhvY7HfkvThdVtW1Icny5DE1" +
                                                                 "E+PxekzQB1kcGjMgCmaxITbyLAj0UWNzU0gfTceElDpMY6kdMbm1UXOwzMGi" +
                                                                 "DkFRVDPknstE6cT5eOaijUNdHARGsNjJtQSLTxWROj7vxuIujg+fwWIPG3cv" +
                                                                 "2+Dey5svfIjdoGxTAMK5KvWNOZb7zMn5Z2xqJzDwybP7A5cIRB/A4k6TlADH" +
                                                                 "8PYgIYVi0pBfsRa3tKkm/uvD6//7naft1W/im7K81Gf55VCgEhDLER1uLxz2" +
                                                                 "QixoLVD5vtDx3IMr1zHYu4plDG3FMslUTyTR420K6lwTbqfZ0rWkdd3i17kG" +
                                                                 "rmdsCQoeEZQG9UpEmI7Z6QDs/KgRCvHY4l4dOpO4eOTGN54/YblsnUwNhLou" +
                                                                 "8rW9/GTJ9zbedphjgec0642TMCHgydFZycGZxfbPc4MF8h8eu0yKC57Mvh+t" +
                                                                 "K/tWlJSDA2CwLijmRkHO4Zl0gFRKRodVCbz3tfsTjDyb1u5J5N0dOGt7MaTU" +
                                                                 "9EG0G+KPQylNtkL7Bus63SslQtjNU4Udbwnexk1My2Cy1wQSJJAZd8cmicqt" +
                                                                 "Rji9CKFIFs7nw1Z6kd57cO+HzfsPcjfL2VwfSoN6x1i89tgerDL7UquwEV0/" +
                                                                 "P77rq4/v2mNbjgHkOmeWGr9ZhGVb99PyPd3n1oADKWG+u0NloanC0YxVdSv+" +
                                                                 "qjGS4YRdfEUsniUR4l8MVC2BKSxXT/r/5tQP2w6dO8B44hJZXyBZGxgpH5VP" +
                                                                 "Jf5jx3ftTT7hd2O2L91ayI1FuKyfKyzrCN4+bYvWgyce+LWNtb7AK5u+XMow" +
                                                                 "PfnohonTfxD/Tuvn2B59b2xwlkjobUpw/Cfnbvz6X0819vH4rdFhC82bzQxS" +
                                                                 "rKHBcT86U7JgjNnwOWb9pZjoxPWqgM8G9jRJrPibFDYX17xxDmPLkbF4Uwb/" +
                                                                 "fuvaZZLMleV0MIMjS6m46GZWWB1vxrSR+3bIygn/iVZi+36J7XuqSWrcbvZJ" +
                                                                 "2lKGGb68LAZHxdQhKVZPPrsv/bP1e7kUa8IST4oTbvj7VOauFaeYooxjQmIG" +
                                                                 "1sfFNccP2taghj5/P/8rrII6khTPHb/v9OxfbpzIUs22Onhf860VtPZgQhFT" +
                                                                 "A1A/qvzs179Re8drJSTaRSplVUh3CSzTCoeFIZ0aQ6qczmvWYalqBIPb8U70" +
                                                                 "8g+OMtVi8wxLiW6zrhuw9TQzzpdtRH6jCCI7GckwKl9RirLkGZ6iXBpui839" +
                                                                 "yKYRVd9G9QbnHPvnWf48yx9/lj9lE/ODV5i+//gAbL/HYtn8BYsWNi1um7/k" +
                                                                 "+sYiP3AJkQSz9meweJMB979g8QoWX2ZNDzuYg7ldMteKCDZZ109cYaz+Dpv2" +
                                                                 "KI8a84j+DYVAPYjPre3iuhfVGxoZyJeZQ5LR0MJeFRTA9naei3rdyQB6TjLs" +
                                                                 "yw5d3crZaSfjpo/+QsnDWx97PBRPYcnSbz9lt790d+A9RGLxK3aQLJj4wrp9" +
                                                                 "WLzA4h8WQzFXyVruu8SR8F12JMTiQR5G+ZMntZYsbimUPAkIcaIdhWDHLuva" +
                                                                 "eYVCvOAVoqtILXzRE+DfHQstdIj1UzbNdmlI0TrrmrhCyj4IUeYutAwXusla" +
                                                                 "QLWu2Stc6KJ3IZNUOvHutYWE/FfFhRwpCQoZi9+yYReweL8AC9lWXTJG/HpR" +
                                                                 "jYEDV74iSTUsgumNqJWldGho8aRLWoA3mY+VAwpCtD8VlNRpVjVpgYzQToaX" +
                                                                 "f7ur0ZepwJxMpPyKicbRVbjxyFWXNR8+HmQFm2LSJcQ3GYsy0AB3d2xgMcU/" +
                                                                 "xCkIJ2S8lSYZH+Qjz7PzbbEdzfrIXLvLgzo3cd/iz7mHqza5w651X220+PPv" +
                                                                 "4aqv8Cp+SIw0fZxDYuE97nbXn8X0PyRQLFqZUIvhbWQsFnF7jkiTO1GBTKBJ" +
                                                                 "Nv1/0pvFdZplOblEkYqJoXPxw1h84TJwJ7IkBHCM8Sdtxrd/NOOxeCFwLI8s" +
                                                                 "wduXAq4tsgiL5Vi0XZLVzLWxOSLt223Ui4y/hNmsYmaDxRQv6rlcee9yuNLp" +
                                                                 "5cr2IqDpzv0/lzP36rBLweL3lzPHrX5vMSGo+Nd+TE1lMmFybmPl2KDnCcmg" +
                                                                 "4hIy6CssA8bQYkxkHGFbyns+yrKAC4791dZZHtOQzTwNaR/6qzA41WTBRLvK" +
                                                                 "5/ncvjd3hcnc4n9vgy/Ocvyb1aT42wWtnc+fnnPKendb9ATvjjh+ZM26nRfa" +
                                                                 "+JueUSCqO5nvrUqQcm7vjAb8jGV20dnsucpWN/7v2KcrrnE+jsDC/jAptLuY" +
                                                                 "a4lPYeBbV+jDZe93tUnx8yu+97tTg9tfgcB0gIx3PqbrGKIiRPz+l+tWqt4/" +
                                                                 "wTaya98391R/GnY7QCoko1/PGSZ+Ilsh2ikvpOeLPD3P3+JGMkDcdaEPNHwz" +
                                                                 "+z7PmHTbF9e8/fDyKVwI1wZfoPuGuRn5yNZD6xPlH37SeatTEGEYu2q4rvzC" +
                                                                 "CXNGYRsm1uogvIlb1/nYaoc5mmOpEflyLDUbRJKZ2v8BoMQVQ2guAAA=");
    
    public fabric.lang.security.Principal foo_remote(final fabric.lang.security.Principal worker$principal,
                                                     final fabricated.util.List storeList);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$internal_l1();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy implements internal
    {
        
        public fabric.lang.security.Label get$jif$internal_l1() { return ((internal.
                                                                            _Impl)
                                                                            fetch(
                                                                              )).
                                                                    get$jif$internal_l1(
                                                                      ); }
        
        public fabric.lang.security.Principal foo(fabricated.util.List arg1) { return ((internal)
                                                                                         fetch(
                                                                                           )).
                                                                                 foo(
                                                                                   arg1);
        }
        
        public internal internal$() { return ((internal) fetch()).internal$(); }
        
        public fabric.lang.security.Principal foo_remote(fabric.lang.security.Principal arg1,
                                                         fabricated.util.List arg2) {
            return ((internal) fetch()).foo_remote(arg1, arg2); }
        
        public static final java.lang.Class[] $paramTypes5 = { fabricated.util.List.class };
        
        public fabric.lang.security.Principal foo$remote(final fabric.worker.remote.RemoteWorker $remoteWorker,
                                                         fabric.lang.security.Principal arg1,
                                                         fabricated.util.List arg2) {
            if ($remoteWorker == fabric.worker.Worker.getWorker().getLocalWorker(
                                                                    )) return foo(
                                                                                arg2);
            else try { return (fabric.lang.security.Principal) fabric.lang.Object._Proxy.
                                $getProxy($remoteWorker.issueRemoteCall(this, "foo",
                                                                        $paramTypes5,
                                                                        new java.
                                                                          lang.Object[] { arg2 }));
                 }
                 catch (fabric.worker.remote.RemoteCallException $e) { java.lang.
                                                                         Throwable $t =
                                                                         $e.getCause(
                                                                              );
                                                                       throw new fabric.
                                                                         common.
                                                                         exceptions.
                                                                         InternalError(
                                                                         $e); } }
        
        public void jif$invokeDefConstructor() { ((internal) fetch()).jif$invokeDefConstructor(
                                                                        ); }
        
        public static boolean jif$Instanceof(fabric.lang.security.Label arg1, fabric.lang.Object arg2) {
            return internal._Impl.jif$Instanceof(arg1, arg2); }
        
        public static internal jif$cast$internal(fabric.lang.security.Label arg1,
                                                 fabric.lang.Object arg2) { return internal._Impl.
                                                                              jif$cast$internal(
                                                                                arg1,
                                                                                arg2);
        }
        
        public _Proxy(internal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements internal
    {
        
        public fabric.lang.security.Principal foo(final fabricated.util.List storeList) {
            try { if (storeList.size() > 0) { try { return fabric.worker.Worker.
                                                      getWorker().getWorker((java.lang.String)
                                                                              (java.lang.Object)
                                                                                fabric.lang.WrappedJavaInlineable.
                                                                                $unwrap(
                                                                                  storeList.
                                                                                    get(
                                                                                      0))).
                                                      getPrincipal(); }
                                              catch (final java.lang.IndexOutOfBoundsException e) {
                                                   }
                                              catch (final java.lang.ClassCastException e) {
                                                   } }
                  return fabric.worker.Worker.getWorker().getWorker("alicenode").
                    getPrincipal(); }
            catch (java.lang.NullPointerException exc$13) { throw new fabric.common.exceptions.ApplicationError(
                                                              exc$13); } }
        
        public internal internal$() { ((internal._Impl) this.fetch()).jif$init();
                                      { this.fabric$lang$Object$(); }
                                      return (internal) this.$getProxy(); }
        
        public fabric.lang.security.Principal foo_remote(final fabric.lang.security.Principal worker$principal,
                                                         final fabricated.util.List storeList) {
            if (fabric.lang.security.LabelUtil._Impl.relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                                  join(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       this.get$jif$internal_l1(
                                                                              ).
                                                                         meet(fabric.worker.Worker.
                                                                                getWorker(
                                                                                  ).
                                                                                getLocalStore(
                                                                                  ),
                                                                              fabric.lang.security.LabelUtil._Impl.
                                                                                toLabel(
                                                                                  fabric.worker.Worker.
                                                                                    getWorker(
                                                                                      ).
                                                                                    getLocalStore(
                                                                                      ),
                                                                                  fabric.lang.security.LabelUtil._Impl.
                                                                                    readerPolicy(
                                                                                      fabric.worker.Worker.
                                                                                        getWorker(
                                                                                          ).
                                                                                        getLocalStore(
                                                                                          ),
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                        topPrincipal(
                                                                                          ),
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                        topPrincipal(
                                                                                          )),
                                                                                  fabric.lang.security.LabelUtil._Impl.
                                                                                    writerPolicy(
                                                                                      fabric.worker.Worker.
                                                                                        getWorker(
                                                                                          ).
                                                                                        getLocalStore(
                                                                                          ),
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                        topPrincipal(
                                                                                          ),
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                        topPrincipal(
                                                                                          ))),
                                                                              true),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         liftToLabel(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             writerPolicy(
                                                                               fabric.worker.Worker.
                                                                                 getWorker(
                                                                                   ).
                                                                                 getLocalStore(
                                                                                   ),
                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                 topPrincipal(
                                                                                   ),
                                                                               worker$principal))),
                                                                fabric.lang.security.LabelUtil._Impl.
                                                                  join(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         liftToLabel(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             readerPolicy(
                                                                               fabric.worker.Worker.
                                                                                 getWorker(
                                                                                   ).
                                                                                 getLocalStore(
                                                                                   ),
                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                 topPrincipal(
                                                                                   ),
                                                                               worker$principal)),
                                                                       this.get$jif$internal_l1(
                                                                              ).
                                                                         meet(fabric.worker.Worker.
                                                                                getWorker(
                                                                                  ).
                                                                                getLocalStore(
                                                                                  ),
                                                                              fabric.lang.security.LabelUtil._Impl.
                                                                                noComponents(
                                                                                  ),
                                                                              true))))
                return this.foo(storeList); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                                   ); }
        
        public _Impl(fabric.worker.Store $location, final fabric.lang.security.Label jif$l1) {
            super($location);
            this.jif$internal_l1 = jif$l1; }
        
        public void jif$invokeDefConstructor() { this.internal$(); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.security.Label jif$l1,
                                             final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                 idEquals(
                                                                                   o,
                                                                                   null))
                                                                               return false;
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             accessCheck(
                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                 join(
                                                                                   fabric.worker.Worker.
                                                                                     getWorker(
                                                                                       ).
                                                                                     getLocalStore(
                                                                                       ),
                                                                                   jif$l1.
                                                                                     meet(
                                                                                       fabric.worker.Worker.
                                                                                         getWorker(
                                                                                           ).
                                                                                         getLocalStore(
                                                                                           ),
                                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                                         toLabel(
                                                                                           fabric.worker.Worker.
                                                                                             getWorker(
                                                                                               ).
                                                                                             getLocalStore(
                                                                                               ),
                                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                                             readerPolicy(
                                                                                               fabric.worker.Worker.
                                                                                                 getWorker(
                                                                                                   ).
                                                                                                 getLocalStore(
                                                                                                   ),
                                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                                 topPrincipal(
                                                                                                   ),
                                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                                 topPrincipal(
                                                                                                   )),
                                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                                             writerPolicy(
                                                                                               fabric.worker.Worker.
                                                                                                 getWorker(
                                                                                                   ).
                                                                                                 getLocalStore(
                                                                                                   ),
                                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                                 topPrincipal(
                                                                                                   ),
                                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                                 topPrincipal(
                                                                                                   ))),
                                                                                       true),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     liftToLabel(
                                                                                       fabric.worker.Worker.
                                                                                         getWorker(
                                                                                           ).
                                                                                         getLocalStore(
                                                                                           ),
                                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                                         topInteg(
                                                                                           ))),
                                                                               o);
                                                                           if (fabric.lang.Object._Proxy.
                                                                                 $getProxy(
                                                                                   (java.lang.Object)
                                                                                     fabric.lang.WrappedJavaInlineable.
                                                                                     $unwrap(
                                                                                       o)) instanceof internal) {
                                                                               internal c =
                                                                                 (internal)
                                                                                   fabric.lang.Object._Proxy.
                                                                                   $getProxy(
                                                                                     o);
                                                                               return fabric.lang.security.LabelUtil._Impl.
                                                                                 equivalentTo(
                                                                                   c.
                                                                                     get$jif$internal_l1(
                                                                                       ),
                                                                                   jif$l1);
                                                                           }
                                                                           return false;
        }
        
        public static internal jif$cast$internal(final fabric.lang.security.Label jif$l1,
                                                 final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                     idEquals(
                                                                                       o,
                                                                                       null))
                                                                                   return null;
                                                                               if (internal._Impl.
                                                                                     jif$Instanceof(
                                                                                       jif$l1,
                                                                                       o))
                                                                                   return (internal)
                                                                                            fabric.lang.Object._Proxy.
                                                                                            $getProxy(
                                                                                              o);
                                                                               throw new java.lang.ClassCastException(
                                                                                 );
        }
        
        public fabric.lang.security.Label get$jif$internal_l1() { return this.jif$internal_l1;
        }
        
        private fabric.lang.security.Label jif$internal_l1;
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           join(
                                                                             this.
                                                                               $getStore(
                                                                                 ),
                                                                             this.
                                                                               get$jif$internal_l1(
                                                                                 ).
                                                                               meet(
                                                                                 this.
                                                                                   $getStore(
                                                                                     ),
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     this.
                                                                                       $getStore(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       readerPolicy(
                                                                                         this.
                                                                                           $getStore(
                                                                                             ),
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             )),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       writerPolicy(
                                                                                         this.
                                                                                           $getStore(
                                                                                             ),
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ))),
                                                                                 true),
                                                                             fabric.lang.security.LabelUtil._Impl.
                                                                               liftToLabel(
                                                                                 this.
                                                                                   $getStore(
                                                                                     ),
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                   topInteg(
                                                                                     ))).
                                                                           confPolicy(
                                                                             ));
                                                  return (internal) this.$getProxy(
                                                                           ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new internal._Proxy(
                                                             this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                            interStoreRefs);
                                           $writeRef($getStore(), this.jif$internal_l1,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs); }
        
        public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                     fabric.worker.Store labelStore, long labelOnum, fabric.worker.
                       Store accessPolicyStore, long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs) throws java.io.IOException,
            java.lang.ClassNotFoundException { super(store, onum, version, expiry,
                                                     labelStore, labelOnum, accessPolicyStore,
                                                     accessPolicyOnum, in, refTypes,
                                                     intraStoreRefs, interStoreRefs);
                                               this.jif$internal_l1 = (fabric.lang.
                                                                        security.
                                                                        Label) $readRef(
                                                                                 fabric.
                                                                                   lang.
                                                                                   security.
                                                                                   Label.
                                                                                   _Proxy.class,
                                                                                 (fabric.
                                                                                   common.
                                                                                   RefTypeEnum)
                                                                                   refTypes.
                                                                                   next(
                                                                                     ),
                                                                                 in,
                                                                                 store,
                                                                                 intraStoreRefs,
                                                                                 interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) { super.$copyAppStateFrom(
                                                                                other);
                                                                        internal.
                                                                          _Impl src =
                                                                          (internal.
                                                                            _Impl)
                                                                            other;
                                                                        this.jif$internal_l1 =
                                                                          src.jif$internal_l1;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements internal.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((internal._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((internal.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((internal.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((internal.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(internal._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final internal._Static $instance;
            
            static { internal._Static._Impl impl = (internal._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                     $makeStaticInstance(internal.
                                                                           _Static.
                                                                           _Impl.class);
                     $instance = (internal._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements internal._Static
        {
            
            public fabric.worker.Worker get$worker$() { return this.worker$; }
            
            fabric.worker.Worker worker$;
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return this.
                                                                                jlc$CompilerVersion$fabric;
            }
            
            public java.lang.String jlc$CompilerVersion$fabric;
            
            public long get$jlc$SourceLastModified$fabric() { return this.jlc$SourceLastModified$fabric;
            }
            
            public long jlc$SourceLastModified$fabric;
            
            public java.lang.String get$jlc$ClassType$fabric() { return this.jlc$ClassType$fabric;
            }
            
            public java.lang.String jlc$ClassType$fabric;
            
            public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                   java.util.List intraStoreRefs, java.util.List interStoreRefs)
                  throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                                interStoreRefs);
                                               $writeInline(out, this.jlc$CompilerVersion$fabric);
                                               out.writeLong(this.jlc$SourceLastModified$fabric);
                                               $writeInline(out, this.jlc$ClassType$fabric);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         fabric.worker.Store labelStore, long labelOnum, fabric.
                           worker.Store accessPolicyStore, long accessPolicyOnum,
                         java.io.ObjectInput in, java.util.Iterator refTypes, java.
                           util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs, interStoreRefs);
                this.jlc$CompilerVersion$fabric = (java.lang.String) in.readObject(
                                                                          );
                this.jlc$SourceLastModified$fabric = in.readLong();
                this.jlc$ClassType$fabric = (java.lang.String) in.readObject(); }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new internal.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm138 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff139 = 1;
                                       $label134: for (boolean $commit135 = false;
                                                       !$commit135; ) { if ($backoff139 >
                                                                              32) {
                                                                            while (true) {
                                                                                try {
                                                                                    java.lang.Thread.
                                                                                      sleep(
                                                                                        $backoff139);
                                                                                    break;
                                                                                }
                                                                                catch (java.
                                                                                         lang.
                                                                                         InterruptedException $e136) {
                                                                                    
                                                                                }
                                                                            } }
                                                                        if ($backoff139 <
                                                                              5000)
                                                                            $backoff139 *=
                                                                              2;
                                                                        $commit135 =
                                                                          true;
                                                                        fabric.worker.transaction.TransactionManager.
                                                                          getInstance(
                                                                            ).startTransaction(
                                                                                );
                                                                        try { this.
                                                                                worker$ =
                                                                                fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    );
                                                                        }
                                                                        catch (final fabric.
                                                                                 worker.
                                                                                 RetryException $e136) {
                                                                            $commit135 =
                                                                              false;
                                                                            continue $label134;
                                                                        }
                                                                        catch (final fabric.
                                                                                 worker.
                                                                                 TransactionRestartingException $e136) {
                                                                            $commit135 =
                                                                              false;
                                                                            fabric.
                                                                              common.
                                                                              TransactionID $currentTid137 =
                                                                              $tm138.
                                                                              getCurrentTid(
                                                                                );
                                                                            if ($e136.tid.
                                                                                  isDescendantOf(
                                                                                    $currentTid137))
                                                                                continue $label134;
                                                                            if ($currentTid137.
                                                                                  parent !=
                                                                                  null)
                                                                                throw $e136;
                                                                            throw new InternalError(
                                                                              ("Something is broken with transaction management. Got a signa" +
                                                                               "l to restart a different transaction than the one being mana" +
                                                                               "ged."));
                                                                        }
                                                                        catch (final Throwable $e136) {
                                                                            $commit135 =
                                                                              false;
                                                                            if ($tm138.
                                                                                  checkForStaleObjects(
                                                                                    ))
                                                                                continue $label134;
                                                                            throw new fabric.
                                                                              worker.
                                                                              AbortException(
                                                                              $e136);
                                                                        }
                                                                        finally {
                                                                            if ($commit135) {
                                                                                try {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      commitTransaction(
                                                                                        );
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         AbortException $e136) {
                                                                                    $commit135 =
                                                                                      false;
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         TransactionRestartingException $e136) {
                                                                                    $commit135 =
                                                                                      false;
                                                                                    fabric.
                                                                                      common.
                                                                                      TransactionID $currentTid137 =
                                                                                      $tm138.
                                                                                      getCurrentTid(
                                                                                        );
                                                                                    if ($currentTid137 ==
                                                                                          null ||
                                                                                          $e136.tid.
                                                                                          isDescendantOf(
                                                                                            $currentTid137) &&
                                                                                          !$currentTid137.
                                                                                          equals(
                                                                                            $e136.
                                                                                              tid))
                                                                                        continue $label134;
                                                                                    throw $e136;
                                                                                }
                                                                            } else {
                                                                                fabric.worker.transaction.TransactionManager.
                                                                                  getInstance(
                                                                                    ).
                                                                                  abortTransaction(
                                                                                    );
                                                                            }
                                                                            if (!$commit135) {
                                                                                
                                                                            } } }
                                     } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 126, 113, 66, -111, -74,
    127, 96, 8, -103, -23, 117, 42, 55, -25, 116, -80, 86, -6, -120, -95, 92, 5,
    94, -55, 69, -100, -123, 33, -32, -109, 40, -113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1445481719000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAN17a6zj2HmYZnZ2Z3e93l2vH7U33vW1PV3MWOuhKFEi6anb" +
                                                                "SqREkSIlvkSJdOwJ3yLF90uk3M0Lje3GiN3GaycunABBHbRNtk1bIOmPwGj6" +
                                                                "I42NBAVaFEX6o41htEgCxz+Cog8UaVNSunfmzp3ZcbpdF3UE3MPDc77vO985" +
                                                                "3/PwnPvad1qPpknrA5aqOd7NrIrM9OZE1UiaVZPUNDBPTVOxbr2tv+UK+aU/" +
                                                                "+LvGi5dbl+nWU7oahIGjq97tIM1aT9OuWqhAYGbAkidvfaz1hN4gTtV0k7Uu" +
                                                                "f2xUJq2TKPQq2wuz00Huo//FNvDqz3zi2X/ySOsZpfWMEwiZmjk6FgaZWWZK" +
                                                                "6ynf9DUzSYeGYRpK622BaRqCmTiq5+xrwDBQWs+ljh2oWZ6YKW+moVc0gM+l" +
                                                                "eWQmhzHPGhv2w5rtJNezMKnZf/bIfp45HkA7aXaLbj1mOaZnpHHrh1tX6Naj" +
                                                                "lqfaNeC76LNZAAeKwKRpr8GfdGo2E0vVzTOUK1snMLLW+y5i3JnxtVkNUKNe" +
                                                                "9c1sE94Z6kqg1g2t544seWpgA0KWOIFdgz4a5vUoWev51yVaAz0eqfpWtc3b" +
                                                                "WevdF+HYY1cN9cRhWRqUrPXOi2AHSrXMnr8gs3PS+s78L33uk8E0uNy6VPNs" +
                                                                "mLrX8P94jfTiBSTetMzEDHTziPjUh+gvqe/62mcut1o18DsvAB9h/ulf++O/" +
                                                                "+vKLv/H1I8wPPABmobmmnt3Wv6o9/a/ei91AH2nYeDwKU6dRhXtmfpAqe9pz" +
                                                                "q4xqbX/XHYpN582zzt/g/4X8o79kfvty60my9Zgeerlfa9Xb9NCPHM9MCDMw" +
                                                                "EzUzDbL1hBkY2KGfbF2t67QTmMfWhWWlZka2rniHpsfCw3u9RFZNolmiq3Xd" +
                                                                "CazwrB6p2eZQL6PW6e/p+u/9rdaVHzh9vjVricAm9E3ASTemZQF4EkZaWAJ4" +
                                                                "qOe+GWS1AoRJYHoeoEYeYDsZUJtz4uiAWap+5NUSra1EN7VQA9JEB9T6d7OG" +
                                                                "iL5HdMtmPs/uLl2ql/p9emiYmprWcjvVoRHr1WYyDT3DTG7r3ue+Rrbe/rUv" +
                                                                "H/ToiUb301p/Dyt1qZb9ey96jfO4r+aj8R//w9u/fdTBBvd0IbPW4wd7DFSv" +
                                                                "ZuGpxpJu1r7pZu2bXrtU3sR+nvzlg8I8lh4s6w7WEzXzH/HC2quVrUuXDvy/" +
                                                                "44B8UJNayNvaedT+4akbwsepH/rMBx6p9TPaXanF1IBeu2gtd30MWdfU2gRu" +
                                                                "6898+g/+66986ZXwrt1krWv3mfP9mI05fuDiYiShbhq1u7tL/kMn6q/e/tor" +
                                                                "1y43ruSJ2stlaq2Htct48eIY95jlrTMX1yzFo3TrLVaY+KrXdJ35pSezTRLu" +
                                                                "7rYchPz0of62P61/l+q//9X8NRrcNDTP2o9hp9Zzcsd8sta3vvWTX/3Wp376" +
                                                                "I/f3nVz/rl27MNmaybWolpzuRKr3f0olajq+F2r/kW7v5R78Yahzo/5FRyNo" +
                                                                "NOiC1A4h46NC9HO/+y//sHcIpmfR5ZlzYUgws1vnPFpD7JmD73rbXYUUE9Os" +
                                                                "4f79z7Jf+OJ3Pv2xgzbWEB980IDXmrJZDrVehjD5ia/H/+73/sNX/83luxqc" +
                                                                "tR6Lcq2e2IHzD9aEXro7VO3svNrh1pyk15aBHxqO5aiaZzbW8CfP/EXwV//o" +
                                                                "c88eVdqrW44KkrRe/u4E7ra/Z9T60d/+xH978UDmkt4E27vLcRfs6MHffpfy" +
                                                                "MEnUquGj/LF//cKXf0v9udq0a/+bOnvz4FIvnRpow9Q769h5lOXNJrLeTE09" +
                                                                "T5ysukmrmukdQN6TtZ5qhBl5atZYQXlYjt6h7+ah7DaafSDaOvShTfGB8tD3" +
                                                                "3kN7k1FdDIOTJp+4a6gK8NpXnsf+8rePfu+OoTY03v8Avyep53xI95f8/3L5" +
                                                                "A4/95uXWVaX17CGVUYNMUr28UQelTkZS7LSRbr31nv57E4tjFL11xxG996KT" +
                                                                "ODfsRRdx19/W9Qa6qT959AoHBSovtaKm8lcOGC8dyhtN8fJhjS5njfttUrys" +
                                                                "JuzUrvqod1nr6qmFn0nsHacSOzbfXB0eTd/zRxNrylunQ9Za/GjnZvdmt3kn" +
                                                                "HjzyI031w00xagrsbNznXU+/duYxpDrVrLXt2nHsM1aePajdQXWOSdmDVeYB" +
                                                                "fNUa8fRdZDqs87nP/se/+Tuf/+Dv1RpAtR4tGunUgj83wjxvEt5PvfbFF97y" +
                                                                "6jc/e7DUWo/Zn/K++rsNVbYpyKz1QsO2EOaJbtJqmjEH0zKNM87v10Q2cfza" +
                                                                "qRSnCZn5mVd/8k9vfu7Vo/kes9YP3pc4nsc5Zq6HWb71OL96lPc/bJQDxuT3" +
                                                                "f+WVX/97r3z6mNU9d28ONg5y/x/82//5Ozd/9pvfeEA8v+KFxzh9Udzv/PoU" +
                                                                "Ssnh2W8BqmaPW4I9H8hLUhi7JEtyggxWNEUQxMbTh6RLj/oCJfPrLSaNhgFm" +
                                                                "4h3YBHXVpzqKUge9UhZWC1oqQ0xGJzgC4Ya72Mkda07b7WgFCdB+s5ATzODB" +
                                                                "ToA706kR9vf6ljQXDBFoedot2W7bbOtojrocNUW9PbqFGcDq90ygbMMwvOd3" +
                                                                "sNiRlUWdPGKYZ4yjYj7lQ9BPCpmD2jaxFw2Sy4kOB0iCNUH2y2yKAKa+5kmP" +
                                                                "4ryadduZyztTUrTxVFCVILNjxaRSc7JbLT1hYQw2s01idrkuyi8n/GRsL/sx" +
                                                                "b/uEUsgze0fr8kpF6s6eMWwLPFGB8+F4u1YEV4iiOLSFjQy7JmPqMz0oWT/u" +
                                                                "9nvLaliq1WCycxXR4VMGWog5M3V7EAwYQx82O+GMT+2wkly7S5KEEtqTJb2Y" +
                                                                "Tiiw4ni17KW+uHQIzMzZsbSyhVLflqLQ3cLINiypbTHkQNunZvY2ENdarM71" +
                                                                "IN0MZRrX45hQjT4zgKg8wkA02+PtIbf3tIlq9mnej6GF0PVjv8uvnDGDSl6K" +
                                                                "Bb1RwqyzgdpDNphRAmtrb9uwyVoDdeyOUhuqBG9f8ozA8Qu/g7PDWTDr+NVs" +
                                                                "kGozg4r9EWKPNwbVmaJLj1O0fEMOYmaLxfjK48Z6pYshaKqxWzH1znYekOqW" +
                                                                "3CTdaBkyqSoEhBPnSddIw3Lc10R0joUuBjF2NInX9LqE15LNFjN7bHgkOgRn" +
                                                                "4KbP59s+10EccrfLPHc36HFDWV0pVUBWoB9P2DVOzSVdT2Y2zmiwOAGFUTxN" +
                                                                "ZrgUkOGioN0oWuwibEzrlL3no9pyR5bbRbB1JDFjzR5TcwYLFJeBiGVi98lq" +
                                                                "lW4GfS3rDfxUisnt0BWCWLBdQFruVEKxlxgwX466nYCdjSSLEfCdA1Lt9nxs" +
                                                                "10uzWw6QPcxaeYI5sarQ/b1vqbI0oJl2pqxmikci/iaBu4WwKDfZbEvued+P" +
                                                                "yNl0b+2joEJBO4PX2zkzw1c+P9b8do+Y9CKrm4iwYMXJbBaklL9yZrlZhWNd" +
                                                                "5PFVIoi0pA3GM9CYjPPtdkIQ0FRtI35/jVnbkThTUSNgbBnMO360nyUziZUZ" +
                                                                "mkmGE8ZxmPWwmPGBVGT6WIdco7+XHEDXMGsvbyJjkQ7ApT+QETaWtqoH7TZ+" +
                                                                "GowXPUkSBkHfnOlxPyGX+GhrCxMzpBl9IsoQK/iqbpd7OYlwSaGGYZeUyGU3" +
                                                                "jGstcdbkyGIAkQO27r4fjIG5sOAxejTwYsdXl+GOIwYrPO7FW33CSHFaQeOJ" +
                                                                "4LGmBWOyKJb9OjUQNzLGLsbDtSLuAGcztdKdUcV+hSIzaxuDnbGu7qfCCC/H" +
                                                                "JsiNiaUTctssHtl8PrK59iTd9oxyH1lhGIlUMKKZVJG46ZjuS9JuASHOkpMB" +
                                                                "UUqZCSOOWbJrD/NhR02IwgETOHF6iS12lmUajcTJBtyH1npqQv6cnkOT5bhc" +
                                                                "99F8rU2B7Vr0tmsqHhLDtboQlVDQNVbpJBNqj6Uj0ZVYSdgR/QmprkciAa9H" +
                                                                "rELUOojH44wHt3KU5CJUr4lMbPDOjKO3opfv4QBgWXhIZ0tQi5jOBh8G7Igs" +
                                                                "wwoKGakjqXzEdSmwvd7uoK1Y9eNqMhO2Uz/FddEIVWjuLqCNjcBdv6OXCbjc" +
                                                                "sXIVR5CkEFWVS1SE50HsTAp8To9VB4xzD3P5ITlmdj6QdnYDq21CxWSrUCCd" +
                                                                "zRzOWCLTrWNDCJVs4c6gL2BZD96HygDIAQAJUpAh1dHUUscgkeDpEB+Osp3H" +
                                                                "5EDRQTdtFDA0Hu4YBiXSo6itCPKE6mdFvMXb47VtAQUiA6Q9kqCRBXCyOhH2" +
                                                                "5Njbdt0CxnuDvduZZjMJEGZFznUsfthVq0ksjPsJ2FlKmagiBdZWO5nTIwod" +
                                                                "LPMhJs6GTDGkZ/ON3aekDkgRY4+seKXo97RNstoCiFmgUOIxFTgdUgNVjMLK" +
                                                                "2A0GZk4mmVVYXUBJqIh0ZgMFt8a6lpUBBYT7pBRAaGkIY6vdAZA2hA4jSKzj" +
                                                                "6gzrkAZILaxhhfGYTy1TGS3NSg8IWxO2K303Vu2wu+vh3HpI2RvO41xEGQYz" +
                                                                "Q21LpbXO3QjYbYR0GXAOH+0IwyUJZJVwuDsJNuNq0O2CBNlP9TXjqdGap2kW" +
                                                                "NBMC2qeDXamuYjKPRaWyCzPKZhzH0MNatL49HHLqLErssbfozGTacQh8Hoy5" +
                                                                "wcqcz9s6OF0uCHk5CTo55m+XBq4zbriT7N1MgM2sY6wHgNnlBbArreeTka3M" +
                                                                "KakWrt9dWPp6VfHxagmQXBLL/Taz7DCCpsX0UljghYVnUYZE+2FvRe2KXSht" +
                                                                "w2pJpZK03yPCwircDIQDaMmgJEoqg5HnwFUnmkZwB5qykliCcrW0GCRESFSC" +
                                                                "V51gwiJFBLS38DItNIrj2+XKxYjhaL2ocETPWWBuimw/GdX98xjoBEEP4ABk" +
                                                                "ALNrfW8IG65Ui3A2Hc42CwaaY3C8xNp4Nx8tg7IQ5oFqth0chb3tbLWYMVNu" +
                                                                "tWMtahur9oLoGbLB50jf53TIt2dhRCpdMPLrDKU/dfsoEK/0MbNXmdpR55Nd" +
                                                                "vZNg+G5nrUs5CORGuJ2zOtyOFwLnlUaWAO3uYKCjWTuu1m15PAoyuLbsDdJe" +
                                                                "tVU2cMaaUfC9Ih6ibojTcTBazWAbxUkbgQJDGPDrdsQEhRWgHWSR9KpuupYl" +
                                                                "gp6gHAJU/Zk07zFBj+/OQxSsjXtg6DPMSkWdpPYbHkzEZA9lFm3WAsCUDTme" +
                                                                "hNXO9SpzNeuDo1p3xyAqOlQ7kebzZVoO8p63AQy/gEvDanc1TSt6GNTJXUsm" +
                                                                "sK3RzzwEroew2qwxaPf6qDvM4ERJ0WVWELHi+XqXhrFA9NQc34RDKRvEqRJA" +
                                                                "0MrZ96N0i9AOZfSXkLlfTTOrP7XCnuKTHdZVKwQPMx9HsV57WmCqtpdl2Tb6" +
                                                                "JGwa1og0C9BNpF0SF52pS+XSmpWtHuxCsKlOrYKq7BUG7jwBbIukESVUsFdt" +
                                                                "XhxiqWIa4zlUqgCxCXYAt87aALDbrWo/jYQT3Fpg+a4tMDtRhdqoxaQ6wuG0" +
                                                                "P7E3cttNByWwhPMlLg9LayL6s/lqoUwWMgTEDNZfD0FgUYw0tIzXijEAOJ1F" +
                                                                "qA2ZrjbVZquUA2KTVLUFGOokUBnc4vsV18vnAVF7ZX+DctNegXQpS437fcil" +
                                                                "PHckzmWdxPxSA/gFQ7X7RS5Do+Eeg3xq1RF1WQLhgs2HhooswnQys4xRhcio" +
                                                                "harget8BokUK6tZEWDKTkORNJVqFe2rBdZ2F2t6FCCP0Ew5bdndLdjxG0WRP" +
                                                                "dXUM4TaFDE+tbSFuDClbgaQeVH18DI7bbGlu99wI3OqEy9iYr5akqFX00O0s" +
                                                                "V/P9smNbom6OPTxi5MHWYDQfXK4Xuz1DOPh2wks4u633xOOBLvbNheq4y7kx" +
                                                                "HfJEOnepjBNosVzxCxcuypzMXbtt7VYkS/T0otvGzaqfFhbpGiFRikNm1p8V" +
                                                                "q/lC0eA9E/SVbhlTe3xXaXVA7OskPwKG0HYgekN8irCyTcqS04lGVsqrvTrD" +
                                                                "qjc4BT4AADhQrQxcAY4PzxCQsPp9CauTWJwe82BOl/su55QhLeH9IsPLHskk" +
                                                                "4H4q51Dk4URvwqTeUqxEHup2ZgNvJ2qxVQf01dYweg4Ib4nEN1KMC82xwhHo" +
                                                                "Mhr7JKwMODwFZjaPFVMZ8iBjES0gcyES8z4EyJqXBsvdfEOHOZKsUIAQKXSv" +
                                                                "mUa0cW1T7wygrshgU7syJkqlE0ppaawOMRNkg5h5p5phfZgpNu1sBmQrjxLw" +
                                                                "YRfb0Y7r89vpAlK8jj3hZ3xoSJi272W1D3DxSgPzPtWfd4wFkcGMKi7RMdEh" +
                                                                "2jOWq7x5tqXhUZ4OekLqcZk7X9axTdsFZrkFmaqLVeCOEyhqqBiI7jt6nPGu" +
                                                                "g2KzCTlJ4WAHKrX0+7C+aNdZbMrqrkV3pZ0lhPNBCToImtlmgtPwMs53Y6kD" +
                                                                "sDCcxBBQdP255trT7cI3V0SCKhtC0lCUrVijF4DhEJ+tiHbemXuhGmAduZQM" +
                                                                "oWjHLiFAMm9Y42UaOEse8hBGwrqjmVCuRSAB+hnHdncDY4GMIGhDKetqpBCl" +
                                                                "HU37EktZfbfsWZkowhuju+jSKKhWJDjJZ5U1q/YJNofamlys7Eo32/Rqnmnw" +
                                                                "cNbP22smrKNBEG7CQLRKHBDzIuiO+qjWLXfe1g21ZbQDMCvQiv1g3UFAtJhC" +
                                                                "iTHZYEhX23OlkMymI11EBkuYst15sFusNDRS3LBkV7HNIATq8lTCOtoEl9cg" +
                                                                "XJa4z9JbbQItdxpg5YWqKDTjmVKHIRlXGPjQgFBWqxFYlInAt3saWdkB4EiK" +
                                                                "0+4NKHidx8Iq0bVCobrejCC0AUIPlWlpcJuZpLBskhaAVicN8HapIqQgQqGo" +
                                                                "FS4sLdS83i3KYVi6nE8SYGpN5rrOdjPV3rh9gAWQJCHJwXYjslMNKNBFrZHR" +
                                                                "nlym3e4i2FjIoCNlrGJzm4k4BIdAXOxTEEZ5EzESShBVXog8abnpYcJexEJz" +
                                                                "QiodlRvE+t4vVrLqxSOS1ZAl3EHs9oLJJAfZlbSzGuyTTb5DRW6/j4yMnw5c" +
                                                                "DCNhQt/uZh7KOwm9JZSBsx5FuYP00Gom85KBEiinBVO2CFYWhFQlCngu6o2B" +
                                                                "BS71ndrr20ONBqZyFiAjOV3NHTD3YWDbTmWH7Q2pLSfsUd/cmRQzQmHURrhw" +
                                                                "te4qc3yfyzbsGUxmzjBcUcAOqsvqvo1HRXdGIioCuW1cloas7LKJ2XPyjSUR" +
                                                                "ZahUlKlw9R68w1DmpI5EaJ1p1LsIo+JGA9Gl02rXN3p6oqiC5UYh0ObSAF+V" +
                                                                "KMyOeFbWA0Hbo14Gox2jPUR5gi5nPZWjKRfaJRG6ovdDaIOsuP1kXUlJniMC" +
                                                                "j0zALsWIDsf3ibbCBJkip5rjL9nhltRV0tis8Cld65QpS1ixGEphnJH0es+P" +
                                                                "27g15dLMIF1wudfmhu+RHTFJ/GSDVS5ELk05z4a7dZ56NQOrvCtk+mBG9Uhl" +
                                                                "vlwU0zyTV2NgYk02fL3R5kLFQduVzNfrSCNDDPVWa5EigGnEu/IYl2DIEcRl" +
                                                                "auf+PKZWnkFsQkTl6VU47PlyWxrWJDIckbw5H0muFNnynhNDdW3BedJRzGhH" +
                                                                "lUzEVjNH13hJc8tqslrPeaQ99bmJ36UFSud6hJxWneGoZ3RLm9GXQIltSaor" +
                                                                "IJ4gK9M259Zb9L2mjY29lZBruqDtfdjTDcDZw/403qv93aAPrrvdTptlC0iN" +
                                                                "CwSL2gK561hKicv5GIUDbR0g6w5b8Rw9iZUlWWy0FZdOggnsbh1xARL1zntE" +
                                                                "zQsQ6ETFxFAib6F5460cU+GaLXfgBoFEakfB5HY0xRhEMLIyMYD5YlGa2GRH" +
                                                                "k0i9r83XwxjKUtupt6dOZ63Q0p5elQuBzrrd3tKBKmAz91PZV/GS54pB4umm" +
                                                                "iVb5nI76etIlYzzaFx3A27O9QTKia2H06vo0WfcLRiDC9txoi2PQY/xJpfms" +
                                                                "153hAab7NrQhqwGWpkNE4+RtuZ0SE60K0naVzIYYBKOgoSjTbMHtxd1c5YFB" +
                                                                "XKshuBJHPF3M+kCgF67R2+iBDgVoYVfAQi/q2KOKUHe3ygK9bQUBOzMDQ0OH" +
                                                                "YuG6m1pNu4OAt9Q2lZtLAYsX/aBIljGn+3DhDjXEJaRuxwjSXI9WcwCuDU5P" +
                                                                "kmBcZ2Y4BrjDKKNzWYU8VjZHmtR2ccVEe1md1yNV3kbhTLTRzLL1Uccmyx4b" +
                                                                "awkxIx2e3be9draKMwios75KRWsHAvIAEcgwh4XbEARYQlDapJduus62iBYm" +
                                                                "zeI9ttemCGaL64Y6ZufT3b5dK5I/FbUJaiKAhVczaUeCO8UK2ZzI+aBfzRV8" +
                                                                "ULLetAcLppqJY2Is590YSoo9hq3nhMyn");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("PpiHdkF6GggEshrqfd9fRGPLq2h8qfHUTOkWyChkOInr2nm9w/xo80n3B0+/" +
                                                                  "ab/j8Cn+zk2J46fspo++/xvw8ZTh9uueMlyNEqdQM/Oeb/3PuI517eyM+LYH" +
                                                                  "Ns3QgXb5YEKt01Oxu4c8rear9wuvd0Pi8MX7qz/+6s8bi18EL5+iy1nriSyM" +
                                                                  "PuyZxelp05HU48338/tu4DCHeyF3j32++e0XUGz7n+zj9/P3XRj5IvTfZ177" +
                                                                  "BvGS/tOXW4/cOd+57zLKvUi37j3VeTIxszwJxHvOdk6OJ7410080a/B8/ffB" +
                                                                  "VuvK7PT5kab37QeJvOPcMchLTfGhO6iXG9THT1HQ02fvHOqF07YLR3inB0LN" +
                                                                  "ce7ZyXyanZ3EnDvDPzCQPuTkrmqKIGs9YoXhGfEXH3g+yJ6dNj/4wOfQur0z" +
                                                                  "v/e0Tovm9sZLZ8/7lqYprj+EvR9/SN9fb4ofqad7psTX7q729l4ZPdes3enV" +
                                                                  "kgfLqCmiC4NdPtpP8/rJpsgOoH/jISx9tik+lbWerFfzdmL64dHmPnmBp6sN" +
                                                                  "wkvneDo8s0tXv8+P/D9yEudq6sR5Pe/rx5P0kyJ0jJOjqynCrYmb1rmrHddv" +
                                                                  "nHwy2zjpzTsivH7j1is3ouj1Defwe+zi2jW9n48uCvC8aL7wkL4vNsVPZa13" +
                                                                  "vx6fB6zl6UFf81hnrSvNzC4ItjHoQ+Vewbb+6M+VYI/B5LxknayR5MnHPi6c" +
                                                                  "XJTexShyqalq5b3yvPogeX7lofL8hYf0/Z2m+NtZ6/Ez7pr3n7kgq+aKWeuF" +
                                                                  "+2T16J/8Ob6Sc48cj1cPTk7tVAtDz1SDg0DPomFoXf/Y4V7CyXGoT6q+9soh" +
                                                                  "LBxrZ7Hh+Ha4QHKoHoh44MsnD0M+3rw4wocfn9T641gn18OTj370JMg978bJ" +
                                                                  "MfbW2F5q3nr9KyvLJvypum6mKbYx9e31PzOvDebx1Q2d4PrxVsWxwTaz4+v1" +
                                                                  "G02dboKpUDsC8/qNl09ndwT0TTP7s2O+Aday8NDwPR0jMVXDTNiw1oPqzR3o" +
                                                                  "TtJwfkLRndY3jcwbm/eu7vk+n3dNqI5R5hubv+dYmfj/QL9qnsk6wNtHfsND" +
                                                                  "441bR3N37viak7MkoAklZ/UT/eSjJ9fv9IS37rqFhzoEM87rKOXVflMMr+s3" +
                                                                  "L+x1Xj6acJ1r3ONkvlvsOqSCx2tT5/ZCD0wYobNk+rnzjB593kMu2f3yQ6La" +
                                                                  "P26KX8xaT9/roh+Um1w99ebnQ97ZhdR3n4W6s4am99qbN/GmeO0A9esPmczX" +
                                                                  "muLXstbbmsnoaprdEc+DkvirF5P4Y6y+8vU3FKu/78P4AxPto26dHPKd413O" +
                                                                  "Q052SK+v5ZFRM3horu3pDZhwEDbTDYNmBnWOfnJK9xh3jx70jRE+hN4jsdrV" +
                                                                  "3PEyh5YLVnsMtvfD/l9E1TeF2D3h841R/D6Kk/+fT/BNC4hvkp7djXw39TCw" +
                                                                  "Thfxxq3TwNOM8oZ3vP/8oTuk33xI3281xT/LWm855y6OjvvcP45E0V1f/nye" +
                                                                  "NP/F9tp//gv//bHHxW+eXsVtnfxwPPpbv/YjP/T4l/8w/xD8+9k/kv7HZ37h" +
                                                                  "Bx/9xDfGX/mJ93/zC9c//78B7hwskl03AAA=");
}
