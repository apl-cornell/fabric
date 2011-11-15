package jif.types;

import jif.types.label.Label;
import polyglot.types.MethodInstance;

/** Jif method instance. A wrapper of all the type information related to
 *  a method. 
 */
public interface JifMethodInstance extends MethodInstance, JifProcedureInstance
{
    Label returnValueLabel();
}
