package fabric.extension;

import java.util.List;

import fabric.visit.ProxyRewriter;
import polyglot.ast.ClassMember;
import polyglot.ast.ClassDecl;

public interface ClassMemberExt extends FabricExt {
  /**
   * @param parent TODO
   * @return the members to add to the proxy class, or null
   */
  List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent);
  
  /**
   * @param parent TODO
   * @return the members to add to the interface, or null
   */
  List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent);
  
  /**
   * @param parent TODO
   * @return the members to add to the impl class, or null
   */
  List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent);
}
