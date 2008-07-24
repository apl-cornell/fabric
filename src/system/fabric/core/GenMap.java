package fabric.core;

import fabric.client.Client;
import fabric.client.Core;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;

/**
 * This class is intended to generate serialized object mappings. It should be
 * run through the tailor script so that the framework can be initialized
 */
public interface GenMap {
    public static class $Impl {

        @SuppressWarnings({ "deprecation", "unchecked" })
        public static void main(ObjectArray args) {
            Core core = Client.getClient().getCore(((WrappedJavaInlineable<String>) args.get(0)).obj);

            fabric.client.transaction.TransactionManager.getInstance().startTransaction();
            // XXX Replace with a real label.
            fabric.util.HashMap.$Impl m = new fabric.util.HashMap.$Impl(core, null);
            m.$forceRelocate(core, 0L);
            fabric.client.transaction.TransactionManager.getInstance().commitTransaction();
        }
    }
}
