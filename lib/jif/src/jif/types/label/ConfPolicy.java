package jif.types.label;

import jif.types.hierarchy.LabelEnv;


/**
 * This class represents a Jif confidentiality policy, which is a lattice
 * over reader policies.
 */
public interface ConfPolicy extends Policy {
    boolean isBottomConfidentiality();
    boolean isTopConfidentiality();
    boolean leq_(ConfPolicy p, LabelEnv env, LabelEnv.SearchState state);
    ConfPolicy join(ConfPolicy p);
    ConfPolicy meet(ConfPolicy p);
}