package fabric.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import fabric.visit.ProxyRewriter;
import fabric.visit.ThreadRewriter;

public class MethodDeclExt_c extends ClassMemberExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#implMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
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
  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl methodDecl = node();
    Flags flags = methodDecl.flags();

    // Don't include static methods in interfaces.
    if (flags.isStatic()) return Collections.emptyList();

    // Interface methods must be public and cannot be final nor synchronized.
    flags = ProxyRewriter.toPublic(flags).clearFinal().clearSynchronized();

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
  @Override
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    // Proxy methods will be added based on the method instances in the class
    // type, not on the methods declared. This handles the case where interfaces
    // and abstract classes don't explicitly declare all of their methods.
    return Collections.emptyList();
  }

  @Override
  public Node rewriteThreads(ThreadRewriter tr) {
    MethodDecl method = node();
    if (!method.name().equals("start")) return super.rewriteThreads(tr);

    ClassType ct = (ClassType) method.methodInstance().container();
    if (!tr.shouldRewrite(ct)) return super.rewriteThreads(tr);

    QQ qq = tr.qq();
    Block body = method.body();
    body =
        body.prepend(qq
            .parseStmt("fabric.client.transaction.TransactionManager"
                + ".getInstance().registerThread(this);"));

    return method.body(body);
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
