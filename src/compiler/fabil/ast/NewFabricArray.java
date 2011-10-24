package fabil.ast;

public interface NewFabricArray extends polyglot.ast.NewArray, Annotated {
  @Override
  FabricArrayInit init();
  
  @Override
  NewFabricArray init(polyglot.ast.ArrayInit init);
}
