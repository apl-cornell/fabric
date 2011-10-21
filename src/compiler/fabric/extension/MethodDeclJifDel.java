package fabric.extension;

import jif.ast.JifUtil;
import jif.extension.JifMethodDeclDel;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.TypeBuilder;
import fabric.types.FabricParsedClassType;

public class MethodDeclJifDel extends JifMethodDeclDel {

  @Override
  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    Node n = super.buildTypes(tb);
    MethodDeclJifExt ext = (MethodDeclJifExt) JifUtil.jifExt(n);
    //Don't add remote wrappers to the class type 
    if (ext.isRemote()) {
      MethodDecl md = (MethodDecl) n;
      FabricParsedClassType ct = (FabricParsedClassType) tb.currentClass();
      ct.removeMethod(md.methodInstance());
    }
    return n;
  }

}
