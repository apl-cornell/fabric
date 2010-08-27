package fabric.util;

public interface MapEntry_JIF_IMPL extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.MapEntry_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        native public static fabric.util.MapEntry jif$cast$fabric_util_MapEntry(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.Object arg3);
        
        public _Proxy(MapEntry_JIF_IMPL._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.MapEntry_JIF_IMPL
    {
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
        native public static fabric.util.MapEntry jif$cast$fabric_util_MapEntry(
          final fabric.lang.security.Label jif$K,
          final fabric.lang.security.Label jif$V, final java.lang.Object o);
        
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
          implements fabric.util.MapEntry_JIF_IMPL._Static
        {
            
            public _Proxy(fabric.util.MapEntry_JIF_IMPL._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.MapEntry_JIF_IMPL._Static
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
      ("H4sIAAAAAAAAALVXXWxURRSe7rbbljb0hwIN0PYCtdlVaI1GgmwMNkjjttuw" +
       "tqVKkSyzd+duh87e\ne3vv3HYLlmg0gjyYKPiX+PNiQmJ4kqgvJhrBfx9MH8" +
       "QXTAzGmChEH4yEoHhmZu/udreFqHGTvTt3\n5vyf75w5e+YyqnEdtMnAKcp6" +
       "+ZxN3N4BnIrFE9hxSXoXw647BrtJPfDo2ud3PnXtowBCOQdptsXm\nMszieZ" +
       "4K8h2br89+dWxwfRA1TaAmao5yzKm+yzI5yfEJ1Jgl2RRx3P50mqQnUItJSH" +
       "qUOBQzehgI\nLXMCtbo0Y2LuOcQdIa7FZgRhq+vZxJE6/c04atQt0+WOp3PL" +
       "cTlqjh/CM7jP45T1xanLo3EUMihh\naXcaHUWBOKoxGM4A4Zq470WflNg3IP" +
       "aBfAUFMx0D68RnqZ6iZpqjrnKOgsfdQ0AArLVZwietgqpq\nE8MGalUmMWxm" +
       "+ka5Q80MkNZYHmjhaN2yQoGozsb6FM6QJEft5XQJdQRU9TIsgoWj1eVkUhLk" +
       "bF1Z\nzkqytSfU+OeJxB9aAFWBzWmiM2F/CJg6y5hGiEEcYupEMV71ek/F9n" +
       "kbFCpWlxErmv7b3tsb/+mD\nLkWzfgmaPalDROdJ/fq2DR0L/T/UB4UZdbbl" +
       "UgGFRZ7LrCbyJ9GcDeBdU5AoDnv9ww9HPtn3+Fvk\n5wCqj6GQbjEva8ZQPT" +
       "HTu/LrWljHqUnU7h7DcAmPoWomt0KWfIdwGJQREY4aWFPTsPy1jfmkXOfs\n" +
       "G+rzl/gi9aHiwZGK8gMQ0TFrEDCwOwd+9go0aGHdytog29EyxCQO5iQdse2c" +
       "ELlytqoKPNtQXmUM\nIPmgxdLESeqnL33x2O6hZ46rnAmc5Y3hqAPK2aG6is" +
       "cwtneb3JlLDsYGkrHhRBxVVUnpaxfHTSQi\nLerll7ejzc9udd8NoOAEqqfZ" +
       "rMdxihGoM8yYNUvSSS6B1lICauklALExBZgEeCcZCJI1kLPRDDSY\ncuwVKz" +
       "YGKwyAWjh64+srydmzAiYirW1CujINkjSlbGuMjB4YPHh8U1AQzVZDiIUn3b" +
       "eWntSvnBg+\n+82XF8NFjHPUXVF6lZyidMrNTziWTtLQmoriX7r24K8na+59" +
       "J4CqoR6hI3EMOILy7izXsaiEon47\nEsEKxlGDYTlZzMSR30NW8EnHmi3uSI" +
       "Q0ikezAosIVpmBspNdfTJ054X3Gz6WHvtNr6mkO44Srkqo\npRjrMYcQ2L/4" +
       "cuLkC5eP7ZeBzkeao5DtpRjVc9KQ1VWQ2FVLlHNve9upFyOvXvAzuaoovd9x" +
       "8JxI\nZO6JhY5XPsWvQalDybn0MAGgwEdqQr4C8YzI9e0lh+Jdy5MIcJWXyY" +
       "Bo9n5msqkjv597fYWmjBE8\n60p1bJTPbuVhgKM6nAIAYF2Ct8dXUrSjwOyg" +
       "juX6qbwLjj3yW+PT+PwB1fVaF9fabtPL3vPGtyRy\nf6O+RP3Wc8veysgMYU" +
       "Uny7UNy3vG97Lp4a7vB7adni/3MgB2dt2UM6m3zKx/KDhJPwsI/OULo+Ki\n" +
       "W8wULbUYEOoQuKdN4bvYqZMZ6pRmNIERK+HbIQCUb43ylwe2/LPWuOOu7Xfv" +
       "0KY97NJpz+Ik7MqR\nQlOQ1FKWxQg2tUPU6PbNtIzwfoOamGmqIR7B2dR8r2" +
       "hYauUS3XMon1NvcZwiTC6lkKEt2r/mHfd5\nhTflnOq2U8TWgYGIdoQaWtjS" +
       "aMFqze/ZcFZYa7p2nxYunFjRgsvWFJxA1yJRuYJHT0/eaqm2YKsy\nc69AIJ" +
       "n26AxmxORjVhiiDkZnCFdMSYHRZOHeGApHtsiARP4f+eN5+eORqMIRaIjO55" +
       "cGZi6Jzhcu\nxiWKVlYq9CaFh6VbR1CSBuV7K4xcy1svKGI+XbPsXJJKZe3W" +
       "jamfo5WLMehWTlEJh2ZhSJnJT1HP\ndb7549lLI22qFahRc3PFtFfKo8ZNqb" +
       "DBFs1o4800SOrzd2w8c3Tku1Qgb+h2jmrzCJKO7FT1KuYY\n8KW9WK9VPf9p" +
       "lFkuY+IxcNNsyVQUHkO3jv04jD4i9jp2efdSWPPz2rbUgJSDkaZiWBL3V3vF" +
       "nx01\nkuubFg6Gz9ktn8s7vzA218LsaniMlTTI0mYZsh1iUGlvrbrKVaAg5w" +
       "0lZnFULX6kxbqiyADIFYV4\nm7R9b1pL0ZzH6d9/0gz92Q0AAA==");
}
