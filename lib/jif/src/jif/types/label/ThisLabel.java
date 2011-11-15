package jif.types.label;

import polyglot.types.ReferenceType;


/**
 * This label is used as a place-holder for the "this" label. It can be regarded
 * as being similar to an ArgLabel, as it is substituted away at every field access
 * and method call.
 * 
 * In the context of a class, it represents how much information may be gained
 * by knowing the reference to the "this" object, i.e., it is the label of
 * the special "this". 
 */
public interface ThisLabel extends Label {
    ReferenceType classType();
}
