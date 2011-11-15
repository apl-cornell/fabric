package jif.ast;

import jif.types.*;
import jif.visit.JifTypeChecker;
import polyglot.ast.*;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

/** An implementation of the <code>AmbParam</code> interface. 
 */
public class AmbExprParam_c extends Node_c implements AmbExprParam
{
    protected Expr expr;
    protected ParamInstance expectedPI;
    
    public AmbExprParam_c(Position pos, Expr expr, ParamInstance expectedPI) {
        super(pos);
        this.expr = expr;
        this.expectedPI = expectedPI;
    }
    
    public boolean isDisambiguated() {
        return false;
    }
    
    public Expr expr() {
        return this.expr;
    }
    
    public AmbParam expr(Expr expr) {
        AmbExprParam_c n = (AmbExprParam_c) copy();
        n.expr = expr;
        return n;
    }

    public AmbParam expectedPI(ParamInstance expectedPI) {
        AmbExprParam_c n = (AmbExprParam_c) copy();
        n.expectedPI = expectedPI;
        return n;
    }
    
    public Param parameter() {
        throw new InternalCompilerError("No parameter yet");
    }
    
    public String toString() {
        return expr + "{amb}";
    }
    
    public Node visitChildren(NodeVisitor v) {
        Expr expr = (Expr) visitChild(this.expr, v);
        return reconstruct(expr, expectedPI);
    }
    protected AmbExprParam_c reconstruct(Expr expr, ParamInstance expectedPI) {
        if (this.expr == expr && this.expectedPI == expectedPI) { return this; }
        AmbExprParam_c n = (AmbExprParam_c)this.copy();
        n.expr = expr;
        n.expectedPI = expectedPI;
        return n;         
    }
        
    /** 
     * Always return a CanoncialLabelNode, and let the dynamic label be possibly 
     * changed to a dynamic principal later.
     */
    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (!ar.isASTDisambiguated(expr)) {
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }
        JifContext c = (JifContext)ar.context();
        JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();
        JifNodeFactory nf = (JifNodeFactory) ar.nodeFactory();

        // run the typechecker over expr.
        TypeChecker tc = new JifTypeChecker(ar.job(), ts, nf, true);
        tc = (TypeChecker) tc.context(ar.context());
	expr = (Expr)expr.visit(tc);
    
        if (! expr.isTypeChecked()) {
            if (expr instanceof Field) {
                Field f = (Field)expr;
                if (ts.unlabel(f.target().type()) instanceof ParsedClassType) {
                    // disambiguate the class of the receiver of the field,
                    // so that type checking will eventually go through.
                    ParsedClassType pct = (ParsedClassType)ts.unlabel(f.target().type());
                    Scheduler sched = ar.job().extensionInfo().scheduler();
                    Goal g = sched.Disambiguated(pct.job());
                    throw new MissingDependencyException(g);                        
                }
            }
//            Scheduler sched = ar.job().extensionInfo().scheduler();
//            Goal g = sched.Disambiguated(ar.job());
//            throw new MissingDependencyException(g);                        
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

	if (expr instanceof PrincipalNode || 
            ts.isImplicitCastValid(expr.type(), ts.Principal()) ||
            (expectedPI != null && expectedPI.isPrincipal())) {
            if (!JifUtil.isFinalAccessExprOrConst(ts, expr, ts.Principal())) {
                throw new SemanticDetailedException(
                    "Illegal principal parameter.",
                    "The expression " + expr + " is not suitable as a " +
                    "principal parameter. Principal parameters can be either " +
                    "dynamic principals, or principal expressions, such as a " +
                    "principal parameter, or an external principal.",
                    this.position());                                        
            }
            return nf.CanonicalPrincipalNode(position(), 
                                             JifUtil.exprToPrincipal(ts, expr, c));
        }
        if (!JifUtil.isFinalAccessExprOrConst(ts, expr, ts.Label())) {
            throw new SemanticDetailedException(
                "Illegal label parameter.",
                "The expression " + expr + " is not suitable as a " +
                "label parameter. Label parameters can be either " +
                "dynamic labels, or label expressions.",
                this.position());
        }
        return nf.CanonicalLabelNode(position(), 
                                     JifUtil.exprToLabel(ts, expr, c));            
    }
}
