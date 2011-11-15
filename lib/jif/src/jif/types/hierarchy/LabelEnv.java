package jif.types.hierarchy;

import java.util.Map;
import java.util.Set;

import jif.types.Solver;
import jif.types.VarMap;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.label.Policy;
import jif.types.principal.Principal;

public interface LabelEnv
{
    // returns true if "L1 <= L2"
    boolean leq(Label L1, Label L2);

    // returns true if "L1 <= L2"
    boolean leq(Label L1, Label L2, SearchState state);

    boolean leq(Policy p1, Policy p2);
    boolean leq(Policy p1, Policy p2, SearchState state);

    boolean actsFor(Principal p, Principal q);

    /**
     * Finds an upper bound for L using the assertions in this environment.
     * May return the top label if there is insufficient information to
     * determine a more precise bound. 
     */
    Label findUpperBound(Label L);
    
    /**
     * Finds an upper bound for L that does not have any arg labels in it.
     * May return the top label if there is insufficient information to
     * determine a more precise bound. 
     */
    Label findNonArgLabelUpperBound(Label L);

    /**
     * Returns a Map of Strings to List[String]s which is the descriptions of any 
     * components that appear in the environment. This map is used for verbose 
     * output to the user, to help explain the meaning of constraints and 
     * labels.
     * 
     * Seen components is a Set of Labels whose definitions will not be 
     * displayed.
     */
    Map definitions(VarMap bounds, Set seenComponents);

    /**
     * Trigger any writersToReaders transforms in label, and return the result.
     */
    Label triggerTransforms(Label label);
    
    /**
     * Is this environment empty, or does is contain some constraints?
     */
    boolean isEmpty();
    
    /**
     * Do any of the assertions in this label environment contain variables? 
     */
    boolean hasVariables();
    
    /**
     * Set the solver used for this Label Environment. When necessary, the
     * label environment will use the variable bounds of label variables
     * when determining if constraints are satisfied.
     */
    void setSolver(Solver solver);
    
    /**
     * Encapsulates the solvers search state. 
     */
    public interface SearchState { }

    /**
     * This method checks if two access paths are equivalent. Called in two cases:
     * a. To check if a dynamic principal acts for another
     * b. While checking a label constraint involving a DynamicLabel
     * @param p
     * @param q
     * @return true if the two access paths are equivalent
     */
    boolean equivalentAccessPaths(AccessPath p, AccessPath q);
}
