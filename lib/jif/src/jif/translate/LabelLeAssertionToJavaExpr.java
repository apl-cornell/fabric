package jif.translate;

import java.io.Serializable;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;

import jif.types.LabelLeAssertion;

public interface LabelLeAssertionToJavaExpr extends Serializable {
  Expr toJava(LabelLeAssertion lla, JifToJavaRewriter rw) throws SemanticException;
}
