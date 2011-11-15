package jif.types.label;

import jif.types.principal.Principal;

/** The policy label of the form <code>owner -> r1,&period;&period;&period;,rn</code>
 */
public interface ReaderPolicy extends ConfPolicy {
    Principal owner();
    Principal reader();
}

