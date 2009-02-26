package fabric.ast;

import fabric.types.FabricTypeSystem;

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

public class Client_c extends Local_c implements Client {
  public Client_c(Position pos, NodeFactory nf) {
    super(pos, nf.Id(pos, "client$"));
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();

    Client c = (Client)this.type(ts.Client());
    c = (Client)c.localInstance(ts.clientLocalInstance());
    
    return c;
  }
  
  @Override
  public String toString() {
    return "client$";
  }
}
