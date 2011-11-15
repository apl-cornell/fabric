package jif.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.types.*;
import jif.types.label.*;
import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;

/** An implementation of the <code>JifMethod</code> interface.
 */
public class JifMethodDecl_c extends MethodDecl_c implements JifMethodDecl
{
    protected LabelNode startLabel;
    protected LabelNode returnLabel;
    protected List constraints;

    public JifMethodDecl_c(Position pos, Flags flags, TypeNode returnType,
            Id name, LabelNode startLabel, List formals, LabelNode returnLabel,
            List throwTypes, List constraints, Block body) {
        super(pos, flags, returnType, name, formals, throwTypes, body);
        this.startLabel = startLabel;
        this.returnLabel = returnLabel;
        this.constraints = TypedList.copyAndCheck(constraints, 
                                                  ConstraintNode.class, true);    
    }

    public LabelNode startLabel() {
        return this.startLabel;
    }

    public JifMethodDecl startLabel(LabelNode startLabel) {
        JifMethodDecl_c n = (JifMethodDecl_c) copy();
        n.startLabel = startLabel;
        return n;
    }

    public LabelNode returnLabel() {
        return this.returnLabel;
    }

    public JifMethodDecl returnLabel(LabelNode returnLabel) {
        JifMethodDecl_c n = (JifMethodDecl_c) copy();
        n.returnLabel = returnLabel;
        return n;
    }

    public List constraints() {
        return this.constraints;
    }

    public JifMethodDecl constraints(List constraints) {
        JifMethodDecl_c n = (JifMethodDecl_c) copy();
        n.constraints = TypedList.copyAndCheck(constraints, ConstraintNode.class, true);
        return n;
    }

    public Node visitChildren(NodeVisitor v) {
        Id name = (Id)visitChild(this.name, v);
        TypeNode returnType = (TypeNode) visitChild(this.returnType, v);
        LabelNode startLabel = (LabelNode) visitChild(this.startLabel, v);
        List formals = visitList(this.formals, v);
        LabelNode returnLabel = (LabelNode) visitChild(this.returnLabel, v);
        List throwTypes = visitList(this.throwTypes, v);
        List constraints = visitList(this.constraints, v);
        Block body = (Block) visitChild(this.body, v);
        return reconstruct(name, returnType, startLabel, formals, returnLabel, throwTypes, constraints, body);
    }

    protected JifMethodDecl_c reconstruct(Id name, TypeNode returnType, LabelNode startLabel, List formals, LabelNode returnLabel, List throwTypes, List constraints, Block body) {
        if (startLabel != this.startLabel || returnLabel != this.returnLabel || ! CollectionUtil.equals(constraints, this.constraints)) {
            JifMethodDecl_c n = (JifMethodDecl_c) copy();
            n.startLabel = startLabel;
            n.returnLabel = returnLabel;
            n.constraints = TypedList.copyAndCheck(constraints, ConstraintNode.class, true);
            return (JifMethodDecl_c) n.reconstruct(returnType, name, formals, throwTypes, body);
        }

        return (JifMethodDecl_c) super.reconstruct(returnType, name, formals, throwTypes, body);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        JifMethodDecl n = (JifMethodDecl)super.disambiguate(ar);

        JifMethodInstance jmi = (JifMethodInstance)n.methodInstance();
        JifTypeSystem jts = (JifTypeSystem)ar.typeSystem();        

        // set the formal types
        List formalTypes = new ArrayList(n.formals().size());
        for (Iterator i = n.formals().iterator(); i.hasNext(); ) {
            Formal f = (Formal)i.next();
            if (!f.isDisambiguated()) {
                // formals are not disambiguated yet.
                ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }
            formalTypes.add(f.declType());
        }
        jmi.setFormalTypes(formalTypes);

        // return type
        if (!n.returnType().isDisambiguated()) {
            // return type node not disambiguated yet
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        DefaultSignature ds = jts.defaultSignature();

        if (n.startLabel() != null && !n.startLabel().isDisambiguated()) {
            // the startlabel node hasn't been disambiguated yet
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        Type declrt = n.returnType().type();
        if (! declrt.isVoid() && !jts.isLabeled(declrt)) {
            // return type isn't labeled. Add the default label.
            declrt = jts.labeledType(declrt.position(), declrt, ds.defaultReturnValueLabel(n));
            n = (JifMethodDecl)n.returnType(n.returnType().type(declrt));
        }
        jmi.setReturnType(declrt);

        if (n.returnLabel() != null && !n.returnLabel().isDisambiguated()) {
            // the return label node hasn't been disambiguated yet
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        Label Li; // pc bound for the method
        boolean isDefaultPCBound = false;
        if (n.startLabel() == null) {
            Li = ds.defaultPCBound(n.position(), n.name());
            isDefaultPCBound = true;
        } 
        else {
            Li = n.startLabel().label();
        }
        jmi.setPCBound(Li, isDefaultPCBound);

        Label Lr; // return label
        boolean isDefaultReturnLabel = false;
        if (n.returnLabel() == null) {
            Lr = ds.defaultReturnLabel(n);
            isDefaultReturnLabel = true;
        }
        else {
            Lr = n.returnLabel().label();
        }        
        jmi.setReturnLabel(Lr, isDefaultReturnLabel);


        // set the labels for the throwTypes.
        List throwTypes = new LinkedList();        
        for (Iterator i = n.throwTypes().iterator(); i.hasNext();) {
            TypeNode tn = (TypeNode)i.next();
            if (!tn.isDisambiguated()) {
                // throw types haven't been disambiguated yet.
                ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }

            Type xt = tn.type();
            if (!jts.isLabeled(xt)) {
                // default exception label is the return label
                xt = jts.labeledType(xt.position(), xt, Lr);
            }
            throwTypes.add(xt);
        }
        jmi.setThrowTypes(throwTypes);

        List constraints = new ArrayList(n.constraints().size());
        for (Iterator i = n.constraints().iterator(); i.hasNext(); ) {
            ConstraintNode cn = (ConstraintNode) i.next();
            if (!cn.isDisambiguated()) {
                // constraint nodes haven't been disambiguated yet.
                ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }
            constraints.addAll(cn.constraints());
        }
        jmi.setConstraints(constraints);

        renameArgs(jmi, new TypeSubstitutor(new ArgRenamer(false)));

        return n.methodInstance(jmi);
    }

    /**
     * Rename the arg labels and arg roots. This is needed to make sure
     * that during substitution of args in a recursive method call,
     * we don't confuse the 
     */
    public static JifMethodInstance unrenameArgs(JifMethodInstance jmi) {
        jmi = (JifMethodInstance)jmi.copy();
        try {
            renameArgs(jmi, new TypeSubstitutor(new ArgRenamer(true)));
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected semantic", e);
        }
        return jmi;
    }
    /**
     * Rename the arg labels and arg roots. This is needed to make sure
     * that during substitution of args in a recursive method call,
     * we don't confuse the 
     */
    private static void renameArgs(JifMethodInstance jmi, TypeSubstitutor tsub) throws SemanticException {
        // formal types
        List formalTypes = new ArrayList(jmi.formalTypes().size());
        for (Iterator i = jmi.formalTypes().iterator(); i.hasNext(); ) {
            Type t = (Type)i.next();
            formalTypes.add(tsub.rewriteType(t));
        }
        jmi.setFormalTypes(formalTypes);

        // return type
        jmi.setReturnType(tsub.rewriteType(jmi.returnType()));

        // pc bound label
        jmi.setPCBound(tsub.rewriteLabel(jmi.pcBound()), jmi.isDefaultPCBound());

        // return label
        jmi.setReturnLabel(tsub.rewriteLabel(jmi.returnLabel()), jmi.isDefaultReturnLabel());

        // throw types
        List throwTypes = new ArrayList(jmi.throwTypes().size());
        for (Iterator i = jmi.throwTypes().iterator(); i.hasNext(); ) {
            Type t = (Type)i.next();
            throwTypes.add(tsub.rewriteType(t));
        }
        jmi.setThrowTypes(throwTypes);


        // constraints
        List constraints = new ArrayList(jmi.constraints().size());
        for (Iterator i = jmi.constraints().iterator(); i.hasNext(); ) {
            Assertion c = (Assertion) i.next();
            constraints.add(tsub.rewriteAssertion(c));
        }
        jmi.setConstraints(constraints);
    }


    private static class ArgRenamer extends LabelSubstitution {
        boolean revertToOriginal;
        ArgRenamer(boolean revertToOriginal) {
            this.revertToOriginal = revertToOriginal;
        }
        public Label substLabel(Label L) {
            if (L instanceof ArgLabel) {
                ArgLabel al = (ArgLabel)L;
                if (!revertToOriginal && !al.name().endsWith("'")) {
                    // change the name to end with a prime
                    al = (ArgLabel)al.copy();
                    al.setName(al.name() + "'");
                    return al;
                }
                if (revertToOriginal && al.name().endsWith("'")) {
                    // change the name to remove the prime
                    al = (ArgLabel)al.copy();
                    al.setName(al.name().substring(0, al.name().length()-1));
                    return al;
                }
            }

            return L;
        }

        public AccessPath substAccessPath(AccessPath ap) {            
            AccessPathRoot r = ap.root();
            if (r instanceof AccessPathLocal) {
                AccessPathLocal apl = (AccessPathLocal)r;
                if (!revertToOriginal && !apl.name().endsWith("'")) {
                    apl = apl.name(apl.name() + "'");
                    AccessPath newPath = ap.subst(r, apl);
                    return newPath;
                }
                if (revertToOriginal && apl.name().endsWith("'")) {
                    apl = apl.name(apl.name().substring(0, apl.name().length()-1));
                    AccessPath newPath = ap.subst(r, apl);
                    return newPath;
                }
            }
            return ap;
        }        
    }
}

/**
 *  This class substitutes all signature ArgLabels, DynamicArgLabels and
 * ArgPrincipals in a procedure declaration with appropriate non-signature
 * labels/principals.
 */
//class SignatureArgSubstitution extends ArgLabelSubstitution {
//public SignatureArgSubstitution(List nonSigArgLabels) {
//super(nonSigArgLabels, false);
//}
//public Label substLabel(Label L) {
//L = super.substLabel(L);

//if (L instanceof DynamicArgLabel) {
//DynamicArgLabel dal = (DynamicArgLabel)L;
//JifTypeSystem jts = (JifTypeSystem)dal.typeSystem();
//L = jts.dynamicArgLabel(dal.position(), 
//dal.uid(), 
//dal.name(), 
//dal.label(), 
//dal.index(), 
//false);
//}
//return L;
//}

//public Principal substPrincipal(Principal p) {
//p = super.substPrincipal(p);
//if (p instanceof ArgPrincipal) {
//ArgPrincipal dap = (ArgPrincipal)p;
//JifTypeSystem jts = (JifTypeSystem)dap.typeSystem();
//p = jts.argPrincipal(dap.position(),
//dap.uid(),
//dap.name(),
//dap.label(),
//dap.index(),
//false);
//}
//return p;
//}
//}
