package fabric.ast;

import jif.ast.AmbPrincipalNode_c;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import fabric.types.FabricTypeSystem;

/**
 * In Fabric, objects of <code>Worker</code> and <code>RemoteWorker</code> are
 * treated as principals automatically.
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
    if (expr != null && expr instanceof Worker) {
      // Local worker principal.
      FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
      FabricNodeFactory nf = (FabricNodeFactory) ar.nodeFactory();

      return nf.CanonicalPrincipalNode(position(),
          ts.workerLocalPrincipal(position()));
    }

    return super.disambiguate(ar);
  }
}
