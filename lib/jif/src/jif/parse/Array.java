package jif.parse;

import jif.ast.ConstArrayTypeNode;
import jif.ast.LabeledTypeNode;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

/**
 * An <code>Array</code> represents a <code>Amb</code> of the form "P[]".  
 * This must be an array type, although the base of that type is still ambiguous.
 */  
public class Array extends Amb {
    // prefix[]
    final protected TypeNode prefix;
    final protected boolean isConst;

    public Array(Grm parser, Position pos, TypeNode prefix) {
        this(parser, pos, prefix, false);
    }
    public Array(Grm parser, Position pos, TypeNode prefix, boolean isConst) {
        super(parser, pos);
        this.prefix = prefix;
        this.isConst = isConst;
    }
    
    public TypeNode prefix() {
        return prefix;
    }
    public boolean isConst() {
        return isConst;
    }

    public TypeNode toType() {
        // if the (unlabeled) base type is an array, inherit the constness from that.
        TypeNode base = prefix;
        if (base instanceof LabeledTypeNode) {
            base = ((LabeledTypeNode)base).typePart();
        }
        if (isConst || (base instanceof ConstArrayTypeNode)) {
            return parser.nf.ConstArrayTypeNode(pos, prefix);
        }
        return parser.nf.ArrayTypeNode(pos, prefix);
    }

    public TypeNode toUnlabeledType() { return toType(); }
    public Receiver toReceiver() { return toType(); }
}

