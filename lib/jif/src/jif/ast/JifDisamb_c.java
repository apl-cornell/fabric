package jif.ast;

import jif.types.JifParsedPolyType;
import jif.types.JifTypeSystem;
import jif.types.ParamInstance;
import jif.types.PrincipalInstance;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.Disamb_c;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.FieldInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.VarInstance;
import polyglot.util.InternalCompilerError;

/**
 * Utility class which is used to disambiguate ambiguous
 * AST nodes (Expr, Type, Receiver, Qualifier, Prefix).
 *
 * We need to override the default functionality of <code>Disamb_c</code>
 * to deal with using the correct instantiations of polymorphic classes.
 */
public class JifDisamb_c extends Disamb_c
{
    protected Node disambiguateVarInstance(VarInstance vi) throws SemanticException {
        Node n = super.disambiguateVarInstance(vi);
        if (n != null) {
            return n;
        }
        if (vi instanceof PrincipalInstance) {
            PrincipalInstance pi = (PrincipalInstance)vi;
            JifNodeFactory jnf = (JifNodeFactory)nf;
            return jnf.CanonicalPrincipalNode(pos, pi.principal());
        }
        if (vi instanceof ParamInstance) {
            ParamInstance pi = (ParamInstance)vi;
            JifNodeFactory jnf = (JifNodeFactory)nf;
            JifTypeSystem ts = (JifTypeSystem)vi.typeSystem();

            if (pi.isPrincipal()) {
                // <param principal uid> => <principal-param uid>
                Principal p = ts.principalParam(pos, pi);
                return jnf.CanonicalPrincipalNode(pos, p);
            }
            else if (pi.isInvariantLabel()) {
                Label l = ts.paramLabel(pos, pi);
                return jnf.LabelExpr(pos, l);
            }
            else if (pi.isCovariantLabel()) {
                Label l = ts.covariantLabel(pos, pi);
                return jnf.LabelExpr(pos, l);
            }

            throw new InternalCompilerError("Unexpected param " + pi,
                                            pos);
        }
        return null;
    }

    /**
     * Override superclass functionality to avoid returning an
     * uninstantiated polymorphic class.
     */
    protected Receiver makeMissingFieldTarget(FieldInstance fi) throws SemanticException {
        if (fi.flags().isStatic()) {
            JifTypeSystem jts = (JifTypeSystem)fi.typeSystem();
            Type container = fi.container();
            if (container instanceof JifParsedPolyType) {
                JifParsedPolyType jppt = (JifParsedPolyType)container;
                if (jppt.params().size() > 0) {
                    // return the "null instantiation" of the base type,
                    // to ensure that all TypeNodes contain either
                    // a JifParsedPolyType with zero params, or a
                    // JifSubstClassType
                    return nf.CanonicalTypeNode(pos, jts.nullInstantiate(pos, jppt.instantiatedFrom()));
                }
            }
        }

        return super.makeMissingFieldTarget(fi);
    }

}


