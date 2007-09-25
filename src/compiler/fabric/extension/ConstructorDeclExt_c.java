package fabric.extension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import diaspora.visit.DObjectRewriter;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Flags;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends FabricExt_c implements ClassMemberExt {

  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // TODO add Core parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = node.name("$Impl");
    return Collections.singletonList((ClassMember) node);
  }

  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    return Collections.emptyList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to the constructor declaration if the
    // containing class is a DObject.
    ConstructorDecl decl = (ConstructorDecl) node();
    NodeFactory nf = pr.nodeFactory();

    Position pos = Position.compilerGenerated();
    List<Formal> formals = new LinkedList<Formal>(decl.formals());
    formals.add(0, nf.Formal(pos, Flags.NONE, nf.TypeNodeFromQualifiedName(pos,
        "fabric.client.Core"), nf.Id(pos, "$location")));
    return decl.formals(formals);
  }

}
