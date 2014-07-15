package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.LabelToJavaExpr;
import jif.translate.LabelToJavaExpr_c;
import jif.types.label.Label;
import polyglot.ast.Expr;
import fabric.visit.FabricToFabilRewriter;

/**
 *
 */
public class FabricThisLabelToFabilExpr_c extends LabelToJavaExpr_c implements
LabelToJavaExpr {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw) {

    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    Expr loc = ffrw.currentLocation();
    Expr workerPrincipal =
        rw.qq().parseExpr("Worker.getWorker().getPrincipal()");
    return rw.qq().parseExpr(
        rw.runtimeLabelUtil() + ".writerPolicyLabel(%E, %E, %E)", loc,
        workerPrincipal, workerPrincipal);
  }

}
