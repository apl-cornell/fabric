package fabric.util;

public interface List_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.List_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.List jif$cast$fabric_util_List(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(List_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.List_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.List jif$cast$fabric_util_List(
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
          implements fabric.util.List_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.List_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.List_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAAKVXXWwUVRS+u223LV3pD38VaDtAJbsKrdFIkI3BBtmwZQlr" +
       "W1SKZLk7e2d74e7M\ndOZOuwVLNBpBHkwU/Ev8eTEhMTxJ1BcTjeC/D6YP4g" +
       "smBmNMFKIPRkJQPPfend3ttIUYJ9mZO3fO\nOfc753zn3LtnLqMG10FrDZyj" +
       "rI9P2cTtS+JcKp3Bjkvy2xh23RGYzerhx1e8uPWZa5+EESo5SLMt\nNlVgFi" +
       "/rzBHfsu765DfHBlfVodZR1ErNYY451bdZJiclPoqiRVLMEccdyOdJfhS1m4" +
       "Tkh4lDMaOH\nQdAyR1GHSwsm5p5D3CHiWmxCCHa4nk0cuaY/mUZR3TJd7ng6" +
       "txyXo7b0QTyB+z1OWX+aujyRRhGD\nEpZ3x9FRFE6jBoPhAgguT/te9EuL/U" +
       "kxD+KLKMB0DKwTX6X+EDXzHPUENSoe9+4EAVBtLBI+ZlWW\nqjcxTKAOBYlh" +
       "s9A/zB1qFkC0wfJgFY5WLmgUhJpsrB/CBZLlqDMol1GfQKpZhkWocLQsKCYt" +
       "Qc5W\nBnJWk63dkejfJzJ/aWEUAsx5ojOBPwJK3QGlIWIQh5g6UYpXvb5Tqb" +
       "3easWKZQFhJTNwxwd70r98\n1KNkVs0jszt3kOg8q1/ftLprZuCn5joBo8m2" +
       "XCqoMMtzmdVM+UuiZAN5l1csio99/sePhz7b++Q7\n5Ncwak6hiG4xr2imUD" +
       "Mx89vK40YYp6lJ1Oxuw3AJT6F6JqcilnyHcBiUERGOBhhT07D8sY35mByX\n" +
       "7Bvq+kf8kLqouHGkovwQRHTEGgQObC+Bn32CDVpMt4o22Ha0AjGJgznJx227" +
       "JEwungyFwLPVwSpj\nQMkdFssTJ6ufvvTVE9t3Pndc5UzwrAyGo9uhnB2qq3" +
       "iIEsgOppLZ1K5MGoVC0vKK2TETScgLwd/e\nTbQ9v9F9P4zqRlEzLRY9jnOM" +
       "QI1hxqxJks9ySbL2GkJLD4GE0RzwEaidZWBI8r9kowloLkHeVas1\nBSMMZJ" +
       "o5euPbK9nJs4IiIqVLhXUfvnlIYYvGh/cPHji+tk4ITdZDeIUnvbe2ntWvnN" +
       "h19ruvL8aq\n/Oaod07ZzdUUZROEn3EsneShLVXNv3Jtx+8nG+5/L4zqoRah" +
       "G3EMHILS7g6uMat8En4rEsGqS6MW\nw3KKmIlPfv9YxMcca7I6I9kRFbc2RR" +
       "QRrABA2cWuPh25+8KHLZ9Kj/2G11rTGYcJV+XTXo31iEMI\nzF98NXPypcvH" +
       "9slAlyPNUcT2cozqJQlkWQgSu2SeUu7rXHrq5fjrF/xMLqlaH3AcPCUSWXpq" +
       "puu1\nz/EbUOZQbi49TIAocMmVkL+AuMfl+M6aj+JdK4sIcgVLJCkavZ+ZYu" +
       "7In+feXKQpMEJnZe0aa+S9\nV3kY5qgJ54AAWJfkXe8vUsVRUXZQ10K9VO4D" +
       "xx77I/osPr9fdbyO2bW23fSK9731PYk/GNXnqd1m\nbtkbGZkgrOpkcLVdco" +
       "/xvWx9tOfH5KbT00Evw4Cz56aaWb19YtXDdWP0i7DgX7kw5mxys5UStYiB\n" +
       "oQ6BPdoUvouZJpmhbgmjFUAshl+XIFC5LconDx34b21xyz2b792ijXvYpeOe" +
       "xUnMlccJTVFSy1kW\nI9jUDlKj14dpGbF9BjUx01QzPIKLuek+0bDUyCW651" +
       "A+pd7SOEeYHEoj6Q2a0hWIgppqt1LC1v5k\nXDtCDS1mabSysiZYDvPyqena" +
       "A1pMzlgJTUWrjEkarSBRIPYIjpBxj05gRkw+YsUgLgCpQLhSygoW\nZWVXT8" +
       "fiGyTceGK6Ypi5JDFd2UjmIbpkN9SziuH85RYuM0i8d8ARZWG8vkibLHQpoA" +
       "J06zoe4Gjx\n7JS5cw8cGYcWYT+fKB84Xuh+++ezl4aWqspRp7J1cw5GtTrq" +
       "ZCYXbLFF7a652QpS+vxda84cHfoh\nFy4D3cxRY5lj0pGtit5iywdfOqv0Dq" +
       "3/X7v+QskSt+RNEyVeU+K289ZhfwQOCCLsOnZ5b5BTlXQG\njxAljm6bdZQQ" +
       "3b1zzt8AdVjV184ciJ2z27+UO2LlQNkIpzrDY6ymfdS2kojtEINKjI1qo1Nx" +
       "gRS3\n1MDhqF48JFJdSRSAzkpCvI3Zvhcdtbwt0/JfBa1b2/MMAAA=");
}
