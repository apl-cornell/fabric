package jif.extension;

import jif.ast.Jif;
import jif.types.JifClassType;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>FieldDecl</code> node. 
 * 
 *  @see polyglot.ast.FieldDecl
 */
public interface JifFieldDeclExt extends Jif
{
    void labelCheckField(LabelChecker lc, JifClassType ct) throws SemanticException;
}
