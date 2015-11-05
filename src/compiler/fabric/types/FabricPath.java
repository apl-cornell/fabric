package fabric.types;

import jif.types.Path;

import polyglot.util.SerialVersionUID;

/**
 * Extend Jif Paths with an additional path for accessed confidentiality.
 */
public interface FabricPath extends Path {
  /**
   * Accessed Confidentiality: Join of the confidentiality of all accesses up to
   * and including the node.
   */
  public static final Path A = new FixedFabricPath("A");

  public static class FixedFabricPath extends FixedPath implements FabricPath {
    private static final long serialVersionUID = SerialVersionUID.generate();

    protected FixedFabricPath(String name) {
      super(name);
    }
  }
}
