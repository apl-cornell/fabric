import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;

interface DoubleReadBob extends fabric.lang.Object {
    
    public DoubleReadBob DoubleReadBob$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1446077003000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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" +
       "5uZYJQAA");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements DoubleReadBob
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            DoubleReadBob._Impl.main(arg1);
        }
        
        public DoubleReadBob DoubleReadBob$() {
            return ((DoubleReadBob) fetch()).DoubleReadBob$();
        }
        
        public void jif$invokeDefConstructor() {
            ((DoubleReadBob) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return DoubleReadBob._Impl.jif$Instanceof(arg1);
        }
        
        public static DoubleReadBob jif$cast$DoubleReadBob(
          fabric.lang.Object arg1) {
            return DoubleReadBob._Impl.jif$cast$DoubleReadBob(arg1);
        }
        
        public _Proxy(DoubleReadBob._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements DoubleReadBob
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm22 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff23 = 1;
                $label18: for (boolean $commit19 = false; !$commit19; ) { if ($backoff23 >
                                                                                32) {
                                                                              while (true) {
                                                                                  try {
                                                                                      java.lang.Thread.
                                                                                        sleep(
                                                                                          $backoff23);
                                                                                      break;
                                                                                  }
                                                                                  catch (java.
                                                                                           lang.
                                                                                           InterruptedException $e20) {
                                                                                      
                                                                                  }
                                                                              } }
                                                                          if ($backoff23 <
                                                                                5000)
                                                                              $backoff23 *=
                                                                                2;
                                                                          $commit19 =
                                                                            true;
                                                                          fabric.worker.transaction.TransactionManager.
                                                                            getInstance(
                                                                              ).
                                                                            startTransaction(
                                                                              );
                                                                          try { final fabric.worker.Store alicestore =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getStore(
                                                                                    "alicenode");
                                                                                final fabric.lang.security.Principal alice =
                                                                                  alicestore.
                                                                                  getPrincipal(
                                                                                    );
                                                                                final fabric.worker.Store store =
                                                                                  fabric.worker.Worker.
                                                                                  getWorker(
                                                                                    ).
                                                                                  getStore(
                                                                                    "bobnode");
                                                                                final fabric.lang.security.Principal bob =
                                                                                  store.
                                                                                  getPrincipal(
                                                                                    );
                                                                                final fabric.lang.security.Principal top =
                                                                                  fabric.lang.security.PrincipalUtil._Impl.
                                                                                  topPrincipal(
                                                                                    );
                                                                                if (fabric.lang.security.PrincipalUtil._Impl.
                                                                                      equivalentTo(
                                                                                        p,
                                                                                        bob)) {
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
                                                                                                bob).
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
                                                                                                    alice)),
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
                                                                                                bob).
                                                                                              join(
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
                                                                                                    alice))),
                                                                                        store.
                                                                                          getRoot(
                                                                                            ).
                                                                                          get(
                                                                                            fabric.lang.WrappedJavaInlineable.
                                                                                              $wrap(
                                                                                                "bobMessage")));
                                                                                    if (!fabric.lang.Object._Proxy.
                                                                                          idEquals(
                                                                                            myMessage,
                                                                                            null)) {
                                                                                        final java.lang.String messageText =
                                                                                          myMessage.
                                                                                          getMessage(
                                                                                            );
                                                                                        fabric.runtime.Runtime._Impl.
                                                                                          getRuntime(
                                                                                            top).
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
                                                                                   RetryException $e20) {
                                                                              $commit19 =
                                                                                false;
                                                                              continue $label18;
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   TransactionRestartingException $e20) {
                                                                              $commit19 =
                                                                                false;
                                                                              fabric.
                                                                                common.
                                                                                TransactionID $currentTid21 =
                                                                                $tm22.
                                                                                getCurrentTid(
                                                                                  );
                                                                              if ($e20.tid.
                                                                                    isDescendantOf(
                                                                                      $currentTid21))
                                                                                  continue $label18;
                                                                              if ($currentTid21.
                                                                                    parent !=
                                                                                    null)
                                                                                  throw $e20;
                                                                              throw new InternalError(
                                                                                ("Something is broken with transaction management. Got a signa" +
                                                                                 "l to restart a different transaction than the one being mana" +
                                                                                 "ged."));
                                                                          }
                                                                          catch (final Throwable $e20) {
                                                                              $commit19 =
                                                                                false;
                                                                              if ($tm22.
                                                                                    checkForStaleObjects(
                                                                                      ))
                                                                                  continue $label18;
                                                                              throw new fabric.
                                                                                worker.
                                                                                AbortException(
                                                                                $e20);
                                                                          }
                                                                          finally {
                                                                              if ($commit19) {
                                                                                  try {
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                        getInstance(
                                                                                          ).
                                                                                        commitTransaction(
                                                                                          );
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           AbortException $e20) {
                                                                                      $commit19 =
                                                                                        false;
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           TransactionRestartingException $e20) {
                                                                                      $commit19 =
                                                                                        false;
                                                                                      fabric.
                                                                                        common.
                                                                                        TransactionID $currentTid21 =
                                                                                        $tm22.
                                                                                        getCurrentTid(
                                                                                          );
                                                                                      if ($currentTid21 ==
                                                                                            null ||
                                                                                            $e20.tid.
                                                                                            isDescendantOf(
                                                                                              $currentTid21) &&
                                                                                            !$currentTid21.
                                                                                            equals(
                                                                                              $e20.
                                                                                                tid))
                                                                                          continue $label18;
                                                                                      throw $e20;
                                                                                  }
                                                                              } else {
                                                                                  fabric.worker.transaction.TransactionManager.
                                                                                    getInstance(
                                                                                      ).
                                                                                    abortTransaction(
                                                                                      );
                                                                              }
                                                                              if (!$commit19) {
                                                                                  
                                                                              } }
                } } }
        
        public DoubleReadBob DoubleReadBob$() { ((DoubleReadBob._Impl) this.fetch(
                                                                              )).
                                                  jif$init();
                                                { this.fabric$lang$Object$(); }
                                                return (DoubleReadBob) this.$getProxy(
                                                                              );
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.DoubleReadBob$(); }
        
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
                                                                                   o)) instanceof DoubleReadBob;
        }
        
        public static DoubleReadBob jif$cast$DoubleReadBob(final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (DoubleReadBob._Impl.jif$Instanceof(o)) return (DoubleReadBob) fabric.lang.Object._Proxy.
                                                                $getProxy(o);
            throw new java.lang.ClassCastException(); }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (DoubleReadBob) this.$getProxy(
                                                                                );
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new DoubleReadBob.
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
        
        final class _Proxy extends fabric.lang.Object._Proxy implements DoubleReadBob.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((DoubleReadBob._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((DoubleReadBob.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((DoubleReadBob.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((DoubleReadBob.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(DoubleReadBob._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final DoubleReadBob._Static $instance;
            
            static { DoubleReadBob._Static._Impl impl = (DoubleReadBob._Static._Impl)
                                                          fabric.lang.Object._Static._Proxy.
                                                          $makeStaticInstance(DoubleReadBob.
                                                                                _Static.
                                                                                _Impl.class);
                     $instance = (DoubleReadBob._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements DoubleReadBob._Static
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new DoubleReadBob.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm28 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff29 = 1;
                                       $label24: for (boolean $commit25 = false;
                                                      !$commit25; ) { if ($backoff29 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff29);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e26) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff29 <
                                                                            5000)
                                                                          $backoff29 *=
                                                                            2;
                                                                      $commit25 =
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
                                                                               RetryException $e26) {
                                                                          $commit25 =
                                                                            false;
                                                                          continue $label24;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e26) {
                                                                          $commit25 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid27 =
                                                                            $tm28.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e26.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid27))
                                                                              continue $label24;
                                                                          if ($currentTid27.
                                                                                parent !=
                                                                                null)
                                                                              throw $e26;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e26) {
                                                                          $commit25 =
                                                                            false;
                                                                          if ($tm28.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label24;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e26);
                                                                      }
                                                                      finally { if ($commit25) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e26) {
                                                                                        $commit25 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e26) {
                                                                                        $commit25 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid27 =
                                                                                          $tm28.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid27 ==
                                                                                              null ||
                                                                                              $e26.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid27) &&
                                                                                              !$currentTid27.
                                                                                              equals(
                                                                                                $e26.
                                                                                                  tid))
                                                                                            continue $label24;
                                                                                        throw $e26;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit25) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 16, 110, 107, 36, -128,
    4, -57, -35, 110, -51, -112, -80, 97, -20, 20, -87, 57, 22, -27, 84, 33, 93,
    -72, 107, 62, -44, -32, 59, 54, 39, -18, -13 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1446077003000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16a8zj2HWYZnZ2Z1/e927trdf+vJ66O+Z6KFGiKO3UTiVK" +
                                                                "JCWSevAliovNlqRIieL7TdHewm2R2EgK10jWrlMkQVBsmybeOkbRNAXaBVIU" +
                                                                "TZzaDVrDKNofqRdGgyRwXDRIH/7RJr2UvsfM982OkWIDWIAuL++959xzz+se" +
                                                                "3nPf/F7t3jiqPW+qmuXcSHaBEd8gVG3EzNQoNla4o8axAFpf1R+6Mvri7//i" +
                                                                "6gOXa5eZ2sO66vmepavOq16c1B5htmqmwp6RwCI3uvly7QG9AqTUeJPULr/c" +
                                                                "L6LaUeA7u7XjJ8eTXMD/BQh+/e/+6GP/5J7ao0rtUcvjEzWxdNz3EqNIlNrD" +
                                                                "ruFqRhT3VitjpdQe9wxjxRuRpTpWCQb6nlJ7IrbWnpqkkRFzRuw7WTXwiTgN" +
                                                                "jGg/50ljRb4PyI5SPfEjQP5jB/LTxHJgxoqTm0ztPtMynFUc1v567QpTu9d0" +
                                                                "1DUY+Axzsgp4jxEmqnYw/EELkBmZqm6cgFyxLW+V1D54HuJ0xddoMACAXnWN" +
                                                                "ZOOfTnXFU0FD7YkDSY7qrWE+iSxvDYbe66dglqT27DsiBYPuD1TdVtfGq0nt" +
                                                                "vefHzQ5dYNQDe7ZUIEnt6fPD9piAzJ49J7NbpPW9yV/53Cc9yrtcuwRoXhm6" +
                                                                "U9F/PwD6wDkgzjCNyPB04wD48EeZL6rPvPXZy7UaGPz0ucGHMb/2qT/6qy9+" +
                                                                "4Ne/dhjzF+8wZqptDT15VX9De+Q/vB+/3r2nIuP+wI+tShVuW/leqrPjnptF" +
                                                                "ALT9mVOMVeeNk85f535j+elfNr57ufbgqHaf7jupC7Tqcd13A8sxItLwjEhN" +
                                                                "jNWo9oDhrfB9/6h2FdQZyzMOrVPTjI1kVLvi7Jvu8/fvgEUmQFGx6CqoW57p" +
                                                                "n9QDNdns60VQO/49Cf5P12qXv3z8/FJSewXe+K4BW/HGME14EPmB5hfwwNdT" +
                                                                "1/ASoAB+5BmOA6uBA6+tBAbmHFk6bBSqGzhAosBKdEPzNTiOdACWao7BGeqq" +
                                                                "72s3wNDgz3uColrhY/mlS4D5H9T9laGpMZDksVb1Zw4wHMp3Vkb0qu587q1R" +
                                                                "7cm3fmavWQ9U1hADjd7z7hLQhvef9yO3wr6e9od/9JVXv37Qygr2mLVJ7T23" +
                                                                "EQXoeLgysBvAZd0ALuvNS8UN/OdHX97r0X3x3uBOQR8AK3jJ8YGzK2qXLu0X" +
                                                                "8dQeeK89QPY28CnAbTx8nX9l/Nc++/w9QG2D/AqQXjX02nkjOnM9I1BTgWW8" +
                                                                "qj/6md//X7/yxdf8M3NKatcuWPlFyMpKnz/PkcjXjRXwgmfoP3qk/uqrb712" +
                                                                "7XLlYR4Azi9RgXoCT/KB83PcZq03TzxfxYp7mdpDph+5qlN1nbirB5NN5Odn" +
                                                                "LXtJP7KvP/6n4HcJ/P+k+leKXTVUT+De8GOjOjq1qqT2ne/8xBvf+fGfeuli" +
                                                                "39ELP7Ar9yPbiK4FQHK6FajOnxVLUHW8m0aggl+l+y8hzReb2Mda9evgFxws" +
                                                                "odKgc1Lb7yQf54Of+0+//QfN/R57suk8esvuxBvJzVscXYXs0b1Le/xMIYXI" +
                                                                "MMC43/nS7Ke/8L3PvLzXRjDiw3ea8FpVVuxQARv86Me+Fv7nb/+XN751+UyD" +
                                                                "k9p9ATAcS99T/mGA6CNnUwEf6AA/DCiJr4me668s01KBlVXW8H8e/UuNX/3D" +
                                                                "zz12UGkHtBwUJKq9+IMRnLW/r1/79Nd/9H9/YI/mkl7twWfsOBt2cOxPnmHu" +
                                                                "RZG6q+go/sY3n/uZ31R/Dpg2cMuxVRoHT7tfXm2/quZeX2/sS+RcH1oVzxf7" +
                                                                "vvfv26/EFzc5oooWzuxNgd/82WfxT3z34MNO7a3C8aE7+DBJvcUVIL/s/s/L" +
                                                                "z9/3by7Xriq1x/aBiuolkuqklVQVEGrE+HEjU3vPbf23hw2HPfLmqT95/3lb" +
                                                                "v2Xa85Z+5jtBvRpd1R88GPdeD4pLtaCq3NxDfGRfXq+KF/c8upxUXrQK4BKA" +
                                                                "2PKA69yDJbWrx4a6h3g6qT11MKIbh+Ybi/2j6nv2YClViR1PCZTx3voN5AZS" +
                                                                "veN3nvmeqvqxqvhEVfzIybzPbh392onhSyCQBEpz7TD3CSmP7bWn4t2NQ8i1" +
                                                                "73hfUnu4MuPAUZPK/xV3oAtoxCNnwIwPorWf/K+f/8bf+fC3gQaMa/dmlXSA" +
                                                                "4G+ZYZJW4eyPv/mF5x56/e2f3BscsLbZV3/vf3y/wspUxTCpPVeRzftppBuM" +
                                                                "Gifs3kKM1QnlFzVxFlku8A3ZcbhlfPb1n/jTG597/WCFh5j0wxfCwlthDnHp" +
                                                                "fpXvOawPzPKhu82yhyB+71de+xf/6LXPHGK2J26PsIZe6v7j//h/v3HjS2//" +
                                                                "1h325iuOf9huz4v7yU9RrXjUO/kxDRVHe2LBeam8bGEDH5Nzk4AYCl/PJqUT" +
                                                                "ySN/W6bzHBJb2/XMMemijLFGs4exZd4p41jEcxb35xNCH/fsMWERat7z6bW4" +
                                                                "YPmC2zD9FTzjemOOYeMw4fit5O82vSCCMRRrDtwN74aZ6gVeCkcZjMImXG+y" +
                                                                "lBCQ043dCtk6XTK+U+iYRG7Njd51elm7p0ykToovBQ5f1f1Ow8yUmWlPih00" +
                                                                "ttfoWLTU0Wi7YA1nsSgmoksqnM64Ir8d5Bu80xAFh494X51JMRGLUl/kuBEx" +
                                                                "VcXlOHUXMt6u43OXcH3bGWhiLofD0WIR+Gt7E4VDVZoQ0tDSmSHmFVYZCHZB" +
                                                                "jkMplRrsZo3EGehBxJaNDWZqjG3bcmII0+4yCBDWTkdu3bZ3jmWRNk0AGiO7" +
                                                                "JBTJqZfrZLny6ipnu0wfJhpDIvO4vmgX27Hmcqvhjqc2TB0f+QHOK5OyK0vR" +
                                                                "0CXHgrwMh9KinAsTN3cm9dEiHstst15MY4kt2pgotoEqq+NwEWgBvelzYZh0" +
                                                                "VB4a8VZhlA5j2jTD9BONZCJvZTahYZ2qR8h4NTbEQlXWvDX2kTUHufZGcVxV" +
                                                                "CxcYy4erIPW9uoRvJkxbbY/ZLU8Glk/biN0c91WCpdj2ivP90TJMIGqoW1Ov" +
                                                                "t0xbQRcZj1023C1dN0wyiyB1mw7NJGi1lMVi3R/BUtHQGcJtZV1/lE39YTtU" +
                                                                "Ong4sT0Ot52xv2vhxKjNOpKJ9/FespPo3MEm0tAMKV4PUXTB9VXRjieeNFaX" +
                                                                "ZECtFro19w2dRYmw25uvow2u0sPAhudMGWbevMQjB7dxXiz4oVKS0noUDeab" +
                                                                "5Y6LN6vVTGvsotLBrXGP7QZ0HI3Mwp4P5vZ8suqxDZ1iaF6PSXSmM9kczXCH" +
                                                                "xoe7FB+Wrl5g3bqhl0XeQDuWZbJl3y5jxkWleV2EaAN129lA9Q1zYVMKPRWC" +
                                                                "jc+TScebyjPIcFPAk06/n3GFFAv9dNBsKey03HU1kyuhqR23GwGxEVEpX8DA" +
                                                                "L4TkssGrWpuQ+M3UV0YM7xI53XBb2MLuT9uCbY9RrDNmi7Dvgj5+IRnFLkD7" +
                                                                "HOcse44s9o2WM1ZnahcN1knEYNlQjbMGloe7np0mUBvbDsarpbOYiLFgJwTN" +
                                                                "os5wq6JhufAhUZ3SKb8j5r3lqL4Rw13u9O2CTezAW496RkEj8wbJ57TOU7wS" +
                                                                "iejSWFv2dKEqq6C3Mbk4EeypiImm4bMdPtlgEj/Hd5tOGLqeyvQ3a15PIyUx" +
                                                                "meFgW0p9ez6yZBpbriJehQdT1PEcIfAtPhnmXEz2FWrAY3Fj0symaL4SmCae" +
                                                                "cUTAJLwWtF2WhvDtoDeKByyr991ed9jJqLCpyZjLdSRyV66pyZi3SVyNerSN" +
                                                                "AgPNfcEvl5bSbQ0SKcfpHhMuthLaaZq5Nlk3sx7ZjoDzwNsQJbaElhg11U0Q" +
                                                                "qJtovYXgeMliSw2h51OPq3vU2O5v/ZbibsZF3HDMmeSxtE8oWdBO+72wFwyG" +
                                                                "ysDXiuGwcCe86S/Wgx4l7wZCU1zMtR7nJ8Jo2V4Q8SqBWy2hM9MkyEu3RGbH" +
                                                                "Pk7joc3ha4Xa8lrk+/WBMYiZbccdb3f8NNqlrB3S+ay3dnOXH63yCW5gmzU6" +
                                                                "D0kU5RxnNGfas3AhUuRIiiNPbyxkqel0t/0mwxruAF6xCWn15kstdnbNxWrT" +
                                                                "wCB1MhY5poElTH9dyq3mKN3wNiyPi6DBW+Q4w5At7KNJUCpGXuS44ucjpM3y" +
                                                                "CBL1SNZyfYbTtm2s0+44zW6UKyulOdoQcyaz0rGhKVghmDnRoswGvBnkbG+a" +
                                                                "DRk1ZLQenaprR/TEbqllxgIeEBvVqmMYPKUhasEO116poDCzGYu7tjrrOwRl" +
                                                                "iGGv1ef7YFqZcyUe5WM3WO7sdb9ODsZe6kN6h117mIC0+I43z3PEtpuUmfkD" +
                                                                "rdnsIAsTxrtSZ7LUkyE3LEUtpbZCOvObnWRlKGqKt1NuCxeTKIcNQxSWfXM4" +
                                                                "Q8jGSN1Z1BJZ9vKgtIeh1uHVXXc66CEDJWZ6nIuvscFqPjChBVfgET8r5tba" +
                                                                "6WaOZQxpbQGzaacNT31nQrW2ozXPGTHtRHHuTfK5KMhpzC6XTWQwbbgYQdRl" +
                                                                "vtnhyGZrgrfzVF2EozQUlGwdoyEDdiGZ6IeCMOn0evM6IXhrlNZFuyUTQ5ZU" +
                                                                "oNGyHq9JFJo3qHk6ntiE10hxztb1lj6x/CL3izHQdaSurCiNQFWwWUul11zu" +
                                                                "MokweAwWtqrUmSJy0aaRgUBlwdot3LmWkGpfsaAd33S1mQpi40LQwyFNjfGp" +
                                                                "R8sUMmyuqG3QbaF5w5BGxVoYhENqM9aSgG+FEDMtOllTQz2CXmpteh64QtE1" +
                                                                "M37YgHRSXkxxy8vrY3c9KQfdBZpJjEVg6azeAttqCTsITaeiTztxroz58bze" +
                                                                "0xOvnbcjLUCS9Tb0+yrWJmRLTsxS9Lpotz2ZY11hovDUOt4yEjeXnTRVegML" +
                                                                "mynaijaB+4VaPhKiES4OQzjrQNFyvuxkUMuRhfkQL6ESMWVDgqmm3tZSBRm1" +
                                                                "ze1823LFSS478GBlaZ5TJ6dlvW+MowbSdrKiGyQUAildMm1RS25ejjMFasUT" +
                                                                "CF6tVCcWJ0TDwlquxIUMma23et7ogi16RgczdFmfTTtA2dh1SPYkt7NNLKPR" +
                                                                "aeR9OddH2xyeo4tSmkyMjGu74nZrQdk0y9S8qQ4bgqOSRl1mh4OA0Catkpx1" +
                                                                "kToUy4iV1fU+ka38rbqZZW6IOQuZM0oUDQcj3pgP6nWh7KujOWvY3W6IZBri" +
                                                                "4oSOKC1WXTZh39AWGnBIXWhjy1taGnER67JutGSw7WzULEbeeDHpd7S1bjgm" +
                                                                "6UcTdOCSrAnv6E5BonABYraoQEYC8K9tY9ZljX4HbkDx3EBlrQNNk7TLMhja" +
                                                                "7HF8s5khDCvKjALB3V03lltbzzDxnTPl6w0prVuuJgkINvIIH+6Mlj0CE5vz" +
                                                                "KPCzLFRtvTsR6G4KZVok5LN2MaT64XwWb9lsLVJeBGJnoEae4iwbC6qPtCEz" +
                                                                "ZMOoX+8FmZWEBJUCz5WyujcmBhg3MGEsEklitcN26DpZ7XxenbTcJl5MfGNQ" +
                                                                "MLsyDDiZWWCJ5Mum34AwyW7YM3VrDryBg7QV37XUDGsaKWz2cjXcmaaxW+Bd" +
                                                                "R+310TrDiUhuBxipp0sbolyFGpUkz7gmtVgpG5mbbJrzfj9UUjuRQs1E4IVC" +
                                                                "1LGG20xFxQx9mqQ4kcDNAHLR7hgWJnSAwfxAHadkX0LwcbCkvYQ1FKeM8YJl" +
                                                                "h+R0NkNzBDgETUokbcyn62LCzbaSnLB1IlmOuUiIx6y51fldiw4LHE05Vgmn" +
                                                                "uN4IyVa9EbXaiSLLSYlxDb8UKbqPrMNiKXKCyMQeX9CDxo4OhqjamunyqDXT" +
                                                                "VlbcmreH5SooF33E1DqMq6RoP0Hwsj3XeTwxIEyDtxE86Bojdzf3iU7eaJS6" +
                                                                "og9SJZ7JTYF1opaXlw6Sokhjkc8ibrvmU24CvO2MUPtgF8f8Ma4mbRymN8XS" +
                                                                "dEKmLnhQoUI03KSinpIqJaMgslya5c6q4x7PjxdwSqhJroQQKRpYR0AkBLOQ" +
                                                                "VJiBDXHFF01T4AeZvduZgrSVY5HoIHpWWgpMd4GDWEpcagu9kBmbcjRazjc0" +
                                                                "IwmGwpRxyzRmkeiIcMGNhhRHkl4ZzVqhwLMdC4/1hqWSuDKajXpWZ8lYU90a" +
                                                                "Rc0Q0k0baFWuNjK/XyLoFmaIBT+1yea0OenOOwk0yeSp2JVafMwz8025GXoA" +
                                                                "Nh0i66EsIHCbWVjrecEGEt8r2p6IktJYlsYKXq7ZpcBbPiqynfoKieaSPKEF" +
                                                                "SB6UrTqqTqJ0gRPINGAlP04j8Nkby+7Wj9pbLtk5RBsiKau3NKP2EFuw8VjQ" +
                                                                "DDPlk1CI4SZLL9r4BouRLG8MuB5tQNx2J8CxGTilK+bFFul5/ZHXDMwuuw07" +
                                                                "4KvMp5ektaal9lwgZdwbTUGYovKkOQ6ZZdtu85HWLWDwOTlb0HGnwGKtZ+tJ" +
                                                                "M+U4ZRbxq8iGgw6SZwzgOb0icCZGqXEbHe2UmLZcvBzFu37Od9h+Puz39aHj" +
                                                                "0uUWHUJWp9GNZitIsbpTaNfzjMDS7AGJA5dJ+hLVqyK6rqBF9DhJkShC3eYW" +
                                                                "kzZtD2YKmGNJLkUN0oMdzOdWCCmrkMxtiWavM43kJMawcLJal30LoYPergcb" +
                                                                "fQwPpK6TLjfJSpyuBdaAENQjY8pVaQ6foOR2xy1aabEj3JHkJiiQYzRNnP5y" +
                                                                "O+ZmfmlNhJYn8jgJTTE+Egk6Vw0RxAy91GZ3mh1BphvHsKVRHdXj5UhVikmP" +
                                                                "QlKEBttPw0s4l4l0mVrgodG2IeDM8wymxcRqNHAJa3Ya3jgPBI+Boy5WMng0" +
                                                                "nZUbwjVmsTftAEu1evg8ai9imy2pNSVxs2ANz7I23RiEsQvNVyBa1AxcoPmW" +
                                                                "0lmwUW83mE1t3e2uUqSzbGdQNAYx4K63dPpSsIvikcFFzsiE4pz1Zs12tw3h" +
                                                                "g2jaSnK/wwxGLu7BQzrrMugsahUtOckThO3CYpNfY8hutMFIaKG3JtvBckxK" +
                                                                "rVbo9CF7QvWn4GN/upXBh8ky8+WJ013rdTRvQUsexQcsMQmgwjWam42kr1fD" +
                                                                "aL4xo15UCCQdNqKN6A1hqAGsuAu2RshehZDWMzSb0/0pHiIzbgbNNJPu03RB" +
                                                                "2SAyrWvmMLXUrqivUwVrNdBxLhEI4fngSyWTpVWLqHPIej1uUOCjNiZVmVuR" +
                                                                "xLw0eHZuZizK070FijgRrrgcZ4+GPBWjUBjKC2y0zGkXZtl82Ya4dU+DMMeI" +
                                                                "ptsuhVOKjaGEsIxND+wNelebzuOMyYlRQjsgni87VIGBuAvYYD4Gn0PmxJzK" +
                                                                "rK2oUlmfIZtBREA6KjfFiCAEfjXY2XwvT+MR2E6NZrCFxDo7GfqO36BmJK9A" +
                                                                "5a4jYUtWgZGhZ3poOQuEspGjkN2Xg8FuN2N4a2SaGRqmJK02O10YmmcKCP7Q" +
                                                                "1ZrIhcmQQh0Nt40F1tgtmZnnqqm5YrucRnKDpuLLI1KZDJN50iHm6G4KzzuZ" +
                                                                "j3Zz3ffYTZFljZmTl4pO6ludsoX6MsjcXFj4daG/lfs9UjOyWR9pLj1SE0oL" +
                                                                "9yLKYreU3B1sYGbe3LGLJcKhy5m52sogAosTu6QpFJLjqbZbEeMWvfGwwBLL" +
                                                                "goLLtS+B6es6q9DkeIiExnQQhRa12GH0PHK35hajug42ZssNxMyxzGmoJWTu" +
                                                                "fIie+owWOe7ARZX+MmUaW2WxzIaSpQ8kLDLGqA/DK2xJ8baOUoRbxhgaE/V4" +
                                                                "0TE8Lhp6y8UQfGZIG2aLt2ImyDNttArgHgNP0VRZjue9XnUMJx+fQz61Pz49" +
                                                                "zV0fjh+rPmp/blfc+TC2dpw0ODs8r1Wnic+9U155f5L4xt98/edX03/QuHwM" +
                                                                "Lia1BxI/+JhjZIZzC6qr1bnkhXsL7D6bfnac/vZ3n+vi9u+uD+eSHzw38/nR" +
                                                                "v8S++VvkR/Sfuly75/Tc/EIK/3agm7eflj8YGUkaecJtZ+ZHh4QYIPqxigfv" +
                                                                "O/zvefr4+XDV++T+PP2pW46XL56qn51tF6cYL1cYHz3G9NDheflPbsF4Lrlx" +
                                                                "6TQz+dw55gGJ7rMmh5Pj3/7F77/vrWt/8P0D485fBbhl4H9/89vf/eZ7nvvK" +
                                                                "Prl4pcrs7hd+/g7FxSsSt9182K/74dNVYdWqfgT8nwGr+frx818nNeXdz1PP" +
                                                                "LG99nAX/c8S+Xx91QQqn591+lVE591pVkh+kCqcZjvscw1snm/3I+fHJffUQ" +
                                                                "kto9gPdV1bszskt7ZAc8VZFXxf7VK04pvnyY9yRZ8uRZKgN3fM+osnb7tM1p" +
                                                                "Ymc/wPJvnN7SORlR3JEL2mHZ+1mr4oW7JOV+7C59n6mKv5XU7tUrsu6Q2znk" +
                                                                "xQ6U7CHsd7CQiysdFroRnCRen90D/+2q");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("MJLaFVe1vDvx/krmWwcd1061+8k7XfP48gUv8IPY8MW79H2pKn46qT1y272H" +
                                                                  "a2fe5YyaqxXQR6olH19AuXRI0n/rnVPoe0V/V9PlF+6MvNRsvthsfqyFXH/p" +
                                                                  "KEzV2ApTPzFeOCSjjyqmHm0t85rlZb5tDMAX99ntiBeuH30y2VjxjdvX/sL1" +
                                                                  "m69dD4JbPWxVfPSUEfvffecZUfX+bBDchde/cJe+v18Vfy+pvfediK36P39O" +
                                                                  "IvdX4I9flMg//aGUSGRlgJJbRWIllQiOXn6FPzrP8Qu+J6ldPcZQ3C6Iq3cS" +
                                                                  "xD+8qyDevEvfV6rijaR2/wmJd2L8I9Xw5y4w/tIv/DAy/pBgPzq2CM33HUP1" +
                                                                  "9hI4iU1884WX99n3o8OEn1Rd7bW9KzvUDq5wXz/yXyGAzCzz6AX/6OMfP/JS" +
                                                                  "x7l+dAhmALQTGzePM/V7TxgbehpZye4Go2qGI1ZJXlUHW3uMbwzdfuHO050A" +
                                                                  "Hd5OIQ+vnl/xGLhswK8Xrr945O+br988JgFQZZ2u6ug2Fl0067sFTnvvfHeP" +
                                                                  "/8StKz3w6M5XAfbI/tldlO5fVsVXgRe+XSp32ieuHgvwVo08uTD13hNNPGmo" +
                                                                  "eq+9K2uuXn9tP+A37rKOr1XFv0pqz1Tr0NU4uXabBN5xV3nioil96ofRlI5t" +
                                                                  "6CDro7172KtnvPdj+73kWhqsALH75qOPv4NB/Rk0/ObRMd6D3cx8QMDu3UB8" +
                                                                  "A3zCmgd0L5yaTzXV//fu9427Ot1/f5e+b1bFv01qD93C0oPGnb+RGQRnmvhs" +
                                                                  "GlW3xt/847/w/fvuF94+vhxTO3rMs699+spv/o737z7/VfUPn/ql7jO/K3zo" +
                                                                  "lX9uf+Jbb99s/+X/9sf/D6bz5VPNLgAA");
}
