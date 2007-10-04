package fabric.extension;

import java.util.List;

import fabric.visit.ProxyRewriter;
import polyglot.ast.ClassMember;
import polyglot.ast.ClassDecl;

public interface ClassMemberExt extends FabricExt {
  /**
   * Returns the Proxy translation of the class member. The result is a list of
   * class members to be included in the Proxy class.
   */
  List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the interface translation of the class member. The result is a list
   * of class members to be included in the interface.
   */
  List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the Impl translation of the class member. The result is a list of
   * class members to be included in the Impl class.
   */
  List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent);
}
