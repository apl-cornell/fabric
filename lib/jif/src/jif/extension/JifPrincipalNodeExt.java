package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.ast.PrincipalNode;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class JifPrincipalNodeExt extends JifExprExt {
    public JifPrincipalNodeExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException {
        PrincipalNode pn = (PrincipalNode)node();

        JifContext A = lc.jifContext();
        A = (JifContext)pn.del().enterScope(A);
        JifTypeSystem ts = lc.jifTypeSystem();

        List throwTypes = new ArrayList(pn.del().throwTypes(ts));

        Principal p = pn.principal();
        // make sure the principal is runtime representable
        if (!p.isRuntimeRepresentable()) {
            throw new SemanticException(
                                        "A principal used in an expression must be representable at runtime.",
                                        pn.position());
        }

        PathMap X1 = p.labelCheck(A, lc);
        throwTypes.removeAll(p.throwTypes(ts));
        A = (JifContext)A.pop();

        checkThrowTypes(throwTypes);
        return updatePathMap(pn, X1);
    }

}