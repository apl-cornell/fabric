package jif.ast;

import java.util.LinkedList;
import java.util.List;

import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import jif.types.ParamInstance;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;

/** An implementation of the <code>AmbParamTypeOrAccess</code> interface.
 */
public class AmbParamTypeOrAccess_c extends Node_c implements AmbParamTypeOrAccess
{
    protected Receiver prefix;
    protected Object expr;
    protected Type type;

    public AmbParamTypeOrAccess_c(Position pos, Receiver prefix, Object expr) {
        super(pos);
        this.prefix = prefix;
        this.expr = expr;
    }

    public boolean isDisambiguated() {
        return false;
    }

    public Receiver prefix() {
        return this.prefix;
    }

    public AmbParamTypeOrAccess prefix(Receiver prefix) {
        AmbParamTypeOrAccess_c n = (AmbParamTypeOrAccess_c) copy();
        n.prefix = prefix;
        return n;
    }

    public Object expr() {
        return this.expr;
    }


    public Type type() {
        return this.type;
    }

    protected AmbParamTypeOrAccess_c reconstruct(Receiver prefix, Object expr) {
        if (prefix != this.prefix || expr != this.expr) {
            AmbParamTypeOrAccess_c n = (AmbParamTypeOrAccess_c) copy();
            n.prefix = prefix;
            n.expr = expr;
            return n;
        }

        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        Receiver prefix = (Receiver) visitChild(this.prefix, v);
        Object expr = this.expr;
        if (expr instanceof Expr) {
            expr = visitChild((Expr)expr, v);
        }
        return reconstruct(prefix, expr);
    }

    public String toString() {
        return prefix + "[" + expr + "]{amb}";
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();
        JifNodeFactory nf = (JifNodeFactory) ar.nodeFactory();

        if (!ar.isASTDisambiguated(prefix) || 
                (expr instanceof Expr && !ar.isASTDisambiguated((Expr)expr))) {
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        if (prefix instanceof TypeNode) {
            // "expr" must be a parameter.
            TypeNode tn = (TypeNode) prefix;

            if (! (tn.type() instanceof JifPolyType)) {
                throw new SemanticException(tn.type() + " is not a parameterized type.", position());
            }
            JifPolyType pt = (JifPolyType)tn.type();

            if (pt.params().isEmpty()) {
                throw new SemanticException(tn.type() + " is not a parameterized type.", position());            
            }

            ParamNode n;
            ParamInstance pi = (ParamInstance)pt.params().get(0);
            if (expr instanceof Expr) {
                n = nf.AmbParam(position(), (Expr)expr, pi);
                n = (ParamNode) n.del().disambiguate(ar);
            }
            else {
                n = nf.AmbParam(position(), (Id)expr, pi);	        
                n = (ParamNode) n.del().disambiguate(ar);
                if (!n.isDisambiguated()) {
                    throw new SemanticException("\"" + expr + "\" is not " + 
                                                "suitable as a parameter.", position());

                }
            }

            List l = new LinkedList();
            l.add(n.parameter());

            Type t = ts.instantiate(position(), pt.instantiatedFrom(), l);

            return nf.CanonicalTypeNode(position(), t);
        }
        else if (prefix instanceof Expr) {
            // "expr" must be an access index.
            Expr n;
            if (expr instanceof Expr) {
                n = (Expr)expr;
            }
            else {
                n = nf.AmbExpr(position(), (Id)expr);;
                n = (Expr)n.visit(ar);
            }
            return nf.ArrayAccess(position(), (Expr) prefix, n);
        }

        throw new SemanticException("Could not disambiguate type or expression.", position());
    }
}
