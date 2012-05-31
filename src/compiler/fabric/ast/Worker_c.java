package fabric.ast;

import polyglot.ast.Local_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

public class Worker_c extends Local_c implements Worker {
  public Worker_c(Position pos, NodeFactory nf) {
    super(pos, nf.Id(pos, "worker$"));
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();

    Worker c = (Worker) this.type(ts.Worker());
    c = (Worker) c.localInstance(ts.workerLocalInstance());

    return c;
  }

  @Override
  public String toString() {
    return "worker$";
  }

  // @Override
  // public boolean isConstant() {
  // return true;
  // }
}
