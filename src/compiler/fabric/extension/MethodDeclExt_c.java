package fabric.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.Block;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.MethodDecl_c;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class MethodDeclExt_c extends FabricExt_c implements ClassMemberExt {

  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.singletonList((ClassMember) node());
  }

  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl_c node = (MethodDecl_c) node();
    node = (MethodDecl_c) node.body(null);
    
    return Collections.singletonList((ClassMember) node);
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl_c node = (MethodDecl_c) node();
    QQ qq = pr.qq();
    NodeFactory nf = pr.nodeFactory();
    String ret = node.returnType().type().isVoid() ? "" : "return";
    List<Expr> params = new ArrayList<Expr>();
    for (Object o : node.formals()) {
      Formal f = (Formal) o;
      params.add(nf.Local(Position.compilerGenerated(), f.id()));
    }
    
    String type = parent.id() + ".$Impl";
    Block body = (Block) qq.parseStmt("{ "+ret+" (("+type+") fetch())."+node.name()+"(%LE); }", (Object)params);
    return Collections.singletonList((ClassMember) node.body(body));
  }
}
