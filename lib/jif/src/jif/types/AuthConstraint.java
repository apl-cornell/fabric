package jif.types;

import polyglot.util.*;
import java.util.*;

/** The authority constraint. 
 */
public interface AuthConstraint extends Assertion {
    List principals();
    AuthConstraint principals(List principals);
}
