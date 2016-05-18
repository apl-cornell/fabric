package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.FieldToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public class FieldToFabilExt_c extends FieldToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    Field orig = (Field) node();
    FabricStagingExt fse = FabricUtil.fabricStagingExt(orig);
    Field fd = (Field) super.toJava(rw);
    if (fse.nextStage() != null) {
      if (!(fd.target() instanceof Expr)) {
        throw new InternalCompilerError("Staging does not currently support static non-final fields!");
      }
      return fd.target(fse.stageCheck(frw, orig, (Expr) fd.target()));
    }
    return fd;
  }
}
