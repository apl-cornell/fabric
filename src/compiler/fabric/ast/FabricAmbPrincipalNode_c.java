package fabric.ast;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import jif.ast.AmbPrincipalNode_c;

/**
 * In Fabric, objects of <code>Client</code> and <code>RemoteClient</code> are 
 * treated as principals automatically.
 * 
 * TODO For now, we only allow <code>Client</code>/<code>RemoteClient</code> 
 * variables to appear as principals. 
 * 
 * @author qixin
 */
public class FabricAmbPrincipalNode_c extends AmbPrincipalNode_c {
  public FabricAmbPrincipalNode_c(Position pos, Expr expr) {
    super(pos, expr);
  }
  
  public FabricAmbPrincipalNode_c(Position pos, Id name) {
    super(pos, name);
  }
  
  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    if (expr != null && expr instanceof Client) {
      // Local client principal.
      FabricTypeSystem ts = (FabricTypeSystem)ar.typeSystem();
      FabricNodeFactory nf = (FabricNodeFactory)ar.nodeFactory();

      return nf.CanonicalPrincipalNode(position(), ts.clientPrincipal(position()));
    }
    
    return super.disambiguate(ar);
  }  
}
