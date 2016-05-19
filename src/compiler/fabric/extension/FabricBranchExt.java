package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifBranchExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Branch;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.SerialVersionUID;

public class FabricBranchExt extends JifBranchExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricBranchExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    final Branch bs = (Branch) super.labelCheckStmt(lc);

    // Check that the current stage works with the stage associated with the
    // label we're branching to.
    FabricContext A = (FabricContext) lc.context();
    A = (FabricContext) bs.del().enterScope(A);
    Label conflictPC = A.conflictLabel();
    Label gotoCL = A.gotoConflictLabel(bs.kind(), bs.label());

    if (gotoCL == null) {
      throw new InternalCompilerError("Can't find target for " + bs.kind() + " "
          + bs.label());
    }

    lc.constrain(
        new NamedLabel("conflict_pc_target",
          "lower bound on the conflict label for the stage that can be running at the target program point",
          gotoCL),
        LabelConstraint.LEQ,
        new NamedLabel("conflict_pc",
          "the conflict label for the current stage",
          conflictPC),
        A.labelEnv(), bs.position(), new ConstraintMessage() {
          @Override
          public String msg() {
            return "The branch statement at " + bs.position() + " is running at" 
                 + " a less privileged stage during staged commit than the "
                 + "target point.  As a result, this branch would violate "
                 + "the monotonicity rule for staged commit.";
          }
        });
    
    // Remove the CL from the pathmap so it's not included in the next
    // statement's conflict PC and add it to the gotoPath.
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricPathMap X = (FabricPathMap) getPathMap(bs);
    X = X.setCL(ts.gotoPath(bs.kind(), bs.label()), X.CL());
    X = X.CL(ts.noAccesses());
    return updatePathMap(bs, X);
  }
}
