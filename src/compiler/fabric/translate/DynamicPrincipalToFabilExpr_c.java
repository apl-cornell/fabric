package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.types.AccessPathLocalWorker;
import fabric.types.AccessPathStore;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;
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

public class DynamicPrincipalToFabilExpr_c
    extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw,
      Expr thisQualifier) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal) principal;
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
          Expr pathExpr = accessPathToExpr(frw, dp.path(), thisQualifier);
          return nf.Call(pathExpr.position(), pathExpr,
              nf.Id(Position.compilerGenerated(), "getPrincipal"));
        }
      } else if (dp.path() instanceof AccessPathStore) {
        AccessPathStore store = (AccessPathStore) dp.path();
        if (ts.isTransient(store.path().type())) {
          return frw.qq().parseExpr("Worker.getWorker().getPrincipal()");
        }

        Expr e;
        if (store.path() instanceof AccessPathThis
            && frw.staticThisExpr() != null
            && frw.context().inStaticContext()) {
          // safe to use "this" since it doesn't result in a fetch.
          e = frw.staticThisExpr();
        } else {
          e = accessPathToExpr(frw, store.path(), thisQualifier);
        }
        /* TODO XXX HUGE HACK. WE SHOULD NOT CALL fetch(). REMOVE AFTER SURROGATES PROBLEM IS FIXED. */
        return frw.qq().parseExpr("%E.fetch().$getStore().getPrincipal()", e);
      }
    }
    return super.toJava(principal, frw, thisQualifier);
  }

  @Override
  protected Expr accessPathToExpr(JifToJavaRewriter rw, AccessPath ap,
      Expr thisQualifier) {
    NodeFactory nf = rw.java_nf();
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;

    if (ap instanceof AccessPathThis) {
      if (frw.staticThisExpr() != null && frw.context().inStaticContext()) {
        // replace "this" principal with its store.
        /* TODO XXX HUGE HACK. WE SHOULD NOT CALL fetch(). REMOVE AFTER SURROGATES PROBLEM IS FIXED. */
        return frw.qq().parseExpr("%E.fetch().$getStore().getPrincipal()",
            frw.staticThisExpr());
      }

      return thisQualifier;
    } else if (ap instanceof AccessPathField) {
      AccessPathField apf = (AccessPathField) ap;
      FabricFieldInstance fi = (FabricFieldInstance) apf.fieldInstance();
      String splitName = fi.splitClassName();
      if (splitName == null)
        return super.accessPathToExpr(frw, apf, thisQualifier);

      return rw.qq().parseExpr("%E.%s.%s",
          accessPathToExpr(rw, apf.path(), thisQualifier), splitName,
          fi.name());
    } else {
      return super.accessPathToExpr(frw, ap, thisQualifier);
    }
  }
}
