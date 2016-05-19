package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.JifToJavaRewriter;
import jif.translate.TryToJavaExt_c;

import polyglot.ast.Block;
import polyglot.ast.Node;
import polyglot.ast.Try;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class TryToFabilExt_c extends TryToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    Try orig = (Try) node();
    Try rewritten = (Try) super.toJava(rw);

    // Stage try block
    FabricStagingExt fseTry = FabricUtil.fabricStagingExt(origTry);
    rewritten = rewritten.tryBlock(frw.java_nf().Block(rewritten.position(),
          fseTry.stageCheck(frw, orig, rewritten.tryBlock())));

    // Staging for each catch handled in the Catch rewriting.

    // Stage finally block
    if (origFinally != null) {
      FabricStagingExt fseFinally = FabricUtil.fabricStagingExt(origFinally);
      rewritten =
        rewritten.finallyBlock(frw.java_nf().Block(rewritten.position(),
            fseFinally.stageCheck(frw, orig, rewritten.finallyBlock())));
    }

    // Stage this node 
    FabricStagingExt fse = FabricUtil.fabricStagingExt(orig);
    return fse.stageCheck(frw, orig, rewritten);
  }

  protected Block origTry;
  protected Block origFinally;

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw)
      throws SemanticException {
    // Save the original try and finally
    origTry = ((Try) node()).tryBlock();
    origFinally = ((Try) node()).finallyBlock();
    return super.toJavaEnter(rw);
  }
}
