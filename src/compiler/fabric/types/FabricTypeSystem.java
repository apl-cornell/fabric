package fabric.types;

import java.util.List;

import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseTypeSystem;

import fabric.ExtensionInfo;
import fabric.ast.RemoteWorkerGetter;
import fabric.ast.Store;

import jif.types.Assertion;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.principal.Principal;

import polyglot.ast.Expr;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.ParsedClassType;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

public interface FabricTypeSystem extends JifTypeSystem, CodebaseTypeSystem {
  ClassType FObject();

  ClassType RemoteWorker();

  ClassType Worker();

  ClassType Store();

  ClassType DelegatingPrincipal();

  /**
   * Constructs a principal for the local worker. Remote workers directly use
   * <code>DynamicPrincipal</code>.
   *
   * @param pos
   * @return
   */
  Principal workerLocalPrincipal(Position pos);

  /**
   * Constructs an AccessPath for the local worker.
   * @param position
   * @return
   */
  AccessPath workerLocalAccessPath(Position position);

  WorkerLocalInstance workerLocalInstance();

  FabricDefaultSignature fabricDefaultSignature();

  public FabricMethodInstance fabricMethodInstance(Position pos,
      ReferenceType container, Flags flags, Type returnType, String name,
      Label startLabel, boolean isDefaultStartLabel, Label beginAccessLab,
      boolean isDefaultBeginAccess, List<? extends Type> formalTypes,
      List<jif.types.label.Label> formalArgLabels, Label endLabel,
      boolean isDefaultEndLabel, Label endAccessLab, boolean isDefaultEndAccess,
      List<? extends Type> excTypes, List<Assertion> constraints);

  FabricFieldInstance fabricFieldInstance(Position pos,
      ReferenceType container, Flags flags, Type type, ConfPolicy accessLabel,
      String name);

  Type strip(Type type);

  ConfPolicy representableConfProjection(Label L);

  IntegPolicy representableIntegProjection(Label L);

  /**
   * Returns true if type extends fabric.lang.Object
   */
  boolean isPersistent(Type type);

  /**
   * Returns true if type does not extend fabric.lang.Object
   */
  public boolean isTransient(Type type);

  /**
   * Checks whether <code>type</code> is a Fabric class, that is, inherits
   * <code>fabric.lang.Object</code>.
   * Returns false if <code>type</code> is an interface.
   */
  boolean isFabricClass(Type type);

  /**
   * Checks whether <code>type</code> is a Fabric interface,
   * and inherits <code>fabric.lang.Object</code>.
   */
  boolean isFabricInterface(Type type);

  boolean isFabricArray(Type type);

  FabricArrayType toFabricArray(Type type);

  FabricArrayType fabricArrayOf(Position pos, Type t);

  FabricArrayType fabricArrayOf(Position pos, Type t, int dims);

  /** Returns the join of L1 and L2 as per the trust ordering */
  Label tjoin(Label L1, Label L2);

  /** Returns the meet of L1 and L2 as per the trust ordering */
  Label tmeet(Label L1, Label L2);

  /** Compares L1 and L2 as per the trust ordering */
  boolean tleq(LabelEnv env, Label L1, Label L2);

  fabric.lang.security.Label sourceUpdateLabel(CodebaseSource src);

  fabric.lang.security.ConfPolicy sourceAccessPolicy(CodebaseSource src);

  /**
   * Returns true if label contains a {this} label.
   */
  boolean containsThisLabel(Label label);

  /**
   * Returns true if assertion contains a {this} label.
   */
  boolean containsThisLabel(Assertion assertion);

  /**
   * Returns true if assertion contains a argument label.
   */
  boolean containsArgLabel(Assertion as);

  /**
   * Returns true if label contains a argument label.
   */
  boolean containsArgLabel(Label label);

  /**
   * Returns a label with c joined with a top integrity component.
   */
  Label toLabel(ConfPolicy c);

  /**
   * @param label
   * @return
   */
  Label replaceProjections(Label label);

  /**
   * @param expr
   * @param context
   * @param pos
   * @return
   * @throws SemanticException
   */
  Principal storePrincipal(Store store, FabricContext context, Position pos)
      throws SemanticException;

  /**
   * @param worker
   * @param context
   * @param pos
   * @return
   * @throws SemanticException
   */
  Principal remoteWorkerPrincipal(RemoteWorkerGetter worker,
      FabricContext context, Position pos) throws SemanticException;

  /**
   * @param ct
   * @param context
   * @return
   * @throws SemanticException
   */
  AccessPath currentStoreAccessPathFor(ClassType ct, JifContext context)
      throws SemanticException;

  /**
   * @param ref
   * @param context
   * @return
   * @throws SemanticException
   */
  AccessPath storeAccessPathFor(Expr ref, JifContext context)
      throws SemanticException;

  /**
   * @param pol
   * @return
   * @throws SemanticException
   */
  boolean accessPolicyValid(ConfPolicy pol) throws SemanticException;

  /**
   * @param label
   * @return The read-conflict label for the input label.
   */
  Label readConflict(Label l);

  /**
   * @param label
   * @return The write-conflict label for the input label.
   */
  Label writeConflict(Label l);

  /**
   * @param pos
   * @param ct
   * @param policy
   * @return
   */
  AccessPolicyInstance accessPolicyInstance(Position pos, ParsedClassType ct,
      ConfPolicy policy);

  /** Return the language extension this type system is for. */
  @Override
  ExtensionInfo extensionInfo();

}
