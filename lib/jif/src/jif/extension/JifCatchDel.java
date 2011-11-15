package jif.extension;

import jif.types.JifTypeSystem;
import jif.types.SemanticDetailedException;
import polyglot.ast.Catch;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>Catch</code> node.
 *
 *  @see polyglot.ast.Catch_c
 */
public class JifCatchDel extends JifJL_c 
{
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        Catch c = (Catch)this.node();

        JifTypeSystem jts = (JifTypeSystem)tc.typeSystem();
        Type catchType = jts.unlabel(c.catchType());
        if (jts.isSubtype(catchType, jts.Error())) {
            throw new SemanticDetailedException("Cannot catch exceptions of type " + catchType.toClass().fullName() + ".",
                                                "Subclasses of java.lang.Error can not be caught in Jif " +
                                                "programs, as the Jif compiler treats Errors as unchecked exceptions, and " +
                                                "does not track the information flow that may arise from raising " +
                                                "and catching them.",
                                                c.position());
        }
        if (jts.Throwable().equals(catchType)) {
            throw new SemanticDetailedException("Cannot catch exceptions of type " + catchType.toClass().fullName() + ".",
                                                "Subclasses of java.lang.Error can not be caught in Jif " +
                                                "programs, as the Jif compiler treats Errors as unchecked exceptions, and " +
                                                "does not track the information flow that may arise from raising " +
                                                "and catching them. Thus, java.lang.Throwable cannot be caught.",
                                                c.position());
        }

        return super.typeCheck(tc);
    }
    
}
