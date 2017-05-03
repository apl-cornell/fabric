package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import fabric.worker.Store;

public interface Iterable extends fabric.lang.Object {
    /**
   * Creates an iterator on the given store.
   */
    fabric.util.Iterator iterator(fabric.worker.Store store);
    
    /**
   * Creates an iterator on the local store.
   */
    fabric.util.Iterator iterator();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterable {
        public fabric.util.Iterator iterator(fabric.worker.Store arg1) {
            return ((fabric.util.Iterable) fetch()).iterator(arg1);
        }
        
        public fabric.util.Iterator iterator() {
            return ((fabric.util.Iterable) fetch()).iterator();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
