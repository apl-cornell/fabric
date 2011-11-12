package fabil.ast;

import polyglot.ast.TypeNode;

public interface New extends Annotated, polyglot.ast.New {
  @Override
  New objectType(TypeNode type);
}
