package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.JifToJavaRewriter;
import jif.translate.WhileToJavaExt_c;

import polyglot.ast.Node;
import polyglot.ast.While;
import polyglot.types.SemanticException;

public class WhileToFabilExt_c extends WhileToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    While ws = (While) super.toJava(rw);
    return fse.stageCheck(frw, node(), ws);
  }

}
