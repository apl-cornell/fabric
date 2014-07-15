package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.extension.JifInstanceOfDel;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.InstanceOfToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.ParamInstance;
import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.types.FabricClassType;
import fabric.types.FabricSubstType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class InstanceOfToFabilExt_c extends InstanceOfToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Instanceof io = (Instanceof) this.node();
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    FabricTypeSystem ts = (FabricTypeSystem) ffrw.typeSystem();
    if (!ts.needsDynamicTypeMethods(compareType)) {
      return rw.java_nf()
          .Instanceof(io.position(), io.expr(), io.compareType());
    }

    List<Expr> args = new ArrayList<>();
    if (((JifInstanceOfDel) io.del()).isToSubstJifClass()) {
      // add all the actual param expressions to args
      FabricSubstType t = (FabricSubstType) compareType;
      JifSubst subst = (JifSubst) t.subst();
      JifPolyType base = (JifPolyType) t.base();
      for (ParamInstance pi : base.params()) {
        args.add(ffrw.paramToJava(subst.get(pi)));
      }
    }
    // add the actual expression being cast.
    args.add(io.expr());

    // add the access label
    FabricClassType fct = (FabricClassType) compareType;

    String jifImplClass = fct.fullName();
    if (fct.flags().isInterface()) {
      jifImplClass = ClassDeclToJavaExt_c.interfaceClassImplName(jifImplClass);
    }
    return ffrw.qq().parseExpr(
        jifImplClass + "." + ClassDeclToJavaExt_c.INSTANCEOF_METHOD_NAME
            + "(%LE)", (Object) args);
  }

}
