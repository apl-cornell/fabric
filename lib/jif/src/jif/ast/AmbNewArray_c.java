package jif.ast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import jif.types.ParamInstance;
import jif.types.SemanticDetailedException;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.*;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

/** An implementation of the <code>AmbNewArray</code> interface.
 */
public class AmbNewArray_c extends Expr_c implements AmbNewArray
{
    protected TypeNode baseType;
    /** The ambiguous expr. May be a parameter or an array dimension. */
    protected Object expr;
    protected List dims;
    protected int addDims;

    public AmbNewArray_c(Position pos, TypeNode baseType, Object expr, List dims, int addDims) {
        super(pos);
        this.baseType = baseType;
        this.expr = expr;
        if (!(expr instanceof Expr) && !(expr instanceof Id)) {
            throw new InternalCompilerError("wrong type for expr: " + expr.getClass().getName());
        }
        this.dims = TypedList.copyAndCheck(dims, Expr.class, true);
        this.addDims = addDims;
    }

    public boolean isDisambiguated() {
        return false;
    }

    /** Gets the base type.     */
    public TypeNode baseType() {
        return this.baseType;
    }

    /** Returns a copy of this node with <code>baseType</code> updated. */
    public AmbNewArray baseType(TypeNode baseType) {
        AmbNewArray_c n = (AmbNewArray_c) copy();
        n.baseType = baseType;
        return n;
    }

    /** Gets the expr. */
    public Object expr() {
        return this.expr;
    }

    /** Returns a copy of this node with <code>name</code> updated. */
//  public AmbNewArray expr(Expr expr) {
//  AmbNewArray_c n = (AmbNewArray_c) copy();
//  n.expr = expr;
//  return n;
//  }

    /** Gets the additional dimensions. */
    public List dims() {
        return this.dims;
    }

    /** Returns a copy of this node with <code>dims</code> updated. */
    public AmbNewArray dims(List dims) {
        AmbNewArray_c n = (AmbNewArray_c) copy();
        n.dims = TypedList.copyAndCheck(dims, Expr.class, true);
        return n;
    }
    
    public int additionalDims() {
    	return this.addDims;
    }
    
    public AmbNewArray additionalDims(int addDims) {
    	AmbNewArray_c n = (AmbNewArray_c) copy();
    	n.addDims = addDims;
    	return n;
    }

    /** Reconstructs the node. */
    protected AmbNewArray_c reconstruct(TypeNode baseType, Object expr, List dims) {
        if (baseType != this.baseType || expr != this.expr || ! CollectionUtil.equals(dims, this.dims)) {
            AmbNewArray_c n = (AmbNewArray_c) copy();
            n.baseType = baseType;
            n.expr = expr;
            n.dims = TypedList.copyAndCheck(dims, Expr.class, true);
            return n;
        }

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

    /** Visits the children of this node. */
    public Node visitChildren(NodeVisitor v) {
        TypeNode baseType = (TypeNode) visitChild(this.baseType, v);
        List dims = visitList(this.dims, v);
        Object expr = this.expr;
        if (expr instanceof Expr) {
            expr = visitChild((Expr)expr, v);
        }
        return reconstruct(baseType, expr, dims);
    }

    public String toString() {
        return "new " + baseType + "[" + expr + "]...{amb}";
    }

    /** Disambiguates
     */
    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (expr instanceof Expr && !ar.isASTDisambiguated((Expr)expr)) {
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }
        
        JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();
        JifNodeFactory nf = (JifNodeFactory) ar.nodeFactory();

        if (dims.isEmpty()) {
            throw new InternalCompilerError(position(),
                                            "Cannot disambiguate ambiguous new array with no " +
            "dimension expressions.");
        }

        Type t = baseType.type();

        if (t instanceof JifPolyType) {
            JifPolyType pt = (JifPolyType) t;

            if (pt.params().size() > 1) {
                //this node shouldn't be ambiguous.
                throw new SemanticDetailedException(
                                                    "Not enough parameters for parameterized type " + pt + ".",
                                                    "The type " + pt + " is a parameterized type with " +
                                                    pt.params().size() + " parameters. So, to instantiate this type, " +
                                                    "you must supply " + pt.params().size() + "",
                                                    this.position());
            }
            else if (pt.params().size() == 1) {
                // "name" is a parameter.  Instantiate the base type with the
                // parameter and use it as the new base type.
                ParamNode pn;
                ParamInstance pi = (ParamInstance)pt.params().get(0);
                if (expr instanceof Expr) {
                    pn = nf.AmbParam(position(), (Expr)expr, pi);                    
                }
                else {
                    pn = nf.AmbParam(position(), (Id)expr, pi);                                        
                }

                pn = (ParamNode) pn.del().disambiguate(ar);

                List l = new LinkedList();
                if (!pn.isDisambiguated()) {
                    // the instance is not yet ready
                    ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                    return this;
                }

                l.add(pn.parameter());

                Type base = ts.instantiate(baseType.position(),
                                           pt.instantiatedFrom(), l);

                return nf.NewArray(position(),
                                   nf.CanonicalTypeNode(baseType.position(),
                                                        base),
                                   dims,
                                   addDims);
            }
        }

        // "name" is an expression.  Prepend it to the list of dimensions.
        Expr e;
        if (expr instanceof Expr) {
            e = (Expr) ((Expr)expr).visit(ar);
        }
        else {
            e = nf.AmbExpr(position(), (Id)expr);
            e = (Expr) e.visit(ar);
        }


        List l = new LinkedList();
        l.add(e);
        l.addAll(dims);

        return nf.NewArray(position(), baseType, l, addDims);
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("new ");
        print(baseType, w, tr);
        w.write("[");
        if (expr instanceof Expr) {
            print((Expr)expr, w, tr);
        }
        else {
            w.write(((Id)expr).id());            
        }
        w.write("]");

        for (Iterator i = dims.iterator(); i.hasNext();) {
            Expr e = (Expr) i.next();
            w.write("[");
            printBlock(e, w, tr);
            w.write("]");
        }
    }
}
