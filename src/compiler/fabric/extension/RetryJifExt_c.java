package fabric.extension;

import jif.extension.JifBranchExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import fabric.ast.RetryStmt;

public class RetryJifExt_c extends JifBranchExt {
  public RetryJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) {
    RetryStmt retry = (RetryStmt) node();

    JifTypeSystem ts = lc.jifTypeSystem();
    JifContext A = lc.jifContext();
    A = (JifContext) retry.del().enterScope(A);

    PathMap X = ts.pathMap();
    // prevent the single path rule from being used.
    X = X.set(ts.gotoPath(retry.kind(), retry.label()), ts.topLabel());

    return updatePathMap(retry, X);
  }
}
