package fabric.visit;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;

public class PrincipalCastAdder extends AscriptionVisitor {
  public PrincipalCastAdder(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }
  
  @Override
  public Expr ascribe(Expr e, Type toType) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (ts.isPrincipal(toType) 
     && (ts.typeEquals(ts.Client(), e.type()) 
      || ts.typeEquals(ts.RemoteClient(), e.type()))) {
      Cast result = nf.Cast(e.position(), 
                            nf.CanonicalTypeNode(Position.compilerGenerated(), toType), 
                            e);
      return result.type(toType);
    }
    return e;
  }
}
