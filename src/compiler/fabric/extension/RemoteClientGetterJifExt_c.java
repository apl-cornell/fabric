package fabric.extension;

import fabric.ast.RemoteClientGetter;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.extension.JifExprExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.visit.LabelChecker;

public class RemoteClientGetterJifExt_c extends JifExprExt {
  public RemoteClientGetterJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    RemoteClientGetter rcg = (RemoteClientGetter)node();
    
    JifContext A = lc.jifContext();
    A = (JifContext)rcg.del().enterScope(A);
    
    Expr rcName = rcg.remoteClientName();
    rcName = (Expr)lc.context(A).labelCheck(rcName);
    
    return updatePathMap(rcg.remoteClientName(rcName), getPathMap(rcName));
  }
}
