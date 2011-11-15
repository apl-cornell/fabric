package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.ast.JifMethodDecl;
import jif.ast.JifMethodDecl_c;
import jif.types.*;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathLocal;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.visit.TypeChecker;

/** The delegate of the <code>JifMethodDecl</code> node. 
 * 
 *  @see jif.ast.JifMethodDecl
 */
public class JifMethodDeclDel extends JifProcedureDeclDel {
    public JifMethodDeclDel() {
    }
    
    public Context enterScope(Context c) {
        JifMethodDecl jmd = (JifMethodDecl)this.node();
        JifMethodInstance mi = JifMethodDecl_c.unrenameArgs((JifMethodInstance)jmd.methodInstance()); 
        c = c.pushCode(mi);
        addFormalsToScope(c);
        return c;
    }
    
    
    /**
     * @see polyglot.ast.JL_c#typeCheck(polyglot.visit.TypeChecker)
     */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JifMethodDecl jmd = (JifMethodDecl)this.node();
        if (jmd.name().indexOf('$') >= 0) {
            throw new SemanticException("Method names can not contain the character '$'.");
        }
        
        JifMethodInstance mi = JifMethodDecl_c.unrenameArgs((JifMethodInstance)jmd.methodInstance()); 
        if ("main".equals(mi.name()) && mi.flags().isStatic()) {
            // check that the class is not parameterized.
            JifClassType currClass = (JifClassType)tc.context().currentClass();
            if (currClass.actuals().size() > 0) {                
                throw new SemanticDetailedException(
                      "A parameterized class can not have a \"main\" method.",
                      "Parameterized classes cannot have a main method, as " +
                      "the invoker of the main method has no way to specify " +
                      "instantiations of the class parameters.",
                      mi.position());
            }
            // ensure the signature of mi is either main(String[]) or
            // main(principal, String[])
            boolean wrongSig = true;
            List formalTypes = mi.formalTypes();
            
            String principalArgName = null;
            JifTypeSystem jts = (JifTypeSystem)tc.typeSystem();
            Type stringArrayType = jts.arrayOf(jts.String());
            if (formalTypes.size() == 1) {
                Type formal0 = jts.unlabel((Type)formalTypes.get(0));
                if (formal0.equals(stringArrayType)) {
                    // the main method signature is main(String[])
                    wrongSig = false;
                }
            }
            else if (formalTypes.size() == 2) {
                Type formal0 = jts.unlabel((Type)formalTypes.get(0));
                Type formal1 = jts.unlabel((Type)formalTypes.get(1));
                if (formal0.equals(jts.Principal()) && formal1.equals(stringArrayType)) {
                    // the main method signature is main(principal, String[])
                    wrongSig = false;
                    principalArgName = ((Formal)jmd.formals().get(0)).name();
                }                
            }

            if (wrongSig) {
                // warn the user that there may be a potentially wrong 
                // signature
                ErrorQueue eq = tc.errorQueue();
                eq.enqueue(ErrorInfo.WARNING,
                          "The signature of an invocable main " +
                          "method in a Jif class should either be " +
                          "\"main(String[] args)\" or \"main(principal p, " +
                          "String[] args)\" where p will be the user " +
                          "invoking the main method. This method may have " +
                          "an incorrect signature.", 
                          mi.position());
            }
            
            // check that the method does not have any constraints that we do not check.
            for (Iterator iter = mi.constraints().iterator(); iter.hasNext();) {
                Assertion constraint = (Assertion)iter.next();
                if (constraint instanceof ActsForConstraint || 
                        constraint instanceof LabelLeAssertion) {
                    // cannot have any actsfor or label le constraints
                    throw new SemanticDetailedException("The main method of a class can not have actsfor " +
                                "or label constraint annotations.", 
                                                        "The main method of a class can not have actsfor " +
                                "or label constraint annotations, as these constraints are not guaranteed to " +
                                "hold when the program is invoked. Use runtime tests to establish these constraints.",
                                                        constraint.position());
                }
                if (constraint instanceof CallerConstraint) {
                    // the only caller constraint allowed is if the principal is the first argument
                    CallerConstraint cc = (CallerConstraint)constraint;
                    boolean callerOK = false;                    
                    if (cc.principals().size() == 1) {
                        Principal callerP = (Principal)cc.principals().get(0);
                        // check that callerP is the same as the first arg.
                        if (callerP instanceof DynamicPrincipal) {
                            AccessPath ap = ((DynamicPrincipal)callerP).path();
                            callerOK = ap instanceof AccessPathLocal &&
                                        ((AccessPathLocal)ap).name().equals(principalArgName);
                        }
                    }
                    if (!callerOK) {
                        throw new SemanticException("The main method of a class " +
                                        "can only have a where caller constraint " +
                                        "of the principal given as an argument to " +
                                        "the main method.",
                                        constraint.position());                        
                    }
                }
            }    
        }
        return super.typeCheck(tc);
    }
}
