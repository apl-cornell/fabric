package jif.types.principal;

import jif.types.label.AccessPath;

/** Dynamic principal. 
 */
public interface DynamicPrincipal extends Principal {
    AccessPath path();
}
