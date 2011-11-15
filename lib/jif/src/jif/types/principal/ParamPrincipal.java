package jif.types.principal;

import jif.types.ParamInstance;


/** The principal derived from a principal parameter. 
 */
public interface ParamPrincipal extends Principal {
    ParamInstance paramInstance();
}
