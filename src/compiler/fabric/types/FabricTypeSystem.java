package fabric.types;

import jif.types.Assertion;
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
  
  /** Returns the join of L1 and L2 as per the trust ordering */
  Label tjoin(Label L1, Label L2);

  /** Returns the meet of L1 and L2 as per the trust ordering */
  Label tmeet(Label L1, Label L2);
  
  fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src);
  fabric.lang.security.ConfPolicy sourceAccessPolicy(CodebaseSource src);

  /**
   * @param assertion
   * @return
   */
  boolean containsThisLabel(Assertion assertion);

}
