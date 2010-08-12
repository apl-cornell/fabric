package fabric.util;


public interface LinkedHashSet extends fabric.util.HashSet {
    
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
      final int initialCapacity, final float loadFactor)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
      final int initialCapacity)
          throws java.lang.IllegalArgumentException;
    
    public fabric.lang.security.Label get$jif$fabric_util_LinkedHashSet_L();
    
    public static class _Proxy extends fabric.util.HashSet._Proxy
      implements fabric.util.LinkedHashSet
    {
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedHashSet_L();
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1, float arg2)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.LinkedHashSet
          jif$cast$fabric_util_LinkedHashSet(fabric.lang.security.Label arg1,
                                             java.lang.Object arg2);
        
        public _Proxy(LinkedHashSet._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.util.HashSet._Impl
      implements fabric.util.LinkedHashSet
    {
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          final int initialCapacity, final float loadFactor)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.LinkedHashSet fabric$util$LinkedHashSet$(
          final int initialCapacity)
              throws java.lang.IllegalArgumentException;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label, jif$L);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.LinkedHashSet
          jif$cast$fabric_util_LinkedHashSet(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_util_LinkedHashSet_L();
        
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
          implements fabric.util.LinkedHashSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public java.lang.String get$jlc$CompilerVersion$fabric();
            
            native public long get$jlc$SourceLastModified$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric();
            
            public _Proxy(fabric.util.LinkedHashSet._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.LinkedHashSet._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public java.lang.String get$jlc$CompilerVersion$fabric();
            
            native public long get$jlc$SourceLastModified$fabric();
            
            native public java.lang.String get$jlc$ClassType$fabric();
            
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
      ("H4sIAAAAAAAAAKVae8zj2FXPzOzM7GannZ3dbbvso/t1O5QZ3I6d2HHiHSEa" +
       "J07i2Ekc23Ecl9XU\n7zh+P+Nk2S0IaEsrXn0AlaAVElIltEiFCvgHAaLlXS" +
       "S0f7T80wJqhZCgFfyBqKpCsZPvm/m+b2Zn\nWRrJzs31ueeex++ce31uXvlm" +
       "5XwcVZ4zZMVybiSbQI9v9GSFpBk5inWt48hxzBe9t9SzP/a2j/3o\nT3/nT8" +
       "9WKnlUOQh8Z2M6fnI45i7y59/53fWXPjR86lzlslS5bHlcIieW2vG9RM8TqX" +
       "LJ1V1Fj+K2\npumaVLni6brG6ZElO9a2IPQ9qfJobJmenKSRHrN67DtZSfho" +
       "nAZ6tJvzqJOuXFJ9L06iVE38KE4q\nj9ArOZPBNLEckLbi5CZduWBYuqPFYe" +
       "Xlylm6ct5wZLMgfCt9pAW44wj2yv6CvGoVYkaGrOpHQx6w\nLU9LKs+eHnFb" +
       "46tUQVAMvejqydK/PdUDnlx0VB7di+TInglySWR5ZkF63k+LWZLKk6/JtCB6" +
       "MJBV\nWzb1W0nlidN0zP5RQfXQzizlkKTyltNkO06Fz5485bNj3ppcuPTfH2" +
       "H+6+Bs5Uwhs6arTin/hWLQ\n208NYnVDj3RP1fcDv53e+AS5SJ/eo+Itp4j3" +
       "NO0f/IMZ/S9//Oye5ql70EyUla4mt9Tvok8/82r7\nGw+dK8V4MPBjq4TCCc" +
       "13XmUOn9zMgwK8b73NsXx44+jhn7B/vviJ39L/9WzlIbJyQfWd1PXIykO6\n" +
       "p3UO2xeLNm15+r53YhixnpCVB5xd1wV/97swh2E5emmO80Xb8gz/qB3IyXLX" +
       "zoNKpXKxuJ4srgcr\n+8/uO6lcKSawdW0gx0tOT24UcRYklRE4iwvwg/5a98" +
       "Ag8kvtY7CwuhXEOljQRJYKxpEKRqmXWO7t\nrkNIn2KYl0K8eX3mTGGLp0/H" +
       "pVOAeOA7mh7dUj/79b/+cYL62Q/vvVwi81D8pPID+xn2FjwxQ+XM\nmR3nt5" +
       "20cuk2rYyuf/vdm4/8/Hvi3z9bOSdVHrJcN01kxdGLqJQdp1BQu5XsYHnlWA" +
       "jskFfA9pJS\nILgIhltOwWgXMYUpsyIdnUbqnfgmi5ZcwO/Vl7/3d9+6tf58" +
       "CaoSBI+X3I+LX8p26Tr3wvD9H37u\nXEm0fqBwSKnJ1dfnfkv91kdGn//y33" +
       "z12p2ISCpX7wrUu0eWgXZafCbyVV0rEtkd9r/yncG/f/w8\n9ntnKw8U0Vvk" +
       "r0QuUFckg7efnuNEwN08Sl6lsc7RlYcNP3Jlp3x0lHGqyTLy13d6dui4tGtf" +
       "/t7+\n8z/ltUfpmQ/sYbpPBt1CTd4fFpYk8iIcb5Q2Pbim+m5QhEB0YOqFiH" +
       "Kia9eDYA+60vCnlN3l0G//\n1AXoK3/48J/trHeUbi8fy8sFsPbBe+WO3/hI" +
       "14v+r/4q8/FPfvND79s57dBrSeVCkCqOpeY7Rd56\npgDJY/dIJDeeePwTv3" +
       "z9175yhIrH7nBvR5G8KUGR/+Srz3zqL+RfL5JMEeyxtdV38XvmEB8l/8eK\n" +
       "pHwYECVeb8S6mkZWsrlBy4ruHMlQ3t+9a7+nNOJufGVnl3cckpRYPh2RvXIl" +
       "OgKCq7z4n1/4dPVg\nL2855qkdm3JNPp15Twy8pW7/aPbpb/9t8rWdie8gqO" +
       "RxkN89rSAfA3fry9mVC5/7jHu2clGqPLJb\nPWUvEWQnLR0gFetf3DnspCtv" +
       "OvH85Fq2T9w3b0fI06fRe2za09i9k4CKdkldth+8P1wrV/dwBY/B\ntVduXV" +
       "4fr2cqQcn05o711d39h/boOpsUglmeXMh/Id5tU/KkcnHtR7YeXT3Cw+OHeN" +
       "h335jvvvYx\nUN6brynxz+0lvr6T+GiLU3C4r6wF4M9DN2o3oJIrcbfI58r2" +
       "e8vb9fLWLgR+cuWoVzuH7IRidSmW\nwKt7oY90eGQXDTtE7zchx+Qvb718l+" +
       "nffIeM9oudyke/8Ytf+oV3/kMBtGHlfFaCoMDXMV7jtNzK\nffCVTz7z8Cf+" +
       "8aM7KBeB9MPKmQevlVzH5Y1MKs+UAnJ+Gqk6LcfJyNesYlemHcl4N+CZyHKL" +
       "lTw7\n3Gr80tt/858//3X28X1G3u/H3nnXluj4mP2ebIeoh4O8mOEd95thR/" +
       "1F4B2vvMx+TdnvVR49ueYR\nXuo2PvP3+vX3XlLvsYY+4Pj3NGlyWR0gMdk+" +
       "+tCC1IHxWY21gRR2+QVOkktH53B2SBt9LsBFj+Dj\nXntMd2bKdLhA0uUGA5" +
       "pSY0RrOgrCVc+G5kaPzZ2RDg34mIrkOmXwq1let6IZUDOlnGWVfoIh0401\n" +
       "0KYWhBGhMMxxSOvnIOgxmRgb7ro2G3cZrwoPky0IMxMQ3AJgI8daHTOIoS3n" +
       "BjIXWEM4bkP9hA/R\nlgQgUs+HarJAOdGMWy6ZTWJNFCExMNWF6jyxmlSHBB" +
       "w5NDvOauywA1BI5CLThEmy1MjEbavRxJr2\nmlq4bGFpGvcEfsHGi3CRJjYi" +
       "2/JwEcgrzp1T6y2b+rPqdk5x+ZIbQgIHh3O1GLbYLigbF+Ya67pj\ndrqlHB" +
       "6fwlZLZsnGcr4KmXlNZ1HClAAyRkYuanewpTECBlukOmikIm/Zy8So20IyQN" +
       "3RdJbOMrHf\n37J83d+MhxM7iGa+I1OB4VDZTIpCH11YoRr0yTFXM1b0ZGYu" +
       "IGsujKtyJnBcTR9MJgRC1ZMpAWoQ\n7k5sNpHw4WoUjTBoNmhToLyYh5SuFz" +
       "u5adgVrJE3qVFUykeNdGTCS7PX7zJ81bY3GiWLUY+DexyR\nrFmn2a+5Qrvd" +
       "KxgtecjTgMJ7Y9Mk17zrqI7CdeUcynv9dZcbrSA63bY3qW5Z0x4bVDlmiMqL" +
       "ttsf\n2ouATGkewoXlIjJnuSURHb7rqDhlr8fzVrL0WNEb97DM8iC+M2xPWw" +
       "FqxWqGRAthuF30yKzKCxNe\nXpECAWHupN7M5tJEo7J8M/XxBdTJQx3OheYY" +
       "8uZrfuzgPV+weHEm2U5j1MrcJIQMrM+GS3rChvNl\ndb724czGNtoYRUB5tp" +
       "WgdtuTaoYtgrHaRYCakmTbKQxDoNENqHDgjFLUjWs+I3OUHNop0IQ6sDuh\n" +
       "2Fj0i9czXvbDzcChfESgmisQ7UwSashFPkLpPR1KJmirn5NKzujqdGoFZnch" +
       "za0Za4BLO8CR+pzA\neKcQfIFjTnXAdiPcYmgfjWOV0X3G3tTC/iAUB37UcQ" +
       "KxSyZ9nG0QtDCZuI5Nbmc5sCGb6pokG1ub\n19SOSaSBKa+IqVD1GHMjFqE9" +
       "2gwyvYB9bdOQEm9Ox10a5ocJxlqKRJJTGZvU2lOrp6ZaP5PmaL3X\n1tlu3g" +
       "wiJgtwTVqZXQWrBkQ0lOjhiAv9GeLgnMC21868W7z1znVigJtBZzEcmFvc7u" +
       "FLckq4+NLe\nbAzODJo+Cy/GyFBjsBoMNlrRgKi2KEYNFZMfM10h0/Mt7EYy" +
       "gDbU1FNoDB9Oxr01hZt2FxWnhu6j\na82m9bhvdMykxhhGQLhDvtaGJNmqO3" +
       "J1E/ox7M2HXjJr63i0mTah2YhwaW7mhrlt1UwcGSKjEW62\ng26+ckfjkWSK" +
       "29gWZoiRC665dCNeXLCMBXUW1WTYkPGp16jTirPKGwiY4KNxfS3JXK1ttyhp" +
       "0K53\nJj6qpHbOmxSYtlYg2KRJRtmkCoiqa2o6J9uaveBGC7Yabl1kuW4Jk2" +
       "WdaCjUzBSXAazmJJWBttyf\njfpbvrYQVUI0zNigWgOqJuAosqjnPuIYDMUg" +
       "a1gfry2mUVsw1Q5JtI2McluOm4GyiM41jjLBec1L\n1gPRqk99rFFzg5kb8D" +
       "iA4kZeby4MWFz3dD9fu2ynqw21ceiH6nS98KrLGO/mCNBeLCG+X28akNj0\n" +
       "ZKRBrzxNFOucDJmFhoqtYFtDa0k51Nu0VZKsOwo1JQdpv6VuZXTre8C0N+pU" +
       "2R4QgDE63wxzaNqw\nomgdYXiTjHEbIuIhzagMYpiAL7lFcmZRkZTHIaFwer" +
       "MO0PKaIdrNAJgmKCqBIxOr8lALsRxk6iyF\nvmsGGj0UYBdCsoTsOPFasofm" +
       "GncxY0101vlYWRFy7HneooYbbu72EnCWRM1N3FqnErVAqywHS0J7\nM8HiRt" +
       "0JyFxoi1ymjLWJNhHNZQ1pxgoy2ojjWTiQCa0PE6tWumFtd4sOu0QyVds91E" +
       "8DGw/qa7Tq\nsL6E6lpHEnm+3+PtDkNqkDZiEQNhN+yWlMJVn/T9PjBzuIIL" +
       "2RQ6osoOmQ1LSa7OU37oks6YG8gd\nrlElxeG4SQf1LJ51Rr2pQsUL1g/HNL" +
       "TiNHzGDjcMJqn6JlhNFSyErAZtIA1c4/mBG6ExI+Ih5qt5\nHRotQlerdkw0" +
       "jEAhY2A2jWsrgdhu6xhWQBhDAaQFMNtcWs/XTBgamT4NibjWnylIc27wsKXH" +
       "PuWu\nMkaGIgVW6i2kCuhONo7TwUhSBotEDNK2bdNTwMqjujHx+k1d9PjxbB" +
       "z1tRoUI/OG2lYpSbDtpacs\nLS7gxl1tI03nMwvOkaojZqtsOjEMeuCBy4kv" +
       "dhtL20qzFeAS2QQ05/BcwgAUmLY4BK+3PN3kHUpN\n+AEW9mRxlAsLCx33tE" +
       "VfNDtV2howky0+E0F04En2OlzgrSWGkEuEFooF0/HHJoMrA0GaAp2+uDVA\n" +
       "dZ6EkynKzEQbJtr1FjnsUsVuZcmt4WotlNpujsRBtO6Ta9ZAVqvVHLMMCuig" +
       "y1EPsCxl2UjVruYk\nSM1s88v6cL7gIWXcmI30CTcVVRCIGAcG4SZU9TSkbw" +
       "v9nuKqs9oY1ieK3CFEctwNRZ5NYjyUWo3C\nj90aqAMuy+bjUcOYd2A2DuzG" +
       "ZA1ymD+rtUyrNw+8sCq0nRqYmEHCBMwwgZueEwHTrjCXPFE1/WXm\n+m16MI" +
       "lYkGAEyxRjORpaXCektLaYRdZ0iSStkdftuJsGHVZ1p9vVJGMEi3MSdAhb1A" +
       "RlY7LCILAa\nSstoN4cQw9TW88QZWECzSYznKVRss4Y0ZM51u09mKEhgSrAG" +
       "CVqvblUZV5YbBMa7QTYEWCJciUQP\nawzzbW29WvM9aKiM+lG9QbYauhE2An" +
       "ToSWMhCKaO4NRojQhlogjvKRXReVXU1+LK37YXWF6z53nu\nZeMGNJFAcrMa" +
       "BqCGtD054ZsJzccMMUUnMtCPxHyS5Ov2Ru53cNLBuiQ6VMQJNVOrTZRBtkso" +
       "GeKL\niU60a8mAiYdwX8fZpolGhDBPEh/rYewK3m4Cc6jxLr72J33JnYwzFg" +
       "FCyOwNxXmxckiUWuVpokUk\nMsyyIyHCs7G1WSMrkQqtPIUHAzRA1bngL/F1" +
       "QgynVLEDkawJZGlAOwW7QwaUKVSNNGQVLw08kauR\nUmsoIr5liZWeGC3D8R" +
       "ZhQ6Yi3KUwVOAhGF4O08lo1hbBqNOZizCSTxSeo7o9rFnrZm3RY5kJEGV9\n" +
       "XgyrBiD0QXGhpjiT97F5R6m1nM0kiZ00mIxaFg0FAI5hLRFjGsqyA28RNWsq" +
       "sqp4NQBeGJ69REYq\n7BqRm4B6Fe9kY8VrwmjKN3mGkgk4ywbLcU7KylBKWn" +
       "w04mouMxJQH3AXmS7OJ7UYTAe566S2hDag\njW9GfcqxUq+PVzm/WKdXpCj2" +
       "pw0xUVF4LOhwRmVNP6mLgkEOuPGqA0WgNRVFOKVCIUABA4BH2UzB\nYLBTLN" +
       "UAEQId1YvRsBpGahuLNmJ/I42p1rLpcNagK65b25lVy3Nr5DDkYBRpg1l3TQ" +
       "syaiIMmCDo\ndtyf9AZLpRFDLqVzGOY3VY2u0lsAaGBz1nBGJLSSVIMRW4Ia" +
       "UoP1SsairTkeqSg0gONQ7TUmWGQM\ntkFWC+Vmi1jybjCNHXrBcTZHz7YkUv" +
       "VZA0umA5EIhJqmoRHAC5HZgNoWMyK6hp3NOy3MXmdzZCyG\nLQjVBzow7kgp" +
       "BTegGQQLHSV1gOZYHgyCjl7t9YOIKt6dpl7TgggdHggp1hL82OsnLFWzRtja" +
       "w7zJ\nCAQSl59nWUsb1oFBvdcfE0FH8dJmM2oZILBa66LVrDbTlaFvgHqxRa" +
       "Sa8Rxxu7guZIO+F9W8CK47\nS5JrZpxgd9e85BNuMhVq48Z03cS06UjAQpJq" +
       "49Fca6AEio+rXSFkDHiN+zrba9S2MGCuvInJAnAA\nM2lNQPwibGR4BRNpi2" +
       "tPObU7hyZ9fTbTpwsSoBZiWxypnhMMYrc5rc7GNUGEZWDu1fyJEDa9NhQK\n" +
       "aRyDNjPjiEV/DnaFLmyZNXZSLCLjYrtsGXV6ARQv0IjAgcbcAWyDYbQwJurV" +
       "TZOxsOaGAMleXpNF\nsDmAIyPtuyPER+1VssIwtB7NgVbL8EerSbyBxkZ/JU" +
       "qmgKzkYQYqCM1Z4daJqHnLqHJ9oInOrMTE\ncq81R1VD1ZVes94AuoVlDXGx" +
       "9RlaS0HA4HqbIi1CvZWdbBuNhp/SdidvJdgkThrjhdLStmC13aEW\nnTYXD6" +
       "btdvm2LR2WGh7f1UJuH83sKwzlM/r/ULE5c/D/LYkelpheuEeJaVevSSoXg8" +
       "jK5PIIrPLs\nyjIOJbtVVhdunai639pJCu7E3RUw3nVYUbxTdHzqsOAYR5Vn" +
       "XutkZ1fT+JD4H5c+KH/xhbOH1cl5\nUnko8YP3OHqmO3cKlaeZjHYHWUfVu8" +
       "vzZ/+ph372pXtVKp+978hb6pXsqem5pfWXZ3dlv32l8K6T\ntJODbp6sD1Yj" +
       "PUkjjz9RJXx278FCiIeKq1dc1cOa2+67fHhl55BH98XqY2Y8Vbs9u3fS7jcX" +
       "7Iu3\nQlI5Z3nJ6b7zhuPLyb1LwKdKyM/dKZGRjqObstOOzNTVvYTIVT0oz6" +
       "h2gvm3q81XSyBcPQGEq3cQ\n657Qt1Zclw/1vfwG9T0UtPwZ31eV8ueLO24v" +
       "v2EhywOwKyWTQyHP7MPq1hss3T7fhJ8/CFM5tsLU\nT/RrhxF0kPmWdlDGkO" +
       "VZybXrBy8evO8F7uClO0cTdwXhThvl/tX71y3t/0xSefBo1tPYeKCU6ZQV\n" +
       "3lxcz9xlhTMfeGPJ5fl666QV9nXqg/2hyIHi+44ueztzHEWQb1x7366qfbB3" +
       "24uyq7y0Q+O+dXSm\nsf+1O9jYNXdM6Hcf7MeWEp0euS/674n9F3qF6S3j4J" +
       "p/YN2e+eAEOkrfnOg4UA9+5ODaSRr/5sE+\nxg9e++BlVhZh9TAtAOAUUcT7" +
       "1wqT3T+LvnunzfWbL91m7sT6zfuh5Oyxqvr9ksUuP9+jsr63zusD\n6WNJ5c" +
       "0n/XUaThcP3XocUeVaVXB44g6izrzr+zvB+/4MUN4+9frK/kaZDQtlVTlOXt" +
       "td+9Uuqbzp\nRG95jvfEXX832f8pQn3u1fdf+0Jw5a9256i3/7hwka48aKSO" +
       "c/yE6Vj7QhDphrUT7OL+vGlvjN9O\nKg8fOwcvArr82in0yp7ic0nlwp3dxO" +
       "8ER/5/7Pjx+aHc+f8COF4omVwjAAA=");
}
