package fabric.extension;

import jif.extension.JifDoExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricDoExt extends JifDoExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricDoExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // TODO Auto-generated method stub
    // TODO Need to handle "no staging during loops"
    return super.labelCheckStmt(lc);
  }
}
