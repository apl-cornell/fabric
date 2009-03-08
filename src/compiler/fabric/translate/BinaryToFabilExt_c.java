package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import jif.translate.BinaryToJavaExt_c;
import jif.translate.JifToJavaRewriter;

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
  public Expr actsforToJava(JifToJavaRewriter rw, boolean isEquiv) throws SemanticException {
    Binary b = (Binary) node();
    String meth = isEquiv?"equivalentTo":"actsFor";
    String comparison = "jif.lang.PrincipalUtil." + meth + "((%E), (%E))";
    
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();

    Expr l = wrapExpr(ts, nf, lhsType, b.left());
    Expr r = wrapExpr(ts, nf, rhsType, b.right());
    
    return rw.qq().parseExpr(comparison, l, r);
  }
  
  protected Expr wrapExpr(FabricTypeSystem ts, FabILNodeFactory nf, Type t, Expr e) {
    if (ts.typeEquals(t, ts.Client()) || ts.typeEquals(t, ts.RemoteClient()) || ts.typeEquals(t, ts.Core())) {
      // Local/remote client or core
      return nf.Call(Position.compilerGenerated(), e, nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    return e;
  }
}
