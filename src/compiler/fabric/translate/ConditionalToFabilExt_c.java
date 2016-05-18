package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.ConditionalToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ConditionalToFabilExt_c extends ConditionalToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Expr rewritten = (Expr) super.toJava(rw);
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    return fse.stageCheck(frw, node(), rewritten);
  }
}
