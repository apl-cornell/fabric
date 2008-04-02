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

        @SuppressWarnings("deprecation")
        public static void main(ObjectArray<WrappedJavaInlineable<String>> args) {
            Core core = Client.getClient().getCore(args.get(0).obj);

            fabric.client.TransactionManager.INSTANCE.startTransaction();
            fabric.util.HashMap.$Impl m = new fabric.util.HashMap.$Impl(core);
            m.$forceRelocate(core, 0L);
            fabric.client.TransactionManager.INSTANCE.commitTransaction();
        }
    }
}
