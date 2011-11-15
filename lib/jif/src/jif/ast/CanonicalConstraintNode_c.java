package jif.ast;

import jif.types.Assertion;
import polyglot.util.*;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;

/** An implementation of the <code>CanonicalConstraint</code>. 
 */
public class CanonicalConstraintNode_c extends ConstraintNode_c implements CanonicalConstraintNode
{
    public CanonicalConstraintNode_c(Position pos, Assertion constraint) {
	super(pos);
	this.setConstraint(constraint);
    }

    public Assertion constraint() {
        return super.constraint();
    }
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write(constraint().toString());
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("Cannot translate " + this);
    }
}
