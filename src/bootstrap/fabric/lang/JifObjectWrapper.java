package fabric.lang;

public interface JifObjectWrapper
  extends fabric.lang.JifObject, fabric.lang.Object
{
    
    public fabric.lang.Object get$obj();
    
    public fabric.lang.Object set$obj(fabric.lang.Object val);
    
    public fabric.lang.JifObjectWrapper fabric$lang$JifObjectWrapper$(
      final fabric.lang.Object obj);
    
    public fabric.lang.Object getObject();
    
    public fabric.lang.Object getObject_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean equals(final fabric.lang.IDComparable obj);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable obj);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable other);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl,
      final fabric.lang.IDComparable other);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifObjectWrapper_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifObject_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_JifObject_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_JifObject_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_IDComparable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_Hashable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_ToStringable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_ToStringable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_ToStringable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.JifObjectWrapper
    {
        
        native public fabric.lang.Object get$obj();
        
        native public fabric.lang.Object set$obj(fabric.lang.Object val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObjectWrapper_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        native public fabric.lang.JifObjectWrapper
          fabric$lang$JifObjectWrapper$(fabric.lang.Object arg1);
        
        native public fabric.lang.Object getObject();
        
        native public fabric.lang.Object getObject_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.Object getObject$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.IDComparable arg2);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.IDComparable arg2);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.security.Label arg2,
                                            fabric.lang.IDComparable arg3);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.IDComparable arg3);
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.JifObjectWrapper
          jif$cast$fabric_lang_JifObjectWrapper(fabric.lang.security.Label arg1,
                                                java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(JifObjectWrapper._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.JifObjectWrapper
    {
        
        native public fabric.lang.Object get$obj();
        
        native public fabric.lang.Object set$obj(fabric.lang.Object val);
        
        native public fabric.lang.JifObjectWrapper
          fabric$lang$JifObjectWrapper$(final fabric.lang.Object obj);
        
        native public fabric.lang.Object getObject();
        
        native public fabric.lang.Object getObject_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean equals(final fabric.lang.IDComparable obj);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable obj);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable other);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable other);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.JifObjectWrapper
          jif$cast$fabric_lang_JifObjectWrapper(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObjectWrapper_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
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
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.JifObjectWrapper._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public java.lang.String get$jlc$CompilerVersion$fabric();
            
            native public long get$jlc$SourceLastModified$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric$1();
            
            public _Proxy(fabric.lang.JifObjectWrapper._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.JifObjectWrapper._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public java.lang.String get$jlc$CompilerVersion$fabric();
            
            native public long get$jlc$SourceLastModified$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric$1();
            
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
    final public static long jlc$SourceLastModified$fabil = 1282916368000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAM17ecws2VVfvxl7xm4PHs/Y2I7tsZ/tIZmh7Kmq7lo9Yenu" +
       "6u7au7v2LscZqmvv\n2teuLmJEFrFKCYkhIRIBRUJCQvyRxEr4JyILBLKQKL" +
       "IiyD9AIhCKRECJlCgIQUj1t8x775s3HpuE\n4Jaq6n5V955z7rm/s9xP5/7U" +
       "b4/eXpWjT3jWIYxfqc+5W72ysg4Mv7XKynUWsVVVyvD2NfuJP/eB\nv/Etf+" +
       "X3/vkTo1FXju7nWXz246y+GfOG7p/55O+ffvG72Q8/OXrWHD0bpnJt1aG9yN" +
       "La7Wpz9Ezi\nJge3rGaO4zrm6LnUdR3ZLUMrDvuhY5aao+er0E+tuindSnKr" +
       "LG4vHZ+vmtwtr3jevuRHz9hZWtVl\nY9dZWdWj9/BHq7XApg5jkA+r+lV+9J" +
       "QXurFTFaPvGD3Bj97uxZY/dHw/fzsL8IoiuLq8H7qPw0HM\n0rNs93bI26Iw" +
       "derRx+6OeH3GL3JDh2Ho04lbB9nrrN6WWsOL0fPXIsVW6oNyXYapP3R9e9YM" +
       "XOrR\nh96U6NDpHbllR5bvvlaPPni33/b609DrnVdquQypR19/t9sVpWHNPn" +
       "RnzR5arc1Tz/zB923/1/0n\nRvcGmR3Xji/yPzUM+uidQZLruaWb2u71wN9t" +
       "XvlBZt985BoVX3+n83Wf2Tf8tMr/l3/yses+H35M\nn83h6Nr1a/bvYx954U" +
       "uz33jnkxcx3pFnVXiBwiMzv1rV7c2XV7t8AO/7X6d4+fjK7cd/Kv38/jt/\n" +
       "0v2tJ0bvZEZP2VncJCkzeqebOoub9tNDmw9T9/rtxvMqt2ZGb4uvXj2VXf09" +
       "qMMLY/eijrcP7TD1\nstt2btXBVbvLR6PR1w3XJ4fr6dH17+pZj97Hht719P" +
       "TSyodFemUwtbwefaoqbbBs0jpMXHB4VYb2\nNTwe17+7sHn36d69YbYfuWt5" +
       "8QBTOosdt3zN/olf/9d/Ycl97/dcr+MFezcC1qOPXDN55cLklbtM\nRvfuXR" +
       "H/wKOqvKyNczGh//oPXn3PX/109Y+eGD1pjt4ZJklTW4fYHUzPiuPs5Dqv1V" +
       "fYe+4hnF/B\na8DmM4cBpgPiX4sHQldmMeirHXzOXTg+MGJmaFkDxr70HX/4" +
       "73/ntdMXL8i5rPT7LtSvRRvWLbqW\n7ZmX5c+x3/Y9n3jy0un0tkHrl5m8+N" +
       "bUX7N/5/uEL/7Sv/mVlx7Avh69+AZrfOPIizXdFX9bZrbr\nDN7qAfm/9Xv0" +
       "f/vC28l/+MTobYOJDk6qtgZoDRb/0bs8HrGqV2891EVZT/Kjd3lZmVjx5dOt" +
       "WxnX\nQZmdHry5AsgzV+1n//D6978v1wWDlz+usfj8IkvyAczl/bU7yGHV7u" +
       "DSfvMbP/3Np6yM3PLFfHBM\ndphb8Wfe2PH+S0O/N3v//238V2M0n5lMP4Wg" +
       "n0aRly+//NqGLiC6s3BXTv93//JT0C//43f9iysk\n3MaHZx8KJLJbX3ub5x" +
       "5gUCldd3j/Kz+8/cIP/fZ3f/YKgDcIrEdP5c0hDu3ualHef28A/Hsf4/le\n" +
       "+eD7fvBvvvwjv3yL8Pc+oD4rS+t8AXj3F7/0wt/+BevvDF5x8E5V2LtXDufe" +
       "DdYv9N87RJGH7bty\n7aYM6/MrvHVw41sZLvdPX7VfuQDiavzoSi8fv+lysc" +
       "u7DmZ1CZ23oE4O3/4/f/ZHx/ev5b2M+fAV\nmXdVbwwVjwx8ze5/Rv3R3/23" +
       "9a9eqfiBNVxo3O/eyFazHjJU4pfa5576ez+WPDF62hy95yrcW2mt\nWXFzWQ" +
       "BzCNjV4uYlP/q6R74/GnyvUfLq69b+kbuW+BDbu3b4wJ8O7UvvS/sd16Z3uX" +
       "2iuzfKL40/\nezXiG67uL10D4ol6oBWm1sDyqeoqFerq0dM3uL9dwvfdLOH1" +
       "61f0q8c1bC934tq+B2qX64XheudN\nrLl6Xj4+d8X/+VtBqDcKcgWCbxyYP5" +
       "kdjreMn38YO9cqeojtzdwGTL8degV+Bbr8zb2R8pOX9rdc\nkb/cvnXg8aFj" +
       "bL94a8vakO4NYfnFa163rN9zBfgrxteJ0WMYD9h694NufDZkT9//Gz/wi3/t" +
       "k782\nYIkdvb29rPMAoYdoic0lvfyun/qhF971g//p+6/QOsz9G7/489/8nR" +
       "eq0uUm1KMXLgLKWVPaLm9V\ntZA54ZApOrcyvhHT2zJMhuyivUl//vpHf/w3" +
       "v/jr0vuuA8h1jvjJN6RpD4+5zhOvQPOuvBs4fPzL\ncbjq/XPAx3/qO6RfPV" +
       "znT88/GqKXaZOgP/Yf3Ze/9Rn7MVH/bXH2WJXW90c0UjGz25+oOQvjpGpm\n" +
       "0W5zZjcL5bm98HbyfD5cszmk+Jslc1rsZGYZ0T4Uq1nMWccGK7CG5mbZGIsI" +
       "QotU2nTMeD5dEGt6\naa9X9mq6UoefA9H0klZl+7zJsBJBaLo4LjYY3rfuVD" +
       "NoWGPZBQYaONC0dEmPSRzvQRw1DKWHFuZG\ni9hUlkrTXZX+2dR0QUElDZEI" +
       "uGR7f33OV2K+zlhHC/DiMC3jWCbWIK+DJwE6I/2YlZulVBqkwa0l\nscCkbq" +
       "VDuL2CEwB0t+CEQkFYyNiSNbVGNaNIj1cqp8IL0k/g+AxX6k4+t6uA5SRFU8" +
       "bMcqcsglqb\nrw7BGssjzmJOGZYfTc5PVI3nAMOKt8KBO3A7mz037W6fM/Xk" +
       "XCm2bHJdBGayG5HagrXzOTiGmwCs\neRgCnT6YyRZ4rCIjyypVC9aIHCZFtE" +
       "iLhEKkbcxvz0hiUjuY2HWwst4YaBDpBM8vworNoJhj4bGy\nBZbQXIpjyTKV" +
       "cx8tUX6/8Kc4C+dhy3S9lRuaMic3TNLIeh+sojBZBSK69iX7UPPAeR3pE85a" +
       "nnzk\nrI2hVtN2CmwaCTBfLvbTKji3zi5pVhwjLo5beRqJ3h6ICCibQ/0+PQ" +
       "UiiGnCMpPPxR7MlZ4J6pqL\ngei85KBxqChiFJcMP8t6fqP7+WrdroWmNtkW" +
       "qQRGqAZDzQ4OU8BdNKclKWHoiXn05chhTgkqCUSc\n7KauiyeTAhgvvMiksT" +
       "Xqu84y4wkDU5QFnETBlDjyHqscLds58ro27VeprkY9bBeLWtCh3ib6Y4hV\n" +
       "oLvOooMiELJZrcakXCOKH/cgGugGzZ7QtEFn8uKoQR23WMyJM5FLrt6qm84Y" +
       "iLAzH9xJiG6ZTrpt\nT42rl8ZkBxLHLSqgwbg6xuyBiZdov0A9Xg7UcrphHE" +
       "/b5XJa7ypr3W0IIbPZMA413qW9YD4R/cqn\nNlOeqaV5HfNKueYqCJlq+Pi4" +
       "Xu/bZtcnaXdeb7Nt2+lJOPczw0HlZA9JjKlKNkLkPSMve9bgeVs5\nM+kJJX" +
       "dzdWty8AFx8kWdE9uSt8b7cvAUqiT2qLecx8jGPoEEaqtln8NT5ASrx2KOc9" +
       "mSU7EztjbD\nqKcQFgG3AcJs6nKmmCKzSIFtOF3t9OlYiU6EFHccbQqltGDq" +
       "RVy1RKXs9dVanbThumI4TJscZZuA\nDZKLUivKoG14ln3rSIfUuhbSjWxIkF" +
       "ihE2qsZOmRgnEUlAiAOtjkLM+oKMQWkBzn0hopOR0muxDA\nnA1+XkMIt+M2" +
       "E2azxMrNLgqRWhBSa6+DBnTit5MxTk2BgAXZItxsBGp9OEykg8TpiZ0f1GVo" +
       "uLka\ntnrlK123qc2mRiuzZDMQW7EKuqhAzl4zDrlamyfRjI1xrVTSJB+wRH" +
       "DrrRjt53yxChcT1UzMebJt\nobxNtjRV7gQbOyl7NXcOvmBCxMlVmq01kVmk" +
       "a5hlr69BMPXGPUhWO9Iyj2BhMtvl0VxDnC4lSC7R\nk418TowB0IPV86s4dr" +
       "OzROvzVWNpfV4qCq/mteTDpU1hBB+a0nl81m0bcOV8nm09mxZnlJCFRbvz\n" +
       "HacKoWXlNb2JQ00LJgfbaywGJelO8VftkiN35Gqec+q57bdc2gKia40pwwHs" +
       "droqqHVxDv0oLw01\nPPHG0d9tCGkp0ia11ExV5A75EQED0INh5byj1xglxo" +
       "kGrdZ1DJD2Wm5QNB17EG0Pfj9d6Ev42OGL\n/Nym+6WAqhKIzJssNwgIB/eH" +
       "HSzswy0+a1egTgcrBkqCeReKcwrAq2lVuG1fwcG4aGv2rEJnXJkf\n9eB8UM" +
       "XzkkBcdI3aIgBMdfNkAn5c+uhJXnHGutCZTSMGWoJDTDTdsZv5Ep7ljuHJ2T" +
       "4b1z7QBbyW\noZMZvpWzrsIIp8J5Y3oWtgy1QinjvIgQdd/QUt1ku124jxah" +
       "m1qOmHAi6zGzHQNNclCQAiEc+4qi\nAknZoWVaC1K8MOWa92daZ1QhZitWTK" +
       "21MCNYe8Y0nExzle7OFzLtMJbfSeQO6OxY5AOGKBgrBsZz\nTT5UyyAzKOgU" +
       "rgmm8jSZ64DC6HRsiIWiOz9O+AVnC7YvHL3FWT7urVjaHQCdbg6nCpgeKqwr" +
       "ydke\niJmx1ZE0Bi+38bTTgXoe73sppvV+mgJuH+pmuGS8FQy0DU4F8Xw+1U" +
       "yKKkBl0pd43SMYTufwzJgg\n7infLNKxcA6piCaPq4U5WxXn9hQqsuynKK1p" +
       "xWyAyyxGzjiGxAaJklO1zloakuRJjmXKnFJkDOrS\nczlrXKscNv3j1Tax4x" +
       "WqmzCFIrMTMdOawAp2nMSZuyb1mZ5nuhUUdQLoFQBaAICL53OcXKARR2f2\n" +
       "weINmW5ce0OfE2bs8UeUT0qDDQi84yV9sRIShLPDPljnzXI5N/FKWbKhwAud" +
       "uCUlK+Hho0woW5Sd\n7QMyZ0x+vVxp7Wzp7KVxzjaZtmiGBKSfllaoVYW3CA" +
       "aUVEt1VkECMwuTddLzVYFKRF7nUhTHCcUI\nhugER561UnVuO6oQELtcWY3P" +
       "sr3z0OlM2clSVRAYspML8cDsGCU8aTu9qBEAIUGYBkvuFC4Ha65b\niYrmNV" +
       "4I57UIV5TYpwW96/ebOh0r3RSGWJWfYtiQVireojhsjgdzmScI2jOAB+0d3q" +
       "+AszSDzJ1w\nKpVixW8EqN4BIK4KvZtmTlbOZlEhZvtxEjh6v4+tAuSW88Vy" +
       "evajTqt2saGeQ9by4GyCC11P6ck+\nXmtRZXNdWWi8lMBBtHW2M9xydlqvHd" +
       "d9Sh7GPWBPxGhqEnnrmdtYi6frOWtwoqJ7622RyelKiD16\n7oSAqO1bOOI4" +
       "05ckTacWknWgTKioDpmNrTK4YMhxvNGQ7S5JOnKlTSFrVu2XDLU5CMpCZubm" +
       "xne2\nB389YCx3lWntKOwks316DjckEsHlYjEYIQWSNukaW7obe/o0WxuZAk" +
       "yqCJiGc/1kzCEEiThQx2cL\nJwjJIhJltBNZ9DhdHUg22KX1WSiXiFyEWmnt" +
       "POekN/mctVB9zAT6QmanTrlwp0CNEhgwNYq1ta3L\net3u24Au3AZ05yANps" +
       "f9RIB9E6BKNg7CDvUb+jDhN5JZ5UlQMy46JiWbzc6ELYI7Al0tdJgxARuT\n" +
       "8lZZiSVRw9TeFoL9+Xhiz+VC0CiaYndBRxrpkNLCJAk6PlrUs3iv01A/Rq3E" +
       "TD0o22Br4lSWDrSJ\n8gLVwk0UB3jbTdNjfJ7aAE1TC/oQV7QKVFsGrSZT5z" +
       "S5eNDaR1upbv2jgwTjIRkKyDkrK/ie66Fz\npBsYXXb4ZFCVApfAztjwrFuf" +
       "e9CscQ0ByBaKxJCignWq6QCfYXHU7+WJNhf2u3o8NXCIpJqmdPJw\nCHRcxC" +
       "98SqplV+XQSUDNSO3g7715Hxe7TB3MFLajVplnhE22RGrmslHEvXnoYP9guW" +
       "M00V24cQ0F\nxOGTRtawbSFBiMPzWpQPGKtVQ2Ss4bbjzzo6U+exe+roJoc2" +
       "K14Q9m3d0/oEiw7MNC+3+hgovAJO\nFofD3qY1c8ZCYHfOt/i5vMwrgM7eym" +
       "XbKOnmPsvX/Xwq42CCn5G9xxz9RUo4EAaY7G5NB0ueGcPg\n6pxXgb+elHCE" +
       "VFN6WpuuVNXQbE5DfKuuwvJ8tpzlVMnOk9SVVXo9FWAFEbQhUK8DA5KzNSW0" +
       "+x3J\nieOjDKcYANGRDfrOBsKPPRaAvWVbM2S168JiOS99SmwtCCO3qwMiQa" +
       "dzcIww+bDCVTUnd4qQzoj2\n3KJnnByDm9SIGt3C9QnfZ5jXaMHeXqzqDYwZ" +
       "ogg08WaNwRY+8YKW9kgJwwqp9LL1mi66dDk4JTeg\nNVmd84HeF2M1sQJb7o" +
       "vAEyHblTOZ46acjxNaHgKbBNRasdkf6Wi6dekJ3RWLNRuoOr8IlMoQBco9\n" +
       "YyV9oGt8s41Uc6wCUrTPAzxrNjxlEo1uQCsuRYAyaI7Ygud8c1O7pw2sAlFo" +
       "4RUngxnJOS01ZX1w\n1fUkUORThdCJojKDsY2z0Taoe3ZZ+zjkFdWUdz16mM" +
       "lGb+dBAK4c8RCL4ZoLDoqyPDdQ2F9CIHtY\nh9vCwyoFHtLWNZYdhAUxdg85" +
       "EeTb7ZyvCWRjHosNt7UICkwUZWYQoDQRZmC9gjnQZWJ1c14wybmH\nJ+U5Mc" +
       "kGxZYnYTKZNfRGd4l5NW5caKdDhSI7RHPmShs/1+4ZGPREFrtS2fGhw7L2Up" +
       "aUwTNbqw5d\n+PtWNg2tw6YLUnLIaWI7AHCO9XIWjiuKNCKdUaQDpq0QyN9L" +
       "5DBtNLQoBPOAlHGwItkeqDUdahgL\n9M3Jydp+DiMMCPQ2ZtsuP8SC4rQfNt" +
       "jpWIXcxFDlPTc5OV6y9nXDn/eEoUpcedwJnnU28TQbTEU/\nWtPsDJm9zsAx" +
       "RVk1nK4gRqMEBa/rcHtMG2k7lphcbDkeY/TKnp15LD9vF2bg+CVj5JPUro/t" +
       "VFva\nHgRK8sBX5jtg72hCBZ6z0LXWQrbttZ2J2Nr6mJRjOEid9rjbGYytea" +
       "ukyDhpj/tTrm3AvAUBiiJn\nlEcuEAgn5bDaLSOPKrYsNlsrgcNKqBG5x8CA" +
       "jwmD8otx1zN5wO47yY8p2zzLQq65HNL7XOCZbBWV\n7iFWO8LYBQGqBOmuVq" +
       "OYG9wRgbpn1iJMxWvPS6+E5yhAWGPSkiNbGDIocC0MqOFDTCizye64S2ge\n" +
       "XQptCyoWvrVo0fAskbTciVZshxR7ng6bGYZfEyRr7Uy+idRCTMbUiTseh6zA" +
       "2cxqTcpQCIit/cQM\ngN3B5PwIdjL1dNggyGTInCesIao6thUXfdyIIlKujP" +
       "K4xuIghlwHLYpxvS9izcr9DnOEOadZk0Q6\nThJG5N35lDbWEVyYibjnQ3E1" +
       "bKaY82xzWuCeJJ72i50YHhmD8+l6HoRQaznY2JSPLo6WNKlOy1bw\ntFqV2c" +
       "TSbTVE2SI6yWmjbQRrQYj7NGqydX5mMNdcrmu226uTODrVlYPM+OmCzrvZ2H" +
       "M9BALk+RY4\nzAqAsyN31+8EtZ715wWvVjak2qJgyIcoZdWpmiJIJW1W0wxd" +
       "wBLFB2TLHJDEshaOO8Pc8RwsMxuq\nQNmG0cW6AELR2cmqP4lOZDwL9bqjkS" +
       "qD+IOfdQWebWpmox2nS8g/WnUQsVRwZKaJHA95/PogjUNn\nq2A7uSGD/RRu" +
       "OHxltwfHEfyVYZaVptcCntfwEqasyea8iitNxlo14qNcsY+qhneU0a8qJLJP" +
       "dSiY\nY6Tg035m7mWElVs7LAFT6gqPycnSQvwQQjp1sVrr3GbfWMlkPdvGad" +
       "WzgTEDQ8/E9hKKV6YGwqzI\ntb47tofFsWB+7pxIe2IK3JJIUp6FC2clyqeD" +
       "R9GipPXbiJvyx+3eqHdbppwMu3C4XnLd+qDq9U5c\nIQRfZPxxM0aP9Jw6kS" +
       "zWr5WUGbZqu514wnGcR1uknehDLIDw2b5d5ouwxduWjsws28RiUA9+NAAg\n" +
       "02dYoQK2bLuZQuNdDjqpuAcJwYNbEIwmLFqS3qZYs0iBbZKTHEVqqJ6W3EDE" +
       "kM6iPIMXDIp1/IbT\nalkF3alB5qXOWxbajnfNHpDa6U63e2uGOqRw4vrtEd" +
       "ksexTTKjTTiuViyVUnUDHrYAmCpFjuV/oC\nnWJnqtBOQLxucrWnIRkz3HHA" +
       "5z0WaiwqJpkOdeFmHSl8JnKnCbxOWQRCWjlaMwFRdnUjADyUz5cH\nvhSaPN" +
       "iuh70hbs0DbJaVoBp4x7GLsgCuQonBbGPOCYjG3IoErfvBNMDgubj1EeVcOV" +
       "NR2/qYFCQH\nwUfx/bDznxKG0sA8rZYUlqwdNloA4HjYf6139AGv+ixmDbKl" +
       "YF5GtDYBmcSjIz6P0lVUBAWeFElo\nbwhaXokxM5/stUyR1154XEvaUpPm07" +
       "lpkGNgCBswweHN3FtO51OT3ZJLcVLUQqLLZda5VZQCxaEs\nXOKUAO4SZUQi" +
       "V5en0+LU5kGdnQMy9D");
    final public static java.lang.String jlc$ClassType$fabil$1 =
      ("fzehUTWTkG6JUnqMuDT6CSWfo1hqDs3jRhN2jMPkB9EUvY\nheUziWFPbefk" +
       "7ulJzGEApXdzMHZX4XbT2eBWEFaAWI9RgN0S4cInHNk49FsdRyi+VI4SU4bE" +
       "fFIr\nh5APgQyZEckESB1oBbUwb69mqbz1jEnI8d5przqXuMKR1DiBV7PzLL" +
       "BTf0jHJqywGrzJyUzTxt/v\nQfNs0idEz+ggTY6GonM1zYobUldqcMpCW//I" +
       "rpCaEydOYwpivxkHcyG2l6J6CNNS92gbX0aEbG6K\nslGpbr8XnCTzT2tEo8" +
       "UzGZ6yNtfbCRPPMjGtnUV88FAttaBN5VLDXm0MVmh+SvfbJKMMuN11LJ3R\n" +
       "KlJmItlBiYJNEKomNcGnFRWZFiGigdimgQgnbCWBzqHKAzrguFMIGD2tqvGg" +
       "CCw4LJYmtUVVCttE\nNaINmaWUOSHNYI4KK9Ml29FrlZv0BE1amxY5tjaAQm" +
       "oPEnAIFFwVFId5mRdwMU4dul5pkFgr+6Q/\nSFp1kLi50oPpDj8BW49CFU7c" +
       "EqdzAqLDjgc08VZpBXEN6qmLqJPokmko8xKTJF/UxphuBBV1QGce\nxAtV2R" +
       "4077xB+LhBrNPxeIydYY+7JcvaO3UBbdAcjgSKF9kQYExsLUUcfU0syqV3PP" +
       "irchwV9twc\nnH8VrOdqv8m7E6CX623brnc9aCxZkMrQiUUwZsrYKXQcvGw7" +
       "mcIbR+7xNJ9sgFPUlRm1Kc4h348F\nM94u51CgR1SknHbWKe+wUNDYXTSjGg" +
       "7YE/iqF4A0EbmuxVg47c1WwkhXFzcdAxCcKLnUVqFRrcG9\nbIz4rcEXNqgg" +
       "Q+5/6Ijdvsq0owio6vG8gw+8AgvVcRrC7GR9FhxnIWusNSXrCcm0hpYrisGB" +
       "/QrV\nMOTklGOliZ0tdUAqBzzrU2vPmmRBaLaQHGurNpKemEfWcpOvTf/cZV" +
       "OB3tuSuME9GI3KwnBWphVL\nRZ/tMQoXjmPfY8UhRz/oFBbrim0Vk6U6GDAN" +
       "5EdtW6nbJeD2lRzjPB5GO7HHeX0V0qZI13oxnwoU\nj+uxOLcOxCxN7fHKnK" +
       "xmBywwCEecFZtqCOZCtDOwJpipNb/xRF9g1uaWNbwhYRUrw1wX3tqF6MpG\n" +
       "UTOn1hjfnOZD3A4QURjrEAWuwFKddTHmtCIFzJsDPuNis07i3XFd4HEGT7FN" +
       "6JIna62Y6STI57m0\nPjrHkoIjNJ0sB7s3g6TjQmfclwsYOnHMwtmni25adr" +
       "YZhDnoE7W4IpMuOjtHtTsSx0MGo76BgPmC\npuaspjIaE87zZs5MhGp1xJUE" +
       "Magxqs2Pg0vaH5NMSpZALZzVIeWD9fosRpK1gCys22PMXgtKyOdQ\nZk4K8x" +
       "WhnmZTy/Rkx8j3DVgEVAZN8Y053llgXOazYy3rjX0+7HFhuSSc3EGP5CyFyx" +
       "3stiC0W/Hz\nmptbCsuxJO1kENfNI2eT67M5sJickjaasjKxGGt4LK1o3bKm" +
       "Sucek/NidW45dEszSDvrvZUqdda8\nwBYTZdVMuUMcTyZDImmITJy2jLPZzy" +
       "oMSK2c03Fv2NUFKQQoZAKcTI1Egj1eicuT5BM4QcySUxeS\nuH2idHzZIARC" +
       "VBTm8vms6AXNmdCDU/Ym5CE9enuPAhabIaCIsUt0ANzt+XZuzQAcq2Ybn6Rd" +
       "p9bn\nErk9T0uspIa9i9v7ayywlsRewoM5vV+XXg3sOFzVUcZVLX+5D+0xMR" +
       "ed/iDuenQrF+qCn8Bzu55s\nULnyZH83tZpaCHthTk42JFAe7E3pD/uVhXoK" +
       "vVQDmGgZhfEkYNa1bFjL8RyeMMt9Tm6OXuUOwFZ8\nzArwApGnFR3gfAU1rL" +
       "NDs9xJZlbZd/lRtQdA0lRhUwZezWYxF21y+HxYD55pzLiiDEwzKjg0k53G\n" +
       "LD37GDqCYFXUGbV3FsWSO2Rv722+R5CzjLSJqMUFokmLIo3zcHckZbxNdazb" +
       "DnnCOJrX+pqyzAPr\n0kCgVmSRD/jR8px24F0AFeS65yCuVSmTC8ggsyfnqK" +
       "cg/LTfmMhBgdGAlwzB0hmv2orjQ4tp9nYG\nBW6q9QvLGrZBpLZe1uJGTjOE" +
       "Xq9KiGzFM2Tg5cTYZ+DpsK0I2yMKej4LF3kz5FhIohezAnKtsTxs\npo6qa8" +
       "mM5swm3eCYuqYLRVEPGmXYCWusP93VlsNvwbjGmwww4A1Lt+nqMJ1158Rjj5" +
       "OD2nBNyjEC\nPF4Mm7o93BMSRdLrfYcR66pJB0cI4JpOAfguyneMEaDb2Nwx" +
       "pyIxkD7lkDWTQcPc8lkmnCytI5WY\no5ntZhx5sREtV8gs9PYFK0jb1RY2p3" +
       "MHUjXP2U9NDaJXG2VCicxm6vrB/Ow5kKGrRVUIqX6gtOWp\nkf1m4iK7FTSe" +
       "bPB5EoANTRMuJuWcb3iTQJQZerBfVcgaP+YgcsJEOzklIXLl786pVUCdXDeA" +
       "hSpg\nvVtEVKhNg7NiTMaZXp8oTZuovYGkcpNVQcvHp86u6orVjD0OL4eQXh" +
       "RzebrHM7ts2HXhFzC13QXG\nAYDZjbBS5gKJ++kUgcYZcXB2pkap27gGGfkY" +
       "nQkSoDaGLnH4FmwmFeBHiymhpfOz483jbRDhrh+j\nwGqlcY7ISDBJsTGY2X" +
       "zGMIPbnknS1rIgIeoNT5R0JZ44PAeaENvD2aLJUKERAsOETKFai4KIcRLt\n" +
       "zhYkWyZTtOiVDeF6mIQjMwycjGXPkslwvQGnCQMmBDpdRnSMgIfBExG0dykF" +
       "+PM3dRDvuyrUeL2W\n9br84fJt+7jagXt/GvFmbYafp5sy0TCRt9jMi3UUUz" +
       "J82DQM8QapVkQNADNuVrJEMVZYnCVCKu00\nDopSdXNQVvz+wOYLMCyXWaob" +
       "4UCEDHgAL701CjpEG8ceHtbCRh38sXOY4ryxa4ljfqSmY9s9DxpG\nLXcjzU" +
       "lZJzmHoCxP2VcTUDjqAADYZgZkpoPEko4gNeTCQwKi7E4IqJzSvhDlY2KJ+e" +
       "GEQhtxLMJT\n3CjxSbmwgD3ug0f9sIYiN6J9t4lBud5QXdujaE3mNi+EhyYx" +
       "zdaVWiwe8mhU6nBgP+ziSsRETC4f\nWx6R6hZT+LpWzmazb7oozLrR8Psfp+" +
       "EX4TfR8XX5jvOYOqKrIpt69HRehq11qaUefeIYejf0XrvU\nvrx2txjuNf4y" +
       "CHozLuGbFAn5A+0PvynttyCaXG7HevTRuwQY6lIPZJWX8tG3oJHd0PjQXRq0" +
       "VQVf\nwfjizWRQsutSo7s0ujfT93XN1E2J3IMqug8/WoH39Y+tsL3U+LzwZp" +
       "XXV/U9323892e+y/q5zz1x\nU4z32Xr0zjrLPx27rRs/qMu7S0S4KjS/LVZ7" +
       "Vv/Yf15hP/H5u4V5zw/sP/ZlR75mP9d+ePdkEP7L\nJ66q3K4L495Q6f7ooF" +
       "cfLYcbl27dlKnySFHcx16vV7vUp710qRK8qVd71916tSv1X25/5rGlivce\n" +
       "oJJ562LGv1SPXrixr8tSvHjXHl58AJjvfETEj9yU1o1un1+FiLcCvKV03zus" +
       "ru/WD6rsmGtJrqH7\nlSniFnEffWzN5/a2jPathfmBevSe14V5rXSTrHYfku" +
       "kR7XxsuN57o533/tEW8FbuDz4s98Me4a0l\n/uF69JRbNFZcXX3W8+sh5uAS" +
       "D1kWu1b6VajziQc2/oXL7Ufemv+P16Ovu+b/kLr+7mPU9anh+sCN\nuj7wVa" +
       "rrIbmgr1Cun3xdLw8J9BUp4ckH1Ztf+Go4/v2vVBMfvDGt0e3zj8OsfroevS" +
       "MY4sIic9y7\nyHgyTOuv3siuFPLWjP9ZPXr2lvFDiviZxyjiw8N1/0YR9/+4" +
       "FPELgyLq7EEt7faPbeb/bpj5LaeH\nZr69M/N3DNdzF7I3M7961vde+pM+S/" +
       "Andxbh5c/cH+ymCotmUNlLN5nc/TYLnfuXVCVMw/qll+9/\n+/3Pfk6+//mX" +
       "8/zBsh3/77DxHwZs3HK4ayRvu/C/s3TvHl3XmT+6dE9If9Kq/1pZuutK/vvX" +
       "Jz3u\n38SfqzW8zZMy76XPXtX937/m8+1Wcvj8Vdy7bt0G7eu/rk5rXDWviP" +
       "Cfun899lLXfnfktaDXnbPP\nrQa8hN79l7L74euc79+dzgVTd9/dt+9/0/2X" +
       "3tAze/X+dUp3/82PlaiX+vPSjS/tSslesl95653I\np67m9fKrn3+dely5rz" +
       "4A+Ztk4DcnCt4yWD7mVMFtFv5WtvFr9ejdj67cG+LZzUmqD95awu3JqsvH\n" +
       "T/4/msHl9ptvLe1v1aNvuEhrW1X9ZVX+QKZHJ/G+P8okHtkI/VGd0P+42ZEN" +
       "eeeX21l+Tcn8B/Xo\n44+R+TGb2a8lse89dTnh8kax7+yfv6ZEfvbxmn7Mlr" +
       "0bzPwu2i9H6D74hqPp1weo7U986dte+tn8\nuX91dRzz9UPOT/Ojd3hNHD98" +
       "uOuh9lN56Xrh1byfvj7qdaWEex+qR+96yDVeTtoMj8uU7v2p6x4v\nDBn56/" +
       "9Iu/fRqzSP6f4Pwm4w6HE/AAA=");
}
