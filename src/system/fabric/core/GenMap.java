package fabric.core;

import java.io.ObjectOutputStream;
import java.util.Map;

import fabric.client.Client;
import fabric.client.LocalCore;
import fabric.client.RemoteCore;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.arrays.ObjectArray;
import fabric.util.HashMap;

/**
 * This class is intended to generate serialized object mappings. It should be
 * run through the tailor script so that the framework can be initialized
 */
public class GenMap {
  public static class $Impl {
    @SuppressWarnings("deprecation")
    public static void main(ObjectArray<WrappedJavaInlineable<String>> args)
        throws Exception {
      Client client = Client.getClient();
      long coreID = Long.parseLong(args.get(0).obj);
      RemoteCore core = client.getCore(coreID);

      ObjectOutputStream out = new ObjectOutputStream(System.out);
      Map<Long, SerializedObject> store =
          new java.util.HashMap<Long, SerializedObject>();

      LocalCore local = client.getLocalCore();
      Object.$Impl rootMap = new HashMap.$Impl(local);
      local.surrogate(core);
      rootMap.$forceRelocate(core, 0);
      store.put(0L, new SerializedObject(rootMap));

      for (Map.Entry<Long, Object.$Impl> entry : local.objects().entrySet()) {
        long onum = entry.getKey();
        Object.$Impl obj = entry.getValue();
        if (obj != rootMap) store.put(onum, new SerializedObject(obj));
      }

      out.writeObject(store);
    }
  }
}
