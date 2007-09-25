package fabric.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import fabric.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends FabricExt_c implements ClassMemberExt {

  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // TODO add Core parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = node.name("$Impl");
    return Collections.singletonList((ClassMember) node);
  }

  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

}
