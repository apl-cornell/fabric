package fabric.util;

public interface NativeUtil extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.NativeUtil
    {
        
        native public static int hashCode(fabric.lang.security.Label arg1,
                                          fabric.lang.Object arg2);
        
        native public static java.lang.String toString(
          fabric.lang.security.Label arg1, fabric.lang.Object arg2);
        
        native public static boolean equals(fabric.lang.Object arg1,
                                            fabric.lang.security.Label arg2,
                                            fabric.lang.Object arg3);
        
        public _Proxy(NativeUtil._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.NativeUtil
    {
        
        native public static int hashCode(fabric.lang.security.Label lbl,
                                          fabric.lang.Object o);
        
        native public static java.lang.String toString(
          fabric.lang.security.Label lbl, fabric.lang.Object o);
        
        native public static boolean equals(fabric.lang.Object o1,
                                            fabric.lang.security.Label lbl,
                                            fabric.lang.Object o2);
        
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
          implements fabric.util.NativeUtil._Static
        {
            
            public _Proxy(fabric.util.NativeUtil._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.NativeUtil._Static
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
    final public static long jlc$SourceLastModified$fabil = 1281544489000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAK1WXWxURRSe3W233XahP5Sf0P8CESRsSYxEbSI2DU1bFru2" +
       "EKVKltl7Z9uB2Xsv\nc+e2W9RGoxHkwUTBv8SfFxMSw5NEfTHRCP77YPogvm" +
       "BiMMZEIfpgJASjZ2bu7e7eLRASN5nZuTPn\nnPnmnO+cmTOXUa3LUV8e5yhL" +
       "iXmHuKlhnBtNZzB3iTnEsOvuhdmsEX1s7Us7n732aRShIkc9js3m\np5ktfJ" +
       "0q8fs2XJ/79thYeww1TaEmak0KLKgxZFuCFMUUShZIIUe4O2iaxJxCLRYh5i" +
       "ThFDN6FARt\nawq1unTawsLjxJ0grs1mpWCr6zmEqz2DyTRKGrblCu4Zwuau" +
       "QM3pQ3gW93uCsv40dcVAGsXzlDDT\nPYIWUDSNavMMT4PgmnRwin5lsX9Yzo" +
       "N4AwWYPI8NEqjUHKaWKVB3WGPpxBt3gwCo1hWImLGXtqqx\nMEygVg2JYWu6" +
       "f1Jwak2DaK3twS4Crb+hURCqd7BxGE+TrEDrwnIZvQRSCeUWqSLQ6rCYsgQx" +
       "Wx+K\nWVm0xuPJf05k/u6JoghgNonBJP44KHWFlCZInnBiGUQrXvVSp0b3ex" +
       "2aFatDwlpmcNOH+9K/ftyt\nZdqXkRnPHSKGyBrXd3R0Lg7+nIhJGPWO7VJJ" +
       "hYqTq6hm/JWBogPkXbNkUS6mgsVPJj7f/9S75Lco\nSoyiuGEzr2CNogSxzC" +
       "F/XAfjNLWInh3P510iRlENU1NxW32DO/KUEemOWhhTK28HYweLGTUuOgih\n" +
       "OmgRaM1I/2KyE2jlg0DnWbJPIgNDDoQbco1TQ5+kcrUoza2ci0TgVB3hDGNA" +
       "xxGbmYRnjdOXvn5i\n1+7nj+t4SY75QIDU2rz2Rck8ikSU2bWVzpLeN2WS/P" +
       "7eQPML29wPoig2hRK0UPAEzjECyYUZs+eI\nmRWKXS1lTFYEAvYlc0BE4HSW" +
       "gSFFfPDILFSVMOFKaToKIwwsWlz497sr2bmzkhsylm3SuoYGkTms\nsSW3TB" +
       "4YO3i8LyaF5mqko0F0462tZ40rJ/ac/f6bi5tLxBZoY1W+VWvKfAnDz3DbIC" +
       "bUo5L5V6+N\n/HGy9t73o6gGkhDKkMBAHsjprvAeFXkzENQg6axYGjXmbV7A" +
       "TC4FhaNBzHB7rjSjqJGUXbNmiXRW\nCKAqX1efiW+/8FHjZ+rEQaVrKiuJk0" +
       "TovGkp+XovJwTmL76WOfny5WOPKkf7nhYo7ng5Ro2iArIm\nAoFdtUwOp9a1" +
       "nXplyxsXgkiuKlkf5BzPy0AWn17sfP0L/CbkN+SZS48SlTpI7YSCDWR/pxpv" +
       "LVuU\n372+iCRXOD+GZYUPIlPIPf7XubcaejQYqdNevkef6jf5J5TjOwLLpc" +
       "2XNDjqvFHlVFX/2CN/Jp/D\n5w/o+tZamWC7LK9w99s/kC0PJI1lsjUhbGcb" +
       "I7OElU4W3m2PulGCozU93P3T8I7TT4aPFgOc3TfV\nzBots+0PxWbol1FJOj" +
       "8bqq60SqWBcsRAS07gRrbk2eVMvQpLt4LRBCBk2w6t1i+C6l8utjiyb/Wp\n" +
       "WxWDqIoBUM1Vz4TlmRDVoup71VIdTclSlHKJ4XEq5lNpnPN9CSKt5SL6krk1" +
       "yQYFqp/B7syQbRK3\n+gLMcFqgsqrqy+3Frnd+OXtpok3HVr8SNlRd1OU6+q" +
       "Wgtmp0JLt6b7aDkj6/tffMwsSPuagP8R6B\nYvBEUfB3Vjj/LmgJ3/mJ23K+" +
       "7Hbd1PHyc0R2Y7d24j5worD1UyeIRrOqCCoWZQuV+O+HtsLHv+L/\nwh/TCa" +
       "Kg384hDgIhyREPM1ctjztaZUKgupxtM4KtokANpTtWlr11VQ9j/Xwz+hYPbj" +
       "7ntHylroql\nJ1YdvHPyHmNlKVaebnGHkzxVYOr0DeCoP3gTNZbd8wLVyD+F" +
       "rqAljgB2LSG/uKOP/B+9d0wK7wsA\nAA==");
}
