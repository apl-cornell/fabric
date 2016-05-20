package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import fabric.visit.FabricToFabilRewriter;
import jif.translate.JifToJavaRewriter;
import jif.translate.MeetLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import polyglot.ast.Expr;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricMeetLabelToFabilExpr_c extends MeetLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw, Expr qualifier)
      throws SemanticException {
    MeetLabel L = (MeetLabel) label;

    if (L.meetComponents().size() == 1) {
      return rw.labelToJava(L.meetComponents().iterator().next(), qualifier);
    }

    boolean simplify = true;
    if (rw.context().currentCode() instanceof ConstructorInstance
        && rw.currentClass().isSubtype(rw.jif_ts().PrincipalClass()))
      simplify = false;

    LinkedList<Label> l = new LinkedList<>(L.meetComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head, qualifier);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head, qualifier);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e = rw.qq().parseExpr("%E.meet(%E, %E, %E)", e, loc, f,
          rw.java_nf().BooleanLit(Position.compilerGenerated(), simplify));

    }
    return e;
  }

}
