package fabric.translate;

import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricArrayInit;

import jif.translate.ArrayInitToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class FabricArrayInitToFabilExt_c extends ArrayInitToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricArrayInit n = (FabricArrayInit) node();
    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();

    return nf.FabricArrayInit(n.position(), n.elements());
  }
}
