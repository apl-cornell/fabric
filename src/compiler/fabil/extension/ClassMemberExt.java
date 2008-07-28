package fabil.extension;

import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import fabil.visit.ProxyRewriter;

public interface ClassMemberExt extends FabILExt {
  /**
   * Returns the $Proxy translation of the class member. The result is a list of
   * class members to be included in the $Proxy class.
   */
  List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the interface translation of the class member. The result is a list
   * of class members to be included in the interface.
   */
  List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the $Impl translation of the class member. The result is a list of
   * class members to be included in the $Impl class.
   */
  List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the $Static-interface translation of the class member. The result
   * is a list of class members to be included in the $Static interface.
   */
  List<ClassMember> staticInterfaceMember(ProxyRewriter pr, ClassDecl classDecl);

  /**
   * Returns the $Static.$Proxy translation of the class member. The result is a
   * list of class members to be included in the $Static.$Proxy class.
   */
  List<ClassMember> staticProxyMember(ProxyRewriter pr, ClassDecl classDecl);

  /**
   * Returns the $Static.$Impl translation of the class member. The result is a
   * list of class members to be included in the $Static.$Impl class.
   */
  List<ClassMember> staticImplMember(ProxyRewriter pr, ClassDecl classDecl);
}
