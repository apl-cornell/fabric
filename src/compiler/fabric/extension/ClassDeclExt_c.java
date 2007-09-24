package fabric.extension;

import java.lang.reflect.Method;
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
import polyglot.util.InternalCompilerError;

public class ClassDeclExt_c extends FabricExt_c implements ClassMemberExt {
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    ClassDecl node = (ClassDecl) node();
    ClassBody body = node.body();

    // TODO: static fields of interfaces
    if (node.flags().contains(Flags.INTERFACE)) return node;

    List<ClassMember> oldMembers = body.members();
    int size = oldMembers.size();

    List<ClassMember> ifaceMembers = new ArrayList<ClassMember>(size);
    List<ClassMember> proxyMembers = new ArrayList<ClassMember>(size);
    List<ClassMember> implMembers  = new ArrayList<ClassMember>(size);

    for (ClassMember m : oldMembers) {
      if (!(m.ext() instanceof ClassMemberExt))
        System.err.println(m.getClass());
      ClassMemberExt ext = (ClassMemberExt) m.ext();
      ifaceMembers.addAll(ext.interfaceMember(pr));
      proxyMembers.addAll(ext.proxyMember(pr));
       implMembers.addAll(ext.implMember(pr));
    }
    
    ifaceMembers = makePublic(ifaceMembers);
    proxyMembers = makePublic(proxyMembers);
     implMembers = makePublic(implMembers); 

    // TODO: abstract classes
    NodeFactory nf = pr.nodeFactory();
    QQ qq = pr.qq();

    // TODO: this is probably the wrong way to tell
    if (node.superClass() == null ||
        "java.lang.Object".equals(node.superClass().toString()))
      node = node.superClass(nf.CanonicalTypeNode(node().position(),
                                                  pr.typeSystem().FObject()));
    
    ClassDecl proxy =
        qq.parseDecl(
            "public static class $Proxy" +
            " extends " + node.superClass() + ".$Proxy" +
            " implements %T {%LM}", node.type(), proxyMembers);
    ClassDecl impl =
        qq.parseDecl(
            "public static class $Impl" +
            " extends " + node.superClass() + ".$Impl" +
            " implements %T {%LM}", node.type(), implMembers);
    ifaceMembers.add(proxy.type(node.type()));
    ifaceMembers.add(impl.type(node.type()));

    List<TypeNode> interfaces = new ArrayList<TypeNode>(node.interfaces());
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
    return Collections.singletonList((ClassMember) node());
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr) {
    return Collections.emptyList();
  }

  public List<ClassMember> makePublic(List<ClassMember> members) {
    List<ClassMember> result = new ArrayList<ClassMember> (members.size());
    for (ClassMember m : members) {
      try {
        // Note: this is an ugly hack to get around the fact that there's no
        // flags() method in ClassMember but there is one in each subclass of it.
        Method getFlags = m.getClass().getMethod("flags");
        Method setFlags = m.getClass().getMethod("flags", Flags.class);
        Flags f = (Flags) getFlags.invoke(m);
        // Clear all access flags, then set public
        f = f.Package().Public();
        result.add((ClassMember) setFlags.invoke(m, f));
      } catch(Exception e) {
        throw new InternalCompilerError(e);
      }
    }
    return result;
  }
}
