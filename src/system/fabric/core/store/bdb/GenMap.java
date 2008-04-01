package fabric.core.store.bdb;

import fabric.lang.WrappedJavaInlineable;

public interface GenMap extends fabric.lang.Object {
    public static class $Proxy extends fabric.lang.Object.$Proxy implements GenMap {
        
        public static void main(fabric.lang.arrays.ObjectArray arg1) { GenMap.$Impl.main(arg1); }
        
        public $Proxy(GenMap.$Impl impl) { super(impl); }
        
        public $Proxy(fabric.client.Core core, long onum) { super(core, onum); }
    }
    
    public static class $Impl extends fabric.lang.Object.$Impl implements GenMap {
        
        public static void main(fabric.lang.arrays.ObjectArray<WrappedJavaInlineable<String>> args) {
            fabric.client.Core core = fabric.client.Client.getClient().getCore(args.get(0).obj);
            {
                boolean $commit0 = true;
                fabric.client.TransactionManager.INSTANCE.startTransaction();
                try {
                    fabric.util.HashMap.$Impl m = new fabric.util.HashMap.$Impl(core);
                    m.$forceRelocate(core, 0L);
                }
                catch (final Throwable $_) {
                    $commit0 = false;
                    throw new fabric.client.AbortException($_);
                }
                finally {
                    if ($commit0) {
                        fabric.client.TransactionManager.INSTANCE.commitTransaction();
                    } else {
                        fabric.client.TransactionManager.INSTANCE.abortTransaction();
                    }
                }
            }
        }
        
        public $Impl(fabric.client.Core $location) { super($location); }
        
        protected fabric.lang.Object.$Proxy $makeProxy() { return new GenMap.$Proxy(this); }
        
        public void $serialize(java.io.ObjectOutput out, java.util.List refTypes, java.util.List intracoreRefs,
                               java.util.List intercoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
        }
        
        public $Impl(fabric.client.Core core, long onum, int version, fabric.common.Policy policy,
                     java.io.ObjectInput in, java.util.Iterator refTypes, java.util.Iterator intracoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(core, onum, version, policy, in, refTypes, intracoreRefs);
        }
    }
    
    interface $Static extends fabric.lang.Object.$Static {
        class $Proxy extends fabric.lang.Object.$Static.$Proxy implements GenMap.$Static {
            
            public $Proxy(GenMap.$Static.$Impl impl) { super(impl); }
            
            public $Proxy(fabric.client.Core core, long onum) { super(core, onum); }
        }
        
        class $Impl extends fabric.lang.Object.$Static.$Impl implements GenMap.$Static {
            
            public $Impl(fabric.client.Core core) throws fabric.client.UnreachableCoreException { super(core); }
            
            public $Impl(fabric.client.Core core, fabric.common.Policy policy)
                  throws fabric.client.UnreachableCoreException {
                super(core, policy);
            }
            
            protected fabric.lang.Object.$Proxy $makeProxy() {
                return new GenMap.$Static.$Proxy(this);
            }
        }
        
    }
    
    GenMap.$Static $static =
      new GenMap.$Static.$Impl(fabric.client.Client.getClient().getCore("core0"));
    final public static java.lang.String jlc$CompilerVersion$fabric = "0.1.0";
    final public static long jlc$SourceLastModified$fabric = 1206832433688L;
    final public static java.lang.String jlc$ClassType$fabric =
      ("H4sIAAAAAAAAAIVXXWgUVxS++5PNj9FkE02txiSmEWPFjUUQIUIJwdDoitvE" +
       "31VZ787c3YzenZnO\n3NlsYistBbVCC1IrFap9EYTiQ1FooS22oP2nIHnQJw" +
       "WxtIVWwYdSH1rsuffO7M7OrjEws3fuPf/n\nO+eeXH6AGmwLDeRw1tKUBJsx" +
       "iZ0YEx8pbNlEHaXYtnfCdka51juzaPDgqrEwQiUL9ZkGnclTg7lM\nNeRHv2" +
       "jZsGT/jdkIakujNk2fZJhpyqihM1JiadRaIIUssewRVSVqGsV1QtRJYmmYar" +
       "NAaOhp1GFr\neR0zxyL2BLENWuSEHbZjEkvo9DaTqFUxdJtZjsIMy2aoPXkY" +
       "F/GQwzQ6lNRsNpxEsZxGqGq/ho6h\ncBI15CjOA2FX0vNiSEgcGuP7QN6igZ" +
       "lWDivEY4ke0XSVod4gR9njgW1AAKyNBcKmjLKqqI5hA3VI\nkyjW80OTzNL0" +
       "PJA2GA5oYWjZU4UCUZOJlSM4TzIMLQ3SpeQRUDWLsHAWhpYEyYQkyNmyQM58" +
       "2doR\na/3vVOqfvjAKgc0qUSi3PwZMPQGmCZIjFtEVIhkfO4kz4/ucbomKJQ" +
       "FiSTOy6vNdyT++7pU0y+vQ\n7MgeJgrLKP9u7F4xN/Jrc4Sb0WQatsahUOW5" +
       "yGrKPRkumQDfrrJEfpjwDr+Z+G7fm5+QP8OoaRzF\nFIM6BX0cNRNdHXXXjb" +
       "BOajqRuztyOZuwcRSlYitmiG8IR06jhIejAdYmZlNiXTIRQo3whOBpR/Iv\n" +
       "xl8MxXdaWLexwq1Y/1ICqouhfkZsZh+wSB7wbMPBgRqiEhe8aDoUAp+6g/VF" +
       "AYyvGFQlVka5dP+n\n17dse+ekzBZHmGsSRKqiIFGlAIVCQvBz1cHi0Vd5kf" +
       "x1Zbj9vXX2Z2EUSaNmrVBwGM5SAsWFKTWm\niZphAl1xH5IFgAB9rVkAImA6" +
       "Q0GQAD5Ep2ih/iDgKmU6DisMKJo79uTmw8z0VY4NnsvFXLo0DTJz\nRNrWum" +
       "by4NZDJ/sjnGg6yoPOu9azpWeUh6e2X731853BCrAZGqipt1pOXi9B81OWoR" +
       "AV+lFF/MXn\n2yJ70O7TYRSFIoQ2xDCAB2q6J6ijqm6GvR7EgxVJogU5wypg" +
       "yo+8xtHCpixjurIjwNHKX+0SJzxY\nAQNF+3r8dmz97a8WfCs89jpdm68lTh" +
       "Im6yZeifVOixDYv/Nh6v0PHpzYLwLtRpqhmOlkqaaUhCFd\nIUhsZ50aTixd" +
       "fObsmo9ue5nsrEgfsSw8wxNZemtuxbnv8Xmob6gzW5slooyQ0IQ8Bfz9oliv" +
       "9R3y\n75UuCQdXsELGeIf3MlPIHv37+oWWPmkM51nu19Ev3qtcD/l6tSe5or" +
       "zMYaEVT+ucouuf2Puo9Ti+\ncVD2t47qAtuiO4XfZ66T1ZvfvVenXpuZYa6j" +
       "pEhoxbOgtu3iRvFca9vTe29s46U3gq7xkuidlzOj\nxIvLX41MaT+EOejcaq" +
       "i50qqZhv0WAywtAjeyzn3nO00iLb3CjDa3EQ7C0+Q2RPHLD+P81eEityYF\n" +
       "YZECQJotpoT6QAiV3eypM68IjMn75ubms8fPjz05K/MRvPV8hFciA4/CX3YN" +
       "iOqNZrEtfQqOC7XT\nQNUlL5xqKYdgGTx984RAEHbClCLqg/fRhJwI6jk6H8" +
       "Yzyuy1XRce/8LuClxVug+X01eqrZDd2NcY\nN90qxmOfflwIo8Y0ahcDFNbZ" +
       "bkwd3gjS4LQ96m4m0cKq8+pxRt7dw2U8dQfx5FMb7Ht+ZEVZFaZE\nq9tWCi" +
       "GTLybrYIaBLE3HVJYvgIcSPc+m6oQtZWkFGAmK7sxyuufib1fvTyyW5SgHux" +
       "dqZis/jxzu\nhF0LTN4QVs6nQVDfWLvy8rGJu9mw27o2MRQBHPHl1lI51WHp" +
       "ioeJzgomRqmhE34De2fyZtSMRHlM\nhsNSDWj498sydkIXf22Yt7E+s+tCeB" +
       "oUbk4d6Mrsl54tZIShaAHgKQ73mpJhP2wWDU2F7C2smlb4\nBbK05l8MOQgr" +
       "/XOHBq+b8R9l2XrDaiNMjDmHUj+kfOuYaZGcJkxplAAzxQ9jvKd5MxPfcUzP" + 
       "zw63\nz/g9/R+R/dnyMw0AAA==");
}
