package fabric.ast;

import java.util.List;

import jif.ast.JifUtil;
import polyglot.ast.ClassBody_c;
import polyglot.ast.ClassMember;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabric.extension.ClassBodyJifExt_c;

// XXX should be replaced with extension
@Deprecated
public class FabricClassBody_c extends ClassBody_c {

//  @Deprecated
  public FabricClassBody_c(Position pos, List<ClassMember> members) {
    this(pos, members, null);
  }

  public FabricClassBody_c(Position pos, List<ClassMember> members, Ext ext) {
    super(pos, members, ext);
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    ClassBodyJifExt_c ext = (ClassBodyJifExt_c) JifUtil.jifExt(n);
    List<ClassMember> remote_wrappers = visitList(ext.remoteWrappers(), v);
    ext.setRemoteWrappers(remote_wrappers);
    return n;
  }

}
