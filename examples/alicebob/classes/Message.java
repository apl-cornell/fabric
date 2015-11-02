import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
public interface Message extends fabric.lang.Object {
    
    public java.lang.String get$message();
    
    public java.lang.String set$message(java.lang.String val);
    
    public Message Message$(final java.lang.String message);
    
    public void update(final java.lang.String new_message);
    
    public java.lang.String getMessage();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1445201358000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("H4sIAAAAAAAAALVaC5RV1Xne9zIPBkYYhmFgkMeFGSUgzI2goI4oMCMyZJBZ" +
       "PKSO4PXMufvOnOHcc86cc+5wQWk1RjFYSUtAZS0hmoVLNDSSrlLTRqxx1YCP" +
       "mqbGSMnyUZu2tAm22tjGRmP//9/nfc8MwySZtWbvc/bzf37/v/e5R8+xcstk" +
       "TTmp21TkZnu7wa3mlfTSKZkWz7aqkmVtgOaMfK7uB4eL9525KslGdbBqSZa5" +
       "ZXXqqiJvt9m0jj4ll6bpaVXq5mq6VddyorcFRsuSpmuKLKkZzbLZuI4+aUBK" +
       "a9xOb1zXDv2TNCnPLUOSeRs3uJblmqxwGDheDCzYippez+2Wosmmw0YOnauV" +
       "nCAS9tkuaPzk8deyj9xivJtkFV1stGJt1CwpxztYlVSwe3VTsYHWmsCiHYpl" +
       "w/5jZR3oMiVFs61+9oesvIPVKNAiabYi2Ty70tTzNpvVYcBGPapup3nRThuS" +
       "KeUdnjtJTrBSBbW6i4w2TH1AyXLTZjNLJNTp9HXgG7KWcpd3+CvRwP7L0vse" +
       "vq3mz0ex8V1svKKttyVbkUHSNtDTxarzPN/NTWt5NsuzXWyCxnl2PTcVSVV2" +
       "wEBd62K1ltKjSXbB5NY6bunqAA6stQoGkIh7uo2oMhJJQbZ102WnIqdwNeu+" +
       "ledUqQeUVO+LRbC3EttBFmNAnNzMgVbdKWVbFS2LsojM8Hhs+hIMgKmVeQ76" +
       "8rYqQ/uwWa3QnCppPen1tqloPTC0XC/YKOCpgy7agoqQ5K1SD8/YbEp0XKfo" +
       "glFVJAicYrNJ0WG0EmhpakRLQQ+56do9d2irtCRLAM1ZLqtIfzVMmhGZtI7n" +
       "uAlWzsXE6nkdD0n1J+5PMgaDJ0UGizHP3vnhsvkzXjglxlwcM2Ztdx+X7Yx8" +
       "uHvcj6a1zr16FJIx2tAtBZUf4pyMv9PpaSkagAH13orY2ex2vrDuB7fc9TT/" +
       "eZKNaWcVsq4W8mBHE2Q9bygqN2/kGjfRRdpZFThuK/W3s0p47lA0LlrX5nIW" +
       "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");
    
    public void update_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String new_message);
    
    public java.lang.String getMessage_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.security.Label get$jif$Message_l();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements Message
    {
        
        public java.lang.String get$message() {
            return ((Message._Impl) fetch()).get$message();
        }
        
        public java.lang.String set$message(java.lang.String val) {
            return ((Message._Impl) fetch()).set$message(val);
        }
        
        public fabric.lang.security.Label get$jif$Message_l() {
            return ((Message._Impl) fetch()).get$jif$Message_l();
        }
        
        public Message Message$(java.lang.String arg1) {
            return ((Message) fetch()).Message$(arg1);
        }
        
        public void update(java.lang.String arg1) {
            ((Message) fetch()).update(arg1);
        }
        
        public java.lang.String getMessage() {
            return ((Message) fetch()).getMessage();
        }
        
        public void update_remote(fabric.lang.security.Principal arg1,
                                  java.lang.String arg2) {
            ((Message) fetch()).update_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes0 =
          { java.lang.String.class };
        
        public void update$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                update(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "update", $paramTypes0,
                                    new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public java.lang.String getMessage_remote(
          fabric.lang.security.Principal arg1) {
            return ((Message) fetch()).getMessage_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes1 = null;
        
        public java.lang.String getMessage$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return getMessage();
            else
                try {
                    return (java.lang.String)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               $remoteWorker.issueRemoteCall(this,
                                                             "getMessage",
                                                             $paramTypes1,
                                                             null));
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static boolean jif$Instanceof(fabric.lang.security.Label arg1,
                                             fabric.lang.Object arg2) {
            return Message._Impl.jif$Instanceof(arg1, arg2);
        }
        
        public static Message jif$cast$Message(fabric.lang.security.Label arg1,
                                               fabric.lang.Object arg2) {
            return Message._Impl.jif$cast$Message(arg1, arg2);
        }
        
        public _Proxy(Message._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements Message
    {
        
        public java.lang.String get$message() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.message;
        }
        
        public java.lang.String set$message(java.lang.String val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.message = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.String message;
        
        public Message Message$(final java.lang.String message) {
            ((Message._Impl) this.fetch()).jif$init();
            {
                { this.set$message(message); }
                this.fabric$lang$Object$();
            }
            return (Message) this.$getProxy();
        }
        
        public void update(final java.lang.String new_message) {
            this.set$message(new_message);
        }
        
        public java.lang.String getMessage() { return this.get$message(); }
        
        public void update_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String new_message) {
            if (fabric.lang.security.LabelUtil._Impl.relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                                  toLabel(fabric.worker.Worker.
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
                                                                              worker$principal)),
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
                                                                       this.get$jif$Message_l(
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
                this.update(new_message); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                                 ); }
        
        public java.lang.String getMessage_remote(final fabric.lang.security.Principal worker$principal) {
            if (fabric.lang.security.LabelUtil._Impl.relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                                  join(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       this.get$jif$Message_l(
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
                                                                       this.get$jif$Message_l(
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
                return this.getMessage(); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                                 ); }
        
        public _Impl(fabric.worker.Store $location, final fabric.lang.security.Label jif$l) {
            super($location);
            this.jif$Message_l = jif$l; }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.security.Label jif$l,
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
                                                                                   jif$l.
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
                                                                                       o)) instanceof Message) {
                                                                               Message c =
                                                                                 (Message)
                                                                                   fabric.lang.Object._Proxy.
                                                                                   $getProxy(
                                                                                     o);
                                                                               return fabric.lang.security.LabelUtil._Impl.
                                                                                 equivalentTo(
                                                                                   c.
                                                                                     get$jif$Message_l(
                                                                                       ),
                                                                                   jif$l);
                                                                           }
                                                                           return false;
        }
        
        public static Message jif$cast$Message(final fabric.lang.security.Label jif$l,
                                               final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                   idEquals(
                                                                                     o,
                                                                                     null))
                                                                                 return null;
                                                                             if (Message._Impl.
                                                                                   jif$Instanceof(
                                                                                     jif$l,
                                                                                     o))
                                                                                 return (Message)
                                                                                          fabric.lang.Object._Proxy.
                                                                                          $getProxy(
                                                                                            o);
                                                                             throw new java.lang.ClassCastException(
                                                                               );
        }
        
        public fabric.lang.security.Label get$jif$Message_l() { return this.jif$Message_l;
        }
        
        private fabric.lang.security.Label jif$Message_l;
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(this.get$jif$Message_l(
                                                                               ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           join(
                                                                             this.
                                                                               $getStore(
                                                                                 ),
                                                                             this.
                                                                               get$jif$Message_l(
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
                                                  return (Message) this.$getProxy(
                                                                          ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new Message._Proxy(
                                                             this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                            interStoreRefs);
                                           $writeInline(out, this.message);
                                           $writeRef($getStore(), this.jif$Message_l,
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
                                               this.message = (java.lang.String)
                                                                in.readObject();
                                               this.jif$Message_l = (fabric.lang.
                                                                      security.Label)
                                                                      $readRef(fabric.
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
                                                                        Message.
                                                                          _Impl src =
                                                                          (Message.
                                                                            _Impl)
                                                                            other;
                                                                        this.message =
                                                                          src.message;
                                                                        this.jif$Message_l =
                                                                          src.jif$Message_l;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements Message.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((Message._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((Message.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((Message._Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((Message.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(Message._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final Message._Static $instance;
            
            static { Message._Static._Impl impl = (Message._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                    $makeStaticInstance(Message.
                                                                          _Static.
                                                                          _Impl.class);
                     $instance = (Message._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements Message._Static {
            
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new Message.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm60 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff61 = 1;
                                       $label56: for (boolean $commit57 = false;
                                                      !$commit57; ) { if ($backoff61 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff61);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e58) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff61 <
                                                                            5000)
                                                                          $backoff61 *=
                                                                            2;
                                                                      $commit57 =
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
                                                                               RetryException $e58) {
                                                                          $commit57 =
                                                                            false;
                                                                          continue $label56;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e58) {
                                                                          $commit57 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid59 =
                                                                            $tm60.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e58.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid59))
                                                                              continue $label56;
                                                                          if ($currentTid59.
                                                                                parent !=
                                                                                null)
                                                                              throw $e58;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e58) {
                                                                          $commit57 =
                                                                            false;
                                                                          if ($tm60.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label56;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e58);
                                                                      }
                                                                      finally { if ($commit57) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e58) {
                                                                                        $commit57 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e58) {
                                                                                        $commit57 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid59 =
                                                                                          $tm60.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid59 ==
                                                                                              null ||
                                                                                              $e58.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid59) &&
                                                                                              !$currentTid59.
                                                                                              equals(
                                                                                                $e58.
                                                                                                  tid))
                                                                                            continue $label56;
                                                                                        throw $e58;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit57) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 11, 64, -60, -18, -58, 57,
    -106, -94, 4, 3, -11, 64, 96, -39, -113, -111, -13, -40, -103, 66, 102, -20,
    -59, 77, 75, 2, -55, -125, -60, -84, -45, -25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1445201358000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAOV7e6wk6XVXz+zu7MOb3fX6Qbx41zf2YM2411PdXdVV1R4M" +
                                                                "rqp+1Kurq7uqurrLOON6dlXXs+vd5SyEBGxjS04waxMLHEFkSDBLghAGCWQR" +
                                                                "IcfYJIqAIJT8AbEsUIIcI0WRAYlAqOq+d+7MndlJvKwhES3VV3W/x/nO951z" +
                                                                "fudUfee+8u3WI0nceqelao53I91HZnJjrGoUy6txYhqEpyaJWNfe0t/wMPXZ" +
                                                                "3/xp44XLrcts60ldDcLA0VXvVpCkrafYrZqrQGCmgLSgbn6w9bjeDCTVxE5b" +
                                                                "lz+Il3HrJAq9/cYL09NJ7qH/mTbw8l/9wWf+wUOtp5XW004gpGrq6EQYpGaZ" +
                                                                "Kq0nfdPXzDjBDMM0lNYbA9M0BDN2VM+p6o5hoLSeTZxNoKZZbCYLMwm9vOn4" +
                                                                "bJJFZnyY86yyYT+s2Y4zPQ3jmv1njuxnqeMBrJOkN9nWFcsxPSPZtf5s62G2" +
                                                                "9YjlqZu641vZs1UAB4rAuKmvuz/h1GzGlqqbZ0Medp3ASFvvuDji9oqvMnWH" +
                                                                "euijvpna4e2pHg7UuqL17JElTw02gJDGTrCpuz4SZvUsaeu5VyVad3osUnVX" +
                                                                "3Zi30tb3X+zHH5vqXo8ftqUZkrbecrHbgVIts+cuyOwOaX2b+5Of+khABpdb" +
                                                                "l2qeDVP3Gv4fqwe9cGHQwrTM2Ax08zjwyfewn1Xf+uWPX2616s5vudD52Ocf" +
                                                                "/9Bvf+DFF37+a8c+f/w+fWba1tTTW/oXtKf+9duJ64OHGjYei8LEaVThrpUf" +
                                                                "pMqfttwso1rb33qbYtN446zx5xdfXf/wF81vXW49QbWu6KGX+bVWvVEP/cjx" +
                                                                "zHhiBmaspqZBtR43A4M4tFOtR+tn1gnMY+3MshIzpVoPe4eqK+Hh73qLrJpE" +
                                                                "s0WP1s9OYIVnz5Ga2ofnMmq1Wo/WV+vN9fVQfaWn903akgE79E3ASWzTsoBh" +
                                                                "HEZaWALDUM98M0hrBQjjwPQ8QI08YOOkQG3OsaMDZqn6kVdLtLYS3dRCDUhi" +
                                                                "HZiaSVJrwY26U/S9I102q3qmuHSp3vB36KFhampSS+9Uk3Deq42FDD3DjG/p" +
                                                                "3qe+TLXe9OXPHbTp8cYCklqLD/t1qdaAt1/EjjvHvpzho9/+2Vu/eNTEZuzp" +
                                                                "dqatR0/ZqTl4sjGnGzVA3agB6pVL5Q3iJ6m/e9CaK8nBvG4Perzm/X1eWENb" +
                                                                "2bp06cD+mw+DD7pSS9qtEaQGiSevCx+iP/zxd9YSKqPi4VpOTderF03mHGio" +
                                                                "+kmt7eCW/vTHfvO//txnXwrPjSdtXb3Hpu8d2djkOy/uRRzqplFj3jn595yo" +
                                                                "X7r15ZeuXm7w5PEa6lK1VsYaN164OMddtnnzDOearXiEbb3BCmNf9ZqmM3B6" +
                                                                "IrXjsDivOcj4qcPzG3+v/l2qr//VXI0aNxWtgxo/S5ya0MltG0pb3/zmJ77w" +
                                                                "zY9++n33tp1c+32bijB2zfhqVEtOdyLV+26pRE3D66n4av1rtP59PfBFEHkv" +
                                                                "1Lle/6KjDTQadEFqB7/xfiH6/K/+8n8GDx71zMU8fYcvEsz05h2w1hB7+gBg" +
                                                                "bzxXSDE2zbrfv/8J/q985tsf++BBG+se77rfhFebstkOtd6GMP6LX9v92q//" +
                                                                "hy/828vnGpy2rkSZVi/swPm7akLvPp+qRjyvRt2ak+SqFPih4ViOqnlmYw2/" +
                                                                "+/Sf6H7ptz71zFGlvbrmqCBx68Xfn8B5/dvw1g//4g/+txcOZC7pjcc9347z" +
                                                                "bkcYf9M5ZSyO1X3DR/nn/83zn/sX6udr065BOHEq84Crl04NtGHqLbUDPcry" +
                                                                "RuNebySmnsVOur/BqprpHbq8LW092Qgz8tS0sYLysB3goe3Goew1mn0g2jq0" +
                                                                "DZrineWh7e2H+ivJvb5w3AQV54aqAK/89eeIP/WtI+zdNtSGxg/cB/aW6h0Y" +
                                                                "0vui/53L77zyC5dbjyqtZw7xjBqkS9XLGnVQ6ogkIU4r2db33dV+d3RxdKU3" +
                                                                "bwPR2y+CxB3TXoSIc7itn5vezfMTR1Q4KFB5qRU1D3/6MOLdh/J6U7x42KPL" +
                                                                "aQO/TZyX1oSdoMbcw7Aauk8t/Exibz6V2LH6hny4NW3PHU2sKW8egaim+0Qj" +
                                                                "l7fX1yP19fnT+yeb1jcd2HnzGV+T+/NV28GjNbbkNViUt4lebog+fkrsE6f3" +
                                                                "H72DaNrEckeHc8r1MwcNPWjZMYi7v3bdsYTTXasZeKRzo3ej1/y9uD+TDzWP" +
                                                                "720KvCmIs617buvpV89Ab1mHzLXBXD1uX9ODu89stao+dc4qG9bR5if/44//" +
                                                                "0o+969dr1aRbj+SN2tQaecd6uKwJxz/6ymeef8PL3/jkAULqfeN/6B+98vcb" +
                                                                "qnJTiGnr+YYZIcxi3WTVJJ0ebN40Tvm5j4nwsePXaJefhovmx1/+xO/d+NTL" +
                                                                "R1w5xtTvuiesvXPMMa4+bMb3HddXz/IDD5rlMGL8Gz/30j/9mZc+dow5n707" +
                                                                "QhwFmf/3/t3//KUbP/GNr98nznjYC48BxEUhvuVrJJRQ2NmPXapEf7HsrgJz" +
                                                                "UFE87q7Xtj/euFNzPBGkTUX1NiEFhVNiuldoXOEZYQbxq2lm5bNgRuo6Cut6" +
                                                                "u1/MZbe72I7XgpRiitCx/XDJckNBiD1WXOzKsrsmeH+nTYRpsJeUrJdifq+X" +
                                                                "bicLnweAARBYOjot9oKSylquA1plgQiQA2Z/gbZLPUldUfSjXld0lSBDChX0" +
                                                                "PUsUGMWVK0Tduf4y3czUqcUgGQ6Z7KDf6zOJNzE0fCcoal9ZrXY7h87Tgd/Z" +
                                                                "biHPg7VIXa52fX8HLw1jH8b6eJFkQuRnDMu4SqWxSkZBbVXCGXVQzrRutRrm" +
                                                                "PZEjAp6VZoK5SMCM1jx/VxaToW0OiWrtRJFZtXtymPh+0VnPAjVbBgQmDFI3" +
                                                                "U/nKRQ1zvx2UTk6n7njZTdIJnXaJIiqrdO0thcIEl2KyJmU/IRxyHXkF0N8q" +
                                                                "CzCcOstxAnfbg9BMcopfMhxWu6e10xP3SCcVKy7Kl2sdn/BqlEVgsBDtTGBM" +
                                                                "yQi2KS3GuIBrMuxARU8ku9h+xYrKlmNpTowG5GozQRhZjwywTeSLgkfYTp6Y" +
                                                                "+Q70q45ZojWPOcOk6X6puRIpcOlQLNW44jmAE+W0zyVZtZL3RBl4PV7Ul8Jy" +
                                                                "2StGuAH3mK6XMlthpeF9bg1rWbDDMDhJ5+HEpWwkC3vh1KbpuaZ0Oz05oHZG" +
                                                                "wZlZ2zMNaTc3peWszS40AhylKT72OSzgqulmqRmMvNTGQo9Nwu0UJe2kbVPT" +
                                                                "DeMvzTkCo/F6NweyQlbBeUBEEW8G3XTnhbxIdFYEsGZmRLTZI+om3M37TFcY" +
                                                                "osBaG0WdFWOFG2+8pDFHX8cc3ZEnnZFvR6q7YOfzdh82woHRc+KUWLDYtKuw" +
                                                                "u5SyquXUtydKSiFBhnlylaxGcOAaw07Qpdt9frpRh3RHmjgVyYN5YCe5W622" +
                                                                "G6/0Cn3aU1KjbK+QdYlmZK5vYbVbxV14TFNlbRAprkgWFO2n+YCPVKF+KcbI" +
                                                                "LhjG655Z9vJ4mOGIpQ9EETAVhlNlfZnsIBn1cXLUmw+0/WbH9QB3srUWCyNS" +
                                                                "xrUiMIAMJp0IQjysw3rxqh9QcjpIFV+KuLG4G2wge59gAqMSBcp0o5nRR7m9" +
                                                                "CXt9mMUnTDayumsSgPkNCGo7MkpTIbd2ptKX4zFs7uGVJnJg2A3ziPM6WX/o" +
                                                                "QlOXmO9W46UoZRM/6y8Qdo7NLLyWXTsMx2RB9D0QHnSjfV8U1WLIFdhOXSvD" +
                                                                "st3NYGdcLTUpceczrFJEkM32EpvOjHUnjGZjpfQ8FSIYf+Eaeps2tlpZYpN9" +
                                                                "5IQ8tZqM6fEWSwlbxWdWxsB7CNK5XOlCSy+0K3JFqjFH2fjeCReBscNtO8Pt" +
                                                                "Tdt3Nr207O+AUlHE9XKMZEtut2HVuDvuYjzmCHuBAoY7VWTXhJ9CjI+N1/K2" +
                                                                "CwKKtkwHKDCpFhLHqsuN0i/gWTFXYgsuOHo2IbKhoQ3KfEUigZPk+Vaj93GC" +
                                                                "mXCnF0RTZpeCSuZSuUrhC2OJiLMNUkSLcVhuaDASSJGkU44NxzqmSUVlzVeF" +
                                                                "7HPUtO3vxgDpil67OwgA01TtQItzSFRiZjMxO5FLkexsS2y5ri/ruJODtQxl" +
                                                                "F+q74qyfCTFWSsO4UgrcJ3pQFZLOCFNXJD4h/QjLlX0W+avlrNxHIjtBtquu" +
                                                                "toLYiUylvIQS7FK2nUqC81x1pBLAwcSdeN5sBKmsSnWLeY8YGFpZoIC1A9jO" +
                                                                "rvYRhCTNSaiwTBofzkVryitrkprpFpguqh2Q54Aeezmt+vhoPShMcen0sKzE" +
                                                                "NtiKBoOslgdgWaZVbPuzdmS4c2ueGxDpjbcw6qLblQqZBh2BEFrgVmLsPH6I" +
                                                                "EivCxpj+OOImiIwG3nLvpEKqb8VURA1Lz4Ny1d5HIzVahZJEdXyxqpA2Bg7g" +
                                                                "YpOCXdgHgRk0kwV9s+bzbqfEUX9K+k4O0PtOX+hRJTjOgIHdDidixyULqYNL" +
                                                                "wiDACWEuBrHBDeIeN6TLYDHH+1CyXWiIIfGANGJUnyExaj+cMc5oIYyngwrU" +
                                                                "VTEjFGhNw9g6qmKnvc8C3l53hgMb3MVozs4hd1Bi+MQKqIXQHpg+wuw9zu12" +
                                                                "PCUxgo60s2gzROajZJ4kXI/A5IkScKHNbf3Negopskn7jLnIXZjvzKaCpEme" +
                                                                "x3diLo9kaqLknucXO9vPnIzueRjlDKHYQVZrOY0LZWyOfJ2RfZlwx3xtyBM4" +
                                                                "3tsq7aznm5KifU+fjbBFZXS2YclVBbRH+9TYiFIYMquJqqUbjkYtui20h3qs" +
                                                                "KDTIGDW4b5f9sViObaaC5VEJ624C6c6SCPM5DiNY6OnwJjeYkWyKUmC0Nzhv" +
                                                                "yAN+t+JKyJQ3eSejJNoE+55Cd8A5UvjjSRwRS6htCZk+GE22VMUzhjtgl5ZW" +
                                                                "ey4dXYKAg0Qmr7kqh6/ppT/YYETQ1Ux4EKMDDVLW3Bqbi/nASgc9BIDJnA+y" +
                                                                "sUPkS4bRYq8iYaoctwtdlTrrbtyBGZVGRwhsA1ViiDA45VltT5E5tRUMw9hp" +
                                                                "3HpO4L5RqJDcy9aw2OVEfZZMFh3eMswoKPo7ldUWQxkcUrVTc5lFUJXKXLTH" +
                                                                "I3+0CITUHTWhDITUQVLdAgQTgRqVoTPOVuQWlXK+And2ZeS8Y+s8jgHRRoQK" +
                                                                "G9MFqmcEZDVZ03vWGxY+NHcguaOYbVJsQ20xcSJTiHHW4fquTtL9zEvnBBYU" +
                                                                "qwCTk+VOX0Q4shu2rS4Veilhiryw52tnt+EUyWG2XadNt0eA39bqoFaHDWES" +
                                                                "kqgWrhcc50uWZxGVgDPCFuUnLOcohDlV95v5Yrxpl6TGY32AWG2AyoK2VWpj" +
                                                                "MJ+krLbcr0tx5UfJFOAH4Nqy8h6aK+vVCiD64r5aG5jdt0p4lRBxRxuhZTx2" +
                                                                "emre2y42TiLPU0aWGFBwO2awM+c2haEQXQzaAIIYQVWTEkzH3xLjMqJqFSJm" +
                                                                "6wKVKHa43qQbQIVtQ51qTA07fh8wZwhXeDxb9CzOgeZC7gLtIggQUAamNsKi" +
                                                                "phvP5J0Pb5e21kO6YzwAQN6Lc7Xa7Ulqq1Jo4knBnFuP3PFYzEolm+zS9ZyB" +
                                                                "fKbQwnFl2ttVut6EEoghQFRg1ZoDQs4Cq32sS2SVQZ4bdJxJbzqz6IkC+2RX" +
                                                                "mep5igCZHRIctMSQXjGRENQc98J5sRquTB3noYx2ZXDNjlU7KsYLbSXNElX1" +
                                                                "nUKe92x2GBlJZU8l1Oj4XXlLLlxsYduSOu3vWT6O9LnPye7WW++EpWEv9SGO" +
                                                                "SUXb3fQ2PpGzG8ktV+LGFOFtTw22yjJFynGyVSYDf8/VDmSOjDN0Yab7OMpc" +
                                                                "T171ELANYhyuVNh6HCcZO3WHjC5RrjGDCBU34bFXmAiyk1fzwXxCLkJpKQwL" +
                                                                "J9MtoV/1YTHNCS5NtzOmy6G1Y6RmIhnA2cBLEQ/J4Xw+Ls2OIuEzBwRxQVLa" +
                                                                "Aj+MQBuGEbSPzRaLWa6lM6frmmNVNXbVEinLqe8uwZmUhZM+4yh7o7/QgTa3" +
                                                                "qfoDJFqNtMkkscsiIfFMCRmC5WQC51kTpYPZum32Nz0YUiaSK7djAMjZqPbr" +
                                                                "+645ZCTanft1JLfTzdiq+LaCogu/RPylsqU5fk9v9oomT/oiVxvYqktJ0sjZ" +
                                                                "IDuVnwUp2UdVmV/iOaltFW8PJDOkn3SnKhJgztRHoLFsrOIZzC+ttS8h44Gi" +
                                                                "muqsi6A+GnXjQpDERTgayfvtRmIsmUNSpkfwHqyM+6xHpL3OgOwPd51kXVlC" +
                                                                "zwLMTb7CZmi3zzPgkBi6im8vJ4A5KOMewAtZakLqqpQTywExOGoz0Y5e0KNK" +
                                                                "FXsDUAq8LT3qbcstuleZJVmO6IlKkxQMcQ48GtmrZCpqlr3sEgGwc0ifDhIP" +
                                                                "9GK4x+YDrwf08hzx0MyiRrrKmL0qpx18sZ3QbYVV0XXbB5C8y1nldOyTk8Eo" +
                                                                "nIsEuYBKPeI2TpxX8loMt7WDBgJiMRGqOBcXC9jsrXKj0ucrt7+aQmYHcrWw" +
                                                                "LEf9/r49BMIuBoxKqhPgdbU7mgz5NZmj+rbM9EjRWEMES27N0XhP6m9ot+IX" +
                                                                "vjbKt3ahtnfLAmTSLh4gxq5GZH3EcHMIWIRRx8lVnlqo/nDNt9d4UeAgLPBj" +
                                                                "VTNpFDHjcDCbACAnGUBotmlKWG3BnKeQAenoXVuaVjrnbHR9CSwlCJat2U5G" +
                                                                "eR2XilWxEzu1DaxULennxGpQgkwcgI5eRyLUysySYLnd6CGZ4dF6l+wZy7GV" +
                                                                "OWBFuS+lprZPa71mgVXaNgAjpekY2iGyMlJVpxzuu/1sy3LdugqWcgGFUzlx" +
                                                                "wU1OuDAu4z1yOYSHi0K1bJjoteeYQgmeSvtBoXJ1DLuaLmpgjfBiv2sb7WwW" +
                                                                "4nkX3bM9ktDS4aafL6n5RAzYHVziXTXH3Araa5KdgnbfJw18YqPyXikme6wd" +
                                                                "M3NmYa9R3lq35yM88WF7tubHA2oBJSt7z6yEcjaLyg0qspwJSZSVr4iukww0" +
                                                                "H5FSP5zTqFwqFrgOoTrodQoIEXeEXjJ2vzIn+/HQKjJqPOPWNJCTqahrDprK" +
                                                                "QnsGJggsJIbpWeS4QnaAyWfzhU4Dm/08oZBRz+/0DAfjHArzp4yhVL5q+N0u" +
                                                                "FG4wlO1sMI6uVoON4PdBT0GXSmhPAyvQ2mhHmvIQxI1waZQLK3kzXQLuaDWK" +
                                                                "NsF6YIeygnS6lrtmJR2I5+1tj5y5sjPgJbbwtXBiUXqSTAxixa4TyF+L0lxN" +
                                                                "dUTfeXs4HW2lhCM7otMh0b6MrUnOMfkiEkYQQK9qpVtbJYkP2wPaLFKQGA03" +
                                                                "BuBnWFDCwSoY2CPKFrtbr1u2sfmEET0UJZGRv2CAnllNRVBf2TNtsRT6wpzM" +
                                                                "sqE/g6kavSNHFLIhJvd7sLoX1bp1peBkwPUhpaLGGVi/PMEptNDEtl+SW2dM" +
                                                                "hTxhjvobVOjMaJwb5Usa7E1AfIQjxIAIh0S21ziei40yQV3Ips2RtwaY+pU7" +
                                                                "9ZAdxiYioMiLKBllHCkOrVns8jW1aaYOu6ilTOsQkoVHxBoZQWnJxUlYEkNu" +
                                                                "O0/qTcQno/FInawZjttltOBN9RhbBCUn6GgJZ9aQnKKdQW1ODBlzjI8D3Ui1" +
                                                                "0CTbL1GIqCNNm+MoL3Rj256l1DZAhQgAKq8sxp0lafclztZWbkKRzl6mzbqo" +
                                                                "FzeiS2U17KtZb1KxWr8rDmWkY9VvUotxoXtFd4NKwBCHArnjEnyptSHZ8/rW" +
                                                                "rrvRxbWQ1dPYpLno0CDm91cjtaY53HtZA8YLn/JYqfZ0HYEu8WjP7Euhh3dW" +
                                                                "PbLqA7xV9LVpj97QvIPHdjAZqTjkYyW70/d5LKjjepupue1z/Y61U2kbQPCC" +
                                                                "RW2MR8rOYs+KaHuVSBbOA4C2iVGrcnUJY6OJOMxnVYRnWtzeDKxwgMxQY+4B" +
                                                                "oRx6WkHhTHuWACzQB0tzDHShlZ2DLt4eo+uJheSTIdmGBjDNIOP1aLPsdgBz" +
                                                                "F9JwkdFphxOctcB3MiPKTdIzxG21HwVxnzAT2tl1DCZMCtIdiWMUI908GwUb" +
                                                                "0UYRHl+B226iUvE+D1wKjFG5qrJyam/7C6y7kPgFQJrKWgl5To062tKK5jSL" +
                                                                "zCTPom2YcXQKlyQiLpc4RAGdcBzDOgPZZmdi+cDYQ8xBvo+ETg10NRV6juW9" +
                                                                "pONKgD7bK3lZMeSSG4AlJLNl1lf2I16YkZodGXMzHjP4mJth9XvEmsuiHbJM" +
                                                                "5F2moaYBV7tR1g4my62EQ/0JZfYFPigTZlP7mEUFe/KgfuUSlWGFDnoGDwg2" +
                                                                "M6PVatLuji11316lAJ20yRmNAh0/n+Jy");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("ms/wfNBdmpVR9FB5N55kQ1nDE2A6zjyzloBADWBUTu06ShDBQnTYLOnT3Eqd" +
                                                                  "repXnwHt9wc6YEJgHKGON8SCtuu63BQEVtEwnCFELwTYigewtplIMBf68dYf" +
                                                                  "02NYr7Y8Sm7IYR+hpAzDsPc3n3T102/abz58YL+dx/HAT+vHYwbrVY4/mkfm" +
                                                                  "ro/337d1rKunx9a3DjkV0IHo8Rji3U3xntsnEoffldMsAev0/uE7TiTuOI5q" +
                                                                  "NZ/Bn3+1hI7DJ/Av/MjLP2nM/lb38umZ1ofT1uNpGL3XM/PTc7EjqcebD+r3" +
                                                                  "JAxND2ks5wdU3/jW8wPC/U+b4wf1d1yY+WLvvzN95euTd+ufvtx66PZJ1D25" +
                                                                  "M3cPunn3+dMTsZlmcSDedQp1cnuvmoOb1rtOUyz++en9Z+48EjoX3oVtPhz8" +
                                                                  "PHY65KdP73/z4jafnwueHjYeNOJAdf+Ag8OPNEWath47FfrVczZ2dzPfpIU8" +
                                                                  "WdN99Hhv/e53x3wz5H+c3r/zXTD/Iw9g/i80xUtp60oWGWp6PPb6M6eHLs3t" +
                                                                  "Vtp6OA8d437Leb6+nqln2pzehe9uOc2QxemdffXl3Mntpx7Q9uNN8ZfS1hMb" +
                                                                  "Mz2VxGEL7sf5W5oNOrW++wuiKf7chdkuH03+7GjwhfseQfNnCQ2vflB4lMpn" +
                                                                  "HrCWv9YUn66x5CiVW7Hph+lhOR+933Ledsdy7i+Ipvjkq+vJZw+9/sYDOPqp" +
                                                                  "pvh82nrj+e7ewdXFTW7Ee3g44+pwTy9d+97lg3zXVA7JH69rBskwzDTPXJiq" +
                                                                  "gYfaIZUEBF8EwfdCvSaV5H0nu0xNnF1W79m106Ppk8a0ThqH4QROeu36yUdO" +
                                                                  "Pvgh4eSl61F00YDou33Goxc3t2n921H0ABF+8QFtrzTFF2oMO+Plfrr21JnR" +
                                                                  "3y3VK53/JwlAf+gFfrfEj0kSJ8fcnBMtDD1TDQ6iP/OGoXXtg4cMipPjpB9R" +
                                                                  "fe2lA7ocn84g5vjXIdXl8Hgg4r148qCxxxSRY/fwQ+Na0Rzr5Fp48v73nwSZ" +
                                                                  "510/ObreerSXmDdfPbdGas7SVV2vEYCwTd299gdmtRl5/HMbOsG1Y/rHsaKG" +
                                                                  "lOOf1643z2yTuSekYWxeu/7icXHHfr5ppn/wga+BszQ8VHxP54hrbTFjPqy1" +
                                                                  "YP/6TnTb89y5oOh27etG5rWtu6hb/oivuyaUxpn52tbvOVYq/l/Qr5pnKkjN" +
                                                                  "zZHf8FB5/ebR2p3bSHNy6sMbj3P6eKKfvP/k2ll9ePMcEh4IBuYuq12ZV4On" +
                                                                  "GF7Tb9z18vPiwXiv33zpLnS56N3u+051zIo6zWG8bxh2eLM6i8WevZPLI9g9" +
                                                                  "IA3wHz7AE/6TpvjZtPXU3dB8v8D40VMUv9NNnqXMfv+ZezyraFqvvn4Lb4ov" +
                                                                  "HXp95QGL+WpT/LMmi61ejK4m6dU7IuOLryiHzPVn73HvD//Ka3LM/197/gvB" +
                                                                  "3tHnH9Xy5BBeHRNVDwFfajvJjavHQP9QXZvhoe4uU7p5ctrx6HyPOFr3fK3+" +
                                                                  "90isBpzbWHPvnEeHe2/P/wPP+roQu8uFvjaKf4R85R/yBb5uTvF10rNz73dD" +
                                                                  "DwPrdBOv3zx1Qc0s93iguz9QHH5X7vd+9S8f+H71yw9o+1dN8bW09YY7rP8I" +
                                                                  "4ef/4xJF56D+XBY3/3X3yu/8sf9+5THxG6fJua2TN3zgK//lq4PP/tTDD33n" +
                                                                  "Ax/+tR/7y7/zq5/Drd/6hSlz+es/+pVXfuU3/jdIvQsfDTgAAA==");
}
