package fabil.extension;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

public interface FabILExtFactory extends ExtFactory {
  Ext extAtomic();
  Ext extRetry();
  Ext extAbort();
  Ext extFabricArrayTypeNode();
  Ext extNewFabricArray();
}
