package fabric.extension;

import fabric.types.FabricParsedClassType;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.TypeBuilder;
import jif.ast.JifUtil;
import jif.extension.JifMethodDeclDel;

public class MethodDeclJifDel extends JifMethodDeclDel {

  @Override
  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    Node n = super.buildTypes(tb);
    MethodDeclJifExt ext = (MethodDeclJifExt) JifUtil.jifExt(n);
    //Don't add remote wrappers to the class type 
    //  XXX: this is the Right Thing, but happens to avoid a 
    //         known bug (see https://forge.cornell.edu/sf/go/task5278). 
    //         Commenting out the block below will cause the bug to reappear.
    if(ext.isRemote()) {
      MethodDecl md = (MethodDecl) n;
      FabricParsedClassType ct = (FabricParsedClassType) tb.currentClass();
      ct.removeMethod(md.methodInstance());
    }
    return n;
  }

}
