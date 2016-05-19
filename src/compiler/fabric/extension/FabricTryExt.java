package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.ast.FabricUtil;
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
    Label startingCL = A.conflictLabel();

    Try trs = (Try) super.labelCheckStmt(lc);

    FabricPathMap X = (FabricPathMap) getPathMap(trs);
    FabricPathMap Xt = (FabricPathMap) getPathMap(trs.tryBlock());
    
    // Staging for try block.
    FabricStagingExt fseTry = FabricUtil.fabricStagingExt(trs.tryBlock());
    fseTry.setStageCheck(startingCL, Xt.CL(), A);

    // Staging for catches.
    List<FabricPathMap> Xcatches = new ArrayList<>(trs.catchBlocks().size());
    for (Catch cb : trs.catchBlocks()) {
      Xcatches.add((FabricPathMap) getPathMap(cb));
    }
    // Simplify the meet of the end of all catch blocks and a successful try
    // block.
    Label preFinally = Xt.CL();
    for (FabricPathMap Xc : Xcatches) {
      if (A.labelEnv().leq(Xc.CL(), preFinally)) {
        preFinally = Xc.CL();
      } if (!A.labelEnv().leq(preFinally, Xc.CL())) {
        preFinally = ts.meet(preFinally, Xc.CL());
      }
    }
    for (Catch cb : trs.catchBlocks()) {
      FabricStagingExt fseCB = FabricUtil.fabricStagingExt(cb);
      fseCB.setStageCheck(Xt.CL(), preFinally, A);
    }

    // Staging for finally, if it's there.
    if (trs.finallyBlock() != null) {
      FabricStagingExt fseFinally =
        FabricUtil.fabricStagingExt(trs.finallyBlock());
      fseFinally.setStageCheck(preFinally, X.CL(), A);
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
    // Starting CL is the meet of all previous block ending CLs
    ((FabricContext) A).setConflictLabel(((FabricPathMap) Xprev).CL());
    return super.checkFinally(lc, A, f, Xprev);
  }
}
