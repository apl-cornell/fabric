package fabric.extension;

import jif.types.label.Label;
import fabric.types.FabricContext;

public class NewLabelExt_c extends LocatedExt_c {

  @Override
  protected Label referenceLabel(FabricContext ctx) {
    //XXX: revisit?
    return ctx.pc();
  }

  @Override
  protected Label instantiateThisLabel(FabricContext ctx, Label label) {
    return label;
  }

}
