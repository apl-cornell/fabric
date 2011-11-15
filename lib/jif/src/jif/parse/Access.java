package jif.parse;

import java.util.LinkedList;
import java.util.List;

import jif.ast.AmbNewArray;
import polyglot.ast.Expr;
import polyglot.ast.Labeled;
import polyglot.ast.NewArray;
import polyglot.ast.Prefix;
import polyglot.ast.Receiver;
import polyglot.util.Position;

/**
 * An <code>Access</code> represents a <code>Amb</code> of the form "P[e]"
 * where e must be an expression.  This must be an array access, although the
 * array itself is still ambiguous.
 */
public class Access extends Amb {
    // prefix[e]
    Amb prefix;
    Expr index;

    public Access(Grm parser, Position pos, Amb prefix, Expr index) throws Exception {
        super(parser, pos);
        this.prefix = prefix;
        this.index = index;

        if (prefix instanceof Labeled) parser.die(pos);
        if (prefix instanceof Array) parser.die(pos);
    }

    public Amb prefix() { return prefix; }
    public Expr index() { return index; }
    public Prefix toPrefix() throws Exception { return toExpr(); }
    public Receiver toReceiver() throws Exception { return toExpr(); }

    public Expr toExpr() throws Exception {
        index = (Expr) index.visit(new UnwrapVisitor());
        return parser.nf.ArrayAccess(pos, prefix.toExpr(), index);
    }

    public Expr toNewArrayPrefix(Position p, Integer extraDims) throws Exception {
        return toNewArray(p, extraDims);
    }

    public Expr toNewArray(Position p, Integer extraDims) throws Exception {
        if (prefix instanceof Name || prefix instanceof Inst) {
            // "new T.a[n]" or "new T[L,M][n]"
            List dims = new LinkedList();
            dims.add(index);
            return parser.nf.NewArray(p, prefix.toType(), dims, extraDims.intValue());
        }
        else if (prefix instanceof Access || prefix instanceof InstOrAccess) {
            // The prefix is "S[n]".  Recurse as if we were doing "new S[n]"
            Expr e = prefix.toNewArrayPrefix(p, extraDims);

            // Take the expression we built and add a new dimension.
            if (e instanceof NewArray) {
                NewArray a = (NewArray) e;
                List dims = new LinkedList(a.dims());
                dims.add(index);
                return a.dims(dims);
            }
            else if (e instanceof AmbNewArray) {
                AmbNewArray a = (AmbNewArray) e;
                List dims = new LinkedList(a.dims());
                dims.add(index);
                return a.dims(dims);
            }
        }

        parser.die(pos);
        return null;
    }
}


