package jif.ast;

import java.util.*;

import jif.types.AuthConstraint_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.*;
import polyglot.visit.*;

/** An implementation of the <code>AuthConstraintNode</code> interface.
 */
public class AuthConstraintNode_c extends ConstraintNode_c implements AuthConstraintNode
{
    protected List principals;

    public AuthConstraintNode_c(Position pos, List principals) {
	super(pos);
	this.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
    }

    public List principals() {
	return this.principals;
    }

    public AuthConstraintNode principals(List principals) {
	AuthConstraintNode_c n = (AuthConstraintNode_c) copy();
	n.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
        if (constraint()!=null) {
            List l = new LinkedList();
            for (Iterator i = principals.iterator(); i.hasNext(); ) {
                PrincipalNode p = (PrincipalNode) i.next();
                l.add(p.principal());
            }
            n.setConstraint(((AuthConstraint_c)constraint()).principals(l));
        }
	return n;
    }

    protected AuthConstraintNode_c reconstruct(List principals) {
	if (! CollectionUtil.equals(principals, this.principals)) {
            List newPrincipals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
            return (AuthConstraintNode_c)this.principals(newPrincipals);
	}
	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	List principals = visitList(this.principals, v);
	return reconstruct(principals);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (constraint() == null) {
            JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();

            List l = new LinkedList();

            for (Iterator i = this.principals.iterator(); i.hasNext(); ) {
                PrincipalNode p = (PrincipalNode) i.next();
                l.add(p.principal());
            }

            return constraint(ts.authConstraint(position(), l));
        }

        return this;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("authority(");

        for (Iterator i = principals.iterator(); i.hasNext(); ) {
            PrincipalNode p = (PrincipalNode) i.next();
            print(p, w, tr);
            w.write(",");
            w.allowBreak(0, " ");
        }

        w.write(")");
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("Cannot translate " + this);
    }
}
