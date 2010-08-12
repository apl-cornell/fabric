package fabric.util;


public interface MapEntry extends fabric.lang.JifObject, fabric.lang.Object {
    
    fabric.lang.JifObject getKey();
    
    fabric.lang.JifObject getValue();
    
    fabric.lang.JifObject setValue(final fabric.lang.JifObject value);
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_K();
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_V();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.MapEntry
    {
        
        native public fabric.lang.JifObject getKey();
        
        native public fabric.lang.JifObject getValue();
        
        native public fabric.lang.JifObject setValue(
          fabric.lang.JifObject arg1);
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_K(
          );
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_V(
          );
        
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
    long jlc$SourceLastModified$fabil = 1281544489000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAANV6W+zkaHZXdU9Pz0xtszM9O7tZdnd2Z3cHtINJ21XlqrIZ" +
       "Iahy+W5Xle2yXS6I\nOr7f73aVbWAVBMrmwiWXDQkiJC9IkdA+ICLgBQEi4a" +
       "ogoX0gvBBAiRASBMEDYhUFgquq/9P//s/s\nbiTggZJsf/58vvOd73y/c459" +
       "Tn3jNwcvl8XgS45u+NGTqs3s8gmhGzS31YvStrBIL8td3/vUvP/H\nv+cn/s" +
       "if/a1/eH8waIrBO1katW6UVs/GfIj8D335t0+/8jXmsy8NXj8MXvcTqdIr38" +
       "TSpLKb6jB4\nFNuxYRflwrJs6zB4nNi2JdmFr0d+1xOmyWHwZum7iV7VhV2K" +
       "dplGxzPhm2Wd2cVlzptObvDITJOy\nKmqzSouyGrzBBfpRB+vKj0DOL6v3uc" +
       "FDx7cjq8wHXx3c5wYvO5Hu9oSf4m5WAV44gsS5vycf+r2Y\nhaOb9s2QB6Gf" +
       "WNXgC3dHfLDid9meoB/6SmxXXvrBVA8Sve8YvHkVKdITF5Sqwk/cnvTltO5n" +
       "qQaf\n+bZMe6JXM90Mddd+Wg0+fZdue33UU712Uct5SDX45F2yC6d+zz5zZ8" +
       "9u7dbm4aP/+SPb//HO/cG9\nXmbLNqOz/A/7QZ+/M0i0HbuwE9O+DvxW/eTr" +
       "tFZ/7oqKT94hvtIsft/fkbn/+Pe/cKX57EfQbIzA\nNqun5m/PPvf2Nxe/8d" +
       "pLZzFezdLSP0PhhZVfdnX77Mn7TdaD91MfcDw/fHLz8B+I/1j7gb9u/6f7\n" +
       "g9fowUMzjeo4oQev2YmFPWu/0rc5P7GvvRvHKe2KHjyILl0P08t9rw7Hj+yz" +
       "Ol7u237ipDftTK+8\nS7vJBoPBK/3xe/vj44Pr73KtBo94PcOTqmif9CaWVQ" +
       "MKlMse92B6shMwK9LzwkuwV7iflTbY0xS+\nCZaFCRZ1UvnxB12Xdd/m1Zyn" +
       "/vjp3r1eA5+7a41RD10qjSy7eGr+wq//8z+Jsz/8Q9e9PePxmdDV\n4K0r86" +
       "vebpgP7t27MP2eF9V63ifrbE7/+W++/8Zf+N7yb98fvHQYvObHcV3pRmT3Zq" +
       "hHUb8s62l1\nweHjW5i/QK3H6SOjh2yP/qdRz+hiIr3ujr3/uQvN5wZN9y29" +
       "x9s3v/o7//K/PD394hlF511/68z9\nKlq/h+FVtkfvSd/HfP8PfemlM9HpwX" +
       "kLmospfuo8y101EWencMM/Nv7Ef/+lnxu+c+V/HvPZC4MH\n5YeN4IWBT83u" +
       "78k/961/Uf3aRcOv9c6o0nsI9Zb9+bum+IL1nG3yrkiKXjzni/yr4+OHf+Pn" +
       "4/uD\nVw6DNy5OTk8qRY9qW7J7Jzr0S+xZJzf4PS88f9HlXO3r/WemXQ0+d1" +
       "euW9O+f+Mfzyp46TZi+vaZ\n+tx+9YK+Rxea13/n+vtf5+MZ+N+9gv+676t+" +
       "zl1KnCMM3vRW/uQs2DtfMdM46y2reMe1e2XplW29\nl2XNvUF2Zvr7zzt8V+" +
       "tnqb71Zx5Cv/p3P/aPLrq+ceSv3/L4vWaubuHxc4DsCvussX/zM9uf/Knf\n" +
       "/Nofu6DjCo+Xqp6Jn+i9Uh5mtRH5Zt8oL7GqqQavnNIitIt3L+v8xHN7uXY/" +
       "US+Xix1eKL74bfXx\n56/6eO+ij5s413P4jpq418sGPRk9gc5c4Qvv9y7nP/" +
       "hM9nP7yfkEnk9QL/Bngsh8F3vGTun9TO8H\n370KfbOGNy5qOcPiyTUS3ZL/" +
       "fJo2F+v/+HMyLu3D1Y/+xo/9yl/88r/t1c4MXj6eIdaj9xavdX2O\n5z/4jZ" +
       "96+2Nf/3c/ejGiweDeHzDuvfqVM9c//LsS/+2z+FJaF6bN6WXFp5bfB27rZg" +
       "UfNsRt4ce9\nsz8+i0Y//vm/9h9+8dfFt66u7hqyv/yhqHl7zDVsX9D8sazp" +
       "Z/jid5rhQv3LwBe/8VXx14xrOHvz\nRS+JJ3U8/fl/bb/3Rx+ZH+FwH0TpRy" +
       "q8evPHKbikFzc/HtKx/UJWHGPrrVtGkjDBxASeIfBNq2OL\ntjwoJw9nyIOu" +
       "LdeitjnuIBQT1ltrVKFTNBy6pc0WC06toxldAatlmvuwnoRyDZTKCj0EypxQ" +
       "ceF4\nrPaYIkClvJcmE9AB50kW4qFeWMp2v52j4PEIgvUwnZT2mK5bS5goTe" +
       "HTzJEb1TiioNu0f7fZsrqR\nCom0buoKXrZjCmhDpFZHzKxFWZyakaGX56q/" +
       "w1dOimtDDcEqv9pRGmWZptl77WnbTlsZbN3cc7DZ\nsm6UdUNBWqTpqrkbmT" +
       "xNEKzoSpZ1EoSeT0GuN43PUl7PcpiVQkN6lrRQ1H22wdyQF5eRtYFtZmET\n" +
       "cRsRPDmmuDzfw4ta2M9cZryqedpjaQfdr3cgdCyy2cl2S2XNMkMplKbUfq1N" +
       "N2bKp1qwiQhxT3vU\nEh8dA1wndMzMw5Psjf1pSvs2w56WQrg7ijIxKcMgNq" +
       "GFhO6shZJr5mK4quRdnq9EHpkQOFb4hL1k\nJHCpQaJPCCTKE6yU2gfTCudu" +
       "Tem8uM5t/tjq7ao+KT6OaUS9hk/WqT1wYToUO1yfoSMPUQJj4WKn\n9c6sXa" +
       "6tUtigKax1U7lrcd+aLK26YcrMWjB5xWwWcoAfInGRLWypoDjAnnmrJTmMlB" +
       "msHDU63bIZ\nzbtqQB/mJ8mNoTWdBlPNBKJYlsFdEOkTZ3ns6GUnUksF9tGN" +
       "s8ziYpsFmsfEPtYtqnIIzwgcNIrY\nbJggJqANelp0Ma/PmEan0OCERiXJ79" +
       "xNx3IMD1OkBgJkyCvynAxApmuZcMrGLOdxUd35ybCpqnxL\nRnReLxd02/JT" +
       "kkQBBztUnrOt9xKHcKEz3dkBRcxEPCGnpC1zZiJteBMgbNmXl4Y+Y5fGbmm1" +
       "/NAL\nPYiVbQ9tsU2MuYy4bKf80oliktsownQCTSJJFILoFMhSEfMCMNuCvJ" +
       "TpLZt0B7YbjbSNkTfBetXO\n1eHJYldSjrjLkbKmta4QRAzONxkW0iqWsvNG" +
       "kKDl3osnkR84u6hhzETMCgShcC8I0B3C7CgboEOZ\n4MzmOASPQeQD+qggJi" +
       "sLP5aQtNDTCR2gBDaNTV/sIFtUioCQjoLMKFTgQ6YuHZGTuEfMU1gmrecE\n" +
       "iLCL6YxeDIPSYvBioazUde6N5U3cOYEY4fhuqZfb9rSkPQ1V1lKQOoQNzUWr" +
       "ZkKSJeaaaUbbAkA4\ntJuyBe2lx0PjDg8zabnKzG6cpnGVARNtE+uIEuI5Zd" +
       "KyTEp1TEcbUy4yX5MIvEMQ0yeWvZNgF+4J\nJMVtpIWL1XFbMzTlDyE2N3MV" +
       "BDNnQbCR22WZAGtxO3eZmIiqGLJ6DC029dwY5yiY6miOAJG3Ytiy\nW7Fwyy" +
       "3WpRWsaHokNBg51JmaljDuCJOiRvp2XrSNU03mvqGic1LCZgUe7zQ03ZmMGy" +
       "xdkTtG0WmU\nOdtjFtbN3vNaCEC1zcE3LfIwXFG6LwojHJnkOpm21SSZFMxk" +
       "bvOTJD8YzlxXW95c5aNmdxISCHZ5\nIQt3ASwrJ34EQBy7zxLP0rcuG2DIsE" +
       "iX0/2OEjqaZeG9sRYBbzyiMqvF3CXUustTS2lkSkfmVlZ7\nPCAKpjMeGDe9" +
       "uE7/3gXM4zA+bhlZ0JzpcAuAwOyYodLoYIr4uPD4NG5wGc8PvTu1XMFYcbgW" +
       "A7mx\nd1Zrw23NUaF423i2goqJA26NoDV2IVnlJzXBh/jyIAWUC2QTZ2UnRl" +
       "3EzlxsG5Q+HafxCFtm1Epk\n0tmM4wtqcsDSusUxxtrvDiNqTWz38UnxcnIj" +
       "0jFQqUMh22WeSgHNYXKMEA2sgXWs+9w05MtmCRIa\nDSWLxhA6SSwOtZUuYZ" +
       "Njw4Rv1wJdwrvdfEps7ZmfcIC1IYf1EoysnJHUUFkcDAkPm35ZXe2x1Soh\n" +
       "6dJTNs1sXs5P3cLVA7xlIdE7iolmjscYuYoZYWzCi9Wex3a2RgxHG39T6g2w" +
       "JjrKno3tLdN64ZEM\nGZyBTNewZHlLaTMoDRtod9DgGMBDQZ2Iuu/LzYxAtz" +
       "vHrmLPp4x1FgyzbVXkADhF1+R2jGQHLiak\nAgozadrMV2tyB8GZgocFtdoL" +
       "mLefAkjhUlwczXvfhGgzd74yxzscxA/kRGOH+NhUEDVs1eKAdwxo\nJmHXaq" +
       "5WpIhlrvdcr2huA2oyFExlaeIcTjLFsR29TeViW+79VLak5X4aqkrYetzQsB" +
       "fFkQuPAg+O\naVsAYn3WIm2s8xQ4yTQbqMflCGzI6DjyRnJqiC3ekLtNOzml" +
       "R/W08iaKNWmPGzCIwVE6lBauIrqq\nNYIkhcBJkhH3qRa1isj6mKDiStADL/" +
       "JXhFBmswI7Ldi2l9Xdixsi3qertbtYTiDYaMM8I9lh2NFr\n+4DoymxCCTiw" +
       "mk3EIy8L9ipfj1VhHjcpvJE3MCFR6jJKFxIGrvD8tM1aj5bc8iCh4TiP+w+D" +
       "vOSQ\nYRuOrAk4h+eMvlov1JZaeykk76SG85f6ijWZjCN100e8fcqTMoergF" +
       "ex7QRjN57PJ4JLQanEztA1\ngarocGGkGTizyfWpmnAzcRzSm7lojMZsHbGQ" +
       "q0x8ngrdNU8jecZ7u3Y0Q5XEGUXQ0nEQWxZ6tziJ\nNUi1aDdEh24s7XECDr" +
       "Fx4mheZrRKFGzHp5nubIlJTPsSaKvLeopqzkyxoHTTMUegzS0gwkeozsIC\n" +
       "3NCSkUGdCiX0EOqDkApV1aIQO96TKRKbFGN/pWrz9aSNlGmdOmbilkLsjrYR" +
       "snAad06LjHucNRF6\n6CNzzYzHp1CDdnXiDSdMqU9WJOwyE12T9/6IZEeKso" +
       "ZpIbfy/aQOLHC7nSxRtOyofIXMwpE/QifL\n/RZQ1stUbkSt6vAkCrFcXgz5" +
       "/Cj1LxswRYPhmMxhyT0FHIjrtTo+ce5xEoJhf0OQsR0GcCMKaLeY\nzzsrJy" +
       "uLmtVdj9U+Rs5BfCJMleGiE/m68LB0BQlmtjeXrtbuaxGWNtM57RAQZnjEbL" +
       "22BA3K+1eM\n9rBdk7AhjFwxbsxIYyBqsaRSZG0ibjDcIM088jFur7Ipibs8" +
       "sWVKbALDe4wn3SM4RbSiBBoI1dgw\nKBY7hmdGUg0fInbHbbLNNssPqpSYYq" +
       "WmDjZc210K5b33k08tbK1Uyx9Bbtj2b5HxCbWkVOiUNVLW\nGaGWABA6yqQ7" +
       "lpsNIa8lb2KRqU+s5EAHY7C259HwoKDlLJdZzl0cfUPOoY0DhVrBxDMuVnFw" +
       "KQkZ\nY6JitIjsaLFyjGCUZJAck8yi9tYnRpGRLp/0FjdNysnQnbubpXjAfV" +
       "1VxjJaoZsel9044GdFw/hE\n4U/H9CFqZ1OgokAwgc3eSHo0RAINGmqiumhK" +
       "dTOkU6uSM4bjjJmRUCEkFmckO6dMot26pIrc3PuA\n0Yu+njUgkiZZ73EqOj" +
       "yE8VJUREPZEyqEEY0MMbl5csD9QSvHhyEO6bZgqGLh7CR7tVdp5ahw1BhT\n" +
       "PALXXX2t+aQ0ETxZ6T8IxtACpA+7bkrzILXz10xQ7bcKe0gDIJmlU2+Y+9tg" +
       "Yq5we1mDooyi3ozR\nGbp3qSICoGAv2UgHYE8CDjC/AootXBgOcCycdlfmY0" +
       "VYKfYidXVk64oiFgwJyEVH+WjMTInN3EbN\njZ2jrWk5x90YtfS9YToOOkFn" +
       "HVy4oBUkbELVvIzEoI0qUBtMOXxOVsZmr4PWVh0WaqHDxGI8kjA6\nZUyMT8" +
       "YSIk+NhVIuTQWa2eZU5QnDX+WxXVOjXLQ3KNYg/WfBch1FQlx7CTSnpLZ/Ox" +
       "WHexlEdaRC\nSQgy0Y5QqQl7AoKloeZmO5rM+Ek1whu0RlbhQl3PXBma8nxo" +
       "ZxhcRGhIU0CkRftKloxpdOiG08N4\ngRutCDjAPlsDsr/KtEMCbdKuowtOKD" +
       "JhUchTxhdrumV4lUdwWyUIxGCxGECEegeXgGeZicfIynZI\njvDVqBWgfgMa" +
       "BFzpPqaETZXSvRedlgCyYcFaQowCagpss9pOeDBAtNEWPvDLEKzqNXlEa1ie" +
       "mnbF\nOdWw3I9PEwACCn9vkKONlSKtsF8nounOHOQw3xn2bGTikDFbBW1Uwg" +
       "Yj1iMu52F1f9zkuDneBhbC\ntRMPNBp6OHO7o9WkERuLOdmwORsHG7TcO/SU" +
       "OeCziuKwPFmFWiR6k3DM+X5uQX6Hwg3Or6NCJhfQ\nPJXM3qe64twfIrawBE" +
       "4LBaIhF2dcgVYEGhIWe3mf4wIrHE2S4hpxrbBT0Uj37oZBSnaXRxuwGKWT\n" +
       "ji8UkpyT2uHIa7Q9JB0t4syjtZ17ebtdjGSAWPJe69dzIAi28wQ8WcUsPmKN" +
       "w7Tkng51qNo4m3qk\noIAZo03e5mttJ8B2dLSwobrSRoujDSxSgbRqCCKt1j" +
       "0Vq23BFGiJwIYTqTtJ1hIYJdPlfJaAfWRI\nQpUvk7rsyCXVNVt3kxAu3bXY" +
       "UNogBqwVUJFZPoaImrTBp606O7QtYDsOF09MbjUna3a66dqjhETb\nXeXs18" +
       "jIMder1Vg1wiwoMBfvlgDHDcXxZmRATQ6xxEyX49mS1LW4HOuJ4SEwkkDYku" +
       "mNuVsmbTIT\nRHpP686GdnIryDo/h6KG4OiZvws5co+4Q/REsXQjrw82fFox" +
       "3YJQ7XSzYimNM089bAN2XXkmjbGn\nzqmwpZIoXkPJeyJncmUtwpaIrf0wNG" +
       "wThgFgSFJgRWD8geoRATfWAZsmqjzqX5B3MAkfFEnLWluA\nEHCeoN0Wd5sO" +
       "jNo64CKxW5Vec0SA5YhjQKk47IFgGGm7EQwXCM7nHCPtGMCYUHzvp9cSHstx" +
       "0akK\nygAN5dDpiaWEAtMXwVSEt6jReDiJ9N/xB2xuxQf51H+eD2fHRm53U3" +
       "FjwKlymmhMjvYv+Jv9dI4C\nOyUhR91JmScOhjkHLt2tQG9fAKQNjI8GSOgk" +
       "3Dm9y7TGXVGM6vlQR835Wj91S60yS401o8bCar0L\nU9Pe7ZvxuoWDdYMpAE" +
       "IYZRupTsT4B9cxYqEZjZhYRTcBb/MRKk04rBserHpso1uE55cYrK4c1BzF\n" +
       "QCqtas3q589VdTwF6lm75gkQ5FoMoyWZY9zF4pybYn9Xabu3LlnHDyph12zd" +
       "+eH7l1RX8xFMqsGr\nulFWhW5W1eC1D8ptF7a3Mvaf+Ihi0ZNPv/X1v/Tez/" +
       "7q3UT9vZsM5yefZWkveUnGd67Z73Ny7+1v\nVwG7JPa+tv9vj35Q/+XvOzM+" +
       "s6J6yao0+97IPtrRc5nuMuEvBb+b9Pnr6hf+PTH7hT91V7pzlfUL\n33HkU/" +
       "Px8bPCS57/T+9f8u7XVP2HKo4vDnr/xQT9sLCrukh2H6TpP1xa2RY9iqy6sJ" +
       "/P+9O/Rf3X\nn3wZ/Vv3Bw9u1y/OHL50pxrwMSctYj06T3BT4hxWXpGenvfc" +
       "Lg30C3+1Pz7fH68/S4VfrueHjy+J\n/jeb5+nQFyBy/9wWbwPi3vms9Hr8xP" +
       "PM/qIo9PZc+Wn+9Dff/sv/RP+rLw3u0YMHpd/Zl6rc4IUi\n0DMO57N/6+H5" +
       "3qoGD127Yu32fLe/0OgvLOKd/njj2SLe+D9dxF0Rvqt8SW8xvXyX4s53kvDx" +
       "Mwkf\n/9+S8N7VuC5zfncxT72Y5bcT81wVeVYpvXeV8t4718LIk1uFIqbf3O" +
       "9eJ/p/rvAfOJcjfOfdXulX\nf/L0DLinN1XSp+yNu/nMbXdT2mZd+FX7hNON" +
       "Z07j/8fF/7nvsnjlTPQjvet/9abrXHH79If+FHL9\n64L5pW9+/1d+KXv8zy" +
       "4O5oO/F7zCDV516ii6XWC81X6YFbbjX8R55epTrgv/mar3Qs/r1tXgwfly\n" +
       "WcZPXyn+Sm/Lz4PQz2Y3O/Xm7Z16FhX+Nxd3+hIBIwAA");
}
