package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * This code is mostly copied from Jif.
 */
public interface Closure extends fabric.lang.Object {
    java.lang.Object invoke();
    
    fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();
    
    fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Closure {
        public java.lang.Object invoke() {
            return ((fabric.lang.security.Closure) fetch()).invoke();
        }
        
        public fabric.lang.security.Principal
          jif$getfabric_lang_security_Closure_P() {
            return ((fabric.lang.security.Closure) fetch()).
              jif$getfabric_lang_security_Closure_P();
        }
        
        public fabric.lang.security.Label jif$getfabric_lang_security_Closure_L(
          ) {
            return ((fabric.lang.security.Closure) fetch()).
              jif$getfabric_lang_security_Closure_L();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -78, -9, -86, 124, 123,
    103, -4, 4, -98, -114, 91, -55, -53, 39, -5, 119, -26, -91, 50, 2, -117, 36,
    -50, 45, -79, -115, -79, 79, -107, -108, -10, -3 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
