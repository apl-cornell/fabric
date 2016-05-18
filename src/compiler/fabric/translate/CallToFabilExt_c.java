package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILCall;
import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricCall;
import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;
import jif.translate.CallToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class CallToFabilExt_c extends CallToJavaExt_c {
  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    FabricCall c = (FabricCall) node();
    Expr e = super.exprToJava(rw);

    // AFAIK the only reason the result isn't a call is that it was a call to
    // clone.  I don't think we support that in Fabric?
    if (e instanceof FabILCall) {
      FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();

      FabILCall result = (FabILCall) e;
      // // Make sure that the first argument is a label
      // if (result.methodInstance() == null) return result;
      // List formalTypes = result.methodInstance().formalTypes();
      // if (formalTypes.size() <= 0 ||
      // !formalTypes.get(0).equals(((FabricTypeSystem)rw.typeSystem()).Label()))
      // throw new SemanticException("Method " + result.id() +
      // " cannot be called remotely since its first argument is not of label type");
      if (c.remoteWorker() != null) {
        result = (FabILCall) result.name(result.name() + "_remote");
        result = result.remoteWorker(c.remoteWorker());
        List<Expr> args = new ArrayList<>(result.arguments().size());
        // The first argument is actually a principal.
        args.add(nf.Call(Position.compilerGenerated(),
            rw.qq().parseExpr("worker$"),
            // nf.Local(Position.compilerGenerated(),
            // nf.Id(Position.compilerGenerated(), "worker$")),
            nf.Id(Position.compilerGenerated(), "getPrincipal")));
        // args.addAll(result.arguments().subList(1,
        // result.arguments().size()));
        args.addAll(result.arguments());
        result = (FabILCall) result.arguments(args);
      }

      FabricStagingExt fse = FabricUtil.fabricStagingExt(c);
      FabILNodeFactory fnf = (FabILNodeFactory) rw.java_nf();
      if (fse.endStage() != null) {
        // Add in staging.
        if (result.arguments().size() > 0) {
          // Wrap the last argument
          int lastIdx = result.arguments().size() - 1;
          List<Expr> args = new ArrayList<>(result.arguments());
          args.set(lastIdx,
              fnf.StageCall(result.position(),
                args.get(lastIdx),
                frw.stageCheckExpr(c, fse.endStage())));
          result = (FabILCall) result.arguments(args);
        } else if (result.target() instanceof Expr) {
          // Wrap the target
          result = (FabILCall) result.target(fnf.StageCall(result.position(),
                (Expr) result.target(),
                frw.stageCheckExpr(c, fse.endStage())));
        } else {
          // Use a ternary operator.
          return rw.qq().parseExpr("%E ? %E : %E",
                fnf.StageCall(result.position(), fnf.BooleanLit(result.position(), true),
                  frw.stageCheckExpr(c, fse.endStage())),
                result,
                result);
        }
      }

      return result;
    }
    return e;
  }
}
