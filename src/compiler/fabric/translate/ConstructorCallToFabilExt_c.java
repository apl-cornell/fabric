package fabric.translate;

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
    /*
    // Note if we've called another constructor.
    ConstructorCall n = (ConstructorCall) node();
    if (n.kind().equals(ConstructorCall.THIS)) {
      frw.setAnotherConstructorCalled();
    }
    */
    return super.toJava(frw);
  }
}
