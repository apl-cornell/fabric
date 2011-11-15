package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.ast.JifClassDecl;
import jif.types.*;
import polyglot.ast.Node;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.visit.TypeChecker;

/** The delegate of the <code>JifClassDecl</code> node.
 *
 *  @see jif.ast.JifClassDecl
 */
public class JifClassDeclDel extends JifJL_c {
    public JifClassDeclDel() {
    }

    /**
     * @see polyglot.ast.JL_c#typeCheck(polyglot.visit.TypeChecker)
     */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JifClassDecl cd = (JifClassDecl)this.node();
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();


        // check that there are not two static methods called "main"
        MethodInstance staticMain = null;
        List mains = cd.type().methodsNamed("main");
        
        for (Iterator iter = mains.iterator(); iter.hasNext(); ) {
            MethodInstance mi = (MethodInstance)iter.next();
            if (mi.flags().isStatic()) {
                if (staticMain != null) {
                    // this is the second static method named main.
                    // we don't like this.
                    throw new SemanticDetailedException("Only one static " +
                        "method named \"main\" allowed per class.", 
                        "Two main methods can be used to invoke a Jif " + 
                        "program: public static main(String[]), or public " +
                        "static main(principal, String[]). Any class may " +
                        "have at most one static method named \"main\".", 
                        mi.position());
                }

                staticMain = mi;
            }
        }

        // check that if this class extends Throwable, then it does not have
        // any parameters.
        if (cd.type().isSubtype(ts.Throwable())) {
            JifParsedPolyType jppt = (JifParsedPolyType)cd.type();
            if (jppt.params().size() > 0) {
                throw new SemanticDetailedException("Subclasses of " +
                    "java.lang.Throwable can not have parameters.",
                    "Subclasses of java.lang.Throwable can not have any parameters, " +
                    "since Jif does not currently support catch blocks for " +
                    "parameterized subclasses of Throwable.",
                    jppt.position());
            }
        }

        return super.typeCheck(tc);
    }

}
