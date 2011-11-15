package jif.visit;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.TypeSystem;
import polyglot.visit.TypeChecker;

public class JifTypeChecker extends TypeChecker
{
    /**
     * Should type checking attempt to infer missing class parameters?
     */
    private boolean inferClassParameters = false;
    
    private boolean disambiguationInProgress = false;
    
    public JifTypeChecker(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
    }
    
    public boolean inferClassParameters() {
        return this.inferClassParameters;
    }

    public JifTypeChecker(Job job, TypeSystem ts, NodeFactory nf, boolean dip) {
        this(job, ts, nf);
        disambiguationInProgress = dip;
    }
    
    public boolean disambiguationInProgress() {
        return disambiguationInProgress;
    }
    
    
    public JifTypeChecker inferClassParameters(boolean inferClassParameters) {
        if (this.inferClassParameters == inferClassParameters) return this;
        JifTypeChecker jtc = (JifTypeChecker)this.copy();
        jtc.inferClassParameters = inferClassParameters;
        return jtc;
    }
       
}
