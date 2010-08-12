package fabric.util;

public interface ListIterator_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.ListIterator
          jif$cast$fabric_util_ListIterator(fabric.lang.security.Label arg1,
                                            java.lang.Object arg2);
        
        public _Proxy(ListIterator_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.ListIterator_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.ListIterator
          jif$cast$fabric_util_ListIterator(
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
          implements fabric.util.ListIterator_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.ListIterator_JIF_IMPL._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.ListIterator_JIF_IMPL._Static
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
    final public static long jlc$SourceLastModified$fabil = 1281544489000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKVXW2xURRie3bbblm7ohVsDtD2FSnYVWqORIBuDDdK4ZQlr" +
       "W1SKZJk9O2c7MHvO\n6Tlz2i1YgtEI8mCi4C3x8mJCYniSqC8mGsG7D6YP4g" +
       "smBmNMFKIPRkJQ/Gdmz15OW4hxk52dy//P\nf/v+f/49ewU1uA5aZ+AsZf18" +
       "xiZu/xDOJlNp7Lgkt51h1x2D3YwefmLVi9ueuf5JGKGigzTbYjN5\nZvESzz" +
       "zyretvTH9zfHhNHWodR63UHOWYU327ZXJS5OMoWiCFLHHcwVyO5MZRu0lIbp" +
       "Q4FDN6GAgt\ncxx1uDRvYu45xB0hrsWmBGGH69nEkTL9zRSK6pbpcsfTueW4" +
       "HLWlDuIpPOBxygZS1OWJFIoYlLCc\nO4mOonAKNRgM54FwZcq3YkDeODAk9o" +
       "F8CQU1HQPrxGepP0TNHEc9QY6yxX07gQBYGwuET1hlUfUm\nhg3UoVRi2MwP" +
       "jHKHmnkgbbA8kMLR6kUvBaImG+uHcJ5kOOoM0qXVEVA1S7cIFo5WBMnkTRCz" +
       "1YGY\nVUVrdyT698n0X1oYhUDnHNGZ0D8CTN0BphFiEIeYOlGM17z+08m93l" +
       "qFihUBYkUzeMcHe1K/fNSj\naNYsQLM7e5DoPKPf2Ly2a27wp+Y6oUaTbblU" +
       "QKHGchnVdOkkUbQBvCvLN4rDfv/w45HP9h57h/wa\nRs1JFNEt5hXMJGomZm" +
       "57ad4I8xQ1idrdbRgu4UlUz+RWxJJrcIdBGRHuaIA5NQ3Ln9uYT8h50b6p\n" +
       "Pv+IL1IfKgaOlJcfAo+OWcOAgR1FsLNfoEGL6VbBhrsdLU9M4mBOcnHbLoor" +
       "l06HQmDZ2mCWMYDk\nwxbLESejn7n81ZM7dj53QsVM4KykDEe9kM4O1ZU/RA" +
       "okubjfcjLDyaFMclc6hUIhKWFVre9EMHKC\n4bd3E23Pb3LfD6O6cdRMCwWP" +
       "4ywjkGuYMWua5DJcgq29CtjSUgBjNAu4BIhnGFwk86BooykoMkH8\nVbI2CT" +
       "MMoJo7evPbq5npcwIqIrTLxe2+GeYhpVs0Prp/+MCJdXWCaLoe3Cws6bv97R" +
       "n96sld5777\n+lKsgnOO+ual33xOkT5B9dOOpZMclKfK9a9cf/j3Uw33vxdG" +
       "9ZCTUJU4BixBincHZdSkUcIvScJZ\ndSnUYlhOATNx5NeRJXzCsaYrOxIlUT" +
       "G0KcAIZwUUlNXs2tORuy9+2PKptNgvfK1VFXKUcJVG7RVf\njzmEwP6lV9On" +
       "XrpyfJ90dMnTHEVsL8uoXpSKrAhBYJctkNL9nctPvxx//aIfyWWV2wcdB8+I" +
       "QBaf\nmut67XP8BqQ7pJ1LDxMACnykJOQLEGNczu+sOhRrrUQiwBVMlSFR8P" +
       "3IFLJH/jz/5hJNKSN4VlfL\n6JVjn7IwzFETzgIAsC7Bu8EXUtGjzOygrsVq" +
       "qnwPjj/+R/RZfGG/qnwdtbm2w/QK9731PYk/GNUX\nyOFmbtmbGJkirGJkUN" +
       "ou+db4VrY+1vPj0OYzs0Erw6Bnzy05M3r71JpH6iboF2GBv1JizHvsapkS\n" +
       "1RoDQh0Cb7UpbBc7TTJC3VKNVlBiKXy7BIBK5VH+8tCx/1Yet96z5d6t2qSH" +
       "XTrpWZzEXNlWaAqS\nWtayGMGmdpAafb6alhHbZ1ATM00VxSO4kJ3tFwVLzV" +
       "yiew7lM2qVwlnC5FRektqoKV6hUZBTvVqK\n2No/FNeOUEOLWRotS9aqay+c" +
       "16w1XXtAi9VQWAlNebGkqxRW1lApt0dgh0x6dAozYvIxKwb+AlXz\nhCumjE" +
       "BXpqbqp2LxjdKceGK2LIC5JDFbfnAWSASJfsh35eOF0zFcQphYd0Ars7jePk" +
       "mbLASSQDnw\n9nk+yNHS2pC68xuTtEML8O5PlRqTF7rf/vnc5ZHlKrNU97Z+" +
       "XgNVzaM6OCmwxRa53XsrCZL6wl29\nZ4+O/JANlxTdwlFjCYPSkG0K/qI1AF" +
       "s6K/APbfhf3cFiwRLD0C0DJZZJMey8vdsfhUZCuF3HLu9b\nDFt+WDsXazmK" +
       "oiNdqAURr0LnvL8RqtnV180diJ2327+UL2m5IW2ErtDwGKsqO9UlKGI7xKBS" +
       "90b1\nQCp/QehbqtTjqF78SM11RZEHmCsKsZqwfas6qvFcguu/XaynzjMNAA" + "A=");
}
