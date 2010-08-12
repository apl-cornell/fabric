package fabric.lang;

public interface IDComparable_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.IDComparable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.IDComparable
          jif$cast$fabric_lang_IDComparable(fabric.lang.security.Label arg1,
                                            java.lang.Object arg2);
        
        public _Proxy(IDComparable_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.IDComparable_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.IDComparable
          jif$cast$fabric_lang_IDComparable(
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
          implements fabric.lang.IDComparable_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.lang.IDComparable_JIF_IMPL._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.IDComparable_JIF_IMPL._Static
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
    final public static long jlc$SourceLastModified$fabil = 1281544053000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAKVXW2xURRie3bbblm7ohVsDtD2FSnYVtkYjQTYGm0LDliWs" +
       "bVEpkmX27JztwOw5\nh3PmtFuwjUYjyIOJAl4SLy8mJIYnifpiohG8+2D6IL" +
       "5gYjDGRCH6YCQExX9m9uytLcS4yc7O5f/n\nv33/P/+eu4oaXAetM3CGshif" +
       "tokbG8KZRDKFHZdkBxl23THYTevBJ1a9tO3ZG58EESo4SLMtNp1j\nFi/yzC" +
       "Pfuv7m1DfHh9fUodZx1ErNUY451Qctk5MCH0fhPMlniOMOZLMkO47aTUKyo8" +
       "ShmNGjQGiZ\n46jDpTkTc88h7ghxLTYpCDtczyaOlOlvJlFYt0yXO57OLcfl" +
       "qC15CE/ifo9T1p+kLo8nUcighGXd\nI2gWBZOowWA4B4Qrk74V/fLG/iGxD+" +
       "RLKKjpGFgnPkv9YWpmOeqp5ShZ3LcLCIC1MU/4hFUSVW9i\n2EAdSiWGzVz/" +
       "KHeomQPSBssDKRytXvRSIGqysX4Y50iao85aupQ6Aqpm6RbBwtGKWjJ5E8Rs" +
       "dU3M\nKqK1JxT++2TqLy2IAqBzluhM6B8Cpu4aphFiEIeYOlGM173Y6cQ+b6" +
       "1CxYoaYkUzcNcHe5O/fNSj\naNYsQLMnc4joPK3f3Ly2a27gp+Y6oUaTbblU" +
       "QKHKchnVVPEkXrABvCtLN4rDmH/48chn+556h/wa\nRM0JFNIt5uXNBGomZn" +
       "awOG+EeZKaRO3uMQyX8ASqZ3IrZMk1uMOgjAh3NMCcmoblz23MJ+S8YN9S\n" +
       "n3/EF6kPFQNHysvbwaNj1jBgYEcB7IwJNGgR3crbcLej5YhJHMxJNmrbBXHl" +
       "0qlAACxbW5tlDCC5\n02JZ4qT1s1e+enLHrudPqJgJnBWV4agX0tmhekzgLZ" +
       "bYPghysIMzjKSHE0PpxO5UEgUCUsKqat+J\nYGRFzvz2brzthU3u+0FUN46a" +
       "aT7vccEPuYYZs6ZINs0l2NorgC0tBTCGM4BLgHiawUUyDwo2moQi\nU4u/ct" +
       "YmYIYBVHOzt769lp46L6AiQrtc3K5Ug0AdVrqFo6MHhg+eWFcniKbqwc3Ckr" +
       "47357Wr53c\nff67ry9HyjjnqG9e+s3nFOlTq37KsXSShfJUvv6VGzt/P9Xw" +
       "4HtBVA85CVWJY8ASpHh3rYyqNIr7\nJUk4qy6JWgzLyWMmjvw6soRPONZUeU" +
       "eiJCyGNgUY4awaBWU1u/5M6N5LH7Z8Ki32C19rRYUcJVyl\nUXvZ12MOIbB/" +
       "+dXUqTNXj++Xji56mqOQ7WUY1QtSkRUBCOyyBVI61rn89MvR1y/5kVxWvn3A" +
       "cfC0\nCGTh6bmu1z7Hb0C6Q9q59CgBoMBHSkK+ADFG5fzuikOx1ookAly1qT" +
       "IkCr4fmXzm2J8X3lyiKWUE\nz+pKGb1y7FMWBjlqwhkAANYleDf4Qsp6lJgd" +
       "1LVYTZXvwfHH/wg/hy8eUJWvozrXdphe/oG3vifR\nh8P6AjnczC17EyOThJ" +
       "WNrJW2W741vpWtj/X8OLT57EytlUHQs+e2nGm9fXLNI3UT9IugwF8xMeY9\n" +
       "dtVM8UqNAaEOgbfaFLaLnSYZoW6pRisosRS+XQJAxfIof3lg9r+Vx633bbl/" +
       "q3bEwy494lmcRFzZ\nVmgKklrGshjBpnaIGn2+mpYR2W9QEzNNFcVjOJ+ZkZ" +
       "VRzVyiew7l02qVxBnC5FRektyoKV6hUS2n\nerUUsXVgKKodo4YWsTRakqxV" +
       "1l44r1pruvaQFqmisOKa8qJWWcB9DZVyewV2HMLE3B2zIuAtUDRH\nuGJJC5" +
       "Z0Vc1PRqIbpTHR+EzpeuaS+EzpuVkgDST2IduVhxdOxmARX2LdAY3M4lr7JG" +
       "2yDEgC5b47\nZ/kAR0urA+rOb0tSDs3Dqz9ZbEte7H775/NXRparvFK92/p5" +
       "7VMlj+rfpMAWW2R27+0kSOqL9/Se\nmx35IRMsKrqFo8YiAqUh2xT4RWMAtn" +
       "SWwR/Y8L96g8WCJYah2wZKLBNi2HVntz8KbYRwu45d3rcY\ntvywdi7WcBSg" +
       "H12wARFvQue8PxGq1dXXzR2MXLDbv5TvaKkdbYSe0PAYqyg6lQUoZDvEoFL3" +
       "RvU8\nKn9B6Fsq1OPQ4MGP1FxXFLAXUhRiNWH7VnVUWlWE678mBaqSMQ0AAA" + "==");
}
