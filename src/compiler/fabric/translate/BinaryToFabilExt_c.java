package fabric.translate;

import jif.translate.BinaryToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;

public class BinaryToFabilExt_c extends BinaryToJavaExt_c {
  protected Type lhsType, rhsType;

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    Binary b = (Binary) node();
    this.lhsType = b.left().type();
    this.rhsType = b.right().type();
    return super.toJavaEnter(rw);
  }

  @Override
  public Expr actsforToJava(JifToJavaRewriter rw, boolean isEquiv) {
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();

    Binary b = (Binary) node();
    String className;
    if (ts.isLabel(lhsType)) {
      className = rw.runtimeLabelUtil();
    } else {
      className = ts.PrincipalUtilClassName();
    }

    String meth = isEquiv ? "equivalentTo" : "actsFor";
    String comparison = className + "." + meth + "((%E), (%E))";

    Expr l = wrapExpr(ts, nf, lhsType, b.left());
    Expr r = wrapExpr(ts, nf, rhsType, b.right());

    return rw.qq().parseExpr(comparison, l, r);
  }

  protected Expr wrapExpr(FabricTypeSystem ts, FabILNodeFactory nf, Type t,
      Expr e) {
    if (ts.typeEquals(t, ts.Worker()) || ts.typeEquals(t, ts.RemoteWorker())
        || ts.typeEquals(t, ts.Store())) {
      // Local/remote worker or store
      return nf.Call(Position.compilerGenerated(), e,
          nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    return e;
  }
}
