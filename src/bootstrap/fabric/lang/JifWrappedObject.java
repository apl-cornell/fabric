package fabric.lang;


public interface JifWrappedObject
  extends fabric.lang.JifObject, fabric.lang.Object
{
    
    public fabric.lang.Object get$obj();
    
    public fabric.lang.Object set$obj(fabric.lang.Object val);
    
    public fabric.lang.JifWrappedObject fabric$lang$JifWrappedObject$(
      final fabric.lang.Object obj);
    
    public fabric.lang.Object unwrap();
    
    public fabric.lang.Object unwrap_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean equals(final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable o);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable o);
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifWrappedObject_L();
    
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
      implements fabric.lang.JifWrappedObject
    {
        
        native public fabric.lang.Object get$obj();
        
        native public fabric.lang.Object set$obj(fabric.lang.Object val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifWrappedObject_L();
        
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
        
        native public fabric.lang.JifWrappedObject
          fabric$lang$JifWrappedObject$(fabric.lang.Object arg1);
        
        native public fabric.lang.Object unwrap();
        
        native public fabric.lang.Object unwrap_remote(
          fabric.lang.security.Principal arg1);
        
        native public fabric.lang.Object unwrap$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
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
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.JifWrappedObject
          jif$cast$fabric_lang_JifWrappedObject(fabric.lang.security.Label arg1,
                                                java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(JifWrappedObject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.JifWrappedObject
    {
        
        native public fabric.lang.Object get$obj();
        
        native public fabric.lang.Object set$obj(fabric.lang.Object val);
        
        native public fabric.lang.JifWrappedObject
          fabric$lang$JifWrappedObject$(final fabric.lang.Object obj);
        
        native public fabric.lang.Object unwrap();
        
        native public fabric.lang.Object unwrap_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean equals(final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable o);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable o);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.JifWrappedObject
          jif$cast$fabric_lang_JifWrappedObject(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifWrappedObject_L();
        
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
          implements fabric.lang.JifWrappedObject._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public java.lang.String get$jlc$CompilerVersion$fabric();
            
            native public long get$jlc$SourceLastModified$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric$1();
            
            public _Proxy(fabric.lang.JifWrappedObject._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.JifWrappedObject._Static
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
    final public static long jlc$SourceLastModified$fabil = 1281623830000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAANW8eazsaHYfVq97pnumpq1Z1JIms2ieRm27Jxw1iyySRU5D" +
       "iKu4FJciq0gWySIV\npc21SBb3pbgoIydIYHlJnMVSFsCxAcGAAEM2bAtJgN" +
       "hI7NiJHccJDBmQ/Y+XwIYRw2v+CCIIThzW\nvff1e337zfSMLQ+gC5D1XfJb" +
       "ft/ZvnPqnnN/8R/NPl5Xs68GthMl7zRD4dfvMLbD7Q52Vfsemdh1\nfZyevu" +
       "e+8m/+0H/6b/z7v/Y/vjKb9dXsaZEnwznJm4cxH+r+jR/9Z91f/hn+i6/OPm" +
       "3NPh1lamM3\nkUvmWeP3jTV7I/VTx6/qtef5njX7bOb7nupXkZ1E49Qxz6zZ" +
       "5+ronNlNW/m14td5cr11/FzdFn51\nt+azh7vZG26e1U3Vuk1e1c3sM7vYvt" +
       "pg20QJuIvq5t3d7LUg8hOvLmc/PXtlN/t4kNjnqeMP7p7t\nArybEWRuz6fu" +
       "82iCWQW26z8b8rFLlHnN7CuPR7y/47eEqcM09PXUb8L8/aU+ltnTg9nn7iEl" +
       "dnYG\n1aaKsvPU9eN5O63SzL7wLSedOn2isN2Lffbfa2aff9zvcP9q6vXJO7" +
       "LchjSzH3jc7W6miWdfeMSz\nF7i1f+2N//f3HP6fp6/MnkyYPd9Nbvhfmwb9" +
       "8KNBih/4lZ+5/v3AX23f+VnObL90LxU/8KjzfZ/1\nb/5vtd3/+T985b7PF1" +
       "/SZ+/Evtu85/4z7Etf/uX13/3kqzcYnyjyOrqJwgd2fsfVw8Obd/tiEt4f\n" +
       "fH/G28t3nr38M8r/bP47f8T/B6/MPsnNXnPzpE0zbvZJP/PIh/brU3sXZf79" +
       "030Q1H7DzT6W3D16\nLb/7fSJHECX+jRwfn9pRFuTP2oXdhHftvpjNZq9P15" +
       "en62Oz+5+7z2b2Jh8FRmUXhe/d7/KdSdWK\nZnYAtXqSfzDv/Awsqvz2qgYn" +
       "wkdF7YNTnypywbpywarNmih9/9GdCL1szv4G5fu6J08minzpsXYm\nkyizee" +
       "L51XvuL/ydv/Rv08Lv/l33vL7J58MmmtmX7hd557bIO48XmT15cjf5D32Q3D" +
       "f+eTc1+4d/\n8t3P/L4fq/+bV2avWrNPRmnaNraT+JN62kkybdN7r7mTz8++" +
       "oAt3IjjJ7xvOJMqTVryXTBPdqc5E\n0+tklx6L7HNF56aWPcnhL//0P/8r//" +
       "i97pdu0nWThjdvs99Dm3h7ucf2xtfUn+R/++/66qu3Tt2N\nNbedvPXRs7/n" +
       "/uPfI/7Sr/yvf+Pt56rRzN76kMZ+eORN4x7DP1S563uTRXs+/X/+a+w//f0f" +
       "J/7r\nV2Yfm9R4MmSNPYnfZBV++PEaH9C8d59ZsRuxXt3NPhXkVWont1fPTM" +
       "+8Cau8e/7kTkDeuGt/+p/f\n//x/t+teXJ/8jnt5vbcK1LTNY85PlKT7SS/f" +
       "udH06dtunhaTLlRPz/4E0W5872tFcS93N8I/2uyd\nMf3Vf++1xV/705/6n+" +
       "6o98zufvoFA636zb0Wf/Y5346V70/P/8Z/cfj9P/ePfuYn7pj2wLVm9lrR\n" +
       "Oknk9ncb+cEnk5B8/0ssyjuff/Nn/7Ov/YG/9kwqvv/57OuqsoebUPT/7i9/" +
       "+b/8C/Z/NVmbSevr\naPTvFPnJg3zc5v/+yTq/qBO177ZV1Azv7GzHT55huN" +
       "2/ftf+sRsR78bP7ujyIw9dbrL8WCmZ25H0\nTBBS56f+7z/3B+dP7/Hexnzx" +
       "bppP1R82wR8Y+J47/vfaH/zV/635m3ckfi5Btzme9h9eVrdfEG78\nV66ffe" +
       "2P/6H0ldnr1uwzd8eonTW6nbQ3BljTQViTDw93s9/0gfcfPNTuTcS772vIlx" +
       "5L7wvLPpbd\n5zZoat9639qf+PbiOnvrXlzBF8SVufkwHy2vT2bFbdJ376Z+" +
       "6+7+W++l65VmAhZl9oT/tfrOX+mb\n2etdXl386q1n8vDmgzzcP37HuPu414" +
       "HbfXWPeJrts9P1lel67QHx3eft5Wfv1v/cMyD0h4FMYv7J\n6UxoJpL63oTh" +
       "1dyJn63/uRfl8Z7sH1r9JfT6D+/p9bU7ej3ztCb835ZSE46PL96B3lncZhU/" +
       "jPPV\nW/u33W5fu93WE9QvxIn7FvkwnT6dcNNJ/NY95Gc7+MydLt7hv/eFXs" +
       "B/u0n93Tnzfc+77fLJYfq9\nf/c//sv/0Y/+rUnM+dnHrzcRnKT7hbmk9uZR" +
       "/s5f/Lkvf+pn//bvvVOkiZL/evCn/ug/vc2q325y\nM/vyDaCat5Xr7+y6EX" +
       "MvmpxD7xnGD6vboYrSyaG4Png8/8kP/+G/90t/R3nz/jy4dwt/9EOe2Ytj\n" +
       "7l3DO3n+VNFPK/zIt1vhrvefB37kF39a+ZvOvcv0uQ+euHTWpugf+uv+137b" +
       "G+5LDvGPJflLSdo8\nnbFIza2f/ewhj1x2GuQZQSDr5Pp8MTecsk5I8iwzG2" +
       "Whn3ebaH/gNjTJVHJbr9qjtd3A1tKCpXNi\nziu3yBSmrZpLWTUJX1VmCXON" +
       "xeYWQTr2pWkSQC8TGYR2NnyAISIiGDvx0HFVdUE7Nry4TkH0Gjgt\nKq7mxJ" +
       "Jt/WA87E9e6rXo6oIkF0uwsdRc8gE7bOvLKhLjQ4lbxFav0S46lpvRUG2V1R" +
       "ZBDVc61uVX\nWIek+tzN2SN/bE3rqjf1FeWbAllYikEHzqGRUBiAwfS6tCtE" +
       "EkuOLqxKEsohHoe4ZASDMLuV3Ut6\nyjBwbgNcr85hnNRwLBW70tSyxECU3a" +
       "Lmw1OKrW2ekrQoonMJwlhNFUaRT5uuLoptwYULAd/ZR75E\nk/VF9ctywUaF" +
       "Mqeu/CEDQx6ApNNO6RyzLgzR0HG7yEghG/DjpSssrtCP21RVcRPZH8XlxV+r" +
       "JRqH\ngonudnvpkoUnhLDVcJ4yFROLcbdTE2NwC9eK0hNtarBNnaMLqiFbP9" +
       "CEsDUXVchBkZIhSJ5rXc5f\n3QXOD+oCHHbSumlVzTrH89g6qYSnUwc982ke" +
       "kfnYH11tVdHJalf0nCDirC9DREyoO/XMyl6yl7kr\naBRdlBo6tY6FvctIja" +
       "7Wi0GZA7q8s5VcqWHZCI+Z4EKUau+oyTb7w07vNVNzHHg8qRqm6OKRI9so\n" +
       "FlW2Hlku4TA1chJ6HAyd14D2As1jhTA21mWdH7NNLxvnYKQKSVoZtpqdRjFx" +
       "hXqlxCGRWMSAmFsd\nZifSXhxn24hLVzXx2lnGDS6ni1GPTnPp0kT2KkLoHA" +
       "Ing4QMaZBek9xdx9xWhYXtBmAcPV8JMBb5\npQ/L0Zr2ShPneNjL+aXbLp3l" +
       "UEdL9zCJzxyXhVQgEQE2lHKtH8RC4SHsSp/dZlwXY2i3mIruXbEg\n0ag7Mz" +
       "rDXdEDLLEi2Q+d2a9RNDlIeWbWtZZXczbzOEoURa4gBPx8GYk1ADK2TW73MS" +
       "pGDX/ihNY9\nm723SMJt2hoWEm8hJG5OQrG9XDQksS9hvPcTivGoOWfTKYd7" +
       "FUoO/VgqfqhWFyk12Quz89nzWY7z\nYYQxaa2zKSCQMrJXShDDABB0KyvCVr" +
       "661SSgvLRyym7npb6mL/jQHXGOXscxIpDtSj4hvMyLy6Yx\njACWMDSlyT3W" +
       "mmaMWSNZ1Mc9ycnONDVvWxFZ5hkuSU1JhXPVSMWSEvqoDNebUsi5mGnEbC+e" +
       "DE1a\nQNi625yyrMoWyAb0o8oESSln4GS9yaGDGm3Hk9YUQjGsVni90+dbBO" +
       "dlW4LpNVyjm/Mu3pUia8LX\nSbYGYr9YgSstHuhJ9x1KHPd7G7aHsyTZHm9Y" +
       "xw1Hl4RQJux6m+lr0ZkjiLwtciWi6aZT6D0p65wL\nDLFYrs2hgktnXanXTs" +
       "doSjUGUQ4X5bCDtQIONh6LGIRbB1cSPQRttj3Y1VyUtL3BsvCGiTp8FU52\n" +
       "UnLPZ7IRg2F5QFfgWLlRMS40S+RoG49tZscNZ2HVE/GlxhmyklW45jXBgIZR" +
       "mKu6vdb64hRGUVM3\nYQsJa4PCDzGiKcIZT7L6Wkm2RHT4+cwP57IkyA0ED5" +
       "w8HEFbo44jYAEncF2iec4A89S98LtW1MiI\npShbLy5xuof2IHhs90snq8by" +
       "3MgmpOqaGQcDbtKHBttsOGRPrxdEdz7trXxoW5Y9QnscmFdYdqFd\nJ3dhwz" +
       "rJrg2fWFREtSWIVgSb09g6vlhBDLPCZicnp72WW0NOQQOZt9mRV72mokq0VT" +
       "luRTbbOaiy\nLZPDaZSvU0KiABbZ1Vu7GN1uRFl+v0vho50jfeEaPenK1z2L" +
       "QME1EHLHA8HePS81uSVdHQ2FYlHO\nk4bSi8YWd5ejashcIhblWk3bLpa7fo" +
       "xXNHnzku1DTQgxPUp1Z/vLc4xKS+5IJ6SKHJPlhTcZwNnV\nYjbnTToLEsCp" +
       "LmeF6Tn0vJFYAD1YOm2eTRmArpGJ+yeb5Tlx5RgSj9PsmoQuy962NRoUes1g" +
       "96Gm\nJYqelHPUl3SV9XWY6RnIAYREbwdVxRKdS6eTZ9Pcvm0oanUt60MU5T" +
       "Dv6L3uMEu9l+HTkdPD4xCQ\nqXkUF+somGtT6KCVfMSNuA3ivtos7TZZnbPS" +
       "ATxzv4xtdrk4DlVQOqtrfkntOscGBPBwqZEEaisE\n+GEoU9AMJBTbz+2YM4" +
       "lghH098Jt9piiwpUTkItmRGYiEo8DtjwHPafxFPlXlSPINjdJB6WtKfi51\n" +
       "eXs12OMYXb3rFeSKuSRh4XZLlH5Jd+aik+H0DOhmM46eleiaBiiAb0B2oh3i" +
       "M6V0wlCeTY3MorRM\nRoDmw8QcMNVMJ+/g5MPz4BAXVbQIuImOXR/GK8XGcp" +
       "D1AAhpjysUgPGlVl0W5p4oF3q2ORbtRo61\nivWXW2pTat6YU2tre1SA/ZjN" +
       "4dw8OR7sUpRARRxBOao6GCItGDJF6qpsj41aJJqsJteDMfDOUHim\nJltoiX" +
       "ebVF6fKUG2xG1gQlnWl/MVv9UMwxTPnS/BW3FIak+J1khhUQjQ+7vLogt9a6" +
       "QUjuH20lZ1\nO2NyNkiPr6Qo3VTiMj6A4bIHcYzghDl20QpncLFzio4setqQ" +
       "O1Ht9oHSGs4aqnAcbOPVLlliWj7a\nCzmsRFaHhaLG/UAEnLZ0p5N+F2xMpR" +
       "HP63kgIQgFLUnbRjwXCCq52RK8EkmJKdK60lA4LpQbNal0\nOQ04X02RaqTJ" +
       "KrqQSLSMt1dC4DpmK5qYSLDcfBOzezI3MV3vB6hMlivf9XMGPYmiO3jXleO3" +
       "Kx+i\n+AoHl0sF4UqL7ytcnzYR7+I1Y/AtIybrjHLD/UGfmwHMa+JIRllZb+" +
       "l0AW/HXOcWiosxOeR0RELp\nyGGzT3uC0R1yv6kVWplIa6UD3R/iKBEXVgOK" +
       "V1APMGAemUIdyDG5cBj4uO0vvp4wXUqdpCWwQvPF\nCu5pKwyplT3Y6CEkgg" +
       "2+OrKXzMAMN8w4edFc9Oq0T/KruEbmlc+ljOjBHFmPoVAVcGPTcUbZRK4I\n" +
       "J17ihCzanBvIipcLEBeF3Q6AkzReKlQOYUwn+sfVqm5k8XC4Xru5RS/P1sBX" +
       "aBPhmz6aTB6021vm\nRUSPsH0YsB1IH0e0HtBwvGi0UivE4JpMeS32CRTbq7" +
       "Dm6hAZzh06FuQcNyZ7LR3DYpS1Q1DFZwBw\nI6VoyGioYbpH7dTJRLHYCzsi" +
       "Oh69hZ+ai8ji91osAyAOgP4hExwE9M2l482bM5ZCJZ45qyWEAXDs\n4F6CAa" +
       "ykMA2SLyctA3jZP6dFD5WMNTjqbkcQSQ9Ciyt7rLKrevL59uBjCIGLwbwqQD" +
       "zvnem0FOgE\ni8auCZJL0U5G20N7hYR98eDwBIzkJ2xUvXMuuwLppZMVlbPE" +
       "v4hrHbpQFLIPLCQV5lxkSSdq2U/M\njtEGI4A1IwkqqLfHnd5eNs4RFIfjEB" +
       "OdIYHwcncgVlpflZvd0ghtLDYWaiHk7WlBgdDBnVPyutwP\nsovJJnbZylZ/" +
       "sK48PLl2Dn9aJEkF6j4zefDjymc2B38wbCdyY2aJd8p+SdmXoRYa9QgJdVxT" +
       "6nw3\nuUheElcF4O/9fEUzLqOPu42RxGcXORfhEQGO+PKyhSGqYqEGgBc+qE" +
       "EoSY4qyQGh55xE3cgVv9iV\n0zbPxMl1rkddPiq6Goq1qk5Glt6cBJ6i9Epx" +
       "oCjdk9WJkcIha1Cg2Vebg9QsD13EgNfgrKyb/MjJ\nPjmI9Dzb0/7t1NU9db" +
       "sZJXovMQmwTCypsJSj1FKSoXskToAe4fkNAWU+0QoiIWQ73z9lU1h0bE7J\n" +
       "kGm5uNyg8ylEW10X0BEkEhgUu+qaeylrYv1W7lebUGIGbbMLbaXLrld1v5nO" +
       "pY3gYYikCTKktOYq\nIOO9ttiOVqRf5XmHLhC4d9wzVIiix6USke+1tLvGly" +
       "CmgTy4XpGMWAkHDbD0/ebixT5oggS2XCZl\nD0ZNTqYnbguq1TBMrntc6SkJ" +
       "lxmWngyvMozjqTkttGN8gZAiWF2rcomjkxMdS2pqNgvcqFE+Bjp1\nmPy4YB" +
       "scJ9Pm708HaBGtM2MuLkT6oi21ehX3/UauqqNwshWDUTj/wiV9sceVEbUjud" +
       "v1THse2ckr\nW0wiHaL0Pl/JRFW66zaB5HWsZON8TUaG2SFrZWjDKVgTShHa" +
       "Y3iWZczAh/hObNxJpJ2DuTTs3Uqm\nTlq7SO3qqmF5fYBapIEd3FR04NIcBG" +
       "uOFUnHuJKEVCetOnbbM+RhTtDHRUbpCYE0K0MBAeMk6eg2\nv0JnpfAvJISb" +
       "fJgDHbXizGop5TQDwWAug3MIJQZo0GF+pC8m7u7sFZFPZ0UwNAjqE7Z/BQOf" +
       "iq4Y\ncwgkaH1BmX4rUTDu+BCWJydc3aFwAWDR3h5Xxvx4tZrddA5fBmOZGb" +
       "G1WCtLTShLQ0i8SdGxoxU3\nl9PpctRSn8eT6xQO7iVxirbXdkXVw3FdNEoi" +
       "pCnmpcDcwlcVYwbjIhMLgTm0Ah6k00oCOEJXlJYd\nGUwx+sS2NWIxY1nsc6" +
       "JxlKsKj32BGHZ5LljWZYdsRTUCOB8BMq5bYgKtp7vWUHnRzs3O2NQXdWuj\n" +
       "4ECtKbWMkl5Us36dMhdy4Ufs7rzYQ3p4bY6OjvT7A8DStrPP5tkY7obSW8Cl" +
       "vVAtxtULp42HZjII\nuG9QcnVkpQ2MB01eDcYFu0L62rEXwvWge8chH7l2gK" +
       "Ej4JzHIsGHeeJXHQPEJxajEYSu9fwQmtaR\n1rIFe5XXVM1t1viS9iDrstxp" +
       "cHPkN3CC+AM/gsu2dJbjPpsCUc8ItYsHzBcsFoSCayxkBlJGXnQu\nm8Vghb" +
       "rWyFIhUXQzOXa+r0KEkjc1kJC8c8DXgFQncnF0ErdbL3NQBs4Sy4jynGB44z" +
       "hya3wUszAp\nq328kJIWOGzO190ewbCLyTBTmEBNBstfNARvjdCEymIX7WWr" +
       "bEkYhTAoAdKC21rmfHm9lCzkhFlu\noKWSxbkQUhkrQjp1pgZ9dz5uOlM1pe" +
       "h4ZpDN5JRtdypRre2DdhhQC2FCk10t1wtxEqMjP5cFmmJt\ni1sh6xGgrmfq" +
       "sJKZTrUpXsaQtdqu0Ym6C324uhvNor2gLin0Oow5LhokcjKN4x6ZFJa8UvJp" +
       "smdO\nJY/tqkfhGgDofbyTK5sSIU4Z6cUAqwtnjK1hTHqELZmtNwpWMvlQ4U" +
       "kw1rXULXeKzDGS0+E4y5fV\nPHDB9Qo2R0BKw0OtZgnNQRy7piBzHTACtymo" +
       "reYBVcSHOkw5G91TKq9p4whqSlMSydxNw3WwANRJ\nrZfzwNuFKalUAiYqHT" +
       "6ZEIOxfTS/2BB13i8We+dgOP7Z9KVkd+hYc5GABNMux1rc4DocYhaLZlcd\n" +
       "PQ/pGKvz0FYB+6j3fCAjNZSMlo3WSdSpVwipSVIsFAgZprNpRQNDrcviJR1P" +
       "4WlPXpk9zjItbeiA\nzvsVKFgNxM3ZgbIq2sbSGgZIXLM0vi+UUFgIwxQrUm" +
       "smiqbjT/d353F/KFbGgqB1Ir4OoQtsAOFg\n2hY7sipdUw0od3Px2i73x8nT" +
       "bnGd5YrG0xIulwSuckysw+BmzNa7+ugiA0c6lYbQcJrzzdgPN9+H\n8TIlGT" +
       "oTh/ApggJ2842PFrYFdmvuDBYqyaq9hxnXAHEx8wAW0ZHO7TRkKVDlDOEaag" +
       "umXUENpZgK\nwauKrR42+m5rpGczy+vtnEx7zAegqDKCFZszQS3SYpCd8Utz" +
       "1pfXYRP4Z5tMDAqhhnJzgZlyzRTU\n/oS2dT8xeCE5jDEx2N1NAU01d7S+NB" +
       "kZhQQyQDEzG89yFohFmPLXUsCk8MbhVvEGrVscgr2fdlYc\nBCRo2q2ykp2x" +
       "P0zB3kkrWcCOi/k52x");
    final public static java.lang.String jlc$ClassType$fabil$1 =
      ("05uhk0tEYWGTpoC+Egji6kA45wdC8R7l/tc0yqbqtga+JC\n80RolZ21ddJq" +
       "2/ob6UWuzv0MFI46xLN9hJYago0aTHmY6rVqsqErtWpIbQkdJ65WtaJDXUPV" +
       "2GUB\nF22UHuR9sGPHBW1EUS2Jk+Wat0bSuTJI1T3gdOM+t8kOYbgWtUhdtl" +
       "3FEA0fYU77rYFERGfhlruR\nRi6hRDrFUB42CUsth3wgrHrvg/OdFF+jzmhb" +
       "IwvwNQmcoiuHeAIDVtIKZDN2swRFVU4ofCEg0hJb\nRBUW22rvuttVeQlJeQ" +
       "p4p1NacfM+R+bAcbspa4dkM26pOLq7vzTRlvDO3rW0AD5cXfbYNXC3zqqP\n" +
       "Iy+47FkKgOk1mPBDnB2xE+UTysLU2HbTFce5IhhHIpyixCpjmmSZwpR51pp2" +
       "sKVscWLy7Qnb9qEX\nBvZmMOilEnO9DgMr6U7hy0yPF0RprkTC6D2unG8Tkh" +
       "bEwHMISIWWLTiF8+ElMFXPWSQSw5WnkbzG\nuUL1IshuaJRghDTksa0cHHsX" +
       "WcoXEzuSMWVOgO10DnNrbCUG4yHHKYpwpEbAGbRMNte61zLc5TO/\nv2jiKn" +
       "M1Lut4lvCtoUBZyd1dlrc/5fdyGcSJ6EyaSxFzYwz7Sy3KeWDvbG5oy3OSUR" +
       "fBse2YLsku\nDztepKXcM03pQtMKc7yEsH+y8NwNqwZcAajRLt0rwHk8dpkf" +
       "jq2fiLJWjMggIiet2JANEJzKCjvK\nMHKAYHcy0iYG2gazWiV+sAz69YpHsw" +
       "q8ElOo1yyxNYipyxBfxdjchCUoj9ayGWwiIdkTB17T6GU3\nHgoYjxmxLcEx" +
       "XRDB5NAmA3AZ3ebQE/Eh761xe1o5Wd6BLNIx8jmuiSms1oDr1oXKPTGSoLCR" +
       "tjjt\nh60XKTlAEsRarTsUQ474gl2bpglHJ7W6FmC52Yg6re0pS3fF+IgqDu" +
       "xZGTW/RmhmSIeE2l5lHtFN\nDnEKNzehk2N342rQnPAssNKugQlqMpxDvIu0" +
       "RbZEGm+zrzsDOTBh4BhyHwxgy80zoN4eYpACzTSQ\nwDiyINJdSrUY2oQjMC" +
       "LR9TUYc449+pm7q1OY3h7pnYMl6w0EGHR4LqodVrUoj1FIMKfrKdY+CZAV\n" +
       "WrWKwjYBF9LlgJjn40XF9sTpUKm65i1cgzouu1aCJfYwBPYibUT4JFzIg4Is" +
       "NfGgjBLvsPMpklqy\n7WSMIro6Kr42IKav8VHI5OkFtNVFSCqZmrbtyBpoWH" +
       "bHK2ie1gVXatBKmaihTbPv9Vhf7xrfnJ/J\n1ULqKmSNNF2EgQO/g4KTjfbc" +
       "Ep4ArxSV8ZY6gR72A3iiqc2mXRTOrvHyer1jlO7cnEJGthnPvuoD\nNTeJNY" +
       "q2MQWvHJfh+b1nEFcRdQ9nEtpTmroOY9xMYalYMkuyQkRGloPW5AzKj6nEAs" +
       "TKkToMYw+x\nAA3C3FMwsOp9O7zqXQiF+eaCapvczUxhmVGJZC8HjLRhMoSb" +
       "lQ2t00NHb710rEI3qWnW5Lj9efR5\nX7Zk+zD5ZzTsnfEzd5qcnOs5EiaX6X" +
       "gOBjRxt1ZfoI3IyVl6yEJzzZbL0t+ewrwirLTZ2is1EaBN\nYW9NGB2wtoaL" +
       "edmf7cnZc6Iqk0r76ux26RpYTr7ftOKYc4QeWvip5uCekc3TaVGl14TgisoD" +
       "dXnR\ngu3R3guAspcwzNHPc9jd4DYhoJ1+CII2lHKXzst2jV9tLTA1voEagr" +
       "xI2RT2wwQUBhcUuyA7IPWZ\nZAzl7trBbLnZHjHdrjpuzgBL/LTdwLArnsHe" +
       "HQkycRxPESb3HTuKfMATaGtrzCbdbMaiFdBdvGUx\nB1r4KtKHY4CNJ5cfDG" +
       "fIg247V685ZmukSNZ6yk6uDuiKS4EeuHo6N+uLUXJLnmf8S4Otswu+gUzw\n" +
       "MpnlhA+hpefodqCsdNRrHQ3mM3g5153tQs2hqoEFT4E65xou9jpc4abu2M2m" +
       "bC+MpheNBh40dXk0\n5WAo2WPZyi4DCJtVnaCoYxPcLmu6A9fP5cV5lbE7B7" +
       "FkZxXYNGfL3rHKGlcsmnCwl/zyIMS+6RZZ\nfzmuKjkQj4mEntJKa3v1iLWn" +
       "JCagca9PFlmaMwS6DTDNO51PFyI7uC4g9btCKvht0OwGe530vIbC\nAtAVg5" +
       "pT+1KN+PFS90m0RWRKJQcdKlHhOhTAkBvzydqS2/Na5P3gKqENL0GATpkDig" +
       "UK43YOCLO8\n7uU8gbhmhB8CewVntnFiN77lN0ULouqJaTK2b/Vwbc9TgVq3" +
       "q07pr5yzQs1ABfrOdLgegnhFlwr/\nGlwbArTb0t6eggK+8g3tOPAFd+oIGX" +
       "x6eS7Mk4LTtaWru3kGO/Iobe3Ryc4afMbLTYMiKNvpHrxS\nTWddLyV2MXmp" +
       "mqJvtFJS1/1F2Kbm+hzw4UkSY0PCyr5YhivT2sxT5ghIzkHwV6NnjnQ1egW6" +
       "yk9J\nV6Mg3dmteSlzJYIVGh3diyeJJucE3JJY5SXedcwKI+yeN3i5Yc2en5" +
       "8E/MSR8hRG1me/WgsLf493\n4cU29lUzgAI0ongf7ti9H0zxBbomBgBpRkxX" +
       "Wz1aB8mxLU/NzgvZ7Y5qr/OBPukGdjUAaDNkeKqb\nRpui67ykPGCx8NNWii" +
       "uPcS3romy3DlHl0y/OzmCgeNGvtMXqGOe9ZKwgC9iAxLynuxgBB0tQesDc\n" +
       "KzAi4ztRJTzsxCS0bR0SNjqyw8mHeEAD1i2QUKHIxJkV9w7FHCZj5iSkVkxR" +
       "GhYv53sLqPx6L5LY\nJfI5G5MqnS2SeBFk2IFQbqkAzkMexJt3iRrvp6/epz" +
       "/c3h1fljvwsT+Bbm1bwFaLbJs7JovtsIFm\nTNPLhdrdnAWJS6PaV9Qpxm9b" +
       "u1DnI4+XeYAsgKN5xWgj6ETstErWjtGIxKKXiHSD+CxSOQbkQGBq\n4PBpba" +
       "1WSlGDBx/ZCJHUqY65Uvyrnl3ml8mDxlpvna+qq5MJvMXCxRpUEfgwWQUbiA" +
       "Z92DRVEppc\nJ/RHr7dIcB1d+4NRAdjO2/oAq8ecT8bHq9TcsgO4UIGYyX2f" +
       "ANiK3C3OF59QhQ2UF8wVP5NBv5x8\nNZQVYzCh3APLc5Htrc4F3dh+0HC4Ii" +
       "4c7OzlaDrPMKcyKodwOd2CfWhHgVm75ljLtqi41dsdviGB\npRlKq5LqcGlP" +
       "muHBJREcN89ydKgcmRvx5TpkQAM4K/MECrp2C4fKksumA70dw9YEIJ/BD1C2" +
       "aYil\ntltNQQ4JIBAKo2RKDUcM7kjpsF2McNNpU+iPZEsAchCeBufFaeleGr" +
       "EyvBPIoKiunOywKs6wTYrX\nXBpkvBp5XTsWCwsO+kO9IuBMSnxkKapkqUnH" +
       "frNRclwmBbtvjXlfoTIqBHabSekUSXjwHjwZKeiq\nXIz3yXp9QeJABX0qsF" +
       "luV3hEHrg7mqS1qi9dfLe4hqyBEMGGH1eWNo+AONHq1rCvpbyilOJ6EBLx\n" +
       "iGj93ttfMHYoikVaSkofH/o4HC1HNzw7wuydEiaK7ZlWuS90ja1cvpG9Oblq" +
       "MaW3YE9DdgJamacB\nrstDMdYU5UnXSmXDcSBUdn+tDLZeLq0z0BKGrDkbcT" +
       "yd6XGMs1RknYjcycZ2CuKO8OTNGM50FqQS\nOYyEQZuiqQctJyfE0stL04d7" +
       "W6yLyk4G1wqJKjVJtb1pQtNGcLtoIBuDguWJ1+eFJYO6tiMMf+mE\nAnMKi7" +
       "JHcQ5aIDbseItxhTRESvk45rq+766brSA1Tm9V1l6qYMVstzGYpp5+zApyOw" +
       "cHhwul01LD\nOaSXlyKyt2uOD30ZTgDe0BTN7FM28+Goot0wrKdIzs2P3KXY" +
       "nHmrDuSTytVtMcCtzp+jOXzeAdhC\njSAdPOcATo9BSuJlA7ZB5kUg2oEn6p" +
       "AQ2KGuEFOSWm46oKS+AeRdXGW9rV0diAkEYIVmSRnPe9DM\n+GRJTG4amF7i" +
       "gNRWZAueEKwFN5TbndM4iwiaHuBm6+dwEuM2jkPLUQqTYNfwTYQLy5wD4m2+" +
       "Yxdz\n3FvF3WEr5M5pxaTLIKgCIrAjMTpNBtYQg5VqW4YuAtvDUVkRmGMChj" +
       "N5Qe1xe2CDjlJRah3u0Vt2\n04//+M12+Q/G7gdfZuzegh6bu2+ZHPv0XzQ5" +
       "9iHHL3xJsuFd7lwze72ooqt9q4qYfTWOggds791S\n2t57nIL93u42CPxeIM" +
       "5fkpV4a6cTzi++BOf3HGB7u93ypB+D4ahb+qFd3ZLPv4d4hgc8X3iMh7Xr\n" +
       "8HuM5ZvfijbH/D7j8jGeu+TE3/KQq/w8nfmLH0yF/oFH5QH3PL9lNH75W5WW" +
       "3GUz/szp/3rjd9p/\n/idfeciKfq+ZfbLJix9L/KufPE+QfjyJeFdJ8yxr+N" +
       "PGV/4PBvuFbz7OkP7ctPxXvu3I99zPXr8o\nvxpGf/GVu3Tj+wzlD5XyfHDQ" +
       "ux/MS55XftNW2fED2clfeT/X9xPT9VsfPmfPPl/M9b2j8gtEfpQz\n/uS5gg" +
       "kfnVX++5rZlx9M2I0Vbz02E289l7H/4AMQPz9dbzxAfOO7hPgMwEei+7lm9l" +
       "qbdROgu+28\nCOMlecl//17UF4qf5o1P2klyv5XqVhn2bdKTm9np17uC5hsQ" +
       "/HX8x2Ck+A659UwtfvilFQKHSc/c\nqLC/gyqBP9DMftM9xd6r7sjwIcI98O" +
       "+HpuszD4T7zL8q/v1CM/tEONksMvfuK0d+orgf9G81s1ej\nrPmNxFDsxtDl" +
       "d8rQ268//9EE+hPN7NPPCPQCw/7YSxj2hel684Ewb/6rYth/NzGsyZ+n0h9/" +
       "A3EI\nXtw4hP06c+jPThx6RpEXOHR8CYe++GAWZ88+v3ur/cwOfP5FO/CiF/" +
       "LRgP/SZDP9srWT+rHGve7k\neeLb2W8kniI3nq4+kqevPPeDf/52+98/mk6/" +
       "MlnKezq9wNa/+hK23q4vP9DmS98lW1/ABX6HuP7W\n+/z7EKBfV2ZZv/7Mwm" +
       "/MIr6OfyS7Xn1e8vPz3w1t/t53yrNbwdSTBxI9uSfRe99ledc3VstvPJ3W\n" +
       "qqOynVZ6+yG2enrNI+/pzRmOsqh5+2tPf+rpT/yk+vSb75cv3m7Vv5wV/ieT" +
       "FX62wmMl/tht/Uc7\n/r4HCf3gjp/87u/O55/498Ed39etPb0vknz6YDzutv" +
       "7Ms82Dt3/irsrt6b1w/JSdOt+8M1r3rWce\nzP1vd4WOd827SXZff3o/9obo" +
       "8ch7sbrvnP8kM5E5Cp6+nT+N3l/56WMZvLHi8bOn7tMff/r2h3rm\n7z69d8" +
       "KffuuKTO1WH1X5ya1dH/O3J8p9ZEj99bt9fe3db74/e1L77z6XjW8Ruj9UvH" +
       "2kBXlJ1duz\nuOmjROrXmtn3fZBzL7UuDyfXgyA9+S33qvMvWsj7L7fb6fbk" +
       "yUfu7Mlrzew333bm2nXzbdnzHNMH\nN/zmCxt++sy+lx+xiftKxW+P7iOhf+" +
       "YhrD77zbf7CuQ7xdx9LzD/a83sR16C+SXflHynsH/qewH7\nq7dqzQ/DfvSF" +
       "yncK+Xd8LyADL6f0S7536SeT8Fjab5Xqn//Qf1a5//8f7ld/+be//eeKz/4v" +
       "d/8p\n4P3/0fH6bvaJoE2SF2uoX2i/VlR+EN3t+/X7iuo7IjzBmtmnXjCjt6" +
       "rR6eO2pSfofQ9icmne/6PQ\nk2/cB/P9/w8DxjraMEYAAA==");
}
