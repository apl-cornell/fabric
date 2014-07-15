package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.translate.JifToJavaRewriter;
import jif.translate.NewToJavaExt_c;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.JifSubstType;
import jif.types.ParamInstance;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.extension.LocatedExt_c;
import fabric.extension.NewExt_c;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class NewToFabilExt_c extends NewToJavaExt_c {

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) super.toJavaEnter(rw);
    FabricClassType ct = (FabricClassType) objectType.toClass();
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();

    if (ts.isFabricClass(ct)) {
      // For non-fabric classes, there cannot be location or field label.
      LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(node());
      Expr loc = ext.location();
      return ffrw.pushLocation(loc);
    } else return ffrw;

  }

  @Override
  /**
   * This method needs more comments, particularly explaining how the location arguments are handled.
   */
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    New n = (New) node();
    FabricClassType ct = (FabricClassType) objectType.toClass();

    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();

    Expr loc = null;

    if (ts.isFabricClass(ct)) {
      // For non-fabric classes, there cannot be location or labels.
      NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
      // set location
      loc = ext.location();
      // if location is implicit, use StoreGetter to inherit location from
      // the context
      Expr labelloc = (loc != null) ? loc : nf.StoreGetter(n.position());
      // push the new location
      rw = ((FabricToFabilRewriter) rw).pushLocation(labelloc);
    }

    if (!rw.jif_ts().isParamsRuntimeRep(ct)
        || (ct instanceof JifSubstType && !rw.jif_ts().isParamsRuntimeRep(
            ((JifSubstType) ct).base()))) {
      // only rewrite creation of classes where params are runtime represented.
      n =
          nf.New(n.position(), n.qualifier(), n.objectType(), loc,
              n.arguments(), n.body());
      return n;
    }

    List<Expr> paramargs = new ArrayList<>();

    if (ct instanceof JifSubstType
        && rw.jif_ts().isParamsRuntimeRep(((JifSubstType) ct).base())) {
      // add all the actual param expressions to args
      JifSubstType t = (JifSubstType) ct;
      JifSubst subst = (JifSubst) t.subst();
      JifPolyType base = (JifPolyType) t.base();
      for (ParamInstance pi : base.params()) {
        paramargs.add(rw.paramToJava(subst.get(pi)));
      }
    }

    // use the appropriate string for the constructor invocation.
    if (!ts.isSignature(ct)) {
      String name = ClassDeclToFabilExt_c.jifConstructorTranslatedName(ct);
      New newExpr =
          nf.New(n.position(), n.qualifier(), n.objectType(), loc, paramargs);
      return rw.qq().parseExpr("(%T) %E.%s(%LE)", n.objectType(), newExpr,
          name, n.arguments());
    } else {
      // ct represents params at runtime, but is a Java class with a
      // Jif signature.
      List<Expr> allArgs =
          new ArrayList<>(paramargs.size() + n.arguments().size());
      allArgs.addAll(paramargs);
      allArgs.addAll(n.arguments());
      return rw.qq().parseExpr("new %T(%LE)", n.objectType(), allArgs);
    }
  }

}
