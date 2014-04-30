package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.Local_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class Worker_c extends Local_c implements Worker {
  @Deprecated
  public Worker_c(Position pos, Id workerId) {
    this(pos, workerId, null);
  }

  public Worker_c(Position pos, Id workerId, Ext ext) {
    super(pos, workerId, ext);
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
    return name();
  }

  // @Override
  // public boolean isConstant() {
  // return true;
  // }
}
