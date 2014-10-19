package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifUtil;
import jif.translate.ClassBodyToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.extension.ClassBodyJifExt_c;

public class ClassBodyToFabilExt_c extends ClassBodyToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    ClassBody cb = (ClassBody) node();
    List<ClassMember> members = new ArrayList<>(cb.members());
    ClassBodyJifExt_c cb_ext = (ClassBodyJifExt_c) JifUtil.jifExt(cb);
    List<ClassMember> remote_wrappers = cb_ext.remoteWrappers();
    members.addAll(remote_wrappers);
    return rw.java_nf().ClassBody(cb.position(), members);
  }
}
