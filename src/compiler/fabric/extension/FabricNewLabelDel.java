package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class FabricNewLabelDel extends JL_c {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    LocatedExt_c ext = (LocatedExt_c)FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = (Expr)v.visitEdge(n, ext.location());
      ext = ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    LocatedExt_c ext = (LocatedExt_c)FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Store())) {
        throw new SemanticException("The location needs to be a Store.", ext.location().position());
      }
    }
    return n;
  }
  
//  @Override
//  public Node disambiguateOverride(Node parent, AmbiguityRemover ar) throws SemanticException {
//    Node n = super.disambiguateOverride(parent, ar);
//    Ext jifExt = n.ext();
//    LocatedExt_c ext = (LocatedExt_c)jifExt.ext();
//    if (ext.location() != null) {
//      Expr loc = (Expr)ar.visitEdge(n, ext.location());
//      ext = (LocatedExt_c)ext.location(loc);
//      jifExt = jifExt.ext(ext);
//      return n.ext(jifExt);
//    }
//    return n;
//  }
//  
//  @Override
//  public Node typeCheckOverride(Node parent, TypeChecker tc) throws SemanticException {
//    Node n = super.typeCheckOverride(parent, tc);
//    Ext jifExt = n.ext();
//    LocatedExt_c ext = (LocatedExt_c)jifExt.ext();
//    if (ext.location() != null) {
//      Expr loc = (Expr)tc.visitEdge(n, ext.location());
//      ext = (LocatedExt_c)ext.location(loc);
//      jifExt = jifExt.ext(ext);
//      return n.ext(jifExt);
//    }
//    return n;    
//  }
}
