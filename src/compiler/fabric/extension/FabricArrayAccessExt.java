package fabric.extension;

import fabric.types.FabricArrayType;
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
import polyglot.types.Type;
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
    ArrayAccess n = (ArrayAccess) super.labelCheck(lc);
    Expr array = n.array();
    Type type = lc.typeSystem().unlabel(array.type());
    DereferenceHelper.checkAccess(array, (FabricReferenceType) type, lc, node()
        .position());
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
  static protected ArrayAccess conflictLabelCheck(final ArrayAccess acc,
      LabelChecker lc, final boolean isWrite) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    Position pos = acc.position();
    FabricPathMap Xe = (FabricPathMap) getPathMap(acc);

    LabeledType arrayEntryType = (LabeledType) ((FabricArrayType) acc.array().type()).arrayOf();
    Label L = arrayEntryType.labelPart();

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

    // Check pc ≤ CL(op array access)
    lc.constrain(pc, LabelConstraint.LEQ, conflictNL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              acc + " may leak secret information to other transactions.";
          }
    });

    // Check CL(op array access) ≤ meet(CL(prev accesses))
    lc.constrain(conflictNL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + acc +
              " can't be staged at the current point in a transaction.";
          }
    });

    // TODO: Insert staging check if necessary (possibly not here?)
    
    // Update the CL
    FabricPathMap X = Xe.CL(lc.lowerBound(conflictL, Xe.CL()));
    return (ArrayAccess) updatePathMap(acc, X);
  }
}
