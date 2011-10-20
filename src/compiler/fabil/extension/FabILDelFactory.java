package fabil.extension;

import polyglot.ast.DelFactory;
import polyglot.ast.JL;

public interface FabILDelFactory extends DelFactory {

  JL delFabricArrayTypeNode();
  JL delFabricArrayInit();
  JL delProviderLabel();
  JL delCodebaseNode();
  JL delCodebaseDecl();

}
