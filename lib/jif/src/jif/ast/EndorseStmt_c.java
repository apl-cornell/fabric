package jif.ast;

import polyglot.ast.Stmt;
import polyglot.util.Position;

/** An implementation of the <code>EndorseStmt</code> interface.
 */
public class EndorseStmt_c extends DowngradeStmt_c implements EndorseStmt
{
    public EndorseStmt_c(Position pos, LabelNode bound,
                            LabelNode label, Stmt body) {
	super(pos, bound, label, body);
    }

    public String downgradeKind() {
        return "endorse";
    }

}
