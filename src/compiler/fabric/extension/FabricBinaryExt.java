package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifBinaryExt;
import jif.translate.ToJavaExt;
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
    Binary b = (Binary) super.labelCheck(lc);
    if (b.operator().equals(Binary.COND_AND) || b.operator().equals(Binary.COND_OR)) {
      // Need to account for short circuiting.
      FabricTypeSystem ts = (FabricTypeSystem) lc.jifTypeSystem();
      FabricPathMap Xl = (FabricPathMap) getPathMap(b.left());
      FabricPathMap Xr = (FabricPathMap) getPathMap(b.right());
      if (!Xr.CL().equals(ts.noAccesses()) && !lc.context().labelEnv().leq(Xl.CL(), Xr.CL())) {
        // stage conflict label after left does not statically flow to the stage
        // conflict label after right (confidentiality check only), so we need
        // to stage when we're short circuiting.
        FabricStagingExt fse = FabricUtil.fabricStagingExt(b);
        fse.setStageCheck(Xr.CL());
      }
    }
    return b;
  }
}
