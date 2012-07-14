package codebases.ast;

import fabric.common.FabricLocation;

import polyglot.types.Qualifier;

public interface CodebaseQualifier extends Qualifier {
  FabricLocation namespace();
}
