package jif.ast;

import polyglot.ast.Expr;

/** 
 * Superinterface for downgrade expressions.
 */
public interface DowngradeExpr extends Expr
{
    LabelNode label();
    DowngradeExpr label(LabelNode label);

    Expr expr();
    DowngradeExpr expr(Expr expr);
    
    LabelNode bound();
    DowngradeExpr bound(LabelNode label);

    /**
     * @return Name of the kind of downgrade, e.g. "declassify" or "endorse"
     */
    String downgradeKind();
}
