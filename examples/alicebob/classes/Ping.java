import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;

interface Ping extends fabric.lang.Object {
    
    public int m1();
    
    public int m2(final int x);
    
    public Ping Ping$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1444938114000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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" +
       "DFszDUiHoDSM4K9eArw8yCIX2ntC487heBV0DmCcLrDrF0G1/wCO+0KhH1IA" +
       "D1KVy6n0rs9eacYouAqPtWx3sLhhnq2p2fuK7k3z/EKVMWG+ChPKzNqxkaEX" +
       "8E40Xdgx7Z0jB5wde1ggc3Gde8rxpwveXPyd7dSEnlLKu5lCNeo5HGKnUpW5" +
       "1kQPpUIK78WCp0asfTr1eaSq6KUIV0xiHCNXUK3FgpKGgqiV6yWb9ayT6NP3" +
       "3n+yRY9x6pzYLA/GpmfaYAnojdJCyxefbi7XG2wynOVw77DrUa9NOA5vtoSj" +
       "bgHcxiw4LYDzR4uIIKuCQrGYPGEJYmYfehHnSpHKsYsdekkPbVz7dc36jRRm" +
       "qQ1GZR3OeWmYITDycDJwnpH5ZkGKWR/tW/n8npVr7P2SeEOJ4/d2FVyew6WQ" +
       "Q9Ufi9c0np1DgKEA8btewwSHWBIwArsaVX9Xb9l0gonOCs2TcDLi5CVrfVvf" +
       "YmhW49g97oa+2r+TZnftYJqAyxMWV0A2Cy6kvvGjD6AzN4JBdIkN1Vno89wl" +
       "0Qea/SjzAVfA/dky+7sWO4IMBV6VTAAG/AWRIGj9Gsev81NVsNHFYVQ49BGP" +
       "F6PCn7cVDi1Nirr8ED6R/JEV9nyMXc3L27aAxGBXJfe2xbMzMnh+wOEyFbgs" +
       "YNSH2XVXACuHB/PXGUY7Kx/3XPXSq+eGzjqG5WNElKESzTqjSki5NvW0rkuG" +
       "N7mIdMlwtxnXfYsj5rQwMX/qFdPiBJg3pRl6h8xqwaiWjNLzqahgtONhRjQD" +
       "nfSkP5qqjV7bBpNLiajQpnVJ0bbl0RUIJWdWb2jT2uqi7OGH5GElnl2M9u8b" +
       "9YKqalZWvVckygfOxZIX7PCfRX2wC5oXqE2heTGfjaA5Rp3nODSvId3ruN43" +
       "L49f9jaySF2qkkqDouKC3nvT/3R4wglbWgZ0eP9ungznFDT/ChlOLdyd5Liw" +
       "M46s6ofNzTxoYOzj7fP/98zP7clvoWtitdQb9HI60EnghsEL1Qwq5T+6n7fc" +
       "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" +
       "Ut69fADaSv3/AWzS076nNAAA");
    
    public int m1_remote(final fabric.lang.security.Principal worker$principal);
    
    public int m2_remote(final fabric.lang.security.Principal worker$principal,
                         final int x);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Principal get$jif$Ping_alice();
    
    public fabric.lang.security.Principal get$jif$Ping_bob();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements Ping
    {
        
        public fabric.lang.security.Principal get$jif$Ping_alice() {
            return ((Ping._Impl) fetch()).get$jif$Ping_alice();
        }
        
        public fabric.lang.security.Principal get$jif$Ping_bob() {
            return ((Ping._Impl) fetch()).get$jif$Ping_bob();
        }
        
        public int m1() { return ((Ping) fetch()).m1(); }
        
        public int m2(int arg1) { return ((Ping) fetch()).m2(arg1); }
        
        public Ping Ping$() { return ((Ping) fetch()).Ping$(); }
        
        public int m1_remote(fabric.lang.security.Principal arg1) {
            return ((Ping) fetch()).m1_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes2 = null;
        
        public int m1$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return m1();
            else
                try {
                    return (java.lang.Integer)
                             $remoteWorker.issueRemoteCall(this, "m1",
                                                           $paramTypes2, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public int m2_remote(fabric.lang.security.Principal arg1, int arg2) {
            return ((Ping) fetch()).m2_remote(arg1, arg2);
        }
        
        public static final java.lang.Class[] $paramTypes3 = { int.class };
        
        public int m2$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                return m2(arg2);
            else
                try {
                    return (java.lang.Integer)
                             $remoteWorker.issueRemoteCall(
                                             this, "m2", $paramTypes3,
                                             new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void jif$invokeDefConstructor() {
            ((Ping) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2, fabric.lang.Object arg3) {
            return Ping._Impl.jif$Instanceof(arg1, arg2, arg3);
        }
        
        public static Ping jif$cast$Ping(fabric.lang.security.Principal arg1,
                                         fabric.lang.security.Principal arg2,
                                         fabric.lang.Object arg3) {
            return Ping._Impl.jif$cast$Ping(arg1, arg2, arg3);
        }
        
        public _Proxy(Ping._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements Ping {
        
        public int m1() {
            try {
                final fabric.worker.remote.RemoteWorker w =
                  fabric.worker.Worker.getWorker().getWorker("bobnode");
                if (fabric.lang.security.PrincipalUtil._Impl.
                      actsFor(w.getPrincipal(), this.get$jif$Ping_bob())) {
                    int data = 1;
                    int y = 0;
                    final fabric.worker.Store alicestore =
                      fabric.worker.Worker.getWorker().getStore("alicenode");
                    final fabric.worker.Store bobstore =
                      fabric.worker.Worker.getWorker().getStore("bobnode");
                    final fabric.runtime.Runtime runtime =
                      fabric.runtime.Runtime._Impl.getRuntime(
                                                     this.get$jif$Ping_alice());
                    if (fabric.lang.security.PrincipalUtil._Impl.
                          actsFor(runtime.fetch().$getStore().getPrincipal(),
                                  this.get$jif$Ping_alice())) {
                        if (fabric.lang.security.PrincipalUtil._Impl.
                              actsFor(alicestore.getPrincipal(),
                                      this.get$jif$Ping_alice())) {
                            final fabric.lang.security.Label message_label =
                              fabric.lang.security.LabelUtil._Impl.
                              toLabel(
                                alicestore,
                                fabric.lang.security.LabelUtil._Impl.
                                  readerPolicy(
                                    alicestore,
                                    this.get$jif$Ping_alice(),
                                    fabric.lang.security.PrincipalUtil._Impl.
                                      topPrincipal()),
                                fabric.lang.security.LabelUtil._Impl.
                                  writerPolicy(
                                    alicestore,
                                    this.get$jif$Ping_alice(),
                                    fabric.lang.security.PrincipalUtil._Impl.
                                      topPrincipal()));
                            Message h =
                              (Message)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((Message)
                                     new Message._Impl(
                                     alicestore, message_label).$getProxy()).
                                    Message$("Hello World!  Fabric is coming for you..."));
                            fabric.util.Map root = alicestore.getRoot();
                            root.put(fabric.lang.WrappedJavaInlineable.$wrap("hello"),
                                     h);
                            runtime.out().println(h.getMessage()); } else { runtime.
                                                                              out(
                                                                                ).
                                                                              println(
                                                                                "store is not sufficiently trusted.");
                        } }
                    y = ((Ping._Proxy) this.$getProxy()).m2$remote(w, Ping._Static._Proxy.$instance.
                                                                     get$worker$(
                                                                       ).getPrincipal(
                                                                           ), data);
                    return y; }
                return 0; }
            catch (java.lang.NullPointerException exc$0) { throw new fabric.common.exceptions.ApplicationError(
                                                             exc$0); }
            catch (java.lang.SecurityException exc$0) { throw new fabric.common.exceptions.ApplicationError(
                                                          exc$0); } }
        
        public int m2(final int x) { try { fabric.runtime.Runtime._Impl.getRuntime(
                                                                          this.get$jif$Ping_alice(
                                                                                 )).
                                             out().println(x);
                                           final int answer = x + 1;
                                           try { final fabric.worker.Store bobstore =
                                                   fabric.worker.Worker.getWorker(
                                                                          ).getStore(
                                                                              "bobnode");
                                                 final fabric.lang.security.Principal alice_meet_bob =
                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                   disjunction(bobstore, this.get$jif$Ping_bob(
                                                                                ),
                                                               this.get$jif$Ping_alice(
                                                                      ));
                                                 final fabric.lang.security.Label message_label =
                                                   fabric.lang.security.LabelUtil._Impl.
                                                   toLabel(bobstore, fabric.lang.security.LabelUtil._Impl.
                                                             readerPolicy(bobstore,
                                                                          this.get$jif$Ping_bob(
                                                                                 ),
                                                                          fabric.lang.security.PrincipalUtil._Impl.
                                                                            topPrincipal(
                                                                              )),
                                                           fabric.lang.security.LabelUtil._Impl.
                                                             writerPolicy(bobstore,
                                                                          this.get$jif$Ping_alice(
                                                                                 ),
                                                                          alice_meet_bob).
                                                             join(bobstore, fabric.lang.security.LabelUtil._Impl.
                                                                    writerPolicy(
                                                                      bobstore, this.
                                                                        get$jif$Ping_bob(
                                                                          ), alice_meet_bob)));
                                                 final fabric.runtime.Runtime runtime =
                                                   fabric.runtime.Runtime._Impl.
                                                   getRuntime(this.get$jif$Ping_bob(
                                                                     ));
                                                 if (fabric.lang.security.PrincipalUtil._Impl.
                                                       actsFor(runtime.fetch().$getStore(
                                                                                 ).
                                                                 getPrincipal(),
                                                               alice_meet_bob) &&
                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                       equivalentTo(runtime.fetch(
                                                                              ).
                                                                      $getStore(
                                                                        ).getPrincipal(
                                                                            ), this.
                                                                      get$jif$Ping_bob(
                                                                        ))) { if (fabric.lang.security.PrincipalUtil._Impl.
                                                                                    actsFor(
                                                                                      bobstore.
                                                                                        getPrincipal(
                                                                                          ),
                                                                                      alice_meet_bob) &&
                                                                                    fabric.lang.security.PrincipalUtil._Impl.
                                                                                    actsFor(
                                                                                      this.
                                                                                        get$jif$Ping_bob(
                                                                                          ),
                                                                                      alice_meet_bob) &&
                                                                                    fabric.lang.security.PrincipalUtil._Impl.
                                                                                    actsFor(
                                                                                      this.
                                                                                        get$jif$Ping_alice(
                                                                                          ),
                                                                                      alice_meet_bob) &&
                                                                                    fabric.lang.security.PrincipalUtil._Impl.
                                                                                    equivalentTo(
                                                                                      bobstore.
                                                                                        getPrincipal(
                                                                                          ),
                                                                                      this.
                                                                                        get$jif$Ping_bob(
                                                                                          ))) {
                                                                                  Message h =
                                                                                    Message._Impl.
                                                                                    jif$cast$Message(
                                                                                      message_label,
                                                                                      bobstore.
                                                                                        getRoot(
                                                                                          ).
                                                                                        get(
                                                                                          fabric.lang.WrappedJavaInlineable.
                                                                                            $wrap(
                                                                                              "hello")));
                                                                                  if (fabric.lang.Object._Proxy.
                                                                                        idEquals(
                                                                                          h,
                                                                                          null))
                                                                                      h =
                                                                                        (Message)
                                                                                          fabric.lang.Object._Proxy.
                                                                                          $getProxy(
                                                                                            ((Message)
                                                                                               new Message.
                                                                                               _Impl(
                                                                                               bobstore,
                                                                                               message_label).
                                                                                               $getProxy(
                                                                                                 )).
                                                                                              Message$(
                                                                                                "Bob Hello World!  Fabric is coming for you..."));
                                                                                  fabric.util.Map root =
                                                                                    bobstore.
                                                                                    getRoot(
                                                                                      );
                                                                                  root.
                                                                                    put(
                                                                                      fabric.lang.WrappedJavaInlineable.
                                                                                        $wrap(
                                                                                          "hello"),
                                                                                      h);
                                                                                  h =
                                                                                    Message._Impl.
                                                                                      jif$cast$Message(
                                                                                        message_label,
                                                                                        bobstore.
                                                                                          getRoot(
                                                                                            ).
                                                                                          get(
                                                                                            fabric.lang.WrappedJavaInlineable.
                                                                                              $wrap(
                                                                                                "hello")));
                                                                                  final java.lang.String new_message =
                                                                                    h.
                                                                                    getMessage(
                                                                                      ) +
                                                                                  "!";
                                                                                  h.
                                                                                    update(
                                                                                      new_message);
                                                                                  runtime.
                                                                                    out(
                                                                                      ).
                                                                                    println(
                                                                                      h.
                                                                                        getMessage(
                                                                                          ));
                                                                              } else {
                                                                                  runtime.
                                                                                    out(
                                                                                      ).
                                                                                    println(
                                                                                      "bobstore is not sufficiently trusted.");
                                                                              } }
                                                 else { runtime.out().println("bobstore is not sufficiently trusted 2.");
                                                 } }
                                           catch (final java.lang.RuntimeException e) {
                                                }
                                           return answer; }
                                     catch (java.lang.SecurityException exc$1) {
                                         throw new fabric.common.exceptions.ApplicationError(
                                           exc$1); }
                                     catch (java.lang.NullPointerException exc$1) {
                                         throw new fabric.common.exceptions.ApplicationError(
                                           exc$1); } }
        
        public Ping Ping$() { ((Ping._Impl) this.fetch()).jif$init();
                              { this.fabric$lang$Object$(); }
                              return (Ping) this.$getProxy(); }
        
        public int m1_remote(final fabric.lang.security.Principal worker$principal) {
            if (fabric.lang.security.LabelUtil._Impl.relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                                  toLabel(fabric.worker.Worker.
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
                                                                              this.
                                                                                get$jif$Ping_alice(
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
                                                                              worker$principal)),
                                                                fabric.lang.security.LabelUtil._Impl.
                                                                  toLabel(fabric.worker.Worker.
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
                                                                              worker$principal),
                                                                          fabric.lang.security.LabelUtil._Impl.
                                                                            writerPolicy(
                                                                              fabric.worker.Worker.
                                                                                getWorker(
                                                                                  ).
                                                                                getLocalStore(
                                                                                  ),
                                                                              this.
                                                                                get$jif$Ping_alice(
                                                                                  ),
                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                topPrincipal(
                                                                                  )))))
                return this.m1(); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                         ); }
        
        public int m2_remote(final fabric.lang.security.Principal worker$principal,
                             final int x) { if (fabric.lang.security.LabelUtil._Impl.
                                                  relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                               toLabel(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         readerPolicy(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           this.
                                                                             get$jif$Ping_alice(
                                                                               ),
                                                                           this.
                                                                             get$jif$Ping_bob(
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
                                                                           worker$principal)),
                                                             fabric.lang.security.LabelUtil._Impl.
                                                               toLabel(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
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
                                                                           worker$principal),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         writerPolicy(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           this.
                                                                             get$jif$Ping_alice(
                                                                               ),
                                                                           fabric.lang.security.PrincipalUtil._Impl.
                                                                             topPrincipal(
                                                                               )))))
                                                return this.m2(x); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                                                          ); }
        
        public _Impl(fabric.worker.Store $location, final fabric.lang.security.Principal jif$alice,
                     final fabric.lang.security.Principal jif$bob) { super($location);
                                                                     this.jif$Ping_alice =
                                                                       jif$alice;
                                                                     this.jif$Ping_bob =
                                                                       jif$bob; }
        
        public void jif$invokeDefConstructor() { this.Ping$(); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.security.Principal jif$alice,
                                             final fabric.lang.security.Principal jif$bob,
                                             final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
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
                                                                           if (fabric.lang.Object._Proxy.
                                                                                 $getProxy(
                                                                                   (java.lang.Object)
                                                                                     fabric.lang.WrappedJavaInlineable.
                                                                                     $unwrap(
                                                                                       o)) instanceof Ping) {
                                                                               Ping c =
                                                                                 (Ping)
                                                                                   fabric.lang.Object._Proxy.
                                                                                   $getProxy(
                                                                                     o);
                                                                               boolean ok =
                                                                                 true;
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$Ping_alice(
                                                                                         ),
                                                                                     jif$alice);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$Ping_bob(
                                                                                         ),
                                                                                     jif$bob);
                                                                               return ok;
                                                                           }
                                                                           return false;
        }
        
        public static Ping jif$cast$Ping(final fabric.lang.security.Principal jif$alice,
                                         final fabric.lang.security.Principal jif$bob,
                                         final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                             idEquals(
                                                                               o,
                                                                               null))
                                                                           return null;
                                                                       if (Ping._Impl.
                                                                             jif$Instanceof(
                                                                               jif$alice,
                                                                               jif$bob,
                                                                               o))
                                                                           return (Ping)
                                                                                    fabric.lang.Object._Proxy.
                                                                                    $getProxy(
                                                                                      o);
                                                                       throw new java.lang.ClassCastException(
                                                                         ); }
        
        public fabric.lang.security.Principal get$jif$Ping_alice() { return this.
                                                                              jif$Ping_alice;
        }
        
        private fabric.lang.security.Principal jif$Ping_alice;
        
        public fabric.lang.security.Principal get$jif$Ping_bob() { return this.jif$Ping_bob;
        }
        
        private fabric.lang.security.Principal jif$Ping_bob;
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (Ping) this.$getProxy();
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new Ping._Proxy(
                                                             this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                            interStoreRefs);
                                           $writeRef($getStore(), this.jif$Ping_alice,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$Ping_bob,
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
                                               this.jif$Ping_alice = (fabric.lang.
                                                                       security.
                                                                       Principal)
                                                                       $readRef(
                                                                         fabric.
                                                                           lang.
                                                                           security.
                                                                           Principal.
                                                                           _Proxy.class,
                                                                         (fabric.
                                                                           common.
                                                                           RefTypeEnum)
                                                                           refTypes.
                                                                           next(
                                                                             ), in,
                                                                         store, intraStoreRefs,
                                                                         interStoreRefs);
                                               this.jif$Ping_bob = (fabric.lang.
                                                                     security.Principal)
                                                                     $readRef(fabric.
                                                                                lang.
                                                                                security.
                                                                                Principal.
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
                                                                        Ping._Impl src =
                                                                          (Ping.
                                                                            _Impl)
                                                                            other;
                                                                        this.jif$Ping_alice =
                                                                          src.jif$Ping_alice;
                                                                        this.jif$Ping_bob =
                                                                          src.jif$Ping_bob;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements Ping._Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((Ping._Static._Impl)
                                                                  fetch()).get$worker$(
                                                                             ); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((Ping.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((Ping._Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((Ping._Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(Ping._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final Ping._Static $instance;
            
            static { Ping._Static._Impl impl = (Ping._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                 $makeStaticInstance(Ping._Static.
                                                                       _Impl.class);
                     $instance = (Ping._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements Ping._Static {
            
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new Ping._Static.
                                                                 _Proxy(this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm66 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff67 = 1;
                                       $label62: for (boolean $commit63 = false;
                                                      !$commit63; ) { if ($backoff67 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff67);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e64) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff67 <
                                                                            5000)
                                                                          $backoff67 *=
                                                                            2;
                                                                      $commit63 =
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
                                                                               RetryException $e64) {
                                                                          $commit63 =
                                                                            false;
                                                                          continue $label62;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e64) {
                                                                          $commit63 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid65 =
                                                                            $tm66.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e64.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid65))
                                                                              continue $label62;
                                                                          if ($currentTid65.
                                                                                parent !=
                                                                                null)
                                                                              throw $e64;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e64) {
                                                                          $commit63 =
                                                                            false;
                                                                          if ($tm66.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label62;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e64);
                                                                      }
                                                                      finally { if ($commit63) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e64) {
                                                                                        $commit63 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e64) {
                                                                                        $commit63 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid65 =
                                                                                          $tm66.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid65 ==
                                                                                              null ||
                                                                                              $e64.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid65) &&
                                                                                              !$currentTid65.
                                                                                              equals(
                                                                                                $e64.
                                                                                                  tid))
                                                                                            continue $label62;
                                                                                        throw $e64;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit63) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -51, 72, 1, 99, 94, -15,
    -72, 124, -27, -36, -42, 91, -119, 109, 87, -54, 50, 35, 19, 21, -44, -33, 6,
    47, -31, 68, 33, 93, -74, 77, 83, -39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1444938114000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAANV7e9Aj2XWXZva9XnvX62cWr/3ZHrZmrPW0Wi2pJQ+GSN2t" +
                                                                "Vkstdavf3Yu99Fv9UL9b/XA2FaASG4cykKyNk0pMFWUeSRZCpQhQBFf5DxLy" +
                                                                "MKlAuQypAmwMqYRyTJVJ8fgDCN3SN69vZscEmz+sKt2+uveec8+955zfPf3d" +
                                                                "8732zc4jadJ5n6Vqjn89qyIzvT5XNYKk1SQ1DcRX05RrWl/W3/Aw8Znf+1vG" +
                                                                "uy93LpOdp3Q1CANHV/2XgzTrvIl01YMKBGYG8Axx46XOE3pLuFDTXda5/NKs" +
                                                                "TDpnUehXth9m55Pcw//TXeDVv/rRZ37hoc7TSudpJ2AzNXN0JAwys8yUzlN7" +
                                                                "c6+ZSTo1DNNQOm8OTNNgzcRRfaduBoaB0nk2dexAzfLETBkzDf1DO/DZNI/M" +
                                                                "5DjnzcZW/LARO8n1LEwa8Z85iZ9njg+QTprdIDuPWo7pG2nc+cHOw2TnEctX" +
                                                                "7Wbg28mbqwCOHIF5294Mf9JpxEwsVTdvkjzsOYGRdd5zkeLWiq+smgEN6WN7" +
                                                                "M9uFt6Z6OFCbhs6zJ5F8NbABNkucwG6GPhLmzSxZ57nXZdoMejxSdU+1zZez" +
                                                                "zjsvjqNPXc2oJ47b0pJknbddHHbk1OjsuQs6u0Nb39z8iU99LFgElzuXGpkN" +
                                                                "U/db+R9viN59gYgxLTMxA908ET71AfIz6tu/8InLnU4z+G0XBp/G/MMf+Nb3" +
                                                                "v/juL/7qacwfu88YSnNNPXtZ/7z2pn/xLuTa5KFWjMejMHVaU7hr5Uet0uc9" +
                                                                "N8qosfa33+LYdl6/2flF5lfkH/pZ8xuXO08SnUf10M/3jVW9WQ/3keObCW4G" +
                                                                "ZqJmpkF0njADAzn2E53HmjrpBOaplbKs1MyIzsP+senR8Pi72SKrYdFu0WNN" +
                                                                "3Qms8GY9UrPdsV5GnfPPk8337Z3O5V85f/6TrMMBu3BvAk66My0LQJMw0sIS" +
                                                                "QEM935tB1hhAmASm7wNq5AO2kwGNOyeODpiluo/8RqONl+imFmpAmugA3VjT" +
                                                                "9WZE9P+Jb9mu55ni0qVmq9+jh4apqWmjt3MbmtF+4yaL0DfM5GXd/9QXiM5b" +
                                                                "vvATRzt6orX9tLHf405danT/rouocSftq/kM+9bfffk3TjbY0p5vZNZ5uJWl" +
                                                                "mf6p1ouuN7h0vcGl1y6V15HPET93NJZH06NX3aJ4ohH8Q37YIFrZuXTpKPtb" +
                                                                "j8RHE2kU7DXA0WDDU9fYjyz/zCfe91Bjm1HxcKOiduiVi55yG1+IpqY25v+y" +
                                                                "/vTHf++//fxnXglv+0zWuXKPK99L2bri+y5uRBLqptFA3W32HzhTf/HlL7xy" +
                                                                "5XILI080CJepjQ02cPHui3Pc5ZI3bsJbuxWPkJ03WGGyV/226yYmPZntkrC4" +
                                                                "3XJU8JuO9Tf/YfO51Hz/d/ttrbdtaJ8NhiHnnnN2y3Wyzte//snPf/1HfuxD" +
                                                                "9/adXf22XUWYeGZyJWo0pzuR6v9RuURtx3fT5NXm05r8h/rQixD8wUHvWvOJ" +
                                                                "Tg7QWtAFrR2Piw+z0U//69/8T9DxIL15sjx9xxHEmtmNO9CsZfb0EbfefNsg" +
                                                                "ucQ0m3H/9rP0j3/6mx9/6WiNzYj332/CK23ZbofabEOY/PCvxr/91X/3+S9f" +
                                                                "vm3BWefRKNeahR0lf3/D6IXbUzVA5zdg20iSXuGDfWg4lqNqvtl6w/98+o+D" +
                                                                "v/j7n3rmZNJ+03IykKTz4rdncLv9+2adH/qNj/73dx/ZXNLbg/b2dtwedkLv" +
                                                                "t9zmPE0StWrlKP/sv3z+J/6Z+tONazfYmzq1eYTTy8flXT5a6tsaRzjp8np7" +
                                                                "ql5PTT1PnKxqXOncmo7Dvi/rPNUqNPLVrPWE444MjiV0HHD9WPZbEz9y7xz7" +
                                                                "Jm3xvvLY965j+6PpvWfhvA0qbnusArz2U88hf/IbJ/C75bEtj/feB/wE9Q4w" +
                                                                "6f/s/r9eft+jv3y585jSeeYYz6hBJqh+3tqF0kQkKXLeSHbeeFf/3dHF6Si9" +
                                                                "cQuR3nURLe6Y9iJW3Abdpt6ObutPnuDhaEnlpU7UVv7UkeKFY3mtLV48aSZr" +
                                                                "cbiN87KGsRM04HskyzqPnbv6TdW99Vx1p+br4vHR9j138rW2vHE+ZWPOj/Su" +
                                                                "96/329/4/Wd+qK1+sC1mbYHcnPc519ev3IQOoYk3G7O7cpr7pijPHO3vaEOn" +
                                                                "yOz17eaCXI1FvOk2MRk2Qd2P/se//KW/9P6vNhaw7DxyaLXTKP6OGTZ5G/X+" +
                                                                "yGuffv4Nr37tR48u2/grvX/kP/xWy5VuCyLrPN+KzYZ5opukmmbro4+Zxk3J" +
                                                                "77XExuj3DboczqMy8xOvfvIPr3/q1ZMfn0LX998TPd5Jcwpfj6t842l9zSzv" +
                                                                "fdAsR4r57/78K7/0t1/5+Cm0e/buQAwL8v3f+cr/+tL1z37t1+53qPvh6cC+" +
                                                                "qO53/NPFICWmNz+koKHKlgcVMR/t/D0+c4e4NSyVvYNPecHGbWXul9hc8Vh8" +
                                                                "IU4rYOVwJanu8yDeh2QcR3E0UhFm0CNAu1iL2BhZr0hMHmzlNUuZ+JZBZoq0" +
                                                                "lcYuqyy2gtbz1n0GxiJdlMFECFDrYAKCBurQtDKqOqli8yDllmUZQRcwunAi" +
                                                                "m4OmYCLdEJbhus+WpR/nbF/bkIsynNslx2bbxN57+XIDlmOjnvqQ4QvJBOIH" +
                                                                "20SllquZgPMcuVH4PlPmuihsqj7j1ZjnVj6yH3HYyOVB1gBEz8CdEI9jchqt" +
                                                                "JJTPEg/gsHhOOqs9OxcOfM9zTRRczX2ci23HBlky9pYOI/gIX6XI2NiTirfU" +
                                                                "lGmah9XMN2bL/bKqI5wTMLuqISIeDA36sFQGEwSe2MVOAtcJ6K/Beex4XsSq" +
                                                                "OYPttIUS7sQeJ1q70Z7TlpFn+bBTxkxVzjyQI+DcygcLD9TpYdBDVqGPDFV+" +
                                                                "jwvhSk61vspyjhEKtRwtcTbfIwN1K++znHF7DOdJslrTUcxkqj+stiiRap4q" +
                                                                "7pSDmiPK0FYFXKOFzPJtvu/KWQYtltAB6M2lzURM4m5KJlOMLdVl5PQcrsqk" +
                                                                "fbRkNWW5qz3bi7BtAoJdIVpvkUqZzhltJKEbjhdieO1vMqHgQ83LPGevJOxs" +
                                                                "S5M1yVAMsR3C/KDA1dAjhViV2W6wQnySTZfdUTTndwvFidZ4LuZupXgY0OP5" +
                                                                "Q+jBS3a2SvoOO2f7PDjVQoJHd0lCDxfrENnOY92TnUkQe2SBDtIkHCrcjOQn" +
                                                                "WTqtFFI0UKq/i1hwO6YRnaMOU4xYDQTckQY7GdqgTgMcAyfsRVuMnzm6TCHF" +
                                                                "cMqlBlnYS3GMceRheOATcOPHO4paNla2FDC0y3tLHmdnsaes2K1W6mHI+mAt" +
                                                                "ZHNbtizHRXSRK8L1zKZyzZ7UI4NO4CobW+F+laFlWh7EarUU/FoHDS1ILHKU" +
                                                                "WpPtFCd7ICpws5yGhRCmoHFtgrjQs20cXchwkaZKt2vkot+dqBON60klngvV" +
                                                                "Rlri0YrypHFMdsNIYhRPrrWlwFcEtm2Mi2BzWhocIixZHoxN6iQMdAiWEeIr" +
                                                                "rlDtbB+c4+6Am7P8EgFnjAYRh7gXbA6ZPo8L+rCukwVw4B1rsB6SB6LKy1GV" +
                                                                "E2KJOj2/FNl62wv3ctHHo8SI9oKDLmOFw2zfm3o4vnL8FbdBBJHM0pJ1Cc+w" +
                                                                "BT6JEBJLnG3EqRmpyqrslErjwRuRxQAbKmt9Npo5sGuqZIypK9HbhJg3cTZd" +
                                                                "A10roYhVYrkhJHs7H0rgrsyHU9mEwl1XoDY2sWg4T3JhFtAoq3k1faAgTulS" +
                                                                "B343xsRYIZwqxUTd7UXMbL7ZMhO1cEdwztcbi4fxoIvX/tKT/e1wjgjYeLVf" +
                                                                "McEULgBsXLmDrWPM5w6BS1hs29g8HBnpHBpO6pqf87MxD04a8Uts26jYm4MH" +
                                                                "OQ3crZMRwSKGJt0YgunuLs4ObjCdiHbg7RsI4T27wUB6wiH9DNvb6mjBguyU" +
                                                                "BYU1t+LJIgc90Us1eyfyOxJT1C3ODjhils39udfYs16srTynrINkKZmk7oe2" +
                                                                "CJbgPBt6fLE0hamYKaLj+MiUgMkVQDqh7swmpshAu+bEnlRGmKYuOc3Wib1A" +
                                                                "YT0m1yW+UdhtpEogUfHhepqIVT5JpkNU7Gr9cGaKW46SKYeRN8jULg5mb2Yl" +
                                                                "fawPjE1phTnQLlhZ0yniFvSWckMYSLCtR7EVBk+AmN7Qk2FPS/tQj10Qzny+" +
                                                                "00EGFXvd6Q6vFAb36EWejrv6IpKkLpFjQ8+zej2I15CVTNUoEKsARS+hnjSY" +
                                                                "NjuEJg57UHGTEEjeZlkyqavayEwFZPIGZrqkFByWwjjtySPfE6jUIzxGmQ+Z" +
                                                                "QzA9OGXGAkvA7qkYA/Nh6i1xJdbkWYPiBr1wJ8NxyCXq2EJoGF3jG6M/Iwsh" +
                                                                "DbFtb5gIzoTl/QjhUg8NCX0FjNxs1AtxGgRMF1OR7SKP6JlrdXt2rQN7rM4F" +
                                                                "8QAdwPlSI3cMb5sQzq0XvAJFUywflnucYIsFxZHNkH45UZ0i0KdCn5+su8Jo" +
                                                                "qe0WIVnWowkGhashVR64RaE1CAWWhI3AA68HQllzjCCzdA8oy1gvdWk2HGVO" +
                                                                "N65wtMZ0M/Qpzc7SCotG25HGaY6FzgVvClJOd+bZMhZVy1ogB5DoOhbJxf2B" +
                                                                "jR4Yil0xcaU1L/Z9xoD3HFGufTz2MXRAhLNMlSjCi+pebzfj+QrrMuz8QGij" +
                                                                "MamuNW9T9pCtotqFUs1SUsTUtdIEqwOTokYbWe6reZ9H5hsJ3/bgMJ6VCzyz" +
                                                                "YaABmpTUCC7ubeu1l/LDbUzAseyz5VqS8Z5ZYDlcFaW0WaGrxhGYJPJm4naD" +
                                                                "wBRNrjirC/AjA+AYXOeR3NuanptmujSHZaq/QBK4L8uKj6+woFomk3WPG4HW" +
                                                                "IoGA3NC3ypikVyHabATCon0nzYMMKIOMqYqhAYQCEpIChY60gutXrK9AxhZh" +
                                                                "XGImcVt2tW2CH8Fw6T5vTUZDeFJHw9yTsd2A3MIjUeCIYg4ttxaimZuDB4k9" +
                                                                "09yHogqHNj/vjk2AzMqhbOXdcN5ww5C664Jcv5cAe27s11nZl0eWu00Ge57a" +
                                                                "ghPTDnjYZ3S3xHU7iTYGtS3UCTAwXEmjAawJB2SGhUB7MNYDLUxn0jp10Z2E" +
                                                                "yolEDgQ1L2B2LoW4m6k9gEO0QaRJB0zGK7Bxnel6Bi7hrXtYbPSpsaMX4XoB" +
                                                                "USBSkiy071F7iDd7h7hxLhCpD8jQ9RuPyyyZQNl8XDmj4cDKDjAfH7y+W6oj" +
                                                                "EE0nbL/vS5yV4HJN5QAeFVsuRFTmkBZibfdreTlkILE2JxbY8wVNCbZ962BN" +
                                                                "oGKw0jaHuhzQOlSwNrbRIpBaQ+vRoF8YDThEKzKi3TXqym7cs8uIMAa1a404" +
                                                                "KSYgeoxOxT602IKL3dRSl0C0ngtDJGFSstYnOoov5GCYVxYTKdqwGsIauODi" +
                                                                "vaTnh7lWjwirISSbw+XALasd64+YwdApevZYZ3V4PjIYaowPJ2GMDIFQoidQ" +
                                                                "BWwUy7b8DKeQVdFdrUtOB3TzAPXX+XRB64Jd7UGR6Q6C+LAI7RUmBDMZRLiy" +
                                                                "2yt2uyCp7BlVFDurtx4mIRKAYxcwaX21D2KHVTEFHs5wm9wGRB3QyW4CszSd" +
                                                                "wpblm5q+whMCZMAVx22ArjOMOSzhMCgBAxjegBEPZGU+PWylJkoY0XNj0+NW" +
                                                                "VBnkudYFXJ5WpsvG+dMa5Z1FiLLrwWpiTpNiXCGE1A8yZDCGd+ZM6KF7RCcT" +
                                                                "NJn3cBAoEKrbW+0WIDFt4l6hGgW9JJ2Yqi6H+TSmM7GEUrJZVNyP+la+gj2o" +
                                                                "BrCVsBbxLrKjV/UmLj0Vk8bYoCcZdgkrPXc2LAwTtG0dwg1znJRRc4hIdGXA" +
                                                                "BosFPrnKhz6yMcPhAl8FfCQOQW9LRzyxgsciEvXU1OIS0FTZeLTiCaFXOgtk" +
                                                                "I/EraG5HK9yOHWFUidpErbjRhoagoFsx8qQcRbVMThtj3FuqPA1C11giVWla" +
                                                                "mgNLeL+oAkooYBhNeHWyoabDQxayrmYv9NIpBqSM1swgJ4hVjmwbq6ZxbCnv" +
                                                                "ZcvcLQI2WqxSOTVMz6M1oYYNHRq5DLOPtyCuMBBlinuZ4NBKR5dFEC2Gioev" +
                                                                "RAbIRCAYB5GQwHSfNTFqOQCM/lyozGIkbrcT3nImei+IQLQxnulYy8yiYBfi" +
                                                                "jCimSaoK0kTSJn7PDgMKSfucjpLOEBESSzhY8n4nDib2BA7Vbj/pckpgZfIy" +
                                                                "nlblWhR60z5SjbzDAXfR0bTeDJYzg/A2oBCE40nh97amCrBuPRWsKWB4VRdO" +
                                                                "RWpArGfCSOCmUxyYigyBuxM8NQ9jcCOkm8VcFraDyQyn5yMaCWBdQB1w4yTJ" +
                                                                "GENHDLDEhElQrSUdIvuwOi3xmbS0zH6pret1sOsWFFbNNTuVN+yiLwnSFMkX" +
                                                                "M4CWcF6GVyUASbTExl0Tr90YH4obzF9hbM3FLus5w81B7CVejm/gPWGs/SIr" +
                                                                "A8mSU3eYbIu1BMRzg3ZmSxuKGH+0G5dxoTqiuyHrWN3ZGbXZMe4WDSzWo4dx" +
                                                                "7AqwNos2pKO62ygQlj1N83sMTyxFQ9T6LtfPYwem8MUQx/zA768n+VibyKtY" +
                                                                "9RXxIOheZVtUqRASTCMWI/ULZBhKMdbfmRxuuep0yERQQKHsgTCgJN8MIHIw" +
                                                                "sSauWrHzfux5MRrmKwQKDhwAMYPC9jXRVnomPckDuw7mKAcgO58ijcoYkk6x" +
                                                                "HozSUWhtaq4Mm5DIhS0amIxrGtb9QyhmTmZstcmCGPWwwN6lGAlui3jIdUcB" +
                                                                "bOnTqA+t68nI9SFoRtF5WDSvP3N+ggPgGp/z4ox0MVScc/QuQ9ylblES529R" +
                                                                "U4SVHSBnOie5cb8xb1brmtgwmIdZ5muKPS9VzTVGkkvDDazVoaqIQ8fbTXag" +
                                                                "Bc6WQSGJjsDUfdNBZYEZ+ugW0NaRNWTRbkl2555dJYg06Osbyt1tx5YlWSsw" +
                                                                "WWs6aR1CySb9PWJiqxlvOP5iXw81INQyf3aY2OSeYC1VW69RL+a8fBfl6KTc" +
                                                                "z5o9Jw2JcdQlvHSChB0TvNxVFzkq9QG7xpjd2JgrBHOA0oFRQEVZutMeUMIy" +
                                                                "HswO+3Qm5oiQakWYkAMCk4QBktPIhEwa/F8Xg0QkJWWL0UM6xYK1uwT6i95k" +
                                                                "vArHgWvb5DQSulpZLOczc9m8sKpJMEb5nLFhkXQTcLhO0Iy2D2iZHdCJhKNL" +
                                                                "lzEPNcM5hW8GMqHYVho076DFkkqI/njpJvWIRHBPJ9VDzBAJWOS6XK26myoK" +
                                                                "9vRGQxhUWlOral1nkCUtgH2UOvqKM8IiXU2dpUpDln5AuXFCTkbwSF1BuxSQ" +
                                                                "CGPe24jkIskJvDv168lwCWt4LKcbza2rrhphxnZjwPWmT++5lC1m2RrD9MpX" +
                                                                "Jy4I5CgYDnCUUPihWPdGLtJE82qOLg9VqNTiJM20mc65uDHfG0FvsKK6oM3u" +
                                                                "mcalNIC3ZcFQefKAgbJAd1cbw+EtmpbQsN4VLJ8P/LJb7GE6koV8SY28YVBn" +
                                                                "Wuv0RS9YbLE1sJn6owPD6mjCONoU69Yjc464fLFporAw0miOWq+Sbb2sDuO+" +
                                                                "tF8S0FKaUXjgAMFWB0bEIkKUYt8dRQLZVXerAF2lOLWjgcCJwVkZiBskzeec" +
                                                                "Q21wjsIGXqUt+MTcDrprarcBTXOOmp6PK0uZ9Wog0gbbnrOVtQEEUSx/APor" +
                                                                "ZavFywML54o1R8shTRxEhyZUeDqMlAjZQWAo9GTGS41ltdf7ehAZwyb6TiVh" +
                                                                "hWhhb+StGe7A2VUJavpwKlJJ6R+MbpKKeZ/L3NDSZHjkD7vJyAk0xicEKY+o" +
                                                                "arcCyqlkdalNndco4W+WYFj7hYJOxwBHI2ot0wsF7wWHfUDJvZDktsEmUAch" +
                                                                "BJj8bJRHoyQfRVYMVov1cMfsQjA3wxG0NiIahXJOWxlDhEXKoZ5bBpdrgEVq" +
                                                                "QbklGGvQG/ZG2zWjLoeLNLSMIQPnoIvjpcXTedroV1KSGqegxcDSUL4vsPU+" +
                                                                "dMdwLlPNixyx5lViOddtpiFrbG9VTYi81vWUr6YA3zeWcOaPeXrhTfqUjYyo" +
                                                                "AQAOaGQOFUXzFptl002PncUzjbQn0wnYw21qBKzt6cRc81PTdtQ5yqjYTIIM" +
                                                                "Wd0NXCV0Y2TXvNA6rjfQ5pal6PUMJ3gDm2dGOdMN2zSKGup7csaExETkZHtb" +
                                                                "A3xipAajSKZezUN6vK4MZ0cxJutidE1xywHEQANmO8Ctcba3aQ3uJ5rVH1rm" +
                                                                "FJ/L5SqbSIirz8KsARshhMLFvrRq3DXscZc0GUyDlT1rDbg9WWw1eoHJohfI" +
                                                                "gxnbvKaM6kBYbOnCUhaByhu0RU8KwMLQ");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("FOAWaHfYW6U9oAkzCnJG75KUqDW1eWmZ6K5n2q4P5OGCpCiE02G6lBWLKqjF" +
                                                                  "zGApfg7UJs8zCigHMdqvA4M0Yc7sGvXMF2GeGoJyM0UTOVor1einfEpA0H44" +
                                                                  "ncKhR+blXFtY8pzviqM+tIGlmCkCVNr2ATISfH4ZxpYsjLH12LaUYO7DaJrR" +
                                                                  "C9Gme7QTFzG5Goj0DBLm6RQZr3f7UQrY3dq2pVQGgCGkDTbOcgPAG0yi60Pf" +
                                                                  "8zbMYLSh9LA7XsypqSquBZkCNpPUWqM234W09V6znF4V9GZAaIYOqZvVZC1m" +
                                                                  "CTYXNluGCex4zDKUFigHItq7QB+g3fEBN8HxqFA3gCCkYHuGx+q4CW9VLqOS" +
                                                                  "4gBrLm0Ahs7NtWa7ASoKza6eYPLwMLTCYQCRktFzHd4JdkBRkRZj7RUY4UU4" +
                                                                  "yKGJykPNvkDrWKgoBSH6YMN43GM9Dl72q61SrCfJyLK6PRbHqAWuSGBv2KXT" +
                                                                  "bpfJewujS+UmasE+uCIlbKPP6UQqPdgeJ8BylY2H+eoAw1xXxPwNGjLp1Khs" +
                                                                  "qbD5bDSxpqjKHqAmDKvZHjwKNtPptP1z/p8+v8946/Ea5laqzOkao+0j7/37" +
                                                                  "/+mG6eXXvWF6LEqcg5qZd93zvMl1rCttksDLx/vc4yXf67E2Xod1W9Xu4vrU" +
                                                                  "La5aqN3mWd6fQef8tvX2nWGnvUR5/vWybo4XKJ//c69+zqD+Bnj5nFzOOk9k" +
                                                                  "YfRB3zyY/h2snmyvY+7J6lofc41u3yJ+7RvPTxDvd+zTdcx7Lsx8cfTPrF/7" +
                                                                  "NfwF/ccudx66dV14T4LT3UQ37r4kfDIxszwJuLuuCs9OmQSN0E+0e/CO5vvO" +
                                                                  "TuehS6fn5T9oe99y1MRb77hVe6EtPnCL9HJL+vg5yX85f/7+HaQPuLwtHtBX" +
                                                                  "tUWSdS7vwWM/f37Z1T6krPOQE2THenj3InrN9/sbgX7y/Jn+0RbRkiTnT//1" +
                                                                  "F3HpdH3f/vzYkeuff8BKfrgtfrBdyfFi9GMXxO52zos25eh3bz7vEbstrj5g" +
                                                                  "lk8+oO8vtsXHs84jrYtcub0LF/aurVw6z4W6vwG0Rfb6e3G6rv8rD5Dkx9vi" +
                                                                  "U43r7MGXE3MfntDh4pbcI839NdkWP3Rhssu3IWJwWz2ffYBIP9kWn25F6j9A" +
                                                                  "pMfa8S/cIdKlU67Nf/4Ok2aOKNgMbnDru5ogczMn7EOjwYsg9MF+/9q1D53F" +
                                                                  "uZo6cd4s8eop7eTsEDrGWQueTnAIPRM1rTvyoK5eO/tYtnPS60ezuXrtxivX" +
                                                                  "ouj1nej4efTiFrW9n4uiByjgrz+g7/Nt8VNZ552vJ+P9sOHhdlUX9Pf4RZM6" +
                                                                  "19+/+l7V3+lsvVOBTtYq7Oylj7BnFxV18QQ8eqtW3q26x+6nup97oOr+3gP6" +
                                                                  "fqEtfibrPH5Tuvb337yglje1w5+/Ry0PXfkeVcspqebs3Lu0MPRNNTjq5+bB" +
                                                                  "HFpXXzpm3JydJvqYutdeOeacnGo306NOv27lSB1/Hhkd5Xnx7Dvi0SznwRxO" +
                                                                  "yUkngvAj88auHOvsanj24Q+fBbnvXzs7xRMNtZ+aN+6b3kWqmunzbXqJqutm" +
                                                                  "miI7U/eu/t8IfIvy9DMIWw2HQautq9dePAuPzddunCRybu3rWauc1gPa55l+" +
                                                                  "9uGzq8eW8MYtRYRe09ogh3njWGuKF144e3By2nEFZpw37uY3EnDhVf363THs" +
                                                                  "i7e0cu27xLZVz7mart043+nQu/HKXZv+7Xz8eAaeMq3uiHdv++pDt/Oxjifl" +
                                                                  "4Gae1bN3Sn4yhPtnWh3Z/uMHQMAX2+Lvnwf9tx3gfpj92LmK7sSHmxmt77yJ" +
                                                                  "Czcb2t4r3+XVt8UvHYf++gNW9KW2+OWs88Z2RbqaZkeF3S+iOgYMz96DbJf+" +
                                                                  "2vcosp1D2skezo6AfvTT9HjoHMOEK3lkNEIemxs3+I5d/cbZOd8TgNBhI0D1" +
                                                                  "3WB8XQ8D68Tu6i0Ha6f6f45wfuuBx+SXH9D3lbb4zazzhju29GSL59n0UXTb" +
                                                                  "NJ/Lk/bfel77g3f8j0cf5752npbYOfvni0v6R7/1j37gd/7NV176C3vx1/vv" +
                                                                  "f8vbvvzVR4F/j773I/9gzf72/wF/S/qmbjQAAA==");
}
