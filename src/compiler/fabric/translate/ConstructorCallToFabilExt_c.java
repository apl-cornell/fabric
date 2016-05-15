package fabric.translate;

import fabric.extension.FabricStagingDel;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.ConstructorCallToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.ConstructorCall;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ConstructorCallToFabilExt_c extends ConstructorCallToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    ConstructorCall orig = (ConstructorCall) node();
    ConstructorCall call = (ConstructorCall) super.toJava(frw);
    FabricStagingDel fsd = (FabricStagingDel) orig.del();
    // Now update call with staging operation if needed
    if (fsd.stageCheck() != null) {
      // TODO: Staging!
    }
    return call;
  }
}
