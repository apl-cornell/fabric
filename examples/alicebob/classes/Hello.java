import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;

interface Hello extends fabric.lang.Object {
    
    public Hello Hello$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1443794726000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements Hello
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            Hello._Impl.main(arg1);
        }
        
        public Hello Hello$() { return ((Hello) fetch()).Hello$(); }
        
        public void jif$invokeDefConstructor() {
            ((Hello) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return Hello._Impl.jif$Instanceof(arg1);
        }
        
        public static Hello jif$cast$Hello(fabric.lang.Object arg1) {
            return Hello._Impl.jif$cast$Hello(arg1);
        }
        
        public _Proxy(Hello._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements Hello
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm48 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff49 = 1;
                $label44: for (boolean $commit45 = false; !$commit45; ) { if ($backoff49 >
                                                                                32) {
                                                                              while (true) {
                                                                                  try {
                                                                                      java.lang.Thread.
                                                                                        sleep(
                                                                                          $backoff49);
                                                                                      break;
                                                                                  }
                                                                                  catch (java.
                                                                                           lang.
                                                                                           InterruptedException $e46) {
                                                                                      
                                                                                  }
                                                                              } }
                                                                          if ($backoff49 <
                                                                                5000)
                                                                              $backoff49 *=
                                                                                2;
                                                                          $commit45 =
                                                                            true;
                                                                          fabric.worker.transaction.TransactionManager.
                                                                            getInstance(
                                                                              ).
                                                                            startTransaction(
                                                                              );
                                                                          try { final fabric.lang.security.Principal alice =
                                                                                  p;
                                                                                final fabric.lang.security.Principal bob =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getWorker(
                                                                                    "bobnode").
                                                                                  getPrincipal(
                                                                                    );
                                                                                final fabric.worker.Store alicestore =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getStore(
                                                                                    "alicenode");
                                                                                Ping ping =
                                                                                  null;
                                                                                {
                                                                                    Ping ping$var30 =
                                                                                      ping;
                                                                                    fabric.
                                                                                      worker.
                                                                                      transaction.
                                                                                      TransactionManager $tm35 =
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        );
                                                                                    int $backoff36 =
                                                                                      1;
                                                                                    $label31: for (boolean $commit32 =
                                                                                                     false;
                                                                                                   !$commit32;
                                                                                                   ) {
                                                                                        if ($backoff36 >
                                                                                              32) {
                                                                                            while (true) {
                                                                                                try {
                                                                                                    java.lang.Thread.
                                                                                                      sleep(
                                                                                                        $backoff36);
                                                                                                    break;
                                                                                                }
                                                                                                catch (java.
                                                                                                         lang.
                                                                                                         InterruptedException $e33) {
                                                                                                    
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        if ($backoff36 <
                                                                                              5000)
                                                                                            $backoff36 *=
                                                                                              2;
                                                                                        $commit32 =
                                                                                          true;
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          startTransaction(
                                                                                            );
                                                                                        try {
                                                                                            ping =
                                                                                              (Ping)
                                                                                                fabric.lang.Object._Proxy.
                                                                                                $getProxy(
                                                                                                  ((Ping)
                                                                                                     new Ping.
                                                                                                     _Impl(
                                                                                                     alicestore,
                                                                                                     alice,
                                                                                                     bob).
                                                                                                     $getProxy(
                                                                                                       )).
                                                                                                    Ping$(
                                                                                                      ));
                                                                                        }
                                                                                        catch (final fabric.
                                                                                                 worker.
                                                                                                 RetryException $e33) {
                                                                                            $commit32 =
                                                                                              false;
                                                                                            continue $label31;
                                                                                        }
                                                                                        catch (final fabric.
                                                                                                 worker.
                                                                                                 TransactionRestartingException $e33) {
                                                                                            $commit32 =
                                                                                              false;
                                                                                            fabric.
                                                                                              common.
                                                                                              TransactionID $currentTid34 =
                                                                                              $tm35.
                                                                                              getCurrentTid(
                                                                                                );
                                                                                            if ($e33.tid.
                                                                                                  isDescendantOf(
                                                                                                    $currentTid34))
                                                                                                continue $label31;
                                                                                            if ($currentTid34.
                                                                                                  parent !=
                                                                                                  null)
                                                                                                throw $e33;
                                                                                            throw new InternalError(
                                                                                              ("Something is broken with transaction management. Got a signa" +
                                                                                               "l to restart a different transaction than the one being mana" +
                                                                                               "ged."));
                                                                                        }
                                                                                        catch (final Throwable $e33) {
                                                                                            $commit32 =
                                                                                              false;
                                                                                            if ($tm35.
                                                                                                  checkForStaleObjects(
                                                                                                    ))
                                                                                                continue $label31;
                                                                                            throw new fabric.
                                                                                              worker.
                                                                                              AbortException(
                                                                                              $e33);
                                                                                        }
                                                                                        finally {
                                                                                            if ($commit32) {
                                                                                                try {
                                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                                      getInstance(
                                                                                                        ).
                                                                                                      commitTransaction(
                                                                                                        );
                                                                                                }
                                                                                                catch (final fabric.
                                                                                                         worker.
                                                                                                         AbortException $e33) {
                                                                                                    $commit32 =
                                                                                                      false;
                                                                                                }
                                                                                                catch (final fabric.
                                                                                                         worker.
                                                                                                         TransactionRestartingException $e33) {
                                                                                                    $commit32 =
                                                                                                      false;
                                                                                                    fabric.
                                                                                                      common.
                                                                                                      TransactionID $currentTid34 =
                                                                                                      $tm35.
                                                                                                      getCurrentTid(
                                                                                                        );
                                                                                                    if ($currentTid34 ==
                                                                                                          null ||
                                                                                                          $e33.tid.
                                                                                                          isDescendantOf(
                                                                                                            $currentTid34) &&
                                                                                                          !$currentTid34.
                                                                                                          equals(
                                                                                                            $e33.
                                                                                                              tid))
                                                                                                        continue $label31;
                                                                                                    throw $e33;
                                                                                                }
                                                                                            }
                                                                                            else {
                                                                                                fabric.worker.transaction.TransactionManager.
                                                                                                  getInstance(
                                                                                                    ).
                                                                                                  abortTransaction(
                                                                                                    );
                                                                                            }
                                                                                            if (!$commit32) {
                                                                                                ping =
                                                                                                  ping$var30;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                                {
                                                                                    Ping ping$var37 =
                                                                                      ping;
                                                                                    fabric.
                                                                                      worker.
                                                                                      transaction.
                                                                                      TransactionManager $tm42 =
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        );
                                                                                    int $backoff43 =
                                                                                      1;
                                                                                    $label38: for (boolean $commit39 =
                                                                                                     false;
                                                                                                   !$commit39;
                                                                                                   ) {
                                                                                        if ($backoff43 >
                                                                                              32) {
                                                                                            while (true) {
                                                                                                try {
                                                                                                    java.lang.Thread.
                                                                                                      sleep(
                                                                                                        $backoff43);
                                                                                                    break;
                                                                                                }
                                                                                                catch (java.
                                                                                                         lang.
                                                                                                         InterruptedException $e40) {
                                                                                                    
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        if ($backoff43 <
                                                                                              5000)
                                                                                            $backoff43 *=
                                                                                              2;
                                                                                        $commit39 =
                                                                                          true;
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          startTransaction(
                                                                                            );
                                                                                        try {
                                                                                            int result =
                                                                                              ping.
                                                                                              m1(
                                                                                                );
                                                                                            fabric.runtime.Runtime._Impl.
                                                                                              getRuntime(
                                                                                                p).
                                                                                              out(
                                                                                                ).
                                                                                              println(
                                                                                                result);
                                                                                        }
                                                                                        catch (final fabric.
                                                                                                 worker.
                                                                                                 RetryException $e40) {
                                                                                            $commit39 =
                                                                                              false;
                                                                                            continue $label38;
                                                                                        }
                                                                                        catch (final fabric.
                                                                                                 worker.
                                                                                                 TransactionRestartingException $e40) {
                                                                                            $commit39 =
                                                                                              false;
                                                                                            fabric.
                                                                                              common.
                                                                                              TransactionID $currentTid41 =
                                                                                              $tm42.
                                                                                              getCurrentTid(
                                                                                                );
                                                                                            if ($e40.tid.
                                                                                                  isDescendantOf(
                                                                                                    $currentTid41))
                                                                                                continue $label38;
                                                                                            if ($currentTid41.
                                                                                                  parent !=
                                                                                                  null)
                                                                                                throw $e40;
                                                                                            throw new InternalError(
                                                                                              ("Something is broken with transaction management. Got a signa" +
                                                                                               "l to restart a different transaction than the one being mana" +
                                                                                               "ged."));
                                                                                        }
                                                                                        catch (final Throwable $e40) {
                                                                                            $commit39 =
                                                                                              false;
                                                                                            if ($tm42.
                                                                                                  checkForStaleObjects(
                                                                                                    ))
                                                                                                continue $label38;
                                                                                            throw new fabric.
                                                                                              worker.
                                                                                              AbortException(
                                                                                              $e40);
                                                                                        }
                                                                                        finally {
                                                                                            if ($commit39) {
                                                                                                try {
                                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                                      getInstance(
                                                                                                        ).
                                                                                                      commitTransaction(
                                                                                                        );
                                                                                                }
                                                                                                catch (final fabric.
                                                                                                         worker.
                                                                                                         AbortException $e40) {
                                                                                                    $commit39 =
                                                                                                      false;
                                                                                                }
                                                                                                catch (final fabric.
                                                                                                         worker.
                                                                                                         TransactionRestartingException $e40) {
                                                                                                    $commit39 =
                                                                                                      false;
                                                                                                    fabric.
                                                                                                      common.
                                                                                                      TransactionID $currentTid41 =
                                                                                                      $tm42.
                                                                                                      getCurrentTid(
                                                                                                        );
                                                                                                    if ($currentTid41 ==
                                                                                                          null ||
                                                                                                          $e40.tid.
                                                                                                          isDescendantOf(
                                                                                                            $currentTid41) &&
                                                                                                          !$currentTid41.
                                                                                                          equals(
                                                                                                            $e40.
                                                                                                              tid))
                                                                                                        continue $label38;
                                                                                                    throw $e40;
                                                                                                }
                                                                                            }
                                                                                            else {
                                                                                                fabric.worker.transaction.TransactionManager.
                                                                                                  getInstance(
                                                                                                    ).
                                                                                                  abortTransaction(
                                                                                                    );
                                                                                            }
                                                                                            if (!$commit39) {
                                                                                                ping =
                                                                                                  ping$var37;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   RetryException $e46) {
                                                                              $commit45 =
                                                                                false;
                                                                              continue $label44;
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   TransactionRestartingException $e46) {
                                                                              $commit45 =
                                                                                false;
                                                                              fabric.
                                                                                common.
                                                                                TransactionID $currentTid47 =
                                                                                $tm48.
                                                                                getCurrentTid(
                                                                                  );
                                                                              if ($e46.tid.
                                                                                    isDescendantOf(
                                                                                      $currentTid47))
                                                                                  continue $label44;
                                                                              if ($currentTid47.
                                                                                    parent !=
                                                                                    null)
                                                                                  throw $e46;
                                                                              throw new InternalError(
                                                                                ("Something is broken with transaction management. Got a signa" +
                                                                                 "l to restart a different transaction than the one being mana" +
                                                                                 "ged."));
                                                                          }
                                                                          catch (final Throwable $e46) {
                                                                              $commit45 =
                                                                                false;
                                                                              if ($tm48.
                                                                                    checkForStaleObjects(
                                                                                      ))
                                                                                  continue $label44;
                                                                              throw new fabric.
                                                                                worker.
                                                                                AbortException(
                                                                                $e46);
                                                                          }
                                                                          finally {
                                                                              if ($commit45) {
                                                                                  try {
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                        getInstance(
                                                                                          ).
                                                                                        commitTransaction(
                                                                                          );
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           AbortException $e46) {
                                                                                      $commit45 =
                                                                                        false;
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           TransactionRestartingException $e46) {
                                                                                      $commit45 =
                                                                                        false;
                                                                                      fabric.
                                                                                        common.
                                                                                        TransactionID $currentTid47 =
                                                                                        $tm48.
                                                                                        getCurrentTid(
                                                                                          );
                                                                                      if ($currentTid47 ==
                                                                                            null ||
                                                                                            $e46.tid.
                                                                                            isDescendantOf(
                                                                                              $currentTid47) &&
                                                                                            !$currentTid47.
                                                                                            equals(
                                                                                              $e46.
                                                                                                tid))
                                                                                          continue $label44;
                                                                                      throw $e46;
                                                                                  }
                                                                              } else {
                                                                                  fabric.worker.transaction.TransactionManager.
                                                                                    getInstance(
                                                                                      ).
                                                                                    abortTransaction(
                                                                                      );
                                                                              }
                                                                              if (!$commit45) {
                                                                                  
                                                                              } }
                } } }
        
        public Hello Hello$() { ((Hello._Impl) this.fetch()).jif$init();
                                { this.fabric$lang$Object$(); }
                                return (Hello) this.$getProxy(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.Hello$(); }
        
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
                                                                                   o)) instanceof Hello;
        }
        
        public static Hello jif$cast$Hello(final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                               idEquals(
                                                                                 o,
                                                                                 null))
                                                                             return null;
                                                                         if (Hello._Impl.
                                                                               jif$Instanceof(
                                                                                 o))
                                                                             return (Hello)
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
                                                  return (Hello) this.$getProxy(
                                                                        ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new Hello._Proxy(
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
        
        final class _Proxy extends fabric.lang.Object._Proxy implements Hello._Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((Hello._Static._Impl)
                                                                  fetch()).get$worker$(
                                                                             ); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((Hello.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((Hello._Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((Hello.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(Hello._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final Hello._Static $instance;
            
            static { Hello._Static._Impl impl = (Hello._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                  $makeStaticInstance(Hello._Static.
                                                                        _Impl.class);
                     $instance = (Hello._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements Hello._Static {
            
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new Hello.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm54 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff55 = 1;
                                       $label50: for (boolean $commit51 = false;
                                                      !$commit51; ) { if ($backoff55 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff55);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e52) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff55 <
                                                                            5000)
                                                                          $backoff55 *=
                                                                            2;
                                                                      $commit51 =
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
                                                                               RetryException $e52) {
                                                                          $commit51 =
                                                                            false;
                                                                          continue $label50;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e52) {
                                                                          $commit51 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid53 =
                                                                            $tm54.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e52.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid53))
                                                                              continue $label50;
                                                                          if ($currentTid53.
                                                                                parent !=
                                                                                null)
                                                                              throw $e52;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e52) {
                                                                          $commit51 =
                                                                            false;
                                                                          if ($tm54.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label50;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e52);
                                                                      }
                                                                      finally { if ($commit51) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e52) {
                                                                                        $commit51 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e52) {
                                                                                        $commit51 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid53 =
                                                                                          $tm54.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid53 ==
                                                                                              null ||
                                                                                              $e52.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid53) &&
                                                                                              !$currentTid53.
                                                                                              equals(
                                                                                                $e52.
                                                                                                  tid))
                                                                                            continue $label50;
                                                                                        throw $e52;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit51) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -69, 103, 78, -117, 101,
    35, 73, 27, -118, -72, 113, 7, 68, -36, -80, 84, -9, 47, 123, -81, -123, 22,
    -13, -112, 36, -98, 100, 32, 32, 63, -39, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1443794726000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16a8zj2HWYZnZ2Z9e73l2vH7E3XvuLPTVmrPVQEklR8tRx" +
                                                                "JVIUSZEUKVESScPZ8inx/RZJOVukKRwbduEaydpxgsQoGrdJ060dFHVSIDWa" +
                                                                "omjq1EbQGkXRAG1tGC2awDXQIGjjH21TkvoeM9/MjpHCASxAl9S955x77nnd" +
                                                                "o3vuq99tPZrErXeZimq5t9MyNJLbuKKSNKfEiaGjrpIkQtX7kvbkNfKzf/ir" +
                                                                "+juutq7Srac0xQ98S1Pcl/wkbT1N28peAXwjBVYL8s6HWk9oNSKhJLu0dfVD" +
                                                                "4yJunYSBW27dID2d5D76n2kDr/z8Tzz7jx5pPSO3nrH8ZaqkloYGfmoUqdx6" +
                                                                "yjM81YiTka4butx6g28Y+tKILcW1DhVg4Mut5xJr6ytpFhvJwkgCd18DPpdk" +
                                                                "oRE3c5511uwHFdtxpqVBXLH/7JH9LLVcgLaS9A7desy0DFdPotZfa12jW4+a" +
                                                                "rrKtAN9Cn60CaCgCeN1fgb/OqtiMTUUzzlCuOZavp613XsY4X/GNWQVQoV73" +
                                                                "jHQXnE91zVeqjtZzR5Zcxd8CyzS2/G0F+miQVbOkredfk2gF9HioaI6yNV5K" +
                                                                "W2+9DMcdhyqoJxqx1Chp682XwRpKlc6ev6Szu7T1XfYvf+ojPuFfbV2peNYN" +
                                                                "za35f7xCesclpIVhGrHha8YR8an30p9V3vKVj19ttSrgN18CPsL81k/+8V95" +
                                                                "8R2/89UjzI8+AGau2oaWvqR9QX36374dvTV8pGbj8TBIrNoU7ll5o1XudORO" +
                                                                "EVbW/pZzivXg7bPB31n8rvRTv25852rrdWTrMS1wM6+yqjdogRdarhFPDd+I" +
                                                                "ldTQydYThq+jzTjZul6905ZvHHvnppkYKdm65jZdjwXN70pEZkWiFtH16t3y" +
                                                                "zeDsPVTSXfNehK3Tz5PV982t1tVfOX1+Lm2tgF3gGYCV7AzTBLA4CNWgALBA" +
                                                                "yzzDTysDCGLfcF1ACV1ga6VA5c6xpQFGoXihW2m08hLNUAMVSGINICrI4HYF" +
                                                                "Ev5FES7qFT2bX7lSCfudWqAbqpJUmju1ojHnVo5CBK5uxC9p7qe+Qrbe+JVf" +
                                                                "aCzpidr6k8qCG1ldqbT/9stx427cV7Lx5I+/+NLXjlZY456KMm092jBTzf9U" +
                                                                "7Ui3q9B0uwpNr14pbqOfJ/9BYy+PJY1jnaM8UXH+fjeoglrRunKlYf5NDXJj" +
                                                                "JZWOnSp2VOHhqVvLD1N/9ePveqQyzzC/VmmpBr1x2VkuQgxZvSmVB7ykPfOx" +
                                                                "P/xfX/rsy8GF26StG/d58/2YtTe+67Ik4kAz9CraXZB/74ny5Ze+8vKNq3Uk" +
                                                                "eaIKcqlSmWEVMd5xeY57vPLOWYSrRfEo3XrSDGJPceuhs7D0unQXB/lFT6Ph" +
                                                                "p5v3N/xZ9blSff9v/a0NuO6on1UYQ0+d5+Tce9LWt7/9iS98+2d+9v33j53c" +
                                                                "/L5DeRA7RnwjrDSnWaHi/nmphPXAD9LolepT2/z7e+CLIPI+qHOr+oRHD6gt" +
                                                                "6JLWmh3jA8vwl//D7/8R2OylZ5vLM3ftQksjvXNXQKuJPdOErjdcGKQQG0YF" +
                                                                "958+x/3cZ777sQ811lhBvPtBE96o21ocSiWGIP7oV6M/+OZ//sK/u3phwWnr" +
                                                                "sTBTq4U1nL+7IvSei6mqWOdW8bbiJLmx8r1At0xLUV2j9ob//cxf6n75v3/q" +
                                                                "2aNJu1XP0UDi1ovfn8BF/9vGrZ/62k/86TsaMle0eq+9EMcF2DGAv/GC8iiO" +
                                                                "lbLmo/jr33jhF/6V8suVa1fhN7EOxjGiNstrNasCG3u93bS9S2Nw3byraMbe" +
                                                                "3vRfS+7fzPA6K7jwNxl49ZeeR3/8O8fYde5vNY0fe0DsWit3hYLer3v/8+q7" +
                                                                "HvuXV1vX5dazTUKi+OlacbNaq3KVUiToaSfdev094/emB8e98M55PHn7ZV+/" +
                                                                "a9rLnn4RM6v3Grp+f93RuRs7KK60wvrlToPxnqa9VTcvNjK6mtZRtE7Uqohr" +
                                                                "Wn4VOhu0tHX91FEbjDenrTcdnej2sfv2pnnUY88fPaVukdMpK2N8tHO7d7tX" +
                                                                "/0YfPPMj9ev76ubH6+aDZ/M+b7vajTPHX1cJY2U0N45zn7HybGM9texuH1Or" +
                                                                "ZuBtaeup2o1DV0nr+Fc8gK/KIp6+QKaDKiv75H/59Nf/1ru/WVkA1Xp0X2un" +
                                                                "UvxdM7BZnbb+zKufeeHJV771ycbhKm/jbn7zxUaudN1M0tYLNdvLIIs1g1aS" +
                                                                "lGk8xNDPOL/fErnY8qrYsD9Nq4yPv/KJP7v9qVeOXnjMPd99X/p3N84x/2xW" +
                                                                "+frj+qpZfuxhszQY+H/70su//Wsvf+yYmz13byY18TPvH/77//P125/71u89" +
                                                                "YE++5gbH7fayut/4LQJKyNHZh+4qKDxaFQs/Ew1knGu+DRFbYG3v+NUUhlHU" +
                                                                "IgbxFkN8FB+AVhAi9PxgqgamHeYYNj8UObnbrafjgHZnk/Eo4mNhglqWVUTb" +
                                                                "EBdWXhXeF9GEX5DdrTMTpjM029CWE7f34N6YF2OGxY0uJzBDQDSBQxsAsrbe" +
                                                                "I6w5vnOgqFNh0IG3ht0ZOw1FobeaD4Nx0lkvO/TOnohZyFmJAe1VJkvn/q5w" +
                                                                "6QXt4NQYn7m77iwrKRTqLlVgwluOLrcXxWZjWVpcKGudHqiLDYNv1/h6uVg4" +
                                                                "1HwmydTcktZoVIzFaOyZ3e6IQAbJmlwxrLAYh5i+CZxoNpvhE6lHOdyBipYE" +
                                                                "NcQOSqQJ+xjjd4rJ9FcduaA3Gy2EOcIHy77LdnsWEfSXgaMuRZvwZqSyCLch" +
                                                                "T25UFu8uV4IxBy1P4HfTpZKAXXy1XQ51ZywIm0nRFkaBEJLJhJMoaxlQ1GrI" +
                                                                "dtnNfKkVjEKzfBL1FA1OSojKdmgRzmA/cyjZCq29KTmIsNZVeb1cYyaVYznb" +
                                                                "Xe/3k701CRIx6S9BeGCVOSTRUNtONjrnsvPhDHbE5cbFPE+aBJbCBhNzF1KK" +
                                                                "JHtetMuEiecqs2ih46tA0Hq7zBLIjk1KERqxIVPy3k4cH+bxSpmh01lsWjlG" +
                                                                "2lE+pbOZqltL2Z4l3iyMYhXaLV1qGfnIzERTNO9sYbyfTeMd5ICEmY7xcXfk" +
                                                                "d3V2u5YzR6KmToiPxBEfEkUQ0xKP8rinBT0mlaMtNszLhQsSszLABAKGrX6I" +
                                                                "CVhnQnaV/gxtw0WvYwWdHTrKJmEYdqzlcJtxUXuZSGuNrOTMezN+MJcO87Fr" +
                                                                "BpN4vmQd3BF6sBSnq6hKXzEfH69dmB6srJE0scZh4GXxFpZ5wd3CnDpe9BEc" +
                                                                "Eiien7q5Nh0cphzQtiF4lBAcnYl221EZT+51OmTkOW6x6c79LhCuMSRzCJVe" +
                                                                "iC7DohK4d+AcNhGwG1qRP+LVjjZVJy4s9sqeporxzoNFsJOXdriaGLMFLs7l" +
                                                                "BOWyIIwWIetESXuLppXrzkMS5RcZK6bmAop3nDUJXc6cC5HGKqmyGIVuF1f8" +
                                                                "ZLpernaWyy8W4E506UoLvT26kehsMAwLLo5ReljyEQ1uWJ2lQkdUu5MDi0Iy" +
                                                                "LSx1u4rTUao4cEZ2o9BmV6Ods0XHeqCSGn7gYdAqZY2HywmdoouQGwe7kUh6" +
                                                                "3aXXnk3pRI4lZaGgq3mH6MUWMYlX5spxRklgzGZB2LN4ScJ82uqrm7IYBakS" +
                                                                "MBY1WsGqquuKoCIJyDidQNMZXg48bGg6U2fAzrJDp6/rfiFqOwDko2V3PfWW" +
                                                                "fCIxMd6ebdc8NcJzakTnByfKEtANQQ6Yi2TA4K7DLkfVih18M0J2JbUY2wdw" +
                                                                "4RSDYpYPltQI7WCLjocf4nBYaSccI9pomC5XuISutHliEd10lYUsX+hjcRwP" +
                                                                "21LIgjwbq/sC3YbqSFixmzDYJPZM9sHuGtvEIyzs6kigzEaomGejEqdnNCsT" +
                                                                "C2+28Il0VzqjVQnKnUnJKxhrZehkAU7lzFwvkCGkzZlZV+qmTldw+BXUplb0" +
                                                                "NJihsI5vnBk/3ulAvGMMYgHPoSqY4cvRSCJ6q5LHpnjKEyOWABernoAySbgc" +
                                                                "GVN3fci35T6edTsJnnT0latuh4f8QCWGj3ILMplOpzxpZo6matl4fYCkSVmx" +
                                                                "UcYpR1noYdiRDH/RNTezTLAcpw9qYFdvtyHdjhTYxXLSHkubdCXhCbsZUfIE" +
                                                                "XvTKIYKrxaCdTrtAl1hZky4VpVsHnSX6hj4McLMgIFNKgPFITXI0c3a9aieZ" +
                                                                "kvNZ6E44hOr1YHzd7+5IYb9tQ0g4HatyMCoqQx6uPHs+oKSBrM2nKmOR6TRA" +
                                                                "FpgodkZUEo3K/igLejPDcatdyA2W2/HShrP9nO9Iw7aLq3gbXjP7RT5t+06+" +
                                                                "Hwy2+t40ylDONsDYzTmJHU8WGkKpEGJjc3YMaj3DCLI9usp4W29zMSCY+4kq" +
                                                                "jcajMTMdkmUZTsWeNMo5xJP64oBRBhoBOuAI9vPRrj2mN7P+Hs0nCT6bJo5o" +
                                                                "s06xQPzxdtXtq+Ke0bWY5AKXzCFsZS8nfEbK8X6KUAOxS4iZkpeH2ntjfDWL" +
                                                                "g7YrJPxUrDL0qDefTSMyU1QV4eM4lCa7rdgdRYKAkxVjna7sbamZHnVyEXeY" +
                                                                "qTknD4y8bPuwwOsKJG/4jbmBmOki747z3swmJHsU2vGwmyrQXLSTTIm82Kaz" +
                                                                "0tXFcpUmwzat+DuvtyfYZTzjYYBZ9ZhSkDOBJ1g7RmM9TIYhOANxrBB3PEwt" +
                                                                "nL4ApUsfgQLNnJv8FCnaU6aU9NF2SNFyshRxur3ocq7eA9Rt5E/nll+Ok6G2" +
                                                                "EbpRtsAxoE0VS6Tfk9DtXBhvIgZJ3cWCU7EuOR0oBkDZmIWEE3StUtOM3610" +
                                                                "chJTy3RelrkuSHQ/Gs2zebSGu+BmOGfbempgRY6shZ60g9RJ7jo47Kq2y2+h" +
                                                                "UvA2ICoOUA2wQn+WRpiTUtpqynFdgEf6bmxm4XoFYSMGCdMCBnG/W0ADC/LS" +
                                                                "jPW3OduGd6jW0Y3c1w6wLGM2A6PiUmnrRlj9rQKHe6UNEpAI8QsbD3XO8G1A" +
                                                                "jfZ6WdIoDO3UVSRGEMOa8jph3dJe9GIBO4ACQWDLAraZPEPRUMm3emn0GLiD" +
                                                                "iaO1gOWOQA2rXZ5dMUU/Fe0YgphZpHYRJF50d0lRphjH0NMZ1rFcpwMwvqEa" +
                                                                "7cSfglxHG+N7PbAVi9t7ke1uCD6TJoPu2KG4AF1GuJRvliRJwLSmsEiOmKOx" +
                                                                "mFo6tcnaZkS7bj5QTU6EFjEcziZ8xxMMCae77WLtzQcWla8Pmj6FiEmbGMYo" +
                                                                "pvBwsZ8AA2/PFJ4+mDKqgiJoOCSx/pIbkj0iMtXMxHQPVM2Yaiu9uLdX5lA0" +
                                                                "FdugxncoOYUhprsXI0Qbsn2aFNehpbgR2ROUpSdynrsF2r6JrJExG6yTjq22" +
                                                                "kTCiTa1KC/cxsOxoNJCgYSFNsD6JMUic2jAUlDvIDnvclFxbhKOKwnDhrjVu" +
                                                                "i1ODtc4HQ39hrXd2BJNBppCmMZgn1Nwg5T4QSWzXzDULWYX4dGDLqQRmc5nX" +
                                                                "+jIrRQc9lj1juOyre5OZOjayEvo6tMpAAZ90CsGIhj3EzJQs0hljWG0A9HQc" +
                                                                "jgibT7uqTKD7TLaG9lraSCRjc1Mpo9I+EnOzcpb0So1k11xHtvaCegD8tggS" +
                                                                "hgOa5YEarqllrjDbuFON61zMKTE2HALhOMVFNOmDI5xjcAHccGQ5PIxnW2bS" +
                                                                "QwEOBrgic8oo6neXuIzafT+cooAmQbRxIOOFRyo6MOYdd4uLtgyaO/KwWE+K" +
                                                                "YOWmYTx1Q5V2ezIrcuv8IBDUorOawcpqLKwPk/1iodCdPo1D7V2iQop80KY2" +
                                                                "Lu98kPDKjT9QnTaR26ytF0PEkb3DCthNeGiggKat+gPBnAxkeYJ6koTAcjmf" +
                                                                "IwyILOAiHyvDWYeB9DhbpEaCDToSNI4djPUmmLzZjgdJv4/yXqwDTBloGwJW" +
                                                                "aB0qhqlngmtH0pOFhdgD14X1ZQ8AMJpX5Bnpih5URh2RNYtleciKvaazm81+" +
                                                                "kC1ngIIgdioSjqkKbswbSb/bRkua1qPqr01P3VV5I79wqW42DxHwoJVtChrq" +
                                                                "wK4kDLtKl4TE8pMZ58wnKdZnZTOQp5ugHEw1iR/gM3kNQNOpSaz3h6FhcFy6" +
                                                                "hhF1YCOcMOCGI5nl+iuVVVdg1+4eim6vffDjeLRuyzI550lngGHVftY1NZlT" +
                                                                "iZxh2WrDlPnAYePOZu3IjjNcuQ7ZG0q5h1NySS4QN0432yhTJXFgIcTOR3Jx" +
                                                                "rSVE0dFHSjyW9dVBtErds1d62ScjULCHWo6NGY50QWpooyKeqWZqbPYaHHFx" +
                                                                "b7zEE1sDxsEAGsFCyfouB8VzNwZH2RYhoq2+dOZVin2Ymwt+JXKLjlOExKZK" +
                                                                "gETcRwFyIxSigvI6NeOUvlPyGnjYAxtzAkzQTukzm6wMczGb4UBOCy5o0wDu" +
                                                                "hvF8gyPQwg353Y7ttCfwsE+iUjqzLOyQJEH1f6e9GW+rLcFQ4d682IbEcEuY" +
                                                                "WrECp6UoHgb5sOjbIz3x/Sk9HIMGaYP7Kv+cbUftziYyeZ0B+6miRb2heojm" +
                                                                "a2IocQchyYYsV0LgNgsSJoLlboSvVgUADCbV3gGbiR7NZ6wNucIuHyRzDw8P" +
                                                                "4FqWjR4WgONpmCF0xioLVAUPczyIwVwO2jbog5pJ9rFklOelOGPMri3qYDeH" +
                                                                "eatLQEvF6MpoLnUL3KeLQdlJRqCV5YSkh6jjwH3EhRFuClXxVxJLJZylw94W" +
                                                                "6yRLBcH9WO1l3qSvHVieN2Rg24cPvsnvc59C4Hjv7EH2sGaIQXcu7ttJAXob" +
                                                                "HNQJYVfJgvNAgmA1ophgZNiBexgj2wEVkggEDDIAivAZkIzaZDISOuCA7ggh" +
                                                                "dIASWRo7XmW6G7avrnfDNqwNe8IBx71R0acid5blOxifL3cAIviSBphcTwPi" +
                                                                "VKLW2UjMA4nZboDcWAPSnh3ONxyqt/GsAMO+1+fpHsRbnKuWas9OFmJbcOek" +
                                                                "HG7VcDLuLJzJ1g7w9h7q0QhlK6ZkzjfwVqB3gwL3UC9vS0tWHSX7aDTbraA8" +
                                                                "647YNmWiWR5HG68jDBAIjgYbbANoi3CIBLK0Dufh/BAcgHg5AIYStTPIPHOC" +
                                                                "gZfHbbsPxrshHBzGPGh7K7fkXJrcW0w77YMTk2KgpUl6U5Yb49PJKrcdfles" +
                                                                "mDbbc7l2RIieDxGHAyJ4gL7sE3tubneZzRjZWWta3PRJAIu4GdTfYehaWQ7t" +
                                                                "cp8jm36mHhIPJfQ9HFGmxMwtQ2DUoTJb5wBWMs54GpWRmwf7Ab8hAGZODP1w" +
                                                                "sELgw6Cf6oi3M4Ax66yine1PunAXLVZiZ81nIckzAo2SMW137f6eVj2eX1mk" +
                                                                "rAGbRDJmfntBLzjfLoieauEH1fcOpilyKBY5O1PiPHRP0NXu29+vrT6ApNoB" +
                                                                "A4bRyhoaMdWpwLiOo3q45gk7OPUoyGXF+XBPdwhhLNEOoimZEQ/mPaavJ+ue" +
                                                                "lBkraV9oOkrB2WjVGyO+s9ch6WDsyLYujOn1Yc+tJX07hLYFhQEZOly7Dsz6" +
                                                                "K7mXp3smi6lDDiGrtXaQ5r3VxLUIO9ljxFj0t26emE4Ed5m+5hbdte8PcYrL" +
                                                                "kpg5oJSWgSW9lntatQXkAutWG8dynblzyh7CKAljkIHPkGyeW2KbbU8yYIag" +
                                                                "snHgBusY85frjUFQOZnuxUkfE/FiAOZu1scNuOTENn8IBjwDlmZ3ibY9uhNR" +
                                                                "kT/MEV0CO7g2ChaezcQ9dZD3FEPDksrwepXB+vCAwIDNLOfJTSwxeNnDnYAV" +
                                                                "Y1elIL0zGk+NA1ztyoSp7IVte41VOZQ1xW0TQuZO2d/IvSrDIuT+pOPBrEGT" +
                                                                "AE0v+hrV23Ly/tCnVmx74I1Gow/UR33i6Vnnm5oj2vM6+PGIsx4jmrPB4sEH" +
                                                                "vq3TwsTFAX2rPrF84bVq1M1p5Rd++pXP6/O/2716ir5KW0+kQfg+19gb7l2k" +
                                                                "rtdnn/fdgWCayvzFkf23vvPCEHX+6/Z49vnOSzNfhv77zKu/N32P9rNXW4+c" +
                                                                "n83fdx3gXqQ7957Ivy420iz2hXvO5U+ORbeK6WdrGbyt+v5Iq3X1n50+f7Me" +
                                                                "fWNztvymu46w7z+5vzg/L84pXq0pPnNK6cunz1+7i+KlAsqV8+rnC5eEV2m0" +
                                                                "qcwcT6d//1e/97av3Pij7x0Fd/lawV2A/+PVb37nG69/4YtNAfNaXTVuFn75" +
                                                                "Psb91y3uuUXRrPup81Uh9ao+WH3fUq3ma6fPf5G25B9kGRALMtU1OMvfnlbW" +
                                                                "/wKpN+sj7tPC+Zl6UFdtLv2sX9LvZwrnVZTHXMPfprsGkj+tDtQPIW09Usm+" +
                                                                "fvUfTOxKQ+xIp27yuml++sU5x1eP854V");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("ZN54US5B3cA36spgUxo6Lx41AFZw+/zGzxlE8UApqMdlN7PWzc2HFP4++pCx" +
                                                                  "j9XN30hbj2o1Ww+oHx1rb0dOGgznNTzk/pVOCs0Iz4q7zzfIf7NujLR1zVMs" +
                                                                  "/0Gyv7YPrKONq+fW/eSDroz8yn1R4PuJ4bMPGftc3fxcZRXNnYobF1Hlgovr" +
                                                                  "NfB76qWeXmK5crwA8PXXLs83Bv4DLcUfvWRhKPo4UJuaPAi+CILvg3q33n8S" +
                                                                  "ZUpiRVmQGjePhe6TWpgntmXesPx94BiYYd518+LmrZOPpDsruX1c881bd16+" +
                                                                  "FYZ3R9S6ee+5AJrPY5cFUI/+Uhg+RLZ/+yFjf6dufjFtvfW1mKzHP31JE4/X" +
                                                                  "6G+4XxP/+IdSE7G1rzi5WxVWWov+5EMfXp5clvh9sSZtXT+lUNyriOsPUsTf" +
                                                                  "e6giXn3I2Bfr5gtp6/EzFh8k+Kdr8BfuE/yVz/0wCv5YtD859QQ1CFxD8RsN" +
                                                                  "nOUigXnzQ01F/+Q44UcUT325CV3Ht2Poa95Pgg/jlc4s8+RmcPKBD5z4meve" +
                                                                  "OjkmLxW2mxh3Tqv/TeRLDC2LrbS8TSuq4a7qwrGiVVt5gu4Mzbn54OnOkI6/" +
                                                                  "zjGPP/2glnEVoit53bz14knQdN+6c8pCxZV1vqqTxqHvd+eHJUhNFH54ZH/u" +
                                                                  "7hUeZfPgawUNsd98iLH907r5jbT19L3aeNB+cP1UcXdb4tnlq7eeWeBZRz16" +
                                                                  "4wey5vrnbzUAv/uQdXy1bv756To0JUlvNJJ/zd3juftd5yd/GF3n1GeOOj5p" +
                                                                  "wkFjjkkTt5o940YW6hWzTffJB17Dgf4cFn3n5JTu0U+4oGKg/EEQvq0Fvnkk" +
                                                                  "d/PcXeqp/r93u68/NMj+m4eMfaNu/nXaevIukR4t7ew2ZxheWN7zWVzfLH/1" +
                                                                  "T37ke489Lnzr9GJN6+S3t+wnjXeTP/qJfxJdx/7jbwh/CnzkSx99y598+sbn" +
                                                                  "9ZOTD/7B+/8fC9bxSPEuAAA=");
}
