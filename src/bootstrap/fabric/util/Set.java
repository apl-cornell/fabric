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
    long jlc$SourceLastModified$fabil = 1281544489000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAI1Yaazk2FX26+7pnq5ppmfLZDRrz0yDZjB5dtnlWmghqMWu" +
       "ctm1eSuXIXrxcl3l\nKpf3rQyMQKBMQsSaCYsEyR+kSGh+ICLgDwJEwqogof" +
       "lB+EMAJUJIEAQ/EKMoEOyq97pfv54ZUlK5\nbt177nfPPfc75/qct78BPRQG" +
       "0Cumqln2cbTzQHhMqRrNTtUgBEbXVsNQKHpP9Es/8uFf+sGf/uaf\nXIKgLI" +
       "Buea69W9pudDrnAfHvf/Vb6ZffHD53GbqpQDcth4/UyNK7rhOBLFKgG1uw1U" +
       "AQtg0DGAr0\nuAOAwYPAUm0rLwRdR4GeCK2lo0ZxAEIOhK6dlIJPhLEHgv2a" +
       "Z50sdEN3nTAKYj1ygzCCHmPXaqIi\ncWTZCGuF0R0WumpawDZCH3oDusRCD5" +
       "m2uiwEn2bPdoHsERGq7C/EK1ahZmCqOjibcmVjOUYEvXRx\nxt0d32YKgWLq" +
       "tS2IVu7dpa44atEBPXFQyVadJcJHgeUsC9GH3LhYJYKefV/QQuhhT9U36hKc" +
       "RNAz\nF+Wmh6FC6vreLOWUCPrQRbE9UnFmz144s3OnNbl6439+Zvrfty5BR4" +
       "XOBtDtUv+rxaQXL0zigAkC\n4OjgMPHd+PgtehE/f2DFhy4IH2Ta3/37Ivsv" +
       "f/TSQea595CZaGugRyf6t+rPv/BO++vXL5dqPOy5\noVVS4b6d7091ejpyJ/" +
       "MK8j59F7EcPD4b/GPuzxY/8VvgXy9B12noqu7a8dahoevAMbqn7WtFm7Uc\n" +
       "cOidmGYIIhq6Yu+7rrr7/4U5TMsGpTkeKtqWY7pnbU+NVvt25kEQdK34PlV8" +
       "H4UOn/1vBF3jQXRc\neJcXQV1EDAvKI24KHMQL3HLPIVLY2vJCgBQygaUjYa" +
       "AjQexE1vZu137LpzBZueCj6dFRse/nL/qg\nXRB24NoGCE70z3/tr36MZD75" +
       "icOJliw8VTWCbh5wD9YqcKGjoz3eh++3Y3kwRuk///Y7dx77uY+E\nv3cJuq" +
       "xA163tNo5UzQaF36m2XWzGOIn2xHv8HMn33CqIeUMrOFrQ/cQugPY+URgrKQ" +
       "LORS7e82C6\naKkFwd5549t/8+8n6RdK2pTH/FSJflCtOLTNQbcbr/MfHX7s" +
       "E69cLoXSK6XNs73vPV2uctFCVBkF\nzvC32o/+1xc/W7l1wC/nPLcHuBI+yP" +
       "r7Jp7o+R+Kn333r6Ov7o17vYg+kVpwpnDlFy/63n3uUjrh\nRZUkNbiH2/zb" +
       "5PGrv/257SXomgI9to9qqhNJqh2D4pyUIi6F3dNOFvqu+8bvjzEHh7pz6ssR" +
       "9PxF\nvc4te+csIJYmuHyeLEW7lC7bD++Jd2Mvc/Pbh8//lt9Ttt8+sP1w7r" +
       "1iTcGlyiuFzAq3Pi4Vu/Wa\n7m69wpWCW0tQGEuNgPG652VHkFeCfk95whet" +
       "Xmr17k9dRb/yB4/86d7WZ5H75rkQX1jmEAcev0cQ\nIQClxf7+V6ef/sw33v" +
       "zhPTsO9LgcFSCWoxZGuerFmm3pRSPcX05Z4a2pG2xAcHu/zycj6KlTVzl0\n" +
       "H8/3P3sX3Eu8/L72+NmDPV7f2+PsYisQPtASR4Vu6HH1GC1Ra3vs1/fP7zvV" +
       "vWwflw+kfKCFws+u\nbf129xROKqJLEfhuH5Q+28Nje7OUtDg+XD3n9C8fRL" +
       "b3/kfvibFucT996uu/8OWff/UfCrMPoYeS\nkmIFe89hjePyAv/425954ZG3" +
       "/vFTeyeCoKPv1Y4efq1E/YHvSP0XSvV5Nw50wKphNHINq7ipjbMd\nPOiI08" +
       "DaFtE9Ob1+fvHF3/znL3yNe+oQ5Q539KsPXJPn5xzu6T2bH/GyYoWXP2iFvf" +
       "SX4JfffoP7\nqna4v564P0qSTrwlPvd34PUfuqG/R6y9YrvvafDokSuDWki3" +
       "zz6MJJvzVM840XRyZKLkSQbz42wa\n5zVyHdIkTLFTdOUFmLeOhW2qV/uxsW" +
       "UYprEeNXRcqWzhPNIcxckzHRcWFL0iOZ+bSZTH+0OYYvwl\n4/s8xTFMi3Tb" +
       "Bu/bqsXRJCbNrL4/G2TibOZvqIUFA23cqEQNANdzuBpNYgIzTZAMnBw0EJyI" +
       "NlOe\nHUcbQDZzyfdHoTvd6ikQADMds1JorxLZ7+PJEmxHeswypjDlKg5czW" +
       "cuL7I9JpWbWKPpjX156Ts9\ngM1sCqsjsLBmW416HoYr1FtaJAhSoI95mKcm" +
       "ru+0InK97tmgCki/WgH8kHJ9L9brXm7LzJyvev2l\nMhkz3HiUzzf9dWPEr4" +
       "lEdF2xxq6UqYuCZdVl8taG2q1ooe0QllMT/Go+xZJKT8bqTcZYb+BQ44Ee\n" +
       "1iWhqop1wndTbeLqtrKxB0tuvstZdqCvt/PtTjIm2wmhzlVPokbyzOjNFHSL" +
       "B3rDq9i9cB7WwUKb\nryjaWEWTnZrrZDUZbcQ61Z5iSm/DjHe5R5uWJhHbeR" +
       "OvVYlcrCv4ZBcHQyHW4M2WNqMiJC8r2+Hc\n6SZjcbusu8CtC6na6YMF0pug" +
       "ZFsajahOQsZsa74RieUSF9hdP5KdWjTVxUQkyD6KDSiL2uBSdb0Y\nzio+up" +
       "XaOD9RhJVCEhy7Q5tLSqfa3Y40kZYyhXYYHQ2CNmM6EwU32AzTHdRDyJk/65" +
       "ijWsZxUziq\nsfzK7fEVahTmFrxhNrVAdoz+YAgTKGyJsZTOSU/PF0SEhIKG" +
       "56nfFedSq6c4BjatjmtcYqNzBGmj\nkSXxzDh1WyJa0ShRNyN5QmqZ10JjSy" +
       "HpbsATGtVpDZIFjDh44OVOMgmnRnfYG3bmK66np0G9Q2/S\nqWDG63CUCv6m" +
       "vmlMGpUmvtWaM4f3JpRo1foIzWWENB9Zgz658PEANEeLLVHsh/W25KbXbiZm" +
       "NxxO\n6SmCj3hnqzSpHqsT/VkzifR+ZTMC446qDQQ9nIGGtib7iRRRth5uen" +
       "rk8TVC7HSp0BPiviwL5Cbs\nU1VXpTutVgpyYTlt41vCncwV2x6PkwqCDNK1" +
       "hw8GRNth7J4bh97QQ8Nav4ulfOaqKEWYBpdTKZiR\ngpYjdVRAudzU8nRi5k" +
       "OGnlvLmtNfVDue3a+MZyi2aUZph6rLPXgGRJnbtNVRu1VL+yuLK4IC527E\n" +
       "aoellJ2xpBV+TrFcTiCEPh2lzqg7jpkFqEnVOufqFWw58OZNk25RmABnjUW1" +
       "a5GU55E5GS8AlpBM\nSNLDmciC6YLFQMuY4PCO5bWoS6CdPG2HVDQT80GvpY" +
       "6SeaWKIzJisf1qd8IJK9ibrdv2kA39JK5n\nfXpK7PLWeIrwQq9GtKgMk5y1" +
       "skM3fh72MV9v1zstwRq0G2h9LlalitlLPF8hqy4W1Qc4sSEmIOnk\n+jJ3GJ" +
       "ez+4qxbWdyix1Mchgkk6kfS0ZQVfilNkHkHk5Ug0bQwKpDzJuk4wo61d3qci" +
       "iP1TW+G60a\nGV/VGngRb/r8whO0btiRu0oaDYUtW/VTXOWtnoXhVEOe19Vg" +
       "AZwMxfKBZ2+ZXd+pjPWlsxuEOLbT\nEAfbBZG7iM22bczbkpQSTZgSSRoOvP" +
       "pMb7rLpuMMkNweITOqryGA0AYThoVbyJjqDZq1sDLYKKgx\nH9bEtcBG/Y3i" +
       "dyLenXB9z6fRKYVGdt3ys7U0HdbHobme1oNaC2mOF3OPchJ6mtbwjTxWBAt2" +
       "jCCv\njMZNPXGEGJl4LRPbbFye89B0p/JVdjRfsatmtasTNQrh4wUewdOV0d" +
       "2h6TrxF84wUgzDqym8bHUc\n21tESYVrJ4njpKLIMR04RD2dbdsW2/JRpNGt" +
       "U9qmJ9SHjB/v6CGYb5rtUaF3gCOeGqqJLdV1huYY\nMJQG4YBpa5Wt31dpYo" +
       "hRa7cDWjtDGJKSZMCB33DDxWyLA4flB/W8m2xFYeCENNeTG3XO6c9sf4Rl\n" +
       "nSrTTNqNmuEw7VWz0jTJudjY4fZs3UnnmDtaCEm/6ZEokcnGwiv2py6pLNGC" +
       "jE7NpgfkmBVs2293\nbQ/lkd10nOfVHDaBE/tSxRa5vjlfKQzMyro4SiQ7Wn" +
       "XXmgEX4XU22bDzfAEDTPAb1a0yaOGSPbcG\njRUXT+Q5t57nidgPxBBTximF" +
       "NCsI7buWI81WYdLUJ7jQwNwob3BrZhEz4a7Kq71xdSuStbFKyUJj\nHowDK2" +
       "Dq3nBs5Lw9WEf0DNuANYXOrPqwsl059k6OYwSJh4ump9WnNIPCzdEQS4KapL" +
       "Qjq+VWA7kW\nqBOt1xBgD2/XuaHHez18oI0kXhBJmHXWqsZKy4pquWiMd/qm" +
       "VpNQ0tWDxTp2uFhoUzmX+8VhyZK2\nTOM2NbZ6AxyIAhmi9mo58qcq7cFkex" +
       "g023kGetMFUqtg9HjWaM9JfMGxGd7De+2CHR0xb64pajig\nQ5TW6XTlkI4c" +
       "TnZyZz4YuW4rr7VaTdshmoSeEq5jtYgFphSOrnmBaOjGmJTcNadztSGsDgAj" +
       "puPm\nzF3PMBmMMLEz5AdqlcI39FzY6E0haCgm7ooO3mQdSY5qfmMVV/vDtE" +
       "JT3grhki5Mdlu9Fd4BcNqQ\n8iT2MjMmnWhAk6yQtvFccVFpKyketRj2jbhP" +
       "ZfRYJjKsh6CreILgLUJCp5VhDY9pNVMMDxOkrGOj\nLQbuN2FNllttZDhPut" +
       "zArZqTiZfYSt4z4hGPFTfeYG2N+Kllwm08ilrdSXfDm1mvYqcE050Bl9/4\n" +
       "tIzKgt/sGYHnLnGbGiYsMd8ZJr2lGtbaUwR4alFi1UzMWO6wPhw0GFuMewPW" +
       "dQ1SrG7rFQNzlqhi\neotq26wJ3tKvhaMJnvHqDGn4djfdEaRcq+W7woFxTY" +
       "Y1EE9GHttfxe685joDrEwP7V2jZcxWbEXO\np5sMMBa261PNmiK5VEBsAiwL" +
       "1vLUToKtimwErt4qXqQ6CiM1jGbozSwd7sOORoK+QtVidhJiBiKq\no6jCeo" +
       "nrNyeZ3Ghk82RdL95lVTkiW2zfRxqkmzXRAOkYhDpaBFWyeO0tX4eZ7yhTeG" +
       "qf6Nytth0S\nhHLwzv7tOnsPkAh6WNXCKFD1KIKu3y3p7WHPFQmefI+C1PEz" +
       "T731y6//+lcu1gaOzpKqp8/XULqu\nbRfpdpF6lQnFC+9XZtsnE2/K/3nj4+" +
       "qXPloil1iDQrXI9T5igwTY95S6CDLaVxXPUvab85f+iap/\n/scfVC+AXvrA" +
       "mSf648lzs8sr6y8u7XP9Q3nggbLm/ZPu3F8UqAQgigNHuFsaeLCcMw1cHRhx" +
       "AO6t\n+yvfHPzHpx9q/e4l6Mr5mkmJ8MqFCsQjplvwzi4XOKujVqJV4Kb3ei" +
       "6WI04LcEeH7Pvo1iH9Pj5X\njhgWmev/X424l6rdx6VLZZs7z5yj8ikV9n7y" +
       "XtWhHQTqrqxKZT/5zgu/9ufqb1yGjmjoSlhk/vsS\nIXRfgeoUoXyuzw2W/4" +
       "ss9pm1Zd5egujAs5MS/4QH0Ql7xsBnTxm4T8ZDoMeBFe2OWVUDduEulwvZ\n" +
       "sibyzAN1+kM1WX/lnY+99kXv8b/cH8fdiu81FnrYjG37fAnoXPuqFwDT2qt4" +
       "7XACB4uFUXFm9xyi\nSHvLn72mwUEiiaCr93w29c528cT5XRyqVtn/AQd0cn" +
       "eUGAAA");
}
