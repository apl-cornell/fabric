package fabric.extension;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import jif.extension.JifNewArrayDel;

public class FabricNewArrayDel extends JifNewArrayDel {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    Ext jifExt = n.ext();
    NewArrayExt_c ext = (NewArrayExt_c)jifExt.ext();
    if (ext.location() != null) {
      Expr loc = (Expr)v.visitEdge(n, ext.location());
      ext = (NewArrayExt_c)ext.location(loc);
      jifExt = jifExt.ext(ext);
      return n.ext(jifExt);
    }
    return n;
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    Ext jifExt = n.ext();
    NewArrayExt_c ext = (NewArrayExt_c)jifExt.ext();
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Core())) {
        throw new SemanticException("The location needs to be a Core.", ext.location().position());
      }
    }
    return n;
  }
}
