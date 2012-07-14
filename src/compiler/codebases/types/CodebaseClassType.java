package codebases.types;

import fabric.common.FabricLocation;

import polyglot.types.ClassType;

public interface CodebaseClassType extends ClassType {
  FabricLocation canonicalNamespace();
}
