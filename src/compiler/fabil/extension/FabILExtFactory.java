package fabil.extension;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import polyglot.ast.JL;

public interface FabILExtFactory extends ExtFactory {
  Ext extAtomic();
  Ext extRetry();
  Ext extAbort();
  Ext extFabricArrayTypeNode();
  Ext extNewFabricArray();
  Ext extFabricArrayInit();
  Ext extProviderLabel();
  Ext extCodebaseNode();
  Ext extCodebaseDecl();
}
