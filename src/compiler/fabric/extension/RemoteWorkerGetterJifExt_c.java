package fabric.extension;

import jif.extension.JifExprExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.ast.RemoteWorkerGetter;

public class RemoteWorkerGetterJifExt_c extends JifExprExt {
  public RemoteWorkerGetterJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    RemoteWorkerGetter rcg = (RemoteWorkerGetter) node();

    JifContext A = lc.jifContext();
    A = (JifContext) rcg.del().enterScope(A);

    Expr rcName = rcg.remoteWorkerName();
    rcName = (Expr) lc.context(A).labelCheck(rcName);

    return updatePathMap(rcg.remoteWorkerName(rcName), getPathMap(rcName));
  }
}
