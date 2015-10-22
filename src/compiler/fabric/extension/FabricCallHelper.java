package fabric.extension;

import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricMethodInstance;
import fabric.types.FabricTypeSystem;

import jif.extension.CallHelper;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.JifProcedureInstance;
import jif.types.JifTypeSystem;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
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
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    LabelChecker newlc = lc.context(A);

    // The method instances we're checking.
    final FabricMethodInstance overridden = (FabricMethodInstance) this.pi;
    final FabricMethodInstance overriding = (FabricMethodInstance) this.overridingMethod;
    // pc bounds  are contravariant:
    //  the pc bound on mi may be more restrictive than the
    // pc bound on mj
    ConfPolicy startAccessPoli = overriding.beginAccessPolicy();
    NamedLabel startAccessi = new NamedLabel(
        "sub_start_access_bound", "starting access bound of method " + overriding.name() + " in " + overriding.container(),
        ts.toLabel(startAccessPoli));
    ConfPolicy startAccessPolj = overridden.beginAccessPolicy();
    NamedLabel startAccessj = new NamedLabel(
        "sup_start_access_bound", "starting access bound of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, ts.toLabel(startAccessPolj)));
    newlc.constrain(startAccessj, LabelConstraint.LEQ, startAccessi, A.labelEnv(),
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
    ConfPolicy endConfPoli = overriding.endConfPolicy();
    NamedLabel endConfi = new NamedLabel("sub_end_conf_label",
        "ending confidentiality label of method " + overriding.name() + " in " + overriding.container(),
        ts.toLabel(endConfPoli));
    ConfPolicy endConfPolj = overridden.endConfPolicy();
    NamedLabel endConfj = new NamedLabel("sup_return_label",
        "ending confidentiality label of method " + overridden.name() + " in " + overridden.container(),
        instantiate(A, ts.toLabel(endConfPolj)));
    newlc.constrain(endConfi, LabelConstraint.LEQ, endConfj, A.labelEnv(),
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
}
