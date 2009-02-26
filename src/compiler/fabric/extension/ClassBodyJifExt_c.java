package fabric.extension;

import java.util.LinkedList;
import java.util.List;

import fabric.types.SilenceableSolverGLB;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.extension.JifClassBodyExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.visit.LabelChecker;

public class ClassBodyJifExt_c extends JifClassBodyExt {
  public ClassBodyJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) {
    ClassBody n = (ClassBody) node();

    JifTypeSystem jts = lc.typeSystem();

    JifContext A = lc.context();
    A = (JifContext) n.del().enterScope(A);
    A.setCurrentCodePCBound(jts.notTaken());
    lc = lc.context(A);

//    SilenceableSolverGLB solver = (SilenceableSolverGLB)lc.solver();

    // find all the final fields that have an initializer
    List<ClassMember> members = new LinkedList<ClassMember>();

    for (ClassMember cm : (List<ClassMember>)n.members()) {
      boolean isRemoteWrapper = false;
      if (cm instanceof MethodDecl) {
        MethodDecl md = (MethodDecl)cm;
        isRemoteWrapper = md.name().endsWith("_remote");
        SilenceableSolverGLB.mute(isRemoteWrapper);
      }
      try {
        ClassMember toAdd = (ClassMember)lc.context(A).labelCheck(cm);
        if (toAdd != null) {
//          if (isRemoteWrapper) {
//            System.err.println("one!");
//          }
          members.add(toAdd);
        }
      } 
      catch (SemanticException e) {
        // report it and keep going.
        lc.reportSemanticException(e);
      }
      SilenceableSolverGLB.mute(false);
    }

    return n.members(members);
  }
}
