package fabric.common;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * FabricLocationFactory provides a factory method for generating fabric
 * locations.
 */
public class FabricLocationFactory {
  private static String prefix = "FABRIC_LOCATION_";
  private static int counter = 0;
  private static Map<URI, FabricLocation> locationPool =
      new HashMap<URI, FabricLocation>();

  public static FabricLocation getLocation(boolean isOutputLocation, URI uri) {
    FabricLocation l = locationPool.get(uri);
    if (l == null) {
      l = new FabricLocation_c(prefix + counter++, isOutputLocation, uri);
      locationPool.put(uri, l);
    }
    return l;
  }
}
