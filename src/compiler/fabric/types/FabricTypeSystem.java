package fabric.types;

import jif.types.JifTypeSystem;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.util.Position;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseTypeSystem;

public interface FabricTypeSystem extends JifTypeSystem, CodebaseTypeSystem {
  ClassType FObject();
  
  ClassType RemoteWorker();
  
  ClassType Worker();
  
  ClassType Store();

  ClassType DelegatingPrincipal();
  /**
   * Constructs a principal for the local worker. 
   * 
   * Remote workers directly use <code>DynamicPrincipal</code>.
   * 
   * @param pos
   * @return
   */
  Principal workerPrincipal(Position pos);
  
  LocalInstance workerLocalInstance();
  
  FabricDefaultSignature fabricDefaultSignature();
  
  FabricFieldInstance fabricFieldInstance(Position pos,
      ReferenceType container, Flags flags, Type type,
      Label accessLabel, String name);
  
  Type strip(Type type);
  
  boolean isLocalWorkerAccessPath(AccessPath ap);
  
  ConfPolicy representableConfProjection(Label L);
  IntegPolicy representableIntegProjection(Label L);
  
  /**
   * Checks whether <code>type</code> is a Fabric class, 
   * that is, inherits <code>fabric.lang.Object</code>.
   */
  boolean isFabricClass(Type type);
  
  boolean isFabricArray(Type type);
  FabricArrayType toFabricArray(Type type);
  
  boolean containsThisLabel(Label label);

  FabricArrayType fabricArrayOf(Position pos, Type t);
  FabricArrayType fabricArrayOf(Position pos, Type t, int dims);

  fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src);
  fabric.lang.security.Label sourceAccessLabel(CodebaseSource src);
}
