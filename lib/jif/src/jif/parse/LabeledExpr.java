package jif.parse;

import jif.ast.LabelNode;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class LabeledExpr extends Amb {
    // prefix{L}
    public Amb prefix;
    public LabelNode label;

    public LabeledExpr(Grm parser, Position pos, Amb prefix, LabelNode label) throws Exception {
	super(parser, pos);
	this.prefix = prefix;
	this.label = label;
	if (prefix instanceof LabeledExpr) 
	    parser.die(pos);
    }

    public TypeNode toType() throws Exception {
	return parser.nf.LabeledTypeNode(pos, prefix.toType(), label);
    }
}


