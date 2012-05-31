package fabric.translate;

import jif.ast.PrincipalExpr;
import jif.translate.JifToJavaRewriter;
import jif.translate.PrincipalExprToJavaExt_c;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabric.ast.FabricUtil;
import fabric.extension.LocatedExt_c;
import fabric.extension.PrincipalExprExt_c;
import fabric.visit.FabricToFabilRewriter;

public class PrincipalExprToFabilExt_c extends PrincipalExprToJavaExt_c {

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) super.toJavaEnter(rw);
    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(node());
    return ffrw.pushLocation(ext.location());
  }

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    // toJava is called explicitly on principal types, without actually visiting
    // so we need to use this ugly hack to keep track of the proper location
    // to assign to new label objects, just like for labels.
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    PrincipalExpr n = (PrincipalExpr) node();
    PrincipalExprExt_c ext = (PrincipalExprExt_c) FabricUtil.fabricExt(n);
    Expr loc = ext.location();
    // if (loc == null) {
    // loc = fabilnf.StoreGetter(Position.compilerGenerated());
    // }
    rw = ffrw.pushLocation(loc);
    Node node = super.toJava(rw);
    return node;
  }

}
