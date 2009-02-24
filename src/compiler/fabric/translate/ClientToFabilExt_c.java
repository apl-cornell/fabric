package fabric.translate;

import fabil.ast.FabILNodeFactory;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.ExprToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class ClientToFabilExt_c extends ExprToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    return nf.Local(Position.compilerGenerated(), nf.Id(Position.compilerGenerated(), "client$"));
  }
}
