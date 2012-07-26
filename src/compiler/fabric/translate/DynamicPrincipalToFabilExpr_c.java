package fabric.translate;

import jif.translate.DynamicPrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.label.AccessPathField;
import jif.types.label.AccessPathLocal;
import jif.types.label.AccessPathThis;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.FieldInstance;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.types.AccessPathLocalWorker;
import fabric.types.FabricTypeSystem;

public class DynamicPrincipalToFabilExpr_c extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal)principal;
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();

    if (!dp.path().isUninterpreted()) {
      if (dp.path() instanceof AccessPathLocalWorker) {
        // Local worker.
        return nf.Call(dp.position(), rw.qq().parseExpr("worker$"),
            nf.Id(Position.compilerGenerated(), "getPrincipal"));
      }

      if (dp.path() instanceof AccessPathLocal) {
        AccessPathLocal apl = (AccessPathLocal) dp.path();
        LocalInstance li = apl.localInstance();
        if (ts.equals(li.type(), ts.RemoteWorker())
            || ts.equals(li.type(), ts.Store())) {

          Expr pathExpr = accessPathToExpr(rw, dp.path());
          return nf.Call(pathExpr.position(), pathExpr, nf.Id(Position
              .compilerGenerated(), "getPrincipal"));
        }
      } else if (dp.path() instanceof AccessPathField) {
        AccessPathField apf = (AccessPathField) dp.path();
        FieldInstance fi = apf.fieldInstance();

        if (ts.equals(fi.type(), ts.RemoteWorker())
            || ts.equals(fi.type(), ts.Store())) {

          Expr pathExpr = accessPathToExpr(rw, dp.path());
          return nf.Call(pathExpr.position(), pathExpr, nf.Id(Position
              .compilerGenerated(), "getPrincipal"));
        }
      }
      else if (dp.path() instanceof AccessPathThis) {
        if (rw.staticThisPrincipal() != null)
          return rw.staticThisPrincipal();
        else
          return nf.This(dp.path().position());
      }
    }
    return super.toJava(principal, rw);
  }
}
