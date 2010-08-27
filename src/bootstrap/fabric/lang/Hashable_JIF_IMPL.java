package fabric.lang;

public interface Hashable_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.Hashable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.Hashable jif$cast$fabric_lang_Hashable(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        public _Proxy(Hashable_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.Hashable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.Hashable jif$cast$fabric_lang_Hashable(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
        }
        
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
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.Hashable_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.lang.Hashable_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.Hashable_JIF_IMPL._Static
        {
            
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
      ("H4sIAAAAAAAAAKVXXWxURRSe3bbblm7oDwUaoOUClewqbI1GgmwMNkjDliWs" +
       "bVEpkmX27tztwOy9\nl3vntluwRKMR5MFEwb/EnxcTEsOTRH0x0Qj++2D6IL" +
       "5gYjDGRCH6YCQExTMze3e3d1uIcZO9O3fm\nnJnvnPOdM2fPXkFNroPWGjhH" +
       "WYJP28RNDOFcKp3Bjkvy2xh23TGYzerhx5e/uPWZ65+EESo5SLMt\nNl1gFi" +
       "/r1IlvWXdj6pvjwysbUPs4aqfmKMec6tssk5MSH0fRIinmiOMO5vMkP446TU" +
       "Lyo8ShmNEj\nIGiZ46jLpQUTc88h7ghxLTYpBLtczyaOPNOfTKOobpkudzyd" +
       "W47LUUf6IJ7EAx6nbCBNXZ5Mo4hB\nCcu7h9ExFE6jJoPhAgguS/tWDMgdB4" +
       "bEPIgvogDTMbBOfJXGQ9TMc7Q6qFGxuH8nCIBqc5HwCaty\nVKOJYQJ1KUgM" +
       "m4WBUe5QswCiTZYHp3C0YsFNQajFxvohXCBZjnqCchm1BFKt0i1ChaOlQTG5" +
       "E8Rs\nRSBmNdHaHYn+fTLzlxZGIcCcJzoT+COg1BdQGiEGcYipE6V4zUucTu" +
       "31VilWLA0IK5nBOz7Yk/7l\no9VKZuU8MrtzB4nOs/qNTat6Zwd/am0QMFps" +
       "y6WCCnMsl1HNlFeSJRvIu6yyo1hM+Isfj3y298l3\nyK9h1JpCEd1iXtFMoV" +
       "Zi5reVx80wTlOTqNndhuESnkKNTE5FLPkO7jAoI8IdTTCmpmH5YxvzCTku\n" +
       "2TfV5x/xRepDxYMj5eWHwKNj1jBwYHsJ7EwINmgx3SrasLejFYhJHMxJPm7b" +
       "JbHl4qlQCCxbFcwy\nBpTcYbE8cbL6mctfPbF953MnVMwEz8pgOOqFdHaonh" +
       "B8S+zA7gTOMZIdTg1lU7syaRQKyd2Xz/Wb\nCERe5Mtv7yY7nt/ovh9GDeOo" +
       "lRaLHhf6kGeYMWuK5LNcEq2zhtTSSiBiNAecBHpnGWwkc6Bko0ko\nMEHuVT" +
       "M2BSMMhJo9dvPbq9mpc4ImIqzdYncFDYJ0SGGLxkf3Dx84sbZBCE01gouFJf" +
       "233z2rXz25\n69x3X1+KVTnOUX9d6tVritQJws84lk7yUJqq279yfcfvp5ru" +
       "fy+MGiEfoSJxDDyC9O4LnjEnhZJ+\nORLOakijNsNyipiJJb+GLOITjjVVnZ" +
       "EMiYpHhyKLcFYAoKxk156O3H3xw7ZPpcV+0WuvqY6jhKsU\n6qz6eswhBOYv" +
       "vZo59dKV4/uko8ue5ihiezlG9ZIEsjQEgV0yTzonerpPvxx//aIfySXV3Qcd" +
       "B0+L\nQJaemu197XP8BqQ6pJxLjxAgCnzkScg/QDzjcnxnzaJ418oiglzBNB" +
       "kSxd6PTDF39M/zby7SFBih\ns6L2jDXy2a8sDHPUgnNAAKxL8q73D6niqCg7" +
       "qHeheirvguOP/RF9Fl/Yr6pe19xc2256xfve+p7E\nH4zq8+RvK7fsjYxMEl" +
       "Y1MnjaLnnP+Fa2P7r6x6FNZ2aCVoYB5+pbamb1zsmVDzdM0C/Cgn/lxKi7\n" +
       "6OYqJWsRA0MdAve0KWwXMy0yQn0SRjuAWAzfXkGgcmmUvzxk/rfSuOWezfdu" +
       "0Q572KWHPYuTmCtb\nCk1RUstZFiPY1A5So9+HaRmxfQY1MdNUQTyKi7kZWR" +
       "XVyCW651A+rd7SOEeYHMpN0hs0pSsQBTXV\njaWErf1Dce0oNbSYpdHKyZpf" +
       "d2GtMtZ07QEtVlmxkprynFZbsH1UCtAewReHMDF2x6wYeAjAFQhX\nKlmhkq" +
       "3U+HQsvkGCjydnKlszlyRnKlfLPLSXXIfsVh6dP/nCZT6J9y5oWhZG7It0yL" +
       "SXAspdt8/q\nQY4Wzw2gW9+CZBxahBt+styCvND39s/nLo90qzxSfdq6ulap" +
       "Vkf1avLANltk8ppbnSClL9y15uyx\nkR9y4TLQzRw1lxknDdmqyC6aALClp0" +
       "r20Pr/1QcsFCzxGLploMRrSjx23t7tj0DLINyuY5f3z8cr\nP6Td8zUWJWgF" +
       "6poMUfd76v4kqFZWXzt7IHbe7vxS3pWVdrMZej7DY6ymsNQWmYjtEINKvM3q" +
       "ClQ+\ngnC31cDi0MDBj0SsKwmYiygJ8TZh+9Z01VpTpui/T3soIBENAAA=");
}
