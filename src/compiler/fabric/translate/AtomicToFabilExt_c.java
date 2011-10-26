package fabric.translate;

import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabric.ast.Atomic;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import jif.translate.BlockToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class AtomicToFabilExt_c extends BlockToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) {
    Atomic b = (Atomic) node();
    @SuppressWarnings("unchecked")
    List<Stmt> stmts = b.statements();
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(), stmts);
  }
}
