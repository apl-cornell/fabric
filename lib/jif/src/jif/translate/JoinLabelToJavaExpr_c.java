package jif.translate;

import java.util.Iterator;
import java.util.LinkedList;

import jif.types.label.*;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class JoinLabelToJavaExpr_c extends LabelToJavaExpr_c {
    public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
        JoinLabel L = (JoinLabel) label;

        if (L.joinComponents().size() == 1) {
            return rw.labelToJava((Label)L.joinComponents().iterator().next());
        }

        LinkedList l = new LinkedList(L.joinComponents());
        Iterator iter = l.iterator();            
        Label head = (Label)iter.next();
        Expr e = rw.labelToJava(head);
        while (iter.hasNext()) {
            head = (Label)iter.next();
            Expr f = rw.labelToJava(head);
            e = rw.qq().parseExpr("%E.join(%E)", e, f);
        }
        return e;            
    }
}
