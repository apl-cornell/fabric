package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
/**
 * This code is mostly copied from Jif.
 */
public interface Closure
  extends fabric.lang.Object
{
    
    java.lang.Object invoke();
    
    fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();
    
    fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Closure
    {
        
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
    
}
