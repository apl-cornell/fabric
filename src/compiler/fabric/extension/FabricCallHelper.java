package fabric.extension;

import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricMethodInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricProcedureInstance;
import fabric.types.FabricTypeSystem;

import jif.extension.CallHelper;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.JifProcedureInstance;
import jif.types.JifTypeSystem;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 *
 */
public class FabricCallHelper extends CallHelper {

  /**
   * @param receiverLabel
   * @param receiver
   * @param calleeContainer
   * @param pi
   * @param actualArgs
   * @param position
   */
  public FabricCallHelper(Label receiverLabel, Receiver receiver,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position) {
    super(receiverLabel, receiver, calleeContainer, pi, actualArgs, position);
  }

  protected FabricCallHelper(Label receiverLabel, Receiver receiver,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position, boolean overrideChecker) {
    super(receiverLabel, receiver, calleeContainer, pi, actualArgs, position,
        overrideChecker);
  }

  @Override
  public Label instantiate(JifContext A, Label L) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) A.typeSystem();
    AccessPath storeap = null;
    if (receiverExpr != null) storeap = ts.storeAccessPathFor(receiverExpr, A);
    return StoreInstantiator.instantiate(
        L,
        A,
        receiverExpr,
        calleeContainer,
        receiverLabel,
        getArgLabelsFromFormalTypes(pi.formalTypes(),
            (JifTypeSystem) pi.typeSystem(), pi.position()), pi.formalTypes(),
            this.actualArgLabels, this.actualArgs, this.actualParamLabels, storeap);
  }

  @Override
  public Principal instantiate(JifContext A, Principal p)
      throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) A.typeSystem();
    AccessPath storeap = null;
    if (receiverExpr != null) storeap = ts.storeAccessPathFor(receiverExpr, A);

    return StoreInstantiator.instantiate(
        p,
        A,
        receiverExpr,
        calleeContainer,
        receiverLabel,
        getArgLabelsFromFormalTypes(this.pi.formalTypes(),
            (JifTypeSystem) this.pi.typeSystem(), this.pi.position()), pi
            .formalTypes(), this.actualArgLabels, this.actualArgs,
            this.actualParamLabels, storeap);
  }

  @Override
  public Type instantiate(JifContext A, Type t) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) A.typeSystem();
    AccessPath storeap = null;
    if (receiverExpr != null) storeap = ts.storeAccessPathFor(receiverExpr, A);

    return StoreInstantiator.instantiate(
        t,
        A,
        receiverExpr,
        calleeContainer,
        receiverLabel,
        getArgLabelsFromFormalTypes(pi.formalTypes(),
            (JifTypeSystem) pi.typeSystem(), pi.position()), pi.formalTypes(),
            this.actualArgLabels, this.actualArgs, this.actualParamLabels, storeap);
  }

  /**
   * this.pi is a Jif method instance that this.overridingMethod is attempting to
   * override. Previous type checks have made sure that things like
   * abstractness, access flags, throw sets, etc. are ok.
   * We need to check that the labels conform.
   * 
   * @throws SemanticException
   */
  @Override
  public void checkOverride(LabelChecker lc) throws SemanticException {
    super.checkOverride(lc);
    FabricContext A = (FabricContext) lc.context();
    A = (FabricContext) A.pushBlock();
    LabelChecker newlc = lc.context(A);

    // The method instances we're checking.
    // mi
    final FabricMethodInstance overriding = (FabricMethodInstance) this.overridingMethod;
    // mj
    final FabricMethodInstance overridden = (FabricMethodInstance) this.pi;

    // begin conflict bounds are covariant:
    //  the begin conflict bound on mi may be less restrictive than the begin
    //  conflict bound on mj
    Label beginConflictLi = overriding.beginConflictLabel();
    NamedLabel beginConflictNLi = new NamedLabel("sub_start_conflict_bound",
        "starting conflict bound of method " + overriding.name() + " in " + overriding.container(),
        beginConflictLi);

    Label beginConflictLj = overridden.beginConflictLabel();
    NamedLabel beginConflictNLj = new NamedLabel("sup_start_conflict_bound",
        "starting conflict bound of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, beginConflictLj));

    newlc.constrain(beginConflictNLi, LabelConstraint.LEQ, beginConflictNLj, A.labelEnv(),
        overriding.position(), new ConstraintMessage() {
          @Override
          public String msg() {
            return "Cannot override " + overridden.signature()
                 + " in " + overridden.container() + " with "
                 + overriding.signature() + " in "
                 + overriding.container()
                 + ". The beginning conflict bound of the "
                 + "overriding method "
                 + "cannot be more restrictive than in "
                 + "the overridden method.";

          }

          @Override
          public String detailMsg() {
            return msg()
                + "The beginning conflict bound of a method is an upper "
                + "bound on the conflict labels of field accesses that "
                + "the method may perform, and a lower bound of the "
                + "conflict labels of accesses prior to the call site.";

          }
        });

    // end conflict bounds are contravariant:
    //  the end conflict bound on mi may be more restrictive than the end
    //  conflict bound on mj
    Label endConflictLi = overriding.endConflictLabel();
    NamedLabel endConflictNLi = new NamedLabel("sub_end_conflict_label",
        "ending conflict label of method " + overriding.name() + " in " + overriding.container(),
        endConflictLi);

    Label endConflictLj = overridden.endConflictLabel();
    NamedLabel endConflictNLj = new NamedLabel("sup_end_conflict_label",
        "ending conflict label of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, endConflictLj));

    newlc.constrain(endConflictNLj, LabelConstraint.LEQ, endConflictNLi, A.labelEnv(),
        overriding.position(),
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Cannot override " + overridden.signature()
                 + " in " + overridden.container() + " with "
                 + overriding.signature() + " in "
                 + overriding.container()
                 + ". The ending conflict label of the "
                 + "overriding method "
                 + "cannot be less restrictive than in "
                 + "the overridden method.";

          }

          @Override
          public String detailMsg() {
            return msg()
                + "The ending conflict label of a method is a lower "
                + "bound on the conflict labels of field accesses during "
                + "the method, and an upper bound of the "
                + "conflict labels of accesses after the call site.";

          }
        });
  }

  @Override
  public void checkCall(LabelChecker lc, List<Type> throwTypes,
      boolean targetMayBeNull) throws SemanticException {
    super.checkCall(lc, throwTypes, targetMayBeNull);
    FabricTypeSystem fts = (FabricTypeSystem) lc.context().typeSystem();
    Label beginConflict = resolveBeginConflict(lc);
    Label endConflict = resolveEndConflict(lc);
    FabricContext A = (FabricContext) lc.context();

    // Check previous accesses against access label bound of method.
    NamedLabel beginConflictNL = new NamedLabel("begin conflict label of " + pi.signature(),
          "the upper bound of the conflict labels of all accesses in the method",
          beginConflict);

    // Get join of update labels of previous accesses
    NamedLabel conflictNL = new NamedLabel("prev conflict label",
        "the meet of the conflict labels of previous accesses",
        fts.meet(A.conflictLabel(),
                 fts.meet(A.beginConflictBound(),
                          ((FabricPathMap) X).CL())));

    lc.constrain(beginConflictNL, LabelConstraint.LEQ, conflictNL,
        A.labelEnv(), position, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The accesses during the call to " + FabricCallHelper.this.pi.signature()
             + " could leak information about previous accesses to the stores "
             + "accessed during the method.";
      }
    });

    // Add in the conflict labels of the call's accesses to the CL path.
    Label newCL = fts.meet(fts.meet(((FabricPathMap) X).CL(), endConflict),
        A.conflictLabel());
    X = ((FabricPathMap) X).CL(newCL);

    // TODO: Not convinced this should be done here...
    // Early check of end conflict bound to get better location reporting.
    NamedLabel newCLN = new NamedLabel("prev conflict label",
        "the meet of the conflict labels of accesses up to the end of the call",
        newCL);

    NamedLabel endConflictBoundLabel = new NamedLabel("end conflict label",
        "the lower bound on the conflict labels of accesses in the current method",
        A.endConflictBound());

    lc.constrain(endConflictBoundLabel, LabelConstraint.LEQ, newCLN,
        A.labelEnv(), position, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The current method makes less restricted accesses than the ending "
             + "conflict label allows.";
      }
    });
  }

  /**
   * Returns the instantiated begin conflict policy.
   * @throws SemanticException
   */
  protected Label resolveBeginConflict(LabelChecker lc)
      throws SemanticException {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pi;
    return instantiate(lc.context(), fpi.beginConflictLabel());
  }

  /**
   * Returns the instantiated end conflict policy.
   * @throws SemanticException
   */
  protected Label resolveEndConflict(LabelChecker lc)
      throws SemanticException {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pi;
    return instantiate(lc.context(), fpi.endConflictLabel());
  }
}
