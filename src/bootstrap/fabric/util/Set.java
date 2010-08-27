package fabric.util;


public interface Set extends fabric.util.Collection, fabric.lang.Object {
    
    fabric.lang.security.Label jif$getfabric_util_Set_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Set
    {
        
        native public fabric.lang.security.Label jif$getfabric_util_Set_L();
        
        native public int size();
        
        native public boolean isEmpty();
        
        native public boolean contains(fabric.lang.JifObject arg1);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       fabric.lang.JifObject arg2);
        
        native public fabric.util.Iterator iterator();
        
        native public boolean add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(fabric.lang.JifObject arg1);
        
        native public boolean containsAll(fabric.util.Collection arg1)
              throws java.lang.NullPointerException;
        
        native public boolean addAll(fabric.util.Collection arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean removeAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.util.Collection arg1);
        
        native public boolean retainAll(fabric.lang.security.Label arg1,
                                        fabric.util.Collection arg2);
        
        native public void clear();
        
        native public fabric.lang.JifObject get(int arg1)
              throws java.lang.IndexOutOfBoundsException;
        
        native public boolean add(java.lang.String arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public boolean remove(java.lang.String arg1);
        
        native public boolean contains(java.lang.String arg1);
        
        native public boolean contains(fabric.lang.security.Label arg1,
                                       java.lang.String arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_util_Collection_L();
        
        native public fabric.lang.security.Label jif$getfabric_lang_JifObject_L(
          );
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public int hashCode();
        
        native public fabric.lang.security.Label jif$getfabric_lang_Hashable_L(
          );
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    long jlc$SourceLastModified$fabil = 1282915709000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAI1Yaazk2FX26+7pnq5ppmfLZDRrz0yDMpg8u+xyLbQQ1GJX" +
       "uezavJXLEL14ua5y\nlcv7VgZGIFAmELFmwiJB8gcpEpofiBHwJwJEwqogof" +
       "lB+EMAJUJIEAQ/EKMoEOyq97pfv54ZUlK5\nbt177nfPPfc75/qct74BPRQG" +
       "0Cumqln2cbTzQHhMqRrNTtUgBEbXVsNQKHpP9Es/8uFf/sGf/uaf\nXIKgLI" +
       "Buea69W9pudDrnAfHvf/Vb6ZffGD53GbqpQDcth4/UyNK7rhOBLFKgG1uw1U" +
       "AQtg0DGAr0\nuAOAwYPAUm0rLwRdR4GeCK2lo0ZxAEIOhK6dlIJPhLEHgv2a" +
       "Z50sdEN3nTAKYj1ygzCCHmPXaqIi\ncWTZCGuF0R0WumpawDZCH3odusRCD5" +
       "m2uiwEn2bPdoHsERGq7C/EK1ahZmCqOjibcmVjOUYEvXRx\nxt0d32YKgWLq" +
       "tS2IVu7dpa44atEBPXFQyVadJcJHgeUsC9GH3LhYJYKefV/QQuhhT9U36hKc" +
       "RNAz\nF+Wmh6FC6vreLOWUCPrQRbE9UnFmz144s3OnNbl6439+dvrfty5BR4" +
       "XOBtDtUv+rxaQXL0zigAkC\n4OjgMPHd+PhNehE/f2DFhy4IH2Ta3/0HIvsv" +
       "f/TSQea595CZaGugRyf6t+rPv/BO++vXL5dqPOy5\noVVS4b6d7091ejpyJ/" +
       "MK8j59F7EcPD4b/GPuzxY/8dvgXy9B12noqu7a8dahoevAMbqn7WtFm7Uc\n" +
       "cOidmGYIIhq6Yu+7rrr7/4U5TMsGpTkeKtqWY7pnbU+NVvt25kEQdK34PlV8" +
       "H4UOn/1vBF3jQXRc\neJcXQV1EDAvKI24KHMQL3HLPIVLY2vJCgBQygaUjYa" +
       "AjQexE1vZu137LpzBZueCj6dFRse/nL/qg\nXRB24NoGCE70z3/tr36MZH7m" +
       "k4cTLVl4qmoE3TzgHqxV4EJHR3u8D99vx/JgjNJ//u137zz28x8N\nf/8SdF" +
       "mBrlvbbRypmg0Kv1Ntu9iMcRLtiff4OZLvuVUQ84ZWcLSg+4ldAO19ojBWUg" +
       "Sci1y858F0\n0VILgr3z+rf/5t9P0rdL2pTH/FSJflCtOLTNQbcbr/EfG378" +
       "k69cLoXSK6XNs73vPV2uctFCVBkF\nzvC32o/+1xc/W7l1wC/nPLcHuBI+yP" +
       "r7Jp7o+R+Kn333r6Ov7o17vYg+kVpwpnDlFy/63n3uUjrh\nRZUkNbiH2/zb" +
       "5PGrv/O57SXomgI9to9qqhNJqh2D4pyUIi6F3dNOFvqu+8bvjzEHh7pz6ssR" +
       "9PxF\nvc4te+csIJYmuHyeLEW7lC7bD++Jd2Mvc/Pbh8//lt9Ttt8+sP1w7r" +
       "1iTcGlyiuFzAq3Pi4Vu/UR\n3d16hSsFt5agMJYaAeM1z8uOIK8E/Z7yhC9a" +
       "vdTq3Z+6in7lC4/86d7WZ5H75rkQX1jmEAcev0cQ\nIQClxf7+16af/sw33v" +
       "jhPTsO9LgcFSCWoxZGuerFmm3pRSPcX05Z4a2pG2xAcHu/zycj6KlTVzl0\n" +
       "H8/3P3sX3Eu8/L72+LmDPV7b2+PsYisQPtASR4Vu6HH1GC1Ra3vs1/bP7zvV" +
       "vWwflw+kfKCFws+u\nbf129xROKqJLEfhuH5Q+28Nje7OUtDg+XD3n9C8fRL" +
       "b3/kfvibFucT996uu/+OVfePUfCrMPoYeS\nkmIFe89hjePyAv/EW5954ZE3" +
       "//FTeyeCoKPvffsL6KBE/YHvSP0XSvV5Nw50wKphNHINq7ipjbMd\nPOiI08" +
       "DaFtE9Ob1+funF3/rnt7/GPXWIcoc7+tUHrsnzcw739J7Nj3hZscLLH7TCXv" +
       "pL8Mtvvc59\nVTvcX0/cHyVJJ94Sn/s78NoP3dDfI9Zesd33NHj0yJVBLaTb" +
       "Zx9Gks15qmecaDo5MlHyJIP5cTaN\n8xq5DmkSptgpuvICzFvHwjbVq/3Y2D" +
       "IM01iPGjquVLZwHmmO4uSZjgsLil6RnM/NJMrj/SFMMf6S\n8X2e4himRbpt" +
       "g/dt1eJoEpNmVt+fDTJxNvM31MKCgTZuVKIGgOs5XI0mMYGZJkgGTg4aCE5E" +
       "mynP\njqMNIJu55Puj0J1u9RQIgJmOWSm0V4ns9/FkCbYjPWYZU5hyFQeu5j" +
       "OXF9kek8pNrNH0xr689J0e\nwGY2hdURWFizrUY9D8MV6i0tEgQp0Mc8zFMT" +
       "13daEble92xQBaRfrQB+SLm+F+t1L7dlZs5Xvf5S\nmYwZbjzK55v+ujHi10" +
       "Qiuq5YY1fK1EXBsuoyeWtD7Va00HYIy6kJfjWfYkmlJ2P1JmOsN3Co8UAP\n" +
       "65JQVcU64bupNnF1W9nYgyU33+UsO9DX2/l2JxmT7YRQ56onUSN5ZvRmCrrF" +
       "A73hVexeOA/rYKHN\nVxRtrKLJTs11spqMNmKdak8xpbdhxrvco01Lk4jtvI" +
       "nXqkQu1hV8souDoRBr8GZLm1ERkpeV7XDu\ndJOxuF3WXeDWhVTt9MEC6U1Q" +
       "si2NRlQnIWO2Nd+IxHKJC+yuH8lOLZrqYiISZB/FBpRFbXCpul4M\nZxUf3U" +
       "ptnJ8owkohCY7doc0lpVPtbkeaSEuZQjuMjgZBmzGdiYIbbIbpDuoh5Myfdc" +
       "xRLeO4KRzV\nWH7l9vgKNQpzC94wm1ogO0Z/MIQJFLbEWErnpKfnCyJCQkHD" +
       "89TvinOp1VMcA5tWxzUusdE5grTR\nyJJ4Zpy6LRGtaJSom5E8IbXMa6GxpZ" +
       "B0N+AJjeq0BskCRhw88HInmYRTozvsDTvzFdfT06DeoTfp\nVDDjdThKBX9T" +
       "3zQmjUoT32rNmcN7E0q0an2E5jJCmo+sQZ9c+HgAmqPFlij2w3pbctNrNxOz" +
       "Gw6n\n9BTBR7yzVZpUj9WJ/qyZRHq/shmBcUfVBoIezkBDW5P9RIooWw83PT" +
       "3y+BohdrpU6AlxX5YFchP2\nqaqr0p1WKwW5sJy28S3hTuaKbY/HSQVBBuna" +
       "wwcDou0wds+NQ2/ooWGt38VSPnNVlCJMg8upFMxI\nQcuROiqgXG5qeTox8y" +
       "FDz61lzekvqh3P7lfGMxTbNKO0Q9XlHjwDosxt2uqo3aql/ZXFFUGBczdi\n" +
       "tcNSys5Y0go/p1guJxBCn45SZ9Qdx8wC1KRqnXP1CrYcePOmSbcoTICzxqLa" +
       "tUjK88icjBcAS0gm\nJOnhTGTBdMFioGVMcHjH8lrUJdBOnrZDKpqJ+aDXUk" +
       "fJvFLFERmx2H61O+GEFezN1m17yIZ+Etez\nPj0ldnlrPEV4oVcjWlSGSc5a" +
       "2aEbPw/7mK+3652WYA3aDbQ+F6tSxewlnq+QVReL6gOc2BATkHRy\nfZk7jM" +
       "vZfcXYtjO5xQ4mOQySydSPJSOoKvxSmyByDyeqQSNoYNUh5k3ScQWd6m51OZ" +
       "TH6hrfjVaN\njK9qDbyIN31+4QlaN+zIXSWNhsKWrfoprvJWz8JwqiHP62qw" +
       "AE6GYvnAs7fMru9UxvrS2Q1CHNtp\niIPtgshdxGbbNuZtSUqJJkyJJA0HXn" +
       "2mN91l03EGSG6PkBnV1xBAaIMJw8ItZEz1Bs1aWBlsFNSY\nD2viWmCj/kbx" +
       "OxHvTri+59PolEIju2752VqaDuvj0FxP60GthTTHi7lHOQk9TWv4Rh4rggU7" +
       "RpBX\nRuOmnjhCjEy8loltNi7PeWi6U/kqO5qv2FWz2tWJGoXw8QKP4OnK6O" +
       "7QdJ34C2cYKYbh1RRetjqO\n7S2ipMK1k8RxUlHkmA4cop7Otm2Lbfko0ujW" +
       "KW3TE+pDxo939BDMN832qNA7wBFPDdXEluo6Q3MM\nGEqDcMC0tcrW76s0Mc" +
       "SotdsBrZ0hDElJMuDAb7jhYrbFgcPyg3reTbaiMHBCmuvJjTrn9Ge2P8Ky\n" +
       "TpVpJu1GzXCY9qpZaZrkXGzscHu27qRzzB0thKTf9EiUyGRj4RX7U5dUlmhB" +
       "Rqdm0wNyzAq27be7\ntofyyG46zvNqDpvAiX2pYotc35yvFAZmZV0cJZIdrb" +
       "przYCL8DqbbNh5voABJviN6lYZtHDJnluD\nxoqLJ/KcW8/zROwHYogp45RC" +
       "mhWE9l3LkWarMGnqE1xoYG6UN7g1s4iZcFfl1d64uhXJ2lilZKEx\nD8aBFT" +
       "B1bzg2ct4erCN6hm3AmkJnVn1Y2a4ceyfHMYLEw0XT0+pTmkHh5miIJUFNUt" +
       "qR1XKrgVwL\n1InWawiwh7fr3NDjvR4+0EYSL4gkzDprVWOlZUW1XDTGO31T" +
       "q0ko6erBYh07XCy0qZzL/eKwZElb\npnGbGlu9AQ5EgQxRe7Uc+VOV9mCyPQ" +
       "ya7TwDvekCqVUwejxrtOckvuDYDO/hvXbBjo6YN9cUNRzQ\nIUrrdLpySEcO" +
       "Jzu5Mx+MXLeV11qtpu0QTUJPCdexWsQCUwpH17xANHRjTErumtO52hBWB4AR" +
       "03Fz\n5q5nmAxGmNgZ8gO1SuEbei5s9KYQNBQTd0UHb7KOJEc1v7GKq/1hWq" +
       "Epb4VwSRcmu63eCu8AOG1I\neRJ7mRmTTjSgSVZI23iuuKi0lRSPWgz7Rtyn" +
       "MnosExnWQ9BVPEHwFiGh08qwhse0mimGhwlS1rHR\nFgP3m7Amy602MpwnXW" +
       "7gVs3JxEtsJe8Z8YjHihtvsLZG/NQy4TYeRa3upLvhzaxXsVOC6c6Ay298\n" +
       "WkZlwW/2jMBzl7hNDROWmO8Mk95SDWvtKQI8tSixaiZmLHdYHw4ajC3GvQHr" +
       "ugYpVrf1ioE5S1Qx\nvUW1bdYEb+nXwtEEz3h1hjR8u5vuCFKu1fJd4cC4Js" +
       "MaiCcjj+2vYndec50BVqaH9q7RMmYrtiLn\n000GGAvb9almTZFcKiA2AZYF" +
       "a3lqJ8FWRTYCV28VL1IdhZEaRjP0ZpYO92FHI0FfoWoxOwkxAxHV\nUVRhvc" +
       "T1m5NMbjSyebKuF++yqhyRLbbvIw3SzZpogHQMQh0tgipZvPaWr8PMd5QpPL" +
       "VPdO5W2w4J\nQjl4Z/92nb0HSAQ9rGphFKh6FEHX75b09rDnigRPvkdB6viZ" +
       "p978ldd+4ysXawNHZ0nV0+drKF3X\ntot0u0i9yoTihfcrs+2TiTfk/7zxCf" +
       "VLHyuRS6xBoVrkeh+1QQLse0pdBBntq4pnKfvN+Uv/RNU/\n/+MPqhdAL33g" +
       "zBP98eS52eWV9ReX9rn+oTzwQFnz/kl37i8KVAIQxYEj3C0NPFjOmQauDow4" +
       "APfW\n/dVvDv7j0w+1fu8SdOV8zaREeOVCBeIR0y14Z5cLnNVRK9EqcNN7PR" +
       "fLEacFuKND9n1065B+H58r\nRwyLzPX/r0bcS9Xu49Klss2dZ85R+ZQKez95" +
       "r+rQDgJ1V1alsp9854Vf/3P1Ny9DRzR0JSwy/32J\nELqvQHWKUD7X5wbL/0" +
       "UW+8zaMm8vQXTg2UmJf8KD6IQ9Y+CzpwzcJ+Mh0OPAinbHrKoBu3CXy4Vs\n" +
       "WRN55oE6/aGarL/yzsc/8kXv8b/cH8fdiu81FnrYjG37fAnoXPuqFwDT2qt4" +
       "7XACB4uFUXFm9xyi\nSHvLn72mwUEiiaCr93w29c528cT5XRyqVtn/Aa/Jy0" +
       "CUGAAA");
}
