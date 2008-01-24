package fabric.client.debug;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.core.SerializedObject;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Map;

/**
 * Counts the number of objects reachable from a given object.
 */
public class ObjectCount {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) showUsage();
    Client.initialize();

    for (String uri : args)
      countObjs(uri);

    Client.getClient().shutdown();
  }

  private static void countObjs(String uri) {
    URI path;
    try {
      path = new URI(uri);
    } catch (URISyntaxException e) {
      System.err.println("Ignoring invalid URI: " + uri);
      return;
    }

    Client client = Client.getClient();
    RemoteCore core = client.getCore(path.getHost());
    Map rootMap;
    try {
      rootMap = (Map) core.getRoot().$getProxy();
    } catch (UnreachableCoreException e) {
      System.err.println("Unreachable core: " + uri);
      return;
    }

    if (path.getPath().equals("") || path.getPath().equals("/")) {
      System.out.println(countReachable(rootMap) + " objects reachable from "
          + uri + " (including the root map itself).");
      return;
    }

    Object obj = rootMap.get(WrappedJavaInlineable.$wrap(path.getPath()));
    if (obj == null) {
      System.out.println("Object \"" + uri + "\" does not exist.");
      return;
    }

    obj = obj.$getProxy();
    System.out.println(countReachable(obj) + " objects reachable from " + uri
        + " (including the object itself).");
  }

  private static void showUsage() {
    System.err.println("Usage: ObjectCount [uri] ...");
    System.err.println("e.g.");
    System.err.println("  ObjectCount fab://core0/ fab://core1/OO7");
    System.exit(1);
  }

  @SuppressWarnings("deprecation")
  private static int countReachable(Object obj) {
    int result = 0;
    Set<Long> visited = new HashSet<Long>();
    Stack<Long> toVisit = new Stack<Long>();
    toVisit.add(obj.$getOnum());

    Client client = Client.getClient();
    Core core = obj.$getCore();

    while (!toVisit.isEmpty()) {
      long onum = toVisit.pop();
      visited.add(onum);

      Object.$Impl impl = client.fetchObject(core, onum);
      SerializedObject so = new SerializedObject(impl);
      for (long ref : so.intracoreRefs)
        if (!visited.contains(ref)) toVisit.push(ref);
      
      result++;
    }

    return result;
  }
}
