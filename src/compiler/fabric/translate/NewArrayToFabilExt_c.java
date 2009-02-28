package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.extension.NewArrayExt_c;
import polyglot.ast.Ext;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.NewArrayToJavaExt_c;

public class NewArrayToFabilExt_c extends NewArrayToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    NewArray n = (NewArray)node();
    NewArrayExt_c ext = (NewArrayExt_c)FabricUtil.fabricExt(n);
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    // TODO we need to pass on the field label as well.
    return nf.NewArray(n.position(), n.baseType(), null, ext.location(), 
                       n.dims(), n.additionalDims(), n.init());
  }
}
