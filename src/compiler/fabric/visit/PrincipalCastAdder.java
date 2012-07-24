package fabric.visit;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabric.types.FabricTypeSystem;

public class PrincipalCastAdder extends AscriptionVisitor {
  public PrincipalCastAdder(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Expr ascribe(Expr e, Type toType) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();
    if (ts.isPrincipal(toType)
        && (ts.typeEquals(ts.Worker(), e.type())
            || ts.typeEquals(ts.RemoteWorker(), e.type()) || ts.typeEquals(
            ts.Store(), e.type()))) {
      Cast result =
          nf.Cast(e.position(),
              nf.CanonicalTypeNode(Position.compilerGenerated(), toType), e);
      return result.type(toType);
    }
    return e;
  }
}
