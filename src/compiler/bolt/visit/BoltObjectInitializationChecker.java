package bolt.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bolt.ast.BoltExt;
import bolt.ast.BoltNodeFactory;
import bolt.ast.BoltPrologueExt;
import bolt.types.BoltTypeSystem;
import polyglot.ast.Block;
import polyglot.ast.ClassBody;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.FieldDecl;
import polyglot.ast.Initializer;
import polyglot.ast.Local;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.FieldInstance;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.VarInstance;
import polyglot.util.Position;
import polyglot.visit.AbstractAssignmentChecker;
import polyglot.visit.FlowGraph;

public class BoltObjectInitializationChecker extends
    AbstractAssignmentChecker<BoltObjectInitializationChecker.ClassBodyInfo> {
  public BoltObjectInitializationChecker(Job job, BoltTypeSystem ts,
      BoltNodeFactory nf) {
    super(job, ts, nf);
  }

  protected static class ClassBodyInfo
      extends AbstractAssignmentChecker.ClassBodyInfo<ClassBodyInfo> {
    /**
     * Maps ConstructorInstances to sets of FieldInstances that are assigned by
     * the constructor's prologue.
     */
    public Map<ConstructorInstance, Set<FieldInstance>> fieldsPrologueInitializes =
        new HashMap<>();

    /**
     * Maps ConstructorInstances to their constructor prologues.
     */
    public Map<ConstructorInstance, Block> constructorPrologues =
        new HashMap<>();

    public ClassBodyInfo(ClassBodyInfo outer, ClassType curClass) {
      super(outer, curClass);
    }
  }

  @Override
  protected void leaveClassBody(ClassBody cb) throws SemanticException {
    // Check that all non-static fields are definitely assigned by the end of
    // each constructor prologue.
    for (FieldInstance fi : curCBI.curClassFieldAsgtStatuses.keySet()) {
      if (fi.flags().isStatic()) continue;

      AssignmentStatus asgtStat =
          curCBI.curClassFieldAsgtStatuses.get(fi.orig());
      if (asgtStat != null && asgtStat.definitelyAssigned) {
        // Field is initialized outside a constructor.
        continue;
      }

      for (ConstructorDecl cd : curCBI.allConstructors) {
        if (curCBI.constructorsCallingThis.contains(cd)) {
          // Constructor calls this(), which will ensure that fields are
          // properly initialized.
          continue;
        }

        ConstructorInstance ci = cd.constructorInstance().orig();
        Set<FieldInstance> s = curCBI.fieldsPrologueInitializes.get(ci);
        if (s != null && s.contains(fi)) {
          // Constructor prologue initializes the field.
          continue;
        }

        // Field not properly initialized. Figure out where to point the error.
        Position blamePos;
        Block prologue = curCBI.constructorPrologues.get(ci);
        if (prologue != null) {
          // Assume the last statement of the prologue is the super() call.
          // Point the error at the very start of the call.
          List<Stmt> stmts = prologue.statements();
          Stmt stmt = stmts.get(stmts.size() - 1);
          blamePos = stmt.position().startOf();
        } else {
          // Blame the start of the constructor.
          blamePos = cd.position().endOf();
        }
        throw new SemanticException(
            "Field \"" + fi.name() + "\" might not have been initialized",
            blamePos);
      }
    }
  }

  @Override
  protected ClassBodyInfo newCBI(ClassBodyInfo prevCBI, ClassType ct) {
    return new ClassBodyInfo(prevCBI, ct);
  }

  @Override
  protected void checkField(FlowGraph<FlowItem> graph, Field f, FlowItem dfIn)
      throws SemanticException {
    FieldInstance fi = f.fieldInstance();
    // Use of blank final field only needs to be checked when the use
    // occurs inside the same class as the field's container.
    if (ts.typeEquals(curCBI.curClass, fi.container())) {
      if ((curCBI.curCodeDecl instanceof FieldDecl
          || curCBI.curCodeDecl instanceof ConstructorDecl
          || curCBI.curCodeDecl instanceof Initializer)
          && isFieldsTargetAppropriate(f)) {
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
  protected void checkLocal(FlowGraph<FlowItem> graph, Local l, FlowItem dfIn)
      throws SemanticException {
  }

  @Override
  protected void checkLocalAssign(FlowGraph<FlowItem> graph, LocalInstance li,
      Position pos, FlowItem dfIn) throws SemanticException {
  }

  @Override
  protected void checkFieldAssign(FlowGraph<FlowItem> graph, FieldAssign a,
      FlowItem dfIn) throws SemanticException {
  }

  @Override
  protected void checkLocalsUsedByInnerClass(FlowGraph<FlowItem> graph,
      ClassBody cb, Set<LocalInstance> localsUsed, FlowItem dfIn,
      FlowItem dfOut) throws SemanticException {
  }

  @Override
  protected void checkOther(FlowGraph<FlowItem> graph, Node n, FlowItem dfIn,
      FlowItem dfOut) throws SemanticException {
    if (n instanceof Block && BoltExt.ext(n) instanceof BoltPrologueExt) {
      checkPrologue(graph, (Block) n, dfIn, dfOut);
    }
  }

  protected void checkPrologue(FlowGraph<FlowItem> graph, Block prologue,
      FlowItem dfIn, FlowItem dfOut) {
    // We are leaving a constructor prologue. Save the set of fields that the
    // prologue initializes.
    Set<FieldInstance> s = new HashSet<>();
    for (Entry<VarInstance, AssignmentStatus> entry : dfOut.assignmentStatus
        .entrySet()) {
      if (!(entry.getKey() instanceof FieldInstance)) continue;

      FieldInstance fi = (FieldInstance) entry.getKey();
      if (fi.flags().isStatic()) continue;

      // The prologue initializes a field if the field's status changes to
      // being definitely assigned.
      AssignmentStatus asgtStatus = entry.getValue();
      AssignmentStatus origAsgtStatus =
          curCBI.curClassFieldAsgtStatuses.get(fi);
      if (asgtStatus.definitelyAssigned && !origAsgtStatus.definitelyAssigned) {
        // The constructor prologue initialized this field.
        s.add(fi);
      }
    }

    // Associate the set of initialized fields with the prologue's constructor
    // instance.
    ConstructorDecl cd = (ConstructorDecl) curCBI.curCodeDecl;
    ConstructorInstance ci = cd.constructorInstance().orig();

    if (!s.isEmpty()) {
      curCBI.fieldsPrologueInitializes.put(ci, s);
    }

    // Associate the constructor instance with the constructor prologue.
    curCBI.constructorPrologues.put(ci, prologue);
  }
}
