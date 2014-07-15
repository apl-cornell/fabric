package fabil.visit;

import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.types.TypeSystem;
import polyglot.visit.NodeVisitor;

/**
 * Outputs a java skeleton for bootstrapping.
 */
public class JavaSkeletonCreator extends NodeVisitor {
  QQ qq;

  public JavaSkeletonCreator(Job job, TypeSystem ts, NodeFactory nf) {
    qq = new QQ(job.extensionInfo());
  }

  @Override
  public Node leave(Node parent, Node old, Node n, NodeVisitor v) {
    // For each class,
    if (n instanceof ClassBody) {
      ClassDecl cd = (ClassDecl) parent;
      List<ClassMember> members = ((ClassBody) n).members();
      List<ClassMember> stubmembers = new LinkedList<>();
      for (ClassMember m : members) {
        if (m instanceof ClassDecl) {
          stubmembers.add(m);
        } else if (m instanceof MethodDecl) {
          MethodDecl pd = (MethodDecl) m;
          if (pd.body() != null) {
            pd = (MethodDecl) pd.body(null);
            if (!pd.flags().isNative())
              pd = (MethodDecl) pd.flags(pd.flags().Native());
          }
          stubmembers.add(pd);
          // } else if (m instanceof ConstructorDecl) {
          // ConstructorDecl cd = (ConstructorDecl) m;
          // Block b = cd.body();
          // if (b != null && b.statements() != null
          // && b.statements().get(0) instanceof ConstructorCall) {
          // List<Stmt> l = new LinkedList<Stmt>();
          // l.add((Stmt) b.statements().get(0));
          // cd = (ConstructorDecl) cd.body(b.statements(l));
          // } else
          // cd = (ConstructorDecl) cd.body(null);
          // stubmembers.add(cd);
          // }
        }
      }
      // The Impl class will be public and static.
      Flags flags = ProxyRewriter.toPublic(cd.flags()).Static();

      List<ClassMember> proxyMembers = new LinkedList<>();
      if (!cd.flags().isInterface()) {
        ClassMember implConstr =
            qq.parseMember("public _Proxy(" + cd.id() + "._Impl impl) {"
                + "super(impl); }");
        proxyMembers.add(implConstr);
      }

      ClassMember oidConstr =
          qq.parseMember("public _Proxy(fabric.worker.Store store, long onum) {"
              + "super(store, onum); }");
      proxyMembers.add(oidConstr);

      ClassDecl proxy =
          qq.parseDecl("public static class _Proxy extends "
              + cd.superClass().name() + "._Proxy implements %S {%LM}", cd
              .superClass().name(), members);
      stubmembers.add(proxy);

      ClassDecl impl =
          qq.parseDecl(flags + " class _Impl extends " + cd.id()
              + "._Impl implements %T {%LM}", cd.superClass().name(), members);

      stubmembers.add(impl);

      return ((ClassBody) n).members(stubmembers);
    }
    return n;
  }
}
