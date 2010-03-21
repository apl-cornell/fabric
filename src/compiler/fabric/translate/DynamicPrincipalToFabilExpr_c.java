package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.DynamicPrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.label.AccessPathLocal;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;

public class DynamicPrincipalToFabilExpr_c extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal)principal;
    if (dp.path() instanceof AccessPathLocal) {
      AccessPathLocal apl = (AccessPathLocal)dp.path();
      LocalInstance li = apl.localInstance();

      FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
      FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();

      if (ts.equals(li, ts.workerLocalInstance())) {
        // Local worker.
        return nf.Call(li.position(), 
//                       nf.Local(li.position(), nf.Id(li.position(), "worker$")),
                       rw.qq().parseExpr("worker$"),
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
      else if (ts.equals(li.type(), ts.RemoteWorker())) {
        // Remote worker
        return nf.Call(li.position(), 
                       nf.Local(li.position(), nf.Id(li.position(), li.name())), 
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
      else if (ts.equals(li.type(), ts.Store())) {
        // Store
        return nf.Call(li.position(), 
                       nf.Local(li.position(), nf.Id(li.position(), li.name())), 
                       nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }
    }
    
    return super.toJava(principal, rw);
  }
}
