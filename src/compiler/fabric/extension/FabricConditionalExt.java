package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifConditionalExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Conditional;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricConditionalExt extends JifConditionalExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricConditionalExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForConsequent(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForConsequent(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfexpr.CL()));
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Label origCL = ((FabricContext) lc.context()).conflictLabel();
    Conditional tern = (Conditional) super.labelCheck(lc);

    FabricTypeSystem ts = (FabricTypeSystem) lc.jifTypeSystem();
    FabricPathMap Xt = (FabricPathMap) getPathMap(tern.consequent());
    FabricPathMap Xf = (FabricPathMap) getPathMap(tern.alternative());
    // Either:
    // consequent staged and alternative didn't
    // alternative staged and consequent didn't
    // both staged but ended at differen't places
    if ((Xt.CL().equals(ts.noAccesses()) && !Xf.CL().equals(ts.noAccesses())) ||
        (!Xt.CL().equals(ts.noAccesses()) && Xf.CL().equals(ts.noAccesses())) ||
        (!Xt.CL().equals(ts.noAccesses()) && !Xf.CL().equals(ts.noAccesses()) &&
         (!lc.context().labelEnv().leq(Xt.CL(), Xf.CL()) ||
          !lc.context().labelEnv().leq(Xf.CL(), Xt.CL()))))
    {
      // For now, if we know that the stage might be different depending on the
      // branch we took, just stage up to the lower of the two.
      FabricStagingExt fse = FabricUtil.fabricStagingExt(tern);
      // Resulting path map CL will have the right stage label
      fse.setStageCheck(origCL, ((FabricPathMap) getPathMap(tern)).CL());
    }

    return tern;
  }
}
