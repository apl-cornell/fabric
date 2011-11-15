package jif.extension;

import jif.ast.JifUtil;
import jif.types.JifTypeSystem;
import polyglot.ast.JL_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.visit.Translator;
import polyglot.visit.TypeBuilder;

/** An implementation of the <code>Jif</code> interface. 
 */
public class JifJL_c extends JL_c
{
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) tb.typeSystem();
        Node n = super.buildTypes(tb);
        return JifUtil.updatePathMap(n, ts.pathMap());
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("cannot translate " + node() +
        "; still has a Jif extension");
    }
}
