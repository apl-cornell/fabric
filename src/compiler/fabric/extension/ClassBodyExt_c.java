package fabric.extension;

import java.util.List;

import jif.ast.JifUtil;

import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;

public class ClassBodyExt_c extends NodeExt_c {

  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    ClassBodyJifExt_c ext = (ClassBodyJifExt_c) JifUtil.jifExt(n);
    List<ClassMember> remote_wrappers = visitList(ext.remoteWrappers(), v);
    ext.setRemoteWrappers(remote_wrappers);
    return n;
  }
}
