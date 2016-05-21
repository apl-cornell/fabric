package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;

import jif.extension.JifArrayAccessExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Node;
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
    NamedLabel pc = new NamedLabel("pc", ts.join(Xe.N(), A.pc()));
    NamedLabel conflictPC = new NamedLabel("conflict pc", A.conflictLabel());
    NamedLabel endConflict = new NamedLabel("end conflict bound", A.endConflictBound());

    // Squirrel away the dynamic staging check
    FabricStagingExt fse = FabricUtil.fabricStagingExt(acc);
    fse.setStageCheck(conflictPC.label(), conflictL.label(), A);

    // Check CL(op array access) ≤ meet(CL(prev accesses))
    //lc.constrain(conflictL.meet(lc,
            //"{⊤→;⊤←}",
            //ts.pairLabel(pos, ts.topConfPolicy(pos), ts.bottomIntegPolicy(pos))),
    lc.constrain(conflictL,
        LabelConstraint.LEQ,
        conflictPC.join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
        A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access " + origACC +
              " can't be staged at the current point in a transaction.";
          }
    });

    // Conflict label on right hand.
    NamedLabel conflictLR = new NamedLabel("write conflict label",
        conflictL.label()).join(lc, "{⊥→;⊥←}", ts.noComponentsLabel());

    // Check pc ≤ CL(op array access)
    lc.constrain(pc, LabelConstraint.LEQ, conflictLR,  A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              origACC + " may leak secret information to other transactions.";
          }
    });
    
    // Check end conflict.
    lc.constrain(endConflict, LabelConstraint.LEQ, conflictLR, A.labelEnv(),
        pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + origACC +
              " is lower than the current method's ending conflict label.";
          }
    });
    
    // Update the CL
    Xe = Xe.CL(conflictL.label());
    A.setConflictLabel(Xe.CL());
    return (ArrayAccess) updatePathMap(acc, Xe);
  }
}
