package jif.ast;

import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.ast.Ambiguous;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.ast.TypeNode_c;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.ExceptionChecker;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;

/** An implementation of the <code>LabeledTypeNode</code> interface. 
 */
public class LabeledTypeNode_c extends TypeNode_c implements LabeledTypeNode, Ambiguous
{
    protected TypeNode typePart;
    protected LabelNode labelPart;

    public LabeledTypeNode_c(Position pos, TypeNode typePart, LabelNode labelPart) {
	super(pos);
	this.typePart = typePart;
	this.labelPart = labelPart;
    }

    public TypeNode typePart() {
	return this.typePart;
    }

    public LabeledTypeNode typePart(TypeNode typePart) {
	LabeledTypeNode_c n = (LabeledTypeNode_c) copy();
	n.typePart = typePart;
	return n;
    }

    public LabelNode labelPart() {
	return this.labelPart;
    }

    public LabeledTypeNode labelPart(LabelNode labelPart) {
	LabeledTypeNode_c n = (LabeledTypeNode_c) copy();
	n.labelPart = labelPart;
	return n;
    }

    protected LabeledTypeNode_c reconstruct(TypeNode typePart, LabelNode labelPart) {
	if (typePart != this.typePart || labelPart != this.labelPart) {
	    LabeledTypeNode_c n = (LabeledTypeNode_c) copy();
	    n.typePart = typePart;
	    n.labelPart = labelPart;
	    return n;
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	TypeNode typePart = (TypeNode) visitChild(this.typePart, v);
	LabelNode labelPart = (LabelNode) visitChild(this.labelPart, v);
	return reconstruct(typePart, labelPart);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
          if (type == null) {
              return type(typePart.type());
          }

          return this;
    }

    public boolean isDisambiguated() {
        return false;
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
//        System.err.println("LabeledTypeNode.disamb: typepart: " + this.typePart + "   :::  labelPart: " + this.labelPart);
        LabeledTypeNode_c n = this;
        if (n.typePart.isDisambiguated()) {
            // give n's type a partial disambiguation, even if we can't give
            // the full one yet.
            n = (LabeledTypeNode_c)n.type(n.typePart.type());
        }
        if (!n.typePart.isDisambiguated() || !n.labelPart.isDisambiguated()) {
            // the children haven't been disambiguated yet
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return n;
        }

        JifTypeSystem jts = (JifTypeSystem) ar.typeSystem();

	Type t = n.typePart.type();
	Label L = n.labelPart.label();
    
	if (t.isVoid()) {
	    throw new SemanticException("The void type cannot be labeled.",
		position());
	}

	return ar.nodeFactory().CanonicalTypeNode(position(), 
	    jts.labeledType(n.position(), t, L));
    }

    public String toString() {
	return typePart.toString() + labelPart.toString();
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot type check ambiguous node " + this + ".");
    } 

    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot exception check ambiguous node " + this + ".");
    } 

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        print(typePart, w, tr);
        w.write("{");
        print(labelPart, w, tr);
        w.write("}");
    }

    public void translate(CodeWriter w, Translator tr) {
	throw new InternalCompilerError(position(),
	    "Cannot translate ambiguous node " + this + ".");
    }
}
