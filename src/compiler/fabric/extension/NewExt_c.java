package fabric.extension;

import jif.types.JifConstructorInstance;
import jif.types.label.Label;
import polyglot.ast.New;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;

public class NewExt_c extends LocatedExt_c implements FabricExt {

  @Override
  protected boolean requiresLocation(FabricTypeSystem ts) {
    New n = (New) node();
    return ts.isFabricClass(n.type());
  }

  @Override
  protected Label referenceLabel(FabricContext ctx) {
    New n = (New) node();
    FabricTypeSystem ts = (FabricTypeSystem) ctx.typeSystem();
    JifConstructorInstance ci =
        (JifConstructorInstance) n.constructorInstance();
    return ts.join(ctx.pc(), ci.returnLabel());
  }

}
