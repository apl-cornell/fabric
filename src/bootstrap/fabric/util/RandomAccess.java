package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
    
}
