package jif.types;

import java.util.*;

import jif.types.hierarchy.LabelEnv;
import jif.types.hierarchy.LabelEnv_c;
import jif.types.hierarchy.PrincipalHierarchy;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.label.PairLabel;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Local;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;

/** An implementation of the <code>JifContext</code> interface.
 */
public class JifContext_c extends Context_c implements JifContext
{
    private final TypeSystem jlts;
    private final JifTypeSystem jifts;

    private LabelEnv_c env; // label environment (ph, constraints known to be true)

    private Set auth;
    private Label pc; //internal pc
    private Label currentCodePCBound; //external pc
    
    /**
     * Map of local variables that have been endorsed
     * using a checked endorse statement
     */
    protected Map<LocalInstance, Label> checkedEndorsements;


    /**
     * Map from JifContext_c.Key (pairs of Branch.Kind and String) to Labels. 
     */
    protected Map gotos;

    protected boolean checkingInits;
    protected boolean inConstructorCall;
    protected Label constructorReturnLabel;

    protected JifContext_c(JifTypeSystem ts, TypeSystem jlts) {
        super(ts);
        this.jlts = jlts;
        this.jifts = ts;
        this.env = (LabelEnv_c)ts.createLabelEnv();
    }

    public Object copy() {
        JifContext_c ctxt = (JifContext_c)super.copy();
        if (auth != null) {
            ctxt.auth = new LinkedHashSet(auth);
        }
        if (gotos != null) {
            ctxt.gotos = new HashMap(gotos);
        }
        return ctxt;        
    }

    public VarInstance findVariableSilent(String name) {
        VarInstance vi = super.findVariableSilent(name);

        if (vi != null) {
            return vi;
        }

        // Principals are masquerading as classes.   Find the class
        // and pull the principal out of the class.  Ick.
        ClassType principal;

        try {
            principal = (ClassType)jlts.typeForName(jifts.PrincipalClassName());
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Cannot find " + jifts.PrincipalClassName() + " class.", e);
        }

        Named n;
        try {
            // Look for the principal only in class files.
            String className = "jif.principals." + name;
            n = jlts.loadedResolver().find(className);
        } catch (SemanticException e) {
            return null;
        }

        if (n instanceof Type) {
            Type t = (Type) n;
            if (t.isClass()) {

                if (jlts.isSubtype(t.toClass(), principal)) {
                    return jifts.principalInstance(null,
                                                   jifts.externalPrincipal(null, name));
                }                
            }
        }
        return null;
    }

    public LabelEnv labelEnv() {
        return env;
    }

    /*
     * Called when the label environment is about to be modified.
     * This makes sure that we are dealing with a copy of the environment,
     * and do not accidentally modify the parent's environment
     */
    protected void envModification() {
        JifContext_c jifOuter = (JifContext_c)this.outer;
        if (jifOuter != null && jifOuter.env == this.env) {
            // the outer's label environment points to the same object as this
            // one. Create a new label env.
            this.env = this.env.copy();
        }
    }

    public void addAssertionLE(Label L1, Label L2) {
        envModification();
        env.addAssertionLE(L1, L2);
    }

    public void addDefinitionalAssertionEquiv(Label L1, Label L2) {
        addDefinitionalAssertionEquiv(L1, L2, false);
    }
    /**
     * Adds the assertion to this context, and all outer contexts up to
     * the method/constructor/initializer level
     * @param L1
     * @param L2
     */
    public void addDefinitionalAssertionEquiv(Label L1, Label L2, boolean addToClass) {
        // don't bother copying the environment, as we'll be
        // propogating it upwards anyway.
        // envModification();
        env.addEquiv(L1, L2);
        JifContext_c jc = this;
        LabelEnv_c lastEnvAddedTo = env;
        while (jc != null && (!jc.isCode() || addToClass)) {
            jc = (JifContext_c)jc.pop();
            if (jc != null && jc.scope == this.scope && jc.env != lastEnvAddedTo) {
                // only add to env we haven't seen yet, and
                // envs in the scope of the same class as us.
                jc.env.addEquiv(L1, L2);
                lastEnvAddedTo = jc.env;
            }            
        }
    }

    public void addDefinitionalAssertionEquiv(AccessPath p, AccessPath q) {
        // don't bother copying the environment, as we'll be
        // propogating it upwards anyway.
        // envModification();
        env.addEquiv(p, q);
        JifContext_c jc = this;
        LabelEnv_c lastEnvAddedTo = env;
        while (!jc.isCode()) {
            jc = (JifContext_c)jc.pop();
            if (jc != null && jc.scope == this.scope && jc.env != lastEnvAddedTo) {
                // only add to env we haven't seen yet, and
                // envs in the scope of the same class as us.
                jc.env.addEquiv(p, q);
                lastEnvAddedTo = jc.env;
            }            
        }
    }

    public void addEquiv(Label L1, Label L2) {
        envModification();
        env.addEquiv(L1, L2);
    }
    public void addEquiv(Principal p1, Principal p2) {
        envModification();
        env.addEquiv(p1, p2);
    }
    public void addActsFor(Principal p1, Principal p2) {
        envModification();
        env.addActsFor(p1, p2);
    }
    public void addEquiv(AccessPath p, AccessPath q) {
        //envModification(); XXX add the equivalence to the current environment.
//        env.addEquiv(p, q);
        // don't bother copying the environment, as we'll be
        // propogating it upwards anyway.
        // envModification();
        env.addEquiv(p, q);
        JifContext_c jc = this;
        LabelEnv_c lastEnvAddedTo = env;
        boolean lastJCBlock = (jc.kind == Context_c.BLOCK);
        while (!jc.isCode() && !lastJCBlock) {
            jc = (JifContext_c)jc.pop();
            if (jc != null && jc.scope == this.scope && jc.env != lastEnvAddedTo) {
                // only add to env we haven't seen yet, and
                // envs in the scope of the same class as us.
                jc.env.addEquiv(p, q);
                lastEnvAddedTo = jc.env;
            }            
            lastJCBlock = (jc.kind == Context_c.BLOCK);
        }
        
    }
    /**
     * Adds the assertion to this context, and all outer contexts up to
     * the method/constructor/initializer level
     */
    public void addDefinitionalEquiv(Principal p1, Principal p2) {
        // don't bother copying the environment, as we'll be
        // propogating it upwards anyway.
        // envModification();
        this.addEquiv(p1, p2);
        JifContext_c jc = this;
        LabelEnv_c lastEnvAddedTo = env;
        while (!jc.isCode()) {
            jc = (JifContext_c)jc.pop();
            if (jc != null && jc.scope == this.scope && jc.env != lastEnvAddedTo) {
                // only add to env we haven't seen yet, and
                // envs in the scope of the same class as us.
                jc.env.addEquiv(p1, p2);
                lastEnvAddedTo = jc.env;
            }            
        }
    }

    public void clearPH() {
        envModification();
        env.ph().clear();
    }

    static class Key {
        polyglot.ast.Branch.Kind kind;
        String label;

        Key(polyglot.ast.Branch.Kind kind, String label) {
            this.kind = kind;
            this.label = label;
        }

        public int hashCode() {
            return kind.hashCode() + (label == null ? 0 : label.hashCode());
        }
        public String toString() {
            return kind.toString() + label;
        }

        public boolean equals(Object o) {
            if (o instanceof Key) {
                Key that = (Key)o;
                return this.kind.equals(that.kind) && (this.label == that.label || (this.label != null && this.label.equals(that.label))); 
            }
            return false;
        }
    }

    public Label gotoLabel(polyglot.ast.Branch.Kind kind, String label) {
        if (gotos == null) return null;
        return (Label) gotos.get(new Key(kind, label));
    }

    public void gotoLabel(polyglot.ast.Branch.Kind kind, String label, Label L) {
        if (gotos == null) gotos = new HashMap();
        gotos.put(new Key(kind, label), L);
    }

    public Label currentCodePCBound() { return currentCodePCBound; }
    public void setCurrentCodePCBound(Label currentCodePCBound) { 
        this.currentCodePCBound = currentCodePCBound; 
    }

    public Label pc() { return pc; }
    public void setPc(Label pc, LabelChecker lc) { this.pc = pc; }

    public Set authority() { return auth; }
    public void setAuthority(Set auth) { this.auth = auth; }

    public PrincipalHierarchy ph() { return env.ph(); }

    public Label authLabel() {
        Set auth = authority();

        Set labels = new LinkedHashSet();
        for (Iterator i = auth.iterator(); i.hasNext(); ) {
            Principal p = (Principal) i.next();
            PairLabel pl = jifts.pairLabel(p.position(),
                                           jifts.readerPolicy(p.position(),
                                                              p,
                                                              jifts.topPrincipal(p.position())),
                                                              jifts.topIntegPolicy(p.position()));
            labels.add(pl);
        }

        if (labels.isEmpty()) {
            return jifts.bottomLabel(currentCode().position());
        }
        Label L = jifts.joinLabel(currentCode().position(), 
                                  labels);
        return L;
    }

    public Label authLabelInteg() {
        Set labels = new LinkedHashSet();
        for (Iterator i = authority().iterator(); i.hasNext(); ) {
            Principal p = (Principal) i.next();
            PairLabel pl = jifts.pairLabel(p.position(),
                                           jifts.bottomConfPolicy(p.position()),
                                           jifts.writerPolicy(p.position(),
                                                              p,
                                                              jifts.topPrincipal(p.position())));
            labels.add(pl);
        }

        if (labels.isEmpty()) {
            return jifts.topLabel(currentCode().position());
        }
        Label L = jifts.meetLabel(currentCode().position(), 
                                  labels);
        return L;
    }

    public boolean checkingInits() {
        return checkingInits;
    }

    public void setCheckingInits(boolean checkingInits) {
        this.checkingInits = checkingInits;
    }

    public Label constructorReturnLabel() {
        return constructorReturnLabel;
    }

    public void setConstructorReturnLabel(Label Lr) {
        this.constructorReturnLabel = Lr;
    }

    public Context pushConstructorCall() {
        JifContext_c A = (JifContext_c)pushStatic();
        A.inConstructorCall = true;
        return A;
    }


    public Context pushClass(ParsedClassType classScope, ClassType type) {
        JifContext_c jc = (JifContext_c)super.pushClass(classScope, type);
        // force a new label environment
        jc.envModification();
        return jc;
    }

    public Context pushCode(CodeInstance ci) {
        JifContext_c jc = (JifContext_c)super.pushCode(ci);
        // force a new label environment
        jc.envModification();
        return jc;
    }
//    public Context pushBlock() {
//        JifContext_c jc = (JifContext_c)super.pushBlock();
//        // force a new label environment
//        jc.envModification();
//        return jc;
//    }

    public boolean inConstructorCall() {
        return this.inConstructorCall;
    }

    public PathMap pathMapForLocal(LocalInstance li, LabelChecker lc) {
        JifTypeSystem ts = lc.jifTypeSystem();
        Label L = null;
        if (checkedEndorsements != null && checkedEndorsements.containsKey(li)) {
            L = checkedEndorsements.get(li);
        }
        else {
            L = ts.labelOfLocal(li, this.pc());
        }

        PathMap X = ts.pathMap();
        X = X.N(this.pc());
        X = X.NV(lc.upperBound(L, this.pc()));
        return X;
    }

    public boolean updateAllowed(Expr e) {
        if (e instanceof Local && checkedEndorsements != null) {
            // cannot update locals that are involved in a checked endorse
            return !checkedEndorsements.containsKey(((Local)e).localInstance());
        }
        return true;
    }

    public void addCheckedEndorse(LocalInstance li, Label downgradeTo) {
        if (this.checkedEndorsements == null) {
            this.checkedEndorsements = new HashMap<LocalInstance, Label>();
        }
        else {
            this.checkedEndorsements = new HashMap<LocalInstance, Label>(this.checkedEndorsements);            
        }
        this.checkedEndorsements.put(li, downgradeTo);
    }
}
