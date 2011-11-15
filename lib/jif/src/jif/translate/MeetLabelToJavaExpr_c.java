package jif.translate;

import java.util.Iterator;
import java.util.LinkedList;

import jif.types.label.Label;
import jif.types.label.MeetLabel;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class MeetLabelToJavaExpr_c extends LabelToJavaExpr_c {
    public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
        MeetLabel L = (MeetLabel) label;

        if (L.meetComponents().size() == 1) {
            return rw.labelToJava((Label)L.meetComponents().iterator().next());
        }

        LinkedList l = new LinkedList(L.meetComponents());
        Iterator iter = l.iterator();            
        Label head = (Label)iter.next();
        Expr e = rw.labelToJava(head);
        while (iter.hasNext()) {
            head = (Label)iter.next();
            Expr f = rw.labelToJava(head);
            e = rw.qq().parseExpr("%E.meet(%E)", e, f);
        }
        return e;            
    }
}
