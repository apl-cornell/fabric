package fabric.extension;

import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricMethodInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricProcedureInstance;
import fabric.types.FabricTypeSystem;

import jif.ast.JifInstantiator;
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
    // construct a JifContext here, that equates the arg labels of mi and mj.
    FabricContext A = (FabricContext) lc.context();
    A = (FabricContext) A.pushBlock();
    LabelChecker newlc = lc.context(A);

    // The method instances we're checking.
    final FabricMethodInstance overridden = (FabricMethodInstance) this.pi;
    final FabricMethodInstance overriding = (FabricMethodInstance) this.overridingMethod;
    // pc bounds  are contravariant:
    //  the pc bound on mi may be more restrictive than the
    // pc bound on mj
    Label beginAccessLabi = overriding.beginAccessLabel();
    NamedLabel beginAccessi = new NamedLabel(
        "sub_start_access_bound", "starting access bound of method " + overriding.name() + " in " + overriding.container(),
        beginAccessLabi);
    Label beginAccessLabj = overridden.beginAccessLabel();
    NamedLabel beginAccessj = new NamedLabel(
        "sup_start_access_bound", "starting access bound of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, beginAccessLabj));
    newlc.constrain(beginAccessj, LabelConstraint.LEQ, beginAccessi, A.labelEnv(),
        overriding.position(), new ConstraintMessage() {
          @Override
          public String msg() {
            return "Cannot override " + overridden.signature()
                 + " in " + overridden.container() + " with "
                 + overriding.signature() + " in "
                 + overriding.container()
                 + ". The beginning access bound of the "
                 + "overriding method "
                 + "cannot be less restrictive than in "
                 + "the overridden method.";

          }

          @Override
          public String detailMsg() {
            return msg()
                + " The beginning access bound of a method is a lower "
                + "bound on the access labels of field references that "
                + "the method may perform, and an upper bound of the "
                + "confidentiality of accesses prior to the call site.";

          }
        });

    // return labels are covariant
    //    the return label on mi may be less restrictive than the
    //    return label on mj
    Label endAccessLabi = overriding.endAccessLabel();
    NamedLabel endAccessi = new NamedLabel("sub_end_conf_label",
        "ending confidentiality label of method " + overriding.name() + " in " + overriding.container(),
        endAccessLabi);
    Label endAccessLabj = overridden.endAccessLabel();
    NamedLabel endAccessj = new NamedLabel("sup_return_label",
        "ending confidentiality label of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, endAccessLabj));
    newlc.constrain(endAccessi, LabelConstraint.LEQ, endAccessj, A.labelEnv(),
        overriding.position(),
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Cannot override " + overridden.signature()
                 + " in " + overridden.container() + " with "
                 + overriding.signature() + " in "
                 + overriding.container()
                 + ". The ending confidentiality label of the "
                 + "overriding method "
                 + "cannot be more restrictive than in "
                 + "the overridden method.";

          }

          @Override
          public String detailMsg() {
            return msg()
                + " The ending confidentiality label of a method is an upper "
                + "bound on the confidentiality of information accessed during "
                + "the method.";

          }
        });
  }

  @Override
  public void checkCall(LabelChecker lc, List<Type> throwTypes,
      boolean targetMayBeNull) throws SemanticException {
    super.checkCall(lc, throwTypes, targetMayBeNull);
    FabricTypeSystem fts = (FabricTypeSystem) lc.context().typeSystem();
    Label beginAccess = resolveBeginAccess(lc);
    Label endConf = resolveEndConf(lc);
    FabricContext A = (FabricContext) lc.context();

    // Check previous accesses against access label bound of method.
    NamedLabel accessPolLabel = new NamedLabel("begin access label of " + pi.signature(),
          "the lower bound of the access labels of all accesses in the method",
          fts.join(beginAccess,
            fts.pairLabel(position, fts.bottomConfPolicy(position), fts.topIntegPolicy(position))));

    // Get join of update labels of previous accesses
    NamedLabel accessedLabel = new NamedLabel("accessed label",
        "the join of the update labels of previously accessed objects",
        fts.join(A.accessedLabel(),
                 fts.join(A.accessedLabelBound(),
                          ((FabricPathMap) X).A())));

    lc.constrain(accessedLabel, LabelConstraint.LEQ, accessPolLabel,
        A.labelEnv(), position, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The objects accessed during the call to " + FabricCallHelper.this.pi.signature()
             + " could leak information about preceding accesses to the stores "
             + "of previously accessed objects, which have "
             + "access lower bounded by " + FabricCallHelper.this.pi + "'s begin access "
             + "label.";
      }
    });

    // Add in the update labels of the call's accesses to the A path.
    Label newA = fts.join(fts.join(((FabricPathMap) X).A(), endConf),
                           A.accessedLabel());
    X = ((FabricPathMap) X).A(newA);

    // Early check of end access bound to get better location reporting.
    NamedLabel newANamed = new NamedLabel("accessed label",
        "the join of the update labels of previously accessed objects",
        newA);

    NamedLabel endAccessBoundLabel = new NamedLabel("end access label",
        "the upper bound on the update labels of objects accessed in the current method",
        fts.join(A.endAccessBound(),
          fts.pairLabel(position, fts.bottomConfPolicy(position), fts.topIntegPolicy(position))));

    lc.constrain(newANamed, LabelConstraint.LEQ, endAccessBoundLabel,
        A.labelEnv(), position, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The current method makes more restricted accesses than the ending "
             + "access label allows.";
      }
    });
  }

  /**
   * Returns the instantiated begin access policy.
   * @throws SemanticException
   */
  protected Label resolveBeginAccess(LabelChecker lc)
      throws SemanticException {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pi;
    return instantiate(lc.context(), fpi.beginAccessLabel());
  }

  /**
   * Returns the instantiated end conf policy.
   * @throws SemanticException
   */
  protected Label resolveEndConf(LabelChecker lc)
      throws SemanticException {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pi;
    return instantiate(lc.context(), fpi.endAccessLabel());
  }
}
