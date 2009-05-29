package fabric.types;

import polyglot.types.Type;
import polyglot.util.Position;
import jif.types.ConstArrayType_c;

/** The only ArrayType class created by the FabricTypeSystem.
 * @see FabricTypeSystem for further description
 * @author mdgeorge
 *
 */
public class FabricArrayType_c
     extends ConstArrayType_c
  implements FabricArrayType {
  
  protected boolean isNative;
  
  public FabricArrayType_c(FabricTypeSystem ts, Position pos, Type base,
                           boolean isConst, boolean isNonConst,
                           boolean isNative) {
    super(ts, pos, base, isConst, isNonConst);
    this.isNative = isNative;
  }

  public boolean isNative() {
    return isNative;
  }
  
}
