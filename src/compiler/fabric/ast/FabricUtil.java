package fabric.ast;

import fabric.extension.FabricExt;
import fabric.extension.FabricStagingExt;

import polyglot.ast.Ext;
import polyglot.ast.Node;

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

  /**
   * Gets the Fabric staging extension. Note that Fabric extensions are NOT
   * subclasses of Jif extensions.
   *
   * @param n
   */
  public static FabricStagingExt fabricStagingExt(Node n) {
    Ext ext = n.ext();
    while (ext != null && !(ext instanceof FabricExt)) {
      ext = ext.ext();
    }
    return (FabricStagingExt) ext;
  }

  public static <N extends Node> N updateFabricStagingExt(N n, FabricExt fab) {
    return (N) n.ext(updateFabricStagingExt(n.ext(), fab));
  }

  private static Ext updateFabricStagingExt(Ext e, FabricExt fab) {
    if (e instanceof FabricStagingExt) return fab;
    if (e == null) return e;
    return e.ext(updateFabricStagingExt(e.ext(), fab));
  }
}
