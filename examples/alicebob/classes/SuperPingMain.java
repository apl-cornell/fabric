import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;
import fabricated.util.List;
import fabricated.util.Comparator;
import fabricated.util.IdComparator;
import fabricated.util.LinkedList;
import fabric.lang.security.PrincipalUtil;
import fabric.lang.security.LabelUtil;

interface SuperPingMain extends fabric.lang.Object {
    
    public SuperPingMain SuperPingMain$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1445981450000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements SuperPingMain
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            SuperPingMain._Impl.main(arg1);
        }
        
        public SuperPingMain SuperPingMain$() {
            return ((SuperPingMain) fetch()).SuperPingMain$();
        }
        
        public void jif$invokeDefConstructor() {
            ((SuperPingMain) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return SuperPingMain._Impl.jif$Instanceof(arg1);
        }
        
        public static SuperPingMain jif$cast$SuperPingMain(
          fabric.lang.Object arg1) {
            return SuperPingMain._Impl.jif$cast$SuperPingMain(arg1);
        }
        
        public _Proxy(SuperPingMain._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements SuperPingMain
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm108 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff109 = 1;
                $label104: for (boolean $commit105 = false; !$commit105; ) { if ($backoff109 >
                                                                                   32) {
                                                                                 while (true) {
                                                                                     try {
                                                                                         java.lang.Thread.
                                                                                           sleep(
                                                                                             $backoff109);
                                                                                         break;
                                                                                     }
                                                                                     catch (java.
                                                                                              lang.
                                                                                              InterruptedException $e106) {
                                                                                         
                                                                                     }
                                                                                 }
                                                                             }
                                                                             if ($backoff109 <
                                                                                   5000)
                                                                                 $backoff109 *=
                                                                                   2;
                                                                             $commit105 =
                                                                               true;
                                                                             fabric.worker.transaction.TransactionManager.
                                                                               getInstance(
                                                                                 ).
                                                                               startTransaction(
                                                                                 );
                                                                             try {
                                                                                 final fabric.worker.Store store =
                                                                                   fabric.worker.Worker.
                                                                                   getWorker(
                                                                                     ).
                                                                                   getStore(
                                                                                     "alicenode");
                                                                                 final fabric.worker.remote.RemoteWorker alicestore =
                                                                                   fabric.worker.Worker.
                                                                                   getWorker(
                                                                                     ).
                                                                                   getWorker(
                                                                                     "alicenode");
                                                                                 final fabric.worker.remote.RemoteWorker bobstore =
                                                                                   fabric.worker.Worker.
                                                                                   getWorker(
                                                                                     ).
                                                                                   getWorker(
                                                                                     "bobnode");
                                                                                 final fabric.worker.remote.RemoteWorker carolstore =
                                                                                   fabric.worker.Worker.
                                                                                   getWorker(
                                                                                     ).
                                                                                   getWorker(
                                                                                     "carolnode");
                                                                                 final fabric.lang.security.Principal alice =
                                                                                   alicestore.
                                                                                   getPrincipal(
                                                                                     );
                                                                                 final fabric.lang.security.Principal bob =
                                                                                   bobstore.
                                                                                   getPrincipal(
                                                                                     );
                                                                                 final fabric.lang.security.Principal carol =
                                                                                   carolstore.
                                                                                   getPrincipal(
                                                                                     );
                                                                                 final fabric.lang.security.Label aliceReads =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       readerPolicy(
                                                                                         store,
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         alice),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       topInteg(
                                                                                         ));
                                                                                 final fabric.lang.security.Label bobReads =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       readerPolicy(
                                                                                         store,
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         bob),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       topInteg(
                                                                                         ));
                                                                                 final fabric.lang.security.Label carolReads =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       readerPolicy(
                                                                                         store,
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         carol),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       topInteg(
                                                                                         ));
                                                                                 final fabric.lang.security.Label aliceWrites =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       bottomConf(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       writerPolicy(
                                                                                         store,
                                                                                         fabric.lang.security.PrincipalUtil._Impl.
                                                                                           topPrincipal(
                                                                                             ),
                                                                                         alice));
                                                                                 final fabric.lang.security.Label bobWrites =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       bottomConf(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       writerPolicy(
                                                                                         store,
                                                                                         alice,
                                                                                         bob));
                                                                                 final fabric.lang.security.Label carolWrites =
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   toLabel(
                                                                                     store,
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       bottomConf(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       writerPolicy(
                                                                                         store,
                                                                                         alice,
                                                                                         carol));
                                                                                 final fabric.lang.security.Label initialReadPast =
                                                                                   aliceReads;
                                                                                 final fabric.lang.security.Label initialWritePast =
                                                                                   aliceWrites;
                                                                                 final fabric.lang.security.Label initialWriteFuture =
                                                                                   aliceWrites.
                                                                                   join(
                                                                                     store,
                                                                                     bobWrites,
                                                                                     true).
                                                                                   join(
                                                                                     store,
                                                                                     carolWrites,
                                                                                     true);
                                                                                 final fabric.lang.security.Label initialReadFuture =
                                                                                   aliceReads.
                                                                                   meet(
                                                                                     store,
                                                                                     bobReads,
                                                                                     true).
                                                                                   meet(
                                                                                     store,
                                                                                     carolReads,
                                                                                     true);
                                                                                 final fabric.lang.security.Label initialReadFutureWritePast =
                                                                                   initialReadFuture.
                                                                                   join(
                                                                                     store,
                                                                                     initialWritePast,
                                                                                     true);
                                                                                 if (fabric.lang.security.PrincipalUtil._Impl.
                                                                                       equivalentTo(
                                                                                         alicestore.
                                                                                           getPrincipal(
                                                                                             ),
                                                                                         alice) &&
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                       equivalentTo(
                                                                                         alice,
                                                                                         store.
                                                                                           getPrincipal(
                                                                                             )) &&
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                       equivalentTo(
                                                                                         p,
                                                                                         alice) &&
                                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                                       relabelsTo(
                                                                                         initialReadFuture,
                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                           toLabel(
                                                                                             store,
                                                                                             fabric.lang.security.LabelUtil._Impl.
                                                                                               readerPolicy(
                                                                                                 store,
                                                                                                 fabric.lang.security.PrincipalUtil._Impl.
                                                                                                   topPrincipal(
                                                                                                     ),
                                                                                                 p),
                                                                                             fabric.lang.security.LabelUtil._Impl.
                                                                                               topInteg(
                                                                                                 )))) {
                                                                                     final SuperPing sp =
                                                                                       (SuperPing)
                                                                                         fabric.lang.Object._Proxy.
                                                                                         $getProxy(
                                                                                           ((SuperPing)
                                                                                              new SuperPing.
                                                                                              _Impl(
                                                                                              store,
                                                                                              initialReadFutureWritePast,
                                                                                              initialReadFutureWritePast,
                                                                                              initialReadPast,
                                                                                              initialWritePast,
                                                                                              initialReadFuture,
                                                                                              initialWriteFuture).
                                                                                              $getProxy(
                                                                                                )).
                                                                                             SuperPing$(
                                                                                               ));
                                                                                     final Message message =
                                                                                       (Message)
                                                                                         fabric.lang.Object._Proxy.
                                                                                         $getProxy(
                                                                                           ((Message)
                                                                                              new Message.
                                                                                              _Impl(
                                                                                              store,
                                                                                              initialReadFuture.
                                                                                                join(
                                                                                                  store,
                                                                                                  initialWritePast,
                                                                                                  true)).
                                                                                              $getProxy(
                                                                                                )).
                                                                                             Message$(
                                                                                               "Hello, World"));
                                                                                     final fabricated.util.Comparator comparator =
                                                                                       (fabricated.util.IdComparator)
                                                                                         fabric.lang.Object._Proxy.
                                                                                         $getProxy(
                                                                                           ((fabricated.util.IdComparator)
                                                                                              new fabricated.
                                                                                              util.
                                                                                              IdComparator.
                                                                                              _Impl(
                                                                                              store,
                                                                                              initialReadFuture.
                                                                                                join(
                                                                                                  store,
                                                                                                  initialWritePast,
                                                                                                  true)).
                                                                                              $getProxy(
                                                                                                )).
                                                                                             fabricated$util$IdComparator$(
                                                                                               ));
                                                                                     fabricated.util.List storeList =
                                                                                       (fabricated.util.LinkedList)
                                                                                         fabric.lang.Object._Proxy.
                                                                                         $getProxy(
                                                                                           ((fabricated.util.LinkedList)
                                                                                              new fabricated.
                                                                                              util.
                                                                                              LinkedList.
                                                                                              _Impl(
                                                                                              store,
                                                                                              initialReadFuture.
                                                                                                join(
                                                                                                  store,
                                                                                                  initialWritePast,
                                                                                                  true)).
                                                                                              $getProxy(
                                                                                                )).
                                                                                             fabricated$util$LinkedList$(
                                                                                               comparator));
                                                                                     storeList.
                                                                                       add(
                                                                                         fabric.lang.WrappedJavaInlineable.
                                                                                           $wrap(
                                                                                             "bobnode"));
                                                                                     storeList.
                                                                                       add(
                                                                                         fabric.lang.WrappedJavaInlineable.
                                                                                           $wrap(
                                                                                             "carolnode"));
                                                                                     {
                                                                                         fabric.
                                                                                           worker.
                                                                                           transaction.
                                                                                           TransactionManager $tm102 =
                                                                                           fabric.worker.transaction.TransactionManager.
                                                                                           getInstance(
                                                                                             );
                                                                                         int $backoff103 =
                                                                                           1;
                                                                                         $label98: for (boolean $commit99 =
                                                                                                          false;
                                                                                                        !$commit99;
                                                                                                        ) {
                                                                                             if ($backoff103 >
                                                                                                   32) {
                                                                                                 while (true) {
                                                                                                     try {
                                                                                                         java.lang.Thread.
                                                                                                           sleep(
                                                                                                             $backoff103);
                                                                                                         break;
                                                                                                     }
                                                                                                     catch (java.
                                                                                                              lang.
                                                                                                              InterruptedException $e100) {
                                                                                                         
                                                                                                     }
                                                                                                 }
                                                                                             }
                                                                                             if ($backoff103 <
                                                                                                   5000)
                                                                                                 $backoff103 *=
                                                                                                   2;
                                                                                             $commit99 =
                                                                                               true;
                                                                                             fabric.worker.transaction.TransactionManager.
                                                                                               getInstance(
                                                                                                 ).
                                                                                               startTransaction(
                                                                                                 );
                                                                                             try {
                                                                                                 ((SuperPing.
                                                                                                    _Proxy)
                                                                                                    sp).
                                                                                                   ping$remote(
                                                                                                     alicestore,
                                                                                                     SuperPingMain._Static._Proxy.$instance.
                                                                                                       get$worker$(
                                                                                                         ).
                                                                                                       getPrincipal(
                                                                                                         ),
                                                                                                     message,
                                                                                                     storeList);
                                                                                             }
                                                                                             catch (final fabric.
                                                                                                      worker.
                                                                                                      RetryException $e100) {
                                                                                                 $commit99 =
                                                                                                   false;
                                                                                                 continue $label98;
                                                                                             }
                                                                                             catch (final fabric.
                                                                                                      worker.
                                                                                                      TransactionRestartingException $e100) {
                                                                                                 $commit99 =
                                                                                                   false;
                                                                                                 fabric.
                                                                                                   common.
                                                                                                   TransactionID $currentTid101 =
                                                                                                   $tm102.
                                                                                                   getCurrentTid(
                                                                                                     );
                                                                                                 if ($e100.tid.
                                                                                                       isDescendantOf(
                                                                                                         $currentTid101))
                                                                                                     continue $label98;
                                                                                                 if ($currentTid101.
                                                                                                       parent !=
                                                                                                       null)
                                                                                                     throw $e100;
                                                                                                 throw new InternalError(
                                                                                                   ("Something is broken with transaction management. Got a signa" +
                                                                                                    "l to restart a different transaction than the one being mana" +
                                                                                                    "ged."));
                                                                                             }
                                                                                             catch (final Throwable $e100) {
                                                                                                 $commit99 =
                                                                                                   false;
                                                                                                 if ($tm102.
                                                                                                       checkForStaleObjects(
                                                                                                         ))
                                                                                                     continue $label98;
                                                                                                 throw new fabric.
                                                                                                   worker.
                                                                                                   AbortException(
                                                                                                   $e100);
                                                                                             }
                                                                                             finally {
                                                                                                 if ($commit99) {
                                                                                                     try {
                                                                                                         fabric.worker.transaction.TransactionManager.
                                                                                                           getInstance(
                                                                                                             ).
                                                                                                           commitTransaction(
                                                                                                             );
                                                                                                     }
                                                                                                     catch (final fabric.
                                                                                                              worker.
                                                                                                              AbortException $e100) {
                                                                                                         $commit99 =
                                                                                                           false;
                                                                                                     }
                                                                                                     catch (final fabric.
                                                                                                              worker.
                                                                                                              TransactionRestartingException $e100) {
                                                                                                         $commit99 =
                                                                                                           false;
                                                                                                         fabric.
                                                                                                           common.
                                                                                                           TransactionID $currentTid101 =
                                                                                                           $tm102.
                                                                                                           getCurrentTid(
                                                                                                             );
                                                                                                         if ($currentTid101 ==
                                                                                                               null ||
                                                                                                               $e100.tid.
                                                                                                               isDescendantOf(
                                                                                                                 $currentTid101) &&
                                                                                                               !$currentTid101.
                                                                                                               equals(
                                                                                                                 $e100.
                                                                                                                   tid))
                                                                                                             continue $label98;
                                                                                                         throw $e100;
                                                                                                     }
                                                                                                 }
                                                                                                 else {
                                                                                                     fabric.worker.transaction.TransactionManager.
                                                                                                       getInstance(
                                                                                                         ).
                                                                                                       abortTransaction(
                                                                                                         );
                                                                                                 }
                                                                                                 if (!$commit99) {
                                                                                                     
                                                                                                 }
                                                                                             }
                                                                                         }
                                                                                     }
                                                                                 }
                                                                                 return;
                                                                             }
                                                                             catch (final fabric.
                                                                                      worker.
                                                                                      RetryException $e106) {
                                                                                 $commit105 =
                                                                                   false;
                                                                                 continue $label104;
                                                                             }
                                                                             catch (final fabric.
                                                                                      worker.
                                                                                      TransactionRestartingException $e106) {
                                                                                 $commit105 =
                                                                                   false;
                                                                                 fabric.
                                                                                   common.
                                                                                   TransactionID $currentTid107 =
                                                                                   $tm108.
                                                                                   getCurrentTid(
                                                                                     );
                                                                                 if ($e106.tid.
                                                                                       isDescendantOf(
                                                                                         $currentTid107))
                                                                                     continue $label104;
                                                                                 if ($currentTid107.
                                                                                       parent !=
                                                                                       null)
                                                                                     throw $e106;
                                                                                 throw new InternalError(
                                                                                   ("Something is broken with transaction management. Got a signa" +
                                                                                    "l to restart a different transaction than the one being mana" +
                                                                                    "ged."));
                                                                             }
                                                                             catch (final Throwable $e106) {
                                                                                 $commit105 =
                                                                                   false;
                                                                                 if ($tm108.
                                                                                       checkForStaleObjects(
                                                                                         ))
                                                                                     continue $label104;
                                                                                 throw new fabric.
                                                                                   worker.
                                                                                   AbortException(
                                                                                   $e106);
                                                                             }
                                                                             finally {
                                                                                 if ($commit105) {
                                                                                     try {
                                                                                         fabric.worker.transaction.TransactionManager.
                                                                                           getInstance(
                                                                                             ).
                                                                                           commitTransaction(
                                                                                             );
                                                                                     }
                                                                                     catch (final fabric.
                                                                                              worker.
                                                                                              AbortException $e106) {
                                                                                         $commit105 =
                                                                                           false;
                                                                                     }
                                                                                     catch (final fabric.
                                                                                              worker.
                                                                                              TransactionRestartingException $e106) {
                                                                                         $commit105 =
                                                                                           false;
                                                                                         fabric.
                                                                                           common.
                                                                                           TransactionID $currentTid107 =
                                                                                           $tm108.
                                                                                           getCurrentTid(
                                                                                             );
                                                                                         if ($currentTid107 ==
                                                                                               null ||
                                                                                               $e106.tid.
                                                                                               isDescendantOf(
                                                                                                 $currentTid107) &&
                                                                                               !$currentTid107.
                                                                                               equals(
                                                                                                 $e106.
                                                                                                   tid))
                                                                                             continue $label104;
                                                                                         throw $e106;
                                                                                     }
                                                                                 }
                                                                                 else {
                                                                                     fabric.worker.transaction.TransactionManager.
                                                                                       getInstance(
                                                                                         ).
                                                                                       abortTransaction(
                                                                                         );
                                                                                 }
                                                                                 if (!$commit105) {
                                                                                     
                                                                                 }
                                                                             } }
            } }
        
        public SuperPingMain SuperPingMain$() { ((SuperPingMain._Impl) this.fetch(
                                                                              )).
                                                  jif$init();
                                                { this.fabric$lang$Object$(); }
                                                return (SuperPingMain) this.$getProxy(
                                                                              );
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.SuperPingMain$(); }
        
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
                                                                                   o)) instanceof SuperPingMain;
        }
        
        public static SuperPingMain jif$cast$SuperPingMain(final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (SuperPingMain._Impl.jif$Instanceof(o)) return (SuperPingMain) fabric.lang.Object._Proxy.
                                                                $getProxy(o);
            throw new java.lang.ClassCastException(); }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (SuperPingMain) this.$getProxy(
                                                                                );
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new SuperPingMain.
                                                             _Proxy(this); }
        
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
        
        final class _Proxy extends fabric.lang.Object._Proxy implements SuperPingMain.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((SuperPingMain._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((SuperPingMain.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((SuperPingMain.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((SuperPingMain.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(SuperPingMain._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final SuperPingMain._Static $instance;
            
            static { SuperPingMain._Static._Impl impl = (SuperPingMain._Static._Impl)
                                                          fabric.lang.Object._Static._Proxy.
                                                          $makeStaticInstance(SuperPingMain.
                                                                                _Static.
                                                                                _Impl.class);
                     $instance = (SuperPingMain._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements SuperPingMain._Static
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new SuperPingMain.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm114 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff115 = 1;
                                       $label110: for (boolean $commit111 = false;
                                                       !$commit111; ) { if ($backoff115 >
                                                                              32) {
                                                                            while (true) {
                                                                                try {
                                                                                    java.lang.Thread.
                                                                                      sleep(
                                                                                        $backoff115);
                                                                                    break;
                                                                                }
                                                                                catch (java.
                                                                                         lang.
                                                                                         InterruptedException $e112) {
                                                                                    
                                                                                }
                                                                            } }
                                                                        if ($backoff115 <
                                                                              5000)
                                                                            $backoff115 *=
                                                                              2;
                                                                        $commit111 =
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
                                                                                 RetryException $e112) {
                                                                            $commit111 =
                                                                              false;
                                                                            continue $label110;
                                                                        }
                                                                        catch (final fabric.
                                                                                 worker.
                                                                                 TransactionRestartingException $e112) {
                                                                            $commit111 =
                                                                              false;
                                                                            fabric.
                                                                              common.
                                                                              TransactionID $currentTid113 =
                                                                              $tm114.
                                                                              getCurrentTid(
                                                                                );
                                                                            if ($e112.tid.
                                                                                  isDescendantOf(
                                                                                    $currentTid113))
                                                                                continue $label110;
                                                                            if ($currentTid113.
                                                                                  parent !=
                                                                                  null)
                                                                                throw $e112;
                                                                            throw new InternalError(
                                                                              ("Something is broken with transaction management. Got a signa" +
                                                                               "l to restart a different transaction than the one being mana" +
                                                                               "ged."));
                                                                        }
                                                                        catch (final Throwable $e112) {
                                                                            $commit111 =
                                                                              false;
                                                                            if ($tm114.
                                                                                  checkForStaleObjects(
                                                                                    ))
                                                                                continue $label110;
                                                                            throw new fabric.
                                                                              worker.
                                                                              AbortException(
                                                                              $e112);
                                                                        }
                                                                        finally {
                                                                            if ($commit111) {
                                                                                try {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      commitTransaction(
                                                                                        );
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         AbortException $e112) {
                                                                                    $commit111 =
                                                                                      false;
                                                                                }
                                                                                catch (final fabric.
                                                                                         worker.
                                                                                         TransactionRestartingException $e112) {
                                                                                    $commit111 =
                                                                                      false;
                                                                                    fabric.
                                                                                      common.
                                                                                      TransactionID $currentTid113 =
                                                                                      $tm114.
                                                                                      getCurrentTid(
                                                                                        );
                                                                                    if ($currentTid113 ==
                                                                                          null ||
                                                                                          $e112.tid.
                                                                                          isDescendantOf(
                                                                                            $currentTid113) &&
                                                                                          !$currentTid113.
                                                                                          equals(
                                                                                            $e112.
                                                                                              tid))
                                                                                        continue $label110;
                                                                                    throw $e112;
                                                                                }
                                                                            } else {
                                                                                fabric.worker.transaction.TransactionManager.
                                                                                  getInstance(
                                                                                    ).
                                                                                  abortTransaction(
                                                                                    );
                                                                            }
                                                                            if (!$commit111) {
                                                                                
                                                                            } } }
                                     } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 78, 113, 10, -36, 30, 14,
    -56, 120, -57, 51, -52, 16, -87, -55, -35, 89, 5, 8, 59, 31, 108, 89, -32, -41,
    -116, -17, -51, 116, -37, -24, -110, -77 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1445981450000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16a8zj2HWYZnb26fXuetd27Y13/cWeGjvWeihSokh5uklF" +
                                                                "ihIpPkSJEily4Wz5EkXx/RIf7hZpCseGE7iBs3acIjGSYts0ydYpUqT50RpI" +
                                                                "UTSxazdojaIPtKkNo0UduEYaFG39o21KSt9j5pvZMVI4gAXo8vLee84997zu" +
                                                                "4T33je+0Hkzi1vs2qma7N9MyNJObY1WjGF6NE9PAXTVJlnXrK/pbrlGf/dav" +
                                                                "GM9fbV1lWo/rqh/4tq66r/hJ2nqC2al7FfDNFFgtqFsvtx7VG0BSTbZp6+rL" +
                                                                "WBG3TsLALS03SE8nuQv/Z9rAaz/3Y0/95gOtJ5XWk7YvpGpq63jgp2aRKq3H" +
                                                                "PdPTzDgZGoZpKK23+aZpCGZsq65d1QMDX2k9ndiWr6ZZbCYLMwncfTPw6SQL" +
                                                                "zfgw51ljQ35Qkx1nehrENflPHcnPUtsFGDtJbzGthza26RpJ1PorrWtM68GN" +
                                                                "q1r1wHcyZ6sADhiBcdNeD3/MrsmMN6punoFcc2zfSFvvvQxxvuLrdD2gBn3Y" +
                                                                "M9NtcD7VNV+tG1pPH0lyVd8ChDS2fase+mCQ1bOkrWffFGk96JFQ1R3VMl9J" +
                                                                "W++6PI4/dtWjHj2wpQFJW++4POyAqZbZs5dkdpu0vsP9hU991Cf9q60rNc2G" +
                                                                "qbsN/Y/UQM9fAlqYGzM2fd08Aj7+Qeaz6ju/+ImrrVY9+B2XBh/H/PZf/uO/" +
                                                                "+OLzv/Ol45gfuseYmbYz9fQV/XXtiX/5HvzG4IGGjEfCILEbVbhj5Qep8qc9" +
                                                                "t4qw1vZ3nmNsOm+edf7O4nflH/8189tXW49RrYf0wM28WqvepgdeaLtmPDF9" +
                                                                "M1ZT06Baj5q+gR/6qdbDdZ2xffPYOttsEjOlWtfcQ9NDweG9ZtGmRtGw6OG6" +
                                                                "bvub4Kwequn2UC/C1unvmfr/Q63WAx87fdYS/wiwDTwTsJOtudkAozgItaAA" +
                                                                "RoGeeaaf1goQxL7puoAauoBlp0BtzrGtA2aheqFbS7S2Et3UAg1IYh0QGtHz" +
                                                                "tUqxqu3frIeGf9YTFM0Kn8qvXKmZ/149MExNTWpJnmoVxru14ZCBa5jxK7r7" +
                                                                "qS9SrWe++PMHzXq0sYak1ugD767U2vCey37kdtjXMoz44y+88pWjVjawp6xN" +
                                                                "W2+9g6iajscbA7tZu6ybtct640pxE/889esHPXooORjcOeij9Qo+7Aa1syta" +
                                                                "V64cFvH2A/BBe2rZO7VPqd3G4zeEj0z/0ife90CttmF+rZZeM/T6ZSO6cD1U" +
                                                                "XVNry3hFf/Lj3/qfv/HZV4MLc0pb1++y8rshGyt932WOxIFuGrUXvED/wRP1" +
                                                                "t1754qvXrzYe5tHa+aU1DxpP8vzlOe6w1ltnnq9hxYNM6y2bIPZUt+k6c1eP" +
                                                                "pds4yC9aDpJ+4lB/25/Uvyv1//82/0axm4bmWbs3/NSoTs6tKm1985uffP2b" +
                                                                "P/npD9/dd/LC9+zKg9gx4+thLTndDlX3T4slbDq+n0ag1r9G9z8MdV/sIh/q" +
                                                                "dW7Uv/BoCY0GXZLaYSd5SQh/8d/+/h92D3vs2abz5G27k2Cmt25zdA2yJw8u" +
                                                                "7W0XCrmMTbMe9wef43/2M9/5+MsHbaxHvP9eE15vyoYdas2GIP7Yl6J/9/X/" +
                                                                "+Pq/unqhwWnroTDT6oUdKH9/jegDF1PVPtCt/XBNSXJ95XuBYW9sVXPNxhr+" +
                                                                "95N/Hvyt//qpp44q7dYtRwWJWy9+bwQX7e/GWj/+lR/7X88f0FzRmz34gh0X" +
                                                                "w46O/ZkLzMM4VsuGjuKvfu25n/899Rdr067dcmJX5tHTHpbXOqyqe9DXm4cS" +
                                                                "utQHN8X7ikPfew7t15K7N7lxEy1c2JsCvPELz+I/8u2jDzu3twbHD9/Dh4nq" +
                                                                "ba4A+jXvf1x930P/9GrrYaX11CFQUf1UVN2skapShxoJftrItN56R/+dYcNx" +
                                                                "j7x17k/ec9nWb5v2sqVf+M663oxu6o8djfugB8WVVthUbh0gPnAobzTFiwce" +
                                                                "XU0bL9oEcGmN2PZr13kAS1sPnxrqAeIdaevtRyO6eWy+KR0eTd+zR0tpSuR0" +
                                                                "yloZH+zchG5CzTt+75kfaKofaoofaYofPZv32Z2rXz8zfLEOJGuluX6c+4yU" +
                                                                "pw7a0/Du5jHkOnS8O2093phx6Kpp4/+Ke9BVa8QTF8BMUEdrP/Wffuarf/39" +
                                                                "X681YNp6cN9Ipxb8bTNwWRPO/uQbn3nuLa9946cOBldbG//r8B891WBlmoJI" +
                                                                "W881ZAtBFusmoyYpe7AQ0zij/G5N5GPbq33D/jTcMj/x2if/5OanXjta4TEm" +
                                                                "ff9dYeHtMMe49LDKtx7XV8/yw/eb5QAx/i+/8eo//DuvfvwYsz19Z4RF+Jn3" +
                                                                "d//1//nqzc9948v32JuvucFxu70s7mfeIHsJNTz7MaCKw8NVsfCztdxDRgGy" +
                                                                "Rte9vU7jqOZ7O5LSSHKNkzhcdC0yiPqDIkEG3SHC5jnbTRICL1nUstUon2OB" +
                                                                "MCVo0MIWxlAmSwkvnAVGmfSISFbU2PFEiQonZd9aUXsfQFIXRuShN9bKCFH8" +
                                                                "EIkBBAGAAlgPut12vwppaevAtLOiB4wcefCS5ib9eQWthG6AOdCq7E23FcVH" +
                                                                "GWlDaJkgrJfO1hjrMRLtCBg2Vrc7MbLLadmphBmiUIIjK6Y4l/qRozOiKU4n" +
                                                                "aGoN+nZA2/R0GJbr0Zjt2H1ImFhCYAVgAHBxFOEDu6SdiCamVARZE3cliBjh" +
                                                                "4tuuhvbgcAXTu9SYJ2XbdkadwkGXLiP4lCcrsOuzRjUqwKnGuMqsWHntteS4" +
                                                                "0pIV83wrSDNnRdjaTg0yT8dibGBarO7k8qYHBVaI9mXa8VhZ0C0jnAisMaWt" +
                                                                "xXjMCstpJAdqEjqAwKtLbGH1S3WlBEUARxZeunFliOxyhwl8tOdG6ZTuDFYe" +
                                                                "vRqDDrBkHBEHwYU2YsBCnY4yNNV6+kjK1XEK9gCUmm2gAoNSczEIp4KaR96U" +
                                                                "op2lifu7reCqdGT2E60O6Siv72zJrTElGGPlzhVVmncMMRL48TYVLHyiqGFH" +
                                                                "5jhRgPFlIk0gh5PYOZzAUsDaqpBO7Mj0Zbi98qZl7WNFNHOYkTB04qAvZyTj" +
                                                                "9UiQovYSRfSjaWcSSwqNFgotzMd+j57loOvmPCnjHYoQsTWgrEzJ2kxt3/NC" +
                                                                "dhwKY1VtR0LHwgypH4ZEvtZRmB65EWFj5KToEKPdNPMzBNxwXh9bUwuWcELb" +
                                                                "sRFUm+ReB+Rm/lQzyWrqr/YEHYos5WCj3I9wawd0xCHIGaRIzJbiUI9DuMzV" +
                                                                "yTZJBhgIIEFOGThs99EKAXi2QtoyG1d7u2f0fLCHbUxNYvq0PVkYXZAxkKS7" +
                                                                "gZ1JqlFYlnKcNF/xnuku+V2MKGq5o1SZwNZmZ5EwbAfau7uqTKCYjCabLeHH" +
                                                                "dEjtI5+qqddWK3Gx9OzFciMoYkT1slVtKfmaZKR1ItLyqOvRi/l6l3S9pRBI" +
                                                                "q3WpjFUxRqZ7YkwIW2e4SOVxV1h5EbQ2OBbbK2ZH3oEu6rf5lR2yYtTPHXVf" +
                                                                "dtWIVgJxRKnqklJiAxNjEeEWeLpiFcjusdZwroW4snQilaQ78yW9I2fLodGT" +
                                                                "VtOAm+TCgJAiQFrkoLHjVLsf7bFwIo/wcIGLbZBewiNsvFbMiRVhKJ9N1YCR" +
                                                                "8FwJOjEdqcaqnBF9l7Y4vCAk2FyjYl9bADQyS+bbJNXleS4R3J7v0j11aXph" +
                                                                "pwvIDoCyjEGHjEioMA2RkTrcldNOMqaGsFv/U2i7nPjwIApJb9F1J2i11bgp" +
                                                                "LrFjQpqLVAVjcxuHbLbkPNCa9dd5HgyzrcrRSyh1vW5c7OfDpJAkOCChZJYN" +
                                                                "qzxYZ/BwuRBH6wWZAGZ3CmVdrtcNOTycSYY8miwskcPrT94Y72/CSBbndHdm" +
                                                                "uCsOW2HLCZVP5r6HTWu5O90lM5SI2d7BvVnNWGiFz9XA33R6vKwxAFRxBsL1" +
                                                                "BytSQWu5ObPxTBSocM6idMqJHUkdkhi03aIqofRpBBwohK3UVG5lL3fiObKL" +
                                                                "+zAwGpauK613k3SxxNGQjvzt1NHYkA61tRLRKeMvQm+XiLIxkyR7EYOsbs8B" +
                                                                "LVQ1I0PiLopOnQpHXFfNmOFkXyWFOPMDiJ2jvkhRve0giTR43xl4YFrhIwvr" +
                                                                "jay2HOQapbND1abn23SgEi4Cd/qZycnQiLCJzjTNtj2dYTkP6VILwGHydgED" +
                                                                "OLaVrRVKTdorSSLEJB1NR7bBxwZcmAk0tjV37/Tt/SodL8UxOBsshixA2CLn" +
                                                                "kACdLdYUMQkxJ170e9sVxs2Fjq2y/g7rtGVDoFRU5pINPN/3WAPvjQKKmBFD" +
                                                                "iy2ETI7p5Wo7FaTtOlIpYtAGyWoZRau2NusOASeaTKS+ONvugXxIwIMd5Dqg" +
                                                                "ORl3gYWb9xZERfR1jQXCwuHMKbRxIi3IGIJts77ZBqVNPJcIreYJwRCcuvQd" +
                                                                "VnYV0psYel4W2QhP/DYXeLJPgDk9AYg6DoHloTksBhSKCyagr8exi87sSVDR" +
                                                                "O9hZ1pFpOuV8cqhIHUtZRrKSpGnHx1A18icy4XpI21MLBfX6zJTpsyPGHgcr" +
                                                                "T8NGIrE26HJkZViKrLaWMsWi0RKbY5jVBRe+pYwSYLxajwl2kreZ4UxGwWUy" +
                                                                "CGaewi9lZpay5Jai+JlWu4Cd5BbraZFXYGiQ2ra94SxBrMbdtZ214XwJ1xav" +
                                                                "LvVZaeZyOcjnJEBZ3iKbJysHHZcyQiFuu0Q4F5kQ+yEKCoEzWTocDs9mAGb1" +
                                                                "2SUyHQCeZyyGRbTg+jgzje1OpmVpCQD8eAf15dL2+6bglqaDqpLW8/i9Ot1X" +
                                                                "k858t4/7oEGOFiuLI7lBoa/2Kdy2OJOfbXQZE0UoavM6w3nM0E/G/bwPTSpH" +
                                                                "wSbiHBMHYBWzibrZl3syLZCBtwTHM1lgLHA3D+1qiozZkuqNO4NtXGFZqa1h" +
                                                                "Gu57aiVO9TW3A3fSZOAjAOxCvaI93+p7Je3y4ihvb7BqhExdqCtDnYnhc8nY" +
                                                                "1eZdhlaKDbZzTEzzFGOWhRKs87stGEX7PIGGfZzGVR7xK2vJ6yZdVlwvEKpY" +
                                                                "9OslzazEaxOaau4QYhsi9GqNe6Gpl56mLjAimS5AmI8JPVR3FpZue8QYQ8PS" +
                                                                "8yJV1R0fzALCH4eiYfIcL83bmtxJXZs0Md6Ju+x+RiLZfgHw/SgznELdbwWt" +
                                                                "S6WpDK+XBlVWo2SdRCg97JOkmy8k3JF3Wx7swiYHelVPhkKdgeDabmf10tXK" +
                                                                "THlfUVCt5BmC6kijcjxF+mjhxJyuMuXYX/CTqsdvN3kcgPpciVJyhtZOTy3b" +
                                                                "VL6ZgxY8olJh3Lf5Aa+tBGXAIHNSAfx6o6uUbIkWAxNbbNS41rtKNARVN832" +
                                                                "BncMKiWRnupMibUXBdA8UjiR1Dh74VWDQbHj0sScQb69pfRI8CMgrXciANYT" +
                                                                "CbA2Lrbpov2uWaFlBmnpxl+6cpcdzuyoXM0mGwYpV5GOJtiKinl7y4xr187i" +
                                                                "PT6VOAIbGMPRxu9Yi13h8JrZk3ORl4ecOEm0dUQ52mSXD0rD3m19cjVIlWAA" +
                                                                "7vmBCi82EgFUlF7xs4mEy4HXnmqDngIhA3guIutIbq+D7TyfUVZfdiFXoZxN" +
                                                                "1xPaRcBWLOUt3B2rkXytCd7aZlhowPKcyIOYvRcQqELQbFJ1JV2XQ6LNEJHM" +
                                                                "zRaJsGU6q/2UV+HROjM7VhdfbmllsLeHEq5W7RG/HnhMf9hRh2PebPPwGsuz" +
                                                                "0AzVmpPjAWWrOx5PBWc5n4DbySSd5E6IkgtxakXhpN5niKKcQGMzlqaLLFov" +
                                                                "RCUIBxvUtEWGyw14pcJ6vfWJ1dBftFWmjrvcAHUoqScpuT7Z4fLO65J2Ia0J" +
                                                                "xm2THX/sGwWKOJrdFdeys+qhoy5ArmdwkZOVq6/oBRZmLiOmRrTZQzvAriUn" +
                                                                "zCqakPttTdL8BQf1KRkeLa21o4b0GC5HWTy150EXhaZbTN64gQvt/H5fM/vd" +
                                                                "sKgDuD1V7Xe9OI4QfzcA+rTTERKb4seIL65YYuNquVAOUISSMoMDNUe0oTxe" +
                                                                "qvSeakMxv4L7gMeSoAtBO2/CFOGaGbK+FS+XuL6pGLSKDBNJBsKgiOrvg1gd" +
                                                                "jdDIR1kMVLPFnkOXVKfjMQ4k4BNruhF8frmuelb9sQTuOka32zVwABVY0+8p" +
                                                                "fVJQ1539RDXbWezDtVq3gWUcV0OnrSjUZL6fBho5788i0+nLIq93BBobk4K7" +
                                                                "ophlqA+oEpu2c2a40uUKV+fjXd1rr1jQ5KIw3m1sfm+uGb1ANuXWlOxaUZVK" +
                                                                "q81hprbLZLytaolXKT2FqxlPrOe7JboDWUxewwMQQfmaeZIOJfhYLTOk1x8B" +
                                                                "yUAk6VlbGCVqW9rrfXjAZxEOMrtetiVXe3Q2QrfAbJXTVO3k6vA+gK0ptsy4" +
                                                                "qbAFQ2bhu6InsSkvdcwOwLSNce6Z6coMuvsta3FA1/OmwFAy+7wwYxN6kBvj" +
                                                                "cIFtGRQdF4P+HFc42rZHVcqGkLIzpXb97aab8liaFXZIDrZjGc3F9aTktQog" +
                                                                "wALZDfVk7ePCYNzNpqOuDxMgbc07HcncFAbXnQwWBuNVRhm3pfXY4irE6uoc" +
                                                                "X9m5BQUssQdKtIvTEwVNyY27DzsxCMkDfKe0t0u13iP8uZWxZh3xjqv1DGHa" +
                                                                "m0n9SaJgDNljCzboW+lmlMY2r1eiUiSUneNLRmd76gjcoCwu7uuP2oTewjwU" +
                                                                "LtAJg9rkunBKmB127SQnqdEWdxy4r7mwxuO9bEAR0zoCUKyBNh/1BbIDjbsx" +
                                                                "D2UOUerldC6YMkD0+oW/wbPcn8ZibDl8d2SLrK87s3XaDsO1J8FdxWUyDGqT" +
                                                                "ya5PTnWyTYyGoYAiWMa2V0Tf2XW09qpC6VLIECHs7ILZuq/5cFFOh2u1cKxM" +
                                                                "l+EZrSFhvO5ujBm3EzlruFBH8c6L8l0PZ1JRH/CAujGjZTqIIUhnS2hZohRE" +
                                                                "UjDT5ioGTAZcXn9sid3eRCNVP5LDIa9tcart7hltwWnzolyMzakdzpdbYp7P" +
                                                                "nY61o0AI6tUfXqNCNGSSMxIF8Ahvvq2/mtVNAiNcPvfEqruiO5TQzyN0DjlR" +
                                                                "ohTm0uYBzRQgs9pF673hqtNyylY9e0VuQMUAO0CxnCt+sZnl+JpPtWqf8SLa" +
                                                                "NaExQpJtDOzOSJ/wxKRfLvyYMbcwuOvt3IXZJ9ZRHXADujYXF3V81FnEAK/O" +
                                                                "QYifo1Cx9XekGSOpXOKratBVhEKqzC3KBD6D8JOZlbPsUCJZv4DDNJjr9ScL" +
                                                                "j4GglQODLm3GYLQQQJQcTubqmHYXJKby5SjwO2sdQJP5uGjLer0VWDnIrAO+" +
                                                                "h689ZiOCStfZjYulsMBXlrqZs8nU3u/b5l7t7RJFxRZ0Ue9VmrLrabk3lvdq" +
                                                                "UKxT1NZMauB3gG4HyYO+OzcgDJaIPo5Mfd4pwG62WfFJBxlo/rTrh7ABL1Fx" +
                                                                "MB3C0XocdAwVTmSa4QaOVjpYrKxGuietfLjTEcAIKZVRRWkGwECjWGovZ9sV" +
                                                                "j26c3CsyRSYWUm9NxVGxXPPbSbZIJqNJhgc0JCrmiLPLtmJ0Z1TfSCfaYERq" +
                                                                "+1HV62FconaKxCoBO42qpdaVncytZhhs+slULU0Y7paYUXM5Mfo7clR4AsAB" +
                                                                "CEUEHBmuCnk8svKBqqAilNGUksDLQdkBIIYtPYhJB2BYu4xe5W8rNM2lcs2g" +
                                                                "xtQuhzm6qSa4U2UcRwCSryYAG2zhQTJY73bUfqdhlJ/WHzqzvj3OZksHwesd" +
                                                                "d8va5nIy9bQ1txUlXhMBazaL8/GGLOXhcPjSS82x3fr03PLth+PW81z38biy" +
                                                                "6SMP53zFvQ9vW6dJhovD9lZz+vjcm+WhDyePr//Ea583Zn8LvHoKvkpbj6ZB" +
                                                                "+CHX3Jvubagebs4x77rnwB6y7xfH79/49nMD3PnP1vEc872XZr48+lfZN748" +
                                                                "+YD+6autB87P2e9K+d8JdOvO0/XHYjOtt8XlHWfsJ8cEWk30Uw0P3l3/n2u1" +
                                                                "Hvjl0+fnmt5nDufvb7/tOPruU/iLs/DiHOPVBuOTp5h+7vT5ydswXkqGXDnP" +
                                                                "ZD53iXm1RA9ZluNJ8+//ynff/cXrf/jdI+MuXx24beB/e+Pr3/7aW5/7wiEZ" +
                                                                "ea3JBB8WfvnOxd1XKu64KXFY9+Pnq0KaVf1o/X9nvcKvnD7/SdpSvp8pvVGQ" +
                                                                "aa7Z5JBPs+Z/htgP6yPvksL5+XjQZGAuvTaV9HupwnlG5CHX9K10exg5Pz3p" +
                                                                "bx7LtPVAzfum6t8b2ZUDsiOepsib4vDqF+cUXz3Oe5ZceeYi9YG7gW82Wb5D" +
                                                                "muc8EXQYYAc3z2/1nI0o7skF7bjsw6xN");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("8cJ9kngfu0/fx5vir6WtB/WGrHvkgo55tCMlBwjnTSzk7pUShW6GZ4naZw/A" +
                                                                  "P90UZtq65jXXEO7B+2v7wD7quHau3c/c61rIx+7yAt+LDZ+9T9/Bp/xs2nri" +
                                                                  "jnsS1y+8ywU1DzdAH2iWfHph5coxqf+1N0+5h9/X1Ppd90s+DPdfBDvdD4Ed" +
                                                                  "5MaHT6JMTewoC1LzhWPq+qRh6cnO3ly3/X3gmCNzc9tdihdunHw03drJzTtX" +
                                                                  "/sKNW6/eCMPb/WtTfPCcDYffQ5fZ0PT+Qhjeh9O/dJ++v9kUfyNtvevNiG36" +
                                                                  "f+aSPB5pwN92tzx+8wdQHrG9r+m4XSB22gjg5OWPCCeX+X2X30lbD59iKO4U" +
                                                                  "w8P3EsPfvq8Y3rhP3xea4vW09cgZifdi+xOt4+Z5ie1XPv+Dx/ZjKv7k1Bq0" +
                                                                  "IHBN1T/w/ywqCTYvvHzI058cp/yo6mmvHpzYsXZ0gof6SfCRcS0xe3PyQnDy" +
                                                                  "0ksnfua6N06OYUwN7SbmrdOc/sEHJqaexXZa3mRUzXRXTTpY1etNPcG3pu68" +
                                                                  "cO/pzoCOb+eQx1c/aDhcO+uaYy/cePEkODTfuHVKQk2Vfb6qkzuYdLdJ3y9k" +
                                                                  "Ovjl+/v6p29f6ZFH9740cED2D+6jcv+oKf5e7X/vlMq9doiHTwV4uz6eXa16" +
                                                                  "15kenjU0vde/L2tuXn/7MOB377OOLzXFP05b72zWoatJev0OCbzpfvL03YZU" +
                                                                  "/eAZ0qkFHSV9cnANB+VMDj7ssItcz0KjJvXQfPLSm5jTn0K/b52c4j1aDR/U" +
                                                                  "BJTfD8Q39cDfHNG9cG48zVT/3/veV+/rcP/Fffq+1hT/LG295TaWHvXt8s3N" +
                                                                  "MLzQw2ezuLld/sZ//3PffeiR5TdOL9G0Trjosf/w/BNfKn6v+9WnfvXLfyA/" +
                                                                  "+Mit97ryN/7NT//RP0///bc+/ff/H5M19Iz1LgAA");
}
