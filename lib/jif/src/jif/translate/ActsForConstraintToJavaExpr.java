package jif.translate;

import java.io.Serializable;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;

import jif.types.ActsForConstraint;

public interface ActsForConstraintToJavaExpr extends Serializable {
  Expr toJava(ActsForConstraint actsfor, JifToJavaRewriter rw)
      throws SemanticException;
}
