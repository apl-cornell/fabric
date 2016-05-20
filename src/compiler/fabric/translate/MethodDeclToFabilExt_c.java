package fabric.translate;

import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricMethodDecl;
import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;

import polyglot.ast.Block;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw)
      throws SemanticException {
    FabricMethodDecl n = (FabricMethodDecl) node();
    // Bypass startLabel, returnLabel and constraints.
    return ((JifToJavaRewriter) super.toJavaEnter(rw))
        .bypass(n.beginConflictLabel()).bypass(n.endConflictLabel());
  }

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl) super.toJava(rw);

    if (md.body() == null) {
      // abstract method
      return md;
    }

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();

    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
      If ifStmt = (If) md.body().statements().get(0);
      ifStmt = ifStmt.alternative(rw.qq().parseStmt(
          "throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));
      md = (MethodDecl) md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }

    // Staging
    FabricStagingExt fse = FabricUtil.fabricStagingExt(node());
    return fse.stageCheck((FabricToFabilRewriter) rw, node(), md);
  }

  @Override
  protected Block guardWithConstraints(JifToJavaRewriter rw, Block b)
      throws SemanticException {
    Block guarded = super.guardWithConstraints(rw, b);

    // Do nothing if no guard was added.
    if (guarded == b) return b;

    // Wrap in atomic.
    return ((FabILNodeFactory) rw.java_nf())
        .Atomic(Position.compilerGenerated(), guarded.statements());
  }
}
