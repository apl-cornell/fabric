package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricConstructorDecl;

import jif.translate.ConstructorDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class ConstructorDeclToFabilExt_c extends ConstructorDeclToJavaExt_c {
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw)
      throws SemanticException {
    FabricConstructorDecl n = (FabricConstructorDecl) node();
    // Bypass startLabel, returnLabel and constraints.
    return ((JifToJavaRewriter) super.toJavaEnter(rw))
        .bypass(n.beginConflictLabel()).bypass(n.endConflictLabel());
  }

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Node n = super.toJava(rw);
    if (n instanceof MethodDecl) {
      // The constructor declaration has been rewritten to a method declaration.
      MethodDecl md = (MethodDecl) n;
      if (md.body() != null) {
        FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
        // FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();

        List<Stmt> stmts = new ArrayList<>(md.body().statements().size() + 1);

        // TypeNode worker = nf.CanonicalTypeNode(Position.compilerGenerated(),
        // ts.Worker());
        // stmts.add(nf.LocalDecl(Position.compilerGenerated(),
        // Flags.FINAL,
        // worker,
        // nf.Id(Position.compilerGenerated(),
        // "worker$"),
        // nf.Call(Position.compilerGenerated(),
        // worker,
        // nf.Id(Position.compilerGenerated(),
        // "getWorker"))));
        stmts.addAll(md.body().statements());

        return md.body(nf.Block(md.body().position(), stmts));
      }
    }
    return n;
  }
}
