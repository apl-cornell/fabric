package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import jif.extension.JifNewArrayDel;

public class FabricNewArrayDel extends JifNewArrayDel {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    NewArrayExt_c ext = (NewArrayExt_c)FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = (Expr)v.visitEdge(n, ext.location());
      ext = (NewArrayExt_c)ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    NewArrayExt_c ext = (NewArrayExt_c)FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Core())) {
        throw new SemanticException("The location needs to be a Core.", ext.location().position());
      }
    }
    return n;
  }
}
