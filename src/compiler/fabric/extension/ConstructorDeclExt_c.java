package fabric.extension;

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
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends ClassMemberExt_c {
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ClassMemberExt#implMember(fabric.visit.ProxyRewriter,
   *      polyglot.ast.ClassDecl)
   */
  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // TODO add Core parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = node.name("$Impl");
    return Collections.singletonList((ClassMember) node);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to the constructor declaration if the
    // containing class is a Fabric class.
    ConstructorDecl decl = (ConstructorDecl) node();
    FabricTypeSystem ts = pr.typeSystem();

    // Ensure that we're translating a Fabric type.
    Type containerType = decl.constructorInstance().container();
    if (!ts.isFabricClass(containerType)) return decl;

    NodeFactory nf = pr.nodeFactory();
    Position pos = Position.compilerGenerated();
    List<Formal> formals = new LinkedList<Formal>(decl.formals());
    formals.add(0, nf.Formal(pos, Flags.NONE, nf.TypeNodeFromQualifiedName(pos,
        "fabric.client.Core"), nf.Id(pos, "$location")));
    formals.add(1, nf.Formal(pos, Flags.NONE, nf.TypeNodeFromQualifiedName(pos,
        "fabric.lang.auth.Label"), nf.Id(pos, "$label")));
    return decl.formals(formals);
  }

}
