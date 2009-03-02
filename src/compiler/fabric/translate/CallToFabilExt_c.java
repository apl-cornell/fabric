package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILCall;
import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricCall;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.CallToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class CallToFabilExt_c extends CallToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabricCall c = (FabricCall)node();
    Expr e = super.exprToJava(rw);
    if (e instanceof FabILCall) {
      FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
      
      FabILCall result = (FabILCall)e;
      
      if (c.remoteClient() != null) {
        result = result.remoteClient(c.remoteClient());
        List<Expr> args = new ArrayList<Expr>(result.arguments().size());
        // The first argument is actually a principal.
        args.add(nf.Call(Position.compilerGenerated(), 
                         nf.Local(Position.compilerGenerated(), nf.Id(Position.compilerGenerated(), "client$")), 
                         nf.Id(Position.compilerGenerated(), "getPrincipal")));
        args.addAll(result.arguments().subList(1, result.arguments().size()));
        result = (FabILCall)result.arguments(args);
      }

      return result;
    }
    return e;
  }
}
