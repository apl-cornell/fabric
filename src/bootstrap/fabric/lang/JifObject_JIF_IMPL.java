package fabric.lang;

public interface JifObject_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.JifObject_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.JifObject
          jif$cast$fabric_lang_JifObject(fabric.lang.security.Label arg1,
                                         java.lang.Object arg2);
        
        public _Proxy(JifObject_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.JifObject_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.JifObject
          jif$cast$fabric_lang_JifObject(final fabric.lang.security.Label jif$L,
                                         final java.lang.Object o);
        
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
          implements fabric.lang.JifObject_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.lang.JifObject_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.JifObject_JIF_IMPL._Static
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
    final public static long jlc$SourceLastModified$fabil = 1281544053000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKVXXWxURRSe3bbblm7oDxQaoOUClewqbI1GgmwMNkjDliWs" +
       "bVEpkmX27tztwOy9\nl3vntluwRKMR5MFEwb/EnxcTEsOTRH0x0Qj++2D6IL" +
       "5gYjDGRCH6YCQExTMze3e3d1uIcZO9O3fm\nnJnvnPOdM2fPXkFNroPWGjhH" +
       "WYJP28RNDOFcKp3Bjkvy2xh23TGYzerhx5e/uPWZ65+EESo5SLMt\nNl1gFi" +
       "/r1IlvWXdj6pvjwysbUPs4aqfmKMec6tssk5MSH0fRIinmiOMO5vMkP446TU" +
       "Lyo8ShmNEj\nIGiZ46jLpQUTc88h7ghxLTYpBLtczyaOPNOfTKOobpkudzyd" +
       "W47LUUf6IJ7EAx6nbCBNXZ5Mo4hB\nCcu7h9ExFE6jJoPhAgguS/tWDMgdB4" +
       "bEPIgvogDTMbBOfJXGQ9TMc7Q6qFGxuH8nCIBqc5HwCaty\nVKOJYQJ1KUgM" +
       "m4WBUe5QswCiTZYHp3C0YsFNQajFxvohXCBZjnqCchm1BFKt0i1ChaPuoJjc" +
       "CWK2\nIhCzmmjtjkT/Ppn5SwujEGDOE50J/BFQ6gsojRCDOMTUiVK85iVOp/" +
       "Z6qxQrugPCSmbwjg/2pH/5\naLWSWTmPzO7cQaLzrH5j06re2cGfWhsEjBbb" +
       "cqmgwhzLZVQz5ZVkyQbyLqvsKBYT/uLHI5/tffId\n8msYtaZQRLeYVzRTqJ" +
       "WY+W3lcTOM09Qkana3YbiEp1Ajk1MRS76DOwzKiHBHE4ypaVj+2MZ8Qo5L\n" +
       "9k31+Ud8kfpQ8eBIefkh8OiYNQwc2F4COxOCDVpMt4o27O1oBWISB3OSj9t2" +
       "SWy5eCoUAstWBbOM\nASV3WCxPnKx+5vJXT2zf+dwJFTPBszIYjvognR2qJw" +
       "TfEsPUKPt3ODWUTe3KpFEoJLdfPtdxIhJ5\nkTC/vZvseH6j+34YNYyjVlos" +
       "ehznGIFEw4xZUySf5ZJpnTWslmYCE6M5ICXwO8tgI5kEJRtNQoUJ\nkq+asi" +
       "kYYWDU7LGb317NTp0TPBFxXSp2V9AgSocUtmh8dP/wgRNrG4TQVCP4WFjSf/" +
       "vds/rVk7vO\nfff1pViV5Bz11+VevabInSD8jGPpJA+1qbr9K9d3/H6q6f73" +
       "wqgREhJKEsdAJMjvvuAZc3Io6dcj\n4ayGNGozLKeImVjyi8giPuFYU9UZSZ" +
       "GoeHQotghnBQDKUnbt6cjdFz9s+1Ra7Fe99pryOEq4yqHO\nqq/HHEJg/tKr" +
       "mVMvXTm+Tzq67GmOIraXY1QvSSDdIQjsknnyOdGz9PTL8dcv+pFcUt190HHw" +
       "tAhk\n6anZ3tc+x29ArkPOufQIAaLAR56E/APEMy7Hd9YsinetLCLIFcyTIV" +
       "Ht/cgUc0f/PP/mIk2BETor\nas9YI5/9ysIwRy04BwTAuiTvev+QKo6KsoN6" +
       "Fyqo8jI4/tgf0Wfxhf2q7HXNzbXtple8763vSfzB\nqD5PArdyy97IyCRhVS" +
       "ODp+2SF41vZfujq38c2nRmJmhlGHCuvqVmVu+cXPlwwwT9Iiz4V06Muptu\n" +
       "rlKyFjEw1CFwUZvCdjHTIiPUJ2G0A4jF8O0VBCrXRvnLQ85/q41b7tl87xbt" +
       "sIddetizOIm5sqfQ\nFCW1nGUxgk3tIDX6fZiWEdtnUBMzTVXEo7iYm5FlUY" +
       "1consO5dPqLY1zhMmh3CS9QVO6AlFQU5VU\nJWztH4prR6mhxSyNVk7WKoUX" +
       "Fqsvmq49oMWqa1ZSU87Taou2D0xh2iMo4xAmxu6YFQMnAb4C4Uol\nK1Sy1T" +
       "qfjsU3SAPiyZnK3swlyZnK/TIP9SXfIcOVV+dPwHCZU+K9CzqXhSH7Ih0y9a" +
       "WAQnf7zB7k\naPHcILr1fUjGoUW45ifLfcgLfW//fO7yyFKVS6pZW1fXL9Xq" +
       "qIZNHthmi2xec6sTpPSFu9acPTby\nQy5cBrqZo+Yy66QhWxXhRScAtvRUCR" +
       "9a/7+agYWCJR5DtwyUeE2Jx87bu/0RuKmE23Xs8v55ieXH\ntHve9qIEbW59" +
       "ryHKf0/dnwXV0uprZw/EztudX8ors9J2NkPvZ3iM1dSX2loTsR1iUAm5Wd2E" +
       "yk0Q\n8bYaYBwaOfiRmHUlAXMRJSHeJmzfnq5ae8rG/AtAVTaDGQ0AAA==");
}
