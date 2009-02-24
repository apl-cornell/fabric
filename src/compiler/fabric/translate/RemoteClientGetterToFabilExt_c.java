package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.RemoteClientGetter;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.ExprToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class RemoteClientGetterToFabilExt_c extends ExprToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    
    RemoteClientGetter rcg = (RemoteClientGetter)node();
    
    return nf.Call(rcg.position(), 
                   nf.Local(Position.compilerGenerated(), 
                            nf.Id(Position.compilerGenerated(), "client$")), 
                   nf.Id(Position.compilerGenerated(), "getClient"), 
                   rcg.remoteClientName());
  }
}
