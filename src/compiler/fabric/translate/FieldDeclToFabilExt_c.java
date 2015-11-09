package fabric.translate;

import fabil.types.FabILFlags;

import fabric.ast.FabricFieldDecl;

import jif.translate.FieldDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class FieldDeclToFabilExt_c extends FieldDeclToJavaExt_c {
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    rw = (JifToJavaRewriter) super.toJavaEnter(rw);

    // XXX: the serialization pass doesn't use the Fabric NodeFactory *sigh*
    // skip fields used for polyglot metadata
    if (node() instanceof FabricFieldDecl) {
      FabricFieldDecl f = (FabricFieldDecl) node();
      return rw.bypass(f.accessPolicy());
    } else return rw;
  }

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FieldDecl n = (FieldDecl) node();
    FieldDecl fd = (FieldDecl) super.toJava(rw);
    if (n.flags().isFinal() && n instanceof FabricFieldDecl)
      fd = fd.flags(fd.flags().set(FabILFlags.IMMUTABLE));
    return fd;
  }
}
