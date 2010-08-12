package fabric.lang;

public interface IDComparable extends fabric.lang.Object {
    
    boolean equals(final fabric.lang.IDComparable obj);
    
    boolean equals(final fabric.lang.security.Label lbl,
                   final fabric.lang.IDComparable obj);
    
    fabric.lang.security.Label jif$getfabric_lang_IDComparable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.IDComparable
    {
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    long jlc$SourceLastModified$fabil = 1281544053000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKV6W8zs6nXQ7HM/k5Mm5yRNo1xPklPUg+m2Pb7NcBTBjMee" +
       "Gd/HnrE9hmrXd3t8\nHd9tIAIBSS/i0jblIkH7glQJ8oCogBcEiJarioTyQH" +
       "mhgFohJCiCB0QUFYpn/r3P/vc+SYhgJNvf\nfF5rfeu+Pnv5G789ebksJl/0" +
       "TCuMH1Z97pYPadPacZJZlK5DxmZZHsbZR/YLf/iHfvYP/Klv/6MX\nJpOumL" +
       "ydZ3Hvx1n1GOcD4L//S7/T/trXmE+/OPmIMflImCqVWYU2maWV21XG5I3ETS" +
       "y3KJeO4zrG\n5M3UdR3FLUIzDocRMEuNyVtl6KdmVRduKbtlFjdXwLfKOneL" +
       "25pPJrnJG3aWllVR21VWlNXko9zZ\nbEywrsIY5MKyeo+bvOKFbuyUl8lXJi" +
       "9wk5e92PRHwE9wT6QAbxRB+jo/gk/Dkc3CM233CcpLUZg6\n1eTzz2O8L/E7" +
       "7Agwor6auFWQvb/US6k5TkzeumMpNlMfVKoiTP0R9OWsHlepJp/6rkRHoNdy" +
       "045M\n331UTT75PJx0d2uEev2mlitKNfnB58FulEabfeo5m92zlvjKG//rp6" +
       "T/+fYLkwcjz45rx1f+XxmR\nPvcckux6buGmtnuH+K364dd3p/ozd17xg88B" +
       "38Esf/jvHrn/9A8+fwfz6e8AI1pn164e2b+Df+az\n31z+1usvXtl4Lc/K8O" +
       "oKz0h+s6r0+M57XT467yfep3i9+fDJzX8o/5PTH//r7n9+YfL6bvKKncV1\n" +
       "ku4mr7upQz4evzqOuTB172ZFzyvdajd5Kb5NvZLd/o/q8MLYvarj5XEcpl72" +
       "ZJybVXAbd/lkMnlt\nPD43Hh+a3P1u19EZd2syS3KzMK3YfTiGWV5NOPBYjr" +
       "4PZq2bgnmRXYUvwVHpYV664AhThDZYFjZY\n1GkVJu9P3dzneXrdlYUfaB88" +
       "GDXxmeejMh5deJvFjls8sn/pN//FH6XYn/yJOxtf/fIx86N+7xZ4\neF3g4f" +
       "0FJg8e3Aj/0LMqvtrMuYbWf/lb7330z/5o+XdemLxoTF4Pk6SurmhjSJpxPI" +
       "rnPKpuPvnm\nPf+/ud3os29Yo/uOkfAoHgndwmXUYzPmoufd9Glw78aROfre" +
       "N7/yu//qvz5qf/nqUVcP+PiV+h1r\noz2jO97eeFf5MebHf+KLL16B2peu5u" +
       "huYfmJ6yrPq4q+Jogn9BPrj/yPX/mF6dt39K84n74ReKn8\nYEA8g/jIHv7+" +
       "8Re+9S+r37hp+fUxMVXm6E5jlH/u+bB8JpKu8fk8S6pZPKU7/9fNm6/8zV9M" +
       "Xpi8\nakw+ekt4ZlqpZly7ijsm1GlYko8nucmHn7n/bPq5i7X3Hod5NfnM83" +
       "zdW/a9J7nyqoIX73vNOL5C\nX8ev3TzwjRvMR3737ve/r8fjQHjnLhDu7L4e" +
       "1zxk9LXaUN0Y8Q+vjL39I/bocWOUFW/77qgss3Kd\nd/O8ezDJr0R/z9XCz2" +
       "v9ytW3/uQr0K//vQ/945uunyT1j9zL/qNm7lLEm08d5FC4V439278k/dzP\n" +
       "//bX/tDNO+7c48VqJBKm5qiUV/LaikN7HJS3utVVk1fbrIjc4p2bnB+rJh9/" +
       "HDN30w+12+UWizeI\nL3xXffyZO328e9PHk5o3Uviemngw8gY9hB9CV6rojf" +
       "a7t/Pve8z7dfzwegKvJ2hk+FPn2H6HfExO\nHfPNmBPfuWP6iQwfvanlFvV3" +
       "Veke/9cT1t2i/weegnHZWLp++rf+/K/9uS/9u1HtzOTl5upio/fe\noyXU19" +
       "r+1W/8/Gc/9PV//9O3IJpMHvxe89vma1eqX/6+2P/slX0lqwvb5cyy4jMnHI" +
       "u480SCDwai\nVITJmPibx5XpZz731/7jL/+m/PG7dHdXvr/0gQp6H+euhN+8" +
       "+UN5N67whe+1wg36V4EvfOMr8m9Y\nd6XtrWezJJXWCfaL/8Z99w++YX+HpP" +
       "tSnH1HhVef+BtbtNwtn/wE2CKR/RGWE6BEItBc7ijyNI/C\nNpEUPlX8LBfy" +
       "48oXrNWSJOlLQfZDSUhMKw48vxatqUWzMLvSV1njkyaehJYCaZ1l9BWJVJk6" +
       "JkqT\n0VDTP64XuTmbXbisOAGzKj8TCNg0GxvhCA0eDjFhgJY4bTxwQYALEA" +
       "QaEasI/cjgSVZk1Abm6SLa\nNqoeVrtjD1QceelK15Jp6zBLV0jMNQmmNFZP" +
       "AAATqEuWnYXTS6GSbRCCGnXet8qsn1HNdlvFEIDN\nwESSWK7lMwYdQZmDy5" +
       "g1iVzq87wMzCovhVyXV2vqSB00gM+2zGoKpbIG+aqGAUKgHomLSTNUa+5x\n" +
       "WY2kuRYtcm55Sdpe7o67KiD5OWeAkFk3MqaES2CfmHzbJ57YgUVjLaZp6Tb6" +
       "uQ1WmApna2alXZKh\nLpdwb7BiDATL/BgBG1dQLR2dD6VNQFTIYABfuKrqyy" +
       "YlrTElXp9llhqyeKpGQN4IFmsS3Gm9F7am\nktDqPDGNxKZifdcjuWiiqWzk" +
       "chd6AU9yc0uwkIiJXOTobBWzUUW8GdYQCkDqlMT8TrDpvllHsYNB\nfqerRx" +
       "SDA8Kia1LGl3l3ilyla0w8tkP76JgrRW34gE3NjVqH+S7VgyzNB/ICBNnUo8" +
       "3QpcmgTbli\nRqHA5SKwBOFrgB9KbMiSPhnhm5p35DAIuBOCmHwSpICO5R6A" +
       "57XunJbLLhFtdEDT5dTrN64qzgyY\nz1KX0XSRwUSPBFV7Kw2ymTA045ylnZ" +
       "QnUnLwCP8yLwvJrMMTKacbbgdU2CFa9AUMwqVyme5AwePK\nbCX4marJwhIw" +
       "cYcCydmGmptQm80ygS/DhmxbvUNLSZPDwSpRb67sKV1gGeDQqbJiRgbdrORm" +
       "ytco\nN9/CVLQ9m/XQy9A2XGxcjc3JymMqoBNXLNnveJRsyMWOVKM5jAULBe" +
       "tT+LTfZzp6RCGQOZ2BSKln\n09o16YO+UwbymMx95ASvjo58ZAthN8wiYVgr" +
       "PU3H2nbQfJ/T1SUkRQdnfwwOtUGabW4aFZftPG6A\noHJFTt09uD5RMb8QLk" +
       "FI0vMmHDbuQWcJj1icMLvWCma8eexQgfG3ZaPb1Kbd74Rhae/bQFqdKjRY\n" +
       "Gci23ZFeM7X6HrQya7bbnuCtGewU0jiI0bCWy5DdVZgrsdnhsMMgVAChnXns" +
       "1vSiJFVKpnDWlamt\nw54Lh4QlK2VqZloCHLrDHHAuHArIwBY+GSz3S3Wf41" +
       "3gmfi5YXStILpobhRCDnjLjMHsI+WayeYY\nF+JOVWXfDqgI3HiNMp2hTs21" +
       "mAKcybQPlSUNSqPOvQ4prXlig4tK3OsyFyZeHw+AUKsNRchixSXV\nEYldpw" +
       "PRpKyEGQwS7oWbkqZ4hLrWoa1d12xmGoQcS03gHdw5D3nChu06nvHny3p/UI" +
       "9q1c8zELWG\nmW7v6eNMglbzmXDUmzjBQx1Bp1RyqbJFz+q9EkeJV4ulpQ99" +
       "E/M1mSmMq24UzhtO5cxdEGoccKBJ\nXXBix5oIqkuzdGbEi80Sy+CeSGN5Sj" +
       "Srpq+wkyA17XbMXUUrbnkNCtLZ6pBVhFJRK1E5+DOVDpzi\n0IobmKXOHh2u" +
       "GH2P86swMjKgjtZBv2qd6fmE+Mx+1rA2h1JMQrfqJZxFsj8L2jLBcsDprFhO" +
       "DUlj\ntgfm0LHoedVdgogMt5WSwWIoLglZPVIIRIv2dBFX27DPB2/Vb2W9cT" +
       "QQ68CgRDADGfbLdYyW4XKB\nN2fkyPoX71QTKZecFaM9mdG8ETWqIA2DknEj" +
       "CgttWsMK1gFo0NTOAduikRpkmXUegHiV1RE9BKjm\n0zO/uFzKc6tVjJhpvt" +
       "RKEGTyaGvz8FJshfOJkyvOE9Rp7EJ0C2GebR4ucAz0FUBlvjWDmebQdUtq\n" +
       "CFZWp56NNuQ5VFzC1AHz+FEFcx6mZJ+DjtRePx92OpqeI3SKlEt+jpQdqmtU" +
       "ZR0ZYC3CDY4Ehu1Z\nfceZiSiIZYofZwgnA1LTAFJygtGAXea9n/W5BzHe2V" +
       "ufTvlqb0+RfbxZq+gaMc6UTAKMU1l9tFo3\nHVJgvBEY9Xbb1yUDMlv7YERF" +
       "2PBm7vs7GYI7kcJYlhXJY6+2tY7Z+vQ0QEtvl6wubB+Fh+Cci5Ys\nB2ithO" +
       "XFIDmH4wnLXrZ+NpY4ckclqWDI7EDaMa3FCkCSAev66TJQ0qVqTKuDqGLkcc" +
       "HynrY/O0K+\n4+ZAsC/Mks55xwe2LdDzVmKt5wbuw3P8NLfsHrisnOV+9GbO" +
       "KPj4tO/4QrG4enpSQBbUUwxvPYKW\nMBmvuguyFeZZ7zkqqG4Qa1YwWBUYlx" +
       "YZmEEyY+Tky/NDCjQnr2Z5MypU1CUFDCz1qV6oiw5dACJT\nbXbZSgRkLznK" +
       "ndwwc2FQu8u2Ws1b7UhDJNYuVz3A4fuQ8bwU0qss7nZQFZ5BDD4sUL1WpxWF" +
       "K24v\nXzBssVhk6fbcYzzSevhuY5a5wBu4Qp1ZnkNshC5zNVxbGLTbHHi/rS" +
       "4bgQbXidyc+trbWimRT2FY\nlaoqWfVQtLfhc1Ud8LVs8zt7lkuDVUUDpwbR" +
       "3KPV5ZLNLY3XE1nKUawvt7SPBuK8o6vT/nDMVgsc\nm86tDcwA4GWn4vWGa8" +
       "AViQorE9W3uxPSrFXj0G83e6OQx+1HPffhugrmyDHHHKLYBBLo7LiQ1Daa\n" +
       "CZtY4kw1jzI3s/Bgn2z/6Fe2LS5FrDkPXNfHcawXVextaaFKhsXQ9hU4kzzJ" +
       "V2mx7scNIEt0WgxR\nYHHouflMwadNHyLEkQ2cUoNs15ZZ1wiaI7UsRt5knl" +
       "a3pJ1xLLYZ1mV2plVy5VhzvjZR6GKPz4Y9\npEi4tEiJtot7ZSqvI5ur9rhe" +
       "RIR8xGxTAbBGZho0VaymVKU9Vpca2dG1J4xbQl4viEs8L9YHTI8S\nkTLBpO" +
       "oxe1ZJmAFH0xlmWQWooZ7WkCjdgdw8IMJ4rPqh6QDnatZ5vp6nC3yGi4ZgYH" +
       "lXeLXreZan\niiAERWWkFqycCGpN99XUoLnZyrz4s/lFswYfxgFTQFVINGRe" +
       "OAVbKNrRx3SxqsuV2hayOpMkxt6J\nNZ5u5d3CEqUhaAkQwGNpWK2mgKxybK" +
       "PXaz1PxMIbbcI02+a8TmvQPTnc6cA5eKqJBTwXNIa49Fh2\n5kuTdTaxYVf1" +
       "ukQUD90ulr2sy8m0XGAkMkhDJM+cEnEWBFJhKrlwF0CzG3e0drpxCRkwQA+2" +
       "e3+Z\nt0ZE9JZqKTM46laOKq76TKtI88yjzGZaMzhxgIqsPJJnFFLCmSGexU" +
       "KMdAFmow7ZnbpVJ6ngYGBj\nxUsihgrrhLMcMxmTqJUcWXi2ETNsrCKHmpty" +
       "yHnITNE41Oz81JdAA+K2anM9rctloxwh6QIh0YqH\nTniOIBmBN4gspUgaW3" +
       "tFqg+0tgPLS8c0+olVxKlfpRK+9Cs87NhsFdNWDUSXgyMeC6Hi7UINVsAc\n" +
       "LDBixixkK2zmIlr10pB2cDRrKtBxlGZ1kHM/2KZ+PDW0mm0Rzxj3yVpd2nAd" +
       "oTw/z8I+QgDLS3KH\nIObj9r6nN3I9g+M9NzuqOkQJgGyN27kSBSsygBaneX" +
       "riyelJ3Zd90s4HhjuPG3bfuSRzONTGTbUc\nrnaSsj2HGHvw+jDdzoq4XrIn" +
       "e7Xd1p0kpRfC8hS0pGVV1XABgObTZi2Ia7N0dnB3xE0qODpLkcUs\nDeUXPA" +
       "FtFB47hWSdLLzonLYSLB6jc2BRp3OAtydk30Y9TYx7JDRh3YCcVshm70ASXI" +
       "EASoBuRVvK\nJUcGHfBRO9V5exB5isTn7cWaq70YbnMIwBcgp9EzNlljdS5i" +
       "3b52KgzYq/CUsEJCD/SB6KMggJAY\npeRdy1BZRQV6CYw2DiioxxKTxYwOO3" +
       "emf16isM5jdpAFnHihKEVEzHjRm2DlTQliuNgKG1Ony0GW\nZ0KPG4lFnKC1" +
       "Dl6Aea2NTyF1MI987dRsM8U/+S67W2J7uuwQa2kc18kFXO6OLXaaZ+rUQTQA" +
       "wIHt\nouiEfW+vVDmDNMNjFgWcbEkCzyQ6609JLK8szM1dH7ZnFA+qPHchZS" +
       "YjCe9wXAo7cC/6a3MqJCGw\n1xkaLTQG73KK8AlBi/OyptCz7C/2ZdP0OK/1" +
       "p42VcQgMMyCYNtRsGNYtfrF9xcJaE3YpNzSUarrP\nFzKjV5C+5va4X6BctA" +
       "BOOFKrAhcYxGkBODkBDI65MWES3WRYoxgGvd2curXGqIXDKmXepZRPkluy\n" +
       "nCLqCkYXkuUk2Ek+IHjeuu4JdCUNXc62NLGwzuOWtuTlTr0AcEdo5moPEv6e" +
       "2Es5YPrROs2QbAuS\nQ+Cnw9SY++F6iOan7KRQ8EWU08jSWisFOoUtWgdWV+" +
       "Rh5CVlTka3I4mcA3B/Bxwvg1dkmM6eIQgj\nMThnzPExeVpYY4ScQYN0YJvF" +
       "txyUq8qF0pycr1Iu3e2U9hJ4p0w5Vh2EqAACXWgww0e3K/GqgNFu\nXQpUpi" +
       "f84Rzx00EWkw0DRDFu93s715zgRBfgqdiQhLSGNAvguWitBIoyU3nUWkLDqT" +
       "2lrMn4Z6Cb\nF0S2w9aIPLMXg7gPpv1hr9sDAWfd6VIK9HJllI505PdCRVry" +
       "XnEjeYAqL5nts4PMk0M0dBE5D+pt\njqvYfrFU6gNo0fY83aAUNU1UBxWFHi" +
       "HH/c2yPFkMzmgmEeXjpm/RAMfSOWzkvbyowoHuFMqzBprt\n+4Ir2WyzPvZG" +
       "uTXaHmtnc9m+9NMIgccNCLXjaA9g8ICkcO2YqbOVLlMat9cgBRUD8CzvsIJd" +
       "Lw/w\nWi/4odmpLSJSdGM6xWWAq+KktjbarqcLBq+TWaRLluEba8AOLXlGFX" +
       "mBSqfVsbOy5R4gJZQHsRYV\nW6fYLlJ2k+3TgY8qyRa4EA6kdiNj+8O4pZ7i" +
       "p8Qe1ht9FhX5wq1zGJ+nYg2xWj+mnTMCA40abcN5\nl4CFemzBvGkh0w6KtU" +
       "XHTW3mTdQYNTijKEcEhekYyFhJO2HUMqlKxqAEs3DoIEDnVDmyR0HFjqHh\n" +
       "DHgoEgjmnJYaS890qTcQDGwdXdZSFst8iotpQ9en/Mpsm2KTLBQUUHaE7Ied" +
       "wSz9uc8KB3DnAofK\ndJg9OlhNk8ZlWyBiebG1y3xD2MjeRpNmUc1KkQtpud" +
       "GmkAC4yrZFih7jQm1HrY77sUZXNYwkCd6q\nhZiJe8JSTUNNG69J6+NsPjJU" +
       "h45xACVzPsY4yG3s42EMLne65gKgXSpFTFNHC5KoYpfCmxCI1+VG\nN9EwH5" +
       "+i+WR3akOCnV+y3UzBQv3iNnADY70T7xol78Pe1HOVDQ9Te53xJi1Eh8Usap" +
       "yAXWEJZMk7\nEdqCW9LLSg4W9bIsSn9eAxqJuiBpa6e9fcmMwCxKAxOcWZPZ" +
       "S7ZjFGTqyWdqmVEWLh6TgLhkJ4Hb\nZdba8eHlQViXi0MQuWoLZvFyYaFQud" +
       "mcFy2bLzigGVR3PU+keXdJpajTbFmeng+CVWjqSjN5kInA\nM+yUq3yB95iy" +
       "T8x4dtp2HBXA585nkRU2opPJMTpqO7cCBG22Atm2wfbyxV5YtltNyzVwDFQY" +
       "W2Tj\nswJorgYJRyGgD9BOOLflXBpOh8DJF858TUlr3oI4VvTFoWn1lsYAeL" +
       "UkYinjqXXNAnUwtZFg6aWL\n3Z4n6BnW4jR5ODP5ai3koLypvEMuHMtyFFhi" +
       "1uAWPaIFHVCtsVZ9JPQP80PYbXEfpYTzFg/5ad6c\nJaL0ASMvl9sw2zKnbM" +
       "3FW/S8PO5UWVzjsr2xUjleCWKUnVhmtzlDEQXxRgQZ3qpl4CXIjfVJ2A9L\n" +
       "cFrwjmJAu6Wi9zExLNb4dj7YrO1pB6mK1lyvAvI2FCwCVFhbUJyWqhfzwgXQ" +
       "Ye62ytKPKKUPOTBl\nIl33p4fVTBWFnQKtFGC5AVYz/UBtmbaHum1LyD0eDB" +
       "S+qXXeqM/NWm7BwiTYhViKOKqHceYgjZXi\nF7faGg3ETGf0WTcJJWa9ouHh" +
       "bZ1zHN6IjqUvujPIn2Icr1p3bgF7TES2axBI4AJfjo4QgDrc6hea\nUuUliS" +
       "i9JLXFdLgoJUx1ZpsA3kHtR+6Pfbk447Go79arxaaKcSfdE3aFLjKJMyIQxA" +
       "keHJYIueuE\nsuEQIT0gIYYSaIJMsSKNuypQ5wsCaloKpg+BthmOy+Xyy1++" +
       "vrVmv68X+h+/9SPe75ffvce/3nzv\n9hK8+w5EqslrplVWhWlX1eT195vyN7" +
       "L3enkf+w4t5Yef/PjX/8K7f+XXn2/hTa4v9j/73Trht5f6\nX9P/+xtfNX/1" +
       "x66oV8TtuHaV5T8au4");
    java.lang.String jlc$ClassType$fabil$1 =
      ("0bP131eSL8rfH/pHX2Ee3z/4HGf+mPPb/+i+Pyn/+emI/s\nN5tP718Mwn/2" +
       "wq3ndtem+8CXB88ivfdsc25auFVdpIf3W3QfbKtKRWa7Tl24T9f9i9/e/ref" +
       "e3nx\nt1+YvHS/d3ml8MXnOoEf8rJizDHXBZ586jCtgiJrn87cbwtee6fj8a" +
       "nxeONxG+x2vd5889bke6t7\n2gp5xgleuI7l+yZ/cD2rox4/9rSrtywKs792" +
       "fbs/8c3P/uV/av7VFycPdpOXynBwb935BzdaD+55\n2hMy17N/5eWxe1z/W9" +
       "XkFfdSm3F5u73O71A21eRVK8ti10xv/x89I9wPj8eHHwv34f9f4Z5w9cId\n" +
       "2JOG3afuN+pL166LsOofcqblxt+naNn7ol3/xffluDYqR7BXr4q6E+PB23e9" +
       "yof3erfMqPP/e+v2\n/1XeyTON+u8hx1BNvnAOvXd8t7rTyaOrTh7d/3jhEX" +
       "cFrMfU88b96Ws/+JMf+Hzp7iMb+4vf/PEf\n+ZX8zX9+C4H3P4R5lZu85tVx" +
       "fL/9fW/8Sl64Xnhj69U7r79TwFerMU6eGuza8hsvN3H+9B3ET47W\neJoIfy" +
       "p/Yue37tv5rmPf/R8BjaJAqyUAAA==");
}
