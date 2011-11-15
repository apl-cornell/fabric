package jif.extension;

import jif.visit.LabelChecker;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

/** The root of all kinds of Jif extensions for statements. 
 *  It provides a generic <node>labelCheck</code> method, which
 *  will invoke the <ndoe>labelCheckStmt</code> methods provided
 *  by the subclasses of this class. 
 */
public interface JifStmtExt extends Ext
{
    public JifStmtExt stmtDel();
    public JifStmtExt stmtDel(JifStmtExt stmtDel);

    public Node labelCheckStmt(LabelChecker lc)
	throws SemanticException;
}
