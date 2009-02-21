package fabric.translate;

import fabil.ast.FabILCall;
import fabric.ast.FabricCall;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import jif.translate.CallToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class CallToFabilExt_c extends CallToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabricCall c = (FabricCall)node();
    Expr e = super.exprToJava(rw);
    if (e instanceof FabILCall) {
      return ((FabILCall)e).remoteClient(c.remoteClient());
    }
    return e;
  }
}
