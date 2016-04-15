package fabric.types;

import jif.types.Path;

import polyglot.util.SerialVersionUID;

/**
 * Extend Jif Paths with an additional path for path conflict label.
 */
public interface FabricPath extends Path {
  /**
   * Path Conflict Label: Meet of the conflict labels of all accesses up to the
   * current point.
   *
   * Note that the FabricPathMap handles this meet vs. join difference.
   */
  public static final Path CL = new FixedFabricPath("CL");

  public static class FixedFabricPath extends FixedPath implements FabricPath {
    private static final long serialVersionUID = SerialVersionUID.generate();

    protected FixedFabricPath(String name) {
      super(name);
    }
  }
}
