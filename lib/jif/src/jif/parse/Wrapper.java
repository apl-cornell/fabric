package jif.parse;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;

/** 
 * A <code>Wrapper</code> wraps an <code>Amb</code> inside an AST node so that
 * it can be insert in the AST.  However, the tree <em>must</em> be visited
 * by an <code>UnwrapVisitor</code> before it is returned from the parser, 
 * otherwise the other phases of the compiler will be unable to work with 
 * the tree! 
 */
public class Wrapper extends Expr_c {
    public Amb amb;

    public Wrapper(Amb amb) {
        super(amb.pos);
        this.amb = amb;
    }

    public Node visitChildren(NodeVisitor v) {
        // throw new InternalCompilerError("Cannot visit illegal expression.");
        return this;
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    public Term firstChild() {
        return null;
    }
}


