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
    final public static long jlc$SourceLastModified$fabil = 1282915709000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKVae8zj2FXPzOzO7GanOzu723bZR/frdigzuB07sePEO0IQ" +
       "J07i2Ekc23Ecl9XU\n7zh+P+Nk2S0IaEsrXn0AlaAVElIltEiFFfBPBYiWd5" +
       "HQ/tHyTwuoFUKCVvAHoqoKxU6+b+b7vpmd\nZWkkOzfX5557Hr9z7vW5eeWb" +
       "lfvjqPKcISuWcz3ZBHp8vScrJM3IUaxrHUeOY77ovame/fG3f+xH\nf+Y7f3" +
       "q2UsmjykHgOxvT8ZPDMXeQP/+u766/9KHhU+cql6TKJcvjEjmx1I7vJXqeSJ" +
       "WLru4qehS3\nNU3XpMplT9c1To8s2bG2BaHvSZVHY8v05CSN9JjVY9/JSsJH" +
       "4zTQo92cR5105aLqe3ESpWriR3FS\neYReyZkMponlgLQVJzfoynnD0h0tDi" +
       "svV87SlfsNRzYLwrfRR1qAO45gr+wvyKtWIWZkyKp+NOQ+\n2/K0pPLs6RG3" +
       "NL5CFQTF0Auuniz9W1Pd58lFR+XRvUiO7Jkgl0SWZxak9/tpMUtSefJ1mRZE" +
       "DwSy\nasumfjOpPHGajtk/Kqge3JmlHJJU3nqabMep8NmTp3x2zFuT8xf/+y" +
       "PMfx2crZwpZNZ01SnlP18M\nesepQaxu6JHuqfp+4LfT658gF+nTe1S89RTx" +
       "nqb9g384o//lj5/d0zx1F5qJstLV5Kb6XfTpZ15r\nf+PBc6UYDwR+bJVQOK" +
       "H5zqvM4ZMbeVCA9223OJYPrx89/BP2zxc/+dv6v56tPEhWzqu+k7oeWXlQ\n" +
       "97TOYftC0aYtT9/3Tgwj1hOycp+z6zrv734X5jAsRy/NcX/RtjzDP2oHcrLc" +
       "tfOgUqlcKK4ni+uB\nyv6z+04ql4sJbF0byPGS05PrRZwFSWUEzuIC/KC/1j" +
       "0wiPxS+xgsrG4FsQ4WNJGlgnGkglHqJZZ7\nq+sQ0qcY5qUQD6/PnCls8fTp" +
       "uHQKEA98R9Ojm+pnv/7XP0FQP/fhvZdLZB6Kn1R+YD/D3oInZqic\nObPj/P" +
       "aTVi7dppXR9W+/d+ORX3hv/AdnK+ekyoOW66aJrDh6EZWy4xQKajeTHSwvHw" +
       "uBHfIK2F5U\nCgQXwXDTKRjtIqYwZVako9NIvR3fZNGSC/i99vL3/u5bN9ev" +
       "lqAqQfB4yf24+KVsF69xLwzf/+Hn\nzpVE6/sKh5SaXHlj7jfVb31k9OqX/+" +
       "arV29HRFK5ckeg3jmyDLTT4jORr+pakchus//V7wz+/eP3\nY79/tnJfEb1F" +
       "/krkAnVFMnjH6TlOBNyNo+RVGuscXXnI8CNXdspHRxmnmiwjf327Z4eOi7v2" +
       "pe/t\nP/9TXnuUnvnAHqb7ZNAt1OT9YWFJIi/C8Xpp04Orqu8GRQhEB6ZeiC" +
       "gnunYtCPagKw1/StldDv32\nT5+HvvL5h/5sZ72jdHvpWF4ugLUP3su3/cZH" +
       "ul70f/XXmI9/8psfet/OaYdeSyrng1RxLDXfKfK2\nMwVIHrtLIrn+xOOf+J" +
       "Vrv/6VI1Q8dpt7O4rkTQmK/Kdee+ZTfyH/RpFkimCPra2+i98zh/go+T9W\n" +
       "JOXDgCjxej3W1TSyks11WlZ050iG8v6eXfu9pRF34ys7u7zzkKTE8umI7JUr" +
       "0REQXOXF//zCp6sH\ne3nLMU/t2JRr8unMe2LgTXX7R7NPf/tvk6/tTHwbQS" +
       "WPg/zOaQX5GLhbX84un//cZ9yzlQtS5ZHd\n6il7iSA7aekAqVj/4s5hJ115" +
       "y4nnJ9eyfeK+cStCnj6N3mPTnsbu7QRUtEvqsv3AveFaubKHK3gM\nrr1y6/" +
       "LGeD1TCUqmN3asr+zuP7RH19mkEMzy5EL+8/Fum5InlQtrP7L16MoRHh4/xM" +
       "O++/p897WP\ngfLefF2Jf34v8bWdxEdbnILDPWUtAH8/dL12HSq5EneKfK5s" +
       "/1h5u1be2oXAT64c9UrnkJ1QrC7F\nEnhlL/SRDo/somGH6P0m5Jj85a2X7z" +
       "L9w7fJaL/YqXz0G7/0pV981z8UQBtW7s9KEBT4OsZrnJZb\nuQ++8slnHvrE" +
       "P350B+UikH741c9Dg5LruLyRSeWZUkDOTyNVp+U4GfmaVezKtCMZ7wQ8E1lu" +
       "sZJn\nh1uNX37Hb/3zq19nH99n5P1+7F13bImOj9nvyXaIeijIixneea8Zdt" +
       "RfBN75ysvs15T9XuXRk2se\n4aVu4zN/r1/7sYvqXdbQ+xz/riZNLqkDJCbb" +
       "Rx9akDowPquxNpDCLr/ASXLp6BzODmmjzwW46BF8\n3GuP6c5MmQ4XSLrcYE" +
       "BTaoxoTUdBuOrZ0Nzosbkz0qEBH1ORXKcMfjXL61Y0A2qmlLOs0k8wZLqx\n" +
       "BtrUgjAiFIY5Dmn9HAQ9JhNjw13XZuMu41XhYbIFYWYCglsAbORYq2MGMbTl" +
       "3EDmAmsIx22on/Ah\n2pIAROr5UE0WKCeaccsls0msiSIkBqa6UJ0nVpPqkI" +
       "Ajh2bHWY0ddgAKiVxkmjBJlhqZuG01mljT\nXlMLly0sTeOewC/YeBEu0sRG" +
       "ZFseLgJ5xblzar1lU39W3c4pLl9yQ0jg4HCuFsMW2wVl48JcY113\nzE63lM" +
       "PjU9hqySzZWM5XITOv6SxKmBJAxsjIRe0OtjRGwGCLVAeNVOQte5kYdVtIBq" +
       "g7ms7SWSb2\n+1uWr/ub8XBiB9HMd2QqMBwqm0lR6KMLK1SDPjnmasaKnszM" +
       "BWTNhXFVzgSOq+mDyYRAqHoyJUAN\nwt2JzSYSPlyNohEGzQZtCpQX85DS9W" +
       "InNw27gjXyJjWKSvmokY5MeGn2+l2Gr9r2RqNkMepxcI8j\nkjXrNPs1V2i3" +
       "ewWjJQ95GlB4b2ya5Jp3HdVRuK6cQ3mvv+5yoxVEp9v2JtUta9pjgyrHDFF5" +
       "0Xb7\nQ3sRkCnNQ7iwXETmLLckosN3HRWn7PV43kqWHit64x6WWR7Ed4btaS" +
       "tArVjNkGghDLeLHplVeWHC\nyytSICDMndSb2VyaaFSWb6Y+voA6eajDudAc" +
       "Q958zY8dvOcLFi/OJNtpjFqZm4SQgfXZcElP2HC+\nrM7XPpzZ2EYbowgoz7" +
       "YS1G57Us2wRTBWuwhQU5JsO4VhCDS6ARUOnFGKunHNZ2SOkkM7BZpQB3Yn\n" +
       "FBuLfvF6xst+uBk4lI8IVHMFop1JQg25yEcovadDyQRt9XNSyRldnU6twOwu" +
       "pLk1Yw1waQc4Up8T\nGO8Ugi9wzKkO2G6EWwzto3GsMrrP2Jta2B+E4sCPOk" +
       "4gdsmkj7MNghYmE9exye0sBzZkU12TZGNr\n85raMYk0MOUVMRWqHmNuxCK0" +
       "R5tBphewr20aUuLN6bhLw/wwwVhLkUhyKmOTWntq9dRU62fSHK33\n2jrbzZ" +
       "tBxGQBrkkrs6tg1YCIhhI9HHGhP0McnBPY9tqZd4u33rlODHAz6CyGA3OL2z" +
       "18SU4JF1/a\nm43BmUHTZ+HFGBlqDFaDwUYrGhDVFsWooWLyY6YrZHq+hd1I" +
       "BtCGmnoKjeHDybi3pnDT7qLi1NB9\ndK3ZtB73jY6Z1BjDCAh3yNfakCRbdU" +
       "eubkI/hr350EtmbR2PNtMmNBsRLs3N3DC3rZqJI0NkNMLN\ndtDNV+5oPJJM" +
       "cRvbwgwxcsE1l27EiwuWsaDOopoMGzI+9Rp1WnFWeQMBE3w0rq8lmau17RYl" +
       "Ddr1\nzsRHldTOeZMC09YKBJs0ySibVAFRdU1N52RbsxfcaMFWw62LLNctYb" +
       "KsEw2FmpniMoDVnKQy0Jb7\ns1F/y9cWokqIhhkbVGtA1QQcRRb13Eccg6EY" +
       "ZA3r47XFNGoLptohibaRUW7LcTNQFtG5xlEmOK95\nyXogWvWpjzVqbjBzAx" +
       "4HUNzI682FAYvrnu7na5ftdLWhNg79UJ2uF151GePdHAHaiyXE9+tNAxKb\n" +
       "now06JWniWKdkyGz0FCxFWxraC0ph3qbtkqSdUehpuQg7bfUrYxufQ+Y9kad" +
       "KtsDAjBG55thDk0b\nVhStIwxvkjFuQ0Q8pBmVQQwT8CW3SM4sKpLyOCQUTm" +
       "/WAVpeM0S7GQDTBEUlcGRiVR5qIZaDTJ2l\n0HfNQKOHAuxCSJaQHSdeS/bQ" +
       "XOMuZqyJzjofKytCjj3PW9Rww83dXgLOkqi5iVvrVKIWaJXlYElo\nbyZY3K" +
       "g7AZkLbZHLlLE20SaiuawhzVhBRhtxPAsHMqH1YWLVSjes7W7RYZdIpmq7h/" +
       "ppYONBfY1W\nHdaXUF3rSCLP93u83WFIDdJGLGIg7IbdklK46pO+3wdmDldw" +
       "IZtCR1TZIbNhKcnVecoPXdIZcwO5\nwzWqpDgcN+mgnsWzzqg3Vah4wfrhmI" +
       "ZWnIbP2OGGwSRV3wSrqYKFkNWgDaSBazw/cCM0ZkQ8xHw1\nr0OjRehq1Y6J" +
       "hhEoZAzMpnFtJRDbbR3DCghjKIC0AGabS+v5mglDI9OnIRHX+jMFac4NHrb0" +
       "2Kfc\nVcbIUKTASr2FVAHdycZxOhhJymCRiEHatm16Clh5VDcmXr+pix4/no" +
       "2jvlaDYmTeUNsqJQm2vfSU\npcUF3LirbaTpfGbBOVJ1xGyVTSeGQQ88cDnx" +
       "xW5jaVtptgJcIpuA5hyeSxiAAtMWh+D1lqebvEOp\nCT/Awp4sjnJhYaHjnr" +
       "boi2anSlsDZrLFZyKIDjzJXocLvLXEEHKJ0EKxYDr+2GRwZSBIU6DTF7cG\n" +
       "qM6TcDJFmZlow0S73iKHXarYrSy5NVythVLbzZE4iNZ9cs0ayGq1mmOWQQEd" +
       "dDnqAZalLBup2tWc\nBKmZbX5ZH84XPKSMG7ORPuGmogoCEePAINyEqp6G9G" +
       "2h31NcdVYbw/pEkTuESI67ocizSYyHUqtR\n+LFbA3XAZdl8PGoY8w7MxoHd" +
       "mKxBDvNntZZp9eaBF1aFtlMDEzNImIAZJnDTcyJg2hXmkieqpr/M\nXL9NDy" +
       "YRCxKMYJliLEdDi+uElNYWs8iaLpGkNfK6HXfToMOq7nS7mmSMYHFOgg5hi5" +
       "qgbExWGARW\nQ2kZ7eYQYpjaep44AwtoNonxPIWKbdaQhsy5bvfJDAUJTAnW" +
       "IEHr1a0q48pyg8B4N8iGAEuEK5Ho\nYY1hvq2tV2u+Bw2VUT+qN8hWQzfCRo" +
       "AOPWksBMHUEZwarRGhTBThPaUiOq+K+lpc+dv2Astr9jzP\nvWzcgCYSSG5W" +
       "wwDUkLYnJ3wzofmYIaboRAb6kZhPknzd3sj9Dk46WJdEh4o4oWZqtYkyyHYJ" +
       "JUN8\nMdGJdi0ZMPEQ7us42zTRiBDmSeJjPYxdwdtNYA413sXX/qQvuZNxxi" +
       "JACJm9oTgvVg6JUqs8TbSI\nRIZZdiREeDa2NmtkJVKhlafwYIAGqDoX/CW+" +
       "TojhlCp2IJI1gSwNaKdgd8iAMoWqkYas4qWBJ3I1\nUmoNRcS3LLHSE6NlON" +
       "4ibMhUhLsUhgo8BMPLYToZzdoiGHU6cxFG8onCc1S3hzVr3awteiwzAaKs\n" +
       "z4th1QCEPigu1BRn8j427yi1lrOZJLGTBpNRy6KhAMAxrCViTENZduAtomZN" +
       "RVYVrwbAC8Ozl8hI\nhV0jchNQr+KdbKx4TRhN+SbPUDIBZ9lgOc5JWRlKSY" +
       "uPRlzNZUYC6gPuItPF+aQWg+kgd53UltAG\ntPHNqE85Vur18SrnF+v0ihTF" +
       "/rQhJioKjwUdzqis6Sd1UTDIATdedaAItKaiCKdUKAQoYADwKJsp\nGAx2iq" +
       "UaIEKgo3oxGlbDSG1j0Ubsb6Qx1Vo2Hc4adMV1azuzanlujRyGHIwibTDrrm" +
       "lBRk2EARME\n3Y77k95gqTRiyKV0DsP8pqrRVXoLAA1szhrOiIRWkmowYktQ" +
       "Q2qwXslYtDXHIxWFBnAcqr3GBIuM\nwTbIaqHcbBFL3g2msUMvOM7m6NmWRK" +
       "o+a2DJdCASgVDTNDQCeCEyG1DbYkZE17CzeaeF2etsjozF\nsAWh+kAHxh0p" +
       "peAGNINgoaOkDtAcy4NB0NGrvX4QUcW709RrWhChwwMhxVqCH3v9hKVq1ghb" +
       "e5g3\nGYFA4vLzLGtpwzowqPf6YyLoKF7abEYtAwRWa120mtVmujL0DVAvto" +
       "hUM54jbhfXhWzQ96KaF8F1\nZ0lyzYwT7O6al3zCTaZCbdyYrpuYNh0JWEhS" +
       "bTyaaw2UQPFxtSuEjAGvcV9ne43aFgbMlTcxWQAO\nYCatCYhfhI0Mr2AibX" +
       "HtKad259Ckr89m+nRBAtRCbIsj1XOCQew2p9XZuCaIsAzMvZo/EcKm14ZC\n" +
       "IY1j0GZmHLHoz8Gu0IUts8ZOikVkXGyXLaNOL4DiBRoRONCYO4BtMIwWxkS9" +
       "umkyFtbcECDZy2uy\nCDYHcGSkfXeE+Ki9SlYYhtajOdBqGf5oNYk30Njor0" +
       "TJFJCVPMxABaE5K9w6ETVvGVWuDzTRmZWY\nWO615qhqqLrSa9YbQLewrCEu" +
       "tj5DaykIGFxvU6RFqLeyk22j0fBT2u7krQSbxEljvFBa2hastjvU\notPm4s" +
       "G03S7ftqXDUsPju1rIraOZfYWhfEb/Hyo2Zw7+vyXRwxLTC3cpMe3qNUnlQh" +
       "BZmVwegVWe\nXVnGoWQ3y+rCzRNV95s7ScGduLsCxrsPK4q3i45PHRYc46jy" +
       "zOud7OxqGh8S/+PiB+UvvnD2sDo5\nTyoPJn7wXkfPdOd2ofI0k9HuIOuoen" +
       "dp/uw/9dDPvnS3SuWz9xx5U72cPTU9t7T+8uyu7LevFN5x\nknZy0I2T9cFq" +
       "pCdp5PEnqoTP7j1YCPFgcfWKq3pYc9t9lw8v7xzy6L5YfcyMp2q3Z/dO2v3m" +
       "gn3x\nVkgq5ywvOd13v+H4cnL3EvCpEvJzt0tkpOPopuy0IzN1dS8hclUPyj" +
       "OqnWD+rWrzlRIIV04A4cpt\nxLon9K0V16VDfS+9SX0PBS1/xvdUpfz54o7b" +
       "y29ayPIA7HLJ5FDIM/uwuvkmS7fPN+HnD8JUjq0w\n9RP96mEEHWS+pR2UMW" +
       "R5VnL12sGLB+97gTt46fbRxB1BuNNGuXf1/g1L+z+bVB44mvU0Nu4rZTpl\n" +
       "hYeL65k7rHDmA28uuTxfb520wr5OfbA/FDlQfN/RZW9njqMI8o2r79tVtQ/2" +
       "bntRdpWXdmjct47O\nNPa/dgcbu+aOCf2eg/3YUqLTI/dF/z2x/0KvML1lHF" +
       "z1D6xbMx+cQEfpmxMdB+rBjxxcPUnj3zjY\nx/jB6x+8zMoirB6mBQCcIop4" +
       "/2phsntn0ffstLl246VbzJ1Yv3EvlJw9VlW/V7LY5ee7VNb31nlj\nIH0sqT" +
       "x80l+n4XTh0K3HEVWuVQWHJ24j6sy7v78TvO/PAOXtU2+s7G+W2bBQVpXj5P" +
       "XdtV/tkspb\nTvSW53hP3PF3k/2fItTnXnv/1S8El/9qd456648LF+jKA0bq" +
       "OMdPmI61zweRblg7wS7sz5v2xvid\npPLQsXPwIqDLr51Cr+wpPpdUzt/eTf" +
       "xucOT/x44fnx/Knf8v+RKgKFwjAAA=");
}
