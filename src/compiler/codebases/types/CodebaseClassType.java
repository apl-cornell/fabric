package codebases.types;

import polyglot.types.ClassType;
import fabric.common.FabricLocation;

public interface CodebaseClassType extends ClassType {
  FabricLocation canonicalNamespace();
}
