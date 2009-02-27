package fabric.extension;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class FabricNewDel extends JL_c {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    Ext jifExt = n.ext();
    NewExt_c ext = (NewExt_c)jifExt.ext();
    if (ext.location() != null) {
      Expr loc = (Expr)v.visitEdge(n, ext.location());
      ext = (NewExt_c)ext.location(loc);
      jifExt = jifExt.ext(ext);
      return n.ext(jifExt);
    }
    return n;
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    Node n = super.typeCheck(tc);
    Ext jifExt = n.ext();
    NewExt_c ext = (NewExt_c)jifExt.ext();
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Core())) {
        throw new SemanticException("The location needs to be a Core.", ext.location().position());
      }
    }
    return n;
  }
  
  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar) throws SemanticException {
    Node n = super.disambiguateOverride(parent, ar);
    Ext jifExt = n.ext();
    NewExt_c ext = (NewExt_c)jifExt.ext();
    if (ext.location() != null) {
      Expr loc = (Expr)ar.visitEdge(n, ext.location());
      ext = (NewExt_c)ext.location(loc);
      jifExt = jifExt.ext(ext);
      return n.ext(jifExt);
    }
    return n;
  }
  
  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc) throws SemanticException {
    Node n = super.typeCheckOverride(parent, tc);
    Ext jifExt = n.ext();
    NewExt_c ext = (NewExt_c)jifExt.ext();
    if (ext.location() != null) {
      Expr loc = (Expr)tc.visitEdge(n, ext.location());
      ext = (NewExt_c)ext.location(loc);
      jifExt = jifExt.ext(ext);
      return n.ext(jifExt);
    }
    return n;    
  }
}
