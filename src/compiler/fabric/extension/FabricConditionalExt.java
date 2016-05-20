package fabric.extension;

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
    Label tCL = Xt.CL();
    Label fCL = Xf.CL();
    boolean tToF = A.labelEnv().leq(tCL, fCL);
    boolean fToT = A.labelEnv().leq(tCL, fCL);

    // Simplify based on environment
    FabricPathMap X = (FabricPathMap) getPathMap(tern);
    if (fToT && tToF) {
      Expr alt = (Expr) updatePathMap(tern.alternative(), Xf.CL(tCL));
      tern = tern.alternative(alt);
      X = X.CL(tCL);
    } else if (tToF) {
      X = X.CL(tCL);
    } else if (fToT) {
      X = X.CL(fCL);
    } else {
      X = X.CL(ts.meet(tCL, fCL));
    }
    tern = (Conditional) updatePathMap(tern, X);

    // Update the conflict pc to be the meet now.
    A.setConflictLabel(X.CL());

    // Staging happened in at least one branch.
    if ((!Xt.CL().equals(ts.noAccesses()) && !startingCL.equals(Xt.CL())) ||
        (!Xf.CL().equals(ts.noAccesses()) && !startingCL.equals(Xf.CL()))) {
      // We need to worry about staging checks at the next access if one of the
      // branchs didn't start the stage.
      A.setStageStarted(tToF && fToT);
    }

    return tern;
  }
}
