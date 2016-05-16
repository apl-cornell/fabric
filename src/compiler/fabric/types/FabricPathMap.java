package fabric.types;

import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.Path;
import jif.types.PathMap;
import jif.types.VarMap;
import jif.types.label.Label;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * Extends the Jif PathMap with the path conflict label (CL).
 */
public class FabricPathMap extends PathMap {

  public FabricPathMap(JifTypeSystem ts) {
    super(ts);
  }

  /**
   * Return a FabricPathMap with the CL label updated.
   * @param label The new CL label.
   * @return An updated copy of the FabricPathMap.
   */
  public FabricPathMap CL(Label label) {
    return set(FabricPath.CL, label);
  }

  /**
   * Get the current CL.
   * @return The current CL.
   */
  public Label CL() {
    // Default to {⊤→;⊥←} for CL since we'll be performing meets.
    //
    // TODO: Should I instead use NoAccesses?
    if (!map.containsKey(FabricPath.CL)) return ts.topLabel(Position.COMPILER_GENERATED);
    return get(FabricPath.CL);
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
    FabricPathMap r = (FabricPathMap) super.join(m);
    // CL is actually a meet across statements, unlike other path labels.
    r.set(FabricPath.CL, ts.meet(CL(), m.get(FabricPath.CL)));
    return r;
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

  @Override
  public Label get(Path p) {
    // Make sure we don't return NotTaken for CL.
    if (p.equals(FabricPath.CL) && !map.containsKey(p))
      return ts.topLabel(Position.COMPILER_GENERATED);
    return super.get(p);
  }

  @Override
  public boolean singlePath() {
    for (Path p : paths()) {
      if (p.equals(Path.N) || p.equals(Path.R) || p.equals(FabricPath.CL))
        continue;
      return false;
    }
    return true;
  }
}
