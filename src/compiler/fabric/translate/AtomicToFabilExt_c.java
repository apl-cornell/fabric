package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.Atomic;
import polyglot.ast.Node;
import jif.translate.BlockToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class AtomicToFabilExt_c extends BlockToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) {
    Atomic b = (Atomic) node();
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(), b.statements());
  }
}
