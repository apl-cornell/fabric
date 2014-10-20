package fabric.types;

import jif.types.JifTypeSystem;
import jif.types.label.ThisLabel;
import jif.types.label.ThisLabel_c;
import polyglot.types.ReferenceType;
import polyglot.util.Position;
import fabric.translate.FabricThisLabelToFabilExpr_c;

/**
 *
 */
public class FabricThisLabel_c extends ThisLabel_c implements ThisLabel {

  /**
   * @param ts
   * @param ct
   * @param pos
   */
  public FabricThisLabel_c(JifTypeSystem ts, ReferenceType ct, Position pos) {
    super(ts, ct, pos);
    this.toJava = new FabricThisLabelToFabilExpr_c();
  }

}
