package jpa2fab.ext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Ext;
import polyglot.ast.Formal;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.translate.ExtensionRewriter;
import polyglot.translate.ext.ClassDeclToExt_c;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class ClassDeclToFabIL_c extends ClassDeclToExt_c implements Ext {

  @Override
  public NodeVisitor toExtEnter(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;
    ClassDecl cd = (ClassDecl) node();
    JL5ParsedClassType type = (JL5ParsedClassType) cd.type();
    // entities should extend other entities or at least mapped superclasses,
    // otherwise we're asking for trouble...
    j2f.checkInheritence(type);

    if (j2f.isPersistent(type)) j2f.inEntity(true);
    return super.toExtEnter(rw);
  }

  @Override
  public Node toExt(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;
    ClassDecl cd = (ClassDecl) node();
    TypeNode superClass = cd.superClass();
    if (!superClass.type().equals(rw.from_ts().Object())
        && !j2f.isPersistent(cd.type()) && !cd.type().flags().isInterface()) {
      // not a persistent type. extend java.lang.Object explicitly.            
      superClass =
          rw.to_nf().CanonicalTypeNode(cd.position(), rw.to_ts().Object());
    }
    ClassBody body = cd.body();
    List<ClassMember> members = new ArrayList<ClassMember>();
    members.add(j2f.createStoreFieldDecl());
    members.add(produceDefaultConstructor(rw));
    members.addAll(body.members());
    body = body.members(members);

    return rw.to_nf().ClassDecl(cd.position(), cd.flags(), cd.id(), superClass,
        cd.interfaces(), body);
  }

  protected ClassMember produceDefaultConstructor(ExtensionRewriter rw) {
    ClassDecl cd = (ClassDecl) node();
    ClassType ct = cd.type();
    Id name = rw.to_nf().Id(Position.compilerGenerated(), ct.name());
    return rw.to_nf().ConstructorDecl(
        Position.compilerGenerated(),
        Flags.PUBLIC,
        name,
        Collections.<Formal> emptyList(),
        Collections.<TypeNode> emptyList(),
        rw.to_nf().Block(Position.compilerGenerated(),
            Collections.<Stmt> emptyList()));
  }

}
