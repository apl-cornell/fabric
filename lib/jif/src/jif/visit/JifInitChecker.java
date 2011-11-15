package jif.visit;

import java.util.Iterator;
import java.util.Set;

import jif.types.JifClassType;
import jif.types.JifTypeSystem;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.visit.FlowGraph;
import polyglot.visit.InitChecker;

/**
 * Override the init checker, since type nodes may now mention local variables.
 */
public class JifInitChecker extends InitChecker {
    public JifInitChecker(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
    }

    
    protected void checkOther(FlowGraph graph, 
                              Node n, 
                              DataFlowItem dfIn,
                              DataFlowItem dfOut) 
    throws SemanticException {
        if (n instanceof TypeNode) {
            // need to check type nodes for uses of locals.
            TypeNode tn = (TypeNode)n;
            Type t = tn.type();
            if (t instanceof JifClassType) {
                JifTypeSystem ts = (JifTypeSystem)t.typeSystem();
                Set lis = ts.labelTypeCheckUtil().localInstancesUsed((JifClassType)t);
                for (Iterator iter = lis.iterator(); iter.hasNext(); ) {
                    LocalInstance li = (LocalInstance)iter.next();
                    checkLocalInstanceInit(li, dfIn, tn.position());
                }
            }
        }
    }
}
