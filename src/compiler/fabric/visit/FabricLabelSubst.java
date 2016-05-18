package fabric.visit;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;

import jif.types.JifTypeSystem;
import jif.types.Solver;
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
      fse.setStageCheck(bounds.applyTo(fse.nextStage()));
    }
    return nd;
  }
}
