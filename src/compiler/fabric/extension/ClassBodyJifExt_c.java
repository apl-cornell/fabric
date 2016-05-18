package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.types.SilenceableSolverGLB;

import jif.extension.JifClassBodyExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.visit.LabelChecker;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ClassBodyJifExt_c extends JifClassBodyExt {
  protected List<ClassMember> remote_wrappers;

  public ClassBodyJifExt_c(ToJavaExt toJava) {
    super(toJava);
    this.remote_wrappers = new ArrayList<>();
  }

  @Override
  public Node labelCheck(LabelChecker lc) {
    ClassBody n = (ClassBody) node();

    JifTypeSystem jts = lc.typeSystem();

    JifContext A = lc.context();
    A = (JifContext) n.del().enterScope(A);
    A.setCurrentCodePCBound(jts.notTaken());
    lc = lc.context(A);

    List<ClassMember> members = new ArrayList<>();
    // label check each member, but mute reporting of errors on
    // remote wrappers.
    for (ClassMember cm : n.members()) {
      try {
        ClassMember toAdd = (ClassMember) lc.context(A).labelCheck(cm);
        if (toAdd != null) {
          members.add(toAdd);
        }
      } catch (SemanticException e) {
        // report it and keep going.
        lc.reportSemanticException(e);
      }
    }

    List<ClassMember> new_wrappers = new ArrayList<>();
    SilenceableSolverGLB.mute(true);
    for (ClassMember cm : remoteWrappers()) {
      try {
        ClassMember toAdd = (ClassMember) lc.context(A).labelCheck(cm);
        if (toAdd != null) {
          new_wrappers.add(toAdd);
        }
      } catch (SemanticException e) {
        // report it and keep going.
        lc.reportSemanticException(e);
      }
    }
    SilenceableSolverGLB.mute(false);

    setRemoteWrappers(new_wrappers);
    return n.members(members);
  }

  public List<ClassMember> remoteWrappers() {
    return remote_wrappers;
  }

  public void setRemoteWrappers(List<ClassMember> remote_wrappers) {
    this.remote_wrappers = remote_wrappers;
  }
}
