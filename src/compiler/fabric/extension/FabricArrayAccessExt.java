package fabric.extension;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;
import fabric.types.NoAccesses;

import jif.extension.JifArrayAccessExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.Unary;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/**
 * Special handling for access and conflict labels.
 */
public class FabricArrayAccessExt extends JifArrayAccessExt {

  /**
   * @param toJava
   */
  public FabricArrayAccessExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // Jif checks
    ArrayAccess n = (ArrayAccess) super.labelCheck(lc);

    // Access label checks
    Expr array = n.array();
    FabricReferenceType type = (FabricReferenceType) lc.typeSystem().unlabel(array.type());
    DereferenceHelper.checkAccess(array,  type, lc, node()
        .position());

    // Conflict label checks
    return conflictLabelCheck(n, lc, false);
  }

  /**
   * Check that the array access does not leak on conflicts and can be "staged"
   * according to staged commit.
   *
   * Treats the array access as a write if isWrite is true and otherwise treats
   * the array access as a read.
   *
   * Assumes array expression has already been checked.
   */
  static protected ArrayAccess conflictLabelCheck(ArrayAccess acc,
      LabelChecker lc, final boolean isWrite) throws SemanticException {
    final ArrayAccess origACC = acc;
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    // Only check persistent object accesses
    if (ts.isTransient(acc.array().type())
        && !ts.isFabricArray(acc.array().type()))
      return acc;

    Position pos = acc.position();

    // If the current method requires that there's no accesses, then we're in
    // trouble.
    if (A.beginConflictBound().equals(ts.noAccesses())) {
      throw new SemanticException("Access " + acc +
          " made in a method which does not provide begin and end conflict labels!", pos);
    }

    FabricPathMap Xe = (FabricPathMap) getPathMap(acc);

    LabeledType arrayEntryType = (LabeledType) acc.array().type();
    Label L = arrayEntryType.labelPart();

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
        ts.join(ts.meet(Xe.CL(), ts.meet(A.conflictLabel(), A.beginConflictBound())),
                ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

    // Squirrel away the dynamic staging check
    // XXX: I don't think we need to update the pathmap or anything, since
    // straight label comparisons don't do anything interesting for label
    // checking state.
    if (!lc.context().labelEnv().leq(conflictPC.label().simplify(),
          conflictL.label().simplify())) {
      FabricNodeFactory nf = (FabricNodeFactory) lc.nodeFactory();

      FabricArrayAccessDel accDel = (FabricArrayAccessDel) acc.del();

      // Squirrel it away for rewrite.
      accDel.setStageCheck(conflictPC.label().simplify(),
          conflictL.label().simplify());
    }

    // Check CL(op array access) ≤ meet(CL(prev accesses))
    lc.constrain(conflictL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access " + origACC +
              " can't be staged at the current point in a transaction.";
          }
    });

    // Check pc ≤ CL(op array access)
    lc.constrain(pc, LabelConstraint.LEQ, conflictL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              origACC + " may leak secret information to other transactions.";
          }
    });
    
    // Update the CL
    Xe = Xe.CL(ts.meet(conflictL.label(), conflictPC.label()));
    A.setConflictLabel(Xe.CL());
    return (ArrayAccess) updatePathMap(acc, Xe);
  }

  @Override
  protected void updateContextForIndex(LabelChecker lc, JifContext A,
      PathMap Xarr) {
    super.updateContextForIndex(lc, A, Xarr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfarr = (FabricPathMap) Xarr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfarr.CL()));
  }
}
