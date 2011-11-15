package jif.ast;

import java.util.List;

import jif.types.JifTypeSystem;
import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import polyglot.visit.TypeChecker;

/** An implementation of the <code>NewLabel</code> interface. 
 */
public class LabelExpr_c extends Expr_c implements LabelExpr
{
    protected LabelNode label;

    public LabelExpr_c(Position pos, LabelNode label) {
	super(pos);
	this.label = label;
    }

    public LabelNode label() {
	return this.label;
    }

    public LabelExpr label(LabelNode label) {
	LabelExpr_c n = (LabelExpr_c) copy();
	n.label = label;
	return n;
    }
    
    protected LabelExpr_c reconstruct(LabelNode label) {
	if (label != this.label) {
	    LabelExpr_c n = (LabelExpr_c) copy();
	    n.label = label;
	    return n;
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	LabelNode label = (LabelNode) visitChild(this.label, v);
	return reconstruct(label);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	JifTypeSystem ts = (JifTypeSystem) tc.typeSystem();	
	return type(ts.Label());
    }

    public List throwTypes(TypeSystem ts) {
        return label().label().throwTypes(ts);
    }
    
    public Term firstChild() {
        return null;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("{");
        print(label, w, tr);
        w.write("}");
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("cannot translate " + this);
    }

    public String toString() {
	return label.toString();
    }
}
