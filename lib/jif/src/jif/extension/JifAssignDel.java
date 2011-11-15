package jif.extension;

import java.util.LinkedList;
import java.util.List;

import jif.ast.JifUtil;
import jif.visit.IntegerBoundsChecker.Interval;
import polyglot.ast.Assign;
import polyglot.types.TypeSystem;

public class JifAssignDel extends JifJL_c
{
    public List throwTypes(TypeSystem ts) {
        List l = new LinkedList();

        if (throwsArithmeticException()) {
          l.add(ts.ArithmeticException());
        }

        return l;
    }
    
    public boolean throwsArithmeticException() {
        Assign a = (Assign)this.node();
        if (a.operator() == Assign.DIV_ASSIGN || a.operator() == Assign.MOD_ASSIGN) {
            // it's a divide or mod operation.
            if (a.right().type().isFloat() || a.right().type().isDouble()) {
                // floats and doubles don't throw 
                return false;
            }
            if (a.right().isConstant()) {
                // is it a non-zero constant?
                Object o = a.right().constantValue();
                if (o instanceof Number && ((Number)o).longValue() != 0) {
                    return false;
                }
            }
            if (((JifExprExt) JifUtil.jifExt(a.right())).getNumericBounds() != null) {
                Interval i = ((JifExprExt) JifUtil.jifExt(a.right())).getNumericBounds();
                if ((i.getLower() != null && i.getLower().longValue() > 0) ||
                        (i.getUpper() != null && i.getUpper().longValue() < 0)) {
                    // the right operand is non zero
                    return false;                    
                }
            }
            return true;
        }
        return false;
    }    

}
