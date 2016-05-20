package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifBinaryExt;
import jif.translate.ToJavaExt;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Binary;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricBinaryExt extends JifBinaryExt { 
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricBinaryExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    FabricContext A = (FabricContext) lc.context();
    Label startingCL = A.conflictLabel();

    Binary b = (Binary) super.labelCheck(lc);
    if (b.operator().equals(Binary.COND_AND) || b.operator().equals(Binary.COND_OR)) {
      // Need to account for short circuiting.
      FabricPathMap Xr = (FabricPathMap) getPathMap(b.right());

      // stage conflict label after left does not statically flow to the stage
      // conflict label after right (confidentiality check only), so we need
      // to stage in cases where we're short circuiting.
      FabricStagingExt fse = FabricUtil.fabricStagingExt(b);
      fse.setStageCheck(startingCL.simplify(), Xr.CL().simplify(),
          (FabricContext) lc.context());
    }
    return b;
  }
}
