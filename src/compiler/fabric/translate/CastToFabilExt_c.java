package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.extension.JifCastDel;
import jif.translate.CastToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.ParamInstance;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.types.FabricClassType;
import fabric.types.FabricSubstType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class CastToFabilExt_c extends CastToJavaExt_c {
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)node();
    Type exprType = c.expr().type();
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    FabricTypeSystem ts = (FabricTypeSystem)ffrw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)ffrw.java_nf();
    if (ts.isPrincipal(castType) 
     && (ts.typeEquals(ts.Worker(), exprType) 
      || ts.typeEquals(ts.RemoteWorker(), exprType))
      || ts.typeEquals(ts.Store(), exprType)) {
      return nf.Call(c.position(), c.expr(), nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    
    if (castType.isPrimitive() || !ts.isJifClass(castType)) {
      return rw.java_nf().Cast(c.position(), c.castType(), c.expr());      
    }

    List<Expr> args = new ArrayList<Expr>();
    if (((JifCastDel) c.del()).isToSubstJifClass()) {
      // add all the actual param expressions to args
      FabricSubstType t = (FabricSubstType) castType;
      JifSubst subst = (JifSubst) t.subst();
      JifPolyType base = (JifPolyType) t.base();
      for (ParamInstance pi : base.params()) {
        args.add(ffrw.paramToJava(subst.get(pi)));
      }
    }
    // add the actual expression being cast.
    args.add(c.expr());

    FabricClassType fct = (FabricClassType) castType;

    String jifImplClass = fct.fullName();
    if (fct.flags().isInterface()) {
      jifImplClass = ClassDeclToFabilExt_c.interfaceClassImplName(jifImplClass);
    }
    return ffrw.qq().parseExpr(jifImplClass + ".%s(%LE)",
        ClassDeclToFabilExt_c.castMethodName(fct), args);

  }
}
