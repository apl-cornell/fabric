package jif.types.label;

import polyglot.types.CodeInstance;
import polyglot.types.VarInstance;


/**
 * This label is used as a place-holder for method argument labels.
 * The purpose is to avoid having to re-interpret labels at each call.
 */
public interface ArgLabel extends Label {
    VarInstance formalInstance();
    Label upperBound();
    String name();
    void setName(String name);
    void setUpperBound(Label upperBound);
    void setCodeInstance(CodeInstance ci);
}
