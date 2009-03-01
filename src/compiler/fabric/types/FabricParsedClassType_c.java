package fabric.types;

import java.util.List;

import jif.types.JifParsedPolyType_c;
import jif.types.label.Label;
import polyglot.frontend.Source;
import polyglot.types.*;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label defaultFieldLabel = null;
  private transient boolean fieldLabelFound = false;
  
  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ClassType_c#descendsFromImpl(polyglot.types.Type)
   */
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    // All Fabric interface types descend from fabric.lang.Object.
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject()) && flags().isInterface()) {
      // Determine whether we have a Fabric interface.
      // XXX Assume any class loaded from the DeserializedClassInitializer was
      // compiled with loom.
      if (job() != null
          || initializer() instanceof DeserializedClassInitializer)
        return true;
    }

    return super.descendsFromImpl(ancestor);
  }
  
  @SuppressWarnings("unchecked")
  public Label defaultFieldLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!fieldLabelFound) {
      FabricClassType superType = (FabricClassType)superType();
      if (superType != null && superType.defaultFieldLabel() != null) {
        defaultFieldLabel = superType.defaultFieldLabel();
      }
      else {
        for (FieldInstance fi : (List<FieldInstance>)fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            defaultFieldLabel = ts.labelOfType(t);
            break;
          }
        }
      }
      fieldLabelFound = true;
    }
    return defaultFieldLabel;
  }
}
