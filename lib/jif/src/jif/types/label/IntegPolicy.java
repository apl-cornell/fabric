package jif.types.label;

import jif.types.hierarchy.LabelEnv;


/**
 * This class represents a Jif integrity policy which is a lattice over
 * writer policies.
 */
public interface IntegPolicy extends Policy {
    boolean isBottomIntegrity();
    boolean isTopIntegrity();   
    boolean leq_(IntegPolicy p, LabelEnv env, LabelEnv.SearchState state);
    IntegPolicy join(IntegPolicy p);
    IntegPolicy meet(IntegPolicy p);
}