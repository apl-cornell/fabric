package fabric.util;

public interface Collection_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Collection_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Collection
          jif$cast$fabric_util_Collection(fabric.lang.security.Label arg1,
                                          java.lang.Object arg2);
        
        public _Proxy(Collection_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Collection_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Collection
          jif$cast$fabric_util_Collection(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Collection_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.Collection_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Collection_JIF_IMPL._Static
        {
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
    final public static java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    final public static long jlc$SourceLastModified$fabil = 1282915709000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKVXXWxURRSe3bbblm7oD38N0PYWKtlVaI1GgmwMNkjDliWs" +
       "bVEpkmX27tztwOy9\nt/fObbdgiUYjyIOJgn+JPy8mJIYnifpiohH898H0QX" +
       "zBxGCMiUL0wUgIimdm9u5ub7cQ4yZ7d+7M\nOWe+c853zsyevYIaXAetM3CW" +
       "sn4+YxO3fwhnk6k0dlyS286w647BbEYPP77qxW3PXP8kjFDRQZpt\nsZk8s3" +
       "hJZ4H41vU3pr85PrymDrWOo1ZqjnLMqb7dMjkp8nEULZBCljjuYC5HcuOo3S" +
       "QkN0ocihk9\nAoKWOY46XJo3Mfcc4o4Q12JTQrDD9WziyD39yRSK6pbpcsfT" +
       "ueW4HLWlDuEpPOBxygZS1OWJFIoY\nlLCcO4mOoXAKNRgM50FwZcr3YkBaHB" +
       "gS8yC+hAJMx8A68VXqD1Mzx1FPUKPscd8uEADVxgLhE1Z5\nq3oTwwTqUJAY" +
       "NvMDo9yhZh5EGywPduFo9aJGQajJxvphnCcZjjqDcmm1BFLNMixChaMVQTFp" +
       "CXK2\nOpCzqmztiUT/Ppn+SwujEGDOEZ0J/BFQ6g4ojRCDOMTUiVK85vWfTu" +
       "7z1ipWrAgIK5nBOz7Ym/rl\nox4ls6aGzJ7sIaLzjH5j89quucGfmusEjCbb" +
       "cqmgwjzPZVbTpZVE0QbyrixbFIv9/uLHI5/te/Id\n8msYNSdRRLeYVzCTqJ" +
       "mYue2lcSOMU9QkanaPYbiEJ1E9k1MRS75DOAzKiAhHA4ypaVj+2MZ8Qo6L\n" +
       "9k31+Ud8kfpQ8eBIRfkhiOiYNQwc2FEEP/sFG7SYbhVssO1oeWISB3OSi9t2" +
       "UZhcOh0KgWdrg1XG\ngJI7LZYjTkY/c/mrJ3bseu6EypngWQkM0BTK2aG6ig" +
       "d4yyC6EJHMcHIok9ydTqFQSNpfNT9yIhU5\nUTG/vZtoe36T+34Y1Y2jZloo" +
       "eBxnGYFKw4xZ0ySX4ZJq7VW0ln4CFaNZYCUQPMPAkKyCoo2moMUE\n2Vep2S" +
       "SMMFBq7tjNb69mps8JoojELhfWFTRI02GFLRofPTB88MS6OiE0XQ9BFp703d" +
       "56Rr96cve5\n776+FKuwnKO+BcW3UFMUTxB+2rF0koPmVDH/yvWdv59quP+9" +
       "MKqHioSexDEwCQq8O7jHvCJK+A1J\nBKsuhVoMyylgJpb8LrKETzjWdGVGci" +
       "QqHm2KLiJYAYCyl117OnL3xQ9bPpUe+22vtao/jhKuiqi9\nEusxhxCYv/Rq" +
       "+tRLV47vl4EuRZqjiO1lGdWLEsiKECR2WY2C7u9cfvrl+OsX/Uwuq1gfdBw8" +
       "IxJZ\nfGqu67XP8RtQ7FB0Lj1CgCjwkTshfwPxjMvxnVWL4l0riQhyBQtlSL" +
       "R7PzOF7NE/z7+5RFNghM7q\n6j165bNPeRjmqAlngQBYl+Td4G9SwVFWdlDX" +
       "Yh1VngbHH/sj+iy+cED1vY75tbbD9Ar3vfU9iT8Y\n1WtUcDO37E2MTBFWcT" +
       "K422550vhetj7a8+PQ5jOzQS/DgLPnlpoZvX1qzcN1E/SLsOBfqTAWHHXz\n" +
       "lRLViIGhDoGT2hS+i5kmmaFuCaMVQCyFb5cgUKk5yl8eKv635rj1ni33btUm" +
       "PezSSc/iJObKS4Wm\nKKllLYsRbGqHqNHnw7SM2H6DmphpqiUexYXsbL9oWG" +
       "rkEt1zKJ9RbymcJUwOpZHURk3pCkRBTXVm\nKWHrwFBcO0oNLWZptLyzVum8" +
       "sFr1punaA1qsatVKaCp+JZRymzI2BWuvYA2Z9OgUZsTkY1YMIgUg\n84QrpY" +
       "zgVaaq26di8Y3SjXhitmyeuSQxWz5mahSAZD3UuYpt7TIMl5gl3jvgArM4al" +
       "+kTTYAKaAC\nd/v6HuRo6fxUuguvI2mHFuC0nypdR17ofvvnc5dHlquKUne2" +
       "9QuuTdU66t4mN2yxRU333moHKX3h\nrt6zx0Z+yIZLQLdw1FjinnRkm6K9uB" +
       "CAL50V2oc2/K87wWLJEo+hWyZKvCbFY9ftw/4IlL4Iu45d\n3lebWX5SV9a+" +
       "ZhQ5Wlbj0iHOgc4FfxvU5VZfN3cwdt5u/1KeneULaCPcAg2PsapGU910IrZD" +
       "DCpR\nN6ojUUUKkt5SBY2jevEjUetKIg8EVxLibcL2PeqoZnKJqP8Cm5NKRy" +
       "MNAAA=");
}
