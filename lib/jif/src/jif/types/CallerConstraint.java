package jif.types;

import polyglot.util.*;
import java.util.*;

/** The caller constraint. 
 */
public interface CallerConstraint extends Assertion {
    List principals();
    CallerConstraint principals(List principals);
}
