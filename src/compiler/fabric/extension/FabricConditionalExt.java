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
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricConditionalExt extends JifConditionalExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricConditionalExt(ToJavaExt toJava) {
    super(toJava);
  }

  /**
   * Modified so we can reset the CL after the first branch.
   */
  @Override
  protected void updateContextForConsequent(LabelChecker lc, JifContext A,
      PathMap Xexpr) {
    super.updateContextForConsequent(lc, A, Xexpr);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfexpr = (FabricPathMap) Xexpr;
    Af.setConflictLabel(Xfexpr.CL());
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    FabricContext A = (FabricContext) lc.context();
    Label startingCL = A.conflictLabel();

    Conditional tern = (Conditional) super.labelCheck(lc);

    FabricTypeSystem ts = (FabricTypeSystem) lc.jifTypeSystem();
    FabricPathMap Xt = (FabricPathMap) getPathMap(tern.consequent());
    FabricPathMap Xf = (FabricPathMap) getPathMap(tern.alternative());

    // Simplify based on environment
    FabricPathMap X = (FabricPathMap) getPathMap(tern);
    if (A.labelEnv().leq(Xt.CL(), Xf.CL()) && A.labelEnv().leq(Xf.CL(), Xt.CL())) {
      Expr alt = (Expr) updatePathMap(tern.alternative(), Xf.CL(Xt.CL()));
      tern = tern.alternative(alt);
      X = X.CL(Xt.CL());
    } else if (A.labelEnv().leq(Xt.CL(), Xf.CL())) {
      X = X.CL(Xt.CL());
    } else if (A.labelEnv().leq(Xf.CL(), Xt.CL())) {
      X = X.CL(Xf.CL());
    } else {
      X = X.CL(ts.meet(Xt.CL(), Xf.CL()));
    }
    tern = (Conditional) updatePathMap(tern, X);

    // Update the conflict pc to be the meet now.
    A.setConflictLabel(X.CL());

    // Either:
    // consequent staged and alternative didn't
    // alternative staged and consequent didn't
    // both staged but ended at differen't places
    if ((Xt.CL().equals(ts.noAccesses()) && !Xf.CL().equals(ts.noAccesses())) ||
        (!Xt.CL().equals(ts.noAccesses()) && Xf.CL().equals(ts.noAccesses())) ||
        (!Xt.CL().equals(ts.noAccesses()) && !Xf.CL().equals(ts.noAccesses()) &&
         (!A.labelEnv().leq(Xt.CL(), Xf.CL()) ||
          !A.labelEnv().leq(Xf.CL(), Xt.CL()))))
    {
      // For now, if we know that the stage might be different depending on the
      // branch we took, just stage up to the lower of the two.
      FabricStagingExt fse = FabricUtil.fabricStagingExt(tern);
      // Resulting path map CL will have the right stage label
      fse.setStageCheck(startingCL.simplify(), X.CL().simplify());
    }

    return tern;
  }
}
