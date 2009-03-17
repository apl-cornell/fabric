package fabil.ast;

public interface NewFabricArray extends polyglot.ast.NewArray, Annotated {
  FabricArrayInit init();
  NewFabricArray init(polyglot.ast.ArrayInit init);
}
