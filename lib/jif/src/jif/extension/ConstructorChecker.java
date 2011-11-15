package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.types.*;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/** A tool to label check constructors. 
 */
public class ConstructorChecker
{
    public void checkConstructorAuthority(ClassType t, JifContext A, LabelChecker lc, Position pos)
    throws SemanticException
    {
        if (t instanceof JifClassType) {
            final JifClassType jct = (JifClassType)t;
            // construct a principal representing the authority of the context
            Principal authPrincipal = lc.jifTypeSystem().conjunctivePrincipal(pos, A.authority());

            // Check the authority
            for (Iterator iter = jct.constructorCallAuthority().iterator(); iter.hasNext();) {
                final Principal pi = (Principal)iter.next();

                // authPrincipal must actfor pi, i.e., at least one
                // of the principals that have authorized the code must act for pi.
                lc.constrain(authPrincipal, 
                             PrincipalConstraint.ACTSFOR, 
                            pi, 
                           A.labelEnv(),
                           pos,
                           new ConstraintMessage() {
                    public String msg() {
                        return "Calling context does not "
                        + "have enough authority to construct \"" + jct
                        + "\".";
                    }
                    public String detailMsg() {
                        return "In order to construct an instance of class "
                        + jct
                        + ", the calling context must have the authority of "
                        + "the following principal(s): "
                        + principalListString(jct.constructorCallAuthority())
                        + ".";
                    }
                });                     
            }
        }
    }
    public void checkStaticMethodAuthority(final JifMethodInstance mi, JifContext A, LabelChecker lc, Position pos)
    throws SemanticException
    {
        ClassType t = mi.container().toClass();
        if (t instanceof JifClassType) {
            final JifClassType jct = (JifClassType)t;
            // construct a principal representing the authority of the context
            Principal authPrincipal = lc.jifTypeSystem().conjunctivePrincipal(mi.position(), A.authority());

            // Check the authority
            for (Iterator iter = jct.constructorCallAuthority().iterator(); iter.hasNext();) {
                final Principal pi = (Principal)iter.next();

                // authPrincipal must actfor pi, i.e., at least one
                // of the principals that have authorized the code must act for pi.
                lc.constrain(authPrincipal, 
                             PrincipalConstraint.ACTSFOR, 
                            pi, 
                           A.labelEnv(),
                           pos,
                           new ConstraintMessage() {
                    public String msg() {
                        return "Calling context does not "
                        + "have enough authority to invoke the static method " + 
                        mi.signature() + " of class " + jct + ".";
                    }
                    public String detailMsg() {
                        return "In order to call a static method of class " + jct
                        + ", the calling context must have the authority of "
                        + "the following principal(s): "
                        + principalListString(jct.constructorCallAuthority()) + ".";
                    }
                });                     
            }
        }
    }
    
    private String principalListString(List principals) {
        int size = principals.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            Principal p = (Principal)principals.get(i);
            sb.append(p);
            if (i == size-2) {
                sb.append(" and ");
            }
            else if (i < size-2) {
                sb.append(", ");                
            }
        }
        return sb.toString();
    }
}
