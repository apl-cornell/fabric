package fabric.visit;

import jif.types.JifMethodInstance;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;


/**
 * A Visitor pass that adds explicit calls to getPrincipal() for expressions
 * with non-principal types that are implicitly cast to Principal.
 * 
 * <p>The wrapped types are currently:
 * <ul>
 *      <li>Store</li>
 *      <li>Worker</li>
 *      <li>RemoteStore</li> </ul> </p>
 */
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
    
    if (ts.isPrincipal(toType)) {
      JifMethodInstance getPrincipal;
      
      if      (ts.typeEquals(ts.Worker(), e.type()))
        getPrincipal = ts.WorkerGetPrincipalMethod();
      
      else if (ts.typeEquals(ts.RemoteWorker(), e.type()))
        getPrincipal = ts.RemoteWorkerGetPrincipalMethod();
      
      else if (ts.typeEquals(ts.Store(), e.type()))
        getPrincipal = ts.StoreGetPrincipalMethod();
      
      else
        return e;

      Call result = nf.Call(e.position(), e, nf.Id(Position.COMPILER_GENERATED, getPrincipal.name()));
      return result.methodInstance(getPrincipal).type(toType);
    }

    return e;
  }
}
