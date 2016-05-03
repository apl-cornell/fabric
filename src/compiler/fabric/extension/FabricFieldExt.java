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
    return conflictLabelCheck(target, (Field) super.labelCheck(lc), lc, false);
  }

  /**
   * Check that the access does not leak on conflicts and can be "staged"
   * according to staged commit.
   *
   * Treats the access as a write if isWrite is true and otherwise treats the
   * access as a read.
   *
   * Assumes target has already been checked.
   */
  static protected Field conflictLabelCheck(Receiver target, final Field fe,
      LabelChecker lc, final boolean isWrite) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    Position pos = fe.position();
    FabricPathMap Xe = (FabricPathMap) getPathMap(target);

    ReferenceType targetType = targetType(ts, A, target, fe);
    FieldInstance fi = ts.findField(targetType, fe.name());
    Label L = ts.labelOfField(fi, A.pc());

    Label conflictL;
    NamedLabel conflictNL;
    if (isWrite) {
      conflictL = ts.writeConflict(L);
      conflictNL = new NamedLabel("write conflict label", conflictL);
    } else {
      conflictL = ts.readConflict(L);
      conflictNL = new NamedLabel("read conflict label", conflictL);
    }
    NamedLabel pc = new NamedLabel("pc", ts.join(Xe.N(),
          A.currentCodePCBound()));
    NamedLabel conflictPC = new NamedLabel("conflict pc", ts.meet(Xe.CL(),
          ts.meet(A.conflictLabel(), A.beginConflictBound())));

    // Check pc ≤ CL(op field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictNL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              fe + " may leak secret information to other transactions.";
          }
    });

    // Check CL(op field) ≤ meet(CL(prev accesses))
    lc.constrain(conflictNL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + fe +
              " can't be staged at the current point in a transaction.";
          }
    });

    // TODO: Insert staging check if necessary (possibly not here?)
    
    // Update the CL
    FabricPathMap X = Xe.CL(lc.lowerBound(conflictL, Xe.CL()));
    return (Field) updatePathMap(fe.target(target), X);
  }

  static protected Receiver checkTarget(LabelChecker lc, Field fe) throws SemanticException {
    return JifFieldExt.checkTarget(lc, fe);
  }
}
