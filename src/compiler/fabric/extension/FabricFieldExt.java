package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricContext;
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
import polyglot.ast.Special;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // Jif checks.
    Field fe = (Field) super.labelCheck(lc);

    // Access label checks
    DereferenceHelper.checkDereference(fe.target(), lc, node().position());

    // Conflict checks.
    fe = conflictLabelCheck(fe, lc, false);
    return fe;
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
  static protected Field conflictLabelCheck(Field fe, LabelChecker lc,
      final boolean isWrite) throws SemanticException {
    final Field origFE = fe;
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    // Only check persistent object accesses
    if (ts.isTransient(fe.target().type())
        && !ts.isFabricArray(fe.target().type()))
      return fe;

    // Don't bother checking final field accesses, they aren't locked during
    // runtime.
    if (fe.fieldInstance().flags().isFinal())
      return fe;

    // If we're in a constructor, we know that we can't conflict with accesses
    // to the currently constructing object.
    if (lc.context().currentCode() instanceof ConstructorInstance &&
        fe.target() instanceof Special) {
      // XXX: Why doesn't inConstructorCall work?
      Special s = (Special) fe.target();
      if (s.kind().equals(Special.THIS))
        return fe;
    }

    Position pos = fe.position();

    // If the current method requires that there's no accesses, then we're in
    // trouble.
    if (A.beginConflictBound().equals(ts.noAccesses())) {
      throw new SemanticException("Access " + fe +
          " made in a method which does not provide begin and end conflict labels!", pos);
    }

    // Using this instead of field instance so we get the instantiated label
    Label L = ((LabeledType) fe.type()).labelPart();
    FabricPathMap Xe = (FabricPathMap) getPathMap(fe);

    NamedLabel conflictL;
    if (isWrite) {
      conflictL = new NamedLabel("write conflict label", ts.writeConflict(L));
    } else {
      conflictL = new NamedLabel("read conflict label", ts.readConflict(L));
    }
    NamedLabel pc = new NamedLabel("C(pc)",
        ts.pairLabel(pos,
          ts.confProjection(ts.join(Xe.N(), A.pc())),
          ts.topIntegPolicy(pos)));
    NamedLabel conflictPC = new NamedLabel("conflict pc",
        ts.meet(Xe.CL(), ts.meet(A.conflictLabel(), A.beginConflictBound())));

    // Squirrel away the dynamic staging check
    // XXX: I don't think we need to update the pathmap or anything, since
    // straight label comparisons don't do anything interesting for label
    // checking state.
    if (!lc.context().labelEnv().leq(conflictPC.label().simplify(),
          ts.join(conflictL.label().simplify(), ts.noComponentsLabel()))) {
      FabricStagingExt fse = FabricUtil.fabricStagingExt(fe);

      // Squirrel it away for rewrite.
      fse.setStageCheck(conflictL.label().simplify());
    }

    // Check CL(op field) ≤ meet(CL(prev accesses))
    lc.constrain(conflictL,
        LabelConstraint.LEQ,
        conflictPC.join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
        A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + origFE +
              " can't be included in the current transaction stage.";
          }
    });

    // Check pc ≤ CL(op field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              origFE + " may leak secret information to other transactions.";
          }
    });
    
    // Update the CL
    Xe = Xe.CL(ts.meet(conflictL.label(), conflictPC.label()));
    A.setConflictLabel(Xe.CL());
    return (Field) updatePathMap(fe, Xe);
  }

  static protected Receiver checkTarget(LabelChecker lc, Field fe) throws SemanticException {
    return JifFieldExt.checkTarget(lc, fe);
  }
}
