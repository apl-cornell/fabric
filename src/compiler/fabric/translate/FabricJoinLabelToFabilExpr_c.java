package fabric.translate;

import java.util.Iterator;
import java.util.LinkedList;

import fabric.visit.FabricToFabilRewriter;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.JoinLabelToJavaExpr_c;
import jif.types.label.JoinLabel;
import jif.types.label.Label;

public class FabricJoinLabelToFabilExpr_c extends JoinLabelToJavaExpr_c {
  @Override
  @SuppressWarnings("unchecked")
  public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
    JoinLabel L = (JoinLabel) label;

    if (L.joinComponents().size() == 1) {
        return rw.labelToJava(L.joinComponents().iterator().next());
    }

    LinkedList l = new LinkedList(L.joinComponents());
    Iterator iter = l.iterator();            
    Label head = (Label)iter.next();
    Expr e = rw.labelToJava(head);
    while (iter.hasNext()) {
        head = (Label)iter.next();
        Expr f = rw.labelToJava(head);
        Expr loc = ((FabricToFabilRewriter) rw).currentLocation();
        e = rw.qq().parseExpr("%E.join(%E, %E)", e, loc, f);
    }
    return e;            
}

}
