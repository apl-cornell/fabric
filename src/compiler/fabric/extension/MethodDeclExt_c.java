package fabric.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class MethodDeclExt_c extends FabricExt_c {

  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.singletonList((ClassMember) node());
  }

  @SuppressWarnings("unchecked")
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl methodDecl = node();
    Flags flags = methodDecl.flags();
    
    // Don't include static methods in interfaces.
    if (flags.isStatic()) return Collections.EMPTY_LIST;
    
    // Clear the "final" flag.
    flags = flags.clear(Flags.FINAL);
    
    ClassMember result = (ClassMember) methodDecl.flags(flags).body(null);
    return Collections.singletonList(result);
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
    String callTarget = type;
    if (!node.flags().isStatic()) callTarget = "((" + type + ") fetch())";
    Block body =
        (Block) qq.parseStmt("{ " + ret + " " + callTarget + "." + node.name()
            + "(%LE); }", (Object) params);
    return Collections.singletonList((ClassMember) node.body(body));
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public MethodDecl node() {
    return (MethodDecl) super.node();
  }
}
