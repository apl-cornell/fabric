package fabric.ast;

import java.util.List;

import jif.ast.JifUtil;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabric.extension.ClassBodyJifExt_c;

public class ClassBody_c extends polyglot.ast.ClassBody_c {

  public ClassBody_c(Position pos, List<ClassMember> members) {
    super(pos, members);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    ClassBodyJifExt_c ext = (ClassBodyJifExt_c) JifUtil.jifExt(n);
    List<ClassMember> remote_wrappers = visitList(ext.remoteWrappers(), v);
    ext.setRemoteWrappers(remote_wrappers);
    return n;
  }
}
