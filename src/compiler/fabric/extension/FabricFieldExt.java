package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.FieldInstance;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Receiver target = checkTarget(lc, (Field) node());
    DereferenceHelper.checkDereference(target, lc, node().position());
    return conflictLabelCheck(target, (Field) super.labelCheck(lc), lc);
  }

  /**
   * Check that the access does not leak on conflicts and can be "staged"
   * according to staged commit.
   *
   * Assumes target has already been checked.
   */
  private Node conflictLabelCheck(Receiver target, final Field fe,
      LabelChecker lc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    Position pos = fe.position();
    FabricPathMap Xe = (FabricPathMap) getPathMap(target);

    ReferenceType targetType = targetType(ts, A, target, fe);
    FieldInstance fi = ts.findField(targetType, fe.name());
    Label L = ts.labelOfField(fi, A.pc());

    Label conflictL = ts.readConflict(L);
    NamedLabel conflictNL = new NamedLabel("read conflict label", conflictL);
    NamedLabel pc = new NamedLabel("pc", ts.join(Xe.N(), A.currentCodePCBound()));
    NamedLabel conflictPC = new NamedLabel("conflict pc", ts.meet(Xe.CL(), A.conflictLabel()));

    // Check pc ≤ CL(field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictNL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when reading " + fe +
              " may leak secret information to other transactions.";
          }
    });

    // Check CL(field) ≤ meet(CL(prev accesses)
    lc.constrain(conflictNL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Access of " + fe +
              " can't be staged at the current point in a transaction.";
          }
    });

    // TODO: Insert staging check if necessary?
    
    // Update the CL
    FabricPathMap X = Xe.CL(lc.lowerBound(conflictL, Xe.CL()));
    return updatePathMap(fe.target(target), X);
  }
}
