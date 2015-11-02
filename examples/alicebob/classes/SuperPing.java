import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;
import fabric.runtime.Runtime;
import fabricated.util.List;
import fabricated.util.Comparator;
import fabricated.util.IdComparator;
import fabricated.util.LinkedList;

interface SuperPing extends fabric.lang.Object {
    
    public void ping(final Message incoming,
                     final fabricated.util.List storeList);
    
    public SuperPing SuperPing$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1446064403000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("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" +
       "U5rK+3mhMRR0AsxoAOkz9aHDpozaUvRz");
    public static final java.lang.String jlc$ClassType$fabric$1 =
      ("3CNiHuGYSHBssQo6yUNBmK9ZRpiqCE4qUkGn2BRU6xZDnc3Q3Udwi02opxcc" +
       "Q0F7txG8vlgBqrnEUIDpXoI7ihRg3CbA0+UYyuabunv4ppTsm+DnVN51oJjn" +
       "HEMFak0lSDEULBlfzjzV6wS3iRxD6R1DQZVbCYorBRIPQaQl6ORPh7H/KxSl" +
       "T5DD3pawVYqh+jtV+JdVR3U5xVCpTLwpnhQCKNjmtlbl9zuETqN4hWqnUClr" +
       "hjaaM99wuFkqUC/rPXQNj9sHMprSWSANa3rGOFmnGCcrxjgRzsoIpx3HZq5N" +
       "RpvjMdrZavvLTz10a59FY/nGQrARutPXFabzbVjYVXlPK3wieiipK0Dqkl5y" +
       "m9TAUZKqMmqazZKZhpj0mdy1FWo5bkRRzjd7KQciSHnVaftmoiL94h5XH99z" +
       "xf4Jv39hi7D5nGX7U9r2z+AEd8gZN6f/xCnLf/MkbljUMamuVjPzWxKJasWf" +
       "iDtsgG3ZVceyEWCrg6RNXLWplHbUFYlf33/UR6fw17Dt+xEaN6eCx04+4ZHZ" +
       "w/2W/fQBS0/uaS9VnCKxQ9k/i8L6VBDsLiqBIjHcte4Vwz6qnTprP6o/nGAf" +
       "ubMGLjIqYIanL914GMFBJdQ+oLuMA3jBIrBVQTZMDgDLLsmFdHViQ3r78PQ8" +
       "XEh1IT4E6OQRxoWuwSvE6IFrobie2OpA9Uc4aXg0dbPO1M0+dNCw/gZAmDdk" +
       "jiBrSXdoQKVWy6WyWhAv5D0VnP0HIA+qvINn/4Ll3+xb3sDp/W4GBvx+S/CL" +
       "khkYILufc+MaDHIOH5A5bMWLPojIA5jbxc0EAg8gu48YDfP5zLofYdACPPC+" +
       "ZuAsb3uDVzkCd6KtyFp6LD8tcX5LbEqGoI2uIEr68bRp0dcJKYlbfowujR1s" +
       "G5ZnS12VC2Nru7k7A5nhxXe5+e5fMA8xh6BlcuDmJcUKlm1zNUXNSaQNalZj" +
       "PMEoVZhcKW4hwR4ktjfCzSEAtbMJTm/HEectmwqcXPNwomQJwSMdXPN75JrL" +
       "RP5Mc35NcNBvtRsXDuMmXHn9R1M2YNsHxZ+93RTsJh3Ya8jN1dxZqMwiFA7r" +
       "czF3sUKT1XBOyz03WwI7D2NDrgYO9KUJxtvRwL/MycAHEyVrCJ7iYODfkIGH" +
       "ssY6GFr2J4Jlf9lu5AuW7d9nWvaB9tcrQxNEKj73NunvoPi3Lin/Pryyxn16" +
       "GIRJZQCS3oENTuYPkT5Myp9THOb1klnB7EAZSzc+S/ARq1lNLUAvojuwKSfY" +
       "AcXynpNPF+KOIO7IVGh0OJCYeYjgne3NVHdnpoQwJdglLUzghe3Ipc3vq9Xp" +
       "2ayagfp1sRfGnfPW6xc2/hI/AxBI8PfWhrKfGeOn8X2OUU6fhxFaEz7TgXPh" +
       "HnbDuYp14ocJXuVhOFihO9241alCsRF4cIBNxAOdfZKZkzzYiY2bRaoABHkG" +
       "rchEbx+F+g/guIvgLVYJ5J2nDB6O7TvnKYfpnRUw/ZagtPtLvnnK4BgBIeYp" +
       "mXQyyiiHD/M4GlJd7PnHom/+86mbd/Bv9EADfQ2KjwCKTyBKXyNocS+SWWGF" +
       "iXTjk04VijarI21mdZSjWQlUTXZi45nSOhQgZJKNtMnunjJ4hCw83Mqpivzd" +
       "uwT/XFLhTbdROKMt4UEWEFfCID7YSHBd6YV3go202R7DjJQLh42vtrAAq4lg" +
       "no9iQpXjCHo8itlGLvw6VvsagpaHo6WYtb9ThUutEj2psLSCFo3nmwQPzjeS" +
       "4MEFzgzDoVMSPHiyR5RzKhTzNCUINGEVr+w3o8Ke/RZPCtlvq30aqWqQ4tUE" +
       "pe1nShmaBhtd87TXM8xPELREbTySDq5cpeSUp4X6DxO8w8PbGpMKuPG3BO9t" +
       "R9bTCrKRY55WZEMa5pYVmNRp276BQO4cXBO0QXijKQg2GUwRP20maMGobiS4" +
       "xkG160i1coI2uMicJQUbS2+Kkj7kEPznq4oIwfsSvxcT/HlJh6KNnBvXII5z" +
       "eIHMoRiP465UmKANXoDsXmY0jLPE4JL29KSA4HIoTvI2NBgKgj9DI5HV84v8" +
       "1MMZLbENGRJ2yswGN5uZ2WDSzMwGV7drJ8YuxWP9ZU4p2WC63TybmJLlDts1" +
       "R8WmTX3fIfiS7KadHKJYQdqPIbdchkuWLvdRnySaWy4WyHyb4OvtOKrcbZO9" +
       "kxceTpR8RPBZBy98H3lh51xs8FeCL7673bhwzsUGd7S/lgENTLaCd3h7JNhM" +
       "LfgbQ2CuBn4DA0EO+1pe53IzcLGC9HDH0oKSdXlbdhvZVyAwQASW8nEG2Rie" +
       "ycmkBxNFnYgiy77FZNIvkElL2dfgY4ItP9Nu5DtnX4NPtKNCoX3wNsGnvI0Y" +
       "PG7wZV1EPO0a9HjROPgmFPOgOM3J4CHtBPO/esVhCinZk5F2hRujBC0vGrdH" +
       "hvId1Mp9baVd/9+qImK+gcTM/xA8vr2Z+qszU2IMstcp7brKkqgyNMhi9H7V" +
       "BA/30KCR/4QbK50qFB3gfmbj9XNnr+CQ/xTZmCBShT6hJN/mMPKfgGMqwaOs" +
       "Esg//+n1bQ4j/wmYTiQ4w4ox7/yn7dscXDrBIyQLGQfIZxPSRoILPCwEKxxD" +
       "Ny5xqlCshYRCsoWEwo4WIucIZTaWl7aTAiGdbaSVuXsfeyoTXpRAWsGHpAmW" +
       "brkT6Olho7BnW8IzUpkw6PYn2KX0wutrI62fh+t22F/nFkXp7+OwnyXyhtI7" +
       "lQlV/kTwRaGqNDBG0I1GnAZC6Ka3MgLGEbQ9KeiUyhQrjLBK9JTCn5CqSqXX" +
       "5pnODA020pmhcmem4VBOZ0oFtBEa4x5MhCqgGMRMTScTq3llOBlh9gyneLLt" +
       "DCcI9wiC0kcQShn+hSa6Zji3McwnE7QESDxaDU1epeSU4YT6Cwlavj3lFrjD" +
       "jbUET2xH1qcryEaOGU6RjTlWqk4t4rG1ts0eiOSbOrlmOUOjoIBXokLTiKc2" +
       "s5xgWMcQdHjQOFRN6pWznKHDzNlIaGLpzVHSiRTxhjDyLibLCfweRrB0zzkD" +
       "aSdxblxDNc7hMplDIfwNLcGfZXgfsnu60TDOxkIj2tvJAhLYaiU00tvYYJOo" +
       "UA0aiqyihvxUxJktsR0ZUnbKdIZUM9MZmmpmOkMntHtnxq41B8tRTtnO0PR2" +
       "83JitpM7b9dk0G2sb6wmaHnAzs05ihUS1k5VX+wzibkHBiTV3DKeQGoLwWQ7" +
       "jjJn2+Tv5JGHEyUbCFo+A0MeeQN5ZOeMZ2iN4JfPbjcunDOeobU/jqYBFWzu" +
       "Elrn7Z3Og2KjITRHQ4dJ8a+YqLcStKwjS4Z+kFMF6WN2J+f2mQ7GW2uxFh7G" +
       "VmTTxt7Ym4i7nuDmdjTta3Iy7YFEyS0EL3Qw7a1k2iFkzLTpywWbvqbdyBds" +
       "WvjARujKdlYq4LgNis3exnwjFDfrYiIKHYx5OxPuIwR/nYsxixWkRwnyfpJ8" +
       "YfxM+cnbvM26m9Seq4EDwb8jeH87Gvg9ORn4CKLkBYKW15HJwB8kA+8qsWia" +
       "+u2Cqd/Tboy4mPqdP5rKAdtuKO72NvqHoHhYF5270e9gAv+Q4Bu5GL1YYU8h" +
       "HrxeLSZ5oXtwbMXVwIG4Dwi+044G/nzOHhwo+Zig5ZslZOB/1D04Mmaa9ZOC" +
       "WT/fbuS7mPXv21mpgAOMLvSMtzG/AsVrupicjRnC6NsV5ZAQh/3/6WHMXZ0q" +
       "/LsQY3Z6YyJ/Y/ZeiAUig0Tk9+1ozHtzMubBRFFnoujvDsa8Tzdm60Js6G3B" +
       "mPe2G/nOC7Ghd9tZqYADhBF639uYP4LiE11MfDE2NBTrOOdPYe+dEKRaQpVO" +
       "hg9rYJDB/lZxSIJLNmUsxsKN3xD8xGpTJV+3DH2LmtnQxmJsCG27mMVYYGYf" +
       "wbfbmamwz5kpMU3y3zwWY+9gXaqaYE6LsXBjpVOFYvNw4U42Xjs7ewaHxViR" +
       "jQkiVQDC3bBCqRZjAcdUgsUuxob5J1/aWIwFTCcSLHIxNtxfQIiLsVw67oux" +
       "gLSRYE6LsXDjEqcKRVvIoTYLGeJoIU6LsSIbpV+MDY+wkXa4u/exL8biFqTT" +
       "uA/p34lgoKTCq7BRWNmW8IzFWBh4DyHYtfTCs70vFD7Kw3VLi7Gwvvk6o7WF" +
       "oOXzR1B6L8ZClQaCpwpVpYHReZ9K/CTQMqr9AEHbO31CBDjNqYK081Zu+1TG" +
       "k7FUM+vT85hUo01i2JBRY6lMA26YU+Q+leHJxkJteIqXQOCG6WZCPlyFjmUm" +
       "Ot/j82oPDnFpNzzPPTQJg1sJT9KUzgKvWNNrdZdht6/uiifZNF+SKh9uOEfI" +
       "zNJSjCSwTylu3wjK307whmJHEnE7OXkkgS2gYcd/xHQ/wTusGPMeSU63jSRL" +
       "pcD9SEAbJ3S/J2jJ1/LAPYxLT6+4yJ+sy0TQT1O6rYyujlYkosmmioVahlXA" +
       "C7Z9C20WCUUCrdItWA7PgqIRsemkhVfgldkeFplCi4SiBq/jC5XczSu7NSW4" +
       "OhVvaHsXRXxWYwIJapPu2ESBobi0UlghPrIwnHCcQ9Dy3kwhVnimhxXiJr3j" +
       "CdOlBKU3VvK2wrNtVqhJQj0D0P6c0L1P8BmbUM/lQk3nLMBm1CO19xrB54oV" +
       "4EYPAZ4JGDcQpvcI/m+RArzYJsBzWf+SOuIIp/4w1aM/XGH2ByjSzlsnhjVT" +
       "8uawDsMybk95gNnMlwQ/EtlcZR3Wx1iH9Q5UZT/BvUJViWRhdiTPb+Fhi88U" +
       "ZZQOO4kESMP6YKcKg62Kye1VHF3uwniehkP7QK7fWe0wbm82x9mrvDiGG4TN" +
       "nMJbUSk3YH+4Ma/24NB8Liv8Kw/TgDx7+BfMJ6a5BbYxam92GrU3i6N2B10U" +
       "UnfBrMMo0oYOD22juxhideku91B3QbDTwAVZAjQDFiYHGghaHjdX+FDH+zRP" +
       "JeL+uBXIMcb6lVD8FoqHUeBHaEpHgx44sUtXg/KkNNSi1I4kHnVo+dIhDbWQ" +
       "m4eh1io0sgSTwdeNliPQci8gkf1rBJs0ZWnlilSzWhnPrlAbGytnZlLp+lRr" +
       "5cxUDA05W1mVyiTVRKIymk5UNsW1Sr7ncKXaGm1OJ9RsZTQRj6n1qfrKbCZW" +
       "SW6mgt2Ubr+mkb9degwRoStwOAhPuqZ/JGVj2mEAieKnBLGjPIYyflxX9tP5" +
       "KduX0JUcfsJUsmnQoAXMOIFyddirSIP+g+n/ww8IaaQwvtyBDzWFX0DJvWze" +
       "LnoLKF5Bj+EaU2EI/Qhal26DYcfFfch2fM6YaiBo2V9McrrlThWkT77lkXiV" +
       "9vx38brGrdViNj28zbbHuqk12EocPyIP9MUILrPSaYsTTDwuanvL2Q+hzxtH" +
       "/mc1wUYHP/RufqZpbquciyOaSFwmCU52cER7yRE57E0f/lOryaKZqQ3jl9dy" +
       "zdTK/WYQ0bKYoPSIav4a+EjoOO+KHWef2XE+Rro/M2/HjvO/eBa2DQnD+2Th" +
       "N7w7zocoDF1m4b/hlV96jLBfQvELKGDLSUvwFeBahmM5HQym8wUTzG6C2219" +
       "b4e1Qi+68V6nCoVI9J+iRCWMfZ1IfLBI3ye8CsMHZlsaFFNgkwjdGwQf95DM" +
       "UL23w43PO1XIn86IT6ZTQHiEE4UvFamKSEhWhU0ysBmL0sDxjfYR3j0eksEK" +
       "J9CNHzpVKITQMpvNmDO/E51ItHxmEUCkJMsD8D0C+Col4viK4MdtcNfWbDDi" +
       "tTwAX1tQYsSdQhilz2nnOxuM2JYHIrQ8IGvTiEVghvU9wa/awN6WnQ+22bn0" +
       "MQToWH9nbF5P0JYbiQwtTJkZ5RB5X/B5qqrhrlx1sUPe+WC/Num83/gVf7XS" +
       "ifGQTiUhEoWWAuxMEgOK+JkqJrKGSju1YwRdThRfRnC1jfJRjIix9GUOc1+w" +
       "WXjCoGVRip2IJ/i+YF90GbujS93gnXxj86HWDcWEKuZOYt+t333H3GcSE4yt" +
       "2oszevyQRE/i6FyCUg6pwtEEPa1e/wKOFNs04jCn8JjmUYKWTQd5bBM5Or/Y" +
       "prNA0SqH4EZaA5Q1eX6RXc72PXlmCQ5jymhCdx1B6THW0U6+xQPrVGcZQ/IR" +
       "P+YCsj2fYKuDjKvyk3GHjPBNckHAyO9QYZ6LmSbbLGEVBoW2CAodxRtY3iR/" +
       "+sMWMV3rHjFF4J3zXCImuPg1tgbP+YRhA4Xwf41LEd4YxKCREBQdzUud8VIZ" +
       "elQoBuHxYNNlSfkv6FU+3h9d8l+rHLIxArEoND0FvYH/1pSmnCZE/MNAw9IZ" +
       "ppV4OpqQ5kV1GfxikNPq0t6Ltu09/3KHtaTIwoKphtonodEty6s9OJReGIxE" +
       "PYwgBkUt8wcCj1jTKz/F6LDnp8STmtJNFidemcKZQ77OwDMz3J3xHFMS/CG9" +
       "ZYKYTk67nVpqVsviifnCPbVpt1Mv8lN8ohZBOb/ixsZ6EwXnQlr56Gi9XVQp" +
       "FGejWt3mPxFYqY+sxcZ1UiLrTJdgzV3muhG9S47V3artqVago86WeQQy7i0k" +
       "7jnPFveACsK/01VwAaoATj0iMo9VVxle0kzKRODzNhF43i2ywVO2mJTBNhCH" +
       "u7vN9TURt1SKu3CdMyogXTmjApTsKWiScIVtkoDy/UCX72ZDvh+KAsCq8PNv" +
       "0sgT2QQFfA0g4vzeoCHfD/U2EAcfiSKneDghdB/wRakIrrS0MRKZgvmmIMFs" +
       "tQkGG/t3IUZ8k2zE+pRxFY6MRusRfyGtb7N1EWwrXBDb22W2RVI7C6QeVFDz" +
       "d9mkCnmcSHfu7HcWNkMxaOpT0EzyAftMEk4fUkjE+pAtYt1pmSTCmfJClPyI" +
       "ff4Hp4dxwe0uWHARWCaMjObNYF47MqZg+U8obFIjfISdJijY2pRCFPCCTQFP" +
       "mS1OK2A28EeBOHxUIsKG7u7ygA9hdS4DMVw8F9vagOVyKb7PZcBBL4u1L9Pb" +
       "sMb3Nq+62MOrvpWXV0XPiC4Nilvk+H4buhAo7pDj+7uwM0DxOzzG4XU3U0hH" +
       "Q9wUGPbTlB402cfHWBbUr1RjfKCzPcbSyqktF2NPZz7/ms0ofcxExtx447yW" +
       "mirQd13s7+PHzdz93PBnKVugf2CtQm3VKtBQqJJZY+eNc+ev++Lo2zDtEWJm" +
       "c+aZgLSsWonwcR9pCLHWDnVtTW8rPHvkv7vc0/EwPfPQA4rewkq+hTt+EEY/" +
       "gF4UDRPNHR0YdqJWxuwQPWGCyHiypCaTWh1vUDOUvNk67eX/PNu06g9+xbdc" +
       "6QbfjcumozG1aoUaO0NtaHX4VJ3UwBnKWRf/fmOPc5gYljNDzi7KtGQ1taFa" +
       "6Yg9aRGrCUTdw9M+ae5lPmPEjbZlcywtW3I5B5+yfe77N03ty7UzwprLkaqZ" +
       "+Rzfyi0LqiM/nOSdz8FFkV5oRL6QMc2ERUtcxWT/gXcIwjfHxWlm5B/iAzEn" +
       "2H2ir8xoEB7ZgHwBNvQ2wdfkBr8xPdW3hfi+70TfJ1EA79TgWzaA+WWCT8sU" +
       "/Mek4PuCxlJxQyv87JgyKP3/AUZGzu5h0wAA");
    
    public void ping_remote(
      final fabric.lang.security.Principal worker$principal,
      final Message incoming, final fabricated.util.List storeList);
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$SuperPing_incomingL();
    
    public fabric.lang.security.Label get$jif$SuperPing_storeListL();
    
    public fabric.lang.security.Label get$jif$SuperPing_readPast();
    
    public fabric.lang.security.Label get$jif$SuperPing_writePast();
    
    public fabric.lang.security.Label get$jif$SuperPing_readFuture();
    
    public fabric.lang.security.Label get$jif$SuperPing_writeFuture();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements SuperPing
    {
        
        public fabric.lang.security.Label get$jif$SuperPing_incomingL() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_incomingL();
        }
        
        public fabric.lang.security.Label get$jif$SuperPing_storeListL() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_storeListL();
        }
        
        public fabric.lang.security.Label get$jif$SuperPing_readPast() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_readPast();
        }
        
        public fabric.lang.security.Label get$jif$SuperPing_writePast() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_writePast();
        }
        
        public fabric.lang.security.Label get$jif$SuperPing_readFuture() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_readFuture();
        }
        
        public fabric.lang.security.Label get$jif$SuperPing_writeFuture() {
            return ((SuperPing._Impl) fetch()).get$jif$SuperPing_writeFuture();
        }
        
        public void ping(Message arg1, fabricated.util.List arg2) {
            ((SuperPing) fetch()).ping(arg1, arg2);
        }
        
        public SuperPing SuperPing$() {
            return ((SuperPing) fetch()).SuperPing$();
        }
        
        public void ping_remote(fabric.lang.security.Principal arg1,
                                Message arg2, fabricated.util.List arg3) {
            ((SuperPing) fetch()).ping_remote(arg1, arg2, arg3);
        }
        
        public static final java.lang.Class[] $paramTypes4 = { Message.class,
        fabricated.util.List.class };
        
        public void ping$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, Message arg2,
          fabricated.util.List arg3) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                ping(arg2, arg3);
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "ping", $paramTypes4,
                                                  new java.lang.Object[] { arg2,
                                                  arg3 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void jif$invokeDefConstructor() {
            ((SuperPing) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.security.Label arg1,
                                             fabric.lang.security.Label arg2,
                                             fabric.lang.security.Label arg3,
                                             fabric.lang.security.Label arg4,
                                             fabric.lang.security.Label arg5,
                                             fabric.lang.security.Label arg6,
                                             fabric.lang.Object arg7) {
            return SuperPing._Impl.jif$Instanceof(arg1, arg2, arg3, arg4, arg5,
                                                  arg6, arg7);
        }
        
        public static SuperPing jif$cast$SuperPing(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3, fabric.lang.security.Label arg4,
          fabric.lang.security.Label arg5, fabric.lang.security.Label arg6,
          fabric.lang.Object arg7) {
            return SuperPing._Impl.jif$cast$SuperPing(arg1, arg2, arg3, arg4,
                                                      arg5, arg6, arg7);
        }
        
        public _Proxy(SuperPing._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements SuperPing
    {
        
        private fabric.lang.security.Label readFutureRecursion(
          final fabricated.util.List storeList, final int i) {
            try {
                if (storeList.size() > i) {
                    try {
                        final java.lang.String entryName =
                          (java.lang.String)
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(storeList.get(i));
                        final fabric.worker.remote.RemoteWorker entryWorker =
                          fabric.worker.Worker.getWorker().getWorker(entryName);
                        final fabric.lang.security.Principal entryPrincipal =
                          entryWorker.getPrincipal();
                        final fabric.lang.security.Label entryLabel =
                          fabric.lang.security.LabelUtil._Impl.
                          toLabel(
                            this.
                              fetch().
                              $getStore(),
                            fabric.lang.security.LabelUtil._Impl.
                              readerPolicy(
                                this.fetch().$getStore(),
                                fabric.lang.security.PrincipalUtil._Impl.
                                  topPrincipal(),
                                entryPrincipal),
                            fabric.lang.security.LabelUtil._Impl.
                              topInteg());
                        final fabric.lang.security.Label future =
                          ((SuperPing._Impl) this.fetch()).readFutureRecursion(
                                                             storeList, i + 1);
                        return future.meet(this.fetch().$getStore(), entryLabel,
                                           true);
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                return fabric.lang.security.LabelUtil._Impl.
                  toLabel(
                    this.fetch().$getStore(),
                    fabric.lang.security.LabelUtil._Impl.
                      readerPolicy(
                        this.fetch().$getStore(),
                        fabric.lang.security.PrincipalUtil._Impl.topPrincipal(
                                                                   ),
                        fabric.lang.security.PrincipalUtil._Impl.topPrincipal(
                                                                   )),
                    fabric.lang.security.LabelUtil._Impl.topInteg());
            }
            catch (java.lang.NullPointerException exc$2) {
                throw new fabric.common.exceptions.ApplicationError(exc$2);
            }
        }
        
        private fabric.lang.security.Label writeFutureRecursion(
          final fabricated.util.List storeList, final int i) {
            try {
                if (storeList.size() > i) {
                    try {
                        final fabric.lang.security.Principal entryPrincipal =
                          fabric.worker.Worker.
                          getWorker().
                          getWorker(
                            (java.lang.String)
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(storeList.get(i))).
                          getPrincipal();
                        final fabric.lang.security.Label entryLabel =
                          fabric.lang.security.LabelUtil._Impl.
                          toLabel(
                            this.
                              fetch().
                              $getStore(),
                            fabric.lang.security.LabelUtil._Impl.
                              bottomConf(),
                            fabric.lang.security.LabelUtil._Impl.
                              writerPolicy(
                                this.fetch().$getStore(),
                                fabric.lang.security.PrincipalUtil._Impl.
                                  topPrincipal(),
                                entryPrincipal));
                        final fabric.lang.security.Label future =
                          ((SuperPing._Impl) this.fetch()).writeFutureRecursion(
                                                             storeList, i + 1);
                        return future.join(this.fetch().$getStore(), entryLabel,
                                           true);
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                return fabric.lang.security.LabelUtil._Impl.
                  toLabel(
                    fabric.worker.Worker.getWorker().getLocalStore(),
                    fabric.lang.security.LabelUtil._Impl.bottomConf(),
                    fabric.lang.security.LabelUtil._Impl.
                      writerPolicy(
                        fabric.worker.Worker.getWorker().getLocalStore(),
                        fabric.lang.security.PrincipalUtil._Impl.topPrincipal(
                                                                   ),
                        fabric.lang.security.PrincipalUtil._Impl.topPrincipal(
                                                                   )));
            }
            catch (java.lang.NullPointerException exc$3) {
                throw new fabric.common.exceptions.ApplicationError(exc$3);
            }
        }
        
        private fabric.lang.security.Label getNewWritePast(
          final fabricated.util.List storeList) {
            try {
                if (storeList.size() > 0) {
                    try {
                        final fabric.lang.security.Principal entryPrincipal =
                          fabric.worker.Worker.
                          getWorker().
                          getWorker(
                            (java.lang.String)
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(storeList.get(0))).
                          getPrincipal();
                        final fabric.lang.security.Label entryPrincipalWrites =
                          fabric.lang.security.LabelUtil._Impl.
                          toLabel(
                            this.
                              fetch().
                              $getStore(),
                            fabric.lang.security.LabelUtil._Impl.
                              bottomConf(),
                            fabric.lang.security.LabelUtil._Impl.
                              writerPolicy(
                                this.fetch().$getStore(),
                                fabric.lang.security.PrincipalUtil._Impl.
                                  topPrincipal(),
                                entryPrincipal));
                        return this.get$jif$SuperPing_writePast().
                          join(this.fetch().$getStore(), entryPrincipalWrites,
                               true);
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                return this.get$jif$SuperPing_writePast();
            }
            catch (java.lang.NullPointerException exc$4) {
                throw new fabric.common.exceptions.ApplicationError(exc$4);
            }
        }
        
        private fabric.lang.security.Label getNewReadPast(
          final fabricated.util.List storeList) {
            try {
                if (storeList.size() > 0) {
                    try {
                        final fabric.lang.security.Principal entryPrincipal =
                          fabric.worker.Worker.
                          getWorker().
                          getWorker(
                            (java.lang.String)
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(storeList.get(0))).
                          getPrincipal();
                        final fabric.lang.security.Label entryPrincipalReads =
                          fabric.lang.security.LabelUtil._Impl.
                          toLabel(
                            this.
                              fetch().
                              $getStore(),
                            fabric.lang.security.LabelUtil._Impl.
                              readerPolicy(
                                this.fetch().$getStore(),
                                fabric.lang.security.PrincipalUtil._Impl.
                                  topPrincipal(),
                                entryPrincipal),
                            fabric.lang.security.LabelUtil._Impl.
                              topInteg());
                        return this.get$jif$SuperPing_readPast().
                          meet(this.fetch().$getStore(), entryPrincipalReads,
                               true);
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                return this.get$jif$SuperPing_writePast();
            }
            catch (java.lang.NullPointerException exc$5) {
                throw new fabric.common.exceptions.ApplicationError(exc$5);
            }
        }
        
        private fabric.worker.Store getListHeadStore(
          final fabricated.util.List storeList) {
            try {
                if (storeList.size() > 0) {
                    try {
                        return fabric.worker.Worker.
                          getWorker().
                          getStore(
                            (java.lang.String)
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(storeList.get(0)));
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                if (1 == 2 / 2) {
                    throw new java.lang.Error(
                      "something went wrong in getListHeadStore.");
                }
                return null;
            }
            catch (java.lang.NullPointerException exc$6) {
                throw new fabric.common.exceptions.ApplicationError(exc$6);
            }
        }
        
        private fabric.worker.remote.RemoteWorker getListHeadWorker(
          final fabricated.util.List storeList) {
            try {
                if (storeList.size() > 0) {
                    try {
                        return fabric.worker.Worker.
                          getWorker().
                          getWorker(
                            (java.lang.String)
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.
                                $unwrap(storeList.get(0)));
                    }
                    catch (final java.lang.IndexOutOfBoundsException e) {  }
                    catch (final java.lang.ClassCastException e) {  }
                }
                if (1 == 2 / 2) {
                    throw new java.lang.Error(
                      "something went wrong in getListHeadWorker.");
                }
                return null;
            }
            catch (java.lang.NullPointerException exc$7) {
                throw new fabric.common.exceptions.ApplicationError(exc$7);
            }
        }
        
        private fabricated.util.List listRelabel(
          final fabricated.util.List origin,
          final fabric.lang.security.Label originL,
          final fabric.lang.security.Label destinationL,
          final fabric.worker.Store store) {
            try {
                return ((SuperPing._Impl) this.fetch()).listCopy(origin,
                                                                 originL,
                                                                 destinationL,
                                                                 origin.size() -
                                                                   1,
                                                                 origin.size(),
                                                                 0, store);
            }
            catch (java.lang.NullPointerException exc$8) {
                throw new fabric.common.exceptions.ApplicationError(exc$8);
            }
        }
        
        private fabricated.util.List tail(
          final fabricated.util.List origin,
          final fabric.lang.security.Label originL,
          final fabric.lang.security.Label destinationL,
          final fabric.worker.Store store) {
            try {
                return ((SuperPing._Impl) this.fetch()).listCopy(origin,
                                                                 originL,
                                                                 destinationL,
                                                                 origin.size() -
                                                                   1,
                                                                 origin.size() -
                                                                   1, 1, store);
            }
            catch (java.lang.NullPointerException exc$9) {
                throw new fabric.common.exceptions.ApplicationError(exc$9);
            }
        }
        
        private fabricated.util.List listCopy(
          final fabricated.util.List origin,
          final fabric.lang.security.Label originL,
          final fabric.lang.security.Label destinationL, final int index,
          final int destinationSize, final int begin,
          final fabric.worker.Store store) {
            try {
                if (index >= begin) { final fabricated.util.List destination = ((SuperPing.
                                                                                  _Impl)
                                                                                  this.
                                                                                  fetch(
                                                                                    )).
                                        listCopy(origin, originL, destinationL, index -
                                                   1, destinationSize, begin, store);
                                      try { final fabric.lang.Object entry = origin.
                                              get(index);
                                            destination.add(entry);
                                            return destination; }
                                      catch (final java.lang.UnsupportedOperationException e) {
                                          if (0 == 0) throw new java.lang.Error(
                                                        "WAT: an Unsupported Operation exception happened in listCopy");
                                      }
                                      catch (final java.lang.ClassCastException e) {
                                          if (0 == 0) throw new java.lang.Error(
                                                        "WAT: a ClassCast exception happened in listCopy");
                                      }
                                      catch (final java.lang.NullPointerException e) {
                                          if (0 == 0) throw new java.lang.Error(
                                                        "WAT: a Null Pointer exception happened in listCopy");
                                      }
                                      catch (final java.lang.IllegalArgumentException e) {
                                          if (0 == 0) throw new java.lang.Error(
                                                        "WAT: a Illegal Argument Exception on happened in listCopy");
                                      }
                                      catch (final java.lang.IndexOutOfBoundsException e) {
                                          if (0 == 0) throw new java.lang.Error(
                                                        "WAT: an IndexOutOfBounds exception happened in listCopy");
                                      }
                                      if (0 == 0) throw new java.lang.Error("WAT: an unexpected exception happened in listCopy");
                }
                final fabric.lang.security.Principal storeP = store.getPrincipal(
                                                                      );
                final fabric.lang.security.Label storeReads = fabric.lang.security.LabelUtil._Impl.
                  toLabel(this.fetch().$getStore(), fabric.lang.security.LabelUtil._Impl.
                            readerPolicy(this.fetch().$getStore(), fabric.lang.security.PrincipalUtil._Impl.
                                           topPrincipal(), storeP), fabric.lang.security.LabelUtil._Impl.
                            topInteg());
                final fabric.lang.security.Label storeWrites = fabric.lang.security.LabelUtil._Impl.
                  toLabel(this.fetch().$getStore(), fabric.lang.security.LabelUtil._Impl.
                            bottomConf(), fabric.lang.security.LabelUtil._Impl.writerPolicy(
                                                                                 this.
                                                                                   fetch(
                                                                                     ).
                                                                                   $getStore(
                                                                                     ),
                                                                                 fabric.lang.security.PrincipalUtil._Impl.
                                                                                   topPrincipal(
                                                                                     ),
                                                                                 storeP));
                if (fabric.lang.security.PrincipalUtil._Impl.equivalentTo(storeP,
                                                                          store.
                                                                            getPrincipal(
                                                                              )) &&
                      fabric.lang.security.LabelUtil._Impl.relabelsTo(destinationL,
                                                                      storeReads) &&
                      fabric.lang.security.LabelUtil._Impl.relabelsTo(storeWrites,
                                                                      destinationL)) {
                    final fabricated.util.Comparator comparator = (fabricated.util.IdComparator)
                                                                    fabric.lang.Object._Proxy.
                                                                    $getProxy(((fabricated.util.IdComparator)
                                                                                 new fabricated.
                                                                                 util.
                                                                                 IdComparator.
                                                                                 _Impl(
                                                                                 store,
                                                                                 destinationL).
                                                                                 $getProxy(
                                                                                   )).
                                                                                fabricated$util$IdComparator$(
                                                                                  ));
                    return (fabricated.util.LinkedList) fabric.lang.Object._Proxy.
                             $getProxy(((fabricated.util.LinkedList) new fabricated.
                                          util.LinkedList._Impl(store, destinationL).
                                          $getProxy()).fabricated$util$LinkedList$(
                                                         comparator)); }
                if (0 == 0) throw new java.lang.Error("WAT: listCopy If failed");
                return null; }
            catch (java.lang.IllegalStateException exc$10) { throw new fabric.common.exceptions.ApplicationError(
                                                               exc$10); }
            catch (java.lang.NullPointerException exc$10) { throw new fabric.common.exceptions.ApplicationError(
                                                              exc$10); } }
        
        /**
         * record some local information about the message, and maybe print some
         stuff.
         **/
        private void recordLocal(final java.lang.String incomingMessage) { try {
                                                                               final fabric.worker.Store store =
                                                                                 this.
                                                                                 fetch(
                                                                                   ).
                                                                                 $getStore(
                                                                                   );
                                                                               final fabric.lang.security.Principal storeP =
                                                                                 store.
                                                                                 getPrincipal(
                                                                                   );
                                                                               final fabric.lang.security.Label readFutureWritePast =
                                                                                 this.
                                                                                 get$jif$SuperPing_readFuture(
                                                                                   ).
                                                                                 join(
                                                                                   this.
                                                                                     fetch(
                                                                                       ).
                                                                                     $getStore(
                                                                                       ),
                                                                                   this.
                                                                                     get$jif$SuperPing_writePast(
                                                                                       ),
                                                                                   true);
                                                                               final fabric.lang.security.Label storeReads =
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                 toLabel(
                                                                                   this.
                                                                                     fetch(
                                                                                       ).
                                                                                     $getStore(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     readerPolicy(
                                                                                       this.
                                                                                         fetch(
                                                                                           ).
                                                                                         $getStore(
                                                                                           ),
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                         topPrincipal(
                                                                                           ),
                                                                                       storeP),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     topInteg(
                                                                                       ));
                                                                               final fabric.lang.security.Label storeWrites =
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                 toLabel(
                                                                                   this.
                                                                                     fetch(
                                                                                       ).
                                                                                     $getStore(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     bottomConf(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     writerPolicy(
                                                                                       this.
                                                                                         fetch(
                                                                                           ).
                                                                                         $getStore(
                                                                                           ),
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                         topPrincipal(
                                                                                           ),
                                                                                       storeP));
                                                                               final fabric.lang.security.Label storeThinksStoreReads =
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                 toLabel(
                                                                                   this.
                                                                                     fetch(
                                                                                       ).
                                                                                     $getStore(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     readerPolicy(
                                                                                       this.
                                                                                         fetch(
                                                                                           ).
                                                                                         $getStore(
                                                                                           ),
                                                                                       storeP,
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                         topPrincipal(
                                                                                           )),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     topInteg(
                                                                                       ));
                                                                               final fabric.lang.security.Label storeThinksStoreWrites =
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                 toLabel(
                                                                                   this.
                                                                                     fetch(
                                                                                       ).
                                                                                     $getStore(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     bottomConf(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     writerPolicy(
                                                                                       this.
                                                                                         fetch(
                                                                                           ).
                                                                                         $getStore(
                                                                                           ),
                                                                                       storeP,
                                                                                       fabric.lang.security.PrincipalUtil._Impl.
                                                                                         topPrincipal(
                                                                                           )));
                                                                               final java.lang.String initialText =
                                                                                 ("I have, througout history, received the following messages:" +
                                                                                  "\n");
                                                                               if (fabric.lang.security.LabelUtil._Impl.
                                                                                     relabelsTo(
                                                                                       readFutureWritePast,
                                                                                       storeReads) &&
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                     relabelsTo(
                                                                                       storeWrites,
                                                                                       readFutureWritePast) &&
                                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                                     equivalentTo(
                                                                                       storeP,
                                                                                       store.
                                                                                         getPrincipal(
                                                                                           ))) {
                                                                                   fabric.util.Map root =
                                                                                     store.
                                                                                     getRoot(
                                                                                       );
                                                                                   Message myMessage =
                                                                                     Message._Impl.
                                                                                     jif$cast$Message(
                                                                                       this.
                                                                                         get$jif$SuperPing_readFuture(
                                                                                           ).
                                                                                         join(
                                                                                           fabric.worker.Worker.
                                                                                             getWorker(
                                                                                               ).
                                                                                             getLocalStore(
                                                                                               ),
                                                                                           this.
                                                                                             get$jif$SuperPing_writePast(
                                                                                               ),
                                                                                           true),
                                                                                       root.
                                                                                         get(
                                                                                           fabric.lang.WrappedJavaInlineable.
                                                                                             $wrap(
                                                                                               "myMessage")));
                                                                                   if (fabric.lang.Object._Proxy.
                                                                                         idEquals(
                                                                                           myMessage,
                                                                                           null)) {
                                                                                       myMessage =
                                                                                         (Message)
                                                                                           fabric.lang.Object._Proxy.
                                                                                           $getProxy(
                                                                                             ((Message)
                                                                                                new Message.
                                                                                                _Impl(
                                                                                                store,
                                                                                                this.
                                                                                                  get$jif$SuperPing_readFuture(
                                                                                                    ).
                                                                                                  join(
                                                                                                    store,
                                                                                                    this.
                                                                                                      get$jif$SuperPing_writePast(
                                                                                                        ),
                                                                                                    true)).
                                                                                                $getProxy(
                                                                                                  )).
                                                                                               Message$(
                                                                                                 initialText));
                                                                                       root.
                                                                                         put(
                                                                                           fabric.lang.WrappedJavaInlineable.
                                                                                             $wrap(
                                                                                               "myMessage"),
                                                                                           myMessage);
                                                                                   }
                                                                                   myMessage.
                                                                                     update(
                                                                                       myMessage.
                                                                                         getMessage(
                                                                                           ) +
                                                                                       "\t" +
                                                                                       incomingMessage +
                                                                                       "\n");
                                                                               }
                                                                           }
                                                                           catch (java.lang.NullPointerException exc$11) {
                                                                               throw new fabric.common.exceptions.ApplicationError(
                                                                                 exc$11);
                                                                           }
                                                                           catch (java.lang.ClassCastException exc$11) {
                                                                               throw new fabric.common.exceptions.ApplicationError(
                                                                                 exc$11);
                                                                           } }
        
        public void ping(final Message incoming, final fabricated.util.List storeList) {
            try { final fabricated.util.List storeListCopy = ((SuperPing._Impl) this.
                                                                fetch()).listRelabel(
                                                                           storeList,
                                                                           this.
                                                                             get$jif$SuperPing_storeListL(
                                                                               ),
                                                                           this.
                                                                             get$jif$SuperPing_readFuture(
                                                                               ).
                                                                             join(
                                                                               this.
                                                                                 fetch(
                                                                                   ).
                                                                                 $getStore(
                                                                                   ),
                                                                               this.
                                                                                 get$jif$SuperPing_writePast(
                                                                                   ),
                                                                               true),
                                                                           this.
                                                                             fetch(
                                                                               ).
                                                                             $getStore(
                                                                               ));
                  final java.lang.String incomingText = incoming.getMessage();
                  final java.lang.String outgoingText = incomingText + "!";
                  ((SuperPing._Impl) this.fetch()).recordLocal(incomingText);
                  if (storeList.size() > 0) { final fabric.worker.remote.RemoteWorker nextWorker =
                                                ((SuperPing._Impl) this.fetch()).
                                                getListHeadWorker(storeListCopy);
                                              final fabric.lang.security.Principal nextWorkerP =
                                                nextWorker.getPrincipal();
                                              final fabric.worker.Store nextStore =
                                                ((SuperPing._Impl) this.fetch()).
                                                getListHeadStore(storeListCopy);
                                              final fabric.lang.security.Principal nextStoreP =
                                                nextStore.getPrincipal();
                                              final fabric.lang.security.Label newReadPast =
                                                ((SuperPing._Impl) this.fetch()).
                                                getNewReadPast(storeListCopy);
                                              final fabric.lang.security.Label newWritePast =
                                                ((SuperPing._Impl) this.fetch()).
                                                getNewWritePast(storeListCopy);
                                              final fabric.lang.security.Label newReadFuture =
                                                ((SuperPing._Impl) this.fetch()).
                                                readFutureRecursion(storeListCopy,
                                                                    0);
                                              final fabric.lang.security.Label newWriteFuture =
                                                ((SuperPing._Impl) this.fetch()).
                                                writeFutureRecursion(storeListCopy,
                                                                     0);
                                              final fabric.lang.security.Label readFutureWritePast =
                                                this.get$jif$SuperPing_readFuture(
                                                       ).join(this.fetch().$getStore(
                                                                             ), this.
                                                                get$jif$SuperPing_writePast(
                                                                  ), true);
                                              final fabric.lang.security.Label newReadFutureWritePast =
                                                newReadFuture.join(this.fetch().
                                                                     $getStore(),
                                                                   newWritePast,
                                                                   true);
                                              final fabric.worker.Store store = this.
                                                fetch().$getStore();
                                              final fabric.lang.security.Principal storeP =
                                                store.getPrincipal();
                                              final fabric.lang.security.Label storeReads =
                                                fabric.lang.security.LabelUtil._Impl.
                                                toLabel(this.fetch().$getStore(),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          readerPolicy(this.fetch(
                                                                              ).
                                                                         $getStore(
                                                                           ), fabric.lang.security.PrincipalUtil._Impl.
                                                                         topPrincipal(
                                                                           ), storeP),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          topInteg());
                                              final fabric.lang.security.Label storeWrites =
                                                fabric.lang.security.LabelUtil._Impl.
                                                toLabel(this.fetch().$getStore(),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          bottomConf(), fabric.lang.security.LabelUtil._Impl.
                                                          writerPolicy(this.fetch(
                                                                              ).
                                                                         $getStore(
                                                                           ), fabric.lang.security.PrincipalUtil._Impl.
                                                                         topPrincipal(
                                                                           ), storeP));
                                              final fabric.lang.security.Label nextStoreReads =
                                                fabric.lang.security.LabelUtil._Impl.
                                                toLabel(this.fetch().$getStore(),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          readerPolicy(this.fetch(
                                                                              ).
                                                                         $getStore(
                                                                           ), fabric.lang.security.PrincipalUtil._Impl.
                                                                         topPrincipal(
                                                                           ), nextStoreP),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          topInteg());
                                              final fabric.lang.security.Label nextStoreWrites =
                                                fabric.lang.security.LabelUtil._Impl.
                                                toLabel(this.fetch().$getStore(),
                                                        fabric.lang.security.LabelUtil._Impl.
                                                          bottomConf(), fabric.lang.security.LabelUtil._Impl.
                                                          writerPolicy(this.fetch(
                                                                              ).
                                                                         $getStore(
                                                                           ), fabric.lang.security.PrincipalUtil._Impl.
                                                                         topPrincipal(
                                                                           ), nextStoreP));
                                              final fabric.lang.security.Label readFutureNewWritePast =
                                                this.get$jif$SuperPing_readFuture(
                                                       ).join(this.fetch().$getStore(
                                                                             ), newWritePast,
                                                              true);
                                              boolean debuggerator = false;
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(storeP, store.getPrincipal(
                                                                                 ))) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     "DEBUGGERATOR: storeP not equiv store\nstoreP" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         storeP) +
                                                                     "\nstore:" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         store.getPrincipal(
                                                                                 )));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(storeP, this.fetch(
                                                                                ).
                                                                   $getStore().getPrincipal(
                                                                                 ))) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     "DEBUGGERATOR: storeP not equiv this.store$\nstoreP" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         storeP) +
                                                                     "\nthis.store$:" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         this.fetch(
                                                                                ).
                                                                           $getStore(
                                                                             ).getPrincipal(
                                                                                 )));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextStore.
                                                                   getPrincipal(
                                                                     ))) { debuggerator =
                                                                             true;
                                              }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     "DEBUGGERATOR: nextStoreP not equiv nestStore\nnestStoreP" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextStoreP) +
                                                                     "\nnextStore:" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextStore.
                                                                           getPrincipal(
                                                                             )));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextWorker.
                                                                   getPrincipal(
                                                                     ))) { debuggerator =
                                                                             true;
                                              }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     "DEBUGGERATOR: nextStoreP not equiv nextWorker\nnestStoreP" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextStoreP) +
                                                                     "\nnextWorker:" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextWorker.
                                                                           getPrincipal(
                                                                             )));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextWorkerP)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     "DEBUGGERATOR: nextStoreP not equiv nextWorkerP\nnestStoreP" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextStoreP) +
                                                                     "\nnextWorkerP:" +
                                                                     fabric.lang.security.PrincipalUtil._Impl.
                                                                       toString(
                                                                         nextWorkerP));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               storeReads)) { debuggerator =
                                                                                true;
                                              }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: readFutureWritePast doesn\'t flow to  storeRea" +
                                                                      "ds\nreadFutureWritePast:") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         readFutureWritePast) +
                                                                     "\nstoreReads:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         storeReads));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newWritePast, storeReads)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: newWritePast doesn\'t flow to  storeReads\nnew" +
                                                                      "WritePast:") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newWritePast) +
                                                                     "\nstoreReads:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         storeReads));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newWritePast, newReadFuture)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: newWritePast doesn\'t flow to  newReadFuture\n" +
                                                                      "newWritePast:") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newWritePast) +
                                                                     "\nnewReadFuture:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newReadFuture));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               newReadFuture)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: readFutureWritePast doesn\'t flow to  newReadF" +
                                                                      "uture\nreadFutureWritePast:") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         readFutureWritePast) +
                                                                     "\nnewReadFuture:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newReadFuture));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(storeWrites, readFutureNewWritePast)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: storeWrites doesn\'t flow to  readFutureNewWri" +
                                                                      "tePast\nstoreWrites:") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         storeWrites) +
                                                                     "\nreadFutureNewWritePast:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         readFutureNewWritePast));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               this.get$jif$SuperPing_readFuture(
                                                                      ).join(fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getLocalStore(
                                                                                 ),
                                                                             newWritePast,
                                                                             true))) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: storeWrites doesn\'t flow to new label{*readFu" +
                                                                      "ture; *newWritePast} \nreadFutureWritePast :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         readFutureWritePast) +
                                                                     "\nnew label{*readFuture; *newWritePast}:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         this.get$jif$SuperPing_readFuture(
                                                                                ).
                                                                           join(
                                                                             fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getLocalStore(
                                                                                 ),
                                                                             newWritePast,
                                                                             true)));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newReadFutureWritePast,
                                                               nextStoreReads)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: newReadFutureWritePast doesn\'t flow to nextSt" +
                                                                      "oreReads\nnewReadFutureWritePast :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newReadFutureWritePast) +
                                                                     "\nnextStoreReads:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreReads));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, nextStoreReads)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: nextStoreWrites doesn\'t flow to nextStoreRead" +
                                                                      "s\nnextStoreWrites :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreWrites) +
                                                                     "\nnextStoreReads:" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreReads));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, newReadPast)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: nextStoreWrites doesn\'t flow to newReadPast " +
                                                                      "\nnextStoreWrites :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreWrites) +
                                                                     "\nnewReadPast :" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newReadPast));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, newWritePast)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: nextStoreWrites doesn\'t flow to newWritePast " +
                                                                      "\nnextStoreWrites :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreWrites) +
                                                                     "\nnewWritePast :" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newWritePast));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, this.
                                                                 get$jif$SuperPing_readFuture(
                                                                   ))) { debuggerator =
                                                                           true;
                                              }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: nextStoreWrites doesn\'t flow to readFuture \n" +
                                                                      "nextStoreWrites :") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         nextStoreWrites) +
                                                                     "\nreadFuture :" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         this.get$jif$SuperPing_readFuture(
                                                                                )));
                                              }
                                              debuggerator = false;
                                              if (fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               newReadFutureWritePast)) {
                                                  debuggerator = true; }
                                              if (!debuggerator) { throw new java.lang.Error(
                                                                     ("DEBUGGERATOR: readFutureWritePast doesn\'t flow to newReadFu" +
                                                                      "tureWritePast\nreadFutureWritePast: ") +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         readFutureWritePast) +
                                                                     "\nnewReadFutureWritePast :" +
                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                       toString(
                                                                         newReadFutureWritePast));
                                              }
                                              if (fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(storeP, store.getPrincipal(
                                                                                 )) &&
                                                    fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(storeP, this.fetch(
                                                                                ).
                                                                   $getStore().getPrincipal(
                                                                                 )) &&
                                                    fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextStore.
                                                                   getPrincipal(
                                                                     )) && fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextWorker.
                                                                   getPrincipal(
                                                                     )) && fabric.lang.security.PrincipalUtil._Impl.
                                                    equivalentTo(nextStoreP, nextWorkerP) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               storeReads) && fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newWritePast, storeReads) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newWritePast, newReadFuture) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               newReadFuture) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(storeWrites, readFutureNewWritePast) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               this.get$jif$SuperPing_readFuture(
                                                                      ).join(fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getLocalStore(
                                                                                 ),
                                                                             newWritePast,
                                                                             true)) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(newReadFutureWritePast,
                                                               nextStoreReads) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, nextStoreReads) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, newReadPast) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, newWritePast) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(nextStoreWrites, this.
                                                                 get$jif$SuperPing_readFuture(
                                                                   )) && fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(readFutureWritePast,
                                                               newReadFutureWritePast) &&
                                                    fabric.lang.security.LabelUtil._Impl.
                                                    relabelsTo(this.get$jif$SuperPing_readFuture(
                                                                      ).join(fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getLocalStore(
                                                                                 ),
                                                                             newWritePast,
                                                                             true),
                                                               newReadFuture.join(
                                                                               fabric.worker.Worker.
                                                                                 getWorker(
                                                                                   ).
                                                                                 getLocalStore(
                                                                                   ),
                                                                               newWritePast,
                                                                               true))) {
                                                  final fabric.lang.security.Label readFutureNewWritePastN =
                                                    readFutureNewWritePast;
                                                  final fabric.lang.security.Label newWritePastN =
                                                    newWritePast;
                                                  final fabric.lang.security.Label newReadFutureN =
                                                    newReadFuture;
                                                  final fabric.lang.security.Label newWriteFutureN =
                                                    newWriteFuture;
                                                  final fabric.lang.security.Label newReadPastN =
                                                    newReadPast;
                                                  final fabricated.util.List newStoreList =
                                                    ((SuperPing._Impl) this.fetch(
                                                                              )).
                                                    tail(storeListCopy, readFutureWritePast,
                                                         readFutureNewWritePastN,
                                                         nextStore);
                                                  final SuperPing sp = (SuperPing)
                                                                         fabric.lang.Object._Proxy.
                                                                         $getProxy(
                                                                           ((SuperPing)
                                                                              new SuperPing.
                                                                              _Impl(
                                                                              nextStore,
                                                                              readFutureNewWritePastN,
                                                                              readFutureNewWritePastN,
                                                                              newReadPastN,
                                                                              newWritePastN,
                                                                              newReadFutureN,
                                                                              newWriteFutureN).
                                                                              $getProxy(
                                                                                )).
                                                                             SuperPing$(
                                                                               ));
                                                  final Message outgoing = (Message)
                                                                             fabric.lang.Object._Proxy.
                                                                             $getProxy(
                                                                               ((Message)
                                                                                  new Message.
                                                                                  _Impl(
                                                                                  nextStore,
                                                                                  readFutureNewWritePastN).
                                                                                  $getProxy(
                                                                                    )).
                                                                                 Message$(
                                                                                   outgoingText));
                                                  ((SuperPing._Proxy) sp).ping$remote(
                                                                            nextWorker,
                                                                            SuperPing._Static._Proxy.$instance.
                                                                              get$worker$(
                                                                                ).
                                                                              getPrincipal(
                                                                                ),
                                                                            outgoing,
                                                                            newStoreList);
                                              } } }
            catch (java.lang.NullPointerException exc$12) { throw new fabric.common.exceptions.ApplicationError(
                                                              exc$12); } }
        
        public SuperPing SuperPing$() { ((SuperPing._Impl) this.fetch()).jif$init(
                                                                           );
                                        { this.fabric$lang$Object$(); }
                                        return (SuperPing) this.$getProxy(); }
        
        public void ping_remote(final fabric.lang.security.Principal worker$principal,
                                final Message incoming, final fabricated.util.List storeList) {
            if (fabric.lang.security.LabelUtil._Impl.relabelsTo(fabric.lang.security.LabelUtil._Impl.
                                                                  join(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         join(fabric.worker.Worker.
                                                                                getWorker(
                                                                                  ).
                                                                                getLocalStore(
                                                                                  ),
                                                                              fabric.lang.security.LabelUtil._Impl.
                                                                                meet(
                                                                                  fabric.worker.Worker.
                                                                                    getWorker(
                                                                                      ).
                                                                                    getLocalStore(
                                                                                      ),
                                                                                  this.
                                                                                    get$jif$SuperPing_writeFuture(
                                                                                      ).
                                                                                    meet(
                                                                                      fabric.worker.Worker.
                                                                                        getWorker(
                                                                                          ).
                                                                                        getLocalStore(
                                                                                          ),
                                                                                      fabric.lang.security.LabelUtil._Impl.
                                                                                        toLabel(
                                                                                          fabric.worker.Worker.
                                                                                            getWorker(
                                                                                              ).
                                                                                            getLocalStore(
                                                                                              ),
                                                                                          fabric.lang.security.LabelUtil._Impl.
                                                                                            readerPolicy(
                                                                                              fabric.worker.Worker.
                                                                                                getWorker(
                                                                                                  ).
                                                                                                getLocalStore(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  )),
                                                                                          fabric.lang.security.LabelUtil._Impl.
                                                                                            writerPolicy(
                                                                                              fabric.worker.Worker.
                                                                                                getWorker(
                                                                                                  ).
                                                                                                getLocalStore(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ))),
                                                                                      true),
                                                                                  this.
                                                                                    get$jif$SuperPing_writePast(
                                                                                      ).
                                                                                    meet(
                                                                                      fabric.worker.Worker.
                                                                                        getWorker(
                                                                                          ).
                                                                                        getLocalStore(
                                                                                          ),
                                                                                      fabric.lang.security.LabelUtil._Impl.
                                                                                        toLabel(
                                                                                          fabric.worker.Worker.
                                                                                            getWorker(
                                                                                              ).
                                                                                            getLocalStore(
                                                                                              ),
                                                                                          fabric.lang.security.LabelUtil._Impl.
                                                                                            readerPolicy(
                                                                                              fabric.worker.Worker.
                                                                                                getWorker(
                                                                                                  ).
                                                                                                getLocalStore(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  )),
                                                                                          fabric.lang.security.LabelUtil._Impl.
                                                                                            writerPolicy(
                                                                                              fabric.worker.Worker.
                                                                                                getWorker(
                                                                                                  ).
                                                                                                getLocalStore(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ),
                                                                                              fabric.lang.security.PrincipalUtil._Impl.
                                                                                                topPrincipal(
                                                                                                  ))),
                                                                                      true)),
                                                                              this.
                                                                                get$jif$SuperPing_readPast(
                                                                                  ).
                                                                                meet(
                                                                                  fabric.worker.Worker.
                                                                                    getWorker(
                                                                                      ).
                                                                                    getLocalStore(
                                                                                      ),
                                                                                  fabric.lang.security.LabelUtil._Impl.
                                                                                    toLabel(
                                                                                      fabric.worker.Worker.
                                                                                        getWorker(
                                                                                          ).
                                                                                        getLocalStore(
                                                                                          ),
                                                                                      fabric.lang.security.LabelUtil._Impl.
                                                                                        readerPolicy(
                                                                                          fabric.worker.Worker.
                                                                                            getWorker(
                                                                                              ).
                                                                                            getLocalStore(
                                                                                              ),
                                                                                          fabric.lang.security.PrincipalUtil._Impl.
                                                                                            topPrincipal(
                                                                                              ),
                                                                                          fabric.lang.security.PrincipalUtil._Impl.
                                                                                            topPrincipal(
                                                                                              )),
                                                                                      fabric.lang.security.LabelUtil._Impl.
                                                                                        writerPolicy(
                                                                                          fabric.worker.Worker.
                                                                                            getWorker(
                                                                                              ).
                                                                                            getLocalStore(
                                                                                              ),
                                                                                          fabric.lang.security.PrincipalUtil._Impl.
                                                                                            topPrincipal(
                                                                                              ),
                                                                                          fabric.lang.security.PrincipalUtil._Impl.
                                                                                            topPrincipal(
                                                                                              ))),
                                                                                  true)),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         liftToLabel(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             writerPolicy(
                                                                               fabric.worker.Worker.
                                                                                 getWorker(
                                                                                   ).
                                                                                 getLocalStore(
                                                                                   ),
                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                 topPrincipal(
                                                                                   ),
                                                                               worker$principal))),
                                                                fabric.lang.security.LabelUtil._Impl.
                                                                  join(fabric.worker.Worker.
                                                                         getWorker(
                                                                           ).getLocalStore(
                                                                               ),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         liftToLabel(
                                                                           fabric.worker.Worker.
                                                                             getWorker(
                                                                               ).
                                                                             getLocalStore(
                                                                               ),
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             readerPolicy(
                                                                               fabric.worker.Worker.
                                                                                 getWorker(
                                                                                   ).
                                                                                 getLocalStore(
                                                                                   ),
                                                                               fabric.lang.security.PrincipalUtil._Impl.
                                                                                 topPrincipal(
                                                                                   ),
                                                                               worker$principal)),
                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                         meet(fabric.worker.Worker.
                                                                                getWorker(
                                                                                  ).
                                                                                getLocalStore(
                                                                                  ),
                                                                              this.
                                                                                get$jif$SuperPing_storeListL(
                                                                                  ).
                                                                                meet(
                                                                                  fabric.worker.Worker.
                                                                                    getWorker(
                                                                                      ).
                                                                                    getLocalStore(
                                                                                      ),
                                                                                  fabric.lang.security.LabelUtil._Impl.
                                                                                    noComponents(
                                                                                      ),
                                                                                  true),
                                                                              this.
                                                                                get$jif$SuperPing_incomingL(
                                                                                  ).
                                                                                meet(
                                                                                  fabric.worker.Worker.
                                                                                    getWorker(
                                                                                      ).
                                                                                    getLocalStore(
                                                                                      ),
                                                                                  fabric.lang.security.LabelUtil._Impl.
                                                                                    noComponents(
                                                                                      ),
                                                                                  true)))) &&
                  fabric.lang.security.LabelUtil._Impl.relabelsTo(this.get$jif$SuperPing_storeListL(
                                                                         ), this.
                                                                    get$jif$SuperPing_incomingL(
                                                                      )) && fabric.lang.security.LabelUtil._Impl.
                  relabelsTo(this.get$jif$SuperPing_incomingL(), this.get$jif$SuperPing_storeListL(
                                                                        )) && fabric.lang.security.LabelUtil._Impl.
                  relabelsTo(this.get$jif$SuperPing_storeListL(), this.get$jif$SuperPing_readFuture(
                                                                         ).join(
                                                                             fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getLocalStore(
                                                                                 ),
                                                                             this.
                                                                               get$jif$SuperPing_writePast(
                                                                                 ),
                                                                             true)))
                this.ping(incoming, storeList); else throw new fabric.worker.remote.RemoteCallLabelCheckFailedException(
                                                       ); }
        
        public _Impl(fabric.worker.Store $location, final fabric.lang.security.Label jif$incomingL,
                     final fabric.lang.security.Label jif$storeListL, final fabric.lang.security.Label jif$readPast,
                     final fabric.lang.security.Label jif$writePast, final fabric.lang.security.Label jif$readFuture,
                     final fabric.lang.security.Label jif$writeFuture) { super($location);
                                                                         this.jif$SuperPing_incomingL =
                                                                           jif$incomingL;
                                                                         this.jif$SuperPing_storeListL =
                                                                           jif$storeListL;
                                                                         this.jif$SuperPing_readPast =
                                                                           jif$readPast;
                                                                         this.jif$SuperPing_writePast =
                                                                           jif$writePast;
                                                                         this.jif$SuperPing_readFuture =
                                                                           jif$readFuture;
                                                                         this.jif$SuperPing_writeFuture =
                                                                           jif$writeFuture;
        }
        
        public void jif$invokeDefConstructor() { this.SuperPing$(); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.security.Label jif$incomingL,
                                             final fabric.lang.security.Label jif$storeListL,
                                             final fabric.lang.security.Label jif$readPast,
                                             final fabric.lang.security.Label jif$writePast,
                                             final fabric.lang.security.Label jif$readFuture,
                                             final fabric.lang.security.Label jif$writeFuture,
                                             final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                 idEquals(
                                                                                   o,
                                                                                   null))
                                                                               return false;
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             accessCheck(
                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                 join(
                                                                                   fabric.worker.Worker.
                                                                                     getWorker(
                                                                                       ).
                                                                                     getLocalStore(
                                                                                       ),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     join(
                                                                                       fabric.worker.Worker.
                                                                                         getWorker(
                                                                                           ).
                                                                                         getLocalStore(
                                                                                           ),
                                                                                       jif$writePast.
                                                                                         meet(
                                                                                           fabric.worker.Worker.
                                                                                             getWorker(
                                                                                               ).
                                                                                             getLocalStore(
                                                                                               ),
                                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                                             toLabel(
                                                                                               fabric.worker.Worker.
                                                                                                 getWorker(
                                                                                                   ).
                                                                                                 getLocalStore(
                                                                                                   ),
                                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                                 readerPolicy(
                                                                                                   fabric.worker.Worker.
                                                                                                     getWorker(
                                                                                                       ).
                                                                                                     getLocalStore(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       )),
                                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                                 writerPolicy(
                                                                                                   fabric.worker.Worker.
                                                                                                     getWorker(
                                                                                                       ).
                                                                                                     getLocalStore(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ))),
                                                                                           true),
                                                                                       jif$readFuture.
                                                                                         meet(
                                                                                           fabric.worker.Worker.
                                                                                             getWorker(
                                                                                               ).
                                                                                             getLocalStore(
                                                                                               ),
                                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                                             toLabel(
                                                                                               fabric.worker.Worker.
                                                                                                 getWorker(
                                                                                                   ).
                                                                                                 getLocalStore(
                                                                                                   ),
                                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                                 readerPolicy(
                                                                                                   fabric.worker.Worker.
                                                                                                     getWorker(
                                                                                                       ).
                                                                                                     getLocalStore(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       )),
                                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                                 writerPolicy(
                                                                                                   fabric.worker.Worker.
                                                                                                     getWorker(
                                                                                                       ).
                                                                                                     getLocalStore(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ),
                                                                                                   fabric.lang.security.PrincipalUtil._Impl.
                                                                                                     topPrincipal(
                                                                                                       ))),
                                                                                           true)),
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                     liftToLabel(
                                                                                       fabric.worker.Worker.
                                                                                         getWorker(
                                                                                           ).
                                                                                         getLocalStore(
                                                                                           ),
                                                                                       fabric.lang.security.LabelUtil._Impl.
                                                                                         topInteg(
                                                                                           ))),
                                                                               o);
                                                                           if (fabric.lang.Object._Proxy.
                                                                                 $getProxy(
                                                                                   (java.lang.Object)
                                                                                     fabric.lang.WrappedJavaInlineable.
                                                                                     $unwrap(
                                                                                       o)) instanceof SuperPing) {
                                                                               SuperPing c =
                                                                                 (SuperPing)
                                                                                   fabric.lang.Object._Proxy.
                                                                                   $getProxy(
                                                                                     o);
                                                                               boolean ok =
                                                                                 true;
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_incomingL(
                                                                                         ),
                                                                                     jif$incomingL);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_storeListL(
                                                                                         ),
                                                                                     jif$storeListL);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_readPast(
                                                                                         ),
                                                                                     jif$readPast);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_writePast(
                                                                                         ),
                                                                                     jif$writePast);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_readFuture(
                                                                                         ),
                                                                                     jif$readFuture);
                                                                               ok =
                                                                                 ok &&
                                                                                   fabric.lang.security.LabelUtil._Impl.
                                                                                   equivalentTo(
                                                                                     c.
                                                                                       get$jif$SuperPing_writeFuture(
                                                                                         ),
                                                                                     jif$writeFuture);
                                                                               return ok;
                                                                           }
                                                                           return false;
        }
        
        public static SuperPing jif$cast$SuperPing(final fabric.lang.security.Label jif$incomingL,
                                                   final fabric.lang.security.Label jif$storeListL,
                                                   final fabric.lang.security.Label jif$readPast,
                                                   final fabric.lang.security.Label jif$writePast,
                                                   final fabric.lang.security.Label jif$readFuture,
                                                   final fabric.lang.security.Label jif$writeFuture,
                                                   final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (SuperPing._Impl.jif$Instanceof(jif$incomingL, jif$storeListL, jif$readPast,
                                               jif$writePast, jif$readFuture, jif$writeFuture,
                                               o)) return (SuperPing) fabric.lang.Object._Proxy.
                                                            $getProxy(o);
            throw new java.lang.ClassCastException(); }
        
        public fabric.lang.security.Label get$jif$SuperPing_incomingL() { return this.
                                                                                   jif$SuperPing_incomingL;
        }
        
        private fabric.lang.security.Label jif$SuperPing_incomingL;
        
        public fabric.lang.security.Label get$jif$SuperPing_storeListL() { return this.
                                                                                    jif$SuperPing_storeListL;
        }
        
        private fabric.lang.security.Label jif$SuperPing_storeListL;
        
        public fabric.lang.security.Label get$jif$SuperPing_readPast() { return this.
                                                                                  jif$SuperPing_readPast;
        }
        
        private fabric.lang.security.Label jif$SuperPing_readPast;
        
        public fabric.lang.security.Label get$jif$SuperPing_writePast() { return this.
                                                                                   jif$SuperPing_writePast;
        }
        
        private fabric.lang.security.Label jif$SuperPing_writePast;
        
        public fabric.lang.security.Label get$jif$SuperPing_readFuture() { return this.
                                                                                    jif$SuperPing_readFuture;
        }
        
        private fabric.lang.security.Label jif$SuperPing_readFuture;
        
        public fabric.lang.security.Label get$jif$SuperPing_writeFuture() { return this.
                                                                                     jif$SuperPing_writeFuture;
        }
        
        private fabric.lang.security.Label jif$SuperPing_writeFuture;
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           join(
                                                                             this.
                                                                               $getStore(
                                                                                 ),
                                                                             fabric.lang.security.LabelUtil._Impl.
                                                                               join(
                                                                                 this.
                                                                                   $getStore(
                                                                                     ),
                                                                                 this.
                                                                                   get$jif$SuperPing_writePast(
                                                                                     ).
                                                                                   meet(
                                                                                     this.
                                                                                       $getStore(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       toLabel(
                                                                                         this.
                                                                                           $getStore(
                                                                                             ),
                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                           readerPolicy(
                                                                                             this.
                                                                                               $getStore(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 )),
                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                           writerPolicy(
                                                                                             this.
                                                                                               $getStore(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ))),
                                                                                     true),
                                                                                 this.
                                                                                   get$jif$SuperPing_readFuture(
                                                                                     ).
                                                                                   meet(
                                                                                     this.
                                                                                       $getStore(
                                                                                         ),
                                                                                     fabric.lang.security.LabelUtil._Impl.
                                                                                       toLabel(
                                                                                         this.
                                                                                           $getStore(
                                                                                             ),
                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                           readerPolicy(
                                                                                             this.
                                                                                               $getStore(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 )),
                                                                                         fabric.lang.security.LabelUtil._Impl.
                                                                                           writerPolicy(
                                                                                             this.
                                                                                               $getStore(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ),
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                               topPrincipal(
                                                                                                 ))),
                                                                                     true)),
                                                                             fabric.lang.security.LabelUtil._Impl.
                                                                               liftToLabel(
                                                                                 this.
                                                                                   $getStore(
                                                                                     ),
                                                                                 fabric.lang.security.LabelUtil._Impl.
                                                                                   topInteg(
                                                                                     ))).
                                                                           confPolicy(
                                                                             ));
                                                  return (SuperPing) this.$getProxy(
                                                                            ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new SuperPing.
                                                             _Proxy(this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                            interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_incomingL,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_storeListL,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_readPast,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_writePast,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_readFuture,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs);
                                           $writeRef($getStore(), this.jif$SuperPing_writeFuture,
                                                     refTypes, out, intraStoreRefs,
                                                     interStoreRefs); }
        
        public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                     fabric.worker.Store labelStore, long labelOnum, fabric.worker.
                       Store accessPolicyStore, long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs) throws java.io.IOException,
            java.lang.ClassNotFoundException { super(store, onum, version, expiry,
                                                     labelStore, labelOnum, accessPolicyStore,
                                                     accessPolicyOnum, in, refTypes,
                                                     intraStoreRefs, interStoreRefs);
                                               this.jif$SuperPing_incomingL = (fabric.
                                                                                lang.
                                                                                security.
                                                                                Label)
                                                                                $readRef(
                                                                                  fabric.
                                                                                    lang.
                                                                                    security.
                                                                                    Label.
                                                                                    _Proxy.class,
                                                                                  (fabric.
                                                                                    common.
                                                                                    RefTypeEnum)
                                                                                    refTypes.
                                                                                    next(
                                                                                      ),
                                                                                  in,
                                                                                  store,
                                                                                  intraStoreRefs,
                                                                                  interStoreRefs);
                                               this.jif$SuperPing_storeListL = (fabric.
                                                                                 lang.
                                                                                 security.
                                                                                 Label)
                                                                                 $readRef(
                                                                                   fabric.
                                                                                     lang.
                                                                                     security.
                                                                                     Label.
                                                                                     _Proxy.class,
                                                                                   (fabric.
                                                                                     common.
                                                                                     RefTypeEnum)
                                                                                     refTypes.
                                                                                     next(
                                                                                       ),
                                                                                   in,
                                                                                   store,
                                                                                   intraStoreRefs,
                                                                                   interStoreRefs);
                                               this.jif$SuperPing_readPast = (fabric.
                                                                               lang.
                                                                               security.
                                                                               Label)
                                                                               $readRef(
                                                                                 fabric.
                                                                                   lang.
                                                                                   security.
                                                                                   Label.
                                                                                   _Proxy.class,
                                                                                 (fabric.
                                                                                   common.
                                                                                   RefTypeEnum)
                                                                                   refTypes.
                                                                                   next(
                                                                                     ),
                                                                                 in,
                                                                                 store,
                                                                                 intraStoreRefs,
                                                                                 interStoreRefs);
                                               this.jif$SuperPing_writePast = (fabric.
                                                                                lang.
                                                                                security.
                                                                                Label)
                                                                                $readRef(
                                                                                  fabric.
                                                                                    lang.
                                                                                    security.
                                                                                    Label.
                                                                                    _Proxy.class,
                                                                                  (fabric.
                                                                                    common.
                                                                                    RefTypeEnum)
                                                                                    refTypes.
                                                                                    next(
                                                                                      ),
                                                                                  in,
                                                                                  store,
                                                                                  intraStoreRefs,
                                                                                  interStoreRefs);
                                               this.jif$SuperPing_readFuture = (fabric.
                                                                                 lang.
                                                                                 security.
                                                                                 Label)
                                                                                 $readRef(
                                                                                   fabric.
                                                                                     lang.
                                                                                     security.
                                                                                     Label.
                                                                                     _Proxy.class,
                                                                                   (fabric.
                                                                                     common.
                                                                                     RefTypeEnum)
                                                                                     refTypes.
                                                                                     next(
                                                                                       ),
                                                                                   in,
                                                                                   store,
                                                                                   intraStoreRefs,
                                                                                   interStoreRefs);
                                               this.jif$SuperPing_writeFuture = (fabric.
                                                                                  lang.
                                                                                  security.
                                                                                  Label)
                                                                                  $readRef(
                                                                                    fabric.
                                                                                      lang.
                                                                                      security.
                                                                                      Label.
                                                                                      _Proxy.class,
                                                                                    (fabric.
                                                                                      common.
                                                                                      RefTypeEnum)
                                                                                      refTypes.
                                                                                      next(
                                                                                        ),
                                                                                    in,
                                                                                    store,
                                                                                    intraStoreRefs,
                                                                                    interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) { super.$copyAppStateFrom(
                                                                                other);
                                                                        SuperPing.
                                                                          _Impl src =
                                                                          (SuperPing.
                                                                            _Impl)
                                                                            other;
                                                                        this.jif$SuperPing_incomingL =
                                                                          src.jif$SuperPing_incomingL;
                                                                        this.jif$SuperPing_storeListL =
                                                                          src.jif$SuperPing_storeListL;
                                                                        this.jif$SuperPing_readPast =
                                                                          src.jif$SuperPing_readPast;
                                                                        this.jif$SuperPing_writePast =
                                                                          src.jif$SuperPing_writePast;
                                                                        this.jif$SuperPing_readFuture =
                                                                          src.jif$SuperPing_readFuture;
                                                                        this.jif$SuperPing_writeFuture =
                                                                          src.jif$SuperPing_writeFuture;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements SuperPing.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((SuperPing._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((SuperPing.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((SuperPing.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((SuperPing.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public java.lang.String get$jlc$ClassType$fabric$1() { return ((SuperPing.
                                                                             _Static.
                                                                             _Impl)
                                                                             fetch(
                                                                               )).
                                                                     get$jlc$ClassType$fabric$1(
                                                                       ); }
            
            public _Proxy(SuperPing._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final SuperPing._Static $instance;
            
            static { SuperPing._Static._Impl impl = (SuperPing._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                      $makeStaticInstance(SuperPing.
                                                                            _Static.
                                                                            _Impl.class);
                     $instance = (SuperPing._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements SuperPing._Static
        {
            
            public fabric.worker.Worker get$worker$() { return this.worker$; }
            
            fabric.worker.Worker worker$;
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return this.
                                                                                jlc$CompilerVersion$fabric;
            }
            
            public java.lang.String jlc$CompilerVersion$fabric;
            
            public long get$jlc$SourceLastModified$fabric() { return this.jlc$SourceLastModified$fabric;
            }
            
            public long jlc$SourceLastModified$fabric;
            
            public java.lang.String get$jlc$ClassType$fabric() { return this.jlc$ClassType$fabric;
            }
            
            public java.lang.String jlc$ClassType$fabric;
            
            public java.lang.String get$jlc$ClassType$fabric$1() { return this.jlc$ClassType$fabric$1;
            }
            
            public java.lang.String jlc$ClassType$fabric$1;
            
            public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                   java.util.List intraStoreRefs, java.util.List interStoreRefs)
                  throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                                interStoreRefs);
                                               $writeInline(out, this.jlc$CompilerVersion$fabric);
                                               out.writeLong(this.jlc$SourceLastModified$fabric);
                                               $writeInline(out, this.jlc$ClassType$fabric);
                                               $writeInline(out, this.jlc$ClassType$fabric$1);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version, long expiry,
                         fabric.worker.Store labelStore, long labelOnum, fabric.
                           worker.Store accessPolicyStore, long accessPolicyOnum,
                         java.io.ObjectInput in, java.util.Iterator refTypes, java.
                           util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs, interStoreRefs);
                this.jlc$CompilerVersion$fabric = (java.lang.String) in.readObject(
                                                                          );
                this.jlc$SourceLastModified$fabric = in.readLong();
                this.jlc$ClassType$fabric = (java.lang.String) in.readObject();
                this.jlc$ClassType$fabric$1 = (java.lang.String) in.readObject();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new SuperPing.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm96 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff97 = 1;
                                       $label92: for (boolean $commit93 = false;
                                                      !$commit93; ) { if ($backoff97 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff97);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e94) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff97 <
                                                                            5000)
                                                                          $backoff97 *=
                                                                            2;
                                                                      $commit93 =
                                                                        true;
                                                                      fabric.worker.transaction.TransactionManager.
                                                                        getInstance(
                                                                          ).startTransaction(
                                                                              );
                                                                      try { this.
                                                                              worker$ =
                                                                              fabric.worker.Worker.
                                                                                getWorker(
                                                                                  );
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               RetryException $e94) {
                                                                          $commit93 =
                                                                            false;
                                                                          continue $label92;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e94) {
                                                                          $commit93 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid95 =
                                                                            $tm96.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e94.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid95))
                                                                              continue $label92;
                                                                          if ($currentTid95.
                                                                                parent !=
                                                                                null)
                                                                              throw $e94;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e94) {
                                                                          $commit93 =
                                                                            false;
                                                                          if ($tm96.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label92;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e94);
                                                                      }
                                                                      finally { if ($commit93) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e94) {
                                                                                        $commit93 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e94) {
                                                                                        $commit93 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid95 =
                                                                                          $tm96.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid95 ==
                                                                                              null ||
                                                                                              $e94.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid95) &&
                                                                                              !$currentTid95.
                                                                                              equals(
                                                                                                $e94.
                                                                                                  tid))
                                                                                            continue $label92;
                                                                                        throw $e94;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit93) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 36, 122, -107, 0, 99, -105,
    -84, -32, -14, -50, 38, -64, 114, -5, -96, -67, -114, -20, 94, 118, 88, 102,
    -38, 0, -101, -31, 127, -103, 33, 106, -84, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1446064403000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAALS7Wcw0bXoe9M54PF7jGTtORBzHcewh4LTz19pVXRm22vd9" +
                                                                "6ypDhtr3pWvpqi4wSg6IrUQyCJyQSIQjS6BgCIoUcYAs5QQISoQEQggOIFGE" +
                                                                "RFDIQQ5YDoBQ7/f9//zjfyZESNBSVz/97PXc933d1/V2vb/5d9++f57efi6P" +
                                                                "4qr9ZHmN2fwJF8WiYkTTnKV0G82zc9Z+K/mRr4h/5m//2+nPfPnty8rbjyZR" +
                                                                "P/RVErXf6ufl7ceUOnpGQJ8tgGuJ3/yltx9K3gcK0Vwub1/+JWqf3n52HNpX" +
                                                                "0Q7Lp4t81/x/+gL8+r/xR7/+l77v7Wvh29eq3l6ipUrooV+yfQnffrTLujib" +
                                                                "ZjJNszR8+/E+y1I7m6qorY6z49CHbz8xV0UfLeuUzVY2D+3zveNPzOuYTR/W" +
                                                                "/KzyffvDue1pTZZhOrf/9Y/bX5eqBZRqXr6pvH01r7I2nR9v/9LbV5S378/b" +
                                                                "qDg7/m7ls7sAPswIcO/1Z/cfrs5tTnmUZJ8N+UpT9eny9vu/OOLbd/wN+exw" +
                                                                "Dv2BLlvK4dtLfaWPzoq3n/i4pTbqC8Bepqovzq7fP6znKsvbT/0DJz07/eAY" +
                                                                "JU1UZN9a3v6RL/YzPjadvX7ow7G8D1neftcXu32Y6bTZT33BZt9hrb+r/RO/" +
                                                                "9i/0Qv/lty+de06zpH3f/w+eg37mC4OsLM+mrE+yjwN/9A8pfyb63b/1q19+" +
                                                                "ezs7/64vdP7Y5z/8F//eP/OLP/NX/urHPr/3e/TR4zpLlm8lvxH/2H/x0/Qv" +
                                                                "EN/3vo0fHIe5eneF33bnH6xqfNryzX08vf13f3vG98ZPPmv8K9Z/Evyxv5D9" +
                                                                "nS+//bD49tVkaNfu9KofT4ZurNps4rM+m6IlS8W3H8r6lP7QLr79wFlWqj77" +
                                                                "WKvn+Zwt4ttX2g9VXx0+fD+PKD+neD+iHzjLVZ8Pn5XHaCk/lPfx7dPX1873" +
                                                                "7317+z7n0092eQuAcugyoJrLLM8BZhrGeNgBZkjWLuuX0wGGqc/aFojGFiiq" +
                                                                "BTjDeaoSINujbmxPi55RkmTxEAPzlAD2u+mN06U+ObuN/39Ovr/f2de3L33p" +
                                                                "PPTfnwxpFkfzacFPvYky2jNghKFNs+lbSftrvyW+/c7f+nMfPOqH3qNgPj35" +
                                                                "w5l96fSCn/4ifnzn2F9fKfbv/fvf+msfvfF97KdHurz90Lc3dO7hR9+D6pMT" +
                                                                "pj45Yeo3v7R/Qv9b4r/7wXe+On8Iss+Hnbv/I+1wAtz+9qUvfbiBn/ww+IPH" +
                                                                "nPZuThw5oeJHf8H+56R//ld/7vtOVx23r5wWe+/6jS8GzudwI56l6IyGbyVf" +
                                                                "+5W//b/+xT/zy8PnIbS8feO7Ivu7R75H5s998TSmIcnSE/k+n/4P/Wz0l7/1" +
                                                                "W7/8jS+/o8oPnYC3RKdLnujxM19c47dF6Dc/Q7v3o/h+5e1H8mHqova96TOI" +
                                                                "+uGlnIbt85oPVv6xD+Uf//vn60vn+/96f78783vF++cJafSngfSz346k5e1v" +
                                                                "/a0/+Rt/60/8a3/ku9t+9h//hzZtw9Rk0zfG03JJNUbt/9tZxveG/y+dPzpf" +
                                                                "737/R2DkFxH8D6PgL5yv8WMUvHvQF6z2IXv8k/b45/+b//x/Qj7k1c8Szde+" +
                                                                "IyPZ2fLN7wC398m+9gHGfvxzh3SmLDv7/Xd/1vjX//Tf/ZVf+uCNZ4+f/14L" +
                                                                "fuP9+n4c0XkMw/Qv/9XHf/s3/vvf+K++/LkHL29fHdf4vLEPO//5c6I/+PlS" +
                                                                "J+61J/aeO5m/4fbdkFZ5FcVt9h4N/8fX/lHoL//Pv/b1jy7dnjUfHWR6+8V/" +
                                                                "+ASf1/8e6u2P/bU/+r/9zIdpvpS8593Pj+Pzbh/B/Hd+PjM5TdHrfR/7H/8v" +
                                                                "f9+f+0+jP3+G9gnFc3VkH9D1qx9u76sfPPV3nWn0oy0/eU+yn8xZsk7V8vpE" +
                                                                "ieKs/dDl9yxvP/puzLGNlvco+HAa6Pe6fGhBPgz65MMVfnf5D6u9fWgj3i8/" +
                                                                "t39o++kP9T8yf3eq5N45x+cRHAK/+W/+FP1P/Z2PiPjtCH6f4w98D0T0ou8A" +
                                                                "F/gvdP/Ll3/uq//xl99+IHz7+ge6E/WLF7Xru5+EJ2GZ6U8rlbff8dvafzv5" +
                                                                "+Jhpv/lthPrpL6LHdyz7Rez4HInP8nvv9/IPf4SLD561f+ltfC/80x9G/MEP" +
                                                                "1194v/zihzP68vKOy+80cDknrvoTjD8MW95+4NPQ/8yUP/mpKT9Wf+J/+Hhv" +
                                                                "+6mPsfd+/eanS57u/f3gJ/An8Pt3/nuv/H3vxT/8fqHeL/Rn6/5U3Sbf+AxK" +
                                                                "vJOOnm74jY9rf7aVr3/wxw8+9ZG4/YN96Qv7Oj3ixz4frAwn5/tT/8O/+tf/" +
                                                                "lZ//G6cHSG/f/3y3zmn471hBW99J8Z/4zT/9+37k1//mn/oQwmf8Gv/BP/aX" +
                                                                "bu+zGu8XcXn7fe/btod1SjIlmhf1Q8xl6Wc7/25PNKaqO9Hm+Slpy3711//k" +
                                                                "3//k1379Y1x/ZLY//13k8jvHfGS3H+7yd3y8v3OVP/D/tMqHEdz/+Bd/+T/6" +
                                                                "d375Vz4yv5/47TyN7dfu3/uv/8+//smf/Zv/2ffI9F9ph48J/Ivm/tk3AZ1F" +
                                                                "8rOXDhFZuLl77qHHDd6TeW73bRCTFZQfdFnoVGAlokAoY17MCa4TFcbMh8E4" +
                                                                "9G1mC0jm2NutKDLZ2k3mPrPHXpXobi8PQxcRucWvg9mig8YNEO3wdxy6T/g6" +
                                                                "PdBLGgHItF4BAxrny2O5hhOEEdgDu2Aoab+6yA0CxLo028178IsTPcOqbVGX" +
                                                                "MrRQ8/lHY4IdhLDOknPM08LC9jld7qt+s1cqergzP6wj7O+aXPLGlPoumboT" +
                                                                "isKer4AvNoLu7NXzHr5ZP49HiFiwIrWGlNGv6MEunRZ5CpFMA9aVrbu4D47I" +
                                                                "9SBNDKeJYsjfML7V+kfXQHBEdgHCe0haeQhRVbulezTkLUm38HxSmGwIHeea" +
                                                                "iS2GvRLl+wpdLrDALPPTwfPKv931xjxeVz186Fo03W01fVSTW7Zo+aiX8GlG" +
                                                                "gnt7HMKt99XRapOOb4gH7N1l2H0M/tr4IZxVtg1mUGrzfti6MxYk/gh1lTTY" +
                                                                "cgTZceo+QIJTF6lFp4a9zmFMZ1z4HFkVa6AhjSHy6GolkB5stLaBZt2oaE3R" +
                                                                "283PnY3za/lI8wR6IJdNkqGLJ0NI+Rqs2ENrtotO28zQNkZQFGOEp6b9yBTE" +
                                                                "Y7vrXead9in9oRMahD1gExlHRWktPMw49fWYvAbiX0vXgK0365oRi8tp6QrV" +
                                                                "LKOYR1OiwV0J7xZsNsi+eGyBoG7U7Gx3rexsXCo0O7TiqancCAe65qkioT/u" +
                                                                "lYda9RGSimxGZXFj6tVCEvphjaM3VjfGs5jZhDQsUcF2mLvuNXiRNF7nYMp4" +
                                                                "twIjWqmACNMd0xIsmOotafJ0F5hkgrhv9uW11lUhPmRxpG4rEriLagNgqwQN" +
                                                                "ERqY62SrPV+1y56kQ9xSNR3RQM25kcT5D896eoyivVovmXnII9LYQabieatm" +
                                                                "aTXaCutS4ADZC9AxYQfqhnG7z3oSl72ZQ7IWxd4ex16KtJCp3cVg00hrSEG5" +
                                                                "nIWrN6PWRX8O9ur4hKFSgAn7g61w+cTt8Jr7cXxhnq7zJKSxLmV9tMTlSF76" +
                                                                "Oqf2kbWoOcPJQMceI9iW7o4C1PPTzRgSKZEu3i1z5PqCDwq7P+DxaHmSdlDP" +
                                                                "ydSdhdQqIikOCiegfiWRC9RzCnlxgRMRaPZECue3siqlwDIo1PFaf7UVb+Ji" +
                                                                "fn493X2O5e700bXGPb9uDs5fRq9thbBChwYynSoZB/38ymnJYw21OnfyqGfR" +
                                                                "rY6lC+sZkwv4Huy+5qTih+cQhR7p40oKZ3Wc0CGO+BdhVzFRqx9mBBnj2PKV" +
                                                                "NXUt7wQU1KXVRBmslioKN0L7q71i6BJxbMrYN0nJq5AzX6YRhEGKGEqt68BF" +
                                                                "uKc0sUWDjUkep2J8Ye5nVixcyN3OkKZ6g0ufSyQ/ymePiB3M3q6VdOiTmg29" +
                                                                "5CnquaeHUd5eIDlPw83d8hCgDDI/Hrxbc1cUIwBTKQs7eETxnZJ6HtWCkpuJ" +
                                                                "NIwfdz0rx+B28MBhQnn+6IYNC9E2pV/XRjjmq8tj6kJHcBc10UNKmptnA7Un" +
                                                                "I3LPFfY9yuoLijUBVAqn/wYCTSCrvEd7t7GPAmrZEuwIGO3VaVkRAzB6L8tb" +
                                                                "R8HXKFBnvMkzENoEUGta37PV2Kd03z4untCCZj6lIwgd8o3tUjGM96lmuCMR" +
                                                                "qU7YA0iFWvB102RrTrPgVVsCi1BSCw597LrdkgS3AosDEp0yhB6pYZcpTIUN" +
                                                                "/1pd0AQE8tqhJqDkFFKgnjdqd4Az8F049iXL8pxweyLLctiXVZ+YPWRMMZ1T" +
                                                                "U9oPZpD3Kh98bWD34XrACPZAEIR4wutyA8UB1KYXAk28DonPiMlhCjCeMXAH" +
                                                                "CkHa2P7EiXIYhsRT+nJwzHbJsyq+P43noSh+m07S6L8KInuAQfSI6MgrSPQS" +
                                                                "JTKrZoZQh+DGtEUaFw9GTxI2ISjVW9qyuhraIwjR4NijHqLLF/DqMViohecO" +
                                                                "uke6ukULl9U8DOg+rptw5MotfD2Hu/NcOpaJ6+omFDEZ4NcOSVAgAf0rlr1c" +
                                                                "506W8HLFNzhR1wO0lgCA84tezoB7cVee8otBYpPiJueF7rFYOjQyee37hr+8" +
                                                                "NDINniGAi8n8HPoDlZMVej4VMbyPvmMAxTP2ZD9m64VUUcw8LErSsom8glpU" +
                                                                "X6/VfQgAqHFYq13p8Xq6Ylvcrryz+NctgPjbkwXxaZl7vnePWE/VlX3UUWHA" +
                                                                "ZOayTy10xdc2paomtZrKG5XRmda9fl20EKZQnoM00YhQBq65/EGZ7pl3RA+a" +
                                                                "g+Ye315N2V7BRQJYyDbSrgjQPmj7icIY7KhUXXo1C11zzmw6hYyuWmcgR4wS" +
                                                                "MQ4g0o5qDOupVuOXVXMt7tmLLzDiuqhZ6oF4r5lhHBH12vcSe94NZBE43owE" +
                                                                "FlNAeNwuethmW/+EOnRAWg8H8aiknl5WX1MDQtTbBcfWKTd6sxG2Sd6oqHNW" +
                                                                "tdaXmhW51gElIXiJ9mMSFyLL1GzJowE/8DquMkiqWgHsGtiY7xVmlyx4xn7x" +
                                                                "JNWlkO0+WU+OcTIpfrpg7PJE+r54huMIczN7dfUhFJ/+Kx7F2GQ630yg4hrJ" +
                                                                "VtSBpntNccXJJnTLbnr1osE7Ze8+ux55+qzvr0tYP+Mm8plcjeWxy91EcCiZ" +
                                                                "T9ruyimwZS1gVTwM90LhAH7p9SlEgC7mvJOERDqOe3hOEBeMoHRwKJF8fOBX" +
                                                                "4gE2qHyfmy54ZMyrWodeQJ8u0HCmyj+0OUb5+GJipSGxytN2fFkX9KTdrGZy" +
                                                                "3BmmJfEWr13eXnUiz13mGZOLyVb64rAdZGkxPez4uFz6hTCf4KWYr9ltFZ+h" +
                                                                "cUUt57H1HqqRhuLHcrndmmtvXLERjJoNu9+gKgx7LWSYeYGobRZEnE0Y1+dQ" +
                                                                "yTdmDZekhVuTvUpyGMRTQNcYc1n5W7YMYgrde+o2P+5XvCYAAFa45U5ckap7" +
                                                                "QZwyzmYLLeFgJlLYXu89bkhH1tmCsXOALjBx8fL82C5ztDE1pHqyvE0cyq5t" +
                                                                "p5PgvbMX2bqLjmA5GRxFM9Q8HMtptvtN3ovGKR0qd7eezBaGuqakSDi8ABS7" +
                                                                "2cISwGwChsJFujzsGxeg6CP0OLPkGuK2jY8HwVcCNM02jSwiUNwH1xsPI6xd" +
                                                                "AJvDaGfFqNFyZbzCKIbnNXzFjTie+RTeFVyhal7bteEkd7XQK8jqPcNuNgCq" +
                                                                "VkCIkjMZZ/gb4N8FlUEcSzXAetCzfOVIBOoN1GGW+uaVu5Jw0AY/usseg92+" +
                                                                "isSDzBE3UVvLZsG1MttNTwGcDXi6YTe9r8x9eI29ec76aKCTnl3dmrr4496L" +
                                                                "WTKmggqCgRWG9YNsd847Gj2AXry0d4tL6qci1BbffikGOhj4lmGcG78eBvRI" +
                                                                "vUeWI9Gtv58az8MVYkzqFdukcfKO8hFqpzRCk+gxzG1jT7qGrlwAqCzTBkiO" +
                                                                "va6z9ExKxaPPLBrwe9isUCEIGMaiLfSMqKqAgwlUXIq8X9MsyxMZ04WWb/qW" +
                                                                "iHtR0QnAmzvhDjMpFE0nizkxt8P5G1gZryc1yOOYZcyqcFzpcLJdQbVLsGzY" +
                                                                "VtNtNGKo3vD0iqeSdNnqrm2yK81MoShdm7tmOCtn5LqT3CFiWpDMuD2pQ2Lq" +
                                                                "Ja6RNTwFWGef6VIfIdXGg5ZD05fvWAN8a4U+HAASdE/OaR1PA7iL1m3pUg4v" +
                                                                "ddVUPHers+fT05Ar9AL9lN/AOinxeqPjuJe7rRIYYqOX/JkdGBE8oeuAut26" +
                                                                "8EfEj7CcKIoYdOv9juntqcqYu3O9qGJKMd3duOsT3ROlTi/WtDUEYFiFPg/Y" +
                                                                "cpvo8LriWVpnKcFz9KDYorPSCIhRldavIacX3oiEPFJqnli+no6GrcTT0HFj" +
                                                                "3S/NTHc1IRkdvYZDajhHulnZvVdk5izQREOqbsSV4sPEH+YNe5GImZPgA1Rp" +
                                                                "+07LaSzs6WEBuHWz8+XxtLSqqFlt0eRmqewwMkVAaV18jyk/DdlToyh5F/Vt" +
                                                                "lUyyyR6Q8Wp9kalfgx/EmjBEmGj4vcYxFxyJaB+0INmcLplKXa/kQR7goIHt" +
                                                                "w9SIJKBzrjmVCCORIB978OHfn3w7+OJxP8F8HF7LOnDdC8AGX3HCEqWTO2ss" +
                                                                "vu/vztAkN35X7ZeQdQLFTwaMO8fUWFAfpzb+eo182shG2I/h5SR/O6heKv6V" +
                                                                "zMjtxC+X4bnUG8/7bnSLoUIsanA7w8ozjazstm81d0KHcbnd0rJ+6OTdU0cI" +
                                                                "O7qOPk6eozb31VCD1zWVsH42Ony1n2mU0WcYvqDRkCaaw4vBGPunZxq7fykz" +
                                                                "rr5nbsYIdbW/9prqu8N7ZjNpE3vEJK0xo8m8rk6ooXg2Pwl5ISVHcdVUfV7t" +
                                                                "I7FP/DG8O4YQA4b7l233E796nJQ3MwEqaDz3pMa+oTz5U2hzh0RJfHKFr4OJ" +
                                                                "T3mxxNikR7Nc7JOekQAG3aJ0b1+y1sImEd3Szr/qDibt8OUqdjlxW7KDzJLm" +
                                                                "5PEQFy3kCtOPeYKQfjhUBTu0dXsRwkK9JJZ51mJ3fR3Jw+5bfF52mIqfEQRq" +
                                                                "mxuSs0HWjoKyKL1rkb3NrFmOT9C+PWupY85cVLcX/pRU150tJ7q+2dLq4E5m" +
                                                                "4z0Wg2oHOLcjbZgzvfN3WHyliDrYuD/cLlm1Py/rSlbT7GXpcPqKHgfYZY6P" +
                                                                "KwIr1Ch7kGmvo83dOYSZEtXzrAcMCo88tRAXdONH8FyQ+3NKBuv2xIJO4ZTh" +
                                                                "WvFP7oYp4+suPvhSsnFhG+Y1k0bkkQPdSN4SwtYrzz50T1Dd3Bgf6NTpad64" +
                                                                "A7jIwchUbQxufMW2Bzkzjwbpqat46l/ZNO8b4RIw0+dQidh5mQmhbr/wTLPM" +
                                                                "pF9OkXBbHUTKH8jtxgTPyW3boyWPDd1PqXeBbv1lWYTbZFyCViAT5W4YEmIK" +
                                                                "k4zRKMM4l9PZaqlECWIsagufbrMtUB4KE4DFDkaTC4BVuDhMmsWYrmq/6YrI" +
                                                                "TiWksQk9UZ0vNYVOTpNBKQR4NHGAHkq3bBCElKYVVw+aRR9ENYLxbVhrnUkp" +
                                                                "oxZVEi1qOjV7qmwyock3nz0wCX3CD67xsQDcyK4G77U0551xbbC0MvWI5xbL" +
                                                                "2XCcLBP3rgb7KzxO6u0tHrJq2g0R0OxRXRF/vI9unvhrVGM+vEbXRFkuETyi" +
                                                                "x6VXepyAOTOkBC/aRHN73a+T7V7LFM3kO/3s339Yt5qXL22C0ZejhReShc/9" +
                                                                "LYBptMwUFo0nwYZewhLYNRYNEqn28t2OkKZ31T5vaMHfRGSK96V8gZg1ysWF" +
                                                                "5HfBt4ZXm/AGcNJtfC22tHAchF5k4IW9NIFGreN12wd+tYqhAwQz61Y7qWtc" +
                                                                "gdMurZ+UDXoHeq00ukiuXlwn0XgJe0mmb6nYkZtwLd2aviavfFd6h6R7yDyj" +
                                                                "g7BhzgCZHEQvKNJM26tSiMK/FOVxC0iqMERww2nt6vYMsAn2hJNXRa+IeIQu" +
                                                                "PIs3GM1fEEchiDIxbFm884GftxjNwtWpPmkbqZCxcEAtGLQ745lCcXA8XWIZ" +
                                                                "8XjFmwk/YAOqE3Nq8ZNsICAKJT4+9fzrctOvzw3muSCDKDy1LGzfTOoK5bp+" +
                                                                "be/NlRkTi1omvF6Zfvcmd+d2ntfJxqJqwMUszonDEBJqdE3mEfE3T6E3Ug5W" +
                                                                "qScb9kLMrI6BLpInfSKIMGqVesLS+zbirZhtDpmp1VbbGaoWVekgd2ndx6al" +
                                                                "BDIdHtgUG6xyxNJyplRvmnimvGrKHVFV");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("Pt3NcRuChKw3X1W32gE3M1c8mbRhkO7l29gHW8LmRXi51pIRPrUsYosM1hwO" +
                                                                  "XmXjgg+jgHdoZOmnhHJ76uiRIDWvWMD72H7BnRo+kNEuXw2ro1NVjIrvyVwQ" +
                                                                  "QibENZfSLjLaePCPFyL7BaxyuYVIUXcrecW9erVe8HrrBjHkbdks3zuPtV+l" +
                                                                  "FKosxzjSU5ViNJcfd0en+TZQqIDCQwjd+Ob2mgynTmrShed0YrqJFiN3eEo3" +
                                                                  "bKed7P4U707QAu4piiL3hJ8kwcxH7rc3TeCj0gk222WPF+Lv4dGjvqwyzLVY" +
                                                                  "x7gIXR/ryGVRuohw0dJzsnKV7tWrkkJaIQ/hjnBqUJ/M8aSE2suL+NK6D4p/" +
                                                                  "3T33kmlxkl77jGryVm/Swl/7jWx4BUSyWEQLD59UWZOeiCBz4N1qUCh6OaW8" +
                                                                  "XimPuINNQ88KHKOkwflPSlvdzSY1NaeZqzWS9zyajFNybh0dMKsUyIh+8r50" +
                                                                  "kookVMh72kKuuIFKnj4MYdY4IaWTCEau6XVdFI5oHTy4tCeBUtN8QFY2eCBx" +
                                                                  "TdL80+J0EcvjmqVnkPXnQjlyH6BVUpsmB6fiOYuPDto7SqRGXeUpK5wMHD66" +
                                                                  "5AXcDp7jSZ1X5sERfXbfqgbe6qtfTSDGNYgq7vCLfic/MEvgNDXfng/pWIK6" +
                                                                  "qFxXviBdPFPE0e0cKRCQceP92OVuj4wNSDyGtJvmVVReRkqt0ptiG6iwcPST" +
                                                                  "MbSCv0jqTcRptUlhFb6iPv2kYqVl2LqREJcubv0p2OdRZ3ojvRK04K1RS0es" +
                                                                  "S4squJzOS9CgIbrC1cnUTR6SaqIkV/IqMzm5qtOgXEndX9kGlzfrqeWdT0Ed" +
                                                                  "xcuch23yvUW2WqYkKIum6BpERMs0oU4yKlIDIDsniaoJxQO6FeWEczLSmBy+" +
                                                                  "bDabGwoAzzL3HLRqkj2wzScevu5OQWLJuJHp4pjsBc3xurxKOukyO75Tt8mt" +
                                                                  "crQAOrVwScc4pUV7SRyAQ05O5QrEqyNFZhU258lpwotUHwa9iauL0kam6d19" +
                                                                  "Vxb6NSLmvhxMZF4KZMUwHto8Hjgz8gWHrxkgEcOd0y4Y5SmFcscfV4FJyKrb" +
                                                                  "zeY1F4wy3O9kQWVwS8sT1y1suZqWXfBNurkvK6ytQKs0AIfctUDJ5qSfD+DF" +
                                                                  "vuJFF0hpKcrTBw4Qo5tDlXbcZbXdPQ+EzbYapVQtMMcOZKfXieqUCwfmxSmZ" +
                                                                  "IQeZcAd4L32oxCW2tAdSzNQmdq9emyA3fuYcvZAT6pK8xnvWLKGsyhWNKYOK" +
                                                                  "6aA7MZ8kvTdww9hUQmvP65kK4pV6FLC7tbZCWojS+q7SJdZEsddyEXFuEB/0" +
                                                                  "VuvcY+q4bU13fjrholIOJ8F2Vqoov+0QSGoU0nzy16DgDDGPUXMF2nYpWeXC" +
                                                                  "3iDzxdwMzgny9QbTT+3BnCmYA9j51NxFyDEqNAard4+OmTObnVxfViPUtmpJ" +
                                                                  "FU6TPRhKvPlg4HYrLl60O5G6tWwhqfHOu8V6ufWJJHeQLq6NRxf6lSnahVso" +
                                                                  "UwpMPNbPOazB0wZY3zsrcXnntDRYi8wVJ0/nj1G5Nk2gnYxkU2LLzwxAfKbo" +
                                                                  "Sd65pOnTl+a/zMZri9lrmIdfrHop04Ss8u0eTpYft6w50OPp+Ezjh2A9wyrI" +
                                                                  "Gg1LvlOIoULhkSAVUiKeu01wvepAplUXswQ89iDwgErM9rDg2LzB0CpjO2ub" +
                                                                  "8ERajwOxxRKTZ4A1XrETVTGNYuyggE86Q5/eQMXJ0LGkpgH0qY5HknrOZLvN" +
                                                                  "+ybB+aEK5A1IsOccXpieX4m14IejtSnO7OhRvpanigqH132hMQuUzPBe0xS6" +
                                                                  "eCr4ZIfHAfe3IhI6+1WBNlstAdhU0fyIApus9YMP/YUiV26fldhJ63aHlt2V" +
                                                                  "EBoXTKd1QiMtd3yG33NMM6cMIzQBW8PGNcfcM2fb/ByPvaY4qWxNEdIG4CKp" +
                                                                  "eBGlMl/AFNYn1lMhgh2gTsrGmoHwyFBd4/QhuMcBXspxOtAaTTgLU4F0E20z" +
                                                                  "o0rWA+uOTDtmrws4+lY3cccXCFU8EECLQa0exlOeJjdG3zSNIQeh95VypvJe" +
                                                                  "V5pkeVVizNGtV1BhTPcoX4hwu5A3o6yOrCbuslhugvna0uH8XjDd3u527jit" +
                                                                  "hINUqQEGxtL6UWY8VoxhTql5PVhQcibBKUj1jkVdcAZ77GLwTLDk3ENmajR0" +
                                                                  "yquha4HtUP7U2TdCIzUlH4uKBI+ezU9OEk7XETy/gCNdmqmIJu0ltD2G2O/U" +
                                                                  "AkM04GWXsYCNOBMYPGUyY1CeXPUkhWNjaYMYEJQZhc0ElAYji4DSCuPyKl3s" +
                                                                  "5eKTg9gNp58oT9+xy57PKmcsifk4lb3tiYOlq7RDeC2H1Xp2XNuk2FsNK1nI" +
                                                                  "uVI3ziij6eSZ1141xVXkq9yrH6zTidzDW6G2kkB/mC7jXAcnizbZbGbXybT0" +
                                                                  "zcO6iXNIRafJ++aqBNlPQFywvN5vPGnIx+BWtaRhPl7z+oJT7kWl+ucmSVeQ" +
                                                                  "kdDAutXqSxJ5B2NuqWnduayJyO5Mg4x3IugW0+Hp9/wcKPjMN8OVuZHOMFUm" +
                                                                  "6oGOUmAJebRdUrzSziMKcC2zuHTZnY4QVbkX8kYaZOEbp4MUD4LTKaBE/dN1" +
                                                                  "ozNbn0lwvElOd4qyq27tY5nVi4vJvsh5JA8CYhrkE/2yBfdetlSOc12qvAQ+" +
                                                                  "4Y7YHU5qOyrzyeTD2hHCJnYInR6fh4c+2jNXX6f3HF6qlgWS8z6QenMQkcCM" +
                                                                  "wxG5T+t4fxTgn/30WYif/PAIx7efwv34CMR7m/K9nh346f8dobVKWPnglG3z" +
                                                                  "bA6nwTWqk4nQGj/KL7fhe5iyXGwPmT5BTMRNtwqEh3FPEhM8k+7O355tQT2m" +
                                                                  "nnVFZrwPIl6VVoHQMQle3OC1RvHaP9FxdObuxBRPu0KDVPfCmVSiBmxthnUV" +
                                                                  "qs2P+jjT/qkQrJ3BLIgxQy+mg2GiLNEwI7PFdMsocQGQd7t1rgyDjNHmBY/y" +
                                                                  "Ui/eRUq9U1qfnJAeHWUb+9KkZa3uljZuiWNlnYeGlv6jd+l2KusoUgliW7nm" +
                                                                  "4dLAZNxNrSZmQR0n2z8ZYIRMvM51PU8n014X1jEP4rgjQvi4atb4rNV0CqPn" +
                                                                  "U3EOCIyuBBcAPW9OpLUuxp2L4iP26sC7t4aMbFVWVaMMnmK8vb46wiHdUH6o" +
                                                                  "3SM6Qjksxxrs0hfE+cM1bizrqFNcNpPKFnovypduOHUXWl7vAkEce46eLJYo" +
                                                                  "K5y59vwdd4kJPi4km96KUMNyYKd7JQfUJm5rV4261auNw99lKZr9vGKWueCR" +
                                                                  "0EuyF+vTU4FZ2l3xx1Y4KWDQ5ggCuBaWbxDOLJedjTQso/d38RcK4QbAPpCb" +
                                                                  "p44E1sgmvJ6Wd5NogOqmYB7FzEr1oMRaSJYM9bYDe/TOOBnYcJ4Ima+xyiu1" +
                                                                  "+AjEqFcICOVVjpJS8zIoq0oiNSVOlGc6ocqcBEiZOy4VTlqITPjNMG93toRE" +
                                                                  "B2Ux3bT2wV83+8liIjcNcZXB+kgSboJG9wJMY0OHVPCSavQGFCHZMWlTV9Rk" +
                                                                  "39PBlD3/hfkvvySfVwb0egMURPLyeIpaD6BKi1z2GJmZEfBRnnruFxuglaZs" +
                                                                  "AlFZ8+SiRowg6sKVvMaqrxhDLZLXOt4TkCWLW7Git4DJjeuhDHmQkrRh0Jdw" +
                                                                  "JecYWRnvaOgUk5cXN2IKc1uS+1WJ2Qc/iPAqshN2i60RTE/CEbRxPmxrbUEz" +
                                                                  "fw17G4I9GtamFW9OTd2X+z27XrJrrpK3vcSoNlmZTkpcue2gQ3Pvmh2ZWtSG" +
                                                                  "taGygxeWieyj5WahzVLn9GGz5BKrJxQBApslS7c9kruhbUNnSputpmcZv0qT" +
                                                                  "YhlXNUGuWqJRz8Wa78yqdnehJyxIhQNtFvbwQiCWM0slEkS3WoPSPXyoW8WR" +
                                                                  "+JQcJz92ZQe6U/rrAE9ZLeIMrDFTEeiE7Er78rQ2BVBfCSmwL2+iJbwKdCZv" +
                                                                  "RF6Y5wDjuQmWUKnNiXa9kquPp0TvPyOKWJLJ3DVWrnFEO4FjMCpEqXrAmF6v" +
                                                                  "vYcwXrSk0KRtZGA1Zw2Mxrmqr5FwBD2GmrancJYM7SM57UlaD6jVFJd8DvW6" +
                                                                  "yXYekMn91Y+awV1kXdRx0eNLUJfoIDt1Up8hmiek/TiYm5QWreIeRZkh4pgg" +
                                                                  "zwdxH+HoFRX5CM1Wzve5ccrwBAZKESB3SrwZuV9Ocs5nen+7w7rrYnm5cxF7" +
                                                                  "4E5xN0qhj5k1GCF0SKT76pU6V5U6AZ96tKVni0MTkdJ2Uk/zExea4wiqAI6t" +
                                                                  "aCApoeGnpGTj/cJdCqBYxWeheIF0leCeoTAdd569HORjb7kbEEbFYizKs+9l" +
                                                                  "lCooMQvG7m6+XlUlMICK8q2U4kYvDB1wuWFdnALUk2BJJjdhud5o2XlR8v2l" +
                                                                  "DYl9sX2f56r7ScRA1KvqaBJhqEtDu/bN1nXuYYVee1svzSKUZG1R+7WvJbSk" +
                                                                  "txuiPsbqeaTr5gB3TimdOLV0WV1Ql/VZLqdFBn/PMykWeEgxwCBs4UwtmUqJ" +
                                                                  "ws9lIskH2hb9QFTmePJKYbhVS8srzw3znnNUjJEwYH6wxIRTuutyksNxVmya" +
                                                                  "G5C+20zjqDMbVVnuKA5m8FTbEKqXdbUOuXQOjXZFjWe96bIhw03OACs06KV9" +
                                                                  "PhAwevgPE11cdsyg3i67iCqX9IY7d/W2G6xisbHzspnHNRkz2x3RO8bs+m27" +
                                                                  "GTE/qvJq8AilE4tFxA49yVZwvfohq0bXMfC6M1vAOEaZOpqcIQKa96qWzQLm" +
                                                                  "3c0yYLCLVP6EMxvdGx7CwApK3gFCrRLJdGMbvshNjTescfQPdBC3fLaf+ylJ" +
                                                                  "hVY6ib3WU8+7KF6PTBTUwaoMFLu6nUxq94GZ710A1orBopICE7JNooI4OE8R" +
                                                                  "1qhCLmqCEa+Cd0OfN0hVwxOYNN6S4Ne88dp1DWGfsQ914z08naRbsO6+QYQu" +
                                                                  "OsI+jSHOK+6kU/ovM2Td/M65I7mYVzYuR6kgn1IpfRhinCZls/rKM7X6lx35" +
                                                                  "L9R/Ca8Y1Z+LLk9JHOpEsyeZM1o3y6BC6kXcBKQtr9A+37iMVnAMR+JyO2aX" +
                                                                  "T3EbaIszxpWITpESl7RSVfIiNmF6pXqUIUFWv0BjdkXEI1F4mhAYARxkO723" +
                                                                  "cVo+Q+9FbiZp+7ee5WGryl5J11C7xvVBUb1wnAs18dmwrQKneRGg4mIQ2npl" +
                                                                  "lv6OasYia/RwRzZt5vunCAYGj2+hB7GGDkaEUFu6UXScoRe6ikgoLSyUoQPZ" +
                                                                  "7JV01YfNlYNLa60pGnzyUyqUTzGF1JGIIy8FiMtDPHlokTgqsJO35wSHl8d8" +
                                                                  "uLCozksy4vEkN4/ipallRQi4R5EAAsq0BaBmLF/BB0chRd7Fp8DQ+ulCm8Du" +
                                                                  "hB1N2aWDlZIGdKoh5wWvPrZIn9m5AIBLD8UmWgNbFzgnv93QYceBV4+nc3Jn" +
                                                                  "Jkp8mQpv5mK4G7Y1iIpBb6y874z2uu6PE5rKwKLnwWe3AfXFNRFDJ/JbPHLI" +
                                                                  "ik/jyPbW+5xnr8G5jxbpz7wwkp0fT42D6W054FTv38TDHZsNM4Ielu/gti6T" +
                                                                  "iMF5eVlgmAN6rrmP+EXf+tf99f53gtZsPaBxNiviHxpD8aAI8T0Z0ph8ku9C" +
                                                                  "4dnYJOSscl+lOdUKJROSQ/dX+mE/7vfr2OunU8Dg2JVJl3WoR+khOJw6no3Q" +
                                                                  "/aqW9OM8Gkt17MR5IG2rU21JFK8+U5SFoROR2CoabBLTLvtLaUdYWFeImmw9" +
                                                                  "i5uszewR40LZOwaggYPRWxs9a6TlppqpY5d14F2AHsn0YiOddzGrdlr/0aAL" +
                                                                  "OvHQMoNZpduxPrWd7ur1IFL3oVGcSk7g+boXvpqYqVCbOmdp2OgfvSwwh9aK" +
                                                                  "Ea+MhyfYleXsZc28lCpW6HzLrIkCj6NhIjvd4/a0VYKX6tgyYr5pSsLn4b3I" +
                                                                  "537FQIfR90as4eUpkaUSRJULgsszCCR9cEkwqh6vqnzu7i41y2yBodvHciqG" +
                                                                  "p9ZSw64bO71R+0VY+v2BQtRtsZyl10HDgm+IVC2jisdE2oA8PMfY1kVCTx4z" +
                                                                  "eK1K14aaq3e/bEcVNwJVOR7CyJnJD8rmm3hdSz18cZ5XiT85gRuf4H0hQTXL" +
                                                                  "TO0a6pkawpWGSrkR18naM3o2xC5QPwKK808S3049HiJ6caYr0bEEE02lW3QJ" +
                                                                  "HkpiL/JW22h4aTiVw3wNli453Jk478PrzpU473WOsy940KEV3p88Kq2dJULE" +
                                                                  "Fx5w7MsGWQUxNzyCGrjimkNxxtfmx89HnL6odPaf9UnuazltgweqFFo1I9ne" +
                                                                  "bzXH5FNQibIQ7Fd9N1Zxs4A+mPdq9xvr9Oh8PBE039VL0sdPFcY6ws4eKMts" +
                                                                  "8a3OhBvOPwtCt3qUlpWGCmQfugrSRt0a8nJ7zH4VXd5/80wBJx5eaGecYvvU" +
                                                                  "gGS6rLKOw9rzIQxCGXBXGAczoiCfFxOl1iuvQEHg3kph2jPFDq/Dfdli9e5t" +
                                                                  "Io8yW3UBUZQH+kMXym24hFVxDec2PCUsxICo2kUREeq07wB5vNp4Z9yfZ9of" +
                                                                  "C1JXlaetLgcPkLM3rWOV1FZPY9rsXUPSLAEEwHxBvYfgA1BmM0BCLHhJQACE" +
                                                                  "WUW6VQE6DXGBhkNYDnWNKYvj+mGtn8wc3Zwj7TBQmrEzjXZGBp+b02mcL1Ni" +
                                                                  "CR8ByZQUhyHESfJQpWIhSM0Z9sxjzqTrqRFkl5SVrcgcPb0siCmQGApow90W" +
                                                                  "JUXr3HmfZQ3ZmdvOj47kndJfgJVu6c29k2aah/FO9YKAD/ItzuMu6rbj2oyq" +
                                                                  "i7yQY/ODC1Hco0cUsJgy36n6XIILntqGuih8r2c82yJh9yqrNpvKMxkaiSn6" +
                                                                  "cSp3MkndYnbXfYivEgREvrhxDp3YiiDu2rLnplgps7dh+LDuhWJbH+dnavaR" +
                                                                  "yJb3PBN1oPCZNIjuWW+L8ehdVCa4bM09Qlcg25VbXHEPx1lJYEROrjTIDv3A" +
                                                                  "KcvXuneo5XyqRJmXECy5Q884+OQYXN4QT6lwfns+G2zAfW7Q74cFUZhyC+nW" +
                                                                  "jOhxG70xEKwsJGSAdkz1MREkK8Q6AtEUU2uPQAZvULheIIdvcM5VL0/ndi3d" +
                                                                  "AYQqWKKsAGtVEsYC92Je2kyQ9kZaMvFxN3Wax/yQNtoXV3dupy9AfYMHg+m5" +
                                                                  "UTsOw3BMqYBmyTnFwlHIT1/TpjRMywUD1qrKQ4272HJJrDqbi4cm2TVtb86r" +
                                                                  "Xd9/FUBAfCs7SWBPdNbvTVPYcBEkKbALJrA568wb6onUGFLbljImDHHKoVhC" +
                                                                  "bECC69MsCmkEu+JgF/QlXeYX6JgOpGNT8FrrNmjenymvBi8q2ztJ4yFBw6BP" +
                                                                  "uy49peLik7hRPERrzrqm9PKiTZuMG1u6Qu2KegA1fb0nh73imu2jeuk9I2G+" +
                                                                  "W/fdDzZRikZjhVmbOkkf6LZFaUzi1gt0YhLMC2m20uur7hR9lTQhNZ3qLSW7" +
                                                                  "iPeU4W3SFu9RSmYNMqCBJaACGTeqi10VaM0G6RWV6iBah4h9oq7o4KFsYJAh" +
                                                                  "KvFMYo7PRAS6i0VORyDN1OHnF1tfRXEE0A2KVODOCzD1qu2jnfuy9pzbGm3M" +
                                                                  "rL7ihrgWWq+2mwmwS5uEFuLvUCUQ5tUNnVM4vP+G41tlWjlGfOHK6nZzw9DU" +
                                                                  "dbRhbdXAAjmRCwyWXxnkCRyybn2VlY2BmzKe+xm4xd0N3oluejIVz6POuZIU" +
                                                                  "u3aDBMIFVSMARjXqplTPm7wBTsHs+IXKVQmLrlQc7FFHwPzkIhWmwRQEum6f" +
                                                                  "U5OqDuBwqksYrte9rNKsWQmIQsyYcTbSC8naZLgqEPiijIQ6vV84RrgcliFl" +
                                                                  "ymWl7qKaxFjrMTdueAhuRgkROwrFjqKzu9wLQG40za4gEW2E3hsuqVgysW92" +
                                                                  "aFZC1QDOhe1saaa1lWEm1a1ocmiipBkM/b6tWvvwy96YWjLmX0sftsp4aa3p" +
                                                                  "muzuyVWuPtR5rzNHWrL46pqrq80+ZE94n8j3iO8gXSKsOXRMq8Z66JFPckyH" +
                                                                  "vu1jXbHAbjxduuZMwlmxWYO9bDpZGusjBCTeq+GOJatM7imFrw0xeOXtficW" +
                                                                  "4yqn6x2U3CSfJXfuenMg76oucq80rSZbNC7FpQe27SZGkbk9lxfFHD53Bxnp" +
                                                                  "6R+Z0g+KzF2p+sJGLsyKgFlGLYwxQZJzeVLCuYqdHIMaYI9Qk2lqndJvHBxv" +
                                                                  "m3vlEc8ducXTSYe9Sqvh2xHHwvpoIJgdEuMlGoTqispk2FEwOw6zrVH/VGDz" +
                                                                  "Uh77IgBdAm1uIBiAteaPcIwfa1K99Mjmto52r3DCWvjLYq84C+ZAq0+vSA4f" +
                                                                  "JlK3RPo6UkcG/MAEYzkYDFuMnmh0YetB");
    public static final java.lang.String jlc$ClassType$fabil$2 = ("1y+y4Gc5fGcv1jQAKgwI9fPk8FQEM2bPXGCn1PANP4ZkYDioQ2OgbD0COzln" +
                                                                  "bj5ZReYZxXafG3cOKDv1Kpiv7ra5zsFjNAxDRIxmkFldsJuH9fTgiYR0qSza" +
                                                                  "V/MQEnOFEpFXdqNnVQjbOeI4rWi2OuU3MyeujOoqMLKhiKFDd7G/YaKDnR4g" +
                                                                  "VCyLuk8u6mSbLf3SBV8z1SngzOx1HlhIDWbNzMoSQWvCMXqyeOL4+NQ6RgfY" +
                                                                  "KyQuwt6xLt1I6szcGJC+0EOLPisZVdMl0XI/ZphqOSPlDi1mMNupWVbtAfe9" +
                                                                  "vq8qLAWCOJk3kX86FesV5dMltgiINg3ze/MCw3EPqqYz+ALRx9zVAKUgrsxt" +
                                                                  "caQmvFrU9Q5Q18djKqd2h2bXyC4+DWhrd0OAwsvxu13lvOlUbskOvk+uu8oW" +
                                                                  "unyRmoc0ybxdr1CwWalTWvlJOaA6l6xarHaCd/UEumz+vaYH3rOqERYloLoE" +
                                                                  "HAHy8VrbBFL0972+YYLPJD4rr6EeLJcFQRAB4RObRV8LPMxH9nyZFgtUGPei" +
                                                                  "3QIbt4VljQjKgE3WOW0G0Zrub9KZvU5+5pjpNabUqz4BwThpJGg/BJQNI/Jq" +
                                                                  "Qtkds+4JDhv3tqPNa/3qSjrdzyTrCD7oYpfdNCaPT0ITEC3jEtBzDeeBvDK1" +
                                                                  "/Hw1CC/UBqUljUC+dMIMQkzp7Dg+1Y5HDT5Mk0O6W0uAVBZwmVVf9ZBJqDkO" +
                                                                  "lZDjTovdahTQUsTntFZJovctRyGFTdFgP3UjS5np4a7pzRPDrDUHahX2QNNa" +
                                                                  "O2+eGOdekJJIb+1lEhRxJyd20BiQewYCAI1219MhC6gauRFiOI4eqo8P89p4" +
                                                                  "EJ/vVaMZD3DBlGUFCQCRhSVyxG2Qx3IZ+iFllxc2E7pkZby4s0awFVTjLrct" +
                                                                  "oWuoduQD4DCF6ceb7XSLpHEnWneJHJo4rm+MeiASsgucagM0T4SBHDgNhvfe" +
                                                                  "+PDm50kLetqIvXGB1Oet1W5DGVJ6DFT7HGLsdu/NdrpifEXcPXPk+Kz2F6Qz" +
                                                                  "di54BJd5T0LIrZTJkdLi8JBuLXnCRnEPiabl4UXFcMP8zDRfeAU2eN0b3DMj" +
                                                                  "6VkGQMjbyGggnRqs1IHwvL3NwOaQLf2UaSWHIhX82jwd7iSH0kpaWPGnRjiu" +
                                                                  "tUC2vCJ0qr6G4u50NTk6s+xXdiJ6SzMwL8Cl7zgcMs/yIpMz/bgZ2vNMJg5s" +
                                                                  "mlxOggX6PLKBFnIGONGWTPLKlvuMKcqUAk593IjTZIOqJYfFs+tElZsXs+F5" +
                                                                  "YEFEN7w18MFeRuu8c0y11tnKX/wS4xrE+tt0p5gYhn3Y8kScxszM45gjJbqG" +
                                                                  "7rvEFvIVFxJfa9Q90kcHZI8XmS8q26C529TnnOQSLNDY2Q7d5RLdnuCd56gw" +
                                                                  "oum+ZeDqC6Tl05Fa67EWcCiuPsLi4t6GmBPrXLRTqaoDeLRozqBPpskZnQ3p" +
                                                                  "MkzCNzajdGF3xVnm8RV/FSRRpjbKqN5244hnVOjeS45xI0woSkFXSCGMeFZj" +
                                                                  "RHtcMFjVp+fo3z3wblQ0qlXts7kIaLyW5ICPTH3B100FmV0/Dh69u4gLloJJ" +
                                                                  "bC5vxE5LoSCc5IJPxe4EWXUu04eRxPbr0SwcrBnhgQPdDA55srP7chIC/OV3" +
                                                                  "1WSmEDLjF0M40oNADKqXvbXIYXoxO8STF8p8SFIYXDItuWTPy0UZ4INZWF/q" +
                                                                  "kksUnrx/9od+SyxipJUTn3pvcfzbZAjP5ok/ExBgNyM3zPf/sdqax6V0lgvL" +
                                                                  "BkDBoS5jGZpggUH6kPx93UzD5+mXUL1GOnL8Enq6jO4+tTNgsjtgaCuIFcRl" +
                                                                  "j9MEM+P8lNTdcPBQNLiRDmpLJNxRT0Lc7dy+b+VA3ulGci2CttaYo8LlhTeh" +
                                                                  "JeIiLCiIyOIn2nXSjVAvUXZtjc5g6zhsk9e2+Ih4pa6XV57huAYiywt3j22c" +
                                                                  "lCZ6hBfryF8VqDwq9VR2dnrcOK7xroOlYKaawKzlLsl+Cs7inohbnQu733ZX" +
                                                                  "CqjIg+1hAa6m2z6wFB4WwkZpTxPe0ryhzkoj9ZEyry446OQSxJI44RT+EZEX" +
                                                                  "36ed2+wCatag/3d7Vx8aRxHF55LYSxNNbVLTNpB2iyHuaRrtZ9peTwiRttbW" +
                                                                  "xjS11ZLW62Xvss3ldnM3l4/WIBSEgkWKVKXUL7SKQkT8R0SwCOIfBSmiBVH/" +
                                                                  "MBbFlrYgrYpSEOfNzH7e7ObucgktTWDf24+bN/ObeTPzZpb8tjPbMZLcOvLE" +
                                                                  "6NC6tq2J0cc6Vz3YvuvpzYeya/oeGm5vh1eg+/k703rRO9OmFR5vTdn/9B/w" +
                                                                  "/J/+oJ5Wh6JYcfxn/cKDarzJ5GrZr6Zi2gA52WaSLIjySHjkAadO84uc5jNY" +
                                                                  "S1PGCn/7/Xnbr3faTyvR3s4oo7/wtK7lbd1VOcNpFSuTms8UWzlQ+E1Z4NHy" +
                                                                  "tT+ct/3FguK7MxgRW0OcgsQizkDAJNDoxUxFWQROHznxRu+Od1eU8eRPYTQX" +
                                                                  "a/rypDLEqT2YqVrgJMhhPttO+bgsKo2Jy43rO/p/SzBOgqWunN2//mD7+NnN" +
                                                                  "zbGXylC5yZmRQwLmTBR2MmVUpRVSNaluB1+GxOh1SKGroA42kmMJQhUNTJdf" +
                                                                  "hKd1tFkW2KglcqozQBtnxDRWBsbmciO/cz1hM+biNCljTeyi3QA2HYMYKYMN" +
                                                                  "vgsbhRK9tYvzQIDag1G5msK0qC/48KacAPE8RnWWT3YBUUuGE+GspqmOOCsn" +
                                                                  "Qo5VCFVu5HquR+WAOJpbFZCkkuvApFUBl8dAHKemT/mAeR3EK6TWbB1gcjTr" +
                                                                  "yLEJoepjXO8rDA0k6eH6SW80Acs9jlGrp32AvAfiTYzmJRT8uDK82zEciTC0" +
                                                                  "kWM3QjVjXHcWhgGS7OB6SwEYPvTB8BGI9zGqYRi67AO2CMJ6cvQhNH+A64cL" +
                                                                  "gwBJIlyvLQDCJz4QPgXxMUZ3EwjQ8bYQDDthYjO6Z52TFcd6lsNGI0LcTo5n" +
                                                                  "EVrwANN1fxaGGJJc5/pKAYi/9EH8FYgzGM23IWZcPwbkZU7IaWVAw0prF1U2" +
                                                                  "ViAR3g3kOI5Q/XmuTxeGF5K8w/Vr3ngrKJwKc9iwWKQ+o5mc84H/DYizGFUD" +
                                                                  "s1aXkgSmKmpIBIcMX+gkQosSXBfosJAkwrWPw04G53sfOD+A+BajChxVvXHA" +
                                                                  "2PE2Qg0BphefLwwHJPmO66+9cQRpuYICHMedgsGa8IF1AcRPGFVCK3Vo+qgn" +
                                                                  "NJi9ibfdm+VaKQwaJOnluievHraNWr3kU/jLIH4lLpZWYlq6dxtM36LZu2JI" +
                                                                  "U3tdmKBMqJYcVxEKXeP6kg+m+52YKnmSi1xf8MbkikOC25VMJpqg9ddgDSV/" +
                                                                  "+QD9F8QfBIjOaR+vutCMIi6AgvOMoXPQgJB98vnP+1kAwc0bGFWZsXGTVUOu" +
                                                                  "eq2DNkTsT1yvIK65citnbGZGPS0RMt51GvyJ4rkBbv5tVmrgDh9A1SDICq9a" +
                                                                  "Z+sIGHVFdRuEFM02RFTjQNf0cT1O5VFKGcF08uwsKVukgyp1w8q21S1tK5av" +
                                                                  "WwOckaEN0mA2mlEHs6QGZUbGKEGXk2AppaaGtH7lESVuYweVQ9Jh3KdmWi1f" +
                                                                  "kkPhsZCue/c6+jfH3QzQhvN03aeZ63ye3QPiLr6mFBVU5A/Q8+mJyx/WzvqD" +
                                                                  "0B/Y/ondIVQMDiDt7dkpidr8qLPNg6I2X+zb5o0+z5aCWEhmPKMsojaugZ83" +
                                                                  "5rTxnT+XunVuM89w+Qbjy5T4kHFA05JKNEWdxNhu0OLyXkqmKbE8D0cHDozR" +
                                                                  "KYGdGfMCu6J0qPSUexrfl2uRirZhbb5NwYixwzYFE+Y22hSLwVbyUy1IPlYY" +
                                                                  "GStLqPVsIj1ejUuyJkUiUiqbTIYktnVEUiczStib3nYX7NVEYzESNXX0KbF+" +
                                                                  "Oe9CQ0p2eVBTUzJbUrEbZE3GLuUQnNPQkfYFOdSSf60Um4GjQdnvBxQFT2sJ" +
                                                                  "sUZvTGse4GCk72ukN4+WNiMz7LMD0s27JTNTHG7alrc2bmKIxDwKd05rpJj1" +
                                                                  "zpuplW537yyuApJqHHfPgIORQj+awkqCFVijN0NhNuupZkAjmQESBMHmhRST" +
                                                                  "IpJsPdPCZkCk9ZNHgD9Mz4hobpYmmTCVwSyJu5MkfOvW5FirxwvLFkeYFCqV" +
                                                                  "fVvQ5IyhSpaDGVHZw6uSWbeCLcdMXdLS8xDKOdqWFoEtC9t1KMwDL60/POaI" +
                                                                  "wdyLMSrdb0sZ0bztTadwX1L45QEqjO2dWjs8FiuKt3Toqu0+nxUdlCiwDKMa" +
                                                                  "58JBtBMY5F3KvtwzPvqxyFjmGTfgadMM1AgUX6ZI2nxQwlcZAitJvQHKGPFG" +
                                                                  "q7VF+3B016o2ZwUbvDSDC8zZda+17hXvkjG/l+g+BPuWB90ZoXtjTVm9lxSb" +
                                                                  "3iZjQhHzUUqDStBSgEUOhSVul62lWDxQnGG62mHGyLw55XVTriV6x2NMZsFo" +
                                                                  "SXI3os6SGHOEl8VZvIXiyJscoLWcEbiSNd/O+tKsL83g4qNEjmatMlpjWirO" +
                                                                  "a9GM6yCXol+mRHw31tt9nnWACGNUbZvNWGhj/7CdrpvhDmrIpuGDm+PXF/4z" +
                                                                  "p7J7gn8RCElNh15GsVfHJ66da/4ifeOtz1+8sm9oT/xHdOqX504uOzj+zP9/" +
                                                                  "ZApzCHQAAA==");
}
