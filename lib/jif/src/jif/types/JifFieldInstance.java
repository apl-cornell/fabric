package jif.types;

import polyglot.types.FieldInstance;

/** Jif field instance. A wrapper of all the type information related
 *  to a class field.
 */
public interface JifFieldInstance extends FieldInstance, JifVarInstance
{    
    boolean hasInitializer();
    void setHasInitializer(boolean hasInitializer);
}
