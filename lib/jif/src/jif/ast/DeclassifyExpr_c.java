package jif.ast;

import polyglot.ast.Expr;
import polyglot.util.Position;

/** An implementation of the <code>DeclassifyExpr</code> interface.
 */
public class DeclassifyExpr_c extends DowngradeExpr_c implements DeclassifyExpr
{
    public DeclassifyExpr_c(Position pos, Expr expr, 
                            LabelNode bound, LabelNode label) {
        super(pos, expr, bound, label);

    }

    public String downgradeKind() {
        return "declassify";
    }
}