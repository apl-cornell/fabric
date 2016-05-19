package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.ForToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.For;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ForToFabilExt_c extends ForToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    For fs = (For) super.toJava(rw);
    return fse.stageCheck(frw, node(), fs);
  }
}
