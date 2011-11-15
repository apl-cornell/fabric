package jif.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.types.DefaultSignature;
import jif.types.JifConstructorInstance;
import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.ast.*;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

/** 
 * An implementation of the <code>JifConstructor</code> interface.
 */
public class JifConstructorDecl_c extends ConstructorDecl_c implements JifConstructorDecl
{
    protected LabelNode startLabel;
    protected LabelNode returnLabel;
    protected List constraints;

    public JifConstructorDecl_c(Position pos, Flags flags, Id name, LabelNode startLabel, LabelNode returnLabel, List formals, List throwTypes, List constraints, Block body) {
        super(pos, flags, name, formals, throwTypes, body);
        this.startLabel = startLabel;
        this.returnLabel = returnLabel;
        this.constraints = TypedList.copyAndCheck(constraints, ConstraintNode.class, true);
    }

    public LabelNode startLabel() {
        return this.startLabel;
    }

    public JifConstructorDecl startLabel(LabelNode startLabel) {
        JifConstructorDecl_c n = (JifConstructorDecl_c) copy();
        n.startLabel = startLabel;
        return n;
    }

    public LabelNode returnLabel() {
        return this.returnLabel;
    }

    public JifConstructorDecl returnLabel(LabelNode returnLabel) {
        JifConstructorDecl_c n = (JifConstructorDecl_c) copy();
        n.returnLabel = returnLabel;
        return n;
    }

    public List constraints() {
        return this.constraints;
    }

    public JifConstructorDecl constraints(List constraints) {
        JifConstructorDecl_c n = (JifConstructorDecl_c) copy();
        n.constraints = TypedList.copyAndCheck(constraints, ConstraintNode.class, true);
        return n;
    }

    protected JifConstructorDecl_c reconstruct(Id name, LabelNode startLabel, 
	    LabelNode returnLabel, List formals, List throwTypes, 
	    List constraints, Block body) {
        if (startLabel != this.startLabel || returnLabel != this.returnLabel || !CollectionUtil.equals(constraints, this.constraints)) {
            JifConstructorDecl_c n = (JifConstructorDecl_c) copy();
            n.startLabel = startLabel;
            n.returnLabel = returnLabel;
            n.constraints = TypedList.copyAndCheck(constraints, ConstraintNode.class, true);
            return (JifConstructorDecl_c) n.reconstruct(name, formals, throwTypes, body);
        }

        return (JifConstructorDecl_c) super.reconstruct(name, formals, throwTypes, body);
    }

    public Node visitChildren(NodeVisitor v) {
        Id name = (Id)visitChild(this.name, v);
        LabelNode startLabel = (LabelNode) visitChild(this.startLabel, v);
        LabelNode returnLabel = (LabelNode) visitChild(this.returnLabel, v);
        List formals = visitList(this.formals, v);
        List throwTypes = visitList(this.throwTypes, v);
        List constraints = visitList(this.constraints, v);
        Block body = (Block) visitChild(this.body, v);
        return reconstruct(name, startLabel, returnLabel, formals, throwTypes, constraints, body);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        JifConstructorDecl n = (JifConstructorDecl_c)super.disambiguate(ar);

        JifConstructorInstance jci = (JifConstructorInstance)n.constructorInstance();
        JifTypeSystem jts = (JifTypeSystem)ar.typeSystem();

        if (n.startLabel() != null && !n.startLabel().isDisambiguated()) {
            // the startlabel node hasn't been disambiguated yet
            return n;
        }

        if (n.returnLabel() != null && !n.returnLabel().isDisambiguated()) {
            // the return label node hasn't been disambiguated yet
            return n;
        }

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
        jci.setFormalTypes(formalTypes);

        Label Li; // start label
        boolean isDefaultPCBound = false;
        DefaultSignature ds = jts.defaultSignature();
        if (n.startLabel() == null) {
            Li = ds.defaultPCBound(n.position(), n.name());
            isDefaultPCBound = true;
        } 
        else {
            Li = n.startLabel().label();
        }
        jci.setPCBound(Li, isDefaultPCBound);

        Label Lr; // return label
        boolean isDefaultReturnLabel = false;
        if (n.returnLabel() == null) {
            Lr = ds.defaultReturnLabel(n);
            isDefaultReturnLabel = true;
        }
        else {
            Lr = n.returnLabel().label();
        }        
        jci.setReturnLabel(Lr, isDefaultReturnLabel);

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
        jci.setThrowTypes(throwTypes);

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
        jci.setConstraints(constraints);

        return n.constructorInstance(jci);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {

        Node n = super.typeCheck(tc);    
        JifConstructorDecl_c jcd = (JifConstructorDecl_c)n;
        jcd.checkConstructorCall(tc);

        return jcd;
    }

    /**
     * Checks that if there is an explicit constructor call in the constructor
     * body that the call is all right.
     * 
     * In particular, if this is a java class or one of the ancestors of this 
     * class is "untrusted" then the explicit constructor call must be 
     * the first statement in the constructor body.
     * 
     * Moreover, if this is a Jif class, but the superclass is not a Jif class,
     * then first statement must be a default constructor call.
     * @throws SemanticException
     */
    private void checkConstructorCall(TypeChecker tc) throws SemanticException {

        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();

        ClassType ct = tc.context().currentClass();

        // ignore java.lang.Object
        if (ts.equals(ct, ts.Object())) 
            return;

        ClassType untrusted = ts.hasUntrustedAncestor(ct);
        if (!ts.isJifClass(ct)) {
            // If ct is not a jif class, then the first statement of the body
            // had better be a constructor call (which is the normal Java
            // rule).
            checkFirstStmtConstructorCall("The first statement of a constructor " +
                                          "of a Java class must be a constructor call.", true, false);
        }
        else if (ts.isJifClass(ct) && untrusted != null) {
            // If ct is a Jif class, but the super class is an
            // untrusted Java class, then the first statement of the body
            // must be an explicit call to the default super constructor:
            // "super()". If it wasn't, then due to the translation of 
            // Jif constructors, a malicious (non-Jif) superclass access
            // final fields before they have been initialized.
            checkFirstStmtConstructorCall("The first statement of a constructor " +
                                          "of a Jif class with an untrusted Java superclass " +
                                          "must be an explicit call to the default super constructor," +
                                          "\"super()\".", false, true);
        }        
        else if (ts.isJifClass(ct) && !ts.isJifClass(ct.superType())) {
            // this is a Jif class, but it's superclass is a trusted Java class.
            // The first statement must either be a "this(...)" constructor 
            // call, or a "super()" call. That is, the constructor cannot
            // call any super constructor other than the default constuctor,
            // since in translation, the Jif class has no opportunity to
            // marshal the arguments before the super constructor call 
            // happens.
            checkFirstStmtConstructorCall("The first statement of a " +
                                          "constructor of a Jif class with a Java superclass " +
                                          "must be either a \"this(...)\" constructor call, or " +
                                          "a call to the default super constructor, " +
                                          "\"super()\".", 
                                          true, true);
        }
    }

    /**
     * 
     * @param message
     * @param allowThisCalls if false then first statement must be super(); if true then it may be a call to this(...) or super().
     * @throws SemanticException
     */
    private void checkFirstStmtConstructorCall(String message, 
            boolean allowThisCalls,
            boolean superCallMustBeDefault) 
    throws SemanticException {
        if (body().statements().size() < 1) {
            throw new SemanticException("Empty constructor body.", position());
        }
        Stmt s = (Stmt)body().statements().get(0);
        if (!(s instanceof ConstructorCall)) {
            throw new SemanticException(message, position());
        }

        ConstructorCall cc = (ConstructorCall)s;
        if (!allowThisCalls && cc.kind() == ConstructorCall.THIS) {
            throw new SemanticException(message, position());                
        }

        if (superCallMustBeDefault && cc.kind() == ConstructorCall.SUPER &&
                cc.arguments().size() > 0) {
            throw new SemanticException(message, position());                
        }

    }
}
