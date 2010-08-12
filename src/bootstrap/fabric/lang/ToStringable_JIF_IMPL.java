package fabric.lang;

public interface ToStringable_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.ToStringable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.ToStringable
          jif$cast$fabric_lang_ToStringable(fabric.lang.security.Label arg1,
                                            java.lang.Object arg2);
        
        public _Proxy(ToStringable_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.ToStringable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.ToStringable
          jif$cast$fabric_lang_ToStringable(
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
          implements fabric.lang.ToStringable_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.lang.ToStringable_JIF_IMPL._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.ToStringable_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAAKVXW2xURRie3bbblm7ohVsDtD2FSnYVtkYjQTYGG6RhyxLW" +
       "tqgUyTJ7ds72wOyZ\nwzlz2i3YRqMR5MFEwVvi5cWExPAkUV9MNIJ3H0wfxB" +
       "dMDMaYKEQfjISg+M/Mnr22EOMmOzuX/5//\n9v3//Hv2CmpyHbTOwBmTxviM" +
       "TdzYMM4kkinsuCS7nWLXHYfdtB58fNWL2565/kkQoYKDNJvRmRxl\nvMhTR7" +
       "51/Y3pb46PrGlA7ROo3bTGOOamvp1ZnBT4BArnST5DHHcomyXZCdRpEZIdI4" +
       "6JqXkUCJk1\ngbpcM2dh7jnEHSUuo1OCsMv1bOJImf5mEoV1Zrnc8XTOHJej" +
       "juQhPIUHPW7SwaTp8ngShQyT0Kx7\nBM2hYBI1GRTngHBl0rdiUN44OCz2gX" +
       "yJCWo6BtaJz9J42LSyHPXVcpQsHtgFBMDanCd8kpVENVoY\nNlCXUoliKzc4" +
       "xh3TygFpE/NACkerF70UiFpsrB/GOZLmqLuWLqWOgKpVukWwcLSilkzeBDFb" +
       "XROz\nimjtCYX/Ppn6SwuiAOicJToV+oeAqbeGaZQYxCGWThTjNS92OrHPW6" +
       "tQsaKGWNEM3fHB3uQvH/Up\nmjUL0OzJHCI6T+s3Nq/tmR/6qbVBqNFiM9cU" +
       "UKiyXEY1VTyJF2wA78rSjeIw5h9+PPrZviffIb8G\nUWsChXRGvbyVQK3Eym" +
       "4vzpthnjQtonb3GIZLeAI1UrkVYnIN7jBMSoQ7mmBuWgbz5zbmk3JesG+q\n" +
       "zz/ii9THFANHyssPgUfH2QhgYEcB7IwJNGgRneVtuNvRcsQiDuYkG7Xtgrhy" +
       "6XQgAJatrc0yCpDc\nyWiWOGn9zOWvntix67kTKmYCZ0VlOOqHdHZMPSbwFh" +
       "tnCnE4Q0l6JDGcTuxOJVEgICWsqvadCEZW\n5Mxv78Y7nt/kvh9EDROo1czn" +
       "PS74IdcwpWyaZNNcgq2zAtjSUgBjOAO4BIFpChfJPCjYaAqKTC3+\nylmbgB" +
       "kGUM3P3fz2anr6nICKCO1ycbtSDQJ1WOkWjo4dGDl4Yl2DIJpuBDcLSwZuf3" +
       "tav3py97nv\nvr4UKeOco4G69KvnFOlTq37KYTrJQnkqX//K9Z2/n2q6/70g" +
       "aoSchKrEMWAJUry3VkZVGsX9kiSc\n1ZBEbQZz8piKI7+OLOGTDpsu70iUhM" +
       "XQoQAjnFWjoKxm154O3X3xw7ZPpcV+4WuvqJBjhKs06iz7\netwhBPYvvZo6" +
       "9dKV4/ulo4ue5ihkexlq6gWpyIoABHbZAikd615++uXo6xf9SC4r3z7kOHhG" +
       "BLLw\n1HzPa5/jNyDdIe1c8ygBoMBHSkK+ADFG5fzOikOx1ookAly1qTIsCr" +
       "4fmXzm2J/n31yiKWUEz+pK\nGf1yHFAWBjlqwRkAANYleDf4Qsp6lJgd1LNY" +
       "TZXvwfHH/gg/iy8cUJWvqzrXdlhe/r63vifRB8P6\nAjncypm9iZIpQstG1k" +
       "rbLd8a38r2R/t+HN58ZrbWyiDo2XdLzrTeObXm4YZJ84ugwF8xMeoeu2qm\n" +
       "eKXGgFCHwFttCdvFTouMUK9Uox2UWArfHgGgYnmUvzww99/K49Z7tty7VTvi" +
       "Ydc84jFOIq5sKzQF\nSS3DGCXY0g6ZxoCvJjMi+w3TwlRTRfEYzmdmZWVUM5" +
       "fonmPyGbVK4gyhciovSW7UFK/QqJZTvVqK\nmB0YjmrHTEOLMM0sSdYqay+c" +
       "V601XXtAi1RRsLimvKhVFnBfQ6XcXoEdh1Axd8dZBLwFiuYIVyxp\nwZKuqv" +
       "nJSHSjNCYany1dT10Sny09NwukgcQ+ZLvy8MLJGCziS6y7oJFZXGufpEOWAU" +
       "mg3Hf7LB/i\naGl1QN36tiTlmHl49aeKbckLvW//fO7y6HKVV6p3W1/XPlXy" +
       "qP5NCmyzRWb330qCpL5wV//ZudEf\nMsGiols4ai4iUBqyTYFfNAZgS3cZ/I" +
       "EN/6s3WCxYYhi+ZaDEMiGGXbd3+yPQRgi369jlA4thyw9r\n92INRwH60QUb" +
       "EPEmdNf9iVCtrr5u/mDkvN35pXxHS+1oM/SEhkdpRdGpLEAh2yGGKXVvVs+j" +
       "8heE\nvq1CPQ4NHvxIzXVFAXshRSFWk7ZvVVelVUW4/guc6Q18MQ0AAA==");
}
