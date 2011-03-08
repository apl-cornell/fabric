package fabric.visit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import polyglot.ast.Block;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorCall;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.TypeSystem;
import polyglot.visit.NodeVisitor;

/**
 * Outputs a fabil skeleton for bootstrapping.
 */
public class FabILSkeletonCreator extends NodeVisitor {

  private Job job;
  private Set<String> classes;
  private Map<String,Set<String>> nestedClasses;

  public FabILSkeletonCreator(Job job, TypeSystem ts, NodeFactory nf) {
    this.job = job;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Node leave(Node parent, Node old, Node n, NodeVisitor v) {
    if(n instanceof ClassBody) {
      List<ClassMember> members = ((ClassBody) n).members();

      List<ClassMember> stubmembers = new LinkedList<ClassMember>();
      for(ClassMember m : members) {
        if(m instanceof ClassDecl) {
          stubmembers.add(m);
        } else if(m instanceof MethodDecl) {
          MethodDecl pd = (MethodDecl) m;
          if(pd.body() != null) {
            pd = (MethodDecl) pd.body(null);
            //Make this method native if not already
            if(!pd.flags().isNative())
              pd = pd.flags(pd.flags().Native());
          } 
          stubmembers.add(pd);
        } else if(m instanceof ConstructorDecl) {
          ConstructorDecl cd = (ConstructorDecl) m;
          Block b = cd.body();
          if (b != null && b.statements() != null
              && b.statements().get(0) instanceof ConstructorCall) {
            List l = new LinkedList();
            l.add(b.statements().get(0));
            cd = (ConstructorDecl) cd.body(b.statements(l));
          } else
            cd = (ConstructorDecl) cd.body(null);
          stubmembers.add(cd);          
        }
      }

      return ((ClassBody) n).members(stubmembers);
    } else
      return n;
  }
}
