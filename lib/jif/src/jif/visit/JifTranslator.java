package jif.visit;

import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.visit.TypedTranslator;

/** The Jif translator is used to generate Java files from
 *  the current ASTs.
 */
public class JifTranslator extends TypedTranslator
{
    public JifTranslator(Job job, TypeSystem ts, NodeFactory nf, TargetFactory tf) {
	super(job, ts, nf, tf);
    }

    protected boolean translateSource(SourceFile sfn) {
	// Don't translate classes in jif.lang.
	if (sfn.package_() != null &&
	    sfn.package_().equals(typeSystem().createPackage("jif.lang"))) {
	    return true;
	}

	return super.translateSource(sfn);
    }
}
