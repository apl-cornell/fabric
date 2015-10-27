package fabil.visit;

import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.FieldDecl;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

/**
 * Outputs a java skeleton for bootstrapping.
 *
 * Usage: run right after parsing. Strips bodies from methods, ProxyRewriter does the rest.
 *
 * Limitations:
 * -- doesn't play well with classes that import java.lang.Object explicitly.
 * -- doesn't play well with Java classes in the runtime (follow FabricClassLoader hack below if needed)
 *
 */
public class JavaSkeletonCreator extends NodeVisitor {
  QQ qq;
  NodeFactory nf;

  public JavaSkeletonCreator(Job job, TypeSystem ts, NodeFactory nf) {
    this.nf = nf;
    qq = new QQ(job.extensionInfo());
  }

  @Override
  public Node leave(Node parent, Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassBody && parent instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl) parent;
      if (cd.flags().contains(Flags.INTERFACE)) return n;

      List<ClassMember> members = ((ClassBody) n).members();
      List<ClassMember> stubmembers = new LinkedList<>();
      for (ClassMember m : members) {
        if (m instanceof ClassDecl) {
          stubmembers.add(m);
        } else if (m instanceof FieldDecl) {
          FieldDecl fd = (FieldDecl) m;
          if (fd.init() != null) {
            fd = fd.init(null);
          }
          if (fd.flags().isFinal()) fd = fd.flags(fd.flags().clearFinal());

          stubmembers.add(fd);
        } else if (m instanceof MethodDecl) {
          MethodDecl pd = (MethodDecl) m;

          if (pd.body() != null) {
            pd = (MethodDecl) pd.body(null);

            if (!pd.flags().isNative())
              pd = (MethodDecl) pd.flags(pd.flags().Native());
          }
          stubmembers.add(pd);
        } else if (m instanceof ConstructorDecl) {
          //XXX: Special case hacks
          // add additional checks if new code needs to
          // instantiate FabIL classes.
          // Ideally, we would do this for non-fabric classes in the
          // runtime, but (a) we don't have type info yet and
          // (b) FabricClassLoader may be the only bootstrap class that
          // isn't a Fabric class.
          if (!cd.name().equals("FabricClassLoader")) continue;
          ConstructorDecl pd = (ConstructorDecl) m;
          if (pd.body() != null) {
            pd =
                (ConstructorDecl) pd
                .body(nf.Block(Position.compilerGenerated()));

          }
          stubmembers.add(pd);
        }
      }
      return ((ClassBody) n).members(stubmembers);
    }
    return n;
  }
}
