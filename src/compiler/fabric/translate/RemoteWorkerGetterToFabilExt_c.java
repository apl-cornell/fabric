package fabric.translate;

import jif.translate.ExprToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.ast.RemoteWorkerGetter;

public class RemoteWorkerGetterToFabilExt_c extends ExprToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();

    RemoteWorkerGetter rcg = (RemoteWorkerGetter) node();

    return nf.Call(rcg.position(), rw.qq().parseExpr("Worker.getWorker()"),
        nf.Id(Position.compilerGenerated(), "getWorker"),
        rcg.remoteWorkerName());
  }
}
