package jif.ast;

import java.util.*;

import jif.types.JifTypeSystem;
import jif.types.label.*;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.*;
import polyglot.visit.*;

/** An implementation of the <code>JoinLabel</code> interface.
 */
public class JoinLabelNode_c extends AmbLabelNode_c implements JoinLabelNode
{
    protected List components;

    public JoinLabelNode_c(Position pos, List components) {
        super(pos);
        this.components = TypedList.copyAndCheck(components, Node.class, true);
    }

    public List components() {
        return this.components;
    }

    public JoinLabelNode components(List components) {
        JoinLabelNode_c n = (JoinLabelNode_c) copy();
        n.components = TypedList.copyAndCheck(components, Node.class, true);
        return n;
    }

    protected JoinLabelNode_c reconstruct(List components) {
        if (! CollectionUtil.equals(components, this.components)) {
            JoinLabelNode_c n = (JoinLabelNode_c) copy();
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

        Set s = new LinkedHashSet();

        Set confPolicies = new LinkedHashSet();
        Set integPolicies = new LinkedHashSet();
        for (Iterator i = this.components.iterator(); i.hasNext(); ) {
            Node n = (Node) i.next();
            if (!n.isDisambiguated()) {
                sc.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }
            if (n instanceof PolicyNode) {
                Policy pol = ((PolicyNode)n).policy();
                if (pol instanceof ConfPolicy) {
                    confPolicies.add(pol);
                }
                else {
                    integPolicies.add(pol);
                }
            }
            else if (n instanceof LabelNode) {
                s.add(((LabelNode)n).label());

            }
            else {
                throw new InternalCompilerError("Unexpected node: " + n);
            }
        }

        if (!confPolicies.isEmpty() || !integPolicies.isEmpty()) {                
            ConfPolicy cp = ts.bottomConfPolicy(position());
            IntegPolicy ip = ts.topIntegPolicy(position());
            if (!confPolicies.isEmpty()) {
                cp = ts.joinConfPolicy(position(), confPolicies);
            }
            if (!integPolicies.isEmpty()) {
                ip = ts.joinIntegPolicy(position(), integPolicies);
            }
            PairLabel pl = ts.pairLabel(position(), cp, ip);
            s.add(pl);           
        }

        return nf.CanonicalLabelNode(position(), ts.joinLabel(position(), s));
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        for (Iterator i = this.components.iterator(); i.hasNext(); ) {
            Node n = (Node) i.next();
            print(n, w, tr);
            if (i.hasNext()) {
                w.write(";");
                w.allowBreak(0, " ");
            }
        }
    }
}
