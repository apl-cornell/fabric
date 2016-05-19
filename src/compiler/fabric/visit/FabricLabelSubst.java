package fabric.visit;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;

import jif.types.JifTypeSystem;
import jif.types.Solver;
import jif.types.label.Label;
import jif.visit.JifLabelSubst;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;

public class FabricLabelSubst extends JifLabelSubst {
  public FabricLabelSubst(Job job, JifTypeSystem ts, NodeFactory nf,
      Solver solver) {
    super(job, ts, nf, solver);
  }

  @Override
  protected Node updateNode(Node n) throws SemanticException {
    Node nd = super.updateNode(n);
    // Substitute for labels in staging check.
    FabricStagingExt fse = FabricUtil.fabricStagingExt(nd);
    if (fse.nextStage() != null) {
      Label cur = bounds.applyTo(fse.curStage()).simplify();
      Label next = bounds.applyTo(fse.nextStage()).simplify();
      if (cur.equals(next)) {
        fse.setStageCheck(null, null);
      } else {
        fse.setStageCheck(cur, next);
      }
    }
    return nd;
  }
}
