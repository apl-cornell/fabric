package codebases.types;

import fabric.common.FabricLocation;
import polyglot.types.Context;

public interface CodebaseContext extends Context {
  FabricLocation namespace();

  FabricLocation resolveCodebaseName(String id);
}
