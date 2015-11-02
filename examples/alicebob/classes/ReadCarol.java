import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;

interface ReadCarol extends fabric.lang.Object {
    
    public ReadCarol ReadCarol$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1446060498000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("H4sIAAAAAAAAAL1aC5AUxRnuXY47Dk6Bg+MhrwVOkNctT1EOBe7O8w4XOI+H" +
       "eirr7Gzv3cDszDAzeywQUhgVCJqrBEGhIkQrWIIiaoyaUkFjoqAYjY/ygREs" +
       "kiqxFI2W8RFE8v/d894BTUpzVdsz093/33//j+//p+d2HyedDZ1UZoSULolV" +
       "5gqNGlX17KFJ0A2arpUFw1gA3UnxeO/nduTXHrogSjolSJkgitQwmlRZEleY" +
       "ZGBiiZSJM/K4LKSoHK9VlQwfrYbZoqCoiiQKclIxTHJ2YonQLsQVasYXNjfC" +
       "eIUiZKmhCSKtoxpV0lQRJQoTu/OJOVOS4/OpWZ3XySBYyJJztpThQsI6K7iM" +
       "X9/1YnrLVdqRKCluIV0kY6FiCBmaIKVCzmxTdckEWXt4mCYkw4T1u4kqyKUL" +
       "kmIay8hPSecE6SFBj6CYkmDSdL2uZk0yNKHBQq2yasZp3oxrgi5krT03MT0B" +
       "p2LWazPpoulqu5SmukmGFGioyRpL4BNuLWazt/ZXYIHNY+Kbbl/c43edSPcW" +
       "0l1S5puCKYmgaRPkaSFlWZpNUd2YlU7TdAvpqVCank91SZCllTBRVVpIuSG1" +
       "KoKZ06nRTA1VbseJ5UZOAxFxTbsTTcZUkhNNVbe3U5yRqJy2nzpnZKEVjNTH" +
       "VQvfXj32gy66gjqpngGr2iRFSyUljboIUDh7rLwMJgBpSZaCvZylitA/TFLO" +
       "LScLSmt8vqlLSitM7azmTFTwOadlWo2GEMSlQitNmqRfcF4TH4JZpUwRSGKS" +
       "iuA0xgmsdE7ASt4ImTu9Y5XSoERJBGROU1FG+cuAaHCAqJlmqA5eTjlh2ejE" +
       "bUKfveujhMDkisBkPuexn3w6c+zgpw/wOQNC5sxLLaGimRR3pM5+ZWDtqAs7" +
       "oRhdNNWQ0Pi+nTPnb7JGqvMaYEAfhyMOVtmDTzc/d9Wae+mHUdK1kRSLqpzL" +
       "gh/1FNWsJslUv5QqVMcQaSSlELi1bLyRlMB9QlIo752XyRjUbCRFMusqVtkz" +
       "qCgDLFBF3eBeUjKqfa8JZhu7z2vE+usOvwpCovdY1y0muSrepmZpXDLaaCYT" +
       "r9NVLaXm43WqmMtSCGSAIF2hshwXNDneKplxDnIQu0JWk8GiEBciTampuKGL" +
       "8WYqpGsFXZWrYJr2YzLP4856L49EQOlDRDVNU4IBFrS8qaZJhoBpUGVAhqQo" +
       "d+xtJL32bmUeVeqgJHKIghcMDCKGl3ZTruaST/ckD3JvRFpLpSYpdQQCGcow" +
       "qKoAjKsAjHdH8lW12xvvY75TbLAgc8lA+mmyCjCeJ5EI20BvRsw8Buy9FJAD" +
       "ELVs1PxrZ1+3flgncFVteRFYDKcO8yF3rQsvjQxpRfDx12Zo13VMGTA9Sjq3" +
       "AAIbdTQj5GSzqbZGzSmAVL2drmYKIKYw6AyF7xJNZDQm6VsAvBxwgUx3mSDZ" +
       "cNBoZTC6w8Tsvu7YFw/ctlp149wklQXwU0iJ8DEsaDJdFWkaANllPzomPJLc" +
       "u7oySorA6LA3E3aGEDc4uIYPRqptSMa9lML2MqqeFWQcsrXS1WzT1eVuD3PF" +
       "cmz6cK9EiwYEZGh+0Xxt21svfTApSqLeVTp58AWfezEk6en6xAKdUkjc725p" +
       "unXz8XVXM4eAGcPD1qjEthZwBTIoKO2mA8vePnJ4x+tR14lMSK+5FARWnq1e" +
       "cQr+IvD7Fn8IEtiBV0gVtRZAxRyEMol8dMOOo2s3iuj4037o+K5RUxjd0yZN" +
       "GTtp8rjJk3CfI1xNADLKgM6gKKNyoZJV01JGElIyxXj5pvu5Ex75qKMH9ycZ" +
       "erh1dDL2uxm4/f1ryJqDi78czNhERMzMbqXjTuNw38vlPEvXhRUoR/76Vwdt" +
       "3S9sg+AHsDaklZTjL9M+YR5yAdP8JNZODYxNw+Y8gIXgICw3wA1+FoRQ3Ei8" +
       "8kmKfT4bFtfq695jztVVdApHZAM76YZlRKtdaQ4qCOdGdxgDrG9wJWuVomtj" +
       "6c9iw65mUdUtTQ1RlzTbcSHpdDUksCwolaYZGAD8mepsUJJTu+mCYsjgSRxA" +
       "FrDBS/KajpVDu6Aza/CwyGMQOGI0YUmYFKfevE5Xh284P2qpq5w7sZXbChLc" +
       "PTjaV8O2Xx6q1jQHvpgmxmQbsWaBZsfZtbstnlO/WwpeoEKHJKOkSbFm352R" +
       "Q+8cfo1XEcOZkA6hh8TeW1LcVnH7k+X3bZzFKYb4KQpmTx9fuzY5+aG/sKhF" +
       "PxscNAfGCtW5vZLiZ9sP0eYpX3/MgUVdrgRrZQ0ykChpAtbL1h2W2Trjgjpo" +
       "BKn6FXiXxf78X9z5wPHDTTNZSHgsjMVQQT1uuZCDiTXsNuFPXY48VQtUzREp" +
       "KS7u89cxA5+86udeNQUIPLM7dt1R8snYr+9k23b8bHjAzxyCM/oatjO5vAxy" +
       "fAbyCum1U9+Kd18/0N7wcbgfhFHMmNh737F+/VdZlsUF51ir4uXyUGNfAa9c" +
       "rrFjVYlnnippfsFjbGZBUMFyNpHbE9vZrgGuAMbnhumzRjVNNevR6kXDDy2p" +
       "PvnK7+0Im+toZZR/gwFK7zaLRz/Rv+OdNfNsHs18q1d6ttrCu6ZreRadi9lT" +
       "nYHQEyiGGgSjDdLfW/KbLZvfHT2YK9uTHq3xx+tu2nzbHx6bzOulMkSDGTN5" +
       "2ctXbNCYLCIXRfOJEXhcxKe2cQNpjnH8j/was/EZH0axdgw2VR5QH++fCS/g" +
       "p3v/Ye9uO362aXt63t0T+FbL/e8Ulyi57P1vnHyxast7z4dVpaaqjZNpO5U9" +
       "a2Ll6H/nn8NeDd1yaepddZUDn1nW8cMVjRb+h9WHQwK7Dwqza87u5y8dIW5k" +
       "hyRWaVjwuusnqvbqAVCKr4oaxZ6zmRVGOLmiB9qhP/916mldu3hzBS/kQk0a" +
       "xdu4iYU9nhzkHa5ROwMhtxJ+jf7bwzWQ9qOcneFLt0xRNM1fV+/euXtPddmu" +
       "u1mslzI0gDxoWqrtghT2M99iT0eYc1GYSZYwY6zrIO8WYd1zglAzS2+1Mv3O" +
       "s549eLxf/QGW6aOihEVDQXGepqfTf07TqO71g2i7hCdcARaLBE9djzMvdjZQ" +
       "FbaBEf58Pg/ZZVVda5OshB5TMzFesscEvZWVoTENO/lZSCwLrho7L4WC0XRM" +
       "SKntNJZaEVulHV37y9WjNAclHZSrFRRFNQtSdLEoPXI8njlpI1w9B4d2bFZy" +
       "78Fm1WlMjs/XY3MDd58bsVn/Y+wIGd/83wlX+Eq8UFmqQKbhTjm/2+7cDXvH" +
       "vWVvvSdDOHZ7G2s3BfhGOAa5E7ZgA2VoEcrOugkJq3cLMqElh+WiveMfbZv3" +
       "1dEHbUEu5vuz8uoGfvl1oNMkESvzcJVbA3X80sA7sf2tm7LW+3NFYdcix2vj" +
       "KPtEy1tHW9eRQWTZ6dQYgWxctwKCCMpONx0vPDl7wNfHL3uGQz2e6YQdFM/i" +
       "B8kwysNorl+g8ZYg54UJBLJUFZS/QUF89e9Pl9x46+VD297n+Wm0P2AKKd2o" +
       "uSXV9PCcv32b89QFvjLPAiFnMwk8JpnQeM7U6S2PggY6tZBSBVKbPjcnQ/KJ" +
       "ylLI6zyjCYWlfIhHuWs1q6o55VfynoenfHAN31ghOjqT1aILqy99omFE1Hc6" +
       "iKv04vXM/QTDyJdyfYIlxQW37H/z/K3HNjIWnWVvfgwedAYo5R3y/sTnK17i" +
       "UgZj1QOpSXHivdl/RYcVPxslJZDWWbIWFHORIOfwRKGFdJWMWqszQc7yjftP" +
       "ivmxaLXnRHZt4KTEC/9Fpi/xlvuz0hTLC8eFZaUIYTePh+feiAmLSoog87LK" +
       "CWYvlgYD2w3mRdjsYqR/Yh33MlNxsj18/kPcekwGbJ5yi/Rd3iK94ARKyoIP" +
       "tFtn0XT9pg2nqjo28ajlB/bDC87MvTT80N4LqLDK0DOtwijq339g9RM7V6+z" +
       "Iwpe/EodOPlxUwvyfZmlF7bOdYGaG/tuYRrE5o9MoWgvvNnvqPcgN5/jICwD" +
       "zLAcY6p1jfvgytnU67CpBd9rU3BjfI998ZMtLAN4rsB2I6QpJOeu5ZezzpJv" +
       "epicJulpnzYAfSxl10JvW286YTpzc9Ahpr/DrvMd8r+EFHYtcsn+7uapQ4Wp" +
       "y9/1KO+ywu7YacIOb/fZMcf1ATEQ8sGTHYPxGKgl0vvj/6G/DMUr+4w4V0DP" +
       "DXz1RJYRP9YyQPLw2T+p3+Z1t37Zl/EpsQCLQddcVWEPIR+UPPT/3H3kw1fP" +
       "GrSHVbJF+J2AIVPwS1zhhzbf9zO2+R7c7VhPf5P0YC+hiI9V/EsaGxhkkjI8" +
       "w9QgHaIjAhJpBPTrdVxs38Dmc2ze1Jxg+ZC5CDMFNp+wmR3hBVUgdPCNg/SB" +
       "XymMVVrXCpOI/9shLR7Juh867V43I1ySFyk7+rO+9fw/lskHorAetzzPir5L" +
       "revF3ihklxPfGXIOR2RCZlucaqzrDC9HbE/xIPRTNVizZ4ZRBYM3QvzB67C6" +
       "DFnNtVjUW9fagACRYh7OfqqENbsujMob80yAEp8A4M29XG92lB7u0EwHX2DD" +
       "4ncrm/W8lbXw8iKARLsqpcPL+mFh/yjh/Y6fFO+Y+fI3+1uXvQD5E+oW55Nd" +
       "bRsVl9J0PqRoDDBYSlbf/Od15dcz0CiVjAV6zjDxk3ypaB+1+I8v8Wua862b" +
       "v07wo6lIPxB5TEGB7FvPVx1XXHPP7CO/mdGPl2cjg6eCPjK3NI4s2TovUXLq" +
       "SueFMtRbGRD3ZtXJCZNEtZG+igelHXwGX8e+YCUUGfp9KiG21rKwzO1BRC+2" +
       "aR5cg5oNM8bIMGhbcyZow+YrNu0ESnoWk3e063f42DPEx5jVGFne+5lWc1y9" +
       "3DInc3Ze2p7G00nhGV/od5vIZP8xDh6x5fg/tEAGmjihbt+BEfutc2InVdG8" +
       "WcX+1cU+B3MoHtg+e+6qT8/nBz+dwWVXrsRFyyAp8drGSkreEjHIzeZV3DDq" +
       "xNkPlp7rfDLBxv5UWbC7mFeHbj3C3u8j1W4VMb6wsHC6sB2s/Qd4VvxlFyUA" + "AA==");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements ReadCarol
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            ReadCarol._Impl.main(arg1);
        }
        
        public ReadCarol ReadCarol$() {
            return ((ReadCarol) fetch()).ReadCarol$();
        }
        
        public void jif$invokeDefConstructor() {
            ((ReadCarol) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return ReadCarol._Impl.jif$Instanceof(arg1);
        }
        
        public static ReadCarol jif$cast$ReadCarol(fabric.lang.Object arg1) {
            return ReadCarol._Impl.jif$cast$ReadCarol(arg1);
        }
        
        public _Proxy(ReadCarol._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements ReadCarol
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm84 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff85 = 1;
                $label80: for (boolean $commit81 = false; !$commit81; ) { if ($backoff85 >
                                                                                32) {
                                                                              while (true) {
                                                                                  try {
                                                                                      java.lang.Thread.
                                                                                        sleep(
                                                                                          $backoff85);
                                                                                      break;
                                                                                  }
                                                                                  catch (java.
                                                                                           lang.
                                                                                           InterruptedException $e82) {
                                                                                      
                                                                                  }
                                                                              } }
                                                                          if ($backoff85 <
                                                                                5000)
                                                                              $backoff85 *=
                                                                                2;
                                                                          $commit81 =
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
                                                                                    "carolnode");
                                                                                final fabric.lang.security.Principal storeP =
                                                                                  store.
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
                                                                                                storeP),
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
                                                                                   RetryException $e82) {
                                                                              $commit81 =
                                                                                false;
                                                                              continue $label80;
                                                                          }
                                                                          catch (final fabric.
                                                                                   worker.
                                                                                   TransactionRestartingException $e82) {
                                                                              $commit81 =
                                                                                false;
                                                                              fabric.
                                                                                common.
                                                                                TransactionID $currentTid83 =
                                                                                $tm84.
                                                                                getCurrentTid(
                                                                                  );
                                                                              if ($e82.tid.
                                                                                    isDescendantOf(
                                                                                      $currentTid83))
                                                                                  continue $label80;
                                                                              if ($currentTid83.
                                                                                    parent !=
                                                                                    null)
                                                                                  throw $e82;
                                                                              throw new InternalError(
                                                                                ("Something is broken with transaction management. Got a signa" +
                                                                                 "l to restart a different transaction than the one being mana" +
                                                                                 "ged."));
                                                                          }
                                                                          catch (final Throwable $e82) {
                                                                              $commit81 =
                                                                                false;
                                                                              if ($tm84.
                                                                                    checkForStaleObjects(
                                                                                      ))
                                                                                  continue $label80;
                                                                              throw new fabric.
                                                                                worker.
                                                                                AbortException(
                                                                                $e82);
                                                                          }
                                                                          finally {
                                                                              if ($commit81) {
                                                                                  try {
                                                                                      fabric.worker.transaction.TransactionManager.
                                                                                        getInstance(
                                                                                          ).
                                                                                        commitTransaction(
                                                                                          );
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           AbortException $e82) {
                                                                                      $commit81 =
                                                                                        false;
                                                                                  }
                                                                                  catch (final fabric.
                                                                                           worker.
                                                                                           TransactionRestartingException $e82) {
                                                                                      $commit81 =
                                                                                        false;
                                                                                      fabric.
                                                                                        common.
                                                                                        TransactionID $currentTid83 =
                                                                                        $tm84.
                                                                                        getCurrentTid(
                                                                                          );
                                                                                      if ($currentTid83 ==
                                                                                            null ||
                                                                                            $e82.tid.
                                                                                            isDescendantOf(
                                                                                              $currentTid83) &&
                                                                                            !$currentTid83.
                                                                                            equals(
                                                                                              $e82.
                                                                                                tid))
                                                                                          continue $label80;
                                                                                      throw $e82;
                                                                                  }
                                                                              } else {
                                                                                  fabric.worker.transaction.TransactionManager.
                                                                                    getInstance(
                                                                                      ).
                                                                                    abortTransaction(
                                                                                      );
                                                                              }
                                                                              if (!$commit81) {
                                                                                  
                                                                              } }
                } } }
        
        public ReadCarol ReadCarol$() { ((ReadCarol._Impl) this.fetch()).jif$init(
                                                                           );
                                        { this.fabric$lang$Object$(); }
                                        return (ReadCarol) this.$getProxy(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.ReadCarol$(); }
        
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
                                                                                   o)) instanceof ReadCarol;
        }
        
        public static ReadCarol jif$cast$ReadCarol(final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (ReadCarol._Impl.jif$Instanceof(o)) return (ReadCarol) fabric.lang.Object._Proxy.
                                                            $getProxy(o);
            throw new java.lang.ClassCastException(); }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (ReadCarol) this.$getProxy(
                                                                            ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new ReadCarol.
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
        
        final class _Proxy extends fabric.lang.Object._Proxy implements ReadCarol.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((ReadCarol._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((ReadCarol.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((ReadCarol.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((ReadCarol.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(ReadCarol._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final ReadCarol._Static $instance;
            
            static { ReadCarol._Static._Impl impl = (ReadCarol._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                      $makeStaticInstance(ReadCarol.
                                                                            _Static.
                                                                            _Impl.class);
                     $instance = (ReadCarol._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements ReadCarol._Static
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
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new ReadCarol.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm90 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff91 = 1;
                                       $label86: for (boolean $commit87 = false;
                                                      !$commit87; ) { if ($backoff91 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff91);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e88) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff91 <
                                                                            5000)
                                                                          $backoff91 *=
                                                                            2;
                                                                      $commit87 =
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
                                                                               RetryException $e88) {
                                                                          $commit87 =
                                                                            false;
                                                                          continue $label86;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e88) {
                                                                          $commit87 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid89 =
                                                                            $tm90.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e88.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid89))
                                                                              continue $label86;
                                                                          if ($currentTid89.
                                                                                parent !=
                                                                                null)
                                                                              throw $e88;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e88) {
                                                                          $commit87 =
                                                                            false;
                                                                          if ($tm90.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label86;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e88);
                                                                      }
                                                                      finally { if ($commit87) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e88) {
                                                                                        $commit87 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e88) {
                                                                                        $commit87 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid89 =
                                                                                          $tm90.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid89 ==
                                                                                              null ||
                                                                                              $e88.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid89) &&
                                                                                              !$currentTid89.
                                                                                              equals(
                                                                                                $e88.
                                                                                                  tid))
                                                                                            continue $label86;
                                                                                        throw $e88;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit87) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -31, -20, -32, 33, 17, -107,
    81, 69, -47, -82, -95, -6, -38, 102, -21, 113, 26, -98, 36, 20, 67, -81, 34,
    6, 125, 109, -7, 108, -92, -71, -25, 86 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1446060498000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16C8zs2HnQ3Lt3n9nsKw+SJZv82VyivfHmemzPw5PLtnjs" +
                                                                "8YxnPDMe2+MZO0oXvx/j99sTFpWiNlGLQtVu0qRqI0ALgbIkgFQqBJGKECUl" +
                                                                "oaIRQoAEWSJQW20iUSEgVECxZ/7Hvf+9e6OiVIqlOT4+5zvf+c73Ot+c77z+" +
                                                                "3daDSdx63pAV272ZVqGe3CRlhaIZOU50DXflJOHr1pfVt12jPve7X9Lef7V1" +
                                                                "lW49rsp+4Nuq7L7sJ2nrCdqRcxn09RRcs9Stj7ceVZuBEzmx0tbVjw/LuHUS" +
                                                                "Bm5lukF6Osld+D8LgK/+wo899fcfaD0ptZ60fS6VU1vFAz/Vy1RqPe7pnqLH" +
                                                                "CaZpuia1nvZ1XeP02JZde18DBr7UeiaxTV9Os1hPWD0J3LwBfCbJQj0+zHnW" +
                                                                "2JAf1GTHmZoGcU3+U0fys9R2QdpO0lt06yHD1l0tiVp/vnWNbj1ouLJZA76b" +
                                                                "PlsFeMAIkk17Df6YXZMZG7Kqnw25trN9LW194PKI8xVfn9UA9dCHPT21gvOp" +
                                                                "rvly3dB65kiSK/smyKWx7Zs16INBVs+Stp59S6Q10COhrO5kU385bb3nMhxz" +
                                                                "7KqhHj2wpRmStt51GeyAqZbZs5dkdpu0vrv405/5pD/xr7au1DRruuo29D9S" +
                                                                "D3r/pUGsbuix7qv6ceDjH6E/J7/7q5++2mrVwO+6BHyE+bU/9/t/5sX3//rX" +
                                                                "jjB/8h4wS8XR1fRl9TXlid9+H35j8EBDxiNhkNiNKtyx8oNUmdOeW2VYa/u7" +
                                                                "zzE2nTfPOn+d/Q3xx39Ff/Nq6zGq9ZAauJlXa9XTauCFtqvHY93XYznVNar1" +
                                                                "qO5r+KGfaj1c12nb14+tS8NI9JRqXXMPTQ8Fh++aRUaNomHRw3Xd9o3grB7K" +
                                                                "qXWol2Hr9Hmy/r2r1br6pdP359OWCFqBp4N2YumGARJxECpBCRKBmnm6n9YK" +
                                                                "EMS+7rqgHLqgaadgbc6xrYJ6KXuhW0u0thJVVwIFTGIVZHVZw+U4cG/WYOEf" +
                                                                "J/KyWdlTxZUrNdM/oAaarshJLcFTbRoybm0wk8DV9Phl1f3MV6nWO776hYNG" +
                                                                "PdpYQVJr8oFnV2oteN9l/3H72Fez4ej3v/zy14/a2Iw9ZWnaevScoJqGxxuj" +
                                                                "ulm7qZu1m3r9SnkT/yL1tw+681ByMLKLYTX1H3OD2sGVrStXDgt452HwQWNq" +
                                                                "ee9qP1K7isdvcJ+Y/tlPP/9Araphca2WWAN6/bLhXLgbqq7JtTW8rD75qd/9" +
                                                                "H1/53CvBhQmlret3WfbdIxvLfP4yN+JA1bXa812g/8iJ/Ksvf/WV61cbr/Jo" +
                                                                "7fBSuVbJ2nu8//Icd1jorTNv17DiQbr1NiOIPdltus5c1GOpFQfFRctByk8c" +
                                                                "6k//Yf1cqX//t/k1ytw0NO/apeGnhnRybklp69vf/unXvv1TP/exu/tOXvi+" +
                                                                "XUUQ7/T4elhLTrVD2f2jYgmbjh+k8sv10+j9x2DkRaT/0U77Rv2ERytoNOiS" +
                                                                "1A67x0tc+Mv/9rd+Dznsq2cbzZO37Uicnt66zbk1yJ48uLGnLxSSj3W9hvsP" +
                                                                "n2d+/rPf/dTHD9pYQ3zoXhNeb8qGHXLNhiD+ya9F/+5b//G1f331QoPT1kNh" +
                                                                "ptQLO1D+oRrRhy+mqv2eW/vempLk+tr3As02bFlx9cYa/veTfwr61e985qmj" +
                                                                "Srt1y1FB4taL3x/BRft7h60f//qP/c/3H9BcUZt994IdF2BHZ/6OC8xYHMtV" +
                                                                "Q0f5F7753Bf+ufzLtWnXrjix9/rRux6W1zqsCjno681DCV/q6zbF8+Wh732H" +
                                                                "9mvJ3Rsb2UQIF/Ymga//0rP4j7x59F/n9tbg+OA9/Jcg3+YK4F/x/vvV5x/6" +
                                                                "Z1dbD0utpw7BieynguxmjVSlOrxI8NNGuvX2O/rvDBWO++Ktc3/yvsu2ftu0" +
                                                                "ly39wm/W9Qa6qT92NO6DHpRXWmFTuXUY8eFDeaMpXjzw6GraeNEmaEtrxLZf" +
                                                                "u87DsLT18KmhHka8K22982hEN4/NNzeHV9P37NFSmrJ/OmWtjA+2b8I34eYb" +
                                                                "v/fMDzTVjzbFjzTFj57N+6zjqtfPDF+og8daaa4f5z4j5amD9jS8u3kMsw4d" +
                                                                "701bjzdmHLpy2vi/8h501RrxxMVgOqgjtJ/5zz/7jb/8oW/VGjBtPZg30qkF" +
                                                                "f9sMi6wJYX/q9c8+97ZX3/iZg8HV1sZ85TvvYxqsdFOM0tZzDdlckMWqTstJ" +
                                                                "Oj9YiK6dUX63JjKx7dW+IT8NsfRPv/rTf3jzM68erfAYh37orlDw9jHHWPSw" +
                                                                "yrcf11fP8sH7zXIYQf7OV175R3/zlU8d47Rn7oyqRn7m/Z1/83++cfPzb/zm" +
                                                                "Pfbla25w3G4vi/sd5aSTUNjZQ0My3sXWJetnW7HTJ3a9oQnMrbgQlruFmvLQ" +
                                                                "aDnU+0u06GyXFDDR7Tjuj/c5ohLJnsD2elWs1pYp4NMoHo7GFrVbxRuKsuWg" +
                                                                "ktc70gkiTHREsDLZUbKzo5Kk4A3KkiM0BzV00Bny63AvaMm+PUBABAFBBwDB" +
                                                                "ENDgSaVrhAStpYBb04ErdM1oMXa3UH9GTtsYLMfhmiwgE0PWfqkvQV/L+O0k" +
                                                                "DkzOCVxuuqt3klXhuaw0cnVRGg9tW+Kmc3DWoWdcMJkNBHdVGLGoSEGEB7a9" +
                                                                "mqkuVfKTKSwG/opOVoKciNuFY+A6tbalmWfh3CwNylBouyt2ugr4DFxOl8KS" +
                                                                "64DLcCMh24obitV0uk9D3lolVG/Wt+fbAQiJfkwLWsTOcDejhPZO7JE2PvRk" +
                                                                "aibge6/kNXU32mzTjoeLu6LDQ1lnF1WzNb3zKLlCLYbT1VGPI4dOrwbwCE6j" +
                                                                "4UzatavFbDJcRXAlJ1KIBGVs2t1dzLeROd8ehjC9kfdMGGRulHU5L9t5Y3Js" +
                                                                "BxUb0p3hImt6U1DaitRkIQIzRXFBtYPUf/cYddN2Ia4bZvhM9EyZZXYyR7ob" +
                                                                "KVysNVh0131ew/IwEnDZ3w0gTsJdlx+yghBSGTeGZsFMavd8lscgQUn0aXct" +
                                                                "LbGSLHkl4XlVWu+sxUJQdGTdrdhoWsH+PvSl2UhwFvxEWOXG1kFgs+r1TCcK" +
                                                                "fXMcwzZLctXadZSAWhNWP6aL+dQchpwk47lUuXaKgTxJL2lOpTkvRfPIpTk7" +
                                                                "nfflvceTfLGUNXcTwOPRnHNXdrrwItZQyQ4/6UylcF2w3HjG2RnWLk0v0UZ9" +
                                                                "jEs7lMdnA7G75YIROx2O2eUaIseAmmHyBMeiqetFWFfnHdeCdWekrGdGrmeO" +
                                                                "NSd2djLcMbU/8pGipwJ7MyzAYrla4N2kpGl5bHssi4TzLiBtiDTCkn7AWtpi" +
                                                                "sVm1GRvwecZX+tNxlM9lcYQZVjVfCqlH8HsV1AYbWQe8yVqbQTuWo6NwT5sW" +
                                                                "CJGLTTJN3HXeXUXCXF3JHOeOaBtS6Ip158PByqWCfhfeVqyzVnCWnGdJJKS7" +
                                                                "fYSbBE0OSUQf6mtnFhkyOpWsrL+H8xGQJcu8CPEg10SoansZF+uhJ4VbgpZl" +
                                                                "XOTjwUKg5a3XQTaRN085fMxhFAlbiVVsF4Svz0K3JHxqlbWnmx06tkdD1ebr" +
                                                                "MC6bjWl0I0bhOCG30iYWpzEs7mmmg3Z7dGcwWi1dgiWExbpDDcmYQyNZmlrB" +
                                                                "MFyPBcyKVoEGlOmURmDFW+uCHpnTyYwyLXVO4LOxi0h8nMkcD6o7ApnmLBnS" +
                                                                "ISeG2mqFj9WgiGYB5swSzJloE9TdQHCZ52K/yys0ZqqoiwGeV+EOXivriILb" +
                                                                "w5xNCnSYVQNyihEjWkggpowlCEAM1/RVUkh1lhSJmbpEKxKJV0nurMqQNYgS" +
                                                                "gqUAQoT5mMcGnBMhGEYpGr9yumNuHynSeM95mEDDWuyJM2omFj2sIvvldCER" +
                                                                "K68nJFg+LAtzXSIeR0jqYhhIPN4rUD7Js75IFqCauiHUGRQ7d7Ut1m17ihKj" +
                                                                "RCamGrnZydjCdZl94DFTWs36QcUFMdajMWSEyOMRoXYySmTYKEy5uTxw5zQ8" +
                                                                "jyKd3KzqnW7LQZs9D+lCRiAMqXr7njobjOxCHEljC2hvyxSGktzgsZCLaF3g" +
                                                                "wy6KbQx9P9r1dL9XYjNtvXPUPlx7DH+7R7fCdt7RsbHKEFDkjFJLKoY8FcyW" +
                                                                "sIehuc74004JpCzUcefcDOiSGLvxI0hXVzFA5USf9FFqlNpDoW0KKSthJVuN" +
                                                                "5oGLavPlMoATxM6cThvpB8Csy65LR2tX0mLMpyEaO9MdghsUVC4wgp6Z8545" +
                                                                "my2qQJoKbWg6HrlUVW6dbphn3VHZ70az+WRQqZIeW5BG1A619DqgAcXVNCLs" +
                                                                "PqruF9aIne/nSrR1+GxRIGjuMqN8a7HrgAX7PaHQUoQbJCaATRJC3rkS79CD" +
                                                                "OSCy3QrTGLSW6pIQy0UBiKhJbU0KL5ACgwAyYFfIyEbRnTcz+I5CpkbUG3Sd" +
                                                                "fSoIGhZOJGze74mSy85STV51InypjbXSJOLxoJhuMiHwUqenDuyi1/XW2jrl" +
                                                                "VxDr7nlF5SahRqx3CV4ErlxvdxiBenqYrKdTPgPqnSVxTI4tMTImhhw4k51Q" +
                                                                "ILIetpdBvKjG89VyTLXFtSeSM0PPR92tBaMdBo7CqFv0E2mr4BatMrwjC+gS" +
                                                                "3pa9CHJWkzw0vdJbKclGHkqJIaVKkvruEtwUoR6MZpPpbLnjFNJDujC7AlDV" +
                                                                "CKDtSF1KO6xnWSivbyBaFfxJfwdsEUXakzNR6cxWoceXAyPbi11AHoPJzCKZ" +
                                                                "MlEXmDHG0003l2hbGiRMWxfDLQLOzbWgxSYbS6t2tcOtEEsVGrLgDczHjumE" +
                                                                "wXDm98itvS2NProd7LsFMyF4fiFxEzNx5sJqVW+3mYQTNr9NFhnJ1PEPKqzS" +
                                                                "9Z4dhTOun6EDtcS6ANrLNH2xWlMMPe9rKcSiiS/GwGAHIwqqTBxsX1b4HAzk" +
                                                                "1Fl2c6ysDEyp8MyD1gNgpkEo2F70C1UxDYuczL2F1Uf6JAAKmuQma7IL2XEn" +
                                                                "21br8cLA5IEax6QjiK7v22FoJKSjjKXhek5Wi3CijAa5FJvjDJsrvpV5e9YR" +
                                                                "Fgs9Z3u7LUFUcMjk/qwPtCkPSANJYkGzIGf9vUyze5DNgaW2MBbJJIHQCNJ6" +
                                                                "aRrjWU+KdVbW7IHL++JkNars8biOoAiNNjbgTKGl3JjwsDgbZPwkQsradrN6" +
                                                                "01YmZU/ZLzN3vuOIMb/owmq6VRa4SOPUljWW09HWUsuZDqurRej4E9QBCzkB" +
                                                                "R9KkLZeaWcAC211NAMDAOdAMkRwU9UG2RHqwvE17SNrmAVKhQR1du5Xd7TNQ" +
                                                                "7k+qso7T+/uOJoSyHDnSIlx21wMZpt3eBPR9dKETGlTJeqhp0prwIX+DqnPB" +
                                                                "YRXAGmwmq2LWJ4poL6P9rFuhGrXp7qyK3ciCU2RZ4ke5EmECPos7gVxKvb3H" +
                                                                "CuMl7dnDQWQx4ADqea6WIda+Np7datcsIuvY4nrgW/vdRO6yy2JupeUUcIy+" +
                                                                "Uuw3q0G0VH2dpNN2yS7mkCEAoJ5tU0bTvCyDvMTdU22TKINNFKemxcPzwcDm" +
                                                                "a6VWobHISWIh9vYxtax3TA3amy6252AAmufIMul2BjpdGuBqvOnJS9saCcRq" +
                                                                "O9bDyOCNdp+Pln3IhO2+hWsqxsr0cDTQnP081xxpRSrycGR0UcYEJal2N3J3" +
                                                                "mVjDLj/fk3qSqKNc2ggBP1osQVfi8IKOraBPdzZ4e8vWvtpPF1NJHgmZOxGQ" +
                                                                "fjXYOAtMdScSF5MsSy5WaR2XjgJmS6G+44YUVAyGHQ1WSKnqo6QHbdy5k4Bb" +
                                                                "EZkTWhdVEsEHRaCgxhqIIoW7BUYRuia9aDVTLN7xQBjoq8Q2n+QbIc85jVhO" +
                                                                "pP649v9DwxovMbk76ksmBgvBSI2XmbmbRohKz6ORawGb+t/T3FCkHIG00URZ" +
                                                                "j7Junu3RLBgbuQsFi/luxOV7dSooGLJF2wnWTww49fr0BkAWpKipdgnnzoxJ" +
                                                                "RxWUTjYDnV1teKmnrURzU6ppMcPxebhclO2cIdx+sTAmNJKNV3tY74krjZyD" +
                                                                "9hDdFVWyCGIXMFFJNLud9XRIT9ekxIGrNYkO9AHjDspODxAIeND3gY3XZmBs" +
                                                                "NtPgIOO2woDB1bwOddDBiswdmsFs0p8XO3Qa+it93l/3OJgxTbGXSkNuKMwU" +
                                                                "xlH9FTibVi5N9faVg4sb16CsSbZJoKgXdLuCUfmB7hsTbIFAprV0Ih6SKTqq" +
                                                                "BoPAI0fQRO3Zs7zdybdzcMUT1VSrDJFyy9UG6U/APThDJguH2vi1LAyz0DVM" +
                                                                "DLosUoEJ78+gvr02JQdaLUNuO7FzzQVZURVqn+1OJWKzUagNOVmy1NIo2NmY" +
                                                                "1Kab+SZK9iu1XzlGyti5V8r01mcnUtqZ8SMcHJChDZZuLbO2xdM4qpOSvKZE" +
                                                                "XmcswNBtzKO5IDDRPj3DN2LumiIBF4jHak4iRiwoWggydxdl4tROd8eo+q4Y" +
                                                                "K9NuZYOshtizQQdjZ1VAMSmZ+u0tPwhN2I+cAEZlxx2CCoGj/XnO82h70aEj" +
                                                                "mpi1iWVa2HUwhwzMATvvCz1gT9X7GzgKtCXjlusirwzJc8m+stTEcb1jpZnI" +
                                                                "xqNuMJgHGzPPF34eMaq56IJOHQMQexpTxV4IOUZuVqm/7vXxcKgN4jUydAxq" +
                                                                "MECleUpji06cDGGqtwqnSz3rSvp+H05zPbTVaeaG/l7EDK50ofY038BoT+TD" +
                                                                "vTrArDAGiTifa2gMjMiJmKqJD/QzOVx1NmFV/w+dLYGqqGNJWaWWCmhIc9WA" +
                                                                "MH60QpMxPxGZcjwz3XaeO2BvDBLzdQx0io7QJhhiu4fYqcgyA7FaMXQB80CI" +
                                                                "ABKUIn3JBUdhssLplaapwmbEUCywZhgeRCbIroAMZhxzE0GZksAUxiiBKTho" +
                                                                "b1l7xRjslzDA9tW+YDFGl2mLOGDZ1EAA65B2XFq7nUOhxWBlTmTKwaxpOGT4" +
                                                                "WBPKBEEJT4X3OjQtTUDCuzgxJxchUHgsYg2Ftakx+MIEueG28uaCEG/xcEmB" +
                                                                "g8Si2f40s9BeX+ggFFRxZLXrA/ESGBgF6Ca7SR0u6+l+z9hxl471nERsa+eW" +
                                                                "jDHeUksWZnHJzVQ52scEPqg3YbCkGJZfCPZe6kjsUJwjq3w/rGXZI0h/kQrO" +
                                                                "ejMwZBPDWdHqL40oGi2UJHPmgYK2K2wUolhnDg+24wHsDvtZe1RMtjBBdHw1" +
                                                                "9sAN6irbArVnGEsnUagyNOCRFlg5Fij2DHIJiIBBIQCUbfMV0R0BwabbXWgK" +
                                                                "mwjuYNpmIjYgiKHIuYoBLjWoq6n7Nm4LDib09g5R7pAqlIcABFBojm4yGe3B" +
                                                                "St7uF0HPpZiy3gxG42GfzBmhhLbbCcP08SXoaWPGhVXH7Ok448yQBdVJYD7I" +
                                                                "11s64jKdnKesMmYJREq3U1xajFJpDNim7k62xXiL6oCIr9djIxzM7DEMxbNi" +
                                                                "v4SGLA7AMttVzW7tkVGFmo8tRa2koI6X40XNEc4PNjtEb/cQowMTzKjsOsKY" +
                                                                "9HWg625UqBogBOWriKTDYX/cbvvWWAADrZtTEoOWhNUru4YJpiNs7Dm2paRA" +
                                                                "ve9lqQpTvqCN0Y26BSB1DQyReTBJTUAHehGPgv5wD2jFRtjGCUtWFRaiWjzm" +
                                                                "drE+cibUXpcEhCCicrFj3LAfGwQ9LPPueqPgfY/UJ4qD4EiGOtCmI8yVXpFM" +
                                                                "8F5twgm40jpCXrpktcYw7KWXmiO47ekZ5DsPR6fnuerj0WPTNzmc2ZX3Poht" +
                                                                "nSYMLg7OW81J4nNvlUc+nCK+9hOvflFb/nXo6unwddp6NA3Cj7p6rru3oXq4" +
                                                                "OZO8657C/JA9vzhKf+PN5wb47r+YxzPJD1ya+TL035q//pvjD6s/d7X1wPmZ" +
                                                                "+V0p+zsH3brzpPyxWE+z2OfvOC8/OSbDaqKfanjw3uPvgadP3480ve84nKW/" +
                                                                "87aj5btP1C/OtctzjFfPUsQNpoeP76t/cBvGS4mNK+dZyecuMa+W6CFjcjw1" +
                                                                "/q0vfe+9X73+e987Mu5y6v82wP/6+rfe/Obbn/vyIbF4rcnoHhZ++c7E3Vci" +
                                                                "7rjpcFj34+er6jer+tH69+56NV8/ff/TtCX9INNzRJAprs7Yvnma+f5jxH5Y" +
                                                                "3+QuKZyfdQdNNuXSZ1NJv58qnGc3HnJ130ytA+Tq9NS+efFp64Ga903Vvzey" +
                                                                "KwdkRzxNUTTF4dMvzym+epz3LFHyjos0Bu4Gvt5k7A4pm/OkzgHADm6e38o5" +
                                                                "gyjvyQXluOzDrE3xwn0Scj95n75PNcVfTFsPqg1Z98jrHHNiR0oOI3ZvYSF3" +
                                                                "r3RUqnp4lnR99jD4LzWFnrauebLt34v3");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("1/LAPuq4cq7dT97rWseX7vIC348Nn7tP3+eb4ufT1mPn9x2uX3iWC0oah9H6" +
                                                                  "cLPc08smV47J+X/51qnzJA1infmB5snvuCTyMQR5EYY+ikA3PnYSZXJiR1mQ" +
                                                                  "6i8cM9AnDTdPHNu4bvt5sNMJ3bjtSsQLN04+mVp2c8nobNEv3Lj1yo0wvN2t" +
                                                                  "NsVHzjlweB66zIGm95fC8D4M/iv36ftrTfGLaes9b0Vo0/+zl0TxSDP86btF" +
                                                                  "8fd++EQR23lNxu2ysNOG9ycf/wR3cpndd3mbtPXwKYbyTik8fC8p/I37SuH1" +
                                                                  "+/R9uSleS1uPnJF4L64/0YA/dxfXr3zhh47rx1z6yakdKEHg6rJ/YP9ZKBIY" +
                                                                  "L3z8kGg/OU72SdlTXjl4rmPt6PkO9ZPgE2QtMNs4eSE4eemlEz9z3Rsnx9il" +
                                                                  "Hu0m+q3TpPzB8SW6msV2Wt2kZUV3100+V1brnTzBLV3dvXDv6c4GHb/ORx4/" +
                                                                  "/aBhcO2ha169cOPFk+DQfOPWKQk1Vfb5qk7O2XO3Md8vRjo44vs792duX+WR" +
                                                                  "P/fO+B+Q/YP7aNs/boq/m7aeuFMi99oSHj4V3u2qeHYv6j1nKnjW0PRe/4Gs" +
                                                                  "ufn8tQPAb9xnHV9rin9Sc6ZZhyon6fVz7r/lBvLM3fZT/dDZz6nhHIV8cnAI" +
                                                                  "B51MDp7rsG1cz0KtpvTQfPLSW1jRH0Gtb52c4j0aCxPUBFQ/CMQ31cA3juhe" +
                                                                  "OLeZZqr/783uG/d1s//qPn3fbIp/kbbedhtLj6p2+23LMLxQv2ezuLkF/vp/" +
                                                                  "+xPfe+gR/o3Tiy+tk//0nTc++PRnV6Pf/vJf/YN/b7wZPfvF6+/Ev/L8Q694" +
                                                                  "/8t97R/+jvD/AC0tX0WdLgAA");
}
