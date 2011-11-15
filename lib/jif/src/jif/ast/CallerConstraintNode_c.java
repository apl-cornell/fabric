package jif.ast;

import java.util.*;

import jif.types.CallerConstraint_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.*;
import polyglot.visit.*;

/** An implementation of the <code>CallerConstraint</code> interface.
 */
public class CallerConstraintNode_c extends ConstraintNode_c implements CallerConstraintNode
{
    protected List principals;

    public CallerConstraintNode_c(Position pos, List principals) {
	super(pos);
	this.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
    }

    public List principals() {
	return this.principals;
    }

    public CallerConstraintNode principals(List principals) {
	CallerConstraintNode_c n = (CallerConstraintNode_c) copy();
	n.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
        if (constraint()!=null) {
            List l = new LinkedList();
            for (Iterator i = principals.iterator(); i.hasNext(); ) {
                PrincipalNode p = (PrincipalNode) i.next();
                l.add(p.principal());
            }
            n.setConstraint(((CallerConstraint_c)constraint()).principals(l));
        }
	return n;
    }

    protected CallerConstraintNode_c reconstruct(List principals) {
	if (! CollectionUtil.equals(principals, this.principals)) {
            List newPrincipals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
            return (CallerConstraintNode_c)this.principals(newPrincipals);
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

            return constraint(ts.callerConstraint(position(), l));
        }

        return this;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("caller(");

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
