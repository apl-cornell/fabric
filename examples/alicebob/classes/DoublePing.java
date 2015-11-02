import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Map;

interface DoublePing extends fabric.lang.Object {
    
    public DoublePing DoublePing$();
    
    public static final java.lang.String jlc$CompilerVersion$fabric = "0.2.2";
    public static final long jlc$SourceLastModified$fabric = 1446065567000L;
    public static final java.lang.String jlc$ClassType$fabric =
      ("H4sIAAAAAAAAAL1aC3QU1Rm+u+RBIEII7/cKAQU0y0NADZZHQkhwITEE1Kis" +
       "k9m7ycDszDAzGxYoHqwKiDanRUA4CtVTOAVF0Fq1RwRfVVCsL6wCFvFweg5a" +
       "xVZba7Ei/f97Z+e1A9qe2pyzd2bu3P+///0f3//fO9l5iuQbOilLCs26JJab" +
       "SzRqlFezh3pBN2iiUhYMoxG64+KpHi9vzaw8enmYdIiRYkEUqWHUq7IkLjHJ" +
       "gNgCKRll5FFZaKZytFJVkvxtBYwWBUVVJFGQ44phki6xBUKbEFWoGZ3bUAvv" +
       "eypCihqaINIqqlElQRVRojCwKx+YNiU5OoeaFRmdDISJLDlnSkkuJMyzhMt4" +
       "+sHXEhuv146HSUET6SgZcxVDSNIYKRLSZquqSybIWuJiGpMME+bvLKogly5I" +
       "imksIreQ/BgpkaBHUExJMGmiWldTJrkwpsFELbJqRmnGjGqCLqSsNdczPQGn" +
       "AtabZdJR09U2KUF1kwzO0VC99S6GT7i0SJa9tb4cC6wfFV137/ySX3cgXZtI" +
       "V0mZYwqmJIKmTZCniRSnaKqZ6sbURIImmkg3hdLEHKpLgiwthYGq0kRKDalF" +
       "Ecy0To0GaqhyGw4sNdIaiIhzZjvRZEwladFU9exyCpISlRPZp/ykLLSAkXo5" +
       "auHLq8Z+0EUnUCfVk2DVLEneQklJoC58FPYay66GAUBamKJgL3uqPPQPk5Ry" +
       "y8mC0hKdY+qS0gJD89W0iQrud06mFWgIQVwotNC4Sfr4x9XzVzCqiCkCSUzS" +
       "0z+McQIr9fNZyR0hsye1L1NqlDAJgcwJKsoofzEQDfIRNdAk1cHLKScsHhnb" +
       "IPTauzpMCAzu6RvMxzz148+nXDLouQN8TP+AMXXNC6hoxsWtzV3eGlA54ooO" +
       "KEZHTTUkNL5n5cz56603FRkNMKCXzRFflmdfPtfw8vUrHqKfhEmnWlIgqnI6" +
       "BX7UTVRTmiRTfQZVqI4hUkuKIHAr2ftaUgj3MUmhvLcumTSoWUvyZNZVoLJn" +
       "UFESWKCKOsO9pCTV7L0mmK3sPqMR668Efj0ICd9pXSGQm6KtaopGJaOVJpPR" +
       "Kl3VmtVMtEoV0ykKgQwQpCtUlqOCJkdbJDPKQQ5iV0hpMlgU4kKkzWpz1NBF" +
       "IEs3y7QefKocxmk/KPcMrq3H4lAI1D5YVBO0WTDAhpY/TauXIWRqVBmwIS7K" +
       "7XtrSfe9m5hPFdk4iRzC4AcD/Jjhpl2Xnjb9813xg9wfkdZSqkk6ORKBEMUY" +
       "V+WAx+WAxztDmfLKLbUPM/cpMFic2XRFIP6VsgpIniGhEFtBD0bMnAZMvhDA" +
       "A0C1eMScm2bevHpIB/BWbXEeGA2HDvGAd6WDMLUMbEVw80OTtZvbx/efFCb5" +
       "TQDCRhVNCmnZrK+cpqYVAKsedlcDBRxTGHoGInihJjIak/TOwV6OuUCmO0yQ" +
       "bCiotMwf4EFidl310T92b1iuOqFukrIcBMqlRAQZ4reZroo0AZjssB8ZEZ6I" +
       "711eFiZ5YHVYmwkrQ5Qb5J/DgyQVWVTGtRTB8pKqnhJkfJXVSiezVVcXOz3M" +
       "F0ux6cXdEi3qE5AB+lVztM2HX/94XJiE3bN0cEEMPndnYNLN8YlGnVLI3cc2" +
       "1t+z/tSqG5hDwIihQXOUYVsJ0AJJFJR2x4FFR45/sPWdsONEJmRYcFxJzLDZ" +
       "e56FvxD8vsUf4gR24BWyRaWFUREbpMDvk6oa12lKNSlKMdyRE6BLBviEZRhl" +
       "c5WUmpCSkgAhgt78TddhY574tL2EW1uGHq47nVzy3Qyc/r7TyIqD878axNiE" +
       "REydTiniDON43N3hPFXXhSUoR+bWtwdu2i9shtAENDWkpZQDJNMNYfa7nOll" +
       "HGsn+t5dic3FELT+lzBdfyc0WYhA9SHx0iQu9vpiSFSrrvqQmb6TaFd2yAZW" +
       "0hnzfEu2FByYE2y1zmt0/97+maxZ8m6KJL6IDLmB+XznBDVEXdKybgVZoZMh" +
       "AbKCUmmChSqAk6nOBCXZxZUuKIYMdubh3cheTs9oOqb2NkFn1uBOm0EXtcWo" +
       "x5otLk68a5WuDl0zIWypq5S7mJV8cjLQnfi2t4ZtnwyUlQkOSxFNjMhZPJkK" +
       "mr00W1xnxbMLbEvBjSp0SDJKGhen7XsgdPT9Dw7xND+UCWkTukiya4uLm3ve" +
       "+0zpw2uncorBXoqc0ZNGV66MX/bY71lMoZ8N8pujgQqQPbi94uIXW47ShvGn" +
       "P+Nhry5W/MWsBvlBlDQBC1rrDutgnXFBHdSCVH1yvMtiP+GnD+w+9UH9FBYS" +
       "LgtjtZJTMFsuZCPWNHYb8yYWW57yRlWzRYqL83u9OWrAM9ff6VaTj8A1un3H" +
       "/YV/ueT0A2zZtp8N9fmZTXBeX8N2CpeXQY7HQG4h3Xbq3fPYOwfaaj4L9oMg" +
       "islje+z7qE/fZZZlccJZ1qx4uSbQ2NfCnsgxdqQ89sKzhQ2vuozNLAgqWMwG" +
       "cntiO9MxwLXAeFiQPqeppqmmXFq9aujRBRVn3vpNNsJm21oZ4V2gj9K9zIKR" +
       "e/q2v7+iLsujgS/1OtdSm3jXJC3DonM+e6oyEHp8pUqNYLRCcjosv9e0/tjI" +
       "QVzZruRlvX+66o71G3771GW8milGNJg8hdelfMYajckiclE0jxi+x3l8aCs3" +
       "kGYbx/vIr5EsPuPDCNaOwqbcBeqjvSNhh3yuDQrbXG39ybotibptY/hSS71F" +
       "/3QlnXrk3TOvlW/88JWAorHIVLVLZdpGZdecWNd5N+Wz2N7NKWYmPlhVNuCF" +
       "Re3/u5LOwv+g6m2wb/V+YXbM2vnKjOHiWnaKYRVuOftRL1GFWw+AUnxW1Cj2" +
       "dGFWGO7NFX3h1wtyxDbrer87V/AyK9CkYbyNmlh249Y+Y3MNI9euFrf7rGu7" +
       "i6sv7Yc5O8OTbpmiaILvJ7dt37mronjHNhbrRQwNIA+almo7IkX2mS+xmy3M" +
       "MBRmnCXEs9b1MfcSYd5+fqiZqrdYmX77BS8dPNWn+gDL9GFRwqIhp3RO0HPp" +
       "P63BLt3tB+E2CY+gfCzmCa6qG0f+yF5AedACnvbm8zpkl1J1rVWyEnpETUZ4" +
       "QR0R9Ba2DYxo2MkPKyIpcNXIxc0oGE1EhGa1jUaal0SWaSdW/mz5CM1GSRvl" +
       "KgVFUc2cFF0gSk+ciibPZBGumoNDGzZLufdgs+wcJsfnW7G5jbvP7dis/iFW" +
       "hIzv+s+Ey92xzlUWKpBpuFPO6bwzfdveSw9nl96NIRy73cDadT6+IY5BzoCN" +
       "2EAZmoeys25CgurdnExoyWG5aI/op5vr/nni0awgP+Lrs/LqGn65z9dpkpCV" +
       "ebjKrRdV/FLDO7H9pZOyVntzRW7XPNtroyj7WMtb91nXPX5k2W7XGL5sXLUE" +
       "ggjKTicdzz0zs//pU1e/wKEeD12CTnKn8pNeeMvDaLZXoNGWIM8ECQSylOeU" +
       "v35BPPXvLQtuv+eaC1tP8vw00hswuZRO1NzdXP/4rD9+m3bVBZ4yzwIhezEx" +
       "PMQYU9tv4qSmJ0EDHZpIkQKpTZ+dliH5hGUpYLPNaAJhKRPgUc5cDapqjv+5" +
       "vOvx8R/fyBeWi472YDXviooZe2qGhz3HdzhLd17PPEIwjDwp1yNYXGy8e/97" +
       "EzZ9tJaxyJfd+dF/EumjlLfK+2N/X/I6l9Ifqy5IjYtjH0p9GR5S8FKYFEJa" +
       "Z8laUMx5gpzG/X4T6SQZlVZnjFzgee89yuXnlhWuI9OVvnMMN/znmZ7EW+rN" +
       "SuMtL3w+KCuFCLt5Ojj3hkyYVFIEmZdVdjC7sdQf2E4wz8NmByN9kXU8xEzF" +
       "yXbx8Y9x6zEZsHnWKdJ3uIv0nPMhKQU+0GYdFtPV69acLW9fx6OWn6gPzTnU" +
       "dtPwU3U3oMIsF55vFkZRfXL38j3bl6/KRhRs/IpsOPlhUwvyfYOnF2zvZtrC" +
       "5nmmPLQN3uy3VXmQm8p2Bob2ky0nOGhdX/RAk72AQ7CAxu+1ALgxvscaTqzZ" +
       "emLlWkz5PC9guxZSEpJzN/LKWWXJ92aQnCbplj1ZAPpIc7buOWztarC9OXBP" +
       "gs0RZrljjqMd8W44crvmOWQnnJx0JDdNebue5F1WiJ08R4jh7b5sfHF9gL8H" +
       "fH1kR17c3yuJdHL0n/Q3oFBl3/RmC+ilvk+QyDLkxVUGPi4++8f1Wb/qnq96" +
       "Mz6FFjgxmJqtKuwh4OuOi/6vO49/8vYFA3exqjUPj+wZCvk/i+V+9fJ8zGKL" +
       "L+Fux3r6mqSEbTgRC8v5Zy32YqBJivG0XYPUh44IqKMR0K/bcbH9AzZ/w+Zd" +
       "J1j+zFyEmQKbz9jI9uDiyRc6hYS7ISmCd2XWtadJxP/ugwh+/nC+OmZ7HfSf" +
       "nhEpO+azvrv8P6bJ+KKwGpdcZ0Xf+9b1bXcUssvX3xlyNscZ2DnT4vSudT3k" +
       "5ojttzwIvVQ11uh3gqhygvesN3htVlcjq9kWi6PW9T2fAKF8Hs5eqpg1+nAQ" +
       "lTvmUYBQgUcA8ObujjfbSg92aKaDL7Fh8buJjXrFylB4eQ1Aok2VEsEl/JCg" +
       "/1pwf1SPi/dPeeOb/S2LXoVcCTWK/fWsspWKC2kiE1Ag+hgsJMvv+t2q0lsZ" +
       "aBRJRqOeNkz8Pl4kZo9VvEeV+F3L/vDMtw78GCrUG0QelVMMe+bzVMI9b/zV" +
       "zOO/mNyHl2IX+U8APWROGRxasKkuVnj2OnvzGOitDIh7sErka5OEtYs81Q1K" +
       "O/A8vo59/qonFPk+VQ+ba1FQ5nYhohvbNBeuQX2GGeOiIGhbcT5ow+YrNuxr" +
       "lLSYyTvC8Tt8LAnwMWY1RpbxfDHVbF8vtezJvJ3XsedwdZJ7oBf4kSY0zntm" +
       "g+dpaf7vJZCCxo6p2ndg+H7rUNjOVTRjlrN/PMkeetkUu7fMnL3s8wn8lCcf" +
       "fHbpUpy0GLISL26srOSuB/3csrwKakb8q8ujRcPs7yPYZL8a5qwu4laiU5Cw" +
       "zXzoSqeMGJ1bWdhd2A7S/g17Alz/pSQAAA==");
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements DoublePing
    {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1)
              throws java.lang.Exception {
            DoublePing._Impl.main(arg1);
        }
        
        public DoublePing DoublePing$() {
            return ((DoublePing) fetch()).DoublePing$();
        }
        
        public void jif$invokeDefConstructor() {
            ((DoublePing) fetch()).jif$invokeDefConstructor();
        }
        
        public static boolean jif$Instanceof(fabric.lang.Object arg1) {
            return DoublePing._Impl.jif$Instanceof(arg1);
        }
        
        public static DoublePing jif$cast$DoublePing(fabric.lang.Object arg1) {
            return DoublePing._Impl.jif$cast$DoublePing(arg1);
        }
        
        public _Proxy(DoublePing._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements DoublePing
    {
        
        public static void main(final fabric.lang.arrays.ObjectArray args)
              throws java.lang.Exception {
            final fabric.lang.security.Principal p =
              fabric.runtime.Runtime._Impl.user(null);
            {
                fabric.worker.transaction.TransactionManager $tm10 = fabric.worker.transaction.TransactionManager.
                  getInstance();
                int $backoff11 = 1;
                $label6: for (boolean $commit7 = false; !$commit7; ) { if ($backoff11 >
                                                                             32) {
                                                                           while (true) {
                                                                               try {
                                                                                   java.lang.Thread.
                                                                                     sleep(
                                                                                       $backoff11);
                                                                                   break;
                                                                               }
                                                                               catch (java.
                                                                                        lang.
                                                                                        InterruptedException $e8) {
                                                                                   
                                                                               }
                                                                           } }
                                                                       if ($backoff11 <
                                                                             5000)
                                                                           $backoff11 *=
                                                                             2;
                                                                       $commit7 =
                                                                         true;
                                                                       fabric.worker.transaction.TransactionManager.
                                                                         getInstance(
                                                                           ).startTransaction(
                                                                               );
                                                                       try { final fabric.worker.Store alicestore =
                                                                               fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getStore(
                                                                                 "alicenode");
                                                                             final fabric.worker.Store bobstore =
                                                                               fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getStore(
                                                                                 "bobnode");
                                                                             final fabric.worker.Store carolstore =
                                                                               fabric.worker.Worker.
                                                                               getWorker(
                                                                                 ).
                                                                               getStore(
                                                                                 "carolnode");
                                                                             final fabric.lang.security.Principal alice =
                                                                               alicestore.
                                                                               getPrincipal(
                                                                                 );
                                                                             final fabric.lang.security.Principal bob =
                                                                               bobstore.
                                                                               getPrincipal(
                                                                                 );
                                                                             final fabric.lang.security.Principal carol =
                                                                               carolstore.
                                                                               getPrincipal(
                                                                                 );
                                                                             { fabric.
                                                                                 worker.
                                                                                 transaction.
                                                                                 TransactionManager $tm4 =
                                                                                 fabric.worker.transaction.TransactionManager.
                                                                                 getInstance(
                                                                                   );
                                                                               int $backoff5 =
                                                                                 1;
                                                                               $label0: for (boolean $commit1 =
                                                                                               false;
                                                                                             !$commit1;
                                                                                             ) {
                                                                                   if ($backoff5 >
                                                                                         32) {
                                                                                       while (true) {
                                                                                           try {
                                                                                               java.lang.Thread.
                                                                                                 sleep(
                                                                                                   $backoff5);
                                                                                               break;
                                                                                           }
                                                                                           catch (java.
                                                                                                    lang.
                                                                                                    InterruptedException $e2) {
                                                                                               
                                                                                           }
                                                                                       }
                                                                                   }
                                                                                   if ($backoff5 <
                                                                                         5000)
                                                                                       $backoff5 *=
                                                                                         2;
                                                                                   $commit1 =
                                                                                     true;
                                                                                   fabric.worker.transaction.TransactionManager.
                                                                                     getInstance(
                                                                                       ).
                                                                                     startTransaction(
                                                                                       );
                                                                                   try {
                                                                                       if (fabric.lang.security.PrincipalUtil._Impl.
                                                                                             equivalentTo(
                                                                                               alice,
                                                                                               alicestore.
                                                                                                 getPrincipal(
                                                                                                   )) &&
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                             equivalentTo(
                                                                                               bob,
                                                                                               bobstore.
                                                                                                 getPrincipal(
                                                                                                   )) &&
                                                                                             fabric.lang.security.PrincipalUtil._Impl.
                                                                                             equivalentTo(
                                                                                               carol,
                                                                                               carolstore.
                                                                                                 getPrincipal(
                                                                                                   ))) {
                                                                                           final Message carolMessage =
                                                                                             (Message)
                                                                                               fabric.lang.Object._Proxy.
                                                                                               $getProxy(
                                                                                                 ((Message)
                                                                                                    new Message.
                                                                                                    _Impl(
                                                                                                    carolstore,
                                                                                                    fabric.lang.security.LabelUtil._Impl.
                                                                                                      toLabel(
                                                                                                        carolstore,
                                                                                                        fabric.lang.security.LabelUtil._Impl.
                                                                                                          readerPolicy(
                                                                                                            carolstore,
                                                                                                            fabric.lang.security.PrincipalUtil._Impl.
                                                                                                              topPrincipal(
                                                                                                                ),
                                                                                                            carol).
                                                                                                          meet(
                                                                                                            carolstore,
                                                                                                            fabric.lang.security.LabelUtil._Impl.
                                                                                                              readerPolicy(
                                                                                                                carolstore,
                                                                                                                fabric.lang.security.PrincipalUtil._Impl.
                                                                                                                  topPrincipal(
                                                                                                                    ),
                                                                                                                alice)),
                                                                                                        fabric.lang.security.LabelUtil._Impl.
                                                                                                          writerPolicy(
                                                                                                            carolstore,
                                                                                                            fabric.lang.security.PrincipalUtil._Impl.
                                                                                                              topPrincipal(
                                                                                                                ),
                                                                                                            carol).
                                                                                                          join(
                                                                                                            carolstore,
                                                                                                            fabric.lang.security.LabelUtil._Impl.
                                                                                                              writerPolicy(
                                                                                                                carolstore,
                                                                                                                fabric.lang.security.PrincipalUtil._Impl.
                                                                                                                  topPrincipal(
                                                                                                                    ),
                                                                                                                alice)))).
                                                                                                    $getProxy(
                                                                                                      )).
                                                                                                   Message$(
                                                                                                     "Hello, Carol!"));
                                                                                           carolstore.
                                                                                             getRoot(
                                                                                               ).
                                                                                             put(
                                                                                               fabric.lang.WrappedJavaInlineable.
                                                                                                 $wrap(
                                                                                                   "carolMessage"),
                                                                                               carolMessage);
                                                                                           final Message bobMessage =
                                                                                             (Message)
                                                                                               fabric.lang.Object._Proxy.
                                                                                               $getProxy(
                                                                                                 ((Message)
                                                                                                    new Message.
                                                                                                    _Impl(
                                                                                                    bobstore,
                                                                                                    fabric.lang.security.LabelUtil._Impl.
                                                                                                      toLabel(
                                                                                                        bobstore,
                                                                                                        fabric.lang.security.LabelUtil._Impl.
                                                                                                          readerPolicy(
                                                                                                            bobstore,
                                                                                                            fabric.lang.security.PrincipalUtil._Impl.
                                                                                                              topPrincipal(
                                                                                                                ),
                                                                                                            bob).
                                                                                                          meet(
                                                                                                            bobstore,
                                                                                                            fabric.lang.security.LabelUtil._Impl.
                                                                                                              readerPolicy(
                                                                                                                bobstore,
                                                                                                                fabric.lang.security.PrincipalUtil._Impl.
                                                                                                                  topPrincipal(
                                                                                                                    ),
                                                                                                                alice)),
                                                                                                        fabric.lang.security.LabelUtil._Impl.
                                                                                                          writerPolicy(
                                                                                                            bobstore,
                                                                                                            fabric.lang.security.PrincipalUtil._Impl.
                                                                                                              topPrincipal(
                                                                                                                ),
                                                                                                            bob).
                                                                                                          join(
                                                                                                            bobstore,
                                                                                                            fabric.lang.security.LabelUtil._Impl.
                                                                                                              writerPolicy(
                                                                                                                bobstore,
                                                                                                                fabric.lang.security.PrincipalUtil._Impl.
                                                                                                                  topPrincipal(
                                                                                                                    ),
                                                                                                                alice)))).
                                                                                                    $getProxy(
                                                                                                      )).
                                                                                                   Message$(
                                                                                                     "Hello, Bob!"));
                                                                                           bobstore.
                                                                                             getRoot(
                                                                                               ).
                                                                                             put(
                                                                                               fabric.lang.WrappedJavaInlineable.
                                                                                                 $wrap(
                                                                                                   "bobMessage"),
                                                                                               bobMessage);
                                                                                       }
                                                                                   }
                                                                                   catch (final fabric.
                                                                                            worker.
                                                                                            RetryException $e2) {
                                                                                       $commit1 =
                                                                                         false;
                                                                                       continue $label0;
                                                                                   }
                                                                                   catch (final fabric.
                                                                                            worker.
                                                                                            TransactionRestartingException $e2) {
                                                                                       $commit1 =
                                                                                         false;
                                                                                       fabric.
                                                                                         common.
                                                                                         TransactionID $currentTid3 =
                                                                                         $tm4.
                                                                                         getCurrentTid(
                                                                                           );
                                                                                       if ($e2.tid.
                                                                                             isDescendantOf(
                                                                                               $currentTid3))
                                                                                           continue $label0;
                                                                                       if ($currentTid3.
                                                                                             parent !=
                                                                                             null)
                                                                                           throw $e2;
                                                                                       throw new InternalError(
                                                                                         ("Something is broken with transaction management. Got a signa" +
                                                                                          "l to restart a different transaction than the one being mana" +
                                                                                          "ged."));
                                                                                   }
                                                                                   catch (final Throwable $e2) {
                                                                                       $commit1 =
                                                                                         false;
                                                                                       if ($tm4.
                                                                                             checkForStaleObjects(
                                                                                               ))
                                                                                           continue $label0;
                                                                                       throw new fabric.
                                                                                         worker.
                                                                                         AbortException(
                                                                                         $e2);
                                                                                   }
                                                                                   finally {
                                                                                       if ($commit1) {
                                                                                           try {
                                                                                               fabric.worker.transaction.TransactionManager.
                                                                                                 getInstance(
                                                                                                   ).
                                                                                                 commitTransaction(
                                                                                                   );
                                                                                           }
                                                                                           catch (final fabric.
                                                                                                    worker.
                                                                                                    AbortException $e2) {
                                                                                               $commit1 =
                                                                                                 false;
                                                                                           }
                                                                                           catch (final fabric.
                                                                                                    worker.
                                                                                                    TransactionRestartingException $e2) {
                                                                                               $commit1 =
                                                                                                 false;
                                                                                               fabric.
                                                                                                 common.
                                                                                                 TransactionID $currentTid3 =
                                                                                                 $tm4.
                                                                                                 getCurrentTid(
                                                                                                   );
                                                                                               if ($currentTid3 ==
                                                                                                     null ||
                                                                                                     $e2.tid.
                                                                                                     isDescendantOf(
                                                                                                       $currentTid3) &&
                                                                                                     !$currentTid3.
                                                                                                     equals(
                                                                                                       $e2.
                                                                                                         tid))
                                                                                                   continue $label0;
                                                                                               throw $e2;
                                                                                           }
                                                                                       }
                                                                                       else {
                                                                                           fabric.worker.transaction.TransactionManager.
                                                                                             getInstance(
                                                                                               ).
                                                                                             abortTransaction(
                                                                                               );
                                                                                       }
                                                                                       if (!$commit1) {
                                                                                           
                                                                                       }
                                                                                   }
                                                                               }
                                                                             } }
                                                                       catch (final fabric.
                                                                                worker.
                                                                                RetryException $e8) {
                                                                           $commit7 =
                                                                             false;
                                                                           continue $label6;
                                                                       }
                                                                       catch (final fabric.
                                                                                worker.
                                                                                TransactionRestartingException $e8) {
                                                                           $commit7 =
                                                                             false;
                                                                           fabric.
                                                                             common.
                                                                             TransactionID $currentTid9 =
                                                                             $tm10.
                                                                             getCurrentTid(
                                                                               );
                                                                           if ($e8.tid.
                                                                                 isDescendantOf(
                                                                                   $currentTid9))
                                                                               continue $label6;
                                                                           if ($currentTid9.
                                                                                 parent !=
                                                                                 null)
                                                                               throw $e8;
                                                                           throw new InternalError(
                                                                             ("Something is broken with transaction management. Got a signa" +
                                                                              "l to restart a different transaction than the one being mana" +
                                                                              "ged."));
                                                                       }
                                                                       catch (final Throwable $e8) {
                                                                           $commit7 =
                                                                             false;
                                                                           if ($tm10.
                                                                                 checkForStaleObjects(
                                                                                   ))
                                                                               continue $label6;
                                                                           throw new fabric.
                                                                             worker.
                                                                             AbortException(
                                                                             $e8);
                                                                       }
                                                                       finally {
                                                                           if ($commit7) {
                                                                               try {
                                                                                   fabric.worker.transaction.TransactionManager.
                                                                                     getInstance(
                                                                                       ).
                                                                                     commitTransaction(
                                                                                       );
                                                                               }
                                                                               catch (final fabric.
                                                                                        worker.
                                                                                        AbortException $e8) {
                                                                                   $commit7 =
                                                                                     false;
                                                                               }
                                                                               catch (final fabric.
                                                                                        worker.
                                                                                        TransactionRestartingException $e8) {
                                                                                   $commit7 =
                                                                                     false;
                                                                                   fabric.
                                                                                     common.
                                                                                     TransactionID $currentTid9 =
                                                                                     $tm10.
                                                                                     getCurrentTid(
                                                                                       );
                                                                                   if ($currentTid9 ==
                                                                                         null ||
                                                                                         $e8.tid.
                                                                                         isDescendantOf(
                                                                                           $currentTid9) &&
                                                                                         !$currentTid9.
                                                                                         equals(
                                                                                           $e8.
                                                                                             tid))
                                                                                       continue $label6;
                                                                                   throw $e8;
                                                                               }
                                                                           } else {
                                                                               fabric.worker.transaction.TransactionManager.
                                                                                 getInstance(
                                                                                   ).
                                                                                 abortTransaction(
                                                                                   );
                                                                           }
                                                                           if (!$commit7) {
                                                                                }
                                                                       } } } }
        
        public DoublePing DoublePing$() { ((DoublePing._Impl) this.fetch()).jif$init(
                                                                              );
                                          { this.fabric$lang$Object$(); }
                                          return (DoublePing) this.$getProxy(); }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        public void jif$invokeDefConstructor() { this.DoublePing$(); }
        
        private void jif$init() {  }
        
        public static boolean jif$Instanceof(final fabric.lang.Object o) { if (fabric.lang.Object._Proxy.
                                                                                 idEquals(
                                                                                   o,
                                                                                   null))
                                                                               return false;
                                                                           fabric.lang.security.LabelUtil._Impl.
                                                                             accessCheck(
                                                                               fabric.lang.security.LabelUtil._Impl.
                                                                                 noComponents(
                                                                                   ),
                                                                               o);
                                                                           return fabric.lang.Object._Proxy.
                                                                             $getProxy(
                                                                               (java.lang.Object)
                                                                                 fabric.lang.WrappedJavaInlineable.
                                                                                 $unwrap(
                                                                                   o)) instanceof DoublePing;
        }
        
        public static DoublePing jif$cast$DoublePing(final fabric.lang.Object o) {
            if (fabric.lang.Object._Proxy.idEquals(o, null)) return null;
            if (DoublePing._Impl.jif$Instanceof(o)) return (DoublePing) fabric.lang.Object._Proxy.
                                                             $getProxy(o);
            throw new java.lang.ClassCastException(); }
        
        public fabric.lang.Object $initLabels() { this.set$$updateLabel(fabric.lang.security.LabelUtil._Impl.
                                                                          noComponents(
                                                                            ));
                                                  this.set$$accessPolicy(fabric.lang.security.LabelUtil._Impl.
                                                                           noComponents(
                                                                             ).confPolicy(
                                                                                 ));
                                                  return (DoublePing) this.$getProxy(
                                                                             ); }
        
        protected fabric.lang.Object._Proxy $makeProxy() { return new DoublePing.
                                                             _Proxy(this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                               java.util.List intraStoreRefs, java.util.List interStoreRefs)
              throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
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
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        final class _Proxy extends fabric.lang.Object._Proxy implements DoublePing.
                                                                          _Static
        {
            
            public fabric.worker.Worker get$worker$() { return ((DoublePing._Static.
                                                                  _Impl) fetch()).
                                                          get$worker$(); }
            
            public java.lang.String get$jlc$CompilerVersion$fabric() { return ((DoublePing.
                                                                                 _Static.
                                                                                 _Impl)
                                                                                 fetch(
                                                                                   )).
                                                                         get$jlc$CompilerVersion$fabric(
                                                                           ); }
            
            public long get$jlc$SourceLastModified$fabric() { return ((DoublePing.
                                                                        _Static.
                                                                        _Impl) fetch(
                                                                                 )).
                                                                get$jlc$SourceLastModified$fabric(
                                                                  ); }
            
            public java.lang.String get$jlc$ClassType$fabric() { return ((DoublePing.
                                                                           _Static.
                                                                           _Impl)
                                                                           fetch(
                                                                             )).
                                                                   get$jlc$ClassType$fabric(
                                                                     ); }
            
            public _Proxy(DoublePing._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) { super(store, onum);
            }
            
            public static final DoublePing._Static $instance;
            
            static { DoublePing._Static._Impl impl = (DoublePing._Static._Impl) fabric.lang.Object._Static._Proxy.
                                                       $makeStaticInstance(DoublePing.
                                                                             _Static.
                                                                             _Impl.class);
                     $instance = (DoublePing._Static) impl.$getProxy();
                     impl.$init(); }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements DoublePing._Static
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
            
            public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
                                   java.util.List intraStoreRefs, java.util.List interStoreRefs)
                  throws java.io.IOException { super.$serialize(out, refTypes, intraStoreRefs,
                                                                interStoreRefs);
                                               $writeInline(out, this.jlc$CompilerVersion$fabric);
                                               out.writeLong(this.jlc$SourceLastModified$fabric);
                                               $writeInline(out, this.jlc$ClassType$fabric);
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
                this.jlc$ClassType$fabric = (java.lang.String) in.readObject(); }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() { return new DoublePing.
                                                                 _Static._Proxy(
                                                                 this); }
            
            private void $init() { { { fabric.worker.transaction.TransactionManager $tm16 =
                                         fabric.worker.transaction.TransactionManager.
                                         getInstance();
                                       int $backoff17 = 1;
                                       $label12: for (boolean $commit13 = false;
                                                      !$commit13; ) { if ($backoff17 >
                                                                            32) {
                                                                          while (true) {
                                                                              try {
                                                                                  java.lang.Thread.
                                                                                    sleep(
                                                                                      $backoff17);
                                                                                  break;
                                                                              }
                                                                              catch (java.
                                                                                       lang.
                                                                                       InterruptedException $e14) {
                                                                                  
                                                                              } }
                                                                      }
                                                                      if ($backoff17 <
                                                                            5000)
                                                                          $backoff17 *=
                                                                            2;
                                                                      $commit13 =
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
                                                                               RetryException $e14) {
                                                                          $commit13 =
                                                                            false;
                                                                          continue $label12;
                                                                      }
                                                                      catch (final fabric.
                                                                               worker.
                                                                               TransactionRestartingException $e14) {
                                                                          $commit13 =
                                                                            false;
                                                                          fabric.
                                                                            common.
                                                                            TransactionID $currentTid15 =
                                                                            $tm16.
                                                                            getCurrentTid(
                                                                              );
                                                                          if ($e14.tid.
                                                                                isDescendantOf(
                                                                                  $currentTid15))
                                                                              continue $label12;
                                                                          if ($currentTid15.
                                                                                parent !=
                                                                                null)
                                                                              throw $e14;
                                                                          throw new InternalError(
                                                                            ("Something is broken with transaction management. Got a signa" +
                                                                             "l to restart a different transaction than the one being mana" +
                                                                             "ged."));
                                                                      }
                                                                      catch (final Throwable $e14) {
                                                                          $commit13 =
                                                                            false;
                                                                          if ($tm16.
                                                                                checkForStaleObjects(
                                                                                  ))
                                                                              continue $label12;
                                                                          throw new fabric.
                                                                            worker.
                                                                            AbortException(
                                                                            $e14);
                                                                      }
                                                                      finally { if ($commit13) {
                                                                                    try {
                                                                                        fabric.worker.transaction.TransactionManager.
                                                                                          getInstance(
                                                                                            ).
                                                                                          commitTransaction(
                                                                                            );
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             AbortException $e14) {
                                                                                        $commit13 =
                                                                                          false;
                                                                                    }
                                                                                    catch (final fabric.
                                                                                             worker.
                                                                                             TransactionRestartingException $e14) {
                                                                                        $commit13 =
                                                                                          false;
                                                                                        fabric.
                                                                                          common.
                                                                                          TransactionID $currentTid15 =
                                                                                          $tm16.
                                                                                          getCurrentTid(
                                                                                            );
                                                                                        if ($currentTid15 ==
                                                                                              null ||
                                                                                              $e14.tid.
                                                                                              isDescendantOf(
                                                                                                $currentTid15) &&
                                                                                              !$currentTid15.
                                                                                              equals(
                                                                                                $e14.
                                                                                                  tid))
                                                                                            continue $label12;
                                                                                        throw $e14;
                                                                                    }
                                                                                }
                                                                                else {
                                                                                    fabric.worker.transaction.TransactionManager.
                                                                                      getInstance(
                                                                                        ).
                                                                                      abortTransaction(
                                                                                        );
                                                                                }
                                                                                if (!$commit13) {
                                                                                    
                                                                                }
                                                                      } } } } }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 10, -100, -51, -105, -103,
    100, 79, 42, -98, 65, 21, -110, -99, 93, -68, -67, -24, -60, 52, 32, -1, -119,
    34, -39, 21, -40, -17, -42, -124, -74, -55, -100 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.2.2";
    public static final long jlc$SourceLastModified$fabil = 1446065567000L;
    public static final java.lang.String jlc$ClassType$fabil = ("H4sIAAAAAAAAAM16e+zs2HnQ3Lt39+5uNvvekCzZ5NfNJbo33lzPjOflvWzL" +
                                                                "jMee8fgxHnvG9nhJFz/HHr+fYzssagttHpUWSDdp0iYRQlsVwpICIiABKypR" +
                                                                "SkJKBBWq2j8gUQQiVYigQkD+AII983vc+/vdvVFRkDLSHB+fc77vfOd7nc/n" +
                                                                "O29+r3F/HDWeN2TFcm4mRaDHNzFZwUlGjmJdQxw5jpdV6yvqO67gn/nOr2vv" +
                                                                "u9y4TDYeUWXP9yxVdl7x4qTxKLmVMxn09ARcsfitlxsPqTXgVI7NpHH55VEe" +
                                                                "NY4C3yk2jp8cT3IB/6cB8PVf/unH/959jcekxmOWxyVyYqmI7yV6nkiNR1zd" +
                                                                "VfQoHmqarkmNJzxd1zg9smTHKquBvic1noytjScnaaTHrB77TlYPfDJOAz3a" +
                                                                "z3nSWJPvV2RHqZr4UUX+4wfy08RyQNKKk1tk4wHD0h0tDht/oXGFbNxvOPKm" +
                                                                "Gvgu8mQV4B4jiNXt1fCHrYrMyJBV/QTkim15WtJ4/3mI0xVfI6oBFehVV09M" +
                                                                "/3SqK55cNTSePJDkyN4G5JLI8jbV0Pv9tJolaTz7tkirQQ8GsmrLG/2VpPHu" +
                                                                "8+OYQ1c16qE9W2qQpPHM+WF7TJXMnj0ns9uk9T36T7/2UW/qXW5cqmjWdNWp" +
                                                                "6X+wAnrfOSBWN/RI91T9APjIh8jPyO966+OXG41q8DPnBh/G/MM//0d/5oX3" +
                                                                "/eZXD2P+5F3GzJWtriavqG8oj/6b9yI34PtqMh4M/NiqVeGOle+lyhz33MqD" +
                                                                "StvfdYqx7rx50vmb7G+vf+ZL+ncvNx7GGw+ovpO6lVY9ofpuYDl6NNE9PZIT" +
                                                                "XcMbD+mehuz78cbVqk5ann5onRtGrCd444qzb3rA379XLDIqFDWLrlZ1yzP8" +
                                                                "k3ogJ+a+ngeN49/j1f/pRuPyJ46fRdKQQNN3ddCKTd0wwHHkB4qfg2NfTV3d" +
                                                                "SyoF8CNPdxxQDhxwYyVgZc6RpYJ6LruBU0m0shJVV3wFjCO1AksVR2cqnbpZ" +
                                                                "jQv+v2LP67U9vrt0qWL7+1Vf0xU5rmR4rE8jxqlMZuo7mh69ojqvvYU3nnrr" +
                                                                "c3udeqi2g7jS5T3XLlV68N7zHuR22NfTEfpHX37l6wd9rGGPmZo0Hj6jqCLi" +
                                                                "kdqublae6mblqd68lN9Evoj/rb36PBDv7ewU7qGK/Bcdv/JxeePSpf0Knt4D" +
                                                                "75WmErlduZLKWzxyg/vI7M99/Pn7Km0NdlcqodVDr523nTOPg1c1uTKIV9TH" +
                                                                "Pvad//Ebn3nVP7OipHHtgnFfhKyN8/nz7Ih8Vdcq53eG/kNH8ldeeevVa5dr" +
                                                                "x/JQ5fMSudLKyoG87/wcdxjprROHV7PifrLxDsOPXNmpu0681MOJGfm7s5a9" +
                                                                "mB/d15/4QfW7VP3/T/2v9bluqJ+VV0OObeno1JiSxre//ck3vv0Ln3rxYt/R" +
                                                                "9R/atfMjW4+uBZXkVCuQnT8ulqDu+FGqv1z9asV/sQ29APU/3GneqH7BwQxq" +
                                                                "DTontf0G8hIXfOH3v/GH0H5rPdlrHrttU+L05NZt/q1G9tjekz1xppDLSNer" +
                                                                "cf/us8wvffp7H3t5r43ViA/cbcJrdVmzQ67Y4Ec//9XwD77579/4t5fPNDhp" +
                                                                "PBBUVmOpe8o/UCH64NlUletzKvdbURJfW3mur1mGJVcmVlvD/3rsT7W+8p9f" +
                                                                "e/yg0k7VclCQqPHCD0dw1v6eUeNnvv7T//N9ezSX1HrrPWPH2bCDP3/qDPMw" +
                                                                "iuSipiP/2d997nP/Qv5CZdqVN46tUj842P3yGvtVQXt9vbkv2+f6unXxfL7v" +
                                                                "e+++/Up8cW/D6iDhzN4k8M3PP4v85HcPDuzU3mocP3EXB8bLt7mC9pfc/375" +
                                                                "+Qf++eXGVanx+D4+kb2El520lqpURRgxctxINt55R/+d0cJha7x16k/ee97W" +
                                                                "b5v2vKWfOc6qXo+u6w8fjHuvB/mlRlBXbu0hPrgvb9TFC3seXU5qL1rHbUmF" +
                                                                "2PIq17kHSxpXjw11D/FM0nj6YEQ3D803hf2j7nv2YCl12T+eslLG+5s32zfb" +
                                                                "9Tty95nvq6sfroufrIufOpn32a2jXjsxfL6KHyuluXaY+4SUx/faU/Pu5iHS" +
                                                                "2ne8J2k8Uptx4MhJ7f/yu9BVacSjZ8CkXwVpv/gf/srv/OUPfLPSgFnj/qyW" +
                                                                "TiX422ag0zqK/YU3P/3cO17/1i/uDa6yNubvwOm7a6xkXaBJ47mabM5PI1Un" +
                                                                "5Tih9haiayeUX9REJrLcyjdkx1GW/vHXP/mDm6+9frDCQyj6gQvR4O0wh3B0" +
                                                                "v8p3HtZXzfIT95plD4H9p9949R//jVc/dgjVnrwzsEK91P3bv/e/f+fmZ7/1" +
                                                                "tbtszFcc/7Ddnhf3U3922onx4cmPbMkItFi1WBdIAXaEozjeB1ViuFg1iyY9" +
                                                                "HEvSdGGbO3yJoi3Cim3Y7RfqOC7Hw3KSr/1puCOsseyxq2EHSfjlfMhjTSxs" +
                                                                "sTvW4HkkJns7BMuwnb7pyjkvKXIftUAD7kuA3hrCod4OilJrKynEACAINg0I" +
                                                                "BI057HStdKHMLYdE4nZvNpMVrmRDB9NLEwpGdNuyVuaa8vNJrwMQwdSCs1Le" +
                                                                "DXTPXWu4xa5tdBUK01XYx21yO51t6A1CsI7TxTBJJqQiwWa8neXJaL0TRiue" +
                                                                "n2GTRacTmLbVy0crQkExwZ9IkLIaZpleBIhlhjJO42HooM6C5Vl/i+yIHFjT" +
                                                                "1qo793UZY11tjHMzcux4nG2nM6ooTSrpAPFURM00a3V6U26FLnvcRlJzfszR" +
                                                                "+JpgSU4K05ArFFxIZ9YkoCgUUDKS8DdO0cUt147xSF+Ao01gy6PpCs1n1MS1" +
                                                                "BEdbziJ7F8QYSiqzkHW9yXYSImTIxbSmlsxi4g0TWZG0GduEV21ihbXsXTlv" +
                                                                "8ggMc73Jsm81uZkMJ0pHRXo7A+NbUB8sOBFSN6KU4HzTlda70J3hhLuUEM9i" +
                                                                "OU8mQqkXK1VIh7uuvYFMjUSnREBurF1iridh02aJsexgqFo0l35zIodmMdl1" +
                                                                "Knpw2cLNUtpmrITuHFnDVrCeF0iAcw7LSAFY2CTJDW3R78rplAwHEtBEV94K" +
                                                                "MXi7PeZJ1NMXtjPzke4AY9Zy0etNhquR7BKzDQmlIcMsGLvLFsXARZomoa2B" +
                                                                "kGtuZuMBLDStRZRqzZ6LCRt8we/8hEY6nBF1clgs+RIJLcRGuFXJNUuAUhd4" +
                                                                "BBu0VLBNMy70FtmiQ2c8nG3U3YyIfd8o0cXYwFlaG9otdUoSnBpPTU/BmaWc" +
                                                                "lgGFDMu0x2ZUXpatzgDwxDHppwt6sGSAISWKNNIz/TwWkpKH9Ikz7HZ8f06g" +
                                                                "0nS5XNk02fW6atnt9ZoQxto0RSBiyWKG42oGkxi7QbaKpiFhmKjrCQ6+CT28" +
                                                                "NxL7qyXPLl2LXRoLiQ/xjrmyUwKfDqO2kcirzRaiVq6ZkVWQteR8YZUVEi3x" +
                                                                "PjnzVhjKCQRCzOM5HCBLvk82XZeAYiak2F5vgBrbJo5ocCByk4XbIxLZDyYz" +
                                                                "ec7PAoYlHIh3E55KUJ9vFjaxWewWAZKsnHCOzGxNmY0ZYrHoWnzTDChns+rY" +
                                                                "hF0KQdmiRxiOxlDRX+gG4au5a4MEo1JRCE07zhBvjbtrfjRvCsvx0AzWYMVV" +
                                                                "ERfYkB+5O5zrE30xiThDh92uyWCa3bHYEukM1TE425qB0xa9rQIPWlC+gXeG" +
                                                                "rUlcaNu9OUuMUGq15gFhmNf/Mhm7BkmQyoqhQDWgMEttiyyGMz0+3gS4Z01M" +
                                                                "FEtG46ZCeUOJyszxcNhEWjrFqW5b0bNpgI/AkaCIqD4ZtqfjbDLUesKYIleU" +
                                                                "gvK7QXO+TKRMyUHdwBClu1yMEdbkaaT6yo0IQA3CNT8kYl1z1PGIzZeTqT+p" +
                                                                "JjVnW1y2weV8x/fm6Qpx581ioSCWn1A7o4NiluZAreYSbistK52HWGpHuDqi" +
                                                                "QocdLqROpEuR7zeH0jaORwMJlXp4v9XSMUvqrqf82t3Z0aK7ULozcLwpFrzA" +
                                                                "LycJuxwNAiL0zJltzESes/SS51VxXkIU1kzznipqqAWFaHc4Ahy+TKj2VATh" +
                                                                "jjizUEihmyqKUoyneWuapAdrZ+LxJA6paruVACWYcOGka453uGUuxHnF/HjW" +
                                                                "HM5m2Ixt5x0MYztdMGYELZ7T6Mxl1yqWz0pyLOndHpbl40EWr4zOhm13RkLB" +
                                                                "ygK2YlEloMaZ3fPlVtshaM4hKK1g0d6IHGUWKrGmo3aFlRsgnL0ZmpukP1hq" +
                                                                "mrnu9jsJ2UaFVjqZhp2+x5cFtChUMOovCzwcD/oDOactlEULbWl51nhOdyAa" +
                                                                "qpRLFE1u5bOAIiQ7LW1bcGwmQ5QeC3YgLcYzmqLXhtz35o4zncQekHQ1S4Uq" +
                                                                "R7wEnHKkGYXaFNbmEOameeWAUjgLLA7joigLF67SW8qY1i6GktA0vdJA0zY2" +
                                                                "KtZou2iVbtIE4T7gyrk0qIxuRvbwMemOALH0RmMeFTWiUDa85EuUOVQIZOW6" +
                                                                "iMmFQ7vnzUeIKOXSqjL+NaWligmppr6MYX8eKm3axrxWOpq7TVRMVMHPu35C" +
                                                                "2r2B3BXSsdMscaEIwi7Xd2f8QN2SC8+w2klHSrxdsXIW9pzLMXpLmRIzswk3" +
                                                                "ajaFVu7lCkUqaDbackGTG8+Iial7fTHeaMwSHu/6OT0GVohgL1PbKuROSrpQ" +
                                                                "pgPNMti1U0/1o5Hoh2pToid8r0eJoLLutxzKlwb0IEi3m0W4YBQL7qS4KHT6" +
                                                                "nJlNPAagzajoCiAlxKPMGTnjxQhpRdE4TTaeiI2mGofw01aRUYnFABNvDOwA" +
                                                                "eDWFsdGaIzfJNpFmSwqLUwlhBHq6VgbFWhC6/YJ1hJJdh+QASuG5EA0p2DB6" +
                                                                "vLzcDoat1IOm4JoHU33ZHLZLKQkGNGy40lIc6eKAbiOBLi7W/eli0jEhUQkh" +
                                                                "g1nPd4Ysbzsk7FMbK3a2OlD2QxQ0JElyVHUmtcK8k3LKok0biBdvci1cDOCY" +
                                                                "KM1FO0v9kiaHm1CgdhPVS6w5TOU7UhxWhrxgQj0IQtcVsRCW0y07YnkPygKa" +
                                                                "jmduu+VLHb692cIEXOIEq4NhO+sDFhlXvOTA1jzROBfaijMEGvseBeg9v0Cn" +
                                                                "wSBYhfSaZBF168cZrDfBZFvtkaUUpayTiUCv5/dbE6hfFcLAtZwJPdsMTE13" +
                                                                "DGotb1XbUh20uc0tIIecgYzEkD1ZV34vw6MuGjMpM0Zoeb4LFXWkh9uMo00T" +
                                                                "KtLxIlLECQQXip9YSpCsQZPj+i0MhBaOJ0Y2tJxnjgAOBjNh6EdTnCd5UtKC" +
                                                                "iVR9Zwm01qUBoWzTfZoQ1i7jVI41RTBWh8C0S853GbDpue2dAvLAWlSoduYR" +
                                                                "2/nUEqrIZc3y6dxIWLI1wYQdMFx5udBCtm2tep3M+zQmQmZrlxT5WIn0aNDn" +
                                                                "wWFBwIi9Vgradcx2B14Da4iicVhPscjoR5O0b/XirL9Wu/PBfKkEriXjZDbr" +
                                                                "awnUdyPf205pU4nYxY7Gh4DsuH4VhYptlwNyt4AoalRFbIiIxrDSogyVW2td" +
                                                                "wAgtKYy0tSv2+yNuoDG04AGbMQlPMHYX4mYAsfPmlAmYHjQiEygZAWgbWQMb" +
                                                                "AyldRO6Fk6YBFFh3qGtDxMsUd1AGttOatSJeKFAvQJcrqbNgmyw8ktvjde63" +
                                                                "KZshCjxQaRHP1aY/QVuoOZFb25UsO01OkDlNAN2yi1CtCcdmDrHhsZXaVjGs" +
                                                                "ueFsQLUshZFGk9T16fGklMjU0DYxsFNnseEJ/Ul3q2RMX7Ni0MIEEy8zIAbQ" +
                                                                "Am7npbMSiGAUpBgz7ym70tAyndvBngP3aJwt+pA6lrVlD8DX3VHpI3QbHUqu" +
                                                                "rxdxu4ts0kjz0NwvhElXxkE/6GYR6HlWU95tGICMYpAMJ16vW8JaYAcIrytb" +
                                                                "ImI1HsTI8bw/hQNpXvBeUrJCX2ToJPKLbV+gEgVueSIjdu121T4hu7FIrtDN" +
                                                                "ZkssLVUry2YZg2CwaungjkD1cauNLD3CGwS0rlPZWCYl0acnAk4NUGTBFc5c" +
                                                                "X2VQd8QwYkYukjQDdBlpJcwg2zWryHKXSuy8RZeayICdaAwDLqTwI6lTlDvc" +
                                                                "1Es0Z6jFegUnWSfk3FkTHU+lALcXRLCUAM2xpl1zsgrWJEPLw5lJsMxmpXYr" +
                                                                "jgWVkQHdbJ7lAxY0ipEuWJ1+ez0WM8v3Jb0dTswSmmqlSWqtPsOg+G473gWt" +
                                                                "gl6Lk2KgVxsPWkpwMpPzYmZogIaBaOpuHdIggDYNiiDec2DcCKtvku062U6b" +
                                                                "ATxhu9wAI9YYOpOlgSOvqLy7CcoJKHaqb5yF5PCSt8XKja4P4CIVPccUUhye" +
                                                                "85lHUTRYeq7URwUgormUSkhoBxBIiiFk0p1vt5BpF2u+ihZdSeO1LdKPtYXR" +
                                                                "Rju7ckC7o4KGbXE1yNESK0QlH6zhvFeiUjz1BEKbQSmeNT2GMonFCmhO1hqW" +
                                                                "MHwPllVSgI28Nxe83oYpl95KS5jdClpBPoXHWLebYbbEGiIJDbaQ4vRAgjXZ" +
                                                                "GMSDeF45mtUuK4z13MPaZSYlEdL1XJchFzSUFhAvugt3ELRHvtK2i6Y3n5ia" +
                                                                "CLJDu98eLvrIME+B1pDc5FmsysuwlHSXdAS77DIIvElziUODWUmDJCEI4gxu" +
                                                                "ywsPUQJn25KGTICrfSWdC2UiRwg8F0fMJm6yUuRstyGlDYROR40ydy4sPG+W" +
                                                                "N2djjdaVVO9iLcGaNQdsZ2v2lW5sTDF4Qw6mxrRls62+WrnhmTYlmx64gHxs" +
                                                                "5EAg6yKA5WQ2sCjGpJ3zqwljdQBgYLQEBQy9EO5RZQ/DtpvcnYUJke7Mzjyd" +
                                                                "W2Avm3dgcJCtsSGk5+F2xWyBnYRkNmgBPAAbs0E5IUgSwNIcinpuyKHKcsMi" +
                                                                "g6wrFVB7HY5mVjZpimtmZXY6eEhQbJMAFVWZwsNSAndA9fXa2gKVlFF3QSgl" +
                                                                "vMXhdGwVrNoz11wf51xEa4tCImuznAHTzc4EQA8B5pViTQ3O8Ew0mANymUXZ" +
                                                                "gOkpM4AgdpNAU6WmaACV31VEdRXHmQjlTkHHqtQee8tOxqXaErK7/jZJKoPM" +
                                                                "+EWrlZrQpvqW3WR6XIWHCe+o2xC2BavHCPCybQwJbDCNu3oYbFqAN1w4y8xd" +
                                                                "rUeLyjCHg7mSbreGwm0qyRLymOxj5kBJZ1sxwgpn1NEXraFvt3k0Rh1whmzA" +
                                                                "VrAzWhxjgRqj63MF5Nqk6PMqshQI0BDiOKi+2tq2hbaGG8DcxDMqy4CkHTad" +
                                                                "WJJH7IwN5zptlwPG6diSD/MZqvMABpFgNqaSXX8wQaLVpLnQHZ6idEApeQqH" +
                                                                "FbD6FJ7PQZcupk4bF4HVYMgI7HzC5nJM2CnvbdtzLysncysZ42Y/RiNb7s7R" +
                                                                "ZB0PWosWMQeHg8zvwmDoexhjA/wCUOQWtuh6a9M3M4Ue6elCbE8m+tzCJ6ai" +
                                                                "Lrv+DlhHdJpHDsmti2WGDJbJgBx6O0pYt9hAyY3xlFewQZbo5SybQWL1xbTT" +
                                                                "pFnfHxkdm1s3Sy9xN4AWyEY/m0kIkvu9oO1pG2Daj1b0ds4v2a0T0SBD2bqe" +
                                                                "2Ku0b1AeCcWDnTdwENUwpkqwCormhMQ5st2LozmXjkBIjaMdIdvKIBxUQZbK" +
                                                                "xP2CWUtkFSqHu7TjyNaqi+xKsfS5UKcms5YiaKbWHvY5cNPqD50SDLjFcDh8" +
                                                                "6aX6mE08Pmd8en88epqSPhwv1n3T/blcfvfD1sZxUuDscLxRnxY+93bp4v1J" +
                                                                "4Rs/9/oXtfmvtS4fg6+SxkOJH3zY0TPduQ3V1frc8cJ1BGqfJD87Lv/Wd5+r" +
                                                                "QqD/uDmcO77/3MznR/9N6s2vTT6ofupy477Tc/ELmfk7gW7deRr+cKQnaeQt" +
                                                                "7zgTPzokvI5Tto33VP93NRqXf+34+fm696n9efnTtx0fXzw1Pzu7zk8xXq4x" +
                                                                "PnaM6VePn6/dhvFc8uLSaebxuXPMqyS6z4ocToa/8evff89b1/7w+wfGnc/w" +
                                                                "3zbwv775ze/+7juf+/I+eXilTtvuF37+asTFmw93XGjYr/uR01X161X91PFq" +
                                                                "vn78/Gfn+TS9sKrT82G/zkCce60r0Q9j7WlG4AFH9zaJuR+5OD7prh/LpHFf" +
                                                                "tZa66t0d2aU9sgOeukjrItsD5KcUXz7Me5JceOrs6B9xfE+vs1z7NMdpImQ/" +
                                                                "wPJvnl5mORmR35ULymHZ+1nr4vo9klh/8R59P18XP5s07ldrsu6SCznkkQ6U" +
                                                                "7CHst9G4iytFc1UPThKVz+6BP1kXetK44sqWdzfeX8l866Azyp1Wdf42xCcu" +
                                                                "WNUPY8Pr9+j7TF381aTxjrNLAtfOTPWM");
    public static final java.lang.String jlc$ClassType$fabil$1 = ("lKs1xAfr9R5f0rh0yGh/4+3zzfu08I80t3zn1YoXO80XoO6HO50bLx6FqRxb" +
                                                                  "Yeon+vVD2vaoZufR1jKuWV7m2/pYN267R3D9xtFHE9OKb9626us3br16Iwhu" +
                                                                  "d1R18aFTFux/D5xnQd37K0FwDxZ/8R59f60uPps03v12lNb9r52TxYM1+BMX" +
                                                                  "ZfF3f/xkEVlZRcbtwrCSmvlHL3+EOzrP7gv+JmlcPcaQ3ymFq3eTwhv3lMKX" +
                                                                  "7tH3Zl389aTx4AmJd+P6o/Xw5y5w/dKv/Nhx/ZCBPjo2BMX3HV329uw/2dx9" +
                                                                  "4/rL+/T00WG2j8qu8uredx1qB9+3rx/5H8EqgVnG0XX/6KWXjrzUcW4cHaKB" +
                                                                  "CtqJ9VvHqey964t1NY2spLhJyorurOosqKxWe2OMmLpqX7/7dCdAh7dTyMOr" +
                                                                  "59cMrnx0xazrN1448vfNN24dk1BRZZ2u6uiMPxet+V5hx94X39u/P3n7Mg8M" +
                                                                  "unuifI/s799D3f5RXXw5aTx6p0jutitcPZbe7bp4cp3o3Sc6eNJQ9177kay5" +
                                                                  "fv3KfsBv3WMdv10X/7Te+ap1qHKcXDtj/9vuIU9etKDix86Cjk3nIOWjvUvY" +
                                                                  "a2W89137neNaGmgVpfvmo5fexo7+GIp96+gY78FcGL8ioPhRIL6p+p5xQHf9" +
                                                                  "1Grqqf6ft7t/eU9H+4179P3ruvhqFW3cxtKDrt1xTTEIzhTw2TSqb1C/+d/+" +
                                                                  "xPcfeHD5reMbI42jhz//r375c9r8Q18cPvOpL3zkn7z1nd/qHP3gE8//wTO/" +
                                                                  "/19+7y/9g699/v8CtlkMLtktAAA=");
}
