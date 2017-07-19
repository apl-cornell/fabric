package bolt.visit;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bolt.ast.BoltNodeFactory;
import bolt.types.BoltTypeSystem;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Call;
import polyglot.ast.Cast;
import polyglot.ast.ClassBody;
import polyglot.ast.Conditional;
import polyglot.ast.ConstructorCall;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.LocalAssign;
import polyglot.ast.LocalDecl;
import polyglot.ast.NamedVariable;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.FieldInstance;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.VarInstance;
import polyglot.util.InternalCompilerError;
import polyglot.visit.AbstractAssignmentChecker;
import polyglot.visit.AbstractDefiniteAssignmentChecker;
import polyglot.visit.DataFlow;
import polyglot.visit.FlowGraph;
import polyglot.visit.FlowGraph.EdgeKey;
import polyglot.visit.FlowGraph.Peer;

/**
 * This {@link DataFlow} visitor makes the following checks, in addition to
 * those made in {@link AbstractDefiniteAssignmentChecker}:
 * <ul>
 * <li>A points-to analysis is done to determine which variables may point to
 * {@code this} during object initialization. This is used by an escape analysis
 * to ensure that {@code this} does not escape an object until {@code this()} or
 * {@code super()} is called. The escape analysis ensures that {@code this} is
 * not assigned into fields of other objects, is not passed as a call argument,
 * and is not the target of a method call.
 * <li>All fields (including non-final fields) must be definitely assigned
 * before they are used, and before {@code super()} is called.
 * <li>During object initialization, {@code this()} and {@code super()} calls
 * may occur on alternate code paths, but for all code paths, exactly one {@code
 * this()} or {@code super()} call must be made before the end of each
 * constructor.
 * </ul>
 */
public class BoltObjectInitializationChecker extends
    AbstractDefiniteAssignmentChecker<BoltObjectInitializationChecker.ClassBodyInfo, BoltObjectInitializationChecker.FlowItem> {
  public BoltObjectInitializationChecker(Job job, BoltTypeSystem ts,
      BoltNodeFactory nf) {
    super(job, ts, nf);
  }

  protected static class ClassBodyInfo
      extends AbstractAssignmentChecker.ClassBodyInfo<ClassBodyInfo> {
    /**
     * Maps ConstructorCalls to sets of FieldInstances that are assigned by
     * the constructor's code path up until the point of the call.
     */
    public Map<ConstructorCall, Set<FieldInstance>> fieldsInitializedBeforeCall =
        new HashMap<>();

    public ClassBodyInfo(ClassBodyInfo outer, ClassType curClass) {
      super(outer, curClass);
    }
  }

  /**
   * Tracks whether {@code super()} or {@code this()} has been called.
   */
  protected static enum ConstructorCallStatus {
    BOTH(true, true), CALLED(true, false), NOT_CALLED(false,
        true), NEITHER(false, false);

    public final boolean definitelyCalled;
    public final boolean definitelyNotCalled;

    ConstructorCallStatus(boolean definitelyCalled,
        boolean definitelyNotCalled) {
      this.definitelyCalled = definitelyCalled;
      this.definitelyNotCalled = definitelyNotCalled;
    }

    @Override
    public String toString() {
      return "[" + (definitelyCalled ? "definitely called " : "")
          + (definitelyNotCalled ? "definitely not called " : "") + "]";
    }

    public static ConstructorCallStatus join(ConstructorCallStatus ccs1,
        ConstructorCallStatus ccs2) {
      if (ccs1 == null) return ccs2;
      if (ccs2 == null) return ccs1;
      return construct(ccs1.definitelyCalled && ccs2.definitelyCalled,
          ccs1.definitelyNotCalled && ccs2.definitelyNotCalled);
    }

    private static ConstructorCallStatus construct(boolean definitelyCalled,
        boolean definitelyNotCalled) {
      for (ConstructorCallStatus ccs : ConstructorCallStatus.values()) {
        if (ccs.definitelyCalled == definitelyCalled
            && ccs.definitelyNotCalled == definitelyNotCalled)
          return ccs;
      }

      throw new InternalCompilerError(
          "Unable to construct constructor call status from (defCalled="
              + definitelyCalled + ", defNotCalled=" + definitelyNotCalled
              + ")");
    }
  }

  /**
   * Tracks whether a term can be a "this" reference.
   */
  protected static enum ThisStatus {
    BOTH(true, true), THIS(true, false), NOT_THIS(false, true), NEITHER(false,
        false);

    public final boolean definitelyThis;
    public final boolean definitelyNotThis;

    ThisStatus(boolean definitelyThis, boolean definitelyNotThis) {
      this.definitelyThis = definitelyThis;
      this.definitelyNotThis = definitelyNotThis;
    }

    @Override
    public String toString() {
      return "[" + (definitelyThis ? "definitely this " : "")
          + (definitelyNotThis ? "definitely not this " : "") + "]";
    }

    public static ThisStatus join(ThisStatus ts1, ThisStatus ts2) {
      if (ts1 == null) return ts2;
      if (ts2 == null) return ts1;
      return construct(ts1.definitelyThis && ts2.definitelyThis,
          ts1.definitelyNotThis && ts2.definitelyNotThis);
    }

    private static ThisStatus construct(boolean definitelyThis,
        boolean definitelyNotThis) {
      for (ThisStatus ts : ThisStatus.values()) {
        if (ts.definitelyThis == definitelyThis
            && ts.definitelyNotThis == definitelyNotThis)
          return ts;
      }

      throw new InternalCompilerError(
          "Unable to construct ThisStatus from (defThis=" + definitelyThis
              + ", defNotThis=" + definitelyNotThis + ")");
    }
  }

  /**
   * Adds information about whether {@code super()} or {@code this()} has been
   * called.
   *
   * @see polyglot.visit.AbstractAssignmentChecker.FlowItem
   */
  protected static class FlowItem extends AbstractAssignmentChecker.FlowItem {
    /**
     * Indicates whether a given variable can be a "this" reference.
     */
    public final Map<VarInstance, ThisStatus> varsThisStatus;
    public final ConstructorCallStatus callStatus;

    /**
     * Indicates whether the current term might be a {@code this} reference.
     */
    public final ThisStatus termThisStatus;

    protected FlowItem(Map<VarInstance, AssignmentStatus> asgtStatusMap,
        boolean canTerminateNormally, ConstructorCallStatus callStatus,
        Map<VarInstance, ThisStatus> thisStatusMap, ThisStatus thisStatus) {
      super(asgtStatusMap, canTerminateNormally);
      this.callStatus = callStatus;
      this.varsThisStatus =
          Collections.unmodifiableMap(new HashMap<>(thisStatusMap));
      this.termThisStatus = thisStatus;
    }
  }

  @Override
  protected FlowItem newFlowItem(
      Map<VarInstance, AssignmentStatus> asgtStatusMap,
      boolean canTerminateNormally) {
    Map<VarInstance, ThisStatus> thisStatusMap =
        new HashMap<>(asgtStatusMap.size());
    for (VarInstance vi : asgtStatusMap.keySet()) {
      thisStatusMap.put(vi, ThisStatus.BOTH);
    }

    return newFlowItem(asgtStatusMap, canTerminateNormally,
        ConstructorCallStatus.BOTH, thisStatusMap, ThisStatus.BOTH);
  }

  /**
   * Factory method for creating a new flow item.
   */
  protected FlowItem newFlowItem(
      Map<VarInstance, AssignmentStatus> asgtStatusMap,
      boolean canTerminateNormally, ConstructorCallStatus callStatus,
      Map<VarInstance, ThisStatus> thisStatusMap, ThisStatus thisStatus) {
    return new FlowItem(asgtStatusMap, canTerminateNormally, callStatus,
        thisStatusMap, thisStatus);
  }

  @Override
  protected FlowItem reconstructFlowItem(FlowItem fi,
      Map<VarInstance, AssignmentStatus> asgtStatusMap) {
    return reconstructFlowItem(fi, asgtStatusMap, fi.normalTermination,
        fi.callStatus, fi.varsThisStatus, fi.termThisStatus);
  }

  /**
   * Reconstructs a flow item by replacing its components as needed.
   *
   * @param fi the flow item to reconstruct.
   */
  protected FlowItem reconstructFlowItem(FlowItem fi,
      Map<VarInstance, AssignmentStatus> map, boolean canTerminateNormally,
      ConstructorCallStatus callStatus,
      Map<VarInstance, ThisStatus> varsThisStatus, ThisStatus thisStatus) {
    if (fi.assignmentStatus == map
        && fi.normalTermination == canTerminateNormally
        && fi.callStatus == callStatus && fi.varsThisStatus == varsThisStatus
        && fi.termThisStatus == thisStatus) {
      return fi;
    }

    return newFlowItem(map, canTerminateNormally, callStatus, varsThisStatus,
        thisStatus);
  }

  /**
   * Reconstructs a flow item by replacing its assignment-status map and its
   * maybe-this status.
   *
   * @param fi the flow item to reconstruct.
   */
  protected FlowItem reconstructFlowItem(FlowItem fi,
      Map<VarInstance, AssignmentStatus> asgtStatusMap, ThisStatus thisStatus) {
    return reconstructFlowItem(fi, asgtStatusMap, fi.normalTermination,
        fi.callStatus, fi.varsThisStatus, thisStatus);
  }

  /**
   * Reconstructs a flow item by replacing the constructor-call status and the
   * maybe-this status.
   *
   * @param fi the flow item to reconstruct.
   */
  protected FlowItem reconstructFlowItem(FlowItem fi,
      ConstructorCallStatus callStatus, ThisStatus thisStatus) {
    return reconstructFlowItem(fi, fi.assignmentStatus, fi.normalTermination,
        callStatus, fi.varsThisStatus, thisStatus);
  }

  /**
   * Reconstructs a flow item by replacing the constructor-call status and the
   * maybe-this status.
   *
   * @param fi the flow item to reconstruct.
   */
  protected FlowItem reconstructFlowItem(FlowItem fi,
      ConstructorCallStatus callStatus,
      Map<VarInstance, ThisStatus> varsThisStatus, ThisStatus thisStatus) {
    return reconstructFlowItem(fi, fi.assignmentStatus, fi.normalTermination,
        callStatus, varsThisStatus, thisStatus);
  }

  /**
   * Reconstructs a flow item by replacing the this-status.
   *
   * @param fi the flow item to reconstruct.
   * @param thisStatus whether the current term might be a "this" reference.
   */
  protected FlowItem reconstructFlowItem(FlowItem fi, ThisStatus thisStatus) {
    return reconstructFlowItem(fi, fi.assignmentStatus, fi.normalTermination,
        fi.callStatus, fi.varsThisStatus, thisStatus);
  }

  /**
   * Reconstructs a flow item by setting a {@link VarInstance} mapping in the
   * {@link FlowItem#varsThisStatus} map. Also sets {@link FlowItem#termThisStatus}
   * to the given value.
   */
  protected FlowItem setVarThisStatus(FlowItem fi, VarInstance vi,
      ThisStatus viThisStatus, ThisStatus termThisStatus) {
    Map<VarInstance, ThisStatus> varsThisStatus = fi.varsThisStatus;
    if (!viThisStatus.equals(varsThisStatus.get(vi))) {
      varsThisStatus = new HashMap<>(varsThisStatus);
      varsThisStatus.put(vi, viThisStatus);
    }

    return reconstructFlowItem(fi, fi.assignmentStatus, fi.normalTermination,
        fi.callStatus, varsThisStatus, termThisStatus);
  }

  @Override
  protected void leaveClassBody(ClassBody cb) throws SemanticException {
    super.leaveClassBody(cb);

    // Check that all non-static fields are definitely assigned by the time each
    // super() call happens.

    // First, gather up the FieldInstances for those fields we care about.
    Set<FieldInstance> fieldInstances = new HashSet<>();
    for (FieldInstance fi : curCBI.curClassFieldAsgtStatuses.keySet()) {
      if (fi.flags().isStatic()) continue;

      AssignmentStatus asgtStat =
          curCBI.curClassFieldAsgtStatuses.get(fi.orig());
      if (asgtStat != null && asgtStat.definitelyAssigned) {
        // Field is initialized outside a constructor.
        continue;
      }

      fieldInstances.add(fi);
    }

    // Go through and check each constructor call.
    for (Map.Entry<ConstructorCall, Set<FieldInstance>> entry : curCBI.fieldsInitializedBeforeCall
        .entrySet()) {
      ConstructorCall call = entry.getKey();
      Set<FieldInstance> s = entry.getValue();

      for (FieldInstance fi : fieldInstances) {
        if (call.kind() == ConstructorCall.SUPER) {
          if (s == null || !s.contains(fi)) {
            // Field not initialized.
            throw new SemanticException(
                "Field \"" + fi.name() + "\" might not have been initialized",
                call.position().startOf());
          }
        } else if (call.kind() == ConstructorCall.THIS) {
        } else {
          throw new InternalCompilerError(
              "Unexpected constructor call kind: " + call.kind());
        }
      }
    }
  }

  @Override
  protected ClassBodyInfo newCBI(ClassBodyInfo prevCBI, ClassType ct) {
    return new ClassBodyInfo(prevCBI, ct);
  }

  @Override
  protected FlowItem createInitDFI() {
    FlowItem fi = super.createInitDFI();
    Map<VarInstance, ThisStatus> varsThisStatus = new HashMap<>();
    for (VarInstance vi : fi.assignmentStatus.keySet()) {
      varsThisStatus.put(vi, ThisStatus.NOT_THIS);
    }

    return reconstructFlowItem(fi, ConstructorCallStatus.NOT_CALLED,
        varsThisStatus, ThisStatus.NOT_THIS);
  }

  @Override
  protected FlowItem confluence(List<FlowItem> inItems, Peer<FlowItem> peer,
      FlowGraph<FlowItem> graph) {
    FlowItem fi = super.confluence(inItems, peer, graph);
    ConstructorCallStatus callStatus = ConstructorCallStatus.BOTH;
    Map<VarInstance, ThisStatus> varsThisStatus = null;
    ThisStatus thisStatus = ThisStatus.BOTH;
    boolean rebuildVarsThisStatus = true;

    for (FlowItem itm : inItems) {
      if (itm == BOTTOM) continue;
      callStatus = ConstructorCallStatus.join(callStatus, itm.callStatus);

      if (varsThisStatus == null)
        varsThisStatus = itm.varsThisStatus;
      else {
        if (rebuildVarsThisStatus) {
          varsThisStatus = new HashMap<>(varsThisStatus);
          rebuildVarsThisStatus = false;
        }

        varsThisStatus.putAll(itm.varsThisStatus);
      }

      thisStatus = ThisStatus.join(thisStatus, itm.termThisStatus);
    }

    if (varsThisStatus == null) varsThisStatus = Collections.emptyMap();

    return reconstructFlowItem(fi, fi.assignmentStatus, fi.normalTermination,
        callStatus, varsThisStatus, thisStatus);
  }

  /**
   * Determines the {@link ThisStatus} for given term in the given flow graph.
   */
  protected ThisStatus thisStatus(FlowGraph<FlowItem> graph, Term term) {
    Peer<FlowItem> initPeer = graph.peer(term, Term.EXIT);

    // Due to the flow equations, all DataFlowItems in the outItems map are the
    // same, so just take the first one.
    FlowItem dfOut =
        initPeer.outItem(initPeer.succEdgeKeys().iterator().next());
    return dfOut.termThisStatus;
  }

  @Override
  protected Map<EdgeKey, FlowItem> flowLocalDecl(FlowItem inItem,
      FlowGraph<FlowItem> graph, LocalDecl ld, Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> map =
        super.flowLocalDecl(inItem, graph, ld, succEdgeKeys);

    // Figure out the ThisStatus of the initialization expression.
    Expr init = ld.init();
    ThisStatus initThisStatus = ThisStatus.NOT_THIS;
    if (init != null) initThisStatus = thisStatus(graph, init);

    // Set the local's ThisStatus to match.
    LocalInstance li = ld.localInstance().orig();
    Map<EdgeKey, FlowItem> result = new HashMap<>(map.size());
    for (Map.Entry<EdgeKey, FlowItem> entry : map.entrySet()) {
      EdgeKey key = entry.getKey();
      FlowItem item = entry.getValue();
      result.put(key,
          setVarThisStatus(item, li, initThisStatus, ThisStatus.NOT_THIS));
    }

    return result;
  }

  @Override
  protected Map<EdgeKey, FlowItem> flowLocalAssign(FlowItem inItem,
      FlowGraph<FlowItem> graph, LocalAssign a, Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> map =
        super.flowLocalAssign(inItem, graph, a, succEdgeKeys);

    // Figure out the ThisStatus of the assignment expression.
    Expr right = a.right();
    ThisStatus thisStatus = thisStatus(graph, right);

    // Set the local's and the LocalAssign's ThisStatuses to match.
    LocalInstance li = a.left().localInstance().orig();
    Map<EdgeKey, FlowItem> result = new HashMap<>(map.size());
    for (Map.Entry<EdgeKey, FlowItem> entry : map.entrySet()) {
      EdgeKey key = entry.getKey();
      FlowItem item = entry.getValue();
      result.put(key, setVarThisStatus(item, li, thisStatus, thisStatus));
    }

    return map;
  }

  @Override
  protected Map<EdgeKey, FlowItem> flowFieldAssign(FlowItem inItem,
      FlowGraph<FlowItem> graph, FieldAssign a, Set<EdgeKey> succEdgeKeys) {
    Field f = a.left();
    FieldInstance fiOrig = f.fieldInstance().orig();

    // Ignore this assignment if the field's target is not appropriate for what
    // we are interested in.
    if (!isFieldsTargetAppropriate(graph, f)) return null;

    Map<VarInstance, AssignmentStatus> m =
        new HashMap<>(inItem.assignmentStatus);

    // m.get(fi) may be null if the field is defined in an outer class. If so,
    // ignore this assignment.
    if (m.get(fiOrig) == null) return null;

    // How we update m depends on the ThisStatus of the field's target. Because
    // isFieldsTargetAppropriate returned true, we know the target might be
    // "this".
    ThisStatus targetsThisStatus = thisStatus(graph, (Term) f.target());
    if (targetsThisStatus.definitelyThis) {
      // The field's target is definitely this, so the field is definitely
      // assigned.
      m.put(fiOrig, AssignmentStatus.ASSIGNED);
    } else {
      // We don't know that the target is "this" for sure, so the field only
      // might be assigned (i.e., it is not definitely unassigned).
      AssignmentStatus asgtStatus = m.get(fiOrig);
      if (asgtStatus == AssignmentStatus.UNASSIGNED) {
        m.put(fiOrig, AssignmentStatus.NEITHER);
      }
    }

    Map<EdgeKey, FlowItem> result = DataFlow
        .<FlowItem> itemToMap(reconstructFlowItem(inItem, m), succEdgeKeys);

    // Figure out the ThisStatus of the assignment expression.
    Expr right = a.right();
    ThisStatus thisStatus = thisStatus(graph, right);

    // Set the field's and the FieldAssign's ThisStatuses to match.
    for (Map.Entry<EdgeKey, FlowItem> entry : result.entrySet()) {
      FlowItem item = entry.getValue();
      entry.setValue(setVarThisStatus(item, fiOrig, thisStatus, thisStatus));
    }

    return result;
  }

  @Override
  protected Map<EdgeKey, FlowItem> flowConstructorCall(FlowItem inItem,
      FlowGraph<FlowItem> graph, ConstructorCall cc,
      Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> result =
        super.flowConstructorCall(inItem, graph, cc, succEdgeKeys);

    FlowItem defaultFI = reconstructFlowItem(inItem,
        ConstructorCallStatus.CALLED, ThisStatus.NOT_THIS);

    if (result == null) {
      // Map all succEdgeKeys to the default flow item.
      return DataFlow.<FlowItem> itemToMap(defaultFI, succEdgeKeys);
    }

    // Currently, result is always null, so we shouldn't reach here, but just in
    // case...

    // Loop through all succEdgeKeys and make sure the result shows that the
    // super/this constructor was definitely called.
    for (EdgeKey succEdgeKey : succEdgeKeys) {
      FlowItem item = result.get(succEdgeKey);
      if (item == null) {
        item = defaultFI;
      } else {
        item = reconstructFlowItem(item, ConstructorCallStatus.CALLED,
            ThisStatus.NOT_THIS);
      }

      result.put(succEdgeKey, item);
    }

    return result;
  }

  /**
   * Destructively remaps each given edge key to a flow item that is
   * reconstructed with the given ThisStatus. If an edge key doesn't have a
   * mapping, it is mapped to the given default flow item. If the given map is
   * null, a map is constructed that maps all given edge keys to the given
   * default flow item.
   */
  protected Map<EdgeKey, FlowItem> remapThisStatus(Map<EdgeKey, FlowItem> map,
      Set<EdgeKey> edgeKeys, FlowItem defaultFI, ThisStatus termThisStatus) {
    if (map == null) {
      // Map all succEdgeKeys to the default flow item.
      return DataFlow.<FlowItem> itemToMap(defaultFI, edgeKeys);
    }

    // Loop through all succEdgeKeys and update all termThisStatuses.
    for (EdgeKey edgeKey : edgeKeys) {
      FlowItem item = map.get(edgeKey);
      if (item == null) {
        item = defaultFI;
      } else {
        item = reconstructFlowItem(item, termThisStatus);
      }

      map.put(edgeKey, item);
    }

    return map;
  }

  @Override
  protected Map<EdgeKey, FlowItem> flowOther(FlowItem inItem,
      FlowGraph<FlowItem> graph, Node n, Set<EdgeKey> succEdgeKeys) {
    if (n instanceof Special) {
      return flowSpecial(inItem, graph, (Special) n, succEdgeKeys);
    }

    if (n instanceof NamedVariable) {
      return flowNamedVariable(inItem, graph, (NamedVariable) n, succEdgeKeys);
    }

    if (n instanceof Conditional) {
      return flowConditional(inItem, graph, (Conditional) n, succEdgeKeys);
    }

    if (n instanceof Cast) {
      return flowCast(inItem, graph, (Cast) n, succEdgeKeys);
    }

    Map<EdgeKey, FlowItem> result =
        super.flowOther(inItem, graph, n, succEdgeKeys);

    FlowItem defaultFI = reconstructFlowItem(inItem, ThisStatus.NOT_THIS);
    return remapThisStatus(result, succEdgeKeys, defaultFI,
        ThisStatus.NOT_THIS);
  }

  /**
   * Performs the appropriate flow operations for a {@link Special}.
   */
  protected Map<EdgeKey, FlowItem> flowSpecial(FlowItem inItem,
      FlowGraph<FlowItem> graph, Special special, Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> result =
        super.flowOther(inItem, graph, special, succEdgeKeys);

    // Determine whether "this" actually refers to the current object, or if it
    // refers to an outer object.
    ThisStatus termThisStatus = ThisStatus.NOT_THIS;
    if (special.kind() == Special.THIS) {
      TypeNode qualifier = special.qualifier();
      if (qualifier == null) {
        // Unqualified "this" refers to the current object.
        termThisStatus = ThisStatus.THIS;
      } else if (qualifier.type().isClass()) {
        ClassType t = qualifier.type().toClass();
        if (ts.equals(t, curCBI.curClass)) termThisStatus = ThisStatus.THIS;
      }
    }

    FlowItem defaultFI = reconstructFlowItem(inItem, termThisStatus);
    return remapThisStatus(result, succEdgeKeys, defaultFI, termThisStatus);
  }

  /**
   * Performs the appropriate flow operations for a {@link NamedVariable}.
   */
  protected Map<EdgeKey, FlowItem> flowNamedVariable(FlowItem inItem,
      FlowGraph<FlowItem> graph, NamedVariable var, Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> result =
        super.flowOther(inItem, graph, var, succEdgeKeys);

    VarInstance vi = var.varInstance();
    if (vi instanceof LocalInstance) vi = ((LocalInstance) vi).orig();
    if (vi instanceof FieldInstance) vi = ((FieldInstance) vi).orig();

    ThisStatus thisStatus = inItem.varsThisStatus.get(vi);
    if (thisStatus == null) thisStatus = ThisStatus.NOT_THIS;

    FlowItem defaultFI = reconstructFlowItem(inItem, thisStatus);
    return remapThisStatus(result, succEdgeKeys, defaultFI, thisStatus);
  }

  /**
   * Performs the appropriate flow operations for a {@link Conditional}.
   */
  protected Map<EdgeKey, FlowItem> flowConditional(FlowItem inItem,
      FlowGraph<FlowItem> graph, Conditional cond, Set<EdgeKey> succEdgeKeys) {
    return super.flowOther(inItem, graph, cond, succEdgeKeys);
  }

  /**
   * Performs the appropriate flow operations for a {@link Cast}.
   */
  protected Map<EdgeKey, FlowItem> flowCast(FlowItem inItem,
      FlowGraph<FlowItem> graph, Cast cast, Set<EdgeKey> succEdgeKeys) {
    Map<EdgeKey, FlowItem> result =
        super.flowOther(inItem, graph, cast, succEdgeKeys);

    Expr expr = cast.expr();
    ThisStatus thisStatus = thisStatus(graph, expr);

    FlowItem defaultFI = reconstructFlowItem(inItem, thisStatus);
    return remapThisStatus(result, succEdgeKeys, defaultFI, thisStatus);
  }

  @Override
  protected boolean isFieldsTargetAppropriate(FlowGraph<FlowItem> graph,
      Field f) {
    if (super.isFieldsTargetAppropriate(graph, f)) return true;

    // The points-to analysis lets us be a bit more precise here.
    // The field's target is appropriate if the field is not static and the
    // target might be "this".
    if (f.fieldInstance().flags().isStatic()) return false;

    Receiver target = f.target();
    if (target instanceof TypeNode) return false;

    return !thisStatus(graph, (Term) target).definitelyNotThis;
  }

  /**
   * {@inheritDoc}
   * For Bolt, this must hold for every non-final field too.
   */
  @Override
  protected void checkField(FlowGraph<FlowItem> graph, Field f, FlowItem dfIn)
      throws SemanticException {
    FieldInstance fi = f.fieldInstance();

    // Use of blank field only needs to be checked when the use occurs inside
    // the same class as, or a subclass of, the field's container.
    if (ts.isSubtype(curCBI.curClass, fi.container())) {
      if (duringObjectInit() && isFieldsTargetAppropriate(graph, f)) {
        AssignmentStatus initCount = dfIn.assignmentStatus.get(fi.orig());
        if (initCount == null || !initCount.definitelyAssigned) {
          throw new SemanticException(
              "Field \"" + f.name() + "\" might not have been initialized",
              f.position());
        }
      }
    }
  }

  @Override
  protected void checkFieldAssign(FlowGraph<FlowItem> graph, FieldAssign a,
      FlowItem dfIn) throws SemanticException {
    // Do JL7 checks.
    super.checkFieldAssign(graph, a, dfIn);

    // Only interested in checking code that runs during object initialization.
    if (duringObjectInit()) {
      Field field = a.left();
      FieldInstance fi = field.fieldInstance();

      // Determine the ThisStatus of the field's target.
      Receiver target = field.target();
      ThisStatus targetThisStatus;
      if (target == null)
        targetThisStatus = ThisStatus.THIS;
      else if (target instanceof TypeNode)
        targetThisStatus = ThisStatus.NOT_THIS;
      else targetThisStatus = thisStatus(graph, (Term) target);

      // Extra case not caught by superclass's implementation.
      // Couldn't find a sensible way to refactor to avoid this wart. -MJL
      if (fi.flags().isFinal() && isFieldsTargetAppropriate(graph, field)
          && !targetThisStatus.definitelyThis) {
        throw new SemanticException("Cannot assign a value to final field \""
            + fi.name() + "\" of \"" + fi.orig().container() + "\".",
            a.position());
      }

      if (dfIn.callStatus != ConstructorCallStatus.CALLED) {
        // Neither this() nor super() have been called.

        if (!targetThisStatus.definitelyThis) {
          // We might be assigning a field outside the current object. Make sure
          // the value being assigned is definitely not "this".
          if (!thisStatus(graph, a.right()).definitelyNotThis) {
            throw new SemanticException(
                "Cannot assign \"this\" to fields of other objects until "
                    + "super() or this() is called.",
                a.position());
          }
        }

        if (!targetThisStatus.definitelyNotThis) {
          // We might be assigning a field of the current object. Make sure the
          // field is not defined in a super class.
          if (ts.descendsFrom(curCBI.curClass, fi.container())) {
            throw new SemanticException("Cannot assign to a non-static field "
                + "of this object, if the field is declared in a superclass, "
                + "until super() or this() is called.", a.position());
          }
        }
      }
    }
  }

  @Override
  protected void checkOther(FlowGraph<FlowItem> graph, Node n, FlowItem dfIn,
      FlowItem dfOut) throws SemanticException {
    if (n instanceof ConstructorCall) {
      checkConstructorCall(graph, (ConstructorCall) n, dfIn, dfOut);
    } else if (n instanceof Call) {
      checkCall(graph, (Call) n, dfIn, dfOut);
    } else if (n instanceof New) {
      checkNew(graph, (New) n, dfIn, dfOut);
    } else if (n instanceof ArrayAccessAssign) {
      checkArrayAccessAssign(graph, (ArrayAccessAssign) n, dfIn, dfOut);
    } else if (n instanceof ConstructorDecl) {
      checkConstructorDecl(graph, (ConstructorDecl) n, dfIn, dfOut);
    }
  }

  protected void checkConstructorCall(FlowGraph<FlowItem> graph,
      ConstructorCall call, FlowItem dfIn, FlowItem dfOut)
      throws SemanticException {
    // We are leaving a constructor call.

    // Make sure a constructor call definitely hasn't happened yet.
    if (!(dfIn.callStatus.definitelyNotCalled)) {
      throw new SemanticException(
          "A call to this() or super() might have already been made.",
          call.position());
    }

    // Make sure "this" isn't being smuggled out via call arguments.
    for (Expr arg : call.arguments()) {
      if (!thisStatus(graph, arg).definitelyNotThis) {
        throw new SemanticException("Cannot use \"this\" as a call argument "
            + "until super() or this() is called.", arg.position());
      }
    }

    // Save the set of fields that the constructor has initialized up until this
    // point in its code path.
    Set<FieldInstance> s = new HashSet<>();
    for (Entry<VarInstance, AssignmentStatus> entry : dfOut.assignmentStatus
        .entrySet()) {
      if (!(entry.getKey() instanceof FieldInstance)) continue;

      FieldInstance fi = (FieldInstance) entry.getKey();
      if (fi.flags().isStatic()) continue;

      // The constructor has initialized a field if the field's status has
      // changed to being definitely assigned.
      AssignmentStatus asgtStatus = entry.getValue();
      AssignmentStatus origAsgtStatus =
          curCBI.curClassFieldAsgtStatuses.get(fi);
      if (asgtStatus.definitelyAssigned && !origAsgtStatus.definitelyAssigned) {
        // The constructor initialized this field.
        s.add(fi);
      }
    }

    // Associate the set of initialized fields with the constructor call.
    if (s.isEmpty()) s = Collections.emptySet();
    curCBI.fieldsInitializedBeforeCall.put(call, s);
  }

  protected void checkCall(FlowGraph<FlowItem> graph, Call call, FlowItem dfIn,
      FlowItem dfOut) throws SemanticException {
    if (duringObjectInit() && dfIn.callStatus != ConstructorCallStatus.CALLED) {
      // Make sure an instance method on the current object is not being called.
      Receiver target = call.target();
      if (!(target instanceof TypeNode)
          && !thisStatus(graph, (Term) target).definitelyNotThis) {
        throw new SemanticException(
            "Cannot access a non-static method before super() or this() is "
                + "called.",
            call.position());
      }

      // Make sure "this" isn't being smuggled out via call arguments.
      for (Expr arg : call.arguments()) {
        if (!thisStatus(graph, arg).definitelyNotThis) {
          throw new SemanticException("Cannot use \"this\" as a call argument "
              + "until super() or this() is called.", arg.position());
        }
      }
    }
  }

  protected void checkNew(FlowGraph<FlowItem> graph, New n, FlowItem dfIn,
      FlowItem dfOut) throws SemanticException {
    if (duringObjectInit() && dfIn.callStatus != ConstructorCallStatus.CALLED) {
      // Make sure an inner class of the current object is not being
      // instantiated.

      if (n.body() != null) {
        throw new SemanticException(
            "Cannot instantiate anonymous classes until super() or this() is "
                + "called.",
            n.position());
      }

      ClassType ct = n.objectType().type().toClass();
      if (ct.isInnerClass()) {
        // We have an inner class. Make sure the outer object isn't "this".
        Expr qualifier = n.qualifier();
        if (qualifier == null
            || !thisStatus(graph, qualifier).definitelyNotThis) {
          throw new SemanticException(
              "Cannot instantiate inner classes of this object until super() "
                  + "or this() is called.",
              n.position());
        }
      }
    }
  }

  protected void checkArrayAccessAssign(FlowGraph<FlowItem> graph,
      ArrayAccessAssign a, FlowItem dfIn, FlowItem dfOut)
      throws SemanticException {
    if (duringObjectInit() && dfIn.callStatus != ConstructorCallStatus.CALLED) {
      // Neither this() nor super() have been called. Make sure the RHS of the
      // assignment is definitely not "this".
      if (!thisStatus(graph, a.right()).definitelyNotThis) {
        throw new SemanticException(
            "Cannot assign \"this\" to arrays until super() or this() is "
                + "called.",
            a.position());
      }
    }
  }

  protected void checkConstructorDecl(FlowGraph<FlowItem> graph,
      ConstructorDecl cd, FlowItem dfIn, FlowItem dfOut)
      throws SemanticException {
    if (!dfIn.callStatus.definitelyCalled) {
      throw new SemanticException(
          "Constructor might not call super() or this().", cd.position());
    }
  }
}
