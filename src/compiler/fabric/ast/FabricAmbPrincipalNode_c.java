package fabric.ast;

import jif.ast.AmbPrincipalNode_c;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
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
 */
//XXX Should be replaced with extension
@Deprecated
public class FabricAmbPrincipalNode_c extends AmbPrincipalNode_c {
  @Deprecated
  public FabricAmbPrincipalNode_c(Position pos, Expr expr) {
    this(pos, expr, null);
  }

  public FabricAmbPrincipalNode_c(Position pos, Expr expr, Ext ext) {
    super(pos, expr, ext);
  }

  @Deprecated
  public FabricAmbPrincipalNode_c(Position pos, Id name) {
    this(pos, name, null);
  }

  public FabricAmbPrincipalNode_c(Position pos, Id name, Ext ext) {
    super(pos, name, ext);
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
        if (!ar.isASTDisambiguated(expr)) {
          ar.job().extensionInfo().scheduler().currentGoal()
          .setUnreachableThisRun();
          return this;
        }

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
