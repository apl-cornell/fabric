package fabric.extension;

import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifForExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Branch;
import polyglot.ast.For;
import polyglot.ast.ForInit;
import polyglot.ast.ForUpdate;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricForExt extends JifForExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricForExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node checkLoop(LabelChecker lc, JifContext A, For fs,
      List<ForInit> inits, PathMap Xinit) throws SemanticException {

    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext Af = (FabricContext) A;

    Label noAccesses = ts.noAccesses();

    Label L1 = ts.freshLabelVariable(fs.position(), "for",
            "conflict label for the for loop at " + fs.position());
    Label L2 = ts.freshLabelVariable(fs.position(), "for",
            "conflict label for end of the for loop at " +
            fs.position());
    Label loopEntryCL = Af.conflictLabel();

    Af = (FabricContext) Af.pushBlock();

    Af.setConflictLabel(L1);
    Af.gotoConflictLabel(Branch.CONTINUE, null, L1);
    Af.gotoConflictLabel(Branch.BREAK, null, L2);

    Af = (FabricContext) Af.pushBlock();

    // At this point, we don't know if the current stage is the result of
    // accesses before the loop or during it.
    Af.setStageStarted(false);

    fs = (For) super.checkLoop(lc, Af, fs, inits, Xinit);

    Af = (FabricContext) Af.pop();
    Af = (FabricContext) Af.pop();

    FabricPathMap Xbody = (FabricPathMap) getPathMap(fs.body());
    for (ForUpdate update : fs.iters()) {
      FabricPathMap Xs = (FabricPathMap) getPathMap(update);
      Xbody = Xbody.join(Xs);
    }

    if (!Xbody.CL().equals(ts.noAccesses()) ||
        !loopEntryCL.equals(ts.noAccesses())) {
      NamedLabel endCL;
      if (Xbody.CL().equals(ts.noAccesses())) {
        endCL = new NamedLabel("loop_entry_conflict_pc",
            "label of the program counter just before the loop is executed",
            loopEntryCL);
      } else if (loopEntryCL.equals(ts.noAccesses())) {
        endCL = new NamedLabel("for_body.CL",
            "conflict label of normal termination of the loop body", Xbody.CL());
      } else {
        endCL = new NamedLabel("for_body.CL",
            "conflict label of normal termination of the loop body", Xbody.CL())
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
          Af.labelEnv(), fs.position(), false, new ConstraintMessage() {
            @Override
            public String msg() {
              return "The stage at the end of the for loop must be compatible " +
                     "with the stage for evaluating the loop condition.";
            }
          });
    }

    FabricPathMap X = (FabricPathMap) getPathMap(fs);
    X = X.CL(L2);
    X = X.setCL(ts.gotoPath(Branch.BREAK, null), noAccesses);
    X = X.setCL(ts.gotoPath(Branch.CONTINUE, null), noAccesses);

    fs = (For) updatePathMap(fs, X);

    // We don't know if the current stage was started by accesses in the loop or
    // not (depends on the exact path taken), so double check on the next
    // access.
    Af.setStageStarted(false);

    // update the conflict pc
    Af.setConflictLabel(X.CL());

    return fs;
  }
}
