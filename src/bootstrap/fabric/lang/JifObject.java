package fabric.lang;

public interface JifObject
  extends fabric.lang.IDComparable, fabric.lang.Hashable,
          fabric.lang.ToStringable, fabric.lang.Object
{
    
    fabric.lang.security.Label jif$getfabric_lang_JifObject_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.JifObject
    {
        
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
      ("H4sIAAAAAAAAAI1Zaazk2FV2L9M9XdPJLJkko1k7kwFlMHl2lV0bLQS1uWyX" +
       "XeXyWmWIOl7LrvK+\nlwMRCJRJiFiTsEiQ/EGKhPIDEQF/IkAkrAoSmh+EPw" +
       "RQIoQUguAHIooCwa56r/u91zNDnlSuW/ee\ne+6553znXN/vfe6bwCNxBLxs" +
       "KqrtnCT7wIhPMEUlKEaJYkMfOUoc81XvPe3qj7/7V37kZ7/9p1cB\noIiAO4" +
       "Hv7DeOn5zOeUj8h977nfzLr5HPXQMel4HHbY9LlMTWRr6XGEUiA7ddw1WNKB" +
       "7ouqHLwJOe\nYeicEdmKY5eVoO/JwFOxvfGUJI2MmDVi38lqwafiNDCiw5pn" +
       "nRRwW/O9OIlSLfGjOAGeoLZKpkBp\nYjsQZcfJXQq4YdqGo8ch8GHgKgU8Yj" +
       "rKphJ8F3W2C+igEcLq/kq8YVdmRqaiGWdTru9sT0+Aly7P\nuL/jV2aVQDX1" +
       "pmskln9/qeueUnUATx1NchRvA3FJZHubSvQRP61WSYBn31RpJfRooGg7ZWPc" +
       "S4Bn\nLssxx6FK6tbBLfWUBHjnZbGDpipmz16K2bloLW7c/p+fY/77zlXgSm" +
       "WzbmhObf+NatKLlyaxhmlE\nhqcZx4nfSk8+SazT54+oeOcl4aPM4Pv+UKD+" +
       "9Y9fOso89wYyC3VraMk97Tud5194ffD1W9dqMx4N\n/NiuoXBh54eoMqcjd4" +
       "ugAu+77musB0/OBv+E/fP1T/2O8Y2rwC0CuKH5Tup6BHDL8PTRaftm1aZs\n" +
       "zzj2LkwzNhICuO4cum74h9+VO0zbMWp3PFK1bc/0z9qBkliHdhEAAHCz+jxb" +
       "fRrA8e/wnQBvI23z\nuL2TKseCBCAgIa6AD/m54UFB5NdDMVR53A5iA6pkIl" +
       "uD4kiDotRLbPd+1wE7F5QV9eJvz69cqXzw\n/OV8dCrw4r6jG9E97bNf++uf" +
       "mMw+9tFjdGtEnppdgeWo/aTWfnJfO3DlykHruy96tg6VXmfUv/3e\n3Sd+4f" +
       "3xH1wFrsnALdt100RRHaPKRMVxqo3p95IDFJ88B/sD2iqo3lYr1FYJcM+pFB" +
       "2ypHJfVpWg\ny+h8kNNE1VIqyL3+4e/+7b/fyz9fA6kO/NO19qNpVRh3R9tu" +
       "v8p9gPzgR1++Vgvl1+soFIdsfFe9\nymU/YXVdONPvqh/6ry9+unHnqL+e89" +
       "xBwfX44Ty4MPGeVv6R8Olv/U3y1YOLb1X1KFEqFFXJ/eLl\nbLyQQHVaXjZJ" +
       "VKIHent/lz1543c/414FbsrAE4c6p3iJqDipwRlVHW3Y8ei0kwLedmH8YtU5" +
       "Bvbu\naXYnwPOX7Tq37N2zElm74Np5yFTtWrpuP3qA3+2DzOPfPf79b/05xf" +
       "8rR/wf4z6u1uR9rD5kJkWV\n6Ce1YXfep/luUCVXdGdjVM5SEkN/NQiKK0BQ" +
       "K/3+OsKXvV5b9a2fuQF/5QuP/dnB12e1/PFzRb/y\nzLEyPPkAIHxk1B77h1" +
       "9nPvGpb772Ywd0HOFxLamU2J5SOeVGkKqOXeH/Rnw4rooEuJn70c6IXjns\n" +
       "8x0J8PRpwhy7T6TD1yERDxLveVN//PzRH68e/HF21FUa3tITVyrb4JPmCVxr" +
       "RQ+6Xz08f/DU9rp9\nUj+g+gFXBj+7dbRXRqfqxKrSVKXwlaPRZ3t44uCWQ8" +
       "ofD6Nz9tePdnHI/rc/EKP86sT6+Nd/6cu/\n+N5/rNxOAo9kNcQq9J7TNU/r" +
       "I/0jn/vUC4998p8+fkgiALjyA5//AozXWn/4ezL/hdp8zk8jzaCU\nOKF93a" +
       "7Obv1sBw8nIhPZblXvs9MD6Zdf/O1/+fzX2KePte54ar/3oYPz/JzjyX1A82" +
       "NBUa3wnrda\n4SD9JfA9n/sw+1X1eKI9dbFKTrzUbX/m741Xf/S29gYV97rj" +
       "v6HDk8e+gaMxMTj7o5rrsZRrBSsY\nEJTFdIl2+SCHira5ZqNNoLcxRYiHom" +
       "2sJgUjY6kekgNRz70kVePSlButZrPVBveUm8uEtCNsQcSG\nQk44gsLZ4gAT" +
       "RXnqO5hiC8uZj8mSv+OGc0cgKZHA0VAgRHvkL01z7vZb7azRzdFgkKy4KNVd" +
       "xMvK\ndtbsdXswCObTIN4jnNuRSa4p2CYBkV16k0O+xe9bSkCsEaW/6ax528" +
       "YpsGWOx3DR8BTYJacGGznN\nsBVOTFFfcItp2zf2fIL6DM5HBdor40xsL5Tp" +
       "wFtSZK9JkasM5ZZCC155ypinRA5Wqh9gYzKiLVHV\n2k3VmS2EeC7BynTXFY" +
       "oJzM+VgcLE1p5xE852OSvF/NaWjYfStsu0Mmlphku5n9sd3G9yJBiZrYY5\n" +
       "jqB9jLMiL+30dQiHmT8JmoVobfZ6Xsg8SVqWy3Fll8YEO5RcjswW0jQcSVwk" +
       "NgfCZs4sA9jtRHQZ\nOQ1pncloL28lY2fYmrY9XlItL23vtoOlOOi1dzIrph" +
       "JGjuGxkdA24kBhQCkIP227rIwq/N6k5zbu\nd1cBQdoNMfDDcioFiIKEM6HT" +
       "gdiyTUPKYrCc5nN3uW6N06qCNxfr9QzmekkkkiDJe0OqKRmDhO4S\n1Ta1aJ" +
       "b7GLFRGnz1sh5s6TjXnD3TbGrUBhMHA9iSxpbcsfUxZ4wch0czw9yXGd9FWw" +
       "aOkiAWChuh\nE7Rte2TC6kZy59uWqzUC2cN0QiSLMJP3U3qrt8TVvmtpHXsh" +
       "zYl5P+vDsgEamLpzy3BTzvvpMg2y\n0aSXetOm2KI2bW00F2ZzNlnkDdHAe3" +
       "4w3/ZoRXfWU220dGFq1GepiaoVPEhnCL+CFHNt9IUdPaFC\nTGAoOVuyE3fY" +
       "UeKNPO8N0CAN6NJtM42ghUxZ2E4sh2kOM3SqdVhxu3TKOT1cN/XFdFGuA68n" +
       "FqXN\nTeb0qJwEqEtkUM7stWQ3G++NIU5o7QW/M6xFYwoTfSXOS0YKFMux93" +
       "5rWDRVVhan5LBJKUJISlhC\nl7ugHLZsn3eKhWKBih/GNBaauRxOqEIdcwgW" +
       "Eey+0UthqKvDez3dWB0RZnfrrjIZ5QtwsA12agEL\njoQJ25SiuERzWGrQM9" +
       "vZcEdAWYKBuDV07Q0v8COVUkRy29jhiQCDw0245HFhWpYWBVO4NtmxI3i3\n" +
       "nBDjJUsO+DHp7VYrsDMqdr2lZTs0IfURBOz4Bgka+oQYeLieb4OGsBVztNUJ" +
       "pO4QQtMu21mbUnNp\n26AYWRZbuCgyK+x+f5HnxGrFuHKbMTJvjFQ4VvdDAV" +
       "zN8kGrh9CziLI6DR/vjqfdfmZak6TTI2dh\nrrtkYa+WeiDBuIQX7JZeIXkP" +
       "Si2GKbcoI7KC4kRjjpvP82ga5NvdurkTrLE/Uhu8FIbo1iN3W7KN\nYZ7R02" +
       "0WCkAI5fuDMbddCh2V5qesRec9msViCI+QbS/wVntHkJkWgicg2JcVowOpKd" +
       "dItmA2Xg47\nw1k6zsHIW/QpCEnKzthMzXAnozxGqJvuhC2nGsXtuiPHiJm9" +
       "FQ3SKTRFu6shykaWtJwzcXuONkqe\nKpb2QrXQPY0SKqHjRNZ3xX257zFNcz" +
       "MNo4lA0KHKYW2OZpAZF8rMbI6MyxLahy3TH/VnM1wRJH5v\nQ40U2rJ+0V9Q" +
       "aYlNpwlG9ER/5+542ClXBTomYwEchivDdqedTTgJV30RG3VWcdRfgEwXyjzD" +
       "Z81m\ngRjKvD1tlDLpI7sokWQ7U7pW3k73+44KCbtFYMFV9ZuPRZpe6eSG7o" +
       "9gGcUoOpSTVnOE5SvP0uej\n3sAWJHGC6mLuN9qKvF16XgCRqVVSGb51Z/Fw" +
       "Nkq1PFqrhDsScNZXexg8ywRlDEusiLm+1hGX9MRN\n8hhFsrK7EjMoQttO2I" +
       "C3Q2yZWOyS1GBOlWJuDged4TgmJG3POuGsWb0BZEteGChGqEWUsmrD/Vbe\n" +
       "Qacj0g5jlGryA6k5pTQLx5DGiu/2UhCX0UEfilc2P9NRVUP5DYqruw6NI/2V" +
       "Wx1pabSIRRsqhcGU\nzdTYKjdmL0+pyltcrzmQ5WbJZX2m0cxKe9UD+4yOWi" +
       "EawsauT9og5WhCnuluXAwVJYUUQt8xkQZG\nK6ZrZZDRpFwWSQpGD225pBGc" +
       "hxhwq2ENh581IwHlUa4PmrtE2A8C1kriXragAr276sjtsPAJaaKC\nmp+QYK" +
       "ccjbRpMqWCdiBO+7yI6dJuarUDHCexhmZ6oxTXREIdlqsW5nkEsgsU2p6NQI" +
       "snUN0zS8Zm\nwf5mowe0Cld+m9nClqOWiy68R7Sduu0uk25WZG2XbzSVZYsN" +
       "Ar7E1nGT5fQdO+d4VVFoXhAll8lX\nM90btpccy7l2EzSnM4IrFbzDFrNZJ9" +
       "Kw5pKl4pwWkoVOcw2Vsw0OHRmtGbXCOs64Ny1bOmzZ2nI3\nVQbmBFFgcICq" +
       "7HhTztbjjTRPSIvl2E46LZkKOcF0j1gze9tfYYO84fNl3qdzz2vDbX25ivz9" +
       "frlB\nvKYY42Wf63Ml7O5k0plMUrooR1Rhu5OpLU40qeQLN96Tm0UvGUJDMB" +
       "qii+pE7+ZSxmAquyH7q0JK\nVjFi08lsIef4YqhyGcooLDP3Jhri2wnpzkfb" +
       "QcuhmQTpyEZqi5bcNdq6OhqH4LixGpNaNliZSVsI\nBj0JnBAIDHozEGZG6F" +
       "TXVs1o1Rm4c2iSVq9IC0gaxyCnWeMOtpTcyZhI1nvNW3Q3co+nqMZi1oqa\n" +
       "U9fC10qP9tridl2Y+DbI1L4zLXZJh1+iwlh0YW/AID6LpFqBoNXLRBNlEAen" +
       "NrS361jb7lztMdC2\nkTOwHahxiChhEQ/3vR5C5ZXrUtWfoHwwITR60ROkec" +
       "mPYriHoFYLGmw3m2wfrXQYas1RtLcznXmb\nWmI+2xiM0AxkB1umQP1OoXhq" +
       "aUVJBrMKjIMbCxxIyLoTtjyDqV5Ko62WOI7MdtHxRLMYCFrvedkH\nnQW2l1" +
       "G2FzYqYHHMAJ9bs+Uya6MwYgzDfhGsw7TVhr1Nis9kVbKZNuFlPsLgVL9bdj" +
       "gy6YLNhOtM\nJji1ztKJbEDIYEE1VoJuBrv+cJjHK0NM9DW+QUhUjFZTjR24" +
       "ELXf9ZWRIfZ77a4KlSrYtj1ZYVhB\nJQUKn5lM32p1tMU0Y3RbazR3eI8a5e" +
       "i87VYphzkhqUIMpbT8LbtyskjqgN54uO80Uxfbeyu7uc8T\nMOfb0SyOWbTn" +
       "Shtzvg1gCIrjstmYc6Adw8SqT8NqtGU1yJzjbh72SJiVCAQUx0wJDUSr6K6M" +
       "DKve\n7uu3/tn3dCF6+nCfu08zHu9B9eDdwyWieAMlCfCoosZJpGhJAty6z2" +
       "Ue1J7jQt7xBkzcyTNPf/JX\nX/3Nr1ymQK6d3R2fOU8YEeP6oqlENflz+YJ8" +
       "EMCV2Do/eGE27x9vn7VAfe964c34ycOd67XVf97+\niPKlD9SW1drwamuJH7" +
       "zfMTLDebCpy0roAx17xmw8Lr30z1jnsz95eXv1dfelt5x5T3sye255zbL/\n" +
       "8uqBEjmyKA/xwRcn3b3InTQiI0kjj7/PoDzMejGRrxl6GhkP1v21b+P/8YlH" +
       "+r9/Fbh+nlqqNbx8\niah5zPQjV3HqBc4I6EZiRX7+oOcya3PKXF45khRX7h" +
       "xZipNzrA1ZXfD/f9LmwY32Ahav1m32PPKu\n1E+x8vc7HpAzgyhS9jV5V/z0" +
       "6y/8xl8ov3UNuEIA12O7NA7cKnCBxzvVUD+9c4P1bysBXtza5isb\nIzki7V" +
       "6NtHv3ic171BkSnz2PxNjQ0shO9ieUohpOlXS37s+oaaRnHvpnx5GS115+/Y" +
       "Pv+2Lw5F8d\nQnOfNr9JAY+aqeOcZ83OtW8EkWHaB3NvHqNx9F6eVPF7YFTN" +
       "FFRfB3uzo0SZADce5P+HgrO9PHV+\nL0ezi/8DtE0iIdkZAAA=");
}
