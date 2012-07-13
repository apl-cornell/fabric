package fabric.extension;

import jif.types.label.Label;
import fabric.types.FabricContext;

public class PrincipalExprExt_c extends LocatedExt_c {

  @Override
  protected Label referenceLabel(FabricContext ctx) {
    // XXX: this is WRONG
    return ctx.pc();
  }

}
