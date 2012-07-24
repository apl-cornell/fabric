package fabric.translate;

import java.util.List;

import jif.translate.BlockToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import fabil.ast.FabILNodeFactory;
import fabric.ast.Atomic;

public class AtomicToFabilExt_c extends BlockToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) {
    Atomic b = (Atomic) node();
    List<Stmt> stmts = b.statements();
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(), stmts);
  }
}
