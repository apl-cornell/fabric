package jif.ast;

import java.util.List;

import jif.types.JifContext;
import polyglot.ast.ClassDecl;

/** An immutable representation of the Jif class declaration.
 *  It extends the Java class declaration with the label/principal parameters
 *  and the authority constraint.
 */
public interface JifClassDecl extends ClassDecl {
    List params();
    JifClassDecl params(List params);

    List authority();
    JifClassDecl authority(List authority);

    JifClassDecl type(polyglot.types.Type type);
    
    JifContext addParamsToContext(JifContext A);
    JifContext  addAuthorityToContext(JifContext A);    
}
