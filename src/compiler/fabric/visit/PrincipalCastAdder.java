package fabric.visit;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Call;
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
  
  /**
   * @throws SemanticException  
   */
  @Override
  public Expr ascribe(Expr e, Type toType) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (ts.isPrincipal(toType) 
       &&  (  ts.typeEquals(ts.Worker(), e.type()) 
           || ts.typeEquals(ts.RemoteWorker(), e.type())
           || ts.typeEquals(ts.Store(), e.type())
           )
       ) {
      Call result = nf.Call(e.position(), e, nf.Id(Position.COMPILER_GENERATED, "getPrincipal"));
      return result.type(toType);
    }
    return e;
  }
}
