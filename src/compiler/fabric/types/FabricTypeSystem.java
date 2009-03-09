package fabric.types;

import polyglot.types.ClassType;
import polyglot.types.LocalInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.types.JifTypeSystem;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathField;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.principal.*;

public interface FabricTypeSystem extends JifTypeSystem {
  ClassType FObject();
  
  ClassType RemoteClient();
  
  ClassType Client();
  
  ClassType Core();

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
  
  Type strip(Type type);
  
  boolean isLocalClientAccessPath(AccessPath ap);
  
  ConfPolicy representableConfProjection(Label L);
  IntegPolicy representableIntegProjection(Label L);
}
