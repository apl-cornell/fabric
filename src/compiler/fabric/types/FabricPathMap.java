package fabric.types;

import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.Path;
import jif.types.PathMap;
import jif.types.VarMap;
import jif.types.label.Label;
import polyglot.types.SemanticException;
import polyglot.types.Type;

/**
 * Extends the Jif FabricPathMap with the Accessed Confidentiality path label (AC).
 */
public class FabricPathMap extends PathMap {

  public FabricPathMap(JifTypeSystem ts) {
    super(ts);
  }

  /**
   * Return a FabricPathMap with the AC label updated.
   * @param label The new AC label.
   * @return An updated copy of the FabricPathMap.
   */
  public FabricPathMap AC(Label label) {
    return set(FabricPath.AC, label);
  }

  /**
   * Get the current AC label.
   * @return The current AC label.
   */
  public Label AC() {
    return get(FabricPath.AC);
  }

  /* Below are overidden to avoid hilarious amounts of casting elsewhere. */

  @Override
  public FabricPathMap N(Label label) {
    return (FabricPathMap) super.N(label);
  }

  @Override
  public FabricPathMap NV(Label label) {
    return (FabricPathMap) super.NV(label);
  }

  @Override
  public FabricPathMap R(Label label) {
    return (FabricPathMap) super.R(label);
  }

  @Override
  public FabricPathMap exc(Label label, Type type) {
    return (FabricPathMap) super.exc(label, type);
  }

  @Override
  public FabricPathMap exception(Type type, Label label) {
    return (FabricPathMap) super.exception(type, label);
  }

  @Override
  public FabricPathMap join(PathMap m) {
    return (FabricPathMap) super.join(m);
  }

  @Override
  public FabricPathMap set(Path p, Label L) {
    return (FabricPathMap) super.set(p, L);
  }

  @Override
  public FabricPathMap subst(LabelSubstitution subst) throws SemanticException {
    return (FabricPathMap) super.subst(subst);
  }

  @Override
  public FabricPathMap subst(VarMap bounds) {
    return (FabricPathMap) super.subst(bounds);
  }
}
