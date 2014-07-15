package fabil.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Stmt;
import fabil.visit.ProxyRewriter;

/**
 *
 */
public abstract class ClassMemberExt_c extends FabILExt_c implements
ClassMemberExt {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticImplMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<Stmt> staticImplInitMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticInterfaceMember(ProxyRewriter pr,
      ClassDecl parent) {
    return Collections.emptyList();
  }

  @Override
  public List<ClassMember> staticProxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

}
