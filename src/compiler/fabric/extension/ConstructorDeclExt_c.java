package fabric.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import fabric.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends FabricExt_c implements ClassMemberExt {

  public List<ClassMember> implMember(ProxyRewriter pr) {
    // TODO add Core parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = node.name("$Impl");
    return Collections.singletonList((ClassMember) node);
  }

  public List<ClassMember> interfaceMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

}
