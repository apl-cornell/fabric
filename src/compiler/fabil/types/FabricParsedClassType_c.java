package fabil.types;

import polyglot.frontend.Source;
import polyglot.types.*;

public class FabricParsedClassType_c extends ParsedClassType_c {

  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ClassType_c#descendsFromImpl(polyglot.types.Type)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    // All Fabric interface types descend from Fabric.lang.Object.
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
}
