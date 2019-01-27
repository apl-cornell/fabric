package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public static final byte[] $classHash = new byte[] { 36, -10, 4, 5, -20, -1,
    65, 43, 20, 71, 27, -100, 46, 46, 0, 57, 32, 18, -34, 107, 105, 113, -68,
    -26, 86, -20, 19, -90, 84, -20, 86, 42 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
