package fabil.ast;

public interface NewArray extends polyglot.ast.NewArray, Annotated {
  ArrayInit init();
  NewArray init(polyglot.ast.ArrayInit init);
}
