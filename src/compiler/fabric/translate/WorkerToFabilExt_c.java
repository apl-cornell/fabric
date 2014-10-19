package fabric.translate;

import jif.translate.ExprToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class WorkerToFabilExt_c extends ExprToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    return rw.qq().parseExpr("Worker.getWorker()");
  }
}
