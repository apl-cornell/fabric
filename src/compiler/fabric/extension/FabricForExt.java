package fabric.extension;

import jif.extension.JifForExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricForExt extends JifForExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricForExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // TODO Auto-generated method stub
    // TODO: Need to handle loop rules for CL
    return super.labelCheckStmt(lc);
  }
}
