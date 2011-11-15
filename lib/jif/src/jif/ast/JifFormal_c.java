package jif.ast;

import jif.extension.JifFormalDel;
import jif.types.JifTypeSystem;
import polyglot.ast.Formal_c;
import polyglot.ast.Id;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;

/**
 * 
 */
public class JifFormal_c extends Formal_c {

    public JifFormal_c(Position pos, Flags flags, TypeNode type, Id name) {
        super(pos, flags, type, name);
    }

    
    /**
     * 
     */
    public boolean isDisambiguated() {
        boolean typeNotNull = type() != null && type().type() != null;
        JifTypeSystem jts = (JifTypeSystem)(typeNotNull ? type().type().typeSystem() : null);
        return super.isDisambiguated() && 
                (((JifFormalDel)del()).isCatchFormal() ||
                (type() != null && 
                type().type() != null &&
                jts.isLabeled(type().type())));
    }
}
