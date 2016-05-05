package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Field fe = (Field) super.labelCheck(lc);
    DereferenceHelper.checkDereference(fe.target(), lc, node().position());
    return conflictLabelCheck(fe, lc, false);
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
  static protected Field conflictLabelCheck(final Field fe, LabelChecker lc,
      final boolean isWrite) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    // Only check persistent object accesses
    if (ts.isTransient(fe.target().type())
        && !ts.isFabricArray(fe.target().type()))
      return fe;

    Position pos = fe.position();
    // Using this instead of field instance so we get the instantiated label
    Label L = ((LabeledType) fe.type()).labelPart();
    FabricPathMap Xe = (FabricPathMap) getPathMap(fe);

    NamedLabel conflictL;
    if (isWrite) {
      conflictL = new NamedLabel("write conflict label", ts.writeConflict(L));
    } else {
      conflictL = new NamedLabel("read conflict label", ts.readConflict(L));
    }
    NamedLabel pc = new NamedLabel("pc",
        ts.join(ts.join(Xe.N(), A.currentCodePCBound()),
                ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));
    NamedLabel conflictPC = new NamedLabel("conflict pc",
        ts.join(ts.meet(Xe.CL(), ts.meet(A.conflictLabel(), A.beginConflictBound())),
                ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

    // Check pc ≤ CL(op field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              fe + " may leak secret information to other transactions.";
          }
    });

    // Check CL(op field) ≤ meet(CL(prev accesses))
    lc.constrain(conflictL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + fe +
              " can't be staged at the current point in a transaction.";
          }
    });

    // TODO: Insert staging check if necessary (possibly not here?)
    
    // Update the CL
    Xe = Xe.CL(ts.meet(conflictL.label(), conflictPC.label()));
    return (Field) updatePathMap(fe, Xe);
  }

  static protected Receiver checkTarget(LabelChecker lc, Field fe) throws SemanticException {
    return JifFieldExt.checkTarget(lc, fe);
  }
}
