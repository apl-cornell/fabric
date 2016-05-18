package fabric.extension;

import jif.extension.JifWhileExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricWhileExt extends JifWhileExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricWhileExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // TODO Auto-generated method stub
    // TODO Handle looping for staging rules.
    return super.labelCheckStmt(lc);
  }
}
