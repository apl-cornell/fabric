package jif.extension;

import jif.ast.LabelNode;
import jif.ast.LabeledTypeNode;
import jif.types.JifLocalInstance;
import jif.types.JifTypeSystem;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>Formal</code> node. 
 * 
 *  @see polyglot.ast.Formal
 */
public class JifFormalDel extends JifJL_c
{
    public JifFormalDel() { }

    private boolean isCatchFormal = false;
    private boolean explicitFinalFlag = false;
    public void setIsCatchFormal(boolean isCatchFormal) {
        this.isCatchFormal = isCatchFormal;
    }
    public boolean isCatchFormal() {
        return this.isCatchFormal;
    }
    public boolean hasExplicitFinalFlag() {
        return explicitFinalFlag;
    }
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        Formal n = (Formal) this.node();
        this.explicitFinalFlag = n.flags().isFinal();
        n = (Formal) super.buildTypes(tb);
        
        // all formals are final
        n = n.flags(n.flags().Final());
        JifLocalInstance li = (JifLocalInstance)n.localInstance();
        li.setFlags(li.flags().Final());
        
        JifTypeSystem jts = (JifTypeSystem)tb.typeSystem();


        if (isCatchFormal) {
            // formals occuring in a catch clause are treated more like local decls;
            // their label is a VarLabel
            li.setLabel(jts.freshLabelVariable(li.position(), li.name(), "label of the formal " + li.name()));
        }
        else {
            Position pos = n.type().position();
            if (n.type() instanceof LabeledTypeNode) {
                LabelNode ln = ((LabeledTypeNode)n.type()).labelPart();
                if (ln != null) {
                    pos = ln.position();
                }
            }

            // method and constructor formals have an ArgLabel 
            ArgLabel al = jts.argLabel(pos, li, null);
            li.setLabel(al);
        }

        n = n.localInstance(li);
        return n;
    }


    /* Perform an imperative update to the local instance.
     */
    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        Formal n = (Formal)super.disambiguate(ar);
        JifTypeSystem jts = (JifTypeSystem)ar.typeSystem();

        JifLocalInstance li = (JifLocalInstance)n.localInstance();
        li.setFlags(n.flags());
        li.setName(n.name());
        
        // set the type of the local instance, but only if we haven't
        // set the upper bound of the arg label.
        if  (isCatchFormal || ((ArgLabel)li.label()).upperBound() == null) {
            li.setType(n.declType());
        }

        if (!n.type().isDisambiguated()) {
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return n;
        }


        if (!isCatchFormal) {
            ArgLabel al = (ArgLabel)li.label();

            al.setCodeInstance(ar.context().currentCode());

            if (al.upperBound() == null) {
                // haven't set the arg label yet
                // do so now.

                if (!jts.isLabeled(n.declType())) {
                    // declared type isn't labeled, use the default arg bound
                    Type lblType = n.declType();
                    Label defaultBound = jts.defaultSignature().defaultArgBound(n);
                    lblType = jts.labeledType(lblType.position(), lblType, defaultBound);
                    n = n.type(n.type().type(lblType));                    
                }

                // now take the label of the declared type, and set it to 
                // be the bound
                al.setUpperBound(jts.labelOfType(n.declType()));

                // now set the label of the declared type to be the arg label
                Type lblType = n.declType();
                Position pos = n.type().position();
                lblType = jts.labeledType(pos, jts.unlabel(lblType), al);
                n = n.type(n.type().type(lblType));     
            }
        }

        return n;
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        Formal f = (Formal) node();

        // if the declared type is an array type, make sure it is the same all the way through
        if (f.localInstance().type().isArray()) {
            JifTypeSystem jts = (JifTypeSystem)tc.typeSystem(); 
            ArrayType at = jts.unlabel(f.localInstance().type()).toArray();
            JifLocalDeclDel.checkArrayTypeConsistency(at);
        }
        return super.typeCheck(tc);
    }

}
