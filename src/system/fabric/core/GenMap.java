package fabric.core;

import java.io.ObjectOutputStream;
import fabric.client.Client;
import fabric.client.LocalCore;
import fabric.client.RemoteCore;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ResizableArray;
import fabric.util.HashMap;

/**
 * This class is intended to generate serialized object mappings. It should be
 * run through the tailor script so that the framework can be initialized
 */
public class GenMap {
  public static class $Impl {
    @SuppressWarnings("deprecation")
    public static void main(ResizableArray<WrappedJavaInlineable<String>> args)
        throws Exception {
      Client client = Client.getClient();
      String coreName = args.get(0).obj;
      RemoteCore core = client.getCore(coreName);

      ObjectOutputStream out = new ObjectOutputStream(System.out);
      LongKeyMap<SerializedObject> store =
          new LongKeyHashMap<SerializedObject>();

      LocalCore local = client.getLocalCore();
      Object.$Impl rootMap = new HashMap.$Impl(local);
      local.surrogate(core);
      rootMap.$forceRelocate(core, 0);
      
      rootMap.$version = 1;
      store.put(0L, new SerializedObject(rootMap));
      
      Object.$Impl obj = local.readObject(3);
      obj.$version = 1;
      store.put(3L, new SerializedObject(obj));
      out.writeObject(store);
    }
  }
}
