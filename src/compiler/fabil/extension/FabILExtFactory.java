package fabil.extension;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

public interface FabILExtFactory extends ExtFactory {
  Ext extAtomic();

  Ext extRetry();

  Ext extAbort();

  Ext extStage();

  Ext extStageCall();

  Ext extFabricArrayTypeNode();

  Ext extNewFabricArray();

  Ext extFabricArrayInit();

  Ext extProviderLabel();

  Ext extCodebaseNode();

  Ext extCodebaseDecl();

  Ext extCodebaseTypeNode();

  Ext extStoreGetter();
}
