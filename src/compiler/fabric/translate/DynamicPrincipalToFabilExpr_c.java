package fabric.translate;

import jif.translate.DynamicPrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathField;
import jif.types.label.AccessPathLocal;
import jif.types.label.AccessPathThis;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.types.AccessPathLocalWorker;
import fabric.types.AccessPathStore;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class DynamicPrincipalToFabilExpr_c extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal)principal;
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    FabILNodeFactory nf = (FabILNodeFactory) frw.java_nf();
    FabricTypeSystem ts = (FabricTypeSystem) frw.jif_ts();

    if (!dp.path().isUninterpreted()) {
      if (dp.path() instanceof AccessPathLocalWorker) {
        // Local worker.
        return nf.Call(dp.position(), frw.qq().parseExpr("worker$"),
            nf.Id(Position.compilerGenerated(), "getPrincipal"));

      } else if (dp.path() instanceof AccessPathLocal
          || dp.path() instanceof AccessPathField) {

        Type type = dp.path().type();
        if (ts.equals(type, ts.RemoteWorker()) || ts.equals(type, ts.Store())) {
          Expr pathExpr = accessPathToExpr(frw, dp.path());
          return nf.Call(pathExpr.position(), pathExpr,
              nf.Id(Position.compilerGenerated(), "getPrincipal"));
        }
      } else if (dp.path() instanceof AccessPathStore) {
        AccessPathStore store = (AccessPathStore) dp.path();
        Expr e = accessPathToExpr(frw, store.path());
        return frw.qq().parseExpr("%E.$getStore().getPrincipal()", e);
      }
    }
    return super.toJava(principal, frw);
  }

  @Override
  protected Expr accessPathToExpr(JifToJavaRewriter rw, AccessPath ap) {
    NodeFactory nf = rw.java_nf();
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;

    if (ap instanceof AccessPathThis) {
      if (frw.staticThisExpr() != null
          && frw.context().inStaticContext())
        return frw.staticThisExpr();
      else return nf.This(ap.position());
    } else
      return super.accessPathToExpr(frw, ap);
  }
}