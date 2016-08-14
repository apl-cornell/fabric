package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifTryExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Block;
import polyglot.ast.Catch;
import polyglot.ast.Node;
import polyglot.ast.Try;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricTryExt extends JifTryExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricTryExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();
    Try trs = (Try) super.labelCheckStmt(lc);

    FabricPathMap X = (FabricPathMap) getPathMap(trs);
    FabricPathMap Xt = (FabricPathMap) getPathMap(trs.tryBlock());

    // Simplify the meet of the end of all catch blocks and a successful try
    // block.
    List<FabricPathMap> Xcatches = new ArrayList<>(trs.catchBlocks().size());
    for (Catch cb : trs.catchBlocks()) {
      Xcatches.add((FabricPathMap) getPathMap(cb));
    }
    Label preFinally = Xt.CL();
    for (FabricPathMap Xc : Xcatches) {
      if (A.labelEnv().leq(Xc.CL(), preFinally)) {
        preFinally = Xc.CL();
      } if (!A.labelEnv().leq(preFinally, Xc.CL())) {
        preFinally = ts.meet(preFinally, Xc.CL());
      }
    }

    // Simplify the ending CL
    if (A.labelEnv().leq(preFinally, X.CL())) {
      X = X.CL(preFinally);
    }

    return updatePathMap(trs, X);
  }

  @Override
  protected Block checkFinally(LabelChecker lc, JifContext A, Block f,
      PathMap Xprev) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext Af = (FabricContext) A;
    // Additional pop since we just pushed before this check.
    FabricContext AfOuter = (FabricContext) A.pop().pop();
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    // Starting CL is the join of all previous block ending CLs
    Af.setConflictLabel(Xfprev.CL());
    // We don't know if that stage started, however.
    if (!Xfprev.CL().equals(ts.noAccesses()) &&
        !A.labelEnv().leq(Xfprev.CL(), AfOuter.conflictLabel()))
      Af.setStageStarted(false);
    return super.checkFinally(lc, A, f, Xprev);
  }

  @Override
  protected Catch checkCatch(LabelChecker lc, JifContext A, PathMap Xtry,
      Catch cb) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext Af = (FabricContext) A;
    // Additional pop since we just pushed before this check.
    FabricContext AfOuter = (FabricContext) A.pop().pop();
    FabricPathMap Xftry = (FabricPathMap) Xtry;

    // If we started a new stage in the try, we don't know if it was started
    // before this exception was thrown.
    if (!Xftry.CL().equals(ts.noAccesses()) &&
        !A.labelEnv().leq(Xftry.CL(), AfOuter.conflictLabel()))
      Af.setStageStarted(false);

    return super.checkCatch(lc, A, Xtry, cb);
  }
}
