package fabil.extension;

import polyglot.ast.DelFactory;
import polyglot.ast.JLDel;

public interface FabILDelFactory extends DelFactory {

  JLDel delFabricArrayTypeNode();

  JLDel delFabricArrayInit();

  JLDel delProviderLabel();

  JLDel delCodebaseNode();

  JLDel delCodebaseDecl();

  JLDel delCodebaseTypeNode();

}
