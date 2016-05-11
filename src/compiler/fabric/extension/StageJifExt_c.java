package fabric.extension;

import jif.extension.JifStmtExt_c;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class StageJifExt_c extends JifStmtExt_c {
  public StageJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    //TODO
    return node();
  }
}
