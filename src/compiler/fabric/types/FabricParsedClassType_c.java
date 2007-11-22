package fabric.types;

import polyglot.frontend.Source;
import polyglot.types.LazyClassInitializer;
import polyglot.types.ParsedClassType_c;
import polyglot.types.Type;
import polyglot.types.TypeSystem;

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
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.isFabric(this) && ts.typeEquals(ancestor, ts.FObject()))
      return true;
    
    return super.descendsFromImpl(ancestor);
  }
}
