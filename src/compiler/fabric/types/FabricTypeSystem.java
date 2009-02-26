package fabric.types;

import polyglot.types.ClassType;
import polyglot.types.LocalInstance;
import polyglot.util.Position;
import jif.types.JifTypeSystem;
import jif.types.principal.*;

public interface FabricTypeSystem extends JifTypeSystem {
  ClassType FObject();
  
  ClassType RemoteClient();
  
  ClassType Client();

  /**
   * Constructs a principal for the local client. 
   * 
   * Remote clients directly use <code>DynamicPrincipal</code>.
   * 
   * @param pos
   * @return
   */
  Principal clientPrincipal(Position pos);
  
  LocalInstance clientLocalInstance();
}
