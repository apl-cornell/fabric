package jif.extension;

import polyglot.ast.Initializer;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>Initializer</code> node. 
 * 
 *  @see polyglot.ast.Initializer
 */
public class JifInitializerDel extends JifJL_c
{
    public JifInitializerDel() {
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	Initializer ib = (Initializer) node();

        if (! ib.flags().isStatic()) {
	    throw new SemanticException(
		"Jif does not support non-static initalizer blocks.", ib.position());
	}

	return super.typeCheck(tc);
    }
}
