package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.IfToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.If;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class IfToFabilExt_c extends IfToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    If iff = (If) super.toJava(rw);

    // staging.
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    // TODO: Only put it in the necessary branch.
    return fse.stageCheck((FabricToFabilRewriter) rw, node(), iff);
  }
}
