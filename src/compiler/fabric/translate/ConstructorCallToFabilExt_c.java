package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILCall;
import fabil.ast.FabILNodeFactory;

import fabric.extension.FabricStagingDel;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.ConstructorCallToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.ConstructorCall;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ConstructorCallToFabilExt_c extends ConstructorCallToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    ConstructorCall orig = (ConstructorCall) node();
    ConstructorCall call = (ConstructorCall) super.toJava(frw);
    FabricStagingDel fsd = (FabricStagingDel) orig.del();
    FabILNodeFactory nf = (FabILNodeFactory) frw.java_nf();
    // Now update call with staging operation if needed
    if (fsd.stageCheck() != null) {
      // Add in staging.
      if (call.arguments().size() > 0) {
        // Wrap the last argument
        int lastIdx = call.arguments().size() - 1;
        List<Expr> args = new ArrayList<>(call.arguments());
        args.set(lastIdx,
            nf.StageCall(call.position(),
              args.get(lastIdx),
              rw.visitEdge(call, fsd.stageCheck())));
        call = (ConstructorCall) call.arguments(args);
      } else {
        // Use a ternary operator.
        return rw.qq().parseExpr("%E ? %E : %E",
              nf.StageCall(call.position(),
                nf.BooleanLit(call.position(), true),
                rw.visitEdge(call, fsd.stageCheck())),
              call,
              call);
      }
    }
    return call;
  }
}
