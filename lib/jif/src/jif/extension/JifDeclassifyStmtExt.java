package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/** The Jif extension of the <code>DeclassifyStmt</code> node. 
 * 
 *  @see jif.ast.DeclassifyStmt
 */
public class JifDeclassifyStmtExt extends JifDowngradeStmtExt
{
    public JifDeclassifyStmtExt(ToJavaExt toJava) {
        super(toJava);
    }

    protected void checkOneDimenOnly(LabelChecker lc, 
            final JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos) 
    throws SemanticException {
        JifDeclassifyExprExt.checkOneDimen(lc, A, labelFrom, labelTo, pos, false);
    }

    protected void checkAuthority(LabelChecker lc, 
                                  final JifContext A,
                                  Label labelFrom, 
                                  Label labelTo, Position pos) 
            throws SemanticException {
        JifDeclassifyExprExt.checkAuth(lc, A, labelFrom, labelTo, pos, false);
    }

    protected void checkRobustness(LabelChecker lc, 
                                   JifContext A,
                                   Label labelFrom, 
                                   Label labelTo, Position pos) 
    throws SemanticException {
        JifDeclassifyExprExt.checkRobustDecl(lc, A, labelFrom, labelTo, pos, false);
    }
}
