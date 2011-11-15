package jif.extension;

import jif.types.ConstArrayType;
import jif.types.JifLocalInstance;
import jif.types.JifTypeSystem;
import jif.visit.JifTypeChecker;
import polyglot.ast.LocalDecl;
import polyglot.ast.Node;
import polyglot.types.*;
import polyglot.visit.*;

/** The delegate of the <code>JifMethodDecl</code> node. 
 * 
 *  @see jif.ast.JifMethodDecl
 */
public class JifLocalDeclDel extends JifJL_c {
    public JifLocalDeclDel() {
    }
    
    
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        LocalDecl n = (LocalDecl) super.buildTypes(tb);
        JifTypeSystem jts = (JifTypeSystem)tb.typeSystem();

        JifLocalInstance li = (JifLocalInstance)n.localInstance();
        li.setLabel(jts.freshLabelVariable(li.position(), li.name(), "label of the local variable " + li.name()));
        
        return n.localInstance(li);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        LocalDecl n = (LocalDecl)node();
        LocalInstance li = n.localInstance();
        li.setFlags(n.flags());
        li.setName(n.name());
        li.setType(n.declType());
        return n;
    }

    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
        JifTypeChecker jtc = (JifTypeChecker)super.typeCheckEnter(tc);
        return jtc.inferClassParameters(true);
    }

    /**
     * @see polyglot.ast.JL_c#typeCheck(polyglot.visit.TypeChecker)
     */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        LocalDecl ld = (LocalDecl)this.node();
        if (ld.flags().isFinal() && ld.init() == null) {
            throw new SemanticException("Final local variables must have " +
                "an initializing expression.", ld.position());
        }
        
        // set the type on the local instance, as it may have changed during type checking.
        ld.localInstance().setType(ld.declType());
        
        // if the declared type is an array type, make sure it is the same all the way through
        if (ld.localInstance().type().isArray()) {
            JifTypeSystem jts = (JifTypeSystem)tc.typeSystem(); 
            ArrayType at = jts.unlabel(ld.localInstance().type()).toArray();
            checkArrayTypeConsistency(at);
        }

        return super.typeCheck(tc);
    }


    static void checkArrayTypeConsistency(ArrayType at) throws SemanticException {
        boolean isConst = false;
        if (at instanceof ConstArrayType) {
            ConstArrayType cat = (ConstArrayType)at;
            isConst = cat.isConst();
        }
        JifTypeSystem jts = (JifTypeSystem)at.typeSystem(); 
        Type base = jts.unlabel(at.base());
        if (base.isArray()) {
            boolean baseConst = false;
            if (base instanceof ConstArrayType) {
                ConstArrayType cat = (ConstArrayType)base;
                baseConst = cat.isConst();
            }
            if (isConst != baseConst) {
                throw new SemanticException("A const modifier for an array must apply to all dimensions.");
            }
            checkArrayTypeConsistency(base.toArray());
        }
    }

}
