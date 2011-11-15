package jif.ast;

import jif.types.ParamInstance;


/** An ambiguous expression parameter.
 */
public interface AmbExprParam extends AmbParam {
    AmbParam expectedPI(ParamInstance expectedPI);
}
