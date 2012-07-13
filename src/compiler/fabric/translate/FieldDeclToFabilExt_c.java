package fabric.translate;

import jif.translate.FieldDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabric.ast.FabricFieldDecl;

public class FieldDeclToFabilExt_c extends FieldDeclToJavaExt_c {
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    rw = (JifToJavaRewriter) super.toJavaEnter(rw);

    // XXX: the serialization pass doesn't use the Fabric NodeFactory *sigh*
    // skip fields used for polyglot metadata
    if (node() instanceof FabricFieldDecl) {
      FabricFieldDecl f = (FabricFieldDecl) node();
      return rw.bypass(f.accessLabel());
    } else return rw;
  }

}
