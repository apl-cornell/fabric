package fabric.ast;

import jif.ast.AmbPrincipalNode_c;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import fabric.types.FabricContext;
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
    if (expr != null) {
      if (expr instanceof Worker) {
        // Local worker principal.
        FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
        FabricNodeFactory nf = (FabricNodeFactory) ar.nodeFactory();

        return nf.CanonicalPrincipalNode(position(),
            ts.workerLocalPrincipal(position()));
      } else if (expr instanceof RemoteWorkerGetter) {
        // Remote worker principal.
        RemoteWorkerGetter worker = (RemoteWorkerGetter) expr;
        FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
        FabricNodeFactory nf = (FabricNodeFactory) ar.nodeFactory();
        FabricContext ctx = (FabricContext) ar.context();

        return nf.CanonicalPrincipalNode(position(),
            ts.remoteWorkerPrincipal(worker, ctx, position()));

      } else if (expr instanceof Store) {
        // Local worker principal.
        FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
        FabricNodeFactory nf = (FabricNodeFactory) ar.nodeFactory();
        FabricContext ctx = (FabricContext) ar.context();
        Store sg = (Store) expr;
        return nf.CanonicalPrincipalNode(position(),
            ts.storePrincipal(sg, ctx, position()));
      }
    }

    return super.disambiguate(ar);
  }
}
