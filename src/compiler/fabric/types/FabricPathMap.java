package fabric.types;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jif.types.LabelSubstitution;
import jif.types.Path;
import jif.types.PathMap;
import jif.types.VarMap;
import jif.types.label.Label;

import polyglot.types.SemanticException;
import polyglot.types.Type;

/**
 * Extends the Jif PathMap with the path conflict label (CL).
 */
public class FabricPathMap extends PathMap {

  protected Map<Path, Label> conflictMap;

  public FabricPathMap(FabricTypeSystem ts) {
    super(ts);
    conflictMap = new HashMap<>(4);
  }

  @Override
  public FabricPathMap set(Path p, Label L) {
    FabricPathMap n = (FabricPathMap) super.set(p, L);
    n.conflictMap.putAll(conflictMap);
    return n;
  }

  public FabricPathMap setCL(Path p, Label L) {
    FabricPathMap n = (FabricPathMap) ts.pathMap();
    n.map.putAll(map);
    n.conflictMap.putAll(conflictMap);

    if (L instanceof NoAccesses) {
      n.conflictMap.remove(p);
    } else {
      n.conflictMap.put(p, L);
    }

    return n;
  }

  /**
   * Return a FabricPathMap with the CL label updated.
   * @param label The new CL label.
   * @return An updated copy of the FabricPathMap.
   */
  public FabricPathMap CL(Label label) {
    return setCL(FabricPath.CL, label);
  }

  /**
   * Get the current CL.
   * @return The current CL.
   */
  public Label CL() {
    if (!conflictMap.containsKey(FabricPath.CL))
      return ((FabricTypeSystem) ts).noAccesses();
    return getCL(FabricPath.CL);
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
    FabricPathMap fm = (FabricPathMap) m;
    FabricPathMap n = (FabricPathMap) super.join(m);
    n.conflictMap.putAll(map);

    // Iterate over the elements of X, joining those labels with the ones
    // in this and adding the ones that aren't there.
    for (Map.Entry<Path, Label> e : fm.conflictMap.entrySet()) {
      Path p = e.getKey();
      Label l1 = e.getValue();
      Label l2 = n.getCL(p);
      if (l2 instanceof NoAccesses) {
        n.conflictMap.put(p, l1);
      } else {
        n.conflictMap.put(p, ts.join(l1, l2));
      }
    }

    return n;
  }

  @Override
  public FabricPathMap subst(LabelSubstitution subst) throws SemanticException {
    return (FabricPathMap) super.subst(subst);
  }

  @Override
  public FabricPathMap subst(VarMap bounds) {
    return (FabricPathMap) super.subst(bounds);
  }

  /**
   * Get the conflict label associated with the given path.
   */
  public Label getCL(Path p) {
    if (!conflictMap.containsKey(p))
      return ((FabricTypeSystem) ts).noAccesses();
    return conflictMap.get(p);
  }

  public Set<Path> conflictPaths() {
    return new LinkedHashSet<>(conflictMap.keySet());
  }
}
