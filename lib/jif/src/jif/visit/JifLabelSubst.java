package jif.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;

import polyglot.visit.*;
import jif.ast.*;
import jif.types.*;
import polyglot.frontend.Job;

/**
 * This visitor substitutes labels for each variable in the path maps of
 * each JifExt object.
 *
 * By subclassing ContextVisitor, type objects are rebuilt automatically.
 */
public class JifLabelSubst extends ContextVisitor
{
    protected JifTypeSystem ts;
    protected final Solver solver;
    protected ErrorQueue eq;
    protected VarMap bounds;
    
    public JifLabelSubst(Job job, JifTypeSystem ts, NodeFactory nf, Solver solver) {
        super(job, ts, nf);
        this.solver = solver;
        this.ts = ts;
        this.eq = job.compiler().errorQueue();
    }
    
    public Node leaveCall(Node old, Node n, NodeVisitor v)
    throws SemanticException {
        try {
            solve();
        }
        catch (SemanticException e) {
            eq.enqueue(ErrorInfo.SEMANTIC_ERROR, e.getMessage(), e.position());
            return n;
        }
        n = updateNode(n);
        
        return n;
    }
    
    protected void solve() throws SemanticException {
        bounds = solver.solve();
    }
    
    protected Node updateNode(Node n) throws SemanticException {
        if (n instanceof ProcedureDecl) {
            JifProcedureInstance pi = (JifProcedureInstance)((ProcedureDecl)n).procedureInstance();
            pi.subst(bounds);
        }
        if (n instanceof FieldDecl) {
            JifFieldInstance fi = (JifFieldInstance)((FieldDecl)n).fieldInstance();
            fi.subst(bounds);
        }
        if (n instanceof LocalDecl) {
            JifLocalInstance li = (JifLocalInstance)((LocalDecl)n).localInstance();
            li.subst(bounds);
        }
        if (n instanceof Formal) {
            JifLocalInstance li = (JifLocalInstance)((Formal)n).localInstance();
            li.subst(bounds);
        }
        if (n instanceof Local) {
            // update the type of the local in case the local instance changed during solving.
            JifLocalInstance li = (JifLocalInstance)((Local)n).localInstance();
            n = ((Local)n).type(li.type());
        }         
        
        if (n instanceof Call) {
            // update the method instance in case the type of the receiver changed
            // during solving
            Call c = (Call)n;
            MethodInstance mi;
            Receiver target = c.target();
            mi = ts.findMethod(c.target().type().toReference(), 
                    c.methodInstance().name(), 
                    c.methodInstance().formalTypes(), 
                    c.target().type().toClass());
                
            n = c.methodInstance(mi);
        }

        if (JifUtil.jifExt(n) != null) {
            PathMap X = Jif_c.getPathMap(n);
            
            if (X != null) {
                n = Jif_c.updatePathMap(n, X.subst(bounds));
            }
        }
        if (n instanceof CanonicalTypeNode) {
            CanonicalTypeNode tn = (CanonicalTypeNode) n;
            n = tn.type(bounds.applyTo(tn.type()));
        }
        
        if (n instanceof CanonicalLabelNode) {
            CanonicalLabelNode ln = (CanonicalLabelNode) n;
            n = ln.label(bounds.applyTo(ln.label()));
        }
        return n;
    }    
}
