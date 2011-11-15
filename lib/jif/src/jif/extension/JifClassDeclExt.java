package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.ast.JifClassDecl;
import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.ClassBody;
import polyglot.ast.Node;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;

/** The extension of the <code>JifClassDecl</code> node. 
 * 
 *  @see jif.ast.JifClassDecl
 */
public class JifClassDeclExt extends Jif_c {
    public JifClassDeclExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException {
	JifClassDecl n = (JifClassDecl) node();

	JifTypeSystem jts = lc.typeSystem();
	JifContext A = lc.jifContext();
        A = (JifContext)A.pushClass(n.type(), n.type());
        A = n.addParamsToContext(A);
        A = n.addAuthorityToContext(A);

        A.setCurrentCodePCBound(jts.notTaken());

	final JifParsedPolyType ct = (JifParsedPolyType) n.type();

	// let the label checker know that we are about to enter a class body
        lc.enteringClassDecl(ct);

	// Check the authority of the class against the superclass.
	if (ct.superType() instanceof JifClassType) {
            final JifClassType superType = (JifClassType) ct.superType();

            // construct a principal that represents the authority of ct
            Principal authPrincipal = lc.jifTypeSystem().conjunctivePrincipal(ct.position(), ct.authority());
            
            
	    for (Iterator i = superType.authority().iterator(); i.hasNext(); ) {
		final Principal pl = (Principal) i.next();

		// authPrincipal must actfor pl i.e., at least one
                // of the principals that have authorized ct must act for pl.
                lc.constrain(authPrincipal, 
                             PrincipalConstraint.ACTSFOR, 
                            pl, 
                           A.labelEnv(),
                           n.position(),
                           new ConstraintMessage() {
                    public String msg() {
                        return "Superclass " + superType + " requires " + ct + " to " +
                        "have the authority of principal " + pl;
                    }
                    public String detailMsg() {
                        return "The class " + superType + " has the authority of the " +
                        "principal " + pl + ". To extend this class, " + ct + 
                        " must also have the authority of " + pl + ".";
                    }
                });		
		
	    }
	}

        A = (JifContext) n.del().enterScope(A);
	
        lc = lc.context(A);
	                
        // label check class conformance
	labelCheckClassConformance(ct,lc);

	// label check the body
        ClassBody body = (ClassBody) lc.labelCheck(n.body());

        // let the label checker know that we have left the class body
        n = lc.leavingClassDecl((JifClassDecl)n.body(body));
        return n;
    }
    
    private void labelCheckClassConformance(JifParsedPolyType ct, LabelChecker lc) throws SemanticException {
        if (ct.flags().isInterface()) {
            // don't need to check interfaces            
            return;
        }
        
        JifTypeSystem ts = lc.typeSystem();

        // build up a list of superclasses and interfaces that ct 
        // extends/implements that may contain abstract methods that 
        // ct must define.
        List superInterfaces = ts.abstractSuperInterfaces(ct);

        // check each abstract method of the classes and interfaces in
        // superInterfaces
        for (Iterator i = superInterfaces.iterator(); i.hasNext(); ) {
            ReferenceType rt = (ReferenceType)i.next();
            for (Iterator j = rt.methods().iterator(); j.hasNext(); ) {
                JifMethodInstance mi = (JifMethodInstance)j.next();
                if (!mi.flags().isAbstract()) {
                    // the method isn't abstract, so ct doesn't have to
                    // implement it.
                    continue;
                }

                JifMethodInstance mj = (JifMethodInstance)ts.findImplementingMethod(ct, mi);
                if (mj != null && !ct.equals(mj.container()) && !ct.equals(mi.container())) {
                    try {
                        // check that mj can override mi, which
                        // includes access protection checks.
                        if (mj.canOverrideImpl(mi, true)) {
                            // passes the java checks, now perform the label checks                                        
                            lc.createOverrideHelper(mi, mj).checkOverride(lc);
                        }
                    }
                    catch (SemanticException e) {
                        // change the position of the semantic
                        // exception to be the class that we
                        // are checking.
                        throw new SemanticException(e.getMessage(),
                                                    ct.position());
                    }
                }
                else {
                    // the method implementation mj or mi was
                    // declared in ct. So other checks will take
                    // care of labelchecking
                }
            }
        }
    }

}
