package fabric.util;


public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {
    
    boolean hasPrevious();
    
    fabric.lang.JifObject previous() throws fabric.util.NoSuchElementException;
    
    int nextIndex();
    
    int previousIndex();
    
    void set(final fabric.lang.JifObject o)
          throws java.lang.IllegalStateException;
    
    void add(final fabric.lang.JifObject o) throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    fabric.lang.security.Label jif$getfabric_util_ListIterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator
    {
        
        native public boolean hasPrevious();
        
        native public fabric.lang.JifObject previous()
              throws fabric.util.NoSuchElementException;
        
        native public int nextIndex();
        
        native public int previousIndex();
        
        native public void set(fabric.lang.JifObject arg1)
              throws java.lang.IllegalStateException;
        
        native public void add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public fabric.lang.security.Label
          jif$getfabric_util_ListIterator_L();
        
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
    long jlc$SourceLastModified$fabil = 1281544489000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAL16aewrWXaX3+vd/WZ6ncmop3u6p6dB05i82lyuKloR2OVa" +
       "XXaVq8q1GKKXWu2y\na3PtNmEEAmVCIrbMJIAEEyEhRUIjhIiAD0QkImFVkN" +
       "B8IHwhgBIhJAiCD4hRFAjX9lv+7989w0jA\nWCr7+ta5555z7u+cc2+d+uZv" +
       "9Z4ri977oeNG8f3qmAflfdZxBUlxijLw6dgpSx30PvDu/pEf+Kk/\n+Kd++x" +
       "/e7fW6ovdensXHTZxVD8d8jPwPfOl32l/9qvj5Z3qvrHuvRKlWOVXk0VlaBV" +
       "217t1LgsQN\ninLs+4G/7r2WBoGvBUXkxNEJEGbpuvd6GW1Sp6qLoFSDMoub" +
       "M+HrZZ0HxWXOR51S756XpWVV1F6V\nFWXVe1XaOY0D1VUUQ1JUVh9JvefDKI" +
       "j98tD7Su+u1HsujJ0NIPys9EgL6MIRYs/9gLwfATGL0PGC\nR0Oe3UepX/Xe" +
       "vT3iscYfzAABGPpCElTb7PFUz6YO6Oi9fhUpdtINpFVFlG4A6XNZDWapem99" +
       "R6aA\n6MXc8fbOJnhQ9T53m0653gJUL13Mch5S9T5zm+zCCazZW7fW7MZqyc" +
       "/f+58/ofyP9+727gCZ/cCL\nz/I/DwZ94dYgNQiDIki94Drw2/X9rwt2/fYV" +
       "FZ+5RXylGf+ev7eS/uMvvnul+fwn0MjuLvCqB97v\njN5+51vj33zpmbMYL+" +
       "ZZGZ2h8JTml1VVHt75qMsBeD/7mOP55v1HN39J/cf2H/8bwX+623tJ6D3v\n" +
       "ZXGdpELvpSD16YftF0BbitLg2iuHYRlUQu/Z+NL1fHb5D8wRRnFwNsdzoB2l" +
       "YfaonTvV9tLu8l6v\n9wK43gHXvd71c/k9gxEgUADr7ABw3gdullc9CVqVAP" +
       "tQ1gYplBfZWfkSAkaP8jKAAE0ReVBZeFBR\np1WUPO56jOib/LqzCJ9u79wB" +
       "lnj7tlfGAMJ8FvtB8cD7ud/45z/KzP70j1/X+IzLh8ID+14nuNrv\n5gS9O3" +
       "cujH/gaROf18w/0/3nv/3Rq3/2B8u/e7f3zLr3UpQkdeW4cQBc0oljoJ7/oL" +
       "pg8rUb+L/A\nDmD2ngvgCzzhQQwYXdwF2LEBseg2TJ84twBaDsDet77yu//y" +
       "vzxof/6MqDMC3jxzfyR9ur/Kdu9D\n7YfFH/nx9585E7XPnpeju7jlZ8+z3D" +
       "YVew4Qj/gn7h/977/8jf57V/7nMZ+/MHi2/LhDPDXwgXf6\nB6tvfPtfVL9+" +
       "sfJLIDBVDoAT8PIv3HbLpzzp7J+3RTKc4glf8l81rz3/t342udt7Yd179RLw" +
       "nLQy\nnLgOtAAE1H5U0g87pd6nnrr/dPi5+tpHD9286r19W64b0370KFaeTf" +
       "DMTdSA9pn63H7xgsB7F5pX\nfvf6+V/n66EjfHB1hOu6T8Gcesaesw3TAY+/" +
       "fxbsvS97WZIDLyve2wTpGXmB/2Ged3d6+Znp7z2v\n8G2rn6X69p98Hv61X3" +
       "j5H11s/Siov3Ij+gPLXEPEa08AohfB2WL/5i8pX/vp3/rqH76g4wqPZyrA\n" +
       "JEodYJTn89qNIw80ykve6qreC21W7IPig4ueb1S9Nx/6zLX7vnn5ufjiheKL" +
       "39Eef+Zqjw8v9niU\n8wCH72qJO0A2+D5yHz5zHV54f3j5/v0PZT+375+/oP" +
       "MXDAR+axd7H9AP2Rkg3oCY+MFV6Ec6vHox\nyxkW969Z6Yb85y+8u3j/p5+Q" +
       "SRlIXT/5m3/+V//cl/4tMLvYe645Qwyg9wavRX3O7T/2zZ9+5+Wv\n/7ufvD" +
       "hRr3fn97l3XvzymesPfU/iv3MWX8vqwgskp6zmmR+BJO4/0uDjjqgUUQICf/" +
       "MwM/2FL/z1\n//Dzv6G+eQ131/T9pY9l0Jtjrin8guaX8w7M8MXvNsOF+lcG" +
       "X/zmV9Rfd6+p7fWnoyST1gn+s/86\n+PAP3fM+Ieg+G2efaPDqM2t+WArjR5" +
       "85TNDW2EPWBiTKtQlzzPo4DPalaU6mK363nVYRHQpbia+F\nrbl0izE8L9bD" +
       "4GCxij+qib7aZQTG6nTpNdw09oZTTd0gxVRmkmw7WSYTT1DHy0wUd7K8EjRm" +
       "P1FL\nOUEOsjU3BxRGhB61MVf27DDYQ1ifgEKoHkE10iBNeZJ0e1xpbTHeRg" +
       "hmqjF8rLECGIFsdTrBjQk7\n91pkLpJaYRFUaXnKAkeNGTcvnUIxyL5sWtMC" +
       "V3Ve2oar0/qIikjYhJicw9RoCHtxMdXlQ6bNF062\nLgtLtTdYRw6NTlg73Z" +
       "pemTGMFjI5dNYLE+5PFqY2WVUzaps7aCKdhru8XDhiZuxPM3PH+nNBhXfi\n" +
       "lB1k+8hk13I5sO16GBnjKaKuh8bCGBN4OoCGI1JV+kW8wXNcN6YEOzJVFJ4w" +
       "pRmjh8NEiziTQxxt\nQ0nbKtF1phAF38m0WDyupuJ+uWawsUMn04JJVh2uTr" +
       "Rq19dkZ7zmipk8FtBEjf35JkrnXQwyg8Ap\nk/0a9oW5HvHLBBdiMYi92Fmt" +
       "bE+DUZkJcvwIWXnLU4c8Fuad0KcdaS+JCbo0HJ1NSqXAJrgljWdj\nbpKrRk" +
       "YHbp3lhz2tS+NESmN2xfjRBre3h+N8bB7W8Vho2PU29FhhfOj7K94ThE6g7R" +
       "rZyvxooKHj\nQyROhpOZdFpGU3g12uT+fiuFKVwSB5JKKIvCSFsyGTWb7vNo" +
       "WWhWm0jofib1JwdttWaiw0zhWB41\nR0VduSmeSoQlcrS4m1L8ctBM3UG3gR" +
       "zZO3rKlC625MBS6XILNSZTOzARIKNmtl8d+7PUWsQsj489\nQgyUppIpZM2r" +
       "p8zjfb5ZpDHGAySIKRKjRwuKd/Shye3a2eSJqzvdKNsv3HSlHAZjkojKvpZ2" +
       "qK/l\nsRbbYTJyl4YUc8iRluNZJ8yE4UwWQyOWHW80FBCt9tepzprjlVfwkj" +
       "mHKKYZbNuhuYUyczk59Ymx\naCFH2NyNYKT18x2aafsuVafLgbvgxlsf2cMg" +
       "eCwmp6nOJCIzOcFHYjUidoMmlITNfsHgjKBxYo2O\nSaM/lxkjyWernaANCS" +
       "tULHdCIoZheVt0Z/kFs8FOFcPoR/K42OyX20qZzjO7UtdMzQeDPY61GOnZ\n" +
       "pyGTmY7QN7w09+e2tt2uxJSdLFlu3CTuVBC3FUXVELNf5Ns5C7VbYUlzECtk" +
       "xaLbkLCSjNL0lMKr\nglFzHF6FEHT0+91cmzosm409dFjDMlVnvG4UVUaKRw" +
       "NHYLzptIl4cLZyIe3jCBVE7XiwNu3y1BD5\nUBtA0ECvEIMbKCM87/sgTqxl" +
       "zuCAleqiI0mvCQ9YAkY3TIzlYTNBVxI3qnbqqHLaTtw0CYUNIohq\nDlgBbS" +
       "0hV3UzOXRawth9dLJmjq00kWte2JSRyxnrPeyFIjVtZcS3tHABLyIUp2SvHY" +
       "8KYacTw/Vp\nPD5ap6OAWIOWPJwonTiuJApl+va6Uji9wgv3NNLHI+I4W3Fg" +
       "92Ply5hwrJ0/tZuBzBPwsKzq2YgZ\nytrRXgsDQ5gOdUGDTWl9FJwZVc8b2O" +
       "nbB0ilysm6VnGbg2YDdg0xA0wgCWRLK+QiOlnD0CsnY0ya\nS5FebqvGG1R8" +
       "yoeeoNSyBgBoeswE8gfUCOkTo5Prj/m1ocOiGA3nGpltWIpRPWO7P5KqW3Hj" +
       "ll54\nvrZgcWotLGUM0kl5yLMEhS7mRqC2xoA+4VOYn5f9QDA2u1IkTc8MRH" +
       "xIQlJraUgsSqPKXND8eobT\nUBnlBTuV0JmgbQplxMLE1hGjfOtKdOWtXd7X" +
       "SQ13t5v+Mu0SalQuRrrfNMO0zNqDVoNAak/tlms1\nm0p8SYZRbKCN6RoSyt" +
       "RDEAgz6aHJDBzfH9pQDLkwapVCtu0j+2k8EVVh1C7jzWA4msjmpPa1YCfJ\n" +
       "o1ZBNofaxwu/kg+q43IwsVAK3derbhwci5OKbobgIouj48pki/QHftyexrWr" +
       "LIakVYbdlu52Pi+M\niiDakEuQPUQtXrPmRufJ1WTi6E4xKydhhnnQYtAcuV" +
       "RwUBnFwL6RqrE+1Ew3ROs4gS5tV0CPwwoi\nHfSkCoQ94gMlazjBppVDng1C" +
       "P6T48YxUPAU/0LIZ4cl+HzaYiVVyBCIFq/S1qNJh5dSRe3W+G5vl\nbjHx5o" +
       "OmXuDWcYBmWaSbG7f02MMUMYg9AyOOx+VovmfIraEae4vlxHwseIg0n9H9o7" +
       "ARdrloiqXa\nWI2kpoPRaJkeF9qM0CFPtV3W3tawqadqGiAV3qGB6XPHAkoX" +
       "uNkeXNZcIdp8b0yRghn3ywxN0VzX\n60icTpbtKKnT6VJqGGfilSMNqUR1Uy" +
       "PFhrE30eGwmljtmDfhORabPsh0ucyMrSQguHhypNdKf1Mr\n+3Bq4+nq2HHa" +
       "qTQZwReDYUyLlhqwqqSuDBjnou1qs9zpp1pswWbgSJk7iWaDoAG7GIwi7KYh" +
       "YmU9\n7GN11Y3MwzQj5rblOg3Nx+TEo6y9o2wY2ktbdzGYFXhrij6bIKOlNH" +
       "T4FmMbxAt9ibJKxV7Ms0Xr\nxB7M9iMCbJvUcCYEUkQcwgg/oOwoDY7DqJHB" +
       "ihNGwOUePsHKfZRxx26hwI7aRRZ76FywlekmhJ3v\n1pCRVuEA7VNDtWKHbr" +
       "FNhZM1XjHI0WJwjDIaFMI9D4IyWzP2nlfuEZFWC34UHwl1Hh1gp8RqRBgz\n" +
       "1rqbDTcTAvdA1FB5V3LW+4Mv8uZQXi2Xk02gMOhkRiyttSZsFxmhyO4CNviR" +
       "KSdjggKJ0i1ZFvM4\nwSTRrkj5nZvgPppQWT/LNWZb6dNgIIfG4EhU9WkPHV" +
       "mLmgVltZsel5PSmDYwMvMQgW6LwMtUbWrF\nqwSaJMvdEDt5Q9Z31xUyNKp+" +
       "MmhhhJhIJN9uOXftr1brVhm7K8XHJHKHguk7xiK7oC4UJN+SYgVz\nizGPaj" +
       "NzoB0UY7sBBxhsw/O6O3L7gYh25hzNxjNW5/IA2XF7brPNc5MpjpAiRieXtd" +
       "SMUhahstcH\nDN0s0KPrCJpx2lkKHZoTU+3MKbtmZHXdl9JjNTXmyzCVOrry" +
       "A3W/0ZpielTX6E6hFhHryU6+j2h6\nA5tx15hkPgRb5mO22nHz9CSdjpU/QJ" +
       "qC5koy7UOmwHJgn3HSg6OtBfx+MxhA1Ql3KZhaWiBCZNWc\nMpulkpwYM9I8" +
       "eu2MfJLZOMQ05XhubGLyeECcXGWWNP0O5bztqFksF6tOr7BimzQpvF4vsEm2" +
       "EBON\nVQfDeEfBKhoqBVggLjvOPZyCY+vQQlAuYFANTawTDossqfaPmxaHlQ" +
       "VaybjrKQPgVgQvTxVvbgox\nTVq+W1txFjQYX1mc3yIGcF1obxphHY444oST" +
       "PEceYGvpENLM7BsLxjRKcywy09MpMFcMSIBV1XV4\nKa5zfI4ei6UYbbBKqd" +
       "KDHoNEQ5aSPoc5wp5pzTLJnDJVKwKj2nSH930IKoYYmq4SQcwV1OQVDAfb\n" +
       "FtvoULlqBZ0EhJZ1GCFJBIKISFsK7/IEMYpVsClsPEkh9PEEQpJBvnb7UIpG" +
       "+AqP0BF8kvcisi2a\nTSutuLzzBUWbZf5uoIY11jSpjs5gH8KTRsGLoA7peh" +
       "D4Ax6jchgkul2zL6L+YlsW59MdjgCx7HUZ\neIVZFR1B8KZFoQQyW5cwTc9s" +
       "gaFrvesaaqu1U92tT3SoAEPEjpmExhTCCHm27ithEJA2rZMcheWu\nkB25SZ" +
       "avocWJcgPK3zUVBKW8Nwx8jDUJyOzqHFEsZ3FCKHxiIFXWTIlyPN3h0zWC9o" +
       "Ns0uRgP0Ub\nWNDCTDisJcPy+aT2l9OhhYIt3WxNQpW4ihFZnSPYHBUgpeSL" +
       "CEGcuh4PG3VTZYZ24gUi6NsNresn\nQ8FHmsOB3EggUKeacCsMh7Ux0uCcLa" +
       "RyJDL6ciaOEE4yglxebUbHiOsUpV2apBk53HYdHjmm67Pp\nDt20xrKyUTOM" +
       "EHOCrw4uKVXbE0XtsdNwOUBIezrXMAbn4d2iBOGgPg7NXX0M9YGwIvUT1YJo" +
       "reDl\ntOsn+lgKlLnVKUyThvj+NOJRkSgFf4axo72ZlkNUo6PZfOtqPHoooA" +
       "FaKRRa1kcYTrbeok5aqXab\ngYl1HtUnmykeVM2KW3iu7LkA7ejYJvlVxsPF" +
       "BIVbeGeB+MzLBK7ycjqgmg010lhrZqbIYVUWCb08\nGZ2zkWiVX/SbQ2ZDss" +
       "YORD9aW3bOSiLYt2DM8UBMbJ2May+Cx0cbn2bHljXwrpwO7NaapUTDmYh1\n" +
       "EP2UbNXWSbzFUe13R9wh6CmdLigoZxGGi7ij3CRRwsGrXKLHrqoxKaFWqgyi" +
       "31Lmdy1kgnMQCAS7\nhB+23XhKy6lDJLYmlv3pDIREootVInF8dEJ29WTB6g" +
       "KXz1KRjTEdI0ubjD3yIKaoXe/a3SoYSvWw\nU+sOpWYFUhLUqWuptGFgsNdg" +
       "cobTG4mcV42k7Y0ginTYskRySnqzbWKRztHyR+M6tuaKOrNYI+Kd\nxUxLVk" +
       "a6R9VpFenUEJzsOG3sFP18FnEggyzCSlsV8o62V8tadBS6nNq1anuYWoDNPE" +
       "arROWWbolS\nBjsxTl6eLyEKo/RacalZvMaR0QA5EP16v0KkgXNoK0Xh482O" +
       "5m3a0XCwnxmFIgz2Ujk2wH355AxG\nblxYh8kWnGvCkY1jMhE2ICrh69g/7Y" +
       "6HhXzqqw6sMp4j5tst41djQVePWTTf6YcqXqHceo02fO0E\n4byeuZZgzikc" +
       "Z7AwZ/XBnBo74OwizZBxaEgMEdluH5wmZVzajbXWzhYD2ZnRu8AjdwmNB17N" +
       "oQ0F\nohU3woY8jnLYoGaMHX5qyW0HvJ4idJFnypJOwRmP40DYdrdFipe2A5" +
       "f1oaDLFiVbskqEYcMsdXyR\nENE2lGUJTkU+K6Han++CLU/HWsNoKb+yJsta" +
       "UjYBncktuUz7kW3biqi2HAq2uFrrudlC8ybFfLae\n0iYzUicmosgneJONnF" +
       "RHuD2z4icSUe+XvLdeBghnZGFRJukuyVuyXylCCEAcLHaWZxn0HpqHWoMt\n" +
       "DjBLROqwGXhBhG5ScGagV8ftXuFETPDEzXBjJ7NAk1g7sty90rHQxhOTfh1h" +
       "yMjk8h2MiDZIxnwY\nIulkthOUbq9t2m2idbadHfHE3oFNARLQDTFqzTRXYy" +
       "rKB4G8X1d5jpSDhVvL/ZVVKDTCR7M8CNzF\n1mg5JxvvJ5nd+vrEbnHZ6qSm" +
       "1Aa4M9aCjq30k80ok0hWRKbBA93bJLsqVZp2GbhenyPmgRpDMhQS\ntkjW+O" +
       "pIG13bxNjcPxZjlM+JlB1am2KakVp6XDp2QWd6YIqTya5OULROmgW72XXKti" +
       "3ovo4MZ6eB\nJdWWTKVMFY7p1tBlqnOzyBIGrO1tIjJb5ESeOVYaxsNkTekK" +
       "pNueztrL5MQN3RE4Zm/SyerA9Wf6\nVFWWvjrftxM0Do/x8siv60ygUFkbYf" +
       "NhI08XzT46yRhv+3ZEiziz3JjDLekkEb1XAw4aBQfaSqHO\nHfYryI0gEUsl" +
       "q9NcqO0Ux2+bYdBEhTIzmLUKsVAy8AjJXy1mx7V+ODRbQouHVmMGHZdTXBbq" +
       "LD/M\nd4NTyvQHnV2Afe6Qsvm6ZgIdGsF0OLI2A4jGqmBbIKM6QIsCwkSqca" +
       "PTEPWm86HikVxIKDw4Ekoh\n5sEYSZFyjvYHqJG3AcU1qqkoixhH1kOqGRQb" +
       "91RMtlO/AqcVwzPcyaFD6B1EZvMDBI4/eEAmFJo7\nowGkGKO85WKfQEDYTm" +
       "aKyZFFMx4ExagpsAI77FdhfQoVqipqdgdh4xxzeck6QsODHDap76w4PNgs\n" +
       "pXykkJPTlmxnxf40Ho9/6PyQdfY9PX9+8/L4/HF59/rY+Xzzo8sz2+4TmFS9" +
       "Fx23rArHq6reS49r\nyBe2N0pPb3xCBfT+5978+s98+Fd+7XbF6c7tcsPlyf" +
       "Kj8tz5IfU736mqe3lA/VXrv937MedXfvjM\n98yJB4JVWf6DcdAE8RORbjOZ" +
       "X4rYj8pAr5jv/nt29HN/7LZwL4Dp3/2uIx94rzWfXz6zjf7p3Uv9\n6Fpy+l" +
       "gV/elBHz1daOoXQVUXqf643PTxEqFSZF7g10XwZN6/+Nv8f/3ac9Tfudt79m" +
       "Yd7szh/VtV\nrZfDrEic+DzBo7J9v9oWWfuk52aJ61wHBNdb4PrUw5LO5fd8" +
       "87VLwer17slj/acQcvfcVm/i4c75\n2wB2fONJhWpcFM7xXMHs/sS33vnL/8" +
       "T5q8/07gi9Z8voFFwqzb2nipkPOZy/oxs3z//9qvfy1imV\nAoSErC4vNNP8" +
       "Oo6rei+4WRYHTnr57z");
    java.lang.String jlc$ClassType$fabil$1 =
      ("yl3Nvg+vRD5T79f6vcbdE+8eadp+H+/k24LzKt9rZMHCRB\nWjGdF+Tn+v5F" +
       "jgPwufymfmDsZx6OvdSixCi8Vjw/Qcs3wfXKQy1f+b5oeXN1vgJcMQ26Skj9" +
       "oLu9\nNs+A6PEJEn8OXK8+lPjV77vEP1H1PvXI2Bepz51f/QQxz+8+vP5QzN" +
       "f/X4n5ECHnvz/6PWHo3ScV\nSSGOg40Tn19CCp4G0NeArcugum3/Z5ss8r+D" +
       "Zm881OyN759md6+MHmn29hPNLuGedsonfvHYhT6m\n/rjY1B93ob8GLOD4/r" +
       "n5jZsan+vWvet7LXeuCt9571q6vn+jlC+CSf7Plfz/79D8m1Xvi7so/GAT\n" +
       "VFfvf3COHA9uvsvyQHpkmbduBogSbPCLqDrelxw3iEHiv3dz0Pnlgc997F23" +
       "6xtZ3vvf+pEv/3L+\n2j+75JjHb029IPVeDOs4vvmuxI3288CDwugi9AvXtH" +
       "I1zy+AYH0j6AEInn8uIv/9K8UvVr3nn2xD\nfil/pM7rN9W5BrvufwP5OeHa" +
       "2CcAAA==");
}
