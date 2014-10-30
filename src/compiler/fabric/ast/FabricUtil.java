package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import fabric.extension.FabricExt;

public class FabricUtil {
  /**
   * Gets the Fabric extension. Note that Fabric extensions are NOT subclasses
   * of Jif extensions.
   *
   * @param n
   */
  public static FabricExt fabricExt(Node n) {
    Ext ext = n.ext();
    while (ext != null && !(ext instanceof FabricExt)) {
      ext = ext.ext();
    }
    return (FabricExt) ext;
  }

  public static <N extends Node> N updateFabricExt(N n, FabricExt fab) {
    return (N) n.ext(updateFabricExt(n.ext(), fab));
  }

  private static Ext updateFabricExt(Ext e, FabricExt fab) {
    if (e instanceof FabricExt) return fab;
    if (e == null) return e;
    return e.ext(updateFabricExt(e.ext(), fab));
  }
}
