package jif.types;

import polyglot.types.*;

/** The root of the Jif class parameter types.
 */
public interface Param extends TypeObject
{
    boolean isRuntimeRepresentable();
    boolean isCanonical();
}
