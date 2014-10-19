package fabric.extension;

import jif.extension.JifBlockExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.ast.Atomic;
import fabric.ast.FabricBranch;
import fabric.types.FabricTypeSystem;

public class AtomicJifExt_c extends JifBlockExt {
  public AtomicJifExt_c(ToJavaExt toJava) {
    super(toJava);
    // TODO Auto-generated constructor stub
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    Atomic atomic = (Atomic) node();

    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    JifContext A = lc.context();
    A = (JifContext) atomic.del().enterScope(A);

    Label entryPC = A.pc();

    // A fresh label variable for aborts to hook up with.
    Label L =
        ts.freshLabelVariable(atomic.position(), "while",
            "label of PC for the atomic block at " + atomic.position());

    A = (JifContext) A.pushBlock();
    A.setPc(L, lc);
    // Abort stmts will look up the label variable with <FabricBranch.ABORT,
    // null>.
    A.gotoLabel(FabricBranch.ABORT, null, L);

    lc.constrain(new NamedLabel("atomic_entry", "entry label of atomic block",
        entryPC), LabelConstraint.LEQ, new NamedLabel("atomic_block_pc",
            "label of PC in the atomic block", L), A.labelEnv(), atomic.position(),
            false, new ConstraintMessage() {
      @Override
      public String technicalMsg() {
        return "_pc_(atomic {S}) <= _pc_(S)";
      }
    });

    return super.labelCheckStmt(lc);
  }
}
