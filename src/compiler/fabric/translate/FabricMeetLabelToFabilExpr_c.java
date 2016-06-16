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
  public Expr toJava(Label label, JifToJavaRewriter rw, Expr thisQualifier,
      boolean simplify) throws SemanticException {
    MeetLabel L = (MeetLabel) label;

    if (L.meetComponents().size() == 1) {
      return rw.labelToJava(L.meetComponents().iterator().next(), thisQualifier,
          simplify);
    }

    // Never simplify if translating a meet label in the constructor of a
    // principal class. This avoids some run-time bootstrapping issues.
    if (rw.context().currentCode() instanceof ConstructorInstance
        && rw.currentClass().isSubtype(rw.jif_ts().PrincipalClass()))
      simplify = false;

    LinkedList<Label> l = new LinkedList<>(L.meetComponents());
    Iterator<Label> iter = l.iterator();
    Label head = iter.next();
    Expr e = rw.labelToJava(head, thisQualifier, simplify);
    while (iter.hasNext()) {
      head = iter.next();
      Expr f = rw.labelToJava(head, thisQualifier, simplify);
      Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
      e = rw.qq().parseExpr("%E.meet(%E, %E, %E)", e, loc, f,
          rw.java_nf().BooleanLit(Position.compilerGenerated(), simplify));

    }
    return e;
  }

}
