package fabric.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.MethodDecl;
import polyglot.types.Flags;
import fabric.visit.ProxyRewriter;

public class MethodDeclExt_c extends FabricExt_c implements ClassMemberExt {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#implMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // Since the Impl will implement an interface, the method has to be public.
    MethodDecl result = node();
    Flags flags = ProxyRewriter.toPublic(result.flags());
    result = result.flags(flags);
    return Collections.singletonList((ClassMember) result);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#interfaceMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @SuppressWarnings("unchecked")
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl methodDecl = node();
    Flags flags = methodDecl.flags();

    // Don't include static methods in interfaces.
    if (flags.isStatic()) return Collections.EMPTY_LIST;

    // Interface methods must be public and cannot be final.
    flags = ProxyRewriter.toPublic(flags).clearFinal();

    // Clear out the method body.
    ClassMember result = (ClassMember) methodDecl.flags(flags).body(null);
    return Collections.singletonList(result);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#proxyMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @SuppressWarnings("unchecked")
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    // Proxy methods will be added based on the method instances in the class
    // type, not on the methods declared. This handles the case where interfaces
    // and abstract classes don't explicitly declare all of their methods.
    return Collections.EMPTY_LIST;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public MethodDecl node() {
    return (MethodDecl) super.node();
  }
}
