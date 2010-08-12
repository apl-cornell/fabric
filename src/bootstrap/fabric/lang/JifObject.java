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
    long jlc$SourceLastModified$fabil = 1281544053000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAI1Zaazk2FV2L9M9XdPJrJmMZs/MgDKYPLvKro0WgtpctstV" +
       "5fJehqjjtewq73s5\nEIFASSBizYRFguQPUiQ0PxAR8AcBImFVkND8IPwhgB" +
       "IhpBAEPxCjKBDsqve633s9CXlSuW7de+65\n557znXN9v/fm14GH4gh4xVRU" +
       "2zlJ9oERn2CKSlC0EsWGPnKUOOaq3rva1R997y//0E9/40+vAkAR\nAS8Hvr" +
       "PfOH5yOucB8R949Zv5Fz9GPncNeFQGHrU9NlESWxv5XmIUiQzcdg1XNaJ4oO" +
       "uGLgOPe4ah\ns0ZkK45dVoK+JwNPxPbGU5I0MmLGiH0nqwWfiNPAiA5rnnVS" +
       "wG3N9+IkSrXEj+IEeIzaKpkCpYnt\nQJQdJ3co4IZpG44eh8BHgKsU8JDpKJ" +
       "tK8GnqbBfQQSOE1f2VeMOuzIxMRTPOplzf2Z6eAC9dnnFv\nx6/NKoFq6k3X" +
       "SCz/3lLXPaXqAJ44muQo3gZik8j2NpXoQ35arZIAz35bpZXQw4Gi7ZSNcTcB" +
       "nrks\nRx+HKqlbB7fUUxLgPZfFDpqqmD17KWbnorW8cft/fpb+75evAlcqm3" +
       "VDc2r7b1STXrw0iTFMIzI8\nzThOfDs9eYNYp88fUfGeS8JHmcH3/AFP/esf" +
       "v3SUee4dZJbq1tCSu9o3O8+/8Nbgq7eu1WY8HPix\nXUPhws4PUaVPR+4UQQ" +
       "Xep+9prAdPzgb/hPnz9U/8tvG1q8AtArih+U7qegRwy/D00Wn7ZtWmbM84\n" +
       "9i5NMzYSArjuHLpu+IfflTtM2zFqdzxUtW3P9M/agZJYh3YRAABws/o8W30a" +
       "wPHv8J0A7yJt87i9\nkyrHggQgID6ugA/5ueFBQeTXQzFUedwOYgOqZCJbg+" +
       "JIg6LUS2z3XtcBOxeUFfXi786vXKl88Pzl\nfHQq8OK+oxvRXe2zX/nrH5vM" +
       "fubjx+jWiDw1uwLLUftJrf3knnbgypWD1vde9GwdKr3OqH/73TuP\n/fwH4t" +
       "+/ClyTgVu266aJojpGlYmK41Qb0+8mByg+fg72B7RVUL2tVqitEuCuUyk6ZE" +
       "nlvqwqQZfR\neT+niaqlVJB76yPf+tt/v5t/rgZSHfinau1H06ow7o623X6d" +
       "/SD5oY+/cq0Wyq/XUSgO2fh0vcpl\nP2F1XTjT76of/q/Pf7rx8lF/Pee5g4" +
       "Lr8YN5cGHiXa38I/7Tb/9N8uWDi29V9ShRKhRVyf3i5Wy8\nkEB1Wl42SVCi" +
       "+3p7f5c9fuN3PuNeBW7KwGOHOqd4iaA4qcEaVR1t2PHotJMC3nVh/GLVOQb2" +
       "zml2\nJ8Dzl+06t+ydsxJZu+DaechU7Vq6bj98gN/tg8yj3zr+/W/9OcX/a0" +
       "f8H+M+rtbkfKw+ZCZFlegn\ntWEvv1/z3aBKrujljVE5S0kM/fUgKK4AQa30" +
       "e+sIX/Z6bdXbP3UD/tIfPvJnB1+f1fJHzxX9yjPH\nyvD4fYBwkVF77B9+jf" +
       "7kp77+sR85oOMIj2tJpcT2lMopN4JUdewK/zfiw3FVJMDN3I92RvTaYZ9P\n" +
       "JsBTpwlz7D4RD1+HRDxIvO/b+uPnjv54/eCPs6Ou0vAdPXGlsg0+aZ7AtVb0" +
       "oPv1w/P7T22v2yf1\nA6ofcGXws1tHe210qk6oKk1VCl87Gn22h8cObjmk/P" +
       "EwOmd//WgXh+x/930xyq9OrE989Re/+Auv\n/mPldhJ4KKshVqH3nK5FWh/p" +
       "H33zUy888sY/feKQRABw5fuUbygP11p/8Lsy/4XafNZPI82glDiZ\n+7pdnd" +
       "362Q4eTEQ6st2q3menB9Ivvfhb//K5rzBPHWvd8dR+9YGD8/yc48l9QPMjQV" +
       "Gt8L7vtMJB\n+gvg+978CPNl9XiiPXGxSk681G1/5u+N13/4tvYOFfe647+j" +
       "w5NHvoajMTE4+6Oa67GYawXDGxCU\nxfMS7XJBDhVtc81Em0BvYwofDwXbkC" +
       "YFLWOpHpIDQc+9JFXj0pQbrWaz1Qb3lJvLhLgjbF7AhnxO\nOLzC2sIAEwR5" +
       "6juYYvOrmY/Jor9jhwuHJymBwNGQJwR75K9Mc+H2W+2s0c3RYJBIbJTqLuJl" +
       "ZTtr\n9ro9GATzaRDvEdbtyCTb5G2TgMjufJNDvsXtW0pArBGlv+msOdvGKb" +
       "Bljsdw0fAU2CWnBhM5zbAV\nTkxBX7LLads39lyC+jTORQXaK+NMaC+V6cBb" +
       "UWSvSZFShrIrvgVLnjLmKIGFleoH2JiM5pagau2m\n6syWfLwQYWW66/LFBO" +
       "YWykChY2tPuwlru6yVYn5ry8RDcdulW5m4MsOV3M/tDu43WRKMzFbDHEfQ\n" +
       "PsYZgRN3+jqEw8yfBM1CsDZ7PS9kjiQty2XZsjvHeDsUXZbMluI0HIlsJDQH" +
       "/GZBrwLY7UTzMnIa\n4jqT0V7eSsbOsDVte5yoWl7a3m0HK2HQa+9kRkhFjB" +
       "zDYyOZ24gDhQGlINy07TIyqnB7c76wcb8r\nBQRpN4TAD8upGCAKEs74Tgdi" +
       "yvYcUpaD1TRfuKt1a5xWFby5XK9nMNtLIoEESc4bUk3RGCTzLlFt\nU4tmuY" +
       "8RG6XBVS/rwXYe55qzp5tNjdpgwmAAW+LYkju2PmaNkeNwaGaY+zLjumjLwF" +
       "ESxEJ+w3eC\ntm2PTFjdiO5i23K1RiB7mE4IZBFm8n463+otQdp3La1jL8UF" +
       "sehnfVg2QANTd24ZbspFP12lQTaa\n9FJv2hRa1KatjRb8bMEky7whGHjPDx" +
       "bb3lzRnfVUG61cmBr1GWqiagUHzjOEkyDFXBt9fjefUCHG\n05ScrZiJO+wo" +
       "8UZe9AZokAbz0m3TjaCFTBnYTiyHbg4zdKp1GGG7csrFfLhu6svpslwHXk8o" +
       "Spud\nLOajchKgLpFBOb3Xkt1svDeGOKG1l9zOsJaNKUz0lTgvaTFQLMfe+6" +
       "1h0VQZWZiSwyal8CEpYsm8\n3AXlsGX7nFMsFQtU/DCeY6GZy+GEKtQxi2AR" +
       "wewbvRSGujq819ON1RFgZrfuKpNRvgQH22CnFjDv\niBi/TSmKTTSHoQY9s5" +
       "0NdwSUJRiIW0PX3nA8N1IpRSC3jR2e8DA43IQrDuenZWlRMIVrkx0zgner\n" +
       "CTFeMeSAG5PeTpLAzqjY9VaW7cwJsY8gYMc3SNDQJ8TAw/V8GzT4rZCjrU4g" +
       "docQmnaZztoUmyvb\nBoXIspjCRZFZYff7yzwnJIl25TZtZN4YqXCs7oc8KM" +
       "3yQauHzGcRZXUaPt4dT7v9zLQmSadHzsJc\nd8nCllZ6IMK4iBfMdi4heQ9K" +
       "LZoutygtMLziRGOWXSzyaBrk2926ueOtsT9SG5wYhujWI3dbso1h\nntHTbQ" +
       "YKQAjl+oMxu13xHXXOTRlrnvfmDBZDeIRse4En7R1eplsInoBgX1aMDqSmbC" +
       "PZgtl4NewM\nZ+k4ByNv2acgJCk7YzM1w52MchihbroTppxqFLvrjhwjpvdW" +
       "NEin0BTtSkOUiSxxtaDj9gJtlBxV\nrOylaqH7OUqohI4TWd8V9uW+RzfNzT" +
       "SMJjwxD1UWa7NzGpmxoUzPFsi4LKF92DL9UX82wxVe5PY2\n1EihLeMX/SWV" +
       "lth0mmBET/B37o6DnVIq0DEZ8+AwlAzbnXY24SSU+gI26khx1F+CdBfKPMNn" +
       "zGaB\nGMqiPW2UMukjuygRZTtTulbeTvf7jgrxu2VgwVX1W4yF+VzSyc28P4" +
       "JlFKPmoZy0miMslzxLX4x6\nA5sXhQmqC7nfaCvyduV5AUSmVkll+NadxcPZ" +
       "KNXyaK0S7ojHGV/tYfAs45UxLDIC5vpaR1jNJ26S\nxyiSlV1JyKAIbTthA9" +
       "4OsVViMStSg1lVjNkFHHSG45gQtT3jhLNm9QaQrTh+oBihFlGK1Ib7rbyD\n" +
       "TkekHcYo1eQGYnNKaRaOIQ2J6/ZSEJfRQR+KJZub6aiqodwGxdVdZ44jfcmt" +
       "jrQ0WsaCDZX8YMpk\namyVG7OXp1TlLbbXHMhys2SzPt1oZqUt9cA+raNWiI" +
       "awseuTNkg5Gp9nuhsXQ0VJIYXQd3SkgZFE\nd60MMpqUyyBJQeuhLZdzBOcg" +
       "GtxqWMPhZs2IRzmU7YPmLuH3g4CxkriXLalA70oduR0WPiFOVFDz\nExLslK" +
       "ORNk2mVNAOhGmfEzBd3E2tdoDjJNbQTG+U4ppAqMNSamGeRyC7QJnbsxFocQ" +
       "Sqe2ZJ2wzY\n32z0YK7Cld9mNr9lqdWyC+8Rbaduu6ukmxVZ2+UaTWXVYoKA" +
       "K7F13GRYfccsWE5VlDnHC6JL59JM\n94btFcuwrt0EzemMYEsF7zDFbNaJNK" +
       "y5Yqg4n/PJUp+zDZW1DRYdGa0ZJWEdZ9ybli0dtmxttZsq\nA3OCKDA4QFVm" +
       "vCln6/FGXCSkxbBMJ52WdIWcYLpHrJm97UvYIG/4XJn357nnteG2vpIif79f" +
       "bRCv\nKcR42Wf7bAm7O5l0JpN0XpQjqrDdydQWJppYcoUb78nNspcMoSEYDd" +
       "FldaJ3czGjMZXZkH2pEBMp\nRux5MlvKOb4cqmyG0gpDL7yJhvh2QrqL0XbQ" +
       "cuZ0gnRkI7UFS+4abV0djUNw3JDGpJYNJDNp88Gg\nJ4ITAoFBbwbC9Aid6p" +
       "rUjKTOwF1Ak7R6RVpC4jgGWc0ad7CV6E7GRLLea96yu5F7HEU1lrNW1Jy6\n" +
       "Fr5WenOvLWzXhYlvg0ztO9Nil3S4FcqPBRf2BjTiM0iqFQhavUw0URpxcGoz" +
       "93Yda9tdqD0a2jZy\nGrYDNQ4RJSzi4b7XQ6i8cl2q+hOUCyaENl/2eHFRcq" +
       "MY7iGo1YIG280m20eSDkOtBYr2dqazaFMr\nzGcagxGagcxgSxeo3ykUTy2t" +
       "KMlgRoFxcGOBAxFZd8KWZ9DVS2m01RLHkZkuOp5oFg1B6z0n+6Cz\nxPYyyv" +
       "TCRgUslh7gC2u2WmVtFEaMYdgvgnWYttqwt0nxmayKNt0mvMxHaJzqd8sOSy" +
       "ZdsJmwnckE\np9ZZOpENCBksqYbE62aw6w+HeSwZQqKv8Q1CokIkTTVm4ELU" +
       "ftdXRobQ77W7KlSqYNv2ZIVmeJXk\nKXxm0n2r1dGW04zWba3R3OE9apSji7" +
       "ZbpRzmhKQK0ZTS8reM5GSR2AG98XDfaaYutvcku7nPEzDn\n2tEsjhm054ob" +
       "c7ENYAiK47LZWLCgHcOE1J/DarRlNMhc4G4e9kiYEQkEFMZ0CQ0Eq+hKRoZV" +
       "b/f1\nW//su7oQPXW4z92jGY/3oHrwzuESUbyDkgR4WFHjJFK0JAFu3eMyD2" +
       "rPcSFPvgMTd/LMU2/8yuu/\n8aXLFMi1s7vjM+cJI2JcXzSVqCZ/Ll+QDwK4" +
       "ElvnBy/M5vzj7bMWqO9dL3w7fvJw5/qY9J+3P6p8\n4YO1ZbU2vNpa4gcfcI" +
       "zMcO5v6rKS+YGOPWM2HhVf+mes89kfv7y9+rr70neceVd7PHtudc2y//Lq\n" +
       "gRI5sigP8MEXJ925yJ00IiNJI4+7x6A8yHrRka8ZehoZ99f91W/g//HJh/q/" +
       "dxW4fp5aqjW8como\necT0I1dx6gXOCOhGYkV+fr/nMmtzylxeOZIUV14+sh" +
       "Qn51gbsrrg//+kzf0b7QUsXq3bzHnkXamf\nQuXvJ++TM4MoUvY1eVf85Fsv" +
       "/PpfKL95DbhCANdjuzQO3Cpwgcc71VA/vXOD9W8rAV7c2uZrGyM5\nIu1ujb" +
       "S794jNu9QZEp89j8TY0NLITvYnlKIaTpV0t+7NqGmkZx74Z8eRktdeeetD7/" +
       "988PhfHUJz\njza/SQEPm6njnGfNzrVvBJFh2gdzbx6jcfRenlTxu29UzRRU" +
       "Xwd7s6NEmQA37uf/h4OzvTxxfi9H\ns4v/A5puhtLZGQAA");
}
