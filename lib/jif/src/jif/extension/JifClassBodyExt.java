package jif.extension;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.visit.LabelChecker;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.ErrorInfo;
import polyglot.util.Position;

/** The extension of the <code>ClassBody</code> node. 
 * 
 *  @see polyglot.ast.ClassBody
 */
public class JifClassBodyExt extends Jif_c {
    public JifClassBodyExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) {
	ClassBody n = (ClassBody) node();

	JifTypeSystem jts = lc.typeSystem();

	JifContext A = lc.context();
        A = (JifContext) n.del().enterScope(A);
        A.setCurrentCodePCBound(jts.notTaken());
        lc = lc.context(A);

	//find all the final fields that have an initializer
        List members = new LinkedList();

	for (Iterator iter = n.members().iterator(); iter.hasNext(); ) {
	    try {
    	        ClassMember cm = (ClassMember) iter.next();
                members.add(lc.context(A).labelCheck(cm));
            }
            catch (SemanticException e) {
                // report it and keep going.
                lc.reportSemanticException(e);                
            }
	}

        return n.members(members);
    }
}
