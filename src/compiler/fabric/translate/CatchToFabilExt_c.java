package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.CatchToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.Catch;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class CatchToFabilExt_c extends CatchToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Catch c = (Catch) super.toJava(rw);
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    return c.body(rw.java_nf().Block(c.position(),
          fse.stageCheck((FabricToFabilRewriter) rw, node(), c.body())));
  }
}
