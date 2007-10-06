package fabric.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import fabric.visit.ProxyRewriter;

public abstract class ClassMemberExt_c extends FabricExt_c implements
    ClassMemberExt {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#implMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#interfaceMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#proxyMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#staticImplMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> staticImplMember(ProxyRewriter pr,
      ClassDecl classDecl) {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#staticInterfaceMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> staticInterfaceMember(ProxyRewriter pr,
      ClassDecl classDecl) {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#staticProxyMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> staticProxyMember(ProxyRewriter pr,
      ClassDecl classDecl) {
    return Collections.emptyList();
  }

}
