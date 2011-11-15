package jif.ast;

import jif.types.AutoEndorseConstraint_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;

public class AutoEndorseConstraintNode_c extends ConstraintNode_c implements AutoEndorseConstraintNode
{
    protected LabelNode endorseTo;

    public AutoEndorseConstraintNode_c(Position pos, LabelNode endorseTo) {
	super(pos);
	this.endorseTo = endorseTo;
    }

    public LabelNode endorseTo() {
	return this.endorseTo;
    }

    public AutoEndorseConstraintNode endorseTo(LabelNode endorseTo) {
	AutoEndorseConstraintNode_c n = (AutoEndorseConstraintNode_c) copy();
	n.endorseTo = endorseTo;
        if (constraint() != null) {
            n.setConstraint(((AutoEndorseConstraint_c)constraint()).endorseTo(endorseTo.label()));
        }
	return n;
    }

    protected AutoEndorseConstraintNode_c reconstruct(LabelNode endorseTo) {
	if (this.endorseTo != endorseTo) {
            return (AutoEndorseConstraintNode_c)this.endorseTo(endorseTo);
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
        LabelNode endorseTo = (LabelNode) visitChild(this.endorseTo, v);
        return reconstruct(endorseTo);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (constraint() == null) {
            JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();
            return constraint(ts.autoEndorseConstraint(position(), endorseTo.label()));
        }

        return this;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("autoendorse(");
        print(endorseTo, w, tr);
        w.write(")");
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("Cannot translate " + this);
    }
}
