package jif.types;

import jif.types.principal.ExternalPrincipal;
import polyglot.types.*;
import polyglot.util.*;

/**
 * A <code>PrincipalInstance</code> represents a global principal.
 */
public interface PrincipalInstance extends VarInstance 
{
    ExternalPrincipal principal();
    PrincipalInstance principal(ExternalPrincipal principal);
}
