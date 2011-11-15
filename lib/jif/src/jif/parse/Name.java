package jif.parse;

import polyglot.ast.*;
import polyglot.util.Position;

/**
 * A <code>Name</code> represents a <code>Amp</code> of the form "n | P.n".  
 * This could either be a field access or a type.  Both could be preceded by
 * ambiguous qualifiers.
 */  
public class Name extends Amb {
    // prefix.name
    public String name;
    public Amb prefix;
    
    public Name(Grm parser, Position pos, String name) throws Exception {
        this(parser, pos, null, name);
    }
    
    public String name() {
        return name;
    }
    public Amb prefix() {
        return prefix;
    }
    public Name(Grm parser, Position pos, Amb prefix, String name) throws Exception {
        super(parser, pos);
        this.prefix = prefix;
        this.name = name;
        
        if (prefix instanceof LabeledExpr) parser.die(pos);
        if (prefix instanceof Array) parser.die(pos);
    }
    
    public Expr toExpr() throws Exception {
        if (prefix == null) {
            if ("this".equals(name)) {
                return parser.nf.This(pos);
            }
            return parser.nf.AmbExpr(pos, name);
        }
        
        return parser.nf.Field(pos, prefix.toReceiver(), name);
    }
    
    public Receiver toReceiver() throws Exception {
        if (prefix == null) {
            if ("this".equals(name)) {
                return parser.nf.This(pos);
            }
            return parser.nf.AmbReceiver(pos, name);
        }
        
        return parser.nf.AmbReceiver(pos, prefix.toPrefix(), name);
    }
    
    public Prefix toPrefix() throws Exception {
        if (prefix == null) {
            if ("this".equals(name)) {
                return parser.nf.This(pos);
            }
            return parser.nf.AmbPrefix(pos, name);
        }
        
        return parser.nf.AmbPrefix(pos, prefix.toPrefix(), name);
    }
    
    public PackageNode toPackage() throws Exception {
        return parser.nf.PackageNode(pos,
                                     parser.ts.packageForName(toName()));
    }
    
    public TypeNode toType() throws Exception {
        if (prefix == null) {
            return parser.nf.AmbTypeNode(pos, name);
        }
        
        return parser.nf.AmbTypeNode(pos, prefix.toPackage(), name);
    }
    
    public TypeNode toClassType() throws Exception { return toType(); }
    public TypeNode toUnlabeledType() throws Exception { return toType(); }
    
    public Id toIdentifier() throws Exception {
        if (prefix != null) {
            parser.die(pos);
        }
        
        return parser.nf.Id(pos, name);
    }
    
    public String toName() throws Exception {
        if (prefix == null) {
            return name;
        }
        
        return prefix.toName() + "." + name;
    }
    
    public String toString() {
        try {
            return toName();
        }
        catch (Exception e) {
            return super.toString();
        }
    }
}


