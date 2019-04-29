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
