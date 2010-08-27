package fabric.util;


public interface Iterator extends fabric.lang.Object {
    
    boolean hasNext();
    
    fabric.lang.JifObject next() throws fabric.util.NoSuchElementException;
    
    void remove() throws java.lang.IllegalStateException;
    
    fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator
    {
        
        native public boolean hasNext();
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public fabric.lang.security.Label jif$getfabric_util_Iterator_L(
          );
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    long jlc$SourceLastModified$fabil = 1282915709000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAMU5WcwsaVV9l7l3pucyG8MwzsIMw2gYS25Vd3VXdTsh2lt1" +
       "rb3V1lVKLrV3de1r\nV5VINBpAiCsgGBFeTEgMD0aivhA1gmswMfMgvogaiD" +
       "FRjD4YCUGxqvv+9/73nwuSGGMnVfX1951z\nvrN/Ved85mutB5K49ZKpqLZ7" +
       "My1DI7mJKSpBr5Q4MfSJqyQJV8/e0i7/6Jt/6Yd++ht/eLnVKuLW\ni2Hglp" +
       "YbpLdxXgf+g2/75uGL7yefvdJ6VG49avtsqqS2Ngn81ChSuXXDMzzViJORrh" +
       "u63HrcNwyd\nNWJbce2qBgx8ufVEYlu+kmaxkWyMJHDzBvCJJAuN+Ljn2STd" +
       "uqEFfpLGmZYGcZK2HqP3Sq6AWWq7\nIG0n6at065ppG66eRK33ti7TrQdMV7" +
       "FqwKfoMynAI0UQa+Zr8LZdsxmbimacoVx1bF9PWy9cxLgj\n8ctUDVCjXveM" +
       "dBfc2eqqr9QTrSdOLLmKb4FsGtu+VYM+EGT1LmnrmW9LtAZ6MFQ0R7GMW2nr" +
       "6Ytw\nq9NSDfXQUS0NStp600WwI6XaZs9csNk5ay2v3fjPD67+48XLrUs1z7" +
       "qhuQ3/12qkt1xA2himERu+\nZpwQv57d/AghZc+dvOJNF4BPMKPv/V2e/sff" +
       "f+EE8+x9YJbq3tDSW9o3keeef2301YeuNGw8GAaJ\n3bjCPZIfrbq6vfJqEd" +
       "bO+9Qdis3izbPFP9j8sfQTv2H80+XWQ0Trmha4mecTrYcMX5/cHl+vx7Tt\n" +
       "G6fZpWkmRkq0rrrHqWvB8X+tDtN2jUYdD9Rj2zeDs3GopLvjuAhbrdb1+vqe" +
       "+rrROv2Oz7R1g6ht\nrNSOebMOsTBt4SCf1H4PBgfDB8M4aARPwFrhdpgYYA" +
       "0T2xqYxBoYZ35qe3emjnKfp1U0Wz9yuHSp\n1sBzF6PRrV0XD1zdiG9pn/7K" +
       "n79nRv3MB062bfzxNtNp68kT8ZPezoi3Ll06En3zvWpt7KQ34fTP\nv/XqYz" +
       "/3juR3LreuyK2HbM/LUkV1jToMFdetxdJvpUc/fPyczx9drfbTG2rtsrX333" +
       "JrQscQqXWX\n1/nnomveDWiiHim1v7323m/95b/cOny28aLG6k821E+s1TZ0" +
       "TrzdeIV9F/nuD7x0pQE6XG1MUBxD\n8alml4tqwpqkcEbfU3/s3z//yfaLJ/" +
       "oNzrNHAleT1wfBPYi3tOr3+E9+/S/SLx81/FCdjFKldqE6\nst9yMRTviZ4m" +
       "Ji+yJCjxXbqDv8ofv/abn/Iut67LrceOSU7xU0FxM4M16iTatpPJ7Um69YZ7" +
       "1u9N\nOaf4evV2aKet5y7ydW7bV8/yY6OCK+c9ph430M34waP33TjCPPqt0+" +
       "+/muu28798cv6T3af1nlyA\nNSfMrKij/GbD2Itv1wIvrCMrftEy/MbzDP2V" +
       "MCwutcKG6Pc1Fr6o9Yarr//UNehLn3v4j466Pkvk\nj57L+LVmTmnh8bsOws" +
       "VGo7G/+fjqwx/92vt/5OgdJ/e4ktZEbF+plXItzFTX1upBcjyrirR1/RDE\n" +
       "jhG/fJTzjXfj5TR9Uzw+jnF4hHjrt9XHz5708cpRH2fnXE3hO2riUs0bdLNz" +
       "E2qo9o60Xznef+A2\n7834ZnMDmxtUM/zM3tVentwmJ9R5ps6DL5+YPpPhsa" +
       "NaGre4eTqJzvHf3PrFMfofuQtGB/Vx9aGv\n/sIXf/5tf1urnWw9kDcuVnvv" +
       "OVqLrDnP3/eZjz7/8Ef+7kPHIGq1Ln3/Zz8H4Q3Vd35X7D/fsM8G\nWawZtJ" +
       "KkTKDb9cGtn0nw+kBcxbZXJ/v89mn0i2/59X/47Fc2T55S3enIftvrTs3zOK" +
       "dj++jND4dF\nvcNbv9MOR+gvAG/9zHs3X1ZPx9kT92bJmZ95/U/9tfHKD9/Q" +
       "7pNwr7rBfRWePvZOvJcQo7MfI8gT\necR3Ni6whBMzG2l0VhToYdIjiH3iWM" +
       "k8wcoRbfeYJOvZCLVxESNFk0W+gdJhJ2/rgSkQs4gPoIq3\nMIPp7RxmnuQz" +
       "2w0DZV0Y0KakMJ5PVoTn9iVBXNMsVK1AoDOEOZhbotS6I3SVOEO2aN42QRCt" +
       "ABAE\n6mW8v5iUJZtOWNejfEIVu/o6QqDpFg0cCKGLsRI6Iptay0mYr7b7fh" +
       "mFZswj2GTmqeF8vmg78TSs\nFUau9dKNgGSG6r1YReEhovr5qkhsYIOxVCRI" +
       "ebcXqWNoL8izDhSMeG6PYVNe421UwFlStEW+Ytve\nhCjm2eAwZNd7pyTmyT" +
       "Lk9I3iKRupS2ILaufPK22sU2Mmo7QFi9jUwSfkid8V3N2kz7tgMFTzHTpA\n" +
       "EMltz0CSlFkgFOi1Qda7baNxPKXVgNwIpBcUG6gfbPfDRY9GfJEiYJjHlhth" +
       "zhMTXEzd4UZiw4GX\nyBsyFvtRGzGCEYmUBW9JHY0f+syMiORxB2JcpoQgaE" +
       "dotspTlqIPe9QMiXm5mpWLaBlCFDDd9xaI\nvA4I2ZYcjwvp9iSVIjEsN2E2" +
       "ooSBKSa92cjdST6l71VYCUdOdzLqjPZ01KdFDOjvwojujERv5wek\nPh87II" +
       "0xFrWfOYYC8+0NsmQ6IzPMkjXP14okTIvdJNKslEecMXcHh1n9HlIe4ijfec" +
       "pQ21bsnB1X\nNB4pRQnwqcWLC4vn9AVPLqI2s+PpPaIqqa8bDhkEPZ9kLGy/" +
       "rsi8HNDeZtgfBrP9mtPSUWIj3R0T\n5NS2PxgkIp7nxNIFhJKZOUsYW/YHbR" +
       "+QpByDuxyeK9ZorSAw7ThdeNknAcRHXetQoogk2bweErYo\n6ByrdDkXI9I8" +
       "GQN0oPo2RW4YNUgcdRS0kXKNUVZPQExr2XesgMq2HkXI/obPgypmdknNBrfL" +
       "Zs6c\n6hUJJqy1tQnuSRZaO4Brj/huOV1MeUncDYBOm1jQU8CeCodACbWBmD" +
       "LjncawBwVzR+vUOfRVSrKo\n/iqbLeO0L289saMt+pk4HrPSfAABGIR1kwjG" +
       "ok0m6m206oOlRlVp9yDm2bZQRvmCpaDFSrTgXeWT\nJbT09C1SaXsQW4rhYE" +
       "iY3cgFmXTcmxhLxw35oPREkuUocd02SCqy3LigGKYXr9RZTjAqxs0IUdoD\n" +
       "0ShixnggyczIcXrLncf7O85aB57R10GgS/vQGseBzaagO0aJOnQb0ia40hMS" +
       "TF+gtFuWRr5yzFkP\nEpmx7jAyHy8NdMzA/IHHxz0ZYLbbolchHbeSCHA7XF" +
       "kMMV1YdtLnx5XeFqdc3NFMyofU6TLcsYiG\nS3gSznaY7MtYZ8db8X7cRZOu" +
       "r+pFPwWsgKBYk5EpGbMDWnKYgzLX2Qkx0iOvLUWBbVRr2WeXZDcL\n47QzAE" +
       "HdyPsua0nqXtsMK0Vf9X1M0CmuzhdMzM1R0MZEO+jLAw1Y5jAz2bv4lOTLNi" +
       "zbIWTNIzlZ\n02gs8Yw+L/Sl0OfEIezTVRVC2HiAdCzZXOb2IMhzTO4rSzzB" +
       "hj3a0UiDwmBR5Na7UODEdipOAD+l\nLDxlkfUQ4tmAFx2R5PfyuOQ3M4ehvG" +
       "k5EOf7eSJFUj5y9zIbhQdgTyWWyOSL6XgJqBNUCj131q4U\nshxqk1nf9Iel" +
       "v5CMAiO0Yr0AEbIbQ+JuGOz0TAR7LOYgyypDOKzraayNedtODszjIhC7WAge" +
       "is0o\nstrzYAJMac4tFL7IaGd1WGT6YSmomERTE73DsHZkanYhFHm4MLlRZc" +
       "W7Cp/2t9Zw2esN1MlCi+Yr\ns3IdhFm0zWggMMTWobe2SUXEsCABf6AnpO5W" +
       "/sBMGZvAN55YynI/ZpchKiFa3l2kFRTja1oYySNl\nlsiBZSMFae7bC0sHfA" +
       "CQ+0vAZwRaozu+v6bzccBCnGzs9PnOEJBoWzqVBFeQz+/ivR1urAXEBBbP\n" +
       "dDEr2WyXy6Fb4grfrg4ovhfX+DRdyqo8g9H+0iFnwdo1QE1bcfSyUkm0O6H2" +
       "Gg2Ufh0gADNY67Hr\nG2QnZ1Gik8m9TiKFCpQBbTZDZ97EXvFQ0sWkxXZjmy" +
       "w9EcI6lKCNj6mHkvLoA64O+lMM5Asy6XDB\ndCZQMTMld6I/7Y9h3Bpxw7kx" +
       "AtpiNOrMxryQWuI8XUt7IvUUXphR/ELTFF3iKo7GQ0PWZMnLlweD\n6RKcsg" +
       "wJctaVHHsQbWEdhgtOX6H7iGkjDD3WO9V8NUW5jSH4w7gju2ZldcJsllUdkg" +
       "cGxWIwWacF\nUM0H+Ly3OdhAMob0GKtDypN9wwAYd6wPmcmyLRMWP+qPdqvZ" +
       "jMtdkuXRQ284I6aK3T2My5lkrlaA\n7JIQl0FZxzkwdcD53e6kNDpz36LdXg" +
       "80HYO2vLGeKO241MiDpNGJy2gTN9yt05kzZgZcQBRydyVO\nUZCxBV/2uyIf" +
       "seJeG2fVcpDG82zYRwcLOBNdrNwtBDXZh1YbLWJ1SNrbcQLALLtKrcVmVck7" +
       "CK6/\nhxddYjcvZc/S7MgCdQEMuYWer1bFEB5aQgipldtdu1tNli2+N10t26" +
       "NAQxbmcJ2J1Bgjo2ROEpGe\nrWE2JwjRQzqEMmbGsU0hakiR6w7CrLEsXxZ+" +
       "SLr+epOTDujzWQLnILXatdEyKTs6S5vKdLKbVzzP\nH1TCIXEz9wdeAGZ4sm" +
       "Yogumxg7EfTqi815vxNJHQMOYac9hFoUxbL7rrjd5VvPaO12wQl5G5t8LG\n" +
       "lOvVJ+FQtSV0iyJdbZJCc2zMOzpX4eggVUGwfkM6JPOQFYkR22Mr4sDMzSkA" +
       "DoEBOVi1cXYojmej\nvQ3HLLju4mI2VBeaENJZNqkMwS6NCK8WpZ+nIQpLM2" +
       "5uCVuuk/oVMACQXhzHPuj0dM8GAbbf5v3t\n3GPJIpskioWgQbijIYQhl7zp" +
       "R4O+KPb11ZzmyRxbRlN7JizwNFG4zJ7B7NTGAcbTsrmYb2wnSsCy\nnQ46gB" +
       "hxgaMPhwawg6qJtQJWecHl+xWCxvvdMu+ZXJ2uOX8K90VflYSaZxLra0IOgv" +
       "6C6iKBOcBi\nkBlN2lOQx0s5zXMvOoC+XJ/QnTEtzdj+BO2hXYEoR5nN5SIK" +
       "s5nTAXOU3uVgERy4GO5lOsy5oo/7\nuQBqiy03aKtgnBAJA+cLznDNYbLAiT" +
       "5QFZy0cX1kqANGSnf3GwhdMf35tEeQ4xBbA+TKpAdOTxol\nBz+kaT9fdIqc" +
       "ddoFEoDmvC/CaoG6OWpYqrSIXdEDCp6JV2wESEtqWaBgB0FNOFMPAOmudSXF" +
       "O0pe\nv38gRIfuzeGyHEwPEte2RoPlXNuFXnfsAKQ129aJC9plmLGGt+nUNU" +
       "yAiw9AnG6DQy8Hh/SqH2ER\n7XgDeMwSK94W+vU7CgkVjl0Q7Uxj3dWhm0GQ" +
       "YYNwX4cgVJNoz+eI4U6mqS2wK4dkuYXpziZ0vDCR\nhMjrThQ7Evs7aGIe4t" +
       "TL564OD3F93d4Lg07RQTpjuIzWBuotZ3nKahieGkZCdYq+zLh9v4L7WoTs\n" +
       "O5NxiirO1t9SWeKV/YCEs+FuYTlTie9gPaW9xc3hcDTA5aREwLHMoAElyXMs" +
       "F9YUqfoYtQkjfNXd\nYFuL7JcdGMX0LBVJN12BhSuPOYLGwhEA2+EATow23Z" +
       "1ViLicj/aVvWMSqYtOtDjpuqNetsIHqM2r\nXthDsBiNh1vDIaS4UlUIw/vQ" +
       "emmqYOr09ZEr1smhPn6ktgdnM1dREQAeo7yYrXwE1fUDpUOVsDG0\nUBXkgZ" +
       "GZrD7f2hCC78dGx66EKVVOvbQnSGZxyOghB/TkDIzctjmmI2IuRnTCo+S0VI" +
       "hoe9D4MjXd\n7iI8QGq/DOZstY+y7XTdA8PDyD34Yx8mSFjfg1M0She1gee2" +
       "C5hLrG3P6nRghaiy6vE91MSd6XoM\nJLvleBgxpbzB2cloZeh9oY7JwYI+9L" +
       "2lhIV7cRv1FgZS7odpDrsDa0FDpoC1YwwoRM8sCNDVcG+w\nLUR+VZpLS60I" +
       "eDHXIVw0/WqKz33MGACeeugIwITKIIlg0BxEnNlCG3XkciOP9hLaniioTAwx" +
       "cNVP\n9AoTJ95+uplhM0SQt12WjcG0mHY6fXC4Xxkwu+2OxntgPbFkiDRRnY" +
       "hoKnY4XKTTOgCsXrv+qKOY\n3Bmvtr64GuxnK3jrlNt0yw3TvbAaYFV3r5lo" +
       "/Xbl7GqbiRFsDaEKXm4knZpZmRRim26OkOySweis\n7WwwpsDrM4BZTnpOdg" +
       "ijqaV2BvLeTqN8NcqSRafqpod64yXYVxzdRzrIQI4jiRuOELg4JNN1/ZmY\n" +
       "SC43KNuC3UFyM19st6q6YsuJ3FETeL9wSxAdxqhI+DtPY/YbpzMUbG07DUFH" +
       "L6F4quEYSonIwEm9\n3kBc+lCa74U2v1yRnMDWn1gWAOK+G63koQyAhskDSw" +
       "Y2tjteH8IFPkHnHm73QAA1mQrcKhXUsUfI\ndjAqsJhBWMIYjUbvbOoI1HdV" +
       "YnnyWCG607U4VVaaxVePZYniPkTS1oOKmqSxoqVp66E7rZEj2XPV\n1Tfep7" +
       "B/8+knP/LLr3ziSxeLqq2m1PL8t+tHHMss79/+2433KV94V4PaIOL13mkQvs" +
       "M1csO9u+tF\nIsyx/XJWzHxUfOHvMeTTP36/ou4L3xHzlvZ4/uz6ys7+08vH" +
       "KuipcPq6/s+9SK/eWy5tx0aaxT53\np2j6+kL3Kg40Q89i4+6+H/sG/q8ffm" +
       "D425dbV89XkxsKL12ozT5sBrGnuM0GZw2ndrqL60Pvzsz5\nQm0jeH09VV9v" +
       "uF2YPD6bxcePZdcnirvFqXuc4HIz3pw3+aXmLtR6fOPdOusojpWyqcMXP/na" +
       "87/y\nJ8qvXWldImpl25Vx7JG07inJ36bQ3K1zi81/NW1d3ynJwijS4/o0PO" +
       "HM63k1CFxD8Y//b90j2NP1\n9chtwR753wp2ka37Ll46Ll46q62+dL6fsgjY" +
       "TNvNXMMz/HRWaEbYdKWOfARp7SdnstV4b7qNd6yk\nkrZ5qtffR8Lm+dhtCR" +
       "/7/5DwhbsVX8J1DUtxm8auca9470lb12LDC3LjovGu5oGtn5erKZTfbp5d\n" +
       "Ool16cVTrfzmud4BWW/6P7cO/k/kP++VH2wq1Lb5smWkJ4Pdagx966xxdos+" +
       "09Iz5+2ZGFoW22l5\nk1ZUw61T8INnCE2X4unXNdJP7V7tpdfe/fbPh4//2T" +
       "EN3GnJXqdbD5qZ655vypwbXwtjw7SPzF4/\nRf5JLR9P61xx1zdrQzSPI7sf" +
       "O0H8am2zu4fBJ8IzUZ44L8rJL4v/Bg+zrv01IAAA");
}
