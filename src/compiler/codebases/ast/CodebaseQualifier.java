package codebases.ast;

import polyglot.types.Qualifier;
import fabric.common.FabricLocation;

public interface CodebaseQualifier extends Qualifier {
  FabricLocation namespace();
}
