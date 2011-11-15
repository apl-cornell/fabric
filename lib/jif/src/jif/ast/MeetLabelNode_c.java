package jif.ast;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.label.ConfPolicy;
import jif.types.label.Policy;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

/** An implementation of the <code>JoinLabel</code> interface.
 */
public class MeetLabelNode_c extends AmbLabelNode_c implements MeetLabelNode
{
    protected List components;

    public MeetLabelNode_c(Position pos, List components) {
        super(pos);
        this.components = TypedList.copyAndCheck(components, Node.class, true);
    }

    public List components() {
        return this.components;
    }

    public MeetLabelNode components(List components) {
        MeetLabelNode_c n = (MeetLabelNode_c) copy();
        n.components = TypedList.copyAndCheck(components, Node.class, true);
        return n;
    }

    protected MeetLabelNode_c reconstruct(List components) {
        if (! CollectionUtil.equals(components, this.components)) {
            MeetLabelNode_c n = (MeetLabelNode_c) copy();
            n.components = TypedList.copyAndCheck(components, Node.class, true);
            return n;
        }

        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        List components = visitList(this.components, v);
        return reconstruct(components);
    }

    public Node disambiguate(AmbiguityRemover sc) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) sc.typeSystem();
        JifNodeFactory nf = (JifNodeFactory) sc.nodeFactory();

        Set labels = new LinkedHashSet();
        Set policies = new LinkedHashSet();

        boolean policyTypeIsConf = false;

        for (Iterator i = this.components.iterator(); i.hasNext(); ) {
            Node n = (Node) i.next();
            if (!n.isDisambiguated()) {
                sc.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }
            if (n instanceof PolicyNode) {
                Policy pol = ((PolicyNode)n).policy();
                if (policies.isEmpty()) {
                    policyTypeIsConf = (pol instanceof ConfPolicy);
                }
                else {
                    if (policyTypeIsConf != (pol instanceof ConfPolicy)) {
                        throw new SemanticException("Incompatible kinds of " +
                                                    "policies for the meet expression.", position);
                    }
                }
                policies.add(pol);
            }
            else if (n instanceof LabelNode) {
                labels.add(((LabelNode)n).label());                
            }
            else throw new InternalCompilerError("Unexpected node " + n);
        }
        if (!labels.isEmpty() && !policies.isEmpty()) {
            throw new SemanticException("Cannot take the meet of labels and policies.", position);            
        }
        if (!policies.isEmpty()) {
            Policy mp;
            if (policyTypeIsConf) {
                mp = ts.meetConfPolicy(position, policies);                
            }
            else {
                mp = ts.meetIntegPolicy(position, policies);
            }
            return nf.PolicyNode(position, mp);
        }
        return nf.CanonicalLabelNode(position(), ts.meetLabel(position(), labels));
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        for (Iterator i = this.components.iterator(); i.hasNext(); ) {
            LabelNode n = (LabelNode) i.next();
            print(n, w, tr);
            if (i.hasNext()) {
                w.write(";");
                w.allowBreak(0, " ");
            }
        }
    }
}
