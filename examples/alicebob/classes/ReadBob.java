import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;

interface ReadBob extends fabric.lang.Object {
    
    public ReadBob ReadBob$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1446061858000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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" +
       "CkqlaYYagJOmOg+U5JR3uqAYMrgRR5rFbHB2XtOxuGgTdGYNHj95jBZHjEas" +
       "GpPihbds0NXhm6ZGLXWVc2+30l9BDtyDo301bPvlobBNc4SMaWJMtqFtFmh2" +
       "vF3e2+I5Jb6l4MUqdEgySpoUaw7fGTn+5omXeaExnAnpEHpI7L0lxZ0Vtz9W" +
       "fs/mWZxiiJ+iYPb0CbXrkxfc/1cW3uhng4PmwAxFdW6vpPjpruO0acpXH3EE" +
       "UlcpwXLaCWcoqa07rMR1xgV10ABS9SvwLov91F/cefDUicaZLCQ8FsZ6qaBk" +
       "t1zIAc8adpvw5zhHnqrFquaIlBSX9fnb2IGPXfVzr5oCBJ7Z7fvuKPl43Fd3" +
       "sm07fjY84GcOwbf6GrYzubwMcnwG8grptVPfirdeOdpW/1G4H4RRzJjU+/DJ" +
       "fv3XWpbFBedbq+Ll8lBjXwFvZa6xY1WJJx8vaXrWY2xmQVDBKjaR2xPbea4B" +
       "rgDGI8L0WaOappr1aPWS4ceXV5958Q92hC1wtDLav8EApXebxWMe7d/+5vUL" +
       "bR5NfKtXerbazLuma3kWncvYU52B0BOomuoFoxXy5Ovya81b3xozmCvbk0et" +
       "8Ufqbt667Y8PX8ALqzJEgxkzeWXMV6zXmCwiF0XziRF4XMqntnIDaY5x/I/8" +
       "GrPxGR9Gs3YsNlUeUJ/gnwnv6Od6RWKvd7t/tmVXeuGeiXyr5f7XjtlKLnvv" +
       "q2eeq9r+zjMhxWupqWrjZdpGZc+aWGL6jwXms7dHt6668K66yoFPrmz/4apL" +
       "C//DCskhgd0Hhdk3f/8zc0eKm9k5ilVDFrwR+4mqvXoAlOKrokaxpxuzwkgn" +
       "V/RAO/Tnvw7drWuxN1fwii/UpFG8jZv4BoCHC3mHaxS52tw68mv0Sw/XQNqP" +
       "cnaGL90yRdE0f6Pds3f/geqyfXtYrJcyNIA8aFqq7YQU9jPfYk9HmBEozGRL" +
       "mNHWdYB3i7DueUGomaW3WJl+b9enjp3qN+coy/RRUcKioaCKT9Nz6T+naVT3" +
       "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" +
       "EBMKywqnC9vB2n8A/UEUGzglAAA=");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements ReadBob
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            ReadBob._Impl.main(arg1);
        }
        
        public ReadBob ReadBob$() { return ((ReadBob) fetch()).ReadBob$(); }
        
        public void jif$invokeDefConstructor() {
            ((ReadBob) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return ReadBob._Impl.jif$Instanceof(arg1);
        }
        
        public static ReadBob jif$cast$ReadBob(fabric.lang.Object arg1) {
            return ReadBob._Impl.jif$cast$ReadBob(arg1);
        }
        
        public _Proxy(ReadBob._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements ReadBob
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm72 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff73 = 1;
                $label68: for (boolean $commit69 = false; !$commit69; ) { if ($backoff73 >
                                                                                32) {
                                                                              while (true) {
                                                                                  try {
                                                                                      java.lang.Thread.
                                                                                        sleep(
                                                                                          $backoff73);
                                                                                      break;
                                                                                  }
                                                                                  catch (java.
                                                                                           lang.
                                                                                           InterruptedException $e70) {
                                                                                      
                                                                                  }
                                                                              } }
                                                                          if ($backoff73 <
                                                                                5000)
                                                                              $backoff73 *=
                                                                                2;
                                                                          $commit69 =
                                                                            true;
                                                                          fabric.worker.transaction.TransactionManager.
                                                                            getInstance(
                                                                              ).
                                                                            startTransaction(
                                                                              );
                                                                          try { final fabric.worker.Store store =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getStore(
                                                                                    "bobnode");
                                                                                final fabric.lang.security.Principal storeP =
                                                                                  store.
                                                                                  getPrincipal(
                                                                                    );
                                                                                final fabric.worker.Store carolstore =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getStore(
                                                                                    "carolnode");
                                                                                final fabric.lang.security.Principal carol =
                                                                                  carolstore.
                                                                                  getPrincipal(
                                                                                    );
                                                                                final fabric.lang.security.Principal top =
                                                                                  fabric.lang.security.PrincipalUtil._Impl.
                                                                                  topPrincipal(
                                                                                    );
                                                                                final fabric.runtime.Runtime runtime =
                                                                                  fabric.runtime.Runtime._Impl.
                                                                                  getRuntime(
                                                                                    top);
                                                                                if (fabric.lang.security.PrincipalUtil._Impl.
                                                                                      equivalentTo(
                                                                                        storeP,
                                                                                        store.
                                                                                          getPrincipal(
                                                                                            )) &&
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                      equivalentTo(
                                                                                        p,
                                                                                        storeP) &&
                                                                                      fabric.lang.security.PrincipalUtil._Impl.
                                                                                      equivalentTo(
                                                                                        runtime.
                                                                                          fetch(
                                                                                            ).
                                                                                          $getStore(
                                                                                            ).
                                                                                          getPrincipal(
                                                                                            ),
                                                                                        storeP)) {
                                                                                    final fabric.util.Map root =
                                                                                      store.
                                                                                      getRoot(
                                                                                        );
                                                                                    final Message myMessage =
                                                                                      Message._Impl.
                                                                                      jif$cast$Message(
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
                                                                                                storeP).
                                                                                              meet(
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
                                                                                                    carol)),
                                                                                            fabric.lang.security.LabelUtil._Impl.
                                                                                              topInteg(
                                                                                                )),
                                                                                        root.
                                                                                          get(
                                                                                            fabric.lang.WrappedJavaInlineable.
                                                                                              $wrap(
                                                                                                "myMessage")));
                                                                                    if (!fabric.lang.Object._Proxy.
                                                                                          idEquals(
                                                                                            myMessage,
                                                                                            null)) {
                                                                                        final java.lang.String messageText =
                                                                                          myMessage.
                                                                                          getMessage(
                                                                                            );
                                                                                        runtime.
                                                                                          out(
                                                                                            ).
                                                                                          println(
                                                                                            messageText);
                                                                                    }
                                                                                    else {
                                                                                        throw new java.lang.Error(
                                                                                          "It\'s Null! It\'s Null! Everything is Null!");
                                                                                    }
                                                                                }
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   RetryException $e70) {
                                                                              $commit69 =
                                                                                false;
                                                                              continue $label68;
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   TransactionRestartingException $e70) {
                                                                              $commit69 =
                                                                                false;
                                                                              fabric.
                                                                                common.
                                                                                TransactionID $currentTid71 =
                                                                                $tm72.
                                                                                getCurrentTid(
                                                                                  );
                                                                              if ($e70.tid.
                                                                                    isDescendantOf(
                                                                                      $currentTid71))
                                                                                  continue $label68;
                                                                              if ($currentTid71.
                                                                                    parent !=
                                                                                    null)
                                                                                  throw $e70;
                                                                              throw new InternalError(
                                                                                ("Something is broken with transaction management. Got a signa" +
                                                                                 "l to restart a different transaction than the one being mana" +
                                                                                 "ged."));
                                                                          }
                                                                          catch (final Throwable $e70) {
                                                                              $commit69 =
                                                                                false;
                                                                              if ($tm72.
                                                                                    checkForStaleObjects(
                                                                                      ))
                                                                                  continue $label68;
                                                                              throw new fabric.
                                                                                worker.
                                                                                AbortException(
                                                                                $e70);
                                                                          }
                                                                          finally {
                                                                              if ($commit69) {
                                                                                  try {
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                        getInstance(
                                                                                          ).
                                                                                        commitTransaction(
                                                                                          );
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           AbortException $e70) {
                                                                                      $commit69 =
                                                                                        false;
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           TransactionRestartingException $e70) {
                                                                                      $commit69 =
                                                                                        false;
                                                                                      fabric.
                                                                                        common.
                                                                                        TransactionID $currentTid71 =
                                                                                        $tm72.
                                                                                        getCurrentTid(
                                                                                          );
                                                                                      if ($currentTid71 ==
                                                                                            null ||
                                                                                            $e70.tid.
                                                                                            isDescendantOf(
                                                                                              $currentTid71) &&
                                                                                            !$currentTid71.
                                                                                            equals(
                                                                                              $e70.
                                                                                                tid))
                                                                                          continue $label68;
                                                                                      throw $e70;
                                                                                  }
                                                                              } else {
                                                                                  fabric.worker.transaction.TransactionManager.
                                                                                    getInstance(
                                                                                      ).
                                                                                    abortTransaction(
                                                                                      );
                                                                              }
                                                                              if (!$commit69) {
                                                                                  
                                                                              } }
                } } }
        
        public ReadBob ReadBob$() { ((ReadBob._Impl) this.fetch()).jif$init();
                                    { this.fabric$lang$Object$(); }
                                    return (ReadBob) this.$getProxy(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.ReadBob$(); }
        
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
                                                                                   o)) instanceof ReadBob;
        }
        
        public static ReadBob jif$cast$ReadBob(final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                   idEquals(
                                                                                     o,
                                                                                     null))
                                                                                 return null;
                                                                             if (ReadBob._Impl.
                                                                                   jif$Instanceof(
                                                                                     o))
                                                                                 return (ReadBob)
                                                                                          fabric.lang.Object._Proxy.
                                                                                          $getProxy(
                                                                                            o);
                                                                             throw new java.lang.ClassCastException(
                                                                               );
        }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (ReadBob) this.$getProxy(
                                                                          ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new ReadBob._Proxy(
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
        
        final class _Proxy extends fabric.lang.Object._Proxy implements ReadBob.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((ReadBob._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((ReadBob.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((ReadBob._Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((ReadBob.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(ReadBob._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final ReadBob._Static $instance;
            
            static { ReadBob._Static._Impl impl = (ReadBob._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                    $makeStaticInstance(ReadBob.
                                                                          _Static.
                                                                          _Impl.class);
                     $instance = (ReadBob._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements ReadBob._Static {
            
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new ReadBob.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm78 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff79 = 1;
                                       $label74: for (boolean $commit75 = false;
                                                      !$commit75; ) { if ($backoff79 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff79);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e76) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff79 <
                                                                            5000)
                                                                          $backoff79 *=
                                                                            2;
                                                                      $commit75 =
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
                                                                               RetryException $e76) {
                                                                          $commit75 =
                                                                            false;
                                                                          continue $label74;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e76) {
                                                                          $commit75 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid77 =
                                                                            $tm78.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e76.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid77))
                                                                              continue $label74;
                                                                          if ($currentTid77.
                                                                                parent !=
                                                                                null)
                                                                              throw $e76;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e76) {
                                                                          $commit75 =
                                                                            false;
                                                                          if ($tm78.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label74;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e76);
                                                                      }
                                                                      finally { if ($commit75) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e76) {
                                                                                        $commit75 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e76) {
                                                                                        $commit75 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid77 =
                                                                                          $tm78.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid77 ==
                                                                                              null ||
                                                                                              $e76.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid77) &&
                                                                                              !$currentTid77.
                                                                                              equals(
                                                                                                $e76.
                                                                                                  tid))
                                                                                            continue $label74;
                                                                                        throw $e76;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit75) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -26, -107, 30, 85, -2, 20,
    62, -37, 40, 40, -17, -121, 72, 69, -121, -100, -64, 68, -109, -124, 127, -109,
    51, 112, 116, -31, 32, 95, -113, 40, -70, -65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1446061858000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16eewk2XlQz+zsZa9317u2sRfv+pf1YM2411PdXX2VBzt0" +
                                                                "V3VVV3V113215Sx1d3XXfXeZhQRI1kqQY8Vr4yAnQmhNICxOhBQiARZGiOBg" +
                                                                "KwoWQiAOG4sjkbEgQoD/gISq7t8x85vZsYIcyS31q1fvfd/3vvdd79X73hvf" +
                                                                "bT2cxK0XLVVz3FvpPjSTW6iq4SStxolpwK6aJHzd+rL+1mv4537nl4wXrrau" +
                                                                "kq0ndNUPfEdX3Zf9JG09SW7VXAV8MwUEFr/9sdbjeoM4V5NN2rr6sWkZt07C" +
                                                                "wN3bbpCeDnIP/c+2gdf+8o89/Xceaj21bj3l+Fyqpo4OB35qlum69YRnepoZ" +
                                                                "JxPDMI116+2+aRqcGTuq61Q1YOCvW88kju2raRabCWsmgZs3gM8kWWjGhzHP" +
                                                                "Ghv2g5rtONPTIK7Zf/rIfpY6LkA6SXqbbD1iOaZrJFHrz7auka2HLVe1a8B3" +
                                                                "kWezAA4UAbRpr8Hf4tRsxpaqm2co13aOb6St913GOJ/x9UUNUKM+6pnpJjgf" +
                                                                "6pqv1g2tZ44suapvA1waO75dgz4cZPUoaeu5NyVaAz0WqvpOtc2X09a7L8PR" +
                                                                "x64a6vGDWBqUtPXOy2AHSrXOnrukszu09d3Vn/zUJ/y5f7V1pebZMHW34f+x" +
                                                                "GumFS0isaZmx6evmEfGJD5KfU9/15U9ebbVq4HdeAj7C/Pqf+b0/9dILX/nq" +
                                                                "EeaP3weG0ramnr6sv649+c/fC9+EHmrYeCwMEqcxhbtmftAqfdpzuwxra3/X" +
                                                                "OcWm89ZZ51fY31B+/JfN71xtvQVvPaIHbubVVvV2PfBCxzVjzPTNWE1NA289" +
                                                                "bvoGfOjHW4/WddLxzWMrZVmJmeKta+6h6ZHg8F6LyKpJNCJ6tK47vhWc1UM1" +
                                                                "3RzqZdg6/b2t/r+z1br6xdPn59OWBGwCzwScZGNaFoDEQagFJYAEeuaZflob" +
                                                                "QBD7pusCaugCtpMCtTvHjg6YpeqFbq3R2kt0Uws0IIl1gDVVYxpot2qg8I+O" +
                                                                "dNnM6uniypVa4O/TA8PU1KTW3qklTWm3dpZ54Bpm/LLufurLeOvZL//8wZoe" +
                                                                "bzwgqa34IK8rtQW893LsuBP3tWw6+70vvfy1oyU2uKfiTFuPnrJTc/BE4063" +
                                                                "6gB1qw5Qb1wpb8G/iP+tg9U8khzc6xzp8Zr3D7tBHdrK1pUrB/bfcUA+2Eqt" +
                                                                "6V0dQeog8cRN7uPEn/7kiw/VRhoW12pdNaDXL7vMRaDB65pa+8HL+lOv/s7/" +
                                                                "+pXPvRJcOE/aun6PT9+L2fjki5dlEQe6adQx74L8B0/UX3v5y69cv9rEk8fr" +
                                                                "UJeqtTHWceOFy2Pc5Zu3z+JcI4qHydZbrSD2VLfpOgtOb0k3cVBctBx0/OSh" +
                                                                "/vY/qH9X6v/vN//GjJuG5lkHM/jUhU7OfShtffvbP/36t3/q5z58b9/Jje/b" +
                                                                "VQTxzoyvh7XmdCdU3T8slbDp+EEavlr/Gqv/cA98CRx9qN+5Wf/Cow80FnRJ" +
                                                                "a4d14yNc+Av/6rd+FzysqGdLzFN3rEWcmd6+I6w1xJ46BLC3XxgkH5tmDffv" +
                                                                "Pk9/5rPfffVjB2usId5/vwGvN2UjDrUWQxD/5Fejf/3Nf//6v7h6YcFp65Ew" +
                                                                "0+qJHTh/f03oAxdD1RHPraNuzUlyXfC9wHAsR9Vcs/GG//PUn+j+2n/91NNH" +
                                                                "k3brlqOBxK2Xvj+Bi/b3TFs//rUf+98vHMhc0ZsV90IcF2DHMP7sBeVJHKv7" +
                                                                "ho/yJ77x/M//U/UXateug3DiVOYxrh6m1zrMCjzY661D2bvUN2iKF8tD33sP" +
                                                                "7deSe5c0tNkbXPjbGnjjC8/BH/3OMXqd+1tD40fuE71E9Y5Q0Ptl739effGR" +
                                                                "f3K19ei69fRhW6L6qai6WaPVdb2xSODTRrL1trv6794kHFfE2+fx5L2Xff2O" +
                                                                "YS97+kXUrOsNdFN/y9G5D3ZQXmmFTeX2AeMDh/JmU7x0kNHVtImizXYtrQk7" +
                                                                "fh06D2h1BD511APGO9PWO45OdOvYfEs6PJq+546e0pSj0yFrY3y4c6t3q9e8" +
                                                                "w/cf+aGm+qGm+GhT/OjZuM9tXf36meOL9baxNprrx7HPWHn6YD2N7G4dN1iH" +
                                                                "jvekrScaNw5dNW3iX3kfvmqLePICmQzqvdnP/MdPf/1n3//N2gKI1sN5o51a" +
                                                                "8XeMsMqazetPvfHZ59/62rd+5uBwtbfRv9r6t7/dUCWbYpa2nm/Y5oIs1k1S" +
                                                                "TdLlwUNM44zzey2Rjh2vjg356ebK/ORrP/0Htz712tELjzvQ99+zCbwT57gL" +
                                                                "Pczybcf51aP8yINGOWCg/+VXXvn7f+OVV487tGfu3k/N/Mz72//y/3791ue/" +
                                                                "9Zv3WZWvucFxub2s7mc/Pe8n+OTsR3ZVeDARStbPZKU/QoKRXFgY0y3w+Tiu" +
                                                                "XFy1dTgtDB3XSX9pIPRa7BqxBmretLcOnTXNKvhC6Symy65oB1NmpuJihE8c" +
                                                                "Fd+rwg7dBtFE2SrA3mZn6W4flSjek8Yihg/o9mgw6iFeyHtRvPbDOAdyAGjP" +
                                                                "IQDgx0BKd4Qhto3CamKHMruIq5WgrtTYS3ADY3gjyFxFQrBlacAWKox6Fhh2" +
                                                                "6+Vl7ywoj8ExCV6gJKKirMuJZboLMGIg4l6VbLdJCDt6XEoiT4zTaeg5/YWz" +
                                                                "CPFgP/PR5cwDy/liwyt23MWLXJWiyZgJ0T1KILMOq7KVFIWLncNtGM/IdF4f" +
                                                                "KruyNNikivJdByt2sOzTsI9sFLYr+st1PorjzOJ70YgDgyHX92SO28LSApfY" +
                                                                "wA6ZBaWtxC4n8GsK3CjriEGmtDTaLyKc0DMOD8MYZgCC7fAhrs9ohXC4gCA6" +
                                                                "4KqzkjIuYHYr1ItYkV9R495an4HMRm33BjtzR1BTqZLDtAbuyr0h0+kKnX04" +
                                                                "myJbY61MQqhQK0zLNQmY2GkyZ3NLz7P9PAZyhgzAXXvIJYkt8LPdjF8nNl8t" +
                                                                "wz1EJE4WY/GcU8OaLt+XOQheRFwfT2IFI6KUWO7tXsiy86U/G64ILwr0TUqR" +
                                                                "iLHHAgdXochQkYW3C8sgiiPINxdLlpI6gMGUeJGkyoJpx27ch7K1TbcdZzl0" +
                                                                "+OHGK6Jdl0tnBNeJHHKCS1U5jkllsmBcjwl7y3Qd2Vuo2PMuOF/sA5KfoQNv" +
                                                                "GJL8pIsPomrHoD5oUGEqBvAM1XGNZ8KZP5yaenfsYIqoB4LNMV7EbChlUE24" +
                                                                "TEdjilvtJjLfg/QIEiKBnSHeaiq6LjleOhNl7hBhsMlie7BmeDfoEtqUHs5X" +
                                                                "gUIwjCcWjDeudjTQ5njPXGI0iAlVuWOWnrFRtzuxUrk+BclpqWDdYA9kNkZ0" +
                                                                "Oi6LMBk9MpKhnkMzNdIHsT1BoTne70uYQNPpvAOhsgx2OoBDkBSisopIiTqa" +
                                                                "74OwFtBK8Ly2u4+EaRcJcUxgMkRK8hkm2CQIC94mnxsbwlsxSX8m7NV9LGEy" +
                                                                "HSzwZTKbiIaCjjjdC6C443kLMKGjMTscjuN6+ccdc1jGabrSd7Wr6iCXYVyn" +
                                                                "sys8OTJjaRevynYqLNemO1vaE0YL4TW/y9T5Yszy5HZOMbYRSAkRrFCcG8w8" +
                                                                "l+B1YSn3ayvkVmYQcZES0t1+TFP9CBrG+AZl2tMpyxuUoOBIl5y1I3XNbCQq" +
                                                                "FDFxslnocbopQzzuLEceYoqU7RDzBVkPXIuDwlxwvYg9CyRskylHQR2uhj5P" +
                                                                "wJJEwdMppqx5VJ8WqI4UwM5pR/5aLipwqfZr5tw+JXA+Tu+60kRHKmJKbPq5" +
                                                                "4ZXLUi5WeDxBlhNunGLkKOyBvqBtQGFCQKKEKliqU0Ex72lyliBcgUylKQW1" +
                                                                "TWXdk61MmBjUNsSWlbKAWLZawfxW0dReLvgTXOkaeajWwVEuAqQSEZtM0Rnr" +
                                                                "UVxuS8x+OJP2c06G1/hqCnalabYc8ssqwzqDtuUjGpVKK51YCag9pXFOgMsR" +
                                                                "bBcyK4aYN1FCC/KIbaHQJJMlQbBgFnwBDZa9qQf32oiNbfuTSNOmGG1wcJqT" +
                                                                "0TyUdvM53t2FftIVZXG0g6rpnExMH7FYPMU2MNO3bNcZcUZcAQCQMYEjh/LQ" +
                                                                "nE5Rv8pKHt6F7YR3QLGOZRKQamCZRj1/5Var+YTwkAlU7/FJTdpNhD3Rs1Mi" +
                                                                "o2O/2o5YaiT3vSHvsbCS9Fx8sd1rtLRIgIlsG2OwPVv1i8mwDrmLIFUn4cCD" +
                                                                "eU5T21nPB7N2AsZdsouKXN5dwiAr7PgB5qF6j9pKVAWvJ1tL5cc7BGFhBp5h" +
                                                                "PJEOC80hdmOD9raMivIkJ7LeJCyKCbIJuotQDyReSNccsyHE5TJetEfbwbbf" +
                                                                "x62U1rbbvYEglerwugGsCgrM5Q5Ve6qWOGNsydfRcUB1JKbd7/gbdJitS3+o" +
                                                                "RQWWyTgIIFww7c43RG8yCxB/E3OYHmy8tUMuXW5Qh5B42bMTRIGoAmPk/ozd" +
                                                                "zwaM2l8sMQc1A8thjTmnzelwTDu9cD80BrZcRxCjtyHXxWw4VBYyq2zMrV3E" +
                                                                "Tp7MjHK3TbBuRUiRHPTc7bADOUXU9fuG4PIMxLiVtS72s8xAGD+Bd5GrKopq" +
                                                                "E5RH2W1hQLBgVq/Py9hm2UGMavNpT06pMsNs3sCU1XwLDbbT9Xw7UxMspJ2N" +
                                                                "ZiSdlbzxhmuMlCVR77XBjgtNkXSPBGCnq9G5G2jTld7nlqpELNMITVFv08tR" +
                                                                "0Oy18QGhDd3+UoiMmb3nwkBQi5GF8X57EHiyqc5Kf+KHs3mIj8iOW647YlpB" +
                                                                "kpUAnrQuHYdoOwqtdWBNjnelpQNdXQKzjW/3aa+YlKghDfOQ3oZxRnVUPZTn" +
                                                                "4GwmuHpYyKQKS3oQqZ1RvGS7pS12O5MpB3F5QQY51EUB3bQQwdHVNQkzYzRZ" +
                                                                "DyN1uku7FT7Rx6oR5sWg2sz9KEajTMXdKSdDlq/1cIyWLbGKBwE8JUbrkZVn" +
                                                                "C9rtg6gBJl5nZG2VcR/TqUKzhlisVJlWLHW/wMZ2XC9JtOXEhkkZoQR0sf68" +
                                                                "X4cKYiu0AYSwclCWQg2dmoYWtUl37E63coHma3VYKl2JIMpxrsqxpPRKjxGR" +
                                                                "6WKrsr2S1ky1P41tBMPHZEdSqXqdjrsqLXF93ZTFATQYM1BvA3eLFCHVfDyb" +
                                                                "hLKpldLSHBlAe5ALpMYOVtYilc1YUysy81RwJ/l6rgiD3rQzpQIvC9CglPcD" +
                                                                "Kt+r427t8UHlwbm265jpWLAiLR0OR8qYlscTMqqImV3uKlOSl+Kwkj24cNCO" +
                                                                "XDks2peXkZ/6OKIqgxLAgTEW0yXGQlgCKsnKFUyBAGy/8PMhZQxZaZEbo3pD" +
                                                                "7YmymWGmI+/jgTWSt6m7IP08j3qmGUFxu9ouN6WUhZEUb9dGiK31tiph5HBb" +
                                                                "yF4yMuY1a8PxYjjcBygdexXQV0MSBgAETnYb2+jMN8YOtIBxpXfMhQftBo4r" +
                                                                "ImhcsO2+F+VyMHGpRRzs1DIss6rY2b4IsBMK4P28GBmxlJWrrlU4DiCEKDbe" +
                                                                "rvP+NIDnMOjN1ZIb9NJiOPbyDlcAMrOKaN1XMRLq7Flj1rGGtJHUI9tmtLfk" +
                                                                "VIkJfb/CJ2MVjaKRwgjQlraUHTT3prOgwljSW80lQ5mCzgjJTAELPFUCJcJt" +
                                                                "a5Dpb0MwWrX1emvBsoK+cAIVXvDQqhrybV8VebC9wzq8TiDJBCZDhfSnzNSX" +
                                                                "szAJ4DCZDOS2RfaZniSKspSJMc6YQlUvhGk+n5I9FK19E+HzgS7sEnjYV7GB" +
                                                                "CBcqzKEiEWH9nismlqRm7QxoC0PRG1LjxUqP1UkQ1Ru1IEjRoY7yY71YY+25" +
                                                                "p8+nQdFTVlA5SLrhCBnQm/GkUkA9NwfwOlvRY5Xa0bMUGHSLjQRQ3IARYY9R" +
                                                                "RyFHZllFg/ImAKi81/WT/QajwF7XGRH6BCy2c9zXJga23yBOd9rr7rq4RHa1" +
                                                                "3WSXUlwhj1gQ0CkLJqE2VJXaToj7wGIIZf3QsKZVuJrFsz3SNQcrEZmTgDSe" +
                                                                "QbmVOW4cVJCMzpXU0IreNiboJC677lwwZgIn5cJwNdO3mKEmG2eBJMaKrsMx" +
                                                                "XU39slvvbHzK4LeJACiShSdWqiyDar9Ecxv1vAmx3BJLnIDdKELGCBM7EQgB" +
                                                                "Wb73evu+1csDs+pAW2CC8hzlYpXaC0vb8gCgp/kLPRsT4gC17YHX8W1Tj2eb" +
                                                                "fJn5q9F4hjk2U+/XRQ7uDpl0v3Q4K4L3aE7rledOlX1g9F0ylXZ1hBPAvjsH" +
                                                                "N/Y+z+Ux3hGDaFlJXG+YzeMsAIRtMOojLLp30SHkjxxBYeOhMCyXyYIbtiF9" +
                                                                "EwugqYPLUjEIDmrzaB/dRLY7t0i2XFhxvVPgSm8pMu0dkYiE0bOAOduVAJQT" +
                                                                "3NlUXY9dVVjuXTuskKBveB13t1cjkJpSfoQBukXkFN/pxXRggsOCwBYoMCR5" +
                                                                "FHBIy/BDzpPQfn+17wRs4fVNWG9DM3YpuSw7HXiSOBuXJjnpYGktFG40X5ed" +
                                                                "LVAhcbaONGc9sfoAnQ2gtb3sIVWGZv4on4nDot5puhPBUkmKito+Rq5zSozF" +
                                                                "vC/OyQrg5mi5j82e38+0vixgmBgY7R4cUcoYGk0NBchkzZpunU1/zKXdPsXP" +
                                                                "eQ7EoUKS5NQedgsx0rL9yJV2jFeNy426gMaUigf10grO95Q9miyXy9UeWaVs" +
                                                                "mef2PvWFxQgOSwOqZTzdWjgEjbllQU5W/TiZ9pZDJiQoMxuszaoKiVwKHZ3I" +
                                                                "3NCvlInFlW63U7dh46HCh5UOTcswHvNxvjSgUXt2sGPdb/dNNay/MOrvOqA7" +
                                                                "o9r7IhvtKB2ntALQlrrVnfAzZrzrCXOljs0L2+34CQ9EHjBZCnEbWvSZGUIj" +
                                                                "cqGwa4WlIWXP0GRB8YsBCCmrFARZF5iFCQOTjKHDojSjcaOt0zQP9HKgN91D" +
                                                                "3bnMTUSNQNuIN8VFulC64CatthZRG3qbHekjcUNbA7qjwO2Ng0MigC01rNzs" +
                                                                "dlt8XK4Ye67i28mGCKc0Hxtimchj2Vv2SrMzKO22Ag9gZImuNu3KM8HNVBRs" +
                                                                "YxYzGyuexCWPLaJuvBH8GdDu4agN7UCpvTGitjaTNJ/VIwqICprJ2/TaWkwX" +
                                                                "xB6RuoOANaBkWMUG1pcTPQ9GzHi0oab6aND19LUby9yo0BMeJG1oSosSsMq3" +
                                                                "3HKxccYzE6flwBLXeu0cugxPw816wsyckt/rgMgQKrheYYQADsLdpP6onhZE" +
                                                                "OsqMUbJAQD1Y7yg6MbHSHFOrLFkvevlmwO8mDr8WucCUIZ2GgdCC28MdsDD6" +
                                                                "5bgv5dsRl1hTbU9ATFqFJtRzlfpzGwmt2ZbFMLiwo/1owI21vQfZATqLMCXv" +
                                                                "+tjcEYGU6/CD+gsQsvqeDg7AZd8agDbbpVjLQfY9HId7mmnGVZRl1Bqs7b5d" +
                                                                "KWU7R4oRDdrREF5VXJvGx5sOOduLfgyy5mJGQS6DllpXNXxkTYhIbtWKtFOc" +
                                                                "AkrDLMmuTbDKuu13IjzWhD05X41mK4zU1sG8O5x6Rb8srY3TkS0o7eDayu1V" +
                                                                "qrrmOovBCtxSKdhe2VW5NJUuu1mDeU+NKo+Svf56N9oNyV5F901zN8aZHFqk" +
                                                                "q8GEBHCmbyaU3LdG5QQId53EHBWdNTqP0ZVDdOdsjvoosLEw2harxWSUebFh" +
                                                                "158/fFtDaRZElJWUFh2ewDltv0/EBZd2Jks9Xg1Qw6y/08E0T3G2snVeo4rc" +
                                                                "s6WRA+66Q1TZjqhOW8hAaTZdLvZF5MNQPu354wkgzASsst3JZPKR5mhOPj2b" +
                                                                "fMfhSPU8e308kmz65oezvPL+B7St00TCxYF6qzlhfP7NMsuH08XX//xrv2hQ" +
                                                                "X+xePUUX0tbjaRB+yDVz072D1KPNWeU9NxeWh3z6xRH7t77zPATv/pN9PKt8" +
                                                                "36WRL0P/zeUbv4l9QP+5q62Hzs/S70ni3410++4T9LfEZprFPn/XOfrJMUlW" +
                                                                "M/10I4P3HP8PPXX6fKTpffZwxv6OO46c7z1pvzjvLs8pXm0onlF6+Pi8+r07" +
                                                                "KF5KeFw5z1Y+f0l4tUYPmZTjafJv/dL33vPl67/7vaPgLl8GuAPwv7/xze98" +
                                                                "423Pf+mQcLzW5HkPE798i+LeSxJ33X04zPuJ81mNmln9aP1/Vz2br50+/3Ha" +
                                                                "Wv8g03ZIkGmuSTu+fZoN/yOkfpjf/B4tnJ+BB02W5dJrU0m/nymcZz0ecU3f" +
                                                                "TjcHSOb0NL958GnroVr2TdW/P7ErB2JHOk1RNMXh1S/POb56HPcsgfLsRXoD" +
                                                                "dgPfbDJ5h1TOebLnAOAEt87v6ZxBlPeVgnac9mHUprjxgETdTz6g79Wm+Atp" +
                                                                "62G9Yes++Z5jruzIyQFj9yYecu9MZ6Vu");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("hmfJ2OcOyH+pKcy0dc1THf9+sr+WB87RxrVz637b/S56fPGeKPD9xPC5B/R9" +
                                                                  "vik+k7YeO70Fcf0irlzw8WgD/oFmsqeXT64cU/ZfffOEuq7GgfsDTZ7fcWvk" +
                                                                  "w+DgJbD/oT5488MnUaYmTpQFqXnjmJQ+aQR5snWs646fBzsTMa07bkncuHny" +
                                                                  "iXTjNDeOjvO9cfP2KzfD8M542hQfPJ/84ffI5ck3vV8IwwdI9q8+oO+vNcVf" +
                                                                  "SVvvfjM2m/5PX9LCYw362+/Vwpd+yLQQO3nNw51qcNJG7Ccf+zh3clnW98SY" +
                                                                  "tPXoKYXybhU8ej8V/PUHquCNB/R9qSlerw3/jMX7ifzJBvz5e0R+5bUfLpEf" +
                                                                  "0+onp/avBYFrqv5B9me7j8C68bFDzv3kONQnVE975RCsjrVjsDvUT4KPo7W2" +
                                                                  "HOvkRnDykY+c+Jnr3jw5bldqbDcxb5/m5w+xLjH1LHbS/S1S1UxXaFK7ql4v" +
                                                                  "3gm8MfXdjfsPd4Z0fDvHPL76QSPdOijXkrpx86WT4NB88/YpCzVXzvmsTk6F" +
                                                                  "c68TP2hTdIi8D47mz9w5x6N07p/6PxD7uw8wtH/QFL+atp68Wx/3WwMePVXd" +
                                                                  "nVZ4dkHq3WfWd9bQ9F7/gcy5ef31A8BvPGAeX22Kf9QskfU8dDVJr5/K/k1X" +
                                                                  "jGfudZz8h8txTj3mqN+TQxg4GGNyiFeHdeJ6Fho1m4fmk4+8ifv8Iez59skp" +
                                                                  "3aOX0EHNwP4HQfiWHvjWkdyNc2dphvr/Xt++/sDg+tsP6PtGU/yztPXWO0R6" +
                                                                  "tLKL+5ZheGF3z2VxcwP8jf/xx773yGP8t06vvrRO/vNnXxB+/x0f/Tc3bvy3" +
                                                                  "V+ezV7/wFeQzf/HPfQYM0/9w8vLP3vh7//D/AXBzksyZLgAA");
}
