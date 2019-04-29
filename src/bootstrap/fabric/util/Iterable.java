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
