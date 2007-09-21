package fabric.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fabric.visit.ProxyRewriter;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.Flags;

public class ClassDeclExt_c extends FabricExt_c implements ClassMemberExt {
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    ClassDecl node = (ClassDecl) node();
    ClassBody body = node.body();

    // TODO: static fields of interfaces
    if (node.flags().contains(Flags.INTERFACE))
      return node;
    
    List<ClassMember> oldMembers = body.members();
    int               size       = oldMembers.size();
    
    List<ClassMember> ifaceMembers = new ArrayList<ClassMember> (size);
    List<ClassMember> proxyMembers = new ArrayList<ClassMember> (size);
    List<ClassMember>  implMembers = new ArrayList<ClassMember> (size);
    
    for (ClassMember m : oldMembers) {
      if (!(m.ext() instanceof ClassMemberExt))
        System.err.println(m.getClass());
      ClassMemberExt ext = (ClassMemberExt) m.ext();
      ifaceMembers.addAll(ext.interfaceMember(pr));
      proxyMembers.addAll(ext.proxyMember(pr));
       implMembers.addAll(ext.implMember(pr));
    }
    
    // TODO: abstract classes
    NodeFactory nf    = pr.nodeFactory();
    QQ          qq    = pr.qq();
    
    if (node.superClass() == null)
      node = node.superClass(nf.CanonicalTypeNode(node().position(), pr.typeSystem().FObject()));
    ClassDecl proxy = qq.parseDecl(
        "public static class $Proxy implements %T {" +
        "  %LM;" +
        "  protected $Impl fetch() {return ($Impl) super.fetch();}" +
        "}",
        node.type(), proxyMembers);
    ClassDecl impl = qq.parseDecl(
        "public static class $Impl  implements %T {%LM}",
        node.type(), implMembers);
    ifaceMembers.add(proxy.type(node.type()));
    ifaceMembers.add(impl.type(node.type()));

    List<TypeNode> interfaces = new ArrayList<TypeNode> (node.interfaces());
    interfaces.add(node.superClass());
    node = node.interfaces(interfaces);
    node = node.superClass(null);
    node = node.body(nf.ClassBody(node.position(), ifaceMembers));
    node = node.flags(node.flags().set(Flags.INTERFACE));
    
    return node;
  }

  public List<ClassMember> implMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

  public List<ClassMember> interfaceMember(ProxyRewriter pr) {
    return Collections.singletonList((ClassMember)node());
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

}
