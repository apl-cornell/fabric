package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import fabric.visit.FabricToFabilRewriter;
import jif.translate.JifToJavaRewriter;
import jif.translate.JoinLabelToJavaExpr_c;
import jif.types.label.JoinLabel;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricJoinLabelToFabilExpr_c extends JoinLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw, Expr thisQualifier)
      throws SemanticException {
    JoinLabel L = (JoinLabel) label;

    if (L.joinComponents().size() == 1) {
      return rw.labelToJava(L.joinComponents().iterator().next(), thisQualifier);
    }

    boolean simplify = true;
    if (rw.context().currentCode() instanceof ConstructorInstance
        && rw.currentClass().isSubtype(rw.jif_ts().PrincipalClass()))
      simplify = false;

    LinkedList<Label> l = new LinkedList<>(L.joinComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head, thisQualifier);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head, thisQualifier);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e = rw.qq().parseExpr("%E.join(%E, %E, %E)", e, loc, f,
          rw.java_nf().BooleanLit(Position.compilerGenerated(), simplify));
    }
    return e;
  }
}
