package fabric.util;

public interface Iterator_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Iterator jif$cast$fabric_util_Iterator(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(Iterator_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Iterator_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Iterator jif$cast$fabric_util_Iterator(
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
          implements fabric.util.Iterator_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.Iterator_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Iterator_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAAKVXXWxURRSe3bbblm7oDwUaoO0FKtlVaI1GgmwMNkjDliWs" +
       "bVEpkmX27tztwOy9\nt/fObbdgiUYjyIOJgn+JPy8mJIYnifpiohH898H0QX" +
       "zBxGCMiUL0wUgIimdm9u5u77YQ4yZ7d+7M\nOTPfOec7Z86evYIaXAetM3CW" +
       "sn4+YxO3fwhnk6k0dlyS286w647BbEYPP77yxW3PXP8kjFDRQZpt\nsZk8s3" +
       "hJp0Z86/ob098cH15dh1rHUSs1RznmVN9umZwU+TiKFkghSxx3MJcjuXHUbh" +
       "KSGyUOxYwe\nAUHLHEcdLs2bmHsOcUeIa7EpIdjhejZx5Jn+ZApFdct0uePp" +
       "3HJcjtpSh/AUHvA4ZQMp6vJECkUM\nSljOnUTHUDiFGgyG8yC4IuVbMSB3HB" +
       "gS8yC+hAJMx8A68VXqD1Mzx1FvUKNscd8uEADVxgLhE1b5\nqHoTwwTqUJAY" +
       "NvMDo9yhZh5EGywPTuFo1aKbglCTjfXDOE8yHHUF5dJqCaSapVuECkfLg2Jy" +
       "J4jZ\nqkDMqqK1JxL9+2T6Ly2MQoA5R3Qm8EdAqSegNEIM4hBTJ0rxmtd/Or" +
       "nPW6NYsTwgrGQG7/hgb+qX\nj3qVzOoFZPZkDxGdZ/Qbm9d0zw3+1FwnYDTZ" +
       "lksFFeZZLqOaLq0kijaQd0V5R7HY7y9+PPLZviff\nIb+GUXMSRXSLeQUziZ" +
       "qJmdteGjfCOEVNomb3GIZLeBLVMzkVseQ7uMOgjAh3NMCYmoblj23MJ+S4\n" +
       "aN9Un3/EF6kPFQ+OlJcfAo+OWcPAgR1FsLNfsEGL6VbBhr0dLU9M4mBOcnHb" +
       "Lootl06HQmDZmmCW\nMaDkTovliJPRz1z+6okdu547oWImeFYCw1E3pLNDde" +
       "WPJBd7W05mODmUSe5Op1AoJHdfOd9vIhA5\nkS+/vZtoe36T+34Y1Y2jZloo" +
       "eBxnGYE8w4xZ0ySX4ZJo7VWkllYCEaNZ4CTQO8NgI5kDRRtNQYEJ\ncq+SsU" +
       "kYYSDU3LGb317NTJ8TNBFh7RS7K2gQpMMKWzQ+emD44Il1dUJouh5cLCzpu/" +
       "3uGf3qyd3n\nvvv6UqzCcY76alKvVlOkThB+2rF0koPSVNn+les7fz/VcP97" +
       "YVQP+QgViWPgEaR3T/CMeSmU8MuR\ncFZdCrUYllPATCz5NWQJn3Cs6cqMZE" +
       "hUPNoUWYSzAgBlJbv2dOTuix+2fCot9otea1V1HCVcpVB7\nxddjDiEwf+nV" +
       "9KmXrhzfLx1d8jRHEdvLMqoXJZDlIQjssgXSub+r8/TL8dcv+pFcVtl90HHw" +
       "jAhk\n8am57tc+x29AqkPKufQIAaLAR56E/APEMy7Hd1YtinetJCLIFUyTIV" +
       "Hs/cgUskf/PP/mEk2BETqr\nqs9YK599ysIwR004CwTAuiTvBv+QCo6ysoO6" +
       "F6un8i44/tgf0WfxhQOq6nXMz7Udple4763vSfzB\nqL5A/jZzy97EyBRhFS" +
       "ODp+2W94xvZeujvT8ObT4zG7QyDDh7b6mZ0dunVj9cN0G/CAv+lRKj5qKb\n" +
       "r5SoRgwMdQjc06awXcw0yQj1SBitAGIpfLsFgUqlUf7ykP3fSuPWe7bcu1Wb" +
       "9LBLJz2Lk5grWwpN\nUVLLWhYj2NQOUaPPh2kZsf0GNTHTVEE8igvZ2X5RsN" +
       "TIJbrnUD6j3lI4S5gcyk1SGzWlKxAFNdWN\npYStA0Nx7Sg1tJil0fLJml93" +
       "Ya081nTtAS1WXrESmvJcCZ88oIxKAdor+EImPTqFGTH5mBUDHwG8\nPOFKKS" +
       "MYlSlX+VQsvlHCjydmy5szlyRmy5fLAsSXbIf8Vj5dOP3CJUaJ9w5oWxbH7I" +
       "u0ycSXAsph\nt8/rQY6Wzg+hW9uEpB1agDt+qtSEvNDz9s/nLo90qkxSndr6" +
       "mmapWkd1a/LAFlvk8tpbnSClL9y1\n9uyxkR+y4RLQLRw1ljgnDdmm6C7aAL" +
       "Clq0L30Ib/1QksFizxGLploMRrUjx23d7tj0DTINyuY5f3\nLcQrP6SdC7UW" +
       "RWgGatoMUfm7av4mqGZWXzd3MHbebv9S3pblhrMRuj7DY6yqtFSXmYjtEINK" +
       "vI3q\nElQ+gnC3VMHiqF78SMS6ksgDtZWEeJuwfWs6qjlcoui/Ur+E3hMNAA" + "A=");
}
