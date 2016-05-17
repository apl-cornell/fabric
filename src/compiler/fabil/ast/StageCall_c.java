package fabil.ast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fabil.types.FabILTypeSystem;
import fabil.types.StageInstance;

import polyglot.ast.Expr;
import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Precedence;
import polyglot.ast.ProcedureCall;
import polyglot.ast.ProcedureCallOps;
import polyglot.ast.Term;
import polyglot.translate.ExtensionRewriter;
import polyglot.types.ProcedureInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;

/**
 * A {@code StageCall} represents a call to the special procedure {@code T
 * stage(T originalExpr, Label nextStage)}.
 */
public class StageCall_c extends Expr_c implements StageCall, ProcedureCallOps {
    private static final long serialVersionUID = SerialVersionUID.generate();

    protected Expr flagExpr;
    protected Expr origExpr;
    protected StageInstance si;

    public StageCall_c(Position pos, Expr origExpr, Expr flagExpr) {
        this(pos, origExpr, flagExpr, null);
    }

    public StageCall_c(Position pos, Expr origExpr, Expr flagExpr, Ext ext) {
        super(pos, ext);
        this.origExpr = origExpr;
        this.flagExpr = flagExpr;
    }

    @Override
    public Expr origExpr() {
        return this.origExpr;
    }

    @Override
    public StageCall origExpr(Expr origExpr) {
        return origExpr(this, origExpr);
    }

    protected <N extends StageCall_c> N origExpr(N n, Expr origExpr) {
        if (n.origExpr == origExpr) return n;
        n = copyIfNeeded(n);
        n.origExpr = origExpr;
        return n;
    }

    @Override
    public Expr flagExpr() {
        return this.flagExpr;
    }

    @Override
    public StageCall flagExpr(Expr flagExpr) {
        return flagExpr(this, flagExpr);
    }

    protected <N extends StageCall_c> N flagExpr(N n, Expr flagExpr) {
        if (n.flagExpr == flagExpr) return n;
        n = copyIfNeeded(n);
        n.flagExpr = flagExpr;
        return n;
    }

    @Override
    public List<Expr> arguments() {
        List<Expr> args = new ArrayList<>();
        args.add(flagExpr);
        args.add(origExpr);
        return args;
    }

    @Override
    public ProcedureCall arguments(List<Expr> arguments) {
        if (arguments.size() != 2)
          throw new InternalCompilerError("StageCalls must have exactly 2 arguments!");
        return reconstruct(this, arguments.get(0), arguments.get(1));
    }

    @Override
    public ProcedureInstance procedureInstance() {
        return stageInstance();
    }

    public StageInstance stageInstance() {
        return si;
    }

    public StageCall stageInstance(StageInstance si) {
        return stageInstance(this, si);
    }

    protected <N extends StageCall_c> N stageInstance(N n, StageInstance si) {
        if (n.si == si) return n;
        n = copyIfNeeded(n);
        n.si = si;
        return n;
    }

    /** Reconstruct the constructor call. */
    protected <N extends StageCall_c> N reconstruct(N n, Expr origExpr, Expr flagExpr) {
        n = origExpr(n, origExpr);
        n = flagExpr(n, flagExpr);
        return n;
    }

    @Override
    public Node visitChildren(NodeVisitor v) {
        Expr origExpr = visitChild(this.origExpr, v);
        Expr flagExpr = visitChild(this.flagExpr, v);
        return reconstruct(this, origExpr, flagExpr);
    }

    @Override
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        StageCall_c n = (StageCall_c) super.buildTypes(tb);

        FabILTypeSystem ts = (FabILTypeSystem) tb.typeSystem();

        StageInstance si =
                ts.stageInstance(ts.unknownType(position()));
        return stageInstance(n, si);
    }

    // Heavily cribbed from Call_c
    @Override
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        StageCall_c n = this;

        FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();

        Expr origExpr = n.origExpr;
        if (!origExpr.type().isCanonical()) {
            return this;
        }

        Expr flagExpr = n.flagExpr;
        if (!flagExpr.type().isCanonical()) {
            return this;
        }
        if (!flagExpr.type().typeEquals(ts.Label())) {
            throw new SemanticException("The second argument to the stage call "
                + position + " must be a label!");
        }

        StageInstance si = ts.stageInstance(origExpr.type());

        StageCall_c call = this;
        call = stageInstance(call, si);
        call = type(call, si.origType());
        return call;
    }

    @Override
    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();

        if (child == this.origExpr) {
          return si.origType();
        } else if (child == this.flagExpr) {
          return ts.Label();
        }

        return child.type();
    }

    @Override
    public Node extRewrite(ExtensionRewriter rw) throws SemanticException {
        StageCall_c n = (StageCall_c) super.extRewrite(rw);
        n = stageInstance(n, null);
        return n;
    }

    @Override
    public String toString() {
        return "stage(...)";
    }

    @Override
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("stage");
        printArgs(w, tr);
    }

    @Override
    public void printArgs(CodeWriter w, PrettyPrinter tr) {
        w.write("(");
        w.allowBreak(2, 2, "", 0);
        w.begin(0);
        print(origExpr, w, tr);
        w.write(",");
        w.allowBreak(0);
        print(flagExpr, w, tr);
        w.end();
        w.write(")");
    }

    @Override
    public Term firstChild() {
        return origExpr;
    }

    @Override
    public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
        v.visitCFG(origExpr, this, EXIT);
        v.visitCFG(flagExpr, this, EXIT);
        return succs;
    }

    @Override
    public List<Type> throwTypes(TypeSystem ts) {
        return new LinkedList<>();
    }

    @Override
    public Node copy(NodeFactory nf) {
        FabILNodeFactory fnf = (FabILNodeFactory) nf;
        return fnf.StageCall(this.position, this.origExpr, this.flagExpr);
    }

    @Override
    public void printSubExpr(Expr expr, CodeWriter w, PrettyPrinter pp) {
        if (Precedence.LITERAL.isTighter(expr.precedence())) {
            w.write("(");
            printBlock(expr, w, pp);
            w.write(")");
        }
        else {
            print(expr, w, pp);
        }
    }
}
