package fabric.extension;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;
import fabric.visit.StageTxnMethodAdder;

import jif.ast.JifExt;
import jif.extension.JifExprExt;
import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Binary;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.Unary;
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

    // Squirrel away the dynamic staging check and update the path map and what
    // not.
    if (!lc.context().labelEnv().leq(conflictPC.label(), conflictL.label())) {
      FabricNodeFactory nf = (FabricNodeFactory) lc.nodeFactory();

      // Make the staging dynamic check.
      Expr stageCheck = nf.Unary(pos, Unary.NOT,
          nf.Binary(pos,
            nf.LabelExpr(pos, conflictPC.label()).type(ts.Label()),
            Binary.LE,
            nf.LabelExpr(pos, conflictL.label()).type(ts.Label())).type(ts.Boolean())).type(ts.Boolean());

      // Label check it.
      stageCheck = (Expr) lc.labelCheck(stageCheck);
      FabricFieldDel feDel = (FabricFieldDel) fe.del();

      // Squirrel it away for rewrite.
      feDel.setStageCheck(stageCheck);

      // Update the path map.
      Xe = Xe.join(getPathMap(stageCheck));
    }

    // Check pc ≤ CL(op field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              origFE + " may leak secret information to other transactions.";
          }
    });

    // Check CL(op field) ≤ meet(CL(prev accesses))
    lc.constrain(conflictL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + origFE +
              " can't be included in the current transaction stage.";
          }
    });
    
    // Update the CL
    Xe = Xe.CL(ts.meet(conflictL.label(), conflictPC.label()));
    return (Field) updatePathMap(fe, Xe);
  }

  static protected Receiver checkTarget(LabelChecker lc, Field fe) throws SemanticException {
    return JifFieldExt.checkTarget(lc, fe);
  }
}
