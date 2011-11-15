package jif.visit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jif.ExtensionInfo;
import jif.JifScheduler;
import jif.ast.JifUtil;
import jif.extension.JifFieldDeclExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.label.VarLabel;
import polyglot.ast.*;
import polyglot.frontend.CyclicDependencyException;
import polyglot.frontend.Job;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ContextVisitor;
import polyglot.visit.NodeVisitor;

/** A visitor used to resolving field labels. We want to resolve
 *  field labels of all the classes before the label checking pass,
 *  because these field labels might be included in other labels, and 
 *  thus need to be resolved first.
 *  
 *  This visitor also adds dependencies so that the label checking pass for
 *  this job will not run until the FieldLabelResolver for all jobs it is 
 *  dependent on has run.
 */
public class FieldLabelResolver extends ContextVisitor
{
    private final Job job;
    private final JifTypeSystem ts;
    private VarMap bounds;
    private Map fieldVarBounds;
    
    public FieldLabelResolver(Job job, JifTypeSystem ts, NodeFactory nf) {
	super(job, ts, nf);
	this.job = job;
	this.ts = ts;
    }

    public NodeVisitor enterCall(Node n) throws SemanticException {
        if (n instanceof ClassDecl) {
            this.fieldVarBounds = new HashMap();
        }
        
	if (n instanceof ClassBody) {
	    // labelcheck the class body
	    ClassBody d = (ClassBody) n;
	    labelCheckClassBody(d);
	}
	
	if (n instanceof Field) {
	    // this class uses field f. Make sure that the field label resolver
	    // pass for the container of f is run before we run label checking.
	    FieldInstance fi = ((Field)n).fieldInstance();

// Jif Dependency bugfix
	    if(fi == null) {
	        Field f = (Field) n;
            JifScheduler sched = (JifScheduler) this.job().extensionInfo().scheduler();
            Type tp = ts.unlabel(f.target().type());
            Job next;
            if (tp instanceof ParsedClassType) {
                ParsedClassType pct = (ParsedClassType)tp;
                next = pct.job(); 
            } else {
                next = this.job();
            }
            Goal g = sched.TypeChecked(next);
            throw new MissingDependencyException(g);
	    }
	    
            JifScheduler scheduler = (JifScheduler)typeSystem().extensionInfo().scheduler();
            
            Type ct = fi.container();
            while (ct instanceof JifSubstType) {
                ct = ((JifSubstType)ct).base();
            }
                      
            if (ct instanceof ParsedClassType) {
                // the container of fi is a class that is being compiled in
                // this compiler execution
                ParsedTypeObject pct = (ParsedClassType)ct;
                if (pct.job() != null && pct.job() != this.job) {
                    // add a dependency from the label checking of this.job to the
                    // field label inference of pct
                    try {
                        scheduler.addPrerequisiteDependency(scheduler.LabelsChecked(this.job), 
                                                            scheduler.FieldLabelInference(pct.job()));
                    }
                    catch (CyclicDependencyException e) {
                        throw new InternalCompilerError(e);
                    }
                }
            }
	}
	
        return this;
    }

    private void labelCheckClassBody(ClassBody d) throws SemanticException {
        JifClassType ct = (JifClassType) context().currentClassScope();

        
        LabelChecker lc = ((ExtensionInfo)ct.typeSystem().extensionInfo()).createLabelChecker(job, false, false, false); 

        if (lc == null) {
            throw new InternalCompilerError("Could not label check " +
                                            ct + ".", d.position());
        }

        for (Iterator i = d.members().iterator(); i.hasNext(); ) {
            ClassMember m = (ClassMember) i.next();

            if (m instanceof FieldDecl) {
                JifFieldDeclExt ext = (JifFieldDeclExt) JifUtil.jifExt(m);
                ext.labelCheckField(lc, ct);
            }
        }

        Solver solver = lc.solver();
        this.bounds = solver.solve();        
    }

    public Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
        if (n instanceof FieldDecl) {
            FieldDecl f = (FieldDecl) n;
            JifFieldInstance fi = (JifFieldInstance) f.fieldInstance();
            if (fi.label() instanceof VarLabel) {
                this.fieldVarBounds.put(fi.label(), 
                                        bounds.boundOf((VarLabel)fi.label()));
            }

            Type lbledType = ts.labeledType(f.declType().position(), 
                                            ts.unlabel(f.declType()), 
                                            fi.label());
            return f.type(f.type().type(lbledType));
        
        }

        if (n instanceof ClassBody) {
            // need to go through the entire class body and replace the 
            // variables that we have now solved for.
            LabelSubstitutionVisitor lsv = 
                new LabelSubstitutionVisitor(new FieldVarLabelSubst(this.fieldVarBounds), 
                                             false);
            n =  n.del().visitChildren(lsv);

        }
        return n;
    }
    
    private static class FieldVarLabelSubst extends LabelSubstitution {
        Map map;
        public FieldVarLabelSubst(Map fieldVarBounds) {
            map = fieldVarBounds;
        }
        public Label substLabel(Label L) throws SemanticException {
            Label b = (Label)map.get(L);            
            if (b != null) {
                return b;
            }
            return L;
        }        
    }
}
