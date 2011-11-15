package jif.ast;

import java.util.*;
import java.util.Iterator;
import java.util.List;

import jif.types.JifTypeSystem;
import jif.types.label.Policy;
import jif.types.principal.Principal;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.*;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.*;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

/** An implementation of the <code>PolicyLabel</code> interface.
 */
public class ReaderPolicyNode_c extends PolicyNode_c
{
    protected List principals;

    public ReaderPolicyNode_c(Position pos, PrincipalNode owner, List principals) {
	super(pos, owner);
        this.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
    }

    public List principals() {
        return this.principals;
    }

    public PolicyNode principals(List principals) {
        ReaderPolicyNode_c n = (ReaderPolicyNode_c) copy();
        n.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
        return n;
    }

    
    protected Policy producePolicy(JifTypeSystem ts, Principal owner, List principals) {
        return ts.readerPolicy(position(), owner, principals);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) ar.typeSystem();
        if (!owner.isDisambiguated()) {
            ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
            return this;
        }

        Principal o = owner.principal();
        if (o == null) throw new InternalCompilerError("null owner " + owner.getClass().getName() + " " + owner.position());

        List l = new LinkedList();

        for (Iterator i = this.principals.iterator(); i.hasNext(); ) {
            PrincipalNode r = (PrincipalNode) i.next();
            if (!r.isDisambiguated()) {
                ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
                return this;
            }
            l.add(r.principal());
        }
        this.policy = producePolicy(ts, o, l);
        return this;
    }

    protected ReaderPolicyNode_c reconstruct(PrincipalNode owner, List principals) {
        if (owner != this.owner || ! CollectionUtil.equals(principals, this.principals)) {
            ReaderPolicyNode_c n = (ReaderPolicyNode_c) copy();
            n.owner = owner;
            n.principals = TypedList.copyAndCheck(principals, PrincipalNode.class, true);
            return n;
        }

        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        PrincipalNode owner = (PrincipalNode) visitChild(this.owner, v);
        List readers = visitList(this.principals, v);
        return reconstruct(owner, readers);
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        print(owner, w, tr);

        w.write("->");

	for (Iterator i = this.principals.iterator(); i.hasNext(); ) {
	    PrincipalNode n = (PrincipalNode) i.next();
            print(n, w, tr);
            if (i.hasNext()) {
                w.write(";");
                w.allowBreak(0, " ");
            }
        }
    }
}
