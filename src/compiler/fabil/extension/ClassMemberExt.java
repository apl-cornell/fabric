package fabil.extension;

import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Stmt;
import fabil.visit.ProxyRewriter;

public interface ClassMemberExt extends FabILExt {
  /**
   * Returns the _Proxy translation of the class member. The result is a list of
   * class members to be included in the _Proxy class.
   */
  List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the interface translation of the class member. The result is a list
   * of class members to be included in the interface.
   */
  List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the _Impl translation of the class member. The result is a list of
   * class members to be included in the _Impl class.
   */
  List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent);

  /**
   * Returns the _Static-interface translation of the class member. The result
   * is a list of class members to be included in the _Static interface.
   */
  List<ClassMember> staticInterfaceMember(ProxyRewriter pr, ClassDecl classDecl);

  /**
   * Returns the _Static._Proxy translation of the class member. The result is a
   * list of class members to be included in the _Static._Proxy class.
   */
  List<ClassMember> staticProxyMember(ProxyRewriter pr, ClassDecl classDecl);

  /**
   * Returns the _Static._Impl translation of the class member. The result is a
   * list of class members to be included in the _Static._Impl class.
   */
  List<ClassMember> staticImplMember(ProxyRewriter pr, ClassDecl classDecl);
  
  /**
   * Returns the _Static._Impl.$init translation of the class member. The result
   * is a list of statements to be included in the _Static._Impl.$init method.
   */
  List<Stmt> staticImplInitMember(ProxyRewriter pr);
}
