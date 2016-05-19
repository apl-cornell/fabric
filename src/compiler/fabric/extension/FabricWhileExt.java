package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifWhileExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Branch;
import polyglot.ast.Node;
import polyglot.ast.While;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricWhileExt extends JifWhileExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricWhileExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // TODO Auto-generated method stub
    // TODO Handle looping for staging rules.
    While ws = (While) node();

    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();
    A = (FabricContext) ws.del().enterScope(A);

    Label noAccesses = ts.noAccesses();

    Label L1 = ts.freshLabelVariable(ws.position(), "while",
            "conflict label for the while statement at " + ws.position());
    Label L2 = ts.freshLabelVariable(ws.position(), "while",
            "conflict label for end of the while statement at " +
            ws.position());
    Label loopEntryCL = A.conflictLabel();

    A = (FabricContext) A.pushBlock();

    A.setConflictLabel(L1);
    A.gotoConflictLabel(Branch.CONTINUE, null, L1);
    A.gotoConflictLabel(Branch.BREAK, null, L2);

    A = (FabricContext) A.pushBlock();

    ws = (While) super.labelCheckStmt(lc.context(A));

    A = (FabricContext) A.pop();
    A = (FabricContext) A.pop();

    FabricPathMap Xs = (FabricPathMap) getPathMap(ws.body());

    if (!Xs.CL().equals(ts.noAccesses()) ||
        !loopEntryCL.equals(ts.noAccesses())) {
      NamedLabel endCL;
      if (Xs.CL().equals(ts.noAccesses())) {
        endCL = new NamedLabel("loop_entry_conflict_pc",
            "label of the program counter just before the loop is executed",
            loopEntryCL);
      } else if (loopEntryCL.equals(ts.noAccesses())) {
        endCL = new NamedLabel("while_body.CL",
            "conflict label of normal termination of the loop body", Xs.CL());
      } else {
        endCL = new NamedLabel("while_body.CL",
            "conflict label of normal termination of the loop body", Xs.CL())
          .meet(lc, "loop_entry_conflict_pc",
              "label of the program counter just before the loop is executed",
              loopEntryCL);
      }
      // If this loop makes accesses, then we need to ensure that the starting
      // stage is the same as the ending stage before another iteration.
      lc.constrain(
          new NamedLabel("loop_conflict_pc",
            "conflict label at the top of the loop", L1),
          LabelConstraint.LEQ,
          endCL,
          A.labelEnv(), ws.position(), false, new ConstraintMessage() {
            @Override
            public String msg() {
              return "The stage at the end of the while loop must be compatible " +
                     "with the stage for evaluating the loop condition.";
            }
          });
    }

    FabricPathMap X = (FabricPathMap) getPathMap(ws);
    X = X.setCL(ts.gotoPath(Branch.BREAK, null), noAccesses);
    X = X.setCL(ts.gotoPath(Branch.CONTINUE, null), noAccesses);
    X = X.CL(L2);

    ws = (While) updatePathMap(ws, X);

    FabricStagingExt fse = FabricUtil.fabricStagingExt(ws);
    fse.setStageCheck(loopEntryCL, X.CL());

    // update the conflict pc
    A.setConflictLabel(X.CL());

    return ws;
  }
}
