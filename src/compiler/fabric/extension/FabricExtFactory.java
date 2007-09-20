package fabric.extension;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

public interface FabricExtFactory extends ExtFactory {
  Ext extAtomic();
}
