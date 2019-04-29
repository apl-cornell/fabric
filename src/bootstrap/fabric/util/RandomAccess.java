package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Marker interface used to inform <code>List</code> implementations that
 * they support fast (usually constant time) random access. This allows
 * generic list algorithms to tailor their behavior based on the list
 * type.
 * <p>
 *
 * For example, some sorts are n*log(n) on an array, but decay to quadratic
 * time on a linked list.  As a rule of thumb, this interface should be
 * used is this loop:<br>
 * <code>for (int i = 0, n = list.size(); i &lt; n; i++) list.get(i);</code>
 * <br>runs faster than this loop:<br>
 * <code>for (Iterator i = list.iterator(store); i.hasNext(); ) i.next();</code>
 *
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see List
 * @since 1.4
 * @status updated to 1.4
 */
public interface RandomAccess extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.RandomAccess {
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 125, -102, -25, 5,
    -113, 121, -99, 43, 32, 5, -40, -102, -55, 101, -32, 10, 57, 3, -18, 27,
    -56, 67, 36, 25, -123, -57, -62, 23, 87, 111, -36, 122 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
