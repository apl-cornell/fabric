package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class FabricPrincipalExprDel extends JL_c {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    PrincipalExprExt_c ext = (PrincipalExprExt_c)FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = (Expr)v.visitEdge(n, ext.location());
      ext = (PrincipalExprExt_c)ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    PrincipalExprExt_c ext = (PrincipalExprExt_c)FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Store())) {
        throw new SemanticException("The location needs to be a Store.", ext.location().position());
      }
    }
    return n;
  }
}