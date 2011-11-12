package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.LabelToJavaExpr;
import jif.translate.LabelToJavaExpr_c;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import fabric.visit.FabricToFabilRewriter;

/**
 * 
 */
public class FabricThisLabelToFabilExpr_c extends LabelToJavaExpr_c implements
    LabelToJavaExpr {
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {

    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    Expr loc = ffrw.currentLocation();
    Expr workerPrincipal = rw.qq().parseExpr("Worker.getWorker().getPrincipal()");
    return rw.qq().parseExpr(
        rw.runtimeLabelUtil() + ".writerPolicyLabel(%E, %E, %E)", loc,
        workerPrincipal, workerPrincipal);
  }

}
