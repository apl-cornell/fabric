package jif.ast;

import jif.types.ParamInstance;
import polyglot.ast.Node;

/** The AST node representing a label/principal parameter declaration.
 */
public interface ParamDecl extends Node
{
    String name();
    ParamDecl name(String name);

    ParamInstance.Kind kind();
    ParamDecl kind(ParamInstance.Kind kind);

    ParamInstance paramInstance();
    ParamDecl paramInstance(ParamInstance pi);

    boolean isPrincipal();
    boolean isLabel();
    boolean isInvariantLabel();
    boolean isCovariantLabel();
}
