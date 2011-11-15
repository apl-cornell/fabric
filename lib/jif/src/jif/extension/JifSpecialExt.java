package jif.extension;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.JifClassType;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.ast.Special;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Special</code> node. 
 * 
 *  @see polyglot.ast.Special
 */
public class JifSpecialExt extends JifExprExt
{
    public JifSpecialExt(ToJavaExt toJava) {
        super(toJava);
    }

    /*
    protected Param paramToParam(ParamInstance vi, TypeChecker visitor)
	throws SemanticException {
	JifTypeSystem ts = (JifTypeSystem) visitor.typeSystem();
	JifNodeFactory nf = (JifNodeFactory) visitor.nodeFactory();
	Position pos = node().position();

	if (vi.isCovariantLabel()) {
	    // <covariant label uid> => <covariant-label uid>
	    Label L = ts.covariantLabel(pos, vi.uid());
	    return L;
	}

	if (vi.isInvariantLabel()) {
	    // <param label uid> => <label-param uid>
	    Label L = ts.paramLabel(pos, vi.uid());
	    return L;
	}

	if (vi.isPrincipal()) {
	    // <param principal uid> => <principal-param uid>
	    Principal p = ts.principalParam(pos, vi.uid());
	    return p;
	}

	throw new SemanticException("Unrecognized parameter type for " + vi,
		                    pos);
    }
     */

    /** label check the special expression.
     * 
     *  X.nv = X.n = A.pc ( the expression itself does not contain any information.)
     */
    public Node labelCheck(LabelChecker lc) throws SemanticException {
        Special se = (Special) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) se.del().enterScope(A);

        JifClassType ct = (JifClassType) A.currentClass();

        se = (Special)se.type(ts.labeledType(se.position(), ct, ct.thisLabel()));

        PathMap X = ts.pathMap();
        X = X.N(A.pc());

        // X(this).NV = this_label, which is upper-bounded
        // by the begin label. 
        X = X.NV(lc.upperBound(ct.thisLabel(), A.pc()));	

        return updatePathMap(se, X);
    }
}
