package jif.ast;

import polyglot.ast.CompoundStmt;
import polyglot.ast.Stmt;

/** 
 * Superinterface for downgrade statements.
 */
public interface DowngradeStmt extends CompoundStmt
{
    LabelNode label();
    DowngradeStmt label(LabelNode label);

    Stmt body();
    DowngradeStmt body(Stmt body);
    
    LabelNode bound();
    DowngradeStmt bound(LabelNode bound);
    
    /**
     * @return Name of the kind of downgrade, e.g. "declassify" or "endorse"
     */
    String downgradeKind();
}
