package fabric.extension;

import jif.types.label.Label;
import fabric.types.FabricContext;

public class NewFabricArrayExt_c extends LocatedExt_c implements FabricExt {

  @Override
  protected Label referenceLabel(FabricContext ctx) {
     return ctx.pc();
  }
  
}
