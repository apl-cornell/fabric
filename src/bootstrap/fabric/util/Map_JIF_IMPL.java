package fabric.util;

public interface Map_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Map_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.Map jif$cast$fabric_util_Map(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        public _Proxy(Map_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Map_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.Map jif$cast$fabric_util_Map(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
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
          implements fabric.util.Map_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.Map_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Map_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAAK1XXWxURRSebtttSzf0h78GaLlAbXYVWqORIBuDDdK47Tas" +
       "balSJMvs3bnbobP3\n3t47t92CJRqNIA8mCv4l/ryYkBieJOqLiUbw3wfTB/" +
       "EFE4MxJgrRByMhKJ6Z2bu7vW0h/txk586d\nOefM+fnOmbNnLqNa10GbDJyh" +
       "rJvP2MTt7sOZRDKFHZdkdzHsuiOwmtZDj655fudT1z4KIVRwkGZb\nbCbHLF" +
       "7kWUC+Y/P16a+O9a+rRk1jqImawxxzqu+yTE4KfAxF8iSfIY7bm82S7BhqMQ" +
       "nJDhOHYkYP\nA6FljqFWl+ZMzD2HuEPEtdiUIGx1PZs48kx/MYkiumW63PF0" +
       "bjkuR83JQ3gK93icsp4kdXk8icIG\nJSzrTqKjKJREtQbDOSBcnfSt6JESe/" +
       "rEOpAvo6CmY2Cd+Cw1E9TMcrQhyFGyuHMACIC1Lk/4uFU6\nqsbEsIBalUoM" +
       "m7meYe5QMwektZYHp3C0dkmhQFRvY30C50iao7YgXUptAVWDdItg4WhVkExK" +
       "gpit\nDcSsIlp7wpE/T6T+0EKoCnTOEp0J/cPA1BFgGiIGcYipE8V41es+ld" +
       "jnrVeoWBUgVjS9t723N/nT\nBxsUzbpFaPZkDhGdp/Xr29a3z/X+0FAt1Ki3" +
       "LZcKKMyzXEY1VdyJF2wA7+qSRLHZ7W9+OPTJvsff\nIj+HUEMChXWLeXkzgR" +
       "qImd1VnNfBPElNolb3GIZLeALVMLkUtuQ3uMOgjAh31MKcmoblz23Mx+W8\n" +
       "YN9Qz1/ih9RDxcCR8vID4NERqx8wsLsAdnYLNGhR3crbINvRcsQkDuYkG7Pt" +
       "ghC5fLqqCixbH8wy\nBpB80GJZ4qT105e+eGz3wDPHVcwEzorKgL8gnR2qK3" +
       "8MYjvdn+hLJwZTSVRVJQWvme8yEYOsSJVf\n3o43P7vVfTeEqsdQA83nPY4z" +
       "jECKYcasaZJNc4mxlgo8SwMBg5EMwBGQnWYgSMK/YKMpqC1B2JWT\nNQEzDF" +
       "iaO3rj6yvp6bMCISKiK4V0pRrEZ0LpFokNH+g/eHxTtSCargHvCks6by09rV" +
       "85MXj2my8v\nRsvw5qhzQdYt5BRZE1Q/5Vg6yUJVKot/6dqDv56svfedEKqB" +
       "VIRixDFACDK7I3jGvOyJ+5VIOKs6\niRoNy8ljJrb88rGMjzvWdHlFgiMihm" +
       "aFE+GsgIKyiF19MnznhfcbP5YW+/WuqaIwDhOusqel7OsR\nhxBYv/hy6uQL" +
       "l4/tl44uepqjsO1lGNULUpFVVRDYFYtkcnfbylMvxl694EdyRVl6r+PgGRHI" +
       "whNz\n7a98il+DLIdsc+lhAkCBR56E/APEGJPz2ys2xbdWJBHgCmZIn6jzfm" +
       "TymSO/n3t9maaUETxrK8/Y\nKMdOZWGIo3qcAQBgXYK3yz+krEeJ2UHtS5VS" +
       "eQ0ce+S3yNP4/AFV8Frn59pu08vf88a3JHZ/RF8k\ndRu4ZW9lZIqwspHB0w" +
       "blFeNb2fTwhu/7tp2eDVoZAj033JQzrbdMrXuoepx+FhL4KybGgjtuPlO8\n" +
       "UmNAqEPgijaF7WKlXkaoQ6rRBEosh1+7AFCxKso3D634Z1Vxx13b796hTXrY" +
       "pZOexUnUld2EpiCp\nZSyLEWxqh6jR6atpGdH9BjUx01QtPILzmdluUbDUzC" +
       "W651A+o76SOEOYnEohA1u0f8076vMKa4Kc\n6qJTxNaBvph2hBpa1NJoSWsN" +
       "yjUsi5ema/dpUfFtxUs2WhOwCGWKxOUMhq6uoprynJJySq+9AnJk\n0qNTmB" +
       "GTj1hRcDNomSNcMaUFKNPijhiIxrZI42P/u+jRoujRWFzBBYTHZ4tTAzOXxG" +
       "dLV98iuSkT\nEkqQCvviFaJaklbL71ZoqpZWXFAkfLpmWaAklQrOretPL0fL" +
       "50PNXdgnpRyahzZkqtgnPdfx5o9n\nLw2tVBmvmsnNC/q5Sh7VUMoDG21Rcz" +
       "be7ARJff6OjWeODn2XCRUV3c5RXRE30pCdKi1FpwK2tJXT\nsqrrPzUrS0VM" +
       "DH03jZYMRWkYuLXvR6G5Eb7Xscs7AzDzQ9oU6H4KHEUqmyBxL7Ut+P+iumx9" +
       "09zB\n6Dm75XN5l5c64TpoRw2PsYrCV1kEw7ZDDCoVrFNXtPIMBLmxQhmOas" +
       "RL6qkrihygWlGIr/GSDa2V\n8C0C828Ict21rA0AAA==");
}
