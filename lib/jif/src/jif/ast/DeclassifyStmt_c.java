package jif.ast;

import polyglot.ast.Stmt;
import polyglot.util.Position;

/** An implementation of the <code>DeclassifyStmt</code> interface.
 */
public class DeclassifyStmt_c extends DowngradeStmt_c implements DeclassifyStmt
{
    public DeclassifyStmt_c(Position pos, LabelNode bound,
                            LabelNode label, Stmt body) {
	super(pos, bound, label, body);
    }

    public String downgradeKind() {
        return "declassify";
    }
}
