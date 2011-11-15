package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.ast.JifProcedureDecl;
import jif.types.*;
import jif.types.principal.Principal;
import polyglot.ast.*;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

/** The Jif delegate the <code>ProcedureDecl</code> node. 
 * 
 *  @see  polyglot.ast.ProcedureDecl
 */
public class JifProcedureDeclDel extends JifJL_c
{
    public JifProcedureDeclDel() { }


    // add the formals to the context before visiting the formals
    public Context enterScope(Context c) {
        c = super.enterScope(c);
        addFormalsToScope(c);
        return c;
    }
    protected void addFormalsToScope(Context c) {
        ProcedureDecl pd = (ProcedureDecl) node();
        for (Iterator i = pd.formals().iterator(); i.hasNext(); ) {
            Formal f = (Formal) i.next();
            c.addVariable(f.localInstance());
        }        
    }
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JifProcedureDecl pd = (JifProcedureDecl)super.typeCheck(tc);
        JifProcedureInstance jpi = (JifProcedureInstance)pd.procedureInstance(); 
        for (Iterator iter = jpi.constraints().iterator(); iter.hasNext(); ) {
            Assertion a = (Assertion)iter.next();
            if (a instanceof AuthConstraint) {
                AuthConstraint ac = (AuthConstraint)a;
                ensureNotTopPrincipal(ac.principals(), a.position());
            }
            else if (a instanceof CallerConstraint) {
                CallerConstraint cc = (CallerConstraint)a;
                ensureNotTopPrincipal(cc.principals(), a.position());
            }
            else if (a instanceof ActsForConstraint) {
                ActsForConstraint afc = (ActsForConstraint)a;
                ensureNotTopPrincipal(afc.actor(), a.position());
                ensureNotTopPrincipal(afc.granter(), a.position());
            }
        }
        return pd;
    }
    
    protected void ensureNotTopPrincipal(List principals, Position pos) throws SemanticException {
        for (Iterator iter = principals.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();            
            ensureNotTopPrincipal(p, pos);
        }
    }
    protected void ensureNotTopPrincipal(Principal p, Position pos) throws SemanticException {
        if (p.isTopPrincipal()) {
            throw new SemanticException("The top principal " + p + 
                                        " cannot appear in a constraint.", pos);
        }
    }
    
}

