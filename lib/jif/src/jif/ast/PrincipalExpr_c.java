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

public class PrincipalExpr_c extends Expr_c implements PrincipalExpr
{
    protected PrincipalNode principal;

    public PrincipalExpr_c(Position pos, PrincipalNode principal) {
	super(pos);
	this.principal = principal;
    }

    public PrincipalNode principal() {
	return this.principal;
    }

    public PrincipalExpr principal(PrincipalNode principal) {
	PrincipalExpr_c n = (PrincipalExpr_c) copy();
	n.principal = principal;
	return n;
    }
    
    protected PrincipalExpr_c reconstruct(PrincipalNode principal) {
	if (principal != this.principal) {
	    PrincipalExpr_c n = (PrincipalExpr_c) copy();
	    n.principal = principal;
	    return n;
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	PrincipalNode principal = (PrincipalNode) visitChild(this.principal, v);
	return reconstruct(principal);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	JifTypeSystem ts = (JifTypeSystem) tc.typeSystem();	
	return type(ts.Principal());
    }

    public List throwTypes(TypeSystem ts) {
        return principal().principal().throwTypes(ts);
    }
    
    public Term firstChild() {
        return null;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("new principal (");
        print(principal, w, tr);
        w.write(")");
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("cannot translate " + this);
    }

    public String toString() {
	return principal.toString();
    }
}
