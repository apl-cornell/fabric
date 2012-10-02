package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;
import polyglot.ast.Block;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.Try;
import polyglot.qq.QQ;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.util.UniqueID;
import fabil.ast.FabILNodeFactory;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl) super.toJava(rw);

    if (md.body() == null) {
      // abstract method
      return md;
    }

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    QQ qq = rw.qq();

    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
      If ifStmt = (If) md.body().statements().get(0);
      ifStmt =
          ifStmt
          .alternative(rw
              .qq()
              .parseStmt(
                  "throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));

      // Wrap the whole thing in a try-catch that prints out any thrown exceptions.
      String varName = UniqueID.newID("e");
      Try tryStmt =
          (Try) qq.parseStmt("try { %S } catch (java.lang.RuntimeException " + varName
              + ") { System.err.println(\"Caught exception while executing "
              + "remote call:\"); " + varName + ".printStackTrace(); throw "
              + varName + ";}",
              ifStmt);
      return md.body(nf.Block(Position.compilerGenerated(), tryStmt));
    }

    return md;
  }

  @Override
  protected Block guardWithConstraints(JifToJavaRewriter rw, Block b)
      throws SemanticException {
    boolean shouldGuard = false;
    if (shouldGuard) {
      b = super.guardWithConstraints(rw, b);
    }
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(),
        b.statements());
  }
}
