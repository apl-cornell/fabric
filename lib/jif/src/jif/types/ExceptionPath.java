package jif.types;

import polyglot.types.*;

/** The control flow path introduced by throwing a exception. 
 */
public interface ExceptionPath extends Path
{
    Type exception();
}
