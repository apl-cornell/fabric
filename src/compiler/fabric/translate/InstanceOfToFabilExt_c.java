package fabric.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fabric.types.FabricSubstClassType_c;
import fabric.types.FabricTypeSystem;

import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import jif.extension.JifInstanceOfDel;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.InstanceOfToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

public class InstanceOfToFabilExt_c extends InstanceOfToJavaExt_c {
  
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    return super.toJavaEnter(rw);
  }

  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Instanceof io = (Instanceof)this.node();
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();

    if (!((JifInstanceOfDel)io.del()).isToSubstJifClass()) {
        return rw.java_nf().Instanceof(io.position(), io.expr(), io.compareType());
    }

    List args = new ArrayList();

    // add all the actual param expressions to args
    JifSubstType t = (JifSubstType)compareType;
    JifSubst subst = (JifSubst)t.subst();
    JifPolyType base = (JifPolyType)t.base();
    for (Iterator iter = base.params().iterator(); iter.hasNext(); ) {
        ParamInstance pi = (ParamInstance)iter.next();
        args.add(rw.paramToJava(subst.get(pi)));
    }
    
    // add the access label
    if (compareType instanceof FabricSubstClassType_c) {
      FabricSubstClassType_c fpct = (FabricSubstClassType_c) compareType;
      ConfPolicy cp = fpct.accessPolicy();
      Label accessLabel = ts.pairLabel(cp.position(), cp, ts.topIntegPolicy(cp.position()));
      Expr accessLabelExpr = rw.labelToJava(accessLabel);
      args.add(accessLabelExpr);
    }

    // add the actual expression being cast.
    args.add(io.expr());

    String jifImplClass = ((JifSubstType)compareType).fullName();
    if (((JifSubstType)compareType).flags().isInterface()) {
        jifImplClass = ClassDeclToJavaExt_c.interfaceClassImplName(jifImplClass);
    }
    return rw.qq().parseExpr(jifImplClass + "." + ClassDeclToFabilExt_c.INSTANCEOF_METHOD_NAME + "(%LE)", (Object)args);
  }
  

}
