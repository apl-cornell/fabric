package jif.parse;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Prefix;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

/**
 * An <code>Inst</code> represents a <code>Amb</code> of the form "P[T,U,...]".
 * Either there must be at least two types/expressions in the list, which implies that
 * this must be an instantiation of a polymorphic type, or there may be a single element
 * in the list that is known to be a type.
 * The type itself is still ambiguous.
 */
public class Inst extends Amb {
    // prefix[a,b]
    TypeNode prefix;
    List params;

    public Inst(Grm parser, Position pos, TypeNode prefix, List params) throws Exception {
	super(parser, pos);
	this.prefix = prefix;
	this.params = new ArrayList(params);

	if (prefix instanceof Inst) parser.die(pos);
	if (prefix instanceof InstOrAccess) parser.die(pos);
	if (prefix instanceof Access) parser.die(pos);
	if (prefix instanceof LabeledExpr) parser.die(pos);
    }

    public TypeNode prefix() {
        return prefix;
    }
    public List params() {
        return params;
    }
    public TypeNode toType() throws Exception {
	return parser.nf.InstTypeNode(pos, prefix, params);
    }

    public Prefix toPrefix() throws Exception { return toType(); }
    public Receiver toReceiver() throws Exception { return toType(); }
    public TypeNode toUnlabeledType() throws Exception { return toType(); }
    public TypeNode toClassType() throws Exception { return toType(); }
}