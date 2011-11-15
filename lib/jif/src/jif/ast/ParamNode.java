package jif.ast;

import jif.types.Param;
import polyglot.ast.Node;

/** This class is the root of all the classes that may be parameters,
 *  including label node classes and principal node classes. 
 */
public interface ParamNode extends Node
{
    Param parameter();
}
