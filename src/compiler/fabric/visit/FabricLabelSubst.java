package fabric.visit;

import fabric.extension.FabricStagingDel;

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
    if (nd.del() instanceof FabricStagingDel) {
      // Substitute for labels in staging check.
      FabricStagingDel fsd = (FabricStagingDel) nd.del();
      if (fsd.startStage() != null || fsd.endStage() != null) {
        fsd.setStageCheck(bounds.applyTo(fsd.startStage()), bounds.applyTo(fsd.endStage()));
      }
    }
    return nd;
  }
}
