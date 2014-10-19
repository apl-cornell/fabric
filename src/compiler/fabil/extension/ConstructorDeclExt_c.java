package fabil.extension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends ClassMemberExt_c {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // TODO add Store parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = (ConstructorDecl) node.name("_Impl");
    return Collections.singletonList((ClassMember) node);
  }

  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to the constructor declaration if the
    // containing class is a Fabric class.
    ConstructorDecl decl = (ConstructorDecl) node();
    FabILTypeSystem ts = pr.typeSystem();

    // Ensure that we're translating a Fabric type.
    Type containerType = decl.constructorInstance().container();
    if (!ts.isFabricClass(containerType)) return decl;

    NodeFactory nf = pr.nodeFactory();
    Position pos = Position.compilerGenerated();
    List<Formal> formals = new LinkedList<>(decl.formals());
    formals.add(
        0,
        nf.Formal(pos, Flags.NONE,
            nf.TypeNodeFromQualifiedName(pos, "fabric.worker.Store"),
            nf.Id(pos, "$location")));
    return decl.formals(formals);
  }

}
