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
import polyglot.types.FieldInstance;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;

public class DynamicPrincipalToFabilExpr_c extends DynamicPrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    DynamicPrincipal dp = (DynamicPrincipal)principal;
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();

    if(!dp.path().isUninterpreted()) {
      
      if (dp.path() instanceof AccessPathLocal) {
        AccessPathLocal apl = (AccessPathLocal) dp.path();
        LocalInstance li = apl.localInstance();
        if (ts.equals(li, ts.workerLocalInstance())) {
          // Local worker.
          return nf.Call(li.position(), rw.qq().parseExpr("worker$"), nf.Id(
              Position.compilerGenerated(), "getPrincipal"));
        } 
        else if (ts.equals(li.type(), ts.RemoteWorker())
            || ts.equals(li.type(), ts.Store())) {
          
          Expr pathExpr = accessPathToExpr(rw, dp.path());
          return nf.Call(pathExpr.position(), pathExpr, nf.Id(Position
              .compilerGenerated(), "getPrincipal"));
        }
      } else if(dp.path() instanceof AccessPathField) {
        AccessPathField apf = (AccessPathField) dp.path();
        FieldInstance fi = apf.fieldInstance();
        
        if (ts.equals(fi.type(), ts.RemoteWorker())
            || ts.equals(fi.type(), ts.Store())) {
          
          Expr pathExpr = accessPathToExpr(rw, dp.path());
          return nf.Call(pathExpr.position(), pathExpr, nf.Id(Position
              .compilerGenerated(), "getPrincipal"));
        }
      } 

    }
    return super.toJava(principal, rw);
  }

  private static Expr accessPathToExpr(JifToJavaRewriter rw, AccessPath ap) {
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();

    if(ap instanceof AccessPathThis) {
      return nf.This(ap.position());
    }
    else if(ap instanceof AccessPathLocal) {
      LocalInstance li = ((AccessPathLocal) ap).localInstance();
      return nf.Local(li.position(), nf.Id(li.position(), li.name()));
    }
    else if(ap instanceof AccessPathField) {
      AccessPathField apf = (AccessPathField) ap;
      FieldInstance fi = apf.fieldInstance();
      return nf.Field(ap.position(), accessPathToExpr(rw, apf.path()),
          nf.Id(fi.position(), fi.name()));
    }
    throw new InternalCompilerError("Don't know how to translate final access path: " + ap);
  }
}
