package codebases.types;

import polyglot.types.Context;
import fabric.common.FabricLocation;

public interface CodebaseContext extends Context {
  FabricLocation namespace();

  FabricLocation resolveCodebaseName(String id);
}
