package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import jif.translate.JifToJavaRewriter;
import jif.translate.MeetLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.MeetLabel;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import fabric.visit.FabricToFabilRewriter;

public class FabricMeetLabelToFabilExpr_c extends MeetLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {
    MeetLabel L = (MeetLabel) label;

    if (L.meetComponents().size() == 1) {
      return rw.labelToJava(L.meetComponents().iterator().next());
    }

    LinkedList<Label> l = new LinkedList<Label>(L.meetComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e = rw.qq().parseExpr("%E.meet(%E, %E)", e, loc, f);
    }
    return e;
  }

}
