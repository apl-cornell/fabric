package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.RemoteWorkerGetter;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.ExprToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class RemoteWorkerGetterToFabilExt_c extends ExprToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    
    RemoteWorkerGetter rcg = (RemoteWorkerGetter)node();
    
    return nf.Call(rcg.position(),
                   rw.qq().parseExpr("worker$"),
//                   nf.Local(Position.compilerGenerated(), 
//                            nf.Id(Position.compilerGenerated(), "worker$")), 
                   nf.Id(Position.compilerGenerated(), "getWorker"), 
                   rcg.remoteWorkerName());
  }
}
