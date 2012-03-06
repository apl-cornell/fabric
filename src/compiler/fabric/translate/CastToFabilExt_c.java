package fabric.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricSubstClassType_c;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import jif.extension.JifCastDel;
import jif.translate.CastToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

public class CastToFabilExt_c extends CastToJavaExt_c {
  protected Type exprType;
  
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)this.node();
    exprType = c.expr().type();
    return super.toJavaEnter(rw);
  }
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)node();
    
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    
    if (ts.isPrincipal(castType) 
     && (ts.typeEquals(ts.Worker(), exprType) 
      || ts.typeEquals(ts.RemoteWorker(), exprType))
      || ts.typeEquals(ts.Store(), exprType)) {
      return nf.Call(c.position(), c.expr(), nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    
    if (!((JifCastDel)c.del()).isToSubstJifClass()) {
      return rw.java_nf().Cast(c.position(), c.castType(), c.expr());
      
    }

    List args = new ArrayList();

    // add all the actual param expressions to args
    JifSubstType t = (JifSubstType)this.castType;
    JifSubst subst = (JifSubst)t.subst();
    JifPolyType base = (JifPolyType)t.base();
    for (Iterator iter = base.params().iterator(); iter.hasNext(); ) {
      ParamInstance pi = (ParamInstance)iter.next();
      args.add(rw.paramToJava(subst.get(pi)));
    }

    // add the actual expression being cast.
    args.add(c.expr());
    
    // add the access label
    if (castType instanceof FabricSubstClassType_c) {
      FabricSubstClassType_c fpct = (FabricSubstClassType_c) castType;
      ConfPolicy cp = fpct.accessPolicy();
      Label accessLabel = ts.pairLabel(cp.position(), cp, ts.topIntegPolicy(cp.position()));
      Expr accessLabelExpr = rw.labelToJava(accessLabel);
      args.add(accessLabelExpr);
    }

    JifSubstType jst = (JifSubstType)castType;
    String jifImplClass = jst.fullName();
    if (jst.flags().isInterface()) {
      jifImplClass = ClassDeclToFabilExt_c.interfaceClassImplName(jifImplClass);
    }
    return rw.qq().parseExpr(jifImplClass + ".%s(%LE)", ClassDeclToFabilExt_c.castMethodName(jst), args);
  }
}
