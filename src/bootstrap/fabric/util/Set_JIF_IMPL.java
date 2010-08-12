package fabric.util;

public interface Set_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Set_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Set jif$cast$fabric_util_Set(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(Set_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Set_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Set jif$cast$fabric_util_Set(
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
          implements fabric.util.Set_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.Set_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Set_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAAKVWXWxURRSe3bbblm7oD38N0PYClewqbI1GgmwMNkjDliWs" +
       "bVEpkGX27tztwOy9\nt/fObbdgiUYjyIOJAv4k/ryYkBieJOqLiUbw3wfTB/" +
       "EFE4MxJgrRByMhKJ6Z2bu7vW0hxk323pm5\n55z5zjnfOTPnrqIG10FrDZyj" +
       "LMGnbOImBnAulc5gxyX5bQy77gisZvXw/hUvbX32xidhhEoO0myL\nTRWYxc" +
       "s6c8S3rLs5+c3xwVV1qHUUtVJzmGNO9W2WyUmJj6JokRRzxHH783mSH0XtJi" +
       "H5YeJQzOgR\nELTMUdTh0oKJuecQd4i4FpsQgh2uZxNH7ukvplFUt0yXO57O" +
       "LcflqC19CE/gPo9T1pemLk+mUcSg\nhOXdcXQMhdOowWC4AILL074XfdJi34" +
       "BYB/FFFGA6BtaJr1J/mJp5jnqCGhWPe3eCAKg2Fgkfsypb\n1ZsYFlCHgsSw" +
       "Wegb5g41CyDaYHmwC0crFzQKQk021g/jAsly1BmUy6hPINUswyJUOFoWFJOW" +
       "IGcr\nAzmrydbuSPTvk5m/tDAKAeY80ZnAHwGl7oDSEDGIQ0ydKMXrXuJ0aq" +
       "+3WrFiWUBYyfTf9cGe9C8f\n9SiZVfPI7M4dIjrP6jc3re6a6f+puU7AaLIt" +
       "lwoqzPJcZjVT/pIs2UDe5RWL4mPC//jx0Gd7n3qH\n/BpGzSkU0S3mFc0Uai" +
       "Zmflt53AjjNDWJWt1tGC7hKVTP5FLEknMIh0EZEeFogDE1Dcsf25iPyXHJ\n" +
       "vqV+/4g/Uj8qHhypKD8CER2xBoED20vgZ0KwQYvpVtEG245WICZxMCf5uG2X" +
       "hMnFk6EQeLY6WGUM\nKLnDYnniZPWzV756cvvO50+onAmelcFAvKCcHaqreA" +
       "wTnh1MDWRTuzJpFApJwytmh0zkIC9K5bd3\nk20vbHTfD6O6UdRMi0WP4xwj" +
       "UGKYMWuS5LNccqy9hs/SQeBgNAd0BGZnGRiS9C/ZaAJ6S5B21WJN\nwQgDl2" +
       "aO3fr2WnbyvGCIyOhSYV1Bg/wcVtii8eEDgwdPrK0TQpP1EF3hSe+drWf1ay" +
       "d3nf/u68ux\nKr056p1TdXM1RdUE4WccSyd56EpV86/c2PH7qYYH3wujeihF" +
       "aEYcA4WgsruDe8yqnqTfiUSw6tKo\nxbCcImbik98+FvExx5qsrkhyRMWjTf" +
       "FEBCsAUDax689E7r30Ycun0mO/37XWNEZghaqe9mqsRxxC\nYP3yq5lTZ64e" +
       "3ycDXY40RxHbyzGqlySQZSFI7JJ5KjnRufT0y/HXL/mZXFK13u84eEoksvT0" +
       "TNdr\nn+M3oMqh2lx6hABR4Cd3Qv4G4hmX47trPoq5VhYR5ApWyIDo835mir" +
       "mjf154c5GmwAidlbV7rJHP\nXuVhmKMmnAMCYF2Sd72/SRVHRdlBXQu1UnkM" +
       "HH/ij+hz+OIB1fA6ZtfadtMrPvDW9yT+cFSfp3Sb\nuWVvZGSCsKqTwd12yS" +
       "PG97L18Z4fBzadnQ56GQacPbfVzOrtE6serRujX4QF/8qFMeeMm62UrEUM\n" +
       "DHUIHNGm8F2sNMkMdUsYrQBiMfy7BIHKXVG+eWj/f+uKW+7bfP8WbdzDLh33" +
       "LE5irrxNaIqSWs6y\nGMGmdogavT5My4jtM6iJmaZ64VFczE0nRMNSI5fonk" +
       "P5lJqlcY4wOZRG0hs0pSsQBTXVYaWErQMD\nce0oNbSYpdHKzhoUESyLl6Zr" +
       "D2kxMbeSmgpVGZC0WIGhEOwRBCHjHp3AjJh8xIpBUABPgXCllBUU\nyoqOno" +
       "7FN0io8eR0xS5zSXK6cobMQ3LJbKhlFb/5Sy1cZo+Yd8DtZGG4vkibLHIpoI" +
       "Jz5xru52jx\n7HS5c+8aGYcW4SifKN81Xux+++fzV4aWqqpRF7J1c+5EtTrq" +
       "UiY3bLFF3a653Q5S+uI9a84dG/oh\nFy4D3cxRY5lf0pGtitritAdfOqvUDq" +
       "3/Xwf+QskSj4HbJkpMU+Kx885hfwzuBiLsOnZ5b4BSfjZb\nA5eHEkfR2juE" +
       "aOudc67/6pKqr505GLtgt38pj8LKRbIRbnOGx1hN36jtIRHbIQaVABvVCaeC" +
       "Avlt\nqQHDUb14SZy6kigAl5WEmI3Zvg8dtaQtc/JfBH5ZHOsMAAA=");
}
