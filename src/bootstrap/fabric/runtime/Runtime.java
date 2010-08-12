package fabric.runtime;


public interface Runtime extends fabric.lang.Object {
    
    public fabric.lang.security.Principal get$dynp();
    
    public fabric.lang.security.Principal set$dynp(
      fabric.lang.security.Principal val);
    
    public fabric.worker.Store get$local();
    
    public fabric.worker.Store set$local(fabric.worker.Store val);
    
    public java.io.PrintStream stderr(fabric.lang.security.Label l);
    
    public java.io.PrintStream stdout(fabric.lang.security.Label l);
    
    public java.io.InputStream stdin(fabric.lang.security.Label l);
    
    public java.io.PrintStream out();
    
    public java.io.InputStream in();
    
    public java.io.PrintStream err();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.runtime.Runtime
    {
        
        native public fabric.lang.security.Principal get$dynp();
        
        native public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val);
        
        native public fabric.worker.Store get$local();
        
        native public fabric.worker.Store set$local(fabric.worker.Store val);
        
        native public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal arg1)
              throws java.lang.SecurityException;
        
        native public java.io.PrintStream stderr(
          fabric.lang.security.Label arg1);
        
        native public java.io.PrintStream stdout(
          fabric.lang.security.Label arg1);
        
        native public java.io.InputStream stdin(
          fabric.lang.security.Label arg1);
        
        native public java.io.PrintStream out();
        
        native public java.io.InputStream in();
        
        native public java.io.PrintStream err();
        
        native public static int currentYear(
          fabric.lang.security.Principal arg1);
        
        native public static int currentMonth(
          fabric.lang.security.Principal arg1);
        
        native public static int currentDayOfMonth(
          fabric.lang.security.Principal arg1);
        
        native public static int currentHour(
          fabric.lang.security.Principal arg1);
        
        native public static int currentMinute(
          fabric.lang.security.Principal arg1);
        
        native public static void sleep(fabric.lang.security.Principal arg1,
                                        int arg2);
        
        public _Proxy(Runtime._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.runtime.Runtime
    {
        
        native public fabric.lang.security.Principal get$dynp();
        
        native public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val);
        
        native public fabric.worker.Store get$local();
        
        native public fabric.worker.Store set$local(fabric.worker.Store val);
        
        private _Impl(fabric.worker.Store $location,
                      fabric.lang.security.Label $label,
                      fabric.lang.security.Principal p) {
            super($location, $label);
        }
        
        native public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal p)
              throws java.lang.SecurityException;
        
        native private fabric.lang.security.Label defaultOutputLabel();
        
        native private fabric.lang.security.Label defaultInputLabel();
        
        native public java.io.PrintStream stderr(fabric.lang.security.Label l);
        
        native public java.io.PrintStream stdout(fabric.lang.security.Label l);
        
        native public java.io.InputStream stdin(fabric.lang.security.Label l);
        
        native public java.io.PrintStream out();
        
        native public java.io.InputStream in();
        
        native public java.io.PrintStream err();
        
        native public static int currentYear(
          fabric.lang.security.Principal dummy);
        
        native public static int currentMonth(
          fabric.lang.security.Principal dummy);
        
        native public static int currentDayOfMonth(
          fabric.lang.security.Principal dummy);
        
        native public static int currentHour(
          fabric.lang.security.Principal dummy);
        
        native public static int currentMinute(
          fabric.lang.security.Principal dummy);
        
        native public static void sleep(fabric.lang.security.Principal dummy,
                                        int s);
        
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
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public boolean get$_nativeOK();
        
        public boolean set$_nativeOK(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.runtime.Runtime._Static
        {
            
            native public boolean get$_nativeOK();
            
            native public boolean set$_nativeOK(boolean val);
            
            public _Proxy(fabric.runtime.Runtime._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.runtime.Runtime._Static
        {
            
            native public boolean get$_nativeOK();
            
            native public boolean set$_nativeOK(boolean val);
            
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
    final public static long jlc$SourceLastModified$fabil = 1281544308000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAMVYa2xURRSe3ba7tF0t2yIQ2lKeBqJufQLSGEWkobCkpcUH" +
       "VbLO3p1tR2bvvdw7\nd7tFrRqNgD80CiomCn8MJIaAkagxEjEIiq/E8AP8o4" +
       "li1MRH9IeRGI2eeWx39+5uq4kNTWY6O3Pm\nzDfnfOfMzD30E6pzHbQgjZOU" +
       "xfioTdxYN072xPuw45LUaoZddxP0JozgPbOeufmxP04EEco5aJ5t\nsdEhZn" +
       "E9p0x85cI/Rz7Zsa61BjUNoiZqDnDMqbHaMjnJ8UEUyZBMkjjuqlSKpAZR1C" +
       "QkNUAcihnd\nDoKWOYiaXTpkYu45xO0nrsWyQrDZ9WziyDXznXEUMSzT5Y5n" +
       "cMtxOZoevw9ncafHKeuMU5d3xVEo\nTQlLudvQGArGUV2a4SEQnBnP76JTau" +
       "zsFv0g3kABppPGBslPqd1KzRRHHf4Z4ztetB4EYGo4Q/iw\nNb5UrYmhAzUr" +
       "SAybQ50D3KHmEIjWWR6swtGcqkpBaJqNja14iCQ4mu2X61NDIFUvzSKmcHSZ" +
       "X0xq\nAp/N8fmsyFu9ochfT/T9Pi+IAoA5RQwm8Idg0lzfpH6SJg4xDaImXv" +
       "Bie3o2e22KFZf5hJXMqsVv\n3h7//niHkmmtINObvI8YPGH8uayt/cyqb+pr" +
       "BIxptuVSQYWSnUuv9umRrpwN5J05rlEMxvKD7/a/\nv/nhV8gPQVTfg0KGxb" +
       "yM2YPqiZlardthaMepSVRvbzrtEt6DapnsClnyN5gjTRkR5qiDNjXTVr5t\n" +
       "Yz4s2zkbIRSGEoDyNVJ/l4iKo8Z+z+Q0Q2KgxQZfQ6A51Oh0VG9n0WhO6Lp0" +
       "JBCALbX5w4sBF9da\nLEWchHHw/EcPrFm/a6dyliCYRgGMVupjWn1Mq0eBgF" +
       "Q7q9RSwvQpESE/vtY1/cmr3DeCqGYQ1dNM\nxuM4yQhEFmbMGiGpBJfUihbR" +
       "WLIHqBdJAguB0AkGiiTrwRxZSCl+thVitAdaGCh0Zuzvz35OjBwV\nxBCOnC" +
       "G0K2jglq0KW2TpwJZ19+5cUCOERmqFlUF00eTaE8bPT2w4evbjL5YUWM3Ror" +
       "JgK58pgsUP\nv8+xDJKCZFRQ//wfa3/ZXXfj60FUCxEIOYhjYA4E9Fz/GiVB" +
       "05VPQMJYNXHUmLacDGZiKJ81Gviw\nY40UeiQ1IrLdBAaIQKmDMktTbYaoxG" +
       "DUFnWzYpIwqG8TMr9deDR09bljjaekVfKpsKkoZw4QrgIr\nWvDHJocQ6P9i" +
       "b9/uZ3/acbd0hvYGR2HboVkMiV0inBkA77dUiPLY7Bl7nlv64rm8u1sK6lc5" +
       "Dh4V\n3s49cqb9hQ/wS5ABIBJdup3I4Apoxwv9LWBgTXNBxJhLDM+hfBRcRE" +
       "2D2pjlcYj6Ctm+UlhI6kDS\nOPO1iCCqP9a6xVGR93Imef9v7+1rmKcwizmt" +
       "Uk2NW54aSyYmjO3v3L7vwqf8S2nnAj2Ejnm58mXv\nwEXMXXE2Gw29uj8TRO" +
       "FBNF0eb9jkd2DmCS8MwgHlrtadcXRJyXjpYaMya9c4/dv81Cxa1k/MQmqB\n" +
       "tpAW7WkVuNgKJaq5GPVzMYBkY6WoFnPAMWrKjpgiqaiXlWibDaVZa2uuou0m" +
       "ra2OWQZmeVq0aFqM\nWM5W4sQGIKRJpVVaoAhGnNOrnKuyyq1yxkJZX67YHh" +
       "TtJRyFXHmfyXFUn4BLCs2S3vUVKAGUzFAx\nqk7Cp+e+/O3R8/0zVEJSV4qF" +
       "Zad68Rx1rZA2b7RzsML8iVaQ0ievmH9orP/LpDpum0tT/hrTy9yw\n/3Oy9J" +
       "aIUeH8CCctixFsSqvlyrcPwR6yvSSDnesAKsRYq44vANle7aYhAe6469fI4/" +
       "jklqAOxjgY\nkVv2VYxkCSvEpV/JBnmxypO16c6Or7qXHXzQH5hNsHzHhDMT" +
       "RjTburFmmJ4OSparwCi72ZVO6ioN\nhwaHwMXU3FQSFB3jBBO0vQZKuyZYe+" +
       "UEXZldG0XVXTmH6Two42dCiXxItMocK/PkgM6Ta3IGscUF\nSaLYwlHDEOH6" +
       "olDAdXdJTF4O5Tq9mesqbkYG5IR5d9KkPAypK0XS2GO81+O2x+M4ScaDe07F" +
       "nF8Q\nKQW8GMpyDXj5VAHeBjciDbjHzOMVA5YPUaM24ZhGNFYVUe9kbrcmh5" +
       "WT6Qmuis54YpQsoJY8Ijk8\nQAjOVIG4S0PcNaUQH1EQ4QEkfj1UAYtw4G6N" +
       "ZfeUYtkJxwhgoWaZtaRTq1qrDco+jXDff0T4ryn2\nNEc1E1hJnLwHNIYDU4" +
       "VhL0dBKo+Ep6qY4bCGcHiqIOwHMwCfK5lB5NprobylMbxVEcOU5NpihAfg\n" +
       "qQc5CW75fDPBKvLW2mreekAPcVcBuMilxzTwYxcH+GscRTTwDXBHlS/aIxWg" +
       "roByXEM9fnGgvg35\nVkO9DY/2pifEKzhxQuM9cXHwnixwYq3lOdWQXg/llE" +
       "Z66uIg/ZTDK0KTgJoeJxNhPa2xnv6/sAYL\nYvJVcGRywGdF0maE2P5Aq81a" +
       "NAUnYFjfaMRDc3bZx0r1Sc1YcObeJe/Z0Q/lC378s1c4jqalPcaK\nnz9F7Z" +
       "DtkDSVKMLqMWTLf99wdGnp5xdAoVsS3Xkl9x2cfUpO/Prezh87zcUXHPVuy/" +
       "0DndcJHZ8V\nAAA=");
}
